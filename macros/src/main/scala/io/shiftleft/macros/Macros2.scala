package io.shiftleft.macros

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal

object Macros2 {

  def deserializeTraversal(src: String): Cpg => Traversal[_] = macro deserializeTraversalImpl

  def deserializeTraversalImpl(c: whitebox.Context)(src: c.Expr[String]): c.Expr[Cpg => Traversal[_]] = {
    import c.universe._
    val src2 = src.tree.asInstanceOf[Literal]
    val src3 = src2.value.value.asInstanceOf[String]
    val src4 = c.parse(src3)

    c.Expr[Cpg => Traversal[nodes.StoredNode]](q"$src4")
  }
}
