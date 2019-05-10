package io.shiftleft.semanticcpg

import gremlin.scala._
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.layers.ScpgLayers
import io.shiftleft.semanticsloader.SemanticsLoader
import org.apache.tinkerpop.gremlin.structure.Graph

class Fixture(projectName: String) {
  val loadConfig = CpgLoaderConfig.default.copy(ignoredProtoEntries = IgnoredCpgEntities.forJava2Cpg)
  val cpg = CpgLoader.load(s"resources/cpgs/$projectName/cpg.bin.zip", loadConfig)
  new ScpgLayers(SemanticsLoader.emptySemantics).run(cpg, new SerializedCpg())
  val scalaGraph: ScalaGraph = cpg.graph

  protected def applyMethodDecorator(graph: Graph): Unit = {
    val methodDecorator = new MethodDecoratorPass(graph)
    methodDecorator.executeAndApply()
  }
}
