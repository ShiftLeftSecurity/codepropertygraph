package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.NodeTypes
import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Expression, Method, MethodParameterIn, MethodReturn, StoredNode}
import io.shiftleft.semanticcpg.testing.MockCpg
import io.shiftleft.semanticcpg.language.MySteps._
import io.shiftleft.semanticcpg.language.ImportsV3._

import scala.jdk.CollectionConverters._
import org.openjdk.jmh.annotations._
import MyTests._
import io.shiftleft.semanticcpg.language.types.structure.LocalReferencingIdentifiers

import scala.collection.{View, mutable}
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
  @Benchmark
  def travNew(state: MyState) = {
    val x = state.method.methodReturn
    x
  }

  @Benchmark
  def travBase(state: MyState) = {
    val x = state.method._astOut.asScala.collectFirst { case x: MethodReturn => x }.get
    x
  }

  @Benchmark
  def refIdNew2(state: MyState) = {
    val x = toLocalTraversalSingle(state.local).referencingIdentifiers
    x
  }

  @Benchmark
  def refIdNew3(state: MyState) = {
    val x = Seq.from(rftoSingleExt(state.local).referencingIdentifiers)
    x
  }

  @Benchmark
  def refIdBase(state: MyState) = {
    val x = Seq.from(state.local._refIn.asScala.filter(_.label == NodeTypes.IDENTIFIER))
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
  def astTestNewV2(state: MyState) = {
    val x: Option[Expression] = toAstNodeTraversalSingle(state.method).isExpression
    x
  }

  @Benchmark
  def astTestNewV3(state: MyState) = {
    toSingleExt(state.method).isExpression
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
    //toSynth(state.d1:: Nil).toD2Multi
    //val y: TravOps[Iterable, IterableTypes[Iterable, Iterable[Any]]] = toIt2Ops
    val x: List[D2] = List(state.d1).toD2
    x
  }

  @Benchmark
  def syntheticIterableBase(state: MyState) = {
    Iterable.single(state.d1).map(_.x)
  }

  def compileTest(state: MyState) = {
    val a: D2 = state.d1.toD2
    val b: Option[D2] = Option(state.d1).toD2
    val c = toSynthIter(Array(state.d1).view.slice(1,2)).toD2
    val d: View[D2] = c
  }
}

class MyTestsOld {
  import io.shiftleft.semanticcpg.language._

  @Benchmark
  def travOld(state: MyState) = {
    val x = toMethodMethods(state.method).methodReturn.l
    x
  }

  @Benchmark
  def refIdOld(state: MyState) = {
    val x = Traversal(state.local).referencingIdentifiers2.toSeq
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
