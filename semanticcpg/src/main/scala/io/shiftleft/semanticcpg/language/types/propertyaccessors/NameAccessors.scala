package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.{HasName, StoredNode}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class NameAccessors[A <: StoredNode with HasName](steps: Steps[A]) extends StringPropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  /**
    * Traverse to name
    * */
  def name(): Steps[String] =
    stringProperty(NodeKeys.NAME)

  /**
    * Traverse to nodes where the name matches the regular expression `value`
    * */
  def name(value: String): NodeSteps[A] =
    stringPropertyFilter(NodeKeys.NAME, value)

  /**
    * Traverse to nodes where the name matches at least one of the regular expressions in `values`
    * */
  def name(value: String*): NodeSteps[A] =
    stringPropertyFilterMultiple(NodeKeys.NAME, value: _*)

  /**
    * Traverse to nodes where name matches `value` exactly.
    * */
  def nameExact(value: String): NodeSteps[A] =
    stringPropertyFilterExact(NodeKeys.NAME, value)

  /**
    * Traverse to nodes where name matches one of the elements in `values` exactly.
    * */
  def nameExact(values: String*): NodeSteps[A] =
    stringPropertyFilterExactMultiple(NodeKeys.NAME, values: _*)

  /**
    * Traverse to nodes where name does not match the regular expression `value`.
    * */
  def nameNot(value: String): NodeSteps[A] =
    stringPropertyFilterNot(NodeKeys.NAME, value)

  /**
    * Traverse to nodes where name does not match any of the regular expressions in `values`.
    * */
  def nameNot(values: String*): NodeSteps[A] =
    stringPropertyFilterNotMultiple(NodeKeys.NAME, values: _*)

}
