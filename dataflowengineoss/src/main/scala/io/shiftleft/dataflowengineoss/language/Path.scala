package io.shiftleft.dataflowengineoss.language

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.help.Table
import io.shiftleft.semanticcpg.language._

case class Path(elements: List[nodes.TrackingPoint])

object Path {

  implicit val show: Show[Path] = { path: Path =>
    Table(
      columnNames = Array("tracked", "lineNumber", "method", "file"),
      rows = path.elements.map { trackingPoint =>
        val method = trackingPoint.method
        val methodName = method.name
        val lineNumber = trackingPoint.cfgNode.lineNumber.getOrElse("N/A").toString
        val fileName = method.file.name.headOption.getOrElse("N/A")

        val trackedSymbol = trackingPoint match {
          case _: nodes.MethodParameterIn =>
            val paramsPretty = method.parameter.toList.sortBy(_.order).map(_.code).mkString(", ")
            s"$methodName($paramsPretty)"
          case _ => trackingPoint.cfgNode.repr
        }

        Array(trackedSymbol, lineNumber, methodName, fileName)
      }
    ).render
  }

}
