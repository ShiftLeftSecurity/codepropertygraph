package io.shiftleft.queryprimitives.utils

import io.shiftleft.queryprimitives.steps.starters.Cpg
import io.shiftleft.queryprimitives.steps.Implicits._

object Statements {
  def countAll(cpg: Cpg): Long = {
    cpg.method.topLevelExpressions.s.count
  }
}
