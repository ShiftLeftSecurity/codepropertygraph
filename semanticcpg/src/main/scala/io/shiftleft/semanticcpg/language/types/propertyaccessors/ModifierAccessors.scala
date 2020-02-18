package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.semanticcpg.language._
import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes, nodes}
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode

class ModifierAccessors[NodeType <: StoredNode](val wrapped: NodeSteps[NodeType]) extends AnyVal {

  def hasModifier(modifier: String): NodeSteps[NodeType] =
    new NodeSteps(wrapped.raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> modifier)))

  /**
    * Traverse to modifiers, e.g., "static", "public".
    * */
  def modifier: NodeSteps[nodes.Modifier] =
    new NodeSteps(
      wrapped.raw.out
        .hasLabel(NodeTypes.MODIFIER)
        .cast[nodes.Modifier]
    )
}