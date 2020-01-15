package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.ControlStructure
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.AstNode
import io.shiftleft.semanticcpg.language.types.structure.{Local, MethodParameter}
import scala.jdk.CollectionConverters._
import io.shiftleft.Implicits.JavaIteratorDeco

class MethodReturnMethods(val node: nodes.MethodReturn) extends AnyVal {

  def toReturn: Seq[nodes.Return] =
    node._cfgIn.asScala.collect { case r: nodes.Return => r }.toSeq

}
