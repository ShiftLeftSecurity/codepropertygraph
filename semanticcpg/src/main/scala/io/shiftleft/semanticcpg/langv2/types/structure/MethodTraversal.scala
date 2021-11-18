package io.shiftleft.semanticcpg.langv2.types.structure

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.langv2._
import overflowdb.traversal.help
import overflowdb.traversal.help.Doc

import scala.jdk.CollectionConverters._


/**
  * A method, function, or procedure
  * */
@help.Traversal(elementType = classOf[nodes.Method])
class MethodTraversal[I <: nodes.Method, IT[_]](val trav: IT[I]) extends AnyVal {

  /**
    * Traverse to parameters of the method
    * */
  @Doc("All parameters")
  def parameter(implicit ops1: TravOps[IT]) = {
    ops1.oneToMany(trav)(_._astOut.asScala.collect { case par: nodes.MethodParameterIn => par })
  }

  /**
    * Traverse to formal return parameter
    * */
  @Doc("All formal return parameters")
  def methodReturn(implicit ops1: TravOps[IT]) = {
    ops1.oneToOne(trav)(_._astOut.asScala.collectFirst { case ret: nodes.MethodReturn => ret }.get)
  }

}
