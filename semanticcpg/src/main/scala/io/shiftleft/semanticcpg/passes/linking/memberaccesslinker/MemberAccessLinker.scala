package io.shiftleft.semanticcpg.passes.linking.memberaccesslinker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.traversal._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, Operators}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}
import overflowdb.traversal._

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
      .where(
        _.nameExact(Operators.memberAccess,
                    Operators.indirectMemberAccess,
                    Operators.fieldAccess,
                    Operators.indirectFieldAccess,
                    Operators.getElementPtr)
      )
      .l
    val cache = collection.mutable.Map.empty[(Type, String), Member]
    val dstGraph = DiffGraph.newBuilder // don't use parallel executor, caching is better
    memberAccessIterator.foreach(ma => resolve(dstGraph, cache, ma))
    List(dstGraph.build()).iterator
  }

  private def resolve(diffGraph: DiffGraph.Builder,
                      cache: collection.mutable.Map[(Type, String), Member],
                      call: Call): Unit = {
    if (call._refOut.hasNext && !loggedDeprecationWarning) {
      logger.warn(
        s"Using deprecated CPG format with already existing REF edge between" +
          s" a member access node and a member.")
      loggedDeprecationWarning = true
    }
    try {
      // fixme: use argumentIndex once deprecation is over
      val args = call._argumentOut.asScala.toList
        .asInstanceOf[List[HasOrder]]
        .sortBy {
          _.order
        }
        .asInstanceOf[List[StoredNode]]
      if (args.size != 2) throw new RuntimeException("member/field access-style operators need two arguments")
      val typ = args(0)._evalTypeOut.nextOption() match {
        case Some(t: Type) => t
        case _             => return
      }

      val fieldref: String = call.name match {
        case Operators.memberAccess | Operators.indirectMemberAccess =>
          args(1) match {
            case id: Identifier => id.name
            case _              => throw new RuntimeException("memberAccess needs Identifier as second argument")
          }

        case Operators.fieldAccess | Operators.indirectFieldAccess | Operators.getElementPtr =>
          args(1) match {
            case id: FieldIdentifier => id.code // we intentionally don't use the CANONICAL_NAME field here.
            case lit: Literal        => lit.code
            case _                   => return
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

  private def getMember(cache: collection.mutable.Map[(Type, String), Member],
                        typ: Type,
                        name: String,
                        depth: Int): Member = {
    if (depth > 100) {
      logger.warn(
        "Maximum depth for member access resolution exceeded on type=${typ.fullName}, member=$name. Recursive inheritance?")
      return null
    }
    cache.getOrElse(
      (typ, name), {
        cache.update((typ, name), null)
        val members = typ.member.where(_.nameExact(name)).l
        val res = if (members.nonEmpty) {
          cache.update((typ, name), members.head)
          members.head
        } else {
          val recursive_res = typ.baseType.l
            .map { basetyp =>
              getMember(cache, basetyp, name, depth + 1)
            }
            .filter(_ != null)

          if (recursive_res.nonEmpty) {
            cache.update((typ, name), recursive_res.head)
            recursive_res.head
          } else null
        }
        if (depth == 0 && res == null) {
          logger.warn(s"Could not find type member. type=${typ.fullName}, member=$name")
        }
        res
      }
    )
  }
}

object MemberAccessLinker {
  private val logger: Logger = LoggerFactory.getLogger(classOf[MemberAccessLinker])
}
