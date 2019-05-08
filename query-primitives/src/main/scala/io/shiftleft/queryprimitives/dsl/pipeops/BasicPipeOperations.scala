package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe

import scala.collection.GenTraversableOnce
import scala.language.higherKinds

trait BasicPipeOperations[PipeType[+_], ElemType] {
  private[dsl] def map[DstType](pipe: PipeType[ElemType],
                                function: ElemType => DstType): PipeType[DstType]

  private[dsl] def flatMap[DstType](pipe: PipeType[ElemType],
                                    function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType]

  private[dsl] def filter(pipe: PipeType[ElemType],
                          function: ElemType => Boolean): RealPipe[ElemType]

  private[dsl] def head(pipe: PipeType[ElemType]): ElemType

  private[dsl] def toList(pipe: PipeType[ElemType]): List[ElemType]

  private[dsl] def toSet(pipe: PipeType[ElemType]): Set[ElemType]

  private[dsl] def iterator(pipe: PipeType[ElemType]): Iterator[ElemType]

  private[dsl] def foreach[DstType](pipe: PipeType[ElemType],
                                    function: ElemType => DstType): Unit


}
