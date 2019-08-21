package io.shiftleft.queryprimitives

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.queryprimitives.steps.starters.NodeTypeStarters
import shapeless.HList

object Implicits {

  implicit def toNodeTypeStarters(cpg: Cpg): NodeTypeStarters =
    new NodeTypeStarters(cpg)

  implicit class GremlinScalaDeco[End, Labels <: HList](raw: GremlinScala.Aux[End, Labels]) {
    /* in some cases we cannot statically determine the type of the node, e.g. when traversing
     * from a known nodeType via AST edges, so we have to cast */
    def cast[NodeType]: GremlinScala.Aux[NodeType, Labels] =
      raw.asInstanceOf[GremlinScala.Aux[NodeType, Labels]]
  }

}
