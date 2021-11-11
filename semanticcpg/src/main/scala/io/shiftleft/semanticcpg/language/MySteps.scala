package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.language.MySteps._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.types.structure.LocalTraversalNew3
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

// First attempt of new pipeline. Not further used since it is not so fast.

trait EPipe[I] {
  type ColT[_]
  protected val p: ColT[I]
  protected val ops: PipeOps[ColT]

  def head: I = {
    ops.head(p)
  }

  def map[O](f: I => O): EPipe[O] = {
    new Pipe(ops.map(p)(f), ops.T1to1FollowUpOps)
  }

  def filter2(f: I => Boolean): EPipe[I] = {
    new Pipe(ops.filter(p)(f), ops.T1toOptionFollowUpOps)
  }

  def flatMap[O](f: I => Iterable[O]): EPipe[O] = {
    new Pipe(ops.flatMap(p)(f), ops.T1toNFollowUpOps)
  }

  def flatMapIter[O](f: I => Iterator[O]): EPipe[O] = {
    new Pipe(ops.flatMapIter(p)(f), ops.T1toNFollowUpOps)
  }

  def cast[A <: I]: EPipe[A] = {
    this.asInstanceOf[EPipe[A]]
  }
}

class Pipe[I, T[_]](val p: T[I], val ops: PipeOps[T]) extends EPipe[I] {
  override type ColT[A] = T[A]
}

object MySteps {
  type Pipe1[T] = T

  implicit val ops1 = Pipe1Ops

  implicit val opsN = PipeNIterableOps

  implicit def toExtClass[I <: nodes.Method](pipe: Iterable[I]): ExtensionClass[I] = {
    new ExtensionClass(new Pipe(pipe, PipeNIterableOps))
  }

  implicit def toExtClass[I <: nodes.Method](pipe: I): ExtensionClass[I] = {
    new ExtensionClass(new Pipe(pipe: Pipe1[I], Pipe1Ops))
  }

  implicit def toExtClass[I <: nodes.Method](pipe: EPipe[I]): ExtensionClass[I] = {
    new ExtensionClass(pipe)
  }

  implicit def toLocalNew[I <: nodes.Local](pipe: I): LocalTraversalNew3 = {
    new LocalTraversalNew3(new Pipe(pipe: Pipe1[I], Pipe1Ops))
  }

}

class ExtensionClass[I <: nodes.Method](val pipe: EPipe[I]) extends AnyVal {
  def methodReturn2() = {
    pipe.map(_._astOut.asScala.collectFirst { case x: nodes.MethodReturn => x }.get)
  }

  def methodParameters = {
    pipe.flatMapIter(_._astOut.asScala.collect { case x: nodes.MethodParameterIn => x })
  }
}
