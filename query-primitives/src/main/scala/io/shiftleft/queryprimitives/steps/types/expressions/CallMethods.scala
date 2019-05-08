package io.shiftleft.queryprimitives.steps.types.expressions

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.queryprimitives.dsl.Implicits._
import io.shiftleft.queryprimitives.dsl.DslWriterImplicits._
import io.shiftleft.queryprimitives.dsl.pipeops.PipeOperations
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.steps.ICallResolver
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._
import scala.language.higherKinds

class CallMethods[PipeType[+_], ElemType <: nodes.Call]
(val pipe: PipeType[ElemType]) extends AnyVal {

  /**
    * The caller
    */
  def method(implicit ops: PipeOperations[PipeType]): PipeType[nodes.Method] = {
    pipe.map(_.vertices(Direction.IN, EdgeTypes.CONTAINS).next).asInstanceOf
  }

  /**
    * The callee method
    */
  def calledMethod(implicit ops: PipeOperations[PipeType],
                   callResolver: ICallResolver): RealPipe[nodes.Method] = {
    calledMethodInstance.method
  }

  /**
    * The callee method instance
    */
  def calledMethodInstance(implicit ops: PipeOperations[PipeType],
                           callResolver: ICallResolver): RealPipe[nodes.MethodInst] = {
    pipe.flatMap { call =>
      callResolver.resolveDynamicCallSite(call)
      call.vertices(Direction.OUT, EdgeTypes.CALL).asScala.asInstanceOf
    }
  }
}
