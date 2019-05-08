package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.NodeVisitor
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, ModifierTypes, NodeKeys, NodeTypes, nodes}
import io.shiftleft.queryprimitives.dsl.Implicits._
import io.shiftleft.queryprimitives.dsl.DslWriterImplicits._
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.pipeops.PipeOperations
import io.shiftleft.queryprimitives.steps.ICallResolver
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._
import scala.language.higherKinds

class MethodMethods[PipeType[+_]](val pipe: PipeType[nodes.Method]) extends AnyVal {

  /**
    * Traverse to concrete instances of method.
    */
  def methodInstance(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.MethodInst] = {
    pipe.flatMap(_.accept(MethodMethodsMethodInstanceVisitor))
    //ops.flatMapIterator(pipe,
      //_.vertices(Direction.IN, EdgeTypes.REF).asScala)
  }

  /**
    * Traverse to parameters of the method
    * */
  def parameter(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.MethodParameterIn] = {
    pipe.flatMap(_.accept(MethodMethodsParameterVisitor))
  }

  /**
    * Traverse to formal return parameter
    * */
  def methodReturn(implicit ops: PipeOperations[PipeType]): PipeType[nodes.MethodReturn] = {
    pipe.map(_.vertices(Direction.OUT, EdgeTypes.AST).asScala
      .filter(_.label() == NodeTypes.METHOD_RETURN).next.asInstanceOf)
  }

  /**
    * Traverse to the type declarations were this method is in the VTable.
    */
  def inVTableOfTypeDecl(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.TypeDecl] = {
    pipe.flatMap(_.vertices(Direction.IN, EdgeTypes.VTABLE).asScala.asInstanceOf)
  }

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy[SourcePipeType[+_]](sourcePipe: SourcePipeType[nodes.Method])
                                  (implicit ops: PipeOperations[PipeType],
                                   sourceOps: PipeOperations[SourcePipeType],
                                   callResolver: ICallResolver): RealPipe[nodes.Method] = {
    caller.calledByIncludingSink(sourcePipe)
  }

  /**
    * Traverse to direct and transitive callers of the method.
    * */
  def calledBy[SourcePipeType[+_]](sourcePipe: SourcePipeType[nodes.MethodInst])
                                  (implicit ops: PipeOperations[PipeType],
                                   sourceOps: PipeOperations[SourcePipeType],
                                   callResolver: ICallResolver): RealPipe[nodes.Method] = {
    caller.calledByIncludingSink(sourcePipe.method)
  }

  /**
    * Intendend for internal use!
    * Traverse to direct and transitive callers of the method.
    */
  def calledByIncludingSink[SourcePipeType[+_]](sourcePipe: SourcePipeType[nodes.Method],
                                                resolve: Boolean = true)
                                               (implicit ops: PipeOperations[PipeType],
                                                sourceOps: PipeOperations[SourcePipeType],
                                                callResolver: ICallResolver): RealPipe[nodes.Method] = {
    val sourceMethods = sourcePipe.toSet
    val sinkMethods = pipe.toList.distinct

    if (sourceMethods.isEmpty || sinkMethods.isEmpty) {
      RealPipe.empty
    } else {
      RealPipe.empty




      /*
      val ids = sinkMethods.map(_.id)
      val methodTrav = graph.V(ids: _*)

      new Method[Labels](
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
          .asInstanceOf[GremlinScala.Aux[nodes.Method, Labels]]
      )

       */
    }
  }

  /**
    * Traverse to direct callers of this method
    * */
  def caller(implicit ops: PipeOperations[PipeType],
             callResolver: ICallResolver): RealPipe[nodes.Method] = {
    callIn.method
  }

  /**
    * Traverse to methods called by this method
    * */
  def callee(implicit ops: PipeOperations[PipeType],
             callResolver: ICallResolver): RealPipe[nodes.Method] = {
    callOut.calledMethod
  }

  /**
    * Incoming call sites
    * */
  def callIn(implicit ops: PipeOperations[PipeType],
             callResolver: ICallResolver): RealPipe[nodes.Call] = {
    pipe.flatMap(_.accept(new MethodMethodsCallInVisitor(callResolver)))
  }

  /**
    * Outgoing call sites
    * */
  def callOut(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Call] = {
    pipe.flatMap(_.vertices(Direction.OUT, EdgeTypes.CONTAINS).asScala
      .filter(_.label() == NodeTypes.CALL))
      .asInstanceOf
  }

  /**
    * Outgoing call sites to methods where fullName matches `regex`.
    * */
  def callOut(regex: String)(implicit ops: PipeOperations[PipeType],
                             callResolver: ICallResolver): RealPipe[nodes.Call] = {
    callOut.filter(_.calledMethod.fullName(regex).nonEmpty)
  }

  /**
    * The type declaration associated with this method, e.g., the class it is defined in.
    * */
  def definingTypeDecl(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.TypeDecl] = {
    pipe.flatRepeatUntil[nodes.StoredNode](
      _.vertices(Direction.IN, EdgeTypes.AST).asScala.asInstanceOf,
      _.label == NodeTypes.TYPE_DECL)
      .asInstanceOf
  }

  /**
    * The method in which this method is defined
    * */
  def definingMethod(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.flatRepeatUntil(
      _.vertices(Direction.IN, EdgeTypes.AST).asScala.asInstanceOf,
      _.label == NodeTypes.METHOD)
      .asInstanceOf
  }

  /**
    * Traverse only to methods that are stubs, e.g., their code is not available
    * */
  def isStub(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.filter(_.edges(Direction.OUT, EdgeTypes.CFG).asScala.isEmpty)
  }

  /**
    * Traverse only to methods that are not stubs.
    * */
  def isNotStub(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.filter(_.edges(Direction.OUT, EdgeTypes.CFG).asScala.nonEmpty)
  }

  /**
    * Traverse to public methods
    * */
  def isPublic(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.filter(hasMethodModifier(ModifierTypes.PUBLIC))
  }

  /**
    * Traverse to private methods
    * */
  def isPrivate(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.filter(hasMethodModifier(ModifierTypes.PRIVATE))
  }

  /**
    * Traverse to protected methods
    * */
  def isProtected(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.filter(hasMethodModifier(ModifierTypes.PROTECTED))
  }

  /**
    * Traverse to abstract methods
    * */
  def isAbstract(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.filter(hasMethodModifier(ModifierTypes.ABSTRACT))
  }

  /**
    * Traverse to static methods
    * */
  def isStatic(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.filter(hasMethodModifier(ModifierTypes.STATIC))
  }

  /**
    * Traverse to native methods
    * */
  def isNative(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.filter(hasMethodModifier(ModifierTypes.NATIVE))
  }

  /**
    * Traverse to constructors, that is, keep methods that are constructors
    * */
  def isConstructor(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.filter(hasMethodModifier(ModifierTypes.CONSTRUCTOR))
  }

  /**
    * Traverse to virtual method
    * */
  def isVirtual(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.filter(hasMethodModifier(ModifierTypes.VIRTUAL))
  }

  /**
    * Traverse to external methods, that is, methods not present
    * but only referenced in the CPG.
    * */
  def external(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.filter(_.definingTypeDecl.external)
  }

  /**
    * Traverse to internal methods, that is, methods for which
    * code is included in this CPG.
    * */
  def external(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Method] = {
    pipe.filter(_.definingTypeDecl.internal)
  }

  /**
    * Traverse to method modifiers, e.g., "static", "public".
    * */
  def modifier(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Modifier] = {
    pipe.flatMap(_.vertices(Direction.OUT, EdgeTypes.AST).asScala
      .filter(_.label() == NodeTypes.MODIFIER))
      .asInstanceOf
  }

  /**
    * Traverse to the methods local variables
    * */
  def local(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Local] = {
    pipe.flatMap(_.vertices(Direction.OUT, EdgeTypes.CONTAINS).asScala
      .filter(_.label() == NodeTypes.BLOCK)
      .flatMap(_.vertices(Direction.OUT, EdgeTypes.AST).asScala)
      .filter(_.label() == NodeTypes.LOCAL))
      .asInstanceOf
  }

  /**
    * Traverse to literals of method
    * */
  def literal(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Literal] = {
    pipe.flatMap(_.vertices(Direction.OUT, EdgeTypes.CONTAINS).asScala
      .filter(_.label() == NodeTypes.LITERAL))
      .asInstanceOf
  }

  /**
    * Traverse to top level expressions which are all expression directly located under the
    * method outermost block.
    */
  def topLevelExpressions(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Expression] = {
    block.local
  }

  /**
    *  Traverse to first expressions in CFG.
    *  Can be multiple.
    */
  def cfgFirst(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Expression] = {
    pipe.flatMap(_.vertices(Direction.OUT, EdgeTypes.CFG).asScala).asInstanceOf
  }

  /**
    *  Traverse to last expressions in CFG.
    *  Can be multiple.
    */
  def cfgLast(implicit ops: PipeOperations[PipeType]): RealPipe[nodes.Expression] = {
    methodReturn.cfgLast
  }

  /**
    * Traverse to block
    * */
  def block(implicit ops: PipeOperations[PipeType]): PipeType[nodes.Block] = {
    pipe.map(_.vertices(Direction.OUT, EdgeTypes.AST).asScala
    .filter(_.label() == NodeTypes.BLOCK).next.asInstanceOf)
  }

  /**
    * Traverse to namespace
    * */
  def namespace(implicit ops: PipeOperations[PipeType]): PipeType[nodes.Namespace] = {
    definingTypeDecl.namespace
  }

  private def hasMethodModifier(modifierType: String)(method: Vertex): Boolean = {
    method.vertices(Direction.OUT, EdgeTypes.AST).asScala
      .exists(node => node.label() == NodeTypes.MODIFIER &&
        node.value2(NodeKeys.MODIFIER_TYPE) == modifierType)
  }

}

private object MethodMethodsMethodInstanceVisitor extends NodeVisitor[Iterator[nodes.MethodInst]] {
  implicit def foo[T](x: Iterator[Vertex]): T = {
   x.asInstanceOf[T]
  }
  override def visit(node: nodes.Method): Iterator[nodes.MethodInst] = {
    node.vertices(Direction.IN, EdgeTypes.REF).asScala
  }
}

private object MethodMethodsParameterVisitor extends NodeVisitor[Iterator[nodes.MethodParameterIn]] {
  implicit def foo[T](x: Iterator[Vertex]): T = {
    x.asInstanceOf[T]
  }
  override def visit(node: nodes.Method): Iterator[nodes.MethodParameterIn] = {
    node.vertices(Direction.OUT, EdgeTypes.AST).asScala
      .filter(_.label() == NodeTypes.METHOD_PARAMETER_IN)
  }
}

private class MethodMethodsCallInVisitor(callResolver: ICallResolver) extends NodeVisitor[Iterator[nodes.Call]] {
  implicit def foo[T](x: Iterator[Vertex]): T = {
    x.asInstanceOf[T]
  }
  override def visit(node: nodes.Method): Iterator[nodes.Call] = {
    callResolver.resolveDynamicMethodCallSites(node)

    node.vertices(Direction.IN, EdgeTypes.REF).asScala
      .flatMap(_.vertices(Direction.IN, EdgeTypes.CALL).asScala)
  }
}
