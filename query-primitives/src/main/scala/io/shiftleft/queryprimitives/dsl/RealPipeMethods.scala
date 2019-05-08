package io.shiftleft.queryprimitives.dsl

import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe

import scala.collection.GenTraversableOnce

class RealPipeMethods[ElemType](val pipe: RealPipe[ElemType]) extends AnyVal {

  def head: ElemType = {
    Implicits.realPipeOps.head(pipe)
  }

  def foreach[DstType](function: ElemType => DstType): Unit = {
    Implicits.realPipeOps.foreach(pipe, function)
  }

  def toList: List[ElemType] = {
    Implicits.realPipeOps.toList(pipe)
  }

  def filter(function: ElemType => Boolean): RealPipe[ElemType] = {
    Implicits.realPipeOps.filter(pipe, function)
  }

  def map[DstType](function: ElemType => DstType): RealPipe[DstType] = {
    Implicits.realPipeOps.map(pipe, function)
  }

  def repeat[DstType >: ElemType](function: DstType => DstType,
                                  times: Int): RealPipe[DstType] = {
    Implicits.realPipeOps.repeat(pipe, function, times)
  }

  def repeatUntil[DstType >: ElemType](function: DstType => DstType,
                                       until: DstType => Boolean): RealPipe[DstType] = {
    Implicits.realPipeOps.repeatUntil(pipe, function, until)
  }

  def flatMap[DstType](function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    Implicits.realPipeOps.flatMap(pipe, function)
  }

  def flatRepeat[DstType >: ElemType](function: DstType => GenTraversableOnce[DstType],
                                      times: Int): RealPipe[DstType] = {
    Implicits.realPipeOps.flatRepeat(pipe, function, times)
  }

  def flatRepeatUntil[DstType >: ElemType](function: DstType => GenTraversableOnce[DstType],
                                           until: DstType => Boolean): RealPipe[DstType] = {
    Implicits.realPipeOps.flatRepeatUntil(pipe, function, until)
  }
}

