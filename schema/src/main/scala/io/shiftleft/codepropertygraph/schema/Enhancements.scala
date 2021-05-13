package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._
import overflowdb.storage.ValueTypes

object Enhancements extends SchemaBase {

  override def index: Int = 3

  override def description: String =
    """
      | Enhancement nodes/edges that will automatically be derived from the cpg
      | Note: these should *NOT* be written by the language frontend.
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema, methodSchema: Method.Schema, typeDeclSchema: TypeDecl.Schema) =
    new Schema(builder, base, methodSchema, typeDeclSchema)

  class Schema(builder: SchemaBuilder,
               base: Base.Schema,
               methodSchema: Method.Schema,
               typeDeclSchema: TypeDecl.Schema) {

    import base._
    import methodSchema._
    import typeDeclSchema._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

// node properties
    val value = builder
      .addProperty(
        name = "VALUE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "Tag value"
      )
      .protoId(8)

    val isMethodNeverOverridden = builder
      .addProperty(
        name = "IS_METHOD_NEVER_OVERRIDDEN",
        valueType = ValueTypes.BOOLEAN,
        cardinality = Cardinality.ZeroOrOne,
        comment = "True if the referenced method is never overridden by the subclasses and false otherwise"
      )
      .protoId(1002)

    val evaluationStrategy = builder
      .addProperty(
        name = "EVALUATION_STRATEGY",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "Evaluation strategy for function parameters and return values. One of the values in \"evaluationStrategies\""
      )
      .protoId(15)

    val dispatchType = builder
      .addProperty(
        name = "DISPATCH_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "The dispatch type of a call, which is either static or dynamic. See dispatchTypes"
      )
      .protoId(25)

    val astParentType = builder
      .addProperty(
        name = "AST_PARENT_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment =
          "The type of the AST parent. Since this is only used in some parts of the graph the list does not include all possible parents by intention. Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK"
      )
      .protoId(56)

    val astParentFullName = builder
      .addProperty(
        name = "AST_PARENT_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "The FULL_NAME of a the AST parent of an entity"
      )
      .protoId(57)

    val variable = builder
      .addProperty(
        name = "VARIABLE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "A variable propagated by a reaching-def edge"
      )
      .protoId(11)

// edge types

    val dominate = builder
      .addEdgeType(
        name = "DOMINATE",
        comment = "Points to dominated node in DOM tree"
      )
      .protoId(181)

    val postDominate = builder
      .addEdgeType(
        name = "POST_DOMINATE",
        comment = "Points to dominated node in post DOM tree"
      )
      .protoId(182)

    val cdg = builder
      .addEdgeType(
        name = "CDG",
        comment = "Control dependency graph"
      )
      .protoId(183)

    val parameterLink = builder
      .addEdgeType(
        name = "PARAMETER_LINK",
        comment = "Links together corresponding METHOD_PARAMETER_IN and METHOD_PARAMETER_OUT nodes"
      )
      .protoId(12)

    val call = builder
      .addEdgeType(
        name = "CALL",
        comment = "Referencing to e.g. a LOCAL"
      )
      .protoId(6)

    val taggedBy = builder
      .addEdgeType(
        name = "TAGGED_BY",
        comment = "Edges from nodes to tags"
      )
      .protoId(11)

    val evalType = builder
      .addEdgeType(
        name = "EVAL_TYPE",
        comment = "Link to evaluation type"
      )
      .protoId(21)

    val inheritsFrom = builder
      .addEdgeType(
        name = "INHERITS_FROM",
        comment = "Inheritance relation between types"
      )
      .protoId(23)

    val contains = builder
      .addEdgeType(
        name = "CONTAINS",
        comment = "Shortcut over multiple AST edges"
      )
      .protoId(28)

    val reachingDef = builder
      .addEdgeType(
        name = "REACHING_DEF",
        comment = "Reaching definition edge"
      )
      .protoId(137)
      .addProperties(variable)

    val aliasOf = builder
      .addEdgeType(
        name = "ALIAS_OF",
        comment = "Alias relation between types"
      )
      .protoId(138)

    val typeDeclAlias = builder
      .addEdgeType(
        name = "TYPE_DECL_ALIAS",
        comment = "Alias relation between two TYPE_DECL"
      )
      .protoId(139)

    val binds = builder
      .addEdgeType(
        name = "BINDS",
        comment = "Relation between TYPE_DECL and BINDING node"
      )
      .protoId(155)

// node types

    val file: NodeType = builder
      .addNodeType(
        name = "FILE",
        comment = """Node representing a source file - the root of the AST.
            |Code property graphs are created from sets of files.
            |Information about these files is stored in the graph to enable queries to map nodes
            |of the graph back to the files that contain the code they represent.
            |For each file, the graph must contain exactly one File.
            |As file nodes are root nodes of abstract syntax tress, they are AstNodes and their
            |order field is set to 0.
            |
            |Each code property graph must contain a special file node with name set to
            |"<unknown>". This node is a placeholder used in cases where a file cannot be
            |determined at compile time. As an example, consider the case where an external
            |type is introduced only at link time.
            |Conceptually file nodes serve as indices, e.g., they map all filenames to the
            |list of methods they contain.
            |
            |File nodes MUST NOT be created by the language frontend. Instead, the language
            |frontend is assumed to fill out the FILENAME field wherever possible,
            |allowing File nodes to be created automatically when the semantic CPG layer is created.
            |""".stripMargin
      )
      .protoId(38)
      .addProperties(name, hash)
      .extendz(astNode)

    file
      .addOutEdge(edge = ast, inNode = namespaceBlock, cardinalityIn = Cardinality.ZeroOrOne)

    val binding: NodeType = builder
      .addNodeType(
        name = "BINDING",
        comment = "A binding of a METHOD into a TYPE_DECL"
      )
      .protoId(146)
      .addProperties(name, signature)

    val implicitCall: NodeType = builder
      .addNodeType(
        name = "IMPLICIT_CALL",
        comment = "An implicit call site hidden in a method indicated by METHOD_MAP policy entries"
      )
      .protoId(307)
      .extendz(callRepr, trackingPoint)

    val postExecutionCall: NodeType = builder
      .addNodeType(
        name = "POST_EXECUTION_CALL",
        comment =
          "Indicates the existence of a call executed on a return value or out parameter of a method after this method has been executed. This is used to model framework code calling functors returned from user code. The outgoing REF edge indicates on which returned entitity the call will happen."
      )
      .protoId(3071)
      .extendz(callRepr, trackingPoint)

    val tag: NodeType = builder
      .addNodeType(
        name = "TAG",
        comment = "A string tag"
      )
      .protoId(24)
      .addProperties(name, value)

    val namespace: NodeType = builder
      .addNodeType(
        name = "NAMESPACE",
        comment =
          "This node represents a namespace as a whole whereas the NAMESPACE_BLOCK is used for each grouping occurrence of a namespace in code. Single representing NAMESPACE node is required for easier navigation in the query language"
      )
      .protoId(40)
      .addProperties(name)
      .extendz(astNode)

    callNode
      .addProperties(dispatchType)

    method
      .addProperties(astParentType, astParentFullName)

    binding
      .addProperties(isMethodNeverOverridden)

    methodParameterIn
      .addProperties(evaluationStrategy)

    val methodParameterOut: NodeType = builder
      .addNodeType(
        name = "METHOD_PARAMETER_OUT",
        comment = "This node represents a formal parameter going towards the caller side"
      )
      .protoId(33)
      .addProperties(code, evaluationStrategy, typeFullName, lineNumber, columnNumber)
      .extendz(declaration, trackingPoint, astNode)

    methodReturn
      .addProperties(evaluationStrategy)

    typeDecl
      .addProperties(astParentType, astParentFullName)

// node relations

    method
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = dominate, inNode = callNode)
      .addOutEdge(edge = dominate, inNode = identifier)
      .addOutEdge(edge = dominate, inNode = fieldIdentifier)
      .addOutEdge(edge = dominate, inNode = literal)
      .addOutEdge(edge = dominate, inNode = methodRef)
      .addOutEdge(edge = dominate, inNode = typeRef)
      .addOutEdge(edge = dominate, inNode = ret)
      .addOutEdge(edge = dominate, inNode = block)
      .addOutEdge(edge = dominate, inNode = methodReturn)
      .addOutEdge(edge = dominate, inNode = unknown)

    methodReturn
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = postDominate, inNode = callNode)
      .addOutEdge(edge = postDominate, inNode = identifier)
      .addOutEdge(edge = postDominate, inNode = fieldIdentifier)
      .addOutEdge(edge = postDominate, inNode = literal)
      .addOutEdge(edge = postDominate, inNode = methodRef)
      .addOutEdge(edge = postDominate, inNode = typeRef)
      .addOutEdge(edge = postDominate, inNode = ret)
      .addOutEdge(edge = postDominate, inNode = block)
      .addOutEdge(edge = postDominate, inNode = method)
      .addOutEdge(edge = postDominate, inNode = controlStructure)
      .addOutEdge(edge = postDominate, inNode = jumpTarget)
      .addOutEdge(edge = postDominate, inNode = unknown)

    literal
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = dominate, inNode = callNode)
      .addOutEdge(edge = dominate, inNode = identifier)
      .addOutEdge(edge = dominate, inNode = fieldIdentifier)
      .addOutEdge(edge = dominate, inNode = literal)
      .addOutEdge(edge = dominate, inNode = methodRef)
      .addOutEdge(edge = dominate, inNode = typeRef)
      .addOutEdge(edge = dominate, inNode = ret)
      .addOutEdge(edge = dominate, inNode = block)
      .addOutEdge(edge = dominate, inNode = methodReturn)
      .addOutEdge(edge = dominate, inNode = controlStructure)
      .addOutEdge(edge = dominate, inNode = jumpTarget)
      .addOutEdge(edge = dominate, inNode = unknown)
      .addOutEdge(edge = postDominate, inNode = callNode)
      .addOutEdge(edge = postDominate, inNode = identifier)
      .addOutEdge(edge = postDominate, inNode = fieldIdentifier)
      .addOutEdge(edge = postDominate, inNode = literal)
      .addOutEdge(edge = postDominate, inNode = methodRef)
      .addOutEdge(edge = postDominate, inNode = typeRef)
      .addOutEdge(edge = postDominate, inNode = ret)
      .addOutEdge(edge = postDominate, inNode = block)
      .addOutEdge(edge = postDominate, inNode = method)
      .addOutEdge(edge = postDominate, inNode = controlStructure)
      .addOutEdge(edge = postDominate, inNode = jumpTarget)
      .addOutEdge(edge = postDominate, inNode = unknown)
      .addOutEdge(edge = cdg, inNode = callNode)
      .addOutEdge(edge = cdg, inNode = identifier)
      .addOutEdge(edge = cdg, inNode = fieldIdentifier)
      .addOutEdge(edge = cdg, inNode = literal)
      .addOutEdge(edge = cdg, inNode = methodRef)
      .addOutEdge(edge = cdg, inNode = typeRef)
      .addOutEdge(edge = cdg, inNode = ret)
      .addOutEdge(edge = cdg, inNode = block)
      .addOutEdge(edge = cdg, inNode = methodReturn)
      .addOutEdge(edge = cdg, inNode = controlStructure)
      .addOutEdge(edge = cdg, inNode = jumpTarget)
      .addOutEdge(edge = cdg, inNode = unknown)

    local
      .addOutEdge(edge = taggedBy, inNode = tag)

    member
      .addOutEdge(edge = taggedBy, inNode = tag)

    callNode
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = dominate, inNode = callNode)
      .addOutEdge(edge = dominate, inNode = identifier)
      .addOutEdge(edge = dominate, inNode = fieldIdentifier)
      .addOutEdge(edge = dominate, inNode = literal)
      .addOutEdge(edge = dominate, inNode = methodRef)
      .addOutEdge(edge = dominate, inNode = typeRef)
      .addOutEdge(edge = dominate, inNode = ret)
      .addOutEdge(edge = dominate, inNode = block)
      .addOutEdge(edge = dominate, inNode = methodReturn)
      .addOutEdge(edge = dominate, inNode = controlStructure)
      .addOutEdge(edge = dominate, inNode = jumpTarget)
      .addOutEdge(edge = dominate, inNode = unknown)
      .addOutEdge(edge = postDominate, inNode = callNode)
      .addOutEdge(edge = postDominate, inNode = identifier)
      .addOutEdge(edge = postDominate, inNode = fieldIdentifier)
      .addOutEdge(edge = postDominate, inNode = literal)
      .addOutEdge(edge = postDominate, inNode = methodRef)
      .addOutEdge(edge = postDominate, inNode = typeRef)
      .addOutEdge(edge = postDominate, inNode = ret)
      .addOutEdge(edge = postDominate, inNode = block)
      .addOutEdge(edge = postDominate, inNode = method)
      .addOutEdge(edge = postDominate, inNode = controlStructure)
      .addOutEdge(edge = postDominate, inNode = jumpTarget)
      .addOutEdge(edge = postDominate, inNode = unknown)
      .addOutEdge(edge = cdg, inNode = callNode)
      .addOutEdge(edge = cdg, inNode = identifier)
      .addOutEdge(edge = cdg, inNode = fieldIdentifier)
      .addOutEdge(edge = cdg, inNode = literal)
      .addOutEdge(edge = cdg, inNode = methodRef)
      .addOutEdge(edge = cdg, inNode = typeRef)
      .addOutEdge(edge = cdg, inNode = ret)
      .addOutEdge(edge = cdg, inNode = block)
      .addOutEdge(edge = cdg, inNode = methodReturn)
      .addOutEdge(edge = cdg, inNode = controlStructure)
      .addOutEdge(edge = cdg, inNode = jumpTarget)
      .addOutEdge(edge = cdg, inNode = unknown)

    identifier
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = dominate, inNode = callNode)
      .addOutEdge(edge = dominate, inNode = identifier)
      .addOutEdge(edge = dominate, inNode = fieldIdentifier)
      .addOutEdge(edge = dominate, inNode = literal)
      .addOutEdge(edge = dominate, inNode = methodRef)
      .addOutEdge(edge = dominate, inNode = typeRef)
      .addOutEdge(edge = dominate, inNode = ret)
      .addOutEdge(edge = dominate, inNode = block)
      .addOutEdge(edge = dominate, inNode = methodReturn)
      .addOutEdge(edge = dominate, inNode = controlStructure)
      .addOutEdge(edge = dominate, inNode = jumpTarget)
      .addOutEdge(edge = dominate, inNode = unknown)
      .addOutEdge(edge = postDominate, inNode = callNode)
      .addOutEdge(edge = postDominate, inNode = identifier)
      .addOutEdge(edge = postDominate, inNode = fieldIdentifier)
      .addOutEdge(edge = postDominate, inNode = literal)
      .addOutEdge(edge = postDominate, inNode = methodRef)
      .addOutEdge(edge = postDominate, inNode = typeRef)
      .addOutEdge(edge = postDominate, inNode = ret)
      .addOutEdge(edge = postDominate, inNode = block)
      .addOutEdge(edge = postDominate, inNode = method)
      .addOutEdge(edge = postDominate, inNode = controlStructure)
      .addOutEdge(edge = postDominate, inNode = jumpTarget)
      .addOutEdge(edge = postDominate, inNode = unknown)
      .addOutEdge(edge = cdg, inNode = callNode)
      .addOutEdge(edge = cdg, inNode = identifier)
      .addOutEdge(edge = cdg, inNode = fieldIdentifier)
      .addOutEdge(edge = cdg, inNode = literal)
      .addOutEdge(edge = cdg, inNode = methodRef)
      .addOutEdge(edge = cdg, inNode = typeRef)
      .addOutEdge(edge = cdg, inNode = ret)
      .addOutEdge(edge = cdg, inNode = block)
      .addOutEdge(edge = cdg, inNode = methodReturn)
      .addOutEdge(edge = cdg, inNode = controlStructure)
      .addOutEdge(edge = cdg, inNode = jumpTarget)
      .addOutEdge(edge = cdg, inNode = unknown)

    fieldIdentifier
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = dominate, inNode = callNode)
      .addOutEdge(edge = dominate, inNode = identifier)
      .addOutEdge(edge = dominate, inNode = fieldIdentifier)
      .addOutEdge(edge = dominate, inNode = literal)
      .addOutEdge(edge = dominate, inNode = methodRef)
      .addOutEdge(edge = dominate, inNode = typeRef)
      .addOutEdge(edge = dominate, inNode = ret)
      .addOutEdge(edge = dominate, inNode = block)
      .addOutEdge(edge = dominate, inNode = methodReturn)
      .addOutEdge(edge = dominate, inNode = controlStructure)
      .addOutEdge(edge = dominate, inNode = jumpTarget)
      .addOutEdge(edge = dominate, inNode = unknown)
      .addOutEdge(edge = postDominate, inNode = callNode)
      .addOutEdge(edge = postDominate, inNode = identifier)
      .addOutEdge(edge = postDominate, inNode = fieldIdentifier)
      .addOutEdge(edge = postDominate, inNode = literal)
      .addOutEdge(edge = postDominate, inNode = methodRef)
      .addOutEdge(edge = postDominate, inNode = typeRef)
      .addOutEdge(edge = postDominate, inNode = ret)
      .addOutEdge(edge = postDominate, inNode = block)
      .addOutEdge(edge = postDominate, inNode = method)
      .addOutEdge(edge = postDominate, inNode = controlStructure)
      .addOutEdge(edge = postDominate, inNode = jumpTarget)
      .addOutEdge(edge = postDominate, inNode = unknown)
      .addOutEdge(edge = cdg, inNode = callNode)
      .addOutEdge(edge = cdg, inNode = identifier)
      .addOutEdge(edge = cdg, inNode = fieldIdentifier)
      .addOutEdge(edge = cdg, inNode = literal)
      .addOutEdge(edge = cdg, inNode = methodRef)
      .addOutEdge(edge = cdg, inNode = typeRef)
      .addOutEdge(edge = cdg, inNode = ret)
      .addOutEdge(edge = cdg, inNode = block)
      .addOutEdge(edge = cdg, inNode = methodReturn)
      .addOutEdge(edge = cdg, inNode = controlStructure)
      .addOutEdge(edge = cdg, inNode = jumpTarget)
      .addOutEdge(edge = cdg, inNode = unknown)

    methodParameterIn
      .addOutEdge(edge = taggedBy, inNode = tag)

    ret
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = dominate, inNode = callNode)
      .addOutEdge(edge = dominate, inNode = identifier)
      .addOutEdge(edge = dominate, inNode = fieldIdentifier)
      .addOutEdge(edge = dominate, inNode = literal)
      .addOutEdge(edge = dominate, inNode = methodRef)
      .addOutEdge(edge = dominate, inNode = typeRef)
      .addOutEdge(edge = dominate, inNode = ret)
      .addOutEdge(edge = dominate, inNode = block)
      .addOutEdge(edge = dominate, inNode = methodReturn)
      .addOutEdge(edge = dominate, inNode = controlStructure)
      .addOutEdge(edge = dominate, inNode = jumpTarget)
      .addOutEdge(edge = dominate, inNode = unknown)
      .addOutEdge(edge = postDominate, inNode = callNode)
      .addOutEdge(edge = postDominate, inNode = identifier)
      .addOutEdge(edge = postDominate, inNode = fieldIdentifier)
      .addOutEdge(edge = postDominate, inNode = literal)
      .addOutEdge(edge = postDominate, inNode = methodRef)
      .addOutEdge(edge = postDominate, inNode = typeRef)
      .addOutEdge(edge = postDominate, inNode = ret)
      .addOutEdge(edge = postDominate, inNode = block)
      .addOutEdge(edge = postDominate, inNode = method)
      .addOutEdge(edge = postDominate, inNode = controlStructure)
      .addOutEdge(edge = postDominate, inNode = jumpTarget)
      .addOutEdge(edge = postDominate, inNode = unknown)

    block
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = dominate, inNode = callNode)
      .addOutEdge(edge = dominate, inNode = identifier)
      .addOutEdge(edge = dominate, inNode = fieldIdentifier)
      .addOutEdge(edge = dominate, inNode = literal)
      .addOutEdge(edge = dominate, inNode = methodRef)
      .addOutEdge(edge = dominate, inNode = typeRef)
      .addOutEdge(edge = dominate, inNode = ret)
      .addOutEdge(edge = dominate, inNode = block)
      .addOutEdge(edge = dominate, inNode = methodReturn)
      .addOutEdge(edge = dominate, inNode = controlStructure)
      .addOutEdge(edge = dominate, inNode = jumpTarget)
      .addOutEdge(edge = dominate, inNode = unknown)
      .addOutEdge(edge = postDominate, inNode = callNode)
      .addOutEdge(edge = postDominate, inNode = identifier)
      .addOutEdge(edge = postDominate, inNode = fieldIdentifier)
      .addOutEdge(edge = postDominate, inNode = literal)
      .addOutEdge(edge = postDominate, inNode = methodRef)
      .addOutEdge(edge = postDominate, inNode = typeRef)
      .addOutEdge(edge = postDominate, inNode = ret)
      .addOutEdge(edge = postDominate, inNode = block)
      .addOutEdge(edge = postDominate, inNode = method)
      .addOutEdge(edge = postDominate, inNode = controlStructure)
      .addOutEdge(edge = postDominate, inNode = jumpTarget)
      .addOutEdge(edge = postDominate, inNode = unknown)
      .addOutEdge(edge = cdg, inNode = callNode)
      .addOutEdge(edge = cdg, inNode = identifier)
      .addOutEdge(edge = cdg, inNode = fieldIdentifier)
      .addOutEdge(edge = cdg, inNode = literal)
      .addOutEdge(edge = cdg, inNode = methodRef)
      .addOutEdge(edge = cdg, inNode = typeRef)
      .addOutEdge(edge = cdg, inNode = ret)
      .addOutEdge(edge = cdg, inNode = block)
      .addOutEdge(edge = cdg, inNode = methodReturn)
      .addOutEdge(edge = cdg, inNode = controlStructure)
      .addOutEdge(edge = cdg, inNode = jumpTarget)
      .addOutEdge(edge = cdg, inNode = unknown)

    unknown
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = dominate, inNode = callNode)
      .addOutEdge(edge = dominate, inNode = identifier)
      .addOutEdge(edge = dominate, inNode = fieldIdentifier)
      .addOutEdge(edge = dominate, inNode = literal)
      .addOutEdge(edge = dominate, inNode = methodRef)
      .addOutEdge(edge = dominate, inNode = typeRef)
      .addOutEdge(edge = dominate, inNode = ret)
      .addOutEdge(edge = dominate, inNode = block)
      .addOutEdge(edge = dominate, inNode = methodReturn)
      .addOutEdge(edge = dominate, inNode = controlStructure)
      .addOutEdge(edge = dominate, inNode = jumpTarget)
      .addOutEdge(edge = dominate, inNode = unknown)
      .addOutEdge(edge = postDominate, inNode = callNode)
      .addOutEdge(edge = postDominate, inNode = identifier)
      .addOutEdge(edge = postDominate, inNode = fieldIdentifier)
      .addOutEdge(edge = postDominate, inNode = literal)
      .addOutEdge(edge = postDominate, inNode = methodRef)
      .addOutEdge(edge = postDominate, inNode = typeRef)
      .addOutEdge(edge = postDominate, inNode = ret)
      .addOutEdge(edge = postDominate, inNode = block)
      .addOutEdge(edge = postDominate, inNode = method)
      .addOutEdge(edge = postDominate, inNode = controlStructure)
      .addOutEdge(edge = postDominate, inNode = jumpTarget)
      .addOutEdge(edge = postDominate, inNode = unknown)
      .addOutEdge(edge = cdg, inNode = callNode)
      .addOutEdge(edge = cdg, inNode = identifier)
      .addOutEdge(edge = cdg, inNode = fieldIdentifier)
      .addOutEdge(edge = cdg, inNode = literal)
      .addOutEdge(edge = cdg, inNode = methodRef)
      .addOutEdge(edge = cdg, inNode = typeRef)
      .addOutEdge(edge = cdg, inNode = ret)
      .addOutEdge(edge = cdg, inNode = block)
      .addOutEdge(edge = cdg, inNode = methodReturn)
      .addOutEdge(edge = cdg, inNode = controlStructure)
      .addOutEdge(edge = cdg, inNode = jumpTarget)
      .addOutEdge(edge = cdg, inNode = unknown)

    controlStructure
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = dominate, inNode = callNode)
      .addOutEdge(edge = dominate, inNode = identifier)
      .addOutEdge(edge = dominate, inNode = fieldIdentifier)
      .addOutEdge(edge = dominate, inNode = literal)
      .addOutEdge(edge = dominate, inNode = methodRef)
      .addOutEdge(edge = dominate, inNode = typeRef)
      .addOutEdge(edge = dominate, inNode = ret)
      .addOutEdge(edge = dominate, inNode = block)
      .addOutEdge(edge = dominate, inNode = methodReturn)
      .addOutEdge(edge = dominate, inNode = controlStructure)
      .addOutEdge(edge = dominate, inNode = jumpTarget)
      .addOutEdge(edge = dominate, inNode = unknown)
      .addOutEdge(edge = postDominate, inNode = callNode)
      .addOutEdge(edge = postDominate, inNode = identifier)
      .addOutEdge(edge = postDominate, inNode = fieldIdentifier)
      .addOutEdge(edge = postDominate, inNode = literal)
      .addOutEdge(edge = postDominate, inNode = methodRef)
      .addOutEdge(edge = postDominate, inNode = typeRef)
      .addOutEdge(edge = postDominate, inNode = ret)
      .addOutEdge(edge = postDominate, inNode = block)
      .addOutEdge(edge = postDominate, inNode = method)
      .addOutEdge(edge = postDominate, inNode = controlStructure)
      .addOutEdge(edge = postDominate, inNode = jumpTarget)
      .addOutEdge(edge = postDominate, inNode = unknown)
      .addOutEdge(edge = cdg, inNode = callNode)
      .addOutEdge(edge = cdg, inNode = identifier)
      .addOutEdge(edge = cdg, inNode = fieldIdentifier)
      .addOutEdge(edge = cdg, inNode = literal)
      .addOutEdge(edge = cdg, inNode = methodRef)
      .addOutEdge(edge = cdg, inNode = typeRef)
      .addOutEdge(edge = cdg, inNode = ret)
      .addOutEdge(edge = cdg, inNode = block)
      .addOutEdge(edge = cdg, inNode = methodReturn)
      .addOutEdge(edge = cdg, inNode = controlStructure)
      .addOutEdge(edge = cdg, inNode = jumpTarget)
      .addOutEdge(edge = cdg, inNode = unknown)

    methodRef
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = dominate, inNode = callNode)
      .addOutEdge(edge = dominate, inNode = identifier)
      .addOutEdge(edge = dominate, inNode = fieldIdentifier)
      .addOutEdge(edge = dominate, inNode = literal)
      .addOutEdge(edge = dominate, inNode = methodRef)
      .addOutEdge(edge = dominate, inNode = typeRef)
      .addOutEdge(edge = dominate, inNode = ret)
      .addOutEdge(edge = dominate, inNode = block)
      .addOutEdge(edge = dominate, inNode = methodReturn)
      .addOutEdge(edge = dominate, inNode = controlStructure)
      .addOutEdge(edge = dominate, inNode = jumpTarget)
      .addOutEdge(edge = dominate, inNode = unknown)
      .addOutEdge(edge = postDominate, inNode = callNode)
      .addOutEdge(edge = postDominate, inNode = identifier)
      .addOutEdge(edge = postDominate, inNode = fieldIdentifier)
      .addOutEdge(edge = postDominate, inNode = literal)
      .addOutEdge(edge = postDominate, inNode = methodRef)
      .addOutEdge(edge = postDominate, inNode = typeRef)
      .addOutEdge(edge = postDominate, inNode = ret)
      .addOutEdge(edge = postDominate, inNode = block)
      .addOutEdge(edge = postDominate, inNode = method)
      .addOutEdge(edge = postDominate, inNode = controlStructure)
      .addOutEdge(edge = postDominate, inNode = jumpTarget)
      .addOutEdge(edge = postDominate, inNode = unknown)
      .addOutEdge(edge = cdg, inNode = callNode)
      .addOutEdge(edge = cdg, inNode = identifier)
      .addOutEdge(edge = cdg, inNode = fieldIdentifier)
      .addOutEdge(edge = cdg, inNode = literal)
      .addOutEdge(edge = cdg, inNode = methodRef)
      .addOutEdge(edge = cdg, inNode = typeRef)
      .addOutEdge(edge = cdg, inNode = ret)
      .addOutEdge(edge = cdg, inNode = block)
      .addOutEdge(edge = cdg, inNode = methodReturn)
      .addOutEdge(edge = cdg, inNode = controlStructure)
      .addOutEdge(edge = cdg, inNode = jumpTarget)
      .addOutEdge(edge = cdg, inNode = unknown)

    typeRef
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = dominate, inNode = callNode)
      .addOutEdge(edge = dominate, inNode = identifier)
      .addOutEdge(edge = dominate, inNode = fieldIdentifier)
      .addOutEdge(edge = dominate, inNode = literal)
      .addOutEdge(edge = dominate, inNode = methodRef)
      .addOutEdge(edge = dominate, inNode = typeRef)
      .addOutEdge(edge = dominate, inNode = ret)
      .addOutEdge(edge = dominate, inNode = block)
      .addOutEdge(edge = dominate, inNode = methodReturn)
      .addOutEdge(edge = dominate, inNode = controlStructure)
      .addOutEdge(edge = dominate, inNode = jumpTarget)
      .addOutEdge(edge = dominate, inNode = unknown)
      .addOutEdge(edge = postDominate, inNode = callNode)
      .addOutEdge(edge = postDominate, inNode = identifier)
      .addOutEdge(edge = postDominate, inNode = fieldIdentifier)
      .addOutEdge(edge = postDominate, inNode = literal)
      .addOutEdge(edge = postDominate, inNode = methodRef)
      .addOutEdge(edge = postDominate, inNode = typeRef)
      .addOutEdge(edge = postDominate, inNode = ret)
      .addOutEdge(edge = postDominate, inNode = block)
      .addOutEdge(edge = postDominate, inNode = method)
      .addOutEdge(edge = postDominate, inNode = controlStructure)
      .addOutEdge(edge = postDominate, inNode = jumpTarget)
      .addOutEdge(edge = postDominate, inNode = unknown)
      .addOutEdge(edge = cdg, inNode = callNode)
      .addOutEdge(edge = cdg, inNode = identifier)
      .addOutEdge(edge = cdg, inNode = fieldIdentifier)
      .addOutEdge(edge = cdg, inNode = literal)
      .addOutEdge(edge = cdg, inNode = methodRef)
      .addOutEdge(edge = cdg, inNode = typeRef)
      .addOutEdge(edge = cdg, inNode = ret)
      .addOutEdge(edge = cdg, inNode = block)
      .addOutEdge(edge = cdg, inNode = methodReturn)
      .addOutEdge(edge = cdg, inNode = controlStructure)
      .addOutEdge(edge = cdg, inNode = jumpTarget)
      .addOutEdge(edge = cdg, inNode = unknown)

    jumpTarget
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = dominate, inNode = callNode)
      .addOutEdge(edge = dominate, inNode = identifier)
      .addOutEdge(edge = dominate, inNode = fieldIdentifier)
      .addOutEdge(edge = dominate, inNode = literal)
      .addOutEdge(edge = dominate, inNode = ret)
      .addOutEdge(edge = dominate, inNode = methodRef)
      .addOutEdge(edge = dominate, inNode = typeRef)
      .addOutEdge(edge = dominate, inNode = block)
      .addOutEdge(edge = dominate, inNode = jumpTarget)
      .addOutEdge(edge = dominate, inNode = controlStructure)
      .addOutEdge(edge = dominate, inNode = unknown)
      .addOutEdge(edge = postDominate, inNode = callNode)
      .addOutEdge(edge = postDominate, inNode = identifier)
      .addOutEdge(edge = postDominate, inNode = fieldIdentifier)
      .addOutEdge(edge = postDominate, inNode = literal)
      .addOutEdge(edge = postDominate, inNode = ret)
      .addOutEdge(edge = postDominate, inNode = methodRef)
      .addOutEdge(edge = postDominate, inNode = typeRef)
      .addOutEdge(edge = postDominate, inNode = block)
      .addOutEdge(edge = postDominate, inNode = jumpTarget)
      .addOutEdge(edge = postDominate, inNode = controlStructure)
      .addOutEdge(edge = postDominate, inNode = unknown)
      .addOutEdge(edge = cdg, inNode = callNode)
      .addOutEdge(edge = cdg, inNode = identifier)
      .addOutEdge(edge = cdg, inNode = fieldIdentifier)
      .addOutEdge(edge = cdg, inNode = literal)
      .addOutEdge(edge = cdg, inNode = methodRef)
      .addOutEdge(edge = cdg, inNode = typeRef)
      .addOutEdge(edge = cdg, inNode = ret)
      .addOutEdge(edge = cdg, inNode = block)
      .addOutEdge(edge = cdg, inNode = methodReturn)
      .addOutEdge(edge = cdg, inNode = controlStructure)
      .addOutEdge(edge = cdg, inNode = jumpTarget)
      .addOutEdge(edge = cdg, inNode = unknown)

    binding
      .addOutEdge(edge = ref, inNode = method, cardinalityOut = Cardinality.One)

    postExecutionCall
      .addOutEdge(edge = ref, inNode = methodReturn)
      .addOutEdge(edge = ref, inNode = methodParameterOut)

    file
      .addOutEdge(edge = contains, inNode = typeDecl)
      .addOutEdge(edge = contains, inNode = method)
      .addOutEdge(edge = taggedBy, inNode = tag)

    method
      .addOutEdge(edge = ast, inNode = typeDecl, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = method, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = methodParameterOut)
      .addOutEdge(edge = ast, inNode = implicitCall)
      .addOutEdge(edge = ast, inNode = postExecutionCall)
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = methodParameterIn)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = contains, inNode = callNode)
      .addOutEdge(edge = contains, inNode = identifier)
      .addOutEdge(edge = contains, inNode = fieldIdentifier)
      .addOutEdge(edge = contains, inNode = literal)
      .addOutEdge(edge = contains, inNode = ret)
      .addOutEdge(edge = contains, inNode = methodRef)
      .addOutEdge(edge = contains, inNode = typeRef)
      .addOutEdge(edge = contains, inNode = block)
      .addOutEdge(edge = contains, inNode = controlStructure)
      .addOutEdge(edge = contains, inNode = jumpTarget)
      .addOutEdge(edge = contains, inNode = unknown)
      .addOutEdge(edge = sourceFile, inNode = file)

    ret
      .addOutEdge(edge = reachingDef, inNode = methodReturn)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = methodRef)

    methodRef
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)

    methodParameterIn
      .addOutEdge(edge = evalType, inNode = tpe, cardinalityOut = Cardinality.One)
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = parameterLink, inNode = methodParameterOut)

    methodParameterOut
      .addOutEdge(edge = taggedBy, inNode = tag)
      .addOutEdge(edge = evalType, inNode = tpe)

    methodReturn
      .addOutEdge(edge = evalType, inNode = tpe)

    namespaceBlock
      .addOutEdge(edge = ast, inNode = typeDecl, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = method, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ref, inNode = namespace)
      .addOutEdge(edge = sourceFile, inNode = file)

    methodRef
      .addOutEdge(edge = ref, inNode = method, cardinalityOut = Cardinality.One)
      .addOutEdge(edge = evalType, inNode = tpe)

    typeRef
      .addOutEdge(edge = evalType, inNode = tpe)

    tpe
      .addOutEdge(edge = ref, inNode = typeDecl)

    typeDecl
      .addOutEdge(edge = ast, inNode = typeDecl, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = method, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = inheritsFrom, inNode = tpe)
      .addOutEdge(edge = aliasOf, inNode = tpe)
      .addOutEdge(edge = contains, inNode = method)
      .addOutEdge(edge = sourceFile, inNode = file)
      .addOutEdge(edge = typeDeclAlias, inNode = typeDecl)
      .addOutEdge(edge = binds, inNode = binding, cardinalityIn = Cardinality.One)

    member
      .addOutEdge(edge = evalType, inNode = tpe)

    literal
      .addOutEdge(edge = evalType, inNode = tpe)
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)

    callNode
      .addOutEdge(edge = ref, inNode = member)
      .addOutEdge(edge = call, inNode = method)
      .addOutEdge(edge = evalType, inNode = tpe)
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)

    local
      .addOutEdge(edge = evalType, inNode = tpe)

    identifier
      .addOutEdge(edge = evalType, inNode = tpe)
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)

    block
      .addOutEdge(edge = evalType, inNode = tpe)
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = block)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)

    controlStructure
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = evalType, inNode = tpe)

    unknown
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = evalType, inNode = tpe)

// constants
    val dispatchTypes = builder.addConstants(
      category = "DispatchTypes",
      Constant(
        name = "STATIC_DISPATCH",
        value = "STATIC_DISPATCH",
        valueType = ValueTypes.STRING,
        comment = "For statically dispatched calls the call target is known before program execution"
      ).protoId(1),
      Constant(
        name = "DYNAMIC_DISPATCH",
        value = "DYNAMIC_DISPATCH",
        valueType = ValueTypes.STRING,
        comment = "For dynamically dispatched calls the target is determined during runtime"
      ).protoId(2),
    )

    val evaluationStrategies = builder.addConstants(
      category = "EvaluationStrategies",
      Constant(
        name = "BY_REFERENCE",
        value = "BY_REFERENCE",
        valueType = ValueTypes.STRING,
        comment =
          "A parameter or return of a function is passed by reference which means an address is used behind the scenes"
      ).protoId(1),
      Constant(
        name = "BY_SHARING",
        value = "BY_SHARING",
        valueType = ValueTypes.STRING,
        comment =
          "Only applicable to object parameter or return values. The pointer to the object is passed by value but the object itself is not copied and changes to it are thus propagated out of the method context"
      ).protoId(2),
      Constant(
        name = "BY_VALUE",
        value = "BY_VALUE",
        valueType = ValueTypes.STRING,
        comment = "A parameter or return of a function passed by value which means a flat copy is used"
      ).protoId(3),
    )

  }

}
