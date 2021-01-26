package io.shiftleft.macros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.util.matching._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal

object QueryMacros {
  def queryInit(
    name: String,
    author: String,
    title: String,
    description: String,
    score: Double,
    traversal: Cpg => Traversal[nodes.StoredNode]): Unit = macro queryInitImpl

  def queryInitImpl(c: Context)(
    name: c.Tree,
    author: c.Tree,
    title: c.Tree,
    description: c.Tree,
    score: c.Tree,
    traversal: c.Tree) : c.Expr[Unit] = {
    import c.universe._
    val fileContent = new String(traversal.pos.source.content)
    val start = traversal.pos.start
    val end = traversal.pos.end
    val traversalAsString: String = fileContent.slice(start, end)

    val one = q"val traversal: Cpg => overflowdb.traversal.Traversal[io.shiftleft.codepropertygraph.generated.nodes.StoredNode] = { $traversalAsString }"
    // println(one)
    // q"""
    //   println($one)
    //   ???
    // """
    // c.Expr[Unit](q"""
    //   println($one)
    //   ???
    // """)

    // doesn't work:
    // q"""
    //   val query = Query(name = $name, author = $author, title = $title, description = $description, score = $score,
    //     $traversalAsString
    //   )
    //   ???
    // """

    // this works:
    // q"""
    //   val query = Query(name = $name, author = $author, title = $title, description = $description, score = $score,
    //     cpg => cpg.method.l
    //   )
    //   ???
    // """
    // this works:
    // q"""println($traversalAsString)"""
  }
}
