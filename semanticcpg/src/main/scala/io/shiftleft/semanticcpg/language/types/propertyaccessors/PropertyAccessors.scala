package io.shiftleft.semanticcpg.language.types.propertyaccessors

import overflowdb._
import overflowdb.traversal._
import overflowdb.traversal.filter.P

object PropertyAccessors {

  def filter[A <: Node, B](traversal: Traversal[A], property: PropertyKey[B], value: B): Traversal[A] =
    traversal.has(property, value)

  def filterNot[A <: Node, B](traversal: Traversal[A], property: PropertyKey[B], value: B): Traversal[A] =
    traversal.hasNot(property, value)

  def filterMultiple[A <: Node, B](traversal: Traversal[A], property: PropertyKey[B], values: B*): Traversal[A] =
    traversal.has(property.where(P.within(values)))

  def filterNotMultiple[A <: Node, B](traversal: Traversal[A], property: PropertyKey[B], values: B*): Traversal[A] =
    traversal.hasNot(property.where(P.within(values)))

}
