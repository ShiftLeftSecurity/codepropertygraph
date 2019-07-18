package io.shiftleft.cpgqueryingtests.codepropertygraph

import gremlin.scala.{Graph, ScalaGraph}
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.layers.{DataFlowRunner, EnhancementRunner}
import io.shiftleft.passes.SerializedCpg
import io.shiftleft.semanticsloader.SemanticsLoader

class CpgTestFixture(projectName: String) {
  lazy val cpg = CpgLoader.load(s"resources/cpgs/$projectName/cpg.bin.zip", CpgLoaderConfig.withoutOverflow)
  new EnhancementRunner().run(cpg, new SerializedCpg())
  new DataFlowRunner(SemanticsLoader.emptySemantics).run(cpg, new SerializedCpg());
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
