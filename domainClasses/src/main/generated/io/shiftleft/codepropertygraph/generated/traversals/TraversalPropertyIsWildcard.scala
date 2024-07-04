package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyIsWildcard[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasIsWildcardEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to isWildcard property */
  def isWildcard: Iterator[Boolean] =
    traversal.flatMap(_.isWildcard)

  /** Traverse to nodes where the isWildcard equals the given `value`
    */
  def isWildcard(value: Boolean): Iterator[NodeType] =
    traversal.filter { node => node.isWildcard.isDefined && node.isWildcard.get == value }

}
