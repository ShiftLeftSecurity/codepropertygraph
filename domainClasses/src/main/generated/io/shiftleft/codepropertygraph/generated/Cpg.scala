package io.shiftleft.codepropertygraph.generated
import flatgraph.DiffGraphBuilder

object Cpg {
  def empty: Cpg = new Cpg(new flatgraph.Graph(GraphSchema))

  /** Instantiate a new graph with storage. If the file already exists, this will deserialize the given file into
    * memory. `Graph.close` will serialise graph to that given file (and override whatever was there before), unless you
    * specify `persistOnClose = false`.
    */
  def withStorage(storagePath: java.nio.file.Path, persistOnClose: Boolean = true): Cpg = {
    val graph = flatgraph.Graph.withStorage(GraphSchema, storagePath, persistOnClose)
    new Cpg(graph)
  }

  def newDiffGraphBuilder: DiffGraphBuilder = new DiffGraphBuilder(GraphSchema)
}

class Cpg(private val _graph: flatgraph.Graph = new flatgraph.Graph(GraphSchema)) extends AutoCloseable {
  def graph: flatgraph.Graph = _graph

  override def close(): Unit =
    _graph.close()
}

class CpgNodeStarters(val wrappedCpg: Cpg) {
  def all: Iterator[nodes.StoredNode] = wrappedCpg.graph.allNodes.asInstanceOf[Iterator[nodes.StoredNode]]

  def annotation: Iterator[nodes.Annotation] = wrappedCpg.graph._nodes(0).asInstanceOf[Iterator[nodes.Annotation]]
  def annotationLiteral: Iterator[nodes.AnnotationLiteral] =
    wrappedCpg.graph._nodes(1).asInstanceOf[Iterator[nodes.AnnotationLiteral]]
  def annotationParameter: Iterator[nodes.AnnotationParameter] =
    wrappedCpg.graph._nodes(2).asInstanceOf[Iterator[nodes.AnnotationParameter]]
  def annotationParameterAssign: Iterator[nodes.AnnotationParameterAssign] =
    wrappedCpg.graph._nodes(3).asInstanceOf[Iterator[nodes.AnnotationParameterAssign]]
  def arrayInitializer: Iterator[nodes.ArrayInitializer] =
    wrappedCpg.graph._nodes(4).asInstanceOf[Iterator[nodes.ArrayInitializer]]
  def binding: Iterator[nodes.Binding] = wrappedCpg.graph._nodes(5).asInstanceOf[Iterator[nodes.Binding]]
  def block: Iterator[nodes.Block]     = wrappedCpg.graph._nodes(6).asInstanceOf[Iterator[nodes.Block]]
  def call: Iterator[nodes.Call]       = wrappedCpg.graph._nodes(7).asInstanceOf[Iterator[nodes.Call]]
  def closureBinding: Iterator[nodes.ClosureBinding] =
    wrappedCpg.graph._nodes(8).asInstanceOf[Iterator[nodes.ClosureBinding]]
  def comment: Iterator[nodes.Comment]       = wrappedCpg.graph._nodes(9).asInstanceOf[Iterator[nodes.Comment]]
  def configFile: Iterator[nodes.ConfigFile] = wrappedCpg.graph._nodes(10).asInstanceOf[Iterator[nodes.ConfigFile]]
  def controlStructure: Iterator[nodes.ControlStructure] =
    wrappedCpg.graph._nodes(11).asInstanceOf[Iterator[nodes.ControlStructure]]
  def dependency: Iterator[nodes.Dependency] = wrappedCpg.graph._nodes(12).asInstanceOf[Iterator[nodes.Dependency]]
  def fieldIdentifier: Iterator[nodes.FieldIdentifier] =
    wrappedCpg.graph._nodes(13).asInstanceOf[Iterator[nodes.FieldIdentifier]]
  def file: Iterator[nodes.File]             = wrappedCpg.graph._nodes(14).asInstanceOf[Iterator[nodes.File]]
  def finding: Iterator[nodes.Finding]       = wrappedCpg.graph._nodes(15).asInstanceOf[Iterator[nodes.Finding]]
  def identifier: Iterator[nodes.Identifier] = wrappedCpg.graph._nodes(16).asInstanceOf[Iterator[nodes.Identifier]]
  def imports: Iterator[nodes.Import]        = wrappedCpg.graph._nodes(17).asInstanceOf[Iterator[nodes.Import]]
  def jumpLabel: Iterator[nodes.JumpLabel]   = wrappedCpg.graph._nodes(18).asInstanceOf[Iterator[nodes.JumpLabel]]
  def jumpTarget: Iterator[nodes.JumpTarget] = wrappedCpg.graph._nodes(19).asInstanceOf[Iterator[nodes.JumpTarget]]
  def keyValuePair: Iterator[nodes.KeyValuePair] =
    wrappedCpg.graph._nodes(20).asInstanceOf[Iterator[nodes.KeyValuePair]]
  def literal: Iterator[nodes.Literal]   = wrappedCpg.graph._nodes(21).asInstanceOf[Iterator[nodes.Literal]]
  def local: Iterator[nodes.Local]       = wrappedCpg.graph._nodes(22).asInstanceOf[Iterator[nodes.Local]]
  def location: Iterator[nodes.Location] = wrappedCpg.graph._nodes(23).asInstanceOf[Iterator[nodes.Location]]
  def member: Iterator[nodes.Member]     = wrappedCpg.graph._nodes(24).asInstanceOf[Iterator[nodes.Member]]
  def metaData: Iterator[nodes.MetaData] = wrappedCpg.graph._nodes(25).asInstanceOf[Iterator[nodes.MetaData]]
  def method: Iterator[nodes.Method]     = wrappedCpg.graph._nodes(26).asInstanceOf[Iterator[nodes.Method]]
  def methodParameterIn: Iterator[nodes.MethodParameterIn] =
    wrappedCpg.graph._nodes(27).asInstanceOf[Iterator[nodes.MethodParameterIn]]
  def methodParameterOut: Iterator[nodes.MethodParameterOut] =
    wrappedCpg.graph._nodes(28).asInstanceOf[Iterator[nodes.MethodParameterOut]]
  def methodRef: Iterator[nodes.MethodRef] = wrappedCpg.graph._nodes(29).asInstanceOf[Iterator[nodes.MethodRef]]
  def methodReturn: Iterator[nodes.MethodReturn] =
    wrappedCpg.graph._nodes(30).asInstanceOf[Iterator[nodes.MethodReturn]]
  def modifier: Iterator[nodes.Modifier]   = wrappedCpg.graph._nodes(31).asInstanceOf[Iterator[nodes.Modifier]]
  def namespace: Iterator[nodes.Namespace] = wrappedCpg.graph._nodes(32).asInstanceOf[Iterator[nodes.Namespace]]
  def namespaceBlock: Iterator[nodes.NamespaceBlock] =
    wrappedCpg.graph._nodes(33).asInstanceOf[Iterator[nodes.NamespaceBlock]]
  def ret: Iterator[nodes.Return]              = wrappedCpg.graph._nodes(34).asInstanceOf[Iterator[nodes.Return]]
  def tag: Iterator[nodes.Tag]                 = wrappedCpg.graph._nodes(35).asInstanceOf[Iterator[nodes.Tag]]
  def tagNodePair: Iterator[nodes.TagNodePair] = wrappedCpg.graph._nodes(36).asInstanceOf[Iterator[nodes.TagNodePair]]
  def templateDom: Iterator[nodes.TemplateDom] = wrappedCpg.graph._nodes(37).asInstanceOf[Iterator[nodes.TemplateDom]]
  def typ: Iterator[nodes.Type]                = wrappedCpg.graph._nodes(38).asInstanceOf[Iterator[nodes.Type]]
  def typeArgument: Iterator[nodes.TypeArgument] =
    wrappedCpg.graph._nodes(39).asInstanceOf[Iterator[nodes.TypeArgument]]
  def typeDecl: Iterator[nodes.TypeDecl] = wrappedCpg.graph._nodes(40).asInstanceOf[Iterator[nodes.TypeDecl]]
  def typeParameter: Iterator[nodes.TypeParameter] =
    wrappedCpg.graph._nodes(41).asInstanceOf[Iterator[nodes.TypeParameter]]
  def typeRef: Iterator[nodes.TypeRef] = wrappedCpg.graph._nodes(42).asInstanceOf[Iterator[nodes.TypeRef]]
  def unknown: Iterator[nodes.Unknown] = wrappedCpg.graph._nodes(43).asInstanceOf[Iterator[nodes.Unknown]]

  def astNode: Iterator[nodes.AstNode] = Iterator(
    this.annotation,
    this.annotationLiteral,
    this.annotationParameter,
    this.annotationParameterAssign,
    this.arrayInitializer,
    this.block,
    this.call,
    this.comment,
    this.controlStructure,
    this.fieldIdentifier,
    this.file,
    this.identifier,
    this.imports,
    this.jumpLabel,
    this.jumpTarget,
    this.literal,
    this.local,
    this.member,
    this.method,
    this.methodParameterIn,
    this.methodParameterOut,
    this.methodRef,
    this.methodReturn,
    this.modifier,
    this.namespace,
    this.namespaceBlock,
    this.ret,
    this.templateDom,
    this.typeArgument,
    this.typeDecl,
    this.typeParameter,
    this.typeRef,
    this.unknown
  ).flatten
  def callRepr: Iterator[nodes.CallRepr] = Iterator(this.call).flatten
  def cfgNode: Iterator[nodes.CfgNode] = Iterator(
    this.annotation,
    this.annotationLiteral,
    this.arrayInitializer,
    this.block,
    this.call,
    this.controlStructure,
    this.fieldIdentifier,
    this.identifier,
    this.jumpTarget,
    this.literal,
    this.method,
    this.methodParameterIn,
    this.methodParameterOut,
    this.methodRef,
    this.methodReturn,
    this.ret,
    this.templateDom,
    this.typeRef,
    this.unknown
  ).flatten
  def declaration: Iterator[nodes.Declaration] =
    Iterator(this.local, this.member, this.method, this.methodParameterIn, this.methodParameterOut).flatten
  def expression: Iterator[nodes.Expression] = Iterator(
    this.annotation,
    this.annotationLiteral,
    this.arrayInitializer,
    this.block,
    this.call,
    this.controlStructure,
    this.fieldIdentifier,
    this.identifier,
    this.literal,
    this.methodRef,
    this.ret,
    this.templateDom,
    this.typeRef,
    this.unknown
  ).flatten
}
