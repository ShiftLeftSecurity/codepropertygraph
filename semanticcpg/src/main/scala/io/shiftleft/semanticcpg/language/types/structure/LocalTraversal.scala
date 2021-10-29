package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.semanticcpg.language.MySteps._
import io.shiftleft.semanticcpg.language.{Basic, PipeOps}
import overflowdb.traversal.Traversal

/**
  * A local variable
  * */
class LocalTraversal(val traversal: Traversal[Local]) extends AnyVal {

  /**
    * The method hosting this local variable
    * */
  def method: Traversal[Method] = {
    // TODO The following line of code is here for backwards compatibility.
    // Use the lower commented out line once not required anymore.
    traversal.repeat(_.in(EdgeTypes.AST))(_.until(_.hasLabel(NodeTypes.METHOD))).cast[Method]
    //definingBlock.method
  }

  /**
    * The block in which local is declared.
    */
  def definingBlock: Traversal[Block] =
    traversal.in(EdgeTypes.AST).cast[Block]

  /**
    * Places (identifier) where this local is being referenced
    * */
  def referencingIdentifiers: Traversal[Identifier] =
    traversal.in(EdgeTypes.REF).hasLabel(NodeTypes.IDENTIFIER).cast[Identifier]

  /**
    * The type of the local.
    *
    * Unfortunately, `type` is a keyword, so we use `typ` here.
    * */
  def typ: Traversal[Type] =
    traversal.out(EdgeTypes.EVAL_TYPE).cast[Type]
}

import scala.jdk.CollectionConverters._

class LocalTraversalNew[C[_], PipeT[U[_], _]](nodeOrPipe: PipeT[C, Local]) {
  def definingBlock(implicit ops: PipeOps[C, PipeT]): PipeT[C, Block] = {
    nodeOrPipe.map(_._astIn.asScala.next()).cast[Block]
  }

  def referencingIdentifiers(implicit ops: PipeOps[C, PipeT], ops2: PipeOps[C, PipeN], gen: PipeNGenerator[C]): PipeN[C, Identifier] = {
    nodeOrPipe.flatMapIter(_._refIn.asScala).filter2(_.label == NodeTypes.IDENTIFIER)
      .cast[Identifier]
  }
}
