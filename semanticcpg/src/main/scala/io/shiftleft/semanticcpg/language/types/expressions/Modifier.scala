package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.Steps
import overflowdb.traversal.Traversal

class Modifier(val traversal: Traversal[nodes.Modifier]) extends AnyVal {

  def modifierType: Steps[String] =
    traversal.map(_.modifierType)

}
