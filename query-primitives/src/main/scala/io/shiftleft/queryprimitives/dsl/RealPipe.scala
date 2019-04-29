package io.shiftleft.queryprimitives.dsl

import scala.collection.GenTraversableOnce

object RealPipe {
  def newBuilder[ElemType]: RealPipeBuilder[ElemType] = {
    new RealPipeBuilder()
  }
}

class RealPipe[+ElemType](val impl: List[ElemType]) extends AnyVal {

  def map[DstType](function: ElemType => DstType): RealPipe[DstType] = {
    Implicits.realPipeOps.map(this, function)
  }

  def map[SuperType >: ElemType](function: SuperType => SuperType,
                                 times: Int): RealPipe[SuperType] = {
    Implicits.realPipeOps.map(this, function, times)
  }

  def flatMap2[DstType](function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    Implicits.realPipeOps.flatMap2(this, function)
  }

  def flatMap[DstType](function: ElemType => RealPipe[DstType]): RealPipe[DstType] = {
    Implicits.realPipeOps.flatMap(this, function)
  }

  def head: ElemType = {
    Implicits.realPipeOps.head(this)
  }
}
