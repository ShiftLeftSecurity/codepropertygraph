package io.shiftleft.queryprimitives.dsl

import io.shiftleft.queryprimitives.dsl.RealPipe.RealPipe

import scala.collection.GenTraversableOnce
import scala.language.higherKinds

object RealPipe {
  sealed abstract class RealPipeImpl {
    type PipeType[+ElemType]

    def apply[ElemType](impl: List[ElemType]): PipeType[ElemType]
    def unwrap[ElemType](pipeType: PipeType[ElemType]): List[ElemType]
  }

  val RealPipe: RealPipeImpl = new RealPipeImpl {
    override type PipeType[+ElemType] = List[ElemType]

    override def apply[ElemType](impl: List[ElemType]): List[ElemType] = {
      impl
    }

    override def unwrap[ElemType](pipeType: PipeType[ElemType]): List[ElemType] = {
      pipeType
    }
  }

  type RealPipe[+ElemType] = RealPipe.PipeType[ElemType]
}

class RealPipeMethods[ElemType](val pipe: RealPipe[ElemType]) extends AnyVal {
  def map[DstType](function: ElemType => DstType): RealPipe[DstType] = {
    Implicits.getRealPipeOps.map(pipe, function)
  }

  def mapTimes[SuperType >: ElemType](function: SuperType => SuperType,
                                      times: Int): RealPipe[SuperType] = {
    Implicits.getRealPipeOps.mapTimes(pipe, function, times)
  }

  def flatMap2[DstType](function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    Implicits.getRealPipeOps.flatMap2(pipe, function)
  }

  def flatMap[DstType](function: ElemType => RealPipe[DstType]): RealPipe[DstType] = {
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
