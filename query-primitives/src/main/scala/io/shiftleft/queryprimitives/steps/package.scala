package io.shiftleft.queryprimitives

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.dataflows.steps.TrackingPoint
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.structure._
import io.shiftleft.queryprimitives.steps.types.expressions._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import shapeless.{HList, HNil}

/**
  Steps for traversing the code property graph

  All traversals start at io.shiftleft.queryprimitives.starters.Cpg.

  Implicit conversions to specific steps, based on the node at hand.
  Automatically in scope when using anything in the `steps` package, e.g. `Steps`
  */
package object steps {

  // TODO MP: rather use `start` mechanism?
  // alternative: move to `nodes` package object?
  implicit def trackingPointBaseMethodsQp(node: nodes.TrackingPointBase): TrackingPointMethods =
    new TrackingPointMethods(node)

  implicit var trackingPointToCfgNodeImpl = new TrackPointToCfgNode()

  implicit def withMethodMethodsQp(node: nodes.WithinMethod): WithinMethodMethods =
    new WithinMethodMethods(node)

  implicit def cfgNodeMethodsQp(node: nodes.CfgNode): CfgNodeMethods =
    new CfgNodeMethods(node)

  implicit def toLiteral[Labels <: HList](steps: Steps[nodes.Literal, Labels]): Literal[Labels] =
    new Literal[Labels](steps.raw)

  implicit def toType[Labels <: HList](steps: Steps[nodes.Type, Labels]): Type[Labels] =
    new Type[Labels](steps.raw)

  implicit def toTypeDecl[Labels <: HList](steps: Steps[nodes.TypeDecl, Labels]): TypeDecl[Labels] =
    new TypeDecl[Labels](steps.raw)

  implicit def toCall[Labels <: HList](steps: Steps[nodes.Call, Labels]): Call[Labels] =
    new Call[Labels](steps.raw)

  implicit def toIdentifier[Labels <: HList](steps: Steps[nodes.Identifier, Labels]): Identifier[Labels] =
    new Identifier[Labels](steps.raw)

  implicit def toMember[Labels <: HList](steps: Steps[nodes.Member, Labels]): Member[Labels] =
    new Member[Labels](steps.raw)

  implicit def toLocal[Labels <: HList](steps: Steps[nodes.Local, Labels]): Local[Labels] =
    new Local[Labels](steps.raw)

  implicit def toMethodInst[Labels <: HList](steps: Steps[nodes.MethodInst, Labels]): MethodInst[Labels] =
    new MethodInst[Labels](steps.raw)

  implicit def toMethod[Labels <: HList](steps: Steps[nodes.Method, Labels]): Method[Labels] =
    new Method[Labels](steps.raw)

  implicit def toMethodParameter[Labels <: HList](
      steps: Steps[nodes.MethodParameterIn, Labels]): MethodParameter[Labels] =
    new MethodParameter[Labels](steps.raw)

  implicit def toMethodParameterOut[Labels <: HList](
      steps: Steps[nodes.MethodParameterOut, Labels]): MethodParameterOut[Labels] =
    new MethodParameterOut[Labels](steps.raw)

  implicit def toMethodReturn[Labels <: HList](steps: Steps[nodes.MethodReturn, Labels]): MethodReturn[Labels] =
    new MethodReturn[Labels](steps.raw)

  implicit def toNamespace[Labels <: HList](steps: Steps[nodes.Namespace, Labels]): Namespace[Labels] =
    new Namespace[Labels](steps.raw)

  implicit def toNamespaceBlock[Labels <: HList](steps: Steps[nodes.NamespaceBlock, Labels]): NamespaceBlock[Labels] =
    new NamespaceBlock[Labels](steps.raw)

  implicit def toModifier[Labels <: HList](steps: Steps[nodes.Modifier, Labels]): Modifier[Labels] =
    new Modifier[Labels](steps.raw)

  implicit def toExpression[Labels <: HList](steps: Steps[nodes.Expression, Labels]): Expression[Labels] =
    new Expression[Labels](steps.raw)

  implicit def toDeclaration[Labels <: HList](steps: Steps[nodes.Declaration, Labels]): Declaration[Labels] =
    new Declaration[Labels](steps.raw)

  implicit def toFile[Labels <: HList](steps: Steps[nodes.File, Labels]): File[Labels] =
    new File[Labels](steps.raw)

  implicit def toTrackingPoint[NodeType <: nodes.TrackingPoint, Labels <: HList](
      steps: Steps[NodeType, Labels]): TrackingPoint[Labels] =
    new TrackingPoint[Labels](steps.raw.cast[nodes.TrackingPoint])
}
