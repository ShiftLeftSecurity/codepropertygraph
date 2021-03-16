package io.shiftleft.codepropertygraph.generated.edges

import java.util.{Set => JSet}
import overflowdb._
import scala.jdk.CollectionConverters._

object DynamicType {
  val Label = "DYNAMIC_TYPE"

  object PropertyNames {
    
    val all: Set[String] = Set()
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    
  }

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[DynamicType] {
    override val forLabel = DynamicType.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new DynamicType(graph, outNode, inNode)
  }
}

class DynamicType(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
extends Edge(_graph, DynamicType.Label, _outNode, _inNode, DynamicType.PropertyNames.allAsJava) {

}

