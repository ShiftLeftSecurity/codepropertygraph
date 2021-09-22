package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.Property.ValueType
import overflowdb.schema.{EdgeType, NodeType, SchemaBuilder, SchemaInfo}

object Hidden extends SchemaBase {
  override def index: Int = -1

  override def description: String =
    """
      |The schema elements defined here are NOT included on the schema website.
      |These are schema elements that we are using but would like to rework
      |in the future and not make part of the standard.
      |""".stripMargin

  def apply(builder: SchemaBuilder,
            base: Base.Schema,
            methodSchema: Method.Schema,
            typeDecl: Type.Schema,
            ast: Ast.Schema,
            fs: FileSystem.Schema,
            callGraph: CallGraph.Schema) = new Schema(builder, base, methodSchema, typeDecl, ast, fs, callGraph)

  class Schema(builder: SchemaBuilder,
               base: Base.Schema,
               methodSchema: Method.Schema,
               typeDeclSchema: Type.Schema,
               astSchema: Ast.Schema,
               fsSchema: FileSystem.Schema,
               callGraph: CallGraph.Schema) {

    import base._
    import methodSchema._
    import astSchema._
    import fsSchema._
    import callGraph._
    import typeDeclSchema._

    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    /*
     * Closure bindings
     * */

    val closureBindingId = builder
      .addProperty(
        name = "CLOSURE_BINDING_ID",
        valueType = ValueType.String,
        comment =
          "Identifier which uniquely describes a CLOSURE_BINDING. This property is used to match captured LOCAL nodes with the corresponding CLOSURE_BINDING nodes"
      )
      .protoId(50)

    val closureOriginalName = builder
      .addProperty(
        name = "CLOSURE_ORIGINAL_NAME",
        valueType = ValueType.String,
        comment = "The original name of the (potentially mangled) captured variable"
      )
      .protoId(159)

    // edge types
    val capture = builder
      .addEdgeType(
        name = "CAPTURE",
        comment = "Represents the capturing of a variable into a closure"
      )
      .protoId(40)

    // node types
    local.addProperties(closureBindingId)

    val closureBinding: NodeType = builder
      .addNodeType(
        name = "CLOSURE_BINDING",
        comment = "Represents the binding of a LOCAL or METHOD_PARAMETER_IN into the closure of a method"
      )
      .protoId(334)
      .addProperties(closureBindingId, evaluationStrategy, closureOriginalName)

    val capturedBy = builder
      .addEdgeType(
        name = "CAPTURED_BY",
        comment = "Connection between a captured LOCAL and the corresponding CLOSURE_BINDING"
      )
      .protoId(41)

    // node relations
    local.addOutEdge(edge = capturedBy, inNode = closureBinding)

    methodRef
      .addOutEdge(edge = capture, inNode = closureBinding)

    typeRef
      .addOutEdge(edge = capture, inNode = closureBinding)

    closureBinding
      .addOutEdge(edge = ref, inNode = local, cardinalityOut = EdgeType.Cardinality.One)
      .addOutEdge(edge = ref, inNode = methodParameterIn)

    /*
     * Dependencies
     * */

    val dependencyGroupId = builder
      .addProperty(
        name = "DEPENDENCY_GROUP_ID",
        valueType = ValueType.String,
        comment = "The group ID for a dependency"
      )
      .protoId(58)

    val usedIn = builder
      .addProperty(
        name = "USED_IN",
        valueType = ValueType.String,
        comment = "The name of the file for which this include is valid"
      )
      .protoId(22919)

    // node types
    val dependency: NodeType = builder
      .addNodeType(
        name = "DEPENDENCY",
        comment = "This node represents a dependency"
      )
      .protoId(35)
      .addProperties(version, name, dependencyGroupId, usedIn)

    /*
     * Type hints
     * */

    val dynamicTypeHintFullName = builder
      .addProperty(
        name = "DYNAMIC_TYPE_HINT_FULL_NAME",
        valueType = ValueType.String,
        comment = "Type hint for the dynamic type"
      )
      .asList()
      .protoId(1591)

    callNode.addProperties(dynamicTypeHintFullName)
    methodParameterIn.addProperties(dynamicTypeHintFullName)
    methodReturn.addProperties(dynamicTypeHintFullName)
    methodRef.addProperties(dynamicTypeHintFullName)
    typeRef.addProperties(dynamicTypeHintFullName)
    local.addProperties(dynamicTypeHintFullName)
    block.addProperties(dynamicTypeHintFullName)
    unknown.addProperties(dynamicTypeHintFullName)
    literal.addProperties(dynamicTypeHintFullName)
    identifier.addProperties(dynamicTypeHintFullName)
    member.addProperties(dynamicTypeHintFullName)

    /*
     * Declarative imports
     * */

    val importNode = builder
      .addNodeType(
        name = "IMPORT",
        comment = """Declarative import as it is found in statically typed languages like Java.
            |This kind of node is not supposed to be used for imports in dynamically typed
            |languages like Javascript.
            |""".stripMargin
      )
      .extendz(astNode)

    val importedEntity = builder
      .addProperty(
        name = "IMPORTED_ENTITY",
        valueType = ValueType.String,
        comment = """The identifying string of the imported entity.
            |For a Java import like "import java.nio.ByteBuffer;" this would be "java.nio.ByteBuffer".
            |""".stripMargin
      )

    val importedAs = builder
      .addProperty(
        name = "IMPORTED_AS",
        valueType = ValueType.String,
        comment = """The identifier under which the import can be accessed in the importing context.
            |For a Java import this is always identical to the class name. But e.g. for a
            |Kotlin import like "import java.nio.ByteBuffer as BBuffer" this would be "BBuffer".
            |This property is ignored if IS_WILDCARD is true.
            |""".stripMargin
      )

    val explicitAs = builder
      .addProperty(
        name = "EXPLICIT_AS",
        valueType = ValueType.Boolean,
        comment = """Specifies whether the IMPORTED_AS property was explicitly present in the code.
            |For languages like Java which do not allow a renaming during import this is
            |always false. For e.g. Kotlin it depends on the existence of the "as" keyword.
            |""".stripMargin
      )

    val isWildcard = builder
      .addProperty(
        name = "IS_WILDCARD",
        valueType = ValueType.Boolean,
        comment = """Specifies whether this is a wildcard import.
            |For a Java import like "import java.nio.*;" IS_WILDCARD would be "true" and
            |IMPORTED_ENTITY would be "java.nio".
            |For wildcard imports the IMPORTED_AS property is ignored.
            |""".stripMargin
      )

    val isExplicit = builder
      .addProperty(
        name = "EXPLICIT_IMPORT",
        valueType = ValueType.Boolean,
        comment = """Specifies whether this is an explicit import.
            |Most languages have implicit default imports of some standard library elements
            |and this flag is used to distinguish those from explicit imports found in the
            |code base.
            |""".stripMargin
      )

    importNode.addProperty(importedEntity)
    importNode.addProperty(importedAs)
    importNode.addProperty(explicitAs)
    importNode.addProperty(isWildcard)
    importNode.addProperty(isExplicit)

    block.addOutEdge(edge = ast, inNode = importNode)
    file.addOutEdge(edge = ast, inNode = importNode)
    typeDecl.addOutEdge(edge = ast, inNode = importNode)
  }

}
