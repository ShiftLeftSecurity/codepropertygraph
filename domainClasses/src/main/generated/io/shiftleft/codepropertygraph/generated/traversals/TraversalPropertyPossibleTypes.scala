package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyPossibleTypes[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasPossibleTypesEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to possibleTypes property */
  def possibleTypes: Iterator[String] =
    traversal.flatMap(_.possibleTypes)

}
