package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.NodeTypes
import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Method, MethodReturn}
import io.shiftleft.semanticcpg.testing.MockCpg
import io.shiftleft.semanticcpg.language.MySteps._

import scala.jdk.CollectionConverters._
import org.openjdk.jmh.annotations._
import MyTests._

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import org.openjdk.jmh.infra.Blackhole
import overflowdb.traversal.Traversal
import some.SomeDomain._

object MyTests {
  import io.shiftleft.semanticcpg.language._
  @State(Scope.Benchmark)
  class MyState {
    val (method, local) = {
      val cpg = MockCpg()
        .withMethod("methodForCfgTest")
        .withLocalInMethod("methodForCfgTest", "x")
        .withCallInMethod("methodForCfgTest", "call1")
        .withCallInMethod("methodForCfgTest", "call2")
        .withIdentifierArgument("call1", "x")
        .cpg

      (cpg.method.name("methodForCfgTest").head, cpg.local.name("x").head)
    }
    val d1 = D1(D2())
  }
}

case class Bar[T](x: T)

class MyTestNew {
  import io.shiftleft.semanticcpg.langv2._
  import io.shiftleft.semanticcpg.language.New._
  @Benchmark
  def newTrav(state: MyState) = {
    val x = toExtClass(state.method).methodReturn2()
    x
  }

  @Benchmark
  def newTrav2(state: MyState) = {
    val x = state.method.methodReturn()
    x
  }

  @Benchmark
  def direct2(state: MyState) = {
    val x = state.method._astOut.asScala.collectFirst { case x: MethodReturn => x }.get
    x
  }

  @Benchmark
  def refIdNew1(state: MyState) = {
    val x = toLocalNew(state.local).referencingIdentifiers
    x
  }

  @Benchmark
  def refIdNew2(state: MyState) = {
    val x = toLocalTraversalNew1(state.local).referencingIdentifiers
    x
  }

  @Benchmark
  def refIdBase(state: MyState) = {
    val x = Iterable.from(state.local._refIn.asScala).filter(_.label == NodeTypes.IDENTIFIER)
    x
  }

  @Benchmark
  def allocTime1() = {
    val x = mutable.ArrayBuffer.empty[Int]
    x.append(1)
    x
  }

  @Benchmark
  def allocTime2() = {
    Some(1)
  }

  @Benchmark
  def allocTime3() = {
    val x = ListBuffer.empty[Int]
    x.append(1)
    x
  }

  @Benchmark
  def astTestNew(state: MyState) = {
    state.method.isExpression.headOption
  }

  @Benchmark
  def astAstNew(state: MyState) = {
    state.method.ast
  }

  @Benchmark
  def syntheticNew(state: MyState) = {
    state.d1.toD2
  }

  @Benchmark
  def syntheticBase(state: MyState) = {
    state.d1.x
  }

  @Benchmark
  def syntheticIterableNew(state: MyState) = {
    //toSynth(state.d1:: Nil).toD2
    Iterable.single(state.d1).toD2
  }

  @Benchmark
  def syntheticIterableBase(state: MyState) = {
    Iterable.single(state.d1).map(_.x)
  }
}

class MyTestsOld {
  import io.shiftleft.semanticcpg.language._

  @Benchmark
  def oldTrav(state: MyState) = {
    val x = toMethodMethods(state.method).methodReturn.l
    x
  }

  @Benchmark
  def refIdOld(state: MyState) = {
    val x = Traversal(state.local).referencingIdentifiers2.head
    x
  }

  @Benchmark
  def astTestOld(state: MyState) = {
    Traversal(state.method).isExpression.headOption
  }

  @Benchmark
  def astAstOld(state: MyState) = {
    Traversal(state.method).ast.l
  }
}
