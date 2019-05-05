package io.shiftleft.codepropertygraph

import io.shiftleft.codepropertygraph.generated.{nodes => cpgnodes}
import io.shiftleft.queryprimitives.steps.types.structure.MethodNode

package object nodes {

  implicit def toMethodNode(node : cpgnodes.Method) : MethodNode =
    new MethodNode(node)

}
