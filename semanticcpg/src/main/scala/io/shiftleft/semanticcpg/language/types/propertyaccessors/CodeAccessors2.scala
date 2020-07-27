package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeysOdb
import io.shiftleft.codepropertygraph.generated.nodes.HasCode
import overflowdb.Node
import overflowdb.traversal.Traversal

class CodeAccessors2[A <: Node with HasCode](val traversal: Traversal[A]) extends AnyVal {

  def code: Traversal[String] =
    traversal.map(_.code)

  def code(value: String): Traversal[A] =
    StringPropertyAccessors2.filter(traversal, NodeKeysOdb.CODE, value)

  def code(value: String*): Traversal[A] =
    StringPropertyAccessors2.filterMultiple(traversal, NodeKeysOdb.CODE, value: _*)

  def codeExact(value: String): Traversal[A] =
    StringPropertyAccessors2.filterExact(traversal, NodeKeysOdb.CODE, value)

  def codeExact(values: String*): Traversal[A] =
    StringPropertyAccessors2.filterExactMultiple(traversal, NodeKeysOdb.CODE, values: _*)

  def codeNot(value: String): Traversal[A] =
    StringPropertyAccessors2.filterNot(traversal, NodeKeysOdb.CODE, value)

  def codeNot(values: String*): Traversal[A] =
    StringPropertyAccessors2.filterNotMultiple(traversal, NodeKeysOdb.CODE, values: _*)

}
