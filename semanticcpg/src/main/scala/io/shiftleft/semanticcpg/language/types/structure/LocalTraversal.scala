package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.semanticcpg.language.MySteps._
import io.shiftleft.semanticcpg.language.EPipe
import io.shiftleft.semanticcpg.langv2._
import io.shiftleft.semanticcpg.langv3.{Helper, Kernel1ToN, Kernel1ToO}
import overflowdb.traversal.Traversal

import scala.collection.IterableOps
import scala.jdk.CollectionConverters._

class LocalReferencingIdentifiersKernel[I <: nodes.Local] extends Kernel1ToN[I, nodes.Identifier] {
  override def apply(i: I): Iterator[nodes.Identifier] = {
    i._refIn.asScala.filter(_.label == NodeTypes.IDENTIFIER).asInstanceOf[Iterator[nodes.Identifier]]
  }
}

object LocalReferencingIdentifiers {
  type NodeType = nodes.Local
  type KernelType[T <: NodeType] = LocalReferencingIdentifiersKernel[T]

  private val _impl = new KernelType[NodeType]()
  private def impl[I <: NodeType] = _impl.asInstanceOf[KernelType[I]]

  trait Imports {
    implicit def rftoSingleExt[I <: NodeType](i: I) = new SingleExt(i)
    implicit def rftoIterableExt[I <: NodeType, CC[_], C](i: IterableOps[I, CC, C]) = new IterableExt(i)
    implicit def rftoIteratorExt[I <: NodeType](i: Iterator[I]) = new IteratorExt(i)
    implicit def rftoOptionExt[I <: NodeType](i: Option[I]) = new OptionExt(i)
  }

  class SingleExt[I <: NodeType](val i: I) extends AnyVal {
    def referencingIdentifiers = Helper(i, impl[I])
  }
  class IterableExt[I <: NodeType, CC[_], C](val i: IterableOps[I, CC, C]) extends AnyVal {
    def referencingIdentifiers = Helper(i, impl[I])
  }
  class IteratorExt[I <: NodeType](val i: Iterator[I]) extends AnyVal {
    def referencingIdentifiers = Helper(i, impl[I])
  }
  class OptionExt[I <: NodeType](val i: Option[I]) extends AnyVal {
    def referencingIdentifiers = Helper(i, impl[I])
  }
}

class LocalTraversalNew[I <: nodes.Local, TM <: TypeMultiplexer](val trav: TM#IT[I]) extends AnyVal {
  def referencingIdentifiers(implicit ops1: TravOps[TM]): TM#CCOneToMany[Identifier] = {
    ops1.oneToMany(trav)(_._refIn.asScala.filter(_.label == NodeTypes.IDENTIFIER)
      .cast[Identifier])
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
