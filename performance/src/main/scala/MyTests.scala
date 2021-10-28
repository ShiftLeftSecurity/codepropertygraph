package io.shiftleft.semanticcpg.language.types.expressions

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Method, MethodReturn}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.testing.MockCpg
import io.shiftleft.semanticcpg.language.MySteps._

import scala.jdk.CollectionConverters._
import org.openjdk.jmh.annotations._

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object MyTests {
  @State(Scope.Benchmark)
  class MyState {
    val method = {
      val cpg = MockCpg()
        .withMethod("methodForCfgTest")
        .withCallInMethod("methodForCfgTest", "call1")
        .withCallInMethod("methodForCfgTest", "call2")
        .cpg

      cpg.method.name("methodForCfgTest").head
    }
  }
}

case class Bar[T](x: T)

class MyTests {
  import MyTests._

  @Benchmark
  def newTrav(state: MyState): Unit = {
    val x = state.method.methodReturn2()
  }

  @Benchmark
  def oldTrav(state: MyState): Unit = {
    val x = state.method.methodReturn.l
  }

  @Benchmark
  def base(state: MyState): Unit = {
    val x = Bar(state.method)
  }

  @Benchmark
  def base2(state: MyState): Unit = {
    val x = toBasic1(state.method).map(x => Bar(x))
  }

  def direct(state: MyState): Unit = {
    val x = state.method._methodReturnViaAstOut
  }

  @Benchmark
  def direct2(state: MyState): Unit = {
    val x = state.method._astOut.asScala.collectFirst { case x: MethodReturn => x }.get
  }
}
