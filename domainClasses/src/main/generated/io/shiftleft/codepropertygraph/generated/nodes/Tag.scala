package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait TagEMT extends AnyRef with HasNameEMT with HasValueEMT

trait TagBase extends AbstractNode with StaticType[TagEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    if (("": String) != this.value) res.put("VALUE", this.value)
    res
  }
}

object Tag {
  val Label = "TAG"
}

/** * NODE PROPERTIES:
  *
  * ▸ Name (String); Cardinality `one` (mandatory with default value `<empty>`); Name of represented object, e.g.,
  * method name (e.g. "run")
  *
  * ▸ Value (String); Cardinality `one` (mandatory with default value ``); This property denotes a string value as used
  * in a key-value pair.
  */
class Tag(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 34, seq_4762)
    with TagBase
    with StaticType[TagEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "name"
      case 1 => "value"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.name
      case 1 => this.value
      case _ => null
    }

  override def productPrefix = "Tag"
  override def productArity  = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Tag]
}
