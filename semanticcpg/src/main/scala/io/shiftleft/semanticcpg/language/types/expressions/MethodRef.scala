package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.ExpressionBase
import io.shiftleft.semanticcpg.language.types.propertyaccessors.LineNumberAccessors
import io.shiftleft.semanticcpg.language.types.structure.Method

class MethodRef(raw: GremlinScala[nodes.MethodRef])
    extends NodeSteps[nodes.MethodRef](raw)
    with ExpressionBase[nodes.MethodRef]
    with LineNumberAccessors[nodes.MethodRef] {

  /**
    * Traverse to referenced method.
    * */
  def referencedMethod: Method =
    new Method(raw.out(EdgeTypes.REF).cast[nodes.Method])
}
