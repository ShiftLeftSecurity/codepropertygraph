package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.ExpressionBase
import io.shiftleft.semanticcpg.language.types.propertyaccessors.LineNumberAccessors

class MethodRef(raw: GremlinScala[nodes.MethodRef])
    extends NodeSteps[nodes.MethodRef](raw)
    with ExpressionBase[nodes.MethodRef]
    with LineNumberAccessors[nodes.MethodRef] {}
