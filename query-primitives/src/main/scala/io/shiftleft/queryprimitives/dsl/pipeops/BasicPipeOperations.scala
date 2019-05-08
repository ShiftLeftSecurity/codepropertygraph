package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.pipetypes.ShallowPipe.ShallowPipe

import scala.collection.GenTraversableOnce
import scala.language.higherKinds

trait BasicPipeOperations[PipeType[+_]] {
  private[dsl] def map[DstType, ElemType]
  (pipe: PipeType[ElemType],
   function: ElemType => DstType): PipeType[DstType]

  private[dsl] def flatMap[DstType, ElemType]
  (pipe: PipeType[ElemType],
   function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType]

  private[dsl] def filter[ElemType]
  (pipe: PipeType[ElemType],
   function: ElemType => Boolean): RealPipe[ElemType]

  private[dsl] def head[ElemType]
  (pipe: PipeType[ElemType]): ElemType

  private[dsl] def toList[ElemType]
  (pipe: PipeType[ElemType]): List[ElemType]

  private[dsl] def toSet[ElemType]
  (pipe: PipeType[ElemType]): Set[ElemType]

  private[dsl] def iterator[ElemType]
  (pipe: PipeType[ElemType]): Iterator[ElemType]

  private[dsl] def foreach[DstType, ElemType]
  (pipe: PipeType[ElemType],
   function: ElemType => DstType): Unit

  private[dsl] def append[ElemType]
  (pipe: PipeType[ElemType],
   otherPipe: RealPipe[ElemType]): RealPipe[ElemType]

  private[dsl] def append[ElemType]
  (pipe: PipeType[ElemType],
   otherPipe: ShallowPipe[ElemType]): RealPipe[ElemType]


}
