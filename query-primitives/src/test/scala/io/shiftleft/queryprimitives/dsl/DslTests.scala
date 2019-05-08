package io.shiftleft.queryprimitives.dsl

import Implicits._
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.pipetypes.ShallowPipe.ShallowPipe
import io.shiftleft.queryprimitives.dsl.pipeops.PipeOperations
import io.shiftleft.queryprimitives.dsl.InternalImplicits._
import org.scalatest.{Matchers, WordSpec}

import scala.language.higherKinds

class A(a: Int = 0)
class ADerived() extends A

class B(b: Int = 0)
class BDerived() extends B

class NodeAMethods[PipeType[+_]](val pipe: PipeType[A]) extends AnyVal {
  def toB(implicit ops: PipeOperations[PipeType]): PipeType[B] = {
    pipe.map(_ => new B())
  }
  def toMultipleB(implicit ops: PipeOperations[PipeType]): RealPipe[B] = {
    pipe.flatMap(_ => new B() :: Nil)
  }
}

class NodeBMethods[PipeType[+_]](val pipe: PipeType[B]) extends AnyVal {
  def toA(implicit ops: PipeOperations[PipeType]): PipeType[A] = {
    pipe.map(_ => new A())
  }
  def toMultipleA(implicit ops: PipeOperations[PipeType]): RealPipe[A] = {
    pipe.flatMap(_ => new A() :: Nil)
  }
  def toBMapTimesX(implicit ops: PipeOperations[PipeType]): PipeType[B] = {
    pipe.repeat(_ => new BDerived(), 2)
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
    new A().toB shouldBe new B()
  }

  "Be able to call multiple DSL methods on shallow pipe." in {
    new A().toB.toA shouldBe new A()
  }

  // TODO check/fix "real pipe" in test description is still the correct term.
  "Be able to call multiple DSL methods on real pipe." in {
    new A().toMultipleB.toMultipleA.head shouldBe new A()
  }

  "Be able to use map." in {
    new A().toMultipleB.map(_.toA).head shouldBe new A()
  }

  "Be able to use map with repeat function." in {
    new A().toMultipleB.repeat(_.toA.toB, 1).head shouldBe new B()
  }

  "Be able to use flatMap on GenTraversableOnce function." in {
    new A().toMultipleB.flatMap(_ => new A()::Nil).head shouldBe new A()
  }

  "X" in {
    new B().toBMapTimesX shouldBe new BDerived
  }

  "Y" in {
    new A().toMultipleB.toBMapTimesX.head shouldBe new BDerived
  }

}
