package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe

import scala.collection.GenTraversableOnce
import scala.language.higherKinds

trait BasicPipeOperations[PipeType[+_], ElemType] {
  def toRealPipe(pipe: PipeType[ElemType]): RealPipe[ElemType]

  def map[DstType](pipe: PipeType[ElemType],
                   function: ElemType => DstType): PipeType[DstType]

  def flatMap[DstType](pipe: PipeType[ElemType],
                       function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType]

  def filter(pipe: PipeType[ElemType],
             function: ElemType => Boolean): RealPipe[ElemType]

  def head(pipe: PipeType[ElemType]): ElemType

  def toList(pipe: PipeType[ElemType]): List[ElemType]

  def iterator(pipe: PipeType[ElemType]): Iterator[ElemType]

  def foreach[DstType](pipe: PipeType[ElemType],
                       function: ElemType => DstType): Unit


}
