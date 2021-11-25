package io.shiftleft.semanticcpg.langv2

import scala.collection.IterableOnceOps

class IterTypes[_CC[_], _C] {
  type CC[T] = _CC[T]
  type C = _C
}

trait ToOne[_IN[_], ExtraTypes] {
  type IN[T] = _IN[T]
  type OUT[_]

  def apply[I, O](in: IN[I])(f: I => O): OUT[O]
}

object SingleToOne extends ToOne[Single, Nothing] {
  override type OUT[T] = T

  override def apply[I, O](in: IN[I])(f: I => O): OUT[O] = f(in)
}

object OptionToOne extends ToOne[Option, Nothing] {
  override type OUT[T] = Option[T]

  override def apply[I, O](in: IN[I])(f: I => O): OUT[O] = in.map(f)
}

class IterToOne[CC[_], C] extends ToOne[({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]] {
  override type OUT[T] = CC[T]

  override def apply[I, O](in: IN[I])(f: I => O): OUT[O] = in.map(f)
}

trait ToBoolean[_IN[_], ExtraTypes] {
  type IN[T] = _IN[T]
  type OUT[_]

  def apply[I, O](in: IN[I])(f: I => Boolean): OUT[I]
}

object SingleToBoolean extends ToBoolean[Single, Nothing] {
  override type OUT[T] = Option[T]

  override def apply[I, O](in: IN[I])(f: I => Boolean): OUT[I] =
    if (f(in)) {
      Some(in)
    } else {
      None
    }
}

object OptionToBoolean extends ToBoolean[Option, Nothing] {
  override type OUT[T] = Option[T]

  override def apply[I, O](in: IN[I])(f: I => Boolean): OUT[I] = in.filter(f)
}

class IterToBoolean[CC[_], C] extends ToBoolean[({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]] {
  override type OUT[T] = C

  override def apply[I, O](in: IN[I])(f: I => Boolean): OUT[I] = in.filter(f)
}

trait ToOption[_IN[_], ExtraTypes] {
  type IN[T] = _IN[T]
  type OUT[_]

  def apply[I, O](in: IN[I])(f: I => Option[O]): OUT[O]
}

object SingleToOption extends ToOption[Single, Nothing] {
  override type OUT[T] = Option[T]

  override def apply[I, O](in: IN[I])(f: I => Option[O]): OUT[O] = f(in)
}

object OptionToOption extends ToOption[Option, Nothing] {
  override type OUT[T] = Option[T]

  override def apply[I, O](in: IN[I])(f: I => Option[O]): OUT[O] = in.flatMap(f)
}

class IterToOption[CC[_], C] extends ToOption[({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]] {
  override type OUT[T] = CC[T]

  override def apply[I, O](in: IN[I])(f: I => Option[O]): OUT[O] = in.flatMap(f)
}

trait ToMany[_IN[_], ExtraTypes] {
  type IN[T] = _IN[T]
  type OUT[_]

  def apply[I, O](in: IN[I])(f: I => IterableOnce[O]): OUT[O]
}

object SingleToMany extends ToMany[Single, Nothing] {
  override type OUT[T] = Seq[T]

  override def apply[I, O](in: IN[I])(f: I => IterableOnce[O]): OUT[O] = Seq.from(f(in))
}

object OptionToMany extends ToMany[Option, Nothing] {
  override type OUT[T] = Seq[T]

  override def apply[I, O](in: IN[I])(f: I => IterableOnce[O]): OUT[O] =
    in match {
      case Some(i) =>
        Seq.from(f(i))
      case None =>
        Seq.empty
    }
}

class IterToMany[CC[_], C] extends ToMany[({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]] {
  override type OUT[T] = CC[T]

  override def apply[I, O](in: IN[I])(f: I => IterableOnce[O]): OUT[O] = in.flatMap(f)
}
