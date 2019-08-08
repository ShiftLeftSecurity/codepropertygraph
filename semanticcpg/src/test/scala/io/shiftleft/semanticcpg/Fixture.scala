package io.shiftleft.semanticcpg

import gremlin.scala._
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig, OverflowDbConfig}
import io.shiftleft.layers.EnhancementRunner

private class Fixture(projectName: String) {
  private val config = CpgLoaderConfig().withOverflowConfig(OverflowDbConfig.disabled)
  val cpg = CpgLoader.load(s"resources/testcode/cpgs/$projectName/cpg.bin.zip",
    config)
  new EnhancementRunner().run(cpg, new SerializedCpg())
  val scalaGraph: ScalaGraph = cpg.graph
}

object Fixture {
  def apply[T](projectName: String)(fun: Fixture => T): T = {
    fun(new Fixture(projectName))
  }
}
