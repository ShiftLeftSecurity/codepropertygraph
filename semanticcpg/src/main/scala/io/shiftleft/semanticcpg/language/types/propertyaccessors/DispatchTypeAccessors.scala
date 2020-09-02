package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.HasDispatchType
import overflowdb.Node
import overflowdb.traversal.Traversal

class DispatchTypeAccessors[A <: Node with HasDispatchType](val traversal: Traversal[A]) extends AnyVal {

  /**
    * Traverse to dispatchType
    * */
  def dispatchType: Traversal[String] =
    traversal.map(_.dispatchType)

  /**
    * Traverse to nodes where the dispatchType matches the regular expression `value`
    * */
  def dispatchType(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeys.DISPATCH_TYPE, value)

  /**
    * Traverse to nodes where the dispatchType matches at least one of the regular expressions in `values`
    * */
  def dispatchType(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeys.DISPATCH_TYPE, value: _*)

  /**
    * Traverse to nodes where dispatchType matches `value` exactly.
    * */
  def dispatchTypeExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeys.DISPATCH_TYPE, value)

  /**
    * Traverse to nodes where dispatchType matches one of the elements in `values` exactly.
    * */
  def dispatchTypeExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeys.DISPATCH_TYPE, values: _*)

  /**
    * Traverse to nodes where dispatchType does not match the regular expression `value`.
    * */
  def dispatchTypeNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeys.DISPATCH_TYPE, value)

  /**
    * Traverse to nodes where dispatchType does not match any of the regular expressions in `values`.
    * */
  def dispatchTypeNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeys.DISPATCH_TYPE, values: _*)

}
