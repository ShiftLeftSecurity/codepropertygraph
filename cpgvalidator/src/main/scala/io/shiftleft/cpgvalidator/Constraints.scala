package io.shiftleft.cpgvalidator

import ConstraintClasses._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}

object Constraints {

  val outConstraints = List(
    NodeTypes.METHOD has 1 to 2 outgoing EdgeTypes.AST to NodeTypes.METHOD_RETURN,
  )

  val inConstraints = List(
    NodeTypes.METHOD_RETURN has 1 incoming EdgeTypes.AST from NodeTypes.METHOD
  )


}
