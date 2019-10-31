package io.shiftleft.semanticcpg.language

import gremlin.scala.{GremlinScala, P}

/**
  * Traversal to deal with number of elements returned
  * by another traversal
  * */
class Number(raw: GremlinScala[Long]) extends Steps[Long](raw) {

  def is(n : Long) : Number = new Number(raw.filterOnEnd(_.equals(n)))

}
