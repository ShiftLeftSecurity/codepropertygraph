package io.shiftleft.semanticcpg

import gremlin.scala._
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.layers.{DataFlowRunner, EnhancementRunner}
import io.shiftleft.semanticsloader.SemanticsLoader

class Fixture(projectName: String) {
  val cpg = CpgLoader.load(s"resources/testcode/cpgs/$projectName/cpg.bin.zip",
                           CpgLoaderConfig.withoutOverflow)
  new EnhancementRunner().run(cpg, new SerializedCpg)
  new DataFlowRunner(SemanticsLoader.emptySemantics)
  val scalaGraph: ScalaGraph = cpg.graph
}

object Fixture {
  def apply[T](projectName: String)(fun: Fixture => T): T = {
    fun(new Fixture(projectName))
  }
}
