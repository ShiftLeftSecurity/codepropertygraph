package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.RealPipe

import scala.collection.GenTraversableOnce

/*
class RealPipeOperations[+ElemType] extends PipeOperations[RealPipe, ElemType] {
  override def toRealPipe[SuperType >: ElemType]
  (pipe: RealPipe[SuperType]): RealPipe[SuperType] = {
    pipe
  }

  override def map[SuperType >: ElemType, DstType]
  (pipe: RealPipe[SuperType],
   function: SuperType => DstType): RealPipe[DstType] = {
    new RealPipe(pipe.impl.map(function))
  }

  override def flatMap2[SuperType >: ElemType, DstType]
  (pipe: RealPipe[SuperType],
   function: SuperType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    new RealPipe(pipe.impl.flatMap(function.apply))
  }

  override def flatMap[SuperType >: ElemType, DstType]
  (pipe: RealPipe[SuperType],
   function: SuperType => RealPipe[DstType]): RealPipe[DstType] = {
    val applyAndUnwrap = (sourceElement: SuperType) => function.apply(sourceElement).impl
    new RealPipe(pipe.impl.flatMap(applyAndUnwrap))
  }

  override def filter[SuperType >: ElemType]
  (pipe: RealPipe[SuperType],
   function: SuperType => Boolean): RealPipe[SuperType] = {
    new RealPipe(pipe.impl.filter(function))
  }

  override def head[SuperType >: ElemType]
  (pipe: RealPipe[SuperType]): SuperType = {
    pipe.impl.head
  }

  override def iterator[SuperType >: ElemType]
  (pipe: RealPipe[SuperType]): Iterator[SuperType] = {
    pipe.impl.iterator
  }

  override def toList[SuperType >: ElemType]
  (pipe: RealPipe[SuperType]): List[SuperType] = {
    pipe.impl
  }
}

 */
