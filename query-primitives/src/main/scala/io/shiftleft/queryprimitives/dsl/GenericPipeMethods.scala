package io.shiftleft.queryprimitives.dsl

import io.shiftleft.queryprimitives.dsl.pipeops.PipeOperations
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe

import scala.collection.GenTraversableOnce
import scala.language.higherKinds

class GenericPipeMethods[PipeType[+_], ElemType](val pipe: PipeType[ElemType]) extends AnyVal {

  def head(implicit ops: PipeOperations[PipeType]): ElemType = {
    ops.head(pipe)
  }

  def foreach[DstType](function: ElemType => DstType)
                      (implicit ops: PipeOperations[PipeType]): Unit = {
    ops.foreach(pipe, function)
  }

  def toList(implicit ops: PipeOperations[PipeType]): List[ElemType] = {
    ops.toList(pipe)
  }

  def filter(function: ElemType => Boolean)
            (implicit ops: PipeOperations[PipeType]): RealPipe[ElemType] = {
    ops.filter(pipe, function)
  }

  def map[DstType](function: ElemType => DstType)
                  (implicit ops: PipeOperations[PipeType]): PipeType[DstType] = {
    ops.map(pipe, function)
  }

  def repeat[DstType >: ElemType](function: DstType => DstType,
                                  times: Int)
                                 (implicit ops: PipeOperations[PipeType]): PipeType[DstType] = {
    ops.repeat(pipe, function, times)
  }

  def repeatUntil[DstType >: ElemType](function: DstType => DstType,
                                       until: DstType => Boolean)
                                      (implicit ops: PipeOperations[PipeType]): PipeType[DstType] = {
    ops.repeatUntil(pipe, function, until)
  }

  def flatMap[DstType](function: ElemType => GenTraversableOnce[DstType])
                      (implicit ops: PipeOperations[PipeType]): RealPipe[DstType] = {
    ops.flatMap(pipe, function)
  }

  def flatRepeat[DstType >: ElemType](function: DstType => GenTraversableOnce[DstType],
                                      times: Int)
                                     (implicit ops: PipeOperations[PipeType]): RealPipe[DstType] = {
    ops.flatRepeat(pipe, function, times)
  }

  def flatRepeatUntil[DstType >: ElemType](function: DstType => GenTraversableOnce[DstType],
                                           until: DstType => Boolean)
                                          (implicit ops: PipeOperations[PipeType]): RealPipe[DstType] = {
    ops.flatRepeatUntil(pipe, function, until)
  }
}
