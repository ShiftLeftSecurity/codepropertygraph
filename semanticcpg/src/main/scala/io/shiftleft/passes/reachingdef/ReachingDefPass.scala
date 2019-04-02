package io.shiftleft.passes.reachingdef
import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.CpgPass
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.types.structure.File
import io.shiftleft.queryprimitives.utils.ExpandTo

import scala.collection.mutable.ListBuffer
import org.apache.tinkerpop.gremlin.structure.Direction
import java.nio.file.Paths

import scala.collection.JavaConverters._

class ReachingDefPass(graph: ScalaGraph) extends CpgPass(graph) {
  var dfHelper: DataFlowFrameworkHelper = _

  override def run(): Stream[DiffGraph] = {
    val dstGraph = new DiffGraph()
    val methods = graph.V.hasLabel(NodeTypes.METHOD).l

    dfHelper = new DataFlowFrameworkHelper(graph)

    methods.foreach { method =>
      var worklist = Set[Vertex]()
      var out = Map[Vertex, Set[Vertex]]().withDefaultValue(Set[Vertex]())
      var in = Map[Vertex, Set[Vertex]]().withDefaultValue(Set[Vertex]())
      val allCfgNodes = ExpandTo.allCfgNodesOfMethod(method).toList

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

    Stream(dstGraph)
  }

  /** Pruned DDG, i.e., two call assignment vertices are adjacent if a
    * reaching definition edge reaches a vertex where the definition is used.
    * The final representation makes it straightforward to build def-use/use-def chains */
  private def addReachingDefEdge(dstGraph: DiffGraph,
                                 method: Vertex,
                                 outSet: Map[Vertex, Set[Vertex]],
                                 inSet: Map[Vertex, Set[Vertex]]): Unit = {

    def addEdge(v0: Vertex, v1: Vertex): Unit = {
      dstGraph.addEdgeInOriginal(v0.asInstanceOf[nodes.StoredNode], v1.asInstanceOf[nodes.StoredNode], EdgeTypes.REACHING_DEF)
    }

    method.vertices(Direction.OUT, EdgeTypes.AST).asScala.filter(_.isInstanceOf[nodes.MethodParameterIn]).foreach { mPI =>
      mPI.vertices(Direction.IN, EdgeTypes.REF).asScala.foreach { refInIdentifier =>
        dfHelper.getOperation(refInIdentifier).foreach(operationNode => addEdge(method, operationNode))
      }
    }

    val methodReturn = ExpandTo.methodToFormalReturn(method)

    ExpandTo.formalReturnToReturn(methodReturn)
      .foreach(returnVertex => addEdge(returnVertex, methodReturn))

    /* Use this for member access nodes or indirections. These nodes are stopovers as we do not want them to appear
     * in flows (no incident edge to them). However, we want definitions that reach these expression
     * and which are used here to pass through them to the next call expression */
    var stopover = Map[Vertex, Vertex]()

    outSet.foreach { case (node, outDefs) =>
      if (node.isInstanceOf[nodes.Call] ) {
        val usesInExpression = dfHelper.getUsesOfExpression(node)
        var localRefsUses = usesInExpression.map(ExpandTo.reference(_)).filter(_ != None)
        val nodeIsIndirection = indirectAccess(node)

        /* if use is not an identifier, add edge, as we are going to visit the use separately */
        usesInExpression.foreach { use =>
          if (!use.isInstanceOf[nodes.Identifier] && !use.isInstanceOf[nodes.Literal]) {
            if(indirectAccess(use)) {
              stopover += use -> node
            } else {
              addEdge(use, node)
            }
          }
        }

        val nodeIsOperandAssignment = isOperationAndAssignment(node)
        if (nodeIsOperandAssignment) {
          val localRefGens = dfHelper.getGensOfExpression(node).map(ExpandTo.reference(_))
          inSet(node).filter(inElement => localRefGens.contains(ExpandTo.reference(inElement)))
            .foreach { filteredInElement =>
              val expr = dfHelper.getExpressionFromGen(filteredInElement).foreach(addEdge(_, node))
            }
        }

        for (elem <- outDefs) {
          val localRefGen = ExpandTo.reference(elem)

          dfHelper.getExpressionFromGen(elem).foreach { expressionOfElement =>
            if (expressionOfElement != node && localRefsUses.contains(localRefGen)) {
              if(nodeIsIndirection) {
                if(stopover.contains(node)) {
                  val passThrough = stopover(node)
                  addEdge(expressionOfElement, passThrough)
                }
              } else {
                addEdge(expressionOfElement, node)
              }
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
      case Operators.assignmentLogicalShifRight     => true
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

  private def indirectAccess(vertex: Vertex): Boolean = {
    if (!vertex.isInstanceOf[nodes.Call]) {
      return false
    }

    val callName = vertex.value2(NodeKeys.NAME)
    callName match {
      case Operators.memberAccess                 => true
      case Operators.indirectComputedMemberAccess => true
      case Operators.indirectMemberAccess         => true
      case Operators.computedMemberAccess         => true
      case Operators.indirection                  => true
      case _                                      => false
    }
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
      case _: nodes.Identifier => getOperation(vertex.vertices(Direction.IN, EdgeTypes.AST).next)
      case _: nodes.Call => Some(vertex)
      case _: nodes.Return => Some(vertex)
      case _                    => None
    }
  }
}
