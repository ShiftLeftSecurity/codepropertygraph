package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait BindingEMT extends AnyRef with HasMethodFullNameEMT with HasNameEMT with HasSignatureEMT

trait BindingBase extends AbstractNode with StaticType[BindingEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.methodFullName) res.put("METHOD_FULL_NAME", this.methodFullName)
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    if (("": String) != this.signature) res.put("SIGNATURE", this.signature)
    res
  }
}

object Binding {
  val Label = "BINDING"
}

/** * NODE PROPERTIES:
  *
  * ▸ MethodFullName (String); Cardinality `one` (mandatory with default value `<empty>`); The FULL_NAME of a method.
  * Used to link CALL and METHOD nodes. It is required to have exactly one METHOD node for each METHOD_FULL_NAME
  *
  * ▸ Name (String); Cardinality `one` (mandatory with default value `<empty>`); Name of represented object, e.g.,
  * method name (e.g. "run")
  *
  * ▸ Signature (String); Cardinality `one` (mandatory with default value ``); The method signature encodes the types of
  * parameters in a string. The string SHOULD be human readable and suitable for differentiating methods with different
  * parameter types sufficiently to allow for resolving of function overloading. The present specification does not
  * enforce a strict format for the signature, that is, it can be chosen by the frontend implementor to fit the source
  * language.
  */
class Binding(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 5, seq_4762)
    with BindingBase
    with StaticType[BindingEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "methodFullName"
      case 1 => "name"
      case 2 => "signature"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.methodFullName
      case 1 => this.name
      case 2 => this.signature
      case _ => null
    }

  override def productPrefix = "Binding"
  override def productArity  = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Binding]
}
