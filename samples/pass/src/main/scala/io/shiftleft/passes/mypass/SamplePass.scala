package io.shiftleft.mypass

import gremlin.scala.ScalaGraph
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.cpgloading.CpgLoader
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.{CpgPass, CpgPassRunner}

class SamplePass(graph : ScalaGraph) extends CpgPass(graph) {
  override def run(): Iterator[DiffGraph] = {
   val diffGraph = new DiffGraph
    // Construct additive diff here
    Iterator(diffGraph)
  }
}

object SamplePassMain extends App {
  val filename = "src_cpg.bin.zip"
  val cpg = CpgLoader.load(filename)
  val serializedCpg = new SerializedCpg("/tmp/dst.bin.zip")
  val runner = new CpgPassRunner(serializedCpg)
  val pass = new SamplePass(cpg.graph)
  runner.createStoreAndApplyOverlay(pass)
}
