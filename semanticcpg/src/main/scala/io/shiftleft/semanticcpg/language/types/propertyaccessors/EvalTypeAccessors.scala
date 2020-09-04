package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys}
import overflowdb.Node
import overflowdb.traversal.Traversal
import overflowdb.traversal.filter.P

class EvalTypeAccessors[A <: Node](val traversal: Traversal[A]) extends AnyVal {

  def evalType: Traversal[String] =
    traversal.out(EdgeTypes.EVAL_TYPE).out(EdgeTypes.REF).property(NodeKeys.FULL_NAME)

  def evalType(regex: String): Traversal[A] =
    traversal.where(
      _.out(EdgeTypes.EVAL_TYPE)
        .out(EdgeTypes.REF)
        .has(NodeKeys.FULL_NAME.where(_.matches(regex)))
    )

  def evalType(values: String*): Traversal[A] =
    if (values.isEmpty) Traversal.empty
    else {
      val regexes0 = values.map(_.r).toSet
      traversal.where(
        _.out(EdgeTypes.EVAL_TYPE)
          .out(EdgeTypes.REF)
          .has(NodeKeys.FULL_NAME.where { value =>
            regexes0.exists(_.matches(value))
          })
      )
    }

  def evalTypeExact(value: String): Traversal[A] =
    traversal.where(
      _.out(EdgeTypes.EVAL_TYPE)
        .out(EdgeTypes.REF)
        .has(NodeKeys.FULL_NAME, value))

  def evalTypeExact(values: String*): Traversal[A] =
    if (values.isEmpty) Traversal.empty
    else {
      traversal.where(
        _.out(EdgeTypes.EVAL_TYPE)
          .out(EdgeTypes.REF)
          .has(NodeKeys.FULL_NAME.where(P.within(values.to(Set)))))
    }

  def evalTypeNot(value: String): Traversal[A] =
    traversal.where(
      _.out(EdgeTypes.EVAL_TYPE)
        .out(EdgeTypes.REF)
        .hasNot(NodeKeys.FULL_NAME.where(_.matches(value))))

  def evalTypeNot(regexes: String*): Traversal[A] =
    if (regexes.isEmpty) Traversal.empty
    else {
      val regexes0 = regexes.map(_.r).toSet
      traversal.where(
        _.out(EdgeTypes.EVAL_TYPE)
          .out(EdgeTypes.REF)
          .hasNot(NodeKeys.FULL_NAME.where { value =>
            regexes0.exists(_.matches(value))
          })
      )
    }
}
