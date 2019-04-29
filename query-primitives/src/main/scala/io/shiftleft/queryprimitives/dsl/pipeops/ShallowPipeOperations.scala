package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.RealPipe
import io.shiftleft.queryprimitives.dsl.ShallowPipe.ShallowPipe

import scala.collection.GenTraversableOnce

class ShallowPipeOperations[+ElemType] extends PipeOperations[ShallowPipe, ElemType] {
  override def toRealPipe[SuperType >: ElemType]
  (pipe: ShallowPipe[SuperType]): RealPipe[SuperType] = {
    new RealPipe(pipe :: Nil)
  }

  override def map[SuperType >: ElemType, DstType]
  (pipe: ShallowPipe[SuperType],
   function: SuperType => DstType): ShallowPipe[DstType] = {
    function.apply(pipe)
  }

  override def flatMap2[SuperType >: ElemType, DstType]
  (pipe: ShallowPipe[SuperType],
   function: SuperType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    new RealPipe(function.apply(pipe).toList)
  }

  override def flatMap[SuperType >: ElemType, DstType]
  (pipe: ShallowPipe[SuperType],
   function: SuperType => RealPipe[DstType]): RealPipe[DstType] = {
    new RealPipe(function.apply(pipe).impl)
  }

  override def filter[SuperType >: ElemType]
  (pipe: ShallowPipe[SuperType],
   function: SuperType => Boolean): RealPipe[SuperType] = {
    if (function.apply(pipe)){
      new RealPipe(pipe :: Nil)
    } else {
      new RealPipe(Nil)
    }
  }

  override def head[SuperType >: ElemType]
  (pipe: ShallowPipe[SuperType]): SuperType = {
    pipe
  }

  override def iterator[SuperType >: ElemType]
  (pipe: ShallowPipe[SuperType]): Iterator[SuperType] = {
    (pipe :: Nil).toIterator
  }

  override def toList[SuperType >: ElemType]
  (pipe: ShallowPipe[SuperType]): List[SuperType] = {
    pipe :: Nil
  }
}

