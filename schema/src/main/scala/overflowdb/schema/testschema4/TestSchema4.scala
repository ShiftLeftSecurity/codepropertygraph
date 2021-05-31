package overflowdb.schema.testschema4

import overflowdb.codegen.CodeGen
import overflowdb.schema.{SchemaBuilder, SchemaInfo}

import java.io.File

// TODO create integration test from this
object TestSchema4 extends App {
  val builder = new SchemaBuilder("io.shiftleft.codepropertygraph.generated")

  implicit val schemaInfo = SchemaInfo.forClass(getClass)

  val edge1 = builder.addEdgeType("EDGE_1")
  val rootNode1 = builder.addNodeBaseType("ROOT_NODE_1")
  val rootNode2 = builder.addNodeBaseType("ROOT_NODE_2")
  rootNode1.addOutEdge(edge1, inNode = rootNode2)

  val node1 = builder.addNodeType("NODE_1").extendz(rootNode1)
  val node2 = builder.addNodeType("NODE_2").extendz(rootNode2)

  val schema = builder.build
  // make some brief verifications
  val node1Final = schema.nodeTypes.find(_.name == node1.name).get
  assert(node1Final.outEdges.size == 1)

  new CodeGen(schema).run(new File("target"))
}
