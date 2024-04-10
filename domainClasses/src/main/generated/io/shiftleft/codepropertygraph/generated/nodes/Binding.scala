package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Binding {
  def apply(graph: Graph, id: Long) = new Binding(graph, id)

  val Label = "BINDING"

  object PropertyNames {
    val MethodFullName                   = "METHOD_FULL_NAME"
    val Name                             = "NAME"
    val Signature                        = "SIGNATURE"
    val all: Set[String]                 = Set(MethodFullName, Name, Signature)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val MethodFullName = new overflowdb.PropertyKey[String]("METHOD_FULL_NAME")
    val Name           = new overflowdb.PropertyKey[String]("NAME")
    val Signature      = new overflowdb.PropertyKey[String]("SIGNATURE")

  }

  object PropertyDefaults {
    val MethodFullName = "<empty>"
    val Name           = "<empty>"
    val Signature      = ""
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(io.shiftleft.codepropertygraph.generated.edges.Ref.layoutInformation).asJava,
    List(io.shiftleft.codepropertygraph.generated.edges.Binds.layoutInformation).asJava
  )

  object Edges {
    val Out: Array[String] = Array("REF")
    val In: Array[String]  = Array("BINDS")
  }

  val factory = new NodeFactory[BindingDb] {
    override val forLabel = Binding.Label

    override def createNode(ref: NodeRef[BindingDb]) =
      new BindingDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Binding(graph, id)
  }
}

trait BindingBase extends AbstractNode {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def methodFullName: String
  def name: String
  def signature: String

}

class Binding(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[BindingDb](graph_4762, id_4762)
    with BindingBase
    with StoredNode {
  override def methodFullName: String = get().methodFullName
  override def name: String           = get().name
  override def signature: String      = get().signature
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "METHOD_FULL_NAME" => Binding.PropertyDefaults.MethodFullName
      case "NAME"             => Binding.PropertyDefaults.Name
      case "SIGNATURE"        => Binding.PropertyDefaults.Signature
      case _                  => super.propertyDefaultValue(propertyKey)
    }
  }

  def refOut: Iterator[Method] = get().refOut
  override def _refOut         = get()._refOut

  /** Traverse to METHOD via REF OUT edge.
    */
  def boundMethod: Method = get().boundMethod

  def bindsIn: Iterator[TypeDecl] = get().bindsIn
  override def _bindsIn           = get()._bindsIn

  /** Traverse to TYPE_DECL via BINDS IN edge.
    */
  def bindingTypeDecl: TypeDecl = get().bindingTypeDecl

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
    Binding.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "methodFullName"
      case 2 => "name"
      case 3 => "signature"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => methodFullName
      case 2 => name
      case 3 => signature
    }

  override def productPrefix = "Binding"
  override def productArity  = 4
}

class BindingDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with BindingBase {

  override def layoutInformation: NodeLayoutInformation = Binding.layoutInformation

  private var _methodFullName: String = Binding.PropertyDefaults.MethodFullName
  def methodFullName: String          = _methodFullName
  private var _name: String           = Binding.PropertyDefaults.Name
  def name: String                    = _name
  private var _signature: String      = Binding.PropertyDefaults.Signature
  def signature: String               = _signature

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("METHOD_FULL_NAME", methodFullName)
    properties.put("NAME", name)
    properties.put("SIGNATURE", signature)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == methodFullName)) { properties.put("METHOD_FULL_NAME", methodFullName) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!(("") == signature)) { properties.put("SIGNATURE", signature) }

    properties
  }

  import overflowdb.traversal._
  def refOut: Iterator[Method] = createAdjacentNodeScalaIteratorByOffSet[Method](0)
  override def _refOut         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def boundMethod: Method = try { refOut.collectAll[Method].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "OUT edge with label REF to an adjacent METHOD is mandatory, but not defined for this BINDING node with id=" + id,
        e
      )
  }

  def bindsIn: Iterator[TypeDecl] = createAdjacentNodeScalaIteratorByOffSet[TypeDecl](1)
  override def _bindsIn           = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def bindingTypeDecl: TypeDecl = try { bindsIn.collectAll[TypeDecl].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "IN edge with label BINDS to an adjacent TYPE_DECL is mandatory, but not defined for this BINDING node with id=" + id,
        e
      )
  }

  override def label: String = {
    Binding.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "methodFullName"
      case 2 => "name"
      case 3 => "signature"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => methodFullName
      case 2 => name
      case 3 => signature
    }

  override def productPrefix = "Binding"
  override def productArity  = 4

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[BindingDb]

  override def property(key: String): Any = {
    key match {
      case "METHOD_FULL_NAME" => this._methodFullName
      case "NAME"             => this._name
      case "SIGNATURE"        => this._signature

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "METHOD_FULL_NAME" => this._methodFullName = value.asInstanceOf[String]
      case "NAME"             => this._name = value.asInstanceOf[String]
      case "SIGNATURE"        => this._signature = value.asInstanceOf[String]

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
    this._methodFullName = newNode.asInstanceOf[NewBinding].methodFullName
    this._name = newNode.asInstanceOf[NewBinding].name
    this._signature = newNode.asInstanceOf[NewBinding].signature

  }

}
