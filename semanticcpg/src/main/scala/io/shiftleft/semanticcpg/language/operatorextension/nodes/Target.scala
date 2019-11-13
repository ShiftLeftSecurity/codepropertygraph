package io.shiftleft.semanticcpg.language.operatorextension.nodes

import io.shiftleft.codepropertygraph.generated.{nodes => basenodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.operatorextension.nodes
import io.shiftleft.semanticcpg.language.operatorextension.{ArrayAccess => ArrayAccessStep}

class Target(val expr: basenodes.Expression) extends AnyRef {

  def arrayAccess: ArrayAccessStep =
    new ArrayAccessStep(
      expr.ast.isCall
        .nameExact("<operator>.computedMemberAccess")
        .map(new nodes.ArrayAccess(_))
        .raw)

}
