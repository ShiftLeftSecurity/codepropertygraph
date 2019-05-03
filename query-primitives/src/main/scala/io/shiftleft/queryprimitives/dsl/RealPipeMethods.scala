package io.shiftleft.queryprimitives.dsl

import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe

import scala.collection.GenTraversableOnce

class RealPipeMethods[ElemType](val pipe: RealPipe[ElemType]) extends AnyVal {
  def map[DstType](function: ElemType => DstType): RealPipe[DstType] = {
    Implicits.getRealPipeOps.map(pipe, function)
  }

  def mapTimes[SuperType >: ElemType](function: SuperType => SuperType,
                                      times: Int): RealPipe[SuperType] = {
    Implicits.getRealPipeOps.mapTimes(pipe, function, times)
  }

  def flatMap[DstType](function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    Implicits.getRealPipeOps.flatMap(pipe, function)
  }

  def head: ElemType = {
    Implicits.getRealPipeOps.head(pipe)
  }

  def foreach[DstType](function: ElemType => DstType): Unit = {
    Implicits.getRealPipeOps.foreach(pipe, function)
  }

  def toList: List[ElemType] = {
    Implicits.getRealPipeOps.toList(pipe)
  }
}

