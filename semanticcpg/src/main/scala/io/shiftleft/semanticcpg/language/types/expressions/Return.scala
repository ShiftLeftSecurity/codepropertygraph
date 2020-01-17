package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.ExpressionBase
import io.shiftleft.semanticcpg.language.types.propertyaccessors.{
  ArgumentIndexAccessors,
  CodeAccessors,
  OrderAccessors
}

// TODO: ColumnNumberAccessor missing

class Return(raw: GremlinScala[nodes.Return])
    extends NodeSteps[nodes.Return](raw)
    with ExpressionBase[nodes.Return]
    with OrderAccessors[nodes.Return]
    with ArgumentIndexAccessors[nodes.Return]
    with CodeAccessors[nodes.Return] {}
