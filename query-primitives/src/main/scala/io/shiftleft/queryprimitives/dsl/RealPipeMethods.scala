package io.shiftleft.queryprimitives.dsl

import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe

import scala.collection.GenTraversableOnce

class RealPipeMethods[ElemType](val pipe: RealPipe[ElemType]) extends AnyVal {

  def head: ElemType = {
    Implicits.getRealPipeOps.head(pipe)
  }

  def foreach[DstType](function: ElemType => DstType): Unit = {
    Implicits.getRealPipeOps.foreach(pipe, function)
  }

  def toList: List[ElemType] = {
    Implicits.getRealPipeOps.toList(pipe)
  }

  def cast[NewElemType]: RealPipe[NewElemType] = {
    pipe.asInstanceOf[RealPipe[NewElemType]]
  }

  def map[DstType](function: ElemType => DstType): RealPipe[DstType] = {
    Implicits.getRealPipeOps.map(pipe, function)
  }

  def repeat[DstType >: ElemType](function: DstType => DstType,
                                  times: Int): RealPipe[DstType] = {
    Implicits.getRealPipeOps.repeat(pipe, function, times)
  }

  def repeatUntil[DstType >: ElemType](function: DstType => DstType,
                                       until: DstType => Boolean): RealPipe[DstType] = {
    Implicits.getRealPipeOps.repeatUntil(pipe, function, until)
  }

  def flatMap[DstType](function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    Implicits.getRealPipeOps.flatMap(pipe, function)
  }

  def flatRepeat[DstType >: ElemType](function: DstType => GenTraversableOnce[DstType],
                                      times: Int): RealPipe[DstType] = {
    Implicits.getRealPipeOps.flatRepeat(pipe, function, times)
  }

  def flatRepeatUntil[DstType >: ElemType](function: DstType => GenTraversableOnce[DstType],
                                           until: DstType => Boolean): RealPipe[DstType] = {
    Implicits.getRealPipeOps.flatRepeatUntil(pipe, function, until)
  }
}

