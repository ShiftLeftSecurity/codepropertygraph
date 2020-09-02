package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.HasSignature
import overflowdb.Node
import overflowdb.traversal.Traversal

class SignatureAccessors[A <: Node with HasSignature](val traversal: Traversal[A]) extends AnyVal {

  /**
    * Traverse to signature
    * */
  def signature: Traversal[String] =
    traversal.map(_.signature)

  /**
    * Traverse to nodes where the signature matches the regular expression `value`
    * */
  def signature(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeys.SIGNATURE, value)

  /**
    * Traverse to nodes where the signature matches at least one of the regular expressions in `values`
    * */
  def signature(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeys.SIGNATURE, value: _*)

  /**
    * Traverse to nodes where signature matches `value` exactly.
    * */
  def signatureExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeys.SIGNATURE, value)

  /**
    * Traverse to nodes where signature matches one of the elements in `values` exactly.
    * */
  def signatureExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeys.SIGNATURE, values: _*)

  /**
    * Traverse to nodes where signature does not match the regular expression `value`.
    * */
  def signatureNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeys.SIGNATURE, value)

  /**
    * Traverse to nodes where signature does not match any of the regular expressions in `values`.
    * */
  def signatureNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeys.SIGNATURE, values: _*)

}
