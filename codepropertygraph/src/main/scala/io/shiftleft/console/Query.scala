package io.shiftleft.console

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.traversal.Traversal

case class Query(name: String,
                 author: String,
                 title: String,
                 description: String,
                 score: Double,
                 traversal: Cpg => Traversal[nodes.StoredNode],
                 traversalAsString: String = "",
                 tags: List[String] = List(),
                 language: String = "")

case class TraversalWithMeta(traversal: Cpg => Traversal[nodes.StoredNode], traversalAsString: String = "")

object QueryFactory {
  def create(name: String,
             author: String,
             title: String,
             description: String,
             score: Double,
             traversalWithMeta: TraversalWithMeta,
             tags: List[String] = List()): Query = {
    Query(
      name = name,
      author = author,
      title = title,
      description = description,
      score = score,
      traversal = traversalWithMeta.traversal,
      traversalAsString = traversalWithMeta.traversalAsString,
      tags = tags,
    )
  }
}
