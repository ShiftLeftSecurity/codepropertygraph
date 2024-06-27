package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForMethod(val node: nodes.Method) extends AnyVal {

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def _annotationViaAstOut: Iterator[nodes.Annotation] = astOut.collectAll[nodes.Annotation]

  /** Traverse to BINDING via REF IN edge.
    */
  def _bindingViaRefIn: Iterator[nodes.Binding] = refIn.collectAll[nodes.Binding]

  /** Traverse to BLOCK via CONTAINS OUT edge.
    */
  def _blockViaContainsOut: Iterator[nodes.Block] = containsOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def _blockViaDominateOut: Iterator[nodes.Block] = dominateOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via POST_DOMINATE IN edge.
    */
  def _blockViaPostDominateIn: Iterator[nodes.Block] = postDominateIn.collectAll[nodes.Block]

  /** Traverse to CALL via CALL IN edge.
    */
  def _callViaCallIn: Iterator[nodes.Call] = callIn.collectAll[nodes.Call]

  /** Traverse to CALL via CONTAINS OUT edge.
    */
  def _callViaContainsOut: Iterator[nodes.Call] = containsOut.collectAll[nodes.Call]

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def _callViaDominateOut: Iterator[nodes.Call] = dominateOut.collectAll[nodes.Call]

  /** Traverse to CALL via POST_DOMINATE IN edge.
    */
  def _callViaPostDominateIn: Iterator[nodes.Call] = postDominateIn.collectAll[nodes.Call]

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def _callViaReachingDefOut: Iterator[nodes.Call] = reachingDefOut.collectAll[nodes.Call]

  /** Traverse to CONTROL_STRUCTURE via CONTAINS OUT edge.
    */
  def _controlStructureViaContainsOut: Iterator[nodes.ControlStructure] = containsOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE IN edge.
    */
  def _controlStructureViaPostDominateIn: Iterator[nodes.ControlStructure] =
    postDominateIn.collectAll[nodes.ControlStructure]

  /** Traverse to FIELD_IDENTIFIER via CONTAINS OUT edge.
    */
  def _fieldIdentifierViaContainsOut: Iterator[nodes.FieldIdentifier] = containsOut.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def _fieldIdentifierViaDominateOut: Iterator[nodes.FieldIdentifier] = dominateOut.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE IN edge.
    */
  def _fieldIdentifierViaPostDominateIn: Iterator[nodes.FieldIdentifier] =
    postDominateIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to FILE via CONTAINS IN edge.
    */
  def _fileViaContainsIn: Iterator[nodes.File] = containsIn.collectAll[nodes.File]

  /** Traverse to FILE via SOURCE_FILE OUT edge.
    */
  def _fileViaSourceFileOut: Iterator[nodes.File] = sourceFileOut.collectAll[nodes.File]

  /** Traverse to IDENTIFIER via CONTAINS OUT edge.
    */
  def _identifierViaContainsOut: Iterator[nodes.Identifier] = containsOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def _identifierViaDominateOut: Iterator[nodes.Identifier] = dominateOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via POST_DOMINATE IN edge.
    */
  def _identifierViaPostDominateIn: Iterator[nodes.Identifier] = postDominateIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def _identifierViaReachingDefOut: Iterator[nodes.Identifier] = reachingDefOut.collectAll[nodes.Identifier]

  /** Traverse to JUMP_TARGET via CONTAINS OUT edge.
    */
  def _jumpTargetViaContainsOut: Iterator[nodes.JumpTarget] = containsOut.collectAll[nodes.JumpTarget]

  /** Traverse to LITERAL via DOMINATE OUT edge.
    */
  def _literalViaDominateOut: Iterator[nodes.Literal] = dominateOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via POST_DOMINATE IN edge.
    */
  def _literalViaPostDominateIn: Iterator[nodes.Literal] = postDominateIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def _literalViaReachingDefOut: Iterator[nodes.Literal] = reachingDefOut.collectAll[nodes.Literal]

  /** Traverse to METHOD via AST IN edge.
    */
  def _methodViaAstIn: Option[nodes.Method] = astIn.collectAll[nodes.Method].nextOption()

  /** Traverse to METHOD via AST OUT edge.
    */
  def _methodViaAstOut: Iterator[nodes.Method] = astOut.collectAll[nodes.Method]

  /** Traverse to METHOD_PARAMETER_IN via REACHING_DEF OUT edge.
    */
  def _methodParameterInViaReachingDefOut: Iterator[nodes.MethodParameterIn] =
    reachingDefOut.collectAll[nodes.MethodParameterIn]

  /** Traverse to METHOD_PARAMETER_OUT via AST OUT edge.
    */
  def _methodParameterOutViaAstOut: Iterator[nodes.MethodParameterOut] = astOut.collectAll[nodes.MethodParameterOut]

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def _methodParameterOutViaReachingDefOut: Iterator[nodes.MethodParameterOut] =
    reachingDefOut.collectAll[nodes.MethodParameterOut]

  /** Traverse to METHOD_REF via CONTAINS OUT edge.
    */
  def _methodRefViaContainsOut: Iterator[nodes.MethodRef] = containsOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via DOMINATE OUT edge.
    */
  def _methodRefViaDominateOut: Iterator[nodes.MethodRef] = dominateOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via POST_DOMINATE IN edge.
    */
  def _methodRefViaPostDominateIn: Iterator[nodes.MethodRef] = postDominateIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def _methodRefViaReachingDefOut: Iterator[nodes.MethodRef] = reachingDefOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via REF IN edge.
    */
  def _methodRefViaRefIn: Iterator[nodes.MethodRef] = refIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_RETURN via CFG OUT edge.
    */
  def _methodReturnViaCfgOut: Option[nodes.MethodReturn] = cfgOut.collectAll[nodes.MethodReturn].nextOption()

  /** Traverse to METHOD_RETURN via DOMINATE OUT edge.
    */
  def _methodReturnViaDominateOut: Iterator[nodes.MethodReturn] = dominateOut.collectAll[nodes.MethodReturn]

  /** Traverse to METHOD_RETURN via POST_DOMINATE IN edge.
    */
  def _methodReturnViaPostDominateIn: Iterator[nodes.MethodReturn] = postDominateIn.collectAll[nodes.MethodReturn]

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def _modifierViaAstOut: Iterator[nodes.Modifier] = astOut.collectAll[nodes.Modifier]

  /** Traverse to NAMESPACE_BLOCK via AST IN edge.
    */
  def _namespaceBlockViaAstIn: Option[nodes.NamespaceBlock] = astIn.collectAll[nodes.NamespaceBlock].nextOption()

  /** Traverse to RETURN via CONTAINS OUT edge.
    */
  def _returnViaContainsOut: Iterator[nodes.Return] = containsOut.collectAll[nodes.Return]

  /** Traverse to RETURN via DOMINATE OUT edge.
    */
  def _returnViaDominateOut: Iterator[nodes.Return] = dominateOut.collectAll[nodes.Return]

  /** Traverse to RETURN via POST_DOMINATE IN edge.
    */
  def _returnViaPostDominateIn: Iterator[nodes.Return] = postDominateIn.collectAll[nodes.Return]

  /** Traverse to RETURN via REACHING_DEF OUT edge.
    */
  def _returnViaReachingDefOut: Iterator[nodes.Return] = reachingDefOut.collectAll[nodes.Return]

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: Iterator[nodes.Tag] = taggedByOut.collectAll[nodes.Tag]

  /** Traverse to TEMPLATE_DOM via CONTAINS OUT edge.
    */
  def _templateDomViaContainsOut: Iterator[nodes.TemplateDom] = containsOut.collectAll[nodes.TemplateDom]

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def _typeDeclViaAstIn: Option[nodes.TypeDecl] = astIn.collectAll[nodes.TypeDecl].nextOption()

  /** Traverse to TYPE_DECL via AST OUT edge.
    */
  def _typeDeclViaAstOut: Iterator[nodes.TypeDecl] = astOut.collectAll[nodes.TypeDecl]

  /** Traverse to TYPE_DECL via CONTAINS IN edge.
    */
  def _typeDeclViaContainsIn: Iterator[nodes.TypeDecl] = containsIn.collectAll[nodes.TypeDecl]

  /** Traverse to TYPE_PARAMETER via AST OUT edge.
    */
  def _typeParameterViaAstOut: Iterator[nodes.TypeParameter] = astOut.collectAll[nodes.TypeParameter]

  /** Traverse to TYPE_REF via CONTAINS OUT edge.
    */
  def _typeRefViaContainsOut: Iterator[nodes.TypeRef] = containsOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via DOMINATE OUT edge.
    */
  def _typeRefViaDominateOut: Iterator[nodes.TypeRef] = dominateOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via POST_DOMINATE IN edge.
    */
  def _typeRefViaPostDominateIn: Iterator[nodes.TypeRef] = postDominateIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def _typeRefViaReachingDefOut: Iterator[nodes.TypeRef] = reachingDefOut.collectAll[nodes.TypeRef]

  /** Traverse to UNKNOWN via CONTAINS OUT edge.
    */
  def _unknownViaContainsOut: Iterator[nodes.Unknown] = containsOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via DOMINATE OUT edge.
    */
  def _unknownViaDominateOut: Iterator[nodes.Unknown] = dominateOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via POST_DOMINATE IN edge.
    */
  def _unknownViaPostDominateIn: Iterator[nodes.Unknown] = postDominateIn.collectAll[nodes.Unknown]

  /** First control flow graph node Traverse to CFG_NODE via CFG OUT edge.
    */
  @deprecated("please use cfgFirst instead")
  def _cfgNodeViaCfgOut: Iterator[nodes.CfgNode] = cfgFirst

  /** First control flow graph node Traverse to CFG_NODE via CFG OUT edge.
    */
  def cfgFirst: Iterator[nodes.CfgNode] = cfgOut.collectAll[nodes.CfgNode]

  /** Formal return parameters Traverse to METHOD_RETURN via AST OUT edge.
    */
  @deprecated("please use methodReturn instead")
  def _methodReturnViaAstOut: nodes.MethodReturn = methodReturn

  /** Formal return parameters Traverse to METHOD_RETURN via AST OUT edge.
    */
  def methodReturn: nodes.MethodReturn = {
    try { astOut.collectAll[nodes.MethodReturn].next() }
    catch {
      case e: java.util.NoSuchElementException =>
        throw new flatgraph.SchemaViolationException(
          "OUT edge with label AST to an adjacent METHOD_RETURN is mandatory, but not defined for this METHOD node with seq=" + node.seq,
          e
        )
    }
  }

  /** Literals used in the method Traverse to LITERAL via CONTAINS OUT edge.
    */
  @deprecated("please use literal instead")
  def _literalViaContainsOut: Iterator[nodes.Literal] = literal

  /** Literals used in the method Traverse to LITERAL via CONTAINS OUT edge.
    */
  def literal: Iterator[nodes.Literal] = containsOut.collectAll[nodes.Literal]

  /** Parameters of the method Traverse to METHOD_PARAMETER_IN via AST OUT edge.
    */
  @deprecated("please use parameter instead")
  def _methodParameterInViaAstOut: Iterator[nodes.MethodParameterIn] = parameter

  /** Parameters of the method Traverse to METHOD_PARAMETER_IN via AST OUT edge.
    */
  def parameter: Iterator[nodes.MethodParameterIn] = astOut.collectAll[nodes.MethodParameterIn]

  /** Root of the abstract syntax tree Traverse to BLOCK via AST OUT edge.
    */
  @deprecated("please use block instead")
  def _blockViaAstOut: nodes.Block = block

  /** Root of the abstract syntax tree Traverse to BLOCK via AST OUT edge.
    */
  def block: nodes.Block = {
    try { astOut.collectAll[nodes.Block].next() }
    catch {
      case e: java.util.NoSuchElementException =>
        throw new flatgraph.SchemaViolationException(
          "OUT edge with label AST to an adjacent BLOCK is mandatory, but not defined for this METHOD node with seq=" + node.seq,
          e
        )
    }
  }

  def astIn: Iterator[nodes.AstNode] = node._astIn.cast[nodes.AstNode]

  def astOut: Iterator[nodes.AstNode] = node._astOut.cast[nodes.AstNode]

  def callIn: Iterator[nodes.Call] = node._callIn.cast[nodes.Call]

  def cfgOut: Iterator[nodes.AstNode] = node._cfgOut.cast[nodes.AstNode]

  def containsIn: Iterator[nodes.AstNode] = node._containsIn.cast[nodes.AstNode]

  def containsOut: Iterator[nodes.CfgNode] = node._containsOut.cast[nodes.CfgNode]

  def dominateOut: Iterator[nodes.CfgNode] = node._dominateOut.cast[nodes.CfgNode]

  def postDominateIn: Iterator[nodes.CfgNode] = node._postDominateIn.cast[nodes.CfgNode]

  def reachingDefOut: Iterator[nodes.CfgNode] = node._reachingDefOut.cast[nodes.CfgNode]

  def refIn: Iterator[nodes.StoredNode] = node._refIn.cast[nodes.StoredNode]

  def sourceFileOut: Iterator[nodes.File] = node._sourceFileOut.cast[nodes.File]

  def taggedByOut: Iterator[nodes.Tag] = node._taggedByOut.cast[nodes.Tag]
}

final class AccessNeighborsForMethodTraversal(val traversal: Iterator[nodes.Method]) extends AnyVal {

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def _annotationViaAstOut: Iterator[nodes.Annotation] = traversal.flatMap(_._annotationViaAstOut)

  /** Traverse to BINDING via REF IN edge.
    */
  def _bindingViaRefIn: Iterator[nodes.Binding] = traversal.flatMap(_._bindingViaRefIn)

  /** Traverse to BLOCK via CONTAINS OUT edge.
    */
  def _blockViaContainsOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaContainsOut)

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def _blockViaDominateOut: Iterator[nodes.Block] = traversal.flatMap(_._blockViaDominateOut)

  /** Traverse to BLOCK via POST_DOMINATE IN edge.
    */
  def _blockViaPostDominateIn: Iterator[nodes.Block] = traversal.flatMap(_._blockViaPostDominateIn)

  /** Traverse to CALL via CALL IN edge.
    */
  def _callViaCallIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaCallIn)

  /** Traverse to CALL via CONTAINS OUT edge.
    */
  def _callViaContainsOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaContainsOut)

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def _callViaDominateOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaDominateOut)

  /** Traverse to CALL via POST_DOMINATE IN edge.
    */
  def _callViaPostDominateIn: Iterator[nodes.Call] = traversal.flatMap(_._callViaPostDominateIn)

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def _callViaReachingDefOut: Iterator[nodes.Call] = traversal.flatMap(_._callViaReachingDefOut)

  /** Traverse to CONTROL_STRUCTURE via CONTAINS OUT edge.
    */
  def _controlStructureViaContainsOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaContainsOut)

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE IN edge.
    */
  def _controlStructureViaPostDominateIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_._controlStructureViaPostDominateIn)

  /** Traverse to FIELD_IDENTIFIER via CONTAINS OUT edge.
    */
  def _fieldIdentifierViaContainsOut: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_._fieldIdentifierViaContainsOut)

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def _fieldIdentifierViaDominateOut: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_._fieldIdentifierViaDominateOut)

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE IN edge.
    */
  def _fieldIdentifierViaPostDominateIn: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_._fieldIdentifierViaPostDominateIn)

  /** Traverse to FILE via CONTAINS IN edge.
    */
  def _fileViaContainsIn: Iterator[nodes.File] = traversal.flatMap(_._fileViaContainsIn)

  /** Traverse to FILE via SOURCE_FILE OUT edge.
    */
  def _fileViaSourceFileOut: Iterator[nodes.File] = traversal.flatMap(_._fileViaSourceFileOut)

  /** Traverse to IDENTIFIER via CONTAINS OUT edge.
    */
  def _identifierViaContainsOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaContainsOut)

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def _identifierViaDominateOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaDominateOut)

  /** Traverse to IDENTIFIER via POST_DOMINATE IN edge.
    */
  def _identifierViaPostDominateIn: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaPostDominateIn)

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def _identifierViaReachingDefOut: Iterator[nodes.Identifier] = traversal.flatMap(_._identifierViaReachingDefOut)

  /** Traverse to JUMP_TARGET via CONTAINS OUT edge.
    */
  def _jumpTargetViaContainsOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_._jumpTargetViaContainsOut)

  /** Traverse to LITERAL via DOMINATE OUT edge.
    */
  def _literalViaDominateOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaDominateOut)

  /** Traverse to LITERAL via POST_DOMINATE IN edge.
    */
  def _literalViaPostDominateIn: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaPostDominateIn)

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def _literalViaReachingDefOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaReachingDefOut)

  /** Traverse to METHOD via AST IN edge.
    */
  def _methodViaAstIn: Iterator[nodes.Method] = traversal.flatMap(_._methodViaAstIn)

  /** Traverse to METHOD via AST OUT edge.
    */
  def _methodViaAstOut: Iterator[nodes.Method] = traversal.flatMap(_._methodViaAstOut)

  /** Traverse to METHOD_PARAMETER_IN via REACHING_DEF OUT edge.
    */
  def _methodParameterInViaReachingDefOut: Iterator[nodes.MethodParameterIn] =
    traversal.flatMap(_._methodParameterInViaReachingDefOut)

  /** Traverse to METHOD_PARAMETER_OUT via AST OUT edge.
    */
  def _methodParameterOutViaAstOut: Iterator[nodes.MethodParameterOut] =
    traversal.flatMap(_._methodParameterOutViaAstOut)

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def _methodParameterOutViaReachingDefOut: Iterator[nodes.MethodParameterOut] =
    traversal.flatMap(_._methodParameterOutViaReachingDefOut)

  /** Traverse to METHOD_REF via CONTAINS OUT edge.
    */
  def _methodRefViaContainsOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaContainsOut)

  /** Traverse to METHOD_REF via DOMINATE OUT edge.
    */
  def _methodRefViaDominateOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaDominateOut)

  /** Traverse to METHOD_REF via POST_DOMINATE IN edge.
    */
  def _methodRefViaPostDominateIn: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaPostDominateIn)

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def _methodRefViaReachingDefOut: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaReachingDefOut)

  /** Traverse to METHOD_REF via REF IN edge.
    */
  def _methodRefViaRefIn: Iterator[nodes.MethodRef] = traversal.flatMap(_._methodRefViaRefIn)

  /** Traverse to METHOD_RETURN via CFG OUT edge.
    */
  def _methodReturnViaCfgOut: Iterator[nodes.MethodReturn] = traversal.flatMap(_._methodReturnViaCfgOut)

  /** Traverse to METHOD_RETURN via DOMINATE OUT edge.
    */
  def _methodReturnViaDominateOut: Iterator[nodes.MethodReturn] = traversal.flatMap(_._methodReturnViaDominateOut)

  /** Traverse to METHOD_RETURN via POST_DOMINATE IN edge.
    */
  def _methodReturnViaPostDominateIn: Iterator[nodes.MethodReturn] = traversal.flatMap(_._methodReturnViaPostDominateIn)

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def _modifierViaAstOut: Iterator[nodes.Modifier] = traversal.flatMap(_._modifierViaAstOut)

  /** Traverse to NAMESPACE_BLOCK via AST IN edge.
    */
  def _namespaceBlockViaAstIn: Iterator[nodes.NamespaceBlock] = traversal.flatMap(_._namespaceBlockViaAstIn)

  /** Traverse to RETURN via CONTAINS OUT edge.
    */
  def _returnViaContainsOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaContainsOut)

  /** Traverse to RETURN via DOMINATE OUT edge.
    */
  def _returnViaDominateOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaDominateOut)

  /** Traverse to RETURN via POST_DOMINATE IN edge.
    */
  def _returnViaPostDominateIn: Iterator[nodes.Return] = traversal.flatMap(_._returnViaPostDominateIn)

  /** Traverse to RETURN via REACHING_DEF OUT edge.
    */
  def _returnViaReachingDefOut: Iterator[nodes.Return] = traversal.flatMap(_._returnViaReachingDefOut)

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_._tagViaTaggedByOut)

  /** Traverse to TEMPLATE_DOM via CONTAINS OUT edge.
    */
  def _templateDomViaContainsOut: Iterator[nodes.TemplateDom] = traversal.flatMap(_._templateDomViaContainsOut)

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def _typeDeclViaAstIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_._typeDeclViaAstIn)

  /** Traverse to TYPE_DECL via AST OUT edge.
    */
  def _typeDeclViaAstOut: Iterator[nodes.TypeDecl] = traversal.flatMap(_._typeDeclViaAstOut)

  /** Traverse to TYPE_DECL via CONTAINS IN edge.
    */
  def _typeDeclViaContainsIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_._typeDeclViaContainsIn)

  /** Traverse to TYPE_PARAMETER via AST OUT edge.
    */
  def _typeParameterViaAstOut: Iterator[nodes.TypeParameter] = traversal.flatMap(_._typeParameterViaAstOut)

  /** Traverse to TYPE_REF via CONTAINS OUT edge.
    */
  def _typeRefViaContainsOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaContainsOut)

  /** Traverse to TYPE_REF via DOMINATE OUT edge.
    */
  def _typeRefViaDominateOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaDominateOut)

  /** Traverse to TYPE_REF via POST_DOMINATE IN edge.
    */
  def _typeRefViaPostDominateIn: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaPostDominateIn)

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def _typeRefViaReachingDefOut: Iterator[nodes.TypeRef] = traversal.flatMap(_._typeRefViaReachingDefOut)

  /** Traverse to UNKNOWN via CONTAINS OUT edge.
    */
  def _unknownViaContainsOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaContainsOut)

  /** Traverse to UNKNOWN via DOMINATE OUT edge.
    */
  def _unknownViaDominateOut: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaDominateOut)

  /** Traverse to UNKNOWN via POST_DOMINATE IN edge.
    */
  def _unknownViaPostDominateIn: Iterator[nodes.Unknown] = traversal.flatMap(_._unknownViaPostDominateIn)

  /** First control flow graph node Traverse to CFG_NODE via CFG OUT edge.
    */
  def cfgFirst: Iterator[nodes.CfgNode] = traversal.flatMap(_.cfgFirst)

  /** First control flow graph node Traverse to CFG_NODE via CFG OUT edge.
    */
  @deprecated("please use cfgFirst instead")
  def _cfgNodeViaCfgOut: Iterator[nodes.CfgNode] = traversal.flatMap(_._cfgNodeViaCfgOut)

  /** Formal return parameters Traverse to METHOD_RETURN via AST OUT edge.
    */
  def methodReturn: Iterator[nodes.MethodReturn] = traversal.map(_.methodReturn)

  /** Formal return parameters Traverse to METHOD_RETURN via AST OUT edge.
    */
  @deprecated("please use methodReturn instead")
  def _methodReturnViaAstOut: Iterator[nodes.MethodReturn] = traversal.map(_._methodReturnViaAstOut)

  /** Literals used in the method Traverse to LITERAL via CONTAINS OUT edge.
    */
  def literal: Iterator[nodes.Literal] = traversal.flatMap(_.literal)

  /** Literals used in the method Traverse to LITERAL via CONTAINS OUT edge.
    */
  @deprecated("please use literal instead")
  def _literalViaContainsOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaContainsOut)

  /** Parameters of the method Traverse to METHOD_PARAMETER_IN via AST OUT edge.
    */
  def parameter: Iterator[nodes.MethodParameterIn] = traversal.flatMap(_.parameter)

  /** Parameters of the method Traverse to METHOD_PARAMETER_IN via AST OUT edge.
    */
  @deprecated("please use parameter instead")
  def _methodParameterInViaAstOut: Iterator[nodes.MethodParameterIn] = traversal.flatMap(_._methodParameterInViaAstOut)

  /** Root of the abstract syntax tree Traverse to BLOCK via AST OUT edge.
    */
  def block: Iterator[nodes.Block] = traversal.map(_.block)

  /** Root of the abstract syntax tree Traverse to BLOCK via AST OUT edge.
    */
  @deprecated("please use block instead")
  def _blockViaAstOut: Iterator[nodes.Block] = traversal.map(_._blockViaAstOut)

  def astIn: Iterator[nodes.AstNode] = traversal.flatMap(_.astIn)

  def astOut: Iterator[nodes.AstNode] = traversal.flatMap(_.astOut)

  def callIn: Iterator[nodes.Call] = traversal.flatMap(_.callIn)

  def cfgOut: Iterator[nodes.AstNode] = traversal.flatMap(_.cfgOut)

  def containsIn: Iterator[nodes.AstNode] = traversal.flatMap(_.containsIn)

  def containsOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.containsOut)

  def dominateOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.dominateOut)

  def postDominateIn: Iterator[nodes.CfgNode] = traversal.flatMap(_.postDominateIn)

  def reachingDefOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.reachingDefOut)

  def refIn: Iterator[nodes.StoredNode] = traversal.flatMap(_.refIn)

  def sourceFileOut: Iterator[nodes.File] = traversal.flatMap(_.sourceFileOut)

  def taggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.taggedByOut)
}
