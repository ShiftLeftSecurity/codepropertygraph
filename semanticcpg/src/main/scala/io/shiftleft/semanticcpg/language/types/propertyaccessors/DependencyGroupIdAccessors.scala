package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.{HasDependencyGroupId, StoredNode}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class DependencyGroupIdAccessors[A <: StoredNode with HasDependencyGroupId](steps: Steps[A]) extends StringPropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  def dependencyGroupId(): Steps[String] =
    stringProperty(NodeKeys.DEPENDENCY_GROUP_ID)

  def dependencyGroupId(value: String): NodeSteps[A] =
    stringPropertyFilter(NodeKeys.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupId(value: String*): NodeSteps[A] =
    stringPropertyFilterMultiple(NodeKeys.DEPENDENCY_GROUP_ID, value: _*)

  def dependencyGroupIdExact(value: String): NodeSteps[A] =
    stringPropertyFilterExact(NodeKeys.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupIdExact(values: String*): NodeSteps[A] =
    stringPropertyFilterExactMultiple(NodeKeys.DEPENDENCY_GROUP_ID, values: _*)

  def dependencyGroupIdNot(value: String): NodeSteps[A] =
    stringPropertyFilterNot(NodeKeys.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupIdNot(values: String*): NodeSteps[A] =
    stringPropertyFilterNotMultiple(NodeKeys.DEPENDENCY_GROUP_ID, values: _*)
}
