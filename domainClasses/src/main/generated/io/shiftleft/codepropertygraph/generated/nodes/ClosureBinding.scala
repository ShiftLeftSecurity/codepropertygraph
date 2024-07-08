package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait ClosureBindingEMT
    extends AnyRef
    with HasClosureBindingIdEMT
    with HasClosureOriginalNameEMT
    with HasEvaluationStrategyEMT

trait ClosureBindingBase extends AbstractNode with StaticType[ClosureBindingEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    this.closureBindingId.foreach { p => res.put("CLOSURE_BINDING_ID", p) }
    this.closureOriginalName.foreach { p => res.put("CLOSURE_ORIGINAL_NAME", p) }
    if (("<empty>": String) != this.evaluationStrategy) res.put("EVALUATION_STRATEGY", this.evaluationStrategy)
    res
  }
}

object ClosureBinding {
  val Label = "CLOSURE_BINDING"
  object PropertyNames {

    /** Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with
      * the corresponding CLOSURE_BINDING nodes
      */
    val ClosureBindingId = "CLOSURE_BINDING_ID"

    /** The original name of the (potentially mangled) captured variable */
    val ClosureOriginalName = "CLOSURE_ORIGINAL_NAME"

    /** For formal method input parameters, output parameters, and return parameters, this field holds the evaluation
      * strategy, which is one of the following: 1) `BY_REFERENCE` indicates that the parameter is passed by reference,
      * 2) `BY_VALUE` indicates that it is passed by value, that is, a copy is made, 3) `BY_SHARING` the parameter is a
      * pointer/reference and it is shared with the caller/callee. While a copy of the pointer is made, a copy of the
      * object that it points to is not made.
      */
    val EvaluationStrategy = "EVALUATION_STRATEGY"
  }
  object Properties {

    /** Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with
      * the corresponding CLOSURE_BINDING nodes
      */
    val ClosureBindingId = flatgraph.OptionalPropertyKey[String](kind = 8, name = "CLOSURE_BINDING_ID")

    /** The original name of the (potentially mangled) captured variable */
    val ClosureOriginalName = flatgraph.OptionalPropertyKey[String](kind = 9, name = "CLOSURE_ORIGINAL_NAME")

    /** For formal method input parameters, output parameters, and return parameters, this field holds the evaluation
      * strategy, which is one of the following: 1) `BY_REFERENCE` indicates that the parameter is passed by reference,
      * 2) `BY_VALUE` indicates that it is passed by value, that is, a copy is made, 3) `BY_SHARING` the parameter is a
      * pointer/reference and it is shared with the caller/callee. While a copy of the pointer is made, a copy of the
      * object that it points to is not made.
      */
    val EvaluationStrategy =
      flatgraph.SinglePropertyKey[String](kind = 19, name = "EVALUATION_STRATEGY", default = "<empty>")
  }
  object PropertyDefaults {
    val EvaluationStrategy = "<empty>"
  }
}

class ClosureBinding(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 8.toShort, seq_4762)
    with ClosureBindingBase
    with StaticType[ClosureBindingEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "closureBindingId"
      case 1 => "closureOriginalName"
      case 2 => "evaluationStrategy"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.closureBindingId
      case 1 => this.closureOriginalName
      case 2 => this.evaluationStrategy
      case _ => null
    }

  override def productPrefix = "ClosureBinding"
  override def productArity  = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[ClosureBinding]
}

object NewClosureBinding {
  def apply(): NewClosureBinding                     = new NewClosureBinding
  private val outNeighbors: Map[String, Set[String]] = Map("REF" -> Set("LOCAL", "METHOD_PARAMETER_IN"))
  private val inNeighbors: Map[String, Set[String]] =
    Map("CAPTURE" -> Set("METHOD_REF", "TYPE_REF"), "CAPTURED_BY" -> Set("LOCAL", "METHOD_PARAMETER_IN"))

  object InsertionHelpers {
    object NewNodeInserter_ClosureBinding_closureBindingId extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewClosureBinding =>
              generated.closureBindingId match {
                case Some(item) =>
                  dstCast(offset) = item
                  offset += 1
                case _ =>
              }
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_ClosureBinding_closureOriginalName extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewClosureBinding =>
              generated.closureOriginalName match {
                case Some(item) =>
                  dstCast(offset) = item
                  offset += 1
                case _ =>
              }
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_ClosureBinding_evaluationStrategy extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewClosureBinding =>
              dstCast(offset) = generated.evaluationStrategy
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

class NewClosureBinding extends NewNode(8.toShort) with ClosureBindingBase {
  override type StoredNodeType = ClosureBinding
  override def label: String = "CLOSURE_BINDING"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewClosureBinding.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewClosureBinding.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var closureBindingId: Option[String]                      = None
  var closureOriginalName: Option[String]                   = None
  var evaluationStrategy: String                            = "<empty>": String
  def closureBindingId(value: Option[String]): this.type    = { this.closureBindingId = value; this }
  def closureBindingId(value: String): this.type            = { this.closureBindingId = Option(value); this }
  def closureOriginalName(value: Option[String]): this.type = { this.closureOriginalName = value; this }
  def closureOriginalName(value: String): this.type         = { this.closureOriginalName = Option(value); this }
  def evaluationStrategy(value: String): this.type          = { this.evaluationStrategy = value; this }
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 8, closureBindingId.size)
    interface.countProperty(this, 9, closureOriginalName.size)
    interface.countProperty(this, 19, 1)
  }

  override def copy: this.type = {
    val newInstance = new NewClosureBinding
    newInstance.closureBindingId = this.closureBindingId
    newInstance.closureOriginalName = this.closureOriginalName
    newInstance.evaluationStrategy = this.evaluationStrategy
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "closureBindingId"
      case 1 => "closureOriginalName"
      case 2 => "evaluationStrategy"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.closureBindingId
      case 1 => this.closureOriginalName
      case 2 => this.evaluationStrategy
      case _ => null
    }

  override def productPrefix                = "NewClosureBinding"
  override def productArity                 = 3
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewClosureBinding]
}
