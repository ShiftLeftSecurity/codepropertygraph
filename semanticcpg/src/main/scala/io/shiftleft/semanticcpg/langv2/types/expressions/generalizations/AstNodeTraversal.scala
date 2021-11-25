package io.shiftleft.semanticcpg.langv2.types.expressions.generalizations

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Expression}
import io.shiftleft.semanticcpg.langv2._
import overflowdb.traversal.help.Doc

import scala.collection.IterableOnceOps
import scala.jdk.CollectionConverters._


trait AstNodeTraversalImplicits {
  implicit def toAstNodeTraversalSingle[I <: AstNode](in: I): AstNodeTraversal[I, Single, Nothing] = {
    new AstNodeTraversal(in: Single[I])
  }
  implicit def toAstNodeTraversalOption[I <: AstNode](in: Option[I]): AstNodeTraversal[I, Option, Nothing] = {
    new AstNodeTraversal(in)
  }
  implicit def toAstNodeTraversalIter[I <: AstNode, CC[_], C](in: IterableOnceOps[I, CC, C])
  : AstNodeTraversal[I, ({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]] = {
    new AstNodeTraversal[I, ({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]](in)
  }

}

class AstNodeTraversal[I <: AstNode, IT[_], Marker](val in: IT[I])
  extends AnyVal {

  /**
    * Nodes of the AST rooted in this node, including the node itself.
    * */
  @Doc("All nodes of the abstract syntax tree")
  def ast(implicit applier: ToMany[IT, Marker]) =
    in.rFlatMap(_._astOut.asScala.asInstanceOf[Iterator[I]], _.emit)

  /**
    * Traverse only to AST nodes that are expressions
    * */
  def isExpression(implicit applier: ToBoolean[IT, Marker]) =
    in.collectAll[Expression]

}

