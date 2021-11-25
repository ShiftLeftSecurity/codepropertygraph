package io.shiftleft.semanticcpg

package object langv2 extends ExtensionClassImplicits with AnyTraversalImplicits with InstanceOfOpsImplicits {
  type Single[T] = T

  implicit val singleOps = SingleOps
  implicit val optionOps = OptionOps
  private val it2Ops = new IterableOnceOpsOps()
  implicit def toIt2Ops[CC[_], C] = it2Ops.asInstanceOf[IterableOnceOpsOps[CC, C]]

}
