package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForAnnotationParameterAssign(val node: nodes.AnnotationParameterAssign) extends AnyVal {

  /** Traverse to ANNOTATION via AST IN edge.
    */
  def _annotationViaAstIn: Iterator[nodes.Annotation] = astIn.collectAll[nodes.Annotation]

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def _annotationViaAstOut: Iterator[nodes.Annotation] = astOut.collectAll[nodes.Annotation]

  /** Traverse to ANNOTATION_LITERAL via AST OUT edge.
    */
  def _annotationLiteralViaAstOut: Iterator[nodes.AnnotationLiteral] = astOut.collectAll[nodes.AnnotationLiteral]

  /** Traverse to ANNOTATION_PARAMETER via AST OUT edge.
    */
  def _annotationParameterViaAstOut: Iterator[nodes.AnnotationParameter] = astOut.collectAll[nodes.AnnotationParameter]

  /** Traverse to ARRAY_INITIALIZER via AST OUT edge.
    */
  def _arrayInitializerViaAstOut: Iterator[nodes.ArrayInitializer] = astOut.collectAll[nodes.ArrayInitializer]

  def astIn: Iterator[nodes.Annotation] = node._astIn.cast[nodes.Annotation]

  def astOut: Iterator[nodes.AstNode] = node._astOut.cast[nodes.AstNode]
}

final class AccessNeighborsForAnnotationParameterAssignTraversal(
  val traversal: Iterator[nodes.AnnotationParameterAssign]
) extends AnyVal {

  /** Traverse to ANNOTATION via AST IN edge.
    */
  def _annotationViaAstIn: Iterator[nodes.Annotation] = traversal.flatMap(_._annotationViaAstIn)

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def _annotationViaAstOut: Iterator[nodes.Annotation] = traversal.flatMap(_._annotationViaAstOut)

  /** Traverse to ANNOTATION_LITERAL via AST OUT edge.
    */
  def _annotationLiteralViaAstOut: Iterator[nodes.AnnotationLiteral] = traversal.flatMap(_._annotationLiteralViaAstOut)

  /** Traverse to ANNOTATION_PARAMETER via AST OUT edge.
    */
  def _annotationParameterViaAstOut: Iterator[nodes.AnnotationParameter] =
    traversal.flatMap(_._annotationParameterViaAstOut)

  /** Traverse to ARRAY_INITIALIZER via AST OUT edge.
    */
  def _arrayInitializerViaAstOut: Iterator[nodes.ArrayInitializer] = traversal.flatMap(_._arrayInitializerViaAstOut)

  def astIn: Iterator[nodes.Annotation] = traversal.flatMap(_.astIn)

  def astOut: Iterator[nodes.AstNode] = traversal.flatMap(_.astOut)
}
