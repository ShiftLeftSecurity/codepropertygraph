package io.shiftleft.cpgserver

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.{CpgPass, DiffGraph}

class TestServerImpl extends ServerImpl {

  override def cpg: Option[Cpg] = _cpg
  var _cpg: Option[Cpg] = None

  override def createCpg(filenames: List[String]): Unit = {
    _cpg = Some(new Cpg())

    class MyPass(cpg: Cpg) extends CpgPass(cpg) {

      override def run(): Iterator[DiffGraph] = {
        implicit val diffGraph: DiffGraph = new DiffGraph
        new nodes.NewMethod(name = "main", isExternal = false).start.store
        Iterator(diffGraph)
      }
    }
    new MyPass(_cpg.get).createAndApply()
  }
}
