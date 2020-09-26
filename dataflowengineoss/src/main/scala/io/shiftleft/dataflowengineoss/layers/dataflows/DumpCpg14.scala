package io.shiftleft.dataflowengineoss.layers.dataflows

import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.layers.{LayerCreator, LayerCreatorContext, LayerCreatorOptions}
import overflowdb.traversal._
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics

case class Cpg14DumpOptions(var outDir: String) extends LayerCreatorOptions {}

object DumpCpg14 {

  val overlayName = "dumpCpg14"

  val description = "Dump Code Property Graph (2014) to out/"

  def defaultOpts: Cpg14DumpOptions = Cpg14DumpOptions("out")
}

class DumpCpg14(options: Cpg14DumpOptions)(implicit semantics: Semantics) extends LayerCreator {
  override val overlayName: String = DumpDdg.overlayName
  override val description: String = DumpDdg.description

  override def create(context: LayerCreatorContext, serializeInverse: Boolean): Unit = {
    val cpg = context.cpg
    cpg.method.zipWithIndex.foreach {
      case (method, i) =>
        val str = method.start.dotCpg14.head
        (File(options.outDir) / s"${i}-cpg.dot").write(str)
    }
  }

  override def probe(cpg: Cpg): Boolean = false
}
