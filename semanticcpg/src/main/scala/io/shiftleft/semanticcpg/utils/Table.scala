package io.shiftleft.semanticcpg.utils

import java.io.{ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets

import dnl.utils.text.table.TextTable

case class Table(columnNames: Iterable[String], rows: Iterable[Iterable[String]]) {

  def render: String = {
    val outStream = new ByteArrayOutputStream()
    val ps = new PrintStream(outStream, true, "utf-8")
    val data = rows.map(_.toArray).toArray
    new TextTable(columnNames.toArray, data.asInstanceOf[Array[Array[Object]]]).printTable(ps, 1)
    val content = new String(outStream.toByteArray, StandardCharsets.UTF_8)
    ps.close()
    content
  }

}
