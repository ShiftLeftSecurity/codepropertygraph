package io.shiftleft.semanticcpg.passes.languagespecific.kotlin

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{DispatchTypes, EdgeTypes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

/*
 * This pass uses simple heuristics to link CALL nodes to METHOD nodes.
 * It is most useful for CPGs with incomplete type information, for example
 * generated from the source of Kotlin programs.
 *
 * In this pass, we assume that the METHOD_FULL_NAME properties of both CALL
 * and METHOD nodes have the format `$method_name:$method_ret_type($method_param_1_type, $method_param2_type, ...)`
 *
 * The heuristics used are:
 * 1. If the CPG contains CALLs with a method name matching a single METHOD node,
 * then add a CALL edge between them.
 *
 * 2. If the CPG contains CALLs with a method name matching multiple METHOD nodes with varying numbers of parameters,
 * then add a CALL edge to the METHOD node with a number of parameters matching the number of arguments.
 *
 * 3. If the CPG contains CALLs with a method name matching multiple METHOD nodes with the same number of parameters,
 * then add CALL edges to _all_ the METHOD nodes with a number of parameters matching the number of arguments.
 */
class HeuristicsCallLinker(cpg: Cpg) extends CpgPass(cpg) {

  import HeuristicsCallLinker._
  private val fqMethodNamesToNodes = mutable.Map.empty[String, Seq[(StoredNode, Int)]]

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder
    cpg.method.foreach { method =>
      val fqMethodName = method.fullName.split(":").head
      val entry = fqMethodNamesToNodes.get(fqMethodName)
      if (entry.isDefined) {
        fqMethodNamesToNodes.put(fqMethodName, entry.get ++ Seq((method, method.parameter.size)))
      } else {
        fqMethodNamesToNodes.put(fqMethodName, Seq((method, method.parameter.size)))
      }
    }

    cpg.call.foreach { call =>
      linkCall(call, dstGraph)
    }

    Iterator(dstGraph.build())
  }

  private def linkCall(call: Call, dstGraph: DiffGraph.Builder): Unit = {
    call.dispatchType match {
      case DispatchTypes.STATIC_DISPATCH =>
        linkStaticCall(call, dstGraph)
      case _ =>
    }
  }

  private def linkStaticCall(call: Call, dstGraph: DiffGraph.Builder): Unit = {
    val methodName = call.methodFullName.split(":").head
    val resolvedMethodOption = fqMethodNamesToNodes.get(methodName)
    if (resolvedMethodOption.isDefined) {
      if (resolvedMethodOption.get.size == 1) {
        dstGraph.addEdgeInOriginal(call, resolvedMethodOption.get(0)._1, EdgeTypes.CALL)
      } else {
        val matchingNumOfParams =
          resolvedMethodOption.get.filter { entry =>
            entry._2 == call.argument.size
          }
        if (matchingNumOfParams.size > 0) {
          matchingNumOfParams.foreach { entry =>
            dstGraph.addEdgeInOriginal(call, entry._1, EdgeTypes.CALL)
          }
        } else {
          logger.info(
            s"Unable to link static CALL with METHOD_FULL_NAME ${call.methodFullName}, NAME ${call.name}, " +
              s"SIGNATURE ${call.signature}, CODE ${call.code}")
        }
      }
    } else {
      logger.info(
        s"Unable to link static CALL with METHOD_FULL_NAME ${call.methodFullName}, NAME ${call.name}, " +
          s"SIGNATURE ${call.signature}, CODE ${call.code}")
    }
  }
}

object HeuristicsCallLinker {
  private val logger: Logger = LoggerFactory.getLogger(HeuristicsCallLinker.getClass)
}
