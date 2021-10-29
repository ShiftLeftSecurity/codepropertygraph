package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.language.MySteps.Pipe1


trait PipeOps[PipeT[_], C[_]] {
  type T1to1[T]
  type T1toOption[T]
  type T1toN[T] = C[T]
  def map[I, O](pipe: PipeT[I])(f: I => O): T1to1[O]
  def filter[I](pipe: PipeT[I])(f: I => Boolean): T1toOption[I]
  def flatMap[I, O](pipe: PipeT[I])(f: I => Iterable[O]): T1toN[O]
  def flatMapIter[I, O](pipe: PipeT[I])(f: I => Iterator[O]): T1toN[O]
}

class Pipe1Ops extends PipeOps[Pipe1, Iterable] {
  override type T1to1[T] = Pipe1[T]
  override type T1toOption[T] = Option[T]

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

class PipeNIterableOps extends PipeOps[Iterable, Iterable] {
  override type T1to1[T] = Iterable[T]
  override type T1toOption[T] = Iterable[T]

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

