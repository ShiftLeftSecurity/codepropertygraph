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
    "ARGUMENT_LABEL",
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
    "IS_MODULE_IMPORT",
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
    .updated("evidence", 53)
    .updated("keyValuePairs", 54)
    .updated("node", 55)
    .updated("tag", 56)
  val nodePropertyDescriptors: Array[FormalQtyType.FormalQuantity | FormalQtyType.FormalType] = {
    val nodePropertyDescriptors = new Array[FormalQtyType.FormalQuantity | FormalQtyType.FormalType](4902)
    for (idx <- Range(0, 4902)) {
      nodePropertyDescriptors(idx) =
        if ((idx & 1) == 0) FormalQtyType.NothingType
        else FormalQtyType.QtyNone
    }

    nodePropertyDescriptors(86) = FormalQtyType.IntType // ANNOTATION.ARGUMENT_INDEX
    nodePropertyDescriptors(87) = FormalQtyType.QtyOne
    nodePropertyDescriptors(172) = FormalQtyType.StringType // ANNOTATION.ARGUMENT_LABEL
    nodePropertyDescriptors(173) = FormalQtyType.QtyOption
    nodePropertyDescriptors(258) = FormalQtyType.StringType // ANNOTATION.ARGUMENT_NAME
    nodePropertyDescriptors(259) = FormalQtyType.QtyOption
    nodePropertyDescriptors(688) = FormalQtyType.StringType // ANNOTATION.CODE
    nodePropertyDescriptors(689) = FormalQtyType.QtyOne
    nodePropertyDescriptors(774) = FormalQtyType.IntType // ANNOTATION.COLUMN_NUMBER
    nodePropertyDescriptors(775) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1806) = FormalQtyType.StringType // ANNOTATION.FULL_NAME
    nodePropertyDescriptors(1807) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3010) = FormalQtyType.IntType // ANNOTATION.LINE_NUMBER
    nodePropertyDescriptors(3011) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3354) = FormalQtyType.StringType // ANNOTATION.NAME
    nodePropertyDescriptors(3355) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3440) = FormalQtyType.IntType // ANNOTATION.OFFSET
    nodePropertyDescriptors(3441) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3526) = FormalQtyType.IntType // ANNOTATION.OFFSET_END
    nodePropertyDescriptors(3527) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3612) = FormalQtyType.IntType // ANNOTATION.ORDER
    nodePropertyDescriptors(3613) = FormalQtyType.QtyOne
    nodePropertyDescriptors(88) = FormalQtyType.IntType // ANNOTATION_LITERAL.ARGUMENT_INDEX
    nodePropertyDescriptors(89) = FormalQtyType.QtyOne
    nodePropertyDescriptors(174) = FormalQtyType.StringType // ANNOTATION_LITERAL.ARGUMENT_LABEL
    nodePropertyDescriptors(175) = FormalQtyType.QtyOption
    nodePropertyDescriptors(260) = FormalQtyType.StringType // ANNOTATION_LITERAL.ARGUMENT_NAME
    nodePropertyDescriptors(261) = FormalQtyType.QtyOption
    nodePropertyDescriptors(690) = FormalQtyType.StringType // ANNOTATION_LITERAL.CODE
    nodePropertyDescriptors(691) = FormalQtyType.QtyOne
    nodePropertyDescriptors(776) = FormalQtyType.IntType // ANNOTATION_LITERAL.COLUMN_NUMBER
    nodePropertyDescriptors(777) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3012) = FormalQtyType.IntType // ANNOTATION_LITERAL.LINE_NUMBER
    nodePropertyDescriptors(3013) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3356) = FormalQtyType.StringType // ANNOTATION_LITERAL.NAME
    nodePropertyDescriptors(3357) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3442) = FormalQtyType.IntType // ANNOTATION_LITERAL.OFFSET
    nodePropertyDescriptors(3443) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3528) = FormalQtyType.IntType // ANNOTATION_LITERAL.OFFSET_END
    nodePropertyDescriptors(3529) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3614) = FormalQtyType.IntType // ANNOTATION_LITERAL.ORDER
    nodePropertyDescriptors(3615) = FormalQtyType.QtyOne
    nodePropertyDescriptors(692) = FormalQtyType.StringType // ANNOTATION_PARAMETER.CODE
    nodePropertyDescriptors(693) = FormalQtyType.QtyOne
    nodePropertyDescriptors(778) = FormalQtyType.IntType // ANNOTATION_PARAMETER.COLUMN_NUMBER
    nodePropertyDescriptors(779) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3014) = FormalQtyType.IntType // ANNOTATION_PARAMETER.LINE_NUMBER
    nodePropertyDescriptors(3015) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3444) = FormalQtyType.IntType // ANNOTATION_PARAMETER.OFFSET
    nodePropertyDescriptors(3445) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3530) = FormalQtyType.IntType // ANNOTATION_PARAMETER.OFFSET_END
    nodePropertyDescriptors(3531) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3616) = FormalQtyType.IntType // ANNOTATION_PARAMETER.ORDER
    nodePropertyDescriptors(3617) = FormalQtyType.QtyOne
    nodePropertyDescriptors(694) = FormalQtyType.StringType // ANNOTATION_PARAMETER_ASSIGN.CODE
    nodePropertyDescriptors(695) = FormalQtyType.QtyOne
    nodePropertyDescriptors(780) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.COLUMN_NUMBER
    nodePropertyDescriptors(781) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3016) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.LINE_NUMBER
    nodePropertyDescriptors(3017) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3446) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.OFFSET
    nodePropertyDescriptors(3447) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3532) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.OFFSET_END
    nodePropertyDescriptors(3533) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3618) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.ORDER
    nodePropertyDescriptors(3619) = FormalQtyType.QtyOne
    nodePropertyDescriptors(94) = FormalQtyType.IntType // ARRAY_INITIALIZER.ARGUMENT_INDEX
    nodePropertyDescriptors(95) = FormalQtyType.QtyOne
    nodePropertyDescriptors(180) = FormalQtyType.StringType // ARRAY_INITIALIZER.ARGUMENT_LABEL
    nodePropertyDescriptors(181) = FormalQtyType.QtyOption
    nodePropertyDescriptors(266) = FormalQtyType.StringType // ARRAY_INITIALIZER.ARGUMENT_NAME
    nodePropertyDescriptors(267) = FormalQtyType.QtyOption
    nodePropertyDescriptors(696) = FormalQtyType.StringType // ARRAY_INITIALIZER.CODE
    nodePropertyDescriptors(697) = FormalQtyType.QtyOne
    nodePropertyDescriptors(782) = FormalQtyType.IntType // ARRAY_INITIALIZER.COLUMN_NUMBER
    nodePropertyDescriptors(783) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3018) = FormalQtyType.IntType // ARRAY_INITIALIZER.LINE_NUMBER
    nodePropertyDescriptors(3019) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3448) = FormalQtyType.IntType // ARRAY_INITIALIZER.OFFSET
    nodePropertyDescriptors(3449) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3534) = FormalQtyType.IntType // ARRAY_INITIALIZER.OFFSET_END
    nodePropertyDescriptors(3535) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3620) = FormalQtyType.IntType // ARRAY_INITIALIZER.ORDER
    nodePropertyDescriptors(3621) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3192) = FormalQtyType.StringType // BINDING.METHOD_FULL_NAME
    nodePropertyDescriptors(3193) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3364) = FormalQtyType.StringType // BINDING.NAME
    nodePropertyDescriptors(3365) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4052) = FormalQtyType.StringType // BINDING.SIGNATURE
    nodePropertyDescriptors(4053) = FormalQtyType.QtyOne
    nodePropertyDescriptors(98) = FormalQtyType.IntType // BLOCK.ARGUMENT_INDEX
    nodePropertyDescriptors(99) = FormalQtyType.QtyOne
    nodePropertyDescriptors(184) = FormalQtyType.StringType // BLOCK.ARGUMENT_LABEL
    nodePropertyDescriptors(185) = FormalQtyType.QtyOption
    nodePropertyDescriptors(270) = FormalQtyType.StringType // BLOCK.ARGUMENT_NAME
    nodePropertyDescriptors(271) = FormalQtyType.QtyOption
    nodePropertyDescriptors(700) = FormalQtyType.StringType // BLOCK.CODE
    nodePropertyDescriptors(701) = FormalQtyType.QtyOne
    nodePropertyDescriptors(786) = FormalQtyType.IntType // BLOCK.COLUMN_NUMBER
    nodePropertyDescriptors(787) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1388) = FormalQtyType.StringType // BLOCK.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1389) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3022) = FormalQtyType.IntType // BLOCK.LINE_NUMBER
    nodePropertyDescriptors(3023) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3452) = FormalQtyType.IntType // BLOCK.OFFSET
    nodePropertyDescriptors(3453) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3538) = FormalQtyType.IntType // BLOCK.OFFSET_END
    nodePropertyDescriptors(3539) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3624) = FormalQtyType.IntType // BLOCK.ORDER
    nodePropertyDescriptors(3625) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3882) = FormalQtyType.StringType // BLOCK.POSSIBLE_TYPES
    nodePropertyDescriptors(3883) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4312) = FormalQtyType.StringType // BLOCK.TYPE_FULL_NAME
    nodePropertyDescriptors(4313) = FormalQtyType.QtyOne
    nodePropertyDescriptors(100) = FormalQtyType.IntType // CALL.ARGUMENT_INDEX
    nodePropertyDescriptors(101) = FormalQtyType.QtyOne
    nodePropertyDescriptors(186) = FormalQtyType.StringType // CALL.ARGUMENT_LABEL
    nodePropertyDescriptors(187) = FormalQtyType.QtyOption
    nodePropertyDescriptors(272) = FormalQtyType.StringType // CALL.ARGUMENT_NAME
    nodePropertyDescriptors(273) = FormalQtyType.QtyOption
    nodePropertyDescriptors(702) = FormalQtyType.StringType // CALL.CODE
    nodePropertyDescriptors(703) = FormalQtyType.QtyOne
    nodePropertyDescriptors(788) = FormalQtyType.IntType // CALL.COLUMN_NUMBER
    nodePropertyDescriptors(789) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1304) = FormalQtyType.StringType // CALL.DISPATCH_TYPE
    nodePropertyDescriptors(1305) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1390) = FormalQtyType.StringType // CALL.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1391) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3024) = FormalQtyType.IntType // CALL.LINE_NUMBER
    nodePropertyDescriptors(3025) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3196) = FormalQtyType.StringType // CALL.METHOD_FULL_NAME
    nodePropertyDescriptors(3197) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3368) = FormalQtyType.StringType // CALL.NAME
    nodePropertyDescriptors(3369) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3454) = FormalQtyType.IntType // CALL.OFFSET
    nodePropertyDescriptors(3455) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3540) = FormalQtyType.IntType // CALL.OFFSET_END
    nodePropertyDescriptors(3541) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3626) = FormalQtyType.IntType // CALL.ORDER
    nodePropertyDescriptors(3627) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3884) = FormalQtyType.StringType // CALL.POSSIBLE_TYPES
    nodePropertyDescriptors(3885) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4056) = FormalQtyType.StringType // CALL.SIGNATURE
    nodePropertyDescriptors(4057) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4142) = FormalQtyType.StringType // CALL.STATIC_RECEIVER
    nodePropertyDescriptors(4143) = FormalQtyType.QtyOption
    nodePropertyDescriptors(4314) = FormalQtyType.StringType // CALL.TYPE_FULL_NAME
    nodePropertyDescriptors(4315) = FormalQtyType.QtyOne
    nodePropertyDescriptors(618) = FormalQtyType.StringType // CLOSURE_BINDING.CLOSURE_BINDING_ID
    nodePropertyDescriptors(619) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1478) = FormalQtyType.StringType // CLOSURE_BINDING.EVALUATION_STRATEGY
    nodePropertyDescriptors(1479) = FormalQtyType.QtyOne
    nodePropertyDescriptors(706) = FormalQtyType.StringType // COMMENT.CODE
    nodePropertyDescriptors(707) = FormalQtyType.QtyOne
    nodePropertyDescriptors(792) = FormalQtyType.IntType // COMMENT.COLUMN_NUMBER
    nodePropertyDescriptors(793) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1738) = FormalQtyType.StringType // COMMENT.FILENAME
    nodePropertyDescriptors(1739) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3028) = FormalQtyType.IntType // COMMENT.LINE_NUMBER
    nodePropertyDescriptors(3029) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3458) = FormalQtyType.IntType // COMMENT.OFFSET
    nodePropertyDescriptors(3459) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3544) = FormalQtyType.IntType // COMMENT.OFFSET_END
    nodePropertyDescriptors(3545) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3630) = FormalQtyType.IntType // COMMENT.ORDER
    nodePropertyDescriptors(3631) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1052) = FormalQtyType.StringType // CONFIG_FILE.CONTENT
    nodePropertyDescriptors(1053) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3374) = FormalQtyType.StringType // CONFIG_FILE.NAME
    nodePropertyDescriptors(3375) = FormalQtyType.QtyOne
    nodePropertyDescriptors(108) = FormalQtyType.IntType // CONTROL_STRUCTURE.ARGUMENT_INDEX
    nodePropertyDescriptors(109) = FormalQtyType.QtyOne
    nodePropertyDescriptors(194) = FormalQtyType.StringType // CONTROL_STRUCTURE.ARGUMENT_LABEL
    nodePropertyDescriptors(195) = FormalQtyType.QtyOption
    nodePropertyDescriptors(280) = FormalQtyType.StringType // CONTROL_STRUCTURE.ARGUMENT_NAME
    nodePropertyDescriptors(281) = FormalQtyType.QtyOption
    nodePropertyDescriptors(710) = FormalQtyType.StringType // CONTROL_STRUCTURE.CODE
    nodePropertyDescriptors(711) = FormalQtyType.QtyOne
    nodePropertyDescriptors(796) = FormalQtyType.IntType // CONTROL_STRUCTURE.COLUMN_NUMBER
    nodePropertyDescriptors(797) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1140) = FormalQtyType.StringType // CONTROL_STRUCTURE.CONTROL_STRUCTURE_TYPE
    nodePropertyDescriptors(1141) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3032) = FormalQtyType.IntType // CONTROL_STRUCTURE.LINE_NUMBER
    nodePropertyDescriptors(3033) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3462) = FormalQtyType.IntType // CONTROL_STRUCTURE.OFFSET
    nodePropertyDescriptors(3463) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3548) = FormalQtyType.IntType // CONTROL_STRUCTURE.OFFSET_END
    nodePropertyDescriptors(3549) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3634) = FormalQtyType.IntType // CONTROL_STRUCTURE.ORDER
    nodePropertyDescriptors(3635) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3806) = FormalQtyType.StringType // CONTROL_STRUCTURE.PARSER_TYPE_NAME
    nodePropertyDescriptors(3807) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1228) = FormalQtyType.StringType // DEPENDENCY.DEPENDENCY_GROUP_ID
    nodePropertyDescriptors(1229) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3378) = FormalQtyType.StringType // DEPENDENCY.NAME
    nodePropertyDescriptors(3379) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4496) = FormalQtyType.StringType // DEPENDENCY.VERSION
    nodePropertyDescriptors(4497) = FormalQtyType.QtyOne
    nodePropertyDescriptors(112) = FormalQtyType.IntType // FIELD_IDENTIFIER.ARGUMENT_INDEX
    nodePropertyDescriptors(113) = FormalQtyType.QtyOne
    nodePropertyDescriptors(198) = FormalQtyType.StringType // FIELD_IDENTIFIER.ARGUMENT_LABEL
    nodePropertyDescriptors(199) = FormalQtyType.QtyOption
    nodePropertyDescriptors(284) = FormalQtyType.StringType // FIELD_IDENTIFIER.ARGUMENT_NAME
    nodePropertyDescriptors(285) = FormalQtyType.QtyOption
    nodePropertyDescriptors(542) = FormalQtyType.StringType // FIELD_IDENTIFIER.CANONICAL_NAME
    nodePropertyDescriptors(543) = FormalQtyType.QtyOne
    nodePropertyDescriptors(714) = FormalQtyType.StringType // FIELD_IDENTIFIER.CODE
    nodePropertyDescriptors(715) = FormalQtyType.QtyOne
    nodePropertyDescriptors(800) = FormalQtyType.IntType // FIELD_IDENTIFIER.COLUMN_NUMBER
    nodePropertyDescriptors(801) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3036) = FormalQtyType.IntType // FIELD_IDENTIFIER.LINE_NUMBER
    nodePropertyDescriptors(3037) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3466) = FormalQtyType.IntType // FIELD_IDENTIFIER.OFFSET
    nodePropertyDescriptors(3467) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3552) = FormalQtyType.IntType // FIELD_IDENTIFIER.OFFSET_END
    nodePropertyDescriptors(3553) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3638) = FormalQtyType.IntType // FIELD_IDENTIFIER.ORDER
    nodePropertyDescriptors(3639) = FormalQtyType.QtyOne
    nodePropertyDescriptors(716) = FormalQtyType.StringType // FILE.CODE
    nodePropertyDescriptors(717) = FormalQtyType.QtyOne
    nodePropertyDescriptors(802) = FormalQtyType.IntType // FILE.COLUMN_NUMBER
    nodePropertyDescriptors(803) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1060) = FormalQtyType.StringType // FILE.CONTENT
    nodePropertyDescriptors(1061) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2006) = FormalQtyType.StringType // FILE.HASH
    nodePropertyDescriptors(2007) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3038) = FormalQtyType.IntType // FILE.LINE_NUMBER
    nodePropertyDescriptors(3039) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3382) = FormalQtyType.StringType // FILE.NAME
    nodePropertyDescriptors(3383) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3468) = FormalQtyType.IntType // FILE.OFFSET
    nodePropertyDescriptors(3469) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3554) = FormalQtyType.IntType // FILE.OFFSET_END
    nodePropertyDescriptors(3555) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3640) = FormalQtyType.IntType // FILE.ORDER
    nodePropertyDescriptors(3641) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1578) = FormalQtyType.StringType // FINDING.EVIDENCE_DESCRIPTION
    nodePropertyDescriptors(1579) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4588) = FormalQtyType.RefType // FINDING.evidence
    nodePropertyDescriptors(4589) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4674) = FormalQtyType.RefType // FINDING.keyValuePairs
    nodePropertyDescriptors(4675) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(118) = FormalQtyType.IntType // IDENTIFIER.ARGUMENT_INDEX
    nodePropertyDescriptors(119) = FormalQtyType.QtyOne
    nodePropertyDescriptors(204) = FormalQtyType.StringType // IDENTIFIER.ARGUMENT_LABEL
    nodePropertyDescriptors(205) = FormalQtyType.QtyOption
    nodePropertyDescriptors(290) = FormalQtyType.StringType // IDENTIFIER.ARGUMENT_NAME
    nodePropertyDescriptors(291) = FormalQtyType.QtyOption
    nodePropertyDescriptors(720) = FormalQtyType.StringType // IDENTIFIER.CODE
    nodePropertyDescriptors(721) = FormalQtyType.QtyOne
    nodePropertyDescriptors(806) = FormalQtyType.IntType // IDENTIFIER.COLUMN_NUMBER
    nodePropertyDescriptors(807) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1408) = FormalQtyType.StringType // IDENTIFIER.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1409) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3042) = FormalQtyType.IntType // IDENTIFIER.LINE_NUMBER
    nodePropertyDescriptors(3043) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3386) = FormalQtyType.StringType // IDENTIFIER.NAME
    nodePropertyDescriptors(3387) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3472) = FormalQtyType.IntType // IDENTIFIER.OFFSET
    nodePropertyDescriptors(3473) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3558) = FormalQtyType.IntType // IDENTIFIER.OFFSET_END
    nodePropertyDescriptors(3559) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3644) = FormalQtyType.IntType // IDENTIFIER.ORDER
    nodePropertyDescriptors(3645) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3902) = FormalQtyType.StringType // IDENTIFIER.POSSIBLE_TYPES
    nodePropertyDescriptors(3903) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4332) = FormalQtyType.StringType // IDENTIFIER.TYPE_FULL_NAME
    nodePropertyDescriptors(4333) = FormalQtyType.QtyOne
    nodePropertyDescriptors(722) = FormalQtyType.StringType // IMPORT.CODE
    nodePropertyDescriptors(723) = FormalQtyType.QtyOne
    nodePropertyDescriptors(808) = FormalQtyType.IntType // IMPORT.COLUMN_NUMBER
    nodePropertyDescriptors(809) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1668) = FormalQtyType.BoolType // IMPORT.EXPLICIT_AS
    nodePropertyDescriptors(1669) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2098) = FormalQtyType.StringType // IMPORT.IMPORTED_AS
    nodePropertyDescriptors(2099) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2184) = FormalQtyType.StringType // IMPORT.IMPORTED_ENTITY
    nodePropertyDescriptors(2185) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2442) = FormalQtyType.BoolType // IMPORT.IS_EXPLICIT
    nodePropertyDescriptors(2443) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2614) = FormalQtyType.BoolType // IMPORT.IS_MODULE_IMPORT
    nodePropertyDescriptors(2615) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2786) = FormalQtyType.BoolType // IMPORT.IS_WILDCARD
    nodePropertyDescriptors(2787) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3044) = FormalQtyType.IntType // IMPORT.LINE_NUMBER
    nodePropertyDescriptors(3045) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3474) = FormalQtyType.IntType // IMPORT.OFFSET
    nodePropertyDescriptors(3475) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3560) = FormalQtyType.IntType // IMPORT.OFFSET_END
    nodePropertyDescriptors(3561) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3646) = FormalQtyType.IntType // IMPORT.ORDER
    nodePropertyDescriptors(3647) = FormalQtyType.QtyOne
    nodePropertyDescriptors(724) = FormalQtyType.StringType // JUMP_LABEL.CODE
    nodePropertyDescriptors(725) = FormalQtyType.QtyOne
    nodePropertyDescriptors(810) = FormalQtyType.IntType // JUMP_LABEL.COLUMN_NUMBER
    nodePropertyDescriptors(811) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3046) = FormalQtyType.IntType // JUMP_LABEL.LINE_NUMBER
    nodePropertyDescriptors(3047) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3390) = FormalQtyType.StringType // JUMP_LABEL.NAME
    nodePropertyDescriptors(3391) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3476) = FormalQtyType.IntType // JUMP_LABEL.OFFSET
    nodePropertyDescriptors(3477) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3562) = FormalQtyType.IntType // JUMP_LABEL.OFFSET_END
    nodePropertyDescriptors(3563) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3648) = FormalQtyType.IntType // JUMP_LABEL.ORDER
    nodePropertyDescriptors(3649) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3820) = FormalQtyType.StringType // JUMP_LABEL.PARSER_TYPE_NAME
    nodePropertyDescriptors(3821) = FormalQtyType.QtyOne
    nodePropertyDescriptors(124) = FormalQtyType.IntType // JUMP_TARGET.ARGUMENT_INDEX
    nodePropertyDescriptors(125) = FormalQtyType.QtyOne
    nodePropertyDescriptors(726) = FormalQtyType.StringType // JUMP_TARGET.CODE
    nodePropertyDescriptors(727) = FormalQtyType.QtyOne
    nodePropertyDescriptors(812) = FormalQtyType.IntType // JUMP_TARGET.COLUMN_NUMBER
    nodePropertyDescriptors(813) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3048) = FormalQtyType.IntType // JUMP_TARGET.LINE_NUMBER
    nodePropertyDescriptors(3049) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3392) = FormalQtyType.StringType // JUMP_TARGET.NAME
    nodePropertyDescriptors(3393) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3478) = FormalQtyType.IntType // JUMP_TARGET.OFFSET
    nodePropertyDescriptors(3479) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3564) = FormalQtyType.IntType // JUMP_TARGET.OFFSET_END
    nodePropertyDescriptors(3565) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3650) = FormalQtyType.IntType // JUMP_TARGET.ORDER
    nodePropertyDescriptors(3651) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3822) = FormalQtyType.StringType // JUMP_TARGET.PARSER_TYPE_NAME
    nodePropertyDescriptors(3823) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2878) = FormalQtyType.StringType // KEY_VALUE_PAIR.KEY
    nodePropertyDescriptors(2879) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4426) = FormalQtyType.StringType // KEY_VALUE_PAIR.VALUE
    nodePropertyDescriptors(4427) = FormalQtyType.QtyOne
    nodePropertyDescriptors(128) = FormalQtyType.IntType // LITERAL.ARGUMENT_INDEX
    nodePropertyDescriptors(129) = FormalQtyType.QtyOne
    nodePropertyDescriptors(214) = FormalQtyType.StringType // LITERAL.ARGUMENT_LABEL
    nodePropertyDescriptors(215) = FormalQtyType.QtyOption
    nodePropertyDescriptors(300) = FormalQtyType.StringType // LITERAL.ARGUMENT_NAME
    nodePropertyDescriptors(301) = FormalQtyType.QtyOption
    nodePropertyDescriptors(730) = FormalQtyType.StringType // LITERAL.CODE
    nodePropertyDescriptors(731) = FormalQtyType.QtyOne
    nodePropertyDescriptors(816) = FormalQtyType.IntType // LITERAL.COLUMN_NUMBER
    nodePropertyDescriptors(817) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1418) = FormalQtyType.StringType // LITERAL.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1419) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3052) = FormalQtyType.IntType // LITERAL.LINE_NUMBER
    nodePropertyDescriptors(3053) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3482) = FormalQtyType.IntType // LITERAL.OFFSET
    nodePropertyDescriptors(3483) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3568) = FormalQtyType.IntType // LITERAL.OFFSET_END
    nodePropertyDescriptors(3569) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3654) = FormalQtyType.IntType // LITERAL.ORDER
    nodePropertyDescriptors(3655) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3912) = FormalQtyType.StringType // LITERAL.POSSIBLE_TYPES
    nodePropertyDescriptors(3913) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4342) = FormalQtyType.StringType // LITERAL.TYPE_FULL_NAME
    nodePropertyDescriptors(4343) = FormalQtyType.QtyOne
    nodePropertyDescriptors(646) = FormalQtyType.StringType // LOCAL.CLOSURE_BINDING_ID
    nodePropertyDescriptors(647) = FormalQtyType.QtyOption
    nodePropertyDescriptors(732) = FormalQtyType.StringType // LOCAL.CODE
    nodePropertyDescriptors(733) = FormalQtyType.QtyOne
    nodePropertyDescriptors(818) = FormalQtyType.IntType // LOCAL.COLUMN_NUMBER
    nodePropertyDescriptors(819) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1420) = FormalQtyType.StringType // LOCAL.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1421) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(1936) = FormalQtyType.StringType // LOCAL.GENERIC_SIGNATURE
    nodePropertyDescriptors(1937) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3054) = FormalQtyType.IntType // LOCAL.LINE_NUMBER
    nodePropertyDescriptors(3055) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3398) = FormalQtyType.StringType // LOCAL.NAME
    nodePropertyDescriptors(3399) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3484) = FormalQtyType.IntType // LOCAL.OFFSET
    nodePropertyDescriptors(3485) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3570) = FormalQtyType.IntType // LOCAL.OFFSET_END
    nodePropertyDescriptors(3571) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3656) = FormalQtyType.IntType // LOCAL.ORDER
    nodePropertyDescriptors(3657) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3914) = FormalQtyType.StringType // LOCAL.POSSIBLE_TYPES
    nodePropertyDescriptors(3915) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4344) = FormalQtyType.StringType // LOCAL.TYPE_FULL_NAME
    nodePropertyDescriptors(4345) = FormalQtyType.QtyOne
    nodePropertyDescriptors(390) = FormalQtyType.StringType // MEMBER.AST_PARENT_FULL_NAME
    nodePropertyDescriptors(391) = FormalQtyType.QtyOne
    nodePropertyDescriptors(476) = FormalQtyType.StringType // MEMBER.AST_PARENT_TYPE
    nodePropertyDescriptors(477) = FormalQtyType.QtyOne
    nodePropertyDescriptors(734) = FormalQtyType.StringType // MEMBER.CODE
    nodePropertyDescriptors(735) = FormalQtyType.QtyOne
    nodePropertyDescriptors(820) = FormalQtyType.IntType // MEMBER.COLUMN_NUMBER
    nodePropertyDescriptors(821) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1422) = FormalQtyType.StringType // MEMBER.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1423) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(1938) = FormalQtyType.StringType // MEMBER.GENERIC_SIGNATURE
    nodePropertyDescriptors(1939) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3056) = FormalQtyType.IntType // MEMBER.LINE_NUMBER
    nodePropertyDescriptors(3057) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3400) = FormalQtyType.StringType // MEMBER.NAME
    nodePropertyDescriptors(3401) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3486) = FormalQtyType.IntType // MEMBER.OFFSET
    nodePropertyDescriptors(3487) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3572) = FormalQtyType.IntType // MEMBER.OFFSET_END
    nodePropertyDescriptors(3573) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3658) = FormalQtyType.IntType // MEMBER.ORDER
    nodePropertyDescriptors(3659) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3916) = FormalQtyType.StringType // MEMBER.POSSIBLE_TYPES
    nodePropertyDescriptors(3917) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4346) = FormalQtyType.StringType // MEMBER.TYPE_FULL_NAME
    nodePropertyDescriptors(4347) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2026) = FormalQtyType.StringType // META_DATA.HASH
    nodePropertyDescriptors(2027) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2972) = FormalQtyType.StringType // META_DATA.LANGUAGE
    nodePropertyDescriptors(2973) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3746) = FormalQtyType.StringType // META_DATA.OVERLAYS
    nodePropertyDescriptors(3747) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4004) = FormalQtyType.StringType // META_DATA.ROOT
    nodePropertyDescriptors(4005) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4520) = FormalQtyType.StringType // META_DATA.VERSION
    nodePropertyDescriptors(4521) = FormalQtyType.QtyOne
    nodePropertyDescriptors(394) = FormalQtyType.StringType // METHOD.AST_PARENT_FULL_NAME
    nodePropertyDescriptors(395) = FormalQtyType.QtyOne
    nodePropertyDescriptors(480) = FormalQtyType.StringType // METHOD.AST_PARENT_TYPE
    nodePropertyDescriptors(481) = FormalQtyType.QtyOne
    nodePropertyDescriptors(738) = FormalQtyType.StringType // METHOD.CODE
    nodePropertyDescriptors(739) = FormalQtyType.QtyOne
    nodePropertyDescriptors(824) = FormalQtyType.IntType // METHOD.COLUMN_NUMBER
    nodePropertyDescriptors(825) = FormalQtyType.QtyOption
    nodePropertyDescriptors(910) = FormalQtyType.IntType // METHOD.COLUMN_NUMBER_END
    nodePropertyDescriptors(911) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1770) = FormalQtyType.StringType // METHOD.FILENAME
    nodePropertyDescriptors(1771) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1856) = FormalQtyType.StringType // METHOD.FULL_NAME
    nodePropertyDescriptors(1857) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1942) = FormalQtyType.StringType // METHOD.GENERIC_SIGNATURE
    nodePropertyDescriptors(1943) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2028) = FormalQtyType.StringType // METHOD.HASH
    nodePropertyDescriptors(2029) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2544) = FormalQtyType.BoolType // METHOD.IS_EXTERNAL
    nodePropertyDescriptors(2545) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3060) = FormalQtyType.IntType // METHOD.LINE_NUMBER
    nodePropertyDescriptors(3061) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3146) = FormalQtyType.IntType // METHOD.LINE_NUMBER_END
    nodePropertyDescriptors(3147) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3404) = FormalQtyType.StringType // METHOD.NAME
    nodePropertyDescriptors(3405) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3490) = FormalQtyType.IntType // METHOD.OFFSET
    nodePropertyDescriptors(3491) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3576) = FormalQtyType.IntType // METHOD.OFFSET_END
    nodePropertyDescriptors(3577) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3662) = FormalQtyType.IntType // METHOD.ORDER
    nodePropertyDescriptors(3663) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4092) = FormalQtyType.StringType // METHOD.SIGNATURE
    nodePropertyDescriptors(4093) = FormalQtyType.QtyOne
    nodePropertyDescriptors(654) = FormalQtyType.StringType // METHOD_PARAMETER_IN.CLOSURE_BINDING_ID
    nodePropertyDescriptors(655) = FormalQtyType.QtyOption
    nodePropertyDescriptors(740) = FormalQtyType.StringType // METHOD_PARAMETER_IN.CODE
    nodePropertyDescriptors(741) = FormalQtyType.QtyOne
    nodePropertyDescriptors(826) = FormalQtyType.IntType // METHOD_PARAMETER_IN.COLUMN_NUMBER
    nodePropertyDescriptors(827) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1428) = FormalQtyType.StringType // METHOD_PARAMETER_IN.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1429) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(1514) = FormalQtyType.StringType // METHOD_PARAMETER_IN.EVALUATION_STRATEGY
    nodePropertyDescriptors(1515) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2288) = FormalQtyType.IntType // METHOD_PARAMETER_IN.INDEX
    nodePropertyDescriptors(2289) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2718) = FormalQtyType.BoolType // METHOD_PARAMETER_IN.IS_VARIADIC
    nodePropertyDescriptors(2719) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3062) = FormalQtyType.IntType // METHOD_PARAMETER_IN.LINE_NUMBER
    nodePropertyDescriptors(3063) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3406) = FormalQtyType.StringType // METHOD_PARAMETER_IN.NAME
    nodePropertyDescriptors(3407) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3492) = FormalQtyType.IntType // METHOD_PARAMETER_IN.OFFSET
    nodePropertyDescriptors(3493) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3578) = FormalQtyType.IntType // METHOD_PARAMETER_IN.OFFSET_END
    nodePropertyDescriptors(3579) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3664) = FormalQtyType.IntType // METHOD_PARAMETER_IN.ORDER
    nodePropertyDescriptors(3665) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3922) = FormalQtyType.StringType // METHOD_PARAMETER_IN.POSSIBLE_TYPES
    nodePropertyDescriptors(3923) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4352) = FormalQtyType.StringType // METHOD_PARAMETER_IN.TYPE_FULL_NAME
    nodePropertyDescriptors(4353) = FormalQtyType.QtyOne
    nodePropertyDescriptors(742) = FormalQtyType.StringType // METHOD_PARAMETER_OUT.CODE
    nodePropertyDescriptors(743) = FormalQtyType.QtyOne
    nodePropertyDescriptors(828) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.COLUMN_NUMBER
    nodePropertyDescriptors(829) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1516) = FormalQtyType.StringType // METHOD_PARAMETER_OUT.EVALUATION_STRATEGY
    nodePropertyDescriptors(1517) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2290) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.INDEX
    nodePropertyDescriptors(2291) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2720) = FormalQtyType.BoolType // METHOD_PARAMETER_OUT.IS_VARIADIC
    nodePropertyDescriptors(2721) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3064) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.LINE_NUMBER
    nodePropertyDescriptors(3065) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3408) = FormalQtyType.StringType // METHOD_PARAMETER_OUT.NAME
    nodePropertyDescriptors(3409) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3494) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.OFFSET
    nodePropertyDescriptors(3495) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3580) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.OFFSET_END
    nodePropertyDescriptors(3581) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3666) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.ORDER
    nodePropertyDescriptors(3667) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4354) = FormalQtyType.StringType // METHOD_PARAMETER_OUT.TYPE_FULL_NAME
    nodePropertyDescriptors(4355) = FormalQtyType.QtyOne
    nodePropertyDescriptors(142) = FormalQtyType.IntType // METHOD_REF.ARGUMENT_INDEX
    nodePropertyDescriptors(143) = FormalQtyType.QtyOne
    nodePropertyDescriptors(228) = FormalQtyType.StringType // METHOD_REF.ARGUMENT_LABEL
    nodePropertyDescriptors(229) = FormalQtyType.QtyOption
    nodePropertyDescriptors(314) = FormalQtyType.StringType // METHOD_REF.ARGUMENT_NAME
    nodePropertyDescriptors(315) = FormalQtyType.QtyOption
    nodePropertyDescriptors(744) = FormalQtyType.StringType // METHOD_REF.CODE
    nodePropertyDescriptors(745) = FormalQtyType.QtyOne
    nodePropertyDescriptors(830) = FormalQtyType.IntType // METHOD_REF.COLUMN_NUMBER
    nodePropertyDescriptors(831) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1432) = FormalQtyType.StringType // METHOD_REF.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1433) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3066) = FormalQtyType.IntType // METHOD_REF.LINE_NUMBER
    nodePropertyDescriptors(3067) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3238) = FormalQtyType.StringType // METHOD_REF.METHOD_FULL_NAME
    nodePropertyDescriptors(3239) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3496) = FormalQtyType.IntType // METHOD_REF.OFFSET
    nodePropertyDescriptors(3497) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3582) = FormalQtyType.IntType // METHOD_REF.OFFSET_END
    nodePropertyDescriptors(3583) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3668) = FormalQtyType.IntType // METHOD_REF.ORDER
    nodePropertyDescriptors(3669) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3926) = FormalQtyType.StringType // METHOD_REF.POSSIBLE_TYPES
    nodePropertyDescriptors(3927) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4356) = FormalQtyType.StringType // METHOD_REF.TYPE_FULL_NAME
    nodePropertyDescriptors(4357) = FormalQtyType.QtyOne
    nodePropertyDescriptors(746) = FormalQtyType.StringType // METHOD_RETURN.CODE
    nodePropertyDescriptors(747) = FormalQtyType.QtyOne
    nodePropertyDescriptors(832) = FormalQtyType.IntType // METHOD_RETURN.COLUMN_NUMBER
    nodePropertyDescriptors(833) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1434) = FormalQtyType.StringType // METHOD_RETURN.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1435) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(1520) = FormalQtyType.StringType // METHOD_RETURN.EVALUATION_STRATEGY
    nodePropertyDescriptors(1521) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3068) = FormalQtyType.IntType // METHOD_RETURN.LINE_NUMBER
    nodePropertyDescriptors(3069) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3498) = FormalQtyType.IntType // METHOD_RETURN.OFFSET
    nodePropertyDescriptors(3499) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3584) = FormalQtyType.IntType // METHOD_RETURN.OFFSET_END
    nodePropertyDescriptors(3585) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3670) = FormalQtyType.IntType // METHOD_RETURN.ORDER
    nodePropertyDescriptors(3671) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3928) = FormalQtyType.StringType // METHOD_RETURN.POSSIBLE_TYPES
    nodePropertyDescriptors(3929) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4358) = FormalQtyType.StringType // METHOD_RETURN.TYPE_FULL_NAME
    nodePropertyDescriptors(4359) = FormalQtyType.QtyOne
    nodePropertyDescriptors(748) = FormalQtyType.StringType // MODIFIER.CODE
    nodePropertyDescriptors(749) = FormalQtyType.QtyOne
    nodePropertyDescriptors(834) = FormalQtyType.IntType // MODIFIER.COLUMN_NUMBER
    nodePropertyDescriptors(835) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3070) = FormalQtyType.IntType // MODIFIER.LINE_NUMBER
    nodePropertyDescriptors(3071) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3328) = FormalQtyType.StringType // MODIFIER.MODIFIER_TYPE
    nodePropertyDescriptors(3329) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3500) = FormalQtyType.IntType // MODIFIER.OFFSET
    nodePropertyDescriptors(3501) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3586) = FormalQtyType.IntType // MODIFIER.OFFSET_END
    nodePropertyDescriptors(3587) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3672) = FormalQtyType.IntType // MODIFIER.ORDER
    nodePropertyDescriptors(3673) = FormalQtyType.QtyOne
    nodePropertyDescriptors(750) = FormalQtyType.StringType // NAMESPACE.CODE
    nodePropertyDescriptors(751) = FormalQtyType.QtyOne
    nodePropertyDescriptors(836) = FormalQtyType.IntType // NAMESPACE.COLUMN_NUMBER
    nodePropertyDescriptors(837) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3072) = FormalQtyType.IntType // NAMESPACE.LINE_NUMBER
    nodePropertyDescriptors(3073) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3416) = FormalQtyType.StringType // NAMESPACE.NAME
    nodePropertyDescriptors(3417) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3502) = FormalQtyType.IntType // NAMESPACE.OFFSET
    nodePropertyDescriptors(3503) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3588) = FormalQtyType.IntType // NAMESPACE.OFFSET_END
    nodePropertyDescriptors(3589) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3674) = FormalQtyType.IntType // NAMESPACE.ORDER
    nodePropertyDescriptors(3675) = FormalQtyType.QtyOne
    nodePropertyDescriptors(752) = FormalQtyType.StringType // NAMESPACE_BLOCK.CODE
    nodePropertyDescriptors(753) = FormalQtyType.QtyOne
    nodePropertyDescriptors(838) = FormalQtyType.IntType // NAMESPACE_BLOCK.COLUMN_NUMBER
    nodePropertyDescriptors(839) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1784) = FormalQtyType.StringType // NAMESPACE_BLOCK.FILENAME
    nodePropertyDescriptors(1785) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1870) = FormalQtyType.StringType // NAMESPACE_BLOCK.FULL_NAME
    nodePropertyDescriptors(1871) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3074) = FormalQtyType.IntType // NAMESPACE_BLOCK.LINE_NUMBER
    nodePropertyDescriptors(3075) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3418) = FormalQtyType.StringType // NAMESPACE_BLOCK.NAME
    nodePropertyDescriptors(3419) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3504) = FormalQtyType.IntType // NAMESPACE_BLOCK.OFFSET
    nodePropertyDescriptors(3505) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3590) = FormalQtyType.IntType // NAMESPACE_BLOCK.OFFSET_END
    nodePropertyDescriptors(3591) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3676) = FormalQtyType.IntType // NAMESPACE_BLOCK.ORDER
    nodePropertyDescriptors(3677) = FormalQtyType.QtyOne
    nodePropertyDescriptors(152) = FormalQtyType.IntType // RETURN.ARGUMENT_INDEX
    nodePropertyDescriptors(153) = FormalQtyType.QtyOne
    nodePropertyDescriptors(238) = FormalQtyType.StringType // RETURN.ARGUMENT_LABEL
    nodePropertyDescriptors(239) = FormalQtyType.QtyOption
    nodePropertyDescriptors(324) = FormalQtyType.StringType // RETURN.ARGUMENT_NAME
    nodePropertyDescriptors(325) = FormalQtyType.QtyOption
    nodePropertyDescriptors(754) = FormalQtyType.StringType // RETURN.CODE
    nodePropertyDescriptors(755) = FormalQtyType.QtyOne
    nodePropertyDescriptors(840) = FormalQtyType.IntType // RETURN.COLUMN_NUMBER
    nodePropertyDescriptors(841) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3076) = FormalQtyType.IntType // RETURN.LINE_NUMBER
    nodePropertyDescriptors(3077) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3506) = FormalQtyType.IntType // RETURN.OFFSET
    nodePropertyDescriptors(3507) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3592) = FormalQtyType.IntType // RETURN.OFFSET_END
    nodePropertyDescriptors(3593) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3678) = FormalQtyType.IntType // RETURN.ORDER
    nodePropertyDescriptors(3679) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3422) = FormalQtyType.StringType // TAG.NAME
    nodePropertyDescriptors(3423) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4454) = FormalQtyType.StringType // TAG.VALUE
    nodePropertyDescriptors(4455) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4800) = FormalQtyType.RefType // TAG_NODE_PAIR.node
    nodePropertyDescriptors(4801) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4886) = FormalQtyType.RefType // TAG_NODE_PAIR.tag
    nodePropertyDescriptors(4887) = FormalQtyType.QtyOne
    nodePropertyDescriptors(158) = FormalQtyType.IntType // TEMPLATE_DOM.ARGUMENT_INDEX
    nodePropertyDescriptors(159) = FormalQtyType.QtyOne
    nodePropertyDescriptors(244) = FormalQtyType.StringType // TEMPLATE_DOM.ARGUMENT_LABEL
    nodePropertyDescriptors(245) = FormalQtyType.QtyOption
    nodePropertyDescriptors(330) = FormalQtyType.StringType // TEMPLATE_DOM.ARGUMENT_NAME
    nodePropertyDescriptors(331) = FormalQtyType.QtyOption
    nodePropertyDescriptors(760) = FormalQtyType.StringType // TEMPLATE_DOM.CODE
    nodePropertyDescriptors(761) = FormalQtyType.QtyOne
    nodePropertyDescriptors(846) = FormalQtyType.IntType // TEMPLATE_DOM.COLUMN_NUMBER
    nodePropertyDescriptors(847) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3082) = FormalQtyType.IntType // TEMPLATE_DOM.LINE_NUMBER
    nodePropertyDescriptors(3083) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3426) = FormalQtyType.StringType // TEMPLATE_DOM.NAME
    nodePropertyDescriptors(3427) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3512) = FormalQtyType.IntType // TEMPLATE_DOM.OFFSET
    nodePropertyDescriptors(3513) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3598) = FormalQtyType.IntType // TEMPLATE_DOM.OFFSET_END
    nodePropertyDescriptors(3599) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3684) = FormalQtyType.IntType // TEMPLATE_DOM.ORDER
    nodePropertyDescriptors(3685) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1880) = FormalQtyType.StringType // TYPE.FULL_NAME
    nodePropertyDescriptors(1881) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3428) = FormalQtyType.StringType // TYPE.NAME
    nodePropertyDescriptors(3429) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4288) = FormalQtyType.StringType // TYPE.TYPE_DECL_FULL_NAME
    nodePropertyDescriptors(4289) = FormalQtyType.QtyOne
    nodePropertyDescriptors(764) = FormalQtyType.StringType // TYPE_ARGUMENT.CODE
    nodePropertyDescriptors(765) = FormalQtyType.QtyOne
    nodePropertyDescriptors(850) = FormalQtyType.IntType // TYPE_ARGUMENT.COLUMN_NUMBER
    nodePropertyDescriptors(851) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3086) = FormalQtyType.IntType // TYPE_ARGUMENT.LINE_NUMBER
    nodePropertyDescriptors(3087) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3516) = FormalQtyType.IntType // TYPE_ARGUMENT.OFFSET
    nodePropertyDescriptors(3517) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3602) = FormalQtyType.IntType // TYPE_ARGUMENT.OFFSET_END
    nodePropertyDescriptors(3603) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3688) = FormalQtyType.IntType // TYPE_ARGUMENT.ORDER
    nodePropertyDescriptors(3689) = FormalQtyType.QtyOne
    nodePropertyDescriptors(78) = FormalQtyType.StringType // TYPE_DECL.ALIAS_TYPE_FULL_NAME
    nodePropertyDescriptors(79) = FormalQtyType.QtyOption
    nodePropertyDescriptors(422) = FormalQtyType.StringType // TYPE_DECL.AST_PARENT_FULL_NAME
    nodePropertyDescriptors(423) = FormalQtyType.QtyOne
    nodePropertyDescriptors(508) = FormalQtyType.StringType // TYPE_DECL.AST_PARENT_TYPE
    nodePropertyDescriptors(509) = FormalQtyType.QtyOne
    nodePropertyDescriptors(766) = FormalQtyType.StringType // TYPE_DECL.CODE
    nodePropertyDescriptors(767) = FormalQtyType.QtyOne
    nodePropertyDescriptors(852) = FormalQtyType.IntType // TYPE_DECL.COLUMN_NUMBER
    nodePropertyDescriptors(853) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1798) = FormalQtyType.StringType // TYPE_DECL.FILENAME
    nodePropertyDescriptors(1799) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1884) = FormalQtyType.StringType // TYPE_DECL.FULL_NAME
    nodePropertyDescriptors(1885) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1970) = FormalQtyType.StringType // TYPE_DECL.GENERIC_SIGNATURE
    nodePropertyDescriptors(1971) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2400) = FormalQtyType.StringType // TYPE_DECL.INHERITS_FROM_TYPE_FULL_NAME
    nodePropertyDescriptors(2401) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(2572) = FormalQtyType.BoolType // TYPE_DECL.IS_EXTERNAL
    nodePropertyDescriptors(2573) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3088) = FormalQtyType.IntType // TYPE_DECL.LINE_NUMBER
    nodePropertyDescriptors(3089) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3432) = FormalQtyType.StringType // TYPE_DECL.NAME
    nodePropertyDescriptors(3433) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3518) = FormalQtyType.IntType // TYPE_DECL.OFFSET
    nodePropertyDescriptors(3519) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3604) = FormalQtyType.IntType // TYPE_DECL.OFFSET_END
    nodePropertyDescriptors(3605) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3690) = FormalQtyType.IntType // TYPE_DECL.ORDER
    nodePropertyDescriptors(3691) = FormalQtyType.QtyOne
    nodePropertyDescriptors(768) = FormalQtyType.StringType // TYPE_PARAMETER.CODE
    nodePropertyDescriptors(769) = FormalQtyType.QtyOne
    nodePropertyDescriptors(854) = FormalQtyType.IntType // TYPE_PARAMETER.COLUMN_NUMBER
    nodePropertyDescriptors(855) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3090) = FormalQtyType.IntType // TYPE_PARAMETER.LINE_NUMBER
    nodePropertyDescriptors(3091) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3434) = FormalQtyType.StringType // TYPE_PARAMETER.NAME
    nodePropertyDescriptors(3435) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3520) = FormalQtyType.IntType // TYPE_PARAMETER.OFFSET
    nodePropertyDescriptors(3521) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3606) = FormalQtyType.IntType // TYPE_PARAMETER.OFFSET_END
    nodePropertyDescriptors(3607) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3692) = FormalQtyType.IntType // TYPE_PARAMETER.ORDER
    nodePropertyDescriptors(3693) = FormalQtyType.QtyOne
    nodePropertyDescriptors(168) = FormalQtyType.IntType // TYPE_REF.ARGUMENT_INDEX
    nodePropertyDescriptors(169) = FormalQtyType.QtyOne
    nodePropertyDescriptors(254) = FormalQtyType.StringType // TYPE_REF.ARGUMENT_LABEL
    nodePropertyDescriptors(255) = FormalQtyType.QtyOption
    nodePropertyDescriptors(340) = FormalQtyType.StringType // TYPE_REF.ARGUMENT_NAME
    nodePropertyDescriptors(341) = FormalQtyType.QtyOption
    nodePropertyDescriptors(770) = FormalQtyType.StringType // TYPE_REF.CODE
    nodePropertyDescriptors(771) = FormalQtyType.QtyOne
    nodePropertyDescriptors(856) = FormalQtyType.IntType // TYPE_REF.COLUMN_NUMBER
    nodePropertyDescriptors(857) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1458) = FormalQtyType.StringType // TYPE_REF.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1459) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3092) = FormalQtyType.IntType // TYPE_REF.LINE_NUMBER
    nodePropertyDescriptors(3093) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3522) = FormalQtyType.IntType // TYPE_REF.OFFSET
    nodePropertyDescriptors(3523) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3608) = FormalQtyType.IntType // TYPE_REF.OFFSET_END
    nodePropertyDescriptors(3609) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3694) = FormalQtyType.IntType // TYPE_REF.ORDER
    nodePropertyDescriptors(3695) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3952) = FormalQtyType.StringType // TYPE_REF.POSSIBLE_TYPES
    nodePropertyDescriptors(3953) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4382) = FormalQtyType.StringType // TYPE_REF.TYPE_FULL_NAME
    nodePropertyDescriptors(4383) = FormalQtyType.QtyOne
    nodePropertyDescriptors(170) = FormalQtyType.IntType // UNKNOWN.ARGUMENT_INDEX
    nodePropertyDescriptors(171) = FormalQtyType.QtyOne
    nodePropertyDescriptors(256) = FormalQtyType.StringType // UNKNOWN.ARGUMENT_LABEL
    nodePropertyDescriptors(257) = FormalQtyType.QtyOption
    nodePropertyDescriptors(342) = FormalQtyType.StringType // UNKNOWN.ARGUMENT_NAME
    nodePropertyDescriptors(343) = FormalQtyType.QtyOption
    nodePropertyDescriptors(772) = FormalQtyType.StringType // UNKNOWN.CODE
    nodePropertyDescriptors(773) = FormalQtyType.QtyOne
    nodePropertyDescriptors(858) = FormalQtyType.IntType // UNKNOWN.COLUMN_NUMBER
    nodePropertyDescriptors(859) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1030) = FormalQtyType.StringType // UNKNOWN.CONTAINED_REF
    nodePropertyDescriptors(1031) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1460) = FormalQtyType.StringType // UNKNOWN.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1461) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3094) = FormalQtyType.IntType // UNKNOWN.LINE_NUMBER
    nodePropertyDescriptors(3095) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3524) = FormalQtyType.IntType // UNKNOWN.OFFSET
    nodePropertyDescriptors(3525) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3610) = FormalQtyType.IntType // UNKNOWN.OFFSET_END
    nodePropertyDescriptors(3611) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3696) = FormalQtyType.IntType // UNKNOWN.ORDER
    nodePropertyDescriptors(3697) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3868) = FormalQtyType.StringType // UNKNOWN.PARSER_TYPE_NAME
    nodePropertyDescriptors(3869) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3954) = FormalQtyType.StringType // UNKNOWN.POSSIBLE_TYPES
    nodePropertyDescriptors(3955) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4384) = FormalQtyType.StringType // UNKNOWN.TYPE_FULL_NAME
    nodePropertyDescriptors(4385) = FormalQtyType.QtyOne
    nodePropertyDescriptors
  }
  private val newNodeInsertionHelpers: Array[flatgraph.NewNodePropertyInsertionHelper] = {
    val _newNodeInserters = new Array[flatgraph.NewNodePropertyInsertionHelper](4902)
    _newNodeInserters(86) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_argumentIndex
    _newNodeInserters(172) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_argumentLabel
    _newNodeInserters(258) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_argumentName
    _newNodeInserters(688) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_code
    _newNodeInserters(774) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_columnNumber
    _newNodeInserters(1806) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_fullName
    _newNodeInserters(3010) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_lineNumber
    _newNodeInserters(3354) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_name
    _newNodeInserters(3440) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_offset
    _newNodeInserters(3526) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_offsetEnd
    _newNodeInserters(3612) = nodes.NewAnnotation.InsertionHelpers.NewNodeInserter_Annotation_order
    _newNodeInserters(88) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_argumentIndex
    _newNodeInserters(174) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_argumentLabel
    _newNodeInserters(260) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_argumentName
    _newNodeInserters(690) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_code
    _newNodeInserters(776) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_columnNumber
    _newNodeInserters(3012) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_lineNumber
    _newNodeInserters(3356) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_name
    _newNodeInserters(3442) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_offset
    _newNodeInserters(3528) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_offsetEnd
    _newNodeInserters(3614) = nodes.NewAnnotationLiteral.InsertionHelpers.NewNodeInserter_AnnotationLiteral_order
    _newNodeInserters(692) = nodes.NewAnnotationParameter.InsertionHelpers.NewNodeInserter_AnnotationParameter_code
    _newNodeInserters(778) =
      nodes.NewAnnotationParameter.InsertionHelpers.NewNodeInserter_AnnotationParameter_columnNumber
    _newNodeInserters(3014) =
      nodes.NewAnnotationParameter.InsertionHelpers.NewNodeInserter_AnnotationParameter_lineNumber
    _newNodeInserters(3444) = nodes.NewAnnotationParameter.InsertionHelpers.NewNodeInserter_AnnotationParameter_offset
    _newNodeInserters(3530) =
      nodes.NewAnnotationParameter.InsertionHelpers.NewNodeInserter_AnnotationParameter_offsetEnd
    _newNodeInserters(3616) = nodes.NewAnnotationParameter.InsertionHelpers.NewNodeInserter_AnnotationParameter_order
    _newNodeInserters(694) =
      nodes.NewAnnotationParameterAssign.InsertionHelpers.NewNodeInserter_AnnotationParameterAssign_code
    _newNodeInserters(780) =
      nodes.NewAnnotationParameterAssign.InsertionHelpers.NewNodeInserter_AnnotationParameterAssign_columnNumber
    _newNodeInserters(3016) =
      nodes.NewAnnotationParameterAssign.InsertionHelpers.NewNodeInserter_AnnotationParameterAssign_lineNumber
    _newNodeInserters(3446) =
      nodes.NewAnnotationParameterAssign.InsertionHelpers.NewNodeInserter_AnnotationParameterAssign_offset
    _newNodeInserters(3532) =
      nodes.NewAnnotationParameterAssign.InsertionHelpers.NewNodeInserter_AnnotationParameterAssign_offsetEnd
    _newNodeInserters(3618) =
      nodes.NewAnnotationParameterAssign.InsertionHelpers.NewNodeInserter_AnnotationParameterAssign_order
    _newNodeInserters(94) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_argumentIndex
    _newNodeInserters(180) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_argumentLabel
    _newNodeInserters(266) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_argumentName
    _newNodeInserters(696) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_code
    _newNodeInserters(782) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_columnNumber
    _newNodeInserters(3018) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_lineNumber
    _newNodeInserters(3448) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_offset
    _newNodeInserters(3534) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_offsetEnd
    _newNodeInserters(3620) = nodes.NewArrayInitializer.InsertionHelpers.NewNodeInserter_ArrayInitializer_order
    _newNodeInserters(3192) = nodes.NewBinding.InsertionHelpers.NewNodeInserter_Binding_methodFullName
    _newNodeInserters(3364) = nodes.NewBinding.InsertionHelpers.NewNodeInserter_Binding_name
    _newNodeInserters(4052) = nodes.NewBinding.InsertionHelpers.NewNodeInserter_Binding_signature
    _newNodeInserters(98) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_argumentIndex
    _newNodeInserters(184) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_argumentLabel
    _newNodeInserters(270) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_argumentName
    _newNodeInserters(700) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_code
    _newNodeInserters(786) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_columnNumber
    _newNodeInserters(1388) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_dynamicTypeHintFullName
    _newNodeInserters(3022) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_lineNumber
    _newNodeInserters(3452) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_offset
    _newNodeInserters(3538) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_offsetEnd
    _newNodeInserters(3624) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_order
    _newNodeInserters(3882) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_possibleTypes
    _newNodeInserters(4312) = nodes.NewBlock.InsertionHelpers.NewNodeInserter_Block_typeFullName
    _newNodeInserters(100) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_argumentIndex
    _newNodeInserters(186) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_argumentLabel
    _newNodeInserters(272) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_argumentName
    _newNodeInserters(702) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_code
    _newNodeInserters(788) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_columnNumber
    _newNodeInserters(1304) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_dispatchType
    _newNodeInserters(1390) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_dynamicTypeHintFullName
    _newNodeInserters(3024) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_lineNumber
    _newNodeInserters(3196) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_methodFullName
    _newNodeInserters(3368) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_name
    _newNodeInserters(3454) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_offset
    _newNodeInserters(3540) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_offsetEnd
    _newNodeInserters(3626) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_order
    _newNodeInserters(3884) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_possibleTypes
    _newNodeInserters(4056) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_signature
    _newNodeInserters(4142) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_staticReceiver
    _newNodeInserters(4314) = nodes.NewCall.InsertionHelpers.NewNodeInserter_Call_typeFullName
    _newNodeInserters(618) = nodes.NewClosureBinding.InsertionHelpers.NewNodeInserter_ClosureBinding_closureBindingId
    _newNodeInserters(1478) = nodes.NewClosureBinding.InsertionHelpers.NewNodeInserter_ClosureBinding_evaluationStrategy
    _newNodeInserters(706) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_code
    _newNodeInserters(792) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_columnNumber
    _newNodeInserters(1738) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_filename
    _newNodeInserters(3028) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_lineNumber
    _newNodeInserters(3458) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_offset
    _newNodeInserters(3544) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_offsetEnd
    _newNodeInserters(3630) = nodes.NewComment.InsertionHelpers.NewNodeInserter_Comment_order
    _newNodeInserters(1052) = nodes.NewConfigFile.InsertionHelpers.NewNodeInserter_ConfigFile_content
    _newNodeInserters(3374) = nodes.NewConfigFile.InsertionHelpers.NewNodeInserter_ConfigFile_name
    _newNodeInserters(108) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_argumentIndex
    _newNodeInserters(194) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_argumentLabel
    _newNodeInserters(280) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_argumentName
    _newNodeInserters(710) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_code
    _newNodeInserters(796) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_columnNumber
    _newNodeInserters(1140) =
      nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_controlStructureType
    _newNodeInserters(3032) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_lineNumber
    _newNodeInserters(3462) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_offset
    _newNodeInserters(3548) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_offsetEnd
    _newNodeInserters(3634) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_order
    _newNodeInserters(3806) = nodes.NewControlStructure.InsertionHelpers.NewNodeInserter_ControlStructure_parserTypeName
    _newNodeInserters(1228) = nodes.NewDependency.InsertionHelpers.NewNodeInserter_Dependency_dependencyGroupId
    _newNodeInserters(3378) = nodes.NewDependency.InsertionHelpers.NewNodeInserter_Dependency_name
    _newNodeInserters(4496) = nodes.NewDependency.InsertionHelpers.NewNodeInserter_Dependency_version
    _newNodeInserters(112) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_argumentIndex
    _newNodeInserters(198) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_argumentLabel
    _newNodeInserters(284) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_argumentName
    _newNodeInserters(542) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_canonicalName
    _newNodeInserters(714) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_code
    _newNodeInserters(800) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_columnNumber
    _newNodeInserters(3036) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_lineNumber
    _newNodeInserters(3466) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_offset
    _newNodeInserters(3552) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_offsetEnd
    _newNodeInserters(3638) = nodes.NewFieldIdentifier.InsertionHelpers.NewNodeInserter_FieldIdentifier_order
    _newNodeInserters(716) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_code
    _newNodeInserters(802) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_columnNumber
    _newNodeInserters(1060) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_content
    _newNodeInserters(2006) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_hash
    _newNodeInserters(3038) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_lineNumber
    _newNodeInserters(3382) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_name
    _newNodeInserters(3468) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_offset
    _newNodeInserters(3554) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_offsetEnd
    _newNodeInserters(3640) = nodes.NewFile.InsertionHelpers.NewNodeInserter_File_order
    _newNodeInserters(1578) = nodes.NewFinding.InsertionHelpers.NewNodeInserter_Finding_evidenceDescription
    _newNodeInserters(4588) = nodes.NewFinding.InsertionHelpers.NewNodeInserter_Finding_evidence
    _newNodeInserters(4674) = nodes.NewFinding.InsertionHelpers.NewNodeInserter_Finding_keyValuePairs
    _newNodeInserters(118) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_argumentIndex
    _newNodeInserters(204) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_argumentLabel
    _newNodeInserters(290) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_argumentName
    _newNodeInserters(720) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_code
    _newNodeInserters(806) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_columnNumber
    _newNodeInserters(1408) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_dynamicTypeHintFullName
    _newNodeInserters(3042) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_lineNumber
    _newNodeInserters(3386) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_name
    _newNodeInserters(3472) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_offset
    _newNodeInserters(3558) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_offsetEnd
    _newNodeInserters(3644) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_order
    _newNodeInserters(3902) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_possibleTypes
    _newNodeInserters(4332) = nodes.NewIdentifier.InsertionHelpers.NewNodeInserter_Identifier_typeFullName
    _newNodeInserters(722) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_code
    _newNodeInserters(808) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_columnNumber
    _newNodeInserters(1668) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_explicitAs
    _newNodeInserters(2098) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_importedAs
    _newNodeInserters(2184) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_importedEntity
    _newNodeInserters(2442) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_isExplicit
    _newNodeInserters(2614) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_isModuleImport
    _newNodeInserters(2786) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_isWildcard
    _newNodeInserters(3044) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_lineNumber
    _newNodeInserters(3474) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_offset
    _newNodeInserters(3560) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_offsetEnd
    _newNodeInserters(3646) = nodes.NewImport.InsertionHelpers.NewNodeInserter_Import_order
    _newNodeInserters(724) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_code
    _newNodeInserters(810) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_columnNumber
    _newNodeInserters(3046) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_lineNumber
    _newNodeInserters(3390) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_name
    _newNodeInserters(3476) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_offset
    _newNodeInserters(3562) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_offsetEnd
    _newNodeInserters(3648) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_order
    _newNodeInserters(3820) = nodes.NewJumpLabel.InsertionHelpers.NewNodeInserter_JumpLabel_parserTypeName
    _newNodeInserters(124) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_argumentIndex
    _newNodeInserters(726) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_code
    _newNodeInserters(812) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_columnNumber
    _newNodeInserters(3048) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_lineNumber
    _newNodeInserters(3392) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_name
    _newNodeInserters(3478) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_offset
    _newNodeInserters(3564) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_offsetEnd
    _newNodeInserters(3650) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_order
    _newNodeInserters(3822) = nodes.NewJumpTarget.InsertionHelpers.NewNodeInserter_JumpTarget_parserTypeName
    _newNodeInserters(2878) = nodes.NewKeyValuePair.InsertionHelpers.NewNodeInserter_KeyValuePair_key
    _newNodeInserters(4426) = nodes.NewKeyValuePair.InsertionHelpers.NewNodeInserter_KeyValuePair_value
    _newNodeInserters(128) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_argumentIndex
    _newNodeInserters(214) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_argumentLabel
    _newNodeInserters(300) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_argumentName
    _newNodeInserters(730) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_code
    _newNodeInserters(816) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_columnNumber
    _newNodeInserters(1418) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_dynamicTypeHintFullName
    _newNodeInserters(3052) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_lineNumber
    _newNodeInserters(3482) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_offset
    _newNodeInserters(3568) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_offsetEnd
    _newNodeInserters(3654) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_order
    _newNodeInserters(3912) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_possibleTypes
    _newNodeInserters(4342) = nodes.NewLiteral.InsertionHelpers.NewNodeInserter_Literal_typeFullName
    _newNodeInserters(646) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_closureBindingId
    _newNodeInserters(732) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_code
    _newNodeInserters(818) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_columnNumber
    _newNodeInserters(1420) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_dynamicTypeHintFullName
    _newNodeInserters(1936) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_genericSignature
    _newNodeInserters(3054) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_lineNumber
    _newNodeInserters(3398) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_name
    _newNodeInserters(3484) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_offset
    _newNodeInserters(3570) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_offsetEnd
    _newNodeInserters(3656) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_order
    _newNodeInserters(3914) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_possibleTypes
    _newNodeInserters(4344) = nodes.NewLocal.InsertionHelpers.NewNodeInserter_Local_typeFullName
    _newNodeInserters(390) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_astParentFullName
    _newNodeInserters(476) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_astParentType
    _newNodeInserters(734) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_code
    _newNodeInserters(820) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_columnNumber
    _newNodeInserters(1422) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_dynamicTypeHintFullName
    _newNodeInserters(1938) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_genericSignature
    _newNodeInserters(3056) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_lineNumber
    _newNodeInserters(3400) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_name
    _newNodeInserters(3486) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_offset
    _newNodeInserters(3572) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_offsetEnd
    _newNodeInserters(3658) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_order
    _newNodeInserters(3916) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_possibleTypes
    _newNodeInserters(4346) = nodes.NewMember.InsertionHelpers.NewNodeInserter_Member_typeFullName
    _newNodeInserters(2026) = nodes.NewMetaData.InsertionHelpers.NewNodeInserter_MetaData_hash
    _newNodeInserters(2972) = nodes.NewMetaData.InsertionHelpers.NewNodeInserter_MetaData_language
    _newNodeInserters(3746) = nodes.NewMetaData.InsertionHelpers.NewNodeInserter_MetaData_overlays
    _newNodeInserters(4004) = nodes.NewMetaData.InsertionHelpers.NewNodeInserter_MetaData_root
    _newNodeInserters(4520) = nodes.NewMetaData.InsertionHelpers.NewNodeInserter_MetaData_version
    _newNodeInserters(394) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_astParentFullName
    _newNodeInserters(480) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_astParentType
    _newNodeInserters(738) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_code
    _newNodeInserters(824) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_columnNumber
    _newNodeInserters(910) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_columnNumberEnd
    _newNodeInserters(1770) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_filename
    _newNodeInserters(1856) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_fullName
    _newNodeInserters(1942) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_genericSignature
    _newNodeInserters(2028) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_hash
    _newNodeInserters(2544) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_isExternal
    _newNodeInserters(3060) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_lineNumber
    _newNodeInserters(3146) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_lineNumberEnd
    _newNodeInserters(3404) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_name
    _newNodeInserters(3490) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_offset
    _newNodeInserters(3576) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_offsetEnd
    _newNodeInserters(3662) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_order
    _newNodeInserters(4092) = nodes.NewMethod.InsertionHelpers.NewNodeInserter_Method_signature
    _newNodeInserters(654) =
      nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_closureBindingId
    _newNodeInserters(740) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_code
    _newNodeInserters(826) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_columnNumber
    _newNodeInserters(1428) =
      nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_dynamicTypeHintFullName
    _newNodeInserters(1514) =
      nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_evaluationStrategy
    _newNodeInserters(2288) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_index
    _newNodeInserters(2718) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_isVariadic
    _newNodeInserters(3062) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_lineNumber
    _newNodeInserters(3406) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_name
    _newNodeInserters(3492) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_offset
    _newNodeInserters(3578) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_offsetEnd
    _newNodeInserters(3664) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_order
    _newNodeInserters(3922) =
      nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_possibleTypes
    _newNodeInserters(4352) = nodes.NewMethodParameterIn.InsertionHelpers.NewNodeInserter_MethodParameterIn_typeFullName
    _newNodeInserters(742) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_code
    _newNodeInserters(828) =
      nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_columnNumber
    _newNodeInserters(1516) =
      nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_evaluationStrategy
    _newNodeInserters(2290) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_index
    _newNodeInserters(2720) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_isVariadic
    _newNodeInserters(3064) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_lineNumber
    _newNodeInserters(3408) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_name
    _newNodeInserters(3494) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_offset
    _newNodeInserters(3580) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_offsetEnd
    _newNodeInserters(3666) = nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_order
    _newNodeInserters(4354) =
      nodes.NewMethodParameterOut.InsertionHelpers.NewNodeInserter_MethodParameterOut_typeFullName
    _newNodeInserters(142) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_argumentIndex
    _newNodeInserters(228) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_argumentLabel
    _newNodeInserters(314) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_argumentName
    _newNodeInserters(744) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_code
    _newNodeInserters(830) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_columnNumber
    _newNodeInserters(1432) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_dynamicTypeHintFullName
    _newNodeInserters(3066) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_lineNumber
    _newNodeInserters(3238) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_methodFullName
    _newNodeInserters(3496) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_offset
    _newNodeInserters(3582) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_offsetEnd
    _newNodeInserters(3668) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_order
    _newNodeInserters(3926) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_possibleTypes
    _newNodeInserters(4356) = nodes.NewMethodRef.InsertionHelpers.NewNodeInserter_MethodRef_typeFullName
    _newNodeInserters(746) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_code
    _newNodeInserters(832) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_columnNumber
    _newNodeInserters(1434) =
      nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_dynamicTypeHintFullName
    _newNodeInserters(1520) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_evaluationStrategy
    _newNodeInserters(3068) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_lineNumber
    _newNodeInserters(3498) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_offset
    _newNodeInserters(3584) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_offsetEnd
    _newNodeInserters(3670) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_order
    _newNodeInserters(3928) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_possibleTypes
    _newNodeInserters(4358) = nodes.NewMethodReturn.InsertionHelpers.NewNodeInserter_MethodReturn_typeFullName
    _newNodeInserters(748) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_code
    _newNodeInserters(834) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_columnNumber
    _newNodeInserters(3070) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_lineNumber
    _newNodeInserters(3328) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_modifierType
    _newNodeInserters(3500) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_offset
    _newNodeInserters(3586) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_offsetEnd
    _newNodeInserters(3672) = nodes.NewModifier.InsertionHelpers.NewNodeInserter_Modifier_order
    _newNodeInserters(750) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_code
    _newNodeInserters(836) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_columnNumber
    _newNodeInserters(3072) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_lineNumber
    _newNodeInserters(3416) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_name
    _newNodeInserters(3502) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_offset
    _newNodeInserters(3588) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_offsetEnd
    _newNodeInserters(3674) = nodes.NewNamespace.InsertionHelpers.NewNodeInserter_Namespace_order
    _newNodeInserters(752) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_code
    _newNodeInserters(838) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_columnNumber
    _newNodeInserters(1784) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_filename
    _newNodeInserters(1870) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_fullName
    _newNodeInserters(3074) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_lineNumber
    _newNodeInserters(3418) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_name
    _newNodeInserters(3504) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_offset
    _newNodeInserters(3590) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_offsetEnd
    _newNodeInserters(3676) = nodes.NewNamespaceBlock.InsertionHelpers.NewNodeInserter_NamespaceBlock_order
    _newNodeInserters(152) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_argumentIndex
    _newNodeInserters(238) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_argumentLabel
    _newNodeInserters(324) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_argumentName
    _newNodeInserters(754) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_code
    _newNodeInserters(840) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_columnNumber
    _newNodeInserters(3076) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_lineNumber
    _newNodeInserters(3506) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_offset
    _newNodeInserters(3592) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_offsetEnd
    _newNodeInserters(3678) = nodes.NewReturn.InsertionHelpers.NewNodeInserter_Return_order
    _newNodeInserters(3422) = nodes.NewTag.InsertionHelpers.NewNodeInserter_Tag_name
    _newNodeInserters(4454) = nodes.NewTag.InsertionHelpers.NewNodeInserter_Tag_value
    _newNodeInserters(4800) = nodes.NewTagNodePair.InsertionHelpers.NewNodeInserter_TagNodePair_node
    _newNodeInserters(4886) = nodes.NewTagNodePair.InsertionHelpers.NewNodeInserter_TagNodePair_tag
    _newNodeInserters(158) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_argumentIndex
    _newNodeInserters(244) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_argumentLabel
    _newNodeInserters(330) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_argumentName
    _newNodeInserters(760) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_code
    _newNodeInserters(846) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_columnNumber
    _newNodeInserters(3082) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_lineNumber
    _newNodeInserters(3426) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_name
    _newNodeInserters(3512) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_offset
    _newNodeInserters(3598) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_offsetEnd
    _newNodeInserters(3684) = nodes.NewTemplateDom.InsertionHelpers.NewNodeInserter_TemplateDom_order
    _newNodeInserters(1880) = nodes.NewType.InsertionHelpers.NewNodeInserter_Type_fullName
    _newNodeInserters(3428) = nodes.NewType.InsertionHelpers.NewNodeInserter_Type_name
    _newNodeInserters(4288) = nodes.NewType.InsertionHelpers.NewNodeInserter_Type_typeDeclFullName
    _newNodeInserters(764) = nodes.NewTypeArgument.InsertionHelpers.NewNodeInserter_TypeArgument_code
    _newNodeInserters(850) = nodes.NewTypeArgument.InsertionHelpers.NewNodeInserter_TypeArgument_columnNumber
    _newNodeInserters(3086) = nodes.NewTypeArgument.InsertionHelpers.NewNodeInserter_TypeArgument_lineNumber
    _newNodeInserters(3516) = nodes.NewTypeArgument.InsertionHelpers.NewNodeInserter_TypeArgument_offset
    _newNodeInserters(3602) = nodes.NewTypeArgument.InsertionHelpers.NewNodeInserter_TypeArgument_offsetEnd
    _newNodeInserters(3688) = nodes.NewTypeArgument.InsertionHelpers.NewNodeInserter_TypeArgument_order
    _newNodeInserters(78) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_aliasTypeFullName
    _newNodeInserters(422) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_astParentFullName
    _newNodeInserters(508) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_astParentType
    _newNodeInserters(766) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_code
    _newNodeInserters(852) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_columnNumber
    _newNodeInserters(1798) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_filename
    _newNodeInserters(1884) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_fullName
    _newNodeInserters(1970) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_genericSignature
    _newNodeInserters(2400) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_inheritsFromTypeFullName
    _newNodeInserters(2572) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_isExternal
    _newNodeInserters(3088) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_lineNumber
    _newNodeInserters(3432) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_name
    _newNodeInserters(3518) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_offset
    _newNodeInserters(3604) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_offsetEnd
    _newNodeInserters(3690) = nodes.NewTypeDecl.InsertionHelpers.NewNodeInserter_TypeDecl_order
    _newNodeInserters(768) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_code
    _newNodeInserters(854) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_columnNumber
    _newNodeInserters(3090) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_lineNumber
    _newNodeInserters(3434) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_name
    _newNodeInserters(3520) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_offset
    _newNodeInserters(3606) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_offsetEnd
    _newNodeInserters(3692) = nodes.NewTypeParameter.InsertionHelpers.NewNodeInserter_TypeParameter_order
    _newNodeInserters(168) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_argumentIndex
    _newNodeInserters(254) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_argumentLabel
    _newNodeInserters(340) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_argumentName
    _newNodeInserters(770) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_code
    _newNodeInserters(856) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_columnNumber
    _newNodeInserters(1458) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_dynamicTypeHintFullName
    _newNodeInserters(3092) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_lineNumber
    _newNodeInserters(3522) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_offset
    _newNodeInserters(3608) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_offsetEnd
    _newNodeInserters(3694) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_order
    _newNodeInserters(3952) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_possibleTypes
    _newNodeInserters(4382) = nodes.NewTypeRef.InsertionHelpers.NewNodeInserter_TypeRef_typeFullName
    _newNodeInserters(170) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_argumentIndex
    _newNodeInserters(256) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_argumentLabel
    _newNodeInserters(342) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_argumentName
    _newNodeInserters(772) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_code
    _newNodeInserters(858) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_columnNumber
    _newNodeInserters(1030) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_containedRef
    _newNodeInserters(1460) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_dynamicTypeHintFullName
    _newNodeInserters(3094) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_lineNumber
    _newNodeInserters(3524) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_offset
    _newNodeInserters(3610) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_offsetEnd
    _newNodeInserters(3696) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_order
    _newNodeInserters(3868) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_parserTypeName
    _newNodeInserters(3954) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_possibleTypes
    _newNodeInserters(4384) = nodes.NewUnknown.InsertionHelpers.NewNodeInserter_Unknown_typeFullName
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
          "ARGUMENT_LABEL",
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
          "ARGUMENT_LABEL",
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
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_LABEL",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "LINE_NUMBER",
          "OFFSET",
          "OFFSET_END",
          "ORDER"
        )
      case "BINDING" => Set("METHOD_FULL_NAME", "NAME", "SIGNATURE")
      case "BLOCK" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_LABEL",
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
          "ARGUMENT_LABEL",
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
          "ARGUMENT_LABEL",
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
          "ARGUMENT_LABEL",
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
          "ARGUMENT_LABEL",
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
          "IS_MODULE_IMPORT",
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
          "ARGUMENT_LABEL",
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
          "ARGUMENT_LABEL",
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
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_LABEL",
          "ARGUMENT_NAME",
          "CODE",
          "COLUMN_NUMBER",
          "LINE_NUMBER",
          "OFFSET",
          "OFFSET_END",
          "ORDER"
        )
      case "TAG"           => Set("NAME", "VALUE")
      case "TAG_NODE_PAIR" => Set()
      case "TEMPLATE_DOM" =>
        Set(
          "ARGUMENT_INDEX",
          "ARGUMENT_LABEL",
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
          "ARGUMENT_LABEL",
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
          "ARGUMENT_LABEL",
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
    if (propertyKind < 53) normalNodePropertyNames(propertyKind)
    else if (propertyKind == 53 && nodeKind == 15) "evidence"      /*on node FINDING*/
    else if (propertyKind == 54 && nodeKind == 15) "keyValuePairs" /*on node FINDING*/
    else if (propertyKind == 55 && nodeKind == 35) "node"          /*on node TAG_NODE_PAIR*/
    else if (propertyKind == 56 && nodeKind == 35) "tag"           /*on node TAG_NODE_PAIR*/
    else null
  }

  override def getPropertyKindByName(label: String): Int =
    nodePropertyByLabel.getOrElse(label, flatgraph.Schema.UndefinedKind)
  override def getNumberOfPropertyKinds: Int = 57
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
