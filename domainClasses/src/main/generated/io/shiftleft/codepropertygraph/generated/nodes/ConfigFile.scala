package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait ConfigFileEMT extends AnyRef with HasContentEMT with HasNameEMT

trait ConfigFileBase extends AbstractNode with StaticType[ConfigFileEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.content) res.put("CONTENT", this.content)
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    res
  }
}

object ConfigFile {
  val Label = "CONFIG_FILE"
}

/** * NODE PROPERTIES:
  *
  * ▸ Content (String); Cardinality `one` (mandatory with default value `<empty>`); Certain files, e.g., configuration
  * files, may be included in the CPG as-is. For such files, the `CONTENT` field contains the files content.
  *
  * ▸ Name (String); Cardinality `one` (mandatory with default value `<empty>`); Name of represented object, e.g.,
  * method name (e.g. "run")
  */
class ConfigFile(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 10, seq_4762)
    with ConfigFileBase
    with StaticType[ConfigFileEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "content"
      case 1 => "name"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.content
      case 1 => this.name
      case _ => null
    }

  override def productPrefix = "ConfigFile"
  override def productArity  = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[ConfigFile]
}
