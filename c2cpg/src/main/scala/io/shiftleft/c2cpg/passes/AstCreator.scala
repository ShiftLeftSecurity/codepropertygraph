package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.Defines
import io.shiftleft.codepropertygraph.generated.{DispatchTypes, EdgeTypes, Operators}
import io.shiftleft.codepropertygraph.generated.nodes.{
  NewBlock,
  NewCall,
  NewFile,
  NewIdentifier,
  NewLiteral,
  NewLocal,
  NewMember,
  NewMethod,
  NewMethodParameterIn,
  NewMethodReturn,
  NewNamespaceBlock,
  NewNode
}
import io.shiftleft.passes.DiffGraph
import io.shiftleft.semanticcpg.language.types.structure.NamespaceTraversal
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import org.eclipse.cdt.core.dom.ast.{
  IASTBinaryExpression,
  IASTCompoundStatement,
  IASTDeclSpecifier,
  IASTDeclaration,
  IASTDeclarationStatement,
  IASTDeclarator,
  IASTEqualsInitializer,
  IASTExpression,
  IASTExpressionStatement,
  IASTFunctionDefinition,
  IASTIdExpression,
  IASTInitializer,
  IASTLiteralExpression,
  IASTName,
  IASTNode,
  IASTParameterDeclaration,
  IASTSimpleDeclaration,
  IASTStatement,
  IASTTranslationUnit
}
import org.eclipse.cdt.internal.core.dom.parser.c.{
  CASTCompositeTypeSpecifier,
  CASTFunctionDeclarator,
  CASTParameterDeclaration,
  CASTSimpleDeclSpecifier
}
import org.eclipse.cdt.internal.core.dom.parser.cpp.{
  CPPASTCompositeTypeSpecifier,
  CPPASTFunctionDeclarator,
  CPPASTParameterDeclaration,
  CPPASTSimpleDeclSpecifier
}

import scala.collection.mutable

object AstCreator {

  case class AstEdge(src: NewNode, dst: NewNode)

  object Ast {
    def apply(node: NewNode): Ast = Ast(List(node))

    def apply(): Ast = new Ast(List())
  }

  case class Ast(
      nodes: List[NewNode],
      edges: List[AstEdge] = List(),
      conditionEdges: List[AstEdge] = List(),
      refEdges: List[AstEdge] = List(),
      argEdges: List[AstEdge] = List()
  ) {

    def root: Option[NewNode] = nodes.headOption

    def rightMostLeaf: Option[NewNode] = nodes.lastOption

    /** AST that results when adding `other` as a child to this AST.
      * `other` is connected to this AST's root node.
      */
    def withChild(other: Ast): Ast = {
      Ast(
        nodes ++ other.nodes,
        edges = edges ++ other.edges ++ root.toList.flatMap(r =>
          other.root.toList.map { rc =>
            AstEdge(r, rc)
        }),
        conditionEdges = conditionEdges ++ other.conditionEdges,
        argEdges = argEdges ++ other.argEdges,
        refEdges = refEdges ++ other.refEdges
      )
    }

    /** AST that results when adding all ASTs in `asts` as children,
      * that is, connecting them to the root node of this AST.
      */
    def withChildren(asts: Seq[Ast]): Ast = {
      asts.headOption match {
        case Some(head) =>
          withChild(head).withChildren(asts.tail)
        case None =>
          this
      }
    }

    def withConditionEdge(src: NewNode, dst: NewNode): Ast = {
      this.copy(conditionEdges = conditionEdges ++ List(AstEdge(src, dst)))
    }

    def withRefEdge(src: NewNode, dst: NewNode): Ast = {
      this.copy(refEdges = refEdges ++ List(AstEdge(src, dst)))
    }

    def withArgEdge(src: NewNode, dst: NewNode): Ast = {
      this.copy(argEdges = argEdges ++ List(AstEdge(src, dst)))
    }

  }

  def line(node: IASTNode): Option[Integer] = {
    Some(node.getFileLocation.getStartingLineNumber)
  }

  def column(node: IASTNode): Option[Integer] = {
    Some(node.getFileLocation.getNodeOffset)
  }

}

class AstCreator(filename: String) {

  import AstCreator._

  // private val stack: mutable.Stack[NewNode] = mutable.Stack()

  private val scope = mutable.HashMap.empty[String, (NewNode, String)]
  private val diffGraph: DiffGraph.Builder = DiffGraph.newBuilder

  def createAst(parserResult: IASTTranslationUnit): Iterator[DiffGraph] = {
    storeInDiffGraph(astForFile(parserResult))
    Iterator(diffGraph.build())
  }

  /** Copy nodes/edges of given `AST` into the diff graph
    */
  private def storeInDiffGraph(ast: Ast): Unit = {
    ast.nodes.foreach { node =>
      diffGraph.addNode(node)
    }
    ast.edges.foreach { edge =>
      diffGraph.addEdge(edge.src, edge.dst, EdgeTypes.AST)
    }
    ast.conditionEdges.foreach { edge =>
      diffGraph.addEdge(edge.src, edge.dst, EdgeTypes.CONDITION)
    }
    ast.refEdges.foreach { edge =>
      diffGraph.addEdge(edge.src, edge.dst, EdgeTypes.REF)
    }
    ast.argEdges.foreach { edge =>
      diffGraph.addEdge(edge.src, edge.dst, EdgeTypes.ARGUMENT)
    }
  }

  private def withOrder[T <: IASTNode, X](nodeList: List[T])(f: (T, Int) => X): Seq[X] =
    nodeList.zipWithIndex.map {
      case (x, i) =>
        f(x, i + 1)
    }

  private def astForFile(parserResult: IASTTranslationUnit): Ast =
    Ast(NewFile(name = filename, order = 0))
      .withChild(
        astForIASTTranslationUnit(parserResult)
          .withChildren(withOrder(parserResult.getDeclarations.toList) { (decl, order) =>
            astForIASTDeclaration(decl, order)
          })
      )

  private def astForIASTTranslationUnit(iASTTranslationUnit: IASTTranslationUnit): Ast = {
    val absolutePath = new java.io.File(iASTTranslationUnit.getFilePath).toPath.toAbsolutePath.normalize().toString
    val namespaceBlock = NewNamespaceBlock()
      .name(NamespaceTraversal.globalNamespaceName)
      .fullName(MetaDataPass.getGlobalNamespaceBlockFullName(Some(absolutePath)))
      .filename(absolutePath)
      .order(1)
    Ast(namespaceBlock)
  }

  private def params(functDef: IASTFunctionDefinition): List[IASTParameterDeclaration] = functDef.getDeclarator match {
    case decl: CPPASTFunctionDeclarator => decl.getParameters.toList
    case decl: CASTFunctionDeclarator   => decl.getParameters.toList
    case _                              => ???
  }

  private def astForLiteral(lit: IASTLiteralExpression, order: Int): Ast = {
    val tpe = lit.getExpressionType.toString
    val litNode = NewLiteral()
      .typeFullName(tpe)
      .code(lit.getRawSignature)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(lit))
      .columnNumber(column(lit))
    Ast(litNode)
  }

  private def astForIdentifier(ident: IASTNode, order: Int): Ast = {
    val identifierName = ident.toString

    val variableOption = scope.get(identifierName)
    val identifierTypeName = variableOption match {
      case Some((_, variableTypeName)) =>
        variableTypeName
      case None =>
        Defines.anyTypeName
    }

    val cpgIdentifier = NewIdentifier()
      .name(identifierName)
      .typeFullName(identifierTypeName)
      .code(ident.getRawSignature)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(ident))
      .columnNumber(column(ident))

    variableOption match {
      case Some((variable, _)) =>
        Ast(cpgIdentifier).withRefEdge(cpgIdentifier, variable)
      case None => Ast(cpgIdentifier)
    }
  }

  private def astForIASTNode(node: IASTNode, order: Int): Ast = {
    node match {
      case lit: IASTLiteralExpression => astForLiteral(lit, order)
      case name: IASTName             => astForIdentifier(name, order)
      case ident: IASTIdExpression    => astForIdentifier(ident, order)
      // TODO
      case _ => Ast()
    }
  }

  private def astForIASTFunctionDefinition(functDef: IASTFunctionDefinition, order: Int): Ast = {
    val methodNode = createMethodNode(functDef, order)

    val parameterAsts = withOrder(params(functDef)) { (p, order) =>
      astForParameter(p, order)
    }

    val lastOrder = 2 + parameterAsts.size
    Ast(methodNode)
      .withChildren(parameterAsts)
      .withChild(astForMethodBody(Option(functDef.getBody), lastOrder))
      .withChild(astForMethodReturn(functDef, lastOrder, typeForIASTDeclSpecifier(functDef.getDeclSpecifier)))
  }

  private def astForMethodReturn(functDef: IASTFunctionDefinition, order: Int, tpe: String): Ast = {
    val methodReturnNode =
      NewMethodReturn()
        .order(order)
        .typeFullName(tpe)
        .code(tpe)
        .lineNumber(line(functDef.getDeclarator))
    Ast(methodReturnNode)
  }

  private def paramListSignature(functDef: IASTFunctionDefinition, includeParamNames: Boolean): String = {
    val elements =
      if (!includeParamNames) params(functDef).map(p => typeForIASTDeclSpecifier(p.getDeclSpecifier))
      else params(functDef).map(p => p.getDeclarator.getName.toString)
    "(" + elements.mkString(",") + ")"
  }

  /*
  private def parentName(node: IASTNode): String = Option(node.getParent) match {
    case Some(p: CPPASTCompositeTypeSpecifier) => p.getName.toString
    case Some(p: CASTCompositeTypeSpecifier)   => p.getName.toString
    case None                                  => ""
  }

  private def methodFullName(functDef: IASTFunctionDefinition): String = {
    val typeName = parentName(functDef)
    val returnType = typeForIASTDeclSpecifier(functDef.getDeclSpecifier)
    val methodName = functDef.getDeclarator.getName.toString
    s"$typeName.$methodName:$returnType${paramListSignature(functDef, includeParamNames = false)}"
  }
   */

  private def createMethodNode(
      functDef: IASTFunctionDefinition,
      childNum: Int
  ) = {
    val returnType = typeForIASTDeclSpecifier(functDef.getDeclSpecifier)
    val signature = returnType + " " + paramListSignature(functDef, includeParamNames = false)
    val code = returnType + " " + paramListSignature(functDef, includeParamNames = true)
    val name = functDef.getDeclarator.getName.toString
    val methodNode = NewMethod()
      .name(functDef.getDeclarator.getName.toString)
      .code(code)
      .isExternal(false)
      // TODO: maybe calculate actual fullName here
      // .fullName(methodFullName(functDef))
      .fullName(name)
      .lineNumber(line(functDef))
      .columnNumber(column(functDef))
      .signature(signature)
      .filename(filename)
      .order(childNum)
    methodNode
  }

  private def astForBlockStatement(stmt: IASTCompoundStatement, order: Int): Ast = {
    val block = NewBlock(order = order, lineNumber = line(stmt), columnNumber = column(stmt))
    Ast(block).withChildren(
      withOrder(stmt.getStatements.toList) { (x, order) =>
        astsForIASTStatement(x, order)
      }.flatten
    )
  }

  private def parentIsClassDef(node: IASTNode): Boolean = Option(node.getParent) match {
    case Some(_: CPPASTCompositeTypeSpecifier) => true
    case Some(_: CASTCompositeTypeSpecifier)   => true
    case _                                     => false
  }

  private def newCallNode(astNode: IASTNode, methodName: String, order: Int): NewCall = {
    NewCall()
      .name(methodName)
      .dispatchType(DispatchTypes.STATIC_DISPATCH)
      .methodFullName(methodName)
      .code(astNode.getRawSignature)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(astNode))
      .columnNumber(column(astNode))
  }

  private def astForIASTInitializer(declarator: IASTDeclarator, init: IASTInitializer, order: Int): Ast =
    Option(init) match {
      case Some(i: IASTEqualsInitializer) =>
        val operatorName = Operators.assignment
        val callNode = newCallNode(i, operatorName, order)
        val left = astForIASTNode(declarator.getName, 1)
        val right = astForIASTNode(i.getInitializerClause, 2)
        Ast(callNode)
          .withChild(left)
          .withArgEdge(callNode, left.root.get)
          .withChild(right)
          .withArgEdge(callNode, right.root.get)
      case _ => Ast()
    }

  private def astForIASTDeclarator(declaration: IASTSimpleDeclaration, declarator: IASTDeclarator, order: Int): Ast = {
    val declTypeName = typeForIASTDeclSpecifier(declaration.getDeclSpecifier)
    val name = declarator.getName.toString
    val newNode = if (parentIsClassDef(declaration.getParent)) {
      NewMember()
        .code(declaration.getRawSignature)
        .name(name)
        .typeFullName(declTypeName)
        .order(order)
    } else {
      NewLocal()
        .code(name)
        .name(name)
        .typeFullName(declTypeName)
        .order(order)
    }
    scope(name) = (newNode, declTypeName)
    Ast(newNode)
  }

  def astsForIASTDeclarationStatement(decl: IASTDeclarationStatement, order: Int): Seq[Ast] =
    decl.getDeclaration match {
      case s: IASTSimpleDeclaration =>
        val locals =
          withOrder(s.getDeclarators.toList) { (d, o) =>
            astForIASTDeclarator(s, d, order + o)
          }
        val calls =
          withOrder(s.getDeclarators.toList) { (d, o) =>
            astForIASTInitializer(d, d.getInitializer, locals.size + o)
          }
        locals ++ calls
      case _ => ???
    }

  private def astForIASTBinaryExpression(bin: IASTBinaryExpression, order: Int): Ast = {
    val op = bin.getOperator match {
      case IASTBinaryExpression.op_multiply         => ???
      case IASTBinaryExpression.op_divide           => ???
      case IASTBinaryExpression.op_modulo           => ???
      case IASTBinaryExpression.op_plus             => ???
      case IASTBinaryExpression.op_minus            => ???
      case IASTBinaryExpression.op_shiftLeft        => ???
      case IASTBinaryExpression.op_shiftRight       => ???
      case IASTBinaryExpression.op_lessThan         => ???
      case IASTBinaryExpression.op_greaterThan      => ???
      case IASTBinaryExpression.op_lessEqual        => ???
      case IASTBinaryExpression.op_greaterEqual     => ???
      case IASTBinaryExpression.op_binaryAnd        => ???
      case IASTBinaryExpression.op_binaryXor        => ???
      case IASTBinaryExpression.op_binaryOr         => ???
      case IASTBinaryExpression.op_logicalAnd       => ???
      case IASTBinaryExpression.op_logicalOr        => ???
      case IASTBinaryExpression.op_assign           => Operators.assignment
      case IASTBinaryExpression.op_multiplyAssign   => ???
      case IASTBinaryExpression.op_divideAssign     => ???
      case IASTBinaryExpression.op_moduloAssign     => ???
      case IASTBinaryExpression.op_plusAssign       => ???
      case IASTBinaryExpression.op_minusAssign      => ???
      case IASTBinaryExpression.op_shiftLeftAssign  => ???
      case IASTBinaryExpression.op_shiftRightAssign => ???
      case IASTBinaryExpression.op_binaryAndAssign  => ???
      case IASTBinaryExpression.op_binaryXorAssign  => ???
      case IASTBinaryExpression.op_binaryOrAssign   => ???
      case IASTBinaryExpression.op_equals           => ???
      case IASTBinaryExpression.op_notequals        => ???
      case IASTBinaryExpression.op_pmdot            => ???
      case IASTBinaryExpression.op_pmarrow          => ???
      case IASTBinaryExpression.op_max              => ???
      case IASTBinaryExpression.op_min              => ???
      case IASTBinaryExpression.op_ellipses         => ???
    }
    val callNode = newCallNode(bin, op, order)
    val left = astForIASTNode(bin.getOperand1, 1)
    val right = astForIASTNode(bin.getOperand2, 2)
    Ast(callNode)
      .withChild(left)
      .withArgEdge(callNode, left.root.get)
      .withChild(right)
      .withArgEdge(callNode, right.root.get)
  }

  private def astForIASTExpression(expression: IASTExpression, order: Int): Ast = expression match {
    case bin: IASTBinaryExpression => astForIASTBinaryExpression(bin, order)
    case _                         => ???
  }

  private def astsForIASTExpressionStatement(expr: IASTExpressionStatement, order: Int): Seq[Ast] = {
    Seq(astForIASTExpression(expr.getExpression, order))
  }

  private def astsForIASTStatement(statement: IASTStatement, order: Int): Seq[Ast] = {
    statement match {
      // TODO: handle all statement types
      case decl: IASTDeclarationStatement => astsForIASTDeclarationStatement(decl, order)
      case expr: IASTExpressionStatement  => astsForIASTExpressionStatement(expr, order)
      case _                              => Seq()
    }
  }

  private def astForMethodBody(body: Option[IASTStatement], order: Int): Ast = {
    body match {
      case Some(b: IASTCompoundStatement) => astForBlockStatement(b, order)
      case None                           => Ast(NewBlock())
      case _                              => ???
    }
  }

  private def typeForIASTDeclSpecifier(spec: IASTDeclSpecifier): String = {
    spec match {
      case s: CPPASTSimpleDeclSpecifier => s.toString
      case s: CASTSimpleDeclSpecifier   => s.toString
      case _                            => ???
    }
  }

  private def astForParameter(parameter: IASTParameterDeclaration, childNum: Int): Ast = {
    val (name, tpe) = parameter match {
      case _: CPPASTParameterDeclaration | _: CASTParameterDeclaration =>
        val decl = parameter.getDeclarator
        (decl.getName.getRawSignature, typeForIASTDeclSpecifier(parameter.getDeclSpecifier))
      case _ => ???
    }

    val parameterNode = NewMethodParameterIn()
      .name(name)
      .code(parameter.toString)
      .typeFullName(tpe)
      .order(childNum)
      .lineNumber(line(parameter))
      .columnNumber(column(parameter))

    scope(name) = (parameterNode, tpe)

    Ast(parameterNode)
  }

  private def astForIASTDeclaration(decl: IASTDeclaration, order: Int): Ast =
    decl match {
      case functDef: IASTFunctionDefinition => astForIASTFunctionDefinition(functDef, order)
      case _                                => Ast()
    }

  /**
  def astForTypeDecl(typ: TypeDeclaration[_], order: Int): Ast = {
    val baseTypeFullNames = typ
      .asClassOrInterfaceDeclaration()
      .getExtendedTypes
      .asScala
      .map(_.resolve().getQualifiedName)
      .toList

    val typeDecl = NewTypeDecl()
      .name(typ.getNameAsString)
      .fullName(typ.getFullyQualifiedName.asScala.getOrElse(""))
      .inheritsFromTypeFullName(baseTypeFullNames)
      .order(order)
      .filename(filename)
    Ast(typeDecl).withChildren(
      withOrder(typ.getMethods) { (m, order) =>
        astForMethod(m, typ, order)
      }
    )
  }



  def astsForLabeledStatement(stmt: LabeledStmt, order: Int): Seq[Ast] = {
    val jumpTargetAst = Ast(NewJumpTarget(name = stmt.getLabel.toString, order = order))
    val stmtAst = astsForStatement(stmt.getStatement, order = order + 1)
    Seq(jumpTargetAst) ++ stmtAst
  }

  def astForTry(stmt: TryStmt, order: Int): Ast = {
    val tryNode = NewControlStructure(
      controlStructureType = ControlStructureTypes.TRY,
      code = "try",
      order = order
    )
    Ast(tryNode)
  }

  def astForIf(stmt: IfStmt, order: Int): Ast = {
    val ifNode = NewControlStructure(controlStructureType = ControlStructureTypes.IF, order = order)
    val conditionAst = astForExpression(stmt.getCondition, order = 0)
    val stmtAsts = astsForStatement(stmt.getThenStmt, order = 1)

    val ast = Ast(ifNode)
      .withChild(conditionAst)
      .withChildren(stmtAsts)

    conditionAst.root match {
      case Some(r) =>
        ast.withConditionEdge(ifNode, r)
      case None =>
        ast
    }
  }

  def astForWhile(stmt: WhileStmt, order: Int): Ast = {
    val whileNode =
      NewControlStructure(controlStructureType = ControlStructureTypes.WHILE, order = order)
    val conditionAst = astForExpression(stmt.getCondition, order = 0)
    val stmtAsts = astsForStatement(stmt.getBody, order = 1)
    val ast = Ast(whileNode)
      .withChild(conditionAst)
      .withChildren(stmtAsts)
    conditionAst.root match {
      case Some(r) =>
        ast.withConditionEdge(whileNode, r)
      case None =>
        ast
    }
  }

  def astForDo(stmt: DoStmt, order: Int): Ast = {
    val doNode =
      NewControlStructure(controlStructureType = ControlStructureTypes.DO, order = order)
    val conditionAst = astForExpression(stmt.getCondition, order = 0)
    val stmtAsts = astsForStatement(stmt.getBody, order = 1)
    val ast = Ast(doNode)
      .withChild(conditionAst)
      .withChildren(stmtAsts)
    conditionAst.root match {
      case Some(r) =>
        ast.withConditionEdge(doNode, r)
      case None =>
        ast
    }
  }

  def astForBreakStatement(stmt: BreakStmt, order: Int): Ast = {
    val node = NewControlStructure(
      controlStructureType = ControlStructureTypes.BREAK,
      lineNumber = line(stmt),
      columnNumber = column(stmt),
      code = stmt.toString,
      order = order
    )
    Ast(node)
  }

  def astForContinueStatement(stmt: ContinueStmt, order: Int): Ast = {
    val node = NewControlStructure(
      controlStructureType = ControlStructureTypes.CONTINUE,
      lineNumber = line(stmt),
      columnNumber = column(stmt),
      code = stmt.toString,
      order = order
    )
    Ast(node)
  }

  def astForFor(stmt: ForStmt, order: Int): Ast = {
    val forNode =
      NewControlStructure(controlStructureType = ControlStructureTypes.FOR, order = order)
    val initAsts = withOrder(stmt.getInitialization) { (s, o) =>
      astForExpression(s, o)
    }
    val compareAst = stmt.getCompare.asScala.toList
      .map(x => astForExpression(x, order + initAsts.size + 1))
      .headOption
    val updateAsts = withOrder(stmt.getUpdate) { (s, o) =>
      astForExpression(s, o + initAsts.size + compareAst.size)
    }
    val stmtAst =
      astsForStatement(stmt.getBody, initAsts.size + compareAst.size + updateAsts.size + 1)

    val ast = Ast(forNode)
      .withChildren(initAsts)
      .withChildren(compareAst.toList)
      .withChildren(updateAsts)
      .withChildren(stmtAst)

    compareAst.flatMap(_.root) match {
      case Some(c) =>
        ast.withConditionEdge(forNode, c)
      case None => ast
    }
  }

  def astForSwitchStatement(stmt: SwitchStmt, order: Int): Ast = {
    val switchNode =
      NewControlStructure(
        controlStructureType = ControlStructureTypes.SWITCH,
        order = order,
        code = s"switch(${stmt.getSelector.toString})"
      )
    val entryAsts = withOrder(stmt.getEntries) { (e, order) =>
      astForSwitchEntry(e, order)
    }.flatten
    Ast(switchNode).withChildren(entryAsts)
  }

  def astForSwitchEntry(entry: SwitchEntry, order: Int): Seq[Ast] = {
    val labelNodes = withOrder(entry.getLabels) { (x, o) =>
      NewJumpTarget(name = x.toString, order = o + order)
    }
    val statementAsts = withOrder(entry.getStatements) { (s, o) =>
      astsForStatement(s, order + o + labelNodes.size)
    }.flatten
    labelNodes.map(x => Ast(x)) ++ statementAsts
  }



  private def astForReturnNode(ret: ReturnStmt, order: Int): Ast = {
    // TODO: Make return node with expression as children
    if (ret.getExpression.isPresent) {
      astForExpression(ret.getExpression.get(), order + 1)
    } else {
      Ast()
    }
  }

  def astForBinaryExpr(stmt: BinaryExpr, order: Int): Ast = {
    val operatorName = stmt.getOperator match {
      case BinaryExpr.Operator.OR                   => Operators.logicalOr
      case BinaryExpr.Operator.AND                  => Operators.logicalAnd
      case BinaryExpr.Operator.BINARY_OR            => Operators.or
      case BinaryExpr.Operator.BINARY_AND           => Operators.and
      case BinaryExpr.Operator.DIVIDE               => Operators.division
      case BinaryExpr.Operator.EQUALS               => Operators.equals
      case BinaryExpr.Operator.GREATER              => Operators.greaterThan
      case BinaryExpr.Operator.GREATER_EQUALS       => Operators.greaterEqualsThan
      case BinaryExpr.Operator.LESS                 => Operators.lessThan
      case BinaryExpr.Operator.LESS_EQUALS          => Operators.lessEqualsThan
      case BinaryExpr.Operator.LEFT_SHIFT           => Operators.shiftLeft
      case BinaryExpr.Operator.SIGNED_RIGHT_SHIFT   => Operators.logicalShiftRight
      case BinaryExpr.Operator.UNSIGNED_RIGHT_SHIFT => Operators.arithmeticShiftRight
      case BinaryExpr.Operator.XOR                  => Operators.xor
      case BinaryExpr.Operator.NOT_EQUALS           => Operators.notEquals
      case BinaryExpr.Operator.PLUS                 => Operators.plus
      case BinaryExpr.Operator.MINUS                => Operators.minus
      case BinaryExpr.Operator.MULTIPLY             => Operators.multiplication
      case BinaryExpr.Operator.REMAINDER            => Operators.modulo
      case _                                        => ""
    }

    val callNode = NewCall(name = operatorName, methodFullName = operatorName, code = stmt.toString)
    Ast(callNode)
      .withChild(astForExpression(stmt.getLeft, 0))
      .withChild(astForExpression(stmt.getRight, 1))
  }



  private def astForMethodCall(call: MethodCallExpr, order: Int = 1): Ast = {

    val callNode = NewCall()
      .name(call.getNameAsString)
      .code(s"${call.getNameAsString}(${call.getArguments.asScala.mkString(", ")})")
      .order(order)
      .argumentIndex(order)

    Try(call.resolve()) match {
      case Success(x) =>
        val signature = s"${x.getReturnType.describe()}(${(for (i <- 0 until x.getNumberOfParams)
          yield x.getParam(i).getType.describe()).mkString(",")})"
        callNode.methodFullName(s"${x.getQualifiedName}:$signature")
        callNode.signature(signature)
      // TODO: Generate AST children here
      case Failure(_) =>
    }

    if (call.getName.getBegin.isPresent) {
      callNode
        .lineNumber(line(call.getName))
        .columnNumber(column(call.getName))
    }
    Ast(callNode)
  }




}
    **/

}
