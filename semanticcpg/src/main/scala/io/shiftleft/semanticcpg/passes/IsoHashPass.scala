package io.shiftleft.semanticcpg.passes
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.Identifier
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, Properties, PropertyNames, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.IsoHashPass.computeHashes
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters._
import scala.collection.mutable

object IsoHashPass {

  //we embed the version of the hash computation in the leading byte of the hash. This is in order to recognize
  //when mismatches are caused by changes here.

  //whenever you change _anything_ in this computation, increment the version counter. If it wraps around, whatever.
  //skip zero -- long term we want zero isohash to mean unassigned, once we can get code generation under control and stop boxing everything.
  //this still gives us space for 1<<28 nodes in a CPG before we expect birthday collisions.
  val VERSION = 0x01
  val VERSION_OR = VERSION.toLong << 56
  val VERSION_MASK = 0x00ffffffffffffffL

  def getVersion(h: Long): Int = (h >>> 56).toInt
  def getVersion(): Int = VERSION
  val logger = LoggerFactory.getLogger(getClass)

  def computeHashes(method: nodes.Method): mutable.Map[nodes.StoredNode, Long] = {
    /* Conceptually, we walk through the CFG forwards, then backwards and mix together hashes.
     *
     * Unfortunately there are three complications:
     *
     * First, some frontends emit METHOD within METHOD. This is an abomination, but we need to be able to deal with it.
     *
     * Second, there may be unreachable code, and non-returning (i.e. not reachable backwards from METHOD_RETURN) code.
     * This is valid: Not because the code is really unreachable, but e.g. because of exception handlers.
     * However, we do want such code to participate in the isohash.
     *
     * Third, some frontends need to auto-generate names during lowering (e.g. jvm for stackvalues).
     * This is somewhat non-deterministic, so we ignore names of local variables, and only use equivalence classes of these.
     * */
    try {
      val hashes = mutable.HashMap[nodes.StoredNode, Long]().withDefault(initHash)
      val visited = mutable.HashSet[nodes.StoredNode]()
      var current = mutable.ArrayBuffer[nodes.StoredNode]()
      var next = mutable.ArrayBuffer[nodes.StoredNode]()
      val additions = mutable.ArrayBuffer[(nodes.StoredNode, Long)]()

      val nodesForString = mutable.MultiDict[String, nodes.Identifier]()
      val totalSet = method._containsOut.asScala.filterNot { _.isInstanceOf[nodes.Method] }.to(mutable.HashSet)
      totalSet.foreach { case id: nodes.Identifier => nodesForString.addOne((id.name, id)); case _ => }
      totalSet.add(method)
      totalSet.add(method.methodReturn)

      (method._methodParameterInViaAstOut ++ method._methodParameterOutViaAstOut).foreach { p =>
        totalSet.add(p);
      }
      for (param <- method._methodParameterInViaAstOut) {
        val h = hashes(param)
        for (node <- nodesForString.get(param.name)) {
          hashes(node) += h
        }
      }

      def walkCfg(reverse: Boolean): Unit = {
        var done = false
        while (!done) {
          for (c <- current) {
            val h = fmix64(hashes(c))
            hashes(c) = h
            val iter = if (!reverse) c._cfgOut.asScala else c._cfgIn.asScala
            for (nx <- iter) {
              additions.append((nx, h))
              nx match {
                case id: nodes.Identifier =>
                  for (other <- nodesForString.get(id.name) if other != id) {
                    additions.append((other, h))
                  }
                case _ =>
              }
              if (visited.add(nx)) { next.append(nx) }
            }
          }
          for ((n, h) <- additions) { hashes(n) += h }
          additions.clear
          current.clear
          val tmp = current
          current = next
          next = tmp
          done = current.isEmpty
        }
      }

      //walk cfg forwards
      current.append(method)
      for (node <- totalSet if !node._cfgIn.hasNext) {
        visited.add(node)
        current.append(node)
      }
      if (visited.add(method)) {
        current.append(method)
      }
      walkCfg(reverse = false)
      visited.clear

      //clear leftover linear dependencies
      hashes.mapValuesInPlace { case (n, h) => fmix64(h) }

      //propagate between identifiers
      for (name <- nodesForString.keySet) {
        val hh = nodesForString.get(name).iterator.map(hashes).sum
        for (id <- nodesForString.get(name)) {
          hashes(id) = fmix64(hashes(id) + hh)
        }
      }

      //walk cfg backwards
      for (node <- totalSet if !node._cfgOut.hasNext) {
        visited.add(node)
        current.append(node)
      }
      if (visited.add(method.methodReturn)) {
        current.append(method.methodReturn)
      }
      walkCfg(reverse = true)
      //there might still be linear relationships (from additions), so kill them
      hashes.mapValuesInPlace { case (n, h) => fmix64(h) }
      //we want each item to have global influence
      val sum = fmix64(hashes.valuesIterator.sum)
      hashes.mapValuesInPlace { case (n, h) => (fmix64(h ^ sum) & VERSION_MASK) | VERSION_OR }
      hashes.filterInPlace { case (n, h)    => n.isInstanceOf[nodes.HasIsoHash] && totalSet.contains(n) }

      hashes
    } catch {
      case exc: Exception =>
        logger.warn(s"Failed IsoHash computation for ${method.fullName}", exc)
        return mutable.Map()
    }
  }

  def initHash(node: nodes.StoredNode): Long = {
    var h = fmix64(node.label.hashCode)
    node match {
      case n: nodes.Method =>
        h = fmix64(h + n.fullName.hashCode)
      case n: nodes.MethodParameterIn =>
        h = fmix64(h + n.order)
        h = fmix64(h + n.name.hashCode)
      case n: nodes.FieldIdentifier =>
        h = fmix64(h + n.canonicalName.hashCode)
      case n: nodes.Literal =>
        h = fmix64(h + n.code.hashCode)
        n.method
      case n: nodes.MethodRef =>
        h = fmix64(h + n.methodFullName.hashCode)
      case n: nodes.TypeRef =>
        h = fmix64(h + n.typeFullName.hashCode)
      case n: nodes.Call if n.dispatchType == "STATIC_DISPATCH" =>
        h = fmix64(h + n.methodFullName.hashCode)
      case n: nodes.Call if n.dispatchType == "DYNAMIC_DISPATCH" =>
        h = fmix64(h + n.name.hashCode)
        h = fmix64(h + n.signature.hashCode)
      case n: nodes.MethodParameterIn =>
        h = fmix64(h + n.order)
      case n: nodes.MethodParameterOut =>
        h = fmix64(h + n.order)
      case _ =>
    }
    node match {
      case n: nodes.HasArgumentIndex if n._argumentIn.hasNext =>
        h = fmix64(h + n.argumentIndex)
      case _ =>
    }
    h
  }

  def fmix64(h0: Int): Long = fmix64(h0.toLong)
  def fmix64(h0: Long): Long = {
    //this is fmix64 from murmur3_128
    var h = h0
    h ^= h >>> 33
    h *= 0xff51afd7ed558ccdL
    h ^= h >>> 33
    h *= 0xc4ceb9fe1a85ec53L
    h ^= h >>> 33
    h
  }

}

class IsoHashPass(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    val isohashes = java.util.Arrays
      .stream(cpg.method.toArray)
      .parallel
      .map(computeHashes)
      .toArray
    val dstGraph = DiffGraph.newBuilder
    val hasHash = mutable.HashSet[nodes.StoredNode]()
    isohashes.foreach {
      case forMethod: mutable.Map[nodes.StoredNode, Long] =>
        forMethod.foreach {
          case (n: nodes.HasIsoHash, h: Long) =>
            if (hasHash.add(n)) {
              dstGraph.addNodeProperty(n, PropertyNames.ISO_HASH, java.lang.Long.valueOf(h))
            }
          case _ =>
        }
    }
    //we made the isohash field mandatory, but only computed it for nodes that are reachable from either METHOD or METHOD_RETURN.
    //this especially means that code in exception handlers that rethrow the exception may be unvisited.
    //in order to avoid NPE, we zero the remaining ones.
    cpg.all.foreach {
      case n: nodes.HasIsoHash =>
        if (hasHash.add(n)) {
          dstGraph.addNodeProperty(n, PropertyNames.ISO_HASH, java.lang.Long.valueOf(0L))
        }
      case _ =>
    }
    Iterator(dstGraph.build)
  }

}
