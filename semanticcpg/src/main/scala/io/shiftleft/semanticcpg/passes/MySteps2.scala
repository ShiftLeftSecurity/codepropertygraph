package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.passes.MySteps2.{PipeOps}
import scala.jdk.CollectionConverters._

class Pipe1
class PipeN

object MySteps2 {
  type Pipe1[T] = T

  trait PipeOps[P[_], C[_]] {
    type Out[T]
    def map[I, O](pipe: P[I])(f: I => O): Out[O]
  }

  class Pipe1Ops extends PipeOps[Pipe1, Iterable] {
    override type Out[T] = Pipe1[T]

    override def map[I, O](pipe: Pipe1[I])(f: I => O): Out[O] = {
      f(pipe)
    }
  }

  class PipeNOps extends PipeOps[Iterable, Iterable] {
    override type Out[T] = Iterable[T]

    override def map[I, O](pipe: Iterable[I])(f: I => O): Out[O] = {
      pipe.map(f)
    }
  }
}

class ExtClass[I <: nodes.Method, P[_], C[_]](pipe: P[I]) {
  def methodReturn2()(implicit ops: PipeOps[P, C]): ops.Out[nodes.MethodReturn] = {
    ops.map(pipe)(_._astOut.asScala.collectFirst { case x: nodes.MethodReturn => x }.get)
    //pipe.map(_._astOut.asScala.collectFirst { case x: nodes.MethodReturn => x }.get)
  }

}