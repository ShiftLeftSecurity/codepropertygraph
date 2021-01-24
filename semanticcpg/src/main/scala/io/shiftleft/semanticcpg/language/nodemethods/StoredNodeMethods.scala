package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

class StoredNodeMethods(val node: nodes.StoredNode) extends AnyVal {
  def tag: Traversal[nodes.Tag] = {
    node._taggedByOut.asScala.map(_.asInstanceOf[nodes.Tag]).to(Traversal)
  }
}
