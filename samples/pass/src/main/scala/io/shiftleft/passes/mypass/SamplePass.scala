package io.shiftleft.mypass

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.codepropertygraph.cpgloading.{CpgLoader, CpgLoaderConfig}
import io.shiftleft.codepropertygraph.generated.nodes.NewFile
import io.shiftleft.passes.SimpleCpgPass

class SamplePass(cpg: Cpg) extends SimpleCpgPass(cpg) {
  override def run(builder: DiffGraphBuilder): Unit = {
    val fileNode = NewFile().name("foo")
    builder.addNode(fileNode)
  }
}

object SamplePassMain extends App {
  val filename = "src_cpg.bin.zip"
  val odbConfig    = new overflowdb.Config().withStorageLocation(filename)
  val loaderConfig = CpgLoaderConfig().withOverflowConfig(odbConfig)
  val cpg = CpgLoader.loadFromOverflowDb(loaderConfig)
  val serializedCpg = new SerializedCpg("/tmp/dst.bin.zip")
  val pass = new SamplePass(cpg)
  pass.createApplySerializeAndStore(serializedCpg)
}

