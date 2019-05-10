package io.shiftleft.cpgqueryingtests.codepropertygraph

import gremlin.scala.{Graph, ScalaGraph}
import io.shiftleft.SerializedCpg
import io.shiftleft.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.layers.ScpgLayers
import io.shiftleft.semanticsloader.SemanticsLoader

case class CpgTestFixture(projectName: String) {
  val loadConfig = CpgLoaderConfig.default.copy(ignoredProtoEntries = IgnoredCpgEntities.forJava2Cpg)
  lazy val cpg = CpgLoader.load(s"resources/cpgs/$projectName/cpg.bin.zip", loadConfig)
  new ScpgLayers(SemanticsLoader.emptySemantics).run(cpg, new SerializedCpg())
  implicit val graph: Graph = cpg.graph
  lazy val scalaGraph: ScalaGraph = graph
}
