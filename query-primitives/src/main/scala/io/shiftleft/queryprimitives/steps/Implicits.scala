package io.shiftleft.queryprimitives.steps

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.EnhancedBlock.EnhancedBlock
import shapeless.HList

object Implicits {

  implicit class GremlinScalaDeco[End, Labels <: HList](raw: GremlinScala.Aux[End, Labels]) {
    /* in some cases we cannot statically determine the type of the node, e.g. when traversing
     * from a known nodeType via AST edges, so we have to cast */
    def cast[NodeType]: GremlinScala.Aux[NodeType, Labels] =
      raw.asInstanceOf[GremlinScala.Aux[NodeType, Labels]]
  }

  /**
    * A wrapper around a Java iterator that throws a proper NoSuchElementException.
    *
    * Proper in this case means an exception with a stack trace.
    * This is intended to be used as a replacement for next() on the iterators
    * returned from TinkerPop since those are missing stack traces.
    */
  implicit class JavaIteratorDeco[T](iterator: java.util.Iterator[T]) {
    def nextChecked: T = {
      try {
        iterator.next
      } catch {
        case _: NoSuchElementException =>
          throw new NoSuchElementException()
      }
    }

    def nextOption: Option[T] = {
      if (iterator.hasNext) {
        Some(iterator.next)
      } else {
        None
      }
    }
  }

  implicit def toEnhancedBlock(node: nodes.Block) = new EnhancedBlock(node)

}
