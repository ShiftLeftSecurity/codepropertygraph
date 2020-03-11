package io.shiftleft.dataflowengine.language

import dnl.utils.text.table.TextTable
import io.shiftleft.semanticcpg.language.Show
import java.io.{ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.nodemethods.CfgNodeMethods

import scala.util.Using

case class Path(elements: List[nodes.TrackingPoint])

object Path {

  implicit val show: Show[Path] = (path: Path) =>
    Using.Manager { use =>
      val baos = use(new ByteArrayOutputStream)
      val ps = use(new PrintStream(baos, true, "utf-8"))

      val rows = path.elements.map { trackingPoint =>
        val method = trackingPoint.method
        val methodName= method.name
        val lineNumber = trackingPoint.cfgNode.lineNumber.getOrElse("N/A")
        val fileName = method.start.file.name.headOption.getOrElse("N/A")

        val trackedSymbol = trackingPoint match {
          case _: nodes.MethodParameterIn =>
            val paramsPretty = method.parameter.l.sortBy(_.order).map(_.code).mkString(", ")
            s"$methodName($paramsPretty)"
          case _ => CfgNodeMethods.repr(trackingPoint.cfgNode)
        }

        s"$trackedSymbol\t$lineNumber\t$methodName\t$fileName"
      }

      val columnNames = Array("tracked", "lineNumber", "method", "file")
      val data = rows.map(_.split("\t").toArray[Object]).toArray
      new TextTable(columnNames, data).printTable(ps, 1)
      new String(baos.toByteArray, StandardCharsets.UTF_8)
    }.get

}
