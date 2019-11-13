package io.shiftleft.semanticcpg.language.operatorextension

import gremlin.scala.GremlinScala
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.codepropertygraph.generated.nodes

class AssignTarget(override val raw: GremlinScala[nodes.Expression]) extends NodeSteps[nodes.Expression](raw) {

  def arrayAccess: ArrayAccess = new ArrayAccess(
    new AssignTarget(raw).ast.isCall
      .nameExact("<operator>.computedMemberAccess")
      .raw
      .map(new ArrayAccessNode(_))
  )

}
