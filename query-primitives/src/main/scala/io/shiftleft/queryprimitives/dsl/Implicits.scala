package io.shiftleft.queryprimitives.dsl

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.dsl.ShallowPipe.ShallowPipe
import io.shiftleft.queryprimitives.dsl.pipeops.{RealPipeOperations, ShallowPipeOperations}
import io.shiftleft.queryprimitives.steps.types.structure.MethodMethods

object Implicits extends PipeOperationImplicits with LowPriorityImplicits {

  implicit def methodMethods(pipe: RealPipe[nodes.Method]) = {
    new MethodMethods(pipe)
  }

}

class PipeOperationImplicits {
  implicit val realPipeOps = new RealPipeOperations()
  implicit val shallowPipeOps = new ShallowPipeOperations()
}

sealed trait LowPriorityImplicits {

  implicit def methodMethods(pipe: ShallowPipe[nodes.Method]) = {
    new MethodMethods(pipe)
  }

}


