package io.shiftleft.semanticcpg.langv2.types.expressions.generalizations

import io.shiftleft.codepropertygraph.generated.nodes.{AstNode, Expression}
import io.shiftleft.semanticcpg.langv2._
import overflowdb.traversal.help.Doc

import scala.collection.IterableOnceOps
import scala.jdk.CollectionConverters._


trait AstNodeTraversalImplicits {
  implicit def toAstNodeTraversalSingle[I <: AstNode](trav: I) = {
    new AstNodeTraversal[I, SingleTravTypes](trav: Single[I])
  }
  implicit def toAstNodeTraversalGeneric[I <: AstNode, T[_] <: SupportedTypes[_]](trav: TravTypesFor[T]#Collection[I]) = {
    new AstNodeTraversal[I, TravTypesFor[T]](trav)
  }
  implicit def toAstNodeTraversalIterOnceOps[I <: AstNode, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new AstNodeTraversal[I, IterableOnceOpsTravTypes[CC, C]](trav)
  }

}

class AstNodeTraversal[I <: AstNode, TT <: TravTypes](val trav: TT#Collection[I])
  extends AnyVal {

  /**
    * Nodes of the AST rooted in this node, including the node itself.
    * */
  @Doc("All nodes of the abstract syntax tree")
  def ast(implicit ops1: TravOps[TT]) =
    trav.rFlatMap(_._astOut.asScala.asInstanceOf[Iterator[I]], _.emit)

  /**
    * Traverse only to AST nodes that are expressions
    * */
  def isExpression(implicit ops1: TravOps[TT]) =
    trav.collectAll[Expression]

}

