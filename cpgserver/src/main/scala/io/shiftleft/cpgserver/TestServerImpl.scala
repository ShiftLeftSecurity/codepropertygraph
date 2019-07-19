package io.shiftleft.cpgserver

import gremlin.scala.ScalaGraph
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.{CpgPass, DiffGraph}

class TestServerImpl extends ServerImpl {

  override def cpg: Option[Cpg] = _cpg
  var _cpg: Option[Cpg] = None

  override def createCpg(filenames: List[String]): Unit = {
    _cpg = Some(new Cpg())

    class MyPass(srcGraph: ScalaGraph) extends CpgPass(srcGraph) {

      override def run(): Iterator[DiffGraph] = {
        implicit val diffGraph: DiffGraph = new DiffGraph
        new nodes.NewMethod(name = "main").start.store
        Iterator(diffGraph)
      }
    }
    new MyPass(_cpg.get.graph).createAndApply()
  }
}
