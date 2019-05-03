package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe

import scala.collection.GenTraversableOnce

class RealPipeOperations[ElemType] extends PipeOperations[RealPipe, ElemType] {

  override def map[DstType](pipe: RealPipe[ElemType],
                            function: ElemType => DstType): RealPipe[DstType] = {
    RealPipe(RealPipe.unwrap(pipe).map(function))
  }

  override def flatMap[DstType](pipe: RealPipe[ElemType],
                                function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    RealPipe(RealPipe.unwrap(pipe).flatMap(function))
  }

  override def filter(pipe: RealPipe[ElemType],
                      function: ElemType => Boolean): RealPipe[ElemType] = {
    RealPipe(RealPipe.unwrap(pipe).filter(function))
  }

  override def head(pipe: RealPipe[ElemType]): ElemType = {
    RealPipe.unwrap(pipe).head
  }

  override def iterator(pipe: RealPipe[ElemType]): Iterator[ElemType] = {
    RealPipe.unwrap(pipe).iterator
  }

  override def toList(pipe: RealPipe[ElemType]): List[ElemType] = {
    RealPipe.unwrap(pipe)
  }

  override def foreach[DstType](pipe: RealPipe[ElemType],
                                function: ElemType => DstType): Unit = {
    RealPipe.unwrap(pipe).foreach(function)
  }
}
