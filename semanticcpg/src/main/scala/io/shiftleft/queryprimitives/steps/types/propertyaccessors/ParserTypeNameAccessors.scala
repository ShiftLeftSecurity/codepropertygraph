package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import shapeless.HList

trait ParserTypeNameAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {

  def parserTypeName(): Steps[String, Labels] =
    stringProperty(NodeKeys.PARSER_TYPE_NAME)

  def parserTypeName(value: String): NodeSteps[T, Labels] =
    stringPropertyFilter(NodeKeys.PARSER_TYPE_NAME, value)

  def parserTypeName(value: String*): NodeSteps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.PARSER_TYPE_NAME, value: _*)

  def parserTypeNameExact(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.PARSER_TYPE_NAME, value)

  def parserTypeNameExact(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.PARSER_TYPE_NAME, values: _*)

  def parserTypeNameNot(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.PARSER_TYPE_NAME, value)

  def parserTypeNameNot(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.PARSER_TYPE_NAME, values: _*)

}
