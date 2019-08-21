package io.shiftleft.layers

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.queryprimitives.Implicits._

class EnhancementRunner {
  def run(cpg: Cpg, serializedCpg: SerializedCpg): Unit = {
    val language = cpg.metaData.language.headOption.getOrElse("")
    new EnhancedBaseCreator(cpg, language, serializedCpg).create
  }
}
