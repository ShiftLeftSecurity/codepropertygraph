package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.Language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}

trait MetaDataEMT extends AnyRef with HasHashEMT with HasLanguageEMT with HasOverlaysEMT with HasRootEMT with HasVersionEMT

trait MetaDataBase extends AbstractNode with StaticType[MetaDataEMT] {
  
  override def propertiesMap: java.util.Map[String, Any] = {
 import io.shiftleft.codepropertygraph.generated.accessors.Lang.*
 val res = new java.util.HashMap[String, Any]()
this.hash.foreach{p => res.put("HASH", p )}
res.put("LANGUAGE", this.language )
val tmpOverlays = this.overlays; if(tmpOverlays.nonEmpty) res.put("OVERLAYS", tmpOverlays)
res.put("ROOT", this.root )
res.put("VERSION", this.version )
 res
}
}

object MetaData {
  val Label = "META_DATA"
  object PropertyNames {
    val Hash = io.shiftleft.codepropertygraph.generated.PropertyNames.HASH
val Language = io.shiftleft.codepropertygraph.generated.PropertyNames.LANGUAGE
val Overlays = io.shiftleft.codepropertygraph.generated.PropertyNames.OVERLAYS
val Root = io.shiftleft.codepropertygraph.generated.PropertyNames.ROOT
val Version = io.shiftleft.codepropertygraph.generated.PropertyNames.VERSION
  }
  object PropertyDefaults {
    val Language = "<empty>"
val Root = "<empty>"
val Version = "<empty>"
  }
}

class MetaData(graph_4762: flatgraph.Graph, seq_4762: Int) extends StoredNode(graph_4762, 25.toShort , seq_4762) with MetaDataBase with StaticType[MetaDataEMT] {
  

  override def productElementName(n: Int): String =
    n match {
      case 0 => "hash"
case 1 => "language"
case 2 => "overlays"
case 3 => "root"
case 4 => "version"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.hash
case 1 => this.language
case 2 => this.overlays
case 3 => this.root
case 4 => this.version
      case _ => null
    }

  override def productPrefix = "MetaData"
  override def productArity = 5

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MetaData]
}

object NewMetaData {
  def apply(): NewMetaData = new NewMetaData
  private val outNeighbors: Map[String, Set[String]] = Map()
  private val inNeighbors: Map[String, Set[String]] = Map()
}
class NewMetaData extends NewNode(25.toShort) with MetaDataBase {
  override type StoredNodeType = MetaData
  override def label: String = "META_DATA"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewMetaData.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewMetaData.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var hash: Option[String] = None
var language: String = "<empty>": String
var overlays: IndexedSeq[String] = ArraySeq.empty
var root: String = "<empty>": String
var version: String = "<empty>": String
  def hash(value: Option[String]): this.type = {this.hash = value; this }
def hash(value: String): this.type = {this.hash = Option(value); this }
def language(value: String): this.type = {this.language = value; this }
def overlays(value: IterableOnce[String]): this.type = {this.overlays = value.iterator.to(ArraySeq); this }
def root(value: String): this.type = {this.root = value; this }
def version(value: String): this.type = {this.version = value; this }
  override def flattenProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
if(hash.nonEmpty) interface.insertProperty(this, 23, this.hash)
interface.insertProperty(this, 33, Iterator(this.language))
if(overlays.nonEmpty) interface.insertProperty(this, 44, this.overlays)
interface.insertProperty(this, 48, Iterator(this.root))
interface.insertProperty(this, 54, Iterator(this.version))
}

  override def copy(): this.type = {
    val newInstance = new NewMetaData
    newInstance.hash = this.hash
newInstance.language = this.language
newInstance.overlays = this.overlays
newInstance.root = this.root
newInstance.version = this.version
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "hash"
case 1 => "language"
case 2 => "overlays"
case 3 => "root"
case 4 => "version"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.hash
case 1 => this.language
case 2 => this.overlays
case 3 => this.root
case 4 => this.version
      case _ => null
    }

  override def productPrefix = "NewMetaData"
  override def productArity = 5
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewMetaData]
}
