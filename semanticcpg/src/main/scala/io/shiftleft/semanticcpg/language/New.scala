package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.langv2.types.expressions.generalizations.AstNodeTraversalImplicits
import io.shiftleft.semanticcpg.langv2.types.structure.{LocalTraversalImplicits, MethodTraversalImplicits}

object New
  extends MethodTraversalImplicits
  with AstNodeTraversalImplicits
  with LocalTraversalImplicits