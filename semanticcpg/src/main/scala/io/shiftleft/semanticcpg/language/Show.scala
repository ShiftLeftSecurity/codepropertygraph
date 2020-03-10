package io.shiftleft.semanticcpg.language

import gremlin.scala._

/**
  * Typeclass for (pretty) printing an object
  */
trait Show[A] {
  def apply(a: A): String
}

object Show {
  def default[A]: Show[A] = Default.asInstanceOf[Show[A]]

  private val Default = new Show[Any] {
    override def apply(a: Any): String = a match {
      case node: Vertex =>
        val label = node.label
        val id = node.id().toString
        val keyValPairs = node.valueMap.toList
          .filter(x => x._2.toString != "")
          .sortBy(_._1)
          .map(x => x._1 + ": " + x._2)
        s"($label,$id): " + keyValPairs.mkString(", ")

      case other => other.toString
    }
  }

}
