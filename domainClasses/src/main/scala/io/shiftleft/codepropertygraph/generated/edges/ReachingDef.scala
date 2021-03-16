package io.shiftleft.codepropertygraph.generated.edges

import java.util.{Set => JSet}
import overflowdb._
import scala.jdk.CollectionConverters._

object ReachingDef {
  val Label = "REACHING_DEF"

  object PropertyNames {
    val Variable = "VARIABLE" 
    val all: Set[String] = Set(Variable)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Variable = new PropertyKey[String]("VARIABLE") 
  }

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[ReachingDef] {
    override val forLabel = ReachingDef.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new ReachingDef(graph, outNode, inNode)
  }
}

class ReachingDef(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
extends Edge(_graph, ReachingDef.Label, _outNode, _inNode, ReachingDef.PropertyNames.allAsJava) {
def variable: String = property("VARIABLE").asInstanceOf[String]
}

