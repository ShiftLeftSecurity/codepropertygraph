package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.Shared.Edge
import overflowdb.Node
import overflowdb.traversal._
import scala.jdk.CollectionConverters._

object DotCfgGenerator {

  def toDotCfg(traversal: Traversal[nodes.Method]): Traversal[String] =
    traversal.map(Shared.dotGraph(_, expand, cfgNodeShouldBeDisplayed))

  protected def expand(v: nodes.StoredNode): Iterator[Edge] = {
    v._cfgOut
      .asScala
      .filter(_.isInstanceOf[nodes.StoredNode])
      .map(node => Edge(v, node))
  }

  def cfgNodeShouldBeDisplayed(v: Node): Boolean = !(
    v.isInstanceOf[nodes.Literal] ||
      v.isInstanceOf[nodes.Identifier] ||
      v.isInstanceOf[nodes.Block] ||
      v.isInstanceOf[nodes.ControlStructure] ||
      v.isInstanceOf[nodes.JumpTarget] ||
      v.isInstanceOf[nodes.MethodParameterIn]
  )

}
