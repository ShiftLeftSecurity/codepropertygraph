package io.shiftleft.semanticcpg.langv2.types.structure

import io.shiftleft.codepropertygraph.generated.nodes.Identifier
import io.shiftleft.codepropertygraph.generated.{NodeTypes, nodes}
import io.shiftleft.semanticcpg.langv2._

import scala.collection.IterableOnceOps
import scala.jdk.CollectionConverters._

trait LocalTraversalImplicits {
  implicit def toLocalTraversalSingle[I <: nodes.Local](in: I): LocalTraversal[I, Single, Nothing] = {
    new LocalTraversal(in: Single[I])
  }
  implicit def toLocalTraversalOption[I <: nodes.Local](in: Option[I]): LocalTraversal[I, Option, Nothing] = {
    new LocalTraversal(in)
  }
  implicit def toLocalTraversalIter[I <: nodes.Local, CC[_], C](in: IterableOnceOps[I, CC, C])
  : LocalTraversal[I, ({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]] = {
    new LocalTraversal[I, ({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]](in)
  }
}

class LocalTraversal[I <: nodes.Local, IT[_], Marker](val in: IT[I]) extends AnyVal {
  def referencingIdentifiers(implicit applier: ToMany[IT, Marker]): applier.OUT[Identifier] = {
    applier.apply(in)(_._refIn.asScala.filter(_.label == NodeTypes.IDENTIFIER)
      .cast[Identifier])
  }
}
