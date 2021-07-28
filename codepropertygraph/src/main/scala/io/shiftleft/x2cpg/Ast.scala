package io.shiftleft.x2cpg

import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.NewNode
import io.shiftleft.passes.DiffGraph

case class AstEdge(src: NewNode, dst: NewNode)

object Ast {
  def apply(node: NewNode): Ast = Ast(List(node))
  def apply(): Ast = new Ast(List())

  /** Copy nodes/edges of given `AST` into the given `diffGraph`.
    */
  def storeInDiffGraph(ast: Ast, diffGraph: DiffGraph.Builder): Unit = {
    ast.nodes.foreach { node =>
      diffGraph.addNode(node)
    }
    ast.edges.foreach { edge =>
      diffGraph.addEdge(edge.src, edge.dst, EdgeTypes.AST)
    }
    ast.conditionEdges.foreach { edge =>
      diffGraph.addEdge(edge.src, edge.dst, EdgeTypes.CONDITION)
    }
    ast.receiverEdges.foreach { edge =>
      diffGraph.addEdge(edge.src, edge.dst, EdgeTypes.RECEIVER)
    }
    ast.refEdges.foreach { edge =>
      diffGraph.addEdge(edge.src, edge.dst, EdgeTypes.REF)
    }
    ast.argEdges.foreach { edge =>
      diffGraph.addEdge(edge.src, edge.dst, EdgeTypes.ARGUMENT)
    }
  }

}

case class Ast(nodes: List[NewNode],
               edges: List[AstEdge] = List(),
               conditionEdges: List[AstEdge] = List(),
               refEdges: List[AstEdge] = List(),
               receiverEdges: List[AstEdge] = List(),
               argEdges: List[AstEdge] = List()) {

  def root: Option[NewNode] = nodes.headOption

  def rightMostLeaf: Option[NewNode] = nodes.lastOption

  /** AST that results when adding `other` as a child to this AST.
    * `other` is connected to this AST's root node.
    */
  def withChild(other: Ast): Ast = {
    Ast(
      nodes ++ other.nodes,
      edges = edges ++ other.edges ++ root.toList.flatMap(r =>
        other.root.toList.map { rc =>
          AstEdge(r, rc)
      }),
      conditionEdges = conditionEdges ++ other.conditionEdges,
      argEdges = argEdges ++ other.argEdges,
      receiverEdges = receiverEdges ++ other.receiverEdges,
      refEdges = refEdges ++ other.refEdges
    )
  }

  /** AST that results when adding all ASTs in `asts` as children,
    * that is, connecting them to the root node of this AST.
    */
  def withChildren(asts: Seq[Ast]): Ast = {
    asts.headOption match {
      case Some(head) =>
        withChild(head).withChildren(asts.tail)
      case None =>
        this
    }
  }

  def withConditionEdge(src: NewNode, dst: NewNode): Ast = {
    this.copy(conditionEdges = conditionEdges ++ List(AstEdge(src, dst)))
  }

  def withRefEdge(src: NewNode, dst: NewNode): Ast = {
    this.copy(refEdges = refEdges ++ List(AstEdge(src, dst)))
  }

  def withReceiverEdge(src: NewNode, dst: NewNode): Ast = {
    this.copy(receiverEdges = receiverEdges ++ List(AstEdge(src, dst)))
  }

  def withArgEdge(src: NewNode, dst: NewNode): Ast = {
    this.copy(argEdges = argEdges ++ List(AstEdge(src, dst)))
  }

  def withArgEdges(src: NewNode, dsts: Seq[NewNode]): Ast = {
    this.copy(argEdges = argEdges ++ dsts.map { d =>
      AstEdge(src, d)
    })
  }

}
