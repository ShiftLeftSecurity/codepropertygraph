package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.RealPipe

import scala.collection.GenTraversableOnce

trait Pipe[+ElemType] extends Any {
  def toRealPipe: RealPipe[ElemType]

  def map[DstType] (function: ElemType => DstType): Pipe[DstType]

  def flatMap2[DstType](function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType]

  def flatMap[DstType](function: ElemType => RealPipe[DstType]): RealPipe[DstType]

  def filter(function: ElemType => Boolean): RealPipe[ElemType]

  def head: ElemType

  def toList: List[ElemType]

  def iterator: Iterator[ElemType]

  def map[SuperType >: ElemType](function: SuperType => SuperType,
                                 times: Int): Pipe[SuperType] = {
    var currentPipe: Pipe[SuperType] = this
    for (_ <- 0 until times) {
      currentPipe = currentPipe.map(function)
    }
    currentPipe
  }

  def map[SuperType >: ElemType](function: SuperType => SuperType,
                                 until: SuperType => Boolean): Pipe[SuperType] = {
    // TODO implement
    this
  }

  def flatMap[SuperType >: ElemType](function: SuperType => GenTraversableOnce[SuperType],
                                     times: Int): RealPipe[SuperType] = {
    var currentPipe: RealPipe[SuperType] = toRealPipe
    for (_ <- 0 until times) {
      currentPipe = currentPipe.flatMap2(function)
    }
    currentPipe
  }

  def flatMap3[SuperType >: ElemType](function: SuperType => GenTraversableOnce[SuperType],
                                      until: SuperType => Boolean): RealPipe[SuperType] = {
    var stack: List[SuperType] = toList
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
