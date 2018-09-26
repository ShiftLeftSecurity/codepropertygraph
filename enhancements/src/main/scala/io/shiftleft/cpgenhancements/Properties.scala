package io.shiftleft.cpgenhancements

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._

object Properties {

  def metaNode(graph: ScalaGraph): nodes.MetaData =
    graph.V
      .hasLabel(NodeTypes.META_DATA)
      .headOption
      .getOrElse(throw new Exception("Meta node missing."))
      .toCC[nodes.MetaData]

  def isOfLanguage(graph: ScalaGraph, language: String): Boolean =
    metaNode(graph).language == language

}
