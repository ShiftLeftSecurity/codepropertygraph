package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForExpression(val node: nodes.Expression) extends AnyVal {

  /** Traverse to TEMPLATE_DOM via ARGUMENT OUT edge.
    */
  def _templateDomViaArgumentOut: Iterator[nodes.TemplateDom] = argumentOut.collectAll[nodes.TemplateDom]

  /** Traverse to TEMPLATE_DOM via AST IN edge.
    */
  def _templateDomViaAstIn: Iterator[nodes.TemplateDom] = astIn.collectAll[nodes.TemplateDom]

  /** Traverse to TEMPLATE_DOM via REACHING_DEF IN edge.
    */
  def _templateDomViaReachingDefIn: Iterator[nodes.TemplateDom] = reachingDefIn.collectAll[nodes.TemplateDom]

  def argumentOut: Iterator[nodes.TemplateDom] = node._argumentOut.cast[nodes.TemplateDom]

  def astIn: Iterator[nodes.TemplateDom] = node._astIn.cast[nodes.TemplateDom]

  def reachingDefIn: Iterator[nodes.TemplateDom] = node._reachingDefIn.cast[nodes.TemplateDom]
}

final class AccessNeighborsForExpressionTraversal(val traversal: Iterator[nodes.Expression]) extends AnyVal {

  /** Traverse to TEMPLATE_DOM via ARGUMENT OUT edge.
    */
  def _templateDomViaArgumentOut: Iterator[nodes.TemplateDom] = traversal.flatMap(_._templateDomViaArgumentOut)

  /** Traverse to TEMPLATE_DOM via AST IN edge.
    */
  def _templateDomViaAstIn: Iterator[nodes.TemplateDom] = traversal.flatMap(_._templateDomViaAstIn)

  /** Traverse to TEMPLATE_DOM via REACHING_DEF IN edge.
    */
  def _templateDomViaReachingDefIn: Iterator[nodes.TemplateDom] = traversal.flatMap(_._templateDomViaReachingDefIn)

  def argumentOut: Iterator[nodes.TemplateDom] = traversal.flatMap(_.argumentOut)

  def astIn: Iterator[nodes.TemplateDom] = traversal.flatMap(_.astIn)

  def reachingDefIn: Iterator[nodes.TemplateDom] = traversal.flatMap(_.reachingDefIn)
}
