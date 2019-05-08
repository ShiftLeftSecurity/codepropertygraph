package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.Implicits
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.pipetypes.ShallowPipe.ShallowPipe

import scala.collection.GenTraversableOnce

class RealPipeOperations extends PipeOperations[RealPipe] {

  private[dsl] override def map[DstType, ElemType]
  (pipe: RealPipe[ElemType],
   function: ElemType => DstType): RealPipe[DstType] = {
    RealPipe(RealPipe.unwrap(pipe).map(function))
  }

  private[dsl] override def flatMap[DstType, ElemType]
  (pipe: RealPipe[ElemType],
   function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    RealPipe(RealPipe.unwrap(pipe).flatMap(function))
  }

  private[dsl] override def filter[ElemType]
  (pipe: RealPipe[ElemType],
   function: ElemType => Boolean): RealPipe[ElemType] = {
    RealPipe(RealPipe.unwrap(pipe).filter(function))
  }

  private[dsl] override def head[ElemType]
  (pipe: RealPipe[ElemType]): ElemType = {
    RealPipe.unwrap(pipe).head
  }

  private[dsl] override def iterator[ElemType]
  (pipe: RealPipe[ElemType]): Iterator[ElemType] = {
    RealPipe.unwrap(pipe).iterator
  }

  private[dsl] override def isEmpty[ElemType]
  (pipe: RealPipe[ElemType]): Boolean = {
    RealPipe.unwrap(pipe).isEmpty
  }

  private[dsl] override def nonEmpty[ElemType]
  (pipe: RealPipe[ElemType]): Boolean = {
    RealPipe.unwrap(pipe).nonEmpty
  }

  private[dsl] override def toList[ElemType]
  (pipe: RealPipe[ElemType]): List[ElemType] = {
    RealPipe.unwrap(pipe)
  }

  private[dsl] override def toSet[ElemType]
  (pipe: RealPipe[ElemType]): Set[ElemType] = {
    RealPipe.unwrap(pipe).toSet
  }

  private[dsl] override def foreach[DstType, ElemType]
  (pipe: RealPipe[ElemType],
   function: ElemType => DstType): Unit = {
    RealPipe.unwrap(pipe).foreach(function)
  }
}
