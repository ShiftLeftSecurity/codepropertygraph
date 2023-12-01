package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.Language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}

trait KeyValuePairEMT extends AnyRef with HasKeyEMT with HasValueEMT

trait KeyValuePairBase extends AbstractNode with StaticType[KeyValuePairEMT] {
  
  override def propertiesMap: java.util.Map[String, Any] = {
 import io.shiftleft.codepropertygraph.generated.accessors.Lang.*
 val res = new java.util.HashMap[String, Any]()
res.put("KEY", this.key )
res.put("VALUE", this.value )
 res
}
}

object KeyValuePair {
  val Label = "KEY_VALUE_PAIR"
  object PropertyNames {
    val Key = io.shiftleft.codepropertygraph.generated.PropertyNames.KEY
val Value = io.shiftleft.codepropertygraph.generated.PropertyNames.VALUE
  }
  object PropertyDefaults {
    val Key = "<empty>"
val Value = ""
  }
}

class KeyValuePair(graph_4762: flatgraph.Graph, seq_4762: Int) extends StoredNode(graph_4762, 20.toShort , seq_4762) with KeyValuePairBase with StaticType[KeyValuePairEMT] {
  

  override def productElementName(n: Int): String =
    n match {
      case 0 => "key"
case 1 => "value"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.key
case 1 => this.value
      case _ => null
    }

  override def productPrefix = "KeyValuePair"
  override def productArity = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[KeyValuePair]
}

object NewKeyValuePair {
  def apply(): NewKeyValuePair = new NewKeyValuePair
  private val outNeighbors: Map[String, Set[String]] = Map()
  private val inNeighbors: Map[String, Set[String]] = Map()
}
class NewKeyValuePair extends NewNode(20.toShort) with KeyValuePairBase {
  override type StoredNodeType = KeyValuePair
  override def label: String = "KEY_VALUE_PAIR"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewKeyValuePair.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewKeyValuePair.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var key: String = "<empty>": String
var value: String = "": String
  def key(value: String): this.type = {this.key = value; this }
def value(value: String): this.type = {this.value = value; this }
  override def flattenProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
interface.insertProperty(this, 32, Iterator(this.key))
interface.insertProperty(this, 53, Iterator(this.value))
}

  override def copy(): this.type = {
    val newInstance = new NewKeyValuePair
    newInstance.key = this.key
newInstance.value = this.value
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "key"
case 1 => "value"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.key
case 1 => this.value
      case _ => null
    }

  override def productPrefix = "NewKeyValuePair"
  override def productArity = 2
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewKeyValuePair]
}
