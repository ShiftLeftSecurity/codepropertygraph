package io.shiftleft.layers

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg

class EnhancementRunner {
  def run(cpg: Cpg, serializedCpg: SerializedCpg): Unit = {
    val language = cpg.metaData.language.head
    new EnhancedBaseCreator(cpg.graph, language, serializedCpg).create
  }
}
