package io.shiftleft.c2cpg.astcreation

import io.shiftleft.x2cpg.Ast
import org.eclipse.cdt.core.dom.ast._
import org.eclipse.cdt.core.dom.ast.cpp._
import org.eclipse.cdt.internal.core.dom.parser.cpp.semantics.EvalBinding
import org.eclipse.cdt.internal.core.dom.parser.cpp.{CPPASTIdExpression, CPPASTQualifiedName, CPPFunction}
import org.eclipse.cdt.internal.core.model.ASTStringUtil

trait AstCreatorHelper {

  this: AstCreator =>

  object Defines {
    val anyTypeName = "ANY"
    val voidTypeName = "void"
    val qualifiedNameSeparator = "::"
  }

  private var usedNames: Int = 0

  protected def uniqueName(target: String, name: String, fullName: String): (String, String) = {
    if (name.isEmpty && (fullName.isEmpty || fullName.endsWith("."))) {
      val newName = s"anonymous_${target}_$usedNames"
      val newFullName = s"${fullName}anonymous_${target}_$usedNames"
      usedNames = usedNames + 1
      (newName, newFullName)
    } else {
      (name, fullName)
    }
  }

  private def nullSafeFileLocation(node: IASTNode): Option[IASTFileLocation] = Option(node.getFileLocation)

  private def nullSafeLastNodeLocation(node: IASTNode): Option[IASTNodeLocation] =
    Option(node.getNodeLocations).flatMap(_.lastOption)

  protected def line(node: IASTNode): Option[Integer] = {
    nullSafeFileLocation(node).map(_.getStartingLineNumber)
  }

  protected def lineEnd(node: IASTNode): Option[Integer] = {
    nullSafeFileLocation(node).map(_.getEndingLineNumber)
  }

  protected def column(node: IASTNode): Option[Integer] = {
    if (line(node).isEmpty) None
    else {
      val l = line(node).get - 1
      if (l == 0) {
        nullSafeFileLocation(node).map(_.getNodeOffset)
      } else if (nullSafeFileLocation(node).map(_.getNodeOffset).contains(0)) {
        Some(0)
      } else {
        val slice = fileLines.slice(0, l)
        val length = slice.size - 1
        nullSafeFileLocation(node).map(_.getNodeOffset - 1 - slice.sum - length)
      }
    }
  }

  protected def columnEnd(node: IASTNode): Option[Integer] = {
    if (line(node).isEmpty) None
    else {
      val l = line(node).get - 1
      if (l == 0) {
        nullSafeLastNodeLocation(node).map(_.getNodeOffset)
      } else if (nullSafeLastNodeLocation(node).map(_.getNodeOffset).contains(0)) {
        Some(0)
      } else {
        val slice = fileLines.slice(0, l)
        val length = slice.size - 1
        nullSafeLastNodeLocation(node).map(_.getNodeOffset - 1 - slice.sum - length)
      }
    }
  }

  protected def withOrder[T <: IASTNode, X](nodes: Seq[T])(f: (T, Int) => X): Seq[X] =
    nodes.zipWithIndex.map {
      case (x, i) =>
        f(x, i + 1)
    }

  protected def withOrder[T <: IASTNode, X](nodes: Array[T])(f: (T, Int) => X): Seq[X] =
    nodes.toIndexedSeq.zipWithIndex.map {
      case (x, i) =>
        f(x, i + 1)
    }

  protected def registerType(typeName: String): String = {
    global.usedTypes.put(typeName, true)
    typeName
  }

  private def notHandledText(node: IASTNode): String =
    s"""Node '${node.getClass.getSimpleName}' not handled yet!
       |  Code: '${node.getRawSignature}'
       |  File: '$filename'
       |  Line: ${line(node).getOrElse(-1)}
       |  """.stripMargin

  protected def notHandledYet(node: IASTNode, order: Int): Ast = {
    if (!node.isInstanceOf[IASTProblem] && !node.isInstanceOf[IASTProblemHolder]) {
      val text = notHandledText(node)
      logger.warn(text)
    }
    Ast(newUnknown(node, order))
  }

  protected def nullSafeCode(node: IASTNode): String = {
    Option(node).map(_.getRawSignature).getOrElse("")
  }

  protected def nullSafeAst(node: IASTExpression, order: Int): Ast = {
    Option(node).map(astForNode(_, order)).getOrElse(Ast())
  }

  protected def nullSafeAst(node: IASTStatement, order: Int): Seq[Ast] = {
    Option(node).map(astsForStatement(_, order)).getOrElse(Seq.empty)
  }

  protected def fixQualifiedName(name: String): String =
    name.replace(Defines.qualifiedNameSeparator, ".")

  protected def isQualifiedName(name: String): Boolean =
    name.startsWith(Defines.qualifiedNameSeparator)

  protected def lastNameOfQualifiedName(name: String): String = {
    val cleanedName = if (name.contains("<") && name.contains(">")) {
      name.substring(0, name.indexOf("<"))
    } else {
      name
    }
    cleanedName.split(Defines.qualifiedNameSeparator).lastOption.getOrElse(cleanedName)
  }

  protected def fullName(node: IASTNode): String = {
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
      case enum: IASTEnumerationSpecifier if enum.getParent != null =>
        fullName(enum.getParent) + "." + enum.getName.toString
      case enum: IASTEnumerationSpecifier => enum.getName.toString
      case c: IASTCompositeTypeSpecifier  => c.getName.toString
      case f: IASTFunctionDeclarator if f.getParent != null =>
        fullName(f.getParent) + "." + f.getName.toString
      case f: ICPPASTLambdaExpression if f.getParent != null =>
        fullName(f.getParent) + "."
      case f: IASTFunctionDeclarator =>
        f.getName.toString
      case f: IASTFunctionDefinition if f.getParent != null =>
        fullName(f.getParent) + "." + f.getDeclarator.getName.toString
      case f: IASTFunctionDefinition =>
        f.getDeclarator.getName.toString
      case d: IASTIdExpression    => d.getName.toString
      case _: IASTTranslationUnit => ""
      case u: IASTUnaryExpression => u.getOperand.toString
      case e: ICPPASTElaboratedTypeSpecifier if e.getParent != null =>
        fullName(e.getParent) + "." + e.getName.toString
      case e: ICPPASTElaboratedTypeSpecifier if e.getParent == null =>
        e.getName.toString
      case other if other.getParent != null => fullName(other.getParent)
      case other                            => notHandledYet(other, -1); ""
    }
    val cleaned = fixQualifiedName(qualifiedName)
    if (cleaned.startsWith(".")) {
      cleaned.substring(1)
    } else cleaned
  }

  protected def shortName(node: IASTNode): String = {
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
      case d: IASTIdExpression    => lastNameOfQualifiedName(d.getName.toString)
      case u: IASTUnaryExpression => shortName(u.getOperand)
      case other                  => notHandledYet(other, -1); ""
    }
    name
  }

  private def pointersAsString(spec: IASTDeclSpecifier, parentDecl: IASTDeclarator): String = {
    val nodeAsString = ASTStringUtil.getReturnTypeString(spec, null)
    val pointers = parentDecl.getPointerOperators
    val arr = parentDecl match {
      case p: IASTArrayDeclarator => "[]" * p.getArrayModifiers.length
      case _                      => ""
    }
    if (pointers.isEmpty) { s"$nodeAsString$arr" } else {
      s"$nodeAsString$arr ${"* " * pointers.size}".strip()
    }
  }

  protected def astForNode(node: IASTNode, order: Int): Ast = {
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

  protected def typeForDeclSpecifier(spec: IASTDeclSpecifier): String = {
    spec match {
      case s: IASTSimpleDeclSpecifier if s.getParent.isInstanceOf[IASTParameterDeclaration] =>
        val parentDecl = s.getParent.asInstanceOf[IASTParameterDeclaration].getDeclarator
        pointersAsString(s, parentDecl)
      case s: IASTSimpleDeclSpecifier if s.getParent.isInstanceOf[IASTFunctionDefinition] =>
        val parentDecl = s.getParent.asInstanceOf[IASTFunctionDefinition].getDeclarator
        ASTStringUtil.getReturnTypeString(s, parentDecl)
      case s: IASTSimpleDeclSpecifier => ASTStringUtil.getReturnTypeString(s, null)
      case s: IASTNamedTypeSpecifier if s.getParent.isInstanceOf[IASTParameterDeclaration] =>
        val parentDecl = s.getParent.asInstanceOf[IASTParameterDeclaration].getDeclarator
        pointersAsString(s, parentDecl)
      case s: IASTNamedTypeSpecifier     => s.getName.toString
      case s: IASTCompositeTypeSpecifier => s.getName.toString
      case s: IASTEnumerationSpecifier   => s.getName.toString
      case s: IASTElaboratedTypeSpecifier if s.getParent.isInstanceOf[IASTParameterDeclaration] =>
        val parentDecl = s.getParent.asInstanceOf[IASTParameterDeclaration].getDeclarator
        pointersAsString(s, parentDecl)
      case s: IASTElaboratedTypeSpecifier if s.getParent.isInstanceOf[IASTSimpleDeclaration] =>
        val parentDecl = s.getParent.asInstanceOf[IASTSimpleDeclaration].getDeclarators.head
        pointersAsString(s, parentDecl)
      case s: IASTElaboratedTypeSpecifier => ASTStringUtil.getSignatureString(s, null)
      // TODO: handle other types of IASTDeclSpecifier
      case _ => Defines.anyTypeName
    }
  }

}
