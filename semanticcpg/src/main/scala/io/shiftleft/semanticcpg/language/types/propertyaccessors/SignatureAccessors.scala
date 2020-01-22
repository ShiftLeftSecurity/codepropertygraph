package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.{HasSignature, StoredNode}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class SignatureAccessors[A <: StoredNode with HasSignature](steps: Steps[A]) extends StringPropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  /**
    * Traverse to signature
    * */
  def signature(): Steps[String] =
    stringProperty(NodeKeys.SIGNATURE)

  /**
    * Traverse to nodes where the signature matches the regular expression `value`
    * */
  def signature(value: String): NodeSteps[A] =
    stringPropertyFilter(NodeKeys.SIGNATURE, value)

  /**
    * Traverse to nodes where the signature matches at least one of the regular expressions in `values`
    * */
  def signature(value: String*): NodeSteps[A] =
    stringPropertyFilterMultiple(NodeKeys.SIGNATURE, value: _*)

  /**
    * Traverse to nodes where signature matches `value` exactly.
    * */
  def signatureExact(value: String): NodeSteps[A] =
    stringPropertyFilterExact(NodeKeys.SIGNATURE, value)

  /**
    * Traverse to nodes where signature matches one of the elements in `values` exactly.
    * */
  def signatureExact(values: String*): NodeSteps[A] =
    stringPropertyFilterExactMultiple(NodeKeys.SIGNATURE, values: _*)

  /**
    * Traverse to nodes where signature does not match the regular expression `value`.
    * */
  def signatureNot(value: String): NodeSteps[A] =
    stringPropertyFilterNot(NodeKeys.SIGNATURE, value)

  /**
    * Traverse to nodes where signature does not match any of the regular expressions in `values`.
    * */
  def signatureNot(values: String*): NodeSteps[A] =
    stringPropertyFilterNotMultiple(NodeKeys.SIGNATURE, values: _*)

}
