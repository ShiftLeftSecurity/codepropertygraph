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

  override def run(): Unit = {
    val methods = graph.V.hasLabel(NodeTypes.METHOD).l

    dfHelper = new DataFlowFrameworkHelper(graph)

    methods.foreach { method =>
      var worklist = Set[Vertex]()
      var out = Map[Vertex, Set[Vertex]]().withDefaultValue(Set[Vertex]())
      var in = Map[Vertex, Set[Vertex]]().withDefaultValue(Set[Vertex]())
      val allCfgNodes = dfHelper.allCfgNodesOfMethod(method)

      val mapStatementGens  = dfHelper.statementsToGenMap(method).withDefaultValue(Set[Vertex]())
      val mapStatementsKills = dfHelper.statementsToKillMap(method).withDefaultValue(Set[Vertex]())

      /*Initialize the OUT sets*/
      allCfgNodes.foreach { cfgNode =>
        if(mapStatementGens.contains(cfgNode)) {
          out += cfgNode -> mapStatementGens(cfgNode)
        }
      }

      worklist ++= allCfgNodes

      while(worklist.nonEmpty) {
        var currentCfgNode = worklist.head
        worklist = worklist.tail

        var inSet = Set[Vertex]()

        val cfgPredecessors = currentCfgNode.vertices(Direction.IN, EdgeTypes.CFG).asScala
        cfgPredecessors.foreach { pred =>
          inSet ++= inSet.union(out(pred))
        }

        in += currentCfgNode -> inSet

        val oldSize = out(currentCfgNode).size
        val gens = mapStatementGens(currentCfgNode)
        val kills = mapStatementsKills(currentCfgNode)
        out += currentCfgNode -> gens.union(inSet.diff(kills))
        val newSize = out(currentCfgNode).size

        if (oldSize != newSize)
          worklist ++= currentCfgNode.vertices(Direction.OUT, EdgeTypes.CFG).asScala.toList
      }

      addReachingDefEdge(dstGraph, out, in)
    }
  }

  /** Pruned DDG, i.e., two statements or call assignment vertices are adjacent if a
    * reaching definition edge reaches a statement where the definition is used.
    * The final representation makes it straightforward to build def-use/use-def chains */
  private def addReachingDefEdge(dstGraph: DiffGraph,
                                 outSet: Map[Vertex, Set[Vertex]],
                                 inSet:  Map[Vertex, Set[Vertex]]) : Unit = {

    def addEdge(v0: Vertex, v1: Vertex): Unit = {
      dstGraph.addEdgeInOriginal(v0, v1, EdgeTypes.REACHING_DEF)
    }

    outSet.foreach { case (node, outDefs) =>
      if (node.label == NodeTypes.CALL) {
        for (elem <- outDefs) {

          val usesInStatement = dfHelper.getUsesOfStatement(node)
          var localRefsUses = usesInStatement.map(dfHelper.getRef(_)).filter(_ != None)
          val localRefGen = dfHelper.getRef(elem)

          dfHelper.getStatementFromGen(elem).foreach { statementOfElement =>
            if (statementOfElement != node && localRefsUses.contains(localRefGen)) {
              addEdge(statementOfElement, node)
            }
          }

          if (dfHelper.isOperationAndAssignment(node)) {
            localRefsUses = Set(localRefGen)
            inSet(node).filter(inElement => localRefsUses.contains(dfHelper.getRef(inElement)))
              .foreach { usedInElement =>
                val statementOfInElement = dfHelper.getStatementFromGen(usedInElement)
                  .foreach(statementOfInElement => addEdge(statementOfInElement, node))
              }
          }
        }
      }
    }
  }


  def toDot(graph: ScalaGraph): String = {
    val buf = new StringBuffer()

    buf.append("digraph g {\n node[shape=plaintext];\n")

    graph.E.hasLabel(EdgeTypes.REACHING_DEF).l.foreach { e =>
      val inV  = vertexToStr(e.inVertex).replace("\"", "\'")
      val outV = vertexToStr(e.outVertex).replace("\"", "\'")
      buf.append(s""" "$outV" -> "$inV";\n """)
    }
    buf.append{"}"}
    buf.toString
  }

  /** For debug purposes */
  def vertexToStr(vertex: Vertex): String = {
    try {
      val methodVertex = ExpandTo.expressionToMethod(vertex)
      val fileName = ExpandTo.methodToFile(methodVertex) match {
        case Some(objFile) => objFile.value2(NodeKeys.NAME)
        case None => "NA"
      }

      s"${Paths.get(fileName).getFileName.toString}: ${vertex.value2(NodeKeys.LINE_NUMBER).toString} ${vertex.value2(NodeKeys.CODE)}"
    }
    catch{ case _: Exception => ""}
  }
}


/** Common functionalities needed for data flow frameworks */
class DataFlowFrameworkHelper(graph: ScalaGraph) {

  def allCfgNodesOfMethod(method: Vertex): Set[Vertex] = {
    val worklist = ListBuffer[Vertex]()
    worklist += method

    var cfgNodes = Set[Vertex]()

    while (worklist.nonEmpty) {
      val cfgNode = worklist.remove(0)
      cfgNodes += cfgNode

      val newCfgSuccessors = cfgNode
        .vertices(Direction.OUT, EdgeTypes.CFG)
        .asScala
        .filter(cfgSuccessor => !cfgNodes.contains(cfgSuccessor))
        .toList

      worklist ++= newCfgSuccessors
    }
    cfgNodes
  }

  def isOperationAndAssignment(vertex: Vertex): Boolean = {
    if (vertex.label != NodeTypes.CALL) {
      return false
    }

    val name = vertex.value2(NodeKeys.NAME)
    name match {
      case Operators.assignmentAnd => true
      case Operators.assignmentArithmeticShiftRight => true
      case Operators.assignmentDivision => true
      case Operators.assignmentExponentiation => true
      case Operators.assignmentLogicalShifRight => true
      case Operators.assignmentMinus => true
      case Operators.assignmentModulo => true
      case Operators.assignmentMultiplication => true
      case Operators.assignmentOr => true
      case Operators.assignmentPlus => true
      case Operators.assignmentShiftLeft => true
      case Operators.assignmentXor => true
      case _  => false
    }
  }

  def isAssignment(vertex: Vertex): Boolean = {
    isOperationAndAssignment(vertex) || (vertex.value2(NodeKeys.NAME) == Operators.assignment)
  }

  private def callToMethodParamOut(call: Vertex): Seq[Vertex] = {
    call
      .vertices(Direction.OUT, EdgeTypes.CALL).nextChecked
      .vertices(Direction.OUT, EdgeTypes.REF).nextChecked
      .vertices(Direction.OUT, EdgeTypes.AST).asScala
      .filter(_.label == NodeTypes.METHOD_PARAMETER_OUT)
      .toSeq
  }

  private implicit class Wrapper[T](iterator: Iterator[T]) {
    def headOption = {
      if (iterator.hasNext) {
        Some(iterator.next)
      } else {
        None
      }
    }
  }

  def getRef(vertex: Vertex): Option[Vertex] = {
    vertex.vertices(Direction.OUT, EdgeTypes.REF).asScala.headOption
  }

  private def filterOrder(vertexList: List[Vertex], order: Int): List[Vertex] = {
    vertexList.filter(_.value2(NodeKeys.ORDER) == order)
  }

  private def filterArgumentIndex(vertexList: List[Vertex], orderSeq: Seq[Int]): List[Vertex] = {
    vertexList.filter(v => orderSeq.contains(v.value2(NodeKeys.ARGUMENT_INDEX)))
  }

  def getStatements(method: Vertex): List[Vertex] = {
    val callNodes = method
      .out(EdgeTypes.CONTAINS)
      .hasLabel(NodeTypes.CALL)
      .toList

    callNodes
  }

  def getGenSet(method: Vertex): Set[Vertex] = {
    var genSet = Set[Vertex]()
    getStatements(method).foreach{ genStatement =>
      val methodParamOutsOrder = callToMethodParamOut(genStatement)
        .filter(_.edges(Direction.IN, EdgeTypes.PROPAGATE).hasNext)
        .map(_.value2(NodeKeys.ORDER).toInt)

      val identifierWithOrder = filterArgumentIndex(genStatement.vertices(Direction.OUT, EdgeTypes.AST).asScala.toList, methodParamOutsOrder)
      genSet ++= identifierWithOrder
    }
    genSet
  }

  def getGensOfStatement(stmt: Vertex): Set[Vertex] = {
    var gens = Set[Vertex]()
    val methodParamOutsOrder = callToMethodParamOut(stmt)
      .filter(methPO => methPO.edges(Direction.IN, EdgeTypes.PROPAGATE).hasNext)
      .map(_.value2(NodeKeys.ORDER).toInt)

    val identifierWithOrder = filterArgumentIndex(stmt.vertices(Direction.OUT, EdgeTypes.AST).asScala.toList, methodParamOutsOrder)
    gens ++= identifierWithOrder

    gens
  }

  def getUsesOfStatement(stmt: Vertex): Set[Vertex] = {
    stmt.vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .filter( !getGensOfStatement(stmt).contains(_) ).toSet
  }

  def getStatementFromGen(genVertex: Vertex): Option[Vertex] = {
    getStatement(genVertex).filter(_.label == NodeTypes.CALL)
  }

  /** Returns a set of vertices that are killed by the passed vertex */
  def killsVertices(vertex: Vertex): Set[Vertex] = {
    val localRefIt = vertex.vertices(Direction.OUT, EdgeTypes.REF).asScala

    if(!localRefIt.hasNext) {
      Set()
    } else {
      val localRef = localRefIt.next
      localRef.vertices(Direction.IN, EdgeTypes.REF).asScala.filter(_.id != vertex.id).toSet
    }
  }

  def kills(vertex: Set[Vertex]): Set[Vertex] = {
    vertex.map(v => killsVertices(v)).fold(Set())( (v1,v2) => v1.union(v2) )
  }

  def statementsToKillMap(methodVertex: Vertex) : Map[Vertex, Set[Vertex]] = {
    val genStatements = getStatements(methodVertex)
    val genSet = getGenSet(methodVertex)

    genStatements.map { statement =>
      val gens = getGensOfStatement(statement)
      statement -> kills(gens)
    }.toMap
  }

  def statementsToGenMap(methodVertex: Vertex): Map[Vertex, Set[Vertex]] = {
    /*gen statements correspond to call assignment nodes*/
    val genStatements = getStatements(methodVertex)
    genStatements.map{genStatement =>
      genStatement -> getGensOfStatement(genStatement)}.toMap
  }

  def getStatement(vertex: Vertex): Option[Vertex] = {
    vertex match {
      case v: nodes.Identifier => getStatement(v.vertices(Direction.IN, EdgeTypes.AST).next)
      case v: nodes.Call => Some(v)
      case v: nodes.Return => Some(v)
      case v: nodes.Unknown => Some(v)
      case _ => None
    }
  }
}