package io.shiftleft.dataflowengine.language

import java.io.{ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets

import dnl.utils.text.table.TextTable
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.nodemethods.CfgNodeMethods

object FlowPrettyPrinter {
  def prettyPrint(path: List[nodes.TrackingPointBase]): String = {
    val baos = new ByteArrayOutputStream()
    val ps = new PrintStream(baos, true, "utf-8")
    val rows = path.map { trackingPoint =>
      // implicit val graph = trackingPoint.underlying.graph()
      val method = trackingPoint.start.method.head

      val trackedSymbol = trackingPoint match {
        case methodParamIn: nodes.MethodParameterIn => {
          s"${method.name}(${method.start.parameter.l.sortBy(_.order).map(_.code).mkString(", ")})"
        }
        case _ => CfgNodeMethods.repr(trackingPoint.cfgNode)
      }

      val lineNumberOption = trackingPoint.cfgNode.lineNumber
      val methodNameOption = Some(method.name)
      val fileOption = method.start.file.name.headOption

      s"$trackedSymbol\t${lineNumberOption.getOrElse("N/A")}\t" +
        s"${methodNameOption.getOrElse("N/A")}\t${fileOption.getOrElse("N/A")}"
    }
    val columnNames = List("tracked", "lineNumber", "method", "file").toArray
    val data = rows.map(_.split("\t").toArray).toArray.asInstanceOf[Array[Array[Object]]]
    new TextTable(columnNames, data).printTable(ps, 1)
    val content = new String(baos.toByteArray(), StandardCharsets.UTF_8)
    ps.close()
    content.toString
  }
}
