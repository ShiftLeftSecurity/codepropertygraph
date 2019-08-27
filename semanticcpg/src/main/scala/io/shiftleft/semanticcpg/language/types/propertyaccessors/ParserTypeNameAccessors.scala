package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait ParserTypeNameAccessors[T <: StoredNode] extends StringPropertyAccessors[T] {

  def parserTypeName(): Steps[String] =
    stringProperty(NodeKeys.PARSER_TYPE_NAME)

  def parserTypeName(value: String): NodeSteps[T] =
    stringPropertyFilter(NodeKeys.PARSER_TYPE_NAME, value)

  def parserTypeName(value: String*): NodeSteps[T] =
    stringPropertyFilterMultiple(NodeKeys.PARSER_TYPE_NAME, value: _*)

  def parserTypeNameExact(value: String): NodeSteps[T] =
    stringPropertyFilterExact(NodeKeys.PARSER_TYPE_NAME, value)

  def parserTypeNameExact(values: String*): NodeSteps[T] =
    stringPropertyFilterExactMultiple(NodeKeys.PARSER_TYPE_NAME, values: _*)

  def parserTypeNameNot(value: String): NodeSteps[T] =
    stringPropertyFilterNot(NodeKeys.PARSER_TYPE_NAME, value)

  def parserTypeNameNot(values: String*): NodeSteps[T] =
    stringPropertyFilterNotMultiple(NodeKeys.PARSER_TYPE_NAME, values: _*)

}
