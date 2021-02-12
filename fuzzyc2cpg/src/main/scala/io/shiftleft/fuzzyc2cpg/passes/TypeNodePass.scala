package io.shiftleft.fuzzyc2cpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.{CpgPass, DiffGraph, KeyPool}
import io.shiftleft.codepropertygraph.generated.nodes

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
