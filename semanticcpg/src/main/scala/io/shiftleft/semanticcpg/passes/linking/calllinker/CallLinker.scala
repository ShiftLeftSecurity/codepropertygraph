package io.shiftleft.semanticcpg.passes.linking.calllinker

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{DispatchTypes, EdgeTypes, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.Implicits._
import org.apache.tinkerpop.gremlin.structure.Direction
import org.apache.logging.log4j.{LogManager, Logger}

import scala.collection.mutable
import scala.jdk.CollectionConverters._

class CallLinker(cpg: Cpg) extends ParallelCpgPass[nodes.Call](cpg) {

  import CallLinker._

  private val methodFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]

  override def init(): Unit = {
    cpg.graph.V
      .hasLabel(NodeTypes.METHOD)
      .sideEffectWithTraverser { traverser =>
        val method = traverser.get.asInstanceOf[nodes.Method]
        methodFullNameToNode.put(method.fullName, method)
      }
      .iterate()
  }

  override def partIterator: Iterator[nodes.Call] = cpg.call.toIterator()

  /**
    * Main method of enhancement - to be implemented by child class
    **/
  override def runOnPart(call: nodes.Call): Option[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    try {
      linkCall(call, dstGraph)
    } catch {
      case exception: Exception =>
        throw new RuntimeException(exception)
    }
    Some(dstGraph.build())
  }

  private def linkCall(call: nodes.Call, dstGraph: DiffGraph.Builder): Unit = {
    call.dispatchType match {
      case DispatchTypes.STATIC_DISPATCH =>
        val resolvedMethodOption = methodFullNameToNode.get(call.methodFullName)
        if (resolvedMethodOption.isDefined) {
          dstGraph.addEdgeInOriginal(call, resolvedMethodOption.get, EdgeTypes.CALL)
        } else {
          logger.info(
            s"Unable to link static CALL with METHOD_FULL_NAME ${call.methodFullName}, NAME ${call.name}, " +
              s"SIGNATURE ${call.signature}, CODE ${call.code}")
        }
      case DispatchTypes.DYNAMIC_DISPATCH =>
        val receiverIt = call._receiverOut
        if (receiverIt.hasNext) {
          val receiver = receiverIt.next
          receiver match {
            case methodRefReceiver: nodes.MethodRef =>
              Some(methodRefReceiver._refOut.onlyChecked.asInstanceOf[nodes.Method])
            case _ =>
              val receiverTypeDecl = receiver
                ._evalTypeOut()
                .onlyChecked
                ._refOut
                .onlyChecked

              val resolvedMethodOption = receiverTypeDecl._bindsOut.asScala.collectFirst {
                case binding: nodes.Binding if binding.name == call.name && binding.signature == call.signature =>
                  binding._refOut.onlyChecked.asInstanceOf[nodes.Method]
              }
              if (resolvedMethodOption.isDefined) {
                dstGraph.addEdgeInOriginal(call, resolvedMethodOption.get, EdgeTypes.CALL)
              } else {
                /*
                There is no binding that declares the VTable slot.
                This is a valid possibility in message-passing style languages.

                In JVM and .NET this should not happen -- place breakpoint or uncomment here when debugging frontends
                logger.debug(
                  s"Unable to link dynamic CALL with METHOD_FULL_NAME ${call.methodFullName}, NAME ${call.name}, " +
                    s"SIGNATURE ${call.signature}, CODE ${call.code}")
               */
              }
          }
        } else {
          logger.warn(s"Missing receiver edge on dynamic CALL ${call.code}")
        }
      case _ => logger.warn(s"Unknown dispatch type on dynamic CALL ${call.code}")
    }
  }
}

object CallLinker {
  private val logger: Logger = LogManager.getLogger(getClass)
}
