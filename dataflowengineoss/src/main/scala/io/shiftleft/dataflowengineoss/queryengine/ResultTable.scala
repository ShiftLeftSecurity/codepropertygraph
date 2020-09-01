package io.shiftleft.dataflowengineoss.queryengine

import io.shiftleft.codepropertygraph.generated.nodes
import scala.jdk.CollectionConverters._

class ResultTable {

  private val table = new java.util.concurrent.ConcurrentHashMap[nodes.StoredNode, List[ReachableByResult]].asScala

  /**
    * Add all results in `value` to table entry at `key`, appending to existing
    * results.
    */
  def add(key: nodes.StoredNode, value: List[ReachableByResult]): Unit = {
    table.asJava.compute(key, { (_, existingValue) =>
      Option(existingValue).toList.flatten ++ value
    })
  }

  /**
    * For a given path, determine whether results for the first element (`first`) are stored in the
    * table, and if so, for each result, determine the path up to `first` and prepend it to
    * `path`, giving us new results via table lookup.
    */
  def createFromTable(path: List[PathElement]): Option[List[ReachableByResult]] = {
    val first = path.head
    table.get(first.node).map { res =>
      res.map { r =>
        val completePath = r.path.slice(0, r.path.map(_.node).indexOf(first.node)) ++ path
        r.copy(path = completePath)
      }
    }
  }

  /**
    * Retrieve list of results for `node` or None if they are not
    * available in the table.
    */
  def get(node: nodes.StoredNode): Option[List[ReachableByResult]] = {
    table.get(node)
  }

}

/**
  * A (partial) result, informing about a path that exists from a source to another
  * node in the graph.
  *
  * @param path this is the main result - a known path
  * @param partial indicate whether this result stands on its own or requires further analysis,
  *                e.g., by expanding output arguments backwards into method output parameters.
  * */
case class ReachableByResult(path: List[PathElement], callDepth: Int = 0, partial: Boolean = false) {
  def source: nodes.TrackingPoint = path.head.node

  def unresolvedArgs: List[nodes.Expression] =
    path.collect {
      case elem if !elem.resolved && elem.node.isInstanceOf[nodes.Expression] =>
        elem.node.asInstanceOf[nodes.Expression]
    }.distinct
}

case class PathElement(node: nodes.TrackingPoint, visible: Boolean = true, resolved: Boolean = true)
