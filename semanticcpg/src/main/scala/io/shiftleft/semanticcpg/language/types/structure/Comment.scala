package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language.types.propertyaccessors.{CodeAccessors, LineNumberAccessors}
import shapeless.HList
import io.shiftleft.semanticcpg.language._

class Comment[Labels <: HList](raw: GremlinScala.Aux[nodes.Comment, Labels])
    extends NodeSteps[nodes.Comment, Labels](raw)
    with LineNumberAccessors[nodes.Comment, Labels]
    with CodeAccessors[nodes.Comment, Labels] {

  override def file: File[Labels] =
    new File[Labels](raw.in(EdgeTypes.AST).hasLabel(NodeTypes.FILE).cast[nodes.File])

}
