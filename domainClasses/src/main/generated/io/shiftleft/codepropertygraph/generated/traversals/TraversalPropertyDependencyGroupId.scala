package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyDependencyGroupId[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasDependencyGroupIdEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to dependencyGroupId property */
  def dependencyGroupId: Iterator[String] =
    traversal.flatMap(_.dependencyGroupId)

  /** Traverse to nodes where the dependencyGroupId matches the regular expression `value`
    */
  def dependencyGroupId(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      dependencyGroupIdExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item =>
        val tmp = item.dependencyGroupId; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where the dependencyGroupId matches at least one of the regular expressions in `values`
    */
  def dependencyGroupId(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item =>
      val tmp = item.dependencyGroupId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to nodes where dependencyGroupId matches `value` exactly.
    */
  def dependencyGroupIdExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 16, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ =>
      traversal.filter { node =>
        val tmp = node.dependencyGroupId; tmp.isDefined && tmp.get == value
      }
  }

  /** Traverse to nodes where dependencyGroupId matches one of the elements in `values` exactly.
    */
  def dependencyGroupIdExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) dependencyGroupIdExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item =>
        val tmp = item.dependencyGroupId; tmp.isDefined && valueSet.contains(tmp.get)
      }
    }

  /** Traverse to nodes where dependencyGroupId does not match the regular expression `value`.
    */
  def dependencyGroupIdNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.dependencyGroupId.isEmpty || node.dependencyGroupId.get != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item =>
        val tmp = item.dependencyGroupId; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where dependencyGroupId does not match any of the regular expressions in `values`.
    */
  def dependencyGroupIdNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filterNot { item =>
      val tmp = item.dependencyGroupId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

}
