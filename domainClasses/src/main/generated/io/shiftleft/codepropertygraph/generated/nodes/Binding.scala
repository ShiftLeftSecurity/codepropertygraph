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
  object PropertyNames {

    /** The FULL_NAME of a method. Used to link CALL and METHOD nodes. It is required to have exactly one METHOD node
      * for each METHOD_FULL_NAME
      */
    val MethodFullName = "METHOD_FULL_NAME"

    /** Name of represented object, e.g., method name (e.g. "run") */
    val Name = "NAME"

    /** The method signature encodes the types of parameters in a string. The string SHOULD be human readable and
      * suitable for differentiating methods with different parameter types sufficiently to allow for resolving of
      * function overloading. The present specification does not enforce a strict format for the signature, that is, it
      * can be chosen by the frontend implementor to fit the source language.
      */
    val Signature = "SIGNATURE"
  }
  object Properties {

    /** The FULL_NAME of a method. Used to link CALL and METHOD nodes. It is required to have exactly one METHOD node
      * for each METHOD_FULL_NAME
      */
    val MethodFullName = flatgraph.SinglePropertyKey[String](kind = 37, name = "METHOD_FULL_NAME", default = "<empty>")

    /** Name of represented object, e.g., method name (e.g. "run") */
    val Name = flatgraph.SinglePropertyKey[String](kind = 40, name = "NAME", default = "<empty>")

    /** The method signature encodes the types of parameters in a string. The string SHOULD be human readable and
      * suitable for differentiating methods with different parameter types sufficiently to allow for resolving of
      * function overloading. The present specification does not enforce a strict format for the signature, that is, it
      * can be chosen by the frontend implementor to fit the source language.
      */
    val Signature = flatgraph.SinglePropertyKey[String](kind = 50, name = "SIGNATURE", default = "")
  }
  object PropertyDefaults {
    val MethodFullName = "<empty>"
    val Name           = "<empty>"
    val Signature      = ""
  }
}

class Binding(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 5.toShort, seq_4762)
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

object NewBinding {
  def apply(): NewBinding                            = new NewBinding
  private val outNeighbors: Map[String, Set[String]] = Map("REF" -> Set("METHOD"))
  private val inNeighbors: Map[String, Set[String]]  = Map("BINDS" -> Set("TYPE_DECL"))

  object InsertionHelpers {
    object NewNodeInserter_Binding_methodFullName extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewBinding =>
              dstCast(offset) = generated.methodFullName
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Binding_name extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewBinding =>
              dstCast(offset) = generated.name
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Binding_signature extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewBinding =>
              dstCast(offset) = generated.signature
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
  }
}

class NewBinding extends NewNode(5.toShort) with BindingBase {
  override type StoredNodeType = Binding
  override def label: String = "BINDING"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewBinding.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewBinding.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var methodFullName: String                   = "<empty>": String
  var name: String                             = "<empty>": String
  var signature: String                        = "": String
  def methodFullName(value: String): this.type = { this.methodFullName = value; this }
  def name(value: String): this.type           = { this.name = value; this }
  def signature(value: String): this.type      = { this.signature = value; this }
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 37, 1)
    interface.countProperty(this, 40, 1)
    interface.countProperty(this, 50, 1)
  }

  override def copy: this.type = {
    val newInstance = new NewBinding
    newInstance.methodFullName = this.methodFullName
    newInstance.name = this.name
    newInstance.signature = this.signature
    newInstance.asInstanceOf[this.type]
  }

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

  override def productPrefix                = "NewBinding"
  override def productArity                 = 3
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewBinding]
}
