package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyEvidenceDescription[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasEvidenceDescriptionEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to evidenceDescription property */
  def evidenceDescription: Iterator[String] =
    traversal.flatMap(_.evidenceDescription)

}
