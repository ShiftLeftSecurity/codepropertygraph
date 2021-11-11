package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.language.MySteps.Pipe1

// PipeOps for the new pipeline attempt in MySteps.scala.

trait PipeOps[PipeT[_]] {
  type T1to1[T]
  type T1toOption[T]
  type T1toN[T]
  val T1to1FollowUpOps: PipeOps[T1to1]
  val T1toOptionFollowUpOps: PipeOps[T1toOption]
  val T1toNFollowUpOps: PipeOps[T1toN]
  def head[I](pipe: PipeT[I]): I
  def map[I, O](pipe: PipeT[I])(f: I => O): T1to1[O]
  def filter[I](pipe: PipeT[I])(f: I => Boolean): T1toOption[I]
  def flatMap[I, O](pipe: PipeT[I])(f: I => Iterable[O]): T1toN[O]
  def flatMapIter[I, O](pipe: PipeT[I])(f: I => Iterator[O]): T1toN[O]
}

object Pipe1Ops extends PipeOps[Pipe1] {
  override type T1to1[T] = Pipe1[T]
  override type T1toOption[T] = Option[T]
  override type T1toN[T] = Iterable[T]

  override val T1to1FollowUpOps: PipeOps[T1to1] = Pipe1Ops
  override val T1toOptionFollowUpOps: PipeOps[T1toOption] = PipeOptionOps
  override val T1toNFollowUpOps: PipeOps[Iterable] = PipeNIterableOps

  override def head[I](pipe: Pipe1[I]): I = {
    pipe
  }

  override def map[I, O](pipe: Pipe1[I])(f: I => O): T1to1[O] = {
    f(pipe)
  }

  override def filter[I](pipe: Pipe1[I])(f: I => Boolean): T1toOption[I] = {
    if (f(pipe)) {
      Some(pipe)
    } else {
      None
    }
  }

  override def flatMap[I, O](pipe: Pipe1[I])(f: I => Iterable[O]): T1toN[O] = {
    Iterable.from(f(pipe))
  }

  override def flatMapIter[I, O](pipe: Pipe1[I])(f: I => Iterator[O]): T1toN[O] = {
    Iterable.from(f(pipe))
  }
}

object PipeNIterableOps extends PipeOps[Iterable] {
  override type T1to1[T] = Iterable[T]
  override type T1toOption[T] = Iterable[T]
  override type T1toN[T] = Iterable[T]

  override val T1to1FollowUpOps: PipeOps[T1to1] = PipeNIterableOps
  override val T1toOptionFollowUpOps: PipeOps[T1toOption] = PipeNIterableOps
  override val T1toNFollowUpOps: PipeOps[Iterable] = PipeNIterableOps

  override def head[I](pipe: Iterable[I]): I = {
    pipe.head
  }

  override def map[I, O](pipe: Iterable[I])(f: I => O): T1to1[O] = {
    pipe.map(f)
  }

  override def filter[I](pipe: Iterable[I])(f: I => Boolean): T1toOption[I] = {
    pipe.filter(f)
  }

  override def flatMap[I, O](pipe: Iterable[I])(f: I => Iterable[O]): T1toN[O] = {
    pipe.flatMap(f)
  }

  override def flatMapIter[I, O](pipe: Iterable[I])(f: I => Iterator[O]): T1toN[O] = {
    pipe.flatMap(f)
  }
}

object PipeOptionOps extends PipeOps[Option] {
  override type T1to1[T] = Option[T]
  override type T1toOption[T] = Option[T]
  override type T1toN[T] = Iterable[T]

  override val T1to1FollowUpOps: PipeOps[T1to1] = PipeOptionOps
  override val T1toOptionFollowUpOps: PipeOps[T1toOption] = PipeOptionOps
  override val T1toNFollowUpOps: PipeOps[Iterable] = PipeNIterableOps

  override def head[I](pipe: Option[I]): I = {
    pipe.get
  }

  override def map[I, O](pipe: Option[I])(f: I => O): T1to1[O] = {
    pipe.map(f)
  }

  override def filter[I](pipe: Option[I])(f: I => Boolean): T1toOption[I] = {
    pipe.filter(f)
  }

  override def flatMap[I, O](pipe: Option[I])(f: I => Iterable[O]): T1toN[O] = {
    pipe match {
      case Some(p) =>
        f(p)
      case None =>
        Iterable.empty
    }
  }

  override def flatMapIter[I, O](pipe: Option[I])(f: I => Iterator[O]): T1toN[O] = {
    pipe match {
      case Some(p) =>
        Iterable.from(f(p))
      case None =>
        Iterable.empty
    }
  }
}
