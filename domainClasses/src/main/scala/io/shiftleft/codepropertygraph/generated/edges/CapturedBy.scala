package io.shiftleft.codepropertygraph.generated.edges

import java.util.{Set => JSet}
import overflowdb._
import scala.jdk.CollectionConverters._

object CapturedBy {
  val Label = "CAPTURED_BY"

  object PropertyNames {
    
    val all: Set[String] = Set()
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    
  }

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[CapturedBy] {
    override val forLabel = CapturedBy.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new CapturedBy(graph, outNode, inNode)
  }
}

class CapturedBy(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
extends Edge(_graph, CapturedBy.Label, _outNode, _inNode, CapturedBy.PropertyNames.allAsJava) {

}

