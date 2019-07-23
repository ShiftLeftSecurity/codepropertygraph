package io.shiftleft.semanticcpg

import gremlin.scala._
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.layers.{DataFlowRunner, EnhancementRunner}
import io.shiftleft.semanticsloader.SemanticsLoader
import org.apache.tinkerpop.gremlin.structure.Graph

class Fixture(projectName: String) {
  val cpg = CpgLoader.load(s"resources/cpgs/$projectName/cpg.bin.zip", CpgLoaderConfig.default.withoutOverflow)
  new EnhancementRunner().run(cpg, new SerializedCpg())
  new DataFlowRunner(SemanticsLoader.emptySemantics)
  val scalaGraph: ScalaGraph = cpg.graph
}

object Fixture {
  def apply[T](projectName: String)(fun: Fixture => T): T = {
    fun(new Fixture(projectName))
  }
}
