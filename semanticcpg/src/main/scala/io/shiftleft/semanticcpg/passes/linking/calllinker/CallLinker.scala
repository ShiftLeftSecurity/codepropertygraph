package io.shiftleft.semanticcpg.passes.linking.calllinker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{DispatchTypes, EdgeTypes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

class CallLinker(cpg: Cpg) extends CpgPass(cpg) {

  import CallLinker._

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
        val resolvedMethodOption = methodFullNameToNode.get(call.methodFullName)
        if (resolvedMethodOption.isDefined) {
          dstGraph.addEdgeInOriginal(call, resolvedMethodOption.get, EdgeTypes.CALL)
        } else {
          logger.info(
            s"Unable to link static CALL with METHOD_FULL_NAME ${call.methodFullName}, NAME ${call.name}, " +
              s"SIGNATURE ${call.signature}, CODE ${call.code}")
        }
      case DispatchTypes.DYNAMIC_DISPATCH =>
    }
  }
}

object CallLinker {
  private val logger: Logger = LoggerFactory.getLogger(classOf[CallLinker])
}
