package io.shiftleft.semanticcpg.utils

import io.shiftleft.codepropertygraph.Cpg

object Statements {
  def countAll(cpg: Cpg): Long = {
    cpg.method.topLevelExpressions.s.count
  }
}
