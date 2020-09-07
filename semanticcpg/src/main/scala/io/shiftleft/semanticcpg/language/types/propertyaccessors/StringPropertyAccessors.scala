package io.shiftleft.semanticcpg.language.types.propertyaccessors

import overflowdb.{Node, PropertyKey}
import overflowdb.traversal.Traversal
import overflowdb.traversal.filter.P

object StringPropertyAccessors {

  def filter[A <: Node](traversal: Traversal[A], property: PropertyKey[String], regex: String): Traversal[A] =
    traversal.has(property.where(P.matches(regex)))

  def filterNot[A <: Node](traversal: Traversal[A], property: PropertyKey[String], regex: String): Traversal[A] =
    traversal.hasNot(property.where(P.matches(regex)))

  def filterMultiple[A <: Node](traversal: Traversal[A],
                                property: PropertyKey[String],
                                regexes: String*): Traversal[A] =
    traversal.has(property.where(P.matches(regexes: _*)))

  def filterNotMultiple[A <: Node](traversal: Traversal[A],
                                   property: PropertyKey[String],
                                   regexes: String*): Traversal[A] =
    traversal.hasNot(property.where(P.matches(regexes: _*)))

  def filterExact[A <: Node](traversal: Traversal[A], property: PropertyKey[String], value: String): Traversal[A] =
    traversal.has(property, value)

  def filterExactMultiple[A <: Node](traversal: Traversal[A],
                                     property: PropertyKey[String],
                                     values: String*): Traversal[A] =
    traversal.has(property.where(P.within(values.to(Set))))

}
