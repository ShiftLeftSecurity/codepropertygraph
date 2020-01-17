package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.{HasLineNumber, StoredNode}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class LineNumberAccessors[A <: StoredNode with HasLineNumber](steps: Steps[A]) extends PropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  def lineNumber(): Steps[Integer] =
    property(NodeKeys.LINE_NUMBER)

  def lineNumber(value: Integer): NodeSteps[A] =
    propertyFilter(NodeKeys.LINE_NUMBER, value)

  def lineNumber(value: Integer*): NodeSteps[A] =
    propertyFilterMultiple(NodeKeys.LINE_NUMBER, value: _*)

  def lineNumberNot(value: Integer): NodeSteps[A] =
    propertyFilterNot(NodeKeys.LINE_NUMBER, value)

  def lineNumberNot(values: Integer*): NodeSteps[A] =
    propertyFilterNotMultiple(NodeKeys.LINE_NUMBER, values: _*)

}
