package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.{HasParserTypeName, StoredNode}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class ParserTypeNameAccessors[A <: StoredNode with HasParserTypeName](steps: Steps[A]) extends StringPropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  def parserTypeName(): Steps[String] =
    stringProperty(NodeKeys.PARSER_TYPE_NAME)

  def parserTypeName(value: String): NodeSteps[A] =
    stringPropertyFilter(NodeKeys.PARSER_TYPE_NAME, value)

  def parserTypeName(value: String*): NodeSteps[A] =
    stringPropertyFilterMultiple(NodeKeys.PARSER_TYPE_NAME, value: _*)

  def parserTypeNameExact(value: String): NodeSteps[A] =
    stringPropertyFilterExact(NodeKeys.PARSER_TYPE_NAME, value)

  def parserTypeNameExact(values: String*): NodeSteps[A] =
    stringPropertyFilterExactMultiple(NodeKeys.PARSER_TYPE_NAME, values: _*)

  def parserTypeNameNot(value: String): NodeSteps[A] =
    stringPropertyFilterNot(NodeKeys.PARSER_TYPE_NAME, value)

  def parserTypeNameNot(values: String*): NodeSteps[A] =
    stringPropertyFilterNotMultiple(NodeKeys.PARSER_TYPE_NAME, values: _*)

}
