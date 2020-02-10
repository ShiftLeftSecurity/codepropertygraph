package io.shiftleft.dataflowengine.passes.reachingdef

import java.nio.file.Paths

import gremlin.scala._
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{Call, FieldIdentifier, Identifier, Literal, StoredNode}
import io.shiftleft.codepropertygraph.generated.{nodes, _}
import io.shiftleft.passes.{CpgPass, DiffGraph, ParallelIteratorExecutor}
import io.shiftleft.semanticcpg.utils.{ExpandTo, MemberAccess}
import io.shiftleft.semanticcpg.language._

import scala.collection.mutable
import scala.jdk.CollectionConverters._

class ReachingDefPass(cpg: Cpg) extends CpgPass(cpg) {
  lazy val dfHelper: DataFlowFrameworkHelper = new DataFlowFrameworkHelper(cpg.graph)

  override def run(): Iterator[DiffGraph] = {
    val methods = cpg.method.toIterator

    new ParallelIteratorExecutor(methods).map { method =>
      val dstGraph = DiffGraph.newBuilder
      var worklist = mutable.Set.empty[nodes.CfgNode]
      var out = Map.empty[nodes.StoredNode, Set[nodes.StoredNode]].withDefaultValue(Set.empty[nodes.StoredNode])
      var in = Map.empty[nodes.StoredNode, Set[nodes.StoredNode]].withDefaultValue(Set.empty[nodes.StoredNode])
      val allCfgNodes = method.cfgNode.to(List)

      val mapExpressionsGens = dfHelper.expressionsToGenMap(method).withDefaultValue(Set.empty[nodes.StoredNode])
      val mapExpressionsKills = dfHelper.expressionsToKillMap(method).withDefaultValue(Set.empty[nodes.StoredNode])

      /*Initialize the OUT sets*/
      allCfgNodes.foreach { cfgNode =>
        if (mapExpressionsGens.contains(cfgNode)) {
          out += cfgNode -> mapExpressionsGens(cfgNode)
        }
      }

      worklist ++= allCfgNodes

      while (worklist.nonEmpty) {
        val currentCfgNode = worklist.head
        worklist = worklist.tail

        var inSet = Set.empty[nodes.StoredNode]

        currentCfgNode._cfgIn.asScala.foreach { cfgPredecessor =>
          inSet ++= inSet.union(out(cfgPredecessor))
        }

        in += currentCfgNode -> inSet

        val oldSize = out(currentCfgNode).size
        val gens = mapExpressionsGens(currentCfgNode)
        val kills = mapExpressionsKills(currentCfgNode)

        out += currentCfgNode -> gens.union(inSet.diff(kills))
        val newSize = out(currentCfgNode).size

        if (oldSize != newSize)
          worklist ++= currentCfgNode._cfgOut.asScala.collect { case cfgNode: nodes.CfgNode => cfgNode }
      }

      addReachingDefEdge(dstGraph, method, out, in)
      dstGraph.build()
    }
  }

  /** Pruned DDG, i.e., two call assignment vertices are adjacent if a
    * reaching definition edge reaches a vertex where the definition is used.
    * The final representation makes it straightforward to build def-use/use-def chains */
  private def addReachingDefEdge(dstGraph: DiffGraph.Builder,
                                 method: nodes.Method,
                                 outSet: Map[nodes.StoredNode, Set[nodes.StoredNode]],
                                 inSet: Map[nodes.StoredNode, Set[nodes.StoredNode]]): Unit = {

    def addEdge(fromNode: nodes.StoredNode, toNode: nodes.StoredNode): Unit =
      dstGraph.addEdgeInOriginal(fromNode, toNode, EdgeTypes.REACHING_DEF)

    for {
      methodParameterIn <- method.astOut.asScala.collect { case paramIn: nodes.MethodParameterIn => paramIn }
      refInIdentifier <- methodParameterIn.refIn.asScala
      operationNode <- dfHelper.getOperation(refInIdentifier)
    } addEdge(methodParameterIn, operationNode)

    val methodReturn = method.methodReturn
    methodReturn.toReturn.foreach(returnNode => addEdge(returnNode, methodReturn))

    outSet.foreach {
      case (call: nodes.Call, outDefs) =>
        handleCall(call, outDefs)
      case (ret: nodes.Return, _) =>
        ret.astOut.asScala.foreach { returnExpr =>
          val localRef = reference(returnExpr)
          inSet(ret).filter(inElement => localRef == reference(inElement)).foreach { filteredInElement =>
            dfHelper.getExpressionFromGen(filteredInElement).foreach(addEdge(_, ret))
          }
        }
      case _ => // ignore
    }

    def handleCall(call: Call, outDefs: Set[StoredNode]) = {
      val usesInExpression = dfHelper.getUsesOfExpression(call)
      val localRefsUses = usesInExpression.map(reference).filter(_ != None)

      /* if use is not an identifier, add edge, as we are going to visit the use separately */
      usesInExpression.foreach { use =>
        if (!use.isInstanceOf[Identifier] && !use.isInstanceOf[Literal] && !use
              .isInstanceOf[FieldIdentifier]) {
          addEdge(use, call)

          /* handle indirect access uses: check if we have it in our out set and get
           * the corresponding def expression from which the definition reaches the use
           */
          if (isIndirectAccess(use)) {
            outDefs.filter(out => isIndirectAccess(out)).foreach { indirectOutDef =>
              val indirectOutCall = indirectOutDef.asInstanceOf[Call]
              if (indirectOutCall.code == use.asInstanceOf[Call].code) {
                val expandedToCall = indirectOutCall.parentExpression
                addEdge(expandedToCall, use)
              }
            }
          }
        }
      }

      if (isOperationAndAssignment(call)) {
        val localRefGens = dfHelper.getGensOfExpression(call).map(reference)
        inSet(call)
          .filter(inElement => localRefGens.contains(reference(inElement)))
          .foreach { filteredInElement =>
            dfHelper.getExpressionFromGen(filteredInElement).foreach(addEdge(_, call))
          }
      }

      for (elem <- outDefs) {
        val localRefGen = reference(elem)

        dfHelper.getExpressionFromGen(elem).foreach { expressionOfElement =>
          if (expressionOfElement != call && localRefsUses.contains(localRefGen)) {
            addEdge(expressionOfElement, call)
          }
        }
      }
    }
  }

  private def reference(node: nodes.StoredNode): Option[nodes.StoredNode] =
    node._refOut.nextOption

  def toDot(graph: ScalaGraph): String = {
    val buf = new StringBuffer()

    buf.append("digraph g {\n node[shape=plaintext];\n")

    graph.E.hasLabel(EdgeTypes.REACHING_DEF).l.foreach { e =>
      val inV = vertexToStr(e.inVertex).replace("\"", "\'")
      val outV = vertexToStr(e.outVertex).replace("\"", "\'")
      buf.append(s""" "$outV" -> "$inV";\n """)
    }
    buf.append("}")
    buf.toString
  }

  /** For debug purposes */
  def vertexToStr(vertex: Vertex): String = {
    try {
      val methodVertex = ExpandTo.expressionToMethod(vertex)
      val fileName = ExpandTo.methodToFile(methodVertex) match {
        case Some(objFile) => objFile.value2(NodeKeys.NAME)
        case None          => "NA"
      }

      s"${Paths.get(fileName).getFileName.toString}: ${vertex.value2(NodeKeys.LINE_NUMBER).toString} ${vertex.value2(NodeKeys.CODE)}"
    } catch {
      case _: Exception => ""
    }
  }

  private def isOperationAndAssignment(call: nodes.Call): Boolean = {
    call.name match {
      case Operators.assignmentAnd                  => true
      case Operators.assignmentArithmeticShiftRight => true
      case Operators.assignmentDivision             => true
      case Operators.assignmentExponentiation       => true
      case Operators.assignmentLogicalShiftRight    => true
      case Operators.assignmentMinus                => true
      case Operators.assignmentModulo               => true
      case Operators.assignmentMultiplication       => true
      case Operators.assignmentOr                   => true
      case Operators.assignmentPlus                 => true
      case Operators.assignmentShiftLeft            => true
      case Operators.assignmentXor                  => true
      case _                                        => false
    }
  }

  private def isIndirectAccess(node: nodes.StoredNode): Boolean =
    node match {
      case call: nodes.Call => MemberAccess.isGenericMemberAccessName(call.name)
      case _                => false
    }

}

/** Common functionalities needed for data flow frameworks */
class DataFlowFrameworkHelper(graph: ScalaGraph) {

  private def callToMethodParamOut(call: nodes.StoredNode): Seq[nodes.StoredNode] = {
    ExpandTo.callToCalledMethod(call).flatMap(method => ExpandTo.methodToOutParameters(method))
  }

  private def filterArgumentIndex(vertexList: List[nodes.StoredNode], orderSeq: Seq[Int]): List[nodes.StoredNode] = {
    vertexList.filter(v => orderSeq.contains(v.asInstanceOf[nodes.HasArgumentIndex].argumentIndex.toInt))
  }

  def getExpressions(method: nodes.Method): List[nodes.Call] =
    method.containsOut.asScala.collect { case call: nodes.Call => call }.to(List)

  def getGensOfExpression(expr: nodes.StoredNode): Set[nodes.StoredNode] = {
    var gens = Set[nodes.StoredNode]()
    val methodParamOutsOrder = callToMethodParamOut(expr)
      .filter(methPO => methPO._propagateIn.hasNext)
      .map(_.asInstanceOf[nodes.HasOrder].order.toInt)

    val identifierWithOrder =
      filterArgumentIndex(expr._argumentOut.asScala.toList, methodParamOutsOrder)
    gens ++= identifierWithOrder

    gens
  }

  def getUsesOfExpression(expr: nodes.StoredNode): Set[nodes.StoredNode] = {
    expr._argumentOut.asScala
      .filter(!getGensOfExpression(expr).contains(_))
      .toSet
  }

  def getExpressionFromGen(genVertex: nodes.StoredNode): Option[nodes.StoredNode] = {
    getOperation(genVertex).filter(_.isInstanceOf[nodes.Call])
  }

  /** Returns a set of vertices that are killed by the passed vertex */
  def killsVertices(vertex: nodes.StoredNode): Set[nodes.StoredNode] = {
    val localRefIt = vertex._refOut.asScala

    if (!localRefIt.hasNext) {
      Set()
    } else {
      val localRef = localRefIt.next
      localRef._refIn.asScala.filter(_.id != vertex.id).toSet
    }
  }

  def kills(vertex: Set[nodes.StoredNode]): Set[nodes.StoredNode] = {
    vertex.map(v => killsVertices(v)).fold(Set())((v1, v2) => v1.union(v2))
  }

  def expressionsToKillMap(method: nodes.Method): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {
    val genExpressions = getExpressions(method)

    genExpressions.map { expression =>
      val gens = getGensOfExpression(expression)
      expression -> kills(gens)
    }.toMap
  }

  def expressionsToGenMap(method: nodes.Method): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {
    /*genExpressions correspond to call assignment nodes*/
    val genExpressions = getExpressions(method)
    genExpressions.map { genExpression =>
      genExpression -> getGensOfExpression(genExpression)
    }.toMap
  }

  def getOperation(node: nodes.StoredNode): Option[nodes.StoredNode] = {
    node match {
      case identifier: nodes.Identifier =>
        if (identifier.argumentIn.hasNext) {
          getOperation(identifier.argumentIn.nextChecked)
        } else {
          None
        }
      case _: nodes.Call   => Some(node)
      case _: nodes.Return => Some(node)
      case _               => None
    }
  }
}
