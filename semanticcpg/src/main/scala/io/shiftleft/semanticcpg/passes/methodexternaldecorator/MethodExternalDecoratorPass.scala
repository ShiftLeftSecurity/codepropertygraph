package io.shiftleft.semanticcpg.passes.methodexternaldecorator

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.PropertyNames
import io.shiftleft.codepropertygraph.generated.nodes.{Method, StoredNode, TypeDecl}
import io.shiftleft.passes.{DiffGraph, DiffGraphHandler, SimpleCpgPassV2}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}

import scala.jdk.CollectionConverters._

/**
  * Sets the isExternal flag for Method in case it is not set already.
  * It is set to its parent type decl isExternal, defaulting to false otherwise.
  *
  * This solution is only meant to be used until all language frontends set the isExternal flag on their own.
  */
class MethodExternalDecoratorPass(cpg: Cpg) extends SimpleCpgPassV2 {

  import MethodExternalDecoratorPass._

  private[this] var loggedDeprecatedWarning = false

  private def isValidExternalFlag(isExternal: java.lang.Boolean): Boolean =
    isExternal != null

  private def findMethodTypeDecl(method: Method): Option[StoredNode] =
    method._astIn.asScala.find(_.isInstanceOf[TypeDecl])

  private def methodTypeDeclHasIsExternal(method: Method): Boolean =
    findMethodTypeDecl(method) match {
      case Some(value) =>
        isValidExternalFlag(value.asInstanceOf[TypeDecl].isExternal)
      case None => false
    }

  private def getExternalFromTypeDecl(method: Method): Option[Boolean] =
    findMethodTypeDecl(method).map(_.asInstanceOf[TypeDecl].isExternal)

  private def setIsExtern(dstGraph: DiffGraph.Builder, method: Method, isExtern: Boolean): Unit = {
    log("Using deprecated CPG format with missing IS_EXTERNAL property on METHOD node.")
    dstGraph.addNodeProperty(
      method,
      PropertyNames.IS_EXTERNAL,
      value = Boolean.box(isExtern)
    )
  }

  override def run(diffGraphHandler: DiffGraphHandler): Unit = {
    val dstGraph = DiffGraph.newBuilder

    cpg.method
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

    diffGraphHandler.addDiffGraph(dstGraph.build())
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
  private val logger: Logger = LoggerFactory.getLogger(classOf[MethodExternalDecoratorPass])
}
