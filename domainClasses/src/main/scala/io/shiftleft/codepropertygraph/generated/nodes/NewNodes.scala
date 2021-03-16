package io.shiftleft.codepropertygraph.generated.nodes

/** base type for all nodes that can be added to a graph, e.g. the diffgraph */
trait NewNode extends CpgNode {
  def properties: Map[String, Any]
}

trait NewNodeBuilder {
  def id : Long
  def id(x: Long) : NewNodeBuilder
  def build : NewNode
}


object NewMetaDataBuilder {
  def apply() : NewMetaDataBuilder = new NewMetaDataBuilder()
}

class NewMetaDataBuilder extends NewNodeBuilder {
   var result : NewMetaData = new NewMetaData()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewMetaDataBuilder = { _id = x; this }

   def hash(x : Option[String]) : NewMetaDataBuilder = { result = result.copy(hash = x); this }
def language(x : String) : NewMetaDataBuilder = { result = result.copy(language = x); this }
def overlays(x : List[String]) : NewMetaDataBuilder = { result = result.copy(overlays = x); this }
def policyDirectories(x : List[String]) : NewMetaDataBuilder = { result = result.copy(policyDirectories = x); this }
def version(x : String) : NewMetaDataBuilder = { result = result.copy(version = x); this }

   def build : NewMetaData = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewMetaDataBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewMetaDataBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewMetaDataBuilder(${_id})"
}

object NewMetaData{
  def apply() : NewMetaDataBuilder = NewMetaDataBuilder()
  private def apply(version: String = "", policyDirectories: List[String] = List(), overlays: List[String] = List(), language: String = "", hash: Option[String] = None): NewMetaData = new NewMetaData(version = version, policyDirectories = policyDirectories, overlays = overlays, language = language, hash = hash)
}

case class NewMetaData private[nodes] (var version: String = "", var policyDirectories: List[String] = List(), var overlays: List[String] = List(), var language: String = "", var hash: Option[String] = None) extends NewNode with MetaDataBase {
  override def label:String = "META_DATA"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (hash != null && hash.isDefined) { res += "HASH" -> hash.get }
  if (language != null) { res += "LANGUAGE" -> language }
  if (overlays != null && overlays.nonEmpty) { res += "OVERLAYS" -> overlays }
  if (policyDirectories != null && policyDirectories.nonEmpty) { res += "POLICY_DIRECTORIES" -> policyDirectories }
  if (version != null) { res += "VERSION" -> version }

  res
}
}


object NewFileBuilder {
  def apply() : NewFileBuilder = new NewFileBuilder()
}

class NewFileBuilder extends NewNodeBuilder {
   var result : NewFile = new NewFile()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewFileBuilder = { _id = x; this }

   def hash(x : Option[String]) : NewFileBuilder = { result = result.copy(hash = x); this }
def name(x : String) : NewFileBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewFileBuilder = { result = result.copy(order = x); this }

   def build : NewFile = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewFileBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewFileBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewFileBuilder(${_id})"
}

object NewFile{
  def apply() : NewFileBuilder = NewFileBuilder()
  private def apply(order: java.lang.Integer = -1, name: String = "", hash: Option[String] = None): NewFile = new NewFile(order = order, name = name, hash = hash)
}

case class NewFile private[nodes] (var order: java.lang.Integer = -1, var name: String = "", var hash: Option[String] = None) extends NewNode with FileBase {
  override def label:String = "FILE"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (hash != null && hash.isDefined) { res += "HASH" -> hash.get }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewMethodBuilder {
  def apply() : NewMethodBuilder = new NewMethodBuilder()
}

class NewMethodBuilder extends NewNodeBuilder {
   var result : NewMethod = new NewMethod()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewMethodBuilder = { _id = x; this }

   def astParentFullName(x : String) : NewMethodBuilder = { result = result.copy(astParentFullName = x); this }
def astParentType(x : String) : NewMethodBuilder = { result = result.copy(astParentType = x); this }
def binarySignature(x : Option[String]) : NewMethodBuilder = { result = result.copy(binarySignature = x); this }
def code(x : String) : NewMethodBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewMethodBuilder = { result = result.copy(columnNumber = x); this }
def columnNumberEnd(x : Option[java.lang.Integer]) : NewMethodBuilder = { result = result.copy(columnNumberEnd = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewMethodBuilder = { result = result.copy(depthFirstOrder = x); this }
def filename(x : String) : NewMethodBuilder = { result = result.copy(filename = x); this }
def fullName(x : String) : NewMethodBuilder = { result = result.copy(fullName = x); this }
def hasMapping(x : Option[java.lang.Boolean]) : NewMethodBuilder = { result = result.copy(hasMapping = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewMethodBuilder = { result = result.copy(internalFlags = x); this }
def isExternal(x : java.lang.Boolean) : NewMethodBuilder = { result = result.copy(isExternal = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewMethodBuilder = { result = result.copy(lineNumber = x); this }
def lineNumberEnd(x : Option[java.lang.Integer]) : NewMethodBuilder = { result = result.copy(lineNumberEnd = x); this }
def name(x : String) : NewMethodBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewMethodBuilder = { result = result.copy(order = x); this }
def signature(x : String) : NewMethodBuilder = { result = result.copy(signature = x); this }

   def build : NewMethod = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewMethodBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewMethodBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewMethodBuilder(${_id})"
}

object NewMethod{
  def apply() : NewMethodBuilder = NewMethodBuilder()
  private def apply(signature: String = "", order: java.lang.Integer = -1, name: String = "", lineNumberEnd: Option[java.lang.Integer] = None, lineNumber: Option[java.lang.Integer] = None, isExternal: java.lang.Boolean = false, internalFlags: Option[java.lang.Integer] = None, hasMapping: Option[java.lang.Boolean] = None, fullName: String = "", filename: String = "", depthFirstOrder: Option[java.lang.Integer] = None, columnNumberEnd: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", binarySignature: Option[String] = None, astParentType: String = "", astParentFullName: String = ""): NewMethod = new NewMethod(signature = signature, order = order, name = name, lineNumberEnd = lineNumberEnd, lineNumber = lineNumber, isExternal = isExternal, internalFlags = internalFlags, hasMapping = hasMapping, fullName = fullName, filename = filename, depthFirstOrder = depthFirstOrder, columnNumberEnd = columnNumberEnd, columnNumber = columnNumber, code = code, binarySignature = binarySignature, astParentType = astParentType, astParentFullName = astParentFullName)
}

case class NewMethod private[nodes] (var signature: String = "", var order: java.lang.Integer = -1, var name: String = "", var lineNumberEnd: Option[java.lang.Integer] = None, var lineNumber: Option[java.lang.Integer] = None, var isExternal: java.lang.Boolean = false, var internalFlags: Option[java.lang.Integer] = None, var hasMapping: Option[java.lang.Boolean] = None, var fullName: String = "", var filename: String = "", var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumberEnd: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var binarySignature: Option[String] = None, var astParentType: String = "", var astParentFullName: String = "") extends NewNode with MethodBase {
  override def label:String = "METHOD"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (astParentFullName != null) { res += "AST_PARENT_FULL_NAME" -> astParentFullName }
  if (astParentType != null) { res += "AST_PARENT_TYPE" -> astParentType }
  if (binarySignature != null && binarySignature.isDefined) { res += "BINARY_SIGNATURE" -> binarySignature.get }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (columnNumberEnd != null && columnNumberEnd.isDefined) { res += "COLUMN_NUMBER_END" -> columnNumberEnd.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (filename != null) { res += "FILENAME" -> filename }
  if (fullName != null) { res += "FULL_NAME" -> fullName }
  if (hasMapping != null && hasMapping.isDefined) { res += "HAS_MAPPING" -> hasMapping.get }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (isExternal != null) { res += "IS_EXTERNAL" -> isExternal }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (lineNumberEnd != null && lineNumberEnd.isDefined) { res += "LINE_NUMBER_END" -> lineNumberEnd.get }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }
  if (signature != null) { res += "SIGNATURE" -> signature }

  res
}
}


object NewMethodParameterInBuilder {
  def apply() : NewMethodParameterInBuilder = new NewMethodParameterInBuilder()
}

class NewMethodParameterInBuilder extends NewNodeBuilder {
   var result : NewMethodParameterIn = new NewMethodParameterIn()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewMethodParameterInBuilder = { _id = x; this }

   def code(x : String) : NewMethodParameterInBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewMethodParameterInBuilder = { result = result.copy(columnNumber = x); this }
def dynamicTypeHintFullName(x : List[String]) : NewMethodParameterInBuilder = { result = result.copy(dynamicTypeHintFullName = x); this }
def evaluationStrategy(x : String) : NewMethodParameterInBuilder = { result = result.copy(evaluationStrategy = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewMethodParameterInBuilder = { result = result.copy(lineNumber = x); this }
def name(x : String) : NewMethodParameterInBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewMethodParameterInBuilder = { result = result.copy(order = x); this }
def typeFullName(x : String) : NewMethodParameterInBuilder = { result = result.copy(typeFullName = x); this }

   def build : NewMethodParameterIn = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewMethodParameterInBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewMethodParameterInBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewMethodParameterInBuilder(${_id})"
}

object NewMethodParameterIn{
  def apply() : NewMethodParameterInBuilder = NewMethodParameterInBuilder()
  private def apply(typeFullName: String = "", order: java.lang.Integer = -1, name: String = "", lineNumber: Option[java.lang.Integer] = None, evaluationStrategy: String = "", dynamicTypeHintFullName: List[String] = List(), columnNumber: Option[java.lang.Integer] = None, code: String = ""): NewMethodParameterIn = new NewMethodParameterIn(typeFullName = typeFullName, order = order, name = name, lineNumber = lineNumber, evaluationStrategy = evaluationStrategy, dynamicTypeHintFullName = dynamicTypeHintFullName, columnNumber = columnNumber, code = code)
}

case class NewMethodParameterIn private[nodes] (var typeFullName: String = "", var order: java.lang.Integer = -1, var name: String = "", var lineNumber: Option[java.lang.Integer] = None, var evaluationStrategy: String = "", var dynamicTypeHintFullName: List[String] = List(), var columnNumber: Option[java.lang.Integer] = None, var code: String = "") extends NewNode with MethodParameterInBase {
  override def label:String = "METHOD_PARAMETER_IN"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (dynamicTypeHintFullName != null && dynamicTypeHintFullName.nonEmpty) { res += "DYNAMIC_TYPE_HINT_FULL_NAME" -> dynamicTypeHintFullName }
  if (evaluationStrategy != null) { res += "EVALUATION_STRATEGY" -> evaluationStrategy }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }
  if (typeFullName != null) { res += "TYPE_FULL_NAME" -> typeFullName }

  res
}
}


object NewMethodReturnBuilder {
  def apply() : NewMethodReturnBuilder = new NewMethodReturnBuilder()
}

class NewMethodReturnBuilder extends NewNodeBuilder {
   var result : NewMethodReturn = new NewMethodReturn()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewMethodReturnBuilder = { _id = x; this }

   def code(x : String) : NewMethodReturnBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewMethodReturnBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewMethodReturnBuilder = { result = result.copy(depthFirstOrder = x); this }
def dynamicTypeHintFullName(x : List[String]) : NewMethodReturnBuilder = { result = result.copy(dynamicTypeHintFullName = x); this }
def evaluationStrategy(x : String) : NewMethodReturnBuilder = { result = result.copy(evaluationStrategy = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewMethodReturnBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewMethodReturnBuilder = { result = result.copy(lineNumber = x); this }
def order(x : java.lang.Integer) : NewMethodReturnBuilder = { result = result.copy(order = x); this }
def typeFullName(x : String) : NewMethodReturnBuilder = { result = result.copy(typeFullName = x); this }

   def build : NewMethodReturn = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewMethodReturnBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewMethodReturnBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewMethodReturnBuilder(${_id})"
}

object NewMethodReturn{
  def apply() : NewMethodReturnBuilder = NewMethodReturnBuilder()
  private def apply(typeFullName: String = "", order: java.lang.Integer = -1, lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, evaluationStrategy: String = "", dynamicTypeHintFullName: List[String] = List(), depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = ""): NewMethodReturn = new NewMethodReturn(typeFullName = typeFullName, order = order, lineNumber = lineNumber, internalFlags = internalFlags, evaluationStrategy = evaluationStrategy, dynamicTypeHintFullName = dynamicTypeHintFullName, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code)
}

case class NewMethodReturn private[nodes] (var typeFullName: String = "", var order: java.lang.Integer = -1, var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var evaluationStrategy: String = "", var dynamicTypeHintFullName: List[String] = List(), var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "") extends NewNode with MethodReturnBase {
  override def label:String = "METHOD_RETURN"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (dynamicTypeHintFullName != null && dynamicTypeHintFullName.nonEmpty) { res += "DYNAMIC_TYPE_HINT_FULL_NAME" -> dynamicTypeHintFullName }
  if (evaluationStrategy != null) { res += "EVALUATION_STRATEGY" -> evaluationStrategy }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (order != null) { res += "ORDER" -> order }
  if (typeFullName != null) { res += "TYPE_FULL_NAME" -> typeFullName }

  res
}
}


object NewModifierBuilder {
  def apply() : NewModifierBuilder = new NewModifierBuilder()
}

class NewModifierBuilder extends NewNodeBuilder {
   var result : NewModifier = new NewModifier()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewModifierBuilder = { _id = x; this }

   def modifierType(x : String) : NewModifierBuilder = { result = result.copy(modifierType = x); this }
def order(x : java.lang.Integer) : NewModifierBuilder = { result = result.copy(order = x); this }

   def build : NewModifier = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewModifierBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewModifierBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewModifierBuilder(${_id})"
}

object NewModifier{
  def apply() : NewModifierBuilder = NewModifierBuilder()
  private def apply(order: java.lang.Integer = -1, modifierType: String = ""): NewModifier = new NewModifier(order = order, modifierType = modifierType)
}

case class NewModifier private[nodes] (var order: java.lang.Integer = -1, var modifierType: String = "") extends NewNode with ModifierBase {
  override def label:String = "MODIFIER"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (modifierType != null) { res += "MODIFIER_TYPE" -> modifierType }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewTypeBuilder {
  def apply() : NewTypeBuilder = new NewTypeBuilder()
}

class NewTypeBuilder extends NewNodeBuilder {
   var result : NewType = new NewType()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewTypeBuilder = { _id = x; this }

   def fullName(x : String) : NewTypeBuilder = { result = result.copy(fullName = x); this }
def name(x : String) : NewTypeBuilder = { result = result.copy(name = x); this }
def typeDeclFullName(x : String) : NewTypeBuilder = { result = result.copy(typeDeclFullName = x); this }

   def build : NewType = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewTypeBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewTypeBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewTypeBuilder(${_id})"
}

object NewType{
  def apply() : NewTypeBuilder = NewTypeBuilder()
  private def apply(typeDeclFullName: String = "", name: String = "", fullName: String = ""): NewType = new NewType(typeDeclFullName = typeDeclFullName, name = name, fullName = fullName)
}

case class NewType private[nodes] (var typeDeclFullName: String = "", var name: String = "", var fullName: String = "") extends NewNode with TypeBase {
  override def label:String = "TYPE"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (fullName != null) { res += "FULL_NAME" -> fullName }
  if (name != null) { res += "NAME" -> name }
  if (typeDeclFullName != null) { res += "TYPE_DECL_FULL_NAME" -> typeDeclFullName }

  res
}
}


object NewTypeDeclBuilder {
  def apply() : NewTypeDeclBuilder = new NewTypeDeclBuilder()
}

class NewTypeDeclBuilder extends NewNodeBuilder {
   var result : NewTypeDecl = new NewTypeDecl()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewTypeDeclBuilder = { _id = x; this }

   def aliasTypeFullName(x : Option[String]) : NewTypeDeclBuilder = { result = result.copy(aliasTypeFullName = x); this }
def astParentFullName(x : String) : NewTypeDeclBuilder = { result = result.copy(astParentFullName = x); this }
def astParentType(x : String) : NewTypeDeclBuilder = { result = result.copy(astParentType = x); this }
def filename(x : String) : NewTypeDeclBuilder = { result = result.copy(filename = x); this }
def fullName(x : String) : NewTypeDeclBuilder = { result = result.copy(fullName = x); this }
def inheritsFromTypeFullName(x : List[String]) : NewTypeDeclBuilder = { result = result.copy(inheritsFromTypeFullName = x); this }
def isExternal(x : java.lang.Boolean) : NewTypeDeclBuilder = { result = result.copy(isExternal = x); this }
def name(x : String) : NewTypeDeclBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewTypeDeclBuilder = { result = result.copy(order = x); this }

   def build : NewTypeDecl = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewTypeDeclBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewTypeDeclBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewTypeDeclBuilder(${_id})"
}

object NewTypeDecl{
  def apply() : NewTypeDeclBuilder = NewTypeDeclBuilder()
  private def apply(order: java.lang.Integer = -1, name: String = "", isExternal: java.lang.Boolean = false, inheritsFromTypeFullName: List[String] = List(), fullName: String = "", filename: String = "", astParentType: String = "", astParentFullName: String = "", aliasTypeFullName: Option[String] = None): NewTypeDecl = new NewTypeDecl(order = order, name = name, isExternal = isExternal, inheritsFromTypeFullName = inheritsFromTypeFullName, fullName = fullName, filename = filename, astParentType = astParentType, astParentFullName = astParentFullName, aliasTypeFullName = aliasTypeFullName)
}

case class NewTypeDecl private[nodes] (var order: java.lang.Integer = -1, var name: String = "", var isExternal: java.lang.Boolean = false, var inheritsFromTypeFullName: List[String] = List(), var fullName: String = "", var filename: String = "", var astParentType: String = "", var astParentFullName: String = "", var aliasTypeFullName: Option[String] = None) extends NewNode with TypeDeclBase {
  override def label:String = "TYPE_DECL"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (aliasTypeFullName != null && aliasTypeFullName.isDefined) { res += "ALIAS_TYPE_FULL_NAME" -> aliasTypeFullName.get }
  if (astParentFullName != null) { res += "AST_PARENT_FULL_NAME" -> astParentFullName }
  if (astParentType != null) { res += "AST_PARENT_TYPE" -> astParentType }
  if (filename != null) { res += "FILENAME" -> filename }
  if (fullName != null) { res += "FULL_NAME" -> fullName }
  if (inheritsFromTypeFullName != null && inheritsFromTypeFullName.nonEmpty) { res += "INHERITS_FROM_TYPE_FULL_NAME" -> inheritsFromTypeFullName }
  if (isExternal != null) { res += "IS_EXTERNAL" -> isExternal }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewTypeParameterBuilder {
  def apply() : NewTypeParameterBuilder = new NewTypeParameterBuilder()
}

class NewTypeParameterBuilder extends NewNodeBuilder {
   var result : NewTypeParameter = new NewTypeParameter()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewTypeParameterBuilder = { _id = x; this }

   def name(x : String) : NewTypeParameterBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewTypeParameterBuilder = { result = result.copy(order = x); this }

   def build : NewTypeParameter = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewTypeParameterBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewTypeParameterBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewTypeParameterBuilder(${_id})"
}

object NewTypeParameter{
  def apply() : NewTypeParameterBuilder = NewTypeParameterBuilder()
  private def apply(order: java.lang.Integer = -1, name: String = ""): NewTypeParameter = new NewTypeParameter(order = order, name = name)
}

case class NewTypeParameter private[nodes] (var order: java.lang.Integer = -1, var name: String = "") extends NewNode with TypeParameterBase {
  override def label:String = "TYPE_PARAMETER"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewTypeArgumentBuilder {
  def apply() : NewTypeArgumentBuilder = new NewTypeArgumentBuilder()
}

class NewTypeArgumentBuilder extends NewNodeBuilder {
   var result : NewTypeArgument = new NewTypeArgument()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewTypeArgumentBuilder = { _id = x; this }

   def order(x : java.lang.Integer) : NewTypeArgumentBuilder = { result = result.copy(order = x); this }

   def build : NewTypeArgument = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewTypeArgumentBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewTypeArgumentBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewTypeArgumentBuilder(${_id})"
}

object NewTypeArgument{
  def apply() : NewTypeArgumentBuilder = NewTypeArgumentBuilder()
  private def apply(order: java.lang.Integer = -1): NewTypeArgument = new NewTypeArgument(order = order)
}

case class NewTypeArgument private[nodes] (var order: java.lang.Integer = -1) extends NewNode with TypeArgumentBase {
  override def label:String = "TYPE_ARGUMENT"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewMemberBuilder {
  def apply() : NewMemberBuilder = new NewMemberBuilder()
}

class NewMemberBuilder extends NewNodeBuilder {
   var result : NewMember = new NewMember()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewMemberBuilder = { _id = x; this }

   def code(x : String) : NewMemberBuilder = { result = result.copy(code = x); this }
def dynamicTypeHintFullName(x : List[String]) : NewMemberBuilder = { result = result.copy(dynamicTypeHintFullName = x); this }
def name(x : String) : NewMemberBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewMemberBuilder = { result = result.copy(order = x); this }
def typeFullName(x : String) : NewMemberBuilder = { result = result.copy(typeFullName = x); this }

   def build : NewMember = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewMemberBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewMemberBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewMemberBuilder(${_id})"
}

object NewMember{
  def apply() : NewMemberBuilder = NewMemberBuilder()
  private def apply(typeFullName: String = "", order: java.lang.Integer = -1, name: String = "", dynamicTypeHintFullName: List[String] = List(), code: String = ""): NewMember = new NewMember(typeFullName = typeFullName, order = order, name = name, dynamicTypeHintFullName = dynamicTypeHintFullName, code = code)
}

case class NewMember private[nodes] (var typeFullName: String = "", var order: java.lang.Integer = -1, var name: String = "", var dynamicTypeHintFullName: List[String] = List(), var code: String = "") extends NewNode with MemberBase {
  override def label:String = "MEMBER"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (code != null) { res += "CODE" -> code }
  if (dynamicTypeHintFullName != null && dynamicTypeHintFullName.nonEmpty) { res += "DYNAMIC_TYPE_HINT_FULL_NAME" -> dynamicTypeHintFullName }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }
  if (typeFullName != null) { res += "TYPE_FULL_NAME" -> typeFullName }

  res
}
}


object NewNamespaceBlockBuilder {
  def apply() : NewNamespaceBlockBuilder = new NewNamespaceBlockBuilder()
}

class NewNamespaceBlockBuilder extends NewNodeBuilder {
   var result : NewNamespaceBlock = new NewNamespaceBlock()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewNamespaceBlockBuilder = { _id = x; this }

   def filename(x : String) : NewNamespaceBlockBuilder = { result = result.copy(filename = x); this }
def fullName(x : String) : NewNamespaceBlockBuilder = { result = result.copy(fullName = x); this }
def name(x : String) : NewNamespaceBlockBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewNamespaceBlockBuilder = { result = result.copy(order = x); this }

   def build : NewNamespaceBlock = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewNamespaceBlockBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewNamespaceBlockBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewNamespaceBlockBuilder(${_id})"
}

object NewNamespaceBlock{
  def apply() : NewNamespaceBlockBuilder = NewNamespaceBlockBuilder()
  private def apply(order: java.lang.Integer = -1, name: String = "", fullName: String = "", filename: String = ""): NewNamespaceBlock = new NewNamespaceBlock(order = order, name = name, fullName = fullName, filename = filename)
}

case class NewNamespaceBlock private[nodes] (var order: java.lang.Integer = -1, var name: String = "", var fullName: String = "", var filename: String = "") extends NewNode with NamespaceBlockBase {
  override def label:String = "NAMESPACE_BLOCK"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (filename != null) { res += "FILENAME" -> filename }
  if (fullName != null) { res += "FULL_NAME" -> fullName }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewLiteralBuilder {
  def apply() : NewLiteralBuilder = new NewLiteralBuilder()
}

class NewLiteralBuilder extends NewNodeBuilder {
   var result : NewLiteral = new NewLiteral()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewLiteralBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewLiteralBuilder = { result = result.copy(argumentIndex = x); this }
def code(x : String) : NewLiteralBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewLiteralBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewLiteralBuilder = { result = result.copy(depthFirstOrder = x); this }
def dynamicTypeHintFullName(x : List[String]) : NewLiteralBuilder = { result = result.copy(dynamicTypeHintFullName = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewLiteralBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewLiteralBuilder = { result = result.copy(lineNumber = x); this }
def order(x : java.lang.Integer) : NewLiteralBuilder = { result = result.copy(order = x); this }
def typeFullName(x : String) : NewLiteralBuilder = { result = result.copy(typeFullName = x); this }

   def build : NewLiteral = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewLiteralBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewLiteralBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewLiteralBuilder(${_id})"
}

object NewLiteral{
  def apply() : NewLiteralBuilder = NewLiteralBuilder()
  private def apply(typeFullName: String = "", order: java.lang.Integer = -1, lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, dynamicTypeHintFullName: List[String] = List(), depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", argumentIndex: java.lang.Integer = -1): NewLiteral = new NewLiteral(typeFullName = typeFullName, order = order, lineNumber = lineNumber, internalFlags = internalFlags, dynamicTypeHintFullName = dynamicTypeHintFullName, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code, argumentIndex = argumentIndex)
}

case class NewLiteral private[nodes] (var typeFullName: String = "", var order: java.lang.Integer = -1, var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var dynamicTypeHintFullName: List[String] = List(), var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with LiteralBase {
  override def label:String = "LITERAL"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (dynamicTypeHintFullName != null && dynamicTypeHintFullName.nonEmpty) { res += "DYNAMIC_TYPE_HINT_FULL_NAME" -> dynamicTypeHintFullName }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (order != null) { res += "ORDER" -> order }
  if (typeFullName != null) { res += "TYPE_FULL_NAME" -> typeFullName }

  res
}
}


object NewCallBuilder {
  def apply() : NewCallBuilder = new NewCallBuilder()
}

class NewCallBuilder extends NewNodeBuilder {
   var result : NewCall = new NewCall()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewCallBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewCallBuilder = { result = result.copy(argumentIndex = x); this }
def code(x : String) : NewCallBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewCallBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewCallBuilder = { result = result.copy(depthFirstOrder = x); this }
def dispatchType(x : String) : NewCallBuilder = { result = result.copy(dispatchType = x); this }
def dynamicTypeHintFullName(x : List[String]) : NewCallBuilder = { result = result.copy(dynamicTypeHintFullName = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewCallBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewCallBuilder = { result = result.copy(lineNumber = x); this }
def methodFullName(x : String) : NewCallBuilder = { result = result.copy(methodFullName = x); this }
def methodInstFullName(x : Option[String]) : NewCallBuilder = { result = result.copy(methodInstFullName = x); this }
def name(x : String) : NewCallBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewCallBuilder = { result = result.copy(order = x); this }
def signature(x : String) : NewCallBuilder = { result = result.copy(signature = x); this }
def typeFullName(x : String) : NewCallBuilder = { result = result.copy(typeFullName = x); this }

   def build : NewCall = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewCallBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewCallBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewCallBuilder(${_id})"
}

object NewCall{
  def apply() : NewCallBuilder = NewCallBuilder()
  private def apply(typeFullName: String = "", signature: String = "", order: java.lang.Integer = -1, name: String = "", methodInstFullName: Option[String] = None, methodFullName: String = "", lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, dynamicTypeHintFullName: List[String] = List(), dispatchType: String = "", depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", argumentIndex: java.lang.Integer = -1): NewCall = new NewCall(typeFullName = typeFullName, signature = signature, order = order, name = name, methodInstFullName = methodInstFullName, methodFullName = methodFullName, lineNumber = lineNumber, internalFlags = internalFlags, dynamicTypeHintFullName = dynamicTypeHintFullName, dispatchType = dispatchType, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code, argumentIndex = argumentIndex)
}

case class NewCall private[nodes] (var typeFullName: String = "", var signature: String = "", var order: java.lang.Integer = -1, var name: String = "", var methodInstFullName: Option[String] = None, var methodFullName: String = "", var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var dynamicTypeHintFullName: List[String] = List(), var dispatchType: String = "", var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with CallBase {
  override def label:String = "CALL"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (dispatchType != null) { res += "DISPATCH_TYPE" -> dispatchType }
  if (dynamicTypeHintFullName != null && dynamicTypeHintFullName.nonEmpty) { res += "DYNAMIC_TYPE_HINT_FULL_NAME" -> dynamicTypeHintFullName }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (methodFullName != null) { res += "METHOD_FULL_NAME" -> methodFullName }
  if (methodInstFullName != null && methodInstFullName.isDefined) { res += "METHOD_INST_FULL_NAME" -> methodInstFullName.get }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }
  if (signature != null) { res += "SIGNATURE" -> signature }
  if (typeFullName != null) { res += "TYPE_FULL_NAME" -> typeFullName }

  res
}
}


object NewLocalBuilder {
  def apply() : NewLocalBuilder = new NewLocalBuilder()
}

class NewLocalBuilder extends NewNodeBuilder {
   var result : NewLocal = new NewLocal()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewLocalBuilder = { _id = x; this }

   def closureBindingId(x : Option[String]) : NewLocalBuilder = { result = result.copy(closureBindingId = x); this }
def code(x : String) : NewLocalBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewLocalBuilder = { result = result.copy(columnNumber = x); this }
def dynamicTypeHintFullName(x : List[String]) : NewLocalBuilder = { result = result.copy(dynamicTypeHintFullName = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewLocalBuilder = { result = result.copy(lineNumber = x); this }
def name(x : String) : NewLocalBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewLocalBuilder = { result = result.copy(order = x); this }
def typeFullName(x : String) : NewLocalBuilder = { result = result.copy(typeFullName = x); this }

   def build : NewLocal = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewLocalBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewLocalBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewLocalBuilder(${_id})"
}

object NewLocal{
  def apply() : NewLocalBuilder = NewLocalBuilder()
  private def apply(typeFullName: String = "", order: java.lang.Integer = -1, name: String = "", lineNumber: Option[java.lang.Integer] = None, dynamicTypeHintFullName: List[String] = List(), columnNumber: Option[java.lang.Integer] = None, code: String = "", closureBindingId: Option[String] = None): NewLocal = new NewLocal(typeFullName = typeFullName, order = order, name = name, lineNumber = lineNumber, dynamicTypeHintFullName = dynamicTypeHintFullName, columnNumber = columnNumber, code = code, closureBindingId = closureBindingId)
}

case class NewLocal private[nodes] (var typeFullName: String = "", var order: java.lang.Integer = -1, var name: String = "", var lineNumber: Option[java.lang.Integer] = None, var dynamicTypeHintFullName: List[String] = List(), var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var closureBindingId: Option[String] = None) extends NewNode with LocalBase {
  override def label:String = "LOCAL"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (closureBindingId != null && closureBindingId.isDefined) { res += "CLOSURE_BINDING_ID" -> closureBindingId.get }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (dynamicTypeHintFullName != null && dynamicTypeHintFullName.nonEmpty) { res += "DYNAMIC_TYPE_HINT_FULL_NAME" -> dynamicTypeHintFullName }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }
  if (typeFullName != null) { res += "TYPE_FULL_NAME" -> typeFullName }

  res
}
}


object NewIdentifierBuilder {
  def apply() : NewIdentifierBuilder = new NewIdentifierBuilder()
}

class NewIdentifierBuilder extends NewNodeBuilder {
   var result : NewIdentifier = new NewIdentifier()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewIdentifierBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewIdentifierBuilder = { result = result.copy(argumentIndex = x); this }
def code(x : String) : NewIdentifierBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewIdentifierBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewIdentifierBuilder = { result = result.copy(depthFirstOrder = x); this }
def dynamicTypeHintFullName(x : List[String]) : NewIdentifierBuilder = { result = result.copy(dynamicTypeHintFullName = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewIdentifierBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewIdentifierBuilder = { result = result.copy(lineNumber = x); this }
def name(x : String) : NewIdentifierBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewIdentifierBuilder = { result = result.copy(order = x); this }
def typeFullName(x : String) : NewIdentifierBuilder = { result = result.copy(typeFullName = x); this }

   def build : NewIdentifier = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewIdentifierBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewIdentifierBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewIdentifierBuilder(${_id})"
}

object NewIdentifier{
  def apply() : NewIdentifierBuilder = NewIdentifierBuilder()
  private def apply(typeFullName: String = "", order: java.lang.Integer = -1, name: String = "", lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, dynamicTypeHintFullName: List[String] = List(), depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", argumentIndex: java.lang.Integer = -1): NewIdentifier = new NewIdentifier(typeFullName = typeFullName, order = order, name = name, lineNumber = lineNumber, internalFlags = internalFlags, dynamicTypeHintFullName = dynamicTypeHintFullName, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code, argumentIndex = argumentIndex)
}

case class NewIdentifier private[nodes] (var typeFullName: String = "", var order: java.lang.Integer = -1, var name: String = "", var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var dynamicTypeHintFullName: List[String] = List(), var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with IdentifierBase {
  override def label:String = "IDENTIFIER"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (dynamicTypeHintFullName != null && dynamicTypeHintFullName.nonEmpty) { res += "DYNAMIC_TYPE_HINT_FULL_NAME" -> dynamicTypeHintFullName }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }
  if (typeFullName != null) { res += "TYPE_FULL_NAME" -> typeFullName }

  res
}
}


object NewFieldIdentifierBuilder {
  def apply() : NewFieldIdentifierBuilder = new NewFieldIdentifierBuilder()
}

class NewFieldIdentifierBuilder extends NewNodeBuilder {
   var result : NewFieldIdentifier = new NewFieldIdentifier()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewFieldIdentifierBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewFieldIdentifierBuilder = { result = result.copy(argumentIndex = x); this }
def canonicalName(x : String) : NewFieldIdentifierBuilder = { result = result.copy(canonicalName = x); this }
def code(x : String) : NewFieldIdentifierBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewFieldIdentifierBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewFieldIdentifierBuilder = { result = result.copy(depthFirstOrder = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewFieldIdentifierBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewFieldIdentifierBuilder = { result = result.copy(lineNumber = x); this }
def order(x : java.lang.Integer) : NewFieldIdentifierBuilder = { result = result.copy(order = x); this }

   def build : NewFieldIdentifier = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewFieldIdentifierBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewFieldIdentifierBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewFieldIdentifierBuilder(${_id})"
}

object NewFieldIdentifier{
  def apply() : NewFieldIdentifierBuilder = NewFieldIdentifierBuilder()
  private def apply(order: java.lang.Integer = -1, lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", canonicalName: String = "", argumentIndex: java.lang.Integer = -1): NewFieldIdentifier = new NewFieldIdentifier(order = order, lineNumber = lineNumber, internalFlags = internalFlags, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code, canonicalName = canonicalName, argumentIndex = argumentIndex)
}

case class NewFieldIdentifier private[nodes] (var order: java.lang.Integer = -1, var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var canonicalName: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with FieldIdentifierBase {
  override def label:String = "FIELD_IDENTIFIER"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (canonicalName != null) { res += "CANONICAL_NAME" -> canonicalName }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewReturnBuilder {
  def apply() : NewReturnBuilder = new NewReturnBuilder()
}

class NewReturnBuilder extends NewNodeBuilder {
   var result : NewReturn = new NewReturn()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewReturnBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewReturnBuilder = { result = result.copy(argumentIndex = x); this }
def code(x : String) : NewReturnBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewReturnBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewReturnBuilder = { result = result.copy(depthFirstOrder = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewReturnBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewReturnBuilder = { result = result.copy(lineNumber = x); this }
def order(x : java.lang.Integer) : NewReturnBuilder = { result = result.copy(order = x); this }

   def build : NewReturn = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewReturnBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewReturnBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewReturnBuilder(${_id})"
}

object NewReturn{
  def apply() : NewReturnBuilder = NewReturnBuilder()
  private def apply(order: java.lang.Integer = -1, lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", argumentIndex: java.lang.Integer = -1): NewReturn = new NewReturn(order = order, lineNumber = lineNumber, internalFlags = internalFlags, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code, argumentIndex = argumentIndex)
}

case class NewReturn private[nodes] (var order: java.lang.Integer = -1, var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with ReturnBase {
  override def label:String = "RETURN"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewBlockBuilder {
  def apply() : NewBlockBuilder = new NewBlockBuilder()
}

class NewBlockBuilder extends NewNodeBuilder {
   var result : NewBlock = new NewBlock()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewBlockBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewBlockBuilder = { result = result.copy(argumentIndex = x); this }
def code(x : String) : NewBlockBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewBlockBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewBlockBuilder = { result = result.copy(depthFirstOrder = x); this }
def dynamicTypeHintFullName(x : List[String]) : NewBlockBuilder = { result = result.copy(dynamicTypeHintFullName = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewBlockBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewBlockBuilder = { result = result.copy(lineNumber = x); this }
def order(x : java.lang.Integer) : NewBlockBuilder = { result = result.copy(order = x); this }
def typeFullName(x : String) : NewBlockBuilder = { result = result.copy(typeFullName = x); this }

   def build : NewBlock = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewBlockBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewBlockBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewBlockBuilder(${_id})"
}

object NewBlock{
  def apply() : NewBlockBuilder = NewBlockBuilder()
  private def apply(typeFullName: String = "", order: java.lang.Integer = -1, lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, dynamicTypeHintFullName: List[String] = List(), depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", argumentIndex: java.lang.Integer = -1): NewBlock = new NewBlock(typeFullName = typeFullName, order = order, lineNumber = lineNumber, internalFlags = internalFlags, dynamicTypeHintFullName = dynamicTypeHintFullName, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code, argumentIndex = argumentIndex)
}

case class NewBlock private[nodes] (var typeFullName: String = "", var order: java.lang.Integer = -1, var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var dynamicTypeHintFullName: List[String] = List(), var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with BlockBase {
  override def label:String = "BLOCK"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (dynamicTypeHintFullName != null && dynamicTypeHintFullName.nonEmpty) { res += "DYNAMIC_TYPE_HINT_FULL_NAME" -> dynamicTypeHintFullName }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (order != null) { res += "ORDER" -> order }
  if (typeFullName != null) { res += "TYPE_FULL_NAME" -> typeFullName }

  res
}
}


object NewMethodInstBuilder {
  def apply() : NewMethodInstBuilder = new NewMethodInstBuilder()
}

class NewMethodInstBuilder extends NewNodeBuilder {
   var result : NewMethodInst = new NewMethodInst()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewMethodInstBuilder = { _id = x; this }

   def fullName(x : String) : NewMethodInstBuilder = { result = result.copy(fullName = x); this }
def methodFullName(x : String) : NewMethodInstBuilder = { result = result.copy(methodFullName = x); this }
def name(x : String) : NewMethodInstBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewMethodInstBuilder = { result = result.copy(order = x); this }
def signature(x : String) : NewMethodInstBuilder = { result = result.copy(signature = x); this }

   def build : NewMethodInst = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewMethodInstBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewMethodInstBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewMethodInstBuilder(${_id})"
}

object NewMethodInst{
  def apply() : NewMethodInstBuilder = NewMethodInstBuilder()
  private def apply(signature: String = "", order: java.lang.Integer = -1, name: String = "", methodFullName: String = "", fullName: String = ""): NewMethodInst = new NewMethodInst(signature = signature, order = order, name = name, methodFullName = methodFullName, fullName = fullName)
}

case class NewMethodInst private[nodes] (var signature: String = "", var order: java.lang.Integer = -1, var name: String = "", var methodFullName: String = "", var fullName: String = "") extends NewNode with MethodInstBase {
  override def label:String = "METHOD_INST"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (fullName != null) { res += "FULL_NAME" -> fullName }
  if (methodFullName != null) { res += "METHOD_FULL_NAME" -> methodFullName }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }
  if (signature != null) { res += "SIGNATURE" -> signature }

  res
}
}


object NewArrayInitializerBuilder {
  def apply() : NewArrayInitializerBuilder = new NewArrayInitializerBuilder()
}

class NewArrayInitializerBuilder extends NewNodeBuilder {
   var result : NewArrayInitializer = new NewArrayInitializer()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewArrayInitializerBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewArrayInitializerBuilder = { result = result.copy(argumentIndex = x); this }
def code(x : String) : NewArrayInitializerBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewArrayInitializerBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewArrayInitializerBuilder = { result = result.copy(depthFirstOrder = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewArrayInitializerBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewArrayInitializerBuilder = { result = result.copy(lineNumber = x); this }
def order(x : java.lang.Integer) : NewArrayInitializerBuilder = { result = result.copy(order = x); this }

   def build : NewArrayInitializer = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewArrayInitializerBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewArrayInitializerBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewArrayInitializerBuilder(${_id})"
}

object NewArrayInitializer{
  def apply() : NewArrayInitializerBuilder = NewArrayInitializerBuilder()
  private def apply(order: java.lang.Integer = -1, lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", argumentIndex: java.lang.Integer = -1): NewArrayInitializer = new NewArrayInitializer(order = order, lineNumber = lineNumber, internalFlags = internalFlags, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code, argumentIndex = argumentIndex)
}

case class NewArrayInitializer private[nodes] (var order: java.lang.Integer = -1, var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with ArrayInitializerBase {
  override def label:String = "ARRAY_INITIALIZER"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewMethodRefBuilder {
  def apply() : NewMethodRefBuilder = new NewMethodRefBuilder()
}

class NewMethodRefBuilder extends NewNodeBuilder {
   var result : NewMethodRef = new NewMethodRef()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewMethodRefBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewMethodRefBuilder = { result = result.copy(argumentIndex = x); this }
def code(x : String) : NewMethodRefBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewMethodRefBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewMethodRefBuilder = { result = result.copy(depthFirstOrder = x); this }
def dynamicTypeHintFullName(x : List[String]) : NewMethodRefBuilder = { result = result.copy(dynamicTypeHintFullName = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewMethodRefBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewMethodRefBuilder = { result = result.copy(lineNumber = x); this }
def methodFullName(x : String) : NewMethodRefBuilder = { result = result.copy(methodFullName = x); this }
def methodInstFullName(x : Option[String]) : NewMethodRefBuilder = { result = result.copy(methodInstFullName = x); this }
def order(x : java.lang.Integer) : NewMethodRefBuilder = { result = result.copy(order = x); this }
def typeFullName(x : String) : NewMethodRefBuilder = { result = result.copy(typeFullName = x); this }

   def build : NewMethodRef = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewMethodRefBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewMethodRefBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewMethodRefBuilder(${_id})"
}

object NewMethodRef{
  def apply() : NewMethodRefBuilder = NewMethodRefBuilder()
  private def apply(typeFullName: String = "", order: java.lang.Integer = -1, methodInstFullName: Option[String] = None, methodFullName: String = "", lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, dynamicTypeHintFullName: List[String] = List(), depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", argumentIndex: java.lang.Integer = -1): NewMethodRef = new NewMethodRef(typeFullName = typeFullName, order = order, methodInstFullName = methodInstFullName, methodFullName = methodFullName, lineNumber = lineNumber, internalFlags = internalFlags, dynamicTypeHintFullName = dynamicTypeHintFullName, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code, argumentIndex = argumentIndex)
}

case class NewMethodRef private[nodes] (var typeFullName: String = "", var order: java.lang.Integer = -1, var methodInstFullName: Option[String] = None, var methodFullName: String = "", var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var dynamicTypeHintFullName: List[String] = List(), var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with MethodRefBase {
  override def label:String = "METHOD_REF"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (dynamicTypeHintFullName != null && dynamicTypeHintFullName.nonEmpty) { res += "DYNAMIC_TYPE_HINT_FULL_NAME" -> dynamicTypeHintFullName }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (methodFullName != null) { res += "METHOD_FULL_NAME" -> methodFullName }
  if (methodInstFullName != null && methodInstFullName.isDefined) { res += "METHOD_INST_FULL_NAME" -> methodInstFullName.get }
  if (order != null) { res += "ORDER" -> order }
  if (typeFullName != null) { res += "TYPE_FULL_NAME" -> typeFullName }

  res
}
}


object NewTypeRefBuilder {
  def apply() : NewTypeRefBuilder = new NewTypeRefBuilder()
}

class NewTypeRefBuilder extends NewNodeBuilder {
   var result : NewTypeRef = new NewTypeRef()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewTypeRefBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewTypeRefBuilder = { result = result.copy(argumentIndex = x); this }
def code(x : String) : NewTypeRefBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewTypeRefBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewTypeRefBuilder = { result = result.copy(depthFirstOrder = x); this }
def dynamicTypeHintFullName(x : List[String]) : NewTypeRefBuilder = { result = result.copy(dynamicTypeHintFullName = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewTypeRefBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewTypeRefBuilder = { result = result.copy(lineNumber = x); this }
def order(x : java.lang.Integer) : NewTypeRefBuilder = { result = result.copy(order = x); this }
def typeFullName(x : String) : NewTypeRefBuilder = { result = result.copy(typeFullName = x); this }

   def build : NewTypeRef = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewTypeRefBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewTypeRefBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewTypeRefBuilder(${_id})"
}

object NewTypeRef{
  def apply() : NewTypeRefBuilder = NewTypeRefBuilder()
  private def apply(typeFullName: String = "", order: java.lang.Integer = -1, lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, dynamicTypeHintFullName: List[String] = List(), depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", argumentIndex: java.lang.Integer = -1): NewTypeRef = new NewTypeRef(typeFullName = typeFullName, order = order, lineNumber = lineNumber, internalFlags = internalFlags, dynamicTypeHintFullName = dynamicTypeHintFullName, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code, argumentIndex = argumentIndex)
}

case class NewTypeRef private[nodes] (var typeFullName: String = "", var order: java.lang.Integer = -1, var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var dynamicTypeHintFullName: List[String] = List(), var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with TypeRefBase {
  override def label:String = "TYPE_REF"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (dynamicTypeHintFullName != null && dynamicTypeHintFullName.nonEmpty) { res += "DYNAMIC_TYPE_HINT_FULL_NAME" -> dynamicTypeHintFullName }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (order != null) { res += "ORDER" -> order }
  if (typeFullName != null) { res += "TYPE_FULL_NAME" -> typeFullName }

  res
}
}


object NewControlStructureBuilder {
  def apply() : NewControlStructureBuilder = new NewControlStructureBuilder()
}

class NewControlStructureBuilder extends NewNodeBuilder {
   var result : NewControlStructure = new NewControlStructure()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewControlStructureBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewControlStructureBuilder = { result = result.copy(argumentIndex = x); this }
def code(x : String) : NewControlStructureBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewControlStructureBuilder = { result = result.copy(columnNumber = x); this }
def controlStructureType(x : String) : NewControlStructureBuilder = { result = result.copy(controlStructureType = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewControlStructureBuilder = { result = result.copy(depthFirstOrder = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewControlStructureBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewControlStructureBuilder = { result = result.copy(lineNumber = x); this }
def order(x : java.lang.Integer) : NewControlStructureBuilder = { result = result.copy(order = x); this }
def parserTypeName(x : String) : NewControlStructureBuilder = { result = result.copy(parserTypeName = x); this }

   def build : NewControlStructure = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewControlStructureBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewControlStructureBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewControlStructureBuilder(${_id})"
}

object NewControlStructure{
  def apply() : NewControlStructureBuilder = NewControlStructureBuilder()
  private def apply(parserTypeName: String = "", order: java.lang.Integer = -1, lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, depthFirstOrder: Option[java.lang.Integer] = None, controlStructureType: String = "", columnNumber: Option[java.lang.Integer] = None, code: String = "", argumentIndex: java.lang.Integer = -1): NewControlStructure = new NewControlStructure(parserTypeName = parserTypeName, order = order, lineNumber = lineNumber, internalFlags = internalFlags, depthFirstOrder = depthFirstOrder, controlStructureType = controlStructureType, columnNumber = columnNumber, code = code, argumentIndex = argumentIndex)
}

case class NewControlStructure private[nodes] (var parserTypeName: String = "", var order: java.lang.Integer = -1, var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var depthFirstOrder: Option[java.lang.Integer] = None, var controlStructureType: String = "", var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with ControlStructureBase {
  override def label:String = "CONTROL_STRUCTURE"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (controlStructureType != null) { res += "CONTROL_STRUCTURE_TYPE" -> controlStructureType }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (order != null) { res += "ORDER" -> order }
  if (parserTypeName != null) { res += "PARSER_TYPE_NAME" -> parserTypeName }

  res
}
}


object NewJumpTargetBuilder {
  def apply() : NewJumpTargetBuilder = new NewJumpTargetBuilder()
}

class NewJumpTargetBuilder extends NewNodeBuilder {
   var result : NewJumpTarget = new NewJumpTarget()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewJumpTargetBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewJumpTargetBuilder = { result = result.copy(argumentIndex = x); this }
def code(x : String) : NewJumpTargetBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewJumpTargetBuilder = { result = result.copy(columnNumber = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewJumpTargetBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewJumpTargetBuilder = { result = result.copy(lineNumber = x); this }
def name(x : String) : NewJumpTargetBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewJumpTargetBuilder = { result = result.copy(order = x); this }
def parserTypeName(x : String) : NewJumpTargetBuilder = { result = result.copy(parserTypeName = x); this }

   def build : NewJumpTarget = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewJumpTargetBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewJumpTargetBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewJumpTargetBuilder(${_id})"
}

object NewJumpTarget{
  def apply() : NewJumpTargetBuilder = NewJumpTargetBuilder()
  private def apply(parserTypeName: String = "", order: java.lang.Integer = -1, name: String = "", lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", argumentIndex: java.lang.Integer = -1): NewJumpTarget = new NewJumpTarget(parserTypeName = parserTypeName, order = order, name = name, lineNumber = lineNumber, internalFlags = internalFlags, columnNumber = columnNumber, code = code, argumentIndex = argumentIndex)
}

case class NewJumpTarget private[nodes] (var parserTypeName: String = "", var order: java.lang.Integer = -1, var name: String = "", var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with JumpTargetBase {
  override def label:String = "JUMP_TARGET"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }
  if (parserTypeName != null) { res += "PARSER_TYPE_NAME" -> parserTypeName }

  res
}
}


object NewUnknownBuilder {
  def apply() : NewUnknownBuilder = new NewUnknownBuilder()
}

class NewUnknownBuilder extends NewNodeBuilder {
   var result : NewUnknown = new NewUnknown()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewUnknownBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewUnknownBuilder = { result = result.copy(argumentIndex = x); this }
def code(x : String) : NewUnknownBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewUnknownBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewUnknownBuilder = { result = result.copy(depthFirstOrder = x); this }
def dynamicTypeHintFullName(x : List[String]) : NewUnknownBuilder = { result = result.copy(dynamicTypeHintFullName = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewUnknownBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewUnknownBuilder = { result = result.copy(lineNumber = x); this }
def order(x : java.lang.Integer) : NewUnknownBuilder = { result = result.copy(order = x); this }
def parserTypeName(x : String) : NewUnknownBuilder = { result = result.copy(parserTypeName = x); this }
def typeFullName(x : String) : NewUnknownBuilder = { result = result.copy(typeFullName = x); this }

   def build : NewUnknown = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewUnknownBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewUnknownBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewUnknownBuilder(${_id})"
}

object NewUnknown{
  def apply() : NewUnknownBuilder = NewUnknownBuilder()
  private def apply(typeFullName: String = "", parserTypeName: String = "", order: java.lang.Integer = -1, lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, dynamicTypeHintFullName: List[String] = List(), depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", argumentIndex: java.lang.Integer = -1): NewUnknown = new NewUnknown(typeFullName = typeFullName, parserTypeName = parserTypeName, order = order, lineNumber = lineNumber, internalFlags = internalFlags, dynamicTypeHintFullName = dynamicTypeHintFullName, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code, argumentIndex = argumentIndex)
}

case class NewUnknown private[nodes] (var typeFullName: String = "", var parserTypeName: String = "", var order: java.lang.Integer = -1, var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var dynamicTypeHintFullName: List[String] = List(), var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with UnknownBase {
  override def label:String = "UNKNOWN"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (dynamicTypeHintFullName != null && dynamicTypeHintFullName.nonEmpty) { res += "DYNAMIC_TYPE_HINT_FULL_NAME" -> dynamicTypeHintFullName }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (order != null) { res += "ORDER" -> order }
  if (parserTypeName != null) { res += "PARSER_TYPE_NAME" -> parserTypeName }
  if (typeFullName != null) { res += "TYPE_FULL_NAME" -> typeFullName }

  res
}
}


object NewBindingBuilder {
  def apply() : NewBindingBuilder = new NewBindingBuilder()
}

class NewBindingBuilder extends NewNodeBuilder {
   var result : NewBinding = new NewBinding()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewBindingBuilder = { _id = x; this }

   def isMethodNeverOverridden(x : Option[java.lang.Boolean]) : NewBindingBuilder = { result = result.copy(isMethodNeverOverridden = x); this }
def name(x : String) : NewBindingBuilder = { result = result.copy(name = x); this }
def signature(x : String) : NewBindingBuilder = { result = result.copy(signature = x); this }

   def build : NewBinding = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewBindingBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewBindingBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewBindingBuilder(${_id})"
}

object NewBinding{
  def apply() : NewBindingBuilder = NewBindingBuilder()
  private def apply(signature: String = "", name: String = "", isMethodNeverOverridden: Option[java.lang.Boolean] = None): NewBinding = new NewBinding(signature = signature, name = name, isMethodNeverOverridden = isMethodNeverOverridden)
}

case class NewBinding private[nodes] (var signature: String = "", var name: String = "", var isMethodNeverOverridden: Option[java.lang.Boolean] = None) extends NewNode with BindingBase {
  override def label:String = "BINDING"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (isMethodNeverOverridden != null && isMethodNeverOverridden.isDefined) { res += "IS_METHOD_NEVER_OVERRIDDEN" -> isMethodNeverOverridden.get }
  if (name != null) { res += "NAME" -> name }
  if (signature != null) { res += "SIGNATURE" -> signature }

  res
}
}


object NewImplicitCallBuilder {
  def apply() : NewImplicitCallBuilder = new NewImplicitCallBuilder()
}

class NewImplicitCallBuilder extends NewNodeBuilder {
   var result : NewImplicitCall = new NewImplicitCall()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewImplicitCallBuilder = { _id = x; this }

   def code(x : String) : NewImplicitCallBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewImplicitCallBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewImplicitCallBuilder = { result = result.copy(depthFirstOrder = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewImplicitCallBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewImplicitCallBuilder = { result = result.copy(lineNumber = x); this }
def name(x : String) : NewImplicitCallBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewImplicitCallBuilder = { result = result.copy(order = x); this }
def signature(x : String) : NewImplicitCallBuilder = { result = result.copy(signature = x); this }

   def build : NewImplicitCall = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewImplicitCallBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewImplicitCallBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewImplicitCallBuilder(${_id})"
}

object NewImplicitCall{
  def apply() : NewImplicitCallBuilder = NewImplicitCallBuilder()
  private def apply(signature: String = "", order: java.lang.Integer = -1, name: String = "", lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = ""): NewImplicitCall = new NewImplicitCall(signature = signature, order = order, name = name, lineNumber = lineNumber, internalFlags = internalFlags, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code)
}

case class NewImplicitCall private[nodes] (var signature: String = "", var order: java.lang.Integer = -1, var name: String = "", var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "") extends NewNode with ImplicitCallBase {
  override def label:String = "IMPLICIT_CALL"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }
  if (signature != null) { res += "SIGNATURE" -> signature }

  res
}
}


object NewPostExecutionCallBuilder {
  def apply() : NewPostExecutionCallBuilder = new NewPostExecutionCallBuilder()
}

class NewPostExecutionCallBuilder extends NewNodeBuilder {
   var result : NewPostExecutionCall = new NewPostExecutionCall()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewPostExecutionCallBuilder = { _id = x; this }

   def code(x : String) : NewPostExecutionCallBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewPostExecutionCallBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewPostExecutionCallBuilder = { result = result.copy(depthFirstOrder = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewPostExecutionCallBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewPostExecutionCallBuilder = { result = result.copy(lineNumber = x); this }
def name(x : String) : NewPostExecutionCallBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewPostExecutionCallBuilder = { result = result.copy(order = x); this }
def signature(x : String) : NewPostExecutionCallBuilder = { result = result.copy(signature = x); this }

   def build : NewPostExecutionCall = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewPostExecutionCallBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewPostExecutionCallBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewPostExecutionCallBuilder(${_id})"
}

object NewPostExecutionCall{
  def apply() : NewPostExecutionCallBuilder = NewPostExecutionCallBuilder()
  private def apply(signature: String = "", order: java.lang.Integer = -1, name: String = "", lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = ""): NewPostExecutionCall = new NewPostExecutionCall(signature = signature, order = order, name = name, lineNumber = lineNumber, internalFlags = internalFlags, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code)
}

case class NewPostExecutionCall private[nodes] (var signature: String = "", var order: java.lang.Integer = -1, var name: String = "", var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "") extends NewNode with PostExecutionCallBase {
  override def label:String = "POST_EXECUTION_CALL"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }
  if (signature != null) { res += "SIGNATURE" -> signature }

  res
}
}


object NewTagBuilder {
  def apply() : NewTagBuilder = new NewTagBuilder()
}

class NewTagBuilder extends NewNodeBuilder {
   var result : NewTag = new NewTag()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewTagBuilder = { _id = x; this }

   def name(x : String) : NewTagBuilder = { result = result.copy(name = x); this }
def value(x : String) : NewTagBuilder = { result = result.copy(value = x); this }

   def build : NewTag = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewTagBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewTagBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewTagBuilder(${_id})"
}

object NewTag{
  def apply() : NewTagBuilder = NewTagBuilder()
  private def apply(value: String = "", name: String = ""): NewTag = new NewTag(value = value, name = name)
}

case class NewTag private[nodes] (var value: String = "", var name: String = "") extends NewNode with TagBase {
  override def label:String = "TAG"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (name != null) { res += "NAME" -> name }
  if (value != null) { res += "VALUE" -> value }

  res
}
}


object NewNamespaceBuilder {
  def apply() : NewNamespaceBuilder = new NewNamespaceBuilder()
}

class NewNamespaceBuilder extends NewNodeBuilder {
   var result : NewNamespace = new NewNamespace()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewNamespaceBuilder = { _id = x; this }

   def name(x : String) : NewNamespaceBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewNamespaceBuilder = { result = result.copy(order = x); this }

   def build : NewNamespace = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewNamespaceBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewNamespaceBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewNamespaceBuilder(${_id})"
}

object NewNamespace{
  def apply() : NewNamespaceBuilder = NewNamespaceBuilder()
  private def apply(order: java.lang.Integer = -1, name: String = ""): NewNamespace = new NewNamespace(order = order, name = name)
}

case class NewNamespace private[nodes] (var order: java.lang.Integer = -1, var name: String = "") extends NewNode with NamespaceBase {
  override def label:String = "NAMESPACE"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewMethodParameterOutBuilder {
  def apply() : NewMethodParameterOutBuilder = new NewMethodParameterOutBuilder()
}

class NewMethodParameterOutBuilder extends NewNodeBuilder {
   var result : NewMethodParameterOut = new NewMethodParameterOut()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewMethodParameterOutBuilder = { _id = x; this }

   def code(x : String) : NewMethodParameterOutBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewMethodParameterOutBuilder = { result = result.copy(columnNumber = x); this }
def evaluationStrategy(x : String) : NewMethodParameterOutBuilder = { result = result.copy(evaluationStrategy = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewMethodParameterOutBuilder = { result = result.copy(lineNumber = x); this }
def name(x : String) : NewMethodParameterOutBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewMethodParameterOutBuilder = { result = result.copy(order = x); this }
def typeFullName(x : String) : NewMethodParameterOutBuilder = { result = result.copy(typeFullName = x); this }

   def build : NewMethodParameterOut = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewMethodParameterOutBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewMethodParameterOutBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewMethodParameterOutBuilder(${_id})"
}

object NewMethodParameterOut{
  def apply() : NewMethodParameterOutBuilder = NewMethodParameterOutBuilder()
  private def apply(typeFullName: String = "", order: java.lang.Integer = -1, name: String = "", lineNumber: Option[java.lang.Integer] = None, evaluationStrategy: String = "", columnNumber: Option[java.lang.Integer] = None, code: String = ""): NewMethodParameterOut = new NewMethodParameterOut(typeFullName = typeFullName, order = order, name = name, lineNumber = lineNumber, evaluationStrategy = evaluationStrategy, columnNumber = columnNumber, code = code)
}

case class NewMethodParameterOut private[nodes] (var typeFullName: String = "", var order: java.lang.Integer = -1, var name: String = "", var lineNumber: Option[java.lang.Integer] = None, var evaluationStrategy: String = "", var columnNumber: Option[java.lang.Integer] = None, var code: String = "") extends NewNode with MethodParameterOutBase {
  override def label:String = "METHOD_PARAMETER_OUT"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (evaluationStrategy != null) { res += "EVALUATION_STRATEGY" -> evaluationStrategy }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }
  if (typeFullName != null) { res += "TYPE_FULL_NAME" -> typeFullName }

  res
}
}


object NewAnnotationBuilder {
  def apply() : NewAnnotationBuilder = new NewAnnotationBuilder()
}

class NewAnnotationBuilder extends NewNodeBuilder {
   var result : NewAnnotation = new NewAnnotation()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewAnnotationBuilder = { _id = x; this }

   def code(x : String) : NewAnnotationBuilder = { result = result.copy(code = x); this }
def fullName(x : String) : NewAnnotationBuilder = { result = result.copy(fullName = x); this }
def name(x : String) : NewAnnotationBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewAnnotationBuilder = { result = result.copy(order = x); this }

   def build : NewAnnotation = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewAnnotationBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewAnnotationBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewAnnotationBuilder(${_id})"
}

object NewAnnotation{
  def apply() : NewAnnotationBuilder = NewAnnotationBuilder()
  private def apply(order: java.lang.Integer = -1, name: String = "", fullName: String = "", code: String = ""): NewAnnotation = new NewAnnotation(order = order, name = name, fullName = fullName, code = code)
}

case class NewAnnotation private[nodes] (var order: java.lang.Integer = -1, var name: String = "", var fullName: String = "", var code: String = "") extends NewNode with AnnotationBase {
  override def label:String = "ANNOTATION"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (code != null) { res += "CODE" -> code }
  if (fullName != null) { res += "FULL_NAME" -> fullName }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewAnnotationParameterAssignBuilder {
  def apply() : NewAnnotationParameterAssignBuilder = new NewAnnotationParameterAssignBuilder()
}

class NewAnnotationParameterAssignBuilder extends NewNodeBuilder {
   var result : NewAnnotationParameterAssign = new NewAnnotationParameterAssign()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewAnnotationParameterAssignBuilder = { _id = x; this }

   def code(x : String) : NewAnnotationParameterAssignBuilder = { result = result.copy(code = x); this }
def order(x : java.lang.Integer) : NewAnnotationParameterAssignBuilder = { result = result.copy(order = x); this }

   def build : NewAnnotationParameterAssign = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewAnnotationParameterAssignBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewAnnotationParameterAssignBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewAnnotationParameterAssignBuilder(${_id})"
}

object NewAnnotationParameterAssign{
  def apply() : NewAnnotationParameterAssignBuilder = NewAnnotationParameterAssignBuilder()
  private def apply(order: java.lang.Integer = -1, code: String = ""): NewAnnotationParameterAssign = new NewAnnotationParameterAssign(order = order, code = code)
}

case class NewAnnotationParameterAssign private[nodes] (var order: java.lang.Integer = -1, var code: String = "") extends NewNode with AnnotationParameterAssignBase {
  override def label:String = "ANNOTATION_PARAMETER_ASSIGN"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (code != null) { res += "CODE" -> code }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewAnnotationParameterBuilder {
  def apply() : NewAnnotationParameterBuilder = new NewAnnotationParameterBuilder()
}

class NewAnnotationParameterBuilder extends NewNodeBuilder {
   var result : NewAnnotationParameter = new NewAnnotationParameter()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewAnnotationParameterBuilder = { _id = x; this }

   def code(x : String) : NewAnnotationParameterBuilder = { result = result.copy(code = x); this }
def order(x : java.lang.Integer) : NewAnnotationParameterBuilder = { result = result.copy(order = x); this }

   def build : NewAnnotationParameter = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewAnnotationParameterBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewAnnotationParameterBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewAnnotationParameterBuilder(${_id})"
}

object NewAnnotationParameter{
  def apply() : NewAnnotationParameterBuilder = NewAnnotationParameterBuilder()
  private def apply(order: java.lang.Integer = -1, code: String = ""): NewAnnotationParameter = new NewAnnotationParameter(order = order, code = code)
}

case class NewAnnotationParameter private[nodes] (var order: java.lang.Integer = -1, var code: String = "") extends NewNode with AnnotationParameterBase {
  override def label:String = "ANNOTATION_PARAMETER"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (code != null) { res += "CODE" -> code }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewAnnotationLiteralBuilder {
  def apply() : NewAnnotationLiteralBuilder = new NewAnnotationLiteralBuilder()
}

class NewAnnotationLiteralBuilder extends NewNodeBuilder {
   var result : NewAnnotationLiteral = new NewAnnotationLiteral()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewAnnotationLiteralBuilder = { _id = x; this }

   def argumentIndex(x : java.lang.Integer) : NewAnnotationLiteralBuilder = { result = result.copy(argumentIndex = x); this }
def code(x : String) : NewAnnotationLiteralBuilder = { result = result.copy(code = x); this }
def columnNumber(x : Option[java.lang.Integer]) : NewAnnotationLiteralBuilder = { result = result.copy(columnNumber = x); this }
def depthFirstOrder(x : Option[java.lang.Integer]) : NewAnnotationLiteralBuilder = { result = result.copy(depthFirstOrder = x); this }
def internalFlags(x : Option[java.lang.Integer]) : NewAnnotationLiteralBuilder = { result = result.copy(internalFlags = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewAnnotationLiteralBuilder = { result = result.copy(lineNumber = x); this }
def name(x : String) : NewAnnotationLiteralBuilder = { result = result.copy(name = x); this }
def order(x : java.lang.Integer) : NewAnnotationLiteralBuilder = { result = result.copy(order = x); this }

   def build : NewAnnotationLiteral = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewAnnotationLiteralBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewAnnotationLiteralBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewAnnotationLiteralBuilder(${_id})"
}

object NewAnnotationLiteral{
  def apply() : NewAnnotationLiteralBuilder = NewAnnotationLiteralBuilder()
  private def apply(order: java.lang.Integer = -1, name: String = "", lineNumber: Option[java.lang.Integer] = None, internalFlags: Option[java.lang.Integer] = None, depthFirstOrder: Option[java.lang.Integer] = None, columnNumber: Option[java.lang.Integer] = None, code: String = "", argumentIndex: java.lang.Integer = -1): NewAnnotationLiteral = new NewAnnotationLiteral(order = order, name = name, lineNumber = lineNumber, internalFlags = internalFlags, depthFirstOrder = depthFirstOrder, columnNumber = columnNumber, code = code, argumentIndex = argumentIndex)
}

case class NewAnnotationLiteral private[nodes] (var order: java.lang.Integer = -1, var name: String = "", var lineNumber: Option[java.lang.Integer] = None, var internalFlags: Option[java.lang.Integer] = None, var depthFirstOrder: Option[java.lang.Integer] = None, var columnNumber: Option[java.lang.Integer] = None, var code: String = "", var argumentIndex: java.lang.Integer = -1) extends NewNode with AnnotationLiteralBase {
  override def label:String = "ANNOTATION_LITERAL"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (argumentIndex != null) { res += "ARGUMENT_INDEX" -> argumentIndex }
  if (code != null) { res += "CODE" -> code }
  if (columnNumber != null && columnNumber.isDefined) { res += "COLUMN_NUMBER" -> columnNumber.get }
  if (depthFirstOrder != null && depthFirstOrder.isDefined) { res += "DEPTH_FIRST_ORDER" -> depthFirstOrder.get }
  if (internalFlags != null && internalFlags.isDefined) { res += "INTERNAL_FLAGS" -> internalFlags.get }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (name != null) { res += "NAME" -> name }
  if (order != null) { res += "ORDER" -> order }

  res
}
}


object NewConfigFileBuilder {
  def apply() : NewConfigFileBuilder = new NewConfigFileBuilder()
}

class NewConfigFileBuilder extends NewNodeBuilder {
   var result : NewConfigFile = new NewConfigFile()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewConfigFileBuilder = { _id = x; this }

   def content(x : String) : NewConfigFileBuilder = { result = result.copy(content = x); this }
def name(x : String) : NewConfigFileBuilder = { result = result.copy(name = x); this }

   def build : NewConfigFile = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewConfigFileBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewConfigFileBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewConfigFileBuilder(${_id})"
}

object NewConfigFile{
  def apply() : NewConfigFileBuilder = NewConfigFileBuilder()
  private def apply(name: String = "", content: String = ""): NewConfigFile = new NewConfigFile(name = name, content = content)
}

case class NewConfigFile private[nodes] (var name: String = "", var content: String = "") extends NewNode with ConfigFileBase {
  override def label:String = "CONFIG_FILE"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (content != null) { res += "CONTENT" -> content }
  if (name != null) { res += "NAME" -> name }

  res
}
}


object NewClosureBindingBuilder {
  def apply() : NewClosureBindingBuilder = new NewClosureBindingBuilder()
}

class NewClosureBindingBuilder extends NewNodeBuilder {
   var result : NewClosureBinding = new NewClosureBinding()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewClosureBindingBuilder = { _id = x; this }

   def closureBindingId(x : Option[String]) : NewClosureBindingBuilder = { result = result.copy(closureBindingId = x); this }
def closureOriginalName(x : Option[String]) : NewClosureBindingBuilder = { result = result.copy(closureOriginalName = x); this }
def evaluationStrategy(x : String) : NewClosureBindingBuilder = { result = result.copy(evaluationStrategy = x); this }

   def build : NewClosureBinding = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewClosureBindingBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewClosureBindingBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewClosureBindingBuilder(${_id})"
}

object NewClosureBinding{
  def apply() : NewClosureBindingBuilder = NewClosureBindingBuilder()
  private def apply(evaluationStrategy: String = "", closureOriginalName: Option[String] = None, closureBindingId: Option[String] = None): NewClosureBinding = new NewClosureBinding(evaluationStrategy = evaluationStrategy, closureOriginalName = closureOriginalName, closureBindingId = closureBindingId)
}

case class NewClosureBinding private[nodes] (var evaluationStrategy: String = "", var closureOriginalName: Option[String] = None, var closureBindingId: Option[String] = None) extends NewNode with ClosureBindingBase {
  override def label:String = "CLOSURE_BINDING"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (closureBindingId != null && closureBindingId.isDefined) { res += "CLOSURE_BINDING_ID" -> closureBindingId.get }
  if (closureOriginalName != null && closureOriginalName.isDefined) { res += "CLOSURE_ORIGINAL_NAME" -> closureOriginalName.get }
  if (evaluationStrategy != null) { res += "EVALUATION_STRATEGY" -> evaluationStrategy }

  res
}
}


object NewDependencyBuilder {
  def apply() : NewDependencyBuilder = new NewDependencyBuilder()
}

class NewDependencyBuilder extends NewNodeBuilder {
   var result : NewDependency = new NewDependency()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewDependencyBuilder = { _id = x; this }

   def dependencyGroupId(x : Option[String]) : NewDependencyBuilder = { result = result.copy(dependencyGroupId = x); this }
def name(x : String) : NewDependencyBuilder = { result = result.copy(name = x); this }
def version(x : String) : NewDependencyBuilder = { result = result.copy(version = x); this }

   def build : NewDependency = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewDependencyBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewDependencyBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewDependencyBuilder(${_id})"
}

object NewDependency{
  def apply() : NewDependencyBuilder = NewDependencyBuilder()
  private def apply(version: String = "", name: String = "", dependencyGroupId: Option[String] = None): NewDependency = new NewDependency(version = version, name = name, dependencyGroupId = dependencyGroupId)
}

case class NewDependency private[nodes] (var version: String = "", var name: String = "", var dependencyGroupId: Option[String] = None) extends NewNode with DependencyBase {
  override def label:String = "DEPENDENCY"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (dependencyGroupId != null && dependencyGroupId.isDefined) { res += "DEPENDENCY_GROUP_ID" -> dependencyGroupId.get }
  if (name != null) { res += "NAME" -> name }
  if (version != null) { res += "VERSION" -> version }

  res
}
}


object NewDomNodeBuilder {
  def apply() : NewDomNodeBuilder = new NewDomNodeBuilder()
}

class NewDomNodeBuilder extends NewNodeBuilder {
   var result : NewDomNode = new NewDomNode()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewDomNodeBuilder = { _id = x; this }

   def name(x : String) : NewDomNodeBuilder = { result = result.copy(name = x); this }
def attributes(x : Seq[DomAttributeBase]) : NewDomNodeBuilder = { result = result.copy(attributes = x); this }

   def build : NewDomNode = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewDomNodeBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewDomNodeBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewDomNodeBuilder(${_id})"
}

object NewDomNode{
  def apply() : NewDomNodeBuilder = NewDomNodeBuilder()
  private def apply(attributes: Seq[DomAttributeBase] = List(), name: String = ""): NewDomNode = new NewDomNode(attributes = attributes, name = name)
}

case class NewDomNode private[nodes] (var attributes: Seq[DomAttributeBase] = List(), var name: String = "") extends NewNode with DomNodeBase {
  override def label:String = "DOM_NODE"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (name != null) { res += "NAME" -> name }
  if (attributes != null && attributes.nonEmpty) { res += "attributes" -> attributes }
  res
}
}


object NewDomAttributeBuilder {
  def apply() : NewDomAttributeBuilder = new NewDomAttributeBuilder()
}

class NewDomAttributeBuilder extends NewNodeBuilder {
   var result : NewDomAttribute = new NewDomAttribute()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewDomAttributeBuilder = { _id = x; this }

   def name(x : String) : NewDomAttributeBuilder = { result = result.copy(name = x); this }
def value(x : String) : NewDomAttributeBuilder = { result = result.copy(value = x); this }

   def build : NewDomAttribute = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewDomAttributeBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewDomAttributeBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewDomAttributeBuilder(${_id})"
}

object NewDomAttribute{
  def apply() : NewDomAttributeBuilder = NewDomAttributeBuilder()
  private def apply(value: String = "", name: String = ""): NewDomAttribute = new NewDomAttribute(value = value, name = name)
}

case class NewDomAttribute private[nodes] (var value: String = "", var name: String = "") extends NewNode with DomAttributeBase {
  override def label:String = "DOM_ATTRIBUTE"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (name != null) { res += "NAME" -> name }
  if (value != null) { res += "VALUE" -> value }

  res
}
}


object NewTagsBuilder {
  def apply() : NewTagsBuilder = new NewTagsBuilder()
}

class NewTagsBuilder extends NewNodeBuilder {
   var result : NewTags = new NewTags()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewTagsBuilder = { _id = x; this }

   def tags(x : Seq[TagBase]) : NewTagsBuilder = { result = result.copy(tags = x); this }

   def build : NewTags = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewTagsBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewTagsBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewTagsBuilder(${_id})"
}

object NewTags{
  def apply() : NewTagsBuilder = NewTagsBuilder()
  private def apply(tags: Seq[TagBase] = List()): NewTags = new NewTags(tags = tags)
}

case class NewTags private[nodes] (var tags: Seq[TagBase] = List()) extends NewNode with TagsBase {
  override def label:String = "TAGS"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()

  if (tags != null && tags.nonEmpty) { res += "tags" -> tags }
  res
}
}


object NewFrameworkBuilder {
  def apply() : NewFrameworkBuilder = new NewFrameworkBuilder()
}

class NewFrameworkBuilder extends NewNodeBuilder {
   var result : NewFramework = new NewFramework()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewFrameworkBuilder = { _id = x; this }

   def name(x : String) : NewFrameworkBuilder = { result = result.copy(name = x); this }

   def build : NewFramework = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewFrameworkBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewFrameworkBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewFrameworkBuilder(${_id})"
}

object NewFramework{
  def apply() : NewFrameworkBuilder = NewFrameworkBuilder()
  private def apply(name: String = ""): NewFramework = new NewFramework(name = name)
}

case class NewFramework private[nodes] (var name: String = "") extends NewNode with FrameworkBase {
  override def label:String = "FRAMEWORK"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (name != null) { res += "NAME" -> name }

  res
}
}


object NewFrameworkDataBuilder {
  def apply() : NewFrameworkDataBuilder = new NewFrameworkDataBuilder()
}

class NewFrameworkDataBuilder extends NewNodeBuilder {
   var result : NewFrameworkData = new NewFrameworkData()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewFrameworkDataBuilder = { _id = x; this }

   def content(x : String) : NewFrameworkDataBuilder = { result = result.copy(content = x); this }
def name(x : String) : NewFrameworkDataBuilder = { result = result.copy(name = x); this }

   def build : NewFrameworkData = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewFrameworkDataBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewFrameworkDataBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewFrameworkDataBuilder(${_id})"
}

object NewFrameworkData{
  def apply() : NewFrameworkDataBuilder = NewFrameworkDataBuilder()
  private def apply(name: String = "", content: String = ""): NewFrameworkData = new NewFrameworkData(name = name, content = content)
}

case class NewFrameworkData private[nodes] (var name: String = "", var content: String = "") extends NewNode with FrameworkDataBase {
  override def label:String = "FRAMEWORK_DATA"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (content != null) { res += "CONTENT" -> content }
  if (name != null) { res += "NAME" -> name }

  res
}
}


object NewDetachedTrackingPointBuilder {
  def apply() : NewDetachedTrackingPointBuilder = new NewDetachedTrackingPointBuilder()
}

class NewDetachedTrackingPointBuilder extends NewNodeBuilder {
   var result : NewDetachedTrackingPoint = new NewDetachedTrackingPoint()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewDetachedTrackingPointBuilder = { _id = x; this }

   def cfgNode(x : CfgNodeBase) : NewDetachedTrackingPointBuilder = { result = result.copy(cfgNode = x); this }

   def build : NewDetachedTrackingPoint = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewDetachedTrackingPointBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewDetachedTrackingPointBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewDetachedTrackingPointBuilder(${_id})"
}

object NewDetachedTrackingPoint{
  def apply() : NewDetachedTrackingPointBuilder = NewDetachedTrackingPointBuilder()
  private def apply(cfgNode: CfgNodeBase = null): NewDetachedTrackingPoint = new NewDetachedTrackingPoint(cfgNode = cfgNode)
}

case class NewDetachedTrackingPoint private[nodes] (var cfgNode: CfgNodeBase = null) extends NewNode with DetachedTrackingPointBase {
  override def label:String = "DETACHED_TRACKING_POINT"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()

  if (cfgNode != null) { res += "cfgNode" -> cfgNode }
  res
}
}


object NewFindingBuilder {
  def apply() : NewFindingBuilder = new NewFindingBuilder()
}

class NewFindingBuilder extends NewNodeBuilder {
   var result : NewFinding = new NewFinding()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewFindingBuilder = { _id = x; this }

   def evidence(x : Seq[CpgNode]) : NewFindingBuilder = { result = result.copy(evidence = x); this }
def keyValuePairs(x : Seq[KeyValuePairBase]) : NewFindingBuilder = { result = result.copy(keyValuePairs = x); this }

   def build : NewFinding = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewFindingBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewFindingBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewFindingBuilder(${_id})"
}

object NewFinding{
  def apply() : NewFindingBuilder = NewFindingBuilder()
  private def apply(keyValuePairs: Seq[KeyValuePairBase] = List(), evidence: Seq[CpgNode] = List()): NewFinding = new NewFinding(keyValuePairs = keyValuePairs, evidence = evidence)
}

case class NewFinding private[nodes] (var keyValuePairs: Seq[KeyValuePairBase] = List(), var evidence: Seq[CpgNode] = List()) extends NewNode with FindingBase {
  override def label:String = "FINDING"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()

  if (evidence != null && evidence.nonEmpty) { res += "evidence" -> evidence }
  if (keyValuePairs != null && keyValuePairs.nonEmpty) { res += "keyValuePairs" -> keyValuePairs }
  res
}
}


object NewKeyValuePairBuilder {
  def apply() : NewKeyValuePairBuilder = new NewKeyValuePairBuilder()
}

class NewKeyValuePairBuilder extends NewNodeBuilder {
   var result : NewKeyValuePair = new NewKeyValuePair()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewKeyValuePairBuilder = { _id = x; this }

   def key(x : String) : NewKeyValuePairBuilder = { result = result.copy(key = x); this }
def value(x : String) : NewKeyValuePairBuilder = { result = result.copy(value = x); this }

   def build : NewKeyValuePair = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewKeyValuePairBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewKeyValuePairBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewKeyValuePairBuilder(${_id})"
}

object NewKeyValuePair{
  def apply() : NewKeyValuePairBuilder = NewKeyValuePairBuilder()
  private def apply(value: String = "", key: String = ""): NewKeyValuePair = new NewKeyValuePair(value = value, key = key)
}

case class NewKeyValuePair private[nodes] (var value: String = "", var key: String = "") extends NewNode with KeyValuePairBase {
  override def label:String = "KEY_VALUE_PAIR"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (key != null) { res += "KEY" -> key }
  if (value != null) { res += "VALUE" -> value }

  res
}
}


object NewCommentBuilder {
  def apply() : NewCommentBuilder = new NewCommentBuilder()
}

class NewCommentBuilder extends NewNodeBuilder {
   var result : NewComment = new NewComment()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewCommentBuilder = { _id = x; this }

   def code(x : String) : NewCommentBuilder = { result = result.copy(code = x); this }
def filename(x : String) : NewCommentBuilder = { result = result.copy(filename = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewCommentBuilder = { result = result.copy(lineNumber = x); this }

   def build : NewComment = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewCommentBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewCommentBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewCommentBuilder(${_id})"
}

object NewComment{
  def apply() : NewCommentBuilder = NewCommentBuilder()
  private def apply(lineNumber: Option[java.lang.Integer] = None, filename: String = "", code: String = ""): NewComment = new NewComment(lineNumber = lineNumber, filename = filename, code = code)
}

case class NewComment private[nodes] (var lineNumber: Option[java.lang.Integer] = None, var filename: String = "", var code: String = "") extends NewNode with CommentBase {
  override def label:String = "COMMENT"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (code != null) { res += "CODE" -> code }
  if (filename != null) { res += "FILENAME" -> filename }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }

  res
}
}


object NewPackagePrefixBuilder {
  def apply() : NewPackagePrefixBuilder = new NewPackagePrefixBuilder()
}

class NewPackagePrefixBuilder extends NewNodeBuilder {
   var result : NewPackagePrefix = new NewPackagePrefix()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewPackagePrefixBuilder = { _id = x; this }

   def value(x : String) : NewPackagePrefixBuilder = { result = result.copy(value = x); this }

   def build : NewPackagePrefix = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewPackagePrefixBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewPackagePrefixBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewPackagePrefixBuilder(${_id})"
}

object NewPackagePrefix{
  def apply() : NewPackagePrefixBuilder = NewPackagePrefixBuilder()
  private def apply(value: String = ""): NewPackagePrefix = new NewPackagePrefix(value = value)
}

case class NewPackagePrefix private[nodes] (var value: String = "") extends NewNode with PackagePrefixBase {
  override def label:String = "PACKAGE_PREFIX"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (value != null) { res += "VALUE" -> value }

  res
}
}


object NewLocationBuilder {
  def apply() : NewLocationBuilder = new NewLocationBuilder()
}

class NewLocationBuilder extends NewNodeBuilder {
   var result : NewLocation = new NewLocation()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewLocationBuilder = { _id = x; this }

   def className(x : String) : NewLocationBuilder = { result = result.copy(className = x); this }
def classShortName(x : String) : NewLocationBuilder = { result = result.copy(classShortName = x); this }
def filename(x : String) : NewLocationBuilder = { result = result.copy(filename = x); this }
def lineNumber(x : Option[java.lang.Integer]) : NewLocationBuilder = { result = result.copy(lineNumber = x); this }
def methodFullName(x : String) : NewLocationBuilder = { result = result.copy(methodFullName = x); this }
def methodShortName(x : String) : NewLocationBuilder = { result = result.copy(methodShortName = x); this }
def nodeLabel(x : String) : NewLocationBuilder = { result = result.copy(nodeLabel = x); this }
def packageName(x : String) : NewLocationBuilder = { result = result.copy(packageName = x); this }
def symbol(x : String) : NewLocationBuilder = { result = result.copy(symbol = x); this }
def node(x : Option[CpgNode]) : NewLocationBuilder = { result = result.copy(node = x); this }

   def build : NewLocation = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewLocationBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewLocationBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewLocationBuilder(${_id})"
}

object NewLocation{
  def apply() : NewLocationBuilder = NewLocationBuilder()
  private def apply(node: Option[CpgNode] = None, symbol: String = "", packageName: String = "", nodeLabel: String = "", methodShortName: String = "", methodFullName: String = "", lineNumber: Option[java.lang.Integer] = None, filename: String = "", classShortName: String = "", className: String = ""): NewLocation = new NewLocation(node = node, symbol = symbol, packageName = packageName, nodeLabel = nodeLabel, methodShortName = methodShortName, methodFullName = methodFullName, lineNumber = lineNumber, filename = filename, classShortName = classShortName, className = className)
}

case class NewLocation private[nodes] (var node: Option[CpgNode] = None, var symbol: String = "", var packageName: String = "", var nodeLabel: String = "", var methodShortName: String = "", var methodFullName: String = "", var lineNumber: Option[java.lang.Integer] = None, var filename: String = "", var classShortName: String = "", var className: String = "") extends NewNode with LocationBase {
  override def label:String = "LOCATION"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (className != null) { res += "CLASS_NAME" -> className }
  if (classShortName != null) { res += "CLASS_SHORT_NAME" -> classShortName }
  if (filename != null) { res += "FILENAME" -> filename }
  if (lineNumber != null && lineNumber.isDefined) { res += "LINE_NUMBER" -> lineNumber.get }
  if (methodFullName != null) { res += "METHOD_FULL_NAME" -> methodFullName }
  if (methodShortName != null) { res += "METHOD_SHORT_NAME" -> methodShortName }
  if (nodeLabel != null) { res += "NODE_LABEL" -> nodeLabel }
  if (packageName != null) { res += "PACKAGE_NAME" -> packageName }
  if (symbol != null) { res += "SYMBOL" -> symbol }
  if (node != null && node.isDefined) { res += "node" -> node.get }
  res
}
}


object NewTagNodePairBuilder {
  def apply() : NewTagNodePairBuilder = new NewTagNodePairBuilder()
}

class NewTagNodePairBuilder extends NewNodeBuilder {
   var result : NewTagNodePair = new NewTagNodePair()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewTagNodePairBuilder = { _id = x; this }

   def tag(x : TagBase) : NewTagNodePairBuilder = { result = result.copy(tag = x); this }
def node(x : CpgNode) : NewTagNodePairBuilder = { result = result.copy(node = x); this }

   def build : NewTagNodePair = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewTagNodePairBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewTagNodePairBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewTagNodePairBuilder(${_id})"
}

object NewTagNodePair{
  def apply() : NewTagNodePairBuilder = NewTagNodePairBuilder()
  private def apply(node: CpgNode = null, tag: TagBase = null): NewTagNodePair = new NewTagNodePair(node = node, tag = tag)
}

case class NewTagNodePair private[nodes] (var node: CpgNode = null, var tag: TagBase = null) extends NewNode with TagNodePairBase {
  override def label:String = "TAG_NODE_PAIR"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()

  if (tag != null) { res += "tag" -> tag }
  if (node != null) { res += "node" -> node }
  res
}
}


object NewSourceBuilder {
  def apply() : NewSourceBuilder = new NewSourceBuilder()
}

class NewSourceBuilder extends NewNodeBuilder {
   var result : NewSource = new NewSource()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewSourceBuilder = { _id = x; this }

   def sourceType(x : String) : NewSourceBuilder = { result = result.copy(sourceType = x); this }
def callingMethod(x : Option[MethodBase]) : NewSourceBuilder = { result = result.copy(callingMethod = x); this }
def tags(x : Seq[TagBase]) : NewSourceBuilder = { result = result.copy(tags = x); this }
def nodeType(x : TypeBase) : NewSourceBuilder = { result = result.copy(nodeType = x); this }
def methodTags(x : Seq[TagBase]) : NewSourceBuilder = { result = result.copy(methodTags = x); this }
def node(x : TrackingPointBase) : NewSourceBuilder = { result = result.copy(node = x); this }
def method(x : MethodBase) : NewSourceBuilder = { result = result.copy(method = x); this }
def callsite(x : Option[CallBase]) : NewSourceBuilder = { result = result.copy(callsite = x); this }

   def build : NewSource = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewSourceBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewSourceBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewSourceBuilder(${_id})"
}

object NewSource{
  def apply() : NewSourceBuilder = NewSourceBuilder()
  private def apply(callsite: Option[CallBase] = None, method: MethodBase = null, node: TrackingPointBase = null, methodTags: Seq[TagBase] = List(), nodeType: TypeBase = null, tags: Seq[TagBase] = List(), callingMethod: Option[MethodBase] = None, sourceType: String = ""): NewSource = new NewSource(callsite = callsite, method = method, node = node, methodTags = methodTags, nodeType = nodeType, tags = tags, callingMethod = callingMethod, sourceType = sourceType)
}

case class NewSource private[nodes] (var callsite: Option[CallBase] = None, var method: MethodBase = null, var node: TrackingPointBase = null, var methodTags: Seq[TagBase] = List(), var nodeType: TypeBase = null, var tags: Seq[TagBase] = List(), var callingMethod: Option[MethodBase] = None, var sourceType: String = "") extends NewNode with SourceBase {
  override def label:String = "SOURCE"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (sourceType != null) { res += "SOURCE_TYPE" -> sourceType }
  if (callingMethod != null && callingMethod.isDefined) { res += "callingMethod" -> callingMethod.get }
  if (tags != null && tags.nonEmpty) { res += "tags" -> tags }
  if (nodeType != null) { res += "nodeType" -> nodeType }
  if (methodTags != null && methodTags.nonEmpty) { res += "methodTags" -> methodTags }
  if (node != null) { res += "node" -> node }
  if (method != null) { res += "method" -> method }
  if (callsite != null && callsite.isDefined) { res += "callsite" -> callsite.get }
  res
}
}


object NewSinkBuilder {
  def apply() : NewSinkBuilder = new NewSinkBuilder()
}

class NewSinkBuilder extends NewNodeBuilder {
   var result : NewSink = new NewSink()
   private var _id : Long = -1L
   def id: Long = _id
   def id(x: Long): NewSinkBuilder = { _id = x; this }

   def sinkType(x : String) : NewSinkBuilder = { result = result.copy(sinkType = x); this }
def callingMethod(x : Option[MethodBase]) : NewSinkBuilder = { result = result.copy(callingMethod = x); this }
def nodeType(x : TypeBase) : NewSinkBuilder = { result = result.copy(nodeType = x); this }
def methodTags(x : Seq[TagBase]) : NewSinkBuilder = { result = result.copy(methodTags = x); this }
def node(x : TrackingPointBase) : NewSinkBuilder = { result = result.copy(node = x); this }
def parameterInTags(x : Seq[TagBase]) : NewSinkBuilder = { result = result.copy(parameterInTags = x); this }
def parameterIn(x : Option[MethodParameterInBase]) : NewSinkBuilder = { result = result.copy(parameterIn = x); this }
def method(x : MethodBase) : NewSinkBuilder = { result = result.copy(method = x); this }
def callsite(x : Option[CallBase]) : NewSinkBuilder = { result = result.copy(callsite = x); this }

   def build : NewSink = result

   def canEqual(other: Any): Boolean = other.isInstanceOf[NewSinkBuilder]

   override def equals(other: Any): Boolean = other match {
      case that: NewSinkBuilder => (that canEqual this) && _id == that._id
      case _ => false
   }

   override def hashCode(): Int = {
      val state = Seq(_id)
      state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
   }

   override def toString = s"NewSinkBuilder(${_id})"
}

object NewSink{
  def apply() : NewSinkBuilder = NewSinkBuilder()
  private def apply(callsite: Option[CallBase] = None, method: MethodBase = null, parameterIn: Option[MethodParameterInBase] = None, parameterInTags: Seq[TagBase] = List(), node: TrackingPointBase = null, methodTags: Seq[TagBase] = List(), nodeType: TypeBase = null, callingMethod: Option[MethodBase] = None, sinkType: String = ""): NewSink = new NewSink(callsite = callsite, method = method, parameterIn = parameterIn, parameterInTags = parameterInTags, node = node, methodTags = methodTags, nodeType = nodeType, callingMethod = callingMethod, sinkType = sinkType)
}

case class NewSink private[nodes] (var callsite: Option[CallBase] = None, var method: MethodBase = null, var parameterIn: Option[MethodParameterInBase] = None, var parameterInTags: Seq[TagBase] = List(), var node: TrackingPointBase = null, var methodTags: Seq[TagBase] = List(), var nodeType: TypeBase = null, var callingMethod: Option[MethodBase] = None, var sinkType: String = "") extends NewNode with SinkBase {
  override def label:String = "SINK"

  override def properties: Map[String, Any] = {
  var res = Map[String, Any]()
  if (sinkType != null) { res += "SINK_TYPE" -> sinkType }
  if (callingMethod != null && callingMethod.isDefined) { res += "callingMethod" -> callingMethod.get }
  if (nodeType != null) { res += "nodeType" -> nodeType }
  if (methodTags != null && methodTags.nonEmpty) { res += "methodTags" -> methodTags }
  if (node != null) { res += "node" -> node }
  if (parameterInTags != null && parameterInTags.nonEmpty) { res += "parameterInTags" -> parameterInTags }
  if (parameterIn != null && parameterIn.isDefined) { res += "parameterIn" -> parameterIn.get }
  if (method != null) { res += "method" -> method }
  if (callsite != null && callsite.isDefined) { res += "callsite" -> callsite.get }
  res
}
}

