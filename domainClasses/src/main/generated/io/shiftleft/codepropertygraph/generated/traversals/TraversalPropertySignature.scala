package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertySignature[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasSignatureEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to signature property */
  def signature: Iterator[String] =
    traversal.map(_.signature)

  /** Traverse to nodes where the signature matches the regular expression `value`
    */
  def signature(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      signatureExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.signature).matches }
    }

  /** Traverse to nodes where the signature matches at least one of the regular expressions in `values`
    */
  def signature(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.signature).matches } }
  }

  /** Traverse to nodes where signature matches `value` exactly.
    */
  def signatureExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 49, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.signature == value }
  }

  /** Traverse to nodes where signature matches one of the elements in `values` exactly.
    */
  def signatureExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) signatureExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item => valueSet.contains(item.signature) }
    }

  /** Traverse to nodes where signature does not match the regular expression `value`.
    */
  def signatureNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.signature != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.signature).matches }
    }
  }

  /** Traverse to nodes where signature does not match any of the regular expressions in `values`.
    */
  def signatureNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.signature).matches }.isEmpty }
  }

}
