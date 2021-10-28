package io.shiftleft.semanticcpg.language

import io.shiftleft.semanticcpg.language.MySteps._
import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

object MySteps {
  type Pipe1[T] = T
  type PipeN[T] = Iterable[T]

  trait PipeOps[PipeT[_]] {
    def map[I, O](pipe: PipeT[I])(f: I => O): PipeT[O]
    def filter[I](pipe: PipeT[I])(f: I => Boolean): PipeN[I]
    def flatMap[I, O](pipe: PipeT[I])(f: I => Iterable[O]): PipeN[O]
    def flatMapIter[I, O](pipe: PipeT[I])(f: I => Iterator[O]): PipeN[O]
  }

  implicit val ops1 = new PipeOps[Pipe1] {
    override def map[I, O](pipe: Pipe1[I])(f: I => O): Pipe1[O] = {
      f(pipe)
    }

    override def filter[I](pipe: Pipe1[I])(f: I => Boolean): PipeN[I] = {
      if (f(pipe)) {
        Iterable.single(pipe)
      } else {
        Iterable.empty
      }
    }

    override def flatMap[I, O](pipe: Pipe1[I])(f: I => Iterable[O]): PipeN[O] = {
      f(pipe)
    }

    override def flatMapIter[I, O](pipe: Pipe1[I])(f: I => Iterator[O]): PipeN[O] = {
      f(pipe).to(Iterable)
    }
  }

  private val opsN = new PipeOps[PipeN] {
    override def map[I, O](pipe: PipeN[I])(f: I => O): PipeN[O] = {
      pipe.map(f)
    }

    override def filter[I](pipe: PipeN[I])(f: I => Boolean): PipeN[I] = {
      pipe.filter(f)
    }

    override def flatMap[I, O](pipe: PipeN[I])(f: I => Iterable[O]): PipeN[O] = {
      pipe.flatMap(f)
    }

    override def flatMapIter[I, O](pipe: PipeN[I])(f: I => Iterator[O]): PipeN[O] = {
      pipe.flatMap(f)
    }
  }

  implicit def opsNFunc[PipeN[_]]: PipeOps[PipeN] = opsN.asInstanceOf[PipeOps[PipeN]]

  implicit def toExtClass[I <: nodes.Method, PipeT[_]](pipe: PipeT[I]): ExtensionClass[I, PipeT] = {
    new ExtensionClass(pipe)
  }

  implicit def toExtClass[I <: nodes.Method](pipe: I): ExtensionClass[I, Pipe1] = {
    new ExtensionClass(pipe: Pipe1[I])
  }

  implicit def toBasic[I, PipeT[_]](pipe: PipeT[I]): Basic[I, PipeT] = {
    new Basic(pipe)
  }

  implicit def toBasic1[I](pipe: I): Basic[I, Pipe1] = {
    new Basic(pipe: Pipe1[I])
  }

}

class Basic[I, PipeT[_]](pipe: PipeT[I]) {
  def map[O](f: I => O)(implicit ops: PipeOps[PipeT]): PipeT[O] = {
    ops.map(pipe)(f)
  }

  def filter2(f: I => Boolean)(implicit ops: PipeOps[PipeT]): PipeN[I] = {
    ops.filter(pipe)(f)
  }

  def flatMap[O](f: I => Iterable[O])(implicit ops: PipeOps[PipeT]): PipeN[O] = {
    ops.flatMap(pipe)(f)
  }

  def flatMapIter[O](f: I => Iterator[O])(implicit ops: PipeOps[PipeT]): PipeN[O] = {
    ops.flatMapIter(pipe)(f)
  }

  def cast[T]: PipeT[T] = {
    pipe.asInstanceOf[PipeT[T]]
  }
}

class ExtensionClass[I <: nodes.Method, PipeT[_]](pipe: PipeT[I]) {
  def methodReturn2()(implicit ops: PipeOps[PipeT]): PipeT[nodes.MethodReturn] = {
    pipe.map(_._astOut.asScala.collectFirst { case x: nodes.MethodReturn => x }.get)
    //ops.map(pipe)(_._astOut.asScala.collectFirst { case x: nodes.MethodReturn => x }.get)
  }
}

