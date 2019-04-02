package io.shiftleft.passes.propagateedges

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.CpgPass
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

/**
  * Create PROPAGATE edges.
  * Currently we only do this for assignment operators to encode their data flow semantics.
  */
class PropagateEdgePass(graph: ScalaGraph) extends CpgPass(graph) {
  var dstGraph: DiffGraph = _

  override def run(): Stream[DiffGraph] = {
    dstGraph = new DiffGraph()
    val assignmentMethodOption =
      graph.V().hasLabel(NodeTypes.METHOD).has(NodeKeys.FULL_NAME -> Operators.assignment).headOption()

    assignmentMethodOption match {
      case Some(method) =>
        addAssignmentSemantic(method)
      case None =>
    }

    val combinedAssignmentOperators =
      List(
        Operators.assignmentAnd,
        Operators.assignmentArithmeticShiftRight,
        Operators.assignmentDivision,
        Operators.assignmentExponentiation,
        Operators.assignmentLogicalShifRight,
        Operators.assignmentMinus,
        Operators.assignmentModulo,
        Operators.assignmentMultiplication,
        Operators.assignmentOr,
        Operators.assignmentPlus,
        Operators.assignmentShiftLeft,
        Operators.assignmentXor
      )

    combinedAssignmentOperators.foreach { operatorFullName =>
      val operatorMethodOption =
        graph.V().hasLabel(NodeTypes.METHOD).has(NodeKeys.FULL_NAME -> operatorFullName).headOption()

      operatorMethodOption match {
        case Some(method) =>
          addCombinedAssignmentSemantic(method)
        case None =>
      }
    }

    Stream(dstGraph)
  }

  private def addAssignmentSemantic(method: Vertex): Unit = {
    val secondParameterInOption = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .find(node => node.label() == NodeTypes.METHOD_PARAMETER_IN && node.value2(NodeKeys.ORDER) == 2)
    val firstParameterOutOption = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .find(node => node.label() == NodeTypes.METHOD_PARAMETER_OUT && node.value2(NodeKeys.ORDER) == 1)
    val methodReturnOption = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .find(_.label() == NodeTypes.METHOD_RETURN)

    if (secondParameterInOption.nonEmpty) {
      if (firstParameterOutOption.nonEmpty) {
        if (methodReturnOption.nonEmpty) {
          addPropagateEdge(secondParameterInOption.get, firstParameterOutOption.get, isAlias = true)
          addPropagateEdge(secondParameterInOption.get, methodReturnOption.get, isAlias = true)
        } else {
          logger.warn(s"Could not find method return of ${method.value2(NodeKeys.FULL_NAME)}.")
        }
      } else {
        logger.warn(s"Could not find first output parameter of ${method.value2(NodeKeys.FULL_NAME)}.")
      }
    } else {
      logger.warn(s"Could not find second input parameter of ${method.value2(NodeKeys.FULL_NAME)}.")
    }
  }

  private def addCombinedAssignmentSemantic(method: Vertex): Unit = {
    val secondParameterInOption = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .find(node => node.label() == NodeTypes.METHOD_PARAMETER_IN && node.value2(NodeKeys.ORDER) == 2)
    val firstParameterOutOption = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .find(node => node.label() == NodeTypes.METHOD_PARAMETER_OUT && node.value2(NodeKeys.ORDER) == 1)
    val methodReturnOption = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .find(_.label() == NodeTypes.METHOD_RETURN)
    val firstParameterInOption = method
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .find(node => node.label() == NodeTypes.METHOD_PARAMETER_IN && node.value2(NodeKeys.ORDER) == 1)

    if (secondParameterInOption.nonEmpty) {
      if (firstParameterOutOption.nonEmpty) {
        if (methodReturnOption.nonEmpty) {
          if (firstParameterInOption.nonEmpty) {
            addPropagateEdge(firstParameterInOption.get, firstParameterOutOption.get, isAlias = false)
            addPropagateEdge(secondParameterInOption.get, firstParameterOutOption.get, isAlias = false)
            addPropagateEdge(secondParameterInOption.get, methodReturnOption.get, isAlias = false)
          } else {
            logger.warn(s"Could not find first input parameter of ${method.value2(NodeKeys.FULL_NAME)}.")
          }
        } else {
          logger.warn(s"Could not find method return of ${method.value2(NodeKeys.FULL_NAME)}.")
        }
      } else {
        logger.warn(s"Could not find first output parameter of ${method.value2(NodeKeys.FULL_NAME)}.")
      }
    } else {
      logger.warn(s"Could not find second input parameter of ${method.value2(NodeKeys.FULL_NAME)}.")
    }
  }

  private def addPropagateEdge(src: Vertex, dst: Vertex, isAlias: java.lang.Boolean): Unit = {
    dstGraph.addEdgeInOriginal(src.asInstanceOf[nodes.StoredNode],
                               dst.asInstanceOf[nodes.StoredNode],
                               EdgeTypes.PROPAGATE,
                               (EdgeKeyNames.ALIAS, isAlias) :: Nil)
  }
}
