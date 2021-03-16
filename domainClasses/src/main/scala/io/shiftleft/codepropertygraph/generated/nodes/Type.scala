package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Type {
  def apply(graph: Graph, id: Long) = new Type(graph, id)

  val Label = "TYPE"

  object PropertyNames {
    val FullName = "FULL_NAME" 
    val Name = "NAME" 
    val TypeDeclFullName = "TYPE_DECL_FULL_NAME" 
    val all: Set[String] = Set(FullName, Name, TypeDeclFullName)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val FullName = new PropertyKey[String]("FULL_NAME") 
    val Name = new PropertyKey[String]("NAME") 
    val TypeDeclFullName = new PropertyKey[String]("TYPE_DECL_FULL_NAME") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.Ref.layoutInformation, edges.Ast.layoutInformation).asJava,
    List(edges.InheritsFrom.layoutInformation, edges.EvalType.layoutInformation, edges.AliasOf.layoutInformation, edges.Ref.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("AST","REF")
    val In: Array[String] = Array("ALIAS_OF","EVAL_TYPE","INHERITS_FROM","REF")
  }

  val factory = new NodeFactory[TypeDb] {
    override val forLabel = Type.Label

    override def createNode(ref: NodeRef[TypeDb]) =
      new TypeDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Type(graph, id)
  }
}

trait TypeBase extends CpgNode  with HasFullName with HasName with HasTypeDeclFullName {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class Type(graph: Graph, id: Long) extends NodeRef[TypeDb](graph, id)
  with TypeBase
  with StoredNode
   {
    override def fullName: String = get().fullName
  override def name: String = get().name
  override def typeDeclFullName: String = get().typeDeclFullName
  
  def _typeDeclViaRefOut: Iterator[TypeDecl] = get()._typeDeclViaRefOut
override def _refOut: JIterator[StoredNode] = get()._refOut
def _typeArgumentViaAstOut: Iterator[TypeArgument] = get()._typeArgumentViaAstOut
override def _astOut: JIterator[StoredNode] = get()._astOut
def _typeDeclViaInheritsFromIn: Iterator[TypeDecl] = get()._typeDeclViaInheritsFromIn
override def _inheritsFromIn: JIterator[StoredNode] = get()._inheritsFromIn
def _identifierViaEvalTypeIn: Iterator[Identifier] = get()._identifierViaEvalTypeIn
def _typeRefViaEvalTypeIn: Iterator[TypeRef] = get()._typeRefViaEvalTypeIn
def _blockViaEvalTypeIn: Iterator[Block] = get()._blockViaEvalTypeIn
def _methodParameterInViaEvalTypeIn: Iterator[MethodParameterIn] = get()._methodParameterInViaEvalTypeIn
def _methodParameterOutViaEvalTypeIn: Iterator[MethodParameterOut] = get()._methodParameterOutViaEvalTypeIn
def _localViaEvalTypeIn: Iterator[Local] = get()._localViaEvalTypeIn
def _controlStructureViaEvalTypeIn: Iterator[ControlStructure] = get()._controlStructureViaEvalTypeIn
def _unknownViaEvalTypeIn: Iterator[Unknown] = get()._unknownViaEvalTypeIn
def _callViaEvalTypeIn: Iterator[Call] = get()._callViaEvalTypeIn
def _methodReturnViaEvalTypeIn: Iterator[MethodReturn] = get()._methodReturnViaEvalTypeIn
def _memberViaEvalTypeIn: Iterator[Member] = get()._memberViaEvalTypeIn
def _arrayInitializerViaEvalTypeIn: Iterator[ArrayInitializer] = get()._arrayInitializerViaEvalTypeIn
def _literalViaEvalTypeIn: Iterator[Literal] = get()._literalViaEvalTypeIn
def _methodRefViaEvalTypeIn: Iterator[MethodRef] = get()._methodRefViaEvalTypeIn
override def _evalTypeIn: JIterator[StoredNode] = get()._evalTypeIn
def _typeDeclViaAliasOfIn: Iterator[TypeDecl] = get()._typeDeclViaAliasOfIn
override def _aliasOfIn: JIterator[StoredNode] = get()._aliasOfIn
def _typeArgumentViaRefIn: Iterator[TypeArgument] = get()._typeArgumentViaRefIn
override def _refIn: JIterator[StoredNode] = get()._refIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Type.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "fullName" 
case 2 => "name" 
case 3 => "typeDeclFullName" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => fullName
case 2 => name
case 3 => typeDeclFullName
    }

  override def productPrefix = "Type"
  override def productArity = 4
}

class TypeDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with TypeBase {

  override def layoutInformation: NodeLayoutInformation = Type.layoutInformation

private var _fullName: String = null
def fullName: String = _fullName

private var _name: String = null
def name: String = _name

private var _typeDeclFullName: String = null
def typeDeclFullName: String = _typeDeclFullName


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (fullName != null) { properties.put("FULL_NAME", fullName) }
if (name != null) { properties.put("NAME", name) }
if (typeDeclFullName != null) { properties.put("TYPE_DECL_FULL_NAME", typeDeclFullName) }

  properties
}

  def _typeDeclViaRefOut: Iterator[TypeDecl] = _refOut.asScala.collect { case node: TypeDecl => node }
override def _refOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _typeArgumentViaAstOut: Iterator[TypeArgument] = _astOut.asScala.collect { case node: TypeArgument => node }
override def _astOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _typeDeclViaInheritsFromIn: Iterator[TypeDecl] = _inheritsFromIn.asScala.collect { case node: TypeDecl => node }
override def _inheritsFromIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]
def _identifierViaEvalTypeIn: Iterator[Identifier] = _evalTypeIn.asScala.collect { case node: Identifier => node }
def _typeRefViaEvalTypeIn: Iterator[TypeRef] = _evalTypeIn.asScala.collect { case node: TypeRef => node }
def _blockViaEvalTypeIn: Iterator[Block] = _evalTypeIn.asScala.collect { case node: Block => node }
def _methodParameterInViaEvalTypeIn: Iterator[MethodParameterIn] = _evalTypeIn.asScala.collect { case node: MethodParameterIn => node }
def _methodParameterOutViaEvalTypeIn: Iterator[MethodParameterOut] = _evalTypeIn.asScala.collect { case node: MethodParameterOut => node }
def _localViaEvalTypeIn: Iterator[Local] = _evalTypeIn.asScala.collect { case node: Local => node }
def _controlStructureViaEvalTypeIn: Iterator[ControlStructure] = _evalTypeIn.asScala.collect { case node: ControlStructure => node }
def _unknownViaEvalTypeIn: Iterator[Unknown] = _evalTypeIn.asScala.collect { case node: Unknown => node }
def _callViaEvalTypeIn: Iterator[Call] = _evalTypeIn.asScala.collect { case node: Call => node }
def _methodReturnViaEvalTypeIn: Iterator[MethodReturn] = _evalTypeIn.asScala.collect { case node: MethodReturn => node }
def _memberViaEvalTypeIn: Iterator[Member] = _evalTypeIn.asScala.collect { case node: Member => node }
def _arrayInitializerViaEvalTypeIn: Iterator[ArrayInitializer] = _evalTypeIn.asScala.collect { case node: ArrayInitializer => node }
def _literalViaEvalTypeIn: Iterator[Literal] = _evalTypeIn.asScala.collect { case node: Literal => node }
def _methodRefViaEvalTypeIn: Iterator[MethodRef] = _evalTypeIn.asScala.collect { case node: MethodRef => node }
override def _evalTypeIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(3).asInstanceOf[JIterator[StoredNode]]
def _typeDeclViaAliasOfIn: Iterator[TypeDecl] = _aliasOfIn.asScala.collect { case node: TypeDecl => node }
override def _aliasOfIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(4).asInstanceOf[JIterator[StoredNode]]
def _typeArgumentViaRefIn: Iterator[TypeArgument] = _refIn.asScala.collect { case node: TypeArgument => node }
override def _refIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(5).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    Type.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "fullName" 
case 2 => "name" 
case 3 => "typeDeclFullName" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => fullName
case 2 => name
case 3 => typeDeclFullName
    }

  override def productPrefix = "Type"
  override def productArity = 4

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TypeDb]

  override def property(key:String): AnyRef = {
    key match {
      case "FULL_NAME" => this._fullName
      case "NAME" => this._name
      case "TYPE_DECL_FULL_NAME" => this._typeDeclFullName
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "FULL_NAME" => this._fullName = value.asInstanceOf[String]
      case "NAME" => this._name = value.asInstanceOf[String]
      case "TYPE_DECL_FULL_NAME" => this._typeDeclFullName = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewType]
   this._fullName = other.fullName
   this._name = other.name
   this._typeDeclFullName = other.typeDeclFullName

  graph.indexManager.putIfIndexed("FULL_NAME", other.fullName, this.ref)
}

}

/** Traversal steps for Type */
class TypeTraversal[NodeType <: Type](val traversal: Traversal[NodeType]) extends AnyVal {

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



  /** Traverse to typeDeclFullName property */
  def typeDeclFullName: Traversal[String] =
    traversal.map(_.typeDeclFullName)

    /**
    * Traverse to nodes where the typeDeclFullName matches the regular expression `value`
    * */
  def typeDeclFullName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.typeDeclFullName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.typeDeclFullName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the typeDeclFullName matches at least one of the regular expressions in `values`
    * */
  def typeDeclFullName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.typeDeclFullName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where typeDeclFullName matches `value` exactly.
    * */
  def typeDeclFullNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.typeDeclFullName == value}

  /**
    * Traverse to nodes where typeDeclFullName matches one of the elements in `values` exactly.
    * */
  def typeDeclFullNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.typeDeclFullName)}
  }

  /**
    * Traverse to nodes where typeDeclFullName does not match the regular expression `value`.
    * */
  def typeDeclFullNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.typeDeclFullName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.typeDeclFullName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where typeDeclFullName does not match any of the regular expressions in `values`.
    * */
  def typeDeclFullNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.typeDeclFullName); matcher.matches()}}
   }




}
