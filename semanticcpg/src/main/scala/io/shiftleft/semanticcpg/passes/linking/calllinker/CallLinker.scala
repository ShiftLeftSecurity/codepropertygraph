package io.shiftleft.semanticcpg.passes.linking.calllinker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.Implicits._
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

class CallLinker(cpg: Cpg) extends CpgPass(cpg){
  /**
    * Main method of enhancement - to be implemented by child class
    **/
  override def run(): Iterator[DiffGraph] = {
    val dstGraph = new DiffGraph()
    cpg.call.sideEffect { call =>
      try {
        linkCall(call, dstGraph)
      } catch {
        case exception: Exception =>
          throw new RuntimeException(exception)
      }
    }.exec()

    Iterator(dstGraph)
  }

  private def linkCall(call: nodes.Call, dstGraph: DiffGraph): Unit = {
    call.vertices(Direction.OUT, EdgeTypes.RECEIVER).nextOption.foreach { receiver =>
      val receiverTypeDecl = receiver.vertices(Direction.OUT, EdgeTypes.EVAL_TYPE).next
        .vertices(Direction.OUT, EdgeTypes.REF).next

      val resolvedMethodOption =
        receiverTypeDecl.vertices(Direction.OUT, EdgeTypes.BINDS).asScala.collectFirst {
          case binding: nodes.Binding if binding.name == call.name && binding.signature == call.signature =>
            binding.vertices(Direction.OUT, EdgeTypes.REF).nextChecked.asInstanceOf[nodes.Method]
        }

      resolvedMethodOption.foreach { method =>
        dstGraph.addEdgeInOriginal(call, method, EdgeTypes.CALL)
      }
    }
  }
}
