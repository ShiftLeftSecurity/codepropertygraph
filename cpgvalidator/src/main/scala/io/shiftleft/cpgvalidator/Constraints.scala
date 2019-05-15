package io.shiftleft.cpgvalidator

import ConstraintClasses._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}

object Constraints {

  val outConstraints = List(
    NodeTypes.METHOD has 1 to 2 outgoing EdgeTypes.AST to NodeTypes.METHOD_RETURN,
    NodeTypes.METHOD has 0 to N outgoing EdgeTypes.AST to NodeTypes.METHOD_PARAMETER_IN,
    NodeTypes.METHOD has 0 to N outgoing EdgeTypes.AST to NodeTypes.MODIFIER,
    NodeTypes.METHOD has 1 outgoing EdgeTypes.AST to NodeTypes.BLOCK,
    NodeTypes.METHOD has 0 to N outgoing EdgeTypes.AST to NodeTypes.TYPE_PARAMETER,

  )

  val inConstraints = List(
    NodeTypes.METHOD_RETURN has 1 incoming EdgeTypes.AST from NodeTypes.METHOD
  )


}
