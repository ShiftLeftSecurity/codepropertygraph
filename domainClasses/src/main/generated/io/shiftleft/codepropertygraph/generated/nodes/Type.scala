package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.Language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}

trait TypeEMT extends AnyRef with HasFullNameEMT with HasNameEMT with HasTypeDeclFullNameEMT

trait TypeBase extends AbstractNode with StaticType[TypeEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.Lang.*
    val res = new java.util.HashMap[String, Any]()
    res.put("FULL_NAME", this.fullName)
    res.put("NAME", this.name)
    res.put("TYPE_DECL_FULL_NAME", this.typeDeclFullName)
    res
  }
}

object Type {
  val Label = "TYPE"
  object PropertyNames {
    val FullName         = io.shiftleft.codepropertygraph.generated.PropertyNames.FULL_NAME
    val Name             = io.shiftleft.codepropertygraph.generated.PropertyNames.NAME
    val TypeDeclFullName = io.shiftleft.codepropertygraph.generated.PropertyNames.TYPE_DECL_FULL_NAME
  }
  object PropertyDefaults {
    val FullName         = "<empty>"
    val Name             = "<empty>"
    val TypeDeclFullName = "<empty>"
  }
}

class Type(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 38.toShort, seq_4762)
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

object NewType {
  def apply(): NewType                               = new NewType
  private val outNeighbors: Map[String, Set[String]] = Map("AST" -> Set("TYPE_ARGUMENT"), "REF" -> Set("TYPE_DECL"))
  private val inNeighbors: Map[String, Set[String]] = Map(
    "ALIAS_OF" -> Set("TYPE_DECL"),
    "EVAL_TYPE" -> Set(
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CONTROL_STRUCTURE",
      "IDENTIFIER",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "INHERITS_FROM" -> Set("TYPE_DECL"),
    "REF"           -> Set("TYPE_ARGUMENT")
  )
}
class NewType extends NewNode(38.toShort) with TypeBase {
  override type StoredNodeType = Type
  override def label: String = "TYPE"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewType.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewType.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var fullName: String                           = "<empty>": String
  var name: String                               = "<empty>": String
  var typeDeclFullName: String                   = "<empty>": String
  def fullName(value: String): this.type         = { this.fullName = value; this }
  def name(value: String): this.type             = { this.name = value; this }
  def typeDeclFullName(value: String): this.type = { this.typeDeclFullName = value; this }
  override def flattenProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.insertProperty(this, 22, Iterator(this.fullName))
    interface.insertProperty(this, 39, Iterator(this.name))
    interface.insertProperty(this, 51, Iterator(this.typeDeclFullName))
  }

  override def copy(): this.type = {
    val newInstance = new NewType
    newInstance.fullName = this.fullName
    newInstance.name = this.name
    newInstance.typeDeclFullName = this.typeDeclFullName
    newInstance.asInstanceOf[this.type]
  }

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

  override def productPrefix                = "NewType"
  override def productArity                 = 3
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewType]
}
