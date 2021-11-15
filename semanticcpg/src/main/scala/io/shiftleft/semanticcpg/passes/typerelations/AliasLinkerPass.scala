package io.shiftleft.semanticcpg.passes.typerelations

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.TypeDecl
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, PropertyNames}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.passes.callgraph.MethodRefLinker.{linkToMultiple, typeFullNameToNode}

import scala.concurrent.ExecutionContext

class AliasLinkerPass(cpg: Cpg) extends CpgPass(cpg) {
  override def run()(implicit ec: ExecutionContext): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder
    // Create ALIAS_OF edges from TYPE_DECL nodes to
    // TYPE
    linkToMultiple(
      cpg,
      srcLabels = List(NodeTypes.TYPE_DECL),
      dstNodeLabel = NodeTypes.TYPE,
      edgeType = EdgeTypes.ALIAS_OF,
      dstNodeMap = typeFullNameToNode(cpg, _),
      getDstFullNames = (srcNode: TypeDecl) => {
        srcNode.aliasTypeFullName
      },
      dstFullNameKey = PropertyNames.ALIAS_TYPE_FULL_NAME,
      dstGraph
    )
    Iterator(dstGraph.build())
  }
}
