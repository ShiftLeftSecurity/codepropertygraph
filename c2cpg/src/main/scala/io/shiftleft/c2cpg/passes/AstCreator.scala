package io.shiftleft.c2cpg.passes

import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.{
  NewBlock,
  NewFile,
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
  IASTCompoundStatement,
  IASTDeclSpecifier,
  IASTDeclaration,
  IASTFunctionDefinition,
  IASTNode,
  IASTParameterDeclaration,
  IASTStatement,
  IASTTranslationUnit
}
import org.eclipse.cdt.internal.core.dom.parser.c.{
  CASTFunctionDeclarator,
  CASTParameterDeclaration,
  CASTSimpleDeclSpecifier
}
import org.eclipse.cdt.internal.core.dom.parser.cpp.{
  CPPASTFunctionDeclarator,
  CPPASTParameterDeclaration,
  CPPASTSimpleDeclSpecifier
}

object AstCreator {

  case class AstEdge(src: NewNode, dst: NewNode)

  object Ast {
    def apply(node: NewNode): Ast = Ast(List(node))

    def apply(): Ast = new Ast(List())
  }

  case class Ast(
      nodes: List[NewNode],
      edges: List[AstEdge] = List(),
      conditionEdges: List[AstEdge] = List()
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
        conditionEdges = conditionEdges ++ other.conditionEdges
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
  }

  private def withOrder[T <: IASTNode, X](nodeList: List[T])(f: (T, Int) => X): Seq[X] =
    nodeList.zipWithIndex.map {
      case (x, i) =>
        f(x, i + 1)
    }

  private def astForFile(parserResult: IASTTranslationUnit): Ast = {
    println(filename)
    println(parserResult.getRawSignature)
    Ast(NewFile(name = filename, order = 0))
      .withChild(
        astForIASTTranslationUnit(parserResult)
          .withChildren(withOrder(parserResult.getDeclarations.toList) { (decl, order) =>
            astForIASTDeclaration(decl, order)
          })
      )
  }

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

  private def astsForIASTStatement(statement: IASTStatement, order: Int): Seq[Ast] = {
    println(s"$statement at order $order")
    statement match {
      // TODO: handle all statement types
      case _ => Seq()
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
    Ast(parameterNode)
  }

  private def astForIASTDeclaration(decl: IASTDeclaration, order: Int): Ast = {
    // TODO: descent from here ...
    println("Order " + order + ": " + decl.getRawSignature)
    decl match {
      case functDef: IASTFunctionDefinition => astForIASTFunctionDefinition(functDef, order)
      case _                                => Ast()
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

  private def astForExpression(expression: Expression, order: Int): Ast = {
    expression match {
      case x: AnnotationExpr          => Ast()
      case x: ArrayAccessExpr         => Ast()
      case x: ArrayInitializerExpr    => Ast()
      case x: AssignExpr              => Ast()
      case x: BinaryExpr              => astForBinaryExpr(x, order)
      case x: CastExpr                => Ast()
      case x: ClassExpr               => Ast()
      case x: ConditionalExpr         => Ast()
      case x: EnclosedExpr            => Ast()
      case x: FieldAccessExpr         => Ast()
      case x: InstanceOfExpr          => Ast()
      case x: LambdaExpr              => Ast()
      case x: LiteralExpr             => Ast()
      case x: MethodCallExpr          => astForMethodCall(x, order)
      case x: MethodReferenceExpr     => Ast()
      case x: NameExpr                => Ast()
      case x: ObjectCreationExpr      => Ast()
      case x: PatternExpr             => Ast()
      case x: SuperExpr               => Ast()
      case x: SwitchExpr              => Ast()
      case x: ThisExpr                => Ast()
      case x: TypeExpr                => Ast()
      case x: UnaryExpr               => Ast()
      case x: VariableDeclarationExpr => Ast()
      case _                          => Ast()
    }
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
