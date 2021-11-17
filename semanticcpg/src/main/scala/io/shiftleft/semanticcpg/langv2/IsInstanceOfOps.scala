package io.shiftleft.semanticcpg.langv2

import io.shiftleft.codepropertygraph.generated.nodes

trait IsInstanceOfOps[T] extends Function1[Any, Boolean]

trait InstanceOfOpsImplicits {
  implicit val methodReturnCollectOps: IsInstanceOfOps[nodes.MethodReturn] = _.isInstanceOf[nodes.MethodReturn]
  implicit val astNodeCollectOps: IsInstanceOfOps[nodes.AstNode] = _.isInstanceOf[nodes.AstNode]
}
