package io.shiftleft.cpgserver

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.diffgraph.DiffGraph

class TestServerImpl extends ServerImpl {

  override def cpg: Option[Cpg] = _cpg
  var _cpg : Option[Cpg] = None

  override def createCpg(filenames: List[String]): Unit = {
    _cpg = Some(new Cpg())

    implicit val diffGraph = new DiffGraph
    new nodes.NewMethod(name = "main").start.store
    diffGraph.applyDiff(_cpg.get.scalaGraph)

  }
}
