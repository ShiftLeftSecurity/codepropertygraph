package io.shiftleft.semanticcpg.utils

import dnl.utils.text.table.TextTable
import java.io.{ByteArrayOutputStream, PrintStream}
import java.nio.charset.StandardCharsets
import scala.util.Using

case class Table(columnNames: Iterable[String], rows: Iterable[Iterable[String]]) {

  lazy val render: String = {
    Using.Manager { use =>
      val charset = StandardCharsets.UTF_8
      val baos = use(new ByteArrayOutputStream)
      val ps = use(new PrintStream(baos, true, charset.name))
      val rowsAsArray = rows.map(_.toArray.asInstanceOf[Array[Object]]).toArray
      new TextTable(columnNames.toArray, rowsAsArray).printTable(ps, 0)
      new String(baos.toByteArray, charset)
    }.get
  }

}
