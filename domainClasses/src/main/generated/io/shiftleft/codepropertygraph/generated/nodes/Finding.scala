package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Finding {
  def apply(graph: Graph, id: Long) = new Finding(graph, id)

  val Label = "FINDING"

  object PropertyNames {
    val Evidence                         = "evidence"
    val Keyvaluepairs                    = "keyValuePairs"
    val all: Set[String]                 = Set(Evidence, Keyvaluepairs)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {

    val Evidence      = new overflowdb.PropertyKey[IndexedSeq[StoredNode]]("evidence")
    val Keyvaluepairs = new overflowdb.PropertyKey[IndexedSeq[KeyValuePair]]("keyValuePairs")
  }

  object PropertyDefaults {}

  val layoutInformation = new NodeLayoutInformation(Label, PropertyNames.allAsJava, List().asJava, List().asJava)

  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String]  = Array()
  }

  val factory = new NodeFactory[FindingDb] {
    override val forLabel = Finding.Label

    override def createNode(ref: NodeRef[FindingDb]) =
      new FindingDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Finding(graph, id)
  }
}

trait FindingBase extends AbstractNode {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def evidence: IndexedSeq[AbstractNode]
  def keyValuePairs: IndexedSeq[KeyValuePairBase]
}

class Finding(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[FindingDb](graph_4762, id_4762)
    with FindingBase
    with StoredNode {

  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {

      case _ => super.propertyDefaultValue(propertyKey)
    }
  }

  def evidence: collection.immutable.IndexedSeq[StoredNode] = get().evidence

  def keyValuePairs: collection.immutable.IndexedSeq[KeyValuePair] = get().keyValuePairs

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
    Finding.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "evidence"
      case 2 => "keyValuePairs"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => evidence
      case 2 => keyValuePairs
    }

  override def productPrefix = "Finding"
  override def productArity  = 3
}

class FindingDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with FindingBase {

  override def layoutInformation: NodeLayoutInformation = Finding.layoutInformation

  private var _evidence: IndexedSeq[StoredNode] = collection.immutable.ArraySeq.empty
  def evidence: IndexedSeq[StoredNode]          = this._evidence

  private var _keyValuePairs: IndexedSeq[KeyValuePair] = collection.immutable.ArraySeq.empty
  def keyValuePairs: IndexedSeq[KeyValuePair]          = this._keyValuePairs

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]

    if (this._evidence != null && this._evidence.nonEmpty) { properties.put("evidence", this.evidence) }
    if (this._keyValuePairs != null && this._keyValuePairs.nonEmpty) {
      properties.put("keyValuePairs", this.keyValuePairs)
    }
    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]

    if (this._evidence != null && this._evidence.nonEmpty) { properties.put("evidence", this.evidence) }
    if (this._keyValuePairs != null && this._keyValuePairs.nonEmpty) {
      properties.put("keyValuePairs", this.keyValuePairs)
    }
    properties
  }

  import overflowdb.traversal._

  override def label: String = {
    Finding.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "evidence"
      case 2 => "keyValuePairs"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => evidence
      case 2 => keyValuePairs
    }

  override def productPrefix = "Finding"
  override def productArity  = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[FindingDb]

  override def property(key: String): Any = {
    key match {

      case "evidence"      => this._evidence
      case "keyValuePairs" => this._keyValuePairs
      case _               => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {

      case "evidence" =>
        this._evidence = value match {
          case null                                             => collection.immutable.ArraySeq.empty
          case singleValue: StoredNode                          => collection.immutable.ArraySeq(singleValue)
          case coll: IterableOnce[Any] if coll.iterator.isEmpty => collection.immutable.ArraySeq.empty
          case arr: Array[_] if arr.isEmpty                     => collection.immutable.ArraySeq.empty
          case arr: Array[_] => collection.immutable.ArraySeq.unsafeWrapArray(arr).asInstanceOf[IndexedSeq[StoredNode]]
          case jCollection: java.lang.Iterable[_] =>
            if (jCollection.iterator.hasNext) {
              collection.immutable.ArraySeq.unsafeWrapArray(
                jCollection.asInstanceOf[java.util.Collection[StoredNode]].iterator.asScala.toArray
              )
            } else collection.immutable.ArraySeq.empty
          case iter: Iterable[_] =>
            if (iter.nonEmpty) {
              collection.immutable.ArraySeq.unsafeWrapArray(iter.asInstanceOf[Iterable[StoredNode]].toArray)
            } else collection.immutable.ArraySeq.empty
        }
      case "keyValuePairs" =>
        this._keyValuePairs = value match {
          case null                                             => collection.immutable.ArraySeq.empty
          case singleValue: KeyValuePair                        => collection.immutable.ArraySeq(singleValue)
          case coll: IterableOnce[Any] if coll.iterator.isEmpty => collection.immutable.ArraySeq.empty
          case arr: Array[_] if arr.isEmpty                     => collection.immutable.ArraySeq.empty
          case arr: Array[_] =>
            collection.immutable.ArraySeq.unsafeWrapArray(arr).asInstanceOf[IndexedSeq[KeyValuePair]]
          case jCollection: java.lang.Iterable[_] =>
            if (jCollection.iterator.hasNext) {
              collection.immutable.ArraySeq.unsafeWrapArray(
                jCollection.asInstanceOf[java.util.Collection[KeyValuePair]].iterator.asScala.toArray
              )
            } else collection.immutable.ArraySeq.empty
          case iter: Iterable[_] =>
            if (iter.nonEmpty) {
              collection.immutable.ArraySeq.unsafeWrapArray(iter.asInstanceOf[Iterable[KeyValuePair]].toArray)
            } else collection.immutable.ArraySeq.empty
        }
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

    this._evidence =
      if (newNode.asInstanceOf[NewFinding].evidence == null || newNode.asInstanceOf[NewFinding].evidence.isEmpty) {
        collection.immutable.ArraySeq.empty
      } else {
        collection.immutable.ArraySeq.unsafeWrapArray(
          newNode
            .asInstanceOf[NewFinding]
            .evidence
            .map {
              case null                => throw new NullPointerException("NullPointers forbidden in contained nodes")
              case newNode: NewNode    => mapping(newNode).asInstanceOf[StoredNode]
              case oldNode: StoredNode => oldNode.asInstanceOf[StoredNode]
              case _                   => throw new MatchError("unreachable")
            }
            .toArray
        )
      }

    this._keyValuePairs =
      if (
        newNode.asInstanceOf[NewFinding].keyValuePairs == null || newNode.asInstanceOf[NewFinding].keyValuePairs.isEmpty
      ) {
        collection.immutable.ArraySeq.empty
      } else {
        collection.immutable.ArraySeq.unsafeWrapArray(
          newNode
            .asInstanceOf[NewFinding]
            .keyValuePairs
            .map {
              case null                => throw new NullPointerException("NullPointers forbidden in contained nodes")
              case newNode: NewNode    => mapping(newNode).asInstanceOf[KeyValuePair]
              case oldNode: StoredNode => oldNode.asInstanceOf[KeyValuePair]
              case _                   => throw new MatchError("unreachable")
            }
            .toArray
        )
      }

  }

}
