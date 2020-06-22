package io.shiftleft.semanticcpg.passes.methodexternaldecorator

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.TypeDecl
import io.shiftleft.codepropertygraph.generated.{NodeKeyNames, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import org.apache.logging.log4j.{LogManager, Logger}
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

/**
  * Sets the isExternal flag for Method in case it is not set already.
  * It is set to its parent type decl isExternal, defaulting to false otherwise.
  *
  * This solution is only meant to be used until all language frontends set the isExternal flag on their own.
  */
class MethodExternalDecoratorPass(cpg: Cpg) extends CpgPass(cpg) {

  import MethodExternalDecoratorPass._

  private[this] var loggedDeprecatedWarning = false

  private def isValidExternalFlag(isExternal: java.lang.Boolean): Boolean =
    isExternal != null

  private def findMethodTypeDecl(method: nodes.Method): Option[nodes.StoredNode] =
    method._astIn.asScala.find(_.isInstanceOf[TypeDecl])

  private def methodTypeDeclHasIsExternal(method: nodes.Method): Boolean =
    findMethodTypeDecl(method) match {
      case Some(value) =>
        isValidExternalFlag(value.asInstanceOf[TypeDecl].isExternal)
      case None => false
    }

  private def getExternalFromTypeDecl(method: nodes.Method): Option[Boolean] =
    findMethodTypeDecl(method).map(_.asInstanceOf[TypeDecl].isExternal)

  private def setIsExtern(dstGraph: DiffGraph.Builder, method: nodes.Method, isExtern: Boolean): Unit = {
    log("Using deprecated CPG format with missing IS_EXTERNAL property on METHOD node.")
    dstGraph.addNodeProperty(
      method,
      NodeKeyNames.IS_EXTERNAL,
      value = Boolean.box(isExtern)
    )
  }

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    // TODO MP use `cpg.method` once that's defined in odb api
    Traversal(cpg.graph.nodesByLabel(NodeTypes.METHOD))
      .cast[nodes.Method]
      .filterNot(method => isValidExternalFlag(method.isExternal))
      .foreach { method =>
        if (!methodTypeDeclHasIsExternal(method)) {
          // default
          setIsExtern(dstGraph, method, isExtern = false)
        } else {
          // take isExternal from type decl
          setIsExtern(
            dstGraph,
            method,
            isExtern = getExternalFromTypeDecl(method).getOrElse(false)
          )
        }
      }

    Iterator(dstGraph.build())
  }

  @inline
  private def log(message: String): Unit = {
    if (!loggedDeprecatedWarning) {
      logger.warn(message)
      loggedDeprecatedWarning = true
    }
  }

}

object MethodExternalDecoratorPass {
  private val logger: Logger = LogManager.getLogger(classOf[MethodExternalDecoratorPass])
}
