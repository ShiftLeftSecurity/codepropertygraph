package io.shiftleft.dataflowengineoss.layers.dataflows

import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.layers.{LayerCreator, LayerCreatorContext, LayerCreatorOptions}
import overflowdb.traversal._
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics

case class PdgDumpOptions(var outDir: String) extends LayerCreatorOptions {}

object DumpPdg {

  val overlayName = "dumpPdg"

  val description = "Dump program dependence graph to out/"

  def defaultOpts: PdgDumpOptions = PdgDumpOptions("out")
}

class DumpPdg(options: PdgDumpOptions)(implicit semantics: Semantics) extends LayerCreator {
  override val overlayName: String = DumpPdg.overlayName
  override val description: String = DumpPdg.description

  override def create(context: LayerCreatorContext, serializeInverse: Boolean): Unit = {
    val cpg = context.cpg
    cpg.method.zipWithIndex.foreach {
      case (method, i) =>
        val str = method.start.dotPdg.head
        (File(options.outDir) / s"${i}-pdg.dot").write(str)
    }
  }

  override def probe(cpg: Cpg): Boolean = false
}
