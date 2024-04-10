package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object ClosureBinding {
  def apply(graph: Graph, id: Long) = new ClosureBinding(graph, id)

  val Label = "CLOSURE_BINDING"

  object PropertyNames {
    val ClosureBindingId                 = "CLOSURE_BINDING_ID"
    val ClosureOriginalName              = "CLOSURE_ORIGINAL_NAME"
    val EvaluationStrategy               = "EVALUATION_STRATEGY"
    val all: Set[String]                 = Set(ClosureBindingId, ClosureOriginalName, EvaluationStrategy)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val ClosureBindingId    = new overflowdb.PropertyKey[String]("CLOSURE_BINDING_ID")
    val ClosureOriginalName = new overflowdb.PropertyKey[String]("CLOSURE_ORIGINAL_NAME")
    val EvaluationStrategy  = new overflowdb.PropertyKey[String]("EVALUATION_STRATEGY")

  }

  object PropertyDefaults {
    val EvaluationStrategy = "<empty>"
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(io.shiftleft.codepropertygraph.generated.edges.Ref.layoutInformation).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Capture.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.CapturedBy.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("REF")
    val In: Array[String]  = Array("CAPTURE", "CAPTURED_BY")
  }

  val factory = new NodeFactory[ClosureBindingDb] {
    override val forLabel = ClosureBinding.Label

    override def createNode(ref: NodeRef[ClosureBindingDb]) =
      new ClosureBindingDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = ClosureBinding(graph, id)
  }
}

trait ClosureBindingBase extends AbstractNode {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def closureBindingId: Option[String]
  def closureOriginalName: Option[String]
  def evaluationStrategy: String

}

class ClosureBinding(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[ClosureBindingDb](graph_4762, id_4762)
    with ClosureBindingBase
    with StoredNode {
  override def closureBindingId: Option[String]    = get().closureBindingId
  override def closureOriginalName: Option[String] = get().closureOriginalName
  override def evaluationStrategy: String          = get().evaluationStrategy
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "EVALUATION_STRATEGY" => ClosureBinding.PropertyDefaults.EvaluationStrategy
      case _                     => super.propertyDefaultValue(propertyKey)
    }
  }

  def refOut: Iterator[AstNode] = get().refOut
  override def _refOut          = get()._refOut

  /** Traverse to LOCAL via REF OUT edge.
    */
  def _localViaRefOut: Option[Local] = get()._localViaRefOut

  /** Traverse to METHOD_PARAMETER_IN via REF OUT edge.
    */
  def _methodParameterInViaRefOut: Option[MethodParameterIn] = get()._methodParameterInViaRefOut

  def captureIn: Iterator[Expression] = get().captureIn
  override def _captureIn             = get()._captureIn

  /** Traverse to METHOD_REF via CAPTURE IN edge.
    */
  def _methodRefViaCaptureIn: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaCaptureIn

  /** Traverse to TYPE_REF via CAPTURE IN edge.
    */
  def _typeRefViaCaptureIn: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaCaptureIn

  def capturedByIn: Iterator[AstNode] = get().capturedByIn
  override def _capturedByIn          = get()._capturedByIn

  /** Traverse to LOCAL via CAPTURED_BY IN edge.
    */
  def _localViaCapturedByIn: overflowdb.traversal.Traversal[Local] = get()._localViaCapturedByIn

  /** Traverse to METHOD_PARAMETER_IN via CAPTURED_BY IN edge.
    */
  def _methodParameterInViaCapturedByIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    get()._methodParameterInViaCapturedByIn

  // In view of https://github.com/scala/bug/issues/4762 it is advisable to use different variable names in
  // patterns like `class Base(x:Int)` and `class Derived(x:Int) extends Base(x)`.
  // This must become `class Derived(x_4762:Int) extends Base(x_4762)`.
  // Otherwise, it is very hard to figure out whether uses of the identifier `x` refer to the base class x
  // or the derived class x.
  // When using that pattern, the class parameter `x_47672` should only be used in the `extends Base(x_4762)`
  // clause and nowhere else. Otherwise, the compiler may well decide that this is not just a constructor
  // parameter but also a field of the class, and we end up with two `x` fields. At best, this wastes memory;
  // at worst both fields go out-of-sync for hard-to-debug correctness bugs.

  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def canEqual(that: Any): Boolean                                        = get.canEqual(that)
  override def label: String = {
    ClosureBinding.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "closureBindingId"
      case 2 => "closureOriginalName"
      case 3 => "evaluationStrategy"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => closureBindingId
      case 2 => closureOriginalName
      case 3 => evaluationStrategy
    }

  override def productPrefix = "ClosureBinding"
  override def productArity  = 4
}

class ClosureBindingDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with ClosureBindingBase {

  override def layoutInformation: NodeLayoutInformation = ClosureBinding.layoutInformation

  private var _closureBindingId: String    = null
  def closureBindingId: Option[String]     = Option(_closureBindingId)
  private var _closureOriginalName: String = null
  def closureOriginalName: Option[String]  = Option(_closureOriginalName)
  private var _evaluationStrategy: String  = ClosureBinding.PropertyDefaults.EvaluationStrategy
  def evaluationStrategy: String           = _evaluationStrategy

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    closureBindingId.map { value => properties.put("CLOSURE_BINDING_ID", value) }
    closureOriginalName.map { value => properties.put("CLOSURE_ORIGINAL_NAME", value) }
    properties.put("EVALUATION_STRATEGY", evaluationStrategy)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    closureBindingId.map { value => properties.put("CLOSURE_BINDING_ID", value) }
    closureOriginalName.map { value => properties.put("CLOSURE_ORIGINAL_NAME", value) }
    if (!(("<empty>") == evaluationStrategy)) { properties.put("EVALUATION_STRATEGY", evaluationStrategy) }

    properties
  }

  import overflowdb.traversal._
  def refOut: Iterator[AstNode]                              = createAdjacentNodeScalaIteratorByOffSet[AstNode](0)
  override def _refOut                                       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def _localViaRefOut: Option[Local]                         = refOut.collectAll[Local].nextOption()
  def _methodParameterInViaRefOut: Option[MethodParameterIn] = refOut.collectAll[MethodParameterIn].nextOption()

  def captureIn: Iterator[Expression] = createAdjacentNodeScalaIteratorByOffSet[Expression](1)
  override def _captureIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def _methodRefViaCaptureIn: overflowdb.traversal.Traversal[MethodRef] = captureIn.collectAll[MethodRef]
  def _typeRefViaCaptureIn: overflowdb.traversal.Traversal[TypeRef]     = captureIn.collectAll[TypeRef]

  def capturedByIn: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](2)
  override def _capturedByIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def _localViaCapturedByIn: overflowdb.traversal.Traversal[Local] = capturedByIn.collectAll[Local]
  def _methodParameterInViaCapturedByIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    capturedByIn.collectAll[MethodParameterIn]

  override def label: String = {
    ClosureBinding.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "closureBindingId"
      case 2 => "closureOriginalName"
      case 3 => "evaluationStrategy"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => closureBindingId
      case 2 => closureOriginalName
      case 3 => evaluationStrategy
    }

  override def productPrefix = "ClosureBinding"
  override def productArity  = 4

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[ClosureBindingDb]

  override def property(key: String): Any = {
    key match {
      case "CLOSURE_BINDING_ID"    => this._closureBindingId
      case "CLOSURE_ORIGINAL_NAME" => this._closureOriginalName
      case "EVALUATION_STRATEGY"   => this._evaluationStrategy

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "CLOSURE_BINDING_ID"    => this._closureBindingId = value.asInstanceOf[String]
      case "CLOSURE_ORIGINAL_NAME" => this._closureOriginalName = value.asInstanceOf[String]
      case "EVALUATION_STRATEGY"   => this._evaluationStrategy = value.asInstanceOf[String]

      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

  override def removeSpecificProperty(key: String): Unit =
    this.updateSpecificProperty(key, null)

  override def _initializeFromDetached(
    data: overflowdb.DetachedNodeData,
    mapper: java.util.function.Function[overflowdb.DetachedNodeData, Node]
  ) =
    fromNewNode(data.asInstanceOf[NewNode], nn => mapper.apply(nn).asInstanceOf[StoredNode])

  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = {
    this._closureBindingId = newNode.asInstanceOf[NewClosureBinding].closureBindingId.orNull
    this._closureOriginalName = newNode.asInstanceOf[NewClosureBinding].closureOriginalName.orNull
    this._evaluationStrategy = newNode.asInstanceOf[NewClosureBinding].evaluationStrategy

  }

}
