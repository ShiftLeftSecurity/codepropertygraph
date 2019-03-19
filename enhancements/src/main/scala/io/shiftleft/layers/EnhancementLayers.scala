package io.shiftleft.layers

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes}
import io.shiftleft.layers.enhancedbase.EnhancedBaseCreator

class EnhancementLayers() {

  def run(implicit graph: ScalaGraph) = {
    val lang = language(graph)

    new EnhancedBaseCreator(graph, lang).create
  }

  private def metaNode(graph: ScalaGraph): Vertex = {
    graph.V
      .hasLabel(NodeTypes.META_DATA)
      .headOption()
      .getOrElse(throw new Exception("Meta node missing."))
  }

  def language(graph: ScalaGraph): String =
    metaNode(graph).value2(NodeKeys.LANGUAGE)

  def isOfLanguage(graph: ScalaGraph, lang: String): Boolean = {
    language(graph) == lang
  }

}
