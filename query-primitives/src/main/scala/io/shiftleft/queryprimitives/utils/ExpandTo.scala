package io.shiftleft.queryprimitives.utils

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.queryprimitives.steps.Implicits.JavaIteratorDeco
import org.apache.tinkerpop.gremlin.structure.{Direction, Vertex}

import scala.collection.JavaConverters._

object ExpandTo {
  // The call target is for java always an object instance.
  // For languages which make use of function pointer this can also be the
  // pointer itself.
  def callTargetOption(callNode: Vertex): Option[Vertex] = {
    callNode
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .find(_.value2(NodeKeys.ARGUMENT_INDEX) == 0)
  }

  def callTarget(callNode: Vertex): Vertex = {
    callTargetOption(callNode).get
  }

  def parameterToMethod(parameterNode: Vertex): Vertex = {
    parameterNode.vertices(Direction.IN, EdgeTypes.AST).nextChecked
  }

  def formalReturnToMethod(formalReturnNode: Vertex): Vertex = {
    formalReturnNode.vertices(Direction.IN, EdgeTypes.AST).nextChecked
  }

  def expressionToMethod(expression: Vertex): Vertex = {
    expression.vertices(Direction.IN, EdgeTypes.CONTAINS).nextChecked
  }

  def localToMethod(local: Vertex): Vertex = {
    local.vertices(Direction.IN, EdgeTypes.AST).nextChecked
  }

  def hasModifier(methodNode: Vertex, modifierType: String): Boolean = {
    methodNode
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .exists(astChild =>
        astChild.label == NodeTypes.MODIFIER &&
          astChild.asInstanceOf[nodes.Modifier].modifierType == modifierType)
  }

  def astParent(expression: Vertex): Vertex = {
    expression.vertices(Direction.IN, EdgeTypes.AST).nextChecked
  }
}
