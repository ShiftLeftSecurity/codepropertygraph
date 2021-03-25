package io.shiftleft.macros

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.console.{Query, TraversalWithStrRep}
import overflowdb.traversal.Traversal

import scala.language.experimental.macros
import scala.reflect.macros.whitebox

object QueryMacros {

  def withStrRep(traversal: Cpg => Traversal[nodes.StoredNode]): TraversalWithStrRep = macro withStrRepImpl

  def withStrRepImpl(c: whitebox.Context)(traversal: c.Tree): c.Expr[TraversalWithStrRep] = {
    import c.universe._
    val fileContent = new String(traversal.pos.source.content)
    val start = traversal.pos.start
    val end = traversal.pos.end
    val traversalAsString: String = fileContent.slice(start, end)

    c.Expr(
      q"""
        TraversalWithStrRep($traversal, $traversalAsString)
       """
    )
  }

  def queryInit(name: String,
                author: String,
                title: String,
                description: String,
                score: Double,
                traversal: Cpg => Traversal[nodes.StoredNode],
                tags: List[String]): Query = macro queryInitImpl

  def queryInitImpl(c: whitebox.Context)(name: c.Tree,
                                         author: c.Tree,
                                         title: c.Tree,
                                         description: c.Tree,
                                         score: c.Tree,
                                         traversal: c.Tree,
                                         tags: c.Tree): c.Expr[Query] = {
    import c.universe._
    val fileContent = new String(traversal.pos.source.content)
    val start = traversal.pos.start
    val end = traversal.pos.end
    val traversalAsString: String = fileContent.slice(start, end)

    c.Expr(
      q"""
        Query(
          name = $name,
          author = $author,
          title = $title,
          description = $description,
          score = $score,
          traversal = $traversal,
          traversalAsString = $traversalAsString,
          tags = $tags
        )
     """
    )
  }
}
