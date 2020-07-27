package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeysOdb
import io.shiftleft.codepropertygraph.generated.nodes.HasCode
import overflowdb.Node
import overflowdb.traversal.Traversal

class CodeAccessors[A <: Node with HasCode](val traversal: Traversal[A]) extends AnyVal {

  def code: Traversal[String] =
    traversal.map(_.code)

  def code(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeysOdb.CODE, value)

  def code(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeysOdb.CODE, value: _*)

  def codeExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeysOdb.CODE, value)

  def codeExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeysOdb.CODE, values: _*)

  def codeNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeysOdb.CODE, value)

  def codeNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeysOdb.CODE, values: _*)

}
