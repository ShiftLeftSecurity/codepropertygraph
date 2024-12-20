package io.shiftleft.codepropertygraph.generated.neighboraccessors

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.language.*

final class AccessNeighborsForComment(val node: nodes.Comment) extends AnyVal {

  /** Traverse to COMMENT via SOURCE_FILE IN edge.
    */
  def _commentViaSourceFileIn: Iterator[nodes.Comment] = sourceFileIn.collectAll[nodes.Comment]

  /** Traverse to COMMENT via SOURCE_FILE OUT edge.
    */
  @deprecated("please use file instead")
  def _commentViaSourceFileOut: Iterator[nodes.Comment] = file

  /** Traverse to COMMENT via SOURCE_FILE OUT edge.
    */
  def file: Iterator[nodes.Comment] = sourceFileOut.collectAll[nodes.Comment]

  /** Traverse to FILE via AST IN edge.
    */
  def _fileViaAstIn: Iterator[nodes.File] = astIn.collectAll[nodes.File]

  def astIn: Iterator[nodes.File] = node._astIn.cast[nodes.File]

  def sourceFileIn: Iterator[nodes.Comment] = node._sourceFileIn.cast[nodes.Comment]

  def sourceFileOut: Iterator[nodes.Comment] = node._sourceFileOut.cast[nodes.Comment]
}

final class AccessNeighborsForCommentTraversal(val traversal: Iterator[nodes.Comment]) extends AnyVal {

  /** Traverse to COMMENT via SOURCE_FILE IN edge.
    */
  def _commentViaSourceFileIn: Iterator[nodes.Comment] = traversal.flatMap(_._commentViaSourceFileIn)

  /** Traverse to COMMENT via SOURCE_FILE OUT edge.
    */
  def file: Iterator[nodes.Comment] = traversal.flatMap(_.file)

  /** Traverse to COMMENT via SOURCE_FILE OUT edge.
    */
  @deprecated("please use file instead")
  def _commentViaSourceFileOut: Iterator[nodes.Comment] = traversal.flatMap(_._commentViaSourceFileOut)

  /** Traverse to FILE via AST IN edge.
    */
  def _fileViaAstIn: Iterator[nodes.File] = traversal.flatMap(_._fileViaAstIn)

  def astIn: Iterator[nodes.File] = traversal.flatMap(_.astIn)

  def sourceFileIn: Iterator[nodes.Comment] = traversal.flatMap(_.sourceFileIn)

  def sourceFileOut: Iterator[nodes.Comment] = traversal.flatMap(_.sourceFileOut)
}
