package io.shiftleft.queryprimitives.dsl

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.pipetypes.ShallowPipe.ShallowPipe
import io.shiftleft.queryprimitives.dsl.pipeops.{RealPipeOperations, ShallowPipeOperations}
import io.shiftleft.queryprimitives.steps.types.expressions.CallMethods
import io.shiftleft.queryprimitives.steps.types.structure.{MethodInstMethods, MethodMethods, MethodReturnMethods}

import scala.language.higherKinds

object Implicits extends PipeOperationImplicits with LowPriorityImplicits {
  implicit def realPipeMethods[ElemType](pipe: RealPipe[ElemType])
  : GenericPipeMethods[RealPipe,ElemType] = {
    new GenericPipeMethods(pipe)
  }

  implicit def callMethods[PipeType[+_]](pipe: PipeType[nodes.Call])
  : CallMethods[PipeType] = {
    new CallMethods(pipe)
  }

  implicit def methodMethods[PipeType[+_]](pipe: PipeType[nodes.Method])
  : MethodMethods[PipeType] = {
    new MethodMethods(pipe)
  }

  implicit def methodInstMethods[PipeType[+_]](pipe: PipeType[nodes.MethodInst])
  : MethodInstMethods[PipeType] = {
    new MethodInstMethods(pipe)
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

  implicit def callMethods(pipe: nodes.Call)
  : CallMethods[ShallowPipe] = {
    new CallMethods(pipe.asInstanceOf[ShallowPipe[nodes.Call]])
  }

  implicit def methodMethods(pipe: nodes.Method)
  : MethodMethods[ShallowPipe] = {
    new MethodMethods(pipe.asInstanceOf[ShallowPipe[nodes.Method]])
  }

  implicit def methodInstMethods(pipe: nodes.MethodInst)
  : MethodInstMethods[ShallowPipe] = {
    new MethodInstMethods(pipe.asInstanceOf[ShallowPipe[nodes.MethodInst]])
  }

  implicit def methodReturnMethods(pipe: nodes.MethodReturn)
  : MethodReturnMethods[ShallowPipe] = {
    new MethodReturnMethods(pipe.asInstanceOf[ShallowPipe[nodes.MethodReturn]])
  }

}


