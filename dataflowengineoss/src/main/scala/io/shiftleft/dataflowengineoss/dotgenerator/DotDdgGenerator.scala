package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.codepropertygraph.generated.{EdgeKeys, EdgeTypes, nodes}
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
import io.shiftleft.semanticcpg.dotgenerator.Shared
import io.shiftleft.semanticcpg.dotgenerator.Shared.Edge
import overflowdb.Node
import overflowdb.traversal._

object DotDdgGenerator {

  def expand(v: nodes.StoredNode)(implicit semantics: Semantics): Iterator[Edge] = {

    val allInEdges = v
      .inE(EdgeTypes.REACHING_DEF)
      .map(x => Edge(x.outNode.asInstanceOf[nodes.StoredNode], v, x.property(EdgeKeys.VARIABLE)))

    val edgesFromMethods = allInEdges.filter(_.src.isInstanceOf[nodes.Method])

    v match {
      case trackingPoint: nodes.TrackingPoint =>
        trackingPoint.ddgInPathElem
          .map(x => Edge(x.node.asInstanceOf[nodes.StoredNode], v, x.inEdgeLabel))
          .iterator ++ edgesFromMethods.iterator
      case _ =>
        v.inE(EdgeTypes.REACHING_DEF)
          .map(x => Edge(x.outNode.asInstanceOf[nodes.StoredNode], v, x.property(EdgeKeys.VARIABLE)))
          .iterator
    }

  }

  def cfgNodeShouldBeDisplayed(v: Node): Boolean = !(
    v.isInstanceOf[nodes.Block] ||
      v.isInstanceOf[nodes.ControlStructure] ||
      v.isInstanceOf[nodes.JumpTarget]
  )

  def toDotDdg(traversal: Traversal[nodes.Method])(implicit semantics: Semantics): Traversal[String] =
    traversal.map(Shared.dotGraph(_, expand, cfgNodeShouldBeDisplayed))

}
