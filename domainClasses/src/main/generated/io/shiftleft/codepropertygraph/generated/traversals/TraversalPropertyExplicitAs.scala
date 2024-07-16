package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyExplicitAs[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasExplicitAsEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to explicitAs property */
  def explicitAs: Iterator[Boolean] =
    traversal.flatMap(_.explicitAs)

  /** Traverse to nodes where the explicitAs equals the given `value`
    */
  def explicitAs(value: Boolean): Iterator[NodeType] =
    traversal.filter { node => node.explicitAs.isDefined && node.explicitAs.get == value }

}
