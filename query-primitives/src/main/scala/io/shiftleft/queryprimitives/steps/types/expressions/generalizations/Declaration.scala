package io.shiftleft.queryprimitives.steps.types.expressions.generalizations

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeyNames, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import java.lang.{Long => JLong}
import java.util.{Iterator => JIterator}
import org.apache.tinkerpop.gremlin.structure.{Direction, VertexProperty}
import shapeless.HList

class Declaration[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.Declaration, Labels](raw)(Declaration.marshaller)
    with DeclarationBase[nodes.Declaration, Labels]

object Declaration {

  /* TODO MP: generate in DomainClassCreator */
  val marshaller: Marshallable[nodes.Declaration] = new Marshallable[nodes.Declaration] {
    override def toCC(element: Element): nodes.Declaration =
      element.label match {
        case "LITERAL" => implicitly[Marshallable[nodes.Literal]].toCC(element)
        case "LOCAL" => implicitly[Marshallable[nodes.Local]].toCC(element)
        case "MEMBER" => implicitly[Marshallable[nodes.Member]].toCC(element)
        case "METHOD" => implicitly[Marshallable[nodes.Method]].toCC(element)
        case "METHOD_PARAMETER_IN" => implicitly[Marshallable[nodes.MethodParameterIn]].toCC(element)
        case "METHOD_PARAMETER_OUT" => implicitly[Marshallable[nodes.MethodParameterOut]].toCC(element)
      }

    // not really needed AFAIK
    override def fromCC(cc: nodes.Declaration) = ???
  }
}

trait DeclarationBase[NodeType <: nodes.Declaration, Labels <: HList] {
  this: CpgSteps[NodeType, Labels] =>
  // TODO: steps for Declarations go here
}
