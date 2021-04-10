package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._
import overflowdb.storage.ValueTypes

object Base {
  def apply(builder: SchemaBuilder) = new Schema(builder)

  class Schema(builder: SchemaBuilder) {
// node properties
    val language = builder
      .addNodeProperty(
        name = "LANGUAGE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "The programming language this graph originates from"
      )
      .protoId(19)

    val version = builder
      .addNodeProperty(
        name = "VERSION",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "A version, given as a string"
      )
      .protoId(13)

    val overlays = builder
      .addNodeProperty(
        name = "OVERLAYS",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.List,
        comment = "Names of overlays applied to this graph, in order of application"
      )
      .protoId(118)

    val hash = builder
      .addNodeProperty(
        name = "HASH",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Hash value of the artifact that this CPG is built from."
      )
      .protoId(120)

    val lineNumber = builder
      .addNodeProperty(
        name = "LINE_NUMBER",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Line where the code starts"
      )
      .protoId(2)

    val columnNumber = builder
      .addNodeProperty(
        name = "COLUMN_NUMBER",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Column where the code starts"
      )
      .protoId(11)

    val lineNumberEnd = builder
      .addNodeProperty(
        name = "LINE_NUMBER_END",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Line where the code ends"
      )
      .protoId(12)

    val columnNumberEnd = builder
      .addNodeProperty(
        name = "COLUMN_NUMBER_END",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Column where the code ends"
      )
      .protoId(16)

    val parserTypeName = builder
      .addNodeProperty(
        name = "PARSER_TYPE_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "Type name emitted by parser, only present for logical type UNKNOWN"
      )
      .protoId(3)

    val order = builder
      .addNodeProperty(
        name = "ORDER",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.One,
        comment =
          "General ordering property, such that the children of each AST-node are typically numbered from 1, ..., N (this is not enforced). The ordering has no technical meaning, but is used for pretty printing and OUGHT TO reflect order in the source code"
      )
      .protoId(4)

    val argumentIndex = builder
      .addNodeProperty(
        name = "ARGUMENT_INDEX",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.One,
        comment =
          "AST-children of CALL nodes have an argument index, that is used to match call-site arguments with callee parameters. Explicit parameters are numbered from 1 to N, while index 0 is reserved for implicit self / this parameter. CALLs without implicit parameter therefore have arguments starting with index 1. AST-children of BLOCK nodes may have an argument index as well; in this case, the last argument index determines the return-value of a BLOCK expression"
      )
      .protoId(40)

    val isExternal = builder
      .addNodeProperty(
        name = "IS_EXTERNAL",
        valueType = ValueTypes.BOOLEAN,
        cardinality = Cardinality.One,
        comment =
          "Indicates that the construct (METHOD or TYPE_DECL) is external, that is, it is referenced but not defined in the code (applies both to insular parsing and to library functions where we have header files only)"
      )
      .protoId(7)

    val name = builder
      .addNodeProperty(
        name = "NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "Name of represented object, e.g., method name (e.g. \"run\")"
      )
      .protoId(5)

    val fullName = builder
      .addNodeProperty(
        name = "FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "Full name of an element, e.g., the class name along, including its package (e.g. \"io.shiftleft.dataflowenging.layers.dataflows.DataFlowRunner.run\"). In theory, the FULL_NAME just needs to be unique and is used for linking references, so a consecutive integer would be valid. In practice, this should be human readable"
      )
      .protoId(6)

    val canonicalName = builder
      .addNodeProperty(
        name = "CANONICAL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "Canonical token of a FIELD_IDENTIFIER. Typically identical to the CODE field, but canonicalized according to source language semantics. Human readable names are preferable. FIELD_IDENTIFIERs must share identical CANONICAL_NAME if and only if they alias, e.g. in C-style unions (if the aliasing relationship is unknown or there are partial overlaps, then one must make a reasonable guess, and trade off between false negatives and false positives)"
      )
      .protoId(2001092)

    val code = builder
      .addNodeProperty(
        name = "CODE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "The code snippet the node represents"
      )
      .protoId(21)

    val signature = builder
      .addNodeProperty(
        name = "SIGNATURE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "Method signature. The format is defined by the language front-end, and the backend simply compares strings to resolve function overloading, i.e. match call-sites to METHODs. In theory, consecutive integers would be valid, but in practice this should be human readable"
      )
      .protoId(22)

    val modifierType = builder
      .addNodeProperty(
        name = "MODIFIER_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "Indicates the modifier which is represented by a MODIFIER node. See modifierTypes"
      )
      .protoId(26)

    val controlStructureType = builder
      .addNodeProperty(
        name = "CONTROL_STRUCTURE_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "Indicates the control structure type. See controlStructureTypes"
      )
      .protoId(27)

    val typeFullName = builder
      .addNodeProperty(
        name = "TYPE_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "The static type of an entity. E.g. expressions, local, parameters etc. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME"
      )
      .protoId(51)

    val typeDeclFullName = builder
      .addNodeProperty(
        name = "TYPE_DECL_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "The static type decl of a TYPE. This property is matched against the FULL_NAME of TYPE_DECL nodes. It is required to have exactly one TYPE_DECL for each different TYPE_DECL_FULL_NAME"
      )
      .protoId(52)

    val inheritsFromTypeFullName = builder
      .addNodeProperty(
        name = "INHERITS_FROM_TYPE_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.List,
        comment =
          "The static types a TYPE_DECL inherits from. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME"
      )
      .protoId(53)

    val methodFullName = builder
      .addNodeProperty(
        name = "METHOD_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "The FULL_NAME of a method. Used to link CALL and METHOD nodes. It is required to have exactly one METHOD node for each METHOD_FULL_NAME"
      )
      .protoId(54)

    val aliasTypeFullName = builder
      .addNodeProperty(
        name = "ALIAS_TYPE_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Type full name of which a TYPE_DECL is an alias of"
      )
      .protoId(158)

    val filename = builder
      .addNodeProperty(
        name = "FILENAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "Full path of canonical file that contained this node; will be linked into corresponding FILE nodes. Possible for METHOD, TYPE_DECL and NAMESPACE_BLOCK"
      )
      .protoId(106)

    val containedRef = builder
      .addNodeProperty(
        name = "CONTAINED_REF",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "References to other nodes. This is not a real property; it exists here for the sake of proto serialization only. valueType and cardinality are meaningless."
      )
      .protoId(2007161)

// edge properties
    val localName = builder
      .addEdgeProperty(
        name = "LOCAL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Local name of referenced CONTAINED node. This key is deprecated."
      )
      .protoId(6)

    val index = builder
      .addEdgeProperty(
        name = "INDEX",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.ZeroOrOne,
        comment =
          "Index of referenced CONTAINED node (0 based) - used together with cardinality=list. This key is deprecated."
      )
      .protoId(8)

// edge types
    val ast = builder
      .addEdgeType(
        name = "AST",
        comment = "Syntax tree edge"
      )
      .protoId(3)

    val cfg = builder
      .addEdgeType(
        name = "CFG",
        comment = "Control flow edge"
      )
      .protoId(19)

    val containsNode = builder
      .addEdgeType(
        name = "CONTAINS_NODE",
        comment = "Membership relation for a compound object. This edge is deprecated."
      )
      .protoId(9)
      .addProperties(localName, index)

    val capturedBy = builder
      .addEdgeType(
        name = "CAPTURED_BY",
        comment = "Connection between a captured LOCAL and the corresponding CLOSURE_BINDING"
      )
      .protoId(41)

    val bindsTo = builder
      .addEdgeType(
        name = "BINDS_TO",
        comment = "Type argument binding to a type parameter"
      )
      .protoId(22)

    val ref = builder
      .addEdgeType(
        name = "REF",
        comment = "A reference to e.g. a LOCAL"
      )
      .protoId(10)

    val vtable = builder
      .addEdgeType(
        name = "VTABLE",
        comment = "Indicates that a method is part of the vtable of a certain type declaration"
      )
      .protoId(30)

    val receiver = builder
      .addEdgeType(
        name = "RECEIVER",
        comment = "The receiver of a method call which is either an object or a pointer"
      )
      .protoId(55)

    val condition = builder
      .addEdgeType(
        name = "CONDITION",
        comment = "Edge from control structure node to the expression that holds the condition"
      )
      .protoId(56)

    val argument = builder
      .addEdgeType(
        name = "ARGUMENT",
        comment = "Relation between a CALL and its arguments and RETURN and the returned expression"
      )
      .protoId(156)

    val sourceFile = builder
      .addEdgeType(
        name = "SOURCE_FILE",
        comment = "Source file of a node, in which its LINE_NUMBER and COLUMN_NUMBER are valid"
      )
      .protoId(157)

// node base types
    val withinMethod = builder.addNodeBaseType(
      name = "WITHIN_METHOD",
      comment = "Any node that can exist in a method"
    )

    val trackingPoint = builder
      .addNodeBaseType(
        name = "TRACKING_POINT",
        comment = "Any node that can occur in a data flow"
      )
      .extendz(withinMethod)

    val declaration = builder
      .addNodeBaseType(
        name = "DECLARATION",
        comment = ""
      )
      .addProperties(name)

    val localLike = builder
      .addNodeBaseType(
        name = "LOCAL_LIKE",
        comment = "Formal input parameters, locals, and identifiers"
      )
      .addProperties(name)

    val astNode = builder
      .addNodeBaseType(
        name = "AST_NODE",
        comment = "Any node that can exist in an abstract syntax tree"
      )
      .addProperties(order)

    val cfgNode = builder
      .addNodeBaseType(
        name = "CFG_NODE",
        comment = "Any node that can occur as part of a control flow graph"
      )
      .addProperties(lineNumber, columnNumber, code)
      .extendz(withinMethod, astNode)

    val expression = builder
      .addNodeBaseType(
        name = "EXPRESSION",
        comment = "Expression as a specialisation of tracking point"
      )
      .addProperties(argumentIndex)
      .extendz(trackingPoint, cfgNode, astNode)

    val callRepr = builder
      .addNodeBaseType(
        name = "CALL_REPR",
        comment = "Call representation"
      )
      .addProperties(name, signature)
      .extendz(cfgNode)

// node types
    val metaData: NodeType = builder
      .addNodeType(
        name = "META_DATA",
        comment = "Node to save meta data about the graph on its properties. Exactly one node of this type per graph"
      )
      .protoId(39)
      .addProperties(language, version, overlays, hash)

    val file: NodeType = builder
      .addNodeType(
        name = "FILE",
        comment = "Node representing a source file - the root of the AST"
      )
      .protoId(38)
      .addProperties(name, hash)
      .extendz(astNode)

    val method: NodeType = builder
      .addNodeType(
        name = "METHOD",
        comment = "A method/function/procedure"
      )
      .protoId(1)
      .addProperties(fullName, isExternal, signature, lineNumberEnd, columnNumberEnd, filename, hash)
      .extendz(declaration, cfgNode, astNode)

    val methodParameterIn: NodeType = builder
      .addNodeType(
        name = "METHOD_PARAMETER_IN",
        comment = "This node represents a formal parameter going towards the callee side"
      )
      .protoId(34)
      .addProperties(code, typeFullName, lineNumber, columnNumber)
      .extendz(declaration, localLike, trackingPoint, astNode)

    val methodReturn: NodeType = builder
      .addNodeType(
        name = "METHOD_RETURN",
        comment = "A formal method return"
      )
      .protoId(3)
      .addProperties(typeFullName)
      .extendz(cfgNode, trackingPoint)

    val modifier: NodeType = builder
      .addNodeType(
        name = "MODIFIER",
        comment = "A modifier, e.g., static, public, private"
      )
      .protoId(300)
      .addProperties(modifierType)
      .extendz(astNode)

    val tpe: NodeType = builder
      .addNodeType(
        name = "TYPE",
        comment =
          "A type which always has to reference a type declaration and may have type argument children if the referred to type declaration is a template"
      )
      .protoId(45)
      .addProperties(name, fullName, typeDeclFullName)

    val typeDecl: NodeType = builder
      .addNodeType(
        name = "TYPE_DECL",
        comment = "A type declaration"
      )
      .protoId(46)
      .addProperties(name, fullName, isExternal, inheritsFromTypeFullName, aliasTypeFullName, filename)
      .extendz(astNode)

    val typeParameter: NodeType = builder
      .addNodeType(
        name = "TYPE_PARAMETER",
        comment = "Type parameter of TYPE_DECL or METHOD"
      )
      .protoId(47)
      .addProperties(name)
      .extendz(astNode)

    val typeArgument: NodeType = builder
      .addNodeType(
        name = "TYPE_ARGUMENT",
        comment = "Argument for a TYPE_PARAMETER that belongs to a TYPE. It binds another TYPE to a TYPE_PARAMETER"
      )
      .protoId(48)
      .extendz(astNode)

    val member: NodeType = builder
      .addNodeType(
        name = "MEMBER",
        comment = "Member of a class struct or union"
      )
      .protoId(9)
      .addProperties(code, typeFullName)
      .extendz(declaration, astNode)

    val namespaceBlock: NodeType = builder
      .addNodeType(
        name = "NAMESPACE_BLOCK",
        comment = "A reference to a namespace"
      )
      .protoId(41)
      .addProperties(name, fullName, filename)
      .extendz(astNode)

    val literal: NodeType = builder
      .addNodeType(
        name = "LITERAL",
        comment = "Literal/Constant"
      )
      .protoId(8)
      .addProperties(typeFullName)
      .extendz(expression)

    val callNode: NodeType = builder
      .addNodeType(
        name = "CALL",
        comment = "A (method)-call"
      )
      .protoId(15)
      .addProperties(methodFullName)
      .extendz(expression, callRepr)

    val local: NodeType = builder
      .addNodeType(
        name = "LOCAL",
        comment = "A local variable"
      )
      .protoId(23)
      .addProperties(code, typeFullName, lineNumber, columnNumber)
      .extendz(declaration, localLike, astNode)

    val identifier: NodeType = builder
      .addNodeType(
        name = "IDENTIFIER",
        comment = "An arbitrary identifier/reference"
      )
      .protoId(27)
      .addProperties(typeFullName)
      .extendz(expression, localLike)

    val fieldIdentifier: NodeType = builder
      .addNodeType(
        name = "FIELD_IDENTIFIER",
        comment =
          "A node that represents which field is accessed in a <operator>.fieldAccess, in e.g. obj.field. The CODE part is used for human display and matching to MEMBER nodes. The CANONICAL_NAME is used for dataflow tracking; typically both coincide. However, suppose that two fields foo and bar are a C-style union; then CODE refers to whatever the programmer wrote (obj.foo or obj.bar), but both share the same CANONICAL_NAME (e.g. GENERATED_foo_bar)"
      )
      .protoId(2001081)
      .addProperties(canonicalName)
      .extendz(expression)

    val ret: NodeType = builder
      .addNodeType(
        name = "RETURN",
        comment = "A return instruction"
      )
      .protoId(30)
      .extendz(expression)

    val block: NodeType = builder
      .addNodeType(
        name = "BLOCK",
        comment = "A structuring block in the AST"
      )
      .protoId(31)
      .addProperties(typeFullName)
      .extendz(expression)

    val methodInst: NodeType = builder
      .addNodeType(
        name = "METHOD_INST",
        comment =
          "A method instance which always has to reference a method and may have type argument children if the referred to method is a template"
      )
      .protoId(32)
      .addProperties(name, signature, fullName, methodFullName)
      .extendz(astNode)

    val arrayInitializer: NodeType = builder
      .addNodeType(
        name = "ARRAY_INITIALIZER",
        comment = "Initialization construct for arrays"
      )
      .protoId(14)
      .extendz(astNode)

    val methodRef: NodeType = builder
      .addNodeType(
        name = "METHOD_REF",
        comment = "Reference to a method instance"
      )
      .protoId(333)
      .addProperties(typeFullName, methodFullName)
      .extendz(expression)

    val typeRef: NodeType = builder
      .addNodeType(
        name = "TYPE_REF",
        comment = "Reference to a type/class"
      )
      .protoId(335)
      .addProperties(typeFullName)
      .extendz(expression)

    val controlStructure: NodeType = builder
      .addNodeType(
        name = "CONTROL_STRUCTURE",
        comment = "A control structure such as if, while, or for"
      )
      .protoId(339)
      .addProperties(parserTypeName, controlStructureType)
      .extendz(expression)

    val jumpTarget: NodeType = builder
      .addNodeType(
        name = "JUMP_TARGET",
        comment = "A jump target made explicit in the code using a label"
      )
      .protoId(340)
      .addProperties(name, parserTypeName, argumentIndex)
      .extendz(cfgNode, astNode)

    val unknown: NodeType = builder
      .addNodeType(
        name = "UNKNOWN",
        comment = "A language-specific node"
      )
      .protoId(44)
      .addProperties(parserTypeName, typeFullName)
      .extendz(expression)

// node relations
    file
      .addOutEdge(edge = ast, inNode = namespaceBlock, cardinalityIn = Cardinality.ZeroOrOne)

    method
      .addOutEdge(edge = ast, inNode = methodReturn, cardinalityOut = Cardinality.One, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = methodParameterIn, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = modifier, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = block, cardinalityOut = Cardinality.One, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = typeParameter, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = cfg, inNode = callNode)
      .addOutEdge(edge = cfg, inNode = identifier)
      .addOutEdge(edge = cfg, inNode = fieldIdentifier)
      .addOutEdge(edge = cfg, inNode = literal)
      .addOutEdge(edge = cfg, inNode = methodRef)
      .addOutEdge(edge = cfg, inNode = typeRef)
      .addOutEdge(edge = cfg,
                  inNode = methodReturn,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = cfg, inNode = ret)
      .addOutEdge(edge = cfg, inNode = block)
      .addOutEdge(edge = cfg, inNode = jumpTarget)
      .addOutEdge(edge = cfg, inNode = controlStructure)
      .addOutEdge(edge = cfg, inNode = unknown)

    tpe
      .addOutEdge(edge = ast, inNode = typeArgument)

    typeDecl
      .addOutEdge(edge = ast, inNode = typeParameter)
      .addOutEdge(edge = ast, inNode = member, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = modifier, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = vtable, inNode = method)

    typeArgument
      .addOutEdge(edge = ref, inNode = tpe)
      .addOutEdge(edge = bindsTo, inNode = typeParameter)

    literal
      .addOutEdge(edge = cfg, inNode = callNode)
      .addOutEdge(edge = cfg, inNode = identifier)
      .addOutEdge(edge = cfg, inNode = fieldIdentifier)
      .addOutEdge(edge = cfg, inNode = literal)
      .addOutEdge(edge = cfg, inNode = methodRef)
      .addOutEdge(edge = cfg, inNode = typeRef)
      .addOutEdge(edge = cfg, inNode = ret)
      .addOutEdge(edge = cfg, inNode = block)
      .addOutEdge(edge = cfg, inNode = jumpTarget)
      .addOutEdge(edge = cfg, inNode = controlStructure)
      .addOutEdge(edge = cfg, inNode = unknown)

    callNode
      .addOutEdge(edge = cfg, inNode = callNode)
      .addOutEdge(edge = cfg, inNode = identifier)
      .addOutEdge(edge = cfg, inNode = fieldIdentifier)
      .addOutEdge(edge = cfg, inNode = literal)
      .addOutEdge(edge = cfg, inNode = methodRef)
      .addOutEdge(edge = cfg, inNode = typeRef)
      .addOutEdge(edge = cfg, inNode = ret)
      .addOutEdge(edge = cfg, inNode = block)
      .addOutEdge(edge = cfg, inNode = jumpTarget)
      .addOutEdge(edge = cfg, inNode = controlStructure)
      .addOutEdge(edge = cfg, inNode = unknown)
      .addOutEdge(edge = ast, inNode = callNode)
      .addOutEdge(edge = ast, inNode = identifier)
      .addOutEdge(edge = ast, inNode = fieldIdentifier, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = literal)
      .addOutEdge(edge = ast, inNode = methodRef)
      .addOutEdge(edge = ast, inNode = typeRef)
      .addOutEdge(edge = ast, inNode = ret)
      .addOutEdge(edge = ast, inNode = block)
      .addOutEdge(edge = ast, inNode = controlStructure)
      .addOutEdge(edge = receiver,
                  inNode = callNode,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = receiver,
                  inNode = identifier,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = receiver,
                  inNode = literal,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = receiver,
                  inNode = methodRef,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = receiver, inNode = typeRef)
      .addOutEdge(edge = receiver,
                  inNode = block,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = receiver, inNode = controlStructure)
      .addOutEdge(edge = receiver, inNode = unknown)
      .addOutEdge(edge = argument, inNode = callNode, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = identifier, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = fieldIdentifier, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = argument, inNode = literal, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = methodRef, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = typeRef)
      .addOutEdge(edge = argument, inNode = block, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = jumpTarget)
      .addOutEdge(edge = argument, inNode = controlStructure)
      .addOutEdge(edge = argument, inNode = unknown)

    identifier
      .addOutEdge(edge = ref, inNode = local, cardinalityOut = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ref, inNode = methodParameterIn, cardinalityOut = Cardinality.ZeroOrOne)
      .addOutEdge(edge = cfg, inNode = callNode)
      .addOutEdge(edge = cfg, inNode = identifier)
      .addOutEdge(edge = cfg, inNode = fieldIdentifier)
      .addOutEdge(edge = cfg, inNode = literal)
      .addOutEdge(edge = cfg, inNode = methodRef)
      .addOutEdge(edge = cfg, inNode = typeRef)
      .addOutEdge(edge = cfg, inNode = methodReturn)
      .addOutEdge(edge = cfg, inNode = ret)
      .addOutEdge(edge = cfg, inNode = block)
      .addOutEdge(edge = cfg, inNode = jumpTarget)
      .addOutEdge(edge = cfg, inNode = controlStructure)
      .addOutEdge(edge = cfg, inNode = unknown)

    fieldIdentifier
      .addOutEdge(edge = cfg,
                  inNode = callNode,
                  cardinalityOut = Cardinality.One,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = cfg, inNode = identifier)
      .addOutEdge(edge = cfg, inNode = fieldIdentifier)
      .addOutEdge(edge = cfg, inNode = literal)
      .addOutEdge(edge = cfg, inNode = methodRef)
      .addOutEdge(edge = cfg, inNode = typeRef)
      .addOutEdge(edge = cfg, inNode = ret)
      .addOutEdge(edge = cfg, inNode = block)
      .addOutEdge(edge = cfg, inNode = jumpTarget)
      .addOutEdge(edge = cfg, inNode = controlStructure)
      .addOutEdge(edge = cfg, inNode = unknown)

    ret
      .addOutEdge(edge = ast, inNode = callNode)
      .addOutEdge(edge = ast, inNode = identifier)
      .addOutEdge(edge = ast, inNode = literal)
      .addOutEdge(edge = ast, inNode = methodRef)
      .addOutEdge(edge = ast, inNode = typeRef)
      .addOutEdge(edge = ast, inNode = ret)
      .addOutEdge(edge = ast, inNode = block)
      .addOutEdge(edge = ast, inNode = unknown)
      .addOutEdge(edge = ast, inNode = jumpTarget)
      .addOutEdge(edge = ast, inNode = controlStructure)
      .addOutEdge(edge = cfg,
                  inNode = methodReturn,
                  cardinalityOut = Cardinality.One,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument,
                  inNode = callNode,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument,
                  inNode = identifier,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument,
                  inNode = literal,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument,
                  inNode = methodRef,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = typeRef)
      .addOutEdge(edge = argument,
                  inNode = ret,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument,
                  inNode = block,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = argument, inNode = jumpTarget)
      .addOutEdge(edge = argument, inNode = controlStructure)
      .addOutEdge(edge = argument, inNode = unknown)

    block
      .addOutEdge(edge = ast, inNode = callNode)
      .addOutEdge(edge = ast, inNode = identifier)
      .addOutEdge(edge = ast, inNode = literal)
      .addOutEdge(edge = ast, inNode = methodRef)
      .addOutEdge(edge = ast, inNode = typeRef)
      .addOutEdge(edge = ast, inNode = ret)
      .addOutEdge(edge = ast, inNode = block, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = local)
      .addOutEdge(edge = ast, inNode = unknown)
      .addOutEdge(edge = ast, inNode = jumpTarget)
      .addOutEdge(edge = ast, inNode = controlStructure)
      .addOutEdge(edge = cfg, inNode = callNode)
      .addOutEdge(edge = cfg, inNode = identifier)
      .addOutEdge(edge = cfg, inNode = fieldIdentifier)
      .addOutEdge(edge = cfg, inNode = literal)
      .addOutEdge(edge = cfg, inNode = methodRef)
      .addOutEdge(edge = cfg, inNode = typeRef)
      .addOutEdge(edge = cfg, inNode = ret)
      .addOutEdge(edge = cfg, inNode = block)
      .addOutEdge(edge = cfg, inNode = jumpTarget)
      .addOutEdge(edge = cfg, inNode = controlStructure)
      .addOutEdge(edge = cfg, inNode = unknown)

    methodInst
      .addOutEdge(edge = ast, inNode = typeArgument)

    methodRef
      .addOutEdge(edge = cfg, inNode = callNode)
      .addOutEdge(edge = cfg, inNode = identifier)
      .addOutEdge(edge = cfg, inNode = fieldIdentifier)
      .addOutEdge(edge = cfg, inNode = literal)
      .addOutEdge(edge = cfg, inNode = methodRef)
      .addOutEdge(edge = cfg, inNode = typeRef)
      .addOutEdge(edge = cfg, inNode = methodReturn)
      .addOutEdge(edge = cfg, inNode = ret)
      .addOutEdge(edge = cfg, inNode = block)
      .addOutEdge(edge = cfg, inNode = jumpTarget)
      .addOutEdge(edge = cfg, inNode = controlStructure)
      .addOutEdge(edge = cfg, inNode = unknown)

    typeRef
      .addOutEdge(edge = cfg, inNode = callNode)
      .addOutEdge(edge = cfg, inNode = identifier)
      .addOutEdge(edge = cfg, inNode = fieldIdentifier)
      .addOutEdge(edge = cfg, inNode = literal)
      .addOutEdge(edge = cfg, inNode = methodRef)
      .addOutEdge(edge = cfg, inNode = typeRef)
      .addOutEdge(edge = cfg, inNode = methodReturn)
      .addOutEdge(edge = cfg, inNode = ret)
      .addOutEdge(edge = cfg, inNode = block)
      .addOutEdge(edge = cfg, inNode = jumpTarget)
      .addOutEdge(edge = cfg, inNode = controlStructure)
      .addOutEdge(edge = cfg, inNode = unknown)

    controlStructure
      .addOutEdge(edge = ast, inNode = literal, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = modifier)
      .addOutEdge(edge = ast, inNode = arrayInitializer)
      .addOutEdge(edge = ast, inNode = callNode, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = local)
      .addOutEdge(edge = ast, inNode = identifier, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = ret, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = block, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = jumpTarget)
      .addOutEdge(edge = ast, inNode = unknown)
      .addOutEdge(edge = ast, inNode = controlStructure)
      .addOutEdge(edge = ast, inNode = methodRef, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = typeRef)
      .addOutEdge(edge = condition, inNode = literal)
      .addOutEdge(edge = condition, inNode = callNode)
      .addOutEdge(edge = condition, inNode = identifier)
      .addOutEdge(edge = condition, inNode = ret)
      .addOutEdge(edge = condition, inNode = block)
      .addOutEdge(edge = condition, inNode = methodRef)
      .addOutEdge(edge = condition, inNode = typeRef)
      .addOutEdge(edge = condition, inNode = controlStructure)
      .addOutEdge(edge = condition, inNode = jumpTarget)
      .addOutEdge(edge = condition, inNode = unknown)
      .addOutEdge(edge = condition, inNode = controlStructure)
      .addOutEdge(edge = condition, inNode = arrayInitializer)
      .addOutEdge(edge = cfg, inNode = callNode)
      .addOutEdge(edge = cfg, inNode = identifier)
      .addOutEdge(edge = cfg, inNode = fieldIdentifier)
      .addOutEdge(edge = cfg, inNode = literal)
      .addOutEdge(edge = cfg, inNode = ret)
      .addOutEdge(edge = cfg, inNode = methodRef)
      .addOutEdge(edge = cfg, inNode = typeRef)
      .addOutEdge(edge = cfg, inNode = block)
      .addOutEdge(edge = cfg, inNode = jumpTarget)
      .addOutEdge(edge = cfg, inNode = controlStructure)
      .addOutEdge(edge = cfg, inNode = unknown)

    jumpTarget
      .addOutEdge(edge = cfg, inNode = callNode)
      .addOutEdge(edge = cfg, inNode = identifier)
      .addOutEdge(edge = cfg, inNode = fieldIdentifier)
      .addOutEdge(edge = cfg, inNode = literal)
      .addOutEdge(edge = cfg, inNode = ret)
      .addOutEdge(edge = cfg, inNode = methodRef)
      .addOutEdge(edge = cfg, inNode = typeRef)
      .addOutEdge(edge = cfg, inNode = block)
      .addOutEdge(edge = cfg, inNode = jumpTarget)
      .addOutEdge(edge = cfg, inNode = controlStructure)
      .addOutEdge(edge = cfg, inNode = unknown)

    unknown
      .addOutEdge(edge = cfg, inNode = callNode)
      .addOutEdge(edge = cfg, inNode = identifier)
      .addOutEdge(edge = cfg, inNode = fieldIdentifier)
      .addOutEdge(edge = cfg, inNode = literal)
      .addOutEdge(edge = cfg, inNode = ret)
      .addOutEdge(edge = cfg, inNode = methodRef)
      .addOutEdge(edge = cfg, inNode = typeRef)
      .addOutEdge(edge = cfg, inNode = block)
      .addOutEdge(edge = cfg, inNode = jumpTarget)
      .addOutEdge(edge = cfg, inNode = controlStructure)
      .addOutEdge(edge = cfg, inNode = unknown)
      .addOutEdge(edge = ast, inNode = literal)
      .addOutEdge(edge = ast, inNode = member)
      .addOutEdge(edge = ast, inNode = modifier)
      .addOutEdge(edge = ast, inNode = arrayInitializer)
      .addOutEdge(edge = ast, inNode = callNode)
      .addOutEdge(edge = ast, inNode = local)
      .addOutEdge(edge = ast, inNode = identifier)
      .addOutEdge(edge = ast, inNode = fieldIdentifier)
      .addOutEdge(edge = ast, inNode = ret)
      .addOutEdge(edge = ast, inNode = block)
      .addOutEdge(edge = ast, inNode = jumpTarget)
      .addOutEdge(edge = ast, inNode = unknown)
      .addOutEdge(edge = ast, inNode = controlStructure)

// constants
    val languages = builder.addConstants(
      category = "Languages",
      Constant(name = "JAVA", value = "JAVA", valueType = ValueTypes.STRING, comment = "").protoId(1),
      Constant(name = "JAVASCRIPT", value = "JAVASCRIPT", valueType = ValueTypes.STRING, comment = "").protoId(2),
      Constant(name = "GOLANG", value = "GOLANG", valueType = ValueTypes.STRING, comment = "").protoId(3),
      Constant(name = "CSHARP", value = "CSHARP", valueType = ValueTypes.STRING, comment = "").protoId(4),
      Constant(name = "C", value = "C", valueType = ValueTypes.STRING, comment = "").protoId(5),
      Constant(name = "PYTHON", value = "PYTHON", valueType = ValueTypes.STRING, comment = "").protoId(6),
      Constant(name = "LLVM", value = "LLVM", valueType = ValueTypes.STRING, comment = "").protoId(7),
      Constant(name = "PHP", value = "PHP", valueType = ValueTypes.STRING, comment = "").protoId(8),
    )

    val modifierTypes = builder.addConstants(
      category = "ModifierTypes",
      Constant(name = "STATIC", value = "STATIC", valueType = ValueTypes.STRING, comment = "The static modifier")
        .protoId(1),
      Constant(name = "PUBLIC", value = "PUBLIC", valueType = ValueTypes.STRING, comment = "The public modifier")
        .protoId(2),
      Constant(name = "PROTECTED",
               value = "PROTECTED",
               valueType = ValueTypes.STRING,
               comment = "The protected modifier").protoId(3),
      Constant(name = "PRIVATE", value = "PRIVATE", valueType = ValueTypes.STRING, comment = "The private modifier")
        .protoId(4),
      Constant(name = "ABSTRACT", value = "ABSTRACT", valueType = ValueTypes.STRING, comment = "The abstract modifier")
        .protoId(5),
      Constant(name = "NATIVE", value = "NATIVE", valueType = ValueTypes.STRING, comment = "The native modifier")
        .protoId(6),
      Constant(name = "CONSTRUCTOR",
               value = "CONSTRUCTOR",
               valueType = ValueTypes.STRING,
               comment = "The constructor modifier").protoId(7),
      Constant(name = "VIRTUAL", value = "VIRTUAL", valueType = ValueTypes.STRING, comment = "The virtual modifier")
        .protoId(8),
    )

    val controlStructureTypes = builder.addConstants(
      category = "ControlStructureTypes",
      Constant(name = "BREAK", value = "BREAK", valueType = ValueTypes.STRING, comment = "Represents a break statement")
        .protoId(1),
      Constant(name = "CONTINUE",
               value = "CONTINUE",
               valueType = ValueTypes.STRING,
               comment = "Represents a continue statement").protoId(2),
      Constant(name = "WHILE", value = "WHILE", valueType = ValueTypes.STRING, comment = "Represents a while statement")
        .protoId(3),
      Constant(name = "DO", value = "DO", valueType = ValueTypes.STRING, comment = "Represents a do statement")
        .protoId(4),
      Constant(name = "FOR", value = "FOR", valueType = ValueTypes.STRING, comment = "Represents a for statement")
        .protoId(5),
      Constant(name = "GOTO", value = "GOTO", valueType = ValueTypes.STRING, comment = "Represents a goto statement")
        .protoId(6),
      Constant(name = "IF", value = "IF", valueType = ValueTypes.STRING, comment = "Represents an if statement")
        .protoId(7),
      Constant(name = "ELSE", value = "ELSE", valueType = ValueTypes.STRING, comment = "Represents an else statement")
        .protoId(8),
      Constant(name = "SWITCH",
               value = "SWITCH",
               valueType = ValueTypes.STRING,
               comment = "Represents a switch statement").protoId(9),
      Constant(name = "TRY", value = "TRY", valueType = ValueTypes.STRING, comment = "Represents a try statement")
        .protoId(10),
    )

  }

}
