package overflowdb

import gnu.trove.map.TLongObjectMap
import io.shiftleft.codepropertygraph.cpgloading._
import overflowdb._
import overflowdb.traversal._

//object Hack {
//  def nodes(graph: OdbGraph) =
//    graph.nodes
//}

object Foo extends App {
  val odbConfig = overflowdb.OdbConfig.withDefaults.withStorageLocation("/home/mp/Projects/shiftleft/codepropertygraph/sp.bin")
  val config = CpgLoaderConfig.withDefaults.withOverflowConfig(odbConfig)
  val cpg = CpgLoader.loadFromOverflowDb(config)
  val graph = cpg.graph

//  import scala.jdk.CollectionConverters._
////  val nodesCached = Hack.nodes(graph).valueCollection().asScala.toList
//  val nodesCached = graph.nodes().asScala.toList
//  timed("nodes cached") { () =>
//    val iter = nodesCached.iterator
//    while (iter.hasNext) iter.next
//  }
//
//  timed("Hack.nodes.valueCollection") { () =>
//    val iter = Hack.nodes(graph).valueCollection().iterator()
//    while (iter.hasNext) iter.next
//  }
//
//  timed("Hack.nodes.values") { () =>
//    val arr = Hack.nodes(graph).values()
//    var i = 0
//    var ref: AnyRef = null
//    while (i < arr.length) {
//      ref = arr(i)
//      i += 1
//    }
//  }

//  timed("Hack.nodes.iterator") { () =>
//    val iter = Hack.nodes(graph).iterator()
//    while (iter.hasNext) {
//      iter.advance()
//      iter.value()
//    }
//  }
//
  timed("graph.nodes") { () =>
    // with NodesList: 1.6ms
    val iter = graph.nodes()
    while (iter.hasNext) iter.next
  }

  // prepare: copy to NodesList
//  val nl = new overflowdb.util.NodesList(graph.nodeCount)
////  val i: Int = graph.nodes().next
//  val iter = graph.nodes()
//  while (iter.hasNext) nl.add(iter.next.asInstanceOf[NodeRef[_]])
//  timed("NodesList.iterator") { () =>
//    // 0.32ms
//    val iter = nl.iterator
//    while (iter.hasNext) iter.next
//  }

  def timed(msg: String)(fun: () => Unit) = {
    fun() //warmup
    val iterations = 10
    val start = System.nanoTime
    1.to(iterations).foreach {_ => fun()}
    val average = (System.nanoTime - start) / iterations.toFloat / 1000000f
    println(s"$msg: ${average}ms")
  }

  def getUsedMemory = {
    System.gc()
    System.gc()
    System.gc()
    Runtime.getRuntime.totalMemory - Runtime.getRuntime.freeMemory
  }

//  println(graph.nodeCount()) //665k nodes
  println(getUsedMemory / 1024 / 1024)
  /* odb master results:
  nodes cached: 1.7582682ms
  Hack.nodes: directly on collection: 3.9964814ms
  graph.nodes (including redirection over iteratorutils): 9.965945ms
  78M heap

  with nodes being an ArrayList:
  nodes cached: 1.5225911ms
  graph.nodes: 0.392954ms
  79M heap
  good, but problem with arraylist is: need to be careful with IdToIndex mapping, esp. when removing elements
  idea: when removing, set elements to some static instance that indicates "delete me" (or even null?), and take those out on trim
   */

  graph.close

}
