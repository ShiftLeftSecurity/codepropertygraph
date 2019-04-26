package io.shiftleft.queryprimitives.dsl

import scala.collection.GenTraversableOnce

// We cannot make this any AnyVal because Scala currently has the limitation of
// no allowing value classes to wrap other value classes.
class RealPipeMethods[ElemType](val pipe: RealPipe[ElemType]) /* extends AnyVal */ {

  def map[DstType](function: ElemType => DstType)
                  (implicit ops: PipeOperations[RealPipe]): RealPipe[DstType] = {
    ops.map(pipe, function)
  }

  def map(function: ElemType => ElemType, times: Int)
         (implicit ops: PipeOperations[RealPipe]): RealPipe[ElemType] = {
    ops.map(pipe, function, times)
  }

  def flatMap2[DstType](function: ElemType => GenTraversableOnce[DstType])
                      (implicit ops: PipeOperations[RealPipe]): RealPipe[DstType] = {
    ops.flatMap2(pipe, function)
  }

  def flatMap[DstType](function: ElemType => RealPipe[DstType])
                      (implicit ops: PipeOperations[RealPipe]): RealPipe[DstType] = {
    ops.flatMap(pipe, function)
  }

  def head(implicit ops: PipeOperations[RealPipe]): ElemType = {
    ops.head(pipe)
  }
}
