package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.RealPipe

import scala.collection.GenTraversableOnce

trait PipeOperations[PipeType[+_]] {
  def toRealPipe[ElemType](pipe: PipeType[ElemType]): RealPipe[ElemType]

  def map[SrcType, DstType](pipe: PipeType[SrcType],
                            function: SrcType => DstType): PipeType[DstType]

  def flatMap2[SrcType, DstType](pipe: PipeType[SrcType],
                                 function: SrcType => GenTraversableOnce[DstType]): RealPipe[DstType]

  def flatMap[SrcType, DstType](pipe: PipeType[SrcType],
                                function: SrcType => RealPipe[DstType]): RealPipe[DstType]

  def filter[ElemType](pipe: PipeType[ElemType],
                       function: ElemType => Boolean): RealPipe[ElemType]

  def head[ElemType](pipe: PipeType[ElemType]): ElemType

  def toList[ElemType](pipe: PipeType[ElemType]): List[ElemType]

  def iterator[ElemType](pipe: PipeType[ElemType]): Iterator[ElemType]

  def map[ElemType, SuperType >: ElemType](pipe: PipeType[ElemType],
                                           function: SuperType => SuperType,
                                           times: Int): PipeType[SuperType] = {
    var currentPipe: PipeType[SuperType] = pipe
    for (_ <- 0 until times) {
      currentPipe = map(pipe, function)
    }
    currentPipe
  }

  def map[ElemType, SuperType >: ElemType](pipe: PipeType[ElemType],
                                           function: SuperType => SuperType,
                                           until: SuperType => Boolean): PipeType[SuperType] = {
    // TODO implement
    pipe
  }

  def flatMap[ElemType, SuperType >: ElemType](pipe: PipeType[ElemType],
                                               function: SuperType => GenTraversableOnce[SuperType],
                                               times: Int): RealPipe[SuperType] = {
    var currentPipe: RealPipe[SuperType] = toRealPipe(pipe)
    for (_ <- 0 until times) {
      currentPipe = flatMap2(pipe, function)
    }
    currentPipe
  }

  def flatMap3[ElemType, SuperType >: ElemType](pipe: PipeType[ElemType],
                                                function: SuperType => GenTraversableOnce[SuperType],
                                                until: SuperType => Boolean): RealPipe[SuperType] = {
    var stack: List[SuperType] = toList(pipe)
    var builder = RealPipe.newBuilder[SuperType]

    while (stack.nonEmpty) {
      val elem = stack.head
      stack = stack.tail

      if (until(elem)) {
        builder += elem
      } else {
        stack = function(elem).toList ::: stack
      }
    }
    builder.result
  }
}
