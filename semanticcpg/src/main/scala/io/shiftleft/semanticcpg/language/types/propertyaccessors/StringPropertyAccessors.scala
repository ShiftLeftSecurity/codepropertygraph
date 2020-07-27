package io.shiftleft.semanticcpg.language.types.propertyaccessors

import overflowdb._
import overflowdb.traversal._
import overflowdb.traversal.filter.P

object StringPropertyAccessors {

  def filter[A <: Node](traversal: Traversal[A], property: PropertyKey[String], value: String): Traversal[A] =
    traversal.has(property.where(_.matches(value)))

  def filterNot[A <: Node](traversal: Traversal[A], property: PropertyKey[String], value: String): Traversal[A] =
    traversal.hasNot(property.where(_.matches(value)))

  def filterMultiple[A <: Node](traversal: Traversal[A],
                                property: PropertyKey[String],
                                values: String*): Traversal[A] = {
    val regexes = values.toSet
    traversal.has(property.where { value =>
      regexes.exists(_.matches(value))
    })
  }

  def filterNotMultiple[A <: Node](traversal: Traversal[A],
                                   property: PropertyKey[String],
                                   values: String*): Traversal[A] = {
    val regexes = values.toSet
    traversal.hasNot(property.where { value =>
      regexes.exists(_.matches(value))
    })
  }

  def filterExact[A <: Node](traversal: Traversal[A], property: PropertyKey[String], value: String): Traversal[A] =
    traversal.has(property, value)

  def filterExactMultiple[A <: Node](traversal: Traversal[A],
                                     property: PropertyKey[String],
                                     values: String*): Traversal[A] =
    traversal.has(property.where(P.within(values)))

}
