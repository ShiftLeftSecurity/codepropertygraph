package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.Steps
import shapeless.HList

trait SignatureAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {

  /**
    * Traverse to signature
    * */
  def signature(): Steps[String, Labels] =
    stringProperty(NodeKeys.SIGNATURE)

  /**
    * Traverse to nodes where the signature matches the regular expression `value`
    * */
  def signature(value: String): Steps[T, Labels] =
    stringPropertyFilter(NodeKeys.SIGNATURE, value)

  /**
    * Traverse to nodes where the signature matches at least one of the regular expressions in `values`
    * */
  def signature(value: String*): Steps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.SIGNATURE, value: _*)

  /**
    * Traverse to nodes where signature matches `value` exactly.
    * */
  def signatureExact(value: String): Steps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.SIGNATURE, value)

  /**
    * Traverse to nodes where signature matches one of the elements in `values` exactly.
    * */
  def signatureExact(values: String*): Steps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.SIGNATURE, values: _*)

  /**
    * Traverse to nodes where signature does not match the regular expression `value`.
    * */
  def signatureNot(value: String): Steps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.SIGNATURE, value)

  /**
    * Traverse to nodes where signature does not match any of the regular expressions in `values`.
    * */
  def signatureNot(values: String*): Steps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.SIGNATURE, values: _*)

}
