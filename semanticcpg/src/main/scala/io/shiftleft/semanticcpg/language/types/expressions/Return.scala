package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.ExpressionBase
import io.shiftleft.semanticcpg.language.types.propertyaccessors.{
  ArgumentIndexAccessors,
  CodeAccessors,
  LineNumberAccessors,
  OrderAccessors
}
import shapeless.HList

// TODO: ColumnNumberAccessor missing

class Return[Labels <: HList](raw: GremlinScala.Aux[nodes.Return, Labels])
    extends NodeSteps[nodes.Return, Labels](raw)
    with ExpressionBase[nodes.Return, Labels]
    with LineNumberAccessors[nodes.Return, Labels]
    with OrderAccessors[nodes.Return, Labels]
    with ArgumentIndexAccessors[nodes.Return, Labels]
    with CodeAccessors[nodes.Return, Labels] {}
