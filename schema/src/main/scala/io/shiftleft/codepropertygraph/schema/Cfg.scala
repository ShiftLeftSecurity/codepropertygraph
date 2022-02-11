package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.EdgeType.Cardinality
import overflowdb.schema.{SchemaBuilder, SchemaInfo}

object Cfg extends SchemaBase {

  def index: Int = 9

  override def description: String =
    """
      |The Control Flow Graph Layer provides control flow graphs for each method. Control
      |flow graphs are constructed by marking a sub set of the abstract syntax tree nodes
      |as control flow nodes (`CFG_NODE`) and connecting these nodes via `CFG` edges.
      |The control flow graph models both the control flow within the calculation of an
      |expression as well as from expression to expression. The layer can be automatically
      |generated from the syntax tree layer if only control structure types supported by
      |this specification are employed.
      |""".stripMargin

  def apply(builder: SchemaBuilder, methodSchema: Method.Schema, ast: Ast.Schema) =
    new Schema(builder, methodSchema, ast)

  class Schema(builder: SchemaBuilder, methodSchema: Method.Schema, ast: Ast.Schema) {
    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)
    import methodSchema._
    import ast._

    val cfgNode = builder
      .addNodeBaseType(
        name = "CFG_NODE",
        comment = """This is the base class for all control flow nodes. It is itself
            |a child class of `AST_NODE`, that is, all control flow graph nodes
            |are also syntax tree nodes in the CPG specification.
            |"""".stripMargin
      )
      .extendz(astNode)

    // Method and MethodReturn nodes are used as ENTRY and EXIT nodes respectively
    method.extendz(cfgNode)
    methodReturn.extendz(cfgNode)

    // While an input parameter is a declaration, we can just as well view it
    // as the CFG node that assigns the actual in to the parameter variable.
    // Similarly, output parameters can be seen as assignments of the parameter
    // to the actual out.
    methodParameterIn.extendz(cfgNode)
    methodParameterOut.extendz(cfgNode)

    expression.extendz(cfgNode)
    callRepr.extendz(cfgNode)
    jumpTarget.extendz(cfgNode)

    val cfg = builder
      .addEdgeType(name = "CFG", comment = "This edge indicates control flow from the source to the destination node.")
      .protoId(19)

    method
      .addOutEdge(
        edge = cfg,
        inNode = methodReturn,
        cardinalityOut = Cardinality.ZeroOrOne,
        cardinalityIn = Cardinality.ZeroOrOne
      )
      .addOutEdge(
        edge = cfg,
        inNode = cfgNode,
        stepNameOut = "cfgFirst",
        stepNameOutDoc = "First control flow graph node"
      )

    fieldIdentifier
      .addOutEdge(edge = cfg, inNode = cfgNode)
      .addOutEdge(
        edge = cfg,
        inNode = callNode,
        cardinalityOut = Cardinality.One,
        cardinalityIn = Cardinality.ZeroOrOne
      )

    block.addOutEdge(edge = cfg, inNode = cfgNode)
    callNode.addOutEdge(edge = cfg, inNode = cfgNode)
    controlStructure.addOutEdge(edge = cfg, inNode = cfgNode)
    jumpTarget.addOutEdge(edge = cfg, inNode = cfgNode)
    identifier.addOutEdge(edge = cfg, inNode = cfgNode)
    literal.addOutEdge(edge = cfg, inNode = cfgNode)
    methodRef.addOutEdge(edge = cfg, inNode = cfgNode)
    typeRef.addOutEdge(edge = cfg, inNode = cfgNode)
    unknown.addOutEdge(edge = cfg, inNode = cfgNode)

    /** Each METHOD has exactly one METHOD_RETURN (the formal return value), but each METHOD_RETURN can have multiple
      * RETURN nodes. This way, we can represent a method with multiple return statements.
      */
    ret.addOutEdge(
      edge = cfg,
      inNode = methodReturn,
      cardinalityOut = Cardinality.One,
      cardinalityIn = Cardinality.List,
      stepNameIn = "toReturn"
    )

    methodRef.addOutEdge(edge = cfg, inNode = methodReturn)
    typeRef.addOutEdge(edge = cfg, inNode = methodReturn)
  }

}
