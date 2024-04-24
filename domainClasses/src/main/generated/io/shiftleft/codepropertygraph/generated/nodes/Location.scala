package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Location {
  def apply(graph: Graph, id: Long) = new Location(graph, id)

  val Label = "LOCATION"

  object PropertyNames {
    val ClassName       = "CLASS_NAME"
    val ClassShortName  = "CLASS_SHORT_NAME"
    val Filename        = "FILENAME"
    val LineNumber      = "LINE_NUMBER"
    val MethodFullName  = "METHOD_FULL_NAME"
    val MethodShortName = "METHOD_SHORT_NAME"
    val NodeLabel       = "NODE_LABEL"
    val PackageName     = "PACKAGE_NAME"
    val Symbol          = "SYMBOL"
    val Node            = "node"
    val all: Set[String] = Set(
      ClassName,
      ClassShortName,
      Filename,
      LineNumber,
      MethodFullName,
      MethodShortName,
      NodeLabel,
      PackageName,
      Symbol,
      Node
    )
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val ClassName       = new overflowdb.PropertyKey[String]("CLASS_NAME")
    val ClassShortName  = new overflowdb.PropertyKey[String]("CLASS_SHORT_NAME")
    val Filename        = new overflowdb.PropertyKey[String]("FILENAME")
    val LineNumber      = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val MethodFullName  = new overflowdb.PropertyKey[String]("METHOD_FULL_NAME")
    val MethodShortName = new overflowdb.PropertyKey[String]("METHOD_SHORT_NAME")
    val NodeLabel       = new overflowdb.PropertyKey[String]("NODE_LABEL")
    val PackageName     = new overflowdb.PropertyKey[String]("PACKAGE_NAME")
    val Symbol          = new overflowdb.PropertyKey[String]("SYMBOL")
    val Node            = new overflowdb.PropertyKey[StoredNode]("node")
  }

  object PropertyDefaults {
    val ClassName       = "<empty>"
    val ClassShortName  = "<empty>"
    val Filename        = "<empty>"
    val MethodFullName  = "<empty>"
    val MethodShortName = "<empty>"
    val NodeLabel       = "<empty>"
    val PackageName     = "<empty>"
    val Symbol          = "<empty>"
  }

  val layoutInformation = new NodeLayoutInformation(Label, PropertyNames.allAsJava, List().asJava, List().asJava)

  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String]  = Array()
  }

  val factory = new NodeFactory[LocationDb] {
    override val forLabel = Location.Label

    override def createNode(ref: NodeRef[LocationDb]) =
      new LocationDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Location(graph, id)
  }
}

trait LocationBase extends AbstractNode {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def className: String
  def classShortName: String
  def filename: String
  def lineNumber: Option[Integer]
  def methodFullName: String
  def methodShortName: String
  def nodeLabel: String
  def packageName: String
  def symbol: String

  def node: Option[AbstractNode]
}

class Location(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[LocationDb](graph_4762, id_4762)
    with LocationBase
    with StoredNode {
  override def className: String           = get().className
  override def classShortName: String      = get().classShortName
  override def filename: String            = get().filename
  override def lineNumber: Option[Integer] = get().lineNumber
  override def methodFullName: String      = get().methodFullName
  override def methodShortName: String     = get().methodShortName
  override def nodeLabel: String           = get().nodeLabel
  override def packageName: String         = get().packageName
  override def symbol: String              = get().symbol
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CLASS_NAME"        => Location.PropertyDefaults.ClassName
      case "CLASS_SHORT_NAME"  => Location.PropertyDefaults.ClassShortName
      case "FILENAME"          => Location.PropertyDefaults.Filename
      case "METHOD_FULL_NAME"  => Location.PropertyDefaults.MethodFullName
      case "METHOD_SHORT_NAME" => Location.PropertyDefaults.MethodShortName
      case "NODE_LABEL"        => Location.PropertyDefaults.NodeLabel
      case "PACKAGE_NAME"      => Location.PropertyDefaults.PackageName
      case "SYMBOL"            => Location.PropertyDefaults.Symbol
      case _                   => super.propertyDefaultValue(propertyKey)
    }
  }

  def node: Option[StoredNode] = get().node

  // In view of https://github.com/scala/bug/issues/4762 it is advisable to use different variable names in
  // patterns like `class Base(x:Int)` and `class Derived(x:Int) extends Base(x)`.
  // This must become `class Derived(x_4762:Int) extends Base(x_4762)`.
  // Otherwise, it is very hard to figure out whether uses of the identifier `x` refer to the base class x
  // or the derived class x.
  // When using that pattern, the class parameter `x_47672` should only be used in the `extends Base(x_4762)`
  // clause and nowhere else. Otherwise, the compiler may well decide that this is not just a constructor
  // parameter but also a field of the class, and we end up with two `x` fields. At best, this wastes memory;
  // at worst both fields go out-of-sync for hard-to-debug correctness bugs.

  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def canEqual(that: Any): Boolean                                        = get.canEqual(that)
  override def label: String = {
    Location.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "id"
      case 1  => "className"
      case 2  => "classShortName"
      case 3  => "filename"
      case 4  => "lineNumber"
      case 5  => "methodFullName"
      case 6  => "methodShortName"
      case 7  => "nodeLabel"
      case 8  => "packageName"
      case 9  => "symbol"
      case 10 => "node"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => id
      case 1  => className
      case 2  => classShortName
      case 3  => filename
      case 4  => lineNumber
      case 5  => methodFullName
      case 6  => methodShortName
      case 7  => nodeLabel
      case 8  => packageName
      case 9  => symbol
      case 10 => node
    }

  override def productPrefix = "Location"
  override def productArity  = 11
}

class LocationDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with LocationBase {

  override def layoutInformation: NodeLayoutInformation = Location.layoutInformation

  private var _className: String       = Location.PropertyDefaults.ClassName
  def className: String                = _className
  private var _classShortName: String  = Location.PropertyDefaults.ClassShortName
  def classShortName: String           = _classShortName
  private var _filename: String        = Location.PropertyDefaults.Filename
  def filename: String                 = _filename
  private var _lineNumber: Integer     = null
  def lineNumber: Option[Integer]      = Option(_lineNumber)
  private var _methodFullName: String  = Location.PropertyDefaults.MethodFullName
  def methodFullName: String           = _methodFullName
  private var _methodShortName: String = Location.PropertyDefaults.MethodShortName
  def methodShortName: String          = _methodShortName
  private var _nodeLabel: String       = Location.PropertyDefaults.NodeLabel
  def nodeLabel: String                = _nodeLabel
  private var _packageName: String     = Location.PropertyDefaults.PackageName
  def packageName: String              = _packageName
  private var _symbol: String          = Location.PropertyDefaults.Symbol
  def symbol: String                   = _symbol

  private var _node: StoredNode = null
  def node: Option[StoredNode]  = Option(this._node)

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("CLASS_NAME", className)
    properties.put("CLASS_SHORT_NAME", classShortName)
    properties.put("FILENAME", filename)
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("METHOD_FULL_NAME", methodFullName)
    properties.put("METHOD_SHORT_NAME", methodShortName)
    properties.put("NODE_LABEL", nodeLabel)
    properties.put("PACKAGE_NAME", packageName)
    properties.put("SYMBOL", symbol)
    node.map { value => properties.put("node", value) }
    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == className)) { properties.put("CLASS_NAME", className) }
    if (!(("<empty>") == classShortName)) { properties.put("CLASS_SHORT_NAME", classShortName) }
    if (!(("<empty>") == filename)) { properties.put("FILENAME", filename) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == methodFullName)) { properties.put("METHOD_FULL_NAME", methodFullName) }
    if (!(("<empty>") == methodShortName)) { properties.put("METHOD_SHORT_NAME", methodShortName) }
    if (!(("<empty>") == nodeLabel)) { properties.put("NODE_LABEL", nodeLabel) }
    if (!(("<empty>") == packageName)) { properties.put("PACKAGE_NAME", packageName) }
    if (!(("<empty>") == symbol)) { properties.put("SYMBOL", symbol) }
    node.map { value => properties.put("node", value) }
    properties
  }

  import overflowdb.traversal._

  override def label: String = {
    Location.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "id"
      case 1  => "className"
      case 2  => "classShortName"
      case 3  => "filename"
      case 4  => "lineNumber"
      case 5  => "methodFullName"
      case 6  => "methodShortName"
      case 7  => "nodeLabel"
      case 8  => "packageName"
      case 9  => "symbol"
      case 10 => "node"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => id
      case 1  => className
      case 2  => classShortName
      case 3  => filename
      case 4  => lineNumber
      case 5  => methodFullName
      case 6  => methodShortName
      case 7  => nodeLabel
      case 8  => packageName
      case 9  => symbol
      case 10 => node
    }

  override def productPrefix = "Location"
  override def productArity  = 11

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[LocationDb]

  override def property(key: String): Any = {
    key match {
      case "CLASS_NAME"        => this._className
      case "CLASS_SHORT_NAME"  => this._classShortName
      case "FILENAME"          => this._filename
      case "LINE_NUMBER"       => this._lineNumber
      case "METHOD_FULL_NAME"  => this._methodFullName
      case "METHOD_SHORT_NAME" => this._methodShortName
      case "NODE_LABEL"        => this._nodeLabel
      case "PACKAGE_NAME"      => this._packageName
      case "SYMBOL"            => this._symbol
      case "node"              => this._node
      case _                   => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "CLASS_NAME"        => this._className = value.asInstanceOf[String]
      case "CLASS_SHORT_NAME"  => this._classShortName = value.asInstanceOf[String]
      case "FILENAME"          => this._filename = value.asInstanceOf[String]
      case "LINE_NUMBER"       => this._lineNumber = value.asInstanceOf[Integer]
      case "METHOD_FULL_NAME"  => this._methodFullName = value.asInstanceOf[String]
      case "METHOD_SHORT_NAME" => this._methodShortName = value.asInstanceOf[String]
      case "NODE_LABEL"        => this._nodeLabel = value.asInstanceOf[String]
      case "PACKAGE_NAME"      => this._packageName = value.asInstanceOf[String]
      case "SYMBOL"            => this._symbol = value.asInstanceOf[String]
      case "node"              => this._node = value.asInstanceOf[StoredNode]
      case _                   => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

  override def removeSpecificProperty(key: String): Unit =
    this.updateSpecificProperty(key, null)

  override def _initializeFromDetached(
    data: overflowdb.DetachedNodeData,
    mapper: java.util.function.Function[overflowdb.DetachedNodeData, Node]
  ) =
    fromNewNode(data.asInstanceOf[NewNode], nn => mapper.apply(nn).asInstanceOf[StoredNode])

  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = {
    this._className = newNode.asInstanceOf[NewLocation].className
    this._classShortName = newNode.asInstanceOf[NewLocation].classShortName
    this._filename = newNode.asInstanceOf[NewLocation].filename
    this._lineNumber = newNode.asInstanceOf[NewLocation].lineNumber.orNull
    this._methodFullName = newNode.asInstanceOf[NewLocation].methodFullName
    this._methodShortName = newNode.asInstanceOf[NewLocation].methodShortName
    this._nodeLabel = newNode.asInstanceOf[NewLocation].nodeLabel
    this._packageName = newNode.asInstanceOf[NewLocation].packageName
    this._symbol = newNode.asInstanceOf[NewLocation].symbol
    this._node = newNode.asInstanceOf[NewLocation].node match {
      case null | None               => null
      case Some(newNode: NewNode)    => mapping(newNode).asInstanceOf[StoredNode]
      case Some(oldNode: StoredNode) => oldNode.asInstanceOf[StoredNode]
      case _                         => throw new MatchError("unreachable")
    }

  }

}
