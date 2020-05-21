package io.shiftleft.semanticcpg.passes.linking.linker

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeyNames, NodeTypes, nodes}
import io.shiftleft.passes.CpgPassBase
import gremlin.scala._

import scala.jdk.CollectionConverters._
import scala.collection.mutable

class LinkerPass(cpg: Cpg) extends CpgPassBase {

  class Maps {
    val typeDeclFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]
    val typeFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]
    val methodFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]
    val namespaceBlockFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]

    cpg.graph.graph.vertices().asScala.foreach {
      case node: nodes.TypeDecl       => typeDeclFullNameToNode += node.fullName -> node
      case node: nodes.Type           => typeFullNameToNode += node.fullName -> node
      case node: nodes.Method         => methodFullNameToNode += node.fullName -> node
      case node: nodes.NamespaceBlock => namespaceBlockFullNameToNode += node.fullName -> node
      case _                          => // ignore
    }

  }

  class RefEdgePass(cpg: Cpg, typeDeclFullNameToNode: mutable.Map[String, nodes.StoredNode])
      extends LinkToSinglePass(
        cpg,
        srcLabels = List(NodeTypes.TYPE),
        dstNodeLabel = NodeTypes.TYPE_DECL,
        edgeType = EdgeTypes.REF,
        dstNodeMap = typeDeclFullNameToNode,
        dstFullNameKey = nodes.Type.PropertyNames.TypeDeclFullName
      )

  class EvalTypePass(cpg: Cpg, typeFullNameToNode: mutable.Map[String, nodes.StoredNode])
      extends LinkToSinglePass(
        cpg,
        srcLabels = List(
          NodeTypes.METHOD_PARAMETER_IN,
          NodeTypes.METHOD_PARAMETER_OUT,
          NodeTypes.METHOD_RETURN,
          NodeTypes.MEMBER,
          NodeTypes.LITERAL,
          NodeTypes.CALL,
          NodeTypes.LOCAL,
          NodeTypes.IDENTIFIER,
          NodeTypes.BLOCK,
          NodeTypes.METHOD_REF,
          NodeTypes.UNKNOWN
        ),
        dstNodeLabel = NodeTypes.TYPE,
        edgeType = EdgeTypes.EVAL_TYPE,
        dstNodeMap = typeFullNameToNode,
        dstFullNameKey = "TYPE_FULL_NAME",
      )

  class MethodRefsPass(cpg: Cpg, methodFullNameToNode: mutable.Map[String, nodes.StoredNode])
      extends LinkToSinglePass(
        cpg,
        srcLabels = List(NodeTypes.METHOD_REF),
        dstNodeLabel = NodeTypes.METHOD,
        edgeType = EdgeTypes.REF,
        dstNodeMap = methodFullNameToNode,
        dstFullNameKey = nodes.MethodRef.PropertyNames.MethodFullName
      )

  class InheritsFromPass(cpg: Cpg, typeFullNameToNode: mutable.Map[String, nodes.StoredNode])
      extends LinkToMultiplePass(
        cpg,
        srcLabels = List(NodeTypes.TYPE_DECL),
        dstNodeLabel = NodeTypes.TYPE,
        edgeType = EdgeTypes.INHERITS_FROM,
        dstNodeMap = typeFullNameToNode,
        getDstFullNames = (srcNode: nodes.TypeDecl) => {
          if (srcNode.inheritsFromTypeFullName != null) {
            srcNode.inheritsFromTypeFullName
          } else {
            Seq()
          }
        },
        dstFullNameKey = nodes.TypeDecl.PropertyNames.InheritsFromTypeFullName
      )

  class AliasOfPass(cpg: Cpg, typeFullNameToNode: mutable.Map[String, nodes.StoredNode])
      extends LinkToMultiplePass(
        cpg,
        srcLabels = List(NodeTypes.TYPE_DECL),
        dstNodeLabel = NodeTypes.TYPE,
        edgeType = EdgeTypes.ALIAS_OF,
        dstNodeMap = typeFullNameToNode,
        getDstFullNames = (srcNode: nodes.TypeDecl) => {
          srcNode.aliasTypeFullName
        },
        dstFullNameKey = NodeKeyNames.ALIAS_TYPE_FULL_NAME,
      )

  override def createAndApply(): Unit = {
    val maps = new Maps()
    new RefEdgePass(cpg, maps.typeDeclFullNameToNode).createAndApply()
    new EvalTypePass(cpg, maps.typeFullNameToNode).createAndApply()
    new MethodRefsPass(cpg, maps.methodFullNameToNode).createAndApply()
    new InheritsFromPass(cpg, maps.typeFullNameToNode).createAndApply()
    new AliasOfPass(cpg, maps.typeFullNameToNode).createAndApply()
  }

  override def createApplySerializeAndStore(serializedCpg: SerializedCpg, inverse: Boolean, prefix: String): Unit = {
    val maps = new Maps()
    new RefEdgePass(cpg, maps.typeDeclFullNameToNode).createApplySerializeAndStore(serializedCpg, inverse, prefix)
    new EvalTypePass(cpg, maps.typeFullNameToNode).createApplySerializeAndStore(serializedCpg, inverse, prefix)
    new MethodRefsPass(cpg, maps.methodFullNameToNode).createApplySerializeAndStore(serializedCpg, inverse, prefix)
    new InheritsFromPass(cpg, maps.typeFullNameToNode).createApplySerializeAndStore(serializedCpg, inverse, prefix)
    new AliasOfPass(cpg, maps.typeFullNameToNode).createApplySerializeAndStore(serializedCpg, inverse, prefix)
  }
}
