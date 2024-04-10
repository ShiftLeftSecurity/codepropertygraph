package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Dependency {
  def apply(graph: Graph, id: Long) = new Dependency(graph, id)

  val Label = "DEPENDENCY"

  object PropertyNames {
    val DependencyGroupId                = "DEPENDENCY_GROUP_ID"
    val Name                             = "NAME"
    val Version                          = "VERSION"
    val all: Set[String]                 = Set(DependencyGroupId, Name, Version)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val DependencyGroupId = new overflowdb.PropertyKey[String]("DEPENDENCY_GROUP_ID")
    val Name              = new overflowdb.PropertyKey[String]("NAME")
    val Version           = new overflowdb.PropertyKey[String]("VERSION")

  }

  object PropertyDefaults {
    val Name    = "<empty>"
    val Version = "<empty>"
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List().asJava,
    List(io.shiftleft.codepropertygraph.generated.edges.Imports.layoutInformation).asJava
  )

  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String]  = Array("IMPORTS")
  }

  val factory = new NodeFactory[DependencyDb] {
    override val forLabel = Dependency.Label

    override def createNode(ref: NodeRef[DependencyDb]) =
      new DependencyDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Dependency(graph, id)
  }
}

trait DependencyBase extends AbstractNode {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def dependencyGroupId: Option[String]
  def name: String
  def version: String

}

class Dependency(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[DependencyDb](graph_4762, id_4762)
    with DependencyBase
    with StoredNode {
  override def dependencyGroupId: Option[String] = get().dependencyGroupId
  override def name: String                      = get().name
  override def version: String                   = get().version
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "NAME"    => Dependency.PropertyDefaults.Name
      case "VERSION" => Dependency.PropertyDefaults.Version
      case _         => super.propertyDefaultValue(propertyKey)
    }
  }

  def importsIn: Iterator[Import] = get().importsIn
  override def _importsIn         = get()._importsIn

  /** Traverse to IMPORT via IMPORTS IN edge.
    */
  def _importViaImportsIn: overflowdb.traversal.Traversal[Import] = get()._importViaImportsIn

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
    Dependency.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "dependencyGroupId"
      case 2 => "name"
      case 3 => "version"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => dependencyGroupId
      case 2 => name
      case 3 => version
    }

  override def productPrefix = "Dependency"
  override def productArity  = 4
}

class DependencyDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with DependencyBase {

  override def layoutInformation: NodeLayoutInformation = Dependency.layoutInformation

  private var _dependencyGroupId: String = null
  def dependencyGroupId: Option[String]  = Option(_dependencyGroupId)
  private var _name: String              = Dependency.PropertyDefaults.Name
  def name: String                       = _name
  private var _version: String           = Dependency.PropertyDefaults.Version
  def version: String                    = _version

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    dependencyGroupId.map { value => properties.put("DEPENDENCY_GROUP_ID", value) }
    properties.put("NAME", name)
    properties.put("VERSION", version)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    dependencyGroupId.map { value => properties.put("DEPENDENCY_GROUP_ID", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!(("<empty>") == version)) { properties.put("VERSION", version) }

    properties
  }

  import overflowdb.traversal._
  def importsIn: Iterator[Import] = createAdjacentNodeScalaIteratorByOffSet[Import](0)
  override def _importsIn         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def _importViaImportsIn: overflowdb.traversal.Traversal[Import] = importsIn.collectAll[Import]

  override def label: String = {
    Dependency.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "dependencyGroupId"
      case 2 => "name"
      case 3 => "version"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => dependencyGroupId
      case 2 => name
      case 3 => version
    }

  override def productPrefix = "Dependency"
  override def productArity  = 4

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[DependencyDb]

  override def property(key: String): Any = {
    key match {
      case "DEPENDENCY_GROUP_ID" => this._dependencyGroupId
      case "NAME"                => this._name
      case "VERSION"             => this._version

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "DEPENDENCY_GROUP_ID" => this._dependencyGroupId = value.asInstanceOf[String]
      case "NAME"                => this._name = value.asInstanceOf[String]
      case "VERSION"             => this._version = value.asInstanceOf[String]

      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
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
    this._dependencyGroupId = newNode.asInstanceOf[NewDependency].dependencyGroupId.orNull
    this._name = newNode.asInstanceOf[NewDependency].name
    this._version = newNode.asInstanceOf[NewDependency].version

  }

}
