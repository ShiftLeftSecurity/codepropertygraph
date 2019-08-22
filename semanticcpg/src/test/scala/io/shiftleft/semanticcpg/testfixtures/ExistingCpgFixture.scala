package io.shiftleft.semanticcpg.testfixtures

import gremlin.scala._
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.semanticcpg.layers.EnhancementRunner

private class ExistingCpgFixture(projectName: String) {
  private val config = CpgLoaderConfig.withoutOverflow
  private val cpgFilename = s"resources/testcode/cpgs/$projectName/cpg.bin.zip"
  lazy val cpg = CpgLoader.load(cpgFilename, config)
  new EnhancementRunner().run(cpg, new SerializedCpg())
  implicit val graph: Graph = cpg.graph
  lazy val scalaGraph: ScalaGraph = graph
}

object ExistingCpgFixture {
  def apply[T](projectName: String)(fun: ExistingCpgFixture => T): T = {
    val fixture = new ExistingCpgFixture(projectName)
    try {
      fun(fixture)
    } finally {
      fixture.cpg.close()
    }
  }
}
