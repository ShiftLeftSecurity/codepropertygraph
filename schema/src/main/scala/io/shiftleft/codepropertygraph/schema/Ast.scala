package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, Constant, NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object Ast extends SchemaBase {

  def index: Int = 7
  override def providedByFrontend: Boolean = true

  override def description: String =
    """
      |The Abstract Syntax Tree (AST) Layer provides syntax trees for all compilation units.
      |Source-based frontends MUST generate this layer, leaving subsequent generation of
      |the control flow graph to the backend. Machine-code- and Bytecode-based
      |MAY choose to skip generating this layer, in which case the frontend must generate
      |the control flow layer.
      |""".stripMargin

  def apply(builder: SchemaBuilder,
            base: Base.Schema,
            namespaces: Namespace.Schema,
            methodSchema: Method.Schema,
            typeSchema: Type.Schema,
            fs: FileSystem.Schema) =
    new Schema(builder, base, namespaces, methodSchema, typeSchema, fs)

  class Schema(builder: SchemaBuilder,
               base: Base.Schema,
               namespaces: Namespace.Schema,
               methodSchema: Method.Schema,
               typeSchema: Type.Schema,
               fs: FileSystem.Schema) {
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)
    import methodSchema._
    import base._
    import namespaces._
    import typeSchema._
    import fs._

    // Base types

    val astNode = builder
      .addNodeBaseType(
        name = "AST_NODE",
        comment = """This is the base type for all nodes of the abstract syntax tree.
                    |In a syntax tree, sibling nodes (nodes who share a parent node) are ordered.
                    |As some graph databases do not guarantee the order in which siblings are returned to
                    |be stable, we make the ordering explicit by storing the position of each node relative
                    |to its siblings in the order field. In the left most sibling, order is set to 0,
                    |while in the right-most sibling, it is set to n-1 where n is the number of siblings.
                    |""".stripMargin
      )
      .addProperties(order, code)
      .addProperties(lineNumber, columnNumber)

    val callRepr = builder
      .addNodeBaseType(
        name = "CALL_REPR",
        comment = "A base class for nodes that represent different types of calls"
      )
      .addProperties(name, signature)

    // Method-related nodes that are part of the AST

    method.extendz(astNode)
    methodParameterIn.extendz(astNode)
    methodParameterOut.extendz(astNode)

    // Type-related nodes that are part of the AST

    val methodFullName = builder
      .addProperty(
        name = "METHOD_FULL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """The FULL_NAME of a method. Used to link CALL and METHOD nodes. It is required
                    |to have exactly one METHOD node for each METHOD_FULL_NAME""".stripMargin
      )
      .protoId(54)

    val expression = builder
      .addNodeBaseType(
        name = "EXPRESSION",
        comment = "Expression as a specialisation of tracking point"
      )

    val block: NodeType = builder
      .addNodeType(
        name = "BLOCK",
        comment = """A compound statement. Compound statements are used in many languages to allow
                    |grouping a sequence of statements. For example, in C and Java, compound statements
                    |are statements enclosed by curly braces. Function/Method bodies are compound
                    |statements. We do not use the term "compound statement" because "statement" would
                    |imply that the block does not yield a value upon evaluation, that is, that it is
                    |not an expression. This is true in languages such as C and Java, but not for languages
                    |such as Scala where the value of the block is given by that of the last expression it
                    |contains. In fact, the Scala grammar uses the term "BlockExpr" (short for
                    |"block expression") to describe what in the CPG spec we just call "Block".
                    |""".stripMargin
      )
      .protoId(31)
      .addProperties(typeFullName)
      .extendz(expression)

    val literal: NodeType = builder
      .addNodeType(
        name = "LITERAL",
        comment = "Literal/Constant"
      )
      .protoId(8)
      .addProperties(typeFullName)
      .extendz(expression)

    val local: NodeType = builder
      .addNodeType(
        name = "LOCAL",
        comment = "This node represents a local variable."
      )
      .protoId(23)
      .addProperties(typeFullName)
      .extendz(declaration, astNode)

    val callNode: NodeType = builder
      .addNodeType(
        name = "CALL",
        comment = """A (function/method) call. The `methodFullName` property is the name of the
                    |invoked method (the callee) while the `typeFullName` is its return type, and
                    |therefore, the return type of the call when viewing it as an expression. For
                    |languages like Javascript, it is common that we may know the (short-) name
                    |of the invoked method, but we do not know at compile time which method
                    |will actually be invoked, e.g., because it depends on a dynamic import.
                    |In this case, we leave `methodFullName` blank but at least fill out `name`,
                    |which contains the method's (short-) name and `signature`, which contains
                    |any information we may have about the types of arguments and return value.
                    |""".stripMargin
      )
      .protoId(15)
      .extendz(callRepr, expression)
      .addProperties(methodFullName, typeFullName)

    val identifier: NodeType = builder
      .addNodeType(
        name = "IDENTIFIER",
        comment = "An arbitrary identifier/reference"
      )
      .protoId(27)
      .addProperties(typeFullName, name)
      .extendz(expression)

    val canonicalName = builder
      .addProperty(
        name = "CANONICAL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = """Canonical token of a FIELD_IDENTIFIER. Typically identical to the CODE field,
                    |but canonicalized according to source language semantics. Human readable names
                    |are preferable. FIELD_IDENTIFIERs must share identical CANONICAL_NAME if and
                    |only if they alias, e.g. in C-style unions (if the aliasing relationship is
                    |unknown or there are partial overlaps, then one must make a reasonable guess,
                    |and trade off between false negatives and false positives)
                    |""".stripMargin
      )
      .protoId(2001092)

    val fieldIdentifier: NodeType = builder
      .addNodeType(
        name = "FIELD_IDENTIFIER",
        comment = """A node that represents which field is accessed in a <operator>.fieldAccess, in
                    |e.g. obj.field. The CODE part is used for human display and matching to MEMBER nodes.
                    |The CANONICAL_NAME is used for dataflow tracking; typically both coincide.
                    |However, suppose that two fields foo and bar are a C-style union; then CODE refers
                    |to whatever the programmer wrote (obj.foo or obj.bar), but both share the same
                    |CANONICAL_NAME (e.g. GENERATED_foo_bar)
                    |""".stripMargin
      )
      .protoId(2001081)
      .addProperties(canonicalName)
      .extendz(expression)

    val modifierType = builder
      .addProperty(
        name = "MODIFIER_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "Indicates the modifier which is represented by a MODIFIER node. See modifierTypes"
      )
      .protoId(26)

    val modifier: NodeType = builder
      .addNodeType(
        name = "MODIFIER",
        comment = "A modifier, e.g., static, public, private"
      )
      .protoId(300)
      .addProperties(modifierType)
      .extendz(astNode)

    val jumpTarget: NodeType = builder
      .addNodeType(
        name = "JUMP_TARGET",
        comment = "A jump target made explicit in the code using a label"
      )
      .protoId(340)
      .addProperties(name, parserTypeName)
      .extendz(astNode)

    val methodInst: NodeType = builder
      .addNodeType(
        name = "METHOD_INST",
        comment = """A method instance which always has to reference a method and may have type
                    |argument children if the referred to method is a template
                    |""".stripMargin
      )
      .protoId(32)
      .addProperties(name, signature, fullName, methodFullName)
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

    val ret: NodeType = builder
      .addNodeType(
        name = "RETURN",
        comment = "A return instruction"
      )
      .protoId(30)
      .extendz(expression)

    val controlStructureType = builder
      .addProperty(
        name = "CONTROL_STRUCTURE_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = "Indicates the control structure type. See controlStructureTypes"
      )
      .protoId(27)

    val controlStructure: NodeType = builder
      .addNodeType(
        name = "CONTROL_STRUCTURE",
        comment = "A control structure such as if, while, or for"
      )
      .protoId(339)
      .addProperties(parserTypeName, controlStructureType)
      .extendz(expression)

    val unknown: NodeType = builder
      .addNodeType(
        name = "UNKNOWN",
        comment = "A language-specific node"
      )
      .protoId(44)
      .addProperties(parserTypeName, typeFullName)
      .extendz(expression)

    typeDecl.extendz(astNode)
    member.extendz(astNode)
    typeParameter.extendz(astNode)
    typeArgument.extendz(astNode)
    namespaceBlock.extendz(astNode)
    expression.extendz(astNode)

    // Edge types

    val ast = builder
      .addEdgeType(
        name = "AST",
        comment = "This edge connects a parent node to its child in the syntax tree."
      )
      .protoId(3)

    val condition = builder
      .addEdgeType(
        name = "CONDITION",
        comment = "Edge from control structure node to the expression that holds the condition"
      )
      .protoId(56)

    file
      .addOutEdge(edge = ast, inNode = namespaceBlock, cardinalityIn = Cardinality.ZeroOrOne)

    file
      .extendz(astNode)

    member.addOutEdge(edge = ast, inNode = modifier)
    tpe.addOutEdge(edge = ast, inNode = typeArgument)

    typeDecl
      .addOutEdge(edge = ast, inNode = typeParameter)
      .addOutEdge(edge = ast, inNode = member, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = modifier, cardinalityIn = Cardinality.One)

    method
      .addOutEdge(edge = ast, inNode = methodReturn, cardinalityOut = Cardinality.One, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = methodParameterIn, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = modifier, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = block, cardinalityOut = Cardinality.One, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = typeParameter, cardinalityIn = Cardinality.One)

    callNode
      .addOutEdge(edge = ast, inNode = callNode)
      .addOutEdge(edge = ast, inNode = identifier)
      .addOutEdge(edge = ast, inNode = fieldIdentifier, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = literal)
      .addOutEdge(edge = ast, inNode = methodRef)
      .addOutEdge(edge = ast, inNode = typeRef)
      .addOutEdge(edge = ast, inNode = ret)
      .addOutEdge(edge = ast, inNode = block)
      .addOutEdge(edge = ast, inNode = controlStructure)

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

    controlStructure
      .addOutEdge(edge = ast, inNode = literal, cardinalityIn = Cardinality.One)
      .addOutEdge(edge = ast, inNode = modifier)
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

    unknown
      .addOutEdge(edge = ast, inNode = literal)
      .addOutEdge(edge = ast, inNode = modifier)
      .addOutEdge(edge = ast, inNode = callNode)
      .addOutEdge(edge = ast, inNode = local)
      .addOutEdge(edge = ast, inNode = identifier)
      .addOutEdge(edge = ast, inNode = fieldIdentifier)
      .addOutEdge(edge = ast, inNode = ret)
      .addOutEdge(edge = ast, inNode = block)
      .addOutEdge(edge = ast, inNode = jumpTarget)
      .addOutEdge(edge = ast, inNode = unknown)
      .addOutEdge(edge = ast, inNode = controlStructure)

    methodInst
      .addOutEdge(edge = ast, inNode = typeArgument)

    unknown
      .addOutEdge(edge = ast, inNode = member)

    methodParameterIn.addOutEdge(edge = ast, inNode = unknown)

    namespaceBlock
      .addOutEdge(edge = ast, inNode = typeDecl, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = method, cardinalityIn = Cardinality.ZeroOrOne)

    namespace.extendz(astNode)

    controlStructure
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

    typeDecl
      .addOutEdge(edge = ast, inNode = typeDecl, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = method, cardinalityIn = Cardinality.ZeroOrOne)

    method
      .addOutEdge(edge = ast, inNode = methodParameterOut)
      .addOutEdge(edge = ast, inNode = typeDecl, cardinalityIn = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ast, inNode = method, cardinalityIn = Cardinality.ZeroOrOne)

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

    // To refactor

    identifier
      .addOutEdge(edge = ref, inNode = local, cardinalityOut = Cardinality.ZeroOrOne)
      .addOutEdge(edge = ref, inNode = methodParameterIn, cardinalityOut = Cardinality.ZeroOrOne)

    namespaceBlock
      .addOutEdge(edge = ref, inNode = namespace)

  }

}
