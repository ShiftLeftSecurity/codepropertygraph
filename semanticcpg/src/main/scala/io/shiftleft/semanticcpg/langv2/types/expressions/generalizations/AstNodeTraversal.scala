package io.shiftleft.semanticcpg.langv2.types.expressions.generalizations

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Expression}
import io.shiftleft.semanticcpg.langv2._
import overflowdb.traversal.help.Doc

import scala.collection.IterableOnceOps
import scala.jdk.CollectionConverters._


trait AstNodeTraversalImplicits {
  implicit def toAstNodeTraversalSingle[I <: AstNode](trav: I): AstNodeTraversal[I, Single] = {
    new AstNodeTraversal(trav: Single[I])
  }
  implicit def toAstNodeTraversalGeneric[I <: AstNode, IT[_]](trav: IT[I]): AstNodeTraversal[I, IT] = {
    new AstNodeTraversal(trav)
  }

}

class AstNodeTraversal[I <: AstNode, IT[_]](val trav: IT[I])
  extends AnyVal {

  /**
    * Nodes of the AST rooted in this node, including the node itself.
    * */
  @Doc("All nodes of the abstract syntax tree")
  def ast(implicit ops1: TravOps[IT]) =
    trav.rFlatMap(_._astOut.asScala.asInstanceOf[Iterator[I]], _.emit)

  /**
    * Traverse only to AST nodes that are expressions
    * */
  def isExpression(implicit ops1: TravOps[IT]) =
    trav.collectAll[Expression]

}

