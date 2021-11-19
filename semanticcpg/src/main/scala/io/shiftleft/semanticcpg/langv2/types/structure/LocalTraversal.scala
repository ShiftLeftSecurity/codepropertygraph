package io.shiftleft.semanticcpg.langv2.types.structure

import io.shiftleft.codepropertygraph.generated.nodes.Identifier
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.semanticcpg.langv2._

import scala.collection.IterableOnceOps
import scala.jdk.CollectionConverters._

trait LocalTraversalImplicits {

  implicit def toLocalTraversalSingle[I <: nodes.Local](trav: I) = {
    new LocalTraversal[I, SingleTravTypes.type](trav: Single[I])
  }
  implicit def toLocalTraversalGeneric[I <: nodes.Local, T[_] <: SupportedTypes[_]](trav: TravTypesFor[T]#Collection[I]) = {
    new LocalTraversal[I, TravTypesFor[T]](trav)
  }
  implicit def toLocalTraversalIterOnceOps[I <: nodes.Local, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new LocalTraversal[I, IterableOnceOpsTravTypes[CC, C]](trav)
  }
}

class LocalTraversal[I <: nodes.Local, TT <: TravTypes](val trav: TT#Collection[I]) extends AnyVal {
  def referencingIdentifiers(implicit ops1: TravOps[TT]): TT#CCOneToMany[Identifier] = {
    trav.flatMap(_._refIn.asScala.filter(_.label == NodeTypes.IDENTIFIER)
      .cast[Identifier])
  }
}
