package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.Language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}

trait ClosureBindingEMT
    extends AnyRef
    with HasClosureBindingIdEMT
    with HasClosureOriginalNameEMT
    with HasEvaluationStrategyEMT

trait ClosureBindingBase extends AbstractNode with StaticType[ClosureBindingEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.Lang.*
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
    val ClosureBindingId    = io.shiftleft.codepropertygraph.generated.PropertyNames.CLOSURE_BINDING_ID
    val ClosureOriginalName = io.shiftleft.codepropertygraph.generated.PropertyNames.CLOSURE_ORIGINAL_NAME
    val EvaluationStrategy  = io.shiftleft.codepropertygraph.generated.PropertyNames.EVALUATION_STRATEGY
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
  override def flattenProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    if (closureBindingId.nonEmpty) interface.insertProperty(this, 8, this.closureBindingId)
    if (closureOriginalName.nonEmpty) interface.insertProperty(this, 9, this.closureOriginalName)
    interface.insertProperty(this, 19, Iterator(this.evaluationStrategy))
  }

  override def copy(): this.type = {
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
