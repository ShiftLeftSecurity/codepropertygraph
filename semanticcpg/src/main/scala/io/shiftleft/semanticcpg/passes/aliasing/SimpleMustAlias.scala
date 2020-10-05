package io.shiftleft.semanticcpg.passes.aliasing
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import scala.jdk.CollectionConverters._
import io.shiftleft.semanticcpg.utils.MemberAccess.isGenericMemberAccessName

class AntiAliasingPass(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    val builder = DiffGraph.newBuilder
    cpg.local.iterator.foreach(processLocal(_, builder))
    val res = builder.build
    println(s"antiAlias pairs: ${res.size} from ${cpg.local.size}")
    Iterator(builder.build())
  }

  private def processLocal(local: nodes.Local, builder: DiffGraph.Builder): Unit = {
    val refs = local._identifierViaRefIn.toList
    if (refs.size != 2) return

    val (definitions, uses) = refs.map(id => (id, getAssignmentRhs(id))).span { _._2.isDefined }
    if (definitions.size == 1 && uses.size == 1) {
      val use = uses.head._1
      val definition = definitions.head._2.get
      builder.addEdge(src = definition, dst = use, edgeLabel = EdgeTypes.MUST_ALIAS)
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
            case arg: nodes.Call if arg.argumentIndex == 2 && isGenericMemberAccessName(arg.methodFullName) =>
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
class SimpleMustAlias {}
