package io.shiftleft.queryprimitives.steps.starters

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.NodeTypes
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.structure._
import io.shiftleft.queryprimitives.steps.types.expressions._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import java.lang.{Long => JLong}
import java.util.{Iterator => JIterator}
import org.apache.tinkerpop.gremlin.structure.{Direction, VertexProperty}
import shapeless.HNil

trait NodeTypeStarters {

  /**
    * The underlying graph
    * */
  def scalaGraph: ScalaGraph

  /**
    Traverse to all nodes.
    */
  def all: CpgSteps[nodes.StoredNode, HNil] =
    new CpgSteps[nodes.StoredNode, HNil](scalaGraph.V)(NodeTypeStarters.storedNodeMarshaller)

  /**
    Traverse to all source files
    */
  def file: File[HNil] =
    new File[HNil](scalaGraph.V.hasLabel(NodeTypes.FILE))

  /**
    Traverse to all namespaces, e.g., packages in Java.
    */
  def namespace: Namespace[HNil] =
    new Namespace[HNil](scalaGraph.V.hasLabel(NodeTypes.NAMESPACE))

  /**
  Traverse to all namespace blocks, e.g., packages in Java.
    */
  def namespaceBlock: NamespaceBlock[HNil] =
    new NamespaceBlock[HNil](scalaGraph.V.hasLabel(NodeTypes.NAMESPACE_BLOCK))

  /**
    Traverse to all types, e.g., Set<String>
    */
  def types: Type[HNil] =
    new Type(scalaGraph.V.hasLabel(NodeTypes.TYPE))

  /**
    Traverse to all declarations, e.g., Set<T>
    */
  def typeDecl: TypeDecl[HNil] =
    new TypeDecl(scalaGraph.V.hasLabel(NodeTypes.TYPE_DECL))

  /**
    Traverse to all methods
    */
  def method: Method[HNil] =
    new Method(scalaGraph.V.hasLabel(NodeTypes.METHOD))

  /**
    Traverse to all method instances
    */
  def methodInstance: MethodInst[HNil] =
    new MethodInst(scalaGraph.V.hasLabel(NodeTypes.METHOD_INST))

  /**
    Traverse to all formal return parameters
    */
  def methodReturn: MethodReturn[HNil] =
    new MethodReturn(scalaGraph.V.hasLabel(NodeTypes.METHOD_RETURN))

  /**
    Traverse to all input parameters
    */
  def parameter: MethodParameter[HNil] =
    new MethodParameter(scalaGraph.V.hasLabel(NodeTypes.METHOD_PARAMETER_IN))

  /**
    Traverse to all class members
    */
  def member: Member[HNil] =
    new Member(scalaGraph.V.hasLabel(NodeTypes.MEMBER))

  /**
    Traverse to all call sites
    */
  def call: Call[HNil] =
    new Call(scalaGraph.V.hasLabel(NodeTypes.CALL))

  /**
    Traverse to all local variable declarations

    */
  def local: Local[HNil] =
    new Local(scalaGraph.V.hasLabel(NodeTypes.LOCAL))

  /**
    Traverse to all literals (constant strings and numbers provided directly in the code).
    */
  def literal: Literal[HNil] =
    new Literal(scalaGraph.V.hasLabel(NodeTypes.LITERAL))

  /**
    Traverse to all identifiers, e.g., occurrences of local variables or class members in method bodies.
    */
  def identifier: Identifier[HNil] =
    new Identifier(scalaGraph.V.hasLabel(NodeTypes.IDENTIFIER))

  /**
    Traverse to all arguments passed to methods
    */
  def argument: Expression[HNil] =
    call.argument

  /**
    * Traverse to all meta data entries
    */
  def metaData: MetaData[HNil] =
    new MetaData(scalaGraph.V.hasLabel(NodeTypes.META_DATA))
}

object NodeTypeStarters {

  /* TODO MP: generate in DomainClassCreator */
  val storedNodeMarshaller: Marshallable[nodes.StoredNode] = new Marshallable[nodes.StoredNode] {
    override def toCC(element: Element): nodes.StoredNode =
      element.label match {
        case nodes.Annotation.Label => implicitly[Marshallable[nodes.Annotation]].toCC(element)
        case nodes.AnnotationLiteral.Label => implicitly[Marshallable[nodes.AnnotationLiteral]].toCC(element)
        case nodes.AnnotationParameter.Label => implicitly[Marshallable[nodes.AnnotationParameter]].toCC(element)
        case nodes.AnnotationParameterAssign.Label => implicitly[Marshallable[nodes.AnnotationParameterAssign]].toCC(element)
        case nodes.ArrayInitializer.Label => implicitly[Marshallable[nodes.ArrayInitializer]].toCC(element)
        case nodes.Block.Label => implicitly[Marshallable[nodes.Block]].toCC(element)
        case nodes.Call.Label => implicitly[Marshallable[nodes.Call]].toCC(element)
        case nodes.CallChain.Label => implicitly[Marshallable[nodes.CallChain]].toCC(element)
        case nodes.CallSite.Label => implicitly[Marshallable[nodes.CallSite]].toCC(element)
        case nodes.ClosureBinding.Label => implicitly[Marshallable[nodes.ClosureBinding]].toCC(element)
        case nodes.ConfigFile.Label => implicitly[Marshallable[nodes.ConfigFile]].toCC(element)
        case nodes.Dependency.Label => implicitly[Marshallable[nodes.Dependency]].toCC(element)
        case nodes.File.Label => implicitly[Marshallable[nodes.File]].toCC(element)
        case nodes.Finding.Label => implicitly[Marshallable[nodes.Finding]].toCC(element)
        case nodes.Flow.Label => implicitly[Marshallable[nodes.Flow]].toCC(element)
        case nodes.Framework.Label => implicitly[Marshallable[nodes.Framework]].toCC(element)
        case nodes.FrameworkData.Label => implicitly[Marshallable[nodes.FrameworkData]].toCC(element)
        case nodes.Identifier.Label => implicitly[Marshallable[nodes.Identifier]].toCC(element)
        case nodes.Ioflow.Label => implicitly[Marshallable[nodes.Ioflow]].toCC(element)
        case nodes.Literal.Label => implicitly[Marshallable[nodes.Literal]].toCC(element)
        case nodes.Local.Label => implicitly[Marshallable[nodes.Local]].toCC(element)
        case nodes.Location.Label => implicitly[Marshallable[nodes.Location]].toCC(element)
        case nodes.Member.Label => implicitly[Marshallable[nodes.Member]].toCC(element)
        case nodes.MetaData.Label => implicitly[Marshallable[nodes.MetaData]].toCC(element)
        case nodes.Method.Label => implicitly[Marshallable[nodes.Method]].toCC(element)
        case nodes.MethodInst.Label => implicitly[Marshallable[nodes.MethodInst]].toCC(element)
        case nodes.MethodParameterIn.Label => implicitly[Marshallable[nodes.MethodParameterIn]].toCC(element)
        case nodes.MethodParameterOut.Label => implicitly[Marshallable[nodes.MethodParameterOut]].toCC(element)
        case nodes.MethodRef.Label => implicitly[Marshallable[nodes.MethodRef]].toCC(element)
        case nodes.MethodReturn.Label => implicitly[Marshallable[nodes.MethodReturn]].toCC(element)
        case nodes.MethodSummary.Label => implicitly[Marshallable[nodes.MethodSummary]].toCC(element)
        case nodes.Modifier.Label => implicitly[Marshallable[nodes.Modifier]].toCC(element)
        case nodes.Namespace.Label => implicitly[Marshallable[nodes.Namespace]].toCC(element)
        case nodes.NamespaceBlock.Label => implicitly[Marshallable[nodes.NamespaceBlock]].toCC(element)
        case nodes.ProgramPoint.Label => implicitly[Marshallable[nodes.ProgramPoint]].toCC(element)
        case nodes.Read.Label => implicitly[Marshallable[nodes.Read]].toCC(element)
        case nodes.Return.Label => implicitly[Marshallable[nodes.Return]].toCC(element)
        case nodes.Sink.Label => implicitly[Marshallable[nodes.Sink]].toCC(element)
        case nodes.Source.Label => implicitly[Marshallable[nodes.Source]].toCC(element)
        case nodes.SpAnnotationParameter.Label => implicitly[Marshallable[nodes.SpAnnotationParameter]].toCC(element)
        case nodes.Tag.Label => implicitly[Marshallable[nodes.Tag]].toCC(element)
        case nodes.Tags.Label => implicitly[Marshallable[nodes.Tags]].toCC(element)
        case nodes.TagNodePair.Label => implicitly[Marshallable[nodes.TagNodePair]].toCC(element)
        case nodes.Transform.Label => implicitly[Marshallable[nodes.Transform]].toCC(element)
        case nodes.Transformation.Label => implicitly[Marshallable[nodes.Transformation]].toCC(element)
        case nodes.Type.Label => implicitly[Marshallable[nodes.Type]].toCC(element)
        case nodes.TypeArgument.Label => implicitly[Marshallable[nodes.TypeArgument]].toCC(element)
        case nodes.TypeDecl.Label => implicitly[Marshallable[nodes.TypeDecl]].toCC(element)
        case nodes.TypeParameter.Label => implicitly[Marshallable[nodes.TypeParameter]].toCC(element)
        case nodes.Unknown.Label => implicitly[Marshallable[nodes.Unknown]].toCC(element)
        case nodes.VariableInfo.Label => implicitly[Marshallable[nodes.VariableInfo]].toCC(element)
        case nodes.Write.Label => implicitly[Marshallable[nodes.Write]].toCC(element)
        case nodes.PackagePrefix.Label => implicitly[Marshallable[nodes.PackagePrefix]].toCC(element)
      }

    // not really needed AFAIK
    override def fromCC(cc: nodes.StoredNode) = ???
  }
}
