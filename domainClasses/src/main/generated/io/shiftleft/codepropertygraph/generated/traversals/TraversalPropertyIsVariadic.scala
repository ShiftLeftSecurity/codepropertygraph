package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyIsVariadic[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasIsVariadicEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to isVariadic property */
  def isVariadic: Iterator[Boolean] =
    traversal.map(_.isVariadic)

  /** Traverse to nodes where the isVariadic equals the given `value`
    */
  def isVariadic(value: Boolean): Iterator[NodeType] =
    traversal.filter { _.isVariadic == value }

}
