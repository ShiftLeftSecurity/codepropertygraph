package io.shiftleft.semanticcpg.language.commentextension

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language.types.structure.{Comment, File => OriginalFile}
import io.shiftleft.semanticcpg.language._

class File(original : OriginalFile) {

  def comment: Comment =
    new Comment(original.raw.out(EdgeTypes.AST).hasLabel(NodeTypes.COMMENT).cast[nodes.Comment])

}
