package io.shiftleft.semanticcpg.langv2.types.expressions.generalizations

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Expression}
import io.shiftleft.semanticcpg.langv2._
import overflowdb.traversal.help.Doc

import scala.collection.IterableOnceOps
import scala.jdk.CollectionConverters._


trait AstNodeTraversalImplicits {
  implicit def toAstNodeTraversalSingle[I <: AstNode](trav: I): AstNodeTraversal[I, Single, Nothing] = {
    new AstNodeTraversal(trav: Single[I])
  }
  implicit def toAstNodeTraversalGeneric[I <: AstNode](trav: Option[I]): AstNodeTraversal[I, Option, Nothing] = {
    new AstNodeTraversal(trav)
  }
  implicit def toAstNodeTraversalIterOnceOps[I <: AstNode, CC[_], C](trav: IterableOnceOps[I, CC, C])
  : AstNodeTraversal[I, ({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]] = {
    new AstNodeTraversal[I, ({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]](trav)
  }

}

class AstNodeTraversal[I <: AstNode, IT[_], Marker](val trav: IT[I])
  extends AnyVal {

  /**
    * Nodes of the AST rooted in this node, including the node itself.
    * */
  @Doc("All nodes of the abstract syntax tree")
  def ast(implicit ops1: TravOps[IT, Marker]) =
    trav.rFlatMap(_._astOut.asScala.asInstanceOf[Iterator[I]], _.emit)

  /**
    * Traverse only to AST nodes that are expressions
    * */
  def isExpression(implicit ops1: TravOps[IT, Marker]) =
    trav.collectAll[Expression]

}

