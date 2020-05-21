package io.shiftleft.semanticcpg.passes.linking.linker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes, nodes}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.passes.linking.linker.LinkAstChildAndParentPass.ChildType
import gremlin.scala._
import org.apache.tinkerpop.gremlin.structure.Direction
import io.shiftleft.Implicits.JavaIteratorDeco
import org.apache.logging.log4j.{LogManager, Logger}

object LinkAstChildAndParentPass {
  type ChildType = nodes.HasAstParentType with nodes.HasAstParentFullName with nodes.StoredNode
}

/**
  * For each node and type decl, check if there is an incoming AST edge.
  * If there is not, look up parent node according to `parentType` field
  * in the corresponding table and add an AST edge from parent to child
  * */
class LinkAstChildAndParentPass(cpg: Cpg, maps: Maps) extends ParallelCpgPass[ChildType](cpg) {

  private val logger: Logger = LogManager.getLogger(classOf[LinkAstChildAndParentPass])

  override def partIterator: Iterator[ChildType] =
    cpg.graph.V
      .hasLabel(NodeTypes.METHOD, NodeTypes.TYPE_DECL)
      .toIterator()
      .map(_.asInstanceOf[ChildType])

  override def runOnPart(astChild: ChildType): Option[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder
    try {
      astChild.edges(Direction.IN, EdgeTypes.AST).nextOption match {
        case None =>
          val astParentOption: Option[nodes.StoredNode] =
            astChild.astParentType match {
              case NodeTypes.METHOD          => maps.methodFullNameToNode.get(astChild.astParentFullName)
              case NodeTypes.TYPE_DECL       => maps.typeDeclFullNameToNode.get(astChild.astParentFullName)
              case NodeTypes.NAMESPACE_BLOCK => maps.namespaceBlockFullNameToNode.get(astChild.astParentFullName)
              case _ =>
                logger.error(
                  s"Invalid AST_PARENT_TYPE=${astChild.valueOption(NodeKeys.AST_PARENT_FULL_NAME)};" +
                    s" astChild LABEL=${astChild.label};" +
                    s" astChild FULL_NAME=${astChild.valueOption(NodeKeys.FULL_NAME)}")
                None
            }

          astParentOption match {
            case Some(astParent) =>
              dstGraph.addEdgeInOriginal(astParent, astChild, EdgeTypes.AST)
            case None =>
              Linker.logFailedSrcLookup(EdgeTypes.AST,
                                        astChild.astParentType,
                                        astChild.astParentFullName,
                                        astChild.label,
                                        astChild.id.toString())
          }
        case _ =>
      }
    } catch {
      case _: NoSuchElementException =>
        logger.info("No such element in `linkAstChildToParent`. Tinkerpop used to not tell us, now we know.")
    }
    Some(dstGraph.build())
  }

}
