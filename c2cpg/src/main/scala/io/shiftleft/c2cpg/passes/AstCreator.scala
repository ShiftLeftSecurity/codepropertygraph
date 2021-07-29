package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.C2Cpg
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.passes.DiffGraph
import io.shiftleft.semanticcpg.language.types.structure.NamespaceTraversal
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.x2cpg.Ast
import org.eclipse.cdt.core.dom.ast._
import org.eclipse.cdt.core.dom.ast.cpp._
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDeclarator
import org.eclipse.cdt.internal.core.dom.parser.cpp.semantics.EvalBinding
import org.eclipse.cdt.internal.core.dom.parser.cpp.{
  CPPASTFunctionDeclarator,
  CPPASTIdExpression,
  CPPASTQualifiedName,
  CPPASTTypeIdInitializerExpression,
  CPPFunction
}
import org.slf4j.LoggerFactory

import java.io.{BufferedReader, FileInputStream, InputStreamReader}
import scala.annotation.tailrec
import scala.collection.mutable
import scala.jdk.CollectionConverters._

object AstCreator {

  private val logger = LoggerFactory.getLogger(classOf[AstCreator])

  object Defines {
    val anyTypeName = "ANY"
    val voidTypeName = "void"
    val qualifiedNameSeparator = "::"
  }

}

class AstCreator(filename: String, global: Global, config: C2Cpg.Config) {

  import AstCreator._

  private val reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"))
  private val fileLines = reader.lines().iterator().asScala.toSeq.map(_.length)

  private val scope = new Scope[String, (NewNode, String), NewNode]()

  private val usingDeclarationMappings = mutable.HashMap.empty[String, String]

  def createAst(parserResult: IASTTranslationUnit): Iterator[DiffGraph] = {
    val diffGraph = DiffGraph.newBuilder
    Ast.storeInDiffGraph(astForFile(parserResult), diffGraph)
    Iterator(diffGraph.build())
  }

  private def nullSafeFileLocation(node: IASTNode): Option[IASTFileLocation] = Option(node.getFileLocation)

  private def nullSafeLastNodeLocation(node: IASTNode): Option[IASTNodeLocation] =
    Option(node.getNodeLocations).flatMap(_.lastOption)

  private def line(node: IASTNode): Option[Integer] = {
    nullSafeFileLocation(node).map(_.getStartingLineNumber)
  }

  private def lineEnd(node: IASTNode): Option[Integer] = {
    nullSafeFileLocation(node).map(_.getEndingLineNumber)
  }

  private def column(node: IASTNode): Option[Integer] = {
    if (line(node).isEmpty) None
    else {
      val l = line(node).get - 1
      if (l == 0) {
        nullSafeFileLocation(node).map(_.getNodeOffset)
      } else if (nullSafeFileLocation(node).map(_.getNodeOffset).contains(0)) {
        Some(0)
      } else {
        nullSafeFileLocation(node).map(_.getNodeOffset - 1 - fileLines.slice(0, l).sum)
      }
    }
  }

  private def columnEnd(node: IASTNode): Option[Integer] = {
    if (line(node).isEmpty) None
    else {
      val l = line(node).get - 1
      if (l == 0) {
        nullSafeLastNodeLocation(node).map(_.getNodeOffset)
      } else if (nullSafeLastNodeLocation(node).map(_.getNodeOffset).contains(0)) {
        Some(0)
      } else {
        nullSafeLastNodeLocation(node).map(_.getNodeOffset - 1 - fileLines.slice(0, l).sum)
      }
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

  private def notHandledText(node: IASTNode): String =
    s"""Node '${node.getClass.getSimpleName}' not handled yet!
       |  Code: '${node.getRawSignature}'
       |  File: '$filename'
       |  Line: ${line(node).getOrElse(-1)}
       |  """.stripMargin

  private def notHandledYetSeq(node: IASTNode, order: Int): Seq[Ast] = {
    if (!node.isInstanceOf[IASTProblem] && !node.isInstanceOf[IASTProblemHolder]) {
      val text = notHandledText(node)
      logger.warn(text)
    }
    Seq(newUnkown(node, order))
  }

  private def notHandledYet(node: IASTNode, order: Int): Ast = {
    if (!node.isInstanceOf[IASTProblem] && !node.isInstanceOf[IASTProblemHolder]) {
      val text = notHandledText(node)
      logger.warn(text)
    }
    newUnkown(node, order)
  }

  private def nullSafeCode(node: IASTNode): String = {
    Option(node).map(_.getRawSignature).getOrElse("")
  }

  private def nullSafeAst(node: IASTExpression, order: Int): Ast = {
    Option(node).map(astForNode(_, order)).getOrElse(Ast())
  }

  private def nullSafeAst(node: IASTStatement, order: Int): Seq[Ast] = {
    Option(node).map(astsForStatement(_, order)).getOrElse(Seq.empty)
  }

  private def fixQualifiedName(name: String): String =
    name.replace(Defines.qualifiedNameSeparator, ".")

  private def isQualifiedName(name: String): Boolean =
    name.startsWith(Defines.qualifiedNameSeparator)

  private def lastNameOfQualifiedName(name: String): String =
    name.split(Defines.qualifiedNameSeparator).lastOption.getOrElse(name)

  private def fullName(node: IASTNode): String = {
    val qualifiedName = node match {
      case d: CPPASTIdExpression if d.getEvaluation.isInstanceOf[EvalBinding] =>
        val evaluation = d.getEvaluation.asInstanceOf[EvalBinding]
        evaluation.getBinding match {
          case f: CPPFunction if f.getDeclarations != null =>
            usingDeclarationMappings.getOrElse(
              fixQualifiedName(d.getName.toString),
              f.getDeclarations.headOption.map(_.getName.toString).getOrElse(f.getName))
          case f: CPPFunction if f.getDefinition != null =>
            usingDeclarationMappings.getOrElse(fixQualifiedName(d.getName.toString), f.getDefinition.getName.toString)
          case other => other.getName
        }
      case alias: ICPPASTNamespaceAlias => alias.getMappingName.toString
      case namespace: ICPPASTNamespaceDefinition if namespace.getName.getBinding != null =>
        namespace.getName.getBinding.toString
      case namespace: ICPPASTNamespaceDefinition if namespace.getParent.isInstanceOf[ICPPASTNamespaceDefinition] =>
        fullName(namespace.getParent) + "." + namespace.getName.toString
      case namespace: ICPPASTNamespaceDefinition if !namespace.getParent.isInstanceOf[ICPPASTNamespaceDefinition] =>
        namespace.getName.toString
      case cppClass: ICPPASTCompositeTypeSpecifier if cppClass.getName.getBinding.isInstanceOf[ICPPBinding] =>
        ASTTypeUtil.getQualifiedName(cppClass.getName.getBinding.asInstanceOf[ICPPBinding])
      case c: IASTCompositeTypeSpecifier => c.getName.toString
      case f: IASTFunctionDeclarator if f.getParent != null =>
        fullName(f.getParent) + "." + f.getName.toString
      case f: IASTFunctionDeclarator =>
        f.getName.toString
      case f: IASTFunctionDefinition if f.getParent != null =>
        fullName(f.getParent) + "." + f.getDeclarator.getName.toString
      case f: IASTFunctionDefinition =>
        f.getDeclarator.getName.toString
      case d: CPPASTIdExpression            => d.getName.toString
      case _: IASTTranslationUnit           => ""
      case u: IASTUnaryExpression           => u.getOperand.toString
      case other if other.getParent != null => fullName(other.getParent)
      case other                            => notHandledYet(other, -1); ""
    }
    val cleaned = fixQualifiedName(qualifiedName)
    if (cleaned.startsWith(".")) {
      cleaned.substring(1)
    } else cleaned
  }

  private def shortName(node: IASTNode): String = {
    val name = node match {
      case f: ICPPASTFunctionDefinition => lastNameOfQualifiedName(f.getDeclarator.getName.toString)
      case f: IASTFunctionDefinition    => f.getDeclarator.getName.toString
      case f: IASTFunctionDeclarator    => f.getName.toString
      case d: CPPASTIdExpression if d.getEvaluation.isInstanceOf[EvalBinding] =>
        val evaluation = d.getEvaluation.asInstanceOf[EvalBinding]
        evaluation.getBinding match {
          case f: CPPFunction if f.getDeclarations != null =>
            f.getDeclarations.headOption.map(_.getName.toString).getOrElse(f.getName)
          case f: CPPFunction if f.getDefinition != null =>
            f.getDefinition.getName.toString
          case other =>
            other.getName
        }
      case d: CPPASTIdExpression  => lastNameOfQualifiedName(d.getName.toString)
      case u: IASTUnaryExpression => shortName(u.getOperand)
      case other                  => notHandledYet(other, -1); ""
    }
    name
  }

  private def parentIsClassDef(node: IASTNode): Boolean = Option(node.getParent) match {
    case Some(_: IASTCompositeTypeSpecifier) => true
    case _                                   => false
  }

  private def isTypeDef(node: IASTNode): Boolean = node.getRawSignature.startsWith("typedef")

  @tailrec
  private def params(funct: IASTNode): Seq[IASTParameterDeclaration] = funct match {
    case decl: CPPASTFunctionDeclarator       => decl.getParameters.toIndexedSeq
    case decl: CASTFunctionDeclarator         => decl.getParameters.toIndexedSeq
    case decl: IASTStandardFunctionDeclarator => decl.getParameters.toIndexedSeq
    case defn: IASTFunctionDefinition         => params(defn.getDeclarator)
    case other                                => notHandledYet(other, -1); Seq.empty
  }

  private def paramListSignature(func: IASTNode, includeParamNames: Boolean): String = {
    val elements =
      if (!includeParamNames) params(func).map(p => typeForDeclSpecifier(p.getDeclSpecifier))
      else
        params(func).map(p => p.getRawSignature)
    "(" + elements.mkString(",") + ")"
  }

  private def newCallNode(astNode: IASTNode,
                          name: String,
                          fullname: String,
                          dispatchType: String,
                          order: Int): NewCall =
    NewCall()
      .name(name)
      .dispatchType(dispatchType)
      .signature("TODO")
      .methodFullName(fullname)
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

  private def newJumpTarget(node: IASTNode, order: Int): NewJumpTarget = {
    val code = node.getRawSignature
    val name = node match {
      case label: IASTLabelStatement    => label.getName.toString
      case _ if code.startsWith("case") => "case"
      case _                            => "default"
    }
    NewJumpTarget()
      .parserTypeName(node.getClass.getSimpleName)
      .name(name)
      .code(code)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(node))
      .columnNumber(column(node))
  }

  private def astForComment(comment: IASTComment): Ast =
    Ast(NewComment().code(comment.getRawSignature).filename(filename).lineNumber(line(comment)))

  private def astForFile(parserResult: IASTTranslationUnit): Ast = {
    val cpgFile = Ast(NewFile(name = filename, order = 0))
    val translationUnitAst = astForTranslationUnit(parserResult)

    var currOrder = 1
    val declsAsts = parserResult.getDeclarations.flatMap { stmt =>
      val r = astsForDeclaration(stmt, currOrder)
      currOrder = currOrder + r.length
      r
    }.toIndexedSeq

    val ast = cpgFile.withChild(translationUnitAst.withChildren(declsAsts))
    if (config.includeComments) {
      val commentsAsts = parserResult.getComments.map(comment => astForComment(comment)).toIndexedSeq
      ast.withChildren(commentsAsts)
    } else {
      ast
    }
  }

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
    val tpe = ASTTypeUtil.getType(lit.getExpressionType)
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
      case None if ident.getParent.isInstanceOf[IASTDeclarator] =>
        ASTTypeUtil.getNodeType(ident.getParent.asInstanceOf[IASTDeclarator])
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

  private def newUnkown(node: IASTNode, order: Int): Ast =
    Ast(
      NewUnknown()
        .parserTypeName(node.getClass.getSimpleName)
        .code(node.getRawSignature)
        .order(order)
        .argumentIndex(order)
        .lineNumber(line(node))
        .columnNumber(column(node)))

  private def astForInitializerList(l: IASTInitializerList, order: Int): Ast = {
    // TODO: how to represent this?
    newUnkown(l, order)
  }

  private def astForNode(node: IASTNode, order: Int): Ast = {
    node match {
      case id: IASTIdExpression if id.getName.isInstanceOf[CPPASTQualifiedName] =>
        astForQualifiedName(id.getName.asInstanceOf[CPPASTQualifiedName], order)
      case name: IASTName          => astForIdentifier(name, order)
      case decl: IASTDeclSpecifier => astForIdentifier(decl, order)
      case expr: IASTExpression    => astForExpression(expr, order)
      case l: IASTInitializerList  => astForInitializerList(l, order)
      case _                       => notHandledYet(node, order)
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

  private def astForMethodReturn(func: IASTNode, order: Int, tpe: String): Ast = {
    val methodReturnNode =
      NewMethodReturn()
        .order(order)
        .typeFullName(registerType(tpe))
        .code(tpe)
        .evaluationStrategy(EvaluationStrategies.BY_VALUE)
        .lineNumber(line(func))
        .columnNumber(column(func))
    Ast(methodReturnNode)
  }

  private def createMethodNode(
      functDef: IASTFunctionDefinition,
      childNum: Int
  ): NewMethod = {
    val returnType = typeForDeclSpecifier(functDef.getDeclSpecifier)
    val name = shortName(functDef)
    val fullname = fullName(functDef)
    val signature = returnType + " " + fullname + " " + paramListSignature(functDef, includeParamNames = false)
    val code = returnType + " " + name + " " + paramListSignature(functDef, includeParamNames = true)
    val methodNode = NewMethod()
      .name(name)
      .code(code)
      .isExternal(false)
      .fullName(fullname)
      .lineNumber(line(functDef))
      .lineNumberEnd(lineEnd(functDef))
      .columnNumber(column(functDef))
      .columnNumberEnd(columnEnd(functDef))
      .signature(signature)
      .filename(filename)
      .order(childNum)
    methodNode
  }

  private def astForBlockStatement(blockStmt: IASTCompoundStatement, order: Int): Ast = {
    val cpgBlock = NewBlock()
      .order(order)
      .argumentIndex(order)
      .typeFullName(registerType(Defines.voidTypeName))
      .lineNumber(line(blockStmt))
      .columnNumber(column(blockStmt))

    scope.pushNewScope(cpgBlock)
    var currOrder = 1
    val childAsts = blockStmt.getStatements.flatMap { stmt =>
      val r = astsForStatement(stmt, currOrder)
      currOrder = currOrder + r.length
      r
    }

    val blockAst = Ast(cpgBlock).withChildren(childAsts.toIndexedSeq)
    scope.popScope()
    blockAst
  }

  private def astForInitializer(declarator: IASTDeclarator, init: IASTInitializer, order: Int): Ast =
    init match {
      case i: IASTEqualsInitializer =>
        val operatorName = Operators.assignment
        val callNode = newCallNode(declarator, operatorName, operatorName, DispatchTypes.STATIC_DISPATCH, order)
        val left = astForNode(declarator.getName, 1)
        val right = astForNode(i.getInitializerClause, 2)
        val ast = Ast(callNode)
          .withChild(left)
          .withArgEdge(callNode, left.root.get)
          .withChild(right)
        right.root match {
          case Some(value) => ast.withArgEdge(callNode, value)
          case None        => ast
        }
      case i: ICPPASTConstructorInitializer =>
        val name = declarator.getName.toString
        val callNode = newCallNode(declarator, name, name, DispatchTypes.STATIC_DISPATCH, order)
        val args = withOrder(i.getArguments) { case (a, o) => astForNode(a, o) }
        val ast = Ast(callNode).withChildren(args)
        val validArgs = args.collect { case a if a.root.isDefined => a.root.get }
        ast.withArgEdges(callNode, validArgs)
      case _ => notHandledYet(init, order)
    }

  private def astForDeclarator(declaration: IASTSimpleDeclaration, declarator: IASTDeclarator, order: Int): Ast = {
    val declTypeName = typeForDeclSpecifier(declaration.getDeclSpecifier)
    val name = declarator.getName.toString
    declaration match {
      case d if isTypeDef(d) =>
        Ast(
          NewTypeDecl()
            .name(name)
            .fullName(name)
            .isExternal(false)
            .aliasTypeFullName(Some(registerType(declTypeName)))
            .filename(declaration.getContainingFilename)
            .order(order))
      case d if parentIsClassDef(d) =>
        Ast(
          NewMember()
            .code(declarator.getRawSignature)
            .name(name)
            .typeFullName(registerType(declTypeName))
            .order(order))
      case _ if declarator.isInstanceOf[IASTArrayDeclarator] =>
        Ast(
          NewTypeDecl()
            .name(name)
            .fullName(name)
            .isExternal(false)
            .filename(declaration.getContainingFilename)
            .order(order))
      case _ if !scope.isEmpty =>
        val l = NewLocal()
          .code(name)
          .name(name)
          .typeFullName(registerType(declTypeName))
          .order(order)
        scope.addToScope(name, (l, declTypeName))
        Ast(l)
      case _ if scope.isEmpty =>
        Ast(
          NewTypeDecl()
            .name(name)
            .fullName(name)
            .isExternal(false)
            .filename(declaration.getContainingFilename)
            .order(order))
    }
  }

  private def astsForDeclarationStatement(decl: IASTDeclarationStatement, order: Int): Seq[Ast] =
    decl.getDeclaration match {
      case simplDecl: IASTSimpleDeclaration
          if simplDecl.getDeclarators.headOption.exists(_.isInstanceOf[IASTFunctionDeclarator]) =>
        Seq(astForFunctionDeclarator(simplDecl.getDeclarators.head.asInstanceOf[IASTFunctionDeclarator], order))
      case simplDecl: IASTSimpleDeclaration =>
        val locals =
          withOrder(simplDecl.getDeclarators) { (d, o) =>
            astForDeclarator(simplDecl, d, order + o - 1)
          }
        val calls =
          withOrder(simplDecl.getDeclarators.filter(_.getInitializer != null)) { (d, o) =>
            astForInitializer(d, d.getInitializer, locals.size + order + o - 1)
          }
        locals ++ calls
      case s: ICPPASTStaticAssertDeclaration =>
        Seq(astForStaticAssert(s, order))
      case usingDeclaration: ICPPASTUsingDeclaration =>
        handleUsingDeclaration(usingDeclaration)
        Seq.empty
      case decl =>
        notHandledYetSeq(decl, order)
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
    val callNode = newCallNode(bin, op, op, DispatchTypes.STATIC_DISPATCH, order)
    val left = nullSafeAst(bin.getOperand1, 1)
    val right = nullSafeAst(bin.getOperand2, 2)
    var ast = Ast(callNode)
      .withChild(left)
      .withChild(right)
    if (left.root.isDefined) ast = ast.withArgEdge(callNode, left.root.get)
    if (right.root.isDefined) ast = ast.withArgEdge(callNode, right.root.get)
    ast
  }

  private def astForExpressionList(exprList: IASTExpressionList, order: Int): Ast = {
    val b = NewBlock()
      .order(order)
      .argumentIndex(order)
      .typeFullName(registerType(Defines.voidTypeName))
      .lineNumber(line(exprList))
      .columnNumber(column(exprList))
    Ast(b).withChildren(exprList.getExpressions.toIndexedSeq.map(astForExpression(_, order)))
  }

  private def astForCall(call: IASTFunctionCallExpression, order: Int): Ast = {
    // TODO: proper handling of call receiver
    val cpgCall = call.getFunctionNameExpression match {
      case reference: IASTFieldReference => astForFieldReference(reference, order)
      case b: IASTBinaryExpression       => astForBinaryExpression(b, order)
      case unaryExpression: IASTUnaryExpression if unaryExpression.getOperand.isInstanceOf[IASTBinaryExpression] =>
        astForBinaryExpression(unaryExpression.getOperand.asInstanceOf[IASTBinaryExpression], order)
      case unaryExpression: IASTUnaryExpression if unaryExpression.getOperand.isInstanceOf[IASTFieldReference] =>
        astForFieldReference(unaryExpression.getOperand.asInstanceOf[IASTFieldReference], order)
      case unaryExpression: IASTUnaryExpression if unaryExpression.getOperand.isInstanceOf[IASTConditionalExpression] =>
        astForUnaryExpression(unaryExpression, order)
      case _ =>
        val name = shortName(call.getFunctionNameExpression)
        val fullname = fullName(call.getFunctionNameExpression)
        Ast(newCallNode(call, name, fullname, DispatchTypes.STATIC_DISPATCH, order))
    }
    val args = withOrder(call.getArguments) { case (a, o) => astForNode(a, o) }

    val ast = cpgCall.withChildren(args)
    val validArgs = args.collect { case a if a.root.isDefined => a.root.get }
    ast.withArgEdges(cpgCall.root.get, validArgs)
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

    if (unary.getOperator == IASTUnaryExpression.op_bracketedPrimary) {
      astForExpression(unary.getOperand, order)
    } else {
      val cpgUnary = newCallNode(unary, operatorMethod, operatorMethod, DispatchTypes.STATIC_DISPATCH, order)
      val operandExpr = unary.getOperand match {
        // special handling for operand expression in brackets - we simply ignore the brackets
        case opExpr: IASTUnaryExpression if opExpr.getOperator == IASTUnaryExpression.op_bracketedPrimary =>
          opExpr.getOperand
        case opExpr => opExpr
      }

      val operand = nullSafeAst(operandExpr, 1)

      val ast = Ast(cpgUnary).withChild(operand)
      operand.root match {
        case Some(op) => ast.withArgEdge(cpgUnary, op)
        case None     => ast
      }
    }
  }

  private def astForTypeIdExpression(typeId: IASTTypeIdExpression, order: Int): Ast = {
    typeId.getOperator match {
      case op
          if op == IASTTypeIdExpression.op_sizeof ||
            op == IASTTypeIdExpression.op_typeid ||
            op == IASTTypeIdExpression.op_alignof ||
            op == IASTTypeIdExpression.op_typeof =>
        val call = newCallNode(typeId, Operators.sizeOf, Operators.sizeOf, DispatchTypes.STATIC_DISPATCH, order)
        val arg = astForNode(typeId.getTypeId.getDeclSpecifier, 1)
        val ast = Ast(call).withChild(arg)
        arg.root match {
          case Some(r) => ast.withArgEdge(call, r)
          case _       => ast
        }
      case _ => notHandledYet(typeId, order)
    }
  }

  private def astForFieldReference(fieldRef: IASTFieldReference, order: Int): Ast = {
    val op = if (fieldRef.isPointerDereference) Operators.indirectFieldAccess else Operators.fieldAccess
    val ma = newCallNode(fieldRef, op, op, DispatchTypes.STATIC_DISPATCH, order)
    val owner = astForExpression(fieldRef.getFieldOwner, 1)
    val member = NewFieldIdentifier()
      .canonicalName(fieldRef.getFieldName.toString)
      .code(fieldRef.getFieldName.toString)
      .order(2)
      .argumentIndex(2)
      .lineNumber(line(fieldRef.getFieldName))
      .columnNumber(column(fieldRef.getFieldName))
    Ast(ma).withChild(owner).withChild(Ast(member)).withArgEdge(ma, owner.root.get).withArgEdge(ma, member)
  }

  private def astForConditionalExpression(expr: IASTConditionalExpression, order: Int): Ast = {
    val call = newCallNode(expr, Operators.conditional, Operators.conditional, DispatchTypes.STATIC_DISPATCH, order)

    val condAst = nullSafeAst(expr.getLogicalConditionExpression, 1)
    val posAst = nullSafeAst(expr.getPositiveResultExpression, 2)
    val negAst = nullSafeAst(expr.getNegativeResultExpression, 3)

    val children = Seq(condAst, posAst, negAst)
    val argChildren = children.collect { case c if c.root.isDefined => c.root.get }

    Ast(call).withChildren(children).withArgEdges(call, argChildren)
  }

  private def astForArrayIndexExpression(arrayIndexExpression: IASTArraySubscriptExpression, order: Int): Ast = {
    val cpgArrayIndexing =
      newCallNode(arrayIndexExpression,
                  Operators.indirectIndexAccess,
                  Operators.indirectIndexAccess,
                  DispatchTypes.STATIC_DISPATCH,
                  order)

    val expr = astForExpression(arrayIndexExpression.getArrayExpression, 1)
    val arg = astForNode(arrayIndexExpression.getArgument, 2)

    var ast = Ast(cpgArrayIndexing)
      .withChild(expr)
      .withChild(arg)
    if (expr.root.isDefined) ast = ast.withArgEdge(cpgArrayIndexing, expr.root.get)
    if (arg.root.isDefined) ast = ast.withArgEdge(cpgArrayIndexing, arg.root.get)
    ast
  }

  private def astForCastExpression(castExpression: IASTCastExpression, order: Int): Ast = {
    val cpgCastExpression =
      newCallNode(castExpression, Operators.cast, Operators.cast, DispatchTypes.STATIC_DISPATCH, order)

    val expr = astForExpression(castExpression.getOperand, 1)
    val argNode = castExpression.getTypeId
    val arg = newUnkown(argNode, 2)

    var ast = Ast(cpgCastExpression)
      .withChild(arg)
      .withChild(expr)
      .withArgEdge(cpgCastExpression, arg.root.get)
    if (expr.root.isDefined) ast = ast.withArgEdge(cpgCastExpression, expr.root.get)
    ast
  }

  private def astForNewExpression(newExpression: ICPPASTNewExpression, order: Int): Ast = {
    val cpgNewExpression =
      newCallNode(newExpression, "<operator>.new", "<operator>.new", DispatchTypes.STATIC_DISPATCH, order)

    val typeId = newExpression.getTypeId
    if (newExpression.isArrayAllocation) {
      val cpgTypeId = astForIdentifier(typeId.getDeclSpecifier, 1)
      Ast(cpgNewExpression).withChild(cpgTypeId).withArgEdge(cpgNewExpression, cpgTypeId.root.get)
    } else {
      val cpgTypeId = astForIdentifier(typeId.getDeclSpecifier, 1)
      val args =
        if (newExpression.getInitializer != null && newExpression.getInitializer
              .isInstanceOf[ICPPASTConstructorInitializer]) {
          val args = newExpression.getInitializer.asInstanceOf[ICPPASTConstructorInitializer].getArguments
          withOrder(args) { (a, o) =>
            astForNode(a, 1 + o)
          }
        } else {
          Seq.empty
        }
      val validArgs = args.filter(_.root.isDefined)
      Ast(cpgNewExpression)
        .withChild(cpgTypeId)
        .withChildren(validArgs)
        .withArgEdge(cpgNewExpression, cpgTypeId.root.get)
        .withArgEdges(cpgNewExpression, validArgs.map(_.root.get))
    }
  }

  private def astForDeleteExpression(delExpression: ICPPASTDeleteExpression, order: Int): Ast = {
    val cpgDeleteNode =
      newCallNode(delExpression, Operators.delete, Operators.delete, DispatchTypes.STATIC_DISPATCH, order)
    val arg = astForExpression(delExpression.getOperand, 1)
    var ast = Ast(cpgDeleteNode).withChild(arg)
    if (arg.root.isDefined) ast = ast.withArgEdge(cpgDeleteNode, arg.root.get)
    ast
  }

  private def astForQualifiedName(qualId: CPPASTQualifiedName, order: Int): Ast = {
    val op = Operators.fieldAccess
    val ma = newCallNode(qualId, op, op, DispatchTypes.STATIC_DISPATCH, order)

    def fieldAccesses(names: List[IASTNode], order: Int): Ast = names match {
      case Nil => Ast()
      case head :: Nil =>
        astForNode(head, order)
      case head :: tail =>
        val callNode = newCallNode(head, op, op, DispatchTypes.STATIC_DISPATCH, order)
        val arg1 = astForNode(head, 1)
        val arg2 = fieldAccesses(tail, 2)
        var call =
          Ast(callNode)
            .withChild(arg1)
            .withChild(arg2)
        if (arg1.root.isDefined) call = call.withArgEdge(callNode, arg1.root.get)
        if (arg2.root.isDefined) call = call.withArgEdge(callNode, arg2.root.get)
        call
    }

    val owner = fieldAccesses(qualId.getQualifier.toIndexedSeq.toList, 1)

    val member = NewFieldIdentifier()
      .canonicalName(qualId.getLastName.toString)
      .code(qualId.getLastName.toString)
      .order(2)
      .argumentIndex(2)
      .lineNumber(line(qualId.getLastName))
      .columnNumber(column(qualId.getLastName))
    val ast = Ast(ma).withChild(owner).withChild(Ast(member)).withArgEdge(ma, member)
    owner.root match {
      case Some(value) => ast.withArgEdge(ma, value)
      case None        => ast
    }

  }

  private def astForTypeIdInitExpression(typeIdInit: CPPASTTypeIdInitializerExpression, order: Int): Ast = {
    val cpgCastExpression =
      newCallNode(typeIdInit, Operators.cast, Operators.cast, DispatchTypes.STATIC_DISPATCH, order)

    // TODO: how to represent the initializer here?
    val expr = newUnkown(typeIdInit.getInitializer, 1)

    val typeNode = typeIdInit.getTypeId
    val typeAst = newUnkown(typeNode, 2)

    Ast(cpgCastExpression)
      .withChild(typeAst)
      .withChild(expr)
      .withArgEdge(cpgCastExpression, typeAst.root.get)
      .withArgEdge(cpgCastExpression, expr.root.get)
  }

  private def astForConstructorExpression(c: ICPPASTSimpleTypeConstructorExpression, order: Int): Ast = {
    val name = c.getDeclSpecifier.toString
    val callNode = newCallNode(c, name, name, DispatchTypes.STATIC_DISPATCH, order)

    // TODO: how to represent the initializer here?
    val arg = newUnkown(c.getInitializer, 1)

    val ast = Ast(callNode).withChild(arg)
    ast.withArgEdge(callNode, arg.root.get)
  }

  private def astForExpression(expression: IASTExpression, order: Int): Ast = expression match {
    case lit: IASTLiteralExpression   => astForLiteral(lit, order)
    case un: IASTUnaryExpression      => astForUnaryExpression(un, order)
    case bin: IASTBinaryExpression    => astForBinaryExpression(bin, order)
    case exprList: IASTExpressionList => astForExpressionList(exprList, order)
    case qualId: IASTIdExpression if qualId.getName.isInstanceOf[CPPASTQualifiedName] =>
      astForQualifiedName(qualId.getName.asInstanceOf[CPPASTQualifiedName], order)
    case ident: IASTIdExpression                            => astForIdentifier(ident, order)
    case call: IASTFunctionCallExpression                   => astForCall(call, order)
    case typeId: IASTTypeIdExpression                       => astForTypeIdExpression(typeId, order)
    case fieldRef: IASTFieldReference                       => astForFieldReference(fieldRef, order)
    case expr: IASTConditionalExpression                    => astForConditionalExpression(expr, order)
    case arrayIndexExpression: IASTArraySubscriptExpression => astForArrayIndexExpression(arrayIndexExpression, order)
    case castExpression: IASTCastExpression                 => astForCastExpression(castExpression, order)
    case newExpression: ICPPASTNewExpression                => astForNewExpression(newExpression, order)
    case delExpression: ICPPASTDeleteExpression             => astForDeleteExpression(delExpression, order)
    case typeIdInit: CPPASTTypeIdInitializerExpression      => astForTypeIdInitExpression(typeIdInit, order)
    case c: ICPPASTSimpleTypeConstructorExpression          => astForConstructorExpression(c, order)
    case _                                                  => notHandledYet(expression, order)
  }

  private def astForReturnStatement(ret: IASTReturnStatement, order: Int): Ast = {
    val cpgReturn = NewReturn()
      .code(ret.getRawSignature)
      .order(order)
      .argumentIndex(order)
      .lineNumber(line(ret))
      .columnNumber(column(ret))
    val expr = nullSafeAst(ret.getReturnValue, 1)
    Ast(cpgReturn).withChild(expr)
  }

  private def astForBreakStatement(br: IASTBreakStatement, order: Int): Ast = {
    Ast(newControlStructureNode(br, ControlStructureTypes.BREAK, br.getRawSignature, order))
  }

  private def astForContinueStatement(cont: IASTContinueStatement, order: Int): Ast = {
    Ast(newControlStructureNode(cont, ControlStructureTypes.CONTINUE, cont.getRawSignature, order))
  }

  private def astForGotoStatement(goto: IASTGotoStatement, order: Int): Ast = {
    val code = s"goto ${goto.getName.toString};"
    Ast(newControlStructureNode(goto, ControlStructureTypes.GOTO, code, order))
  }

  private def astsForLabelStatement(label: IASTLabelStatement, order: Int): Seq[Ast] = {
    val cpgLabel = newJumpTarget(label, order)
    val nestedStmts = nullSafeAst(label.getNestedStatement, order + 1)
    Ast(cpgLabel) +: nestedStmts
  }

  private def astForDo(doStmt: IASTDoStatement, order: Int): Ast = {
    val code = doStmt.getRawSignature

    val doNode = newControlStructureNode(doStmt, ControlStructureTypes.DO, code, order)

    val conditionAst = nullSafeAst(doStmt.getCondition, 2)
    val stmtAsts = nullSafeAst(doStmt.getBody, 1)

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

  private def astForSwitch(switchStmt: IASTSwitchStatement, order: Int): Ast = {
    val code = s"switch(${nullSafeCode(switchStmt.getControllerExpression)})"

    val switchNode = newControlStructureNode(switchStmt, ControlStructureTypes.SWITCH, code, order)

    val conditionAst = nullSafeAst(switchStmt.getControllerExpression, 1)
    val stmtAsts = nullSafeAst(switchStmt.getBody, 2)

    val ast = Ast(switchNode)
      .withChild(conditionAst)
      .withChildren(stmtAsts)

    conditionAst.root match {
      case Some(r) =>
        ast.withConditionEdge(switchNode, r)
      case None =>
        ast
    }
  }

  private def astsForCaseStatement(caseStmt: IASTCaseStatement, order: Int): Seq[Ast] = {
    val labelNode = newJumpTarget(caseStmt, order)
    val stmt = nullSafeAst(caseStmt.getExpression, order)
    Seq(Ast(labelNode), stmt)
  }

  private def astForDefaultStatement(caseStmt: IASTDefaultStatement, order: Int): Ast = {
    Ast(newJumpTarget(caseStmt, order))
  }

  private def astForTryStatement(tryStmt: ICPPASTTryBlockStatement, order: Int): Ast = {
    val cpgTry = newControlStructureNode(tryStmt, ControlStructureTypes.TRY, "try", order)
    val body = nullSafeAst(tryStmt.getTryBody, 1)
    val catches = withOrder(tryStmt.getCatchHandlers) { (c, o) =>
      astsForStatement(c.getCatchBody, o + 1)
    }.flatten
    Ast(cpgTry).withChildren(body).withChildren(catches)
  }

  private def astsForStatement(statement: IASTStatement, order: Int): Seq[Ast] = {
    statement match {
      case expr: IASTExpressionStatement          => Seq(astForExpression(expr.getExpression, order))
      case block: IASTCompoundStatement           => Seq(astForBlockStatement(block, order))
      case ifStmt: IASTIfStatement                => Seq(astForIf(ifStmt, order))
      case whileStmt: IASTWhileStatement          => Seq(astForWhile(whileStmt, order))
      case forStmt: IASTForStatement              => Seq(astForFor(forStmt, order))
      case forStmt: ICPPASTRangeBasedForStatement => Seq(astForRangedFor(forStmt, order))
      case doStmt: IASTDoStatement                => Seq(astForDo(doStmt, order))
      case switchStmt: IASTSwitchStatement        => Seq(astForSwitch(switchStmt, order))
      case ret: IASTReturnStatement               => Seq(astForReturnStatement(ret, order))
      case br: IASTBreakStatement                 => Seq(astForBreakStatement(br, order))
      case cont: IASTContinueStatement            => Seq(astForContinueStatement(cont, order))
      case goto: IASTGotoStatement                => Seq(astForGotoStatement(goto, order))
      case defStmt: IASTDefaultStatement          => Seq(astForDefaultStatement(defStmt, order))
      case tryStmt: ICPPASTTryBlockStatement      => Seq(astForTryStatement(tryStmt, order))
      case caseStmt: IASTCaseStatement            => astsForCaseStatement(caseStmt, order)
      case decl: IASTDeclarationStatement         => astsForDeclarationStatement(decl, order)
      case label: IASTLabelStatement              => astsForLabelStatement(label, order)
      case _: IASTNullStatement                   => Seq.empty
      case _                                      => notHandledYetSeq(statement, order)
    }
  }

  private def astForMethodBody(body: Option[IASTStatement], order: Int): Ast = {
    body match {
      case Some(b: IASTCompoundStatement) => astForBlockStatement(b, order)
      case None                           => Ast(NewBlock())
      case Some(b)                        => notHandledYet(b, order)
    }
  }

  private def typeForDeclSpecifier(spec: IASTDeclSpecifier): String = {
    spec match {
      case s: IASTSimpleDeclSpecifier if s.getParent.isInstanceOf[IASTParameterDeclaration] =>
        val parentDecl = s.getParent.asInstanceOf[IASTParameterDeclaration].getDeclarator
        val pointers = parentDecl.getPointerOperators
        if (pointers.isEmpty) { s"${s.toString}" } else {
          s"${s.toString} ${"* " * pointers.size}".strip()
        }
      case s: IASTSimpleDeclSpecifier if s.getParent.isInstanceOf[IASTFunctionDefinition] =>
        val parentDecl = s.getParent.asInstanceOf[IASTFunctionDefinition].getDeclarator
        val pointers = parentDecl.getPointerOperators
        if (pointers.isEmpty) { s"${s.toString}" } else {
          s"${s.toString} ${"* " * pointers.size}".strip()
        }
      case s: IASTSimpleDeclSpecifier    => s.toString
      case s: IASTNamedTypeSpecifier     => s.getName.toString
      case s: IASTCompositeTypeSpecifier => s.getName.toString
      case s: IASTEnumerationSpecifier   => s.getName.toString
      case s: IASTElaboratedTypeSpecifier if s.getParent.isInstanceOf[IASTSimpleDeclaration] =>
        val parentDecl = s.getParent.asInstanceOf[IASTSimpleDeclaration].getDeclarators.head
        val pointers = parentDecl.getPointerOperators
        if (pointers.isEmpty) { s"${s.toString}" } else {
          s"${s.toString} ${"* " * pointers.size}".strip()
        }
      case s: IASTElaboratedTypeSpecifier => s.toString
      // TODO: handle other types of IASTDeclSpecifier
      case _ => Defines.anyTypeName
    }
  }

  private def astForParameter(parameter: IASTParameterDeclaration, childNum: Int): Ast = {
    val decl = parameter.getDeclarator
    val name = decl.getName.getRawSignature
    val code = parameter.getRawSignature
    val tpe = typeForDeclSpecifier(parameter.getDeclSpecifier)

    val parameterNode = NewMethodParameterIn()
      .name(name)
      .code(code)
      .typeFullName(registerType(tpe))
      .order(childNum)
      .evaluationStrategy(EvaluationStrategies.BY_VALUE)
      .lineNumber(line(parameter))
      .columnNumber(column(parameter))

    scope.addToScope(name, (parameterNode, tpe))

    Ast(parameterNode)
  }

  private def astsForCompositeType(typeSpecifier: IASTCompositeTypeSpecifier,
                                   decls: List[IASTDeclarator],
                                   order: Int): Seq[Ast] = {

    val declAsts = withOrder(decls) { (d, o) =>
      astForDeclarator(typeSpecifier.getParent.asInstanceOf[IASTSimpleDeclaration], d, order + o)
    }

    val typeDecls = if (declAsts.nonEmpty) {
      declAsts
    } else {
      val name = typeSpecifier.getName.toString
      val fullname = fullName(typeSpecifier)
      List(typeSpecifier match {
        case cppClass: ICPPASTCompositeTypeSpecifier =>
          val baseClassList = cppClass.getBaseSpecifiers.toSeq.map(_.getNameSpecifier.toString)
          baseClassList.foreach(registerType)
          Ast(
            NewTypeDecl()
              .name(name)
              .fullName(fullname)
              .isExternal(false)
              .filename(typeSpecifier.getContainingFilename)
              .inheritsFromTypeFullName(baseClassList)
              .order(order))
        case _ =>
          Ast(
            NewTypeDecl()
              .name(name)
              .fullName(fullname)
              .isExternal(false)
              .filename(typeSpecifier.getContainingFilename)
              .order(order))
      })
    }

    if (!typeDecls.exists(_.root.isDefined)) {
      notHandledYetSeq(typeSpecifier, order)
    } else {
      typeDecls.foreach(t => scope.pushNewScope(t.root.get))
      val member = withOrder(typeSpecifier.getDeclarations(true)) { (m, o) =>
        astsForDeclaration(m, o)
      }.flatten
      typeDecls.foreach(_ => scope.popScope())

      typeDecls.map(_.withChildren(member))
    }
  }

  private def astsForElaboratedType(typeSpecifier: IASTElaboratedTypeSpecifier,
                                    decls: List[IASTDeclarator],
                                    order: Int): Seq[Ast] = {

    val declAsts = withOrder(decls) { (d, o) =>
      astForDeclarator(typeSpecifier.getParent.asInstanceOf[IASTSimpleDeclaration], d, order + o)
    }

    val typeDecls = if (declAsts.nonEmpty) {
      declAsts
    } else {
      val name = typeSpecifier.getName.toString
      val fullname = fullName(typeSpecifier)
      List(typeSpecifier match {
        case cppClass: ICPPASTCompositeTypeSpecifier =>
          val baseClassList = cppClass.getBaseSpecifiers.toSeq.map(_.getNameSpecifier.toString)
          baseClassList.foreach(registerType)
          Ast(
            NewTypeDecl()
              .name(name)
              .fullName(fullname)
              .isExternal(false)
              .filename(typeSpecifier.getContainingFilename)
              .inheritsFromTypeFullName(baseClassList)
              .order(order))
        case _ =>
          Ast(
            NewTypeDecl()
              .name(name)
              .fullName(fullname)
              .isExternal(false)
              .filename(typeSpecifier.getContainingFilename)
              .order(order))
      })
    }

    typeDecls
  }

  private def astsForEnumerator(enumerator: IASTEnumerationSpecifier.IASTEnumerator, order: Int): Seq[Ast] = {
    val tpe = enumerator.getParent match {
      case enumeration: ICPPASTEnumerationSpecifier if enumeration.getBaseType != null =>
        enumeration.getBaseType.toString
      case _ =>
        ASTTypeUtil.getNodeType(enumerator) match {
          case ""       => Defines.anyTypeName
          case someType => someType
        }
    }
    val cpgMember = NewMember()
      .code(enumerator.getRawSignature)
      .name(enumerator.getName.toString)
      .typeFullName(registerType(tpe))
      .order(order)

    if (enumerator.getValue != null) {
      val operatorName = Operators.assignment
      val callNode = newCallNode(enumerator, operatorName, operatorName, DispatchTypes.STATIC_DISPATCH, order + 1)
      val left = astForNode(enumerator.getName, 1)
      val right = astForNode(enumerator.getValue, 2)

      var ast = Ast(callNode).withChild(left).withChild(right)
      if (left.root.isDefined) ast = ast.withArgEdge(callNode, left.root.get)
      if (right.root.isDefined) ast = ast.withArgEdge(callNode, right.root.get)
      Seq(Ast(cpgMember), ast)
    } else {
      Seq(Ast(cpgMember))
    }

  }

  private def astsForEnum(enumSpecifier: IASTEnumerationSpecifier,
                          decls: List[IASTDeclarator],
                          order: Int): Seq[Ast] = {
    val declAsts = withOrder(decls) { (d, o) =>
      astForDeclarator(enumSpecifier.getParent.asInstanceOf[IASTSimpleDeclaration], d, order + o)
    }

    val typeDecls = if (declAsts.nonEmpty) {
      declAsts
    } else {
      val (name, fullname) = uniqueName("enum", enumSpecifier.getName.toString, fullName(enumSpecifier))
      List(
        Ast(
          NewTypeDecl()
            .name(name)
            .fullName(fullname)
            .isExternal(false)
            .filename(enumSpecifier.getContainingFilename)
            .order(order)))
    }

    if (!typeDecls.exists(_.root.isDefined)) {
      notHandledYetSeq(enumSpecifier, order)
    } else {
      typeDecls.foreach(t => scope.pushNewScope(t.root.get))
      var currentOrder = 0
      val member = enumSpecifier.getEnumerators.toIndexedSeq.flatMap { e =>
        val eCpg = astsForEnumerator(e, currentOrder)
        currentOrder = eCpg.size + currentOrder
        eCpg
      }
      typeDecls.foreach(_ => scope.popScope())

      typeDecls.map(_.withChildren(member))
    }
  }

  private def astForFunctionDeclarator(funcDecl: IASTFunctionDeclarator, order: Int): Ast = {
    val returnType = typeForDeclSpecifier(funcDecl.getParent.asInstanceOf[IASTSimpleDeclaration].getDeclSpecifier)
    val name = shortName(funcDecl)
    val fullname = fullName(funcDecl)
    val signature = returnType + " " + fullname + " " + paramListSignature(funcDecl, includeParamNames = false)
    val code = returnType + " " + name + " " + paramListSignature(funcDecl, includeParamNames = true)
    val methodNode = NewMethod()
      .name(name)
      .code(code)
      .isExternal(false)
      .fullName(fullname)
      .lineNumber(line(funcDecl))
      .lineNumberEnd(lineEnd(funcDecl))
      .columnNumber(column(funcDecl))
      .columnNumberEnd(columnEnd(funcDecl))
      .signature(signature)
      .filename(filename)
      .order(order)

    scope.pushNewScope(methodNode)

    val parameterAsts = withOrder(params(funcDecl)) { (p, order) =>
      astForParameter(p, order)
    }

    val lastOrder = 2 + parameterAsts.size
    val r = Ast(methodNode)
      .withChildren(parameterAsts)
      .withChild(astForMethodReturn(funcDecl, lastOrder, returnType))

    scope.popScope()
    r
  }

  private var usedNames: Int = 0

  private def uniqueName(target: String, name: String, fullName: String): (String, String) = {
    if (name.isEmpty && (fullName.isEmpty || fullName.endsWith("."))) {
      val newName = s"anonymous_${target}_$usedNames"
      val newFullName = s"${fullName}anonymous_${target}_$usedNames"
      usedNames = usedNames + 1
      (newName, newFullName)
    } else {
      (name, fullName)
    }
  }

  private def astForNamespaceDefinition(namespaceDefinition: ICPPASTNamespaceDefinition, order: Int): Ast = {
    val (name, fullname) =
      uniqueName("namespace", namespaceDefinition.getName.getLastName.toString, fullName(namespaceDefinition))
    val code = "namespace " + fullname
    val cpgNamespace = NewNamespaceBlock()
      .code(code)
      .lineNumber(line(namespaceDefinition))
      .columnNumber(column(namespaceDefinition))
      .filename(filename)
      .name(name)
      .fullName(fullname)
      .order(order)

    scope.pushNewScope(cpgNamespace)
    var currOrder = order
    val childrenAsts = namespaceDefinition.getDeclarations.flatMap { decl =>
      val declAsts = astsForDeclaration(decl, currOrder)
      currOrder = currOrder + declAsts.length
      declAsts
    }.toIndexedSeq

    val namespaceAst = Ast(cpgNamespace).withChildren(childrenAsts)
    scope.popScope()
    namespaceAst
  }

  private def astForNamespaceAlias(namespaceAlias: ICPPASTNamespaceAlias, order: Int): Ast = {
    val name = namespaceAlias.getAlias.toString
    val fullname = fullName(namespaceAlias)

    if (!isQualifiedName(name)) {
      usingDeclarationMappings.put(name, fullname)
    }

    val code = "namespace " + name + " = " + fullname
    val cpgNamespace = NewNamespaceBlock()
      .code(code)
      .lineNumber(line(namespaceAlias))
      .columnNumber(column(namespaceAlias))
      .filename(filename)
      .name(name)
      .fullName(fullname)
      .order(order)

    Ast(cpgNamespace)
  }

  private def handleUsingDeclaration(usingDecl: ICPPASTUsingDeclaration): Unit = {
    val mappedName = lastNameOfQualifiedName(usingDecl.getName.toString)
    // we only do the mapping if the declaration is not global because this is already handled by the parser itself
    if (!isQualifiedName(usingDecl.getName.toString)) {
      usingDecl.getParent match {
        case ns: ICPPASTNamespaceDefinition =>
          usingDeclarationMappings.put(fullName(ns) + "." + mappedName, fixQualifiedName(usingDecl.getName.toString))
        case _ =>
          usingDeclarationMappings.put(mappedName, fixQualifiedName(usingDecl.getName.toString))
      }
    }
  }

  private def astsForLinkageSpecification(l: ICPPASTLinkageSpecification): Seq[Ast] =
    withOrder(l.getDeclarations) {
      case (d, o) =>
        astsForDeclaration(d, o)
    }.flatten

  private def astForStaticAssert(a: ICPPASTStaticAssertDeclaration, order: Int): Ast = {
    val name = "static_assert"
    val call = newCallNode(a, name, name, DispatchTypes.STATIC_DISPATCH, order)
    val cond = nullSafeAst(a.getCondition, 1)
    val messg = nullSafeAst(a.getMessage, 2)
    var ast = Ast(call).withChild(cond).withChild(messg)
    cond.root.foreach(r => ast = ast.withArgEdge(call, r))
    messg.root.foreach(m => ast = ast.withArgEdge(call, m))
    ast
  }

  @tailrec
  private def astsForDeclaration(decl: IASTDeclaration, order: Int): Seq[Ast] = decl match {
    case functDef: IASTFunctionDefinition =>
      Seq(astForFunctionDefinition(functDef, order))
    case declaration: IASTSimpleDeclaration
        if declaration.getDeclSpecifier != null && declaration.getDeclSpecifier
          .isInstanceOf[IASTCompositeTypeSpecifier] =>
      astsForCompositeType(declaration.getDeclSpecifier.asInstanceOf[IASTCompositeTypeSpecifier],
                           declaration.getDeclarators.toList,
                           order)
    case declaration: IASTSimpleDeclaration
        if declaration.getDeclSpecifier != null && declaration.getDeclSpecifier
          .isInstanceOf[IASTEnumerationSpecifier] =>
      astsForEnum(declaration.getDeclSpecifier.asInstanceOf[IASTEnumerationSpecifier],
                  declaration.getDeclarators.toList,
                  order)
    case declaration: IASTSimpleDeclaration
        if declaration.getDeclSpecifier != null && declaration.getDeclSpecifier
          .isInstanceOf[IASTElaboratedTypeSpecifier] =>
      astsForElaboratedType(declaration.getDeclSpecifier.asInstanceOf[IASTElaboratedTypeSpecifier],
                            declaration.getDeclarators.toList,
                            order)
    case declaration: IASTSimpleDeclaration if declaration.getDeclarators.nonEmpty =>
      declaration.getDeclarators.toIndexedSeq.map {
        case d: IASTFunctionDeclarator     => astForFunctionDeclarator(d, order)
        case d if d.getInitializer != null => astForInitializer(d, d.getInitializer, order)
        case d                             => astForDeclarator(declaration, d, order)
      }
    case namespaceAlias: ICPPASTNamespaceAlias           => Seq(astForNamespaceAlias(namespaceAlias, order))
    case namespaceDefinition: ICPPASTNamespaceDefinition => Seq(astForNamespaceDefinition(namespaceDefinition, order))
    case _: ICPPASTVisibilityLabel                       => Seq.empty
    case usingDecl: ICPPASTUsingDeclaration =>
      handleUsingDeclaration(usingDecl)
      Seq.empty
    case _: ICPPASTUsingDirective =>
      Seq.empty
    case _: ICPPASTExplicitTemplateInstantiation              => Seq.empty
    case s: IASTSimpleDeclaration if s.getRawSignature == ";" => Seq.empty
    case l: ICPPASTLinkageSpecification                       => astsForLinkageSpecification(l)
    case t: ICPPASTTemplateDeclaration                        => astsForDeclaration(t.getDeclaration, order)
    case a: ICPPASTStaticAssertDeclaration                    => Seq(astForStaticAssert(a, order))
    case _                                                    => notHandledYetSeq(decl, order)
  }

  private def astForFor(forStmt: IASTForStatement, order: Int): Ast = {
    val codeInit = nullSafeCode(forStmt.getInitializerStatement)
    val codeCond = nullSafeCode(forStmt.getConditionExpression)
    val codeIter = nullSafeCode(forStmt.getIterationExpression)

    val code = s"for ($codeInit$codeCond;$codeIter)"
    val forNode = newControlStructureNode(forStmt, ControlStructureTypes.FOR, code, order)

    val initAsts = nullSafeAst(forStmt.getInitializerStatement, 1)

    val continuedOrder = Math.max(initAsts.size, 1)
    val compareAst = nullSafeAst(forStmt.getConditionExpression, continuedOrder + 1)
    val updateAst = nullSafeAst(forStmt.getIterationExpression, continuedOrder + 2)
    val stmtAst = nullSafeAst(forStmt.getBody, continuedOrder + 3)

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

  private def astForRangedFor(forStmt: ICPPASTRangeBasedForStatement, order: Int): Ast = {
    val codeDecl = nullSafeCode(forStmt.getDeclaration)
    val codeInit = nullSafeCode(forStmt.getInitializerClause)

    val code = s"for ($codeDecl:$codeInit)"
    val forNode = newControlStructureNode(forStmt, ControlStructureTypes.FOR, code, order)

    val initAst = astForNode(forStmt.getInitializerClause, 1)
    val declAst = astsForDeclaration(forStmt.getDeclaration, 2)
    val stmtAst = nullSafeAst(forStmt.getBody, 3)

    Ast(forNode)
      .withChild(initAst)
      .withChildren(declAst)
      .withChildren(stmtAst)
  }

  private def astForWhile(whileStmt: IASTWhileStatement, order: Int): Ast = {
    val code = s"while (${nullSafeCode(whileStmt.getCondition)})"

    val whileNode = newControlStructureNode(whileStmt, ControlStructureTypes.WHILE, code, order)

    val conditionAst = nullSafeAst(whileStmt.getCondition, 1)
    val stmtAsts = nullSafeAst(whileStmt.getBody, 2)

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

  private def astForIf(ifStmt: IASTIfStatement, order: Int): Ast = {
    val code = s"if (${nullSafeCode(ifStmt.getConditionExpression)})"

    val ifNode = newControlStructureNode(ifStmt, ControlStructureTypes.IF, code, order)

    val conditionAst = nullSafeAst(ifStmt.getConditionExpression, 1)
    val stmtAsts = nullSafeAst(ifStmt.getThenClause, 2)

    val elseChild = if (ifStmt.getElseClause != null) {
      val elseNode = newControlStructureNode(ifStmt.getElseClause, ControlStructureTypes.ELSE, "else", 3)
      val elseAsts = astsForStatement(ifStmt.getElseClause, 1)
      Ast(elseNode).withChildren(elseAsts)
    } else Ast()

    val ast = Ast(ifNode)
      .withChild(conditionAst)
      .withChildren(stmtAsts)
      .withChild(elseChild)

    conditionAst.root match {
      case Some(r) =>
        ast.withConditionEdge(ifNode, r)
      case None =>
        ast
    }
  }

}
