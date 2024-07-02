package io.shiftleft.codepropertygraph.generated

object Properties {

  /** This property holds the fully qualified name of the type that the node is a type alias of.
    */
  val AliasTypeFullName = flatgraph.OptionalPropertyKey[String](kind = 0, name = "ALIAS_TYPE_FULL_NAME")

  /** AST-children of CALL nodes have an argument index, that is used to match call-site arguments with callee
    * parameters. Explicit parameters are numbered from 1 to N, while index 0 is reserved for implicit self / this
    * parameter. CALLs without implicit parameter therefore have arguments starting with index 1. AST-children of BLOCK
    * nodes may have an argument index as well; in this case, the last argument index determines the return expression
    * of a BLOCK expression. If the `PARAMETER_NAME` field is set, then the `ARGUMENT_INDEX` field is ignored. It is
    * suggested to set it to -1.
    */
  val ArgumentIndex = flatgraph.SinglePropertyKey[Int](kind = 1, name = "ARGUMENT_INDEX", default = -1: Int)

  /** For calls involving named parameters, the `ARGUMENT_NAME` field holds the name of the parameter initialized by the
    * expression. For all other calls, this field is unset.
    */
  val ArgumentName = flatgraph.OptionalPropertyKey[String](kind = 2, name = "ARGUMENT_NAME")

  /** This field holds the FULL_NAME of the AST parent of an entity. */
  val AstParentFullName =
    flatgraph.SinglePropertyKey[String](kind = 3, name = "AST_PARENT_FULL_NAME", default = "<empty>")

  /** The type of the AST parent. Since this is only used in some parts of the graph, the list does not include all
    * possible parents by intention. Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK.
    */
  val AstParentType = flatgraph.SinglePropertyKey[String](kind = 4, name = "AST_PARENT_TYPE", default = "<empty>")

  /** This field holds the canonical name of a `FIELD_IDENTIFIER`. It is typically identical to the CODE field, but
    * canonicalized according to source language semantics. Human readable names are preferable. `FIELD_IDENTIFIER`
    * nodes must share identical `CANONICAL_NAME` if and only if they alias, e.g., in C-style unions (if the aliasing
    * relationship is unknown or there are partial overlaps, then one must make a reasonable guess, and trade off
    * between false negatives and false positives).
    */
  val CanonicalName = flatgraph.SinglePropertyKey[String](kind = 5, name = "CANONICAL_NAME", default = "<empty>")

  val ClassName = flatgraph.SinglePropertyKey[String](kind = 6, name = "CLASS_NAME", default = "<empty>")

  val ClassShortName = flatgraph.SinglePropertyKey[String](kind = 7, name = "CLASS_SHORT_NAME", default = "<empty>")

  /** Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with
    * the corresponding CLOSURE_BINDING nodes
    */
  val ClosureBindingId = flatgraph.OptionalPropertyKey[String](kind = 8, name = "CLOSURE_BINDING_ID")

  /** The original name of the (potentially mangled) captured variable */
  val ClosureOriginalName = flatgraph.OptionalPropertyKey[String](kind = 9, name = "CLOSURE_ORIGINAL_NAME")

  /** This field holds the code snippet that the node represents. */
  val Code = flatgraph.SinglePropertyKey[String](kind = 10, name = "CODE", default = "<empty>")

  /** This optional fields provides the column number of the program construct represented by the node.
    */
  val ColumnNumber = flatgraph.OptionalPropertyKey[Int](kind = 11, name = "COLUMN_NUMBER")

  /** This optional fields provides the column number at which the program construct represented by the node ends.
    */
  val ColumnNumberEnd = flatgraph.OptionalPropertyKey[Int](kind = 12, name = "COLUMN_NUMBER_END")

  /** References to other nodes. This is not a real property; it exists here for the sake of proto serialization only.
    * valueType and cardinality are meaningless.
    */
  val ContainedRef = flatgraph.SinglePropertyKey[String](kind = 13, name = "CONTAINED_REF", default = "<empty>")

  /** Certain files, e.g., configuration files, may be included in the CPG as-is. For such files, the `CONTENT` field
    * contains the files content.
    */
  val Content = flatgraph.SinglePropertyKey[String](kind = 14, name = "CONTENT", default = "<empty>")

  /** The `CONTROL_STRUCTURE_TYPE` field indicates which kind of control structure a `CONTROL_STRUCTURE` node
    * represents. The available types are the following: BREAK, CONTINUE, DO, WHILE, FOR, GOTO, IF, ELSE, TRY, THROW and
    * SWITCH.
    */
  val ControlStructureType =
    flatgraph.SinglePropertyKey[String](kind = 15, name = "CONTROL_STRUCTURE_TYPE", default = "<empty>")

  /** The group ID for a dependency */
  val DependencyGroupId = flatgraph.OptionalPropertyKey[String](kind = 16, name = "DEPENDENCY_GROUP_ID")

  /** This field holds the dispatch type of a call, which is either `STATIC_DISPATCH` or `DYNAMIC_DISPATCH`. For
    * statically dispatched method calls, the call target is known at compile time while for dynamically dispatched
    * calls, it can only be determined at runtime as it may depend on the type of an object (as is the case for virtual
    * method calls) or calculation of an offset.
    */
  val DispatchType = flatgraph.SinglePropertyKey[String](kind = 17, name = "DISPATCH_TYPE", default = "<empty>")

  /** Type hint for the dynamic type. These are observed to be verifiable at runtime. */
  val DynamicTypeHintFullName = flatgraph.MultiPropertyKey[String](kind = 18, name = "DYNAMIC_TYPE_HINT_FULL_NAME")

  /** For formal method input parameters, output parameters, and return parameters, this field holds the evaluation
    * strategy, which is one of the following: 1) `BY_REFERENCE` indicates that the parameter is passed by reference, 2)
    * `BY_VALUE` indicates that it is passed by value, that is, a copy is made, 3) `BY_SHARING` the parameter is a
    * pointer/reference and it is shared with the caller/callee. While a copy of the pointer is made, a copy of the
    * object that it points to is not made.
    */
  val EvaluationStrategy =
    flatgraph.SinglePropertyKey[String](kind = 19, name = "EVALUATION_STRATEGY", default = "<empty>")

  /** Specifies whether the IMPORTED_AS property was explicitly present in the code. For languages like Java which do
    * not allow a renaming during import this is always false. For e.g. Kotlin it depends on the existence of the "as"
    * keyword.
    */
  val ExplicitAs = flatgraph.OptionalPropertyKey[Boolean](kind = 20, name = "EXPLICIT_AS")

  /** The path of the source file this node was generated from, relative to the root path in the meta data node. This
    * field must be set but may be set to the value `<unknown>` to indicate that no source file can be associated with
    * the node, e.g., because the node represents an entity known to exist because it is referenced, but for which the
    * file that is is declared in is unknown.
    */
  val Filename = flatgraph.SinglePropertyKey[String](kind = 21, name = "FILENAME", default = "<empty>")

  /** This is the fully-qualified name of an entity, e.g., the fully-qualified name of a method or type. The details of
    * what constitutes a fully-qualified name are language specific. This field SHOULD be human readable.
    */
  val FullName = flatgraph.SinglePropertyKey[String](kind = 22, name = "FULL_NAME", default = "<empty>")

  /** This property contains a hash value in the form of a string. Hashes can be used to summarize data, e.g., to
    * summarize the contents of source files or sub graphs. Such summaries are useful to determine whether code has
    * already been analyzed in incremental analysis pipelines. This property is optional to allow its calculation to be
    * deferred or skipped if the hash is not needed.
    */
  val Hash = flatgraph.OptionalPropertyKey[String](kind = 23, name = "HASH")

  /** The identifier under which the import can be accessed in the importing context. For a Java import this is always
    * identical to the class name. But e.g. for a Kotlin import like "import java.nio.ByteBuffer as BBuffer" this would
    * be "BBuffer". This property is ignored if IS_WILDCARD is true.
    */
  val ImportedAs = flatgraph.OptionalPropertyKey[String](kind = 24, name = "IMPORTED_AS")

  /** The identifying string of the imported entity. For a Java import like "import java.nio.ByteBuffer;" this would be
    * "java.nio.ByteBuffer".
    */
  val ImportedEntity = flatgraph.OptionalPropertyKey[String](kind = 25, name = "IMPORTED_ENTITY")

  /** Specifies an index, e.g., for a parameter or argument. Explicit parameters are numbered from 1 to N, while index 0
    * is reserved for implicit self / this parameter.
    */
  val Index = flatgraph.SinglePropertyKey[Int](kind = 26, name = "INDEX", default = -1: Int)

  /** The static types a TYPE_DECL inherits from. This property is matched against the FULL_NAME of TYPE nodes and thus
    * it is required to have at least one TYPE node for each TYPE_FULL_NAME
    */
  val InheritsFromTypeFullName = flatgraph.MultiPropertyKey[String](kind = 27, name = "INHERITS_FROM_TYPE_FULL_NAME")

  /** Specifies whether this is an explicit import. Most languages have implicit default imports of some standard
    * library elements and this flag is used to distinguish those from explicit imports found in the code base.
    */
  val IsExplicit = flatgraph.OptionalPropertyKey[Boolean](kind = 28, name = "IS_EXPLICIT")

  /** Indicates that the construct (METHOD or TYPE_DECL) is external, that is, it is referenced but not defined in the
    * code (applies both to insular parsing and to library functions where we have header files only)
    */
  val IsExternal = flatgraph.SinglePropertyKey[Boolean](kind = 29, name = "IS_EXTERNAL", default = false)

  /** Specifies whether a parameter is the variadic argument handling parameter of a variadic method. Only one parameter
    * of a method is allowed to have this property set to true.
    */
  val IsVariadic = flatgraph.SinglePropertyKey[Boolean](kind = 30, name = "IS_VARIADIC", default = false)

  /** Specifies whether this is a wildcard import. For a Java import like "import java.nio.*;" IS_WILDCARD would be
    * "true" and IMPORTED_ENTITY would be "java.nio". For wildcard imports the IMPORTED_AS property is ignored.
    */
  val IsWildcard = flatgraph.OptionalPropertyKey[Boolean](kind = 31, name = "IS_WILDCARD")

  /** This property denotes a key of a key-value pair. */
  val Key = flatgraph.SinglePropertyKey[String](kind = 32, name = "KEY", default = "<empty>")

  /** This field indicates which CPG language frontend generated the CPG. Frontend developers may freely choose a value
    * that describes their frontend so long as it is not used by an existing frontend. Reserved values are to date: C,
    * LLVM, GHIDRA, PHP.
    */
  val Language = flatgraph.SinglePropertyKey[String](kind = 33, name = "LANGUAGE", default = "<empty>")

  /** This optional field provides the line number of the program construct represented by the node.
    */
  val LineNumber = flatgraph.OptionalPropertyKey[Int](kind = 34, name = "LINE_NUMBER")

  /** This optional fields provides the line number at which the program construct represented by the node ends.
    */
  val LineNumberEnd = flatgraph.OptionalPropertyKey[Int](kind = 35, name = "LINE_NUMBER_END")

  /** The FULL_NAME of a method. Used to link CALL and METHOD nodes. It is required to have exactly one METHOD node for
    * each METHOD_FULL_NAME
    */
  val MethodFullName = flatgraph.SinglePropertyKey[String](kind = 36, name = "METHOD_FULL_NAME", default = "<empty>")

  val MethodShortName = flatgraph.SinglePropertyKey[String](kind = 37, name = "METHOD_SHORT_NAME", default = "<empty>")

  /** The modifier type is a free-form string. The following are known modifier types: `STATIC`, `PUBLIC`, `PROTECTED`,
    * `PRIVATE`, `ABSTRACT`, `NATIVE`, `CONSTRUCTOR`, `VIRTUAL`.
    */
  val ModifierType = flatgraph.SinglePropertyKey[String](kind = 38, name = "MODIFIER_TYPE", default = "<empty>")

  /** Name of represented object, e.g., method name (e.g. "run") */
  val Name = flatgraph.SinglePropertyKey[String](kind = 39, name = "NAME", default = "<empty>")

  val NodeLabel = flatgraph.SinglePropertyKey[String](kind = 40, name = "NODE_LABEL", default = "<empty>")

  /** Start offset into the CONTENT property of the corresponding FILE node. The offset is such that parts of the
    * content can easily be accessed via `content.substring(offset, offsetEnd)`. This means that the offset must be
    * measured in utf16 encoding (i.e. neither in characters/codeunits nor in byte-offsets into a utf8 encoding). E.g.
    * for METHOD nodes this start offset points to the start of the methods source code in the string holding the source
    * code of the entire file.
    */
  val Offset = flatgraph.OptionalPropertyKey[Int](kind = 41, name = "OFFSET")

  /** End offset (exclusive) into the CONTENT property of the corresponding FILE node. See OFFSET documentation for
    * finer details. E.g. for METHOD nodes this end offset points to the first code position which is not part of the
    * method.
    */
  val OffsetEnd = flatgraph.OptionalPropertyKey[Int](kind = 42, name = "OFFSET_END")

  /** This integer indicates the position of the node among its siblings in the AST. The left-most child has an order of
    * 0.
    */
  val Order = flatgraph.SinglePropertyKey[Int](kind = 43, name = "ORDER", default = -1: Int)

  /** The field contains the names of the overlays applied to this CPG, in order of their application. Names are
    * free-form strings, that is, this specification does not dictate them but rather requires tool producers and
    * consumers to communicate them between each other.
    */
  val Overlays = flatgraph.MultiPropertyKey[String](kind = 44, name = "OVERLAYS")

  val PackageName = flatgraph.SinglePropertyKey[String](kind = 45, name = "PACKAGE_NAME", default = "<empty>")

  /** AST node type name emitted by parser. */
  val ParserTypeName = flatgraph.SinglePropertyKey[String](kind = 46, name = "PARSER_TYPE_NAME", default = "<empty>")

  /** Similar to `DYNAMIC_TYPE_HINT_FULL_NAME`, but that this makes no guarantee that types within this property are
    * correct. This property is used to capture observations between node interactions during a 'may-analysis'.
    */
  val PossibleTypes = flatgraph.MultiPropertyKey[String](kind = 47, name = "POSSIBLE_TYPES")

  /** The path to the root directory of the source/binary this CPG is generated from. */
  val Root = flatgraph.SinglePropertyKey[String](kind = 48, name = "ROOT", default = "<empty>")

  /** The method signature encodes the types of parameters in a string. The string SHOULD be human readable and suitable
    * for differentiating methods with different parameter types sufficiently to allow for resolving of function
    * overloading. The present specification does not enforce a strict format for the signature, that is, it can be
    * chosen by the frontend implementor to fit the source language.
    */
  val Signature = flatgraph.SinglePropertyKey[String](kind = 49, name = "SIGNATURE", default = "")

  val Symbol = flatgraph.SinglePropertyKey[String](kind = 50, name = "SYMBOL", default = "<empty>")

  /** The static type decl of a TYPE. This property is matched against the FULL_NAME of TYPE_DECL nodes. It is required
    * to have exactly one TYPE_DECL for each different TYPE_DECL_FULL_NAME
    */
  val TypeDeclFullName =
    flatgraph.SinglePropertyKey[String](kind = 51, name = "TYPE_DECL_FULL_NAME", default = "<empty>")

  /** This field contains the fully-qualified static type name of the program construct represented by a node. It is the
    * name of an instantiated type, e.g., `java.util.List<Integer>`, rather than `java.util.List[T]`. If the type cannot
    * be determined, this field should be set to the empty string.
    */
  val TypeFullName = flatgraph.SinglePropertyKey[String](kind = 52, name = "TYPE_FULL_NAME", default = "<empty>")

  /** This property denotes a string value as used in a key-value pair. */
  val Value = flatgraph.SinglePropertyKey[String](kind = 53, name = "VALUE", default = "")

  /** A version, given as a string. Used, for example, in the META_DATA node to indicate which version of the CPG spec
    * this CPG conforms to
    */
  val Version = flatgraph.SinglePropertyKey[String](kind = 54, name = "VERSION", default = "<empty>")
}
