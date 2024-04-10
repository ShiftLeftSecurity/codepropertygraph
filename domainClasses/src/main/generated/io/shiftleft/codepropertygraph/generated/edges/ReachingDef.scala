package io.shiftleft.codepropertygraph.generated.edges

import overflowdb._
import scala.jdk.CollectionConverters._

object ReachingDef {
  val Label = "REACHING_DEF"

  object PropertyNames {
    val Variable                         = "VARIABLE"
    val all: Set[String]                 = Set(Variable)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Variable = new overflowdb.PropertyKey[String]("VARIABLE")
  }

  object PropertyDefaults {
    val Variable = "<empty>"
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

  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "VARIABLE" => ReachingDef.PropertyDefaults.Variable
      case _          => super.propertyDefaultValue(propertyKey)
    }
  }

}
