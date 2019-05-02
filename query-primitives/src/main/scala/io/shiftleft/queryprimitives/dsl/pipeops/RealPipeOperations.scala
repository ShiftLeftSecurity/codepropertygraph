package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.RealPipe

import scala.collection.GenTraversableOnce

class RealPipeOperations[ElemType] extends PipeOperations[RealPipe, ElemType] {
  override def toRealPipe(pipe: RealPipe[ElemType]): RealPipe[ElemType] = {
    pipe
  }

  override def map[DstType](pipe: RealPipe[ElemType],
                            function: ElemType => DstType): RealPipe[DstType] = {
    new RealPipe(pipe.impl.map(function))
  }

  override def flatMap2[DstType](pipe: RealPipe[ElemType],
                                 function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    new RealPipe(pipe.impl.flatMap(function.apply))
  }

  override def flatMap[DstType](pipe: RealPipe[ElemType],
                                function: ElemType => RealPipe[DstType]): RealPipe[DstType] = {
    val applyAndUnwrap = (sourceElement: ElemType) => function.apply(sourceElement).impl
    new RealPipe(pipe.impl.flatMap(applyAndUnwrap))
  }

  override def filter(pipe: RealPipe[ElemType],
                      function: ElemType => Boolean): RealPipe[ElemType] = {
    new RealPipe(pipe.impl.filter(function))
  }

  override def head(pipe: RealPipe[ElemType]): ElemType = {
    pipe.impl.head
  }

  override def iterator(pipe: RealPipe[ElemType]): Iterator[ElemType] = {
    pipe.impl.iterator
  }

  override def toList(pipe: RealPipe[ElemType]): List[ElemType] = {
    pipe.impl
  }
}
