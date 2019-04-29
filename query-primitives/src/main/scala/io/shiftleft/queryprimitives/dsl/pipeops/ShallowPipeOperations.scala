package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.RealPipe
import io.shiftleft.queryprimitives.dsl.ShallowPipe

import scala.collection.GenTraversableOnce

/*
class ShallowPipeOperations[+ElemType] extends PipeOperations[ShallowPipe, ElemType] {
  override def toRealPipe[SuperType >: ElemType]
  (pipe: ShallowPipe[SuperType]): RealPipe[SuperType] = {
    new RealPipe(pipe.impl :: Nil)
  }

  override def map[SuperType >: ElemType, DstType]
  (pipe: ShallowPipe[SuperType],
   function: SuperType => DstType): ShallowPipe[DstType] = {
    new ShallowPipe(function.apply(pipe.impl))
  }

  override def flatMap2[SuperType >: ElemType, DstType]
  (pipe: ShallowPipe[SuperType],
   function: SuperType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    new RealPipe(function.apply(pipe.impl).toList)
  }

  override def flatMap[SuperType >: ElemType, DstType]
  (pipe: ShallowPipe[SuperType],
   function: SuperType => RealPipe[DstType]): RealPipe[DstType] = {
    new RealPipe(function.apply(pipe.impl).impl)
  }

  override def filter[SuperType >: ElemType]
  (pipe: ShallowPipe[SuperType],
   function: SuperType => Boolean): RealPipe[SuperType] = {
    if (function.apply(pipe.impl)){
      new RealPipe(pipe.impl :: Nil)
    } else {
      new RealPipe(Nil)
    }
  }

  override def head[SuperType >: ElemType]
  (pipe: ShallowPipe[SuperType]): SuperType = {
    pipe.impl
  }

  override def iterator[SuperType >: ElemType]
  (pipe: ShallowPipe[SuperType]): Iterator[SuperType] = {
    (pipe.impl :: Nil).toIterator
  }

  override def toList[SuperType >: ElemType]
  (pipe: ShallowPipe[SuperType]): List[SuperType] = {
    pipe.impl :: Nil
  }
}
 */

