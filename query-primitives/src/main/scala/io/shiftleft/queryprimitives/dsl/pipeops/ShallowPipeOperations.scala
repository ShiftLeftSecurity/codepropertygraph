package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.RealPipe
import io.shiftleft.queryprimitives.dsl.ShallowPipe.ShallowPipe

import scala.collection.GenTraversableOnce

class ShallowPipeOperations[ElemType] extends PipeOperations[ShallowPipe, ElemType] {
  override def toRealPipe(pipe: ShallowPipe[ElemType]): RealPipe[ElemType] = {
    new RealPipe(pipe :: Nil)
  }

  override def map[DstType](pipe: ShallowPipe[ElemType],
                            function: ElemType => DstType): ShallowPipe[DstType] = {
    function.apply(pipe)
  }

  override def flatMap2[DstType](pipe: ShallowPipe[ElemType],
                                 function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    new RealPipe(function.apply(pipe).toList)
  }

  override def flatMap[DstType](pipe: ShallowPipe[ElemType],
                                function: ElemType => RealPipe[DstType]): RealPipe[DstType] = {
    new RealPipe(function.apply(pipe).impl)
  }

  override def filter(pipe: ShallowPipe[ElemType],
                      function: ElemType => Boolean): RealPipe[ElemType] = {
    if (function.apply(pipe)){
      new RealPipe(pipe :: Nil)
    } else {
      new RealPipe(Nil)
    }
  }

  override def head(pipe: ShallowPipe[ElemType]): ElemType = {
    pipe
  }

  override def iterator(pipe: ShallowPipe[ElemType]): Iterator[ElemType] = {
    (pipe :: Nil).toIterator
  }

  override def toList(pipe: ShallowPipe[ElemType]): List[ElemType] = {
    pipe :: Nil
  }
}

