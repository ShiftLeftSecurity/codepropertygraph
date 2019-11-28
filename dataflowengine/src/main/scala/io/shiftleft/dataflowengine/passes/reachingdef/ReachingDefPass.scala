package io.shiftleft.dataflowengine.passes.reachingdef

import java.nio.file.Paths

import gremlin.scala._
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.passes.{CpgPass, DiffGraph, ParallelIteratorExecutor}
import io.shiftleft.semanticcpg.utils.{ExpandTo, MemberAccess}
import org.apache.tinkerpop.gremlin.structure.Direction
import io.shiftleft.semanticcpg.language._

import scala.collection.JavaConverters._

class ReachingDefPass(cpg: Cpg) extends CpgPass(cpg) {
  var dfHelper: DataFlowFrameworkHelper = _

  override def run(): Iterator[DiffGraph] = {
    val methods = cpg.method.toIterator()

    dfHelper = new DataFlowFrameworkHelper(cpg.graph)

    new ParallelIteratorExecutor(methods).map { method =>
      val dstGraph = DiffGraph.newBuilder
      var worklist = Set[nodes.StoredNode]()
      var out = Map[nodes.StoredNode, Set[nodes.StoredNode]]().withDefaultValue(Set[nodes.StoredNode]())
      var in = Map[nodes.StoredNode, Set[nodes.StoredNode]]().withDefaultValue(Set[nodes.StoredNode]())
      val allCfgNodes = ExpandTo.allCfgNodesOfMethod(method).toList

      val mapExpressionsGens = dfHelper.expressionsToGenMap(method).withDefaultValue(Set[nodes.StoredNode]())
      val mapExpressionsKills = dfHelper.expressionsToKillMap(method).withDefaultValue(Set[nodes.StoredNode]())

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

        var inSet = Set[nodes.StoredNode]()

        val cfgPredecessors = currentCfgNode._cfgIn.asScala
        cfgPredecessors.foreach { pred =>
          inSet ++= inSet.union(out(pred))
        }

        in += currentCfgNode -> inSet

        val oldSize = out(currentCfgNode).size
        val gens = mapExpressionsGens(currentCfgNode)
        val kills = mapExpressionsKills(currentCfgNode)

        out += currentCfgNode -> gens.union(inSet.diff(kills))
        val newSize = out(currentCfgNode).size

        if (oldSize != newSize)
          worklist ++= currentCfgNode._cfgOut.asScala.toList
      }

      addReachingDefEdge(dstGraph, method, out, in)
      dstGraph.build()
    }
  }

  /** Pruned DDG, i.e., two call assignment vertices are adjacent if a
    * reaching definition edge reaches a vertex where the definition is used.
    * The final representation makes it straightforward to build def-use/use-def chains */
  private def addReachingDefEdge(dstGraph: DiffGraph.Builder,
                                 method: nodes.StoredNode,
                                 outSet: Map[nodes.StoredNode, Set[nodes.StoredNode]],
                                 inSet: Map[nodes.StoredNode, Set[nodes.StoredNode]]): Unit = {

    def addEdge(v0: nodes.StoredNode, v1: nodes.StoredNode): Unit = {
      dstGraph.addEdgeInOriginal(v0, v1, EdgeTypes.REACHING_DEF)
    }

    method._astOut.asScala.filter(_.isInstanceOf[nodes.MethodParameterIn]).foreach { methodParameterIn =>
      methodParameterIn._refIn.asScala.foreach { refInIdentifier =>
        dfHelper
          .getOperation(refInIdentifier)
          .foreach(operationNode => addEdge(methodParameterIn, operationNode))
      }
    }

    val methodReturn = ExpandTo.methodToFormalReturn(method)

    ExpandTo
      .formalReturnToReturn(methodReturn)
      .foreach(returnVertex => addEdge(returnVertex, methodReturn))

    outSet.foreach {
      case (node, outDefs) =>
        if (node.isInstanceOf[nodes.Call]) {
          val usesInExpression = dfHelper.getUsesOfExpression(node)
          val localRefsUses = usesInExpression.map(ExpandTo.reference(_)).filter(_ != None)

          /* if use is not an identifier, add edge, as we are going to visit the use separately */
          usesInExpression.foreach { use =>
            if (!use.isInstanceOf[nodes.Identifier] && !use.isInstanceOf[nodes.Literal]) {
              addEdge(use, node)

              /* handle indirect access uses: check if we have it in our out set and get
               * the corresponding def expression from which the definition reaches the use
               */
              if (isIndirectAccess(use)) {
                outDefs.filter(out => isIndirectAccess(out)).foreach { indirectOutDef =>
                  if (indirectOutDef.asInstanceOf[nodes.Call].code == use.asInstanceOf[nodes.Call].code) {
                    val expandedToCall = ExpandTo.argumentToCallOrReturn(indirectOutDef)
                    addEdge(expandedToCall, use)
                  }
                }
              }
            }
          }

          val nodeIsOperandAssignment = isOperationAndAssignment(node)
          if (nodeIsOperandAssignment) {
            val localRefGens = dfHelper.getGensOfExpression(node).map(ExpandTo.reference(_))
            inSet(node)
              .filter(inElement => localRefGens.contains(ExpandTo.reference(inElement)))
              .foreach { filteredInElement =>
                dfHelper.getExpressionFromGen(filteredInElement).foreach(addEdge(_, node))
              }
          }

          for (elem <- outDefs) {
            val localRefGen = ExpandTo.reference(elem)

            dfHelper.getExpressionFromGen(elem).foreach { expressionOfElement =>
              if (expressionOfElement != node && localRefsUses.contains(localRefGen)) {
                addEdge(expressionOfElement, node)
              }
            }
          }
        } else if (node.isInstanceOf[nodes.Return]) {
          node._astOut.asScala.foreach { returnExpr =>
            val localRef = ExpandTo.reference(returnExpr)
            inSet(node).filter(inElement => localRef == ExpandTo.reference(inElement)).foreach { filteredInElement =>
              dfHelper.getExpressionFromGen(filteredInElement).foreach(addEdge(_, node))
            }
          }
        }
    }
  }

  def toDot(graph: ScalaGraph): String = {
    val buf = new StringBuffer()

    buf.append("digraph g {\n node[shape=plaintext];\n")

    graph.E.hasLabel(EdgeTypes.REACHING_DEF).l.foreach { e =>
      val inV = vertexToStr(e.inVertex).replace("\"", "\'")
      val outV = vertexToStr(e.outVertex).replace("\"", "\'")
      buf.append(s""" "$outV" -> "$inV";\n """)
    }
    buf.append { "}" }
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
    } catch { case _: Exception => "" }
  }

  private def isOperationAndAssignment(vertex: nodes.StoredNode): Boolean = {
    if (!vertex.isInstanceOf[nodes.Call]) {
      return false
    }

    val name = vertex.asInstanceOf[nodes.Call].name
    name match {
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

  private def isIndirectAccess(vertex: nodes.StoredNode): Boolean = {
    if (!vertex.isInstanceOf[nodes.Call]) {
      return false
    }

    val callName = vertex.asInstanceOf[nodes.Call].name
    MemberAccess.isGenericMemberAccessName(callName)
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

  def getExpressions(method: nodes.StoredNode): List[nodes.StoredNode] = {
    val callNodes = method._containsOut.asScala
      .filter(_.isInstanceOf[nodes.Call])
      .toList

    callNodes
  }

  def getGenSet(method: nodes.StoredNode): Set[nodes.StoredNode] = {
    var genSet = Set[nodes.StoredNode]()
    getExpressions(method).foreach { genExpression =>
      val methodParamOutsOrder = callToMethodParamOut(genExpression)
        .filter(_._propagateIn.hasNext)
        .map(_.asInstanceOf[nodes.HasOrder].order.toInt)

      val identifierWithOrder =
        filterArgumentIndex(genExpression._argumentOut.asScala.toList, methodParamOutsOrder)
      genSet ++= identifierWithOrder
    }
    genSet
  }

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

  def expressionsToKillMap(methodVertex: nodes.StoredNode): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {
    val genExpressions = getExpressions(methodVertex)

    genExpressions.map { expression =>
      val gens = getGensOfExpression(expression)
      expression -> kills(gens)
    }.toMap
  }

  def expressionsToGenMap(methodVertex: nodes.StoredNode): Map[nodes.StoredNode, Set[nodes.StoredNode]] = {
    /*genExpressions correspond to call assignment nodes*/
    val genExpressions = getExpressions(methodVertex)
    genExpressions.map { genExpression =>
      genExpression -> getGensOfExpression(genExpression)
    }.toMap
  }

  def getOperation(vertex: nodes.StoredNode): Option[nodes.StoredNode] = {
    vertex match {
      case _: nodes.Identifier =>
        if (vertex._argumentIn().hasNext) {
          getOperation(vertex._argumentIn().nextChecked)
        } else {
          None
        }
      case _: nodes.Call   => Some(vertex)
      case _: nodes.Return => Some(vertex)
      case _               => None
    }
  }
}
