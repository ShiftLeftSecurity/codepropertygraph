package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait LocationEMT
    extends AnyRef
    with HasClassNameEMT
    with HasClassShortNameEMT
    with HasFilenameEMT
    with HasLineNumberEMT
    with HasMethodFullNameEMT
    with HasMethodShortNameEMT
    with HasNodeLabelEMT
    with HasPackageNameEMT
    with HasSymbolEMT

trait LocationBase extends AbstractNode with StaticType[LocationEMT] {
  def node: Option[AbstractNode]
  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.className) res.put("CLASS_NAME", this.className)
    if (("<empty>": String) != this.classShortName) res.put("CLASS_SHORT_NAME", this.classShortName)
    if (("<empty>": String) != this.filename) res.put("FILENAME", this.filename)
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    if (("<empty>": String) != this.methodFullName) res.put("METHOD_FULL_NAME", this.methodFullName)
    if (("<empty>": String) != this.methodShortName) res.put("METHOD_SHORT_NAME", this.methodShortName)
    if (("<empty>": String) != this.nodeLabel) res.put("NODE_LABEL", this.nodeLabel)
    if (("<empty>": String) != this.packageName) res.put("PACKAGE_NAME", this.packageName)
    if (("<empty>": String) != this.symbol) res.put("SYMBOL", this.symbol)
    this.node.foreach { p => res.put("node", p) }
    res
  }
}

object Location {
  val Label = "LOCATION"
  object PropertyNames {

    val ClassName = "CLASS_NAME"

    val ClassShortName = "CLASS_SHORT_NAME"

    /** The path of the source file this node was generated from, relative to the root path in the meta data node. This
      * field must be set but may be set to the value `<unknown>` to indicate that no source file can be associated with
      * the node, e.g., because the node represents an entity known to exist because it is referenced, but for which the
      * file that is is declared in is unknown.
      */
    val Filename = "FILENAME"

    /** This optional field provides the line number of the program construct represented by the node.
      */
    val LineNumber = "LINE_NUMBER"

    /** The FULL_NAME of a method. Used to link CALL and METHOD nodes. It is required to have exactly one METHOD node
      * for each METHOD_FULL_NAME
      */
    val MethodFullName = "METHOD_FULL_NAME"

    val MethodShortName = "METHOD_SHORT_NAME"

    val NodeLabel = "NODE_LABEL"

    val PackageName = "PACKAGE_NAME"

    val Symbol = "SYMBOL"
    val Node   = "node"
  }
  object Properties {
    val ClassName      = flatgraph.SinglePropertyKey[String](kind = 6, name = "CLASS_NAME", default = "<empty>")
    val ClassShortName = flatgraph.SinglePropertyKey[String](kind = 7, name = "CLASS_SHORT_NAME", default = "<empty>")

    /** The path of the source file this node was generated from, relative to the root path in the meta data node. This
      * field must be set but may be set to the value `<unknown>` to indicate that no source file can be associated with
      * the node, e.g., because the node represents an entity known to exist because it is referenced, but for which the
      * file that is is declared in is unknown.
      */
    val Filename = flatgraph.SinglePropertyKey[String](kind = 21, name = "FILENAME", default = "<empty>")

    /** This optional field provides the line number of the program construct represented by the node.
      */
    val LineNumber = flatgraph.OptionalPropertyKey[Int](kind = 35, name = "LINE_NUMBER")

    /** The FULL_NAME of a method. Used to link CALL and METHOD nodes. It is required to have exactly one METHOD node
      * for each METHOD_FULL_NAME
      */
    val MethodFullName = flatgraph.SinglePropertyKey[String](kind = 37, name = "METHOD_FULL_NAME", default = "<empty>")
    val MethodShortName =
      flatgraph.SinglePropertyKey[String](kind = 38, name = "METHOD_SHORT_NAME", default = "<empty>")
    val NodeLabel   = flatgraph.SinglePropertyKey[String](kind = 41, name = "NODE_LABEL", default = "<empty>")
    val PackageName = flatgraph.SinglePropertyKey[String](kind = 46, name = "PACKAGE_NAME", default = "<empty>")
    val Symbol      = flatgraph.SinglePropertyKey[String](kind = 51, name = "SYMBOL", default = "<empty>")
  }
  object PropertyDefaults {
    val ClassName       = "<empty>"
    val ClassShortName  = "<empty>"
    val Filename        = "<empty>"
    val MethodFullName  = "<empty>"
    val MethodShortName = "<empty>"
    val NodeLabel       = "<empty>"
    val PackageName     = "<empty>"
    val Symbol          = "<empty>"
  }
}

class Location(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 23.toShort, seq_4762)
    with LocationBase
    with StaticType[LocationEMT] {
  def node: Option[StoredNode] = flatgraph.Accessors.getNodePropertyOption[StoredNode](graph, nodeKind, 58, seq)

  override def productElementName(n: Int): String =
    n match {
      case 0 => "className"
      case 1 => "classShortName"
      case 2 => "filename"
      case 3 => "lineNumber"
      case 4 => "methodFullName"
      case 5 => "methodShortName"
      case 6 => "nodeLabel"
      case 7 => "packageName"
      case 8 => "symbol"
      case 9 => "node"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.className
      case 1 => this.classShortName
      case 2 => this.filename
      case 3 => this.lineNumber
      case 4 => this.methodFullName
      case 5 => this.methodShortName
      case 6 => this.nodeLabel
      case 7 => this.packageName
      case 8 => this.symbol
      case 9 => this.node
      case _ => null
    }

  override def productPrefix = "Location"
  override def productArity  = 10

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Location]
}

object NewLocation {
  def apply(): NewLocation                           = new NewLocation
  private val outNeighbors: Map[String, Set[String]] = Map()
  private val inNeighbors: Map[String, Set[String]]  = Map()

  object InsertionHelpers {
    object NewNodeInserter_Location_className extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewLocation =>
              dstCast(offset) = generated.className
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Location_classShortName extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewLocation =>
              dstCast(offset) = generated.classShortName
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Location_filename extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewLocation =>
              dstCast(offset) = generated.filename
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Location_lineNumber extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[Int]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewLocation =>
              generated.lineNumber match {
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
    object NewNodeInserter_Location_methodFullName extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewLocation =>
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
    object NewNodeInserter_Location_methodShortName extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewLocation =>
              dstCast(offset) = generated.methodShortName
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Location_nodeLabel extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewLocation =>
              dstCast(offset) = generated.nodeLabel
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Location_packageName extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewLocation =>
              dstCast(offset) = generated.packageName
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Location_symbol extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewLocation =>
              dstCast(offset) = generated.symbol
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Location_node extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[flatgraph.GNode]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewLocation =>
              generated.node match {
                case Some(item) =>
                  dstCast(offset) = item match {
                    case newV: flatgraph.DNode => newV.storedRef.get; case oldV: flatgraph.GNode => oldV;
                    case null                  => null
                  }
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
  }
}

class NewLocation extends NewNode(23.toShort) with LocationBase {
  override type StoredNodeType = Location
  override def label: String = "LOCATION"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewLocation.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewLocation.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var className: String                            = "<empty>": String
  var classShortName: String                       = "<empty>": String
  var filename: String                             = "<empty>": String
  var lineNumber: Option[Int]                      = None
  var methodFullName: String                       = "<empty>": String
  var methodShortName: String                      = "<empty>": String
  var node: Option[AbstractNode]                   = None
  var nodeLabel: String                            = "<empty>": String
  var packageName: String                          = "<empty>": String
  var symbol: String                               = "<empty>": String
  def className(value: String): this.type          = { this.className = value; this }
  def classShortName(value: String): this.type     = { this.classShortName = value; this }
  def filename(value: String): this.type           = { this.filename = value; this }
  def lineNumber(value: Int): this.type            = { this.lineNumber = Option(value); this }
  def lineNumber(value: Option[Int]): this.type    = { this.lineNumber = value; this }
  def methodFullName(value: String): this.type     = { this.methodFullName = value; this }
  def methodShortName(value: String): this.type    = { this.methodShortName = value; this }
  def node(value: AbstractNode): this.type         = { this.node = Option(value); this }
  def node(value: Option[AbstractNode]): this.type = { this.node = value; this }
  def nodeLabel(value: String): this.type          = { this.nodeLabel = value; this }
  def packageName(value: String): this.type        = { this.packageName = value; this }
  def symbol(value: String): this.type             = { this.symbol = value; this }
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 6, 1)
    interface.countProperty(this, 7, 1)
    interface.countProperty(this, 21, 1)
    interface.countProperty(this, 35, lineNumber.size)
    interface.countProperty(this, 37, 1)
    interface.countProperty(this, 38, 1)
    interface.countProperty(this, 41, 1)
    interface.countProperty(this, 46, 1)
    interface.countProperty(this, 51, 1)
    interface.countProperty(this, 58, node.size)
    node.foreach(interface.visitContainedNode)
  }

  override def copy: this.type = {
    val newInstance = new NewLocation
    newInstance.className = this.className
    newInstance.classShortName = this.classShortName
    newInstance.filename = this.filename
    newInstance.lineNumber = this.lineNumber
    newInstance.methodFullName = this.methodFullName
    newInstance.methodShortName = this.methodShortName
    newInstance.nodeLabel = this.nodeLabel
    newInstance.packageName = this.packageName
    newInstance.symbol = this.symbol
    newInstance.node = this.node
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "className"
      case 1 => "classShortName"
      case 2 => "filename"
      case 3 => "lineNumber"
      case 4 => "methodFullName"
      case 5 => "methodShortName"
      case 6 => "nodeLabel"
      case 7 => "packageName"
      case 8 => "symbol"
      case 9 => "node"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.className
      case 1 => this.classShortName
      case 2 => this.filename
      case 3 => this.lineNumber
      case 4 => this.methodFullName
      case 5 => this.methodShortName
      case 6 => this.nodeLabel
      case 7 => this.packageName
      case 8 => this.symbol
      case 9 => this.node
      case _ => null
    }

  override def productPrefix                = "NewLocation"
  override def productArity                 = 10
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewLocation]
}
