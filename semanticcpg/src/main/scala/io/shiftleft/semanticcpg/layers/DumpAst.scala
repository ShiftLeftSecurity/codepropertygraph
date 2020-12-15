package io.shiftleft.semanticcpg.layers
import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.semanticcpg.language._

case class AstDumpOptions(var outDir: String) extends LayerCreatorOptions {}

object DumpAst {

  val overlayName = "dumpAst"

  val description = "Dump abstract syntax trees to out/"

  def defaultOpts: AstDumpOptions = AstDumpOptions("out")
}

class DumpAst(options: AstDumpOptions) extends LayerCreator {
  override val overlayName: String = DumpAst.overlayName
  override val description: String = DumpAst.description
  override val modifiesCpg: Boolean = false

  override def create(context: LayerCreatorContext, storeUndoInfo: Boolean): Unit = {
    val cpg = context.cpg
    cpg.method.zipWithIndex.foreach {
      case (method, i) =>
        val str = method.dotAst.head
        (File(options.outDir) / s"${i}-ast.dot").write(str)
    }
  }

  override def probe(cpg: Cpg): Boolean = false
}
