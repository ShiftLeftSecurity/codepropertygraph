package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.{HasLineNumberEnd, StoredNode}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class LineNumberEndAccessors[A <: StoredNode with HasLineNumberEnd](steps: Steps[A]) extends PropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  def lineNumberEnd(): Steps[Integer] =
    property(NodeKeys.LINE_NUMBER_END)

  def lineNumberEnd(value: Integer): NodeSteps[A] =
    propertyFilter(NodeKeys.LINE_NUMBER_END, value)

  def lineNumberEnd(value: Integer*): NodeSteps[A] =
    propertyFilterMultiple(NodeKeys.LINE_NUMBER_END, value: _*)

  def lineNumberEndNot(value: Integer): NodeSteps[A] =
    propertyFilterNot(NodeKeys.LINE_NUMBER_END, value)

  def lineNumberEndNot(values: Integer*): NodeSteps[A] =
    propertyFilterNotMultiple(NodeKeys.LINE_NUMBER_END, values: _*)

}
