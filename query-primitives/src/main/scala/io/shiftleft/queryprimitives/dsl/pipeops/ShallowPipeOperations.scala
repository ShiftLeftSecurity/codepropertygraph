package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.pipetypes.ShallowPipe.ShallowPipe

import scala.collection.GenTraversableOnce

class ShallowPipeOperations extends PipeOperations[ShallowPipe] {

  private[dsl] override def map[DstType, ElemType]
  (pipe: ShallowPipe[ElemType],
   function: ElemType => DstType): ShallowPipe[DstType] = {
    function.apply(pipe)
  }

  private[dsl] override def flatMap[DstType, ElemType]
  (pipe: ShallowPipe[ElemType],
   function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    RealPipe(function.apply(pipe).toList)
  }

  private[dsl] override def filter[ElemType]
  (pipe: ShallowPipe[ElemType],
   function: ElemType => Boolean): RealPipe[ElemType] = {
    if (function.apply(pipe)){
      RealPipe(pipe :: Nil)
    } else {
      RealPipe(Nil)
    }
  }

  private[dsl] override def head[ElemType]
  (pipe: ShallowPipe[ElemType]): ElemType = {
    pipe
  }

  private[dsl] override def iterator[ElemType]
  (pipe: ShallowPipe[ElemType]): Iterator[ElemType] = {
    (pipe :: Nil).toIterator
  }

  private[dsl] override def toList[ElemType]
  (pipe: ShallowPipe[ElemType]): List[ElemType] = {
    pipe :: Nil
  }

  private[dsl] override def toSet[ElemType]
  (pipe: ShallowPipe[ElemType]): Set[ElemType] = {
    Set() + pipe
  }

  private[dsl] override def foreach[DstType, ElemType]
  (pipe: ShallowPipe[ElemType],
   function: ElemType => DstType): Unit = {
    function(pipe)
  }

  private[dsl] override def append[ElemType]
  (pipe: ShallowPipe[ElemType],
   otherPipe: RealPipe[ElemType]): RealPipe[ElemType] = {
    RealPipe(pipe :: RealPipe.unwrap(otherPipe))
  }

  private[dsl] override def append[ElemType]
  (pipe: ShallowPipe[ElemType],
   otherPipe: ShallowPipe[ElemType]): RealPipe[ElemType] = {
    RealPipe(pipe :: otherPipe :: Nil)
  }
}

