package overflowdb.schema.testschema3

import overflowdb.codegen.CodeGen
import overflowdb.schema.{Cardinality, SchemaBuilder}

import java.io.File

// TODO create integration test from this
object TestSchema3 extends App {
  val builder = new SchemaBuilder("io.shiftleft.codepropertygraph.generated")

  // node types
  val outer = builder.addNodeType("OUTER", "outer node")
  val inner = builder.addNodeType("INNER", "contained inner node")
  outer.addContainedNode(inner, "innerFoo", Cardinality.ISeq)

  new CodeGen(builder.build).run(new File("target"))
}
