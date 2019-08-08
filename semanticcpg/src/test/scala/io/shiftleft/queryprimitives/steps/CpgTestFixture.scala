package io.shiftleft.queryprimitives.steps

import gremlin.scala.{Graph, ScalaGraph}
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig, OverflowDbConfig}
import io.shiftleft.layers.EnhancementRunner

private class CpgTestFixture(projectName: String) {
  private val cpgFilename = s"resources/testcode/cpgs/$projectName/cpg.bin.zip"
  private val config = CpgLoaderConfig().withOverflowConfig(OverflowDbConfig.disabled)
  lazy val cpg = CpgLoader.load(cpgFilename, config)
  new EnhancementRunner().run(cpg, new SerializedCpg())
  implicit val graph: Graph = cpg.graph
  lazy val scalaGraph: ScalaGraph = graph
}

object CpgTestFixture {
  def apply[T](projectName: String)(fun: CpgTestFixture => T): T = {
    val fixture = new CpgTestFixture(projectName)
    try {
      fun(fixture)
    } finally {
      fixture.cpg.close()
    }
  }
}
