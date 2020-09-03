package io.shiftleft.semanticcpg.language.types.propertyaccessors

import overflowdb._
import overflowdb.traversal._
import overflowdb.traversal.filter.P

object StringPropertyAccessors {

  def filter[A <: Node](traversal: Traversal[A], property: PropertyKey[String], regex: String): Traversal[A] = {
    val regex0 = regex.r
    traversal.has(property.where(propValue => regex0.matches(propValue)))
  }

  def filterNot[A <: Node](traversal: Traversal[A], property: PropertyKey[String], regex: String): Traversal[A] = {
    val regex0 = regex.r
    traversal.hasNot(property.where(propValue => regex0.matches(propValue)))
  }

  def filterMultiple[A <: Node](traversal: Traversal[A],
                                property: PropertyKey[String],
                                regexes: String*): Traversal[A] = {
    val regexes0 = regexes.map(_.r).toSet
    traversal.has(property.where { propValue =>
      regexes0.exists(_.matches(propValue))
    })
  }

  def filterNotMultiple[A <: Node](traversal: Traversal[A],
                                   property: PropertyKey[String],
                                   regexes: String*): Traversal[A] = {
    val regexes0 = regexes.map(_.r).toSet
    traversal.hasNot(property.where { propValue =>
      regexes0.exists(_.matches(propValue))
    })
  }

  def filterExact[A <: Node](traversal: Traversal[A], property: PropertyKey[String], value: String): Traversal[A] =
    traversal.has(property, value)

  def filterExactMultiple[A <: Node](traversal: Traversal[A],
                                     property: PropertyKey[String],
                                     values: String*): Traversal[A] =
    traversal.has(property.where(P.within(values.to(Set))))

}
