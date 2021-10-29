package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.language.MySteps.{Pipe1, PipeNGenerator}


trait PipeOps[PipeT[_], C[_]] {
  type T1to1[T]
  type T1toN[T] = C[T]
  def map[I, O](pipe: PipeT[I])(f: I => O): T1to1[O]
  def filter[I](pipe: PipeT[I])(f: I => Boolean)(implicit gen: PipeNGenerator[C]): T1toN[I]
  def flatMap[I, O](pipe: PipeT[I])(f: I => Iterable[O])(implicit gen: PipeNGenerator[C]): T1toN[O]
  def flatMapIter[I, O](pipe: PipeT[I])(f: I => Iterator[O])(implicit gen: PipeNGenerator[C]): T1toN[O]
}

class Pipe1Ops extends PipeOps[Pipe1, Iterable] {
  override type T1to1[T] = Pipe1[T]

  override def map[I, O](pipe: Pipe1[I])(f: I => O): T1to1[O] = {
    f(pipe)
  }

  override def filter[I](pipe: Pipe1[I])(f: I => Boolean)(implicit gen: PipeNGenerator[Iterable]): T1toN[I] = {
    if (f(pipe)) {
      gen.single(pipe)
    } else {
      gen.empty()
    }
  }

  override def flatMap[I, O](pipe: Pipe1[I])(f: I => Iterable[O])(implicit gen: PipeNGenerator[Iterable]): T1toN[O] = {
    gen.multi(f(pipe))
  }

  override def flatMapIter[I, O](pipe: Pipe1[I])(f: I => Iterator[O])(implicit gen: PipeNGenerator[Iterable]): T1toN[O] = {
    gen.multi(f(pipe))
  }
}

class PipeNIterableOps extends PipeOps[Iterable, Iterable] {
  override type T1to1[T] = Iterable[T]

  override def map[I, O](pipe: Iterable[I])(f: I => O): T1to1[O] = {
    pipe.map(f)
  }

  override def filter[I](pipe: Iterable[I])(f: I => Boolean)(implicit gen: PipeNGenerator[Iterable]): T1toN[I] = {
    pipe.filter(f)
  }

  override def flatMap[I, O](pipe: Iterable[I])(f: I => Iterable[O])(implicit gen: PipeNGenerator[Iterable]): T1toN[O] = {
    pipe.flatMap(f)
  }

  override def flatMapIter[I, O](pipe: Iterable[I])(f: I => Iterator[O])(implicit gen: PipeNGenerator[Iterable]): T1toN[O] = {
    pipe.flatMap(f)
  }
}

