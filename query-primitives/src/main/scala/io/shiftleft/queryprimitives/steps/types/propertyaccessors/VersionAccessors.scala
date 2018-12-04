package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.CpgSteps
import shapeless.HList

trait VersionAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def version(): Steps[String, String, Labels] =
    stringProperty(NodeKeys.VERSION)

  def version(value: String): CpgSteps[T, Labels] =
    stringPropertyFilter(NodeKeys.VERSION, value)

  def version(value: String*): CpgSteps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.VERSION, value: _*)

  def versionExact(value: String): CpgSteps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.VERSION, value)

  def versionExact(values: String*): CpgSteps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.VERSION, values: _*)

  def versionNot(value: String): CpgSteps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.VERSION, value)

  def versionNot(values: String*): CpgSteps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.VERSION, values: _*)

}
