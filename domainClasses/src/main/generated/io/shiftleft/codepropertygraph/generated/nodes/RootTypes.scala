package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object PropertyErrorRegister {
  private var errorMap = Set[(Class[?], String)]()
  private val logger   = org.slf4j.LoggerFactory.getLogger(getClass)

  def logPropertyErrorIfFirst(clazz: Class[?], propertyName: String): Unit = {
    if (!errorMap.contains((clazz, propertyName))) {
      logger.warn("Property " + propertyName + " is deprecated for " + clazz.getName + ".")
      errorMap += ((clazz, propertyName))
    }
  }
}

object Misc {
  val reChars                           = "[](){}*+&|?.,\\$"
  def isRegex(pattern: String): Boolean = pattern.exists(reChars.contains(_))
}

trait StaticType[+T]

/** Abstract supertype for overflowdb.Node and NewNode */
trait AbstractNode extends overflowdb.NodeOrDetachedNode with StaticType[AnyRef] {
  def label: String
}

/* A node that is stored inside an Graph (rather than e.g. DiffGraph) */
trait StoredNode extends Node with AbstractNode with Product {
  /* underlying Node in the graph.
   * since this is a StoredNode, this is always set */
  def underlying: Node = this

  /* all properties plus label and id */
  def toMap: Map[String, Any] = {
    val map = propertiesMap()
    map.put("_label", label)
    map.put("_id", id: java.lang.Long)
    map.asScala.toMap
  }

  /*Sets fields from newNode*/
  def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = ???

  def _aliasOfIn: Iterator[StoredNode]          = Iterator.empty
  def _argumentIn: Iterator[StoredNode]         = Iterator.empty
  def _astIn: Iterator[StoredNode]              = Iterator.empty
  def _bindsIn: Iterator[StoredNode]            = Iterator.empty
  def _bindsToIn: Iterator[StoredNode]          = Iterator.empty
  def _callIn: Iterator[StoredNode]             = Iterator.empty
  def _captureIn: Iterator[StoredNode]          = Iterator.empty
  def _capturedByIn: Iterator[StoredNode]       = Iterator.empty
  def _cdgIn: Iterator[StoredNode]              = Iterator.empty
  def _cfgIn: Iterator[StoredNode]              = Iterator.empty
  def _conditionIn: Iterator[StoredNode]        = Iterator.empty
  def _containsIn: Iterator[StoredNode]         = Iterator.empty
  def _dominateIn: Iterator[StoredNode]         = Iterator.empty
  def _evalTypeIn: Iterator[StoredNode]         = Iterator.empty
  def _importsIn: Iterator[StoredNode]          = Iterator.empty
  def _inheritsFromIn: Iterator[StoredNode]     = Iterator.empty
  def _isCallForImportIn: Iterator[StoredNode]  = Iterator.empty
  def _parameterLinkIn: Iterator[StoredNode]    = Iterator.empty
  def _postDominateIn: Iterator[StoredNode]     = Iterator.empty
  def _reachingDefIn: Iterator[StoredNode]      = Iterator.empty
  def _receiverIn: Iterator[StoredNode]         = Iterator.empty
  def _refIn: Iterator[StoredNode]              = Iterator.empty
  def _sourceFileIn: Iterator[StoredNode]       = Iterator.empty
  def _taggedByIn: Iterator[StoredNode]         = Iterator.empty
  def _aliasOfOut: Iterator[StoredNode]         = Iterator.empty
  def _argumentOut: Iterator[StoredNode]        = Iterator.empty
  def _astOut: Iterator[StoredNode]             = Iterator.empty
  def _bindsOut: Iterator[StoredNode]           = Iterator.empty
  def _bindsToOut: Iterator[StoredNode]         = Iterator.empty
  def _callOut: Iterator[StoredNode]            = Iterator.empty
  def _captureOut: Iterator[StoredNode]         = Iterator.empty
  def _capturedByOut: Iterator[StoredNode]      = Iterator.empty
  def _cdgOut: Iterator[StoredNode]             = Iterator.empty
  def _cfgOut: Iterator[StoredNode]             = Iterator.empty
  def _conditionOut: Iterator[StoredNode]       = Iterator.empty
  def _containsOut: Iterator[StoredNode]        = Iterator.empty
  def _dominateOut: Iterator[StoredNode]        = Iterator.empty
  def _evalTypeOut: Iterator[StoredNode]        = Iterator.empty
  def _importsOut: Iterator[StoredNode]         = Iterator.empty
  def _inheritsFromOut: Iterator[StoredNode]    = Iterator.empty
  def _isCallForImportOut: Iterator[StoredNode] = Iterator.empty
  def _parameterLinkOut: Iterator[StoredNode]   = Iterator.empty
  def _postDominateOut: Iterator[StoredNode]    = Iterator.empty
  def _reachingDefOut: Iterator[StoredNode]     = Iterator.empty
  def _receiverOut: Iterator[StoredNode]        = Iterator.empty
  def _refOut: Iterator[StoredNode]             = Iterator.empty
  def _sourceFileOut: Iterator[StoredNode]      = Iterator.empty
  def _taggedByOut: Iterator[StoredNode]        = Iterator.empty
}

object Factories {
  lazy val all: Seq[NodeFactory[?]] = Seq(
    Annotation.factory,
    AnnotationLiteral.factory,
    AnnotationParameter.factory,
    AnnotationParameterAssign.factory,
    ArrayInitializer.factory,
    Binding.factory,
    Block.factory,
    Call.factory,
    ClosureBinding.factory,
    Comment.factory,
    ConfigFile.factory,
    ControlStructure.factory,
    Dependency.factory,
    FieldIdentifier.factory,
    File.factory,
    Finding.factory,
    Identifier.factory,
    Import.factory,
    JumpLabel.factory,
    JumpTarget.factory,
    KeyValuePair.factory,
    Literal.factory,
    Local.factory,
    Location.factory,
    Member.factory,
    MetaData.factory,
    Method.factory,
    MethodParameterIn.factory,
    MethodParameterOut.factory,
    MethodRef.factory,
    MethodReturn.factory,
    Modifier.factory,
    Namespace.factory,
    NamespaceBlock.factory,
    Return.factory,
    Tag.factory,
    TagNodePair.factory,
    TemplateDom.factory,
    Type.factory,
    TypeArgument.factory,
    TypeDecl.factory,
    TypeParameter.factory,
    TypeRef.factory,
    Unknown.factory
  )
  lazy val allAsJava: java.util.List[NodeFactory[?]] = all.asJava
}
