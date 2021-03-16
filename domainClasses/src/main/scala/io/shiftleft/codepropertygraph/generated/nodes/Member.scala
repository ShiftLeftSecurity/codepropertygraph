package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Member {
  def apply(graph: Graph, id: Long) = new Member(graph, id)

  val Label = "MEMBER"

  object PropertyNames {
    val Code = "CODE" 
    val DynamicTypeHintFullName = "DYNAMIC_TYPE_HINT_FULL_NAME" 
    val Name = "NAME" 
    val Order = "ORDER" 
    val TypeFullName = "TYPE_FULL_NAME" 
    val all: Set[String] = Set(Code, DynamicTypeHintFullName, Name, Order, TypeFullName)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Code = new PropertyKey[String]("CODE") 
    val DynamicTypeHintFullName = new PropertyKey[Seq[String]]("DYNAMIC_TYPE_HINT_FULL_NAME") 
    val Name = new PropertyKey[String]("NAME") 
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    val TypeFullName = new PropertyKey[String]("TYPE_FULL_NAME") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.DynamicType.layoutInformation, edges.EvalType.layoutInformation, edges.TaggedBy.layoutInformation, edges.Ast.layoutInformation).asJava,
    List(edges.Ref.layoutInformation, edges.Ast.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("AST","DYNAMIC_TYPE","EVAL_TYPE","TAGGED_BY")
    val In: Array[String] = Array("AST","REF")
  }

  val factory = new NodeFactory[MemberDb] {
    override val forLabel = Member.Label

    override def createNode(ref: NodeRef[MemberDb]) =
      new MemberDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Member(graph, id)
  }
}

trait MemberBase extends CpgNode with DeclarationBase with AstNodeBase with HasCode with HasDynamicTypeHintFullName with HasName with HasOrder with HasTypeFullName {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class Member(graph: Graph, id: Long) extends NodeRef[MemberDb](graph, id)
  with MemberBase
  with StoredNode
  with Declaration with AstNode {
    override def code: String = get().code
  override def dynamicTypeHintFullName: List[String] = get().dynamicTypeHintFullName
  override def name: String = get().name
  override def order: java.lang.Integer = get().order
  override def typeFullName: String = get().typeFullName
  
  def _typeDeclViaDynamicTypeOut: Iterator[TypeDecl] = get()._typeDeclViaDynamicTypeOut
override def _dynamicTypeOut: JIterator[StoredNode] = get()._dynamicTypeOut
def _typeViaEvalTypeOut: Iterator[Type] = get()._typeViaEvalTypeOut
override def _evalTypeOut: JIterator[StoredNode] = get()._evalTypeOut
def _tagViaTaggedByOut: Iterator[Tag] = get()._tagViaTaggedByOut
override def _taggedByOut: JIterator[StoredNode] = get()._taggedByOut
def _annotationViaAstOut: Iterator[Annotation] = get()._annotationViaAstOut
override def _astOut: JIterator[StoredNode] = get()._astOut
def _callViaRefIn: Iterator[Call] = get()._callViaRefIn
override def _refIn: JIterator[StoredNode] = get()._refIn
def _unknownViaAstIn: Iterator[Unknown] = get()._unknownViaAstIn
def _typeDeclViaAstIn: TypeDecl = get()._typeDeclViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Member.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "code" 
case 2 => "dynamicTypeHintFullName" 
case 3 => "name" 
case 4 => "order" 
case 5 => "typeFullName" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => code
case 2 => dynamicTypeHintFullName
case 3 => name
case 4 => order
case 5 => typeFullName
    }

  override def productPrefix = "Member"
  override def productArity = 6
}

class MemberDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with Declaration with AstNode with MemberBase {

  override def layoutInformation: NodeLayoutInformation = Member.layoutInformation

private var _code: String = null
def code: String = _code

private var _dynamicTypeHintFullName: List[String] = Nil
def dynamicTypeHintFullName: List[String] = _dynamicTypeHintFullName

private var _name: String = null
def name: String = _name

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order

private var _typeFullName: String = null
def typeFullName: String = _typeFullName


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (code != null) { properties.put("CODE", code) }
if (this._dynamicTypeHintFullName != null && this._dynamicTypeHintFullName.nonEmpty) { properties.put("DYNAMIC_TYPE_HINT_FULL_NAME", dynamicTypeHintFullName.asJava) }
if (name != null) { properties.put("NAME", name) }
if (order != null) { properties.put("ORDER", order) }
if (typeFullName != null) { properties.put("TYPE_FULL_NAME", typeFullName) }

  properties
}

  def _typeDeclViaDynamicTypeOut: Iterator[TypeDecl] = _dynamicTypeOut.asScala.collect { case node: TypeDecl => node }
override def _dynamicTypeOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _typeViaEvalTypeOut: Iterator[Type] = _evalTypeOut.asScala.collect { case node: Type => node }
override def _evalTypeOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _tagViaTaggedByOut: Iterator[Tag] = _taggedByOut.asScala.collect { case node: Tag => node }
override def _taggedByOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]
def _annotationViaAstOut: Iterator[Annotation] = _astOut.asScala.collect { case node: Annotation => node }
override def _astOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(3).asInstanceOf[JIterator[StoredNode]]
def _callViaRefIn: Iterator[Call] = _refIn.asScala.collect { case node: Call => node }
override def _refIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(4).asInstanceOf[JIterator[StoredNode]]
def _unknownViaAstIn: Iterator[Unknown] = _astIn.asScala.collect { case node: Unknown => node }
def _typeDeclViaAstIn: TypeDecl = _astIn.asScala.collect { case node: TypeDecl => node }.next()
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(5).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    Member.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "code" 
case 2 => "dynamicTypeHintFullName" 
case 3 => "name" 
case 4 => "order" 
case 5 => "typeFullName" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => code
case 2 => dynamicTypeHintFullName
case 3 => name
case 4 => order
case 5 => typeFullName
    }

  override def productPrefix = "Member"
  override def productArity = 6

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MemberDb]

  override def property(key:String): AnyRef = {
    key match {
      case "CODE" => this._code
      case "DYNAMIC_TYPE_HINT_FULL_NAME" => this._dynamicTypeHintFullName
      case "NAME" => this._name
      case "ORDER" => this._order
      case "TYPE_FULL_NAME" => this._typeFullName
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "CODE" => this._code = value.asInstanceOf[String]
      case "DYNAMIC_TYPE_HINT_FULL_NAME" => this._dynamicTypeHintFullName = value match {
        case singleValue: String => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[String]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[String]]
      }
      case "NAME" => this._name = value.asInstanceOf[String]
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
      case "TYPE_FULL_NAME" => this._typeFullName = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewMember]
   this._code = other.code
   this._dynamicTypeHintFullName = if(other.dynamicTypeHintFullName != null) other.dynamicTypeHintFullName else Nil
   this._name = other.name
   this._order = other.order
   this._typeFullName = other.typeFullName


}

}

/** Traversal steps for Member */
class MemberTraversal[NodeType <: Member](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to code property */
  def code: Traversal[String] =
    traversal.map(_.code)

    /**
    * Traverse to nodes where the code matches the regular expression `value`
    * */
  def code(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.code == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.code); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the code matches at least one of the regular expressions in `values`
    * */
  def code(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.code); matcher.matches()}}
   }

  /**
    * Traverse to nodes where code matches `value` exactly.
    * */
  def codeExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.code == value}

  /**
    * Traverse to nodes where code matches one of the elements in `values` exactly.
    * */
  def codeExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.code)}
  }

  /**
    * Traverse to nodes where code does not match the regular expression `value`.
    * */
  def codeNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.code != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.code); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where code does not match any of the regular expressions in `values`.
    * */
  def codeNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.code); matcher.matches()}}
   }



  /** Traverse to dynamicTypeHintFullName property */
  def dynamicTypeHintFullName: Traversal[String] =
    traversal.flatMap(_.dynamicTypeHintFullName)

  

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


  /** Traverse to typeFullName property */
  def typeFullName: Traversal[String] =
    traversal.map(_.typeFullName)

    /**
    * Traverse to nodes where the typeFullName matches the regular expression `value`
    * */
  def typeFullName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.typeFullName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.typeFullName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
    * */
  def typeFullName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.typeFullName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where typeFullName matches `value` exactly.
    * */
  def typeFullNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.typeFullName == value}

  /**
    * Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
    * */
  def typeFullNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.typeFullName)}
  }

  /**
    * Traverse to nodes where typeFullName does not match the regular expression `value`.
    * */
  def typeFullNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.typeFullName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.typeFullName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
    * */
  def typeFullNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.typeFullName); matcher.matches()}}
   }




}
