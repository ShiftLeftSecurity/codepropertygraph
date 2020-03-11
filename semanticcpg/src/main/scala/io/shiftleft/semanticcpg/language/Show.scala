package io.shiftleft.semanticcpg.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.NewNode

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
      case node: NewNode => {
        val label = node.label
        val properties = propsToString(node.properties)
        s"($label): $properties"
      }

      case node: Vertex =>
        val label = node.label
        val id = node.id().toString
        val properties = propsToString(node.valueMap)
        s"($label,$id): $properties"

      case other => other.toString
    }

    private def propsToString(keyValues: Map[String, Any]): String = {
      keyValues.toList
        .filter(_._2.toString.nonEmpty)
        .sortBy(_._1)
        .map { case (key, value) => s"$key: $value" }
        .mkString(", ")
    }
  }

}
