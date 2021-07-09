package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.Defines
import io.shiftleft.codepropertygraph.generated.nodes.{
  NewBlock,
  NewCall,
  NewControlStructure,
  NewFile,
  NewIdentifier,
  NewJumpTarget,
  NewLiteral,
  NewLocal,
  NewMember,
  NewMethod,
  NewMethodParameterIn,
  NewMethodReturn,
  NewNamespaceBlock,
  NewNode,
  NewReturn
}
import io.shiftleft.codepropertygraph.generated.{
  ControlStructureTypes,
  DispatchTypes,
  EdgeTypes,
  EvaluationStrategies,
  Operators
}
import io.shiftleft.passes.DiffGraph
import io.shiftleft.semanticcpg.language.types.structure.NamespaceTraversal
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.x2cpg.Ast
import org.eclipse.cdt.core.dom.ast.{
  IASTBinaryExpression,
  IASTBreakStatement,
  IASTCompositeTypeSpecifier,
  IASTCompoundStatement,
  IASTContinueStatement,
  IASTDeclSpecifier,
  IASTDeclaration,
  IASTDeclarationStatement,
  IASTDeclarator,
  IASTEqualsInitializer,
  IASTExpression,
  IASTExpressionList,
  IASTExpressionStatement,
  IASTForStatement,
  IASTFunctionCallExpression,
  IASTFunctionDefinition,
  IASTGotoStatement,
  IASTIdExpression,
  IASTIfStatement,
  IASTInitializer,
  IASTLabelStatement,
  IASTLiteralExpression,
  IASTName,
  IASTNamedTypeSpecifier,
  IASTNode,
  IASTNullStatement,
  IASTParameterDeclaration,
  IASTReturnStatement,
  IASTSimpleDeclSpecifier,
  IASTSimpleDeclaration,
  IASTStandardFunctionDeclarator,
  IASTStatement,
  IASTTranslationUnit,
  IASTTypeIdExpression,
  IASTUnaryExpression,
  IASTWhileStatement,
  IPointerType
}
import org.slf4j.LoggerFactory

object AstCreator {

  private val logger = LoggerFactory.getLogger(classOf[AstCreator])

  def line(node: IASTNode): Option[Integer] = {
    Some(node.getFileLocation.getStartingLineNumber)
  }

  def lineEnd(node: IASTNode): Option[Integer] = {
    Some(node.getFileLocation.getEndingLineNumber)
  }

  def column(node: IASTNode): Option[Integer] = {
    Some(node.getFileLocation.getNodeOffset)
  }

  def columnEnd(node: IASTNode): Option[Integer] = {
    Some(node.getNodeLocations.last.getNodeOffset)
  }

}

class AstCreator(filename: String, global: Global) {

  import AstCreator._

  private val scope = new Scope[String, (NewNode, String), NewNode]()

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

  private def withOrder[T <: IASTNode, X](nodes: Seq[T])(f: (T, Int) => X): Seq[X] =
    nodes.zipWithIndex.map {
      case (x, i) =>
        f(x, i + 1)
    }

  private def withOrder[T <: IASTNode, X](nodes: Array[T])(f: (T, Int) => X): Seq[X] =
    nodes.toIndexedSeq.zipWithIndex.map {
      case (x, i) =>
        f(x, i + 1)
    }

  private def registerType(typeName: String): String = {
    global.usedTypes.put(typeName, true)
    typeName
  }

  private def notHandledYetSeq[T](node: IASTNode): Seq[T] = {
    val text = s"Node '${node.getClass.getSimpleName}' not handled yet. Code: '${node.getRawSignature}'"
    println(text)
    logger.warn(text)
    Seq.empty
  }

  private def notHandledYet(node: IASTNode): Ast = {
    val text = s"Node '${node.getClass.getSimpleName}' not handled yet. Code: '${node.getRawSignature}'"
    println(text)
    logger.warn(text)
    Ast()
  }

  private def parentIsClassDef(node: IASTNode): Boolean = Option(node.getParent) match {
    case Some(_: IASTCompositeTypeSpecifier) => true
    case _                                   => false
  }

  private def params(functDef: IASTFunctionDefinition): Seq[IASTParameterDeclaration] = functDef.getDeclarator match {
    case decl: IASTStandardFunctionDeclarator => decl.getParameters.toIndexedSeq
    case _                                    => notHandledYetSeq(functDef)
  }

  private def paramListSignature(functDef: IASTFunctionDefinition, includeParamNames: Boolean): String = {
    val elements =
      if (!includeParamNames) params(functDef).map(p => typeForDeclSpecifier(p.getDeclSpecifier))
      else
        params(functDef).map(p => s"${typeForDeclSpecifier(p.getDeclSpecifier)} ${p.getDeclarator.getName.toString}")
    "(" + elements.mkString(",") + ")"
  }

  private def newCallNode(astNode: IASTNode, methodName: String, dispatchType: String, order: Int): NewCall =
    NewCall()
      .name(methodName)
      .dispatchType(dispatchType)
      .methodFullName(methodName)
      .code(astNode.getRawSignature)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(astNode))
      .columnNumber(column(astNode))

  private def newControlStructureNode(node: IASTNode,
                                      controlStructureType: String,
                                      code: String,
                                      order: Int): NewControlStructure =
    NewControlStructure()
      .parserTypeName(node.getClass.getSimpleName)
      .controlStructureType(controlStructureType)
      .code(code)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(node))
      .columnNumber(column(node))

  private def astForFile(parserResult: IASTTranslationUnit): Ast =
    Ast(NewFile(name = filename, order = 0))
      .withChild(
        astForTranslationUnit(parserResult)
          .withChildren(withOrder(parserResult.getDeclarations) { (decl, order) =>
            astForDeclaration(decl, order)
          })
      )

  private def astForTranslationUnit(iASTTranslationUnit: IASTTranslationUnit): Ast = {
    val absolutePath = new java.io.File(iASTTranslationUnit.getFilePath).toPath.toAbsolutePath.normalize().toString
    val namespaceBlock = NewNamespaceBlock()
      .name(NamespaceTraversal.globalNamespaceName)
      .fullName(MetaDataPass.getGlobalNamespaceBlockFullName(Some(absolutePath)))
      .filename(absolutePath)
      .order(1)
    Ast(namespaceBlock)
  }

  private def astForLiteral(lit: IASTLiteralExpression, order: Int): Ast = {
    val tpe = lit.getExpressionType.toString
    val litNode = NewLiteral()
      .typeFullName(registerType(tpe))
      .code(lit.getRawSignature)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(lit))
      .columnNumber(column(lit))
    Ast(litNode)
  }

  private def astForIdentifier(ident: IASTNode, order: Int): Ast = {
    val identifierName = ident.toString

    val variableOption = scope.lookupVariable(identifierName)
    val identifierTypeName = variableOption match {
      case Some((_, variableTypeName)) =>
        variableTypeName
      case None =>
        Defines.anyTypeName
    }

    val cpgIdentifier = NewIdentifier()
      .name(identifierName)
      .typeFullName(registerType(identifierTypeName))
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

  private def astForNode(node: IASTNode, order: Int): Ast = {
    node match {
      case name: IASTName          => astForIdentifier(name, order)
      case decl: IASTDeclSpecifier => astForIdentifier(decl, order)
      case expr: IASTExpression    => astForExpression(expr, order)
      case _                       => notHandledYet(node)
    }
  }

  private def astForFunctionDefinition(functDef: IASTFunctionDefinition, order: Int): Ast = {
    val methodNode = createMethodNode(functDef, order)

    scope.pushNewScope(methodNode)

    val parameterAsts = withOrder(params(functDef)) { (p, order) =>
      astForParameter(p, order)
    }

    val lastOrder = 2 + parameterAsts.size
    val r = Ast(methodNode)
      .withChildren(parameterAsts)
      .withChild(astForMethodBody(Option(functDef.getBody), lastOrder))
      .withChild(astForMethodReturn(functDef, lastOrder, typeForDeclSpecifier(functDef.getDeclSpecifier)))

    scope.popScope()
    r
  }

  private def astForMethodReturn(functDef: IASTFunctionDefinition, order: Int, tpe: String): Ast = {
    val methodReturnNode =
      NewMethodReturn()
        .order(order)
        .typeFullName(registerType(tpe))
        .code(tpe)
        .evaluationStrategy(EvaluationStrategies.BY_VALUE)
        .lineNumber(line(functDef))
        .columnNumber(column(functDef))
    Ast(methodReturnNode)
  }

  private def createMethodNode(
      functDef: IASTFunctionDefinition,
      childNum: Int
  ) = {
    val returnType = typeForDeclSpecifier(functDef.getDeclSpecifier)
    val name = functDef.getDeclarator.getName.toString
    val signature = returnType + " " + name + " " + paramListSignature(functDef, includeParamNames = false)
    val code = returnType + " " + name + " " + paramListSignature(functDef, includeParamNames = true)
    val methodNode = NewMethod()
      .name(functDef.getDeclarator.getName.toString)
      .code(code)
      .isExternal(false)
      .fullName(name)
      .lineNumber(line(functDef))
      .lineNumberEnd(lineEnd(functDef))
      .columnNumber(column(functDef))
      .columnNumberEnd(columnEnd(functDef))
      .signature(signature)
      .filename(filename)
      .order(childNum)
    methodNode
  }

  private def astForBlockStatement(parent: Option[NewNode], stmt: IASTCompoundStatement, order: Int): Ast = {
    parent match {
      case Some(p) =>
        Ast(p).withChildren(
          withOrder(stmt.getStatements) { (x, order) =>
            astsForIASTStatement(x, order)
          }.flatten
        )
      case None =>
        val block = NewBlock()
          .order(order)
          .argumentIndex(order)
          .typeFullName(registerType(Defines.voidTypeName))
          .lineNumber(line(stmt))
          .columnNumber(column(stmt))

        scope.pushNewScope(block)
        val r = Ast(block).withChildren(
          withOrder(stmt.getStatements) { (x, order) =>
            astsForIASTStatement(x, order)
          }.flatten
        )
        scope.popScope()
        r
    }
  }

  private def astForInitializer(declarator: IASTDeclarator, init: IASTInitializer, order: Int): Ast =
    init match {
      case i: IASTEqualsInitializer =>
        val operatorName = Operators.assignment
        val callNode = newCallNode(declarator, operatorName, DispatchTypes.STATIC_DISPATCH, order)
        val left = astForNode(declarator.getName, 1)
        val right = astForNode(i.getInitializerClause, 2)
        Ast(callNode)
          .withChild(left)
          .withArgEdge(callNode, left.root.get)
          .withChild(right)
          .withArgEdge(callNode, right.root.get)
      case _ => notHandledYet(init)
    }

  private def astForDeclarator(declaration: IASTSimpleDeclaration, declarator: IASTDeclarator, order: Int): Ast = {
    val declTypeName = typeForDeclSpecifier(declaration.getDeclSpecifier)
    val name = declarator.getName.toString
    val newNode = if (parentIsClassDef(declaration.getParent)) {
      NewMember()
        .code(declaration.getRawSignature)
        .name(name)
        .typeFullName(registerType(declTypeName))
        .order(order)
    } else {
      NewLocal()
        .code(name)
        .name(name)
        .typeFullName(registerType(declTypeName))
        .order(order)
    }
    scope.addToScope(name, (newNode, declTypeName))
    Ast(newNode)
  }

  private def astsForDeclarationStatement(decl: IASTDeclarationStatement, order: Int): Seq[Ast] =
    decl.getDeclaration match {
      case decl: IASTSimpleDeclaration =>
        val locals =
          withOrder(decl.getDeclarators) { (d, o) =>
            astForDeclarator(decl, d, order + o)
          }
        val calls =
          withOrder(decl.getDeclarators.filter(_.getInitializer != null)) { (d, o) =>
            astForInitializer(d, d.getInitializer, locals.size + o)
          }
        locals ++ calls
      case decl => notHandledYetSeq(decl)
    }

  private def astForBinaryExpression(bin: IASTBinaryExpression, order: Int): Ast = {
    val op = bin.getOperator match {
      case IASTBinaryExpression.op_multiply         => Operators.multiplication
      case IASTBinaryExpression.op_divide           => Operators.division
      case IASTBinaryExpression.op_modulo           => Operators.modulo
      case IASTBinaryExpression.op_plus             => Operators.addition
      case IASTBinaryExpression.op_minus            => Operators.minus
      case IASTBinaryExpression.op_shiftLeft        => Operators.shiftLeft
      case IASTBinaryExpression.op_shiftRight       => Operators.arithmeticShiftRight
      case IASTBinaryExpression.op_lessThan         => Operators.lessThan
      case IASTBinaryExpression.op_greaterThan      => Operators.greaterThan
      case IASTBinaryExpression.op_lessEqual        => Operators.lessEqualsThan
      case IASTBinaryExpression.op_greaterEqual     => Operators.greaterEqualsThan
      case IASTBinaryExpression.op_binaryAnd        => Operators.and
      case IASTBinaryExpression.op_binaryXor        => Operators.xor
      case IASTBinaryExpression.op_binaryOr         => Operators.or
      case IASTBinaryExpression.op_logicalAnd       => Operators.logicalAnd
      case IASTBinaryExpression.op_logicalOr        => Operators.logicalOr
      case IASTBinaryExpression.op_assign           => Operators.assignment
      case IASTBinaryExpression.op_multiplyAssign   => Operators.assignmentMultiplication
      case IASTBinaryExpression.op_divideAssign     => Operators.assignmentDivision
      case IASTBinaryExpression.op_moduloAssign     => Operators.assignmentModulo
      case IASTBinaryExpression.op_plusAssign       => Operators.assignmentPlus
      case IASTBinaryExpression.op_minusAssign      => Operators.assignmentMinus
      case IASTBinaryExpression.op_shiftLeftAssign  => Operators.assignmentShiftLeft
      case IASTBinaryExpression.op_shiftRightAssign => Operators.assignmentArithmeticShiftRight
      case IASTBinaryExpression.op_binaryAndAssign  => Operators.assignmentAnd
      case IASTBinaryExpression.op_binaryXorAssign  => Operators.assignmentXor
      case IASTBinaryExpression.op_binaryOrAssign   => Operators.assignmentOr
      case IASTBinaryExpression.op_equals           => Operators.equals
      case IASTBinaryExpression.op_notequals        => Operators.notEquals
      case IASTBinaryExpression.op_pmdot            => Operators.indirectFieldAccess
      case IASTBinaryExpression.op_pmarrow          => Operators.indirectFieldAccess
      case IASTBinaryExpression.op_max              => "<operator>.max"
      case IASTBinaryExpression.op_min              => "<operator>.min"
      case IASTBinaryExpression.op_ellipses         => "<operator>.op_ellipses"
      case _                                        => "<operator>.unknown"
    }
    val callNode = newCallNode(bin, op, DispatchTypes.STATIC_DISPATCH, order)
    val left = astForExpression(bin.getOperand1, 1)
    val right = astForExpression(bin.getOperand2, 2)
    Ast(callNode)
      .withChild(left)
      .withArgEdge(callNode, left.root.get)
      .withChild(right)
      .withArgEdge(callNode, right.root.get)
  }

  private def astForExpressionList(exprList: IASTExpressionList, order: Int): Ast =
    Ast().withChildren(exprList.getExpressions.toIndexedSeq.map(astForExpression(_, order)))

  private def astForCall(call: IASTFunctionCallExpression, order: Int): Ast = {
    val targetMethodName = call.getFunctionNameExpression.toString
    val (dispatchType, receiver) = call.getExpressionType match {
      case _: IPointerType =>
        (DispatchTypes.DYNAMIC_DISPATCH, Some(astForExpression(call.getFunctionNameExpression, 0)))
      case _ => (DispatchTypes.STATIC_DISPATCH, None)
    }
    val cpgCall = newCallNode(call, targetMethodName, dispatchType, order)
    val orderInc = if (receiver.isDefined) 1 else 0
    val args = withOrder(call.getArguments) { case (a, o) => astForNode(a, orderInc + o) }

    val ast = Ast(cpgCall).withChildren(args)
    val refAst = receiver match {
      case Some(r) if r.root.isDefined => ast.withRefEdge(cpgCall, r.root.get).withArgEdge(cpgCall, r.root.get)
      case _                           => ast
    }
    args.collect { case a if a.root.isDefined => refAst.withArgEdge(cpgCall, a.root.get) }.last
  }

  private def astForUnaryExpression(unary: IASTUnaryExpression, order: Int): Ast = {
    val operatorMethod = unary.getOperator match {
      case IASTUnaryExpression.op_prefixIncr  => Operators.preIncrement
      case IASTUnaryExpression.op_prefixDecr  => Operators.preDecrement
      case IASTUnaryExpression.op_plus        => Operators.plus
      case IASTUnaryExpression.op_minus       => Operators.minus
      case IASTUnaryExpression.op_star        => Operators.indirection
      case IASTUnaryExpression.op_amper       => Operators.addressOf
      case IASTUnaryExpression.op_tilde       => Operators.not
      case IASTUnaryExpression.op_not         => Operators.logicalNot
      case IASTUnaryExpression.op_sizeof      => Operators.sizeOf
      case IASTUnaryExpression.op_postFixIncr => Operators.postIncrement
      case IASTUnaryExpression.op_postFixDecr => Operators.postDecrement
      case IASTUnaryExpression.op_throw       => "operator.<throw>"
      case IASTUnaryExpression.op_typeid      => "operators.<typeOf>"
      case _                                  => "operators.<unknown>"
    }

    val cpgUnary = newCallNode(unary, operatorMethod, DispatchTypes.STATIC_DISPATCH, order)
    val operandExpr = unary.getOperand match {
      // special handling for operand expression in brackets - we simply ignore the brackets
      case opExpr: IASTUnaryExpression if opExpr.getOperator == IASTUnaryExpression.op_bracketedPrimary =>
        opExpr.getOperand
      case opExpr => opExpr
    }

    val operand = astForExpression(operandExpr, 1)

    val ast = Ast(cpgUnary).withChild(operand)
    operand.root match {
      case Some(op) => ast.withArgEdge(cpgUnary, op)
      case None     => ast
    }
  }

  private def astForTypeIdExpression(typeId: IASTTypeIdExpression, order: Int): Ast = {
    typeId.getOperator match {
      case op
          if op == IASTTypeIdExpression.op_sizeof ||
            op == IASTTypeIdExpression.op_typeid ||
            op == IASTTypeIdExpression.op_alignof ||
            op == IASTTypeIdExpression.op_typeof =>
        val call = newCallNode(typeId, Operators.sizeOf, DispatchTypes.STATIC_DISPATCH, order)
        val arg = astForNode(typeId.getTypeId.getDeclSpecifier, 1)
        val ast = Ast(call).withChild(arg)
        arg.root match {
          case Some(r) => ast.withArgEdge(call, r)
          case _       => ast
        }
      case _ => notHandledYet(typeId)
    }
  }

  private def astForExpression(expression: IASTExpression, order: Int): Ast = expression match {
    case lit: IASTLiteralExpression       => astForLiteral(lit, order)
    case un: IASTUnaryExpression          => astForUnaryExpression(un, order)
    case bin: IASTBinaryExpression        => astForBinaryExpression(bin, order)
    case exprList: IASTExpressionList     => astForExpressionList(exprList, order)
    case ident: IASTIdExpression          => astForIdentifier(ident, order)
    case call: IASTFunctionCallExpression => astForCall(call, order)
    case typeId: IASTTypeIdExpression     => astForTypeIdExpression(typeId, order)
    case _                                => notHandledYet(expression)
  }

  private def astForReturnStatement(ret: IASTReturnStatement, order: Int): Ast = {
    val cpgReturn = NewReturn()
      .code(ret.getRawSignature)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(ret))
      .columnNumber(column(ret))
    val expr = Option(ret.getReturnValue).map(astForExpression(_, 1)).getOrElse(Ast())
    Ast(cpgReturn).withChild(expr)
  }

  private def astForBreakStatement(br: IASTBreakStatement, order: Int): Ast = {
    Ast(newControlStructureNode(br, ControlStructureTypes.BREAK, br.getRawSignature, order))
  }

  private def astForContinueStatement(cont: IASTContinueStatement, order: Int): Ast = {
    Ast(newControlStructureNode(cont, ControlStructureTypes.CONTINUE, cont.getRawSignature, order))
  }

  private def astForGotoStatement(goto: IASTGotoStatement, order: Int): Ast = {
    Ast(newControlStructureNode(goto, ControlStructureTypes.GOTO, goto.getRawSignature, order))
  }

  private def astsForLabelStatement(label: IASTLabelStatement, order: Int): Seq[Ast] = {
    val cpgLabel = NewJumpTarget()
      .parserTypeName(label.getClass.getSimpleName)
      .name(label.getName.toString)
      .code(label.getRawSignature)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(label))
      .columnNumber(column(label))
    val nestedStmts = Option(label.getNestedStatement).map(astsForIASTStatement(_, order + 1)).getOrElse(Seq.empty)
    Ast(cpgLabel) +: nestedStmts
  }

  private def astsForIASTStatement(statement: IASTStatement, order: Int): Seq[Ast] = {
    statement match {
      case expr: IASTExpressionStatement  => Seq(astForExpression(expr.getExpression, order))
      case block: IASTCompoundStatement   => Seq(astForBlockStatement(None, block, order))
      case ifStmt: IASTIfStatement        => Seq(astForIf(ifStmt, order))
      case whileStmt: IASTWhileStatement  => Seq(astForWhile(whileStmt, order))
      case forStmt: IASTForStatement      => Seq(astForFor(forStmt, order))
      case ret: IASTReturnStatement       => Seq(astForReturnStatement(ret, order))
      case br: IASTBreakStatement         => Seq(astForBreakStatement(br, order))
      case cont: IASTContinueStatement    => Seq(astForContinueStatement(cont, order))
      case goto: IASTGotoStatement        => Seq(astForGotoStatement(goto, order))
      case decl: IASTDeclarationStatement => astsForDeclarationStatement(decl, order)
      case label: IASTLabelStatement      => astsForLabelStatement(label, order)
      case _: IASTNullStatement           => Seq.empty
      case _                              => notHandledYetSeq(statement)
    }
  }

  private def astForMethodBody(body: Option[IASTStatement], order: Int): Ast = {
    body match {
      case Some(b: IASTCompoundStatement) => astForBlockStatement(None, b, order)
      case None                           => Ast(NewBlock())
      case Some(b)                        => notHandledYet(b)
    }
  }

  private def typeForDeclSpecifier(spec: IASTDeclSpecifier): String = {
    spec match {
      case s: IASTSimpleDeclSpecifier => s.toString
      case s: IASTNamedTypeSpecifier  => s.getName.toString
      // TODO: handle other types of IASTDeclSpecifier
      case _ => Defines.anyTypeName
    }
  }

  private def astForParameter(parameter: IASTParameterDeclaration, childNum: Int): Ast = {
    val decl = parameter.getDeclarator
    val name = decl.getName.getRawSignature
    val tpe = typeForDeclSpecifier(parameter.getDeclSpecifier)

    val parameterNode = NewMethodParameterIn()
      .name(name)
      .code(s"$tpe $name")
      .typeFullName(registerType(tpe))
      .order(childNum)
      .evaluationStrategy(EvaluationStrategies.BY_VALUE)
      .lineNumber(line(parameter))
      .columnNumber(column(parameter))

    scope.addToScope(name, (parameterNode, tpe))

    Ast(parameterNode)
  }

  private def astForDeclaration(decl: IASTDeclaration, order: Int): Ast = decl match {
    case functDef: IASTFunctionDefinition                                         => astForFunctionDefinition(functDef, order)
    case declaration: IASTSimpleDeclaration if declaration.getDeclarators.isEmpty => Ast()
    case _                                                                        => notHandledYet(decl)
  }

  private def astForFor(stmt: IASTForStatement, order: Int): Ast = {
    val codeInit = Option(stmt.getInitializerStatement).map(_.getRawSignature).getOrElse("")
    val codeCond = Option(stmt.getConditionExpression).map(_.getRawSignature).getOrElse("")
    val codeIter = Option(stmt.getIterationExpression).map(_.getRawSignature).getOrElse("")

    val code = s"for ($codeInit$codeCond;$codeIter)"

    val forNode = newControlStructureNode(stmt, ControlStructureTypes.FOR, code, order)

    val initAsts =
      Option(stmt.getInitializerStatement).map(astsForIASTStatement(_, order = 1)).getOrElse(Seq.empty)

    val continuedOrder = Math.max(initAsts.size, 1)

    val compareAst =
      Option(stmt.getConditionExpression)
        .map(astForExpression(_, order = continuedOrder + 1))
        .getOrElse(Ast())

    val updateAst =
      Option(stmt.getIterationExpression)
        .map(astForExpression(_, order = continuedOrder + 2))
        .getOrElse(Ast())

    val stmtAst = Option(stmt.getBody)
      .map(astsForIASTStatement(_, order = continuedOrder + 3))
      .getOrElse(Seq.empty)

    val ast = Ast(forNode)
      .withChildren(initAsts)
      .withChild(compareAst)
      .withChild(updateAst)
      .withChildren(stmtAst)

    compareAst.root match {
      case Some(c) =>
        ast.withConditionEdge(forNode, c)
      case None => ast
    }
  }

  private def astForWhile(stmt: IASTWhileStatement, order: Int): Ast = {
    val code = Option(stmt.getCondition).map(c => s"while (${c.getRawSignature})").getOrElse("while ()")

    val whileNode = newControlStructureNode(stmt, ControlStructureTypes.WHILE, code, order)

    val conditionAst = Option(stmt.getCondition).map(astForExpression(_, order = 1)).getOrElse(Ast())
    val stmtAsts = Option(stmt.getBody).map(astsForIASTStatement(_, order = 2)).getOrElse(Seq.empty)

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

  def astForIf(stmt: IASTIfStatement, order: Int): Ast = {
    val code = Option(stmt.getConditionExpression).map(c => s"if (${c.getRawSignature})").getOrElse("if ()")

    val ifNode = newControlStructureNode(stmt, ControlStructureTypes.IF, code, order)

    val conditionAst = Option(stmt.getConditionExpression).map(astForExpression(_, order = 1)).getOrElse(Ast())
    val stmtAsts = Option(stmt.getThenClause).map(astsForIASTStatement(_, order = 2)).getOrElse(Seq.empty)
    val elseAsts = Option(stmt.getElseClause).map(astsForIASTStatement(_, order = 3)).getOrElse(Seq.empty)

    val ast = Ast(ifNode)
      .withChild(conditionAst)
      .withChildren(stmtAsts)
      .withChildren(elseAsts)

    conditionAst.root match {
      case Some(r) =>
        ast.withConditionEdge(ifNode, r)
      case None =>
        ast
    }
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
  **/

}
