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
                 language: String = "") {
  def make(name: String,
           author: String,
           title: String,
           description: String,
           score: Double,
           traversalWithStrRep: TraversalWithStrRep,
           tags: List[String] = List()): Query = {
    Query(
      name = name,
      author = author,
      title = title,
      description = description,
      score = score,
      traversal = traversalWithStrRep.traversal,
      traversalAsString = traversalWithStrRep.strRep,
      tags = tags,
    )
  }
}

case class TraversalWithStrRep(traversal: Cpg => Traversal[nodes.StoredNode], strRep: String = "")
