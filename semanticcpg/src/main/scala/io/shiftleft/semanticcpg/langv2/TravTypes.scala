package io.shiftleft.semanticcpg.langv2

import scala.collection.IterableOnceOps

trait TravTypes {
  type Collection[_]
  type CCOneToOne[_]
  type CCOneToOption[_]
  type CCOneToBoolean[_]
  type CCOneToMany[_]
}

abstract final class SingleTravTypes extends TravTypes {
  override type Collection[T] = T
  override type CCOneToOne[T] = T
  override type CCOneToOption[T] = Option[T]
  override type CCOneToBoolean[T] = Option[T]
  override type CCOneToMany[T] = Seq[T]
}

abstract final class OptionTravTypes extends TravTypes {
  override type Collection[T] = Option[T]
  override type CCOneToOne[T] = Option[T]
  override type CCOneToOption[T] = Option[T]
  override type CCOneToBoolean[T] = Option[T]
  override type CCOneToMany[T] = Seq[T]
}

abstract final class IterableOnceOpsTravTypes[CC[_], C] extends TravTypes {
  override type Collection[T] = IterableOnceOps[T, CC, C]
  override type CCOneToOne[T] = CC[T]
  override type CCOneToOption[T] = CC[T]
  override type CCOneToBoolean[T] = C
  override type CCOneToMany[T] = CC[T]
}

