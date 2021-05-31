package overflowdb.schema.testschema1

import java.io.File
import overflowdb.codegen.CodeGen
import overflowdb.schema.{Cardinality, Constant, SchemaBuilder, SchemaInfo}
import overflowdb.storage.ValueTypes

// TODO create integration test from this
object TestSchema1 extends App {
  val builder = new SchemaBuilder("io.shiftleft.codepropertygraph.generated")

  implicit val schemaInfo = SchemaInfo.forClass(getClass)

  // properties
  val name = builder
    .addProperty(
      name = "NAME",
      valueType = ValueTypes.STRING,
      cardinality = Cardinality.One,
      comment = "Name of represented object, e.g., method name (e.g. \"run\")"
    )
    .protoId(5)

  val order = builder
    .addProperty(
      name = "ORDER",
      valueType = ValueTypes.INTEGER,
      cardinality = Cardinality.One,
      comment =
        "General ordering property, such that the children of each AST-node are typically numbered from 1, ..., N (this is not enforced). The ordering has no technical meaning, but is used for pretty printing and OUGHT TO reflect order in the source code"
    )
    .protoId(4)

  val hash = builder
    .addProperty(
      name = "HASH",
      valueType = ValueTypes.STRING,
      cardinality = Cardinality.ZeroOrOne,
      comment = "Hash value of the artifact that this CPG is built from."
    )
    .protoId(120)

  val inheritsFromTypeFullName = builder
    .addProperty(
      name = "INHERITS_FROM_TYPE_FULL_NAME",
      valueType = ValueTypes.STRING,
      cardinality = Cardinality.List,
      comment =
        "The static types a TYPE_DECL inherits from. This property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for each TYPE_FULL_NAME"
    )
    .protoId(53)

  val alias = builder
    .addProperty(
      name = "ALIAS",
      valueType = ValueTypes.BOOLEAN,
      cardinality = Cardinality.One,
      comment = "Defines whether a PROPAGATE edge creates an alias"
    )
    .protoId(1)

  val localName = builder
    .addProperty(
      name = "LOCAL_NAME",
      valueType = ValueTypes.STRING,
      cardinality = Cardinality.ZeroOrOne,
      comment = "Local name of referenced CONTAINED node. This key is deprecated."
    )
    .protoId(6)

  val edgekey1Lst = builder
    .addProperty(
      name = "EDGEKEY_1_LST",
      valueType = ValueTypes.INTEGER,
      cardinality = Cardinality.List,
      comment = "test list edge key"
    )
    .protoId(6999)

  // edge types
  val ast = builder
    .addEdgeType(
      name = "AST",
      comment = "Syntax tree edge"
    )
    .protoId(3)

  // node base types
  val astNode = builder
    .addNodeBaseType(
      name = "AST_NODE",
      comment = "Any node that can exist in an abstract syntax tree"
    )
    .addProperties(order)

  // node types
  val namespaceBlock = builder
    .addNodeType(
      name = "NAMESPACE_BLOCK",
      comment = "A reference to a namespace"
    )
    .protoId(41)
    .addProperties(name, order)
    .extendz(astNode)

  val file = builder
    .addNodeType(
      name = "FILE",
      comment = "Node representing a source file - the root of the AST"
    )
    .protoId(38)
    .addProperties(name, hash, inheritsFromTypeFullName, order)
    .extendz(astNode)
    .addOutEdge(
      edge = ast,
      inNode = namespaceBlock,
      cardinalityOut = Cardinality.List,
      cardinalityIn = Cardinality.ZeroOrOne
    )
    .addContainedNode(namespaceBlock, "tags", Cardinality.List)

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
    ).protoId(2)
  )

  val frameworks = builder.addConstants(
    category = "Frameworks",
    Constant(name = "SPRING", value = "SPRING", valueType = ValueTypes.STRING, comment = "Java spring framework").protoId(3),
    Constant(name = "ASP_NET_MVC", value = "ASP_NET_MVC", valueType = ValueTypes.STRING, comment = "Microsoft ASP.NET MVC")
      .protoId(11),
    Constant(name = "JAXWS", value = "JAXWS", valueType = ValueTypes.STRING, comment = "JAX-WS").protoId(12),
    Constant(
      name = "JAVA_INTERNAL",
      value = "JAVA_INTERNAL",
      valueType = ValueTypes.STRING,
      comment = "Framework facilities directly provided by Java"
    ).protoId(14),
    Constant(
      name = "ASP_NET_WEB_UI",
      value = "ASP_NET_WEB_UI",
      valueType = ValueTypes.STRING,
      comment = "Microsoft ASP.NET Web UI"
    ).protoId(13),
    Constant(name = "JAXRS", value = "JAXRS", valueType = ValueTypes.STRING, comment = "JAX-RS").protoId(7),
    Constant(name = "DROPWIZARD", value = "DROPWIZARD", valueType = ValueTypes.STRING, comment = "Dropwizard framework")
      .protoId(15),
    Constant(name = "PLAY", value = "PLAY", valueType = ValueTypes.STRING, comment = "Play framework").protoId(1),
    Constant(name = "SPARK", value = "SPARK", valueType = ValueTypes.STRING, comment = "Spark micro web framework").protoId(8),
    Constant(name = "VERTX", value = "VERTX", valueType = ValueTypes.STRING, comment = "Polyglot event-driven framework")
      .protoId(4),
    Constant(name = "JSF", value = "JSF", valueType = ValueTypes.STRING, comment = "JavaServer Faces").protoId(5),
    Constant(
      name = "ASP_NET_WEB_API",
      value = "ASP_NET_WEB_API",
      valueType = ValueTypes.STRING,
      comment = "Microsoft ASP.NET Web API"
    ).protoId(10),
    Constant(name = "WCF", value = "WCF", valueType = ValueTypes.STRING, comment = "WCF HTTP and REST").protoId(16),
    Constant(name = "GWT", value = "GWT", valueType = ValueTypes.STRING, comment = "Google web toolkit").protoId(2),
    Constant(name = "SERVLET", value = "SERVLET", valueType = ValueTypes.STRING, comment = "Java Servlet based frameworks")
      .protoId(6),
    Constant(name = "ASP_NET_CORE", value = "ASP_NET_CORE", valueType = ValueTypes.STRING, comment = "Microsoft ASP.NET Core")
      .protoId(9)
  )

  val languages = builder.addConstants(
    category = "Languages",
    Constant(name = "JAVA", value = "JAVA", valueType = ValueTypes.STRING, comment = "").protoId(1),
    Constant(name = "JAVASCRIPT", value = "JAVASCRIPT", valueType = ValueTypes.STRING, comment = "").protoId(2),
    Constant(name = "GOLANG", value = "GOLANG", valueType = ValueTypes.STRING, comment = "").protoId(3),
    Constant(name = "CSHARP", value = "CSHARP", valueType = ValueTypes.STRING, comment = "").protoId(4),
    Constant(name = "C", value = "C", valueType = ValueTypes.STRING, comment = "").protoId(5),
    Constant(name = "PYTHON", value = "PYTHON", valueType = ValueTypes.STRING, comment = "").protoId(6),
    Constant(name = "LLVM", value = "LLVM", valueType = ValueTypes.STRING, comment = "").protoId(7),
    Constant(name = "PHP", value = "PHP", valueType = ValueTypes.STRING, comment = "").protoId(8)
  )

  val modifierTypes = builder.addConstants(
    category = "ModifierTypes",
    Constant(name = "STATIC", value = "STATIC", valueType = ValueTypes.STRING, comment = "The static modifier").protoId(1),
    Constant(name = "PUBLIC", value = "PUBLIC", valueType = ValueTypes.STRING, comment = "The public modifier").protoId(2),
    Constant(name = "PROTECTED", value = "PROTECTED", valueType = ValueTypes.STRING, comment = "The protected modifier")
      .protoId(3),
    Constant(name = "PRIVATE", value = "PRIVATE", valueType = ValueTypes.STRING, comment = "The private modifier").protoId(4),
    Constant(name = "ABSTRACT", value = "ABSTRACT", valueType = ValueTypes.STRING, comment = "The abstract modifier").protoId(5),
    Constant(name = "NATIVE", value = "NATIVE", valueType = ValueTypes.STRING, comment = "The native modifier").protoId(6),
    Constant(name = "CONSTRUCTOR", value = "CONSTRUCTOR", valueType = ValueTypes.STRING, comment = "The constructor modifier")
      .protoId(7),
    Constant(name = "VIRTUAL", value = "VIRTUAL", valueType = ValueTypes.STRING, comment = "The virtual modifier").protoId(8)
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
    ).protoId(3)
  )

  val operators = builder.addConstants(
    category = "Operators",
    Constant(name = "addition", value = "<operator>.addition", valueType = ValueTypes.STRING, comment = ""),
    Constant(
      name = "pointerShift",
      value = "<operator>.pointerShift",
      valueType = ValueTypes.STRING,
      comment =
        "Shifts a pointer. In terms of CPG, the first argument is the pointer and the second argument is the index. The index selection works the same way as for indirectIndexAccess. This operator is currently only used directly by the LLVM language, but it is also used internally for C. For example, pointerShift(ptr, 7) is equivalent to &(ptr[7]). Handling of this operator is special-cased in the back-end"
    )
  )

  new CodeGen(builder.build).run(new File("target"))
}
