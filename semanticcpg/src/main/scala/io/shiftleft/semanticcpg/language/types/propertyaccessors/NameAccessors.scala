package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait NameAccessors[T <: StoredNode] extends StringPropertyAccessors[T] {

  /**
    * Traverse to name
    * */
  def name(): Steps[String] =
    stringProperty(NodeKeys.NAME)

  /**
    * Traverse to nodes where the name matches the regular expression `value`
    * */
  def name(value: String): NodeSteps[T] =
    stringPropertyFilter(NodeKeys.NAME, value)

  /**
    * Traverse to nodes where the name matches at least one of the regular expressions in `values`
    * */
  def name(value: String*): NodeSteps[T] =
    stringPropertyFilterMultiple(NodeKeys.NAME, value: _*)

  /**
    * Traverse to nodes where name matches `value` exactly.
    * */
  def nameExact(value: String): NodeSteps[T] =
    stringPropertyFilterExact(NodeKeys.NAME, value)

  /**
    * Traverse to nodes where name matches one of the elements in `values` exactly.
    * */
  def nameExact(values: String*): NodeSteps[T] =
    stringPropertyFilterExactMultiple(NodeKeys.NAME, values: _*)

  /**
    * Traverse to nodes where name does not match the regular expression `value`.
    * */
  def nameNot(value: String): NodeSteps[T] =
    stringPropertyFilterNot(NodeKeys.NAME, value)

  /**
    * Traverse to nodes where name does not match any of the regular expressions in `values`.
    * */
  def nameNot(values: String*): NodeSteps[T] =
    stringPropertyFilterNotMultiple(NodeKeys.NAME, values: _*)

}
