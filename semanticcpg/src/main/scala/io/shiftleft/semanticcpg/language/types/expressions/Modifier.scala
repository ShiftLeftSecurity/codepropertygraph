package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal

class Modifier(val traversal: Traversal[nodes.Modifier]) extends AnyVal {

  def modifierType: Traversal[String] =
    traversal.map(_.modifierType)

}
