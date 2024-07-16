package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyIsExplicit[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasIsExplicitEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to isExplicit property */
  def isExplicit: Iterator[Boolean] =
    traversal.flatMap(_.isExplicit)

  /** Traverse to nodes where the isExplicit equals the given `value`
    */
  def isExplicit(value: Boolean): Iterator[NodeType] =
    traversal.filter { node => node.isExplicit.isDefined && node.isExplicit.get == value }

}
