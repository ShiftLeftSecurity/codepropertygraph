package io.shiftleft.dataflowengine.passes.reachingdef

import java.nio.file.Paths

import gremlin.scala._
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.utils.{ExpandTo, MemberAccess}
import org.apache.tinkerpop.gremlin.structure.Direction
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

class ReachingDefPass(cpg: Cpg) extends CpgPass(cpg) {
  var dfHelper: DataFlowFrameworkHelper = _

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = new DiffGraph()
    val methods = cpg.method.l

    dfHelper = new DataFlowFrameworkHelper(cpg.graph)

    methods.foreach { method =>
      var worklist = Set[Vertex]()
      var out = Map[Vertex, Set[Vertex]]().withDefaultValue(Set[Vertex]())
      var in = Map[Vertex, Set[Vertex]]().withDefaultValue(Set[Vertex]())
      val allCfgNodes = ExpandTo.allCfgNodesOfMethod(method).iterator.to(List)

      val mapExpressionsGens = dfHelper.expressionsToGenMap(method).withDefaultValue(Set[Vertex]())
      val mapExpressionsKills = dfHelper.expressionsToKillMap(method).withDefaultValue(Set[Vertex]())

      /*Initialize the OUT sets*/
      allCfgNodes.foreach { cfgNode =>
        if (mapExpressionsGens.contains(cfgNode)) {
          out += cfgNode -> mapExpressionsGens(cfgNode)
        }
      }

      worklist ++= allCfgNodes

      while (worklist.nonEmpty) {
        var currentCfgNode = worklist.head
        worklist = worklist.tail

        var inSet = Set[Vertex]()

        val cfgPredecessors = currentCfgNode.vertices(Direction.IN, EdgeTypes.CFG).asScala
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
          worklist ++= currentCfgNode.vertices(Direction.OUT, EdgeTypes.CFG).asScala.toList
      }

      addReachingDefEdge(dstGraph, method, out, in)
    }

    Iterator(dstGraph)
  }

  /** Pruned DDG, i.e., two call assignment vertices are adjacent if a
    * reaching definition edge reaches a vertex where the definition is used.
    * The final representation makes it straightforward to build def-use/use-def chains */
  private def addReachingDefEdge(dstGraph: DiffGraph,
                                 method: Vertex,
                                 outSet: Map[Vertex, Set[Vertex]],
                                 inSet: Map[Vertex, Set[Vertex]]): Unit = {

    def addEdge(v0: Vertex, v1: Vertex): Unit = {
      dstGraph.addEdgeInOriginal(v0.asInstanceOf[nodes.StoredNode],
                                 v1.asInstanceOf[nodes.StoredNode],
                                 EdgeTypes.REACHING_DEF)
    }

    method.vertices(Direction.OUT, EdgeTypes.AST).asScala.filter(_.isInstanceOf[nodes.MethodParameterIn]).foreach {
      methodParameterIn =>
        methodParameterIn.vertices(Direction.IN, EdgeTypes.REF).asScala.foreach { refInIdentifier =>
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
          var localRefsUses = usesInExpression.map(ExpandTo.reference(_)).filter(_ != None)

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
                val expr = dfHelper.getExpressionFromGen(filteredInElement).foreach(addEdge(_, node))
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
          node.vertices(Direction.OUT, EdgeTypes.AST).asScala.foreach { returnExpr =>
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

  private def isOperationAndAssignment(vertex: Vertex): Boolean = {
    if (!vertex.isInstanceOf[nodes.Call]) {
      return false
    }

    val name = vertex.value2(NodeKeys.NAME)
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

  private def isIndirectAccess(vertex: Vertex): Boolean = {
    if (!vertex.isInstanceOf[nodes.Call]) {
      return false
    }

    val callName = vertex.value2(NodeKeys.NAME)
    MemberAccess.isGenericMemberAccessName(callName)
  }

}

/** Common functionalities needed for data flow frameworks */
class DataFlowFrameworkHelper(graph: ScalaGraph) {

  private def callToMethodParamOut(call: Vertex): Seq[Vertex] = {
    ExpandTo.callToCalledMethod(call).flatMap(method => ExpandTo.methodToOutParameters(method))
  }

  private def filterArgumentIndex(vertexList: List[Vertex], orderSeq: Seq[Int]): List[Vertex] = {
    vertexList.filter(v => orderSeq.contains(v.value2(NodeKeys.ARGUMENT_INDEX)))
  }

  def getExpressions(method: Vertex): List[Vertex] = {
    val callNodes = method
      .out(EdgeTypes.CONTAINS)
      .hasLabel(NodeTypes.CALL)
      .toList

    callNodes
  }

  def getGenSet(method: Vertex): Set[Vertex] = {
    var genSet = Set[Vertex]()
    getExpressions(method).foreach { genExpression =>
      val methodParamOutsOrder = callToMethodParamOut(genExpression)
        .filter(_.edges(Direction.IN, EdgeTypes.PROPAGATE).hasNext)
        .map(_.value2(NodeKeys.ORDER).toInt)

      val identifierWithOrder =
        filterArgumentIndex(genExpression.vertices(Direction.OUT, EdgeTypes.AST).asScala.toList, methodParamOutsOrder)
      genSet ++= identifierWithOrder
    }
    genSet
  }

  def getGensOfExpression(expr: Vertex): Set[Vertex] = {
    var gens = Set[Vertex]()
    val methodParamOutsOrder = callToMethodParamOut(expr)
      .filter(methPO => methPO.edges(Direction.IN, EdgeTypes.PROPAGATE).hasNext)
      .map(_.value2(NodeKeys.ORDER).toInt)

    val identifierWithOrder =
      filterArgumentIndex(expr.vertices(Direction.OUT, EdgeTypes.AST).asScala.toList, methodParamOutsOrder)
    gens ++= identifierWithOrder

    gens
  }

  def getUsesOfExpression(expr: Vertex): Set[Vertex] = {
    expr
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .filter(!getGensOfExpression(expr).contains(_))
      .toSet
  }

  def getExpressionFromGen(genVertex: Vertex): Option[Vertex] = {
    getOperation(genVertex).filter(_.isInstanceOf[nodes.Call])
  }

  /** Returns a set of vertices that are killed by the passed vertex */
  def killsVertices(vertex: Vertex): Set[Vertex] = {
    val localRefIt = vertex.vertices(Direction.OUT, EdgeTypes.REF).asScala

    if (!localRefIt.hasNext) {
      Set()
    } else {
      val localRef = localRefIt.next
      localRef.vertices(Direction.IN, EdgeTypes.REF).asScala.filter(_.id != vertex.id).toSet
    }
  }

  def kills(vertex: Set[Vertex]): Set[Vertex] = {
    vertex.map(v => killsVertices(v)).fold(Set())((v1, v2) => v1.union(v2))
  }

  def expressionsToKillMap(methodVertex: Vertex): Map[Vertex, Set[Vertex]] = {
    val genExpressions = getExpressions(methodVertex)
    val genSet = getGenSet(methodVertex)

    genExpressions.map { expression =>
      val gens = getGensOfExpression(expression)
      expression -> kills(gens)
    }.toMap
  }

  def expressionsToGenMap(methodVertex: Vertex): Map[Vertex, Set[Vertex]] = {
    /*genExpressions correspond to call assignment nodes*/
    val genExpressions = getExpressions(methodVertex)
    genExpressions.map { genExpression =>
      genExpression -> getGensOfExpression(genExpression)
    }.toMap
  }

  def getOperation(vertex: Vertex): Option[Vertex] = {
    vertex match {
      case _: nodes.Identifier => getOperation(vertex.vertices(Direction.IN, EdgeTypes.AST).nextChecked)
      case _: nodes.Call       => Some(vertex)
      case _: nodes.Return     => Some(vertex)
      case _                   => None
    }
  }
}
