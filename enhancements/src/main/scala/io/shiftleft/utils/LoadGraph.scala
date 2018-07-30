package io.shiftleft.utils

import io.shiftleft.queryprimitives.CpgLoader
import gremlin.scala._
import io.shiftleft.cpgenhancements.CpgEnhancements
import io.shiftleft.queryprimitives.CpgInputFormat
import io.shiftleft.queryprimitives.steps._
import io.shiftleft.codepropertygraph.generated.NodeTypes
import io.shiftleft.codepropertygraph.generated.nodes
import org.apache.tinkerpop.gremlin.structure.io.IoCore
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph

/* helper util for manual testing */
object LoadGraph extends App {
  val filename = args.head
  println(s"loading cpg from $filename")
  val cpg = CpgLoader.loadCodePropertyGraph(filename, CpgInputFormat.PROTOZIP)
  new CpgEnhancements().run(cpg.graph)
  // val graph = TinkerGraph.open()
  // nodes.Utils.registerVertexFactories(graph)
  // edges.Utils.registerEdgeFactories(graph)
  // graph.io(IoCore.graphml()).readGraph(filename)

  // println("done. running GC")
  // System.gc()
  // println("done. sleeping so you can create a heapdump - just kill this process afterwards")
  // println("`jps` to find out id of this process")
  // println("jmap -dump:file=heapdump.hprof PID")
  // Thread.sleep(1000000)
}
