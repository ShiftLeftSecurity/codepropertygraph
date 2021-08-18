package io.shiftleft.c2cpg.astcreation

import io.shiftleft.c2cpg.datastructures.Stack._
import io.shiftleft.codepropertygraph.generated.EvaluationStrategies
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.x2cpg.Ast
import org.eclipse.cdt.core.dom.ast._
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTLambdaExpression
import org.eclipse.cdt.core.dom.ast.gnu.c.ICASTKnRFunctionDeclarator
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDeclarator
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator

import scala.annotation.tailrec

trait AstForFunctionsCreator {

  this: AstCreator =>

  private def createFunctionTypeAndTypeDecl(method: NewMethod,
                                            methodName: String,
                                            methodFullName: String,
                                            signature: String): Ast = {
    val parentNode: NewTypeDecl = methodAstParentStack.collectFirst { case t: NewTypeDecl => t }.getOrElse {
      val astParentType = methodAstParentStack.head.label
      val astParentFullName = methodAstParentStack.head.properties("FULL_NAME").toString
      val newTypeDeclNode = NewTypeDecl()
        .name(methodName)
        .fullName(methodFullName)
        .astParentType(astParentType)
        .astParentFullName(astParentFullName)
      Ast.storeInDiffGraph(Ast(newTypeDeclNode), diffGraph)
      newTypeDeclNode
    }

    method.astParentFullName = parentNode.fullName
    method.astParentType = parentNode.label
    val functionBinding = NewBinding().name(methodName).signature(signature)
    Ast(functionBinding).withBindsEdge(parentNode, functionBinding).withRefEdge(functionBinding, method)
  }

  @tailrec
  private def parameters(funct: IASTNode): Seq[IASTNode] = funct match {
    case decl: CPPASTFunctionDeclarator            => decl.getParameters.toIndexedSeq
    case decl: CASTFunctionDeclarator              => decl.getParameters.toIndexedSeq
    case decl: IASTStandardFunctionDeclarator      => decl.getParameters.toIndexedSeq
    case defn: IASTFunctionDefinition              => parameters(defn.getDeclarator)
    case lambdaExpression: ICPPASTLambdaExpression => parameters(lambdaExpression.getDeclarator)
    case knr: ICASTKnRFunctionDeclarator           => knr.getParameterDeclarations.toIndexedSeq
    case other if other != null                    => notHandledYet(other, -1); Seq.empty
    case null                                      => Seq.empty
  }

  private def parameterListSignature(func: IASTNode, includeParamNames: Boolean): String = {
    val elements =
      if (!includeParamNames) parameters(func).map {
        case p: IASTParameterDeclaration => typeForDeclSpecifier(p.getDeclSpecifier)
        case other                       => typeForDeclSpecifier(other)
      } else
        parameters(func).map(p => p.getRawSignature)
    "(" + elements.mkString(",") + ")"
  }

  protected def methodRefForLambda(lambdaExpression: ICPPASTLambdaExpression): NewMethodRef = {
    val returnType = lambdaExpression.getDeclarator match {
      case declarator: IASTDeclarator =>
        declarator.getTrailingReturnType match {
          case id: IASTTypeId => typeForDeclSpecifier(id.getDeclSpecifier)
          case _              => Defines.anyTypeName
        }
      case null => Defines.anyTypeName
    }
    val (name, fullname) = uniqueName("lambda", "", fullName(lambdaExpression))
    val signature = returnType + " " + fullname + " " + parameterListSignature(lambdaExpression,
                                                                               includeParamNames = false)
    val code = returnType + " " + name + " " + parameterListSignature(lambdaExpression, includeParamNames = true)
    val methodNode: NewMethod = NewMethod()
      .name(name)
      .code(code)
      .isExternal(false)
      .fullName(fullname)
      .lineNumber(line(lambdaExpression))
      .lineNumberEnd(lineEnd(lambdaExpression))
      .columnNumber(column(lambdaExpression))
      .columnNumberEnd(columnEnd(lambdaExpression))
      .signature(signature)
      .filename(filename)

    scope.pushNewScope(methodNode)
    val parameterAsts = withOrder(parameters(lambdaExpression.getDeclarator)) { (p, o) =>
      astForParameter(p, o)
    }

    val lastOrder = 1 + parameterAsts.size

    val r = Ast(methodNode)
      .withChildren(parameterAsts)
      .withChild(astForMethodReturn(lambdaExpression, lastOrder, returnType))

    scope.popScope()
    val typeDeclAst = createFunctionTypeAndTypeDecl(methodNode, name, fullname, signature)

    Ast.storeInDiffGraph(r, diffGraph)
    Ast.storeInDiffGraph(typeDeclAst, diffGraph)

    createMethodRefNode(code, fullname, methodNode.astParentFullName, lambdaExpression)
  }

  protected def astForFunctionDeclarator(funcDecl: IASTFunctionDeclarator, order: Int): Ast = {
    val returnType = typeForDeclSpecifier(funcDecl.getParent.asInstanceOf[IASTSimpleDeclaration].getDeclSpecifier)
    val name = shortName(funcDecl)
    val fullname = fullName(funcDecl)
    val templateParams = templateParameters(funcDecl).getOrElse("")
    val signature = returnType + " " + fullname + templateParams + " " + parameterListSignature(funcDecl,
                                                                                                includeParamNames =
                                                                                                  false)
    val code = returnType + " " + name + " " + parameterListSignature(funcDecl, includeParamNames = true)
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

    val parameterAsts = withOrder(parameters(funcDecl)) { (p, order) =>
      astForParameter(p, order)
    }

    val lastOrder = 1 + parameterAsts.size
    val r = Ast(methodNode)
      .withChildren(parameterAsts)
      .withChild(astForMethodReturn(funcDecl, lastOrder, returnType))

    scope.popScope()

    val typeDeclAst = createFunctionTypeAndTypeDecl(methodNode, name, fullname, signature)
    Ast.storeInDiffGraph(typeDeclAst, diffGraph)

    r
  }

  protected def astForFunctionDefinition(functDef: IASTFunctionDefinition, order: Int): Ast = {
    val returnType = typeForDeclSpecifier(functDef.getDeclSpecifier)
    val name = shortName(functDef)
    val fullname = fullName(functDef)
    val templateParams = templateParameters(functDef).getOrElse("")
    val signature = returnType + " " + fullname + templateParams + " " + parameterListSignature(functDef,
                                                                                                includeParamNames =
                                                                                                  false)
    val code = returnType + " " + name + " " + parameterListSignature(functDef, includeParamNames = true)
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
      .order(order)

    methodAstParentStack.push(methodNode)
    scope.pushNewScope(methodNode)

    val parameterAsts = withOrder(parameters(functDef)) { (p, order) =>
      astForParameter(p, order)
    }

    val lastOrder = 1 + parameterAsts.size
    val r = Ast(methodNode)
      .withChildren(parameterAsts)
      .withChild(astForMethodBody(Option(functDef.getBody), lastOrder))
      .withChild(astForMethodReturn(functDef, lastOrder + 1, typeForDeclSpecifier(functDef.getDeclSpecifier)))

    scope.popScope()
    methodAstParentStack.pop()

    val typeDeclAst = createFunctionTypeAndTypeDecl(methodNode, name, fullname, signature)
    Ast.storeInDiffGraph(typeDeclAst, diffGraph)

    r
  }

  private def astForParameter(parameter: IASTNode, childNum: Int): Ast = {
    val (name, code, tpe) = parameter match {
      case p: IASTParameterDeclaration =>
        (p.getDeclarator.getName.getRawSignature, p.getRawSignature, typeForDeclSpecifier(p.getDeclSpecifier))
      case s: IASTSimpleDeclaration =>
        (s.getDeclarators.headOption.map(_.getName.getRawSignature).getOrElse(uniqueName("parameter", "", "")._1),
         s.getRawSignature,
         typeForDeclSpecifier(s))
      case other =>
        (other.getRawSignature, other.getRawSignature, typeForDeclSpecifier(other))
    }

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

  private def astForMethodBody(body: Option[IASTStatement], order: Int): Ast = {
    body match {
      case Some(b: IASTCompoundStatement) => astForBlockStatement(b, order)
      case None                           => Ast(NewBlock())
      case Some(b)                        => astForNode(b, order)
    }
  }

  private def astForMethodReturn(func: IASTNode, order: Int, tpe: String): Ast =
    Ast(
      NewMethodReturn()
        .order(order)
        .typeFullName(registerType(tpe))
        .code(tpe)
        .evaluationStrategy(EvaluationStrategies.BY_VALUE)
        .lineNumber(line(func))
        .columnNumber(column(func)))

}
