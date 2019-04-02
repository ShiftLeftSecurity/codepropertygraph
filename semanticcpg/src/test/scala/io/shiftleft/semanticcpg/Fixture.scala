package io.shiftleft.semanticcpg

import java.nio.file.Files

import gremlin.scala._
import io.shiftleft.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.CpgLoader
import io.shiftleft.queryprimitives.steps.starters.Cpg
import org.apache.tinkerpop.gremlin.structure.Graph

class Fixture(projectName: String) {
  val cpg = CpgLoader.loadCodePropertyGraph(s"resources/cpgs/$projectName/cpg.bin.zip")
  val scalaGraph: ScalaGraph = cpg.graph

  protected def applyMethodDecorator(graph: Graph): Unit = {
    val methodDecorator = new MethodDecoratorPass(graph)
    methodDecorator.executeAndApply()
  }
}
