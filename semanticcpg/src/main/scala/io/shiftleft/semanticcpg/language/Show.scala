package io.shiftleft.semanticcpg.language

import gremlin.scala._

/**
  * Typeclass for (pretty) printing an object
  */
trait Show[A] {
  def apply(a: A): String
}

trait ShowLowPrioImplicits {
  implicit def default[A]: Show[A] = {
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

object Show extends ShowLowPrioImplicits
