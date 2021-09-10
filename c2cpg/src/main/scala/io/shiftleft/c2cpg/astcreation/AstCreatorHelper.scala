package io.shiftleft.c2cpg.astcreation

import io.shiftleft.codepropertygraph.generated.DispatchTypes
import io.shiftleft.x2cpg.Ast
import org.eclipse.cdt.core.dom.ast._
import org.eclipse.cdt.core.dom.ast.cpp._
import org.eclipse.cdt.core.dom.ast.gnu.c.ICASTKnRFunctionDeclarator
import org.eclipse.cdt.internal.core.dom.parser.cpp.semantics.EvalBinding
import org.eclipse.cdt.internal.core.dom.parser.cpp.{CPPASTIdExpression, CPPASTQualifiedName, CPPFunction}
import org.eclipse.cdt.internal.core.model.ASTStringUtil

trait AstCreatorHelper {

  this: AstCreator =>

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

  private def cleanType(t: String): String = t match {
    case ""                                        => Defines.anyTypeName
    case t if t.contains("?")                      => Defines.anyTypeName
    case t if t.contains("#")                      => Defines.anyTypeName
    case t if t.startsWith("{") && t.endsWith("}") => Defines.anyTypeName
    case t if t.contains("*")                      => "*"
    case t if t.contains("[")                      => "[]"
    case t if t.contains("::")                     => fixQualifiedName(t).split(".").lastOption.getOrElse(Defines.anyTypeName)
    case someType                                  => someType
  }

  protected def typeFor(node: IASTNode): String = cleanType(ASTTypeUtil.getNodeType(node))

  protected def typeFor(node: IASTTypeId): String = cleanType(ASTTypeUtil.getType(node))

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
    Option(node).map(AstCreator.nodeSignature).getOrElse("")
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
              fixQualifiedName(AstCreator.nodeSignature(d.getName)),
              f.getDeclarations.headOption.map(x => AstCreator.nodeSignature(x.getName)).getOrElse(f.getName))
          case f: CPPFunction if f.getDefinition != null =>
            usingDeclarationMappings.getOrElse(fixQualifiedName(AstCreator.nodeSignature(d.getName)),
                                               AstCreator.nodeSignature(f.getDefinition.getName))
          case other => other.getName
        }
      case alias: ICPPASTNamespaceAlias =>
        AstCreator.nodeSignature(alias.getMappingName)
      case namespace: ICPPASTNamespaceDefinition if AstCreator.nodeSignature(namespace.getName).nonEmpty =>
        fullName(namespace.getParent) + "." + AstCreator.nodeSignature(namespace.getName)
      case namespace: ICPPASTNamespaceDefinition if AstCreator.nodeSignature(namespace.getName).isEmpty =>
        fullName(namespace.getParent) + "." + uniqueName("namespace", "", "")._1
      case cppClass: ICPPASTCompositeTypeSpecifier if AstCreator.nodeSignature(cppClass.getName).nonEmpty =>
        fullName(cppClass.getParent) + "." + cppClass.getName.toString
      case cppClass: ICPPASTCompositeTypeSpecifier if AstCreator.nodeSignature(cppClass.getName).isEmpty =>
        val name = cppClass.getParent match {
          case decl: IASTSimpleDeclaration =>
            decl.getDeclarators.headOption
              .map(x => AstCreator.nodeSignature(x.getName))
              .getOrElse(uniqueName("composite_type", "", "")._1)
          case _ => uniqueName("composite_type", "", "")._1
        }
        val fullname = s"${fullName(cppClass.getParent)}.$name"
        fullname
      case enum: IASTEnumerationSpecifier =>
        fullName(enum.getParent) + "." + AstCreator.nodeSignature(enum.getName)
      case c: IASTCompositeTypeSpecifier => AstCreator.nodeSignature(c.getName)
      case f: IASTFunctionDeclarator =>
        fullName(f.getParent) + "." + AstCreator.nodeSignature(f.getName)
      case f: ICPPASTLambdaExpression =>
        fullName(f.getParent) + "."
      case f: IASTFunctionDefinition =>
        fullName(f.getParent) + "." + AstCreator.nodeSignature(f.getDeclarator.getName)
      case d: IASTIdExpression    => AstCreator.nodeSignature(d.getName)
      case _: IASTTranslationUnit => ""
      case u: IASTUnaryExpression => AstCreator.nodeSignature(u.getOperand)
      case e: ICPPASTElaboratedTypeSpecifier =>
        fullName(e.getParent) + "." + AstCreator.nodeSignature(e.getName)
      case other if other.getParent != null => fullName(other.getParent)
      case other if other != null           => notHandledYet(other, -1); ""
      case null                             => ""
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
      case d: IASTIdExpression           => lastNameOfQualifiedName(d.getName.toString)
      case u: IASTUnaryExpression        => shortName(u.getOperand)
      case c: IASTFunctionCallExpression => shortName(c.getFunctionNameExpression)
      case other                         => notHandledYet(other, -1); ""
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

  private def astforDecltypeSpecifier(decl: ICPPASTDecltypeSpecifier, order: Int): Ast = {
    val op = "operators.<typeOf>"
    val cpgUnary = newCallNode(decl, op, op, DispatchTypes.STATIC_DISPATCH, order)

    val operand = nullSafeAst(decl.getDecltypeExpression, 1)

    val ast = Ast(cpgUnary).withChild(operand)
    operand.root match {
      case Some(op) => ast.withArgEdge(cpgUnary, op)
      case None     => ast
    }
  }

  protected def astForNode(node: IASTNode, order: Int): Ast = {
    node match {
      case id: IASTIdExpression if id.getName.isInstanceOf[CPPASTQualifiedName] =>
        astForQualifiedName(id.getName.asInstanceOf[CPPASTQualifiedName], order)
      case name: IASTName                 => astForIdentifier(name, order)
      case decl: IASTDeclSpecifier        => astForIdentifier(decl, order)
      case expr: IASTExpression           => astForExpression(expr, order)
      case l: IASTInitializerList         => astForInitializerList(l, order)
      case decl: ICPPASTDecltypeSpecifier => astforDecltypeSpecifier(decl, order)
      case _                              => notHandledYet(node, order)
    }
  }

  protected def typeForDeclSpecifier(spec: IASTNode): String = {
    val tpe = spec match {
      case s: IASTSimpleDeclSpecifier if s.getParent.isInstanceOf[IASTParameterDeclaration] =>
        val parentDecl = s.getParent.asInstanceOf[IASTParameterDeclaration].getDeclarator
        pointersAsString(s, parentDecl)
      case s: IASTSimpleDeclSpecifier if s.getParent.isInstanceOf[IASTFunctionDefinition] =>
        val parentDecl = s.getParent.asInstanceOf[IASTFunctionDefinition].getDeclarator
        ASTStringUtil.getReturnTypeString(s, parentDecl)
      case s: IASTSimpleDeclaration if s.getParent.isInstanceOf[ICASTKnRFunctionDeclarator] =>
        val decl = s.getDeclarators.head
        pointersAsString(s.getDeclSpecifier, decl)
      case s: IASTSimpleDeclSpecifier =>
        ASTStringUtil.getReturnTypeString(s, null)
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
    if (tpe.isEmpty) Defines.anyTypeName else tpe
  }

}
