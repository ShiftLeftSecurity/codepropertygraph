package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.HasCode
import overflowdb.Node
import overflowdb.traversal.Traversal

class CodeAccessors[A <: Node with HasCode](val traversal: Traversal[A]) extends AnyVal {

  def code: Traversal[String] =
    traversal.map(_.code)

  def code(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeys.CODE, value)

  def code(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeys.CODE, value: _*)

  def codeExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeys.CODE, value)

  def codeExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeys.CODE, values: _*)

  def codeNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeys.CODE, value)

  def codeNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeys.CODE, values: _*)

}
