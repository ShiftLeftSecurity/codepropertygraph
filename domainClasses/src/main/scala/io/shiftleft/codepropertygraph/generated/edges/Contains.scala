package io.shiftleft.codepropertygraph.generated.edges

import java.util.{Set => JSet}
import overflowdb._
import scala.jdk.CollectionConverters._

object Contains {
  val Label = "CONTAINS"

  object PropertyNames {
    
    val all: Set[String] = Set()
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    
  }

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[Contains] {
    override val forLabel = Contains.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new Contains(graph, outNode, inNode)
  }
}

class Contains(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
extends Edge(_graph, Contains.Label, _outNode, _inNode, Contains.PropertyNames.allAsJava) {

}

