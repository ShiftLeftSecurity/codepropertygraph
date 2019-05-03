package io.shiftleft.queryprimitives.dsl

import io.shiftleft.queryprimitives.dsl.RealPipe.RealPipe

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class RealPipeBuilder[ElemType] extends mutable.Builder[ElemType, RealPipe[ElemType]] {
  private val listBuffer = new ListBuffer[ElemType]()

  override def +=(elem: ElemType): RealPipeBuilder.this.type = {
    listBuffer += elem
    this
  }

  override def clear(): Unit = {
    listBuffer.clear
  }

  override def result(): RealPipe[ElemType] = {
    RealPipe.RealPipe(listBuffer.toList)
  }
}


