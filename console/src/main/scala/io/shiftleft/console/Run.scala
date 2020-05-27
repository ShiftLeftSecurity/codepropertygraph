package io.shiftleft.console

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language.HasStoreMethod
import io.shiftleft.semanticcpg.layers.{LayerCreator, LayerCreatorContext}
import org.reflections.Reflections

import scala.jdk.CollectionConverters._

object Run {

  def runCustomQuery(console: Console[_], query: HasStoreMethod): Unit = {
    console._runAnalyzer(
      new LayerCreator {
        override val overlayName: String = "custom"
        override val description: String = "A custom pass"

        override def create(context: LayerCreatorContext, serializeInverse: Boolean): Unit = {
          val serializedCpg = initSerializedCpg(context.outputDir, "custom", 0)
          val pass = new CpgPass(console.cpg) {
            override def run(): Iterator[DiffGraph] = {
              implicit val diffGraph: DiffGraph.Builder = DiffGraph.newBuilder
              query.store
              Iterator(diffGraph.build())
            }
          }
          pass.createApplySerializeAndStore(serializedCpg, inverse = true, "custom")
          serializedCpg.close()
        }
        override def probe(cpg: Cpg): Boolean = false
      }
    )
  }

  /**
    * Generate code for the run command
    * @param exclude list of analyzers to exclude (by full class name)
    * */
  def codeForRunCommand(exclude: List[String] = List()): String = {
    val r = new Reflections("io.shiftleft")
    val layerCreatorTypeNames = r
      .getSubTypesOf(classOf[LayerCreator])
      .asScala
      .filterNot(t => t.isAnonymousClass || t.isLocalClass || t.isMemberClass || t.isSynthetic)
      .toList
      .map(t => (t.getSimpleName.toLowerCase, t.getName))
      .filter(t => !exclude.contains(t._2))

    val optsMembersCode = layerCreatorTypeNames
      .map { case (varName, typeName) => s"val $varName = $typeName.defaultOpts" }
      .mkString("\n")

    val optsCode =
      s"""
        |class OptsDynamic {
        | $optsMembersCode
        |}
        |
        |val opts = new OptsDynamic()
        |""".stripMargin

    val membersCode = layerCreatorTypeNames
      .map { case (varName, typeName) => s"def $varName: Cpg = _runAnalyzer(new $typeName(opts.$varName))" }
      .mkString("\n")

    val toStringCode =
      s"""
         | import io.shiftleft.overflowdb.traversal.help.Table
         | override def toString() : String = {
         |  val columnNames = List("name", "description")
         |  val rows =
         |   ${layerCreatorTypeNames.map {
           case (varName, typeName) =>
             s"""List("$varName",$typeName.description.trim)"""
         }}
         | "\\n" + Table(columnNames, rows).render
         | }
         |""".stripMargin

    optsCode +
      s"""
       | class OverlaysDynamic {
       |
       | def apply(query : io.shiftleft.semanticcpg.language.HasStoreMethod) {
       |   io.shiftleft.console.Run.runCustomQuery(console, query)
       | }
       |
       | $membersCode
       |
       | $toStringCode
       | }
       | val run = new OverlaysDynamic()
       |""".stripMargin
  }

}
