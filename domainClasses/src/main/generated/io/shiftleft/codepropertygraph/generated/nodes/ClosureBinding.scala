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
}

/** * NODE PROPERTIES:
  *
  * ▸ ClosureBindingId (String); Cardinality `ZeroOrOne` (optional); Identifier which uniquely describes a
  * CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes
  *
  * ▸ ClosureOriginalName (String); Cardinality `ZeroOrOne` (optional); Deprecated. Create an explict REF edge instead.
  * Formerly the original name of the (potentially mangled) captured variable
  *
  * ▸ EvaluationStrategy (String); Cardinality `one` (mandatory with default value `<empty>`); For formal method input
  * parameters, output parameters, and return parameters, this field holds the evaluation strategy, which is one of the
  * following: 1) `BY_REFERENCE` indicates that the parameter is passed by reference, 2) `BY_VALUE` indicates that it is
  * passed by value, that is, a copy is made, 3) `BY_SHARING` the parameter is a pointer/reference and it is shared with
  * the caller/callee. While a copy of the pointer is made, a copy of the object that it points to is not made.
  */
class ClosureBinding(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 8, seq_4762)
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
