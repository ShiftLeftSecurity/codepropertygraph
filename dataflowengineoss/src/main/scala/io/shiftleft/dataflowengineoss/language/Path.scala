package io.shiftleft.dataflowengineoss.language

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.help.Table

case class Path(elements: List[nodes.CfgNode])

object Path {

  implicit val show: Show[Path] = { path: Path =>
    Table(
      columnNames = Array("tracked", "lineNumber", "method", "file"),
      rows = path.elements.map { cfgNode =>
        val method = cfgNode.method
        val methodName = method.name
        val lineNumber = cfgNode.lineNumber.getOrElse("N/A").toString
        val fileName = method.file.name.headOption.getOrElse("N/A")

        val trackedSymbol = cfgNode match {
          case _: nodes.MethodParameterIn =>
            val paramsPretty = method.parameter.toList.sortBy(_.order).map(_.code).mkString(", ")
            s"$methodName($paramsPretty)"
          case x => x.statement.repr
        }

        Array(trackedSymbol, lineNumber, methodName, fileName)
      }
    ).render
  }

}
