package io.shiftleft.queryprimitives.steps.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.ExpressionBase
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.{LineNumberAccessors, MethodInstFullNameAccessors}
import shapeless.HList

class MethodRef[Labels <: HList](raw: GremlinScala.Aux[nodes.MethodRef, Labels])
    extends NodeSteps[nodes.MethodRef, Labels](raw)
    with ExpressionBase[nodes.MethodRef, Labels]
    with LineNumberAccessors[nodes.MethodRef, Labels]
    with MethodInstFullNameAccessors[nodes.MethodRef, Labels] {}
