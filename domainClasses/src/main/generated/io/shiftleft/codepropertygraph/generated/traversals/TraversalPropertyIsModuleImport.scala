package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyIsModuleImport[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasIsModuleImportEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to isModuleImport property */
  def isModuleImport: Iterator[Boolean] =
    traversal.flatMap(_.isModuleImport)

  /** Traverse to nodes where the isModuleImport equals the given `value`
    */
  def isModuleImport(value: Boolean): Iterator[NodeType] =
    traversal.filter { node => node.isModuleImport.isDefined && node.isModuleImport.get == value }

}
