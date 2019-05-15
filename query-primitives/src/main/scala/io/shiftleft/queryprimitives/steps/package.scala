package io.shiftleft.queryprimitives

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.types.structure._
import io.shiftleft.queryprimitives.steps.types.expressions._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import shapeless.HList

/**
  Steps for traversing the code property graph

  All traversals start at io.shiftleft.queryprimitives.starters.Cpg.

  Implicit conversions to specific steps, based on the node at hand.
  Automatically in scope when using anything in the `steps` package, e.g. `Steps`
  */
package object steps {

  implicit def withMethodMethodsQp(node: nodes.WithinMethod): WithinMethodMethods =
    new WithinMethodMethods(node)

  implicit def cfgNodeMethodsQp(node: nodes.CfgNode): CfgNodeMethods =
    new CfgNodeMethods(node)

  implicit def toLiteral[Labels <: HList](steps: Steps[nodes.LiteralRef, Labels]): Literal[Labels] =
    new Literal[Labels](steps.raw)

  implicit def toType[Labels <: HList](steps: Steps[nodes.TypeRef, Labels]): Type[Labels] =
    new Type[Labels](steps.raw)

  implicit def toTypeDecl[Labels <: HList](steps: Steps[nodes.TypeDeclRef, Labels]): TypeDecl[Labels] =
    new TypeDecl[Labels](steps.raw)

  implicit def toCall[Labels <: HList](steps: Steps[nodes.CallRef, Labels]): Call[Labels] =
    new Call[Labels](steps.raw)

  implicit def toIdentifier[Labels <: HList](steps: Steps[nodes.IdentifierRef, Labels]): Identifier[Labels] =
    new Identifier[Labels](steps.raw)

  implicit def toMember[Labels <: HList](steps: Steps[nodes.MemberRef, Labels]): Member[Labels] =
    new Member[Labels](steps.raw)

  implicit def toLocal[Labels <: HList](steps: Steps[nodes.LocalRef, Labels]): Local[Labels] =
    new Local[Labels](steps.raw)

  implicit def toMethodInst[Labels <: HList](steps: Steps[nodes.MethodInstRef, Labels]): MethodInst[Labels] =
    new MethodInst[Labels](steps.raw)

  implicit def toMethod[Labels <: HList](steps: Steps[nodes.MethodRef, Labels]): Method[Labels] =
    new Method[Labels](steps.raw)

  implicit def toMethodParameter[Labels <: HList](
      steps: Steps[nodes.MethodParameterInRef, Labels]): MethodParameter[Labels] =
    new MethodParameter[Labels](steps.raw)

  implicit def toMethodParameterOut[Labels <: HList](
      steps: Steps[nodes.MethodParameterOutRef, Labels]): MethodParameterOut[Labels] =
    new MethodParameterOut[Labels](steps.raw)

  implicit def toMethodReturn[Labels <: HList](steps: Steps[nodes.MethodReturnRef, Labels]): MethodReturn[Labels] =
    new MethodReturn[Labels](steps.raw)

  implicit def toNamespace[Labels <: HList](steps: Steps[nodes.NamespaceRef, Labels]): Namespace[Labels] =
    new Namespace[Labels](steps.raw)

  implicit def toNamespaceBlock[Labels <: HList](steps: Steps[nodes.NamespaceBlockRef, Labels]): NamespaceBlock[Labels] =
    new NamespaceBlock[Labels](steps.raw)

  implicit def toModifier[Labels <: HList](steps: Steps[nodes.ModifierRef, Labels]): Modifier[Labels] =
    new Modifier[Labels](steps.raw)

  implicit def toExpression[Labels <: HList](steps: Steps[nodes.Expression, Labels]): Expression[Labels] =
    new Expression[Labels](steps.raw)

  implicit def toDeclaration[Labels <: HList](steps: Steps[nodes.Declaration, Labels]): Declaration[Labels] =
    new Declaration[Labels](steps.raw)

  implicit def toFile[Labels <: HList](steps: Steps[nodes.FileRef, Labels]): File[Labels] =
    new File[Labels](steps.raw)
}
