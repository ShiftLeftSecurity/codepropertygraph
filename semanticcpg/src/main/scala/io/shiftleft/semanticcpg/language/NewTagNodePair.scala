package io.shiftleft.semanticcpg.language

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes.{NewNode, Node, StoredNode}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.passes.{ DiffGraph}

class NewTagNodePair[NodeType <: Node](raw: GremlinScala[nodes.NewTagNodePair]) {

  def store()(implicit diffGraph: DiffGraph.Builder): Unit = {
    raw.toList.foreach { tagNodePair =>
      val tag = tagNodePair.tag
      val tagValue = tagNodePair.node
      diffGraph.addNode(tag.asInstanceOf[NewNode])
      tagValue match {
        case tagValue: StoredNode =>
          diffGraph.addEdgeFromOriginal(tagValue.asInstanceOf[StoredNode],
                                        tag.asInstanceOf[NewNode],
                                        EdgeTypes.TAGGED_BY)
        case tagValue: NewNode =>
          diffGraph.addEdge(tagValue, tag.asInstanceOf[NewNode], EdgeTypes.TAGGED_BY, Nil)
      }
    }

  }

}
