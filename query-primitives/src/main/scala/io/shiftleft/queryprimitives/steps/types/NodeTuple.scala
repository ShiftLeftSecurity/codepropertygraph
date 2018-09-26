package io.shiftleft.queryprimitives.steps.types

import io.shiftleft.codepropertygraph.generated.nodes.StoredNode

object NodeTuple {}

/**
  * A node tuple, represented in the graph by `repr`, and defined by the product type R.
  *
  * To define a node tuple, add a node type to the CPG definition, then create a
  * case class in a `resulttypes` package, and finally, add a class that derives from
  * `NodeTuple`, where `T` is set to the type of the CPG node, and R is the type of
  * your case class.
  *
  * */
case class NodeTuple[T <: StoredNode, R <: scala.Product](repr: T, caseClass: R) {}
