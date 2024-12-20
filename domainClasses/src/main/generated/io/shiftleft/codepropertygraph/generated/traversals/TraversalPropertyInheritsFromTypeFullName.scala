package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyInheritsFromTypeFullName[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasInheritsFromTypeFullNameEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to inheritsFromTypeFullName property */
  def inheritsFromTypeFullName: Iterator[String] =
    traversal.flatMap(_.inheritsFromTypeFullName)

}
