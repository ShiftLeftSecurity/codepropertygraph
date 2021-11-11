package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.semanticcpg.language.MySteps._
import io.shiftleft.semanticcpg.language.EPipe
import io.shiftleft.semanticcpg.langv2._
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

class LocalTraversalNew[I <: nodes.Local, IT[_], FT[_]](val pipe: IT[I]) extends AnyVal {
  def referencingIdentifiers(implicit ops1: TravOps[IT, FT], ops2: TravNOps[FT]) = {
    pipe
      .flatMap(_._refIn.asScala)
      .filter(_.label == NodeTypes.IDENTIFIER)
      .cast[Identifier]
  }
}

/**
  * A local variable
  * */
class LocalTraversal(val traversal: Traversal[Local]) extends AnyVal {

  /**
    * The method hosting this local variable
    * */
  def method: Traversal[Method] = {
    // FTDO The following line of code is here for backwards compatibility.
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

  def referencingIdentifiers2: Traversal[Identifier] =
    traversal.flatMap(_._refIn.asScala).filter(_.label == NodeTypes.IDENTIFIER).cast[Identifier]

  /**
    * The type of the local.
    *
    * Unfortunately, `type` is a keyword, so we use `typ` here.
    * */
  def typ: Traversal[Type] =
    traversal.out(EdgeTypes.EVAL_TYPE).cast[Type]
}

class LocalTraversalNew3(val nodeOrPipe: EPipe[Local]) extends AnyVal {
  def definingBlock = {
    nodeOrPipe.map(_._astIn.asScala.next()).cast[Block]
  }

  def referencingIdentifiers = {
    nodeOrPipe
      .flatMapIter(_._refIn.asScala)
      .filter2(_.label == NodeTypes.IDENTIFIER)
      .cast[Identifier]
  }
}
