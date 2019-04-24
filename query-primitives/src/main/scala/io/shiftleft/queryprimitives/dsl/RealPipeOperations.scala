package io.shiftleft.queryprimitives.dsl

import scala.collection.GenTraversableOnce

class RealPipeOperations[SrcType] extends PipeOperations[RealPipe, SrcType] {
  override def map[DstType](pipe: RealPipe[SrcType],
                            function: SrcType => DstType): RealPipe[DstType] = {
    new RealPipe(pipe.impl.map(function))
  }

  override def flatMap[DstType](pipe: RealPipe[SrcType],
                                function: SrcType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    new RealPipe(pipe.impl.flatMap(function.apply))
  }
}
