package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object PropertyErrorRegister {
  private var errorMap = Set[(Class[_], String)]()
  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  def logPropertyErrorIfFirst(clazz: Class[_], propertyName: String): Unit = {
    if (!errorMap.contains((clazz, propertyName))) {
      logger.warn("Property " + propertyName + " is deprecated for " + clazz.getName + ".")
      errorMap += ((clazz, propertyName))
    }
  }
}


object Misc {
  val reChars = "[](){}*+&|?.,\\$"
  def isRegex(pattern: String): Boolean = pattern.exists(reChars.contains(_))
}

trait CpgNode {
  def label: String
}

/* A node that is stored inside an Graph (rather than e.g. DiffGraph) */
trait StoredNode extends Node with CpgNode with Product {
  /* underlying Node in the graph.
   * since this is a StoredNode, this is always set */
  def underlying: Node = this

  /** labels of product elements, used e.g. for pretty-printing */
  def productElementLabel(n: Int): String

  /* all properties plus label and id */
  def toMap: Map[String, Any] = {
    val map = valueMap
    map.put("_label", label)
    map.put("_id", id: java.lang.Long)
    map.asScala.toMap
  }

  /*Sets fields from newNode*/
  def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode):Unit = ???

  /* all properties */
  def valueMap: JMap[String, AnyRef]

  def _astIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _cfgIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _containsNodeIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _capturedByIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _bindsToIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _refIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _vtableIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _receiverIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _conditionIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _argumentIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _sourceFileIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _parameterLinkIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _callIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _taggedByIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _evalTypeIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _inheritsFromIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _containsIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _propagateIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _reachingDefIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _aliasOfIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _typeDeclAliasIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _bindsIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _captureIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _taintRemoveIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _dynamicTypeIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _dominateIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _postDominateIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _cdgIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _attachedDataIn: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _astOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _cfgOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _containsNodeOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _capturedByOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _bindsToOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _refOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _vtableOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _receiverOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _conditionOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _argumentOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _sourceFileOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _parameterLinkOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _callOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _taggedByOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _evalTypeOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _inheritsFromOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _containsOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _propagateOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _reachingDefOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _aliasOfOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _typeDeclAliasOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _bindsOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _captureOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _taintRemoveOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _dynamicTypeOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _dominateOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _postDominateOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _cdgOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
def _attachedDataOut: JIterator[StoredNode] = { JCollections.emptyIterator() }
}

  trait HasLanguage { def language: String }
trait HasVersion { def version: String }
trait HasOverlays { def overlays: List[String] }
trait HasHash { def hash: Option[String] }
trait HasLineNumber { def lineNumber: Option[java.lang.Integer] }
trait HasColumnNumber { def columnNumber: Option[java.lang.Integer] }
trait HasLineNumberEnd { def lineNumberEnd: Option[java.lang.Integer] }
trait HasColumnNumberEnd { def columnNumberEnd: Option[java.lang.Integer] }
trait HasParserTypeName { def parserTypeName: String }
trait HasOrder { def order: java.lang.Integer }
trait HasArgumentIndex { def argumentIndex: java.lang.Integer }
trait HasIsExternal { def isExternal: java.lang.Boolean }
trait HasName { def name: String }
trait HasFullName { def fullName: String }
trait HasCanonicalName { def canonicalName: String }
trait HasCode { def code: String }
trait HasSignature { def signature: String }
trait HasModifierType { def modifierType: String }
trait HasControlStructureType { def controlStructureType: String }
trait HasTypeFullName { def typeFullName: String }
trait HasTypeDeclFullName { def typeDeclFullName: String }
trait HasInheritsFromTypeFullName { def inheritsFromTypeFullName: List[String] }
trait HasMethodFullName { def methodFullName: String }
trait HasMethodInstFullName { def methodInstFullName: Option[String] }
trait HasAliasTypeFullName { def aliasTypeFullName: Option[String] }
trait HasFilename { def filename: String }
trait HasContainedRef { def containedRef: String }
trait HasValue { def value: String }
trait HasIsMethodNeverOverridden { def isMethodNeverOverridden: Option[java.lang.Boolean] }
trait HasPolicyDirectories { def policyDirectories: List[String] }
trait HasEvaluationStrategy { def evaluationStrategy: String }
trait HasDispatchType { def dispatchType: String }
trait HasDynamicTypeHintFullName { def dynamicTypeHintFullName: List[String] }
trait HasAstParentType { def astParentType: String }
trait HasAstParentFullName { def astParentFullName: String }
trait HasBinarySignature { def binarySignature: Option[String] }
trait HasContent { def content: String }
trait HasClosureBindingId { def closureBindingId: Option[String] }
trait HasClosureOriginalName { def closureOriginalName: Option[String] }
trait HasDependencyGroupId { def dependencyGroupId: Option[String] }
trait HasDepthFirstOrder { def depthFirstOrder: Option[java.lang.Integer] }
trait HasHasMapping { def hasMapping: Option[java.lang.Boolean] }
trait HasInternalFlags { def internalFlags: Option[java.lang.Integer] }
trait HasKey { def key: String }
trait HasSymbol { def symbol: String }
trait HasMethodShortName { def methodShortName: String }
trait HasPackageName { def packageName: String }
trait HasClassName { def className: String }
trait HasClassShortName { def classShortName: String }
trait HasNodeLabel { def nodeLabel: String }
trait HasSourceType { def sourceType: String }
trait HasSinkType { def sinkType: String }

  object Factories {
  lazy val all: Seq[NodeFactory[_]] = Seq(MetaData.factory, File.factory, Method.factory, MethodParameterIn.factory, MethodReturn.factory, Modifier.factory, Type.factory, TypeDecl.factory, TypeParameter.factory, TypeArgument.factory, Member.factory, NamespaceBlock.factory, Literal.factory, Call.factory, Local.factory, Identifier.factory, FieldIdentifier.factory, Return.factory, Block.factory, MethodInst.factory, ArrayInitializer.factory, MethodRef.factory, TypeRef.factory, ControlStructure.factory, JumpTarget.factory, Unknown.factory, Binding.factory, ImplicitCall.factory, PostExecutionCall.factory, Tag.factory, Namespace.factory, MethodParameterOut.factory, Annotation.factory, AnnotationParameterAssign.factory, AnnotationParameter.factory, AnnotationLiteral.factory, ConfigFile.factory, ClosureBinding.factory, Dependency.factory, DomNode.factory, DomAttribute.factory, Tags.factory, Framework.factory, FrameworkData.factory, DetachedTrackingPoint.factory, Finding.factory, KeyValuePair.factory, Comment.factory, PackagePrefix.factory, Location.factory, TagNodePair.factory, Source.factory, Sink.factory)
  lazy val allAsJava: java.util.List[NodeFactory[_]] = all.asJava
}

