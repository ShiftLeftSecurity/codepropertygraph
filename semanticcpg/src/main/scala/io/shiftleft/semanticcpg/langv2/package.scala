package io.shiftleft.semanticcpg

package object langv2 extends ExtensionClassImplicits with AnyTraversalImplicits with InstanceOfOpsImplicits {
  type Single[T] = T

  implicit val singleToOne: SingleToOne.type = SingleToOne
  implicit val optionToOne: OptionToOne.type = OptionToOne
  private val _iterToOne = new IterToOne()
  implicit def iterToOne[CC[_], C]: IterToOne[CC, C] = _iterToOne.asInstanceOf[IterToOne[CC, C]]

  implicit val singleToBoolean: SingleToBoolean.type = SingleToBoolean
  implicit val optionToBoolean: OptionToBoolean.type = OptionToBoolean
  private val _iterToBoolean = new IterToBoolean()
  implicit def iterToBoolean[CC[_], C]: IterToBoolean[CC, C] = _iterToBoolean.asInstanceOf[IterToBoolean[CC, C]]

  implicit val singleToOption: SingleToOption.type = SingleToOption
  implicit val optionToOption: OptionToOption.type = OptionToOption
  private val _iterToOption = new IterToOption()
  implicit def iterToOption[CC[_], C]: IterToOption[CC, C] = _iterToOption.asInstanceOf[IterToOption[CC, C]]

  implicit val singleToMany: SingleToMany.type = SingleToMany
  implicit val optionToMany: OptionToMany.type = OptionToMany
  private val _iterToMany = new IterToMany()
  implicit def iterToMany[CC[_], C]: IterToMany[CC, C] = _iterToMany.asInstanceOf[IterToMany[CC, C]]

}
