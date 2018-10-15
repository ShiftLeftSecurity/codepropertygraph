package io.shiftleft.cpgqueryingtests.codepropertygraph

import gremlin.scala.Graph
import io.shiftleft.cpgloading.tinkergraph.CpgLoader

case class CpgTestFixture(projectName: String) {
  lazy val cpg = CpgLoader.loadCodePropertyGraph(s"resources/cpgs/$projectName/cpg.bin.zip")
  implicit val graph: Graph = cpg.graph
}
