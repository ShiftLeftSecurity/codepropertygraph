package io.shiftleft.passes.linking.memberaccesslinker

import gremlin.scala.ScalaGraph
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, Operators, nodes}
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.CpgPass
import io.shiftleft.queryprimitives.steps.Implicits.JavaIteratorDeco
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

class MemberAccessLinker(graph: ScalaGraph) extends CpgPass(graph) {

  override def run() = {
    val dstGraph = new DiffGraph
    linkMemberAccessToMember(dstGraph)
    Stream(dstGraph)
  }

  private def linkMemberAccessToMember(dstGraph: DiffGraph): Unit = {
    var loggedDeprecationWarning = false
    var loggedForTypeMemberCombination = Set[(nodes.Type, String)]()
    val cpg = Cpg(graph.graph)

    cpg.call
      .filter(
        _.nameExact(Operators.memberAccess, Operators.indirectMemberAccess)
      )
      .sideEffect { call =>
        if (!call.edges(Direction.OUT, EdgeTypes.REF).hasNext) {
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
        } else if (!loggedDeprecationWarning) {
          logger.warn(
            s"Using deprecated CPG format with alreay existing REF edge between" +
              s" a member access node and a member.")
          loggedDeprecationWarning = true
        }
      }
  }.exec()

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

    members.find(_.name == memberName)
  }
}
