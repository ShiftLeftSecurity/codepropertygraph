package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForAnnotationLiteral(val node: nodes.AnnotationLiteral) extends AnyVal {

  /** Traverse to ANNOTATION_PARAMETER_ASSIGN via AST IN edge.
    */
  def _annotationParameterAssignViaAstIn: Iterator[nodes.AnnotationParameterAssign] =
    astIn.collectAll[nodes.AnnotationParameterAssign]

  def astIn: Iterator[nodes.AnnotationParameterAssign] = node._astIn.cast[nodes.AnnotationParameterAssign]
}

final class AccessNeighborsForAnnotationLiteralTraversal(val traversal: Iterator[nodes.AnnotationLiteral])
    extends AnyVal {

  /** Traverse to ANNOTATION_PARAMETER_ASSIGN via AST IN edge.
    */
  def _annotationParameterAssignViaAstIn: Iterator[nodes.AnnotationParameterAssign] =
    traversal.flatMap(_._annotationParameterAssignViaAstIn)

  def astIn: Iterator[nodes.AnnotationParameterAssign] = traversal.flatMap(_.astIn)
}
