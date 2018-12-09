package io.shiftleft.passes.linking.memberaccesslinker

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.diffgraph.DiffGraph
import io.shiftleft.passes.CpgPass
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.starters.Cpg
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.Expression.marshaller
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

class MemberAccessLinker(graph: ScalaGraph) extends CpgPass(graph) {
  implicit val javaGraph = graph.graph

  override def run(): Unit = {
    linkMemberAccessToMember()
  }

  private def linkMemberAccessToMember(): Unit = {
    var loggedDeprecationWarning = false
    val cpg = Cpg(javaGraph)

    cpg.call
      .filter(
        _.nameExact(Operators.memberAccess, Operators.indirectMemberAccess)
      )
      .sideEffect { call =>
        if (!call.underlying.edges(Direction.OUT, EdgeTypes.REF).hasNext) {
          val memberName = call.underlying
            .vertices(Direction.OUT, EdgeTypes.AST)
            .asScala
            .filter(_.value2(NodeKeys.ORDER) == 2)
            .next
            .value2(NodeKeys.NAME)

          val typ: nodes.Type = getTypeOfMemberAccessBase(call)

          var worklist = List(typ)
          var finished = false
          while (!finished && worklist.nonEmpty) {
            val typ = worklist.head
            worklist = worklist.tail

            findMemberOnType(typ, memberName) match {
              case Some(member) =>
                dstGraph.addEdgeInOriginal(call.underlying, member.underlying, EdgeTypes.REF)
                finished = true
              case None =>
                val baseTypes = typ.start.baseType.toList
                worklist = worklist ++ baseTypes
            }
          }

          if (!finished) {
            logger.error(s"Could not find type member. type=${typ.fullName}, member=$memberName")
          }
        } else if (!loggedDeprecationWarning) {
          logger.warn(
            s"Using deprecated CPG format with alreay existing REF edge between" +
              s" a member access node and a member.")
          loggedDeprecationWarning = true
        }
      }
  }.iterate()

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
    val members = typ.start.member.filter(_.nameExact(memberName)).toList
    members.find(_.name == memberName)
  }
}
