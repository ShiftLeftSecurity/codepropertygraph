package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait TypeEMT extends AnyRef with HasFullNameEMT with HasNameEMT with HasTypeDeclFullNameEMT

trait TypeBase extends AbstractNode with StaticType[TypeEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.fullName) res.put("FULL_NAME", this.fullName)
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    if (("<empty>": String) != this.typeDeclFullName) res.put("TYPE_DECL_FULL_NAME", this.typeDeclFullName)
    res
  }
}

object Type {
  val Label = "TYPE"
}

/** * NODE PROPERTIES:
  *
  * ▸ FullName (String); Cardinality `one` (mandatory with default value `<empty>`); This is the fully-qualified name of
  * an entity, e.g., the fully-qualified name of a method or type. The details of what constitutes a fully-qualified
  * name are language specific. This field SHOULD be human readable.
  *
  * ▸ Name (String); Cardinality `one` (mandatory with default value `<empty>`); Name of represented object, e.g.,
  * method name (e.g. "run")
  *
  * ▸ TypeDeclFullName (String); Cardinality `one` (mandatory with default value `<empty>`); The static type decl of a
  * TYPE. This property is matched against the FULL_NAME of TYPE_DECL nodes. It is required to have exactly one
  * TYPE_DECL for each different TYPE_DECL_FULL_NAME
  */
class Type(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 37, seq_4762)
    with TypeBase
    with StaticType[TypeEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "fullName"
      case 1 => "name"
      case 2 => "typeDeclFullName"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.fullName
      case 1 => this.name
      case 2 => this.typeDeclFullName
      case _ => null
    }

  override def productPrefix = "Type"
  override def productArity  = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Type]
}
