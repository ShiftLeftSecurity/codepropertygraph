package io.shiftleft.semanticcpg.passes.linking.calllinker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{DispatchTypes, EdgeTypes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

class StaticCallLinker(cpg: Cpg) extends CpgPass(cpg) {

  import StaticCallLinker._
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
        linkStaticCall(call, dstGraph)
      case DispatchTypes.DYNAMIC_DISPATCH =>
      // Do nothing
      case _ => logger.warn(s"Unknown dispatch type on dynamic CALL ${call.code}")
    }
  }

  private def linkStaticCall(call: Call, dstGraph: DiffGraph.Builder): Unit = {
    val resolvedMethodOption = methodFullNameToNode.get(call.methodFullName)
    if (resolvedMethodOption.isDefined) {
      dstGraph.addEdgeInOriginal(call, resolvedMethodOption.get, EdgeTypes.CALL)
    } else {
      logger.info(
        s"Unable to link static CALL with METHOD_FULL_NAME ${call.methodFullName}, NAME ${call.name}, " +
          s"SIGNATURE ${call.signature}, CODE ${call.code}")
    }
  }

}

object StaticCallLinker {
  private val logger: Logger = LoggerFactory.getLogger(classOf[StaticCallLinker])
}
