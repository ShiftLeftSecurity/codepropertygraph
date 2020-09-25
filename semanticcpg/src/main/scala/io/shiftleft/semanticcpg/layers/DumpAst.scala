package io.shiftleft.semanticcpg.layers
import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

case class DumpOptions(var outDir: String) extends LayerCreatorOptions {}

object DumpAst {

  val overlayName = "dumpAst"

  val description = "Dump abstract syntax trees to out/"

  def defaultOpts: DumpOptions = DumpOptions("out")
}

class DumpAst(options: DumpOptions) extends LayerCreator {
  override val overlayName: String = DumpAst.overlayName
  override val description: String = DumpAst.description

  override def create(context: LayerCreatorContext, serializeInverse: Boolean): Unit = {
    val cpg = context.cpg
    cpg.method.zipWithIndex.foreach {
      case (method, i) =>
        val str = method.start.dotAst.head
        (File(options.outDir) / s"${i}-ast.dot").write(str)
    }
  }

  override def probe(cpg: Cpg): Boolean = false
}
