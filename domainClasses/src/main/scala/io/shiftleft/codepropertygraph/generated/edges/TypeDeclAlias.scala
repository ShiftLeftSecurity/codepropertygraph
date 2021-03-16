package io.shiftleft.codepropertygraph.generated.edges

import java.util.{Set => JSet}
import overflowdb._
import scala.jdk.CollectionConverters._

object TypeDeclAlias {
  val Label = "TYPE_DECL_ALIAS"

  object PropertyNames {
    
    val all: Set[String] = Set()
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    
  }

  val layoutInformation = new EdgeLayoutInformation(Label, PropertyNames.allAsJava)

  val factory = new EdgeFactory[TypeDeclAlias] {
    override val forLabel = TypeDeclAlias.Label

    override def createEdge(graph: Graph, outNode: NodeRef[NodeDb], inNode: NodeRef[NodeDb]) =
      new TypeDeclAlias(graph, outNode, inNode)
  }
}

class TypeDeclAlias(_graph: Graph, _outNode: NodeRef[NodeDb], _inNode: NodeRef[NodeDb])
extends Edge(_graph, TypeDeclAlias.Label, _outNode, _inNode, TypeDeclAlias.PropertyNames.allAsJava) {

}

