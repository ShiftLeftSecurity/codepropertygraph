package io.shiftleft.semanticcpg.passes.typenodes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.{CpgPass, DiffGraph, KeyPool}

class TypeNodePass(usedTypes: List[String], cpg: Cpg, keyPool: Option[KeyPool] = None)
    extends CpgPass(cpg, "types", keyPool) {
  override def run(): Iterator[DiffGraph] = {
    val diffGraph = DiffGraph.newBuilder
    usedTypes.sorted.foreach { typeName =>
      val node = nodes
        .NewType()
        .name(typeName)
        .fullName(typeName)
        .typeDeclFullName(typeName)
      diffGraph.addNode(node)
    }
    Iterator(diffGraph.build())
  }
}
