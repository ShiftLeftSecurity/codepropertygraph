package io.shiftleft.queryprimitives.dsl

import io.shiftleft.queryprimitives.dsl.ShallowPipe.ShallowPipe

import scala.collection.GenTraversableOnce

class ShallowPipeOperations[SrcType] extends PipeOperations[ShallowPipe, SrcType] {
  override def map[DstType](pipe: ShallowPipe[SrcType],
                            function: SrcType => DstType): ShallowPipe[DstType] = {
    function.apply(pipe)
  }

  override def flatMap[DstType](pipe: ShallowPipe[SrcType],
                                function: SrcType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    new RealPipe(function.apply(pipe).toList)
  }
}
