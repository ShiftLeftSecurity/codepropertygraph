package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeysOdb
import io.shiftleft.codepropertygraph.generated.nodes.HasParserTypeName
import overflowdb.Node
import overflowdb.traversal.Traversal

class ParserTypeNameAccessors[A <: Node with HasParserTypeName](val traversal: Traversal[A]) extends AnyVal {

  def parserTypeName: Traversal[String] =
    traversal.map(_.parserTypeName)

  def parserTypeName(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeysOdb.PARSER_TYPE_NAME, value)

  def parserTypeName(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeysOdb.PARSER_TYPE_NAME, value: _*)

  def parserTypeNameExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeysOdb.PARSER_TYPE_NAME, value)

  def parserTypeNameExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeysOdb.PARSER_TYPE_NAME, values: _*)

  def parserTypeNameNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeysOdb.PARSER_TYPE_NAME, value)

  def parserTypeNameNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeysOdb.PARSER_TYPE_NAME, values: _*)

}
