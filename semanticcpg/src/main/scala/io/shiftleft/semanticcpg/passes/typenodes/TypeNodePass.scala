package io.shiftleft.semanticcpg.passes.typenodes

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.{CpgPass, DiffGraph, KeyPool}

/**
  * Creates a `TYPE` node for each type in `usedTypes`
  * */
class TypeNodePass(usedTypes: List[String], keyPool: Option[KeyPool] = None)
    extends CpgPass("types", keyPool) {
  override def run(): Iterator[DiffGraph] = {
    val diffGraph = DiffGraph.newBuilder
    usedTypes.sorted.foreach { typeName =>
      val shortName = typeName.split('.').lastOption.getOrElse(typeName)
      val node = nodes
        .NewType()
        .name(shortName)
        .fullName(typeName)
        .typeDeclFullName(typeName)
      diffGraph.addNode(node)
    }
    Iterator(diffGraph.build())
  }
}
