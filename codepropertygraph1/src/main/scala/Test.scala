import java.util.{Arrays, UUID}

import io.shiftleft.codepropertygraph.generated.edges.Ast
import org.apache.tinkerpop.gremlin.tinkergraph.structure.{EdgeRef, TinkerGraph, VertexRef}
import io.shiftleft.codepropertygraph.generated.nodes.Method
import org.apache.tinkerpop.gremlin.structure.{T, Vertex}

import scala.collection.JavaConverters._

// `-XX:+UseG1GC -Xms256m -Xmx256m -XX:+HeapDumpOnOutOfMemoryError`
object Test extends App {
  val vertexCount = 1000000
  val configuration = TinkerGraph.EMPTY_CONFIGURATION
  configuration.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_ONDISK_OVERFLOW_ENABLED, true)
  Method.Factory
  val graph = TinkerGraph.open(configuration, Arrays.asList(Method.Factory), Arrays.asList())

  var i = 0
  while (i <= vertexCount) {
    if (i % 50000 == 0) {
      System.out.println(i + " vertices created")
    }
    val v = graph.addVertex(T.label, Method.Label)
    v.property(Method.Keys.FullName, UUID.randomUUID().toString())
    v.property(Method.Keys.Name, UUID.randomUUID().toString())
    i += 1
  }
  graph.close()

}

// `-XX:+UseG1GC -Xms256m -Xmx256m -XX:+HeapDumpOnOutOfMemoryError`
object Test2 extends App {
  val elementCount = 1000000
  val configuration = TinkerGraph.EMPTY_CONFIGURATION
  configuration.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_ONDISK_OVERFLOW_ENABLED, true)
  Method.Factory
  val graph = TinkerGraph.open(configuration, Arrays.asList(Method.Factory), Arrays.asList(Ast.Factory))

  var i = 0
  while (i <= elementCount) {
    if (i % 50000 == 0) {
      System.out.println(i + " elements created; cleared: " + graph.edges().asScala.filter(_.asInstanceOf[EdgeRef[_]].isCleared).size)
//      System.out.println(i + " elements created; cleared")
      //      Thread.sleep(1000) // in lieu of other application usage
    }
    val v0 = graph.addVertex(T.label, Method.Label)
    val v1 = graph.addVertex(T.label, Method.Label)
    (1 to 3).foreach { i =>
      val e = v0.addEdge(Ast.Label, v1)
      e.property("TEST", UUID.randomUUID().toString())
    }
    i += 5
  }
  graph.close()
}

// `-XX:+UseG1GC -Xms256m -Xmx256m -XX:+HeapDumpOnOutOfMemoryError`
object Test3 extends App {
  val edgeCount = 1000000
  val configuration = TinkerGraph.EMPTY_CONFIGURATION
  configuration.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_ONDISK_OVERFLOW_ENABLED, true)
  Method.Factory
  val graph = TinkerGraph.open(configuration, Arrays.asList(Method.Factory), Arrays.asList(Ast.Factory))

  val v0 = graph.addVertex(T.label, Method.Label)
  val v1 = graph.addVertex(T.label, Method.Label)

  var i = 0
  while (i <= edgeCount) {
    if (i % 50000 == 0) {
      System.out.println(i + " edges created; vertex serialization count: " + (v0.asInstanceOf[VertexRef[_]].getSerializationCount + v1.asInstanceOf[VertexRef[_]].getSerializationCount))
      //      Thread.sleep(1000) // in lieu of other application usage
    }
    val e = v0.addEdge(Ast.Label, v1)
    e.property("TEST", UUID.randomUUID().toString())
    i += 1
  }
  graph.close()
}
