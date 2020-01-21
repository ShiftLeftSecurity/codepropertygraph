package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.{HasDispatchType, StoredNode}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class DispatchTypeAccessors[A <: StoredNode with HasDispatchType](steps: Steps[A]) extends StringPropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  /**
    * Traverse to dispatchType
    * */
  def dispatchType(): Steps[String] =
    stringProperty(NodeKeys.DISPATCH_TYPE)

  /**
    * Traverse to nodes where the dispatchType matches the regular expression `value`
    * */
  def dispatchType(value: String): NodeSteps[A] =
    stringPropertyFilter(NodeKeys.DISPATCH_TYPE, value)

  /**
    * Traverse to nodes where the dispatchType matches at least one of the regular expressions in `values`
    * */
  def dispatchType(value: String*): NodeSteps[A] =
    stringPropertyFilterMultiple(NodeKeys.DISPATCH_TYPE, value: _*)

  /**
    * Traverse to nodes where dispatchType matches `value` exactly.
    * */
  def dispatchTypeExact(value: String): NodeSteps[A] =
    stringPropertyFilterExact(NodeKeys.DISPATCH_TYPE, value)

  /**
    * Traverse to nodes where dispatchType matches one of the elements in `values` exactly.
    * */
  def dispatchTypeExact(values: String*): NodeSteps[A] =
    stringPropertyFilterExactMultiple(NodeKeys.DISPATCH_TYPE, values: _*)

  /**
    * Traverse to nodes where dispatchType does not match the regular expression `value`.
    * */
  def dispatchTypeNot(value: String): NodeSteps[A] =
    stringPropertyFilterNot(NodeKeys.DISPATCH_TYPE, value)

  /**
    * Traverse to nodes where dispatchType does not match any of the regular expressions in `values`.
    * */
  def dispatchTypeNot(values: String*): NodeSteps[A] =
    stringPropertyFilterNotMultiple(NodeKeys.DISPATCH_TYPE, values: _*)

}
