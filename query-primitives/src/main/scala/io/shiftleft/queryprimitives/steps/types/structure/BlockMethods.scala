package io.shiftleft.queryprimitives.steps.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.dsl.DslWriterImplicits._
import io.shiftleft.queryprimitives.dsl.pipeops.PipeOperations
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._
import scala.language.higherKinds

class BlockMethods[PipeType[+_], ElemType <: nodes.Block]
(val pipe: PipeType[ElemType]) extends AnyVal {

  /**
    * Traverse to locals of this block.
    */
  def local(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Local] = {
    pipe.flatMap(_.vertices(Direction.OUT, EdgeTypes.AST).asScala
    .filter(_.label() == NodeTypes.LOCAL).asInstanceOf)
  }

  /**
    * Traverse to top level expressions directly located under this block.
    */
  def topLevelExpressions(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Expression] = {
    pipe.flatMap(_.vertices(Direction.OUT, EdgeTypes.AST).asScala
      .filter(_.label() != NodeTypes.LOCAL).asInstanceOf)
  }
}
