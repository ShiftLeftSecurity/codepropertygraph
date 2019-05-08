package io.shiftleft.queryprimitives.steps.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.queryprimitives.dsl.DslWriterImplicits._
import io.shiftleft.queryprimitives.dsl.pipeops.PipeOperations
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.language.higherKinds

class MethodInstMethods[PipeType[+_], ElemType <: nodes.MethodInst]
(val pipe: PipeType[ElemType]) extends AnyVal {

  /**
    * Traverse to corresponding method.
    */
  def method(implicit ops: PipeOperations[PipeType]): PipeType[nodes.Method] = {
    pipe.map(_.vertices(Direction.OUT, EdgeTypes.REF).next).asInstanceOf
  }
}
