package io.shiftleft.cpgqueryingtests.codepropertygraph

import gremlin.scala.{Graph, ScalaGraph}
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.layers.{DataFlowRunner, EnhancementRunner}
import io.shiftleft.semanticsloader.SemanticsLoader

case class CpgTestFixture(projectName: String) {
  lazy val cpg = CpgLoader.load(s"resources/cpgs/$projectName/cpg.bin.zip", CpgLoaderConfig.default)
  new EnhancementRunner().run(cpg, new SerializedCpg())
  new DataFlowRunner(SemanticsLoader.emptySemantics).run(cpg, new SerializedCpg());
  implicit val graph: Graph = cpg.graph
  lazy val scalaGraph: ScalaGraph = graph
}
