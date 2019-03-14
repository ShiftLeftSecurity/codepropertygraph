package io.shiftleft.passes.dataflows.steps

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.queryprimitives.steps.CpgSteps
import org.apache.logging.log4j.LogManager
import shapeless.HList
import DataFlowObject._

object DataFlowObject {
  protected val logger = LogManager.getLogger(getClass)
  
  implicit val marshaller: Marshallable[nodes.DataFlowObject] = new Marshallable[nodes.DataFlowObject] {

    /* TODO MP: generate in DomainClassCreator */
    override def toCC(element: Element): nodes.DataFlowObject =
      element.label match {
        case NodeTypes.IDENTIFIER => implicitly[Marshallable[nodes.Identifier]].toCC(element)
        case NodeTypes.LITERAL => implicitly[Marshallable[nodes.Literal]].toCC(element)
        case NodeTypes.METHOD_PARAMETER_IN => implicitly[Marshallable[nodes.MethodParameterIn]].toCC(element)
        case NodeTypes.METHOD_PARAMETER_OUT => implicitly[Marshallable[nodes.MethodParameterOut]].toCC(element)
        case NodeTypes.METHOD_RETURN => implicitly[Marshallable[nodes.MethodReturn]].toCC(element)

        // Expressions are DataFlowObjects
        case NodeTypes.BLOCK => implicitly[Marshallable[nodes.Block]].toCC(element)
        case NodeTypes.CALL => implicitly[Marshallable[nodes.Call]].toCC(element)
        case NodeTypes.METHOD_REF => implicitly[Marshallable[nodes.MethodRef]].toCC(element)
        case NodeTypes.RETURN => implicitly[Marshallable[nodes.Return]].toCC(element)
        case NodeTypes.UNKNOWN => implicitly[Marshallable[nodes.Unknown]].toCC(element)
      }

    // not really needed AFAIK
    override def fromCC(cc: nodes.DataFlowObject) = ???
  }
}

/**
  * Base class for nodes that can occur in data flows
  * */
class DataFlowObject[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.DataFlowObject, Labels](raw) {

}
