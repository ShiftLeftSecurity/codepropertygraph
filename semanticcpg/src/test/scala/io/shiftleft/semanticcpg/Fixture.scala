package io.shiftleft.semanticcpg

import gremlin.scala._
import io.shiftleft.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.cpgloading.CpgLoader
import org.apache.tinkerpop.gremlin.structure.Graph

class Fixture(projectName: String) {
  val cpg = CpgLoader.load(s"resources/cpgs/$projectName/cpg.bin.zip")
  val scalaGraph: ScalaGraph = cpg.graph

  protected def applyMethodDecorator(graph: Graph): Unit = {
    val methodDecorator = new MethodDecoratorPass(graph)
    methodDecorator.executeAndApply()
  }
}
