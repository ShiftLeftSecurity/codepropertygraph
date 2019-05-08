package io.shiftleft.queryprimitives.dsl

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.MethodReturn
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.pipetypes.ShallowPipe.ShallowPipe
import io.shiftleft.queryprimitives.dsl.pipeops.{RealPipeOperations, ShallowPipeOperations}
import io.shiftleft.queryprimitives.steps.types.structure.{MethodMethods, MethodReturnMethods}

import scala.language.higherKinds

object Implicits extends PipeOperationImplicits with LowPriorityImplicits {
  implicit def realPipeMethods[ElemType](pipe: RealPipe[ElemType])
  : GenericPipeMethods[RealPipe,ElemType] = {
    new GenericPipeMethods(pipe)
  }

  implicit def methodMethods[PipeType[+_]](pipe: PipeType[nodes.Method])
  : MethodMethods[PipeType] = {
    new MethodMethods(pipe)
  }

  implicit def methodReturnMethods[PipeType[+_]](pipe: PipeType[nodes.MethodReturn])
  : MethodReturnMethods[PipeType] = {
    new MethodReturnMethods(pipe)
  }

}

class PipeOperationImplicits {
  implicit val realPipeOps: RealPipeOperations = new RealPipeOperations()
  implicit val shallowPipeOps: ShallowPipeOperations = new ShallowPipeOperations()

}

sealed trait LowPriorityImplicits {

  implicit def methodMethods(pipe: nodes.Method)
  : MethodMethods[ShallowPipe] = {
    new MethodMethods(pipe.asInstanceOf[ShallowPipe[nodes.Method]])
  }

  implicit def methodReturnMethods(pipe: nodes.MethodReturn)
  : MethodReturnMethods[ShallowPipe] = {
    new MethodReturnMethods(pipe.asInstanceOf[ShallowPipe[MethodReturn]])
  }

}


