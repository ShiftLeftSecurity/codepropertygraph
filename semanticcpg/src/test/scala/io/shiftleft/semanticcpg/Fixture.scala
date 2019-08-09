package io.shiftleft.semanticcpg

import gremlin.scala._
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig, OverflowDbConfig}
import io.shiftleft.layers.EnhancementRunner

private class Fixture(projectName: String) {
  private val config = CpgLoaderConfig().withOverflowConfig(OverflowDbConfig.disabled)
  private val cpgFilename = s"resources/testcode/cpgs/$projectName/cpg.bin.zip"
  lazy val cpg = CpgLoader.load(cpgFilename, config)
  new EnhancementRunner().run(cpg, new SerializedCpg())
  implicit val graph: Graph = cpg.graph
  lazy val scalaGraph: ScalaGraph = graph
}

object Fixture {
  def apply[T](projectName: String)(fun: Fixture => T): T = {
    val fixture = new Fixture(projectName)
    try {
      fun(fixture)
    } finally {
      fixture.cpg.close()
    }
  }
}
