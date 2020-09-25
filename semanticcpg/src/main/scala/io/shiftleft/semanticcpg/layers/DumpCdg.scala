package io.shiftleft.semanticcpg.layers

import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

case class CdgDumpOptions(var outDir: String) extends LayerCreatorOptions {}

object DumpCdg {

  val overlayName = "dumpCdg"

  val description = "Dump control dependence graph to out/"

  def defaultOpts: CdgDumpOptions = CdgDumpOptions("out")
}

class DumpCdg(options: CdgDumpOptions) extends LayerCreator {
  override val overlayName: String = DumpCdg.overlayName
  override val description: String = DumpCdg.description

  override def create(context: LayerCreatorContext, serializeInverse: Boolean): Unit = {
    val cpg = context.cpg
    cpg.method.zipWithIndex.foreach {
      case (method, i) =>
        val str = method.start.dotCdg.head
        (File(options.outDir) / s"${i}-cdg.dot").write(str)
    }
  }

  override def probe(cpg: Cpg): Boolean = false
}
