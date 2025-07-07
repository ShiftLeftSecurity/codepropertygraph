package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait KeyValuePairEMT extends AnyRef with HasKeyEMT with HasValueEMT

trait KeyValuePairBase extends AbstractNode with StaticType[KeyValuePairEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.key) res.put("KEY", this.key)
    if (("": String) != this.value) res.put("VALUE", this.value)
    res
  }
}

object KeyValuePair {
  val Label = "KEY_VALUE_PAIR"
}

/** * NODE PROPERTIES:
  *
  * ▸ Key (String); Cardinality `one` (mandatory with default value `<empty>`); This property denotes a key of a
  * key-value pair.
  *
  * ▸ Value (String); Cardinality `one` (mandatory with default value ``); This property denotes a string value as used
  * in a key-value pair.
  */
class KeyValuePair(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 20, seq_4762)
    with KeyValuePairBase
    with StaticType[KeyValuePairEMT] {

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
  override def productArity  = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[KeyValuePair]
}
