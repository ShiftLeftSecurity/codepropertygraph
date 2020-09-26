package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.semanticcpg.dotgenerator.Shared.Edge

import scala.jdk.CollectionConverters._

class CdgGenerator extends CfgGenerator {

  override val edgeType: String = EdgeTypes.CDG

  override def expand(v: nodes.StoredNode): Iterator[Edge] = {
    v._cdgOut()
      .asScala
      .filter(_.isInstanceOf[nodes.StoredNode])
      .map(node => Edge(v, node, edgeType = edgeType))
  }

}
