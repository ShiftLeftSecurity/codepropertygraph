package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.semanticcpg.language._
import overflowdb._
import overflowdb.traversal.help.Doc
import overflowdb.traversal.{Traversal, help}

/**
  * A method, function, or procedure
  * */
@help.Traversal(elementType = classOf[Method])
class MethodTraversal(val traversal: Traversal[Method]) extends AnyVal {

  /**
    * Traverse to parameters of the method
    * */
  @Doc("All parameters")
  def parameter: Traversal[MethodParameterIn] =
    traversal.flatMap(_.parameter)

  /**
    * Traverse to formal return parameter
    * */
  @Doc("All formal return parameters")
  def methodReturn: Traversal[MethodReturn] =
    traversal.map(_.methodReturn)

  /**
    * Traverse to type decl which have this method bound to it.
    */
  def bindingTypeDecl: Traversal[TypeDecl] =
    referencingBinding.bindingTypeDecl

  /**
    * Traverse to bindings which reference to this method.
    */
  def referencingBinding: Traversal[Binding] =
    traversal.in(EdgeTypes.REF).where(_.hasLabel(NodeTypes.BINDING)).cast[Binding]

  /**
    * All control structures of this method
    * */
  @Doc("Control structures (source frontends only)")
  def controlStructure: Traversal[ControlStructure] =
    traversal.ast.isControlStructure

  /**
    * Shorthand to traverse to control structures where condition matches `regex`
    * */
  def controlStructure(regex: String): Traversal[ControlStructure] =
    traversal.ast.isControlStructure.code(regex)

  /**
    * The type declaration associated with this method, e.g., the class it is defined in.
    * */
  @Doc("Type this method is defined in")
  def definingTypeDecl: Traversal[TypeDecl] =
    traversal
      .repeat(_.in(EdgeTypes.AST))(_.until(_.hasLabel(NodeTypes.TYPE_DECL)))
      .cast[TypeDecl]

  /**
    * The method in which this method is defined
    * */
  @Doc("Method this method is defined in")
  def definingMethod: Traversal[Method] =
    traversal
      .repeat(_.in(EdgeTypes.AST))(_.until(_.hasLabel(NodeTypes.METHOD)))
      .cast[Method]

  /**
    * Traverse only to methods that are stubs, e.g., their code is not available
    * or the method body is empty.
    * */
  def isStub: Traversal[Method] =
    traversal.where(_.not(_.out(EdgeTypes.CFG).not(_.hasLabel(NodeTypes.METHOD_RETURN))))

  /**
    * Traverse only to methods that are not stubs.
    * */
  def isNotStub: Traversal[Method] =
    traversal.where(_.out(EdgeTypes.CFG).not(_.hasLabel(NodeTypes.METHOD_RETURN)))

  /**
    * Traverse to external methods, that is, methods not present
    * but only referenced in the CPG.
    * */
  @Doc("External methods (called, but no body available)")
  def external: Traversal[Method] =
    traversal.has(Properties.IS_EXTERNAL -> true)

  /**
    * Traverse to internal methods, that is, methods for which
    * code is included in this CPG.
    * */
  @Doc("Internal methods, i.e., a body is available")
  def internal: Traversal[Method] =
    traversal.has(Properties.IS_EXTERNAL -> false)

  /**
    * Traverse to the methods local variables
    * */
  @Doc("Local variables declared in the method")
  def local: Traversal[Local] = block.ast.isLocal

  /**
    * Traverse to literals of method
    * */
  @Doc("Literals used in the method")
  def literal: Traversal[Literal] =
    traversal.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.LITERAL).cast[Literal]

  @Doc("Top level expressions (\"Statements\")")
  def topLevelExpressions: Traversal[Expression] =
    traversal
      .out(EdgeTypes.AST)
      .hasLabel(NodeTypes.BLOCK)
      .out(EdgeTypes.AST)
      .not(_.hasLabel(NodeTypes.LOCAL))
      .cast[Expression]

  @Doc("Control flow graph nodes")
  def cfgNode: Traversal[CfgNode] =
    traversal.flatMap(_.cfgNode)

  /**
    *  Traverse to first expression in CFG.
    */
  @Doc("First control flow graph node")
  def cfgFirst: Traversal[Expression] =
    traversal.out(EdgeTypes.CFG).cast[Expression]

  /**
    *  Traverse to last expression in CFG.
    */
  @Doc("Last control flow graph node")
  def cfgLast: Traversal[Expression] =
    methodReturn.cfgLast

  /**
    * Traverse to block
    * */
  @Doc("Root of the abstract syntax tree")
  def block: Traversal[Block] =
    traversal.out(EdgeTypes.AST).hasLabel(NodeTypes.BLOCK).cast[Block]

  /** Traverse to method body (alias for `block`) */
  @Doc("Alias for `block`")
  def body: Traversal[Block] = block

  /** Traverse to namespace */
  @Doc("Namespace this method is declared in")
  def namespace: Traversal[Namespace] =
    definingTypeDecl.namespace

  def numberOfLines: Traversal[Int] = traversal.map(_.numberOfLines)

}
