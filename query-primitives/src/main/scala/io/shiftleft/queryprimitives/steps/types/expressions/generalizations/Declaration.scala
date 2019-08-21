package io.shiftleft.queryprimitives.steps.types.expressions.generalizations

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.NodeSteps
import shapeless.HList

class Declaration[Labels <: HList](raw: GremlinScala.Aux[nodes.Declaration, Labels])
    extends NodeSteps[nodes.Declaration, Labels](raw)
    with DeclarationBase[nodes.Declaration, Labels]

trait DeclarationBase[NodeType <: nodes.Declaration, Labels <: HList] {
  this: NodeSteps[NodeType, Labels] =>
  // TODO: steps for Declarations go here
}
