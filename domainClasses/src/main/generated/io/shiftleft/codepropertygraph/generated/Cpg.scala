package io.shiftleft.codepropertygraph.generated

import java.nio.file.{Path, Paths}
import overflowdb.{Config, Graph}
import overflowdb.traversal.help.{DocSearchPackages, TraversalHelp}
import overflowdb.traversal.help.Table.AvailableWidthProvider
import scala.jdk.javaapi.CollectionConverters.asJava

object Cpg {
  implicit val defaultDocSearchPackage: DocSearchPackages = DocSearchPackages(getClass.getPackage.getName)

  /** Syntactic sugar for `new Cpg(graph)`. Usage: `Cpg(graph)` or simply `Cpg` if you have an `implicit Graph` in scope
    */
  def apply(implicit graph: Graph) = new Cpg(graph)

  def empty: Cpg =
    new Cpg(emptyGraph)

  /** Instantiate Cpg with storage. If the storage file already exists, it will load (a subset of) the data into memory.
    * Otherwise it will create an empty Cpg. In either case, configuring storage means that OverflowDb will be stored to
    * disk on shutdown (`close`).
    * I.e. if you want to preserve state between sessions, just use this method to instantiate the Cpg and ensure to
    * properly `close` it at the end.
    * @param path
    *   to the storage file, e.g. /home/user1/overflowdb.bin
    */
  def withStorage(path: Path): Cpg =
    withConfig(Config.withoutOverflow.withStorageLocation(path))

  def withStorage(path: String): Cpg =
    withStorage(Paths.get(path))

  def withConfig(config: overflowdb.Config): Cpg =
    new Cpg(Graph.open(config, nodes.Factories.allAsJava, edges.Factories.allAsJava, convertPropertyForPersistence))

  def emptyGraph: Graph =
    Graph.open(
      Config.withoutOverflow,
      nodes.Factories.allAsJava,
      edges.Factories.allAsJava,
      convertPropertyForPersistence
    )

  def convertPropertyForPersistence(property: Any): Any =
    property match {
      case arraySeq: scala.collection.immutable.ArraySeq[_] => arraySeq.unsafeArray
      case coll: IterableOnce[Any]                          => asJava(coll.iterator.toArray)
      case other                                            => other
    }

}

/** Domain-specific wrapper for graph, starting point for traversals.
  * @param graph
  *   the underlying graph. An empty graph is created if this parameter is omitted.
  */
class Cpg(private val _graph: Graph = Cpg.emptyGraph) extends AutoCloseable {
  def graph: Graph = _graph

  def help(implicit searchPackageNames: DocSearchPackages, availableWidthProvider: AvailableWidthProvider): String =
    new TraversalHelp(searchPackageNames).forTraversalSources

  override def close(): Unit =
    graph.close

  override def toString(): String =
    String.format("Cpg (%s)", graph)
}

class GeneratedNodeStarterExt(val wrapper: Cpg) extends AnyVal {
  import scala.jdk.CollectionConverters.IteratorHasAsScala
  @overflowdb.traversal.help.Doc(info = "All nodes of type Annotation, i.e. with label ANNOTATION")
  def annotation: Iterator[nodes.Annotation] =
    overflowdb.traversal.InitialTraversal.from[nodes.Annotation](wrapper.graph, "ANNOTATION")

  @overflowdb.traversal.help.Doc(info = "All nodes of type AnnotationLiteral, i.e. with label ANNOTATION_LITERAL")
  def annotationLiteral: Iterator[nodes.AnnotationLiteral] =
    overflowdb.traversal.InitialTraversal.from[nodes.AnnotationLiteral](wrapper.graph, "ANNOTATION_LITERAL")

  @overflowdb.traversal.help.Doc(info = "All nodes of type AnnotationParameter, i.e. with label ANNOTATION_PARAMETER")
  def annotationParameter: Iterator[nodes.AnnotationParameter] =
    overflowdb.traversal.InitialTraversal.from[nodes.AnnotationParameter](wrapper.graph, "ANNOTATION_PARAMETER")

  @overflowdb.traversal.help.Doc(
    info = "All nodes of type AnnotationParameterAssign, i.e. with label ANNOTATION_PARAMETER_ASSIGN"
  )
  def annotationParameterAssign: Iterator[nodes.AnnotationParameterAssign] = overflowdb.traversal.InitialTraversal
    .from[nodes.AnnotationParameterAssign](wrapper.graph, "ANNOTATION_PARAMETER_ASSIGN")

  @overflowdb.traversal.help.Doc(info = "All nodes of type ArrayInitializer, i.e. with label ARRAY_INITIALIZER")
  def arrayInitializer: Iterator[nodes.ArrayInitializer] =
    overflowdb.traversal.InitialTraversal.from[nodes.ArrayInitializer](wrapper.graph, "ARRAY_INITIALIZER")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Binding, i.e. with label BINDING")
  def binding: Iterator[nodes.Binding] =
    overflowdb.traversal.InitialTraversal.from[nodes.Binding](wrapper.graph, "BINDING")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Block, i.e. with label BLOCK")
  def block: Iterator[nodes.Block] = overflowdb.traversal.InitialTraversal.from[nodes.Block](wrapper.graph, "BLOCK")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Call, i.e. with label CALL")
  def call: Iterator[nodes.Call] = overflowdb.traversal.InitialTraversal.from[nodes.Call](wrapper.graph, "CALL")

  @overflowdb.traversal.help.Doc(info = "All nodes of type ClosureBinding, i.e. with label CLOSURE_BINDING")
  def closureBinding: Iterator[nodes.ClosureBinding] =
    overflowdb.traversal.InitialTraversal.from[nodes.ClosureBinding](wrapper.graph, "CLOSURE_BINDING")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Comment, i.e. with label COMMENT")
  def comment: Iterator[nodes.Comment] =
    overflowdb.traversal.InitialTraversal.from[nodes.Comment](wrapper.graph, "COMMENT")

  @overflowdb.traversal.help.Doc(info = "All nodes of type ConfigFile, i.e. with label CONFIG_FILE")
  def configFile: Iterator[nodes.ConfigFile] =
    overflowdb.traversal.InitialTraversal.from[nodes.ConfigFile](wrapper.graph, "CONFIG_FILE")

  @overflowdb.traversal.help.Doc(info = "All nodes of type ControlStructure, i.e. with label CONTROL_STRUCTURE")
  def controlStructure: Iterator[nodes.ControlStructure] =
    overflowdb.traversal.InitialTraversal.from[nodes.ControlStructure](wrapper.graph, "CONTROL_STRUCTURE")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Dependency, i.e. with label DEPENDENCY")
  def dependency: Iterator[nodes.Dependency] =
    overflowdb.traversal.InitialTraversal.from[nodes.Dependency](wrapper.graph, "DEPENDENCY")

  @overflowdb.traversal.help.Doc(info = "All nodes of type FieldIdentifier, i.e. with label FIELD_IDENTIFIER")
  def fieldIdentifier: Iterator[nodes.FieldIdentifier] =
    overflowdb.traversal.InitialTraversal.from[nodes.FieldIdentifier](wrapper.graph, "FIELD_IDENTIFIER")

  @overflowdb.traversal.help.Doc(info = "All nodes of type File, i.e. with label FILE")
  def file: Iterator[nodes.File] = overflowdb.traversal.InitialTraversal.from[nodes.File](wrapper.graph, "FILE")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Finding, i.e. with label FINDING")
  def finding: Iterator[nodes.Finding] =
    overflowdb.traversal.InitialTraversal.from[nodes.Finding](wrapper.graph, "FINDING")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Identifier, i.e. with label IDENTIFIER")
  def identifier: Iterator[nodes.Identifier] =
    overflowdb.traversal.InitialTraversal.from[nodes.Identifier](wrapper.graph, "IDENTIFIER")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Import, i.e. with label IMPORT")
  def imports: Iterator[nodes.Import] =
    overflowdb.traversal.InitialTraversal.from[nodes.Import](wrapper.graph, "IMPORT")

  @overflowdb.traversal.help.Doc(info = "All nodes of type JumpLabel, i.e. with label JUMP_LABEL")
  def jumpLabel: Iterator[nodes.JumpLabel] =
    overflowdb.traversal.InitialTraversal.from[nodes.JumpLabel](wrapper.graph, "JUMP_LABEL")

  @overflowdb.traversal.help.Doc(info = "All nodes of type JumpTarget, i.e. with label JUMP_TARGET")
  def jumpTarget: Iterator[nodes.JumpTarget] =
    overflowdb.traversal.InitialTraversal.from[nodes.JumpTarget](wrapper.graph, "JUMP_TARGET")

  @overflowdb.traversal.help.Doc(info = "All nodes of type KeyValuePair, i.e. with label KEY_VALUE_PAIR")
  def keyValuePair: Iterator[nodes.KeyValuePair] =
    overflowdb.traversal.InitialTraversal.from[nodes.KeyValuePair](wrapper.graph, "KEY_VALUE_PAIR")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Literal, i.e. with label LITERAL")
  def literal: Iterator[nodes.Literal] =
    overflowdb.traversal.InitialTraversal.from[nodes.Literal](wrapper.graph, "LITERAL")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Local, i.e. with label LOCAL")
  def local: Iterator[nodes.Local] = overflowdb.traversal.InitialTraversal.from[nodes.Local](wrapper.graph, "LOCAL")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Location, i.e. with label LOCATION")
  def location: Iterator[nodes.Location] =
    overflowdb.traversal.InitialTraversal.from[nodes.Location](wrapper.graph, "LOCATION")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Member, i.e. with label MEMBER")
  def member: Iterator[nodes.Member] = overflowdb.traversal.InitialTraversal.from[nodes.Member](wrapper.graph, "MEMBER")

  @overflowdb.traversal.help.Doc(info = "All nodes of type MetaData, i.e. with label META_DATA")
  def metaData: Iterator[nodes.MetaData] =
    overflowdb.traversal.InitialTraversal.from[nodes.MetaData](wrapper.graph, "META_DATA")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Method, i.e. with label METHOD")
  def method: Iterator[nodes.Method] = overflowdb.traversal.InitialTraversal.from[nodes.Method](wrapper.graph, "METHOD")

  @overflowdb.traversal.help.Doc(info = "All nodes of type MethodParameterIn, i.e. with label METHOD_PARAMETER_IN")
  def methodParameterIn: Iterator[nodes.MethodParameterIn] =
    overflowdb.traversal.InitialTraversal.from[nodes.MethodParameterIn](wrapper.graph, "METHOD_PARAMETER_IN")

  @overflowdb.traversal.help.Doc(info = "All nodes of type MethodParameterOut, i.e. with label METHOD_PARAMETER_OUT")
  def methodParameterOut: Iterator[nodes.MethodParameterOut] =
    overflowdb.traversal.InitialTraversal.from[nodes.MethodParameterOut](wrapper.graph, "METHOD_PARAMETER_OUT")

  @overflowdb.traversal.help.Doc(info = "All nodes of type MethodRef, i.e. with label METHOD_REF")
  def methodRef: Iterator[nodes.MethodRef] =
    overflowdb.traversal.InitialTraversal.from[nodes.MethodRef](wrapper.graph, "METHOD_REF")

  @overflowdb.traversal.help.Doc(info = "All nodes of type MethodReturn, i.e. with label METHOD_RETURN")
  def methodReturn: Iterator[nodes.MethodReturn] =
    overflowdb.traversal.InitialTraversal.from[nodes.MethodReturn](wrapper.graph, "METHOD_RETURN")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Modifier, i.e. with label MODIFIER")
  def modifier: Iterator[nodes.Modifier] =
    overflowdb.traversal.InitialTraversal.from[nodes.Modifier](wrapper.graph, "MODIFIER")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Namespace, i.e. with label NAMESPACE")
  def namespace: Iterator[nodes.Namespace] =
    overflowdb.traversal.InitialTraversal.from[nodes.Namespace](wrapper.graph, "NAMESPACE")

  @overflowdb.traversal.help.Doc(info = "All nodes of type NamespaceBlock, i.e. with label NAMESPACE_BLOCK")
  def namespaceBlock: Iterator[nodes.NamespaceBlock] =
    overflowdb.traversal.InitialTraversal.from[nodes.NamespaceBlock](wrapper.graph, "NAMESPACE_BLOCK")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Return, i.e. with label RETURN")
  def ret: Iterator[nodes.Return] = overflowdb.traversal.InitialTraversal.from[nodes.Return](wrapper.graph, "RETURN")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Tag, i.e. with label TAG")
  def tag: Iterator[nodes.Tag] = overflowdb.traversal.InitialTraversal.from[nodes.Tag](wrapper.graph, "TAG")

  @overflowdb.traversal.help.Doc(info = "All nodes of type TagNodePair, i.e. with label TAG_NODE_PAIR")
  def tagNodePair: Iterator[nodes.TagNodePair] =
    overflowdb.traversal.InitialTraversal.from[nodes.TagNodePair](wrapper.graph, "TAG_NODE_PAIR")

  @overflowdb.traversal.help.Doc(info = "All nodes of type TemplateDom, i.e. with label TEMPLATE_DOM")
  def templateDom: Iterator[nodes.TemplateDom] =
    overflowdb.traversal.InitialTraversal.from[nodes.TemplateDom](wrapper.graph, "TEMPLATE_DOM")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Type, i.e. with label TYPE")
  def typ: Iterator[nodes.Type] = overflowdb.traversal.InitialTraversal.from[nodes.Type](wrapper.graph, "TYPE")

  @overflowdb.traversal.help.Doc(info = "All nodes of type TypeArgument, i.e. with label TYPE_ARGUMENT")
  def typeArgument: Iterator[nodes.TypeArgument] =
    overflowdb.traversal.InitialTraversal.from[nodes.TypeArgument](wrapper.graph, "TYPE_ARGUMENT")

  @overflowdb.traversal.help.Doc(info = "All nodes of type TypeDecl, i.e. with label TYPE_DECL")
  def typeDecl: Iterator[nodes.TypeDecl] =
    overflowdb.traversal.InitialTraversal.from[nodes.TypeDecl](wrapper.graph, "TYPE_DECL")

  @overflowdb.traversal.help.Doc(info = "All nodes of type TypeParameter, i.e. with label TYPE_PARAMETER")
  def typeParameter: Iterator[nodes.TypeParameter] =
    overflowdb.traversal.InitialTraversal.from[nodes.TypeParameter](wrapper.graph, "TYPE_PARAMETER")

  @overflowdb.traversal.help.Doc(info = "All nodes of type TypeRef, i.e. with label TYPE_REF")
  def typeRef: Iterator[nodes.TypeRef] =
    overflowdb.traversal.InitialTraversal.from[nodes.TypeRef](wrapper.graph, "TYPE_REF")

  @overflowdb.traversal.help.Doc(info = "All nodes of type Unknown, i.e. with label UNKNOWN")
  def unknown: Iterator[nodes.Unknown] =
    overflowdb.traversal.InitialTraversal.from[nodes.Unknown](wrapper.graph, "UNKNOWN")

  @overflowdb.traversal.help.Doc(
    info =
      "All nodes of type AstNode, i.e. with label in ANNOTATION, ANNOTATION_LITERAL, ANNOTATION_PARAMETER, ANNOTATION_PARAMETER_ASSIGN, ARRAY_INITIALIZER, BLOCK, CALL, COMMENT, CONTROL_STRUCTURE, FIELD_IDENTIFIER, FILE, IDENTIFIER, IMPORT, JUMP_LABEL, JUMP_TARGET, LITERAL, LOCAL, MEMBER, METHOD, METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT, METHOD_REF, METHOD_RETURN, MODIFIER, NAMESPACE, NAMESPACE_BLOCK, RETURN, TEMPLATE_DOM, TYPE_ARGUMENT, TYPE_DECL, TYPE_PARAMETER, TYPE_REF, UNKNOWN"
  )
  def astNode: Iterator[nodes.AstNode] = wrapper.graph
    .nodes(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    )
    .asScala
    .asInstanceOf[Iterator[nodes.AstNode]]

  @overflowdb.traversal.help.Doc(info = "All nodes of type CallRepr, i.e. with label in CALL")
  def callRepr: Iterator[nodes.CallRepr] = wrapper.graph.nodes("CALL").asScala.asInstanceOf[Iterator[nodes.CallRepr]]

  @overflowdb.traversal.help.Doc(
    info =
      "All nodes of type CfgNode, i.e. with label in ANNOTATION, ANNOTATION_LITERAL, ARRAY_INITIALIZER, BLOCK, CALL, CONTROL_STRUCTURE, FIELD_IDENTIFIER, IDENTIFIER, JUMP_TARGET, LITERAL, METHOD, METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT, METHOD_REF, METHOD_RETURN, RETURN, TEMPLATE_DOM, TYPE_REF, UNKNOWN"
  )
  def cfgNode: Iterator[nodes.CfgNode] = wrapper.graph
    .nodes(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CONTROL_STRUCTURE",
      "FIELD_IDENTIFIER",
      "IDENTIFIER",
      "JUMP_TARGET",
      "LITERAL",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_REF",
      "UNKNOWN"
    )
    .asScala
    .asInstanceOf[Iterator[nodes.CfgNode]]

  @overflowdb.traversal.help.Doc(
    info =
      "All nodes of type Declaration, i.e. with label in LOCAL, MEMBER, METHOD, METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT"
  )
  def declaration: Iterator[nodes.Declaration] = wrapper.graph
    .nodes("LOCAL", "MEMBER", "METHOD", "METHOD_PARAMETER_IN", "METHOD_PARAMETER_OUT")
    .asScala
    .asInstanceOf[Iterator[nodes.Declaration]]

  @overflowdb.traversal.help.Doc(
    info =
      "All nodes of type Expression, i.e. with label in ANNOTATION, ANNOTATION_LITERAL, ARRAY_INITIALIZER, BLOCK, CALL, CONTROL_STRUCTURE, FIELD_IDENTIFIER, IDENTIFIER, LITERAL, METHOD_REF, RETURN, TEMPLATE_DOM, TYPE_REF, UNKNOWN"
  )
  def expression: Iterator[nodes.Expression] = wrapper.graph
    .nodes(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CONTROL_STRUCTURE",
      "FIELD_IDENTIFIER",
      "IDENTIFIER",
      "LITERAL",
      "METHOD_REF",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_REF",
      "UNKNOWN"
    )
    .asScala
    .asInstanceOf[Iterator[nodes.Expression]]
}

/** Domain-specific version of diffgraph builder. This is to allow schema checking before diffgraph application in the
  * future, as well as a schema-aware point for providing backwards compatibility in odbv2.
  */
class DiffGraphBuilder extends overflowdb.BatchedUpdate.DiffGraphBuilder {
  override def absorb(other: overflowdb.BatchedUpdate.DiffGraphBuilder): this.type = { super.absorb(other); this }
  override def addNode(node: overflowdb.DetachedNodeData): this.type               = { super.addNode(node); this }
  override def addNode(label: String, keyvalues: Any*): this.type = { super.addNode(label, keyvalues: _*); this }
  override def addEdge(
    src: overflowdb.NodeOrDetachedNode,
    dst: overflowdb.NodeOrDetachedNode,
    label: String
  ): this.type = { super.addEdge(src, dst, label); this }
  override def addEdge(
    src: overflowdb.NodeOrDetachedNode,
    dst: overflowdb.NodeOrDetachedNode,
    label: String,
    properties: Any*
  ): this.type = { super.addEdge(src, dst, label, properties: _*); this }
  override def setNodeProperty(node: overflowdb.Node, label: String, property: Any): this.type = {
    super.setNodeProperty(node, label, property); this
  }
  override def removeNode(node: overflowdb.Node): this.type = { super.removeNode(node); this }
  override def removeEdge(edge: overflowdb.Edge): this.type = { super.removeEdge(edge); this }
}
