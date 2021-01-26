package io.shiftleft.macros

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

object QueryMacros {

  def deserializeTraversal(src: String): Cpg => Traversal[_] = macro deserializeTraversalImpl

  def deserializeTraversalImpl(c: whitebox.Context)(src: c.Expr[String]): c.Expr[Cpg => Traversal[_]] = {
    import c.universe._
    val literal = src.tree.asInstanceOf[Literal]
    val srcString = literal.value.value.asInstanceOf[String]
    val parsedTree = c.parse(srcString)

    c.Expr[Cpg => Traversal[nodes.StoredNode]](q"$parsedTree")
  }


  def queryInit(
    name: String,
    author: String,
    title: String,
    description: String,
    score: Double,
    traversal: Cpg => Traversal[_]): String = macro queryInitImpl

  def queryInitImpl(c: whitebox.Context)(
    name: c.Tree,
    author: c.Tree,
    title: c.Tree,
    description: c.Tree,
    score: c.Tree,
    traversal: c.Tree) : c.Expr[String] = {
    import c.universe._
    val fileContent = new String(traversal.pos.source.content)
    val start = traversal.pos.start
    val end = traversal.pos.end
    val traversalAsString: String = fileContent.slice(start, end)

    // println(traversalAsString)
    c.Expr[String](q"""$traversalAsString""")
 }
}
