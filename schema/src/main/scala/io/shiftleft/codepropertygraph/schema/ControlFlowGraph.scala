package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, SchemaBuilder, SchemaInfo}

object ControlFlowGraph extends SchemaBase {

  def index: Int = 6

  override def description: String =
    """
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema, methodSchema: Method.Schema, ast: Ast.Schema) =
    new Schema(builder, base, methodSchema, ast)

  class Schema(builder: SchemaBuilder, base: Base.Schema, methodSchema: Method.Schema, ast: Ast.Schema) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)
    import methodSchema._
    import ast._
    import base._

    val cfgNode = builder
      .addNodeBaseType(
        name = "CFG_NODE",
        comment = "Any node that can occur as part of a control flow graph"
      )
      .addProperties(lineNumber, columnNumber, code)
      .extendz(withinMethod, astNode)

    // Method and MethodReturn nodes are used as ENTRY and EXIT nodes respectively
    method.extendz(cfgNode)
    methodReturn.extendz(cfgNode)

    expression.extendz(cfgNode)
    callRepr.extendz(cfgNode)
    jumpTarget.extendz(cfgNode)

    val cfg = builder
      .addEdgeType(
        name = "CFG",
        comment = "Control flow edge"
      )
      .protoId(19)

    method
      .addOutEdge(edge = cfg,
                  inNode = methodReturn,
                  cardinalityOut = Cardinality.ZeroOrOne,
                  cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = cfg, inNode = cfgNode)

    fieldIdentifier
      .addOutEdge(edge = cfg, inNode = cfgNode)
      .addOutEdge(edge = cfg,
                  inNode = callNode,
                  cardinalityOut = Cardinality.One,
                  cardinalityIn = Cardinality.ZeroOrOne)

    block.addOutEdge(edge = cfg, inNode = cfgNode)
    callNode.addOutEdge(edge = cfg, inNode = cfgNode)
    controlStructure.addOutEdge(edge = cfg, inNode = cfgNode)
    jumpTarget.addOutEdge(edge = cfg, inNode = cfgNode)
    identifier.addOutEdge(edge = cfg, inNode = cfgNode)
    literal.addOutEdge(edge = cfg, inNode = cfgNode)
    methodRef.addOutEdge(edge = cfg, inNode = cfgNode)
    typeRef.addOutEdge(edge = cfg, inNode = cfgNode)
    unknown.addOutEdge(edge = cfg, inNode = cfgNode)

    ret.addOutEdge(edge = cfg,
                   inNode = methodReturn,
                   cardinalityOut = Cardinality.One,
                   cardinalityIn = Cardinality.ZeroOrOne)

    methodRef.addOutEdge(edge = cfg, inNode = methodReturn)
    typeRef.addOutEdge(edge = cfg, inNode = methodReturn)
  }

}
