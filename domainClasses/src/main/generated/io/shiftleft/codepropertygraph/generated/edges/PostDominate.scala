package io.shiftleft.codepropertygraph.generated.edges

import overflowdb._
import scala.jdk.CollectionConverters._

object PostDominate {
  val Label = "POST_DOMINATE"

  object PropertyNames {

    val all: Set[String]                 = Set()
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {}

  object PropertyDefaults {}

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[PostDominate] {
    override val forLabel = PostDominate.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new PostDominate(graph, outNode, inNode)
  }
}

class PostDominate(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
    extends Edge(_graph, PostDominate.Label, _outNode, _inNode, PostDominate.PropertyNames.allAsJava) {

  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {

      case _ => super.propertyDefaultValue(propertyKey)
    }
  }

}
