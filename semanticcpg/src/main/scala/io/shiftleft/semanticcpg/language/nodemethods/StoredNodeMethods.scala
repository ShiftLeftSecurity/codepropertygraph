package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes.{StoredNode, Tag}
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

class StoredNodeMethods(val node: StoredNode) extends AnyVal {
  def tag: Traversal[Tag] = {
    node._taggedByOut.asScala.map(_.asInstanceOf[Tag]).to(Traversal)
  }
}
