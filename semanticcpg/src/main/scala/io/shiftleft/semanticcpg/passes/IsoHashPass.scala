package io.shiftleft.semanticcpg.passes
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.Identifier
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, Properties, PropertyNames, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.IsoHashPass.computeHashes

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

  def computeHashes(method: nodes.Method): mutable.HashMap[nodes.StoredNode, Long] = {
    val hashes = mutable.HashMap[nodes.StoredNode, Long]()
    val visited = mutable.HashSet[nodes.StoredNode]()
    var current = mutable.ArrayBuffer[nodes.StoredNode]()
    var next = mutable.ArrayBuffer[nodes.StoredNode]()

    var done = false
    hashes.put(method, initHash(method))
    visited.add(method)
    current.append(method)

    //we walk through the CFG forwards, in breadth-first-order, and propagate hash values
    while (!done) {
      for (c <- current if !c.isInstanceOf[nodes.MethodReturn]) {
        val h = fmix64(hashes(c))
        hashes(c) = h
        for (nx <- c._cfgOut.asScala if !nx.isInstanceOf[nodes.Method]) {
          hashes.updateWith(nx) {
            case Some(hh) => Some(hh + h)
            case None     => Some(initHash(nx) + h)
          }
          if (visited.add(nx)) next.append(nx)
        }
      }
      current.clear
      val tmp = current
      current = next
      next = tmp
      done = current.isEmpty
    }
    visited.clear
    hashes.mapValuesInPlace { case (n, h) => fmix64(h) }

    done = false
    val ret = method.methodReturn
    hashes.getOrElseUpdate(ret, initHash(ret))
    visited.add(ret)
    current.append(ret)
    //we walk through the CFG backwards, in breadth-first-order, and propagate hash values
    while (!done) {
      for (c <- current if !c.isInstanceOf[nodes.Method]) {
        val h = fmix64(hashes(c))
        hashes(c) = h
        for (nx <- c._cfgIn.asScala if !nx.isInstanceOf[nodes.MethodReturn]) {
          hashes.updateWith(nx) {
            case Some(hh) => Some(hh + h)
            case None     => Some(initHash(nx) + h)
          }
          if (visited.add(nx)) next.append(nx)
        }
      }
      current.clear
      val tmp = current
      current = next
      next = tmp
      done = current.isEmpty
    }

    //there might still be linear relationships, so kill them
    hashes.mapValuesInPlace { case (n, h) => (fmix64(h) & VERSION_MASK) | VERSION_OR }
    //the backwards pass has not propagated code that is postdominated by a throw, so let's fix that
    val sum = hashes.valuesIterator.sum
    hashes(method) = (fmix64(sum) & VERSION_MASK) | VERSION_OR
    hashes
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
      case n: Identifier =>
        h = fmix64(h + n.name.hashCode)
      case n: nodes.Literal =>
        h = fmix64(h + n.code.hashCode)
      case n: nodes.MethodRef =>
        h = fmix64(h + n.methodFullName.hashCode)
      case n: nodes.TypeRef =>
        h = fmix64(h + n.typeFullName.hashCode)
      case n: nodes.Call if n.dispatchType == "STATIC_DISPATCH" =>
        h = fmix64(h + n.methodFullName.hashCode)
      case n: nodes.Call if n.dispatchType == "DYNAMIC_DISPATCH" =>
        h = fmix64(h + n.name.hashCode)
        h = fmix64(h + n.signature.hashCode)
      case _ =>
    }
    node match {
      case n: nodes.HasArgumentIndex if n._argumentIn.hasNext =>
        h = fmix64(h + n.argumentIndex)
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
    val hasHash = mutable.HashSet[nodes.CfgNode]()
    isohashes.foreach {
      case forMethod: mutable.HashMap[nodes.StoredNode, Long] =>
        forMethod.foreach {
          case (n: nodes.CfgNode, h: Long) =>
            dstGraph.addNodeProperty(n, PropertyNames.ISOHASH, java.lang.Long.valueOf(h))
            hasHash.add(n)
          case _ =>
        }
    }
    //we made the isohash field mandatory, but only computed it for nodes that are reachable from either METHOD or METHOD_RETURN.
    //this especially means that code in exception handlers that rethrow the exception may be unvisited.
    //in order to avoid NPE, we zero the remaining ones.
    cpg.all.foreach {
      case n: nodes.CfgNode if hasHash.add(n) =>
        dstGraph.addNodeProperty(n, PropertyNames.ISOHASH, java.lang.Long.valueOf(0L))
      case _ =>
    }
    Iterator(dstGraph.build)
  }

}
