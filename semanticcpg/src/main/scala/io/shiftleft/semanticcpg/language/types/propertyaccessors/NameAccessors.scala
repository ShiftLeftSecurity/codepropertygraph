package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeysOdb
import io.shiftleft.codepropertygraph.generated.nodes.HasName
import overflowdb.Node
import overflowdb.traversal.Traversal

class NameAccessors[A <: Node with HasName](val traversal: Traversal[A]) extends AnyVal {

  /**
    * Traverse to name
    * */
  def name: Traversal[String] =
    traversal.map(_.name)

  /**
    * Traverse to nodes where the name matches the regular expression `value`
    * */
  def name(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeysOdb.NAME, value)

  /**
    * Traverse to nodes where the name matches at least one of the regular expressions in `values`
    * */
  def name(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeysOdb.NAME, value: _*)

  /**
    * Traverse to nodes where name matches `value` exactly.
    * */
  def nameExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeysOdb.NAME, value)

  /**
    * Traverse to nodes where name matches one of the elements in `values` exactly.
    * */
  def nameExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeysOdb.NAME, values: _*)

  /**
    * Traverse to nodes where name does not match the regular expression `value`.
    * */
  def nameNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeysOdb.NAME, value)

  /**
    * Traverse to nodes where name does not match any of the regular expressions in `values`.
    * */
  def nameNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeysOdb.NAME, values: _*)

}
