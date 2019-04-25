package io.shiftleft.cpgqueryingtests.codepropertygraph

import gremlin.scala._
import io.shiftleft.cpgloading.CpgLoader

case class CpgTestFixture(projectName: String) {
  lazy val cpg = CpgLoader.loadCodePropertyGraph(s"resources/cpgs/$projectName/cpg.bin.zip", argDefFilename = None)
  implicit val graph: Graph = cpg.graph
  lazy val scalaGraph: ScalaGraph = graph.asScala
}
