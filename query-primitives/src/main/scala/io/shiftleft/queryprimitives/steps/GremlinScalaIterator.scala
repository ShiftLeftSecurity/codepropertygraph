package io.shiftleft.queryprimitives.steps

import gremlin.scala.GremlinScala

case class GremlinScalaIterator[T](traversal: GremlinScala[T]) extends Iterator[T] {
  override def hasNext: Boolean = {
    traversal.traversal.hasNext
  }

  override def next(): T = {
    traversal.traversal.next
  }
}
