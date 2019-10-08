package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala._
import io.shiftleft.semanticcpg.language._
import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes, nodes}
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Modifier

trait ModifierAccessors[T <: StoredNode] {
  val raw: GremlinScala[T]

  def hasModifier(modifier: String): Boolean =
    raw
      .filter(
        _.out
          .hasLabel(NodeTypes.MODIFIER)
          .has(NodeKeys.MODIFIER_TYPE -> modifier)
      )
      .exists()

  /**
    * Traverse to modifiers, e.g., "static", "public".
    * */
  def modifier: Modifier =
    new Modifier(
      raw.out
        .hasLabel(NodeTypes.MODIFIER)
        .cast[nodes.Modifier]
    )
}
