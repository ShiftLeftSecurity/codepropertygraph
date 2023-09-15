package io.shiftleft.codepropertygraph.generated.nodes

trait AstNodeEMT extends AnyRef with HasCodeEMT with HasColumnNumberEMT with HasLineNumberEMT with HasOrderEMT

trait AstNodeBase extends AbstractNode with StaticType[AstNodeEMT]
// new properties: CODE, COLUMN_NUMBER, LINE_NUMBER, ORDER
// inherited properties:
// inherited interfaces:
// implementing nodes: ANNOTATION, ANNOTATION_LITERAL, ANNOTATION_PARAMETER, ANNOTATION_PARAMETER_ASSIGN, ARRAY_INITIALIZER, BLOCK, CALL, COMMENT, CONTROL_STRUCTURE, FIELD_IDENTIFIER, FILE, IDENTIFIER, IMPORT, JUMP_LABEL, JUMP_TARGET, LITERAL, LOCAL, MEMBER, METHOD, METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT, METHOD_REF, METHOD_RETURN, MODIFIER, NAMESPACE, NAMESPACE_BLOCK, RETURN, TEMPLATE_DOM, TYPE_ARGUMENT, TYPE_DECL, TYPE_PARAMETER, TYPE_REF, UNKNOWN
trait AstNode extends StoredNode with AstNodeBase with StaticType[AstNodeEMT]

object AstNode {
  object PropertyDefaults {
    val Code  = "<empty>"
    val Order = -1: Int
  }
}

trait AstNodeNew extends NewNode with AstNodeBase with StaticType[AstNodeEMT] {
  def code: String
  def code_=(value: String): Unit
  def code(value: String): this.type
  def columnNumber: Option[Int]
  def columnNumber_=(value: Option[Int]): Unit
  def columnNumber(value: Option[Int]): this.type
  def columnNumber(value: Int): this.type
  def lineNumber: Option[Int]
  def lineNumber_=(value: Option[Int]): Unit
  def lineNumber(value: Option[Int]): this.type
  def lineNumber(value: Int): this.type
  def order: Int
  def order_=(value: Int): Unit
  def order(value: Int): this.type
}

trait CallReprEMT extends AnyRef with CfgNodeEMT with HasNameEMT with HasSignatureEMT

trait CallReprBase extends AbstractNode with CfgNodeBase with StaticType[CallReprEMT]
// new properties: NAME, SIGNATURE
// inherited properties: CODE, COLUMN_NUMBER, LINE_NUMBER, ORDER
// inherited interfaces: AST_NODE
// implementing nodes: CALL
trait CallRepr extends StoredNode with CallReprBase with CfgNode with StaticType[CallReprEMT]

object CallRepr {
  object PropertyDefaults {
    val Name      = "<empty>"
    val Signature = ""
  }
}

trait CallReprNew extends NewNode with CallReprBase with CfgNodeNew with StaticType[CallReprEMT] {
  def name: String
  def name_=(value: String): Unit
  def name(value: String): this.type
  def signature: String
  def signature_=(value: String): Unit
  def signature(value: String): this.type
}

trait CfgNodeEMT extends AnyRef with AstNodeEMT

trait CfgNodeBase extends AbstractNode with AstNodeBase with StaticType[CfgNodeEMT]
// new properties:
// inherited properties: CODE, COLUMN_NUMBER, LINE_NUMBER, ORDER
// inherited interfaces:
// implementing nodes: ANNOTATION, ANNOTATION_LITERAL, ARRAY_INITIALIZER, BLOCK, CALL, CONTROL_STRUCTURE, FIELD_IDENTIFIER, IDENTIFIER, JUMP_TARGET, LITERAL, METHOD, METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT, METHOD_REF, METHOD_RETURN, RETURN, TEMPLATE_DOM, TYPE_REF, UNKNOWN
trait CfgNode extends StoredNode with CfgNodeBase with AstNode with StaticType[CfgNodeEMT]

object CfgNode {
  object PropertyDefaults {}
}

trait CfgNodeNew extends NewNode with CfgNodeBase with AstNodeNew with StaticType[CfgNodeEMT] {}

trait DeclarationEMT extends AnyRef with HasNameEMT

trait DeclarationBase extends AbstractNode with StaticType[DeclarationEMT]
// new properties: NAME
// inherited properties:
// inherited interfaces:
// implementing nodes: LOCAL, MEMBER, METHOD, METHOD_PARAMETER_IN, METHOD_PARAMETER_OUT
trait Declaration extends StoredNode with DeclarationBase with StaticType[DeclarationEMT]

object Declaration {
  object PropertyDefaults {
    val Name = "<empty>"
  }
}

trait DeclarationNew extends NewNode with DeclarationBase with StaticType[DeclarationEMT] {
  def name: String
  def name_=(value: String): Unit
  def name(value: String): this.type
}

trait ExpressionEMT extends AnyRef with CfgNodeEMT with HasArgumentIndexEMT with HasArgumentNameEMT

trait ExpressionBase extends AbstractNode with CfgNodeBase with StaticType[ExpressionEMT]
// new properties: ARGUMENT_INDEX, ARGUMENT_NAME
// inherited properties: CODE, COLUMN_NUMBER, LINE_NUMBER, ORDER
// inherited interfaces: AST_NODE
// implementing nodes: ANNOTATION, ANNOTATION_LITERAL, ARRAY_INITIALIZER, BLOCK, CALL, CONTROL_STRUCTURE, FIELD_IDENTIFIER, IDENTIFIER, LITERAL, METHOD_REF, RETURN, TEMPLATE_DOM, TYPE_REF, UNKNOWN
trait Expression extends StoredNode with ExpressionBase with CfgNode with StaticType[ExpressionEMT]

object Expression {
  object PropertyDefaults {
    val ArgumentIndex = -1: Int
  }
}

trait ExpressionNew extends NewNode with ExpressionBase with AstNodeNew with CfgNodeNew with StaticType[ExpressionEMT] {
  def argumentIndex: Int
  def argumentIndex_=(value: Int): Unit
  def argumentIndex(value: Int): this.type
  def argumentName: Option[String]
  def argumentName_=(value: Option[String]): Unit
  def argumentName(value: Option[String]): this.type
  def argumentName(value: String): this.type
}

/** Node types with this marker trait are guaranteed to have the ALIAS_TYPE_FULL_NAME property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasAliasTypeFullNameEMT

/** Node types with this marker trait are guaranteed to have the ARGUMENT_INDEX property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasArgumentIndexEMT

/** Node types with this marker trait are guaranteed to have the ARGUMENT_NAME property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasArgumentNameEMT

/** Node types with this marker trait are guaranteed to have the AST_PARENT_FULL_NAME property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasAstParentFullNameEMT

/** Node types with this marker trait are guaranteed to have the AST_PARENT_TYPE property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasAstParentTypeEMT

/** Node types with this marker trait are guaranteed to have the CANONICAL_NAME property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasCanonicalNameEMT

/** Node types with this marker trait are guaranteed to have the CLASS_NAME property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasClassNameEMT

/** Node types with this marker trait are guaranteed to have the CLASS_SHORT_NAME property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasClassShortNameEMT

/** Node types with this marker trait are guaranteed to have the CLOSURE_BINDING_ID property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasClosureBindingIdEMT

/** Node types with this marker trait are guaranteed to have the CLOSURE_ORIGINAL_NAME property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasClosureOriginalNameEMT

/** Node types with this marker trait are guaranteed to have the CODE property. EMT stands for: "erased marker trait",
  * it exists only at compile time in order to improve type safety.
  */
trait HasCodeEMT

/** Node types with this marker trait are guaranteed to have the COLUMN_NUMBER property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasColumnNumberEMT

/** Node types with this marker trait are guaranteed to have the COLUMN_NUMBER_END property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasColumnNumberEndEMT

/** Node types with this marker trait are guaranteed to have the CONTAINED_REF property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasContainedRefEMT

/** Node types with this marker trait are guaranteed to have the CONTENT property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasContentEMT

/** Node types with this marker trait are guaranteed to have the CONTROL_STRUCTURE_TYPE property. EMT stands for:
  * "erased marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasControlStructureTypeEMT

/** Node types with this marker trait are guaranteed to have the DEPENDENCY_GROUP_ID property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasDependencyGroupIdEMT

/** Node types with this marker trait are guaranteed to have the DISPATCH_TYPE property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasDispatchTypeEMT

/** Node types with this marker trait are guaranteed to have the DYNAMIC_TYPE_HINT_FULL_NAME property. EMT stands for:
  * "erased marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasDynamicTypeHintFullNameEMT

/** Node types with this marker trait are guaranteed to have the EVALUATION_STRATEGY property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasEvaluationStrategyEMT

/** Node types with this marker trait are guaranteed to have the EXPLICIT_AS property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasExplicitAsEMT

/** Node types with this marker trait are guaranteed to have the FILENAME property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasFilenameEMT

/** Node types with this marker trait are guaranteed to have the FULL_NAME property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasFullNameEMT

/** Node types with this marker trait are guaranteed to have the HASH property. EMT stands for: "erased marker trait",
  * it exists only at compile time in order to improve type safety.
  */
trait HasHashEMT

/** Node types with this marker trait are guaranteed to have the IMPORTED_AS property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasImportedAsEMT

/** Node types with this marker trait are guaranteed to have the IMPORTED_ENTITY property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasImportedEntityEMT

/** Node types with this marker trait are guaranteed to have the INDEX property. EMT stands for: "erased marker trait",
  * it exists only at compile time in order to improve type safety.
  */
trait HasIndexEMT

/** Node types with this marker trait are guaranteed to have the INHERITS_FROM_TYPE_FULL_NAME property. EMT stands for:
  * "erased marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasInheritsFromTypeFullNameEMT

/** Node types with this marker trait are guaranteed to have the IS_EXPLICIT property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasIsExplicitEMT

/** Node types with this marker trait are guaranteed to have the IS_EXTERNAL property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasIsExternalEMT

/** Node types with this marker trait are guaranteed to have the IS_VARIADIC property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasIsVariadicEMT

/** Node types with this marker trait are guaranteed to have the IS_WILDCARD property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasIsWildcardEMT

/** Node types with this marker trait are guaranteed to have the KEY property. EMT stands for: "erased marker trait", it
  * exists only at compile time in order to improve type safety.
  */
trait HasKeyEMT

/** Node types with this marker trait are guaranteed to have the LANGUAGE property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasLanguageEMT

/** Node types with this marker trait are guaranteed to have the LINE_NUMBER property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasLineNumberEMT

/** Node types with this marker trait are guaranteed to have the LINE_NUMBER_END property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasLineNumberEndEMT

/** Node types with this marker trait are guaranteed to have the METHOD_FULL_NAME property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasMethodFullNameEMT

/** Node types with this marker trait are guaranteed to have the METHOD_SHORT_NAME property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasMethodShortNameEMT

/** Node types with this marker trait are guaranteed to have the MODIFIER_TYPE property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasModifierTypeEMT

/** Node types with this marker trait are guaranteed to have the NAME property. EMT stands for: "erased marker trait",
  * it exists only at compile time in order to improve type safety.
  */
trait HasNameEMT

/** Node types with this marker trait are guaranteed to have the NODE_LABEL property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasNodeLabelEMT

/** Node types with this marker trait are guaranteed to have the OFFSET property. EMT stands for: "erased marker trait",
  * it exists only at compile time in order to improve type safety.
  */
trait HasOffsetEMT

/** Node types with this marker trait are guaranteed to have the OFFSET_END property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasOffsetEndEMT

/** Node types with this marker trait are guaranteed to have the ORDER property. EMT stands for: "erased marker trait",
  * it exists only at compile time in order to improve type safety.
  */
trait HasOrderEMT

/** Node types with this marker trait are guaranteed to have the OVERLAYS property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasOverlaysEMT

/** Node types with this marker trait are guaranteed to have the PACKAGE_NAME property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasPackageNameEMT

/** Node types with this marker trait are guaranteed to have the PARSER_TYPE_NAME property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasParserTypeNameEMT

/** Node types with this marker trait are guaranteed to have the POSSIBLE_TYPES property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasPossibleTypesEMT

/** Node types with this marker trait are guaranteed to have the ROOT property. EMT stands for: "erased marker trait",
  * it exists only at compile time in order to improve type safety.
  */
trait HasRootEMT

/** Node types with this marker trait are guaranteed to have the SIGNATURE property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasSignatureEMT

/** Node types with this marker trait are guaranteed to have the SYMBOL property. EMT stands for: "erased marker trait",
  * it exists only at compile time in order to improve type safety.
  */
trait HasSymbolEMT

/** Node types with this marker trait are guaranteed to have the TYPE_DECL_FULL_NAME property. EMT stands for: "erased
  * marker trait", it exists only at compile time in order to improve type safety.
  */
trait HasTypeDeclFullNameEMT

/** Node types with this marker trait are guaranteed to have the TYPE_FULL_NAME property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasTypeFullNameEMT

/** Node types with this marker trait are guaranteed to have the VALUE property. EMT stands for: "erased marker trait",
  * it exists only at compile time in order to improve type safety.
  */
trait HasValueEMT

/** Node types with this marker trait are guaranteed to have the VERSION property. EMT stands for: "erased marker
  * trait", it exists only at compile time in order to improve type safety.
  */
trait HasVersionEMT
