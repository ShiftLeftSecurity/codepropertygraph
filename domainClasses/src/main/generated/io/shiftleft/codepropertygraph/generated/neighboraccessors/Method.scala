package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.Language.*

final class AccessNeighborsForMethod(val node: nodes.Method) extends AnyVal {

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def annotationViaAstOut: Iterator[nodes.Annotation] = astOut.collectAll[nodes.Annotation]

  /** Traverse to BINDING via REF IN edge.
    */
  def bindingViaRefIn: Iterator[nodes.Binding] = refIn.collectAll[nodes.Binding]

  /** Traverse to BLOCK via CONTAINS OUT edge.
    */
  def blockViaContainsOut: Iterator[nodes.Block] = containsOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def blockViaDominateOut: Iterator[nodes.Block] = dominateOut.collectAll[nodes.Block]

  /** Traverse to BLOCK via POST_DOMINATE IN edge.
    */
  def blockViaPostDominateIn: Iterator[nodes.Block] = postDominateIn.collectAll[nodes.Block]

  /** Traverse to CALL via CALL IN edge.
    */
  def callViaCallIn: Iterator[nodes.Call] = callIn.collectAll[nodes.Call]

  /** Traverse to CALL via CONTAINS OUT edge.
    */
  def callViaContainsOut: Iterator[nodes.Call] = containsOut.collectAll[nodes.Call]

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def callViaDominateOut: Iterator[nodes.Call] = dominateOut.collectAll[nodes.Call]

  /** Traverse to CALL via POST_DOMINATE IN edge.
    */
  def callViaPostDominateIn: Iterator[nodes.Call] = postDominateIn.collectAll[nodes.Call]

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def callViaReachingDefOut: Iterator[nodes.Call] = reachingDefOut.collectAll[nodes.Call]

  /** Traverse to CONTROL_STRUCTURE via CONTAINS OUT edge.
    */
  def controlStructureViaContainsOut: Iterator[nodes.ControlStructure] = containsOut.collectAll[nodes.ControlStructure]

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE IN edge.
    */
  def controlStructureViaPostDominateIn: Iterator[nodes.ControlStructure] =
    postDominateIn.collectAll[nodes.ControlStructure]

  /** Traverse to FIELD_IDENTIFIER via CONTAINS OUT edge.
    */
  def fieldIdentifierViaContainsOut: Iterator[nodes.FieldIdentifier] = containsOut.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def fieldIdentifierViaDominateOut: Iterator[nodes.FieldIdentifier] = dominateOut.collectAll[nodes.FieldIdentifier]

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE IN edge.
    */
  def fieldIdentifierViaPostDominateIn: Iterator[nodes.FieldIdentifier] =
    postDominateIn.collectAll[nodes.FieldIdentifier]

  /** Traverse to FILE via CONTAINS IN edge.
    */
  def fileViaContainsIn: Iterator[nodes.File] = containsIn.collectAll[nodes.File]

  /** Traverse to FILE via SOURCE_FILE OUT edge.
    */
  def fileViaSourceFileOut: Iterator[nodes.File] = sourceFileOut.collectAll[nodes.File]

  /** Traverse to IDENTIFIER via CONTAINS OUT edge.
    */
  def identifierViaContainsOut: Iterator[nodes.Identifier] = containsOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def identifierViaDominateOut: Iterator[nodes.Identifier] = dominateOut.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via POST_DOMINATE IN edge.
    */
  def identifierViaPostDominateIn: Iterator[nodes.Identifier] = postDominateIn.collectAll[nodes.Identifier]

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def identifierViaReachingDefOut: Iterator[nodes.Identifier] = reachingDefOut.collectAll[nodes.Identifier]

  /** Traverse to JUMP_TARGET via CONTAINS OUT edge.
    */
  def jumpTargetViaContainsOut: Iterator[nodes.JumpTarget] = containsOut.collectAll[nodes.JumpTarget]

  /** Traverse to LITERAL via DOMINATE OUT edge.
    */
  def literalViaDominateOut: Iterator[nodes.Literal] = dominateOut.collectAll[nodes.Literal]

  /** Traverse to LITERAL via POST_DOMINATE IN edge.
    */
  def literalViaPostDominateIn: Iterator[nodes.Literal] = postDominateIn.collectAll[nodes.Literal]

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def literalViaReachingDefOut: Iterator[nodes.Literal] = reachingDefOut.collectAll[nodes.Literal]

  /** Traverse to METHOD via AST IN edge.
    */
  def methodViaAstIn: Option[nodes.Method] = astIn.collectAll[nodes.Method].nextOption()

  /** Traverse to METHOD via AST OUT edge.
    */
  def methodViaAstOut: Iterator[nodes.Method] = astOut.collectAll[nodes.Method]

  /** Traverse to METHOD_PARAMETER_IN via REACHING_DEF OUT edge.
    */
  def methodParameterInViaReachingDefOut: Iterator[nodes.MethodParameterIn] =
    reachingDefOut.collectAll[nodes.MethodParameterIn]

  /** Traverse to METHOD_PARAMETER_OUT via AST OUT edge.
    */
  def methodParameterOutViaAstOut: Iterator[nodes.MethodParameterOut] = astOut.collectAll[nodes.MethodParameterOut]

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def methodParameterOutViaReachingDefOut: Iterator[nodes.MethodParameterOut] =
    reachingDefOut.collectAll[nodes.MethodParameterOut]

  /** Traverse to METHOD_REF via CONTAINS OUT edge.
    */
  def methodRefViaContainsOut: Iterator[nodes.MethodRef] = containsOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via DOMINATE OUT edge.
    */
  def methodRefViaDominateOut: Iterator[nodes.MethodRef] = dominateOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via POST_DOMINATE IN edge.
    */
  def methodRefViaPostDominateIn: Iterator[nodes.MethodRef] = postDominateIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def methodRefViaReachingDefOut: Iterator[nodes.MethodRef] = reachingDefOut.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_REF via REF IN edge.
    */
  def methodRefViaRefIn: Iterator[nodes.MethodRef] = refIn.collectAll[nodes.MethodRef]

  /** Traverse to METHOD_RETURN via CFG OUT edge.
    */
  def methodReturnViaCfgOut: Option[nodes.MethodReturn] = cfgOut.collectAll[nodes.MethodReturn].nextOption()

  /** Traverse to METHOD_RETURN via DOMINATE OUT edge.
    */
  def methodReturnViaDominateOut: Iterator[nodes.MethodReturn] = dominateOut.collectAll[nodes.MethodReturn]

  /** Traverse to METHOD_RETURN via POST_DOMINATE IN edge.
    */
  def methodReturnViaPostDominateIn: Iterator[nodes.MethodReturn] = postDominateIn.collectAll[nodes.MethodReturn]

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def modifierViaAstOut: Iterator[nodes.Modifier] = astOut.collectAll[nodes.Modifier]

  /** Traverse to NAMESPACE_BLOCK via AST IN edge.
    */
  def namespaceBlockViaAstIn: Option[nodes.NamespaceBlock] = astIn.collectAll[nodes.NamespaceBlock].nextOption()

  /** Traverse to RETURN via CONTAINS OUT edge.
    */
  def returnViaContainsOut: Iterator[nodes.Return] = containsOut.collectAll[nodes.Return]

  /** Traverse to RETURN via DOMINATE OUT edge.
    */
  def returnViaDominateOut: Iterator[nodes.Return] = dominateOut.collectAll[nodes.Return]

  /** Traverse to RETURN via POST_DOMINATE IN edge.
    */
  def returnViaPostDominateIn: Iterator[nodes.Return] = postDominateIn.collectAll[nodes.Return]

  /** Traverse to RETURN via REACHING_DEF OUT edge.
    */
  def returnViaReachingDefOut: Iterator[nodes.Return] = reachingDefOut.collectAll[nodes.Return]

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: Iterator[nodes.Tag] = taggedByOut.collectAll[nodes.Tag]

  /** Traverse to TEMPLATE_DOM via CONTAINS OUT edge.
    */
  def templateDomViaContainsOut: Iterator[nodes.TemplateDom] = containsOut.collectAll[nodes.TemplateDom]

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def typeDeclViaAstIn: Option[nodes.TypeDecl] = astIn.collectAll[nodes.TypeDecl].nextOption()

  /** Traverse to TYPE_DECL via AST OUT edge.
    */
  def typeDeclViaAstOut: Iterator[nodes.TypeDecl] = astOut.collectAll[nodes.TypeDecl]

  /** Traverse to TYPE_DECL via CONTAINS IN edge.
    */
  def typeDeclViaContainsIn: Iterator[nodes.TypeDecl] = containsIn.collectAll[nodes.TypeDecl]

  /** Traverse to TYPE_PARAMETER via AST OUT edge.
    */
  def typeParameterViaAstOut: Iterator[nodes.TypeParameter] = astOut.collectAll[nodes.TypeParameter]

  /** Traverse to TYPE_REF via CONTAINS OUT edge.
    */
  def typeRefViaContainsOut: Iterator[nodes.TypeRef] = containsOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via DOMINATE OUT edge.
    */
  def typeRefViaDominateOut: Iterator[nodes.TypeRef] = dominateOut.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via POST_DOMINATE IN edge.
    */
  def typeRefViaPostDominateIn: Iterator[nodes.TypeRef] = postDominateIn.collectAll[nodes.TypeRef]

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def typeRefViaReachingDefOut: Iterator[nodes.TypeRef] = reachingDefOut.collectAll[nodes.TypeRef]

  /** Traverse to UNKNOWN via CONTAINS OUT edge.
    */
  def unknownViaContainsOut: Iterator[nodes.Unknown] = containsOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via DOMINATE OUT edge.
    */
  def unknownViaDominateOut: Iterator[nodes.Unknown] = dominateOut.collectAll[nodes.Unknown]

  /** Traverse to UNKNOWN via POST_DOMINATE IN edge.
    */
  def unknownViaPostDominateIn: Iterator[nodes.Unknown] = postDominateIn.collectAll[nodes.Unknown]

  /** First control flow graph node Traverse to CFG_NODE via CFG OUT edge.
    */
  @deprecated("please use cfgFirst instead")
  def cfgNodeViaCfgOut: Iterator[nodes.CfgNode] = cfgFirst

  /** First control flow graph node Traverse to CFG_NODE via CFG OUT edge.
    */
  def cfgFirst: Iterator[nodes.CfgNode] = cfgOut.collectAll[nodes.CfgNode]

  /** Formal return parameters Traverse to METHOD_RETURN via AST OUT edge.
    */
  @deprecated("please use methodReturn instead")
  def methodReturnViaAstOut: nodes.MethodReturn = methodReturn

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
  def literalViaContainsOut: Iterator[nodes.Literal] = literal

  /** Literals used in the method Traverse to LITERAL via CONTAINS OUT edge.
    */
  def literal: Iterator[nodes.Literal] = containsOut.collectAll[nodes.Literal]

  /** Parameters of the method Traverse to METHOD_PARAMETER_IN via AST OUT edge.
    */
  @deprecated("please use parameter instead")
  def methodParameterInViaAstOut: Iterator[nodes.MethodParameterIn] = parameter

  /** Parameters of the method Traverse to METHOD_PARAMETER_IN via AST OUT edge.
    */
  def parameter: Iterator[nodes.MethodParameterIn] = astOut.collectAll[nodes.MethodParameterIn]

  /** Root of the abstract syntax tree Traverse to BLOCK via AST OUT edge.
    */
  @deprecated("please use block instead")
  def blockViaAstOut: nodes.Block = block

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
  def annotationViaAstOut: Iterator[nodes.Annotation] = traversal.flatMap(_.annotationViaAstOut)

  /** Traverse to BINDING via REF IN edge.
    */
  def bindingViaRefIn: Iterator[nodes.Binding] = traversal.flatMap(_.bindingViaRefIn)

  /** Traverse to BLOCK via CONTAINS OUT edge.
    */
  def blockViaContainsOut: Iterator[nodes.Block] = traversal.flatMap(_.blockViaContainsOut)

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def blockViaDominateOut: Iterator[nodes.Block] = traversal.flatMap(_.blockViaDominateOut)

  /** Traverse to BLOCK via POST_DOMINATE IN edge.
    */
  def blockViaPostDominateIn: Iterator[nodes.Block] = traversal.flatMap(_.blockViaPostDominateIn)

  /** Traverse to CALL via CALL IN edge.
    */
  def callViaCallIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaCallIn)

  /** Traverse to CALL via CONTAINS OUT edge.
    */
  def callViaContainsOut: Iterator[nodes.Call] = traversal.flatMap(_.callViaContainsOut)

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def callViaDominateOut: Iterator[nodes.Call] = traversal.flatMap(_.callViaDominateOut)

  /** Traverse to CALL via POST_DOMINATE IN edge.
    */
  def callViaPostDominateIn: Iterator[nodes.Call] = traversal.flatMap(_.callViaPostDominateIn)

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def callViaReachingDefOut: Iterator[nodes.Call] = traversal.flatMap(_.callViaReachingDefOut)

  /** Traverse to CONTROL_STRUCTURE via CONTAINS OUT edge.
    */
  def controlStructureViaContainsOut: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_.controlStructureViaContainsOut)

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE IN edge.
    */
  def controlStructureViaPostDominateIn: Iterator[nodes.ControlStructure] =
    traversal.flatMap(_.controlStructureViaPostDominateIn)

  /** Traverse to FIELD_IDENTIFIER via CONTAINS OUT edge.
    */
  def fieldIdentifierViaContainsOut: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_.fieldIdentifierViaContainsOut)

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def fieldIdentifierViaDominateOut: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_.fieldIdentifierViaDominateOut)

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE IN edge.
    */
  def fieldIdentifierViaPostDominateIn: Iterator[nodes.FieldIdentifier] =
    traversal.flatMap(_.fieldIdentifierViaPostDominateIn)

  /** Traverse to FILE via CONTAINS IN edge.
    */
  def fileViaContainsIn: Iterator[nodes.File] = traversal.flatMap(_.fileViaContainsIn)

  /** Traverse to FILE via SOURCE_FILE OUT edge.
    */
  def fileViaSourceFileOut: Iterator[nodes.File] = traversal.flatMap(_.fileViaSourceFileOut)

  /** Traverse to IDENTIFIER via CONTAINS OUT edge.
    */
  def identifierViaContainsOut: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaContainsOut)

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def identifierViaDominateOut: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaDominateOut)

  /** Traverse to IDENTIFIER via POST_DOMINATE IN edge.
    */
  def identifierViaPostDominateIn: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaPostDominateIn)

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def identifierViaReachingDefOut: Iterator[nodes.Identifier] = traversal.flatMap(_.identifierViaReachingDefOut)

  /** Traverse to JUMP_TARGET via CONTAINS OUT edge.
    */
  def jumpTargetViaContainsOut: Iterator[nodes.JumpTarget] = traversal.flatMap(_.jumpTargetViaContainsOut)

  /** Traverse to LITERAL via DOMINATE OUT edge.
    */
  def literalViaDominateOut: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaDominateOut)

  /** Traverse to LITERAL via POST_DOMINATE IN edge.
    */
  def literalViaPostDominateIn: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaPostDominateIn)

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def literalViaReachingDefOut: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaReachingDefOut)

  /** Traverse to METHOD via AST IN edge.
    */
  def methodViaAstIn: Iterator[nodes.Method] = traversal.flatMap(_.methodViaAstIn)

  /** Traverse to METHOD via AST OUT edge.
    */
  def methodViaAstOut: Iterator[nodes.Method] = traversal.flatMap(_.methodViaAstOut)

  /** Traverse to METHOD_PARAMETER_IN via REACHING_DEF OUT edge.
    */
  def methodParameterInViaReachingDefOut: Iterator[nodes.MethodParameterIn] =
    traversal.flatMap(_.methodParameterInViaReachingDefOut)

  /** Traverse to METHOD_PARAMETER_OUT via AST OUT edge.
    */
  def methodParameterOutViaAstOut: Iterator[nodes.MethodParameterOut] = traversal.flatMap(_.methodParameterOutViaAstOut)

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def methodParameterOutViaReachingDefOut: Iterator[nodes.MethodParameterOut] =
    traversal.flatMap(_.methodParameterOutViaReachingDefOut)

  /** Traverse to METHOD_REF via CONTAINS OUT edge.
    */
  def methodRefViaContainsOut: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaContainsOut)

  /** Traverse to METHOD_REF via DOMINATE OUT edge.
    */
  def methodRefViaDominateOut: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaDominateOut)

  /** Traverse to METHOD_REF via POST_DOMINATE IN edge.
    */
  def methodRefViaPostDominateIn: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaPostDominateIn)

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def methodRefViaReachingDefOut: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaReachingDefOut)

  /** Traverse to METHOD_REF via REF IN edge.
    */
  def methodRefViaRefIn: Iterator[nodes.MethodRef] = traversal.flatMap(_.methodRefViaRefIn)

  /** Traverse to METHOD_RETURN via CFG OUT edge.
    */
  def methodReturnViaCfgOut: Iterator[nodes.MethodReturn] = traversal.flatMap(_.methodReturnViaCfgOut)

  /** Traverse to METHOD_RETURN via DOMINATE OUT edge.
    */
  def methodReturnViaDominateOut: Iterator[nodes.MethodReturn] = traversal.flatMap(_.methodReturnViaDominateOut)

  /** Traverse to METHOD_RETURN via POST_DOMINATE IN edge.
    */
  def methodReturnViaPostDominateIn: Iterator[nodes.MethodReturn] = traversal.flatMap(_.methodReturnViaPostDominateIn)

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def modifierViaAstOut: Iterator[nodes.Modifier] = traversal.flatMap(_.modifierViaAstOut)

  /** Traverse to NAMESPACE_BLOCK via AST IN edge.
    */
  def namespaceBlockViaAstIn: Iterator[nodes.NamespaceBlock] = traversal.flatMap(_.namespaceBlockViaAstIn)

  /** Traverse to RETURN via CONTAINS OUT edge.
    */
  def returnViaContainsOut: Iterator[nodes.Return] = traversal.flatMap(_.returnViaContainsOut)

  /** Traverse to RETURN via DOMINATE OUT edge.
    */
  def returnViaDominateOut: Iterator[nodes.Return] = traversal.flatMap(_.returnViaDominateOut)

  /** Traverse to RETURN via POST_DOMINATE IN edge.
    */
  def returnViaPostDominateIn: Iterator[nodes.Return] = traversal.flatMap(_.returnViaPostDominateIn)

  /** Traverse to RETURN via REACHING_DEF OUT edge.
    */
  def returnViaReachingDefOut: Iterator[nodes.Return] = traversal.flatMap(_.returnViaReachingDefOut)

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.tagViaTaggedByOut)

  /** Traverse to TEMPLATE_DOM via CONTAINS OUT edge.
    */
  def templateDomViaContainsOut: Iterator[nodes.TemplateDom] = traversal.flatMap(_.templateDomViaContainsOut)

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def typeDeclViaAstIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_.typeDeclViaAstIn)

  /** Traverse to TYPE_DECL via AST OUT edge.
    */
  def typeDeclViaAstOut: Iterator[nodes.TypeDecl] = traversal.flatMap(_.typeDeclViaAstOut)

  /** Traverse to TYPE_DECL via CONTAINS IN edge.
    */
  def typeDeclViaContainsIn: Iterator[nodes.TypeDecl] = traversal.flatMap(_.typeDeclViaContainsIn)

  /** Traverse to TYPE_PARAMETER via AST OUT edge.
    */
  def typeParameterViaAstOut: Iterator[nodes.TypeParameter] = traversal.flatMap(_.typeParameterViaAstOut)

  /** Traverse to TYPE_REF via CONTAINS OUT edge.
    */
  def typeRefViaContainsOut: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaContainsOut)

  /** Traverse to TYPE_REF via DOMINATE OUT edge.
    */
  def typeRefViaDominateOut: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaDominateOut)

  /** Traverse to TYPE_REF via POST_DOMINATE IN edge.
    */
  def typeRefViaPostDominateIn: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaPostDominateIn)

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def typeRefViaReachingDefOut: Iterator[nodes.TypeRef] = traversal.flatMap(_.typeRefViaReachingDefOut)

  /** Traverse to UNKNOWN via CONTAINS OUT edge.
    */
  def unknownViaContainsOut: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaContainsOut)

  /** Traverse to UNKNOWN via DOMINATE OUT edge.
    */
  def unknownViaDominateOut: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaDominateOut)

  /** Traverse to UNKNOWN via POST_DOMINATE IN edge.
    */
  def unknownViaPostDominateIn: Iterator[nodes.Unknown] = traversal.flatMap(_.unknownViaPostDominateIn)

  /** First control flow graph node Traverse to CFG_NODE via CFG OUT edge.
    */
  def cfgFirst: Iterator[nodes.CfgNode] = traversal.flatMap(_.cfgFirst)

  /** First control flow graph node Traverse to CFG_NODE via CFG OUT edge.
    */
  @deprecated("please use cfgFirst instead")
  def cfgNodeViaCfgOut: Iterator[nodes.CfgNode] = traversal.flatMap(_.cfgNodeViaCfgOut)

  /** Formal return parameters Traverse to METHOD_RETURN via AST OUT edge.
    */
  def methodReturn: Iterator[nodes.MethodReturn] = traversal.map(_.methodReturn)

  /** Formal return parameters Traverse to METHOD_RETURN via AST OUT edge.
    */
  @deprecated("please use methodReturn instead")
  def methodReturnViaAstOut: Iterator[nodes.MethodReturn] = traversal.map(_.methodReturnViaAstOut)

  /** Literals used in the method Traverse to LITERAL via CONTAINS OUT edge.
    */
  def literal: Iterator[nodes.Literal] = traversal.flatMap(_.literal)

  /** Literals used in the method Traverse to LITERAL via CONTAINS OUT edge.
    */
  @deprecated("please use literal instead")
  def literalViaContainsOut: Iterator[nodes.Literal] = traversal.flatMap(_.literalViaContainsOut)

  /** Parameters of the method Traverse to METHOD_PARAMETER_IN via AST OUT edge.
    */
  def parameter: Iterator[nodes.MethodParameterIn] = traversal.flatMap(_.parameter)

  /** Parameters of the method Traverse to METHOD_PARAMETER_IN via AST OUT edge.
    */
  @deprecated("please use parameter instead")
  def methodParameterInViaAstOut: Iterator[nodes.MethodParameterIn] = traversal.flatMap(_.methodParameterInViaAstOut)

  /** Root of the abstract syntax tree Traverse to BLOCK via AST OUT edge.
    */
  def block: Iterator[nodes.Block] = traversal.map(_.block)

  /** Root of the abstract syntax tree Traverse to BLOCK via AST OUT edge.
    */
  @deprecated("please use block instead")
  def blockViaAstOut: Iterator[nodes.Block] = traversal.map(_.blockViaAstOut)

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
