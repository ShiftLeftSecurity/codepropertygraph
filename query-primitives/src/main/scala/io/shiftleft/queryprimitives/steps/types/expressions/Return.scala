package io.shiftleft.queryprimitives.steps.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.ExpressionBase
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.{ArgumentIndexAccessors, CodeAccessors, LineNumberAccessors, OrderAccessors}
import shapeless.HList

// TODO: ColumnNumberAccessor missing

class Return[Labels <: HList](raw: GremlinScala.Aux[nodes.Return, Labels])
  extends NodeSteps[nodes.Return, Labels](raw)
  with ExpressionBase[nodes.Return, Labels]
  with LineNumberAccessors[nodes.Return, Labels]
  with OrderAccessors[nodes.Return, Labels]
  with ArgumentIndexAccessors[nodes.Return, Labels]
  with CodeAccessors[nodes.Return, Labels]
{

}
