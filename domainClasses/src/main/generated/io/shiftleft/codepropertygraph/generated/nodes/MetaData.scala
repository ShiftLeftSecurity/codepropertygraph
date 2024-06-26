package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object MetaData {
  def apply(graph: Graph, id: Long) = new MetaData(graph, id)

  val Label = "META_DATA"

  object PropertyNames {
    val Hash                             = "HASH"
    val Language                         = "LANGUAGE"
    val Overlays                         = "OVERLAYS"
    val Root                             = "ROOT"
    val Version                          = "VERSION"
    val all: Set[String]                 = Set(Hash, Language, Overlays, Root, Version)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Hash     = new overflowdb.PropertyKey[String]("HASH")
    val Language = new overflowdb.PropertyKey[String]("LANGUAGE")
    val Overlays = new overflowdb.PropertyKey[IndexedSeq[String]]("OVERLAYS")
    val Root     = new overflowdb.PropertyKey[String]("ROOT")
    val Version  = new overflowdb.PropertyKey[String]("VERSION")

  }

  object PropertyDefaults {
    val Language = "<empty>"
    val Root     = "<empty>"
    val Version  = "<empty>"
  }

  val layoutInformation = new NodeLayoutInformation(Label, PropertyNames.allAsJava, List().asJava, List().asJava)

  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String]  = Array()
  }

  val factory = new NodeFactory[MetaDataDb] {
    override val forLabel = MetaData.Label

    override def createNode(ref: NodeRef[MetaDataDb]) =
      new MetaDataDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = MetaData(graph, id)
  }
}

trait MetaDataBase extends AbstractNode {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def hash: Option[String]
  def language: String
  def overlays: IndexedSeq[String]
  def root: String
  def version: String

}

class MetaData(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[MetaDataDb](graph_4762, id_4762)
    with MetaDataBase
    with StoredNode {
  override def hash: Option[String]         = get().hash
  override def language: String             = get().language
  override def overlays: IndexedSeq[String] = get().overlays
  override def root: String                 = get().root
  override def version: String              = get().version
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "LANGUAGE" => MetaData.PropertyDefaults.Language
      case "ROOT"     => MetaData.PropertyDefaults.Root
      case "VERSION"  => MetaData.PropertyDefaults.Version
      case _          => super.propertyDefaultValue(propertyKey)
    }
  }

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
    MetaData.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "hash"
      case 2 => "language"
      case 3 => "overlays"
      case 4 => "root"
      case 5 => "version"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => hash
      case 2 => language
      case 3 => overlays
      case 4 => root
      case 5 => version
    }

  override def productPrefix = "MetaData"
  override def productArity  = 6
}

class MetaDataDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with MetaDataBase {

  override def layoutInformation: NodeLayoutInformation = MetaData.layoutInformation

  private var _hash: String                 = null
  def hash: Option[String]                  = Option(_hash)
  private var _language: String             = MetaData.PropertyDefaults.Language
  def language: String                      = _language
  private var _overlays: IndexedSeq[String] = collection.immutable.ArraySeq.empty
  def overlays: IndexedSeq[String]          = _overlays
  private var _root: String                 = MetaData.PropertyDefaults.Root
  def root: String                          = _root
  private var _version: String              = MetaData.PropertyDefaults.Version
  def version: String                       = _version

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    hash.map { value => properties.put("HASH", value) }
    properties.put("LANGUAGE", language)
    if (this._overlays != null && this._overlays.nonEmpty) { properties.put("OVERLAYS", overlays) }
    properties.put("ROOT", root)
    properties.put("VERSION", version)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    hash.map { value => properties.put("HASH", value) }
    if (!(("<empty>") == language)) { properties.put("LANGUAGE", language) }
    if (this._overlays != null && this._overlays.nonEmpty) { properties.put("OVERLAYS", overlays) }
    if (!(("<empty>") == root)) { properties.put("ROOT", root) }
    if (!(("<empty>") == version)) { properties.put("VERSION", version) }

    properties
  }

  import overflowdb.traversal._

  override def label: String = {
    MetaData.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "hash"
      case 2 => "language"
      case 3 => "overlays"
      case 4 => "root"
      case 5 => "version"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => hash
      case 2 => language
      case 3 => overlays
      case 4 => root
      case 5 => version
    }

  override def productPrefix = "MetaData"
  override def productArity  = 6

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MetaDataDb]

  override def property(key: String): Any = {
    key match {
      case "HASH"     => this._hash
      case "LANGUAGE" => this._language
      case "OVERLAYS" => this._overlays
      case "ROOT"     => this._root
      case "VERSION"  => this._version

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "HASH"     => this._hash = value.asInstanceOf[String]
      case "LANGUAGE" => this._language = value.asInstanceOf[String]
      case "OVERLAYS" =>
        this._overlays = value match {
          case null                                             => collection.immutable.ArraySeq.empty
          case singleValue: String                              => collection.immutable.ArraySeq(singleValue)
          case coll: IterableOnce[Any] if coll.iterator.isEmpty => collection.immutable.ArraySeq.empty
          case arr: Array[_] if arr.isEmpty                     => collection.immutable.ArraySeq.empty
          case arr: Array[_] => collection.immutable.ArraySeq.unsafeWrapArray(arr).asInstanceOf[IndexedSeq[String]]
          case jCollection: java.lang.Iterable[_] =>
            if (jCollection.iterator.hasNext) {
              collection.immutable.ArraySeq.unsafeWrapArray(
                jCollection.asInstanceOf[java.util.Collection[String]].iterator.asScala.toArray
              )
            } else collection.immutable.ArraySeq.empty
          case iter: Iterable[_] =>
            if (iter.nonEmpty) {
              collection.immutable.ArraySeq.unsafeWrapArray(iter.asInstanceOf[Iterable[String]].toArray)
            } else collection.immutable.ArraySeq.empty
        }
      case "ROOT"    => this._root = value.asInstanceOf[String]
      case "VERSION" => this._version = value.asInstanceOf[String]

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
    this._hash = newNode.asInstanceOf[NewMetaData].hash.orNull
    this._language = newNode.asInstanceOf[NewMetaData].language
    this._overlays =
      if (newNode.asInstanceOf[NewMetaData].overlays != null) newNode.asInstanceOf[NewMetaData].overlays
      else collection.immutable.ArraySeq.empty
    this._root = newNode.asInstanceOf[NewMetaData].root
    this._version = newNode.asInstanceOf[NewMetaData].version

  }

}
