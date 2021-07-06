package io.shiftleft.c2cpg.passes

import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.{NewFile, NewNamespaceBlock, NewNode}
import io.shiftleft.passes.DiffGraph
import io.shiftleft.semanticcpg.language.types.structure.NamespaceTraversal
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import org.eclipse.cdt.core.dom.ast.{IASTDeclaration, IASTNode, IASTTranslationUnit}

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

  private def astForIASTDeclaration(decl: IASTDeclaration, order: Int): Ast = {
    // TODO: descent from here ...
    println("Order " + order + ": " + decl.getRawSignature)
    Ast()
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

  private def astForMethod(
      methodDeclaration: MethodDeclaration,
      typeDecl: TypeDeclaration[_],
      childNum: Int
  ): Ast = {
    val methodNode = createMethodNode(methodDeclaration, typeDecl, childNum)
    val parameterAsts = withOrder(methodDeclaration.getParameters) { (p, order) =>
      astForParameter(p, order)
    }
    val lastOrder = 2 + parameterAsts.size
    Ast(methodNode)
      .withChildren(parameterAsts)
      .withChild(astForMethodReturn(methodDeclaration))
      .withChild(astForMethodBody(methodDeclaration.getBody.asScala, lastOrder))
  }

  private def astForMethodReturn(methodDeclaration: MethodDeclaration): Ast = {
    val methodReturnNode =
      NewMethodReturn()
        .order(methodDeclaration.getParameters.size + 2)
        .typeFullName(methodDeclaration.getType.resolve().describe())
        .code(methodDeclaration.getTypeAsString)
        .lineNumber(line(methodDeclaration.getType))
    Ast(methodReturnNode)
  }

  private def createMethodNode(
      methodDeclaration: MethodDeclaration,
      typeDecl: TypeDeclaration[_],
      childNum: Int
  ) = {
    val fullName = methodFullName(typeDecl, methodDeclaration)
    val code = methodDeclaration.getDeclarationAsString().trim
    val methodNode = NewMethod()
      .name(methodDeclaration.getNameAsString)
      .fullName(fullName)
      .code(code)
      .signature(methodDeclaration.getTypeAsString + paramListSignature(methodDeclaration))
      .isExternal(false)
      .order(childNum)
      .filename(filename)
      .lineNumber(line(methodDeclaration))
    methodNode
  }

  private def astForMethodBody(body: Option[BlockStmt], order: Int): Ast = {
    body match {
      case Some(b) => astForBlockStatement(b, order)
      case None =>
        val blockNode = NewBlock()
        Ast(blockNode)
    }
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

  private def astsForStatement(statement: Statement, order: Int): Seq[Ast] = {
    statement match {
      case x: AssertStmt                        => Seq() // TODO: translate to Call
      case x: BlockStmt                         => Seq(astForBlockStatement(x, order))
      case x: BreakStmt                         => Seq(astForBreakStatement(x, order))
      case x: ContinueStmt                      => Seq(astForContinueStatement(x, order))
      case x: DoStmt                            => Seq(astForDo(x, order))
      case x: EmptyStmt                         => Seq()
      case x: ExplicitConstructorInvocationStmt => Seq() // TODO: translate to Call
      case x: ExpressionStmt                    => Seq(astForExpression(x.getExpression, order))
      case x: ForEachStmt                       => Seq() // TODO: translate to For
      case x: ForStmt                           => Seq(astForFor(x, order))
      case x: IfStmt                            => Seq(astForIf(x, order))
      case x: LabeledStmt                       => astsForLabeledStatement(x, order)
      case x: LocalClassDeclarationStmt         => Seq()
      case x: LocalRecordDeclarationStmt        => Seq()
      case x: ReturnStmt                        => Seq(astForReturnNode(x, order))
      case x: SwitchStmt                        => Seq(astForSwitchStatement(x, order))
      case x: SynchronizedStmt                  => Seq()
      case x: ThrowStmt                         => Seq()
      case x: TryStmt                           => Seq(astForTry(x, order))
      case x: UnparsableStmt                    => Seq()
      case x: WhileStmt                         => Seq(astForWhile(x, order))
      case x: YieldStmt                         => Seq()
      case _                                    => Seq()
    }
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

  private def astForBlockStatement(stmt: BlockStmt, order: Int): Ast = {
    val block = NewBlock(order = order, lineNumber = line(stmt), columnNumber = column(stmt))
    Ast(block).withChildren(
      withOrder(stmt.getStatements) { (x, order) =>
        astsForStatement(x, order)
      }.flatten
    )
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

  private def astForParameter(parameter: Parameter, childNum: Int): Ast = {
    val parameterNode = NewMethodParameterIn()
      .name(parameter.getName.toString)
      .code(parameter.toString)
      .typeFullName(parameter.getType.resolve().describe())
      .order(childNum)
      .lineNumber(line(parameter))
      .columnNumber(column(parameter))
    Ast(parameterNode)
  }

  private def methodFullName(
      typeDecl: TypeDeclaration[_],
      methodDeclaration: MethodDeclaration
  ): String = {
    val typeName = typeDecl.getFullyQualifiedName.asScala.getOrElse("")
    val returnType = methodDeclaration.getTypeAsString
    val methodName = methodDeclaration.getNameAsString
    s"$typeName.$methodName:$returnType${paramListSignature(methodDeclaration)}"
  }

  private def paramListSignature(methodDeclaration: MethodDeclaration) = {
    val paramTypes = methodDeclaration.getParameters.asScala.map(_.getType.resolve().describe())
    "(" + paramTypes.mkString(",") + ")"
  }
}

object AstCreator {
  def line(node: Node): Option[Integer] = {
    node.getBegin.map(x => Integer.valueOf(x.line)).asScala
  }

  def column(node: Node): Option[Integer] = {
    node.getBegin.map(x => Integer.valueOf(x.column)).asScala
  }
    **/

}
