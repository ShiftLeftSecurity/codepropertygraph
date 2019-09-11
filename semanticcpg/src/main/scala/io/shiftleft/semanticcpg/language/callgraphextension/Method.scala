package io.shiftleft.semanticcpg.language.callgraphextension

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.{Method => OriginalMethod}

class Method(override val raw: GremlinScala[nodes.Method]) extends Steps[nodes.Method](raw) {

  /**
    * Intended for internal use!
    * Traverse to direct and transitive callers of the method.
    */
  def calledByIncludingSink(sourceTrav: Steps[nodes.Method], resolve: Boolean = true)(
      implicit callResolver: ICallResolver): Steps[nodes.Method] = {
    val sourceMethods = sourceTrav.raw.toSet
    val sinkMethods = raw.dedup.toList()

    if (sourceMethods.isEmpty || sinkMethods.isEmpty) {
      new Steps[nodes.Method](graph.V(-1).cast[nodes.Method])
    } else {
      val ids = sinkMethods.map(_.id)
      val methodTrav = graph.V(ids: _*)

      new Steps[nodes.Method](
        methodTrav
          .emit(_.is(P.within(sourceMethods)))
          .repeat(
            _.sideEffect { method =>
              if (resolve) {
                callResolver.resolveDynamicMethodCallSites(method.asInstanceOf[nodes.Method])
              }
            }.in(EdgeTypes.REF) // expand to method instance
              .in(EdgeTypes.CALL) // expand to call site
              .in(EdgeTypes.CONTAINS) // expand to method
              .dedup
              .simplePath()
          )
          .cast[nodes.Method]
      )
    }
  }

  /**
    * Traverse to direct callers of this method
    * */
  def caller(implicit callResolver: ICallResolver): Steps[nodes.Method] =
    callIn(callResolver).method

  /**
    * Traverse to methods called by this method
    * */
  def callee(implicit callResolver: ICallResolver): Steps[nodes.Method] =
    new OriginalMethod(raw).callOut.calledMethod(callResolver)

  /**
    * Incoming call sites
    * */
  def callIn(implicit callResolver: ICallResolver): Steps[nodes.Call] = {
    new Steps[nodes.Call](
        sideEffect(callResolver.resolveDynamicMethodCallSites)
        .raw
        .in(EdgeTypes.REF)
        .in(EdgeTypes.CALL)
        .cast[nodes.Call])
  }

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy(sourceTrav: Steps[nodes.Method])(implicit callResolver: ICallResolver): Steps[nodes.Method] = {
    caller(callResolver).calledByIncludingSink(sourceTrav)(callResolver)
  }

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy(sourceTrav: Steps[nodes.MethodInst])(implicit callResolver: ICallResolver, x: DummyImplicit): Steps[nodes.Method] = {
    caller(callResolver).calledByIncludingSink(sourceTrav.method)(callResolver)
  }

  /**
    * Outgoing call sites to methods where fullName matches `regex`.
    * */
  def callOutRegex(regex: String)(implicit callResolver: ICallResolver): Steps[nodes.Call] = {
    new OriginalMethod(raw).callOut.filter(_.calledMethod.fullName(regex))
  }

}
