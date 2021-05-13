package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Cardinality, Constant, NodeType, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

object MethodBody extends SchemaBase {

  def index: Int = 4
  override def providedByFrontend: Boolean = true
  override def description: String =
    """
      |""".stripMargin

  def apply(builder: SchemaBuilder, base: Base.Schema, methodSchema: Method.Schema, typeDecl: TypeDecl.Schema) =
    new Schema(builder, base, methodSchema, typeDecl)

  class Schema(builder: SchemaBuilder, base: Base.Schema, methodSchema: Method.Schema, typeDecl: TypeDecl.Schema) {
    import base._
    import methodSchema._
    import typeDecl._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

    val argumentIndex = builder
      .addProperty(
        name = "ARGUMENT_INDEX",
        valueType = ValueTypes.INTEGER,
        cardinality = Cardinality.One,
        comment = """AST-children of CALL nodes have an argument index, that is used to match
                    |call-site arguments with callee parameters. Explicit parameters are numbered
                    |from 1 to N, while index 0 is reserved for implicit self / this parameter.
                    |CALLs without implicit parameter therefore have arguments starting with index 1.
                    |AST-children of BLOCK nodes may have an argument index as well; in this case,
                    |the last argument index determines the return-value of a BLOCK expression
                    |""".stripMargin
      )
      .protoId(40)

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

    val receiver = builder
      .addEdgeType(
        name = "RECEIVER",
        comment = "The receiver of a method call which is either an object or a pointer"
      )
      .protoId(55)

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
        comment = "A base class for nodes that represent different types of calls"
      )
      .addProperties(name, signature)
      .extendz(cfgNode)

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
                    |contains. In fact, the Scala grammar uses the term \"BlockExpr\" (short for
                    |\"block expression\") to describe what in the CPG spec we just call \"Block\".
                    |""".stripMargin
      )
      .protoId(31)
      .addProperties(typeFullName)
      .extendz(expression)

    val local: NodeType = builder
      .addNodeType(
        name = "LOCAL",
        comment = "A local variable"
      )
      .protoId(23)
      .addProperties(code, typeFullName, lineNumber, columnNumber)
      .extendz(declaration, localLike, astNode)

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
      .addProperties(methodFullName, typeFullName)
      .extendz(expression, callRepr)

    val identifier: NodeType = builder
      .addNodeType(
        name = "IDENTIFIER",
        comment = "An arbitrary identifier/reference"
      )
      .protoId(27)
      .addProperties(typeFullName)
      .extendz(expression, localLike)

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

    val jumpTarget: NodeType = builder
      .addNodeType(
        name = "JUMP_TARGET",
        comment = "A jump target made explicit in the code using a label"
      )
      .protoId(340)
      .addProperties(name, parserTypeName, argumentIndex)
      .extendz(cfgNode, astNode)

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

    val unknown: NodeType = builder
      .addNodeType(
        name = "UNKNOWN",
        comment = "A language-specific node"
      )
      .protoId(44)
      .addProperties(parserTypeName, typeFullName)
      .extendz(expression)

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

    method
      .addOutEdge(edge = ast, inNode = block, cardinalityOut = Cardinality.One, cardinalityIn = Cardinality.One)
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

    methodParameterIn.addOutEdge(edge = ast, inNode = unknown)

    identifier
      .addOutEdge(edge = ref, inNode = methodParameterIn, cardinalityOut = Cardinality.ZeroOrOne)
      .addOutEdge(edge = cfg, inNode = methodReturn)

    ret.addOutEdge(edge = cfg,
                   inNode = methodReturn,
                   cardinalityOut = Cardinality.One,
                   cardinalityIn = Cardinality.ZeroOrOne)

    methodRef
      .addOutEdge(edge = cfg, inNode = methodReturn)

    typeRef
      .addOutEdge(edge = cfg, inNode = methodReturn)

    methodInst
      .addOutEdge(edge = ast, inNode = typeArgument)

    unknown
      .addOutEdge(edge = ast, inNode = member)

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
