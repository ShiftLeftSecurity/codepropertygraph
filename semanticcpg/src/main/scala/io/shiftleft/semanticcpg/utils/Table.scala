package io.shiftleft.semanticcpg.utils

import java.io.{ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets

import dnl.utils.text.table.TextTable

case class Table(columnNames: List[String], rows: List[String]) {

  def render: String = {
    val outStream = new ByteArrayOutputStream()
    val ps = new PrintStream(outStream, true, "utf-8")
    val data = rows.map(_.split("\t").toArray).toArray.asInstanceOf[Array[Array[Object]]]
    new TextTable(columnNames.toArray, data).printTable(ps, 1)
    val content = new String(outStream.toByteArray, StandardCharsets.UTF_8)
    ps.close()
    content
  }

}
