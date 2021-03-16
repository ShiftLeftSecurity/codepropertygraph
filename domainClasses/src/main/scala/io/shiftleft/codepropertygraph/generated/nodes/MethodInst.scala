package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object MethodInst {
  def apply(graph: Graph, id: Long) = new MethodInst(graph, id)

  val Label = "METHOD_INST"

  object PropertyNames {
    val FullName = "FULL_NAME" 
    val MethodFullName = "METHOD_FULL_NAME" 
    val Name = "NAME" 
    val Order = "ORDER" 
    val Signature = "SIGNATURE" 
    val all: Set[String] = Set(FullName, MethodFullName, Name, Order, Signature)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val FullName = new PropertyKey[String]("FULL_NAME") 
    val MethodFullName = new PropertyKey[String]("METHOD_FULL_NAME") 
    val Name = new PropertyKey[String]("NAME") 
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    val Signature = new PropertyKey[String]("SIGNATURE") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.Ast.layoutInformation).asJava,
    List().asJava)


  object Edges {
    val Out: Array[String] = Array("AST")
    val In: Array[String] = Array()
  }

  val factory = new NodeFactory[MethodInstDb] {
    override val forLabel = MethodInst.Label

    override def createNode(ref: NodeRef[MethodInstDb]) =
      new MethodInstDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = MethodInst(graph, id)
  }
}

trait MethodInstBase extends CpgNode with AstNodeBase with HasFullName with HasMethodFullName with HasName with HasOrder with HasSignature {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class MethodInst(graph: Graph, id: Long) extends NodeRef[MethodInstDb](graph, id)
  with MethodInstBase
  with StoredNode
  with AstNode {
    override def fullName: String = get().fullName
  override def methodFullName: String = get().methodFullName
  override def name: String = get().name
  override def order: java.lang.Integer = get().order
  override def signature: String = get().signature
  
  def _typeArgumentViaAstOut: Iterator[TypeArgument] = get()._typeArgumentViaAstOut
override def _astOut: JIterator[StoredNode] = get()._astOut
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    MethodInst.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "fullName" 
case 2 => "methodFullName" 
case 3 => "name" 
case 4 => "order" 
case 5 => "signature" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => fullName
case 2 => methodFullName
case 3 => name
case 4 => order
case 5 => signature
    }

  override def productPrefix = "MethodInst"
  override def productArity = 6
}

class MethodInstDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with AstNode with MethodInstBase {

  override def layoutInformation: NodeLayoutInformation = MethodInst.layoutInformation

private var _fullName: String = null
def fullName: String = _fullName

private var _methodFullName: String = null
def methodFullName: String = _methodFullName

private var _name: String = null
def name: String = _name

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order

private var _signature: String = null
def signature: String = _signature


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (fullName != null) { properties.put("FULL_NAME", fullName) }
if (methodFullName != null) { properties.put("METHOD_FULL_NAME", methodFullName) }
if (name != null) { properties.put("NAME", name) }
if (order != null) { properties.put("ORDER", order) }
if (signature != null) { properties.put("SIGNATURE", signature) }

  properties
}

  def _typeArgumentViaAstOut: Iterator[TypeArgument] = _astOut.asScala.collect { case node: TypeArgument => node }
override def _astOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    MethodInst.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "fullName" 
case 2 => "methodFullName" 
case 3 => "name" 
case 4 => "order" 
case 5 => "signature" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => fullName
case 2 => methodFullName
case 3 => name
case 4 => order
case 5 => signature
    }

  override def productPrefix = "MethodInst"
  override def productArity = 6

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MethodInstDb]

  override def property(key:String): AnyRef = {
    key match {
      case "FULL_NAME" => this._fullName
      case "METHOD_FULL_NAME" => this._methodFullName
      case "NAME" => this._name
      case "ORDER" => this._order
      case "SIGNATURE" => this._signature
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "FULL_NAME" => this._fullName = value.asInstanceOf[String]
      case "METHOD_FULL_NAME" => this._methodFullName = value.asInstanceOf[String]
      case "NAME" => this._name = value.asInstanceOf[String]
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
      case "SIGNATURE" => this._signature = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewMethodInst]
   this._fullName = other.fullName
   this._methodFullName = other.methodFullName
   this._name = other.name
   this._order = other.order
   this._signature = other.signature

  graph.indexManager.putIfIndexed("FULL_NAME", other.fullName, this.ref)
}

}

/** Traversal steps for MethodInst */
class MethodInstTraversal[NodeType <: MethodInst](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to fullName property */
  def fullName: Traversal[String] =
    traversal.map(_.fullName)

    /**
    * Traverse to nodes where the fullName matches the regular expression `value`
    * */
  def fullName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.fullName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.fullName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the fullName matches at least one of the regular expressions in `values`
    * */
  def fullName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.fullName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where fullName matches `value` exactly.
    * */
  def fullNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.fullName == value}

  /**
    * Traverse to nodes where fullName matches one of the elements in `values` exactly.
    * */
  def fullNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.fullName)}
  }

  /**
    * Traverse to nodes where fullName does not match the regular expression `value`.
    * */
  def fullNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.fullName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.fullName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where fullName does not match any of the regular expressions in `values`.
    * */
  def fullNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.fullName); matcher.matches()}}
   }



  /** Traverse to methodFullName property */
  def methodFullName: Traversal[String] =
    traversal.map(_.methodFullName)

    /**
    * Traverse to nodes where the methodFullName matches the regular expression `value`
    * */
  def methodFullName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.methodFullName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.methodFullName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the methodFullName matches at least one of the regular expressions in `values`
    * */
  def methodFullName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.methodFullName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where methodFullName matches `value` exactly.
    * */
  def methodFullNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.methodFullName == value}

  /**
    * Traverse to nodes where methodFullName matches one of the elements in `values` exactly.
    * */
  def methodFullNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.methodFullName)}
  }

  /**
    * Traverse to nodes where methodFullName does not match the regular expression `value`.
    * */
  def methodFullNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.methodFullName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.methodFullName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where methodFullName does not match any of the regular expressions in `values`.
    * */
  def methodFullNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.methodFullName); matcher.matches()}}
   }



  /** Traverse to name property */
  def name: Traversal[String] =
    traversal.map(_.name)

    /**
    * Traverse to nodes where the name matches the regular expression `value`
    * */
  def name(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.name == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.name); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the name matches at least one of the regular expressions in `values`
    * */
  def name(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.name); matcher.matches()}}
   }

  /**
    * Traverse to nodes where name matches `value` exactly.
    * */
  def nameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.name == value}

  /**
    * Traverse to nodes where name matches one of the elements in `values` exactly.
    * */
  def nameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.name)}
  }

  /**
    * Traverse to nodes where name does not match the regular expression `value`.
    * */
  def nameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.name != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.name); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where name does not match any of the regular expressions in `values`.
    * */
  def nameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.name); matcher.matches()}}
   }



  /** Traverse to order property */
  def order: Traversal[java.lang.Integer] =
    traversal.map(_.order)

    /**
    * Traverse to nodes where the order equals the given `value`
    * */
  def order(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order == value}

  /**
    * Traverse to nodes where the order equals at least one of the given `values`
    * */
  def order(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => vset.contains(node.order)}
  }

  /**
    * Traverse to nodes where the order is greater than the given `value`
    * */
  def orderGt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order > value}

  /**
    * Traverse to nodes where the order is greater than or equal the given `value`
    * */
  def orderGte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order >= value}

  /**
    * Traverse to nodes where the order is less than the given `value`
    * */
  def orderLt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order < value}

  /**
    * Traverse to nodes where the order is less than or equal the given `value`
    * */
  def orderLte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order <= value}

  /**
    * Traverse to nodes where order is not equal to the given `value`.
    * */
  def orderNot(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order != value}

  /**
    * Traverse to nodes where order is not equal to any of the given `values`.
    * */
  def orderNot(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => !vset.contains(node.order)}
  }


  /** Traverse to signature property */
  def signature: Traversal[String] =
    traversal.map(_.signature)

    /**
    * Traverse to nodes where the signature matches the regular expression `value`
    * */
  def signature(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.signature == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.signature); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the signature matches at least one of the regular expressions in `values`
    * */
  def signature(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.signature); matcher.matches()}}
   }

  /**
    * Traverse to nodes where signature matches `value` exactly.
    * */
  def signatureExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.signature == value}

  /**
    * Traverse to nodes where signature matches one of the elements in `values` exactly.
    * */
  def signatureExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.signature)}
  }

  /**
    * Traverse to nodes where signature does not match the regular expression `value`.
    * */
  def signatureNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.signature != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.signature); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where signature does not match any of the regular expressions in `values`.
    * */
  def signatureNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.signature); matcher.matches()}}
   }




}
