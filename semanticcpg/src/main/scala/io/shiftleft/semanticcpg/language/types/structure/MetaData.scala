package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal

/**
  * A meta data entry
  * */
class MetaData(val traversal: Traversal[nodes.MetaData]) extends AnyVal {

  /**
    * Returns the programming language of the code for which this CPG was
    * generated from.
    * */
  def language: Traversal[String] = traversal.map(_.language)

}
