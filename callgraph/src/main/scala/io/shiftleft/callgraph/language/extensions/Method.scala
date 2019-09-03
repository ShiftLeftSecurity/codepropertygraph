package io.shiftleft.callgraph.language.extensions

import gremlin.scala._
import io.shiftleft.callgraph.language.ICallResolver
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.{Call => OriginalCall}
import io.shiftleft.semanticcpg.language.types.structure.{Method => OriginalMethod, MethodInst => OriginalMethodInst}

class Method(original: OriginalMethod) {

  /**
    * Intended for internal use!
    * Traverse to direct and transitive callers of the method.
    */
  def calledByIncludingSink(sourceTrav: OriginalMethod, resolve: Boolean = true)(
      implicit callResolver: ICallResolver): OriginalMethod = {
    val sourceMethods = sourceTrav.raw.toSet
    val sinkMethods = original.raw.dedup.toList()

    if (sourceMethods.isEmpty || sinkMethods.isEmpty) {
      new OriginalMethod(original.graph.V(-1).asInstanceOf[GremlinScala[nodes.Method]])
    } else {
      val ids = sinkMethods.map(_.id)
      val methodTrav = original.graph.V(ids: _*)

      new OriginalMethod(
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
          .asInstanceOf[GremlinScala[nodes.Method]]
      )
    }
  }

  /**
    * Traverse to direct callers of this method
    * */
  def caller(implicit callResolver: ICallResolver): OriginalMethod =
    callIn(callResolver).method

  /**
    * Traverse to methods called by this method
    * */
  def callee(implicit callResolver: ICallResolver): OriginalMethod =
    new Call(original.callOut).calledMethod(callResolver)

  /**
    * Incoming call sites
    * */
  def callIn(implicit callResolver: ICallResolver): OriginalCall = {
    new OriginalCall(
      original
        .sideEffect(callResolver.resolveDynamicMethodCallSites)
        .raw
        .in(EdgeTypes.REF)
        .in(EdgeTypes.CALL)
        .cast[nodes.Call])
  }

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy(sourceTrav: OriginalMethod)(implicit callResolver: ICallResolver): OriginalMethod = {
    new Method(caller(callResolver)).calledByIncludingSink(sourceTrav)(callResolver)
  }

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy(sourceTrav: OriginalMethodInst)(implicit callResolver: ICallResolver): OriginalMethod = {
    new Method(caller(callResolver)).calledByIncludingSink(sourceTrav.method)(callResolver)
  }

  /**
    * Outgoing call sites to methods where fullName matches `regex`.
    * */
  def callOut(regex: String)(implicit callResolver: ICallResolver): OriginalCall =
    original.callOut.filter(new Call(_).calledMethod.fullName(regex))

}
