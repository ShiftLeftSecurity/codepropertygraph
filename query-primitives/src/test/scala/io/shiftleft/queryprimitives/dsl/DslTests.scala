package io.shiftleft.queryprimitives.dsl

import io.shiftleft.queryprimitives.dsl.Implicits._
import io.shiftleft.queryprimitives.dsl.ShallowPipe.ShallowPipe
import org.scalatest.{Matchers, WordSpec}

case class A()

case class B()

class NodeAMethods[PipeType[_], ElemType <: A](val pipe: PipeType[ElemType]) extends AnyVal {
  def toB(implicit ops: PipeOperations[PipeType]): PipeType[B] = {
    ops.map(pipe, (x: ElemType) => B())
  }
  def toMultipleB(implicit ops: PipeOperations[PipeType]): RealPipe[B] = {
    ops.flatMap(pipe, (x: ElemType) => B() :: Nil)
  }
}

class NodeBMethods[PipeType[_], ElemType <: B](val pipe: PipeType[ElemType]) extends AnyVal {
  def toA(implicit ops: PipeOperations[PipeType]): PipeType[A] = {
    ops.map(pipe, (x: ElemType) => A())
  }
  def toMultipleA(implicit ops: PipeOperations[PipeType]): RealPipe[A] = {
    ops.flatMap(pipe, (x: ElemType) => A() :: Nil)
  }
}

object DslTests extends LowPrio {
  implicit def nodeAMethods(pipe: RealPipe[A]) = {
    new NodeAMethods(pipe)
  }

  implicit def nodeBMethods(pipe: RealPipe[B]) = {
    new NodeBMethods(pipe)
  }
}

trait LowPrio {
  implicit def nodeAMethods(pipe: ShallowPipe[A]) = {
    new NodeAMethods(pipe)
  }

  implicit def nodeBMethods(pipe: ShallowPipe[B]) = {
    new NodeBMethods(pipe)
  }

}


class DslTests extends WordSpec with Matchers {
  import DslTests._

  "Be able to call DSL methods on shallow pipe." in {
    A().toB shouldBe B()
  }

  "Be able to call multiple DSL methods on shallow pipe." in {
    A().toB.toA shouldBe A()
  }

  // TODO check/fix "real pipe" in test description is still the correct term.
  "Be able to call multiple DSL methods on real pipe." in {
    A().toMultipleB.toMultipleA.head shouldBe A()
  }

  "Be able to use map on real pipe." in {
    A().toMultipleB.flatMap(_.toMultipleA).head shouldBe A()
    A().toMultipleB.map(_.to)
  }

}
