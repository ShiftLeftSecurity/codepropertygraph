package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyOverlays[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasOverlaysEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to overlays property */
  def overlays: Iterator[String] =
    traversal.flatMap(_.overlays)

}
