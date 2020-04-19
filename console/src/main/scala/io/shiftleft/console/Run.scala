package io.shiftleft.console

import io.shiftleft.semanticcpg.layers.LayerCreator
import org.reflections.Reflections

import scala.jdk.CollectionConverters._

object Run {

  /**
    * Generate code for the run command
    * @param exclude list of analyzers to exclude (by full class name)
    * */
  def codeForRunCommand(exclude: List[String] = List()): String = {
    val r = new Reflections("io.shiftleft")
    val layerCreatorTypeNames = r
      .getSubTypesOf(classOf[LayerCreator])
      .asScala
      .toList
      .map(t => (t.getSimpleName.toLowerCase, t.getName))
      .filter(t => !exclude.contains(t._2))

    val membersCode = layerCreatorTypeNames
      .map { case (varName, typeName) => s"def $varName: Cpg = _runAnalyzer(new $typeName())" }
      .mkString("\n")

    val toStringCode =
      s"""
         | override def toString() : String = {
         |  val columnNames = List("name", "description")
         |  val rows =
         |   ${layerCreatorTypeNames.map {
        case (varName, typeName) =>
          s"""List("$varName",$typeName.description.trim)"""
      }}.map(_.mkString("\\t"))
         | Table.create(columnNames, rows)
         | }
         |""".stripMargin

    s"""
       | class OverlaysDynamic {
       |
       | $membersCode
       |
       | $toStringCode
       | }
       | val run = new OverlaysDynamic()
       |""".stripMargin
  }

}
