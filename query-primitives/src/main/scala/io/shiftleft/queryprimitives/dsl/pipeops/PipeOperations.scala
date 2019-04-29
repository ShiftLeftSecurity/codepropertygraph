package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.RealPipe

import scala.collection.GenTraversableOnce

/*
trait PipeOperations[PipeType[+_], +ElemType] {
  def toRealPipe[SuperType >: ElemType]
  (pipe: PipeType[SuperType]): RealPipe[SuperType]

  def map[SuperType >: ElemType, DstType]
  (pipe: PipeType[SuperType],
   function: SuperType => DstType): PipeType[DstType]

  def flatMap2[SuperType >: ElemType, DstType]
  (pipe: PipeType[SuperType],
   function: SuperType => GenTraversableOnce[DstType]): RealPipe[DstType]

  def flatMap[SuperType >: ElemType, DstType]
  (pipe: PipeType[SuperType],
   function: SuperType => RealPipe[DstType]): RealPipe[DstType]

  def filter[SuperType >: ElemType]
  (pipe: PipeType[SuperType],
   function: SuperType => Boolean): RealPipe[SuperType]

  def head[SuperType >: ElemType]
  (pipe: PipeType[SuperType]): SuperType

  def toList[SuperType >: ElemType]
  (pipe: PipeType[SuperType]): List[SuperType]

  def iterator[SuperType >: ElemType]
  (pipe: PipeType[SuperType]): Iterator[SuperType]

  def map[SuperType >: ElemType]
  (pipe: PipeType[SuperType],
   function: SuperType => SuperType,
   times: Int): PipeType[SuperType] = {
    var currentPipe: PipeType[SuperType] = pipe
    for (_ <- 0 until times) {
      currentPipe = map(pipe, function)
    }
    currentPipe
  }

  def map[SuperType >: ElemType]
  (pipe: PipeType[SuperType],
   function: SuperType => SuperType,
   until: SuperType => Boolean): PipeType[SuperType] = {
    // TODO implement
    pipe
  }

  def flatMap[SuperType >: ElemType]
  (pipe: PipeType[SuperType],
   function: SuperType => GenTraversableOnce[SuperType],
   times: Int): RealPipe[SuperType] = {
    var currentPipe = toRealPipe(pipe)
    for (_ <- 0 until times) {
      currentPipe = flatMap2(pipe, function)
    }
    currentPipe
  }

  def flatMap3[SuperType >: ElemType]
  (pipe: PipeType[SuperType],
   function: SuperType => GenTraversableOnce[SuperType],
   until: SuperType => Boolean): RealPipe[SuperType] = {
    var stack = toList(pipe)
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

 */
