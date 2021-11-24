package io.shiftleft.semanticcpg.langv2.types.structure

import io.shiftleft.codepropertygraph.generated.nodes.Identifier
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.semanticcpg.langv2._

import scala.collection.IterableOnceOps
import scala.jdk.CollectionConverters._

trait LocalTraversalImplicits {
  implicit def toLocalTraversalSingle[I <: nodes.Local](trav: I): LocalTraversal[I, Single] = {
    new LocalTraversal(trav: Single[I])
  }
  implicit def toLocalTraversalGeneric[I <: nodes.Local, IT[_]](trav: IT[I]): LocalTraversal[I, IT] = {
    new LocalTraversal(trav)
  }
}

class LocalTraversal[I <: nodes.Local, IT[_]](val trav: IT[I]) extends AnyVal {
  def referencingIdentifiers(implicit ops1: TravOps[IT]): ops1.CCOneToMany[Identifier] = {
    trav.flatMap(_._refIn.asScala.filter(_.label == NodeTypes.IDENTIFIER)
      .cast[Identifier])
  }
}
