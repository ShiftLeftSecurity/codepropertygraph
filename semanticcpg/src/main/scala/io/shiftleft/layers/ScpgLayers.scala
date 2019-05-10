package io.shiftleft.layers

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.layers.enhancedbase.EnhancedBaseCreator
import io.shiftleft.semanticsloader.Semantics

class ScpgLayers(semantics: Semantics) {
  def run(cpg: Cpg, serializedCpg: SerializedCpg): Unit = {

    val language = cpg.metaData.language.head
    new EnhancedBaseCreator(cpg.graph, language, serializedCpg, semantics).create
  }
}
