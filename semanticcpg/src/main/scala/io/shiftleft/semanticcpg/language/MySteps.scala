package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.language.MySteps._
import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

object MySteps {
  type Pipe1[T] = T

  implicit val ops1 = new Pipe1Ops()

  implicit val opsN = new PipeNIterableOps()

  implicit def toExtClass[I <: nodes.Method, PipeT[_], C[_]](pipe: PipeT[I]): ExtensionClass[I, PipeT, C] = {
    new ExtensionClass(pipe)
  }

  implicit def toExtClass[I <: nodes.Method, C[_]](pipe: I): ExtensionClass[I, Pipe1, C] = {
    new ExtensionClass(pipe: Pipe1[I])
  }

  implicit def toBasic[I, PipeT[_], C[_]](pipe: PipeT[I]): Basic[I, PipeT, C] = {
    new Basic(pipe)
  }

  implicit def toBasic1[I, C[_]](pipe: I): Basic[I, Pipe1, C] = {
    new Basic(pipe: Pipe1[I])
  }

}

class Basic[I, PipeT[_], C[_]](pipe: PipeT[I]) {
  def map[O](f: I => O)(implicit ops: PipeOps[PipeT, C]): ops.T1to1[O] = {
    ops.map(pipe)(f)
  }

  def filter2(f: I => Boolean)(implicit ops: PipeOps[PipeT, C]): ops.T1toOption[I] = {
    ops.filter(pipe)(f)
  }

  def flatMap[O](f: I => Iterable[O])(implicit ops: PipeOps[PipeT, C]): ops.T1toN[O] = {
    ops.flatMap(pipe)(f)
  }

  def flatMapIter[O](f: I => Iterator[O])(implicit ops: PipeOps[PipeT, C]): ops.T1toN[O] = {
    ops.flatMapIter(pipe)(f)
  }

  def cast[T]: PipeT[T] = {
    pipe.asInstanceOf[PipeT[T]]
  }
}

class ExtensionClass[I <: nodes.Method, PipeT[_], C[_]](pipe: PipeT[I]) {
  def methodReturn2()(implicit ops: PipeOps[PipeT, C]): ops.T1to1[nodes.MethodReturn] = {
    pipe.map(_._astOut.asScala.collectFirst { case x: nodes.MethodReturn => x }.get)
    //ops.map(pipe)(_._astOut.asScala.collectFirst { case x: nodes.MethodReturn => x }.get)
  }

  def methodParameters()(implicit ops: PipeOps[PipeT, C]): ops.T1toN[nodes.MethodParameterIn] = {
    pipe.flatMapIter(_._astOut.asScala.collect { case x: nodes.MethodParameterIn => x })
    //ops.map(pipe)(_._astOut.asScala.collectFirst { case x: nodes.MethodReturn => x }.get)
  }
}

