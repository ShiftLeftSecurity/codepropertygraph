package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForArrayInitializer(val node: nodes.ArrayInitializer) extends AnyVal {

  /** Traverse to ANNOTATION_PARAMETER_ASSIGN via AST IN edge.
    */
  def _annotationParameterAssignViaAstIn: Iterator[nodes.AnnotationParameterAssign] =
    astIn.collectAll[nodes.AnnotationParameterAssign]

  /** Traverse to LITERAL via AST OUT edge.
    */
  def _literalViaAstOut: Iterator[nodes.Literal] = astOut.collectAll[nodes.Literal]

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def _typeViaEvalTypeOut: Iterator[nodes.Type] = evalTypeOut.collectAll[nodes.Type]

  def astIn: Iterator[nodes.AnnotationParameterAssign] = node._astIn.cast[nodes.AnnotationParameterAssign]

  def astOut: Iterator[nodes.Literal] = node._astOut.cast[nodes.Literal]

  def evalTypeOut: Iterator[nodes.Type] = node._evalTypeOut.cast[nodes.Type]
}

final class AccessNeighborsForArrayInitializerTraversal(val traversal: Iterator[nodes.ArrayInitializer])
    extends AnyVal {

  /** Traverse to ANNOTATION_PARAMETER_ASSIGN via AST IN edge.
    */
  def _annotationParameterAssignViaAstIn: Iterator[nodes.AnnotationParameterAssign] =
    traversal.flatMap(_._annotationParameterAssignViaAstIn)

  /** Traverse to LITERAL via AST OUT edge.
    */
  def _literalViaAstOut: Iterator[nodes.Literal] = traversal.flatMap(_._literalViaAstOut)

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def _typeViaEvalTypeOut: Iterator[nodes.Type] = traversal.flatMap(_._typeViaEvalTypeOut)

  def astIn: Iterator[nodes.AnnotationParameterAssign] = traversal.flatMap(_.astIn)

  def astOut: Iterator[nodes.Literal] = traversal.flatMap(_.astOut)

  def evalTypeOut: Iterator[nodes.Type] = traversal.flatMap(_.evalTypeOut)
}
