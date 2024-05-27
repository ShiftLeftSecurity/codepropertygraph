package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForTemplateDom(val node: nodes.TemplateDom) extends AnyVal {

  /** Traverse to EXPRESSION via ARGUMENT IN edge.
    */
  def expressionViaArgumentIn: Iterator[nodes.Expression] = argumentIn.collectAll[nodes.Expression]

  /** Traverse to EXPRESSION via AST OUT edge.
    */
  def expressionViaAstOut: Iterator[nodes.Expression] = astOut.collectAll[nodes.Expression]

  /** Traverse to EXPRESSION via REACHING_DEF OUT edge.
    */
  def expressionViaReachingDefOut: Iterator[nodes.Expression] = reachingDefOut.collectAll[nodes.Expression]

  /** Traverse to FILE via CONTAINS IN edge.
    */
  def fileViaContainsIn: Iterator[nodes.File] = containsIn.collectAll[nodes.File]

  /** Traverse to METHOD via CONTAINS IN edge.
    */
  def methodViaContainsIn: Iterator[nodes.Method] = containsIn.collectAll[nodes.Method]

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: Iterator[nodes.Tag] = taggedByOut.collectAll[nodes.Tag]

  def argumentIn: Iterator[nodes.Expression] = node._argumentIn.cast[nodes.Expression]

  def astOut: Iterator[nodes.Expression] = node._astOut.cast[nodes.Expression]

  def containsIn: Iterator[nodes.AstNode] = node._containsIn.cast[nodes.AstNode]

  def reachingDefOut: Iterator[nodes.Expression] = node._reachingDefOut.cast[nodes.Expression]

  def taggedByOut: Iterator[nodes.Tag] = node._taggedByOut.cast[nodes.Tag]
}

final class AccessNeighborsForTemplateDomTraversal(val traversal: Iterator[nodes.TemplateDom]) extends AnyVal {

  /** Traverse to EXPRESSION via ARGUMENT IN edge.
    */
  def expressionViaArgumentIn: Iterator[nodes.Expression] = traversal.flatMap(_.expressionViaArgumentIn)

  /** Traverse to EXPRESSION via AST OUT edge.
    */
  def expressionViaAstOut: Iterator[nodes.Expression] = traversal.flatMap(_.expressionViaAstOut)

  /** Traverse to EXPRESSION via REACHING_DEF OUT edge.
    */
  def expressionViaReachingDefOut: Iterator[nodes.Expression] = traversal.flatMap(_.expressionViaReachingDefOut)

  /** Traverse to FILE via CONTAINS IN edge.
    */
  def fileViaContainsIn: Iterator[nodes.File] = traversal.flatMap(_.fileViaContainsIn)

  /** Traverse to METHOD via CONTAINS IN edge.
    */
  def methodViaContainsIn: Iterator[nodes.Method] = traversal.flatMap(_.methodViaContainsIn)

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.tagViaTaggedByOut)

  def argumentIn: Iterator[nodes.Expression] = traversal.flatMap(_.argumentIn)

  def astOut: Iterator[nodes.Expression] = traversal.flatMap(_.astOut)

  def containsIn: Iterator[nodes.AstNode] = traversal.flatMap(_.containsIn)

  def reachingDefOut: Iterator[nodes.Expression] = traversal.flatMap(_.reachingDefOut)

  def taggedByOut: Iterator[nodes.Tag] = traversal.flatMap(_.taggedByOut)
}
