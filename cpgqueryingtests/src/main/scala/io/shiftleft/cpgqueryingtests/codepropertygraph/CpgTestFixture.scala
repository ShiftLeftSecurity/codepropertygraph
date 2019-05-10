package io.shiftleft.cpgqueryingtests.codepropertygraph

import gremlin.scala.{Graph, ScalaGraph}
import io.shiftleft.SerializedCpg
import io.shiftleft.cpgloading.CpgLoader
import io.shiftleft.layers.ScpgLayers
import io.shiftleft.semanticsloader.SemanticsLoader

case class CpgTestFixture(projectName: String) {
  lazy val cpg = CpgLoader.load(s"resources/cpgs/$projectName/cpg.bin.zip")
  new ScpgLayers(SemanticsLoader.emptySemantics).run(cpg, new SerializedCpg())
  implicit val graph: Graph = cpg.graph
  lazy val scalaGraph: ScalaGraph = graph
}
