package io.shiftleft.queryprimitives.utils

import io.shiftleft.queryprimitives.steps.starters.Cpg

object Statements {
  def countAll(cpg: Cpg): Long = {
    cpg.method.topLevelExpressions.s.count
  }
}
