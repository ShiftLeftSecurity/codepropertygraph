package io.shiftleft.cpgqueryingtests.codepropertygraph

import gremlin.scala._
import io.shiftleft.cpgloading.CpgLoader

case class CpgTestFixture(projectName: String) {
  lazy val cpg = CpgLoader.load(s"resources/cpgs/$projectName/cpg.bin.zip")
  implicit val graph: Graph = cpg.graph
  lazy val scalaGraph: ScalaGraph = graph.asScala
}
