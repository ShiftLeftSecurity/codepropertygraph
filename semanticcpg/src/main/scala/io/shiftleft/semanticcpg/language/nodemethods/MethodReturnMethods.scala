package io.shiftleft.semanticcpg.language.nodemethods

import io.shiftleft.codepropertygraph.generated.nodes
import scala.jdk.CollectionConverters._

class MethodReturnMethods(val node: nodes.MethodReturn) extends AnyVal {

  def toReturn: Seq[nodes.Return] =
    node.cfgIn.asScala.collect { case r: nodes.Return => r }.toSeq

}
