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

    identifier
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
      .addOutEdge(edge = cfg, inNode = methodReturn)

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

    block
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

    methodRef
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

    typeRef
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

    controlStructure
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

    ret.addOutEdge(edge = cfg,
                   inNode = methodReturn,
                   cardinalityOut = Cardinality.One,
                   cardinalityIn = Cardinality.ZeroOrOne)

    methodRef
      .addOutEdge(edge = cfg, inNode = methodReturn)

    typeRef
      .addOutEdge(edge = cfg, inNode = methodReturn)


    val fooRoot = builder.addNodeBaseType("FOO_ROOT")
    val foo1 = builder.addNodeType("FOO1").extendz(fooRoot)
    val foo2 = builder.addNodeType("FOO2").extendz(fooRoot)
    fooRoot.addOutEdge(cfg, fooRoot)
  }

}
