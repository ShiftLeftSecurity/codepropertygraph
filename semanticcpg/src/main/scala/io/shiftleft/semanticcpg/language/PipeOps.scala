package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.language.MySteps.{Pipe1, PipeN, PipeNGenerator}


trait PipeOps[C[_], PipeT[U[_], _]] {
  def map[I, O](pipe: PipeT[C, I])(f: I => O): PipeT[C, O]
  def filter[I](pipe: PipeT[C, I])(f: I => Boolean)(implicit gen: PipeNGenerator[C]): PipeN[C, I]
  def flatMap[I, O](pipe: PipeT[C, I])(f: I => Iterable[O])(implicit gen: PipeNGenerator[C]): PipeN[C, O]
  def flatMapIter[I, O](pipe: PipeT[C, I])(f: I => Iterator[O])(implicit gen: PipeNGenerator[C]): PipeN[C, O]
}

class Pipe1Ops extends PipeOps[Iterable, Pipe1] {
  override def map[I, O](pipe: Pipe1[Iterable, I])(f: I => O): Pipe1[Iterable, O] = {
    f(pipe)
  }

  override def filter[I](pipe: Pipe1[Iterable, I])(f: I => Boolean)(implicit gen: PipeNGenerator[Iterable]): PipeN[Iterable, I] = {
    if (f(pipe)) {
      gen.single(pipe)
    } else {
      gen.empty()
    }
  }

  override def flatMap[I, O](pipe: Pipe1[Iterable, I])(f: I => Iterable[O])(implicit gen: PipeNGenerator[Iterable]): PipeN[Iterable, O] = {
    gen.multi(f(pipe))
  }

  override def flatMapIter[I, O](pipe: Pipe1[Iterable, I])(f: I => Iterator[O])(implicit gen: PipeNGenerator[Iterable]): PipeN[Iterable, O] = {
    gen.multi(f(pipe))
  }
}

class PipeNIterableOps extends PipeOps[Iterable, PipeN] {
  override def map[I, O](pipe: PipeN[Iterable, I])(f: I => O): PipeN[Iterable, O] = {
    pipe.map(f)
  }

  override def filter[I](pipe: PipeN[Iterable, I])(f: I => Boolean)(implicit gen: PipeNGenerator[Iterable]): PipeN[Iterable, I] = {
    pipe.filter(f)
  }

  override def flatMap[I, O](pipe: PipeN[Iterable, I])(f: I => Iterable[O])(implicit gen: PipeNGenerator[Iterable]): PipeN[Iterable, O] = {
    pipe.flatMap(f)
  }

  override def flatMapIter[I, O](pipe: PipeN[Iterable, I])(f: I => Iterator[O])(implicit gen: PipeNGenerator[Iterable]): PipeN[Iterable, O] = {
    pipe.flatMap(f)
  }
}

