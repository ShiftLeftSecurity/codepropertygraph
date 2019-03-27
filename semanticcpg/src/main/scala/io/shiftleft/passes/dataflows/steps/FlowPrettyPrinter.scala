// TODO markus port to TrackingPoint
// package io.shiftleft.passes.dataflows.steps

// import java.io.{ByteArrayOutputStream, PrintStream}
// import java.nio.charset.StandardCharsets

// import dnl.utils.text.table.TextTable
// import io.shiftleft.codepropertygraph.generated.nodes
// import io.shiftleft.queryprimitives.steps.Implicits._
// import io.shiftleft.passes.dataflows.steps.DataFlowObject._
// import io.shiftleft.passes.dataflows._

// object FlowPrettyPrinter {
//   def prettyPrint(path: List[nodes.DataFlowObject]): String = {
//     val baos = new ByteArrayOutputStream()
//     val ps = new PrintStream(baos, true, "utf-8")
//     val rows = path.map { dataFlowObject =>
//       implicit val graph = dataFlowObject.underlying.graph()
//       val method = dataFlowObject.start.method.head
//       val trackedSymbol = dataFlowObject.code
//       val lineNumberOption = dataFlowObject.lineNumber
//       val methodNameOption = Some(method.name)
//       val fileOption = method.start.file.name.headOption

//       s"$trackedSymbol\t${lineNumberOption.getOrElse("N/A")}\t" +
//         s"${methodNameOption.getOrElse("N/A")}\t${fileOption.getOrElse("N/A")}"
//     }
//     val columnNames = List("tracked", "lineNumber", "method", "file").toArray
//     val data = rows.map(_.split("\t").toArray).toArray.asInstanceOf[Array[Array[Object]]]
//     new TextTable(columnNames, data).printTable(ps, 1)
//     val content = new String(baos.toByteArray(), StandardCharsets.UTF_8)
//     ps.close()
//     content.toString
//   }
// }
