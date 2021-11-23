package io.shiftleft.semanticcpg.langv2.types.structure

import io.shiftleft.codepropertygraph.generated.nodes.Identifier
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.semanticcpg.langv2._

import scala.collection.IterableOnceOps
import scala.jdk.CollectionConverters._

trait LocalTraversalImplicits {
  implicit def toLocalTraversalSingle[I <: nodes.Local](trav: I) = {
    new LocalTraversal[I, Single, DefaultMarker](trav: Single[I])
  }
  implicit def toLocalTraversalGeneric[I <: nodes.Local, IT[_] <: Option[_]](trav: IT[I]) = {
    new LocalTraversal[I, IT, DefaultMarker](trav)
  }
  implicit def toLocalTraversalIterOnceOps[I <: nodes.Local, CC[_], C](trav: IterableOnceOps[I, CC, C])
  : LocalTraversal[I, ({type X[A] = IterableOnceOps[A, CC, C]})#X, IterMarker[CC, C]] = {
    new LocalTraversal[I, ({type X[A] = IterableOnceOps[A, CC, C]})#X, IterMarker[CC, C]](trav)
  }
}

class LocalTraversal[I <: nodes.Local, IT[_], Marker](val trav: IT[I]) extends AnyVal {
  def referencingIdentifiers(implicit ops1: TravOps[IT, Marker]): ops1.CCOneToMany[Identifier] = {
    trav.flatMap(_._refIn.asScala.filter(_.label == NodeTypes.IDENTIFIER)
      .cast[Identifier])
  }
}
