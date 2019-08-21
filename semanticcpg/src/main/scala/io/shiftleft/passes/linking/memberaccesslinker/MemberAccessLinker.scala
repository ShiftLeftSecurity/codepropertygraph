package io.shiftleft.passes.linking.memberaccesslinker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, Operators, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph, ParallelIteratorExecutor}
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.queryprimitives.Implicits._
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

/**
  * This pass has Linker as prerequisite.
  */
class MemberAccessLinker(cpg: Cpg) extends CpgPass(cpg) {
  import MemberAccessLinker.logger
  private var loggedDeprecationWarning: Boolean = _
  private var loggedForTypeMemberCombination: Set[(nodes.Type, String)] = _

  override def run(): Iterator[DiffGraph] = {
    loggedDeprecationWarning = false
    loggedForTypeMemberCombination = Set[(nodes.Type, String)]()

    val memberAccessIterator = cpg.call
      .filter(
        _.nameExact(Operators.memberAccess, Operators.indirectMemberAccess)
      )
      .toIterator

    new ParallelIteratorExecutor(memberAccessIterator).map(perMemberAccess)
  }

  private def perMemberAccess(call: nodes.Call): DiffGraph = {
    val dstGraph = new DiffGraph()

    if (!call.edges(Direction.OUT, EdgeTypes.REF).hasNext) {
      try {
        val memberName = call
          .vertices(Direction.OUT, EdgeTypes.AST)
          .asScala
          .filter(_.value(NodeKeys.ORDER.name) == 2)
          .asJava
          .nextChecked
          .asInstanceOf[nodes.Identifier]
          .name

        val typ = getTypeOfMemberAccessBase(call)

        var worklist = List(typ)
        var finished = false
        while (!finished && worklist.nonEmpty) {
          val typ = worklist.head
          worklist = worklist.tail

          findMemberOnType(typ, memberName) match {
            case Some(member) =>
              dstGraph.addEdgeInOriginal(call, member, EdgeTypes.REF)
              finished = true
            case None =>
              val baseTypes = typ.start.baseType.l
              worklist = worklist ++ baseTypes
          }
        }

        if (!finished && !loggedForTypeMemberCombination.contains((typ, memberName))) {
          loggedForTypeMemberCombination += ((typ, memberName))
          logger.warn(s"Could not find type member. type=${typ.fullName}, member=$memberName")
        }
      } catch {
        case exception: Exception =>
          logger.warn(
            s"Error while obtaining IDENTIFIER associated to member access." +
              s" Reason: ${exception.getMessage}")
      }
    } else if (!loggedDeprecationWarning) {
      logger.warn(
        s"Using deprecated CPG format with alreay existing REF edge between" +
          s" a member access node and a member.")
      loggedDeprecationWarning = true
    }
    dstGraph
  }

  private def getTypeOfMemberAccessBase(call: nodes.Call): nodes.Type = {
    val base = call.start.argument.order(1).head
    base match {
      case call: nodes.Call
          if call.name == Operators.memberAccess ||
            call.name == Operators.indirectComputedMemberAccess =>
        call.start.argument.order(2).typ.head
      case node: nodes.Expression =>
        node.start.typ.head

    }
  }

  private def findMemberOnType(typ: nodes.Type, memberName: String): Option[nodes.Member] = {
    val members = typ.start.member.filter(_.nameExact(memberName)).l

    members.headOption
  }
}

object MemberAccessLinker {
  private val logger: Logger = LogManager.getLogger(classOf[MemberAccessLinker])
}
