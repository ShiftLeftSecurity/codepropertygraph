package overflowdb.schema.testschema2

import java.io.File
import overflowdb.codegen.CodeGen
import overflowdb.schema.{Cardinality, Constant, SchemaBuilder}
import overflowdb.storage.ValueTypes

// TODO create integration test from this
object TestSchema2 extends App {
  val builder = new SchemaBuilder("io.shiftleft.codepropertygraph.generated")
  val base = Base(builder)
  val javaSpecific = JavaSpecific(builder, base)
  new CodeGen(builder.build).run(new File("target"))
}

object Base {
  def apply(builder: SchemaBuilder) = new Schema(builder)
  class Schema(builder: SchemaBuilder) {

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

    val localName = builder
      .addProperty(
        name = "LOCAL_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.ZeroOrOne,
        comment = "Local name of referenced CONTAINED node. This key is deprecated."
      )
      .protoId(6)

    // edge types
    val ast = builder.addEdgeType("AST", "Syntax tree edge")

    // node base types
    val astNode = builder
      .addNodeBaseType("AST_NODE", "Any node that can exist in an abstract syntax tree")
      .addProperties(order)

    // node types
    val namespaceBlock = builder
      .addNodeType("NAMESPACE_BLOCK", "A reference to a namespace")
      .protoId(41)
      .extendz(astNode)

    val file = builder
      .addNodeType("FILE", "Node representing a source file - the root of the AST")
      .protoId(38)
      .extendz(astNode)
    //    .addProperties(name, order)
    //    .addOutEdge(ast, InNode(namespaceBlock, "0-1:n"))

    val dispatchTypes = builder.addConstants(
      category = "DispatchTypes",
      Constant(
        name = "STATIC_DISPATCH",
        value = "STATIC_DISPATCH",
        valueType = ValueTypes.STRING,
        comment = "For statically dispatched calls the call target is known before program execution"
      ),
      Constant(
        name = "DYNAMIC_DISPATCH",
        value = "DYNAMIC_DISPATCH",
        valueType = ValueTypes.STRING,
        comment = "For dynamically dispatched calls the target is determined during runtime "
      )
    )

    val operators = builder.addConstants(
      category = "Operators",
      Constant(name = "addition", value = "<operator>.addition", valueType = ValueTypes.STRING),
      Constant(
        name = "pointerShift",
        value = "<operator>.pointerShift",
        valueType = ValueTypes.STRING,
        comment =
          "Shifts a pointer. In terms of CPG, the first argument is the pointer and the second argument is the index. The index selection works the same way as for indirectIndexAccess. This operator is currently only used directly by the LLVM language, but it is also used internally for C. For example, pointerShift(ptr, 7) is equivalent to &(ptr[7]). Handling of this operator is special-cased in the back-end"
      )
    )
  }
}

object JavaSpecific {
  def apply(builder: SchemaBuilder, base: Base.Schema) = new Schema(builder, base)
  class Schema(builder: SchemaBuilder, base: Base.Schema) {
    val annotation = builder
      .addNodeType(name = "ANNOTATION", "A method annotation")
      .protoId(5)
      .extendz(base.astNode)
      .addProperties(base.name)
  }

}
