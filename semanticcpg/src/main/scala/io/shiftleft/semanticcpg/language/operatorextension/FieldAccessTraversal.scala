package io.shiftleft.semanticcpg.language.operatorextension

import io.shiftleft.codepropertygraph.generated.nodes.{Expression, FieldIdentifier, Member, TypeDecl}
import overflowdb.traversal.Traversal
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.help.Doc

class FieldAccessTraversal(val traversal: Traversal[OpNodes.FieldAccess]) extends AnyVal {

  @Doc(info = "The identifier of the referenced field (right-hand side)")
  def fieldIdentifier : Traversal[FieldIdentifier] =
    traversal.argument(2).isFieldIdentifier

  @Doc(info = "Attempts to resolve the type declaration for this field access")
  def typeDecl : Traversal[TypeDecl] =
    traversal.argument(1).flatMap(resolveTypeDecl)

  //
  //def member : Traversal[Member] =
  //    traversal.map{x => (x.fieldIdentifier) }

  private def resolveTypeDecl(expr: Expression) : Traversal[TypeDecl] =
    expr.typ.referencedTypeDecl

}
