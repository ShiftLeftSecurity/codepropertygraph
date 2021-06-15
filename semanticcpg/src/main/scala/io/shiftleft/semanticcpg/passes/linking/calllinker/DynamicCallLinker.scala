package io.shiftleft.semanticcpg.passes.linking.calllinker

import io.shiftleft.Implicits._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{DispatchTypes, EdgeTypes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable
import scala.jdk.CollectionConverters._

class DynamicCallLinker(cpg: Cpg) extends CpgPass(cpg) {

  import DynamicCallLinker._
  private val methodFullNameToNode = mutable.Map.empty[String, StoredNode]

  /**
    * Main method of enhancement - to be implemented by child class
    **/
  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder
    cpg.method.foreach { method =>
      methodFullNameToNode.put(method.fullName, method)
    }
    cpg.call.foreach { call =>
      try {
        linkCall(call, dstGraph)
      } catch {
        case exception: Exception =>
          throw new RuntimeException(exception)
      }
    }
    Iterator(dstGraph.build())
  }

  private def linkCall(call: Call, dstGraph: DiffGraph.Builder): Unit = {
    call.dispatchType match {
      case DispatchTypes.STATIC_DISPATCH =>
      // Do nothing
      case DispatchTypes.DYNAMIC_DISPATCH =>
        linkDynamicCall(call, dstGraph)
      case _ => logger.warn(s"Unknown dispatch type on dynamic CALL ${call.code}")
    }
  }

  private def linkDynamicCall(call: Call, dstGraph: DiffGraph.Builder): Unit = {
    val receiverIt = call._receiverOut
    if (receiverIt.hasNext) {
      val receiver = receiverIt.next
      receiver match {
        case _ =>
          val receiverTypeDecl = receiver._evalTypeOut.onlyChecked._refOut.onlyChecked

          val resolvedMethodOption = receiverTypeDecl._bindsOut.asScala.collectFirst {
            case binding: Binding if binding.name == call.name && binding.signature == call.signature =>
              binding._methodViaRefOut
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
  }

}

object DynamicCallLinker {
  private val logger: Logger = LoggerFactory.getLogger(classOf[StaticCallLinker])
}
