package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._

object Enhancements extends SchemaBase {

  def index: Int = 3

  override def description: String =
    """
      |Node and edges introduced in the linking stage. These are edges that can only
      |be drawn once all compilation units have gone through local analysis as done
      |by the language frontend.
      |
      |These nodes/edges should NOT be created by the language frontend.
      |""".stripMargin

  def apply(builder: SchemaBuilder,
            base: Base.Schema,
            methodSchema: Method.Schema,
            methodBody: MethodBody.Schema,
            typeDeclSchema: TypeDecl.Schema,
            common: CommonProperties.Schema) =
    new Schema(builder, base, methodSchema, methodBody, typeDeclSchema, common)

  class Schema(builder: SchemaBuilder,
               base: Base.Schema,
               methodSchema: Method.Schema,
               methodBody: MethodBody.Schema,
               typeDeclSchema: TypeDecl.Schema,
               common: CommonProperties.Schema) {

    import base._
    import methodSchema._
    import methodBody._
    import typeDeclSchema._
    import common._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

// node properties

    val parameterLink = builder
      .addEdgeType(
        name = "PARAMETER_LINK",
        comment = "Links together corresponding METHOD_PARAMETER_IN and METHOD_PARAMETER_OUT nodes"
      )
      .protoId(12)

    val evalType = builder
      .addEdgeType(
        name = "EVAL_TYPE",
        comment = "Link to evaluation type"
      )
      .protoId(21)

    val contains = builder
      .addEdgeType(
        name = "CONTAINS",
        comment = "Shortcut over multiple AST edges"
      )
      .protoId(28)

    val inheritsFrom = builder
      .addEdgeType(
        name = "INHERITS_FROM",
        comment = "Inheritance relation between types"
      )
      .protoId(23)

    val aliasOf = builder
      .addEdgeType(
        name = "ALIAS_OF",
        comment = "Alias relation between types"
      )
      .protoId(138)

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

    val namespace: NodeType = builder
      .addNodeType(
        name = "NAMESPACE",
        comment = """This node represents a namespace as a whole whereas the NAMESPACE_BLOCK is used
            |for each grouping occurrence of a namespace in code.
            |Single representing NAMESPACE node is required for easier navigation in
            |the query language.
            |""".stripMargin
      )
      .protoId(40)
      .addProperties(name)
      .extendz(astNode)

// node relations

    binding
      .addOutEdge(edge = ref, inNode = method, cardinalityOut = Cardinality.One)

    file
      .addOutEdge(edge = ast, inNode = namespaceBlock, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = contains, inNode = typeDecl)
      .addOutEdge(edge = contains, inNode = method)

    method
      .addOutEdge(edge = ast, inNode = methodParameterOut)
      .addOutEdge(edge = ast, inNode = typeDecl, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = method, cardinalityIn = Cardinality.ZeroOrOne)
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

    methodParameterIn
      .addOutEdge(edge = evalType, inNode = tpe, cardinalityOut = Cardinality.One)
      .addOutEdge(edge = parameterLink, inNode = methodParameterOut)

    methodParameterOut
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
      .addOutEdge(edge = inheritsFrom, inNode = tpe)
      .addOutEdge(edge = ast, inNode = typeDecl, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = method, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = aliasOf, inNode = tpe)
      .addOutEdge(edge = contains, inNode = method)
      .addOutEdge(edge = sourceFile, inNode = file)

    member
      .addOutEdge(edge = evalType, inNode = tpe)

    literal
      .addOutEdge(edge = evalType, inNode = tpe)

    callNode
      .addOutEdge(edge = ref, inNode = member)
      .addOutEdge(edge = evalType, inNode = tpe)

    local
      .addOutEdge(edge = evalType, inNode = tpe)

    identifier
      .addOutEdge(edge = evalType, inNode = tpe)

    block
      .addOutEdge(edge = evalType, inNode = tpe)

    controlStructure
      .addOutEdge(edge = evalType, inNode = tpe)

    unknown
      .addOutEdge(edge = evalType, inNode = tpe)

  }

}
