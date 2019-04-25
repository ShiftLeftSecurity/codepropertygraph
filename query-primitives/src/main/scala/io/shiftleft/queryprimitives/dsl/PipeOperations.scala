package io.shiftleft.queryprimitives.dsl

import scala.collection.GenTraversableOnce

trait PipeOperations[PipeType[_]] {
  def toRealPipe[ElemType](pipe: PipeType[ElemType]): RealPipe[ElemType]

  def map[SrcType, DstType](pipe: PipeType[SrcType],
                            function: SrcType => DstType): PipeType[DstType]

  def flatMap[SrcType, DstType](pipe: PipeType[SrcType],
                                function: SrcType => GenTraversableOnce[DstType]): RealPipe[DstType]

  def flatMap[SrcType, DstType](pipe: PipeType[SrcType],
                                function: SrcType => RealPipe[DstType])
                               (implicit unused: TypeErasureResolve1): RealPipe[DstType]

  def filter[ElemType](pipe: PipeType[ElemType],
                       function: ElemType => Boolean): RealPipe[ElemType]

  def head[ElemType](pipe: PipeType[ElemType]): ElemType

  def map[ElemType](pipe: PipeType[ElemType],
                    function: ElemType => ElemType,
                    times: Int): PipeType[ElemType] = {
    var currentPipe = pipe
    for (_ <- 0 until times) {
      currentPipe = map(pipe, function)
    }
    currentPipe
  }

  def map[ElemType](pipe: PipeType[ElemType],
                    function: ElemType => ElemType,
                    until: ElemType => Boolean): PipeType[ElemType] = {
    // TODO implement
    pipe
  }

  def flatMap[ElemType](pipe: PipeType[ElemType],
                        function: ElemType => GenTraversableOnce[ElemType],
                        times: Int): RealPipe[ElemType] = {
    var currentPipe = toRealPipe(pipe)
    for (_ <- 0 until times) {
      currentPipe = flatMap(pipe, function)
    }
    currentPipe
  }
}
