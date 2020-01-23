package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala._
import io.shiftleft.semanticcpg.language._
import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes, nodes}
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode

trait ModifierAccessors[T <: StoredNode] {
  val raw: GremlinScala[T]

  def hasModifier(modifier: String): Steps[T] =
    new Steps[T](raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> modifier)))

  /**
    * Traverse to modifiers, e.g., "static", "public".
    * */
  def modifier: NodeSteps[nodes.Modifier] =
    new NodeSteps(
      raw.out
        .hasLabel(NodeTypes.MODIFIER)
        .cast[nodes.Modifier]
    )
}
