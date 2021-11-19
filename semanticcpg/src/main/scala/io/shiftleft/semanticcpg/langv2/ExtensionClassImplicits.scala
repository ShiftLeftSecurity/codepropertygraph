package io.shiftleft.semanticcpg.langv2

import io.shiftleft.semanticcpg.langv2.types.expressions.generalizations.AstNodeTraversalImplicits
import io.shiftleft.semanticcpg.langv2.types.structure.{LocalTraversalImplicits, MethodTraversalImplicits}

trait ExtensionClassImplicits
  extends MethodTraversalImplicits
  with AstNodeTraversalImplicits
  with LocalTraversalImplicits
