package io.shiftleft.semanticcpg

import java.nio.file.Files

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.queryprimitives.CpgLoader
import io.shiftleft.queryprimitives.steps.starters.Cpg
import io.shiftleft.SerializedCpg
import io.shiftleft.layers.enhancedbase.EnhancedBaseCreator
import org.apache.tinkerpop.gremlin.structure.Graph

class Fixture(projectName: String) {
  val cpg = CpgLoader.loadCodePropertyGraph(s"resources/cpgs/$projectName/cpg.bin.zip")
  val scalaGraph: ScalaGraph = cpg.graph
  runEnhancements(cpg.graph)

  protected def runEnhancements(graph: Graph): Unit = {
    val language = metaNode(graph).language
    val serializedCpg = new SerializedCpg
    new EnhancedBaseCreator(graph, language, serializedCpg).create
  }

  private def metaNode(graph: ScalaGraph): nodes.MetaData = {
    graph.V
      .hasLabel(NodeTypes.META_DATA)
      .headOption()
      .getOrElse(throw new Exception("Meta node missing."))
      .asInstanceOf[nodes.MetaData]
  }

  protected def applyMethodDecorator(graph: Graph): Unit = {
    val methodDecorator = new MethodDecoratorPass(graph)
    methodDecorator.executeAndApply()
  }
}
