package io.shiftleft.semanticcpg.passes.cfgdominator

import gremlin.scala.Vertex
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

class CpgCfgAdapter extends CfgAdapter[Vertex] {
  override def successors(node: Vertex): TraversableOnce[Vertex] = {
    node.vertices(Direction.OUT, EdgeTypes.CFG).asScala
  }

  override def predecessors(node: Vertex): TraversableOnce[Vertex] = {
    node.vertices(Direction.IN, EdgeTypes.CFG).asScala
  }
}
