package io.shiftleft.console.embammonite

import java.util.UUID

import org.scalatest.{Matchers, WordSpec}

class EmbeddedAmmoniteTests extends WordSpec with Matchers {

  // The standard frontend attempts to query /dev/tty
  // in multiple places, e.g., to query terminal dimensions.
  // This does not work in intellij tests
  // (see https://github.com/lihaoyi/Ammonite/issues/276)
  // The below hack overrides the default frontend with
  // a custom frontend that does not require /dev/tty
  // For now, we only use this hack for testing, however,
  // it may be necessary to introduce it in production
  // if we want to support environments where /dev/tty
  // is not properly configured.

  private val predef =
    """
    | class CustomFrontend extends ammonite.repl.AmmoniteFrontEnd() {
    |   override def width = 100
    |   override def height = 100
    |
    |  override def readLine(reader: java.io.Reader,
    |                        output: java.io.OutputStream,
    |                        prompt: String,
    |                        colors: ammonite.util.Colors,
    |                        compilerComplete: (Int, String) => (Int, Seq[String], Seq[String]),
    |                        history: IndexedSeq[String]) = {
    |
    |  val writer = new java.io.OutputStreamWriter(output)
    |
    | val multilineFilter = ammonite.terminal.Filter.action(
    |   ammonite.terminal.SpecialKeys.NewLine,
    |   ti => ammonite.interp.Parsers.split(ti.ts.buffer.mkString).isEmpty
    | ){
    | case ammonite.terminal.TermState(rest, b, c, _) => ammonite.terminal.filters.BasicFilters.injectNewLine(b, c, rest)
    |}
    |
    |  val allFilters = ammonite.terminal.filters.BasicFilters.all
    |
    |  new ammonite.terminal.LineReader(width, prompt, reader, writer, allFilters)
    |  .readChar(ammonite.terminal.TermState(ammonite.terminal.LazyList.continually(reader.read()), Vector.empty, 0, ""), 0)
    | }
    |}
    | repl.frontEnd() = new CustomFrontend()
    |
    |""".stripMargin

  "EmbeddedAmmoniteShell" should {
    "start and shutdown" in {
      val shell = new EmbeddedAmmonite(predef)
      shell.start()
      shell.shutdown()
    }

    "execute a command and return output" in {
      val shell = new EmbeddedAmmonite(predef)
      shell.start()
      val uuid = UUID.randomUUID()
      shell.enqueue(uuid, "val x = 1")
      shell.result(uuid)
      shell.shutdown()
    }
  }

}
