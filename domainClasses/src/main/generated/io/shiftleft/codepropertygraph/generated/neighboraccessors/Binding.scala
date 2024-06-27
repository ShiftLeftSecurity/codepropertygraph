package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForBinding(val node: nodes.Binding) extends AnyVal {

  /** Traverse to METHOD via REF OUT edge.
    */
  @deprecated("please use boundMethod instead")
  def _methodViaRefOut: nodes.Method = boundMethod

  /** Traverse to METHOD via REF OUT edge.
    */
  def boundMethod: nodes.Method = {
    try { refOut.collectAll[nodes.Method].next() }
    catch {
      case e: java.util.NoSuchElementException =>
        throw new flatgraph.SchemaViolationException(
          "OUT edge with label REF to an adjacent METHOD is mandatory, but not defined for this BINDING node with seq=" + node.seq,
          e
        )
    }
  }

  /** Traverse to TYPE_DECL via BINDS IN edge.
    */
  @deprecated("please use bindingTypeDecl instead")
  def _typeDeclViaBindsIn: nodes.TypeDecl = bindingTypeDecl

  /** Traverse to TYPE_DECL via BINDS IN edge.
    */
  def bindingTypeDecl: nodes.TypeDecl = {
    try { bindsIn.collectAll[nodes.TypeDecl].next() }
    catch {
      case e: java.util.NoSuchElementException =>
        throw new flatgraph.SchemaViolationException(
          "IN edge with label BINDS to an adjacent TYPE_DECL is mandatory, but not defined for this BINDING node with seq=" + node.seq,
          e
        )
    }
  }

  def bindsIn: Iterator[nodes.TypeDecl] = node._bindsIn.cast[nodes.TypeDecl]

  def refOut: Iterator[nodes.Method] = node._refOut.cast[nodes.Method]
}

final class AccessNeighborsForBindingTraversal(val traversal: Iterator[nodes.Binding]) extends AnyVal {

  /** Traverse to METHOD via REF OUT edge.
    */
  def boundMethod: Iterator[nodes.Method] = traversal.map(_.boundMethod)

  /** Traverse to METHOD via REF OUT edge.
    */
  @deprecated("please use boundMethod instead")
  def _methodViaRefOut: Iterator[nodes.Method] = traversal.map(_._methodViaRefOut)

  /** Traverse to TYPE_DECL via BINDS IN edge.
    */
  def bindingTypeDecl: Iterator[nodes.TypeDecl] = traversal.map(_.bindingTypeDecl)

  /** Traverse to TYPE_DECL via BINDS IN edge.
    */
  @deprecated("please use bindingTypeDecl instead")
  def _typeDeclViaBindsIn: Iterator[nodes.TypeDecl] = traversal.map(_._typeDeclViaBindsIn)

  def bindsIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_.bindsIn)

  def refOut: Iterator[nodes.Method] = traversal.flatMap(_.refOut)
}
