package io.shiftleft.queryprimitives.dsl

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.dsl.ShallowPipe.ShallowPipe
import io.shiftleft.queryprimitives.steps.types.structure.MethodMethods

object Implicits extends PipeOperationImplicits with LowPriorityImplicits {
  implicit def realPipeMethods[ElemType](pipe: RealPipe[ElemType]) = {
    new RealPipeMethods(pipe)
  }

  implicit def methodMethods(pipe: RealPipe[nodes.Method]) = {
    new MethodMethods(pipe)
  }

}

class PipeOperationImplicits {
  implicit val realPipeOps = new RealPipeOperations[Any]()

  implicit val methodRealPipeOps = new RealPipeOperations[nodes.Method]()
  implicit val methodShallowPipeOps = new ShallowPipeOperations[nodes.Method]()
}

sealed trait LowPriorityImplicits {

  implicit def methodMethods(pipe: ShallowPipe[nodes.Method]) = {
    new MethodMethods(pipe)
  }

}


