package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.codepropertygraph.generated.{nodes => basenodes}

class AssignTarget(override val raw: GremlinScala[basenodes.Expression]) extends NodeSteps[basenodes.Expression](raw) {

  def arrayAccess: ArrayAccess = new ArrayAccess(
    new AssignTarget(raw).ast.isCall
      .nameExact("<operator>.computedMemberAccess")
      .raw
      .map(new nodes.ArrayAccess(_))
  )

}
