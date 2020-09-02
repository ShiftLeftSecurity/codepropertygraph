package io.shiftleft.semanticcpg.passes.compat.methodinstcompat

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import overflowdb._

import scala.collection.mutable
import scala.jdk.CollectionConverters._

/**
  * This pass ensures compatibility with CPGs in old format containing METHOD_INST nodes.
  * TODO remove when not needed anymore.
  */
class MethodInstCompat(cpg: Cpg) extends CpgPass(cpg) {
  private val methodInstFullNameToMethodFullName = mutable.Map.empty[String, String]

  override def run(): Iterator[DiffGraph] = {
    val diffGraph = DiffGraph.newBuilder

    if (cpg.graph.nodes(NodeTypes.METHOD_INST).hasNext) {
      MethodInstCompat.logger.warn("Using deprecated CPG format with METHOD_INST nodes.")
      init()

      cpg.call.foreach { call =>
        call.methodInstFullName.foreach { methodInstFullName =>
          methodInstFullNameToMethodFullName.get(methodInstFullName) match {
            case Some(methodFullName) =>
              call.setProperty(NodeKeys.METHOD_FULL_NAME -> methodFullName)
            case None =>
              MethodInstCompat.logger.warn(
                s"Unable to find method full name by " +
                  s"method instance full name $methodInstFullName for CALL ${call.code}.")
          }
        }

      }

      cpg.methodRef.foreach { methodRef =>
        methodRef.methodInstFullName.foreach { methodInstFullName =>
          methodInstFullNameToMethodFullName.get(methodInstFullName) match {
            case Some(methodFullName) =>
              methodRef.setProperty(NodeKeys.METHOD_FULL_NAME -> methodFullName)
            case None =>
              MethodInstCompat.logger.warn(
                s"Unable to find method full name by " +
                  s"method instance full name $methodInstFullName for METHDO_REF ${methodRef.code}.")
          }
        }
      }
    }

    Iterator(diffGraph.build())
  }

  private def init(): Unit = {
    cpg.graph.nodes(NodeTypes.METHOD_INST).asScala.foreach {
      case methodInst: nodes.MethodInst =>
        methodInstFullNameToMethodFullName.put(methodInst.fullName, methodInst.methodFullName)
    }
  }
}

object MethodInstCompat {
  private val logger: Logger = LoggerFactory.getLogger(classOf[MethodInstCompat])
}
