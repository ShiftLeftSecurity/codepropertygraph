package io.shiftleft.queryprimitives.steps.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.queryprimitives.dsl.Implicits._
import io.shiftleft.queryprimitives.dsl.DslWriterImplicits._
import io.shiftleft.queryprimitives.dsl.pipeops.PipeOperations
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.steps.NoResolve
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._
import scala.language.higherKinds

class MethodReturnMethods[PipeType[+_], ElemType <: nodes.MethodReturn]
(val pipe: PipeType[ElemType]) extends AnyVal {

  /**
    * Traverse to the method this formal method return belongs to
    */
  def method(implicit ops: PipeOperations[PipeType]): PipeType[nodes.Method] = {
    pipe.map(_.vertices(Direction.IN, EdgeTypes.AST).next.asInstanceOf)
  }

  /**
    * Traverse to call sites of the corresponding method.
    */
  def returnUser(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Call] = {
    implicit val callResolver = NoResolve
    method.callIn
  }

  /**
    *  Traverse to last expressions in CFG.
    *  Can be multiple.
    */
  def cfgLast(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Expression] = {
    pipe.flatMap(_.vertices(Direction.IN, EdgeTypes.CFG).asScala.asInstanceOf)
  }

  /**
    * Traverse to return type
    * */
  def typ(implicit ops: PipeOperations[PipeType]): PipeType[nodes.Type] = {
    pipe.map(_.vertices(Direction.OUT, EdgeTypes.EVAL_TYPE).next.asInstanceOf)
  }
}
