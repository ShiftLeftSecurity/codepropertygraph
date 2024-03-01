package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.Language.*

final class AccessNeighborsForAnnotationParameter(val node: nodes.AnnotationParameter) extends AnyVal {

  /** Traverse to ANNOTATION_PARAMETER_ASSIGN via AST IN edge.
    */
  def annotationParameterAssignViaAstIn: Iterator[nodes.AnnotationParameterAssign] =
    astIn.collectAll[nodes.AnnotationParameterAssign]

  def astIn: Iterator[nodes.AnnotationParameterAssign] = node._astIn.cast[nodes.AnnotationParameterAssign]
}

final class AccessNeighborsForAnnotationParameterTraversal(val traversal: Iterator[nodes.AnnotationParameter])
    extends AnyVal {

  /** Traverse to ANNOTATION_PARAMETER_ASSIGN via AST IN edge.
    */
  def annotationParameterAssignViaAstIn: Iterator[nodes.AnnotationParameterAssign] =
    traversal.flatMap(_.annotationParameterAssignViaAstIn)

  def astIn: Iterator[nodes.AnnotationParameterAssign] = traversal.flatMap(_.astIn)
}
