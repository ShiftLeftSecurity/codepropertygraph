package io.shiftleft.queryprimitives.dsl

import io.shiftleft.queryprimitives.dsl.pipeops.Pipe

import scala.collection.GenTraversableOnce

object RealPipe {
  def newBuilder[ElemType]: RealPipeBuilder[ElemType] = {
    new RealPipeBuilder()
  }
}

class RealPipe[+ElemType](val impl: List[ElemType]) extends AnyVal with Pipe[ElemType] {
  override def toRealPipe: RealPipe[ElemType] = ???

  override def map[DstType](function: ElemType => DstType): Pipe[DstType] = ???

  override def flatMap2[DstType](function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = ???

  override def flatMap[DstType](function: ElemType => RealPipe[DstType]): RealPipe[DstType] = ???

  override def filter(function: ElemType => Boolean): RealPipe[ElemType] = ???

  override def head: ElemType = ???

  override def toList: List[ElemType] = ???

  override def iterator: Iterator[ElemType] = ???
}
