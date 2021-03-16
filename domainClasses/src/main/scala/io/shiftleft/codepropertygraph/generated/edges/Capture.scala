package io.shiftleft.codepropertygraph.generated.edges

import java.util.{Set => JSet}
import overflowdb._
import scala.jdk.CollectionConverters._

object Capture {
  val Label = "CAPTURE"

  object PropertyNames {
    
    val all: Set[String] = Set()
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    
  }

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[Capture] {
    override val forLabel = Capture.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new Capture(graph, outNode, inNode)
  }
}

class Capture(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
extends Edge(_graph, Capture.Label, _outNode, _inNode, Capture.PropertyNames.allAsJava) {

}

