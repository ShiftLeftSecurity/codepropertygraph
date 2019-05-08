package io.shiftleft.queryprimitives.steps.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.dsl.DslWriterImplicits._
import io.shiftleft.queryprimitives.dsl.pipeops.PipeOperations
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._
import scala.language.higherKinds

class TypeDeclMethods[PipeType[+_], ElemType <: nodes.TypeDecl]
(val pipe: PipeType[ElemType]) extends AnyVal {

  /**
    * Namespace in which this type declaration is defined
    * */
  def namespace(implicit ops: PipeOperations[PipeType]): PipeType[nodes.Namespace] = {
    pipe.map(_.vertices(Direction.IN, EdgeTypes.AST).asScala
    .filter(_.label() == NodeTypes.NAMESPACE_BLOCK).next
    .vertices(Direction.OUT, EdgeTypes.REF).next.asInstanceOf)
  }

  /**
    * Filter for type declarations contained in the analyzed code.
    * */
  def internal(implicit ops: PipeOperations[PipeType]): RealPipe[ElemType] = {
    pipe.filter(_.isExternal() == false)
  }

  /**
    * Filter for type declarations not contained in the analyzed code.
    * */
  def external(implicit ops: PipeOperations[PipeType]): RealPipe[ElemType] = {
    pipe.filter(_.isExternal())
  }

}
