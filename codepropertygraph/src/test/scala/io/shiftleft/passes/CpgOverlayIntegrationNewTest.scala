package io.shiftleft.passes

import io.shiftleft.OverflowDbTestInstance
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.codepropertygraph.generated.edges.ReachingDef
import io.shiftleft.codepropertygraph.generated.nodes.{
  Identifier,
  MethodParameterIn,
  NewIdentifier,
  NewUnknown,
  StoredNode
}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb._
import overflowdb.traversal._

import java.util.Optional

class CpgOverlayIntegrationNewTest extends AnyWordSpec with Matchers {
  val InitialNodeCode  = "initialNode"
  val Pass1NewNodeCode = "pass1NewNodeCode"
  val Pass2NewNodeCode = "pass2NewNodeCode"

  "SimpleCpgPass being serialised to and from overlay protobuf via DiffGraph" in {
    withNewBaseCpg { cpg =>
      val initialNode = cpg.graph.V.has(Properties.CODE -> InitialNodeCode).head.asInstanceOf[StoredNode]
      val pass1       = passAddsEdgeTo(initialNode, Pass1NewNodeCode, cpg)

      pass1.createAndApply()
      cpg.graph.nodeCount shouldBe 2

      initialNode.out.property(Properties.CODE).toList shouldBe List(Pass1NewNodeCode)

      val pass1NewNode = cpg.graph.V.has(Properties.CODE -> Pass1NewNodeCode).head.asInstanceOf[StoredNode]
      val pass2        = passAddsEdgeTo(pass1NewNode, Pass2NewNodeCode, cpg)

      pass2.createAndApply()
      cpg.graph.nodeCount shouldBe 3
      pass1NewNode.out.property(Properties.CODE).toList shouldBe List(Pass2NewNodeCode)
    }
  }

  "apply cpg pass, serialize the inverse DiffGraph, and apply the inverse to undo" in {
    withNewBaseCpg { (cpg: Cpg) =>
      cpg.graph.nodeCount shouldBe 1
      val initialNode = cpg.graph.V.has(Properties.CODE, InitialNodeCode).head.asInstanceOf[StoredNode]

      // 1) add a new node
      val addNodeInverse = applyDiffAndGetInverse(cpg)(_.addNode(NewIdentifier().code(null)))
      cpg.graph.nodeCount shouldBe 2
      val additionalNode = cpg.graph.V
        .label(Identifier.Label)
        .collectFirst {
          case mpe: Identifier if mpe.code == null => mpe
        }
        .get
        .asInstanceOf[StoredNode]

      // 2) add two edges with the same label but different properties (they'll later be disambiguated by their property hash, since edges don't have IDs
      val addEdge1Inverse = applyDiffAndGetInverse(cpg)({
        _.addEdge(initialNode, additionalNode, ReachingDef.Label, ReachingDef.PropertyNames.Variable, "true")
      })
      val addEdge2Inverse = applyDiffAndGetInverse(cpg)({
        _.addEdge(initialNode, additionalNode, ReachingDef.Label, ReachingDef.PropertyNames.Variable, "false")
      })
      def initialNodeOutEdges = initialNode.outE.toList
      initialNodeOutEdges.size shouldBe 2

      // 3) add node property
      val addNodePropertyInverse =
        applyDiffAndGetInverse(cpg)(_.setNodeProperty(additionalNode, PropertyNames.CODE, "Node2Code"))
      additionalNode.property(Properties.CODE) shouldBe "Node2Code"

      // TODO 4) add edge property - not needed for now?
//      val addEdgePropertyInverse = applyDiffAndGetInverse(cpg)(_.addEdgeProperty(initialNodeOutEdges.head, PropertyNames.ALIAS, true))
//      initialNode.start.outE.value(PropertyNames.ALIAS).toList shouldBe List(1)

      // now apply all inverse diffgraphs in the reverse order...
      // TODO 4) remove edge property - not needed for now?
//      initialNode.start.outE.value(PropertyNames.ALIAS).toList shouldBe List.empty

      // 3) remove node property
      overflowdb.BatchedUpdate.applyDiff(cpg.graph, addNodePropertyInverse, null, null)
      additionalNode.propertyOption(Properties.CODE) shouldBe Optional.empty

      // 2) remove edges - they don't have ids and are therefor disambiguated by their property hash
      overflowdb.BatchedUpdate.applyDiff(cpg.graph, addEdge2Inverse, null, null)

      initialNodeOutEdges.size shouldBe 1
      initialNode.outE.property(Properties.VARIABLE).toList shouldBe List("true")
      overflowdb.BatchedUpdate.applyDiff(cpg.graph, addEdge1Inverse, null, null)

      initialNodeOutEdges.size shouldBe 0

      // 1) remove node
      overflowdb.BatchedUpdate.applyDiff(cpg.graph, addNodeInverse, null, null)
      cpg.graph.nodeCount shouldBe 1
    }
  }

  /* like a freshly deserialized cpg.bin.zip without any overlays applied */
  def withNewBaseCpg[T](fun: Cpg => T): T = {
    val graph = OverflowDbTestInstance.create
    graph + (MethodParameterIn.Label, MethodParameterIn.Properties.Code -> InitialNodeCode)
    val cpg = Cpg(graph)
    try fun(cpg)
    finally cpg.close()
  }

  /* applies DiffGraph, gets the inverse, serializes and deserializes the inverse (to verify round-trip works) */
  def applyDiffAndGetInverse(
    cpg: Cpg
  )(fun: overflowdb.BatchedUpdate.DiffGraphBuilder => Unit): overflowdb.BatchedUpdate.DiffGraph = {
    val builder = new overflowdb.BatchedUpdate.DiffGraphBuilder
    fun(builder)
    val diff     = builder.build()
    val listener = new BatchUpdateInverseListener
    overflowdb.BatchedUpdate.applyDiff(cpg.graph, diff, null, listener)
    val inverse = listener.getSerialization()
    DiffGraphProtoSerializer.deserialize(inverse, cpg.graph).build()
  }

  def passAddsEdgeTo(from: StoredNode, propValue: String, cpg: Cpg): SimpleCpgPass = {
    val newNode = NewUnknown().code(propValue)
    new SimpleCpgPass(cpg) {
      override def run(dstGraph: DiffGraphBuilder): Unit = {
        dstGraph.addNode(newNode)
        dstGraph.addEdge(from, newNode, EdgeTypes.AST)
      }
    }
  }

}
