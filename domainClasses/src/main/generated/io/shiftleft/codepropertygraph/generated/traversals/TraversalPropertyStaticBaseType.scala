package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyStaticBaseType[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasStaticBaseTypeEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to staticBaseType property */
  def staticBaseType: Iterator[String] =
    traversal.flatMap(_.staticBaseType)

  /** Traverse to nodes where the staticBaseType matches the regular expression `value`
    */
  def staticBaseType(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      staticBaseTypeExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item =>
        val tmp = item.staticBaseType; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where the staticBaseType matches at least one of the regular expressions in `values`
    */
  def staticBaseType(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item =>
      val tmp = item.staticBaseType; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to nodes where staticBaseType matches `value` exactly.
    */
  def staticBaseTypeExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 46, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ =>
      traversal.filter { node =>
        val tmp = node.staticBaseType; tmp.isDefined && tmp.get == value
      }
  }

  /** Traverse to nodes where staticBaseType matches one of the elements in `values` exactly.
    */
  def staticBaseTypeExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) staticBaseTypeExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item =>
        val tmp = item.staticBaseType; tmp.isDefined && valueSet.contains(tmp.get)
      }
    }

  /** Traverse to nodes where staticBaseType does not match the regular expression `value`.
    */
  def staticBaseTypeNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.staticBaseType.isEmpty || node.staticBaseType.get != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item =>
        val tmp = item.staticBaseType; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where staticBaseType does not match any of the regular expressions in `values`.
    */
  def staticBaseTypeNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filterNot { item =>
      val tmp = item.staticBaseType; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

}
