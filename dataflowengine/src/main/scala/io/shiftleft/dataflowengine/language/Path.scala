package io.shiftleft.dataflowengine.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.overflowdb.traversal.help.Table
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.nodemethods.CfgNodeMethods

case class Path(elements: List[nodes.TrackingPoint])

object Path {

  implicit val show: Show[Path] = { path: Path =>
    Table(
      columnNames = Array("tracked", "lineNumber", "method", "file"),
      rows = path.elements.map { trackingPoint =>
        val method = trackingPoint.method
        val methodName = method.name
        val lineNumber = trackingPoint.cfgNode.lineNumber.getOrElse("N/A").toString
        val fileName = method.start.file.name.headOption.getOrElse("N/A")

        val trackedSymbol = trackingPoint match {
          case _: nodes.MethodParameterIn =>
            val paramsPretty = method.parameter.l.sortBy(_.order).map(_.code).mkString(", ")
            s"$methodName($paramsPretty)"
          case _ => CfgNodeMethods.repr(trackingPoint.cfgNode)
        }

        Array(trackedSymbol, lineNumber, methodName, fileName)
      }
    ).render
  }

}
