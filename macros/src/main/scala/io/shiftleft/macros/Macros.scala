package io.shiftleft.macros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import io.shiftleft.codepropertygraph.Cpg
import overflowdb.traversal.Traversal

object QueryMacros {
  def queryInit(
    name: String,
    author: String,
    title: String,
    description: String,
    score: Double,
    traversal: Cpg => Traversal[_]): String = macro queryInitImpl

  def queryInitImpl(c: Context)(
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
