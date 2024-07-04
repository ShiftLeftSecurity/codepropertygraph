package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyDynamicTypeHintFullName[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasDynamicTypeHintFullNameEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to dynamicTypeHintFullName property */
  def dynamicTypeHintFullName: Iterator[String] =
    traversal.flatMap(_.dynamicTypeHintFullName)

}
