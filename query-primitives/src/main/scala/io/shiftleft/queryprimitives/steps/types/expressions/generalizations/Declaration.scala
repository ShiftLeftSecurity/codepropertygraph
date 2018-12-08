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
        case nodes.Literal.Label => implicitly[Marshallable[nodes.Literal]].toCC(element)
        case nodes.Local.Label => implicitly[Marshallable[nodes.Local]].toCC(element)
        case nodes.Member.Label => implicitly[Marshallable[nodes.Member]].toCC(element)
        case nodes.Method.Label => implicitly[Marshallable[nodes.Method]].toCC(element)
        case nodes.MethodParameterIn.Label => implicitly[Marshallable[nodes.MethodParameterIn]].toCC(element)
        case nodes.MethodParameterOut.Label => implicitly[Marshallable[nodes.MethodParameterOut]].toCC(element)
      }

    // not really needed AFAIK
    override def fromCC(cc: nodes.Declaration) = ???
  }
}

trait DeclarationBase[NodeType <: nodes.Declaration, Labels <: HList] {
  this: CpgSteps[NodeType, Labels] =>
  // TODO: steps for Declarations go here
}
