package io.shiftleft.queryprimitives

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.queryprimitives.steps.starters.NodeTypeStarters

object Implicits {

  implicit def toNodeTypeStarters(cpg: Cpg): NodeTypeStarters =
    new NodeTypeStarters(cpg)

}
