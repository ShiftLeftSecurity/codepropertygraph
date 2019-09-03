package io.shiftleft.dataflowengine

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.dataflowengine.language.extensions.{Call, Method}
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.{Method => OriginalMethod}
import io.shiftleft.semanticcpg.language.types.expressions.{Call => OriginalCall}

package object language {

  // TODO MP: rather use `start` mechanism?
  // alternative: move to `nodes` package object?

  // TODO fabs: I'm wondering the same. I think the reason Markus is doing it
  // like this is performance of `.start`. We should get some more insights
  // into performance of `.start` vs this mechanism and then go for one or
  // the other.

  implicit def trackingPointBaseMethodsQp(node: nodes.TrackingPointBase): TrackingPointMethods =
    new TrackingPointMethods(node.asInstanceOf[nodes.TrackingPoint])

  implicit def toTrackingPoint[NodeType <: nodes.TrackingPointBase](steps: Steps[NodeType]): TrackingPoint =
    new TrackingPoint(steps.raw.cast[nodes.TrackingPoint])

  implicit def toMethodForCallGraph[X <% OriginalMethod](original : X) : Method =
    new Method(original)

  implicit def toCallForCallGraph[X <% OriginalCall](original : X) : Call =
    new Call(original)

}
