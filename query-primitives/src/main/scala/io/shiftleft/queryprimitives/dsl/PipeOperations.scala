package io.shiftleft.queryprimitives.dsl

import scala.collection.GenTraversableOnce

trait PipeOperations[PipeType[_], SrcType] {
  def map[DstType](pipe: PipeType[SrcType],
                   function: SrcType => DstType): PipeType[DstType]

  def flatMap[DstType](pipe: PipeType[SrcType],
                       function: SrcType => GenTraversableOnce[DstType]): RealPipe[DstType]
}
