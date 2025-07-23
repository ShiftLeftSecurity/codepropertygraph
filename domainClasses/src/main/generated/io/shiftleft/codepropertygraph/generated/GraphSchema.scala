package io.shiftleft.codepropertygraph.generated

import flatgraph.FormalQtyType

object GraphSchema extends flatgraph.Schema {
  private val nodeLabels = IndexedSeq(
    "ANNOTATION",
    "ANNOTATION_LITERAL",
    "ANNOTATION_PARAMETER",
    "ANNOTATION_PARAMETER_ASSIGN",
    "ARRAY_INITIALIZER",
    "BINDING",
    "BLOCK",
    "CALL",
    "CLOSURE_BINDING",
    "COMMENT",
    "CONFIG_FILE",
    "CONTROL_STRUCTURE",
    "DEPENDENCY",
    "FIELD_IDENTIFIER",
    "FILE",
    "FINDING",
    "IDENTIFIER",
    "IMPORT",
    "JUMP_LABEL",
    "JUMP_TARGET",
    "KEY_VALUE_PAIR",
    "LITERAL",
    "LOCAL",
    "MEMBER",
    "META_DATA",
    "METHOD",
    "METHOD_PARAMETER_IN",
    "METHOD_PARAMETER_OUT",
    "METHOD_REF",
    "METHOD_RETURN",
    "MODIFIER",
    "NAMESPACE",
    "NAMESPACE_BLOCK",
    "RETURN",
    "TAG",
    "TAG_NODE_PAIR",
    "TEMPLATE_DOM",
    "TYPE",
    "TYPE_ARGUMENT",
    "TYPE_DECL",
    "TYPE_PARAMETER",
    "TYPE_REF",
    "UNKNOWN"
  )
  val nodeKindByLabel = nodeLabels.zipWithIndex.toMap
  val edgeLabels: Array[String] = Array(
    "ALIAS_OF",
    "ARGUMENT",
    "AST",
    "BINDS",
    "BINDS_TO",
    "CALL",
    "CAPTURE",
    "CAPTURED_BY",
    "CDG",
    "CFG",
    "CONDITION",
    "CONTAINS",
    "DOMINATE",
    "EVAL_TYPE",
    "IMPORTS",
    "INHERITS_FROM",
    "IS_CALL_FOR_IMPORT",
    "PARAMETER_LINK",
    "POST_DOMINATE",
    "REACHING_DEF",
    "RECEIVER",
    "REF",
    "SOURCE_FILE",
    "TAGGED_BY"
  )
  val edgeKindByLabel = edgeLabels.zipWithIndex.toMap
  val edgePropertyAllocators: Array[Int => Array[?]] = Array(
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => null,
    size => Array.fill(size)("<empty>") /* label = REACHING_DEF, id = 19 */,
    size => null,
    size => null,
    size => null,
    size => null
  )
  val nodeFactories: Array[(flatgraph.Graph, Int) => nodes.StoredNode] = Array(
    (g, seq) => new nodes.Annotation(g, seq),
    (g, seq) => new nodes.AnnotationLiteral(g, seq),
    (g, seq) => new nodes.AnnotationParameter(g, seq),
    (g, seq) => new nodes.AnnotationParameterAssign(g, seq),
    (g, seq) => new nodes.ArrayInitializer(g, seq),
    (g, seq) => new nodes.Binding(g, seq),
    (g, seq) => new nodes.Block(g, seq),
    (g, seq) => new nodes.Call(g, seq),
    (g, seq) => new nodes.ClosureBinding(g, seq),
    (g, seq) => new nodes.Comment(g, seq),
    (g, seq) => new nodes.ConfigFile(g, seq),
    (g, seq) => new nodes.ControlStructure(g, seq),
    (g, seq) => new nodes.Dependency(g, seq),
    (g, seq) => new nodes.FieldIdentifier(g, seq),
    (g, seq) => new nodes.File(g, seq),
    (g, seq) => new nodes.Finding(g, seq),
    (g, seq) => new nodes.Identifier(g, seq),
    (g, seq) => new nodes.Import(g, seq),
    (g, seq) => new nodes.JumpLabel(g, seq),
    (g, seq) => new nodes.JumpTarget(g, seq),
    (g, seq) => new nodes.KeyValuePair(g, seq),
    (g, seq) => new nodes.Literal(g, seq),
    (g, seq) => new nodes.Local(g, seq),
    (g, seq) => new nodes.Member(g, seq),
    (g, seq) => new nodes.MetaData(g, seq),
    (g, seq) => new nodes.Method(g, seq),
    (g, seq) => new nodes.MethodParameterIn(g, seq),
    (g, seq) => new nodes.MethodParameterOut(g, seq),
    (g, seq) => new nodes.MethodRef(g, seq),
    (g, seq) => new nodes.MethodReturn(g, seq),
    (g, seq) => new nodes.Modifier(g, seq),
    (g, seq) => new nodes.Namespace(g, seq),
    (g, seq) => new nodes.NamespaceBlock(g, seq),
    (g, seq) => new nodes.Return(g, seq),
    (g, seq) => new nodes.Tag(g, seq),
    (g, seq) => new nodes.TagNodePair(g, seq),
    (g, seq) => new nodes.TemplateDom(g, seq),
    (g, seq) => new nodes.Type(g, seq),
    (g, seq) => new nodes.TypeArgument(g, seq),
    (g, seq) => new nodes.TypeDecl(g, seq),
    (g, seq) => new nodes.TypeParameter(g, seq),
    (g, seq) => new nodes.TypeRef(g, seq),
    (g, seq) => new nodes.Unknown(g, seq)
  )
  val edgeFactories: Array[(flatgraph.GNode, flatgraph.GNode, Int, Any) => flatgraph.Edge] = Array(
    (s, d, subseq, p) => new edges.AliasOf(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Argument(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Ast(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Binds(s, d, subseq, p),
    (s, d, subseq, p) => new edges.BindsTo(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Call(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Capture(s, d, subseq, p),
    (s, d, subseq, p) => new edges.CapturedBy(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Cdg(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Cfg(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Condition(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Contains(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Dominate(s, d, subseq, p),
    (s, d, subseq, p) => new edges.EvalType(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Imports(s, d, subseq, p),
    (s, d, subseq, p) => new edges.InheritsFrom(s, d, subseq, p),
    (s, d, subseq, p) => new edges.IsCallForImport(s, d, subseq, p),
    (s, d, subseq, p) => new edges.ParameterLink(s, d, subseq, p),
    (s, d, subseq, p) => new edges.PostDominate(s, d, subseq, p),
    (s, d, subseq, p) => new edges.ReachingDef(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Receiver(s, d, subseq, p),
    (s, d, subseq, p) => new edges.Ref(s, d, subseq, p),
    (s, d, subseq, p) => new edges.SourceFile(s, d, subseq, p),
    (s, d, subseq, p) => new edges.TaggedBy(s, d, subseq, p)
  )
  val nodePropertyAllocators: Array[Int => Array[?]] = Array(
    size => new Array[String](size),
    size => new Array[Int](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[Int](size),
    size => new Array[Int](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[Boolean](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[Int](size),
    size => new Array[String](size),
    size => new Array[Boolean](size),
    size => new Array[Boolean](size),
    size => new Array[Boolean](size),
    size => new Array[Boolean](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[Int](size),
    size => new Array[Int](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[Int](size),
    size => new Array[Int](size),
    size => new Array[Int](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[String](size),
    size => new Array[flatgraph.GNode](size),
    size => new Array[flatgraph.GNode](size),
    size => new Array[flatgraph.GNode](size),
    size => new Array[flatgraph.GNode](size)
  )
  val normalNodePropertyNames: Array[String] = Array(
    "ALIAS_TYPE_FULL_NAME",
    "ARGUMENT_INDEX",
    "ARGUMENT_NAME",
    "AST_PARENT_FULL_NAME",
    "AST_PARENT_TYPE",
    "CANONICAL_NAME",
    "CLOSURE_BINDING_ID",
    "CODE",
    "COLUMN_NUMBER",
    "COLUMN_NUMBER_END",
    "CONTAINED_REF",
    "CONTENT",
    "CONTROL_STRUCTURE_TYPE",
    "DEPENDENCY_GROUP_ID",
    "DISPATCH_TYPE",
    "DYNAMIC_TYPE_HINT_FULL_NAME",
    "EVALUATION_STRATEGY",
    "EVIDENCE_DESCRIPTION",
    "EXPLICIT_AS",
    "FILENAME",
    "FULL_NAME",
    "GENERIC_SIGNATURE",
    "HASH",
    "IMPORTED_AS",
    "IMPORTED_ENTITY",
    "INDEX",
    "INHERITS_FROM_TYPE_FULL_NAME",
    "IS_EXPLICIT",
    "IS_EXTERNAL",
    "IS_VARIADIC",
    "IS_WILDCARD",
    "KEY",
    "LANGUAGE",
    "LINE_NUMBER",
    "LINE_NUMBER_END",
    "METHOD_FULL_NAME",
    "MODIFIER_TYPE",
    "NAME",
    "OFFSET",
    "OFFSET_END",
    "ORDER",
    "OVERLAYS",
    "PARSER_TYPE_NAME",
    "POSSIBLE_TYPES",
    "ROOT",
    "SIGNATURE",
    "STATIC_RECEIVER",
    "TYPE_DECL_FULL_NAME",
    "TYPE_FULL_NAME",
    "VALUE",
    "VERSION"
  )
  val nodePropertyByLabel = normalNodePropertyNames.zipWithIndex.toMap
    .updated("evidence", 51)
    .updated("keyValuePairs", 52)
    .updated("node", 53)
    .updated("tag", 54)
  val nodePropertyDescriptors: Array[FormalQtyType.FormalQuantity | FormalQtyType.FormalType] = {
    val nodePropertyDescriptors = new Array[FormalQtyType.FormalQuantity | FormalQtyType.FormalType](4730)
    for (idx <- Range(0, 4730)) {
      nodePropertyDescriptors(idx) =
        if ((idx & 1) == 0) FormalQtyType.NothingType
        else FormalQtyType.QtyNone
    }

    nodePropertyDescriptors(86) = FormalQtyType.IntType // ANNOTATION.ARGUMENT_INDEX
    nodePropertyDescriptors(87) = FormalQtyType.QtyOne
    nodePropertyDescriptors(172) = FormalQtyType.StringType // ANNOTATION.ARGUMENT_NAME
    nodePropertyDescriptors(173) = FormalQtyType.QtyOption
    nodePropertyDescriptors(602) = FormalQtyType.StringType // ANNOTATION.CODE
    nodePropertyDescriptors(603) = FormalQtyType.QtyOne
    nodePropertyDescriptors(688) = FormalQtyType.IntType // ANNOTATION.COLUMN_NUMBER
    nodePropertyDescriptors(689) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1720) = FormalQtyType.StringType // ANNOTATION.FULL_NAME
    nodePropertyDescriptors(1721) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2838) = FormalQtyType.IntType // ANNOTATION.LINE_NUMBER
    nodePropertyDescriptors(2839) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3182) = FormalQtyType.StringType // ANNOTATION.NAME
    nodePropertyDescriptors(3183) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3268) = FormalQtyType.IntType // ANNOTATION.OFFSET
    nodePropertyDescriptors(3269) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3354) = FormalQtyType.IntType // ANNOTATION.OFFSET_END
    nodePropertyDescriptors(3355) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3440) = FormalQtyType.IntType // ANNOTATION.ORDER
    nodePropertyDescriptors(3441) = FormalQtyType.QtyOne
    nodePropertyDescriptors(88) = FormalQtyType.IntType // ANNOTATION_LITERAL.ARGUMENT_INDEX
    nodePropertyDescriptors(89) = FormalQtyType.QtyOne
    nodePropertyDescriptors(174) = FormalQtyType.StringType // ANNOTATION_LITERAL.ARGUMENT_NAME
    nodePropertyDescriptors(175) = FormalQtyType.QtyOption
    nodePropertyDescriptors(604) = FormalQtyType.StringType // ANNOTATION_LITERAL.CODE
    nodePropertyDescriptors(605) = FormalQtyType.QtyOne
    nodePropertyDescriptors(690) = FormalQtyType.IntType // ANNOTATION_LITERAL.COLUMN_NUMBER
    nodePropertyDescriptors(691) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2840) = FormalQtyType.IntType // ANNOTATION_LITERAL.LINE_NUMBER
    nodePropertyDescriptors(2841) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3184) = FormalQtyType.StringType // ANNOTATION_LITERAL.NAME
    nodePropertyDescriptors(3185) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3270) = FormalQtyType.IntType // ANNOTATION_LITERAL.OFFSET
    nodePropertyDescriptors(3271) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3356) = FormalQtyType.IntType // ANNOTATION_LITERAL.OFFSET_END
    nodePropertyDescriptors(3357) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3442) = FormalQtyType.IntType // ANNOTATION_LITERAL.ORDER
    nodePropertyDescriptors(3443) = FormalQtyType.QtyOne
    nodePropertyDescriptors(606) = FormalQtyType.StringType // ANNOTATION_PARAMETER.CODE
    nodePropertyDescriptors(607) = FormalQtyType.QtyOne
    nodePropertyDescriptors(692) = FormalQtyType.IntType // ANNOTATION_PARAMETER.COLUMN_NUMBER
    nodePropertyDescriptors(693) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2842) = FormalQtyType.IntType // ANNOTATION_PARAMETER.LINE_NUMBER
    nodePropertyDescriptors(2843) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3272) = FormalQtyType.IntType // ANNOTATION_PARAMETER.OFFSET
    nodePropertyDescriptors(3273) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3358) = FormalQtyType.IntType // ANNOTATION_PARAMETER.OFFSET_END
    nodePropertyDescriptors(3359) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3444) = FormalQtyType.IntType // ANNOTATION_PARAMETER.ORDER
    nodePropertyDescriptors(3445) = FormalQtyType.QtyOne
    nodePropertyDescriptors(608) = FormalQtyType.StringType // ANNOTATION_PARAMETER_ASSIGN.CODE
    nodePropertyDescriptors(609) = FormalQtyType.QtyOne
    nodePropertyDescriptors(694) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.COLUMN_NUMBER
    nodePropertyDescriptors(695) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2844) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.LINE_NUMBER
    nodePropertyDescriptors(2845) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3274) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.OFFSET
    nodePropertyDescriptors(3275) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3360) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.OFFSET_END
    nodePropertyDescriptors(3361) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3446) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.ORDER
    nodePropertyDescriptors(3447) = FormalQtyType.QtyOne
    nodePropertyDescriptors(94) = FormalQtyType.IntType // ARRAY_INITIALIZER.ARGUMENT_INDEX
    nodePropertyDescriptors(95) = FormalQtyType.QtyOne
    nodePropertyDescriptors(180) = FormalQtyType.StringType // ARRAY_INITIALIZER.ARGUMENT_NAME
    nodePropertyDescriptors(181) = FormalQtyType.QtyOption
    nodePropertyDescriptors(610) = FormalQtyType.StringType // ARRAY_INITIALIZER.CODE
    nodePropertyDescriptors(611) = FormalQtyType.QtyOne
    nodePropertyDescriptors(696) = FormalQtyType.IntType // ARRAY_INITIALIZER.COLUMN_NUMBER
    nodePropertyDescriptors(697) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2846) = FormalQtyType.IntType // ARRAY_INITIALIZER.LINE_NUMBER
    nodePropertyDescriptors(2847) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3276) = FormalQtyType.IntType // ARRAY_INITIALIZER.OFFSET
    nodePropertyDescriptors(3277) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3362) = FormalQtyType.IntType // ARRAY_INITIALIZER.OFFSET_END
    nodePropertyDescriptors(3363) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3448) = FormalQtyType.IntType // ARRAY_INITIALIZER.ORDER
    nodePropertyDescriptors(3449) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3020) = FormalQtyType.StringType // BINDING.METHOD_FULL_NAME
    nodePropertyDescriptors(3021) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3192) = FormalQtyType.StringType // BINDING.NAME
    nodePropertyDescriptors(3193) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3880) = FormalQtyType.StringType // BINDING.SIGNATURE
    nodePropertyDescriptors(3881) = FormalQtyType.QtyOne
    nodePropertyDescriptors(98) = FormalQtyType.IntType // BLOCK.ARGUMENT_INDEX
    nodePropertyDescriptors(99) = FormalQtyType.QtyOne
    nodePropertyDescriptors(184) = FormalQtyType.StringType // BLOCK.ARGUMENT_NAME
    nodePropertyDescriptors(185) = FormalQtyType.QtyOption
    nodePropertyDescriptors(614) = FormalQtyType.StringType // BLOCK.CODE
    nodePropertyDescriptors(615) = FormalQtyType.QtyOne
    nodePropertyDescriptors(700) = FormalQtyType.IntType // BLOCK.COLUMN_NUMBER
    nodePropertyDescriptors(701) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1302) = FormalQtyType.StringType // BLOCK.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1303) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(2850) = FormalQtyType.IntType // BLOCK.LINE_NUMBER
    nodePropertyDescriptors(2851) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3280) = FormalQtyType.IntType // BLOCK.OFFSET
    nodePropertyDescriptors(3281) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3366) = FormalQtyType.IntType // BLOCK.OFFSET_END
    nodePropertyDescriptors(3367) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3452) = FormalQtyType.IntType // BLOCK.ORDER
    nodePropertyDescriptors(3453) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3710) = FormalQtyType.StringType // BLOCK.POSSIBLE_TYPES
    nodePropertyDescriptors(3711) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4140) = FormalQtyType.StringType // BLOCK.TYPE_FULL_NAME
    nodePropertyDescriptors(4141) = FormalQtyType.QtyOne
    nodePropertyDescriptors(100) = FormalQtyType.IntType // CALL.ARGUMENT_INDEX
    nodePropertyDescriptors(101) = FormalQtyType.QtyOne
    nodePropertyDescriptors(186) = FormalQtyType.StringType // CALL.ARGUMENT_NAME
    nodePropertyDescriptors(187) = FormalQtyType.QtyOption
    nodePropertyDescriptors(616) = FormalQtyType.StringType // CALL.CODE
    nodePropertyDescriptors(617) = FormalQtyType.QtyOne
    nodePropertyDescriptors(702) = FormalQtyType.IntType // CALL.COLUMN_NUMBER
    nodePropertyDescriptors(703) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1218) = FormalQtyType.StringType // CALL.DISPATCH_TYPE
    nodePropertyDescriptors(1219) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1304) = FormalQtyType.StringType // CALL.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1305) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(2852) = FormalQtyType.IntType // CALL.LINE_NUMBER
    nodePropertyDescriptors(2853) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3024) = FormalQtyType.StringType // CALL.METHOD_FULL_NAME
    nodePropertyDescriptors(3025) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3196) = FormalQtyType.StringType // CALL.NAME
    nodePropertyDescriptors(3197) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3282) = FormalQtyType.IntType // CALL.OFFSET
    nodePropertyDescriptors(3283) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3368) = FormalQtyType.IntType // CALL.OFFSET_END
    nodePropertyDescriptors(3369) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3454) = FormalQtyType.IntType // CALL.ORDER
    nodePropertyDescriptors(3455) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3712) = FormalQtyType.StringType // CALL.POSSIBLE_TYPES
    nodePropertyDescriptors(3713) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3884) = FormalQtyType.StringType // CALL.SIGNATURE
    nodePropertyDescriptors(3885) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3970) = FormalQtyType.StringType // CALL.STATIC_RECEIVER
    nodePropertyDescriptors(3971) = FormalQtyType.QtyOption
    nodePropertyDescriptors(4142) = FormalQtyType.StringType // CALL.TYPE_FULL_NAME
    nodePropertyDescriptors(4143) = FormalQtyType.QtyOne
    nodePropertyDescriptors(532) = FormalQtyType.StringType // CLOSURE_BINDING.CLOSURE_BINDING_ID
    nodePropertyDescriptors(533) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1392) = FormalQtyType.StringType // CLOSURE_BINDING.EVALUATION_STRATEGY
    nodePropertyDescriptors(1393) = FormalQtyType.QtyOne
    nodePropertyDescriptors(620) = FormalQtyType.StringType // COMMENT.CODE
    nodePropertyDescriptors(621) = FormalQtyType.QtyOne
    nodePropertyDescriptors(706) = FormalQtyType.IntType // COMMENT.COLUMN_NUMBER
    nodePropertyDescriptors(707) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1652) = FormalQtyType.StringType // COMMENT.FILENAME
    nodePropertyDescriptors(1653) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2856) = FormalQtyType.IntType // COMMENT.LINE_NUMBER
    nodePropertyDescriptors(2857) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3286) = FormalQtyType.IntType // COMMENT.OFFSET
    nodePropertyDescriptors(3287) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3372) = FormalQtyType.IntType // COMMENT.OFFSET_END
    nodePropertyDescriptors(3373) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3458) = FormalQtyType.IntType // COMMENT.ORDER
    nodePropertyDescriptors(3459) = FormalQtyType.QtyOne
    nodePropertyDescriptors(966) = FormalQtyType.StringType // CONFIG_FILE.CONTENT
    nodePropertyDescriptors(967) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3202) = FormalQtyType.StringType // CONFIG_FILE.NAME
    nodePropertyDescriptors(3203) = FormalQtyType.QtyOne
    nodePropertyDescriptors(108) = FormalQtyType.IntType // CONTROL_STRUCTURE.ARGUMENT_INDEX
    nodePropertyDescriptors(109) = FormalQtyType.QtyOne
    nodePropertyDescriptors(194) = FormalQtyType.StringType // CONTROL_STRUCTURE.ARGUMENT_NAME
    nodePropertyDescriptors(195) = FormalQtyType.QtyOption
    nodePropertyDescriptors(624) = FormalQtyType.StringType // CONTROL_STRUCTURE.CODE
    nodePropertyDescriptors(625) = FormalQtyType.QtyOne
    nodePropertyDescriptors(710) = FormalQtyType.IntType // CONTROL_STRUCTURE.COLUMN_NUMBER
    nodePropertyDescriptors(711) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1054) = FormalQtyType.StringType // CONTROL_STRUCTURE.CONTROL_STRUCTURE_TYPE
    nodePropertyDescriptors(1055) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2860) = FormalQtyType.IntType // CONTROL_STRUCTURE.LINE_NUMBER
    nodePropertyDescriptors(2861) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3290) = FormalQtyType.IntType // CONTROL_STRUCTURE.OFFSET
    nodePropertyDescriptors(3291) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3376) = FormalQtyType.IntType // CONTROL_STRUCTURE.OFFSET_END
    nodePropertyDescriptors(3377) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3462) = FormalQtyType.IntType // CONTROL_STRUCTURE.ORDER
    nodePropertyDescriptors(3463) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3634) = FormalQtyType.StringType // CONTROL_STRUCTURE.PARSER_TYPE_NAME
    nodePropertyDescriptors(3635) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1142) = FormalQtyType.StringType // DEPENDENCY.DEPENDENCY_GROUP_ID
    nodePropertyDescriptors(1143) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3206) = FormalQtyType.StringType // DEPENDENCY.NAME
    nodePropertyDescriptors(3207) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4324) = FormalQtyType.StringType // DEPENDENCY.VERSION
    nodePropertyDescriptors(4325) = FormalQtyType.QtyOne
    nodePropertyDescriptors(112) = FormalQtyType.IntType // FIELD_IDENTIFIER.ARGUMENT_INDEX
    nodePropertyDescriptors(113) = FormalQtyType.QtyOne
    nodePropertyDescriptors(198) = FormalQtyType.StringType // FIELD_IDENTIFIER.ARGUMENT_NAME
    nodePropertyDescriptors(199) = FormalQtyType.QtyOption
    nodePropertyDescriptors(456) = FormalQtyType.StringType // FIELD_IDENTIFIER.CANONICAL_NAME
    nodePropertyDescriptors(457) = FormalQtyType.QtyOne
    nodePropertyDescriptors(628) = FormalQtyType.StringType // FIELD_IDENTIFIER.CODE
    nodePropertyDescriptors(629) = FormalQtyType.QtyOne
    nodePropertyDescriptors(714) = FormalQtyType.IntType // FIELD_IDENTIFIER.COLUMN_NUMBER
    nodePropertyDescriptors(715) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2864) = FormalQtyType.IntType // FIELD_IDENTIFIER.LINE_NUMBER
    nodePropertyDescriptors(2865) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3294) = FormalQtyType.IntType // FIELD_IDENTIFIER.OFFSET
    nodePropertyDescriptors(3295) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3380) = FormalQtyType.IntType // FIELD_IDENTIFIER.OFFSET_END
    nodePropertyDescriptors(3381) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3466) = FormalQtyType.IntType // FIELD_IDENTIFIER.ORDER
    nodePropertyDescriptors(3467) = FormalQtyType.QtyOne
    nodePropertyDescriptors(630) = FormalQtyType.StringType // FILE.CODE
    nodePropertyDescriptors(631) = FormalQtyType.QtyOne
    nodePropertyDescriptors(716) = FormalQtyType.IntType // FILE.COLUMN_NUMBER
    nodePropertyDescriptors(717) = FormalQtyType.QtyOption
    nodePropertyDescriptors(974) = FormalQtyType.StringType // FILE.CONTENT
    nodePropertyDescriptors(975) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1920) = FormalQtyType.StringType // FILE.HASH
    nodePropertyDescriptors(1921) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2866) = FormalQtyType.IntType // FILE.LINE_NUMBER
    nodePropertyDescriptors(2867) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3210) = FormalQtyType.StringType // FILE.NAME
    nodePropertyDescriptors(3211) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3296) = FormalQtyType.IntType // FILE.OFFSET
    nodePropertyDescriptors(3297) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3382) = FormalQtyType.IntType // FILE.OFFSET_END
    nodePropertyDescriptors(3383) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3468) = FormalQtyType.IntType // FILE.ORDER
    nodePropertyDescriptors(3469) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1492) = FormalQtyType.StringType // FINDING.EVIDENCE_DESCRIPTION
    nodePropertyDescriptors(1493) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4416) = FormalQtyType.RefType // FINDING.evidence
    nodePropertyDescriptors(4417) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4502) = FormalQtyType.RefType // FINDING.keyValuePairs
    nodePropertyDescriptors(4503) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(118) = FormalQtyType.IntType // IDENTIFIER.ARGUMENT_INDEX
    nodePropertyDescriptors(119) = FormalQtyType.QtyOne
    nodePropertyDescriptors(204) = FormalQtyType.StringType // IDENTIFIER.ARGUMENT_NAME
    nodePropertyDescriptors(205) = FormalQtyType.QtyOption
    nodePropertyDescriptors(634) = FormalQtyType.StringType // IDENTIFIER.CODE
    nodePropertyDescriptors(635) = FormalQtyType.QtyOne
    nodePropertyDescriptors(720) = FormalQtyType.IntType // IDENTIFIER.COLUMN_NUMBER
    nodePropertyDescriptors(721) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1322) = FormalQtyType.StringType // IDENTIFIER.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1323) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(2870) = FormalQtyType.IntType // IDENTIFIER.LINE_NUMBER
    nodePropertyDescriptors(2871) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3214) = FormalQtyType.StringType // IDENTIFIER.NAME
    nodePropertyDescriptors(3215) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3300) = FormalQtyType.IntType // IDENTIFIER.OFFSET
    nodePropertyDescriptors(3301) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3386) = FormalQtyType.IntType // IDENTIFIER.OFFSET_END
    nodePropertyDescriptors(3387) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3472) = FormalQtyType.IntType // IDENTIFIER.ORDER
    nodePropertyDescriptors(3473) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3730) = FormalQtyType.StringType // IDENTIFIER.POSSIBLE_TYPES
    nodePropertyDescriptors(3731) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4160) = FormalQtyType.StringType // IDENTIFIER.TYPE_FULL_NAME
    nodePropertyDescriptors(4161) = FormalQtyType.QtyOne
    nodePropertyDescriptors(636) = FormalQtyType.StringType // IMPORT.CODE
    nodePropertyDescriptors(637) = FormalQtyType.QtyOne
    nodePropertyDescriptors(722) = FormalQtyType.IntType // IMPORT.COLUMN_NUMBER
    nodePropertyDescriptors(723) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1582) = FormalQtyType.BoolType // IMPORT.EXPLICIT_AS
    nodePropertyDescriptors(1583) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2012) = FormalQtyType.StringType // IMPORT.IMPORTED_AS
    nodePropertyDescriptors(2013) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2098) = FormalQtyType.StringType // IMPORT.IMPORTED_ENTITY
    nodePropertyDescriptors(2099) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2356) = FormalQtyType.BoolType // IMPORT.IS_EXPLICIT
    nodePropertyDescriptors(2357) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2614) = FormalQtyType.BoolType // IMPORT.IS_WILDCARD
    nodePropertyDescriptors(2615) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2872) = FormalQtyType.IntType // IMPORT.LINE_NUMBER
    nodePropertyDescriptors(2873) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3302) = FormalQtyType.IntType // IMPORT.OFFSET
    nodePropertyDescriptors(3303) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3388) = FormalQtyType.IntType // IMPORT.OFFSET_END
    nodePropertyDescriptors(3389) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3474) = FormalQtyType.IntType // IMPORT.ORDER
    nodePropertyDescriptors(3475) = FormalQtyType.QtyOne
    nodePropertyDescriptors(638) = FormalQtyType.StringType // JUMP_LABEL.CODE
    nodePropertyDescriptors(639) = FormalQtyType.QtyOne
    nodePropertyDescriptors(724) = FormalQtyType.IntType // JUMP_LABEL.COLUMN_NUMBER
    nodePropertyDescriptors(725) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2874) = FormalQtyType.IntType // JUMP_LABEL.LINE_NUMBER
    nodePropertyDescriptors(2875) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3218) = FormalQtyType.StringType // JUMP_LABEL.NAME
    nodePropertyDescriptors(3219) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3304) = FormalQtyType.IntType // JUMP_LABEL.OFFSET
    nodePropertyDescriptors(3305) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3390) = FormalQtyType.IntType // JUMP_LABEL.OFFSET_END
    nodePropertyDescriptors(3391) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3476) = FormalQtyType.IntType // JUMP_LABEL.ORDER
    nodePropertyDescriptors(3477) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3648) = FormalQtyType.StringType // JUMP_LABEL.PARSER_TYPE_NAME
    nodePropertyDescriptors(3649) = FormalQtyType.QtyOne
    nodePropertyDescriptors(124) = FormalQtyType.IntType // JUMP_TARGET.ARGUMENT_INDEX
    nodePropertyDescriptors(125) = FormalQtyType.QtyOne
    nodePropertyDescriptors(640) = FormalQtyType.StringType // JUMP_TARGET.CODE
    nodePropertyDescriptors(641) = FormalQtyType.QtyOne
    nodePropertyDescriptors(726) = FormalQtyType.IntType // JUMP_TARGET.COLUMN_NUMBER
    nodePropertyDescriptors(727) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2876) = FormalQtyType.IntType // JUMP_TARGET.LINE_NUMBER
    nodePropertyDescriptors(2877) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3220) = FormalQtyType.StringType // JUMP_TARGET.NAME
    nodePropertyDescriptors(3221) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3306) = FormalQtyType.IntType // JUMP_TARGET.OFFSET
    nodePropertyDescriptors(3307) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3392) = FormalQtyType.IntType // JUMP_TARGET.OFFSET_END
    nodePropertyDescriptors(3393) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3478) = FormalQtyType.IntType // JUMP_TARGET.ORDER
    nodePropertyDescriptors(3479) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3650) = FormalQtyType.StringType // JUMP_TARGET.PARSER_TYPE_NAME
    nodePropertyDescriptors(3651) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2706) = FormalQtyType.StringType // KEY_VALUE_PAIR.KEY
    nodePropertyDescriptors(2707) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4254) = FormalQtyType.StringType // KEY_VALUE_PAIR.VALUE
    nodePropertyDescriptors(4255) = FormalQtyType.QtyOne
    nodePropertyDescriptors(128) = FormalQtyType.IntType // LITERAL.ARGUMENT_INDEX
    nodePropertyDescriptors(129) = FormalQtyType.QtyOne
    nodePropertyDescriptors(214) = FormalQtyType.StringType // LITERAL.ARGUMENT_NAME
    nodePropertyDescriptors(215) = FormalQtyType.QtyOption
    nodePropertyDescriptors(644) = FormalQtyType.StringType // LITERAL.CODE
    nodePropertyDescriptors(645) = FormalQtyType.QtyOne
    nodePropertyDescriptors(730) = FormalQtyType.IntType // LITERAL.COLUMN_NUMBER
    nodePropertyDescriptors(731) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1332) = FormalQtyType.StringType // LITERAL.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1333) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(2880) = FormalQtyType.IntType // LITERAL.LINE_NUMBER
    nodePropertyDescriptors(2881) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3310) = FormalQtyType.IntType // LITERAL.OFFSET
    nodePropertyDescriptors(3311) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3396) = FormalQtyType.IntType // LITERAL.OFFSET_END
    nodePropertyDescriptors(3397) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3482) = FormalQtyType.IntType // LITERAL.ORDER
    nodePropertyDescriptors(3483) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3740) = FormalQtyType.StringType // LITERAL.POSSIBLE_TYPES
    nodePropertyDescriptors(3741) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4170) = FormalQtyType.StringType // LITERAL.TYPE_FULL_NAME
    nodePropertyDescriptors(4171) = FormalQtyType.QtyOne
    nodePropertyDescriptors(560) = FormalQtyType.StringType // LOCAL.CLOSURE_BINDING_ID
    nodePropertyDescriptors(561) = FormalQtyType.QtyOption
    nodePropertyDescriptors(646) = FormalQtyType.StringType // LOCAL.CODE
    nodePropertyDescriptors(647) = FormalQtyType.QtyOne
    nodePropertyDescriptors(732) = FormalQtyType.IntType // LOCAL.COLUMN_NUMBER
    nodePropertyDescriptors(733) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1334) = FormalQtyType.StringType // LOCAL.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1335) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(1850) = FormalQtyType.StringType // LOCAL.GENERIC_SIGNATURE
    nodePropertyDescriptors(1851) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2882) = FormalQtyType.IntType // LOCAL.LINE_NUMBER
    nodePropertyDescriptors(2883) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3226) = FormalQtyType.StringType // LOCAL.NAME
    nodePropertyDescriptors(3227) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3312) = FormalQtyType.IntType // LOCAL.OFFSET
    nodePropertyDescriptors(3313) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3398) = FormalQtyType.IntType // LOCAL.OFFSET_END
    nodePropertyDescriptors(3399) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3484) = FormalQtyType.IntType // LOCAL.ORDER
    nodePropertyDescriptors(3485) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3742) = FormalQtyType.StringType // LOCAL.POSSIBLE_TYPES
    nodePropertyDescriptors(3743) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4172) = FormalQtyType.StringType // LOCAL.TYPE_FULL_NAME
    nodePropertyDescriptors(4173) = FormalQtyType.QtyOne
    nodePropertyDescriptors(304) = FormalQtyType.StringType // MEMBER.AST_PARENT_FULL_NAME
    nodePropertyDescriptors(305) = FormalQtyType.QtyOne
    nodePropertyDescriptors(390) = FormalQtyType.StringType // MEMBER.AST_PARENT_TYPE
    nodePropertyDescriptors(391) = FormalQtyType.QtyOne
    nodePropertyDescriptors(648) = FormalQtyType.StringType // MEMBER.CODE
    nodePropertyDescriptors(649) = FormalQtyType.QtyOne
    nodePropertyDescriptors(734) = FormalQtyType.IntType // MEMBER.COLUMN_NUMBER
    nodePropertyDescriptors(735) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1336) = FormalQtyType.StringType // MEMBER.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1337) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(1852) = FormalQtyType.StringType // MEMBER.GENERIC_SIGNATURE
    nodePropertyDescriptors(1853) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2884) = FormalQtyType.IntType // MEMBER.LINE_NUMBER
    nodePropertyDescriptors(2885) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3228) = FormalQtyType.StringType // MEMBER.NAME
    nodePropertyDescriptors(3229) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3314) = FormalQtyType.IntType // MEMBER.OFFSET
    nodePropertyDescriptors(3315) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3400) = FormalQtyType.IntType // MEMBER.OFFSET_END
    nodePropertyDescriptors(3401) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3486) = FormalQtyType.IntType // MEMBER.ORDER
    nodePropertyDescriptors(3487) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3744) = FormalQtyType.StringType // MEMBER.POSSIBLE_TYPES
    nodePropertyDescriptors(3745) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4174) = FormalQtyType.StringType // MEMBER.TYPE_FULL_NAME
    nodePropertyDescriptors(4175) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1940) = FormalQtyType.StringType // META_DATA.HASH
    nodePropertyDescriptors(1941) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2800) = FormalQtyType.StringType // META_DATA.LANGUAGE
    nodePropertyDescriptors(2801) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3574) = FormalQtyType.StringType // META_DATA.OVERLAYS
    nodePropertyDescriptors(3575) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3832) = FormalQtyType.StringType // META_DATA.ROOT
    nodePropertyDescriptors(3833) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4348) = FormalQtyType.StringType // META_DATA.VERSION
    nodePropertyDescriptors(4349) = FormalQtyType.QtyOne
    nodePropertyDescriptors(308) = FormalQtyType.StringType // METHOD.AST_PARENT_FULL_NAME
    nodePropertyDescriptors(309) = FormalQtyType.QtyOne
    nodePropertyDescriptors(394) = FormalQtyType.StringType // METHOD.AST_PARENT_TYPE
    nodePropertyDescriptors(395) = FormalQtyType.QtyOne
    nodePropertyDescriptors(652) = FormalQtyType.StringType // METHOD.CODE
    nodePropertyDescriptors(653) = FormalQtyType.QtyOne
    nodePropertyDescriptors(738) = FormalQtyType.IntType // METHOD.COLUMN_NUMBER
    nodePropertyDescriptors(739) = FormalQtyType.QtyOption
    nodePropertyDescriptors(824) = FormalQtyType.IntType // METHOD.COLUMN_NUMBER_END
    nodePropertyDescriptors(825) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1684) = FormalQtyType.StringType // METHOD.FILENAME
    nodePropertyDescriptors(1685) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1770) = FormalQtyType.StringType // METHOD.FULL_NAME
    nodePropertyDescriptors(1771) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1856) = FormalQtyType.StringType // METHOD.GENERIC_SIGNATURE
    nodePropertyDescriptors(1857) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1942) = FormalQtyType.StringType // METHOD.HASH
    nodePropertyDescriptors(1943) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2458) = FormalQtyType.BoolType // METHOD.IS_EXTERNAL
    nodePropertyDescriptors(2459) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2888) = FormalQtyType.IntType // METHOD.LINE_NUMBER
    nodePropertyDescriptors(2889) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2974) = FormalQtyType.IntType // METHOD.LINE_NUMBER_END
    nodePropertyDescriptors(2975) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3232) = FormalQtyType.StringType // METHOD.NAME
    nodePropertyDescriptors(3233) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3318) = FormalQtyType.IntType // METHOD.OFFSET
    nodePropertyDescriptors(3319) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3404) = FormalQtyType.IntType // METHOD.OFFSET_END
    nodePropertyDescriptors(3405) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3490) = FormalQtyType.IntType // METHOD.ORDER
    nodePropertyDescriptors(3491) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3920) = FormalQtyType.StringType // METHOD.SIGNATURE
    nodePropertyDescriptors(3921) = FormalQtyType.QtyOne
    nodePropertyDescriptors(568) = FormalQtyType.StringType // METHOD_PARAMETER_IN.CLOSURE_BINDING_ID
    nodePropertyDescriptors(569) = FormalQtyType.QtyOption
    nodePropertyDescriptors(654) = FormalQtyType.StringType // METHOD_PARAMETER_IN.CODE
    nodePropertyDescriptors(655) = FormalQtyType.QtyOne
    nodePropertyDescriptors(740) = FormalQtyType.IntType // METHOD_PARAMETER_IN.COLUMN_NUMBER
    nodePropertyDescriptors(741) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1342) = FormalQtyType.StringType // METHOD_PARAMETER_IN.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1343) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(1428) = FormalQtyType.StringType // METHOD_PARAMETER_IN.EVALUATION_STRATEGY
    nodePropertyDescriptors(1429) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2202) = FormalQtyType.IntType // METHOD_PARAMETER_IN.INDEX
    nodePropertyDescriptors(2203) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2546) = FormalQtyType.BoolType // METHOD_PARAMETER_IN.IS_VARIADIC
    nodePropertyDescriptors(2547) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2890) = FormalQtyType.IntType // METHOD_PARAMETER_IN.LINE_NUMBER
    nodePropertyDescriptors(2891) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3234) = FormalQtyType.StringType // METHOD_PARAMETER_IN.NAME
    nodePropertyDescriptors(3235) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3320) = FormalQtyType.IntType // METHOD_PARAMETER_IN.OFFSET
    nodePropertyDescriptors(3321) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3406) = FormalQtyType.IntType // METHOD_PARAMETER_IN.OFFSET_END
    nodePropertyDescriptors(3407) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3492) = FormalQtyType.IntType // METHOD_PARAMETER_IN.ORDER
    nodePropertyDescriptors(3493) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3750) = FormalQtyType.StringType // METHOD_PARAMETER_IN.POSSIBLE_TYPES
    nodePropertyDescriptors(3751) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4180) = FormalQtyType.StringType // METHOD_PARAMETER_IN.TYPE_FULL_NAME
    nodePropertyDescriptors(4181) = FormalQtyType.QtyOne
    nodePropertyDescriptors(656) = FormalQtyType.StringType // METHOD_PARAMETER_OUT.CODE
    nodePropertyDescriptors(657) = FormalQtyType.QtyOne
    nodePropertyDescriptors(742) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.COLUMN_NUMBER
    nodePropertyDescriptors(743) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1430) = FormalQtyType.StringType // METHOD_PARAMETER_OUT.EVALUATION_STRATEGY
    nodePropertyDescriptors(1431) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2204) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.INDEX
    nodePropertyDescriptors(2205) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2548) = FormalQtyType.BoolType // METHOD_PARAMETER_OUT.IS_VARIADIC
    nodePropertyDescriptors(2549) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2892) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.LINE_NUMBER
    nodePropertyDescriptors(2893) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3236) = FormalQtyType.StringType // METHOD_PARAMETER_OUT.NAME
    nodePropertyDescriptors(3237) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3322) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.OFFSET
    nodePropertyDescriptors(3323) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3408) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.OFFSET_END
    nodePropertyDescriptors(3409) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3494) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.ORDER
    nodePropertyDescriptors(3495) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4182) = FormalQtyType.StringType // METHOD_PARAMETER_OUT.TYPE_FULL_NAME
    nodePropertyDescriptors(4183) = FormalQtyType.QtyOne
    nodePropertyDescriptors(142) = FormalQtyType.IntType // METHOD_REF.ARGUMENT_INDEX
    nodePropertyDescriptors(143) = FormalQtyType.QtyOne
    nodePropertyDescriptors(228) = FormalQtyType.StringType // METHOD_REF.ARGUMENT_NAME
    nodePropertyDescriptors(229) = FormalQtyType.QtyOption
    nodePropertyDescriptors(658) = FormalQtyType.StringType // METHOD_REF.CODE
    nodePropertyDescriptors(659) = FormalQtyType.QtyOne
    nodePropertyDescriptors(744) = FormalQtyType.IntType // METHOD_REF.COLUMN_NUMBER
    nodePropertyDescriptors(745) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1346) = FormalQtyType.StringType // METHOD_REF.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1347) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(2894) = FormalQtyType.IntType // METHOD_REF.LINE_NUMBER
    nodePropertyDescriptors(2895) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3066) = FormalQtyType.StringType // METHOD_REF.METHOD_FULL_NAME
    nodePropertyDescriptors(3067) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3324) = FormalQtyType.IntType // METHOD_REF.OFFSET
    nodePropertyDescriptors(3325) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3410) = FormalQtyType.IntType // METHOD_REF.OFFSET_END
    nodePropertyDescriptors(3411) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3496) = FormalQtyType.IntType // METHOD_REF.ORDER
    nodePropertyDescriptors(3497) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3754) = FormalQtyType.StringType // METHOD_REF.POSSIBLE_TYPES
    nodePropertyDescriptors(3755) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4184) = FormalQtyType.StringType // METHOD_REF.TYPE_FULL_NAME
    nodePropertyDescriptors(4185) = FormalQtyType.QtyOne
    nodePropertyDescriptors(660) = FormalQtyType.StringType // METHOD_RETURN.CODE
    nodePropertyDescriptors(661) = FormalQtyType.QtyOne
    nodePropertyDescriptors(746) = FormalQtyType.IntType // METHOD_RETURN.COLUMN_NUMBER
    nodePropertyDescriptors(747) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1348) = FormalQtyType.StringType // METHOD_RETURN.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1349) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(1434) = FormalQtyType.StringType // METHOD_RETURN.EVALUATION_STRATEGY
    nodePropertyDescriptors(1435) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2896) = FormalQtyType.IntType // METHOD_RETURN.LINE_NUMBER
    nodePropertyDescriptors(2897) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3326) = FormalQtyType.IntType // METHOD_RETURN.OFFSET
    nodePropertyDescriptors(3327) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3412) = FormalQtyType.IntType // METHOD_RETURN.OFFSET_END
    nodePropertyDescriptors(3413) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3498) = FormalQtyType.IntType // METHOD_RETURN.ORDER
    nodePropertyDescriptors(3499) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3756) = FormalQtyType.StringType // METHOD_RETURN.POSSIBLE_TYPES
    nodePropertyDescriptors(3757) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4186) = FormalQtyType.StringType // METHOD_RETURN.TYPE_FULL_NAME
    nodePropertyDescriptors(4187) = FormalQtyType.QtyOne
    nodePropertyDescriptors(662) = FormalQtyType.StringType // MODIFIER.CODE
    nodePropertyDescriptors(663) = FormalQtyType.QtyOne
    nodePropertyDescriptors(748) = FormalQtyType.IntType // MODIFIER.COLUMN_NUMBER
    nodePropertyDescriptors(749) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2898) = FormalQtyType.IntType // MODIFIER.LINE_NUMBER
    nodePropertyDescriptors(2899) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3156) = FormalQtyType.StringType // MODIFIER.MODIFIER_TYPE
    nodePropertyDescriptors(3157) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3328) = FormalQtyType.IntType // MODIFIER.OFFSET
    nodePropertyDescriptors(3329) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3414) = FormalQtyType.IntType // MODIFIER.OFFSET_END
    nodePropertyDescriptors(3415) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3500) = FormalQtyType.IntType // MODIFIER.ORDER
    nodePropertyDescriptors(3501) = FormalQtyType.QtyOne
    nodePropertyDescriptors(664) = FormalQtyType.StringType // NAMESPACE.CODE
    nodePropertyDescriptors(665) = FormalQtyType.QtyOne
    nodePropertyDescriptors(750) = FormalQtyType.IntType // NAMESPACE.COLUMN_NUMBER
    nodePropertyDescriptors(751) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2900) = FormalQtyType.IntType // NAMESPACE.LINE_NUMBER
    nodePropertyDescriptors(2901) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3244) = FormalQtyType.StringType // NAMESPACE.NAME
    nodePropertyDescriptors(3245) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3330) = FormalQtyType.IntType // NAMESPACE.OFFSET
    nodePropertyDescriptors(3331) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3416) = FormalQtyType.IntType // NAMESPACE.OFFSET_END
    nodePropertyDescriptors(3417) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3502) = FormalQtyType.IntType // NAMESPACE.ORDER
    nodePropertyDescriptors(3503) = FormalQtyType.QtyOne
    nodePropertyDescriptors(666) = FormalQtyType.StringType // NAMESPACE_BLOCK.CODE
    nodePropertyDescriptors(667) = FormalQtyType.QtyOne
    nodePropertyDescriptors(752) = FormalQtyType.IntType // NAMESPACE_BLOCK.COLUMN_NUMBER
    nodePropertyDescriptors(753) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1698) = FormalQtyType.StringType // NAMESPACE_BLOCK.FILENAME
    nodePropertyDescriptors(1699) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1784) = FormalQtyType.StringType // NAMESPACE_BLOCK.FULL_NAME
    nodePropertyDescriptors(1785) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2902) = FormalQtyType.IntType // NAMESPACE_BLOCK.LINE_NUMBER
    nodePropertyDescriptors(2903) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3246) = FormalQtyType.StringType // NAMESPACE_BLOCK.NAME
    nodePropertyDescriptors(3247) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3332) = FormalQtyType.IntType // NAMESPACE_BLOCK.OFFSET
    nodePropertyDescriptors(3333) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3418) = FormalQtyType.IntType // NAMESPACE_BLOCK.OFFSET_END
    nodePropertyDescriptors(3419) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3504) = FormalQtyType.IntType // NAMESPACE_BLOCK.ORDER
    nodePropertyDescriptors(3505) = FormalQtyType.QtyOne
    nodePropertyDescriptors(152) = FormalQtyType.IntType // RETURN.ARGUMENT_INDEX
    nodePropertyDescriptors(153) = FormalQtyType.QtyOne
    nodePropertyDescriptors(238) = FormalQtyType.StringType // RETURN.ARGUMENT_NAME
    nodePropertyDescriptors(239) = FormalQtyType.QtyOption
    nodePropertyDescriptors(668) = FormalQtyType.StringType // RETURN.CODE
    nodePropertyDescriptors(669) = FormalQtyType.QtyOne
    nodePropertyDescriptors(754) = FormalQtyType.IntType // RETURN.COLUMN_NUMBER
    nodePropertyDescriptors(755) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2904) = FormalQtyType.IntType // RETURN.LINE_NUMBER
    nodePropertyDescriptors(2905) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3334) = FormalQtyType.IntType // RETURN.OFFSET
    nodePropertyDescriptors(3335) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3420) = FormalQtyType.IntType // RETURN.OFFSET_END
    nodePropertyDescriptors(3421) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3506) = FormalQtyType.IntType // RETURN.ORDER
    nodePropertyDescriptors(3507) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3250) = FormalQtyType.StringType // TAG.NAME
    nodePropertyDescriptors(3251) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4282) = FormalQtyType.StringType // TAG.VALUE
    nodePropertyDescriptors(4283) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4628) = FormalQtyType.RefType // TAG_NODE_PAIR.node
    nodePropertyDescriptors(4629) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4714) = FormalQtyType.RefType // TAG_NODE_PAIR.tag
    nodePropertyDescriptors(4715) = FormalQtyType.QtyOne
    nodePropertyDescriptors(158) = FormalQtyType.IntType // TEMPLATE_DOM.ARGUMENT_INDEX
    nodePropertyDescriptors(159) = FormalQtyType.QtyOne
    nodePropertyDescriptors(244) = FormalQtyType.StringType // TEMPLATE_DOM.ARGUMENT_NAME
    nodePropertyDescriptors(245) = FormalQtyType.QtyOption
    nodePropertyDescriptors(674) = FormalQtyType.StringType // TEMPLATE_DOM.CODE
    nodePropertyDescriptors(675) = FormalQtyType.QtyOne
    nodePropertyDescriptors(760) = FormalQtyType.IntType // TEMPLATE_DOM.COLUMN_NUMBER
    nodePropertyDescriptors(761) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2910) = FormalQtyType.IntType // TEMPLATE_DOM.LINE_NUMBER
    nodePropertyDescriptors(2911) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3254) = FormalQtyType.StringType // TEMPLATE_DOM.NAME
    nodePropertyDescriptors(3255) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3340) = FormalQtyType.IntType // TEMPLATE_DOM.OFFSET
    nodePropertyDescriptors(3341) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3426) = FormalQtyType.IntType // TEMPLATE_DOM.OFFSET_END
    nodePropertyDescriptors(3427) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3512) = FormalQtyType.IntType // TEMPLATE_DOM.ORDER
    nodePropertyDescriptors(3513) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1794) = FormalQtyType.StringType // TYPE.FULL_NAME
    nodePropertyDescriptors(1795) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3256) = FormalQtyType.StringType // TYPE.NAME
    nodePropertyDescriptors(3257) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4116) = FormalQtyType.StringType // TYPE.TYPE_DECL_FULL_NAME
    nodePropertyDescriptors(4117) = FormalQtyType.QtyOne
    nodePropertyDescriptors(678) = FormalQtyType.StringType // TYPE_ARGUMENT.CODE
    nodePropertyDescriptors(679) = FormalQtyType.QtyOne
    nodePropertyDescriptors(764) = FormalQtyType.IntType // TYPE_ARGUMENT.COLUMN_NUMBER
    nodePropertyDescriptors(765) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2914) = FormalQtyType.IntType // TYPE_ARGUMENT.LINE_NUMBER
    nodePropertyDescriptors(2915) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3344) = FormalQtyType.IntType // TYPE_ARGUMENT.OFFSET
    nodePropertyDescriptors(3345) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3430) = FormalQtyType.IntType // TYPE_ARGUMENT.OFFSET_END
    nodePropertyDescriptors(3431) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3516) = FormalQtyType.IntType // TYPE_ARGUMENT.ORDER
    nodePropertyDescriptors(3517) = FormalQtyType.QtyOne
    nodePropertyDescriptors(78) = FormalQtyType.StringType // TYPE_DECL.ALIAS_TYPE_FULL_NAME
    nodePropertyDescriptors(79) = FormalQtyType.QtyOption
    nodePropertyDescriptors(336) = FormalQtyType.StringType // TYPE_DECL.AST_PARENT_FULL_NAME
    nodePropertyDescriptors(337) = FormalQtyType.QtyOne
    nodePropertyDescriptors(422) = FormalQtyType.StringType // TYPE_DECL.AST_PARENT_TYPE
    nodePropertyDescriptors(423) = FormalQtyType.QtyOne
    nodePropertyDescriptors(680) = FormalQtyType.StringType // TYPE_DECL.CODE
    nodePropertyDescriptors(681) = FormalQtyType.QtyOne
    nodePropertyDescriptors(766) = FormalQtyType.IntType // TYPE_DECL.COLUMN_NUMBER
    nodePropertyDescriptors(767) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1712) = FormalQtyType.StringType // TYPE_DECL.FILENAME
    nodePropertyDescriptors(1713) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1798) = FormalQtyType.StringType // TYPE_DECL.FULL_NAME
    nodePropertyDescriptors(1799) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1884) = FormalQtyType.StringType // TYPE_DECL.GENERIC_SIGNATURE
    nodePropertyDescriptors(1885) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2314) = FormalQtyType.StringType // TYPE_DECL.INHERITS_FROM_TYPE_FULL_NAME
    nodePropertyDescriptors(2315) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(2486) = FormalQtyType.BoolType // TYPE_DECL.IS_EXTERNAL
    nodePropertyDescriptors(2487) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2916) = FormalQtyType.IntType // TYPE_DECL.LINE_NUMBER
    nodePropertyDescriptors(2917) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3260) = FormalQtyType.StringType // TYPE_DECL.NAME
    nodePropertyDescriptors(3261) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3346) = FormalQtyType.IntType // TYPE_DECL.OFFSET
    nodePropertyDescriptors(3347) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3432) = FormalQtyType.IntType // TYPE_DECL.OFFSET_END
    nodePropertyDescriptors(3433) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3518) = FormalQtyType.IntType // TYPE_DECL.ORDER
    nodePropertyDescriptors(3519) = FormalQtyType.QtyOne
    nodePropertyDescriptors(682) = FormalQtyType.StringType // TYPE_PARAMETER.CODE
    nodePropertyDescriptors(683) = FormalQtyType.QtyOne
    nodePropertyDescriptors(768) = FormalQtyType.IntType // TYPE_PARAMETER.COLUMN_NUMBER
    nodePropertyDescriptors(769) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2918) = FormalQtyType.IntType // TYPE_PARAMETER.LINE_NUMBER
    nodePropertyDescriptors(2919) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3262) = FormalQtyType.StringType // TYPE_PARAMETER.NAME
    nodePropertyDescriptors(3263) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3348) = FormalQtyType.IntType // TYPE_PARAMETER.OFFSET
    nodePropertyDescriptors(3349) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3434) = FormalQtyType.IntType // TYPE_PARAMETER.OFFSET_END
    nodePropertyDescriptors(3435) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3520) = FormalQtyType.IntType // TYPE_PARAMETER.ORDER
    nodePropertyDescriptors(3521) = FormalQtyType.QtyOne
    nodePropertyDescriptors(168) = FormalQtyType.IntType // TYPE_REF.ARGUMENT_INDEX
    nodePropertyDescriptors(169) = FormalQtyType.QtyOne
    nodePropertyDescriptors(254) = FormalQtyType.StringType // TYPE_REF.ARGUMENT_NAME
    nodePropertyDescriptors(255) = FormalQtyType.QtyOption
    nodePropertyDescriptors(684) = FormalQtyType.StringType // TYPE_REF.CODE
    nodePropertyDescriptors(685) = FormalQtyType.QtyOne
    nodePropertyDescriptors(770) = FormalQtyType.IntType // TYPE_REF.COLUMN_NUMBER
    nodePropertyDescriptors(771) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1372) = FormalQtyType.StringType // TYPE_REF.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1373) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(2920) = FormalQtyType.IntType // TYPE_REF.LINE_NUMBER
    nodePropertyDescriptors(2921) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3350) = FormalQtyType.IntType // TYPE_REF.OFFSET
    nodePropertyDescriptors(3351) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3436) = FormalQtyType.IntType // TYPE_REF.OFFSET_END
    nodePropertyDescriptors(3437) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3522) = FormalQtyType.IntType // TYPE_REF.ORDER
    nodePropertyDescriptors(3523) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3780) = FormalQtyType.StringType // TYPE_REF.POSSIBLE_TYPES
    nodePropertyDescriptors(3781) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4210) = FormalQtyType.StringType // TYPE_REF.TYPE_FULL_NAME
    nodePropertyDescriptors(4211) = FormalQtyType.QtyOne
    nodePropertyDescriptors(170) = FormalQtyType.IntType // UNKNOWN.ARGUMENT_INDEX
    nodePropertyDescriptors(171) = FormalQtyType.QtyOne
    nodePropertyDescriptors(256) = FormalQtyType.StringType // UNKNOWN.ARGUMENT_NAME
    nodePropertyDescriptors(257) = FormalQtyType.QtyOption
    nodePropertyDescriptors(686) = FormalQtyType.StringType // UNKNOWN.CODE
    nodePropertyDescriptors(687) = FormalQtyType.QtyOne
    nodePropertyDescriptors(772) = FormalQtyType.IntType // UNKNOWN.COLUMN_NUMBER
    nodePropertyDescriptors(773) = FormalQtyType.QtyOption
    nodePropertyDescriptors(944) = FormalQtyType.StringType // UNKNOWN.CONTAINED_REF
    nodePropertyDescriptors(945) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1374) = FormalQtyType.StringType // UNKNOWN.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1375) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(2922) = FormalQtyType.IntType // UNKNOWN.LINE_NUMBER
    nodePropertyDescriptors(2923) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3352) = FormalQtyType.IntType // UNKNOWN.OFFSET
    nodePropertyDescriptors(3353) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3438) = FormalQtyType.IntType // UNKNOWN.OFFSET_END
    nodePropertyDescriptors(3439) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3524) = FormalQtyType.IntType // UNKNOWN.ORDER
    nodePropertyDescriptors(3525) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3696) = FormalQtyType.StringType // UNKNOWN.PARSER_TYPE_NAME
    nodePropertyDescriptors(3697) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3782) = FormalQtyType.StringType // UNKNOWN.POSSIBLE_TYPES
    nodePropertyDescriptors(3783) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4212) = FormalQtyType.StringType // UNKNOWN.TYPE_FULL_NAME
    nodePropertyDescriptors(4213) = FormalQtyType.QtyOne
    nodePropertyDescriptors
  }
  private val newNodeInsertionHelpers: Array[flatgraph.NewNodePropertyInsertionHelper] = {
    val _newNodeInserters = new Array[flatgraph.NewNodePropertyInsertionHelper](4730)
    _newNodeInserters(86) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_argumentIndex
    _newNodeInserters(172) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_argumentName
    _newNodeInserters(602) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_code
    _newNodeInserters(688) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_columnNumber
    _newNodeInserters(1720) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_fullName
    _newNodeInserters(2838) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_lineNumber
    _newNodeInserters(3182) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_name
    _newNodeInserters(3268) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_offset
    _newNodeInserters(3354) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_offsetEnd
    _newNodeInserters(3440) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_order
    _newNodeInserters(88) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_argumentIndex
    _newNodeInserters(174) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_argumentName
    _newNodeInserters(604) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_code
    _newNodeInserters(690) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_columnNumber
    _newNodeInserters(2840) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_lineNumber
    _newNodeInserters(3184) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_name
    _newNodeInserters(3270) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_offset
    _newNodeInserters(3356) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_offsetEnd
    _newNodeInserters(3442) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_order
    _newNodeInserters(606) = nodes.NewAnnotationParameter.InsertionHelpers.NewNodeInserter_AnnotationParameter_code
    _newNodeInserters(692) =
      nodes.NewAnnotationParameter.InsertionHelpers.NewNodeInserter_AnnotationParameter_columnNumber
    _newNodeInserters(2842) =
      nodes.NewAnnotationParameter.InsertionHelpers.NewNodeInserter_AnnotationParameter_lineNumber
    _newNodeInserters(3272) = nodes.NewAnnotationParameter.InsertionHelpers.NewNodeInserter_AnnotationParameter_offset
    _newNodeInserters(3358) =
      nodes.NewAnnotationParameter.InsertionHelpers.NewNodeInserter_AnnotationParameter_offsetEnd
    _newNodeInserters(3444) = nodes.NewAnnotationParameter.InsertionHelpers.NewNodeInserter_AnnotationParameter_order
    _newNodeInserters(608) =
      nodes.NewAnnotationParameterAssign.InsertionHelpers.NewNodeInserter_AnnotationParameterAssign_code
    _newNodeInserters(694) =
      nodes.NewAnnotationParameterAssign.InsertionHelpers.NewNodeInserter_AnnotationParameterAssign_columnNumber
    _newNodeInserters(2844) =
      nodes.NewAnnotationParameterAssign.InsertionHelpers.NewNodeInserter_AnnotationParameterAssign_lineNumber
    _newNodeInserters(3274) =
      nodes.NewAnnotationParameterAssign.InsertionHelpers.NewNodeInserter_AnnotationParameterAssign_offset
    _newNodeInserters(3360) =
      nodes.NewAnnotationParameterAssign.InsertionHelpers.NewNodeInserter_AnnotationParameterAssign_offsetEnd
    _newNodeInserters(3446) =
      nodes.NewAnnotationParameterAssign.InsertionHelpers.NewNodeInserter_AnnotationParameterAssign_order
    _newNodeInserters(94) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_argumentIndex
    _newNodeInserters(180) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_argumentName
    _newNodeInserters(610) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_code
    _newNodeInserters(696) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_columnNumber
    _newNodeInserters(2846) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_lineNumber
    _newNodeInserters(3276) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_offset
    _newNodeInserters(3362) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_offsetEnd
    _newNodeInserters(3448) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_order
    _newNodeInserters(3020) = nodes.NewBinding.InsertionHelpers.NewNodeInserter_Binding_methodFullName
    _newNodeInserters(3192) = nodes.NewBinding.InsertionHelpers.NewNodeInserter_Binding_name
    _newNodeInserters(3880) = nodes.NewBinding.InsertionHelpers.NewNodeInserter_Binding_signature
    _newNodeInserters(98) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_argumentIndex
    _newNodeInserters(184) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_argumentName
    _newNodeInserters(614) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_code
    _newNodeInserters(700) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_columnNumber
    _newNodeInserters(1302) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_dynamicTypeHintFullName
    _newNodeInserters(2850) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_lineNumber
    _newNodeInserters(3280) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_offset
    _newNodeInserters(3366) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_offsetEnd
    _newNodeInserters(3452) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_order
    _newNodeInserters(3710) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_possibleTypes
    _newNodeInserters(4140) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_typeFullName
    _newNodeInserters(100) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_argumentIndex
    _newNodeInserters(186) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_argumentName
    _newNodeInserters(616) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_code
    _newNodeInserters(702) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_columnNumber
    _newNodeInserters(1218) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_dispatchType
    _newNodeInserters(1304) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_dynamicTypeHintFullName
    _newNodeInserters(2852) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_lineNumber
    _newNodeInserters(3024) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_methodFullName
    _newNodeInserters(3196) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_name
    _newNodeInserters(3282) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_offset
    _newNodeInserters(3368) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_offsetEnd
    _newNodeInserters(3454) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_order
    _newNodeInserters(3712) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_possibleTypes
    _newNodeInserters(3884) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_signature
    _newNodeInserters(3970) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_staticReceiver
    _newNodeInserters(4142) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_typeFullName
    _newNodeInserters(532) = nodes.NewClosureBinding.InsertionHelpers.NewNodeInserter_ClosureBinding_closureBindingId
    _newNodeInserters(1392) = nodes.NewClosureBinding.InsertionHelpers.NewNodeInserter_ClosureBinding_evaluationStrategy
    _newNodeInserters(620) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_code
    _newNodeInserters(706) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_columnNumber
    _newNodeInserters(1652) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_filename
    _newNodeInserters(2856) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_lineNumber
    _newNodeInserters(3286) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_offset
    _newNodeInserters(3372) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_offsetEnd
    _newNodeInserters(3458) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_order
    _newNodeInserters(966) = nodes.NewConfigFile.InsertionHelpers.NewNodeInserter_ConfigFile_content
    _newNodeInserters(3202) = nodes.NewConfigFile.InsertionHelpers.NewNodeInserter_ConfigFile_name
    _newNodeInserters(108) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_argumentIndex
    _newNodeInserters(194) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_argumentName
    _newNodeInserters(624) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_code
    _newNodeInserters(710) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_columnNumber
    _newNodeInserters(1054) =
      nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_controlStructureType
    _newNodeInserters(2860) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_lineNumber
    _newNodeInserters(3290) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_offset
    _newNodeInserters(3376) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_offsetEnd
    _newNodeInserters(3462) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_order
    _newNodeInserters(3634) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_parserTypeName
    _newNodeInserters(1142) = nodes.NewDependency.InsertionHelpers.NewNodeInserter_Dependency_dependencyGroupId
    _newNodeInserters(3206) = nodes.NewDependency.InsertionHelpers.NewNodeInserter_Dependency_name
    _newNodeInserters(4324) = nodes.NewDependency.InsertionHelpers.NewNodeInserter_Dependency_version
    _newNodeInserters(112) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_argumentIndex
    _newNodeInserters(198) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_argumentName
    _newNodeInserters(456) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_canonicalName
    _newNodeInserters(628) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_code
    _newNodeInserters(714) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_columnNumber
    _newNodeInserters(2864) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_lineNumber
    _newNodeInserters(3294) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_offset
    _newNodeInserters(3380) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_offsetEnd
    _newNodeInserters(3466) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_order
    _newNodeInserters(630) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_code
    _newNodeInserters(716) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_columnNumber
    _newNodeInserters(974) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_content
    _newNodeInserters(1920) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_hash
    _newNodeInserters(2866) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_lineNumber
    _newNodeInserters(3210) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_name
    _newNodeInserters(3296) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_offset
    _newNodeInserters(3382) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_offsetEnd
    _newNodeInserters(3468) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_order
    _newNodeInserters(1492) = nodes.NewFinding.InsertionHelpers.NewNodeInserter_Finding_evidenceDescription
    _newNodeInserters(4416) = nodes.NewFinding.InsertionHelpers.NewNodeInserter_Finding_evidence
    _newNodeInserters(4502) = nodes.NewFinding.InsertionHelpers.NewNodeInserter_Finding_keyValuePairs
    _newNodeInserters(118) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_argumentIndex
    _newNodeInserters(204) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_argumentName
    _newNodeInserters(634) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_code
    _newNodeInserters(720) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_columnNumber
    _newNodeInserters(1322) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_dynamicTypeHintFullName
    _newNodeInserters(2870) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_lineNumber
    _newNodeInserters(3214) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_name
    _newNodeInserters(3300) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_offset
    _newNodeInserters(3386) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_offsetEnd
    _newNodeInserters(3472) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_order
    _newNodeInserters(3730) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_possibleTypes
    _newNodeInserters(4160) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_typeFullName
    _newNodeInserters(636) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_code
    _newNodeInserters(722) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_columnNumber
    _newNodeInserters(1582) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_explicitAs
    _newNodeInserters(2012) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_importedAs
    _newNodeInserters(2098) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_importedEntity
    _newNodeInserters(2356) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_isExplicit
    _newNodeInserters(2614) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_isWildcard
    _newNodeInserters(2872) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_lineNumber
    _newNodeInserters(3302) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_offset
    _newNodeInserters(3388) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_offsetEnd
    _newNodeInserters(3474) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_order
    _newNodeInserters(638) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_code
    _newNodeInserters(724) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_columnNumber
    _newNodeInserters(2874) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_lineNumber
    _newNodeInserters(3218) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_name
    _newNodeInserters(3304) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_offset
    _newNodeInserters(3390) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_offsetEnd
    _newNodeInserters(3476) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_order
    _newNodeInserters(3648) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_parserTypeName
    _newNodeInserters(124) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_argumentIndex
    _newNodeInserters(640) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_code
    _newNodeInserters(726) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_columnNumber
    _newNodeInserters(2876) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_lineNumber
    _newNodeInserters(3220) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_name
    _newNodeInserters(3306) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_offset
    _newNodeInserters(3392) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_offsetEnd
    _newNodeInserters(3478) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_order
    _newNodeInserters(3650) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_parserTypeName
    _newNodeInserters(2706) = nodes.NewKeyValuePair.InsertionHelpers.NewNodeInserter_KeyValuePair_key
    _newNodeInserters(4254) = nodes.NewKeyValuePair.InsertionHelpers.NewNodeInserter_KeyValuePair_value
    _newNodeInserters(128) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_argumentIndex
    _newNodeInserters(214) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_argumentName
    _newNodeInserters(644) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_code
    _newNodeInserters(730) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_columnNumber
    _newNodeInserters(1332) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_dynamicTypeHintFullName
    _newNodeInserters(2880) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_lineNumber
    _newNodeInserters(3310) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_offset
    _newNodeInserters(3396) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_offsetEnd
    _newNodeInserters(3482) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_order
    _newNodeInserters(3740) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_possibleTypes
    _newNodeInserters(4170) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_typeFullName
    _newNodeInserters(560) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_closureBindingId
    _newNodeInserters(646) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_code
    _newNodeInserters(732) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_columnNumber
    _newNodeInserters(1334) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_dynamicTypeHintFullName
    _newNodeInserters(1850) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_genericSignature
    _newNodeInserters(2882) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_lineNumber
    _newNodeInserters(3226) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_name
    _newNodeInserters(3312) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_offset
    _newNodeInserters(3398) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_offsetEnd
    _newNodeInserters(3484) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_order
    _newNodeInserters(3742) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_possibleTypes
    _newNodeInserters(4172) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_typeFullName
    _newNodeInserters(304) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_astParentFullName
    _newNodeInserters(390) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_astParentType
    _newNodeInserters(648) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_code
    _newNodeInserters(734) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_columnNumber
    _newNodeInserters(1336) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_dynamicTypeHintFullName
    _newNodeInserters(1852) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_genericSignature
    _newNodeInserters(2884) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_lineNumber
    _newNodeInserters(3228) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_name
    _newNodeInserters(3314) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_offset
    _newNodeInserters(3400) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_offsetEnd
    _newNodeInserters(3486) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_order
    _newNodeInserters(3744) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_possibleTypes
    _newNodeInserters(4174) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_typeFullName
    _newNodeInserters(1940) = nodes.NewMetaData.InsertionHelpers.NewNodeInserter_MetaData_hash
    _newNodeInserters(2800) = nodes.NewMetaData.InsertionHelpers.NewNodeInserter_MetaData_language
    _newNodeInserters(3574) = nodes.NewMetaData.InsertionHelpers.NewNodeInserter_MetaData_overlays
    _newNodeInserters(3832) = nodes.NewMetaData.InsertionHelpers.NewNodeInserter_MetaData_root
    _newNodeInserters(4348) = nodes.NewMetaData.InsertionHelpers.NewNodeInserter_MetaData_version
    _newNodeInserters(308) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_astParentFullName
    _newNodeInserters(394) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_astParentType
    _newNodeInserters(652) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_code
    _newNodeInserters(738) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_columnNumber
    _newNodeInserters(824) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_columnNumberEnd
    _newNodeInserters(1684) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_filename
    _newNodeInserters(1770) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_fullName
    _newNodeInserters(1856) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_genericSignature
    _newNodeInserters(1942) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_hash
    _newNodeInserters(2458) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_isExternal
    _newNodeInserters(2888) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_lineNumber
    _newNodeInserters(2974) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_lineNumberEnd
    _newNodeInserters(3232) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_name
    _newNodeInserters(3318) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_offset
    _newNodeInserters(3404) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_offsetEnd
    _newNodeInserters(3490) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_order
    _newNodeInserters(3920) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_signature
    _newNodeInserters(568) =
      nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_closureBindingId
    _newNodeInserters(654) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_code
    _newNodeInserters(740) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_columnNumber
    _newNodeInserters(1342) =
      nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_dynamicTypeHintFullName
    _newNodeInserters(1428) =
      nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_evaluationStrategy
    _newNodeInserters(2202) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_index
    _newNodeInserters(2546) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_isVariadic
    _newNodeInserters(2890) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_lineNumber
    _newNodeInserters(3234) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_name
    _newNodeInserters(3320) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_offset
    _newNodeInserters(3406) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_offsetEnd
    _newNodeInserters(3492) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_order
    _newNodeInserters(3750) =
      nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_possibleTypes
    _newNodeInserters(4180) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_typeFullName
    _newNodeInserters(656) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_code
    _newNodeInserters(742) =
      nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_columnNumber
    _newNodeInserters(1430) =
      nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_evaluationStrategy
    _newNodeInserters(2204) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_index
    _newNodeInserters(2548) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_isVariadic
    _newNodeInserters(2892) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_lineNumber
    _newNodeInserters(3236) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_name
    _newNodeInserters(3322) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_offset
    _newNodeInserters(3408) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_offsetEnd
    _newNodeInserters(3494) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_order
    _newNodeInserters(4182) =
      nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_typeFullName
    _newNodeInserters(142) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_argumentIndex
    _newNodeInserters(228) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_argumentName
    _newNodeInserters(658) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_code
    _newNodeInserters(744) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_columnNumber
    _newNodeInserters(1346) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_dynamicTypeHintFullName
    _newNodeInserters(2894) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_lineNumber
    _newNodeInserters(3066) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_methodFullName
    _newNodeInserters(3324) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_offset
    _newNodeInserters(3410) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_offsetEnd
    _newNodeInserters(3496) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_order
    _newNodeInserters(3754) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_possibleTypes
    _newNodeInserters(4184) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_typeFullName
    _newNodeInserters(660) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_code
    _newNodeInserters(746) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_columnNumber
    _newNodeInserters(1348) =
      nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_dynamicTypeHintFullName
    _newNodeInserters(1434) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_evaluationStrategy
    _newNodeInserters(2896) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_lineNumber
    _newNodeInserters(3326) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_offset
    _newNodeInserters(3412) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_offsetEnd
    _newNodeInserters(3498) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_order
    _newNodeInserters(3756) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_possibleTypes
    _newNodeInserters(4186) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_typeFullName
    _newNodeInserters(662) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_code
    _newNodeInserters(748) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_columnNumber
    _newNodeInserters(2898) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_lineNumber
    _newNodeInserters(3156) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_modifierType
    _newNodeInserters(3328) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_offset
    _newNodeInserters(3414) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_offsetEnd
    _newNodeInserters(3500) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_order
    _newNodeInserters(664) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_code
    _newNodeInserters(750) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_columnNumber
    _newNodeInserters(2900) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_lineNumber
    _newNodeInserters(3244) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_name
    _newNodeInserters(3330) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_offset
    _newNodeInserters(3416) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_offsetEnd
    _newNodeInserters(3502) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_order
    _newNodeInserters(666) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_code
    _newNodeInserters(752) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_columnNumber
    _newNodeInserters(1698) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_filename
    _newNodeInserters(1784) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_fullName
    _newNodeInserters(2902) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_lineNumber
    _newNodeInserters(3246) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_name
    _newNodeInserters(3332) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_offset
    _newNodeInserters(3418) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_offsetEnd
    _newNodeInserters(3504) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_order
    _newNodeInserters(152) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_argumentIndex
    _newNodeInserters(238) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_argumentName
    _newNodeInserters(668) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_code
    _newNodeInserters(754) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_columnNumber
    _newNodeInserters(2904) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_lineNumber
    _newNodeInserters(3334) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_offset
    _newNodeInserters(3420) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_offsetEnd
    _newNodeInserters(3506) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_order
    _newNodeInserters(3250) = nodes.NewTag.InsertionHelpers.NewNodeInserter_Tag_name
    _newNodeInserters(4282) = nodes.NewTag.InsertionHelpers.NewNodeInserter_Tag_value
    _newNodeInserters(4628) = nodes.NewTagNodePair.InsertionHelpers.NewNodeInserter_TagNodePair_node
    _newNodeInserters(4714) = nodes.NewTagNodePair.InsertionHelpers.NewNodeInserter_TagNodePair_tag
    _newNodeInserters(158) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_argumentIndex
    _newNodeInserters(244) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_argumentName
    _newNodeInserters(674) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_code
    _newNodeInserters(760) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_columnNumber
    _newNodeInserters(2910) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_lineNumber
    _newNodeInserters(3254) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_name
    _newNodeInserters(3340) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_offset
    _newNodeInserters(3426) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_offsetEnd
    _newNodeInserters(3512) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_order
    _newNodeInserters(1794) = nodes.NewType.InsertionHelpers.NewNodeInserter_Type_fullName
    _newNodeInserters(3256) = nodes.NewType.InsertionHelpers.NewNodeInserter_Type_name
    _newNodeInserters(4116) = nodes.NewType.InsertionHelpers.NewNodeInserter_Type_typeDeclFullName
    _newNodeInserters(678) = nodes.NewTypeArgument.InsertionHelpers.NewNodeInserter_TypeArgument_code
    _newNodeInserters(764) = nodes.NewTypeArgument.InsertionHelpers.NewNodeInserter_TypeArgument_columnNumber
    _newNodeInserters(2914) = nodes.NewTypeArgument.InsertionHelpers.NewNodeInserter_TypeArgument_lineNumber
    _newNodeInserters(3344) = nodes.NewTypeArgument.InsertionHelpers.NewNodeInserter_TypeArgument_offset
    _newNodeInserters(3430) = nodes.NewTypeArgument.InsertionHelpers.NewNodeInserter_TypeArgument_offsetEnd
    _newNodeInserters(3516) = nodes.NewTypeArgument.InsertionHelpers.NewNodeInserter_TypeArgument_order
    _newNodeInserters(78) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_aliasTypeFullName
    _newNodeInserters(336) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_astParentFullName
    _newNodeInserters(422) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_astParentType
    _newNodeInserters(680) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_code
    _newNodeInserters(766) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_columnNumber
    _newNodeInserters(1712) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_filename
    _newNodeInserters(1798) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_fullName
    _newNodeInserters(1884) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_genericSignature
    _newNodeInserters(2314) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_inheritsFromTypeFullName
    _newNodeInserters(2486) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_isExternal
    _newNodeInserters(2916) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_lineNumber
    _newNodeInserters(3260) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_name
    _newNodeInserters(3346) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_offset
    _newNodeInserters(3432) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_offsetEnd
    _newNodeInserters(3518) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_order
    _newNodeInserters(682) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_code
    _newNodeInserters(768) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_columnNumber
    _newNodeInserters(2918) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_lineNumber
    _newNodeInserters(3262) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_name
    _newNodeInserters(3348) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_offset
    _newNodeInserters(3434) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_offsetEnd
    _newNodeInserters(3520) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_order
    _newNodeInserters(168) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_argumentIndex
    _newNodeInserters(254) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_argumentName
    _newNodeInserters(684) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_code
    _newNodeInserters(770) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_columnNumber
    _newNodeInserters(1372) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_dynamicTypeHintFullName
    _newNodeInserters(2920) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_lineNumber
    _newNodeInserters(3350) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_offset
    _newNodeInserters(3436) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_offsetEnd
    _newNodeInserters(3522) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_order
    _newNodeInserters(3780) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_possibleTypes
    _newNodeInserters(4210) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_typeFullName
    _newNodeInserters(170) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_argumentIndex
    _newNodeInserters(256) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_argumentName
    _newNodeInserters(686) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_code
    _newNodeInserters(772) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_columnNumber
    _newNodeInserters(944) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_containedRef
    _newNodeInserters(1374) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_dynamicTypeHintFullName
    _newNodeInserters(2922) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_lineNumber
    _newNodeInserters(3352) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_offset
    _newNodeInserters(3438) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_offsetEnd
    _newNodeInserters(3524) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_order
    _newNodeInserters(3696) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_parserTypeName
    _newNodeInserters(3782) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_possibleTypes
    _newNodeInserters(4212) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_typeFullName
    _newNodeInserters
  }
  override def getNumberOfNodeKinds: Int              = 43
  override def getNumberOfEdgeKinds: Int              = 24
  override def getNodeLabel(nodeKind: Int): String    = nodeLabels(nodeKind)
  override def getNodeKindByLabel(label: String): Int = nodeKindByLabel.getOrElse(label, flatgraph.Schema.UndefinedKind)
  override def getEdgeLabel(nodeKind: Int, edgeKind: Int): String = edgeLabels(edgeKind)
  override def getEdgeKindByLabel(label: String): Int = edgeKindByLabel.getOrElse(label, flatgraph.Schema.UndefinedKind)
  override def getNodePropertyNames(nodeLabel: String): Set[String] = {
    nodeLabel match {
      case "ANNOTATION" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "FULL_NAME",
          "LINE_NUMBER",
          "NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER"
        )
      case "ANNOTATION_LITERAL" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "LINE_NUMBER",
          "NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER"
        )
      case "ANNOTATION_PARAMETER"        => Set("CODE", "COLUMN_NUMBER", "LINE_NUMBER", "OFFSET", "OFFSET_END", "ORDER")
      case "ANNOTATION_PARAMETER_ASSIGN" => Set("CODE", "COLUMN_NUMBER", "LINE_NUMBER", "OFFSET", "OFFSET_END", "ORDER")
      case "ARRAY_INITIALIZER" =>
        Set("ARGUMENT_INDEX", "ARGUMENT_NAME", "CODE", "COLUMN_NUMBER", "LINE_NUMBER", "OFFSET", "OFFSET_END", "ORDER")
      case "BINDING" => Set("METHOD_FULL_NAME", "NAME", "SIGNATURE")
      case "BLOCK" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "DYNAMIC_TYPE_HINT_FULL_NAME",
          "LINE_NUMBER",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "POSSIBLE_TYPES",
          "TYPE_FULL_NAME"
        )
      case "CALL" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "DISPATCH_TYPE",
          "DYNAMIC_TYPE_HINT_FULL_NAME",
          "LINE_NUMBER",
          "METHOD_FULL_NAME",
          "NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "POSSIBLE_TYPES",
          "SIGNATURE",
          "STATIC_RECEIVER",
          "TYPE_FULL_NAME"
        )
      case "CLOSURE_BINDING" => Set("CLOSURE_BINDING_ID", "EVALUATION_STRATEGY")
      case "COMMENT"         => Set("CODE", "COLUMN_NUMBER", "FILENAME", "LINE_NUMBER", "OFFSET", "OFFSET_END", "ORDER")
      case "CONFIG_FILE"     => Set("CONTENT", "NAME")
      case "CONTROL_STRUCTURE" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "CONTROL_STRUCTURE_TYPE",
          "LINE_NUMBER",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "PARSER_TYPE_NAME"
        )
      case "DEPENDENCY" => Set("DEPENDENCY_GROUP_ID", "NAME", "VERSION")
      case "FIELD_IDENTIFIER" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_NAME",
          "CANONICAL_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "LINE_NUMBER",
          "OFFSET",
          "OFFSET_END",
          "ORDER"
        )
      case "FILE" =>
        Set("CODE", "COLUMN_NUMBER", "CONTENT", "HASH", "LINE_NUMBER", "NAME", "OFFSET", "OFFSET_END", "ORDER")
      case "FINDING" => Set("EVIDENCE_DESCRIPTION")
      case "IDENTIFIER" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "DYNAMIC_TYPE_HINT_FULL_NAME",
          "LINE_NUMBER",
          "NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "POSSIBLE_TYPES",
          "TYPE_FULL_NAME"
        )
      case "IMPORT" =>
        Set(
          "CODE",
          "COLUMN_NUMBER",
          "EXPLICIT_AS",
          "IMPORTED_AS",
          "IMPORTED_ENTITY",
          "IS_EXPLICIT",
          "IS_WILDCARD",
          "LINE_NUMBER",
          "OFFSET",
          "OFFSET_END",
          "ORDER"
        )
      case "JUMP_LABEL" =>
        Set("CODE", "COLUMN_NUMBER", "LINE_NUMBER", "NAME", "OFFSET", "OFFSET_END", "ORDER", "PARSER_TYPE_NAME")
      case "JUMP_TARGET" =>
        Set(
          "ARGUMENT_INDEX",
          "CODE",
          "COLUMN_NUMBER",
          "LINE_NUMBER",
          "NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "PARSER_TYPE_NAME"
        )
      case "KEY_VALUE_PAIR" => Set("KEY", "VALUE")
      case "LITERAL" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "DYNAMIC_TYPE_HINT_FULL_NAME",
          "LINE_NUMBER",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "POSSIBLE_TYPES",
          "TYPE_FULL_NAME"
        )
      case "LOCAL" =>
        Set(
          "CLOSURE_BINDING_ID",
          "CODE",
          "COLUMN_NUMBER",
          "DYNAMIC_TYPE_HINT_FULL_NAME",
          "GENERIC_SIGNATURE",
          "LINE_NUMBER",
          "NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "POSSIBLE_TYPES",
          "TYPE_FULL_NAME"
        )
      case "MEMBER" =>
        Set(
          "AST_PARENT_FULL_NAME",
          "AST_PARENT_TYPE",
          "CODE",
          "COLUMN_NUMBER",
          "DYNAMIC_TYPE_HINT_FULL_NAME",
          "GENERIC_SIGNATURE",
          "LINE_NUMBER",
          "NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "POSSIBLE_TYPES",
          "TYPE_FULL_NAME"
        )
      case "META_DATA" => Set("HASH", "LANGUAGE", "OVERLAYS", "ROOT", "VERSION")
      case "METHOD" =>
        Set(
          "AST_PARENT_FULL_NAME",
          "AST_PARENT_TYPE",
          "CODE",
          "COLUMN_NUMBER",
          "COLUMN_NUMBER_END",
          "FILENAME",
          "FULL_NAME",
          "GENERIC_SIGNATURE",
          "HASH",
          "IS_EXTERNAL",
          "LINE_NUMBER",
          "LINE_NUMBER_END",
          "NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "SIGNATURE"
        )
      case "METHOD_PARAMETER_IN" =>
        Set(
          "CLOSURE_BINDING_ID",
          "CODE",
          "COLUMN_NUMBER",
          "DYNAMIC_TYPE_HINT_FULL_NAME",
          "EVALUATION_STRATEGY",
          "INDEX",
          "IS_VARIADIC",
          "LINE_NUMBER",
          "NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "POSSIBLE_TYPES",
          "TYPE_FULL_NAME"
        )
      case "METHOD_PARAMETER_OUT" =>
        Set(
          "CODE",
          "COLUMN_NUMBER",
          "EVALUATION_STRATEGY",
          "INDEX",
          "IS_VARIADIC",
          "LINE_NUMBER",
          "NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "TYPE_FULL_NAME"
        )
      case "METHOD_REF" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "DYNAMIC_TYPE_HINT_FULL_NAME",
          "LINE_NUMBER",
          "METHOD_FULL_NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "POSSIBLE_TYPES",
          "TYPE_FULL_NAME"
        )
      case "METHOD_RETURN" =>
        Set(
          "CODE",
          "COLUMN_NUMBER",
          "DYNAMIC_TYPE_HINT_FULL_NAME",
          "EVALUATION_STRATEGY",
          "LINE_NUMBER",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "POSSIBLE_TYPES",
          "TYPE_FULL_NAME"
        )
      case "MODIFIER"  => Set("CODE", "COLUMN_NUMBER", "LINE_NUMBER", "MODIFIER_TYPE", "OFFSET", "OFFSET_END", "ORDER")
      case "NAMESPACE" => Set("CODE", "COLUMN_NUMBER", "LINE_NUMBER", "NAME", "OFFSET", "OFFSET_END", "ORDER")
      case "NAMESPACE_BLOCK" =>
        Set("CODE", "COLUMN_NUMBER", "FILENAME", "FULL_NAME", "LINE_NUMBER", "NAME", "OFFSET", "OFFSET_END", "ORDER")
      case "RETURN" =>
        Set("ARGUMENT_INDEX", "ARGUMENT_NAME", "CODE", "COLUMN_NUMBER", "LINE_NUMBER", "OFFSET", "OFFSET_END", "ORDER")
      case "TAG"           => Set("NAME", "VALUE")
      case "TAG_NODE_PAIR" => Set()
      case "TEMPLATE_DOM" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "LINE_NUMBER",
          "NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER"
        )
      case "TYPE"          => Set("FULL_NAME", "NAME", "TYPE_DECL_FULL_NAME")
      case "TYPE_ARGUMENT" => Set("CODE", "COLUMN_NUMBER", "LINE_NUMBER", "OFFSET", "OFFSET_END", "ORDER")
      case "TYPE_DECL" =>
        Set(
          "ALIAS_TYPE_FULL_NAME",
          "AST_PARENT_FULL_NAME",
          "AST_PARENT_TYPE",
          "CODE",
          "COLUMN_NUMBER",
          "FILENAME",
          "FULL_NAME",
          "GENERIC_SIGNATURE",
          "INHERITS_FROM_TYPE_FULL_NAME",
          "IS_EXTERNAL",
          "LINE_NUMBER",
          "NAME",
          "OFFSET",
          "OFFSET_END",
          "ORDER"
        )
      case "TYPE_PARAMETER" => Set("CODE", "COLUMN_NUMBER", "LINE_NUMBER", "NAME", "OFFSET", "OFFSET_END", "ORDER")
      case "TYPE_REF" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "DYNAMIC_TYPE_HINT_FULL_NAME",
          "LINE_NUMBER",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "POSSIBLE_TYPES",
          "TYPE_FULL_NAME"
        )
      case "UNKNOWN" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "CONTAINED_REF",
          "DYNAMIC_TYPE_HINT_FULL_NAME",
          "LINE_NUMBER",
          "OFFSET",
          "OFFSET_END",
          "ORDER",
          "PARSER_TYPE_NAME",
          "POSSIBLE_TYPES",
          "TYPE_FULL_NAME"
        )
      case _ => Set.empty
    }
  }
  override def getEdgePropertyName(label: String): Option[String] = {
    label match {
      case "REACHING_DEF" => Some("VARIABLE")
      case _              => None
    }
  }

  override def getPropertyLabel(nodeKind: Int, propertyKind: Int): String = {
    if (propertyKind < 51) normalNodePropertyNames(propertyKind)
    else if (propertyKind == 51 && nodeKind == 15) "evidence"      /*on node FINDING*/
    else if (propertyKind == 52 && nodeKind == 15) "keyValuePairs" /*on node FINDING*/
    else if (propertyKind == 53 && nodeKind == 35) "node"          /*on node TAG_NODE_PAIR*/
    else if (propertyKind == 54 && nodeKind == 35) "tag"           /*on node TAG_NODE_PAIR*/
    else null
  }

  override def getPropertyKindByName(label: String): Int =
    nodePropertyByLabel.getOrElse(label, flatgraph.Schema.UndefinedKind)
  override def getNumberOfPropertyKinds: Int = 55
  override def makeNode(graph: flatgraph.Graph, nodeKind: Short, seq: Int): nodes.StoredNode =
    nodeFactories(nodeKind)(graph, seq)
  override def makeEdge(
    src: flatgraph.GNode,
    dst: flatgraph.GNode,
    edgeKind: Short,
    subSeq: Int,
    property: Any
  ): flatgraph.Edge = edgeFactories(edgeKind)(src, dst, subSeq, property)
  override def allocateEdgeProperty(
    nodeKind: Int,
    direction: flatgraph.Edge.Direction,
    edgeKind: Int,
    size: Int
  ): Array[?] = edgePropertyAllocators(edgeKind)(size)
  override def getNodePropertyFormalType(nodeKind: Int, propertyKind: Int): FormalQtyType.FormalType =
    nodePropertyDescriptors(propertyOffsetArrayIndex(nodeKind, propertyKind)).asInstanceOf[FormalQtyType.FormalType]
  override def getNodePropertyFormalQuantity(nodeKind: Int, propertyKind: Int): FormalQtyType.FormalQuantity =
    nodePropertyDescriptors(1 + propertyOffsetArrayIndex(nodeKind, propertyKind))
      .asInstanceOf[FormalQtyType.FormalQuantity]

  override def getNewNodePropertyInserter(nodeKind: Int, propertyKind: Int): flatgraph.NewNodePropertyInsertionHelper =
    newNodeInsertionHelpers(propertyOffsetArrayIndex(nodeKind, propertyKind))
}
