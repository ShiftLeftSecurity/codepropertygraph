package io.shiftleft

import better.files._

package object console {

  /** ammonite doesn't seem to regard the order of the lines in the predef code blocks, which leads to problems with
    * overlapping namespaces (concrete example being `help`). However, code blocks can be separated by `@`...
    * see {{{ammonite.Main}}} */
  val AmmoniteCodeBlockSeparator =
    """
      |@
      |""".stripMargin

  implicit class UnixUtils[A](content: Iterable[A]) {

    /**
      * Iterate over left hand side operand
      * and write to file. Think of it as the
      * Ocular version of the Unix `>` shell redirection.
      * */
    def |>(outfile: String): Unit =
      File(outfile).write(content.mkString("\n"))

    /**
      * Iterate over left hand side operand
      * and append to file. Think of it as the Ocular
      * version of the Unix `>>` shell redirection.
      * */
    def |>>(outfile: String): Unit =
      File(outfile).append("\n").append(content.mkString("\n"))
  }

  implicit class StringOps(value: String) {

    /**
      * Pipe string to file. Think of it as the Ocular version
      * of the Unix `>` shell redirection.
      * */
    def |>(outfile: String): Unit =
      File(outfile).write(value)

    /**
      * Append string to file. Think of it as the Ocular
      * version of the Unix `>>` shell redirection.
      * */
    def |>>(outfile: String): Unit =
      File(outfile).append("\n").append(value)
  }

}
