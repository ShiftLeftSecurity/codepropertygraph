package io.shiftleft.console

import dnl.utils.text.table.TextTable
import java.io.{ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets

object Table {

  def create(columnNames: List[String], rows: List[String]): String = {
    val outStream = new ByteArrayOutputStream()
    val ps = new PrintStream(outStream, true, "utf-8")
    val data = rows.map(_.split("\t").toArray).toArray.asInstanceOf[Array[Array[Object]]]
    new TextTable(columnNames.toArray, data).printTable(ps, 1)
    val content = new String(outStream.toByteArray, StandardCharsets.UTF_8)
    ps.close()
    "\n" + content.toString
  }

}
