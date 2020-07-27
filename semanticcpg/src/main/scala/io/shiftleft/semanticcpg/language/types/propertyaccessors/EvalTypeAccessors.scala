package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeysOdb}
import overflowdb.Node
import overflowdb.traversal.Traversal
import overflowdb.traversal.filter.P

class EvalTypeAccessors[A <: Node](val traversal: Traversal[A]) extends AnyVal {

  def evalType: Traversal[String] =
    traversal.out(EdgeTypes.EVAL_TYPE).out(EdgeTypes.REF).property(NodeKeysOdb.FULL_NAME)

  def evalType(value: String): Traversal[A] =
    traversal.where(
      _.out(EdgeTypes.EVAL_TYPE)
        .out(EdgeTypes.REF)
        .has(NodeKeysOdb.FULL_NAME.where(_.matches(value)))
    )

  def evalType(values: String*): Traversal[A] =
    if (values.isEmpty) Traversal.empty
    else {
      val regexes = values.toSet
      traversal.where(
        _.out(EdgeTypes.EVAL_TYPE)
          .out(EdgeTypes.REF)
          .has(NodeKeysOdb.FULL_NAME.where { value =>
            regexes.exists(_.matches(value))
          }
        )
      )
    }

  def evalTypeExact(value: String): Traversal[A] =
    traversal.where(
      _.out(EdgeTypes.EVAL_TYPE)
        .out(EdgeTypes.REF)
        .has(NodeKeysOdb.FULL_NAME, value))

  def evalTypeExact(values: String*): Traversal[A] =
    if (values.isEmpty) Traversal.empty
    else {
      traversal.where(
        _.out(EdgeTypes.EVAL_TYPE)
          .out(EdgeTypes.REF)
          .has(NodeKeysOdb.FULL_NAME.where(P.within(values))))
    }

  def evalTypeNot(value: String): Traversal[A] =
    traversal.where(
      _.out(EdgeTypes.EVAL_TYPE)
        .out(EdgeTypes.REF)
        .hasNot(NodeKeysOdb.FULL_NAME.where(_.matches(value))))

  def evalTypeNot(values: String*): Traversal[A] =
    if (values.isEmpty) Traversal.empty
    else {
      val regexes = values.toSet
      traversal.where(
        _.out(EdgeTypes.EVAL_TYPE)
          .out(EdgeTypes.REF)
          .hasNot(NodeKeysOdb.FULL_NAME.where { value =>
            regexes.exists(_.matches(value))
          }
        )
      )
    }
}
