package io.shiftleft.codepropertygraph.ext

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language.NodeTypeStarters

package object queryprimitives {
  implicit def toNodeTypeStarters(cpg: Cpg): NodeTypeStarters =
    new NodeTypeStarters(cpg)

}
