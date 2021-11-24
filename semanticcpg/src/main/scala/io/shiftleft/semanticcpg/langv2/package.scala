package io.shiftleft.semanticcpg

package object langv2 extends ExtensionClassImplicits with AnyTraversalImplicits with InstanceOfOpsImplicits {
  type Single[T] = T

  implicit val singleOps = SingleOps
  implicit val optionOps = OptionOps
  implicit val iteratorOps = IteratorOps
  private val it2Ops = new IterableOps()
  implicit def toIt2Ops[IT[T] <: Iterable[T]] = it2Ops.asInstanceOf[IterableOps[IT]]

}
