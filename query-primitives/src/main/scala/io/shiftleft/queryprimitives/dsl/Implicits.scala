package io.shiftleft.queryprimitives.dsl

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.pipetypes.ShallowPipe.ShallowPipe
import io.shiftleft.queryprimitives.dsl.pipeops.{RealPipeOperations, ShallowPipeOperations}
import io.shiftleft.queryprimitives.steps.types.expressions.CallMethods
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.HasFullNameMethods
import io.shiftleft.queryprimitives.steps.types.structure.{BlockMethods, MethodInstMethods, MethodMethods, MethodReturnMethods, TypeDeclMethods}

import scala.language.higherKinds

object Implicits extends PipeOperationImplicits with LowPriorityImplicits {
  implicit def realPipeMethods[ElemType](pipe: RealPipe[ElemType])
  : GenericPipeMethods[RealPipe,ElemType] = {
    new GenericPipeMethods(pipe)
  }

  implicit val erasureDisable: TypeErasureResolver = new TypeErasureResolver()


  implicit def blockMethods[PipeType[+_], ElemType <: nodes.Block]
  (pipe: PipeType[ElemType]): BlockMethods[PipeType, ElemType] = {
    new BlockMethods(pipe)
  }

  implicit def callMethods[PipeType[+_], ElemType <: nodes.Call]
  (pipe: PipeType[ElemType]): CallMethods[PipeType, ElemType] = {
    new CallMethods(pipe)
  }

  implicit def hasFullNameMethods[PipeType[+_], ElemType <: nodes.HasFullName]
  (pipe: PipeType[ElemType]): HasFullNameMethods[PipeType, ElemType] = {
    new HasFullNameMethods(pipe)
  }

  implicit def methodMethods[PipeType[+_], ElemType <: nodes.Method]
  (pipe: PipeType[ElemType]): MethodMethods[PipeType, ElemType] = {
    new MethodMethods(pipe)
  }

  implicit def methodInstMethods[PipeType[+_], ElemType <: nodes.MethodInst]
  (pipe: PipeType[ElemType]): MethodInstMethods[PipeType, ElemType] = {
    new MethodInstMethods(pipe)
  }

  implicit def methodReturnMethods[PipeType[+_], ElemType <: nodes.MethodReturn]
  (pipe: PipeType[ElemType]): MethodReturnMethods[PipeType, ElemType] = {
    new MethodReturnMethods(pipe)
  }

  implicit def typeDeclMethods[PipeType[+_], ElemType <: nodes.TypeDecl]
  (pipe: PipeType[ElemType]): TypeDeclMethods[PipeType, ElemType] = {
    new TypeDeclMethods(pipe)
  }

}

class PipeOperationImplicits {
  implicit val realPipeOps: RealPipeOperations = new RealPipeOperations()
  implicit val shallowPipeOps: ShallowPipeOperations = new ShallowPipeOperations()

}

sealed trait LowPriorityImplicits {

  implicit def blockMethods[ElemType <: nodes.Block]
  (pipe: ElemType): BlockMethods[ShallowPipe, ElemType] = {
    new BlockMethods(pipe.asInstanceOf[ShallowPipe[ElemType]])
  }

  implicit def callMethods[ElemType <: nodes.Call]
  (pipe: ElemType): CallMethods[ShallowPipe, ElemType] = {
    new CallMethods(pipe.asInstanceOf[ShallowPipe[ElemType]])
  }

  implicit def hasFullNameMethods[ElemType <: nodes.HasFullName]
  (pipe: ElemType): HasFullNameMethods[ShallowPipe, ElemType] = {
    new HasFullNameMethods(pipe.asInstanceOf[ShallowPipe[ElemType]])
  }

  implicit def methodMethods[ElemType <: nodes.Method]
  (pipe: ElemType): MethodMethods[ShallowPipe, ElemType] = {
    new MethodMethods(pipe.asInstanceOf[ShallowPipe[ElemType]])
  }

  implicit def methodInstMethods[ElemType <: nodes.MethodInst]
  (pipe: ElemType): MethodInstMethods[ShallowPipe, ElemType] = {
    new MethodInstMethods(pipe.asInstanceOf[ShallowPipe[ElemType]])
  }

  implicit def methodReturnMethods[ElemType <: nodes.MethodReturn]
  (pipe: ElemType): MethodReturnMethods[ShallowPipe, ElemType] = {
    new MethodReturnMethods(pipe.asInstanceOf[ShallowPipe[ElemType]])
  }

  implicit def typeDeclMethods[ElemType <: nodes.TypeDecl]
  (pipe: ElemType): TypeDeclMethods[ShallowPipe, ElemType] = {
    new TypeDeclMethods(pipe.asInstanceOf[ShallowPipe[ElemType]])
  }

}


