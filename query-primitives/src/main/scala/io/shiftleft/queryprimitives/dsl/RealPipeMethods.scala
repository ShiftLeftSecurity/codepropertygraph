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

  def map[DstType](function: ElemType => DstType): RealPipe[DstType] = {
    Implicits.getRealPipeOps.map(pipe, function)
  }

  def repeat[SuperType >: ElemType](function: SuperType => SuperType,
                                    times: Int): RealPipe[SuperType] = {
    Implicits.getRealPipeOps.repeat(pipe, function, times)
  }

  def repeatUntil[SuperType >: ElemType](function: SuperType => SuperType,
                                         until: SuperType => Boolean): RealPipe[SuperType] = {
    Implicits.getRealPipeOps.repeatUntil(pipe, function, until)
  }

  def flatMap[DstType](function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    Implicits.getRealPipeOps.flatMap(pipe, function)
  }

  def flatRepeat[SuperType >: ElemType](function: SuperType => GenTraversableOnce[SuperType],
                                        times: Int): RealPipe[SuperType] = {
    Implicits.getRealPipeOps.flatRepeat(pipe, function, times)
  }

  def flatRepeatUntil[SuperType >: ElemType](function: SuperType => GenTraversableOnce[SuperType],
                                             until: SuperType => Boolean): RealPipe[SuperType] = {
    Implicits.getRealPipeOps.flatRepeatUntil(pipe, function, until)
  }
}

