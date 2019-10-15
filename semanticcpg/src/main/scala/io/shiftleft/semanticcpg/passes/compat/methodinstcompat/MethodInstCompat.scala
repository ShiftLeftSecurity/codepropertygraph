package io.shiftleft.semanticcpg.passes.compat.methodinstcompat

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.apache.logging.log4j.LogManager

import scala.collection.mutable
import scala.collection.JavaConverters._

/**
  * This pass ensures compatibility with CPGs in old format containing METHOD_INST nodes.
  * TODO remove when not needed anymore.
  */
class MethodInstCompat(cpg: Cpg) extends CpgPass(cpg) {
  private val methodInstFullNameToMethodFullName = mutable.Map.empty[String, String]

  override def run(): Iterator[DiffGraph] = {
    val diffGraph = new DiffGraph

    if (cpg.graph.traversal.V().hasLabel(NodeTypes.METHOD_INST).asScala.nonEmpty) {
      MethodInstCompat.logger.warn("Using deprecated CPG format with METHOD_INST nodes.")
      init()

      cpg.call.toIterator.foreach { call =>
        call.methodInstFullName.foreach { methodInstFullName =>
          methodInstFullNameToMethodFullName.get(methodInstFullName) match {
            case Some(methodFullName) =>
              call.setProperty(NodeKeys.METHOD_FULL_NAME, methodFullName)
            case None =>
              MethodInstCompat.logger.warn(
                s"Unable to find method full name by " +
                  s"method instance full name $methodInstFullName for CALL ${call.code}.")
          }
        }

      }

      cpg.methodRef.toIterator.foreach { methodRef =>
        methodRef.methodInstFullName.foreach { methodInstFullName =>
          methodInstFullNameToMethodFullName.get(methodInstFullName) match {
            case Some(methodFullName) =>
              methodRef.setProperty(NodeKeys.METHOD_FULL_NAME, methodFullName)
            case None =>
              MethodInstCompat.logger.warn(
                s"Unable to find method full name by " +
                  s"method instance full name $methodInstFullName for METHDO_REF ${methodRef.code}.")
          }
        }
      }
    }

    Iterator(diffGraph)
  }

  private def init(): Unit = {
    cpg.graph
      .traversal()
      .V()
      .hasLabel(NodeTypes.METHOD_INST)
      .sideEffect { traverser =>
        val methodInst = traverser.get
        methodInstFullNameToMethodFullName.put(methodInst.value2(NodeKeys.FULL_NAME),
                                               methodInst.value2(NodeKeys.METHOD_FULL_NAME))
      }
      .iterate()
  }
}

object MethodInstCompat {
  private val logger = LogManager.getLogger(getClass)
}
