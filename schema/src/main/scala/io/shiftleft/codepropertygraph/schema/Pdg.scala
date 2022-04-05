package io.shiftleft.codepropertygraph.schema

import io.shiftleft.codepropertygraph.schema.CpgSchema.PropertyDefaults
import overflowdb.schema.Property.ValueType
import overflowdb.schema.{SchemaBuilder, SchemaInfo}

object Pdg extends SchemaBase {

  def docIndex: Int = 11
  override def description: String =
    """
      The Program Dependence Graph Layer contains a program dependence graph for
      |each method of the source program. A program dependence graph consists
      |of a data dependence graph (DDG) and a control dependence graph (CDG),
      |created by connecting nodes of the control flow graph via `REACHING_DEF`
      |and `CDG` edges respectively.
      |""".stripMargin

  def apply(builder: SchemaBuilder, methodSchema: Method.Schema, ast: Ast.Schema) =
    new Schema(builder, methodSchema, ast)

  class Schema(builder: SchemaBuilder, methodSchema: Method.Schema, ast: Ast.Schema) {
    implicit private val schemaInfo: SchemaInfo = SchemaInfo.forClass(getClass)
    import methodSchema._
    import ast._

    val variable = builder
      .addProperty(
        name = "VARIABLE",
        valueType = ValueType.String,
        comment = "This edge property represents the variable propagated by a reaching definition edge."
      )
      .mandatory(PropertyDefaults.String)
      .protoId(11)

    val cdg = builder
      .addEdgeType(
        name = "CDG",
        comment = "A CDG edge expresses that the destination node is control dependent on the source node."
      )
      .protoId(183)

    val reachingDef = builder
      .addEdgeType(
        name = "REACHING_DEF",
        comment = """
            |A reaching definition edge indicates that a variable produced at the source node reaches
            |the destination node without being reassigned on the way. The `VARIABLE` property indicates
            |which variable is propagated.
            |""".stripMargin
      )
      .protoId(137)
      .addProperties(variable)

    literal
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

    callNode
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

    block
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

    method
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = methodParameterIn)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = reachingDef, inNode = typeRef)

    ret
      .addOutEdge(edge = reachingDef, inNode = methodReturn)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = reachingDef, inNode = typeRef)

    methodRef
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = reachingDef, inNode = typeRef)

    typeRef
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = reachingDef, inNode = typeRef)

    methodParameterIn
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = reachingDef, inNode = typeRef)

    literal
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = reachingDef, inNode = typeRef)

    callNode
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = reachingDef, inNode = typeRef)

    identifier
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = reachingDef, inNode = typeRef)

    block
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = block)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = reachingDef, inNode = typeRef)

    controlStructure
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = reachingDef, inNode = typeRef)

    unknown
      .addOutEdge(edge = reachingDef, inNode = callNode)
      .addOutEdge(edge = reachingDef, inNode = ret)
      .addOutEdge(edge = reachingDef, inNode = identifier)
      .addOutEdge(edge = reachingDef, inNode = literal)
      .addOutEdge(edge = reachingDef, inNode = methodRef)
      .addOutEdge(edge = reachingDef, inNode = typeRef)

  }

}
