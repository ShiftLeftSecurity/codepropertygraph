package io.shiftleft.codepropertygraph.generated.edges

import java.util.{Set => JSet}
import overflowdb._
import scala.jdk.CollectionConverters._

object ContainsNode {
  val Label = "CONTAINS_NODE"

  object PropertyNames {
    val Index = "INDEX" 
    val LocalName = "LOCAL_NAME" 
    val all: Set[String] = Set(Index, LocalName)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Index = new PropertyKey[java.lang.Integer]("INDEX") 
    val LocalName = new PropertyKey[String]("LOCAL_NAME") 
  }

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[ContainsNode] {
    override val forLabel = ContainsNode.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new ContainsNode(graph, outNode, inNode)
  }
}

class ContainsNode(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
extends Edge(_graph, ContainsNode.Label, _outNode, _inNode, ContainsNode.PropertyNames.allAsJava) {
def index: Option[java.lang.Integer] = Option(property("INDEX")).asInstanceOf[Option[java.lang.Integer]]

def localName: Option[String] = Option(property("LOCAL_NAME")).asInstanceOf[Option[String]]
}

