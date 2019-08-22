package io.shiftleft.semanticcpg

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.nodemethods.{AstNodeMethods, CfgNodeMethods, WithinMethodMethods}
import io.shiftleft.semanticcpg.language.types.structure._
import io.shiftleft.semanticcpg.language.types.expressions._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations._
import shapeless.HList

/**
  Language for traversing the code property graph

  Implicit conversions to specific steps, based on the node at hand.
  Automatically in scope when using anything in the `steps` package, e.g. `Steps`
  */
package object language {

  // Implicit conversions from generated node types. We use these to add methods
  // to generated node types.

  implicit def withMethodMethodsQp(node: nodes.WithinMethod): WithinMethodMethods =
    new WithinMethodMethods(node)

  implicit def astNodeMethodsQp(node: nodes.AstNode): AstNodeMethods =
    new AstNodeMethods(node)

  // Implicit conversions from Step[NodeType, Label] to corresponding Step classes.
  // If you introduce a new Step-type, that is, one that inherits from `Steps[NodeType,Labels]`,
  // then you need to add an implicit conversion from `Steps[NodeType,Labels]` to your type
  // here.

  implicit def toLiteral[Labels <: HList](steps: Steps[nodes.Literal, Labels]): Literal[Labels] =
    new Literal[Labels](steps.raw)

  implicit def toType[Labels <: HList](steps: Steps[nodes.Type, Labels]): Type[Labels] =
    new Type[Labels](steps.raw)

  implicit def toTypeDecl[Labels <: HList](steps: Steps[nodes.TypeDecl, Labels]): TypeDecl[Labels] =
    new TypeDecl[Labels](steps.raw)

  implicit def toCall[Labels <: HList](steps: Steps[nodes.Call, Labels]): Call[Labels] =
    new Call[Labels](steps.raw)

  implicit def toControlStructure[Labels <: HList](
      steps: Steps[nodes.ControlStructure, Labels]): ControlStructure[Labels] =
    new ControlStructure[Labels](steps.raw)

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

  implicit def toCfgNode[Labels <: HList](steps: Steps[nodes.CfgNode, Labels]): CfgNode[Labels] =
    new CfgNode[Labels](steps.raw)

  implicit def toAstNode[Labels <: HList](steps: Steps[nodes.AstNode, Labels]): AstNode[Labels] =
    new AstNode[Labels](steps.raw)

  implicit def toFile[Labels <: HList](steps: Steps[nodes.File, Labels]): File[Labels] =
    new File[Labels](steps.raw)

  implicit def toBlock[Labels <: HList](steps: Steps[nodes.Block, Labels]): Block[Labels] =
    new Block[Labels](steps.raw)

  implicit class GremlinScalaDeco[End, Labels <: HList](raw: GremlinScala.Aux[End, Labels]) {
    /* in some cases we cannot statically determine the type of the node, e.g. when traversing
     * from a known nodeType via AST edges, so we have to cast */
    def cast[NodeType]: GremlinScala.Aux[NodeType, Labels] =
      raw.asInstanceOf[GremlinScala.Aux[NodeType, Labels]]
  }

}
