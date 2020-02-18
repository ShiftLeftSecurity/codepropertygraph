package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.semanticcpg.language._
import io.shiftleft.codepropertygraph.generated.{ModifierTypes, NodeKeys, NodeTypes, nodes}
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode

class ModifierAccessors[NodeType <: StoredNode](val wrapped: NodeSteps[NodeType]) extends AnyVal {
  private def raw: GremlinScala[NodeType] = wrapped.raw

  /** Filter: only `public` nodes */
  def isPublic: NodeSteps[NodeType] =
    hasModifier(ModifierTypes.PUBLIC)

  /** Filter: only `private` nodes */
  def isPrivate: NodeSteps[NodeType] =
    hasModifier(ModifierTypes.PRIVATE)

  /** Filter: only `protected` nodes */
  def isProtected: NodeSteps[NodeType] =
    hasModifier(ModifierTypes.PROTECTED)

  /** Filter: only `abstract` nodes */
  def isAbstract: NodeSteps[NodeType] =
    hasModifier(ModifierTypes.ABSTRACT)

  /** Filter: only `static` nodes */
  def isStatic: NodeSteps[NodeType] =
    hasModifier(ModifierTypes.STATIC)

  /** Filter: only `native` nodes */
  def isNative: NodeSteps[NodeType] =
    hasModifier(ModifierTypes.NATIVE)

  /** Filter: only `constructor` nodes */
  def isConstructor: NodeSteps[NodeType] =
    hasModifier(ModifierTypes.CONSTRUCTOR)

  /** Filter: only `virtual` nodes */
  def isVirtual: NodeSteps[NodeType] =
    hasModifier(ModifierTypes.VIRTUAL)

  def hasModifier(modifier: String): NodeSteps[NodeType] =
    new NodeSteps(raw.filter(_.out.hasLabel(NodeTypes.MODIFIER).has(NodeKeys.MODIFIER_TYPE -> modifier)))

  /**
    * Traverse to modifiers, e.g., "static", "public".
    * */
  def modifier: NodeSteps[nodes.Modifier] =
    new NodeSteps(
      raw.out.hasLabel(NodeTypes.MODIFIER).cast[nodes.Modifier]
    )
}
