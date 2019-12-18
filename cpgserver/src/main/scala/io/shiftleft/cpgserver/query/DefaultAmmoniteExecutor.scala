package io.shiftleft.cpgserver.query

import cats.effect.{ContextShift, IO}

class DefaultAmmoniteExecutor(implicit cs: ContextShift[IO]) extends ServerAmmoniteExecutor {
  override protected lazy val predef: String =
    """
      |import io.shiftleft.codepropertygraph.Cpg
      |import io.shiftleft.semanticcpg.language._
      |import io.shiftleft.semanticcpg.language.NoResolve
      |implicit val resolver = NoResolve
      |""".stripMargin
}
