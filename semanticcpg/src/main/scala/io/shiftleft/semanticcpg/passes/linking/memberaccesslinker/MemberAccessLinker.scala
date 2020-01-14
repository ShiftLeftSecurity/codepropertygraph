package io.shiftleft.semanticcpg.passes.linking.memberaccesslinker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, Operators, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.generated.nodes.{HasArgumentIndex, Identifier}
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.tinkerpop.gremlin.structure.Direction
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

/**
  * This pass has Linker as prerequisite.
  */
class MemberAccessLinker(cpg: Cpg) extends CpgPass(cpg) {
  import MemberAccessLinker.logger

  private[this] var loggedDeprecationWarning: Boolean = _

  override def run(): Iterator[DiffGraph] = {
    loggedDeprecationWarning = false
    val memberAccessIterator = cpg.call
      .filter(
        _.nameExact(Operators.memberAccess, Operators.indirectMemberAccess,
          Operators.fieldAccess, Operators.indirectFieldAccess, Operators.getElementPtr)
      ).toList
    val cache = collection.mutable.Map.empty[Tuple2[nodes.Type, String], nodes.Member]
    val dstGraph = DiffGraph.newBuilder // don't use parallel executor, caching is better
    memberAccessIterator.map(ma => resolve(dstGraph, cache, ma))
    List(dstGraph.build()).iterator
  }

  private def resolve(diffGraph: DiffGraph.Builder, cache: collection.mutable.Map[Tuple2[nodes.Type, String], nodes.Member], call: nodes.Call): Unit = {
    if (call._refOut.hasNext && !loggedDeprecationWarning) {
      logger.warn(
        s"Using deprecated CPG format with alreay existing REF edge between" +
          s" a member access node and a member.")
      loggedDeprecationWarning = true
    }
    try {
      // fixme: use argumentIndex once deprecation is over
      val args = call._argumentOut.asScala.toList.asInstanceOf[List[nodes.HasOrder]].sortBy {
        _.order
      }.asInstanceOf[List[nodes.StoredNode]]
      if (args.size != 2) throw new RuntimeException("member/field access-style operators need two arguments")
      val typ = args(0)._evalTypeOut.nextOption match {
        case Some(t: nodes.Type) => t
        case _ => return
      }

      val fieldref: String = call.name match {
        case Operators.memberAccess | Operators.indirectMemberAccess =>
          args(1) match {
            case id: nodes.Identifier => id.name
            case _ => throw new RuntimeException("memberAccess needs Identifier as second argument")
          }

        case Operators.fieldAccess | Operators.indirectIndexAccess | Operators.getElementPtr =>
          args(1) match {
            case id: nodes.FieldIdentifier => id.code // we intentionally don't use the CANONICAL_NAME field here.
            case lit: nodes.Literal => lit.code
            case _ => return
          }
        case _ => return
      }
      val member = getMember(cache, typ, fieldref, 0)
      if (member != null) {
        diffGraph.addEdgeInOriginal(call, member, EdgeTypes.REF)

      }
    } catch {
      case exception: Exception =>
        logger.warn(
          s"Error while obtaining IDENTIFIER associated to member access at ${call}" +
            s" Reason: ${exception.getMessage}")
    }
  }

  private def getMember(cache: collection.mutable.Map[Tuple2[nodes.Type, String], nodes.Member], typ: nodes.Type, name: String, depth: Int = 0): nodes.Member = {
    if (depth > 100) {
      logger.warn("Maximum depth for member access resolution exceeded on type=${typ.fullName}, member=$name. Recursive inheritance?")
      return null
    }
    cache.getOrElse((typ, name), {
      cache.update((typ, name), null)
      val members = typ.start.member.filter(_.nameExact(name)).l
      val res = if (members.size > 0) {
        cache.update((typ, name), members.head)
        members.head
      } else {
        val recursive_res = typ.start.baseType.l.map { basetyp => getMember(cache, basetyp, name, depth + 1) }
          .filter {
            _ != null
          }

        if (recursive_res.size > 0) {
          cache.update((typ, name), recursive_res.head)
          recursive_res.head
        } else null
      }
      if (depth == 0 && res == null) {
        logger.warn(s"Could not find type member. type=${typ.fullName}, member=$name")
      }
      res
    })
  }
}

object MemberAccessLinker {
  private val logger: Logger = LogManager.getLogger(classOf[MemberAccessLinker])
}
