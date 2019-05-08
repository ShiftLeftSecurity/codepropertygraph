package io.shiftleft.queryprimitives.dsl

import io.shiftleft.queryprimitives.dsl.pipeops.PipeOperations
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe

import scala.collection.GenTraversableOnce
import scala.language.higherKinds

/**
  * This class along with the corresponding implicit conversion enables the DSL writer to
  * write things like: pipe.map(someFunc) instead of ops.map(pipe, someFunc).
  * Besides the obvious benefit of being a little bit nicer to write and read this also
  * solves the problem that for complex functions like repeat(), the scala type system
  * requires us to specify the the passed function parameter if we would use the
  * ops.repeat(pipe, (x: SomeType) => x, 1) syntax. From this obligation we are relieved
  * when using pipe.repeat(x => x, 1) because the ElemType of is predefined in the
  * GenericPipeMethods wrapper creation.
  *
  * It also enables the DSL user to use the provide methods on our pipe types.
  */
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

  def toSet(implicit ops: PipeOperations[PipeType]): Set[ElemType] = {
    ops.toSet(pipe)
  }

  def filter(function: ElemType => Boolean)
            (implicit ops: PipeOperations[PipeType]): RealPipe[ElemType] = {
    ops.filter(pipe, function)
  }

  def isEmpty(implicit ops: PipeOperations[PipeType]): Boolean = {
    ops.isEmpty(pipe)
  }

  def nonEmpty(implicit ops: PipeOperations[PipeType]): Boolean = {
    ops.nonEmpty(pipe)
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
