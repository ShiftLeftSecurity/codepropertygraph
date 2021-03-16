package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object TypeDecl {
  def apply(graph: Graph, id: Long) = new TypeDecl(graph, id)

  val Label = "TYPE_DECL"

  object PropertyNames {
    val AliasTypeFullName = "ALIAS_TYPE_FULL_NAME" 
    val AstParentFullName = "AST_PARENT_FULL_NAME" 
    val AstParentType = "AST_PARENT_TYPE" 
    val Filename = "FILENAME" 
    val FullName = "FULL_NAME" 
    val InheritsFromTypeFullName = "INHERITS_FROM_TYPE_FULL_NAME" 
    val IsExternal = "IS_EXTERNAL" 
    val Name = "NAME" 
    val Order = "ORDER" 
    val all: Set[String] = Set(AliasTypeFullName, AstParentFullName, AstParentType, Filename, FullName, InheritsFromTypeFullName, IsExternal, Name, Order)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val AliasTypeFullName = new PropertyKey[String]("ALIAS_TYPE_FULL_NAME") 
    val AstParentFullName = new PropertyKey[String]("AST_PARENT_FULL_NAME") 
    val AstParentType = new PropertyKey[String]("AST_PARENT_TYPE") 
    val Filename = new PropertyKey[String]("FILENAME") 
    val FullName = new PropertyKey[String]("FULL_NAME") 
    val InheritsFromTypeFullName = new PropertyKey[Seq[String]]("INHERITS_FROM_TYPE_FULL_NAME") 
    val IsExternal = new PropertyKey[java.lang.Boolean]("IS_EXTERNAL") 
    val Name = new PropertyKey[String]("NAME") 
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.AliasOf.layoutInformation, edges.Ast.layoutInformation, edges.Contains.layoutInformation, edges.Vtable.layoutInformation, edges.SourceFile.layoutInformation, edges.TypeDeclAlias.layoutInformation, edges.Binds.layoutInformation, edges.InheritsFrom.layoutInformation).asJava,
    List(edges.Ref.layoutInformation, edges.Ast.layoutInformation, edges.Contains.layoutInformation, edges.TypeDeclAlias.layoutInformation, edges.DynamicType.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("ALIAS_OF","AST","BINDS","CONTAINS","INHERITS_FROM","SOURCE_FILE","TYPE_DECL_ALIAS","VTABLE")
    val In: Array[String] = Array("AST","CONTAINS","DYNAMIC_TYPE","REF","TYPE_DECL_ALIAS")
  }

  val factory = new NodeFactory[TypeDeclDb] {
    override val forLabel = TypeDecl.Label

    override def createNode(ref: NodeRef[TypeDeclDb]) =
      new TypeDeclDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = TypeDecl(graph, id)
  }
}

trait TypeDeclBase extends CpgNode with AstNodeBase with HasAliasTypeFullName with HasAstParentFullName with HasAstParentType with HasFilename with HasFullName with HasInheritsFromTypeFullName with HasIsExternal with HasName with HasOrder {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class TypeDecl(graph: Graph, id: Long) extends NodeRef[TypeDeclDb](graph, id)
  with TypeDeclBase
  with StoredNode
  with AstNode {
    override def aliasTypeFullName: Option[String] = get().aliasTypeFullName
  override def astParentFullName: String = get().astParentFullName
  override def astParentType: String = get().astParentType
  override def filename: String = get().filename
  override def fullName: String = get().fullName
  override def inheritsFromTypeFullName: List[String] = get().inheritsFromTypeFullName
  override def isExternal: java.lang.Boolean = get().isExternal
  override def name: String = get().name
  override def order: java.lang.Integer = get().order
  
  def _typeViaAliasOfOut: Iterator[Type] = get()._typeViaAliasOfOut
override def _aliasOfOut: JIterator[StoredNode] = get()._aliasOfOut
def _memberViaAstOut: Iterator[Member] = get()._memberViaAstOut
def _annotationViaAstOut: Iterator[Annotation] = get()._annotationViaAstOut
def _typeDeclViaAstOut: Iterator[TypeDecl] = get()._typeDeclViaAstOut
def _modifierViaAstOut: Iterator[Modifier] = get()._modifierViaAstOut
def _methodViaAstOut: Iterator[Method] = get()._methodViaAstOut
def _typeParameterViaAstOut: Iterator[TypeParameter] = get()._typeParameterViaAstOut
override def _astOut: JIterator[StoredNode] = get()._astOut
def _methodViaContainsOut: Iterator[Method] = get()._methodViaContainsOut
override def _containsOut: JIterator[StoredNode] = get()._containsOut
def _methodViaVtableOut: Iterator[Method] = get()._methodViaVtableOut
override def _vtableOut: JIterator[StoredNode] = get()._vtableOut
def _fileViaSourceFileOut: Iterator[File] = get()._fileViaSourceFileOut
override def _sourceFileOut: JIterator[StoredNode] = get()._sourceFileOut
def _typeDeclViaTypeDeclAliasOut: Iterator[TypeDecl] = get()._typeDeclViaTypeDeclAliasOut
override def _typeDeclAliasOut: JIterator[StoredNode] = get()._typeDeclAliasOut
def _bindingViaBindsOut: Iterator[Binding] = get()._bindingViaBindsOut
override def _bindsOut: JIterator[StoredNode] = get()._bindsOut
def _typeViaInheritsFromOut: Iterator[Type] = get()._typeViaInheritsFromOut
override def _inheritsFromOut: JIterator[StoredNode] = get()._inheritsFromOut
def _typeViaRefIn: Iterator[Type] = get()._typeViaRefIn
override def _refIn: JIterator[StoredNode] = get()._refIn
def _methodViaAstIn: Option[Method] = get()._methodViaAstIn
def _namespaceBlockViaAstIn: Option[NamespaceBlock] = get()._namespaceBlockViaAstIn
def _typeDeclViaAstIn: Option[TypeDecl] = get()._typeDeclViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
def _fileViaContainsIn: Iterator[File] = get()._fileViaContainsIn
override def _containsIn: JIterator[StoredNode] = get()._containsIn
def _typeDeclViaTypeDeclAliasIn: Iterator[TypeDecl] = get()._typeDeclViaTypeDeclAliasIn
override def _typeDeclAliasIn: JIterator[StoredNode] = get()._typeDeclAliasIn
def _methodReturnViaDynamicTypeIn: Iterator[MethodReturn] = get()._methodReturnViaDynamicTypeIn
def _callViaDynamicTypeIn: Iterator[Call] = get()._callViaDynamicTypeIn
def _methodRefViaDynamicTypeIn: Iterator[MethodRef] = get()._methodRefViaDynamicTypeIn
def _localViaDynamicTypeIn: Iterator[Local] = get()._localViaDynamicTypeIn
def _literalViaDynamicTypeIn: Iterator[Literal] = get()._literalViaDynamicTypeIn
def _memberViaDynamicTypeIn: Iterator[Member] = get()._memberViaDynamicTypeIn
def _fieldIdentifierViaDynamicTypeIn: Iterator[FieldIdentifier] = get()._fieldIdentifierViaDynamicTypeIn
def _blockViaDynamicTypeIn: Iterator[Block] = get()._blockViaDynamicTypeIn
def _identifierViaDynamicTypeIn: Iterator[Identifier] = get()._identifierViaDynamicTypeIn
def _unknownViaDynamicTypeIn: Iterator[Unknown] = get()._unknownViaDynamicTypeIn
def _typeRefViaDynamicTypeIn: Iterator[TypeRef] = get()._typeRefViaDynamicTypeIn
def _controlStructureViaDynamicTypeIn: Iterator[ControlStructure] = get()._controlStructureViaDynamicTypeIn
def _methodParameterInViaDynamicTypeIn: Iterator[MethodParameterIn] = get()._methodParameterInViaDynamicTypeIn
override def _dynamicTypeIn: JIterator[StoredNode] = get()._dynamicTypeIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    TypeDecl.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "aliasTypeFullName" 
case 2 => "astParentFullName" 
case 3 => "astParentType" 
case 4 => "filename" 
case 5 => "fullName" 
case 6 => "inheritsFromTypeFullName" 
case 7 => "isExternal" 
case 8 => "name" 
case 9 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => aliasTypeFullName
case 2 => astParentFullName
case 3 => astParentType
case 4 => filename
case 5 => fullName
case 6 => inheritsFromTypeFullName
case 7 => isExternal
case 8 => name
case 9 => order
    }

  override def productPrefix = "TypeDecl"
  override def productArity = 10
}

class TypeDeclDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with AstNode with TypeDeclBase {

  override def layoutInformation: NodeLayoutInformation = TypeDecl.layoutInformation

private var _aliasTypeFullName: Option[String] = None
def aliasTypeFullName: Option[String] = _aliasTypeFullName

private var _astParentFullName: String = null
def astParentFullName: String = _astParentFullName

private var _astParentType: String = null
def astParentType: String = _astParentType

private var _filename: String = null
def filename: String = _filename

private var _fullName: String = null
def fullName: String = _fullName

private var _inheritsFromTypeFullName: List[String] = Nil
def inheritsFromTypeFullName: List[String] = _inheritsFromTypeFullName

private var _isExternal: java.lang.Boolean = null
def isExternal: java.lang.Boolean = _isExternal

private var _name: String = null
def name: String = _name

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
aliasTypeFullName.map { value => properties.put("ALIAS_TYPE_FULL_NAME", value) }
if (astParentFullName != null) { properties.put("AST_PARENT_FULL_NAME", astParentFullName) }
if (astParentType != null) { properties.put("AST_PARENT_TYPE", astParentType) }
if (filename != null) { properties.put("FILENAME", filename) }
if (fullName != null) { properties.put("FULL_NAME", fullName) }
if (this._inheritsFromTypeFullName != null && this._inheritsFromTypeFullName.nonEmpty) { properties.put("INHERITS_FROM_TYPE_FULL_NAME", inheritsFromTypeFullName.asJava) }
if (isExternal != null) { properties.put("IS_EXTERNAL", isExternal) }
if (name != null) { properties.put("NAME", name) }
if (order != null) { properties.put("ORDER", order) }

  properties
}

  def _typeViaAliasOfOut: Iterator[Type] = _aliasOfOut.asScala.collect { case node: Type => node }
override def _aliasOfOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _memberViaAstOut: Iterator[Member] = _astOut.asScala.collect { case node: Member => node }
def _annotationViaAstOut: Iterator[Annotation] = _astOut.asScala.collect { case node: Annotation => node }
def _typeDeclViaAstOut: Iterator[TypeDecl] = _astOut.asScala.collect { case node: TypeDecl => node }
def _modifierViaAstOut: Iterator[Modifier] = _astOut.asScala.collect { case node: Modifier => node }
def _methodViaAstOut: Iterator[Method] = _astOut.asScala.collect { case node: Method => node }
def _typeParameterViaAstOut: Iterator[TypeParameter] = _astOut.asScala.collect { case node: TypeParameter => node }
override def _astOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _methodViaContainsOut: Iterator[Method] = _containsOut.asScala.collect { case node: Method => node }
override def _containsOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]
def _methodViaVtableOut: Iterator[Method] = _vtableOut.asScala.collect { case node: Method => node }
override def _vtableOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(3).asInstanceOf[JIterator[StoredNode]]
def _fileViaSourceFileOut: Iterator[File] = _sourceFileOut.asScala.collect { case node: File => node }
override def _sourceFileOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(4).asInstanceOf[JIterator[StoredNode]]
def _typeDeclViaTypeDeclAliasOut: Iterator[TypeDecl] = _typeDeclAliasOut.asScala.collect { case node: TypeDecl => node }
override def _typeDeclAliasOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(5).asInstanceOf[JIterator[StoredNode]]
def _bindingViaBindsOut: Iterator[Binding] = _bindsOut.asScala.collect { case node: Binding => node }
override def _bindsOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(6).asInstanceOf[JIterator[StoredNode]]
def _typeViaInheritsFromOut: Iterator[Type] = _inheritsFromOut.asScala.collect { case node: Type => node }
override def _inheritsFromOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(7).asInstanceOf[JIterator[StoredNode]]
def _typeViaRefIn: Iterator[Type] = _refIn.asScala.collect { case node: Type => node }
override def _refIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(8).asInstanceOf[JIterator[StoredNode]]
def _methodViaAstIn: Option[Method] = _astIn.asScala.collect { case node: Method => node }.nextOption()
def _namespaceBlockViaAstIn: Option[NamespaceBlock] = _astIn.asScala.collect { case node: NamespaceBlock => node }.nextOption()
def _typeDeclViaAstIn: Option[TypeDecl] = _astIn.asScala.collect { case node: TypeDecl => node }.nextOption()
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(9).asInstanceOf[JIterator[StoredNode]]
def _fileViaContainsIn: Iterator[File] = _containsIn.asScala.collect { case node: File => node }
override def _containsIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(10).asInstanceOf[JIterator[StoredNode]]
def _typeDeclViaTypeDeclAliasIn: Iterator[TypeDecl] = _typeDeclAliasIn.asScala.collect { case node: TypeDecl => node }
override def _typeDeclAliasIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(11).asInstanceOf[JIterator[StoredNode]]
def _methodReturnViaDynamicTypeIn: Iterator[MethodReturn] = _dynamicTypeIn.asScala.collect { case node: MethodReturn => node }
def _callViaDynamicTypeIn: Iterator[Call] = _dynamicTypeIn.asScala.collect { case node: Call => node }
def _methodRefViaDynamicTypeIn: Iterator[MethodRef] = _dynamicTypeIn.asScala.collect { case node: MethodRef => node }
def _localViaDynamicTypeIn: Iterator[Local] = _dynamicTypeIn.asScala.collect { case node: Local => node }
def _literalViaDynamicTypeIn: Iterator[Literal] = _dynamicTypeIn.asScala.collect { case node: Literal => node }
def _memberViaDynamicTypeIn: Iterator[Member] = _dynamicTypeIn.asScala.collect { case node: Member => node }
def _fieldIdentifierViaDynamicTypeIn: Iterator[FieldIdentifier] = _dynamicTypeIn.asScala.collect { case node: FieldIdentifier => node }
def _blockViaDynamicTypeIn: Iterator[Block] = _dynamicTypeIn.asScala.collect { case node: Block => node }
def _identifierViaDynamicTypeIn: Iterator[Identifier] = _dynamicTypeIn.asScala.collect { case node: Identifier => node }
def _unknownViaDynamicTypeIn: Iterator[Unknown] = _dynamicTypeIn.asScala.collect { case node: Unknown => node }
def _typeRefViaDynamicTypeIn: Iterator[TypeRef] = _dynamicTypeIn.asScala.collect { case node: TypeRef => node }
def _controlStructureViaDynamicTypeIn: Iterator[ControlStructure] = _dynamicTypeIn.asScala.collect { case node: ControlStructure => node }
def _methodParameterInViaDynamicTypeIn: Iterator[MethodParameterIn] = _dynamicTypeIn.asScala.collect { case node: MethodParameterIn => node }
override def _dynamicTypeIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(12).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    TypeDecl.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "aliasTypeFullName" 
case 2 => "astParentFullName" 
case 3 => "astParentType" 
case 4 => "filename" 
case 5 => "fullName" 
case 6 => "inheritsFromTypeFullName" 
case 7 => "isExternal" 
case 8 => "name" 
case 9 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => aliasTypeFullName
case 2 => astParentFullName
case 3 => astParentType
case 4 => filename
case 5 => fullName
case 6 => inheritsFromTypeFullName
case 7 => isExternal
case 8 => name
case 9 => order
    }

  override def productPrefix = "TypeDecl"
  override def productArity = 10

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TypeDeclDb]

  override def property(key:String): AnyRef = {
    key match {
      case "ALIAS_TYPE_FULL_NAME" => this._aliasTypeFullName.orNull
      case "AST_PARENT_FULL_NAME" => this._astParentFullName
      case "AST_PARENT_TYPE" => this._astParentType
      case "FILENAME" => this._filename
      case "FULL_NAME" => this._fullName
      case "INHERITS_FROM_TYPE_FULL_NAME" => this._inheritsFromTypeFullName
      case "IS_EXTERNAL" => this._isExternal
      case "NAME" => this._name
      case "ORDER" => this._order
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "ALIAS_TYPE_FULL_NAME" => this._aliasTypeFullName = value match {
        case null | None => None
        case someVal: String => Some(someVal)
      }
      case "AST_PARENT_FULL_NAME" => this._astParentFullName = value.asInstanceOf[String]
      case "AST_PARENT_TYPE" => this._astParentType = value.asInstanceOf[String]
      case "FILENAME" => this._filename = value.asInstanceOf[String]
      case "FULL_NAME" => this._fullName = value.asInstanceOf[String]
      case "INHERITS_FROM_TYPE_FULL_NAME" => this._inheritsFromTypeFullName = value match {
        case singleValue: String => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[String]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[String]]
      }
      case "IS_EXTERNAL" => this._isExternal = value.asInstanceOf[java.lang.Boolean]
      case "NAME" => this._name = value.asInstanceOf[String]
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewTypeDecl]
   this._aliasTypeFullName = if(other.aliasTypeFullName != null) other.aliasTypeFullName else None
   this._astParentFullName = other.astParentFullName
   this._astParentType = other.astParentType
   this._filename = other.filename
   this._fullName = other.fullName
   this._inheritsFromTypeFullName = if(other.inheritsFromTypeFullName != null) other.inheritsFromTypeFullName else Nil
   this._isExternal = other.isExternal
   this._name = other.name
   this._order = other.order

  graph.indexManager.putIfIndexed("FULL_NAME", other.fullName, this.ref)
}

}

/** Traversal steps for TypeDecl */
class TypeDeclTraversal[NodeType <: TypeDecl](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to aliasTypeFullName property */
  def aliasTypeFullName: Traversal[String] =
    traversal.flatMap(_.aliasTypeFullName)

    /**
    * Traverse to nodes where the aliasTypeFullName matches the regular expression `value`
    * */
  def aliasTypeFullName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.aliasTypeFullName.isDefined && node.aliasTypeFullName.get == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node => node.aliasTypeFullName.isDefined && {matcher.reset(node.aliasTypeFullName.get); matcher.matches()}}
    }
  }

  /**
    * Traverse to nodes where the aliasTypeFullName matches at least one of the regular expressions in `values`
    * */
  def aliasTypeFullName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => node.aliasTypeFullName.isDefined && matchers.exists{ matcher => matcher.reset(node.aliasTypeFullName.get); matcher.matches()}}
   }

  /**
    * Traverse to nodes where aliasTypeFullName matches `value` exactly.
    * */
  def aliasTypeFullNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.aliasTypeFullName.isDefined && node.aliasTypeFullName.get == value}

  /**
    * Traverse to nodes where aliasTypeFullName matches one of the elements in `values` exactly.
    * */
  def aliasTypeFullNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => node.aliasTypeFullName.isDefined && vset.contains(node.aliasTypeFullName.get)}
  }

  /**
    * Traverse to nodes where aliasTypeFullName does not match the regular expression `value`.
    * */
  def aliasTypeFullNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.aliasTypeFullName.isEmpty || node.aliasTypeFullName.get != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node => node.aliasTypeFullName.isEmpty || {matcher.reset(node.aliasTypeFullName.get); !matcher.matches()}}
    }
  }

  /**
    * Traverse to nodes where aliasTypeFullName does not match any of the regular expressions in `values`.
    * */
  def aliasTypeFullNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => node.aliasTypeFullName.isEmpty || !matchers.exists{ matcher => matcher.reset(node.aliasTypeFullName.get); matcher.matches()}}
   }



  /** Traverse to astParentFullName property */
  def astParentFullName: Traversal[String] =
    traversal.map(_.astParentFullName)

    /**
    * Traverse to nodes where the astParentFullName matches the regular expression `value`
    * */
  def astParentFullName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.astParentFullName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.astParentFullName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the astParentFullName matches at least one of the regular expressions in `values`
    * */
  def astParentFullName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.astParentFullName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where astParentFullName matches `value` exactly.
    * */
  def astParentFullNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.astParentFullName == value}

  /**
    * Traverse to nodes where astParentFullName matches one of the elements in `values` exactly.
    * */
  def astParentFullNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.astParentFullName)}
  }

  /**
    * Traverse to nodes where astParentFullName does not match the regular expression `value`.
    * */
  def astParentFullNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.astParentFullName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.astParentFullName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where astParentFullName does not match any of the regular expressions in `values`.
    * */
  def astParentFullNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.astParentFullName); matcher.matches()}}
   }



  /** Traverse to astParentType property */
  def astParentType: Traversal[String] =
    traversal.map(_.astParentType)

    /**
    * Traverse to nodes where the astParentType matches the regular expression `value`
    * */
  def astParentType(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.astParentType == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.astParentType); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the astParentType matches at least one of the regular expressions in `values`
    * */
  def astParentType(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.astParentType); matcher.matches()}}
   }

  /**
    * Traverse to nodes where astParentType matches `value` exactly.
    * */
  def astParentTypeExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.astParentType == value}

  /**
    * Traverse to nodes where astParentType matches one of the elements in `values` exactly.
    * */
  def astParentTypeExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.astParentType)}
  }

  /**
    * Traverse to nodes where astParentType does not match the regular expression `value`.
    * */
  def astParentTypeNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.astParentType != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.astParentType); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where astParentType does not match any of the regular expressions in `values`.
    * */
  def astParentTypeNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.astParentType); matcher.matches()}}
   }



  /** Traverse to filename property */
  def filename: Traversal[String] =
    traversal.map(_.filename)

    /**
    * Traverse to nodes where the filename matches the regular expression `value`
    * */
  def filename(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.filename == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.filename); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the filename matches at least one of the regular expressions in `values`
    * */
  def filename(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.filename); matcher.matches()}}
   }

  /**
    * Traverse to nodes where filename matches `value` exactly.
    * */
  def filenameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.filename == value}

  /**
    * Traverse to nodes where filename matches one of the elements in `values` exactly.
    * */
  def filenameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.filename)}
  }

  /**
    * Traverse to nodes where filename does not match the regular expression `value`.
    * */
  def filenameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.filename != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.filename); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where filename does not match any of the regular expressions in `values`.
    * */
  def filenameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.filename); matcher.matches()}}
   }



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



  /** Traverse to inheritsFromTypeFullName property */
  def inheritsFromTypeFullName: Traversal[String] =
    traversal.flatMap(_.inheritsFromTypeFullName)

  

  /** Traverse to isExternal property */
  def isExternal: Traversal[java.lang.Boolean] =
    traversal.map(_.isExternal)

    /**
    * Traverse to nodes where the isExternal equals the given `value`
    * */
  def isExternal(value: java.lang.Boolean): Traversal[NodeType] =
    traversal.filter{_.isExternal == value}

  /**
    * Traverse to nodes where isExternal is not equal to the given `value`.
    * */
  def isExternalNot(value: java.lang.Boolean): Traversal[NodeType] =
    traversal.filter{_.isExternal != value}


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



}
