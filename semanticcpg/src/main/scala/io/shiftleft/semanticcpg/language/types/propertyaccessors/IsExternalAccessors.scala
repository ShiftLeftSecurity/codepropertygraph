package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.HasIsExternal
import overflowdb.Node
import overflowdb.traversal.Traversal

class IsExternalAccessors[A <: Node with HasIsExternal](val traversal: Traversal[A]) extends AnyVal {

  def isExternal: Traversal[Boolean] =
    traversal.map(_.isExternal)

  def isExternal(value: Boolean): Traversal[A] =
    traversal.has(NodeKeys.IS_EXTERNAL -> value)

  def isExternalNot(value: Boolean): Traversal[A] =
    traversal.hasNot(NodeKeys.IS_EXTERNAL -> value)

}
