package io.shiftleft.passes.dataflows.steps

import java.io.{ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets

import dnl.utils.text.table.TextTable
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps._
import io.shiftleft.queryprimitives.steps.ext.dataflowengine._

object FlowPrettyPrinter {
  def prettyPrint(path: List[nodes.TrackingPoint]): String = {
    val baos = new ByteArrayOutputStream()
    val ps = new PrintStream(baos, true, "utf-8")
    val rows = path.map { trackingPoint =>
      implicit val graph = trackingPoint.underlying.graph()
      val method = trackingPoint.start.method.head
      val trackedSymbol = trackingPoint.cfgNode.code
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
