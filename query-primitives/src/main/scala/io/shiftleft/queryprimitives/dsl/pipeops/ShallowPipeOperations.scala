package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.RealPipe
import io.shiftleft.queryprimitives.dsl.ShallowPipe.ShallowPipe

import scala.collection.GenTraversableOnce

class ShallowPipeOperations extends PipeOperations[ShallowPipe] {
  override def toRealPipe[ElemType](pipe: ShallowPipe[ElemType]): RealPipe[ElemType] = {
    new RealPipe(pipe :: Nil)
  }

  override def map[SrcType, DstType](pipe: ShallowPipe[SrcType],
                                     function: SrcType => DstType): ShallowPipe[DstType] = {
    function.apply(pipe)
  }

  override def flatMap2[SrcType, DstType](pipe: ShallowPipe[SrcType],
                                          function: SrcType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    new RealPipe(function.apply(pipe).toList)
  }

  override def flatMap[SrcType, DstType](pipe: ShallowPipe[SrcType],
                                         function: SrcType => RealPipe[DstType]): RealPipe[DstType] = {
    new RealPipe(function.apply(pipe).impl)
  }

  override def filter[ElemType](pipe: ShallowPipe[ElemType],
                                function: ElemType => Boolean): RealPipe[ElemType] = {
    if (function.apply(pipe)){
      new RealPipe(pipe :: Nil)
    } else {
      new RealPipe(Nil)
    }
  }

  override def head[ElemType](pipe: ShallowPipe[ElemType]): ElemType = {
    pipe
  }
}

