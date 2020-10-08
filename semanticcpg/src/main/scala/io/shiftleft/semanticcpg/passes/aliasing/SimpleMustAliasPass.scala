package io.shiftleft.semanticcpg.passes.aliasing
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.nodemethods.TrackingPointMethodsBase

import scala.jdk.CollectionConverters._
import io.shiftleft.semanticcpg.utils.MemberAccess.isGenericMemberAccessName
import org.slf4j.LoggerFactory

object SimpleMustAliasPass {
  private val logger = LoggerFactory.getLogger(getClass)
}
class SimpleMustAliasPass(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    val builder = DiffGraph.newBuilder
    for (method <- cpg.method;
         local <- method.local
         if !local._closureBindingViaCapturedByOut.hasNext && !method.parameter.exists { _.name == local.name }) {
      processLocal(local, builder)
    }
    val res = builder.build
    SimpleMustAliasPass.logger.debug(s"antiAlias pairs: ${res.size} from ${cpg.local.size}")
    Iterator(builder.build())
  }

  private def processLocal(local: nodes.Local, builder: DiffGraph.Builder): Unit = {
    val refs = local._identifierViaRefIn.toList
    if (refs.size != 2) return

    val (definitions, uses) = refs.partitionMap { identifier =>
      getAssignmentRhs(identifier) match {
        case Some(call) => Right(call)
        case _          => Left(identifier)
      }
    }
    if (definitions.size == 1 && uses.size == 1) {
      val use = uses.head
      val definition = definitions.head
      builder.addEdge(src = use, dst = definition, edgeLabel = EdgeTypes.MUST_ALIAS)
    }

  }

  private def getAssignmentRhs(identifier: nodes.Identifier): Option[nodes.Call] = {
    if (identifier.argumentIndex != 1) return None
    identifier._callViaArgumentIn match {
      case Some(call) if call.methodFullName == Operators.assignment =>
        val rhs = call
          ._argumentOut()
          .asScala
          .flatMap {
            case arg: nodes.Call
                if arg.argumentIndex == 2
                  && (isGenericMemberAccessName(arg.methodFullName)
                    || (TrackingPointMethodsBase.experimentalCastAsMemberAccess && arg.methodFullName == Operators.cast)) =>
              Some(arg)
            case _ => None
          }
          .toList
        if (rhs.length == 1) Some(rhs.head)
        else None
      case _ => None
    }
  }

}
