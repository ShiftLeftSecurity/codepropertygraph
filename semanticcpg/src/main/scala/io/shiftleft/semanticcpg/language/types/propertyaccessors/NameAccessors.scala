package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}
import shapeless.HList

trait NameAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {

  /**
    * Traverse to name
    * */
  def name(): Steps[String, Labels] =
    stringProperty(NodeKeys.NAME)

  /**
    * Traverse to nodes where the name matches the regular expression `value`
    * */
  def name(value: String): NodeSteps[T, Labels] =
    stringPropertyFilter(NodeKeys.NAME, value)

  /**
    * Traverse to nodes where the name matches at least one of the regular expressions in `values`
    * */
  def name(value: String*): NodeSteps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.NAME, value: _*)

  /**
    * Traverse to nodes where name matches `value` exactly.
    * */
  def nameExact(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.NAME, value)

  /**
    * Traverse to nodes where name matches one of the elements in `values` exactly.
    * */
  def nameExact(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.NAME, values: _*)

  /**
    * Traverse to nodes where name does not match the regular expression `value`.
    * */
  def nameNot(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.NAME, value)

  /**
    * Traverse to nodes where name does not match any of the regular expressions in `values`.
    * */
  def nameNot(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.NAME, values: _*)

}
