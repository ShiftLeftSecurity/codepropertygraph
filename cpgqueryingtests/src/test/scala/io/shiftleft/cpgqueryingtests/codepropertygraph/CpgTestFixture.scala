package io.shiftleft.cpgqueryingtests.codepropertygraph

import io.shiftleft.cpgloading.tinkergraph.CpgLoader

case class CpgTestFixture(projectName: String) {
  lazy val cpg = CpgLoader.loadCodePropertyGraph(s"resources/cpgs/$projectName/cpg.bin.zip")
}
