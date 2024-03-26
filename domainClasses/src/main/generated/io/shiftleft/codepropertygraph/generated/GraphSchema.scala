package io.shiftleft.codepropertygraph.generated
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.edges
import flatgraph.FormalQtyType

object GraphSchema extends flatgraph.Schema {
  val nodeLabels = Array(
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
    "LOCATION",
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
  val edgeLabels = Array(
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
  val edgeIdByLabel = edgeLabels.zipWithIndex.toMap
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
    (g, seq) => new nodes.Location(g, seq),
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
    size => new Array[Boolean](size),
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
    size => new Array[String](size),
    size => new Array[flatgraph.GNode](size),
    size => new Array[flatgraph.GNode](size),
    size => new Array[flatgraph.GNode](size),
    size => new Array[flatgraph.GNode](size)
  )
  val normalNodePropertyNames = Array(
    "ALIAS_TYPE_FULL_NAME",
    "ARGUMENT_INDEX",
    "ARGUMENT_NAME",
    "AST_PARENT_FULL_NAME",
    "AST_PARENT_TYPE",
    "CANONICAL_NAME",
    "CLASS_NAME",
    "CLASS_SHORT_NAME",
    "CLOSURE_BINDING_ID",
    "CLOSURE_ORIGINAL_NAME",
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
    "EXPLICIT_AS",
    "FILENAME",
    "FULL_NAME",
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
    "METHOD_SHORT_NAME",
    "MODIFIER_TYPE",
    "NAME",
    "NODE_LABEL",
    "OFFSET",
    "OFFSET_END",
    "ORDER",
    "OVERLAYS",
    "PACKAGE_NAME",
    "PARSER_TYPE_NAME",
    "POSSIBLE_TYPES",
    "ROOT",
    "SIGNATURE",
    "SYMBOL",
    "TYPE_DECL_FULL_NAME",
    "TYPE_FULL_NAME",
    "VALUE",
    "VERSION"
  )
  val nodePropertyByLabel = normalNodePropertyNames.zipWithIndex.toMap
    .updated("evidence", 55)
    .updated("keyValuePairs", 56)
    .updated("node", 57)
    .updated("tag", 58)
  val nodePropertyDescriptors: Array[FormalQtyType.FormalQuantity | FormalQtyType.FormalType] = {
    val nodePropertyDescriptors = new Array[FormalQtyType.FormalQuantity | FormalQtyType.FormalType](5192)
    for (idx <- Range(0, 5192)) {
      nodePropertyDescriptors(idx) =
        if ((idx & 1) == 0) FormalQtyType.NothingType
        else FormalQtyType.QtyNone
    }

    nodePropertyDescriptors(88) = FormalQtyType.IntType // ANNOTATION.ARGUMENT_INDEX
    nodePropertyDescriptors(89) = FormalQtyType.QtyOne
    nodePropertyDescriptors(176) = FormalQtyType.StringType // ANNOTATION.ARGUMENT_NAME
    nodePropertyDescriptors(177) = FormalQtyType.QtyOption
    nodePropertyDescriptors(880) = FormalQtyType.StringType // ANNOTATION.CODE
    nodePropertyDescriptors(881) = FormalQtyType.QtyOne
    nodePropertyDescriptors(968) = FormalQtyType.IntType // ANNOTATION.COLUMN_NUMBER
    nodePropertyDescriptors(969) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1936) = FormalQtyType.StringType // ANNOTATION.FULL_NAME
    nodePropertyDescriptors(1937) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2992) = FormalQtyType.IntType // ANNOTATION.LINE_NUMBER
    nodePropertyDescriptors(2993) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3432) = FormalQtyType.StringType // ANNOTATION.NAME
    nodePropertyDescriptors(3433) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3784) = FormalQtyType.IntType // ANNOTATION.ORDER
    nodePropertyDescriptors(3785) = FormalQtyType.QtyOne
    nodePropertyDescriptors(90) = FormalQtyType.IntType // ANNOTATION_LITERAL.ARGUMENT_INDEX
    nodePropertyDescriptors(91) = FormalQtyType.QtyOne
    nodePropertyDescriptors(178) = FormalQtyType.StringType // ANNOTATION_LITERAL.ARGUMENT_NAME
    nodePropertyDescriptors(179) = FormalQtyType.QtyOption
    nodePropertyDescriptors(882) = FormalQtyType.StringType // ANNOTATION_LITERAL.CODE
    nodePropertyDescriptors(883) = FormalQtyType.QtyOne
    nodePropertyDescriptors(970) = FormalQtyType.IntType // ANNOTATION_LITERAL.COLUMN_NUMBER
    nodePropertyDescriptors(971) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2994) = FormalQtyType.IntType // ANNOTATION_LITERAL.LINE_NUMBER
    nodePropertyDescriptors(2995) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3434) = FormalQtyType.StringType // ANNOTATION_LITERAL.NAME
    nodePropertyDescriptors(3435) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3786) = FormalQtyType.IntType // ANNOTATION_LITERAL.ORDER
    nodePropertyDescriptors(3787) = FormalQtyType.QtyOne
    nodePropertyDescriptors(884) = FormalQtyType.StringType // ANNOTATION_PARAMETER.CODE
    nodePropertyDescriptors(885) = FormalQtyType.QtyOne
    nodePropertyDescriptors(972) = FormalQtyType.IntType // ANNOTATION_PARAMETER.COLUMN_NUMBER
    nodePropertyDescriptors(973) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2996) = FormalQtyType.IntType // ANNOTATION_PARAMETER.LINE_NUMBER
    nodePropertyDescriptors(2997) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3788) = FormalQtyType.IntType // ANNOTATION_PARAMETER.ORDER
    nodePropertyDescriptors(3789) = FormalQtyType.QtyOne
    nodePropertyDescriptors(886) = FormalQtyType.StringType // ANNOTATION_PARAMETER_ASSIGN.CODE
    nodePropertyDescriptors(887) = FormalQtyType.QtyOne
    nodePropertyDescriptors(974) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.COLUMN_NUMBER
    nodePropertyDescriptors(975) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2998) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.LINE_NUMBER
    nodePropertyDescriptors(2999) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3790) = FormalQtyType.IntType // ANNOTATION_PARAMETER_ASSIGN.ORDER
    nodePropertyDescriptors(3791) = FormalQtyType.QtyOne
    nodePropertyDescriptors(96) = FormalQtyType.IntType // ARRAY_INITIALIZER.ARGUMENT_INDEX
    nodePropertyDescriptors(97) = FormalQtyType.QtyOne
    nodePropertyDescriptors(184) = FormalQtyType.StringType // ARRAY_INITIALIZER.ARGUMENT_NAME
    nodePropertyDescriptors(185) = FormalQtyType.QtyOption
    nodePropertyDescriptors(888) = FormalQtyType.StringType // ARRAY_INITIALIZER.CODE
    nodePropertyDescriptors(889) = FormalQtyType.QtyOne
    nodePropertyDescriptors(976) = FormalQtyType.IntType // ARRAY_INITIALIZER.COLUMN_NUMBER
    nodePropertyDescriptors(977) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3000) = FormalQtyType.IntType // ARRAY_INITIALIZER.LINE_NUMBER
    nodePropertyDescriptors(3001) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3792) = FormalQtyType.IntType // ARRAY_INITIALIZER.ORDER
    nodePropertyDescriptors(3793) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3178) = FormalQtyType.StringType // BINDING.METHOD_FULL_NAME
    nodePropertyDescriptors(3179) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3442) = FormalQtyType.StringType // BINDING.NAME
    nodePropertyDescriptors(3443) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4322) = FormalQtyType.StringType // BINDING.SIGNATURE
    nodePropertyDescriptors(4323) = FormalQtyType.QtyOne
    nodePropertyDescriptors(100) = FormalQtyType.IntType // BLOCK.ARGUMENT_INDEX
    nodePropertyDescriptors(101) = FormalQtyType.QtyOne
    nodePropertyDescriptors(188) = FormalQtyType.StringType // BLOCK.ARGUMENT_NAME
    nodePropertyDescriptors(189) = FormalQtyType.QtyOption
    nodePropertyDescriptors(892) = FormalQtyType.StringType // BLOCK.CODE
    nodePropertyDescriptors(893) = FormalQtyType.QtyOne
    nodePropertyDescriptors(980) = FormalQtyType.IntType // BLOCK.COLUMN_NUMBER
    nodePropertyDescriptors(981) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1596) = FormalQtyType.StringType // BLOCK.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1597) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3004) = FormalQtyType.IntType // BLOCK.LINE_NUMBER
    nodePropertyDescriptors(3005) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3796) = FormalQtyType.IntType // BLOCK.ORDER
    nodePropertyDescriptors(3797) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4148) = FormalQtyType.StringType // BLOCK.POSSIBLE_TYPES
    nodePropertyDescriptors(4149) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4588) = FormalQtyType.StringType // BLOCK.TYPE_FULL_NAME
    nodePropertyDescriptors(4589) = FormalQtyType.QtyOne
    nodePropertyDescriptors(102) = FormalQtyType.IntType // CALL.ARGUMENT_INDEX
    nodePropertyDescriptors(103) = FormalQtyType.QtyOne
    nodePropertyDescriptors(190) = FormalQtyType.StringType // CALL.ARGUMENT_NAME
    nodePropertyDescriptors(191) = FormalQtyType.QtyOption
    nodePropertyDescriptors(894) = FormalQtyType.StringType // CALL.CODE
    nodePropertyDescriptors(895) = FormalQtyType.QtyOne
    nodePropertyDescriptors(982) = FormalQtyType.IntType // CALL.COLUMN_NUMBER
    nodePropertyDescriptors(983) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1510) = FormalQtyType.StringType // CALL.DISPATCH_TYPE
    nodePropertyDescriptors(1511) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1598) = FormalQtyType.StringType // CALL.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1599) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3006) = FormalQtyType.IntType // CALL.LINE_NUMBER
    nodePropertyDescriptors(3007) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3182) = FormalQtyType.StringType // CALL.METHOD_FULL_NAME
    nodePropertyDescriptors(3183) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3446) = FormalQtyType.StringType // CALL.NAME
    nodePropertyDescriptors(3447) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3798) = FormalQtyType.IntType // CALL.ORDER
    nodePropertyDescriptors(3799) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4150) = FormalQtyType.StringType // CALL.POSSIBLE_TYPES
    nodePropertyDescriptors(4151) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4326) = FormalQtyType.StringType // CALL.SIGNATURE
    nodePropertyDescriptors(4327) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4590) = FormalQtyType.StringType // CALL.TYPE_FULL_NAME
    nodePropertyDescriptors(4591) = FormalQtyType.QtyOne
    nodePropertyDescriptors(720) = FormalQtyType.StringType // CLOSURE_BINDING.CLOSURE_BINDING_ID
    nodePropertyDescriptors(721) = FormalQtyType.QtyOption
    nodePropertyDescriptors(808) = FormalQtyType.StringType // CLOSURE_BINDING.CLOSURE_ORIGINAL_NAME
    nodePropertyDescriptors(809) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1688) = FormalQtyType.StringType // CLOSURE_BINDING.EVALUATION_STRATEGY
    nodePropertyDescriptors(1689) = FormalQtyType.QtyOne
    nodePropertyDescriptors(898) = FormalQtyType.StringType // COMMENT.CODE
    nodePropertyDescriptors(899) = FormalQtyType.QtyOne
    nodePropertyDescriptors(986) = FormalQtyType.IntType // COMMENT.COLUMN_NUMBER
    nodePropertyDescriptors(987) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1866) = FormalQtyType.StringType // COMMENT.FILENAME
    nodePropertyDescriptors(1867) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3010) = FormalQtyType.IntType // COMMENT.LINE_NUMBER
    nodePropertyDescriptors(3011) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3802) = FormalQtyType.IntType // COMMENT.ORDER
    nodePropertyDescriptors(3803) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1252) = FormalQtyType.StringType // CONFIG_FILE.CONTENT
    nodePropertyDescriptors(1253) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3452) = FormalQtyType.StringType // CONFIG_FILE.NAME
    nodePropertyDescriptors(3453) = FormalQtyType.QtyOne
    nodePropertyDescriptors(110) = FormalQtyType.IntType // CONTROL_STRUCTURE.ARGUMENT_INDEX
    nodePropertyDescriptors(111) = FormalQtyType.QtyOne
    nodePropertyDescriptors(198) = FormalQtyType.StringType // CONTROL_STRUCTURE.ARGUMENT_NAME
    nodePropertyDescriptors(199) = FormalQtyType.QtyOption
    nodePropertyDescriptors(902) = FormalQtyType.StringType // CONTROL_STRUCTURE.CODE
    nodePropertyDescriptors(903) = FormalQtyType.QtyOne
    nodePropertyDescriptors(990) = FormalQtyType.IntType // CONTROL_STRUCTURE.COLUMN_NUMBER
    nodePropertyDescriptors(991) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1342) = FormalQtyType.StringType // CONTROL_STRUCTURE.CONTROL_STRUCTURE_TYPE
    nodePropertyDescriptors(1343) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3014) = FormalQtyType.IntType // CONTROL_STRUCTURE.LINE_NUMBER
    nodePropertyDescriptors(3015) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3806) = FormalQtyType.IntType // CONTROL_STRUCTURE.ORDER
    nodePropertyDescriptors(3807) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4070) = FormalQtyType.StringType // CONTROL_STRUCTURE.PARSER_TYPE_NAME
    nodePropertyDescriptors(4071) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1432) = FormalQtyType.StringType // DEPENDENCY.DEPENDENCY_GROUP_ID
    nodePropertyDescriptors(1433) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3456) = FormalQtyType.StringType // DEPENDENCY.NAME
    nodePropertyDescriptors(3457) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4776) = FormalQtyType.StringType // DEPENDENCY.VERSION
    nodePropertyDescriptors(4777) = FormalQtyType.QtyOne
    nodePropertyDescriptors(114) = FormalQtyType.IntType // FIELD_IDENTIFIER.ARGUMENT_INDEX
    nodePropertyDescriptors(115) = FormalQtyType.QtyOne
    nodePropertyDescriptors(202) = FormalQtyType.StringType // FIELD_IDENTIFIER.ARGUMENT_NAME
    nodePropertyDescriptors(203) = FormalQtyType.QtyOption
    nodePropertyDescriptors(466) = FormalQtyType.StringType // FIELD_IDENTIFIER.CANONICAL_NAME
    nodePropertyDescriptors(467) = FormalQtyType.QtyOne
    nodePropertyDescriptors(906) = FormalQtyType.StringType // FIELD_IDENTIFIER.CODE
    nodePropertyDescriptors(907) = FormalQtyType.QtyOne
    nodePropertyDescriptors(994) = FormalQtyType.IntType // FIELD_IDENTIFIER.COLUMN_NUMBER
    nodePropertyDescriptors(995) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3018) = FormalQtyType.IntType // FIELD_IDENTIFIER.LINE_NUMBER
    nodePropertyDescriptors(3019) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3810) = FormalQtyType.IntType // FIELD_IDENTIFIER.ORDER
    nodePropertyDescriptors(3811) = FormalQtyType.QtyOne
    nodePropertyDescriptors(908) = FormalQtyType.StringType // FILE.CODE
    nodePropertyDescriptors(909) = FormalQtyType.QtyOne
    nodePropertyDescriptors(996) = FormalQtyType.IntType // FILE.COLUMN_NUMBER
    nodePropertyDescriptors(997) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1260) = FormalQtyType.StringType // FILE.CONTENT
    nodePropertyDescriptors(1261) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2052) = FormalQtyType.StringType // FILE.HASH
    nodePropertyDescriptors(2053) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3020) = FormalQtyType.IntType // FILE.LINE_NUMBER
    nodePropertyDescriptors(3021) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3460) = FormalQtyType.StringType // FILE.NAME
    nodePropertyDescriptors(3461) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3812) = FormalQtyType.IntType // FILE.ORDER
    nodePropertyDescriptors(3813) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4870) = FormalQtyType.RefType // FINDING.evidence
    nodePropertyDescriptors(4871) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4958) = FormalQtyType.RefType // FINDING.keyValuePairs
    nodePropertyDescriptors(4959) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(120) = FormalQtyType.IntType // IDENTIFIER.ARGUMENT_INDEX
    nodePropertyDescriptors(121) = FormalQtyType.QtyOne
    nodePropertyDescriptors(208) = FormalQtyType.StringType // IDENTIFIER.ARGUMENT_NAME
    nodePropertyDescriptors(209) = FormalQtyType.QtyOption
    nodePropertyDescriptors(912) = FormalQtyType.StringType // IDENTIFIER.CODE
    nodePropertyDescriptors(913) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1000) = FormalQtyType.IntType // IDENTIFIER.COLUMN_NUMBER
    nodePropertyDescriptors(1001) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1616) = FormalQtyType.StringType // IDENTIFIER.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1617) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3024) = FormalQtyType.IntType // IDENTIFIER.LINE_NUMBER
    nodePropertyDescriptors(3025) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3464) = FormalQtyType.StringType // IDENTIFIER.NAME
    nodePropertyDescriptors(3465) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3816) = FormalQtyType.IntType // IDENTIFIER.ORDER
    nodePropertyDescriptors(3817) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4168) = FormalQtyType.StringType // IDENTIFIER.POSSIBLE_TYPES
    nodePropertyDescriptors(4169) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4608) = FormalQtyType.StringType // IDENTIFIER.TYPE_FULL_NAME
    nodePropertyDescriptors(4609) = FormalQtyType.QtyOne
    nodePropertyDescriptors(914) = FormalQtyType.StringType // IMPORT.CODE
    nodePropertyDescriptors(915) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1002) = FormalQtyType.IntType // IMPORT.COLUMN_NUMBER
    nodePropertyDescriptors(1003) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1794) = FormalQtyType.BoolType // IMPORT.EXPLICIT_AS
    nodePropertyDescriptors(1795) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2146) = FormalQtyType.StringType // IMPORT.IMPORTED_AS
    nodePropertyDescriptors(2147) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2234) = FormalQtyType.StringType // IMPORT.IMPORTED_ENTITY
    nodePropertyDescriptors(2235) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2498) = FormalQtyType.BoolType // IMPORT.IS_EXPLICIT
    nodePropertyDescriptors(2499) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2762) = FormalQtyType.BoolType // IMPORT.IS_WILDCARD
    nodePropertyDescriptors(2763) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3026) = FormalQtyType.IntType // IMPORT.LINE_NUMBER
    nodePropertyDescriptors(3027) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3818) = FormalQtyType.IntType // IMPORT.ORDER
    nodePropertyDescriptors(3819) = FormalQtyType.QtyOne
    nodePropertyDescriptors(916) = FormalQtyType.StringType // JUMP_LABEL.CODE
    nodePropertyDescriptors(917) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1004) = FormalQtyType.IntType // JUMP_LABEL.COLUMN_NUMBER
    nodePropertyDescriptors(1005) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3028) = FormalQtyType.IntType // JUMP_LABEL.LINE_NUMBER
    nodePropertyDescriptors(3029) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3468) = FormalQtyType.StringType // JUMP_LABEL.NAME
    nodePropertyDescriptors(3469) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3820) = FormalQtyType.IntType // JUMP_LABEL.ORDER
    nodePropertyDescriptors(3821) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4084) = FormalQtyType.StringType // JUMP_LABEL.PARSER_TYPE_NAME
    nodePropertyDescriptors(4085) = FormalQtyType.QtyOne
    nodePropertyDescriptors(126) = FormalQtyType.IntType // JUMP_TARGET.ARGUMENT_INDEX
    nodePropertyDescriptors(127) = FormalQtyType.QtyOne
    nodePropertyDescriptors(918) = FormalQtyType.StringType // JUMP_TARGET.CODE
    nodePropertyDescriptors(919) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1006) = FormalQtyType.IntType // JUMP_TARGET.COLUMN_NUMBER
    nodePropertyDescriptors(1007) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3030) = FormalQtyType.IntType // JUMP_TARGET.LINE_NUMBER
    nodePropertyDescriptors(3031) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3470) = FormalQtyType.StringType // JUMP_TARGET.NAME
    nodePropertyDescriptors(3471) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3822) = FormalQtyType.IntType // JUMP_TARGET.ORDER
    nodePropertyDescriptors(3823) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4086) = FormalQtyType.StringType // JUMP_TARGET.PARSER_TYPE_NAME
    nodePropertyDescriptors(4087) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2856) = FormalQtyType.StringType // KEY_VALUE_PAIR.KEY
    nodePropertyDescriptors(2857) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4704) = FormalQtyType.StringType // KEY_VALUE_PAIR.VALUE
    nodePropertyDescriptors(4705) = FormalQtyType.QtyOne
    nodePropertyDescriptors(130) = FormalQtyType.IntType // LITERAL.ARGUMENT_INDEX
    nodePropertyDescriptors(131) = FormalQtyType.QtyOne
    nodePropertyDescriptors(218) = FormalQtyType.StringType // LITERAL.ARGUMENT_NAME
    nodePropertyDescriptors(219) = FormalQtyType.QtyOption
    nodePropertyDescriptors(922) = FormalQtyType.StringType // LITERAL.CODE
    nodePropertyDescriptors(923) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1010) = FormalQtyType.IntType // LITERAL.COLUMN_NUMBER
    nodePropertyDescriptors(1011) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1626) = FormalQtyType.StringType // LITERAL.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1627) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3034) = FormalQtyType.IntType // LITERAL.LINE_NUMBER
    nodePropertyDescriptors(3035) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3826) = FormalQtyType.IntType // LITERAL.ORDER
    nodePropertyDescriptors(3827) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4178) = FormalQtyType.StringType // LITERAL.POSSIBLE_TYPES
    nodePropertyDescriptors(4179) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4618) = FormalQtyType.StringType // LITERAL.TYPE_FULL_NAME
    nodePropertyDescriptors(4619) = FormalQtyType.QtyOne
    nodePropertyDescriptors(748) = FormalQtyType.StringType // LOCAL.CLOSURE_BINDING_ID
    nodePropertyDescriptors(749) = FormalQtyType.QtyOption
    nodePropertyDescriptors(924) = FormalQtyType.StringType // LOCAL.CODE
    nodePropertyDescriptors(925) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1012) = FormalQtyType.IntType // LOCAL.COLUMN_NUMBER
    nodePropertyDescriptors(1013) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1628) = FormalQtyType.StringType // LOCAL.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1629) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3036) = FormalQtyType.IntType // LOCAL.LINE_NUMBER
    nodePropertyDescriptors(3037) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3476) = FormalQtyType.StringType // LOCAL.NAME
    nodePropertyDescriptors(3477) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3828) = FormalQtyType.IntType // LOCAL.ORDER
    nodePropertyDescriptors(3829) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4180) = FormalQtyType.StringType // LOCAL.POSSIBLE_TYPES
    nodePropertyDescriptors(4181) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4620) = FormalQtyType.StringType // LOCAL.TYPE_FULL_NAME
    nodePropertyDescriptors(4621) = FormalQtyType.QtyOne
    nodePropertyDescriptors(574) = FormalQtyType.StringType // LOCATION.CLASS_NAME
    nodePropertyDescriptors(575) = FormalQtyType.QtyOne
    nodePropertyDescriptors(662) = FormalQtyType.StringType // LOCATION.CLASS_SHORT_NAME
    nodePropertyDescriptors(663) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1894) = FormalQtyType.StringType // LOCATION.FILENAME
    nodePropertyDescriptors(1895) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3038) = FormalQtyType.IntType // LOCATION.LINE_NUMBER
    nodePropertyDescriptors(3039) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3214) = FormalQtyType.StringType // LOCATION.METHOD_FULL_NAME
    nodePropertyDescriptors(3215) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3302) = FormalQtyType.StringType // LOCATION.METHOD_SHORT_NAME
    nodePropertyDescriptors(3303) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3566) = FormalQtyType.StringType // LOCATION.NODE_LABEL
    nodePropertyDescriptors(3567) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4006) = FormalQtyType.StringType // LOCATION.PACKAGE_NAME
    nodePropertyDescriptors(4007) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4446) = FormalQtyType.StringType // LOCATION.SYMBOL
    nodePropertyDescriptors(4447) = FormalQtyType.QtyOne
    nodePropertyDescriptors(5062) = FormalQtyType.RefType // LOCATION.node
    nodePropertyDescriptors(5063) = FormalQtyType.QtyOption
    nodePropertyDescriptors(312) = FormalQtyType.StringType // MEMBER.AST_PARENT_FULL_NAME
    nodePropertyDescriptors(313) = FormalQtyType.QtyOne
    nodePropertyDescriptors(400) = FormalQtyType.StringType // MEMBER.AST_PARENT_TYPE
    nodePropertyDescriptors(401) = FormalQtyType.QtyOne
    nodePropertyDescriptors(928) = FormalQtyType.StringType // MEMBER.CODE
    nodePropertyDescriptors(929) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1016) = FormalQtyType.IntType // MEMBER.COLUMN_NUMBER
    nodePropertyDescriptors(1017) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1632) = FormalQtyType.StringType // MEMBER.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1633) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3040) = FormalQtyType.IntType // MEMBER.LINE_NUMBER
    nodePropertyDescriptors(3041) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3480) = FormalQtyType.StringType // MEMBER.NAME
    nodePropertyDescriptors(3481) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3832) = FormalQtyType.IntType // MEMBER.ORDER
    nodePropertyDescriptors(3833) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4184) = FormalQtyType.StringType // MEMBER.POSSIBLE_TYPES
    nodePropertyDescriptors(4185) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4624) = FormalQtyType.StringType // MEMBER.TYPE_FULL_NAME
    nodePropertyDescriptors(4625) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2074) = FormalQtyType.StringType // META_DATA.HASH
    nodePropertyDescriptors(2075) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2954) = FormalQtyType.StringType // META_DATA.LANGUAGE
    nodePropertyDescriptors(2955) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3922) = FormalQtyType.StringType // META_DATA.OVERLAYS
    nodePropertyDescriptors(3923) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4274) = FormalQtyType.StringType // META_DATA.ROOT
    nodePropertyDescriptors(4275) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4802) = FormalQtyType.StringType // META_DATA.VERSION
    nodePropertyDescriptors(4803) = FormalQtyType.QtyOne
    nodePropertyDescriptors(316) = FormalQtyType.StringType // METHOD.AST_PARENT_FULL_NAME
    nodePropertyDescriptors(317) = FormalQtyType.QtyOne
    nodePropertyDescriptors(404) = FormalQtyType.StringType // METHOD.AST_PARENT_TYPE
    nodePropertyDescriptors(405) = FormalQtyType.QtyOne
    nodePropertyDescriptors(932) = FormalQtyType.StringType // METHOD.CODE
    nodePropertyDescriptors(933) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1020) = FormalQtyType.IntType // METHOD.COLUMN_NUMBER
    nodePropertyDescriptors(1021) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1108) = FormalQtyType.IntType // METHOD.COLUMN_NUMBER_END
    nodePropertyDescriptors(1109) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1900) = FormalQtyType.StringType // METHOD.FILENAME
    nodePropertyDescriptors(1901) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1988) = FormalQtyType.StringType // METHOD.FULL_NAME
    nodePropertyDescriptors(1989) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2076) = FormalQtyType.StringType // METHOD.HASH
    nodePropertyDescriptors(2077) = FormalQtyType.QtyOption
    nodePropertyDescriptors(2604) = FormalQtyType.BoolType // METHOD.IS_EXTERNAL
    nodePropertyDescriptors(2605) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3044) = FormalQtyType.IntType // METHOD.LINE_NUMBER
    nodePropertyDescriptors(3045) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3132) = FormalQtyType.IntType // METHOD.LINE_NUMBER_END
    nodePropertyDescriptors(3133) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3484) = FormalQtyType.StringType // METHOD.NAME
    nodePropertyDescriptors(3485) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3660) = FormalQtyType.IntType // METHOD.OFFSET
    nodePropertyDescriptors(3661) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3748) = FormalQtyType.IntType // METHOD.OFFSET_END
    nodePropertyDescriptors(3749) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3836) = FormalQtyType.IntType // METHOD.ORDER
    nodePropertyDescriptors(3837) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4364) = FormalQtyType.StringType // METHOD.SIGNATURE
    nodePropertyDescriptors(4365) = FormalQtyType.QtyOne
    nodePropertyDescriptors(758) = FormalQtyType.StringType // METHOD_PARAMETER_IN.CLOSURE_BINDING_ID
    nodePropertyDescriptors(759) = FormalQtyType.QtyOption
    nodePropertyDescriptors(934) = FormalQtyType.StringType // METHOD_PARAMETER_IN.CODE
    nodePropertyDescriptors(935) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1022) = FormalQtyType.IntType // METHOD_PARAMETER_IN.COLUMN_NUMBER
    nodePropertyDescriptors(1023) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1638) = FormalQtyType.StringType // METHOD_PARAMETER_IN.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1639) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(1726) = FormalQtyType.StringType // METHOD_PARAMETER_IN.EVALUATION_STRATEGY
    nodePropertyDescriptors(1727) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2342) = FormalQtyType.IntType // METHOD_PARAMETER_IN.INDEX
    nodePropertyDescriptors(2343) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2694) = FormalQtyType.BoolType // METHOD_PARAMETER_IN.IS_VARIADIC
    nodePropertyDescriptors(2695) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3046) = FormalQtyType.IntType // METHOD_PARAMETER_IN.LINE_NUMBER
    nodePropertyDescriptors(3047) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3486) = FormalQtyType.StringType // METHOD_PARAMETER_IN.NAME
    nodePropertyDescriptors(3487) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3838) = FormalQtyType.IntType // METHOD_PARAMETER_IN.ORDER
    nodePropertyDescriptors(3839) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4190) = FormalQtyType.StringType // METHOD_PARAMETER_IN.POSSIBLE_TYPES
    nodePropertyDescriptors(4191) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4630) = FormalQtyType.StringType // METHOD_PARAMETER_IN.TYPE_FULL_NAME
    nodePropertyDescriptors(4631) = FormalQtyType.QtyOne
    nodePropertyDescriptors(936) = FormalQtyType.StringType // METHOD_PARAMETER_OUT.CODE
    nodePropertyDescriptors(937) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1024) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.COLUMN_NUMBER
    nodePropertyDescriptors(1025) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1728) = FormalQtyType.StringType // METHOD_PARAMETER_OUT.EVALUATION_STRATEGY
    nodePropertyDescriptors(1729) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2344) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.INDEX
    nodePropertyDescriptors(2345) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2696) = FormalQtyType.BoolType // METHOD_PARAMETER_OUT.IS_VARIADIC
    nodePropertyDescriptors(2697) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3048) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.LINE_NUMBER
    nodePropertyDescriptors(3049) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3488) = FormalQtyType.StringType // METHOD_PARAMETER_OUT.NAME
    nodePropertyDescriptors(3489) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3840) = FormalQtyType.IntType // METHOD_PARAMETER_OUT.ORDER
    nodePropertyDescriptors(3841) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4632) = FormalQtyType.StringType // METHOD_PARAMETER_OUT.TYPE_FULL_NAME
    nodePropertyDescriptors(4633) = FormalQtyType.QtyOne
    nodePropertyDescriptors(146) = FormalQtyType.IntType // METHOD_REF.ARGUMENT_INDEX
    nodePropertyDescriptors(147) = FormalQtyType.QtyOne
    nodePropertyDescriptors(234) = FormalQtyType.StringType // METHOD_REF.ARGUMENT_NAME
    nodePropertyDescriptors(235) = FormalQtyType.QtyOption
    nodePropertyDescriptors(938) = FormalQtyType.StringType // METHOD_REF.CODE
    nodePropertyDescriptors(939) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1026) = FormalQtyType.IntType // METHOD_REF.COLUMN_NUMBER
    nodePropertyDescriptors(1027) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1642) = FormalQtyType.StringType // METHOD_REF.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1643) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3050) = FormalQtyType.IntType // METHOD_REF.LINE_NUMBER
    nodePropertyDescriptors(3051) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3226) = FormalQtyType.StringType // METHOD_REF.METHOD_FULL_NAME
    nodePropertyDescriptors(3227) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3842) = FormalQtyType.IntType // METHOD_REF.ORDER
    nodePropertyDescriptors(3843) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4194) = FormalQtyType.StringType // METHOD_REF.POSSIBLE_TYPES
    nodePropertyDescriptors(4195) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4634) = FormalQtyType.StringType // METHOD_REF.TYPE_FULL_NAME
    nodePropertyDescriptors(4635) = FormalQtyType.QtyOne
    nodePropertyDescriptors(940) = FormalQtyType.StringType // METHOD_RETURN.CODE
    nodePropertyDescriptors(941) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1028) = FormalQtyType.IntType // METHOD_RETURN.COLUMN_NUMBER
    nodePropertyDescriptors(1029) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1644) = FormalQtyType.StringType // METHOD_RETURN.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1645) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(1732) = FormalQtyType.StringType // METHOD_RETURN.EVALUATION_STRATEGY
    nodePropertyDescriptors(1733) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3052) = FormalQtyType.IntType // METHOD_RETURN.LINE_NUMBER
    nodePropertyDescriptors(3053) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3844) = FormalQtyType.IntType // METHOD_RETURN.ORDER
    nodePropertyDescriptors(3845) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4196) = FormalQtyType.StringType // METHOD_RETURN.POSSIBLE_TYPES
    nodePropertyDescriptors(4197) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4636) = FormalQtyType.StringType // METHOD_RETURN.TYPE_FULL_NAME
    nodePropertyDescriptors(4637) = FormalQtyType.QtyOne
    nodePropertyDescriptors(942) = FormalQtyType.StringType // MODIFIER.CODE
    nodePropertyDescriptors(943) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1030) = FormalQtyType.IntType // MODIFIER.COLUMN_NUMBER
    nodePropertyDescriptors(1031) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3054) = FormalQtyType.IntType // MODIFIER.LINE_NUMBER
    nodePropertyDescriptors(3055) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3406) = FormalQtyType.StringType // MODIFIER.MODIFIER_TYPE
    nodePropertyDescriptors(3407) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3846) = FormalQtyType.IntType // MODIFIER.ORDER
    nodePropertyDescriptors(3847) = FormalQtyType.QtyOne
    nodePropertyDescriptors(944) = FormalQtyType.StringType // NAMESPACE.CODE
    nodePropertyDescriptors(945) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1032) = FormalQtyType.IntType // NAMESPACE.COLUMN_NUMBER
    nodePropertyDescriptors(1033) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3056) = FormalQtyType.IntType // NAMESPACE.LINE_NUMBER
    nodePropertyDescriptors(3057) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3496) = FormalQtyType.StringType // NAMESPACE.NAME
    nodePropertyDescriptors(3497) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3848) = FormalQtyType.IntType // NAMESPACE.ORDER
    nodePropertyDescriptors(3849) = FormalQtyType.QtyOne
    nodePropertyDescriptors(946) = FormalQtyType.StringType // NAMESPACE_BLOCK.CODE
    nodePropertyDescriptors(947) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1034) = FormalQtyType.IntType // NAMESPACE_BLOCK.COLUMN_NUMBER
    nodePropertyDescriptors(1035) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1914) = FormalQtyType.StringType // NAMESPACE_BLOCK.FILENAME
    nodePropertyDescriptors(1915) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2002) = FormalQtyType.StringType // NAMESPACE_BLOCK.FULL_NAME
    nodePropertyDescriptors(2003) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3058) = FormalQtyType.IntType // NAMESPACE_BLOCK.LINE_NUMBER
    nodePropertyDescriptors(3059) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3498) = FormalQtyType.StringType // NAMESPACE_BLOCK.NAME
    nodePropertyDescriptors(3499) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3850) = FormalQtyType.IntType // NAMESPACE_BLOCK.ORDER
    nodePropertyDescriptors(3851) = FormalQtyType.QtyOne
    nodePropertyDescriptors(156) = FormalQtyType.IntType // RETURN.ARGUMENT_INDEX
    nodePropertyDescriptors(157) = FormalQtyType.QtyOne
    nodePropertyDescriptors(244) = FormalQtyType.StringType // RETURN.ARGUMENT_NAME
    nodePropertyDescriptors(245) = FormalQtyType.QtyOption
    nodePropertyDescriptors(948) = FormalQtyType.StringType // RETURN.CODE
    nodePropertyDescriptors(949) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1036) = FormalQtyType.IntType // RETURN.COLUMN_NUMBER
    nodePropertyDescriptors(1037) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3060) = FormalQtyType.IntType // RETURN.LINE_NUMBER
    nodePropertyDescriptors(3061) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3852) = FormalQtyType.IntType // RETURN.ORDER
    nodePropertyDescriptors(3853) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3502) = FormalQtyType.StringType // TAG.NAME
    nodePropertyDescriptors(3503) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4734) = FormalQtyType.StringType // TAG.VALUE
    nodePropertyDescriptors(4735) = FormalQtyType.QtyOne
    nodePropertyDescriptors(5088) = FormalQtyType.RefType // TAG_NODE_PAIR.node
    nodePropertyDescriptors(5089) = FormalQtyType.QtyOne
    nodePropertyDescriptors(5176) = FormalQtyType.RefType // TAG_NODE_PAIR.tag
    nodePropertyDescriptors(5177) = FormalQtyType.QtyOne
    nodePropertyDescriptors(162) = FormalQtyType.IntType // TEMPLATE_DOM.ARGUMENT_INDEX
    nodePropertyDescriptors(163) = FormalQtyType.QtyOne
    nodePropertyDescriptors(250) = FormalQtyType.StringType // TEMPLATE_DOM.ARGUMENT_NAME
    nodePropertyDescriptors(251) = FormalQtyType.QtyOption
    nodePropertyDescriptors(954) = FormalQtyType.StringType // TEMPLATE_DOM.CODE
    nodePropertyDescriptors(955) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1042) = FormalQtyType.IntType // TEMPLATE_DOM.COLUMN_NUMBER
    nodePropertyDescriptors(1043) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3066) = FormalQtyType.IntType // TEMPLATE_DOM.LINE_NUMBER
    nodePropertyDescriptors(3067) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3506) = FormalQtyType.StringType // TEMPLATE_DOM.NAME
    nodePropertyDescriptors(3507) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3858) = FormalQtyType.IntType // TEMPLATE_DOM.ORDER
    nodePropertyDescriptors(3859) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2012) = FormalQtyType.StringType // TYPE.FULL_NAME
    nodePropertyDescriptors(2013) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3508) = FormalQtyType.StringType // TYPE.NAME
    nodePropertyDescriptors(3509) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4564) = FormalQtyType.StringType // TYPE.TYPE_DECL_FULL_NAME
    nodePropertyDescriptors(4565) = FormalQtyType.QtyOne
    nodePropertyDescriptors(958) = FormalQtyType.StringType // TYPE_ARGUMENT.CODE
    nodePropertyDescriptors(959) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1046) = FormalQtyType.IntType // TYPE_ARGUMENT.COLUMN_NUMBER
    nodePropertyDescriptors(1047) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3070) = FormalQtyType.IntType // TYPE_ARGUMENT.LINE_NUMBER
    nodePropertyDescriptors(3071) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3862) = FormalQtyType.IntType // TYPE_ARGUMENT.ORDER
    nodePropertyDescriptors(3863) = FormalQtyType.QtyOne
    nodePropertyDescriptors(80) = FormalQtyType.StringType // TYPE_DECL.ALIAS_TYPE_FULL_NAME
    nodePropertyDescriptors(81) = FormalQtyType.QtyOption
    nodePropertyDescriptors(344) = FormalQtyType.StringType // TYPE_DECL.AST_PARENT_FULL_NAME
    nodePropertyDescriptors(345) = FormalQtyType.QtyOne
    nodePropertyDescriptors(432) = FormalQtyType.StringType // TYPE_DECL.AST_PARENT_TYPE
    nodePropertyDescriptors(433) = FormalQtyType.QtyOne
    nodePropertyDescriptors(960) = FormalQtyType.StringType // TYPE_DECL.CODE
    nodePropertyDescriptors(961) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1048) = FormalQtyType.IntType // TYPE_DECL.COLUMN_NUMBER
    nodePropertyDescriptors(1049) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1928) = FormalQtyType.StringType // TYPE_DECL.FILENAME
    nodePropertyDescriptors(1929) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2016) = FormalQtyType.StringType // TYPE_DECL.FULL_NAME
    nodePropertyDescriptors(2017) = FormalQtyType.QtyOne
    nodePropertyDescriptors(2456) = FormalQtyType.StringType // TYPE_DECL.INHERITS_FROM_TYPE_FULL_NAME
    nodePropertyDescriptors(2457) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(2632) = FormalQtyType.BoolType // TYPE_DECL.IS_EXTERNAL
    nodePropertyDescriptors(2633) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3072) = FormalQtyType.IntType // TYPE_DECL.LINE_NUMBER
    nodePropertyDescriptors(3073) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3512) = FormalQtyType.StringType // TYPE_DECL.NAME
    nodePropertyDescriptors(3513) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3688) = FormalQtyType.IntType // TYPE_DECL.OFFSET
    nodePropertyDescriptors(3689) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3776) = FormalQtyType.IntType // TYPE_DECL.OFFSET_END
    nodePropertyDescriptors(3777) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3864) = FormalQtyType.IntType // TYPE_DECL.ORDER
    nodePropertyDescriptors(3865) = FormalQtyType.QtyOne
    nodePropertyDescriptors(962) = FormalQtyType.StringType // TYPE_PARAMETER.CODE
    nodePropertyDescriptors(963) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1050) = FormalQtyType.IntType // TYPE_PARAMETER.COLUMN_NUMBER
    nodePropertyDescriptors(1051) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3074) = FormalQtyType.IntType // TYPE_PARAMETER.LINE_NUMBER
    nodePropertyDescriptors(3075) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3514) = FormalQtyType.StringType // TYPE_PARAMETER.NAME
    nodePropertyDescriptors(3515) = FormalQtyType.QtyOne
    nodePropertyDescriptors(3866) = FormalQtyType.IntType // TYPE_PARAMETER.ORDER
    nodePropertyDescriptors(3867) = FormalQtyType.QtyOne
    nodePropertyDescriptors(172) = FormalQtyType.IntType // TYPE_REF.ARGUMENT_INDEX
    nodePropertyDescriptors(173) = FormalQtyType.QtyOne
    nodePropertyDescriptors(260) = FormalQtyType.StringType // TYPE_REF.ARGUMENT_NAME
    nodePropertyDescriptors(261) = FormalQtyType.QtyOption
    nodePropertyDescriptors(964) = FormalQtyType.StringType // TYPE_REF.CODE
    nodePropertyDescriptors(965) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1052) = FormalQtyType.IntType // TYPE_REF.COLUMN_NUMBER
    nodePropertyDescriptors(1053) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1668) = FormalQtyType.StringType // TYPE_REF.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1669) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3076) = FormalQtyType.IntType // TYPE_REF.LINE_NUMBER
    nodePropertyDescriptors(3077) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3868) = FormalQtyType.IntType // TYPE_REF.ORDER
    nodePropertyDescriptors(3869) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4220) = FormalQtyType.StringType // TYPE_REF.POSSIBLE_TYPES
    nodePropertyDescriptors(4221) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4660) = FormalQtyType.StringType // TYPE_REF.TYPE_FULL_NAME
    nodePropertyDescriptors(4661) = FormalQtyType.QtyOne
    nodePropertyDescriptors(174) = FormalQtyType.IntType // UNKNOWN.ARGUMENT_INDEX
    nodePropertyDescriptors(175) = FormalQtyType.QtyOne
    nodePropertyDescriptors(262) = FormalQtyType.StringType // UNKNOWN.ARGUMENT_NAME
    nodePropertyDescriptors(263) = FormalQtyType.QtyOption
    nodePropertyDescriptors(966) = FormalQtyType.StringType // UNKNOWN.CODE
    nodePropertyDescriptors(967) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1054) = FormalQtyType.IntType // UNKNOWN.COLUMN_NUMBER
    nodePropertyDescriptors(1055) = FormalQtyType.QtyOption
    nodePropertyDescriptors(1230) = FormalQtyType.StringType // UNKNOWN.CONTAINED_REF
    nodePropertyDescriptors(1231) = FormalQtyType.QtyOne
    nodePropertyDescriptors(1670) = FormalQtyType.StringType // UNKNOWN.DYNAMIC_TYPE_HINT_FULL_NAME
    nodePropertyDescriptors(1671) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(3078) = FormalQtyType.IntType // UNKNOWN.LINE_NUMBER
    nodePropertyDescriptors(3079) = FormalQtyType.QtyOption
    nodePropertyDescriptors(3870) = FormalQtyType.IntType // UNKNOWN.ORDER
    nodePropertyDescriptors(3871) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4134) = FormalQtyType.StringType // UNKNOWN.PARSER_TYPE_NAME
    nodePropertyDescriptors(4135) = FormalQtyType.QtyOne
    nodePropertyDescriptors(4222) = FormalQtyType.StringType // UNKNOWN.POSSIBLE_TYPES
    nodePropertyDescriptors(4223) = FormalQtyType.QtyMulti
    nodePropertyDescriptors(4662) = FormalQtyType.StringType // UNKNOWN.TYPE_FULL_NAME
    nodePropertyDescriptors(4663) = FormalQtyType.QtyOne
    nodePropertyDescriptors
  }
  override def getNumberOfNodeKinds: Int              = 44
  override def getNumberOfEdgeKinds: Int              = 24
  override def getNodeLabel(nodeKind: Int): String    = nodeLabels(nodeKind)
  override def getNodeKindByLabel(label: String): Int = nodeKindByLabel.getOrElse(label, flatgraph.Schema.UndefinedKind)
  override def getEdgeLabel(nodeKind: Int, edgeKind: Int): String = edgeLabels(edgeKind)
  override def getEdgeKindByLabel(label: String): Int = edgeIdByLabel.getOrElse(label, flatgraph.Schema.UndefinedKind)
  override def getPropertyLabel(nodeKind: Int, propertyKind: Int): String = {
    if (propertyKind < 55) normalNodePropertyNames(propertyKind)
    else if (propertyKind == 55 && nodeKind == 15) "evidence"      /*on node FINDING*/
    else if (propertyKind == 56 && nodeKind == 15) "keyValuePairs" /*on node FINDING*/
    else if (propertyKind == 57 && nodeKind == 23) "node"          /*on node LOCATION*/
    else if (propertyKind == 57 && nodeKind == 36) "node"          /*on node TAG_NODE_PAIR*/
    else if (propertyKind == 58 && nodeKind == 36) "tag"           /*on node TAG_NODE_PAIR*/
    else null
  }

  override def getPropertyKindByName(label: String): Int =
    nodePropertyByLabel.getOrElse(label, flatgraph.Schema.UndefinedKind)
  override def getNumberOfPropertyKinds: Int = 59
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
}
