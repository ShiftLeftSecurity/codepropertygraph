package io.shiftleft.queryprimitives.utils

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.queryprimitives.Implicits._

object Statements {
  def countAll(cpg: Cpg): Long = {
    cpg.method.topLevelExpressions.s.count
  }
}
