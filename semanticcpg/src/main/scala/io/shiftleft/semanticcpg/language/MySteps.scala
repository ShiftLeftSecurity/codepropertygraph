package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.language.MySteps._
import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

object MySteps {
  type Pipe1[C[_], T] = T
  type PipeN[C[_], T] = C[T]

  trait PipeNGenerator[C[_]] {
    def empty[T](): C[T]
    def single[T](element: T): C[T]
    def multi[T](elements: Iterable[T]): C[T]
    def multi[T](elements: Iterator[T]): C[T]
  }

  implicit val ops1 = new Pipe1Ops()

  implicit val opsN = new PipeNIterableOps()

  val pipeNGenIterable: PipeNGenerator[Iterable] = new PipeNGenerator[Iterable] {
    override def empty[T](): Iterable[T] = {
      Nil
    }

    override def single[T](element: T): Iterable[T] = {
      element :: Nil
    }

    override def multi[T](elements: Iterable[T]): Iterable[T] = {
      elements.toList
    }

    override def multi[T](elements: Iterator[T]): Iterable[T] = {
      elements.toList
    }
  }

  implicit def toExtClass[I <: nodes.Method, C[_], PipeT[U[_], _]](pipe: PipeT[C, I]): ExtensionClass[I, C, PipeT] = {
    new ExtensionClass(pipe)
  }

  implicit def toExtClass[I <: nodes.Method, C[_]](pipe: I): ExtensionClass[I, C, Pipe1] = {
    new ExtensionClass(pipe: Pipe1[C, I])
  }

  implicit def toBasic[I, C[_], PipeT[U[_], _]](pipe: PipeT[C, I]): Basic[I, C, PipeT] = {
    new Basic(pipe)
  }

  implicit def toBasic1[I, C[_]](pipe: I): Basic[I, C, Pipe1] = {
    new Basic(pipe: Pipe1[C, I])
  }

}

class Basic[I, C[_], PipeT[U[_], _]](pipe: PipeT[C, I]) {
  def map[O](f: I => O)(implicit ops: PipeOps[C, PipeT]): PipeT[C, O] = {
    ops.map(pipe)(f)
  }

  def filter2(f: I => Boolean)(implicit ops: PipeOps[C, PipeT], gen: PipeNGenerator[C]): PipeN[C, I] = {
    ops.filter(pipe)(f)
  }

  def flatMap[O](f: I => Iterable[O])(implicit ops: PipeOps[C, PipeT], gen: PipeNGenerator[C]): PipeN[C, O] = {
    ops.flatMap(pipe)(f)
  }

  def flatMapIter[O](f: I => Iterator[O])(implicit ops: PipeOps[C, PipeT], gen: PipeNGenerator[C]): PipeN[C, O] = {
    ops.flatMapIter(pipe)(f)
  }

  def cast[T]: PipeT[C, T] = {
    pipe.asInstanceOf[PipeT[C, T]]
  }
}

class ExtensionClass[I <: nodes.Method, C[_], PipeT[U[_], _]](pipe: PipeT[C, I]) {
  def methodReturn2()(implicit ops: PipeOps[C, PipeT]): PipeT[C, nodes.MethodReturn] = {
    pipe.map(_._astOut.asScala.collectFirst { case x: nodes.MethodReturn => x }.get)
    //ops.map(pipe)(_._astOut.asScala.collectFirst { case x: nodes.MethodReturn => x }.get)
  }

  def methodParameters()(implicit ops: PipeOps[C, PipeT], gen: PipeNGenerator[C]): PipeN[C, nodes.MethodParameterIn] = {
    pipe.flatMapIter(_._astOut.asScala.collect { case x: nodes.MethodParameterIn => x })
    //ops.map(pipe)(_._astOut.asScala.collectFirst { case x: nodes.MethodReturn => x }.get)
  }
}

