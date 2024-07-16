package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyAliasTypeFullName[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasAliasTypeFullNameEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to aliasTypeFullName property */
  def aliasTypeFullName: Iterator[String] =
    traversal.flatMap(_.aliasTypeFullName)

  /** Traverse to nodes where the aliasTypeFullName matches the regular expression `value`
    */
  def aliasTypeFullName(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      aliasTypeFullNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item =>
        val tmp = item.aliasTypeFullName; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where the aliasTypeFullName matches at least one of the regular expressions in `values`
    */
  def aliasTypeFullName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item =>
      val tmp = item.aliasTypeFullName; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to nodes where aliasTypeFullName matches `value` exactly.
    */
  def aliasTypeFullNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 0, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ =>
      traversal.filter { node =>
        val tmp = node.aliasTypeFullName; tmp.isDefined && tmp.get == value
      }
  }

  /** Traverse to nodes where aliasTypeFullName matches one of the elements in `values` exactly.
    */
  def aliasTypeFullNameExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) aliasTypeFullNameExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item =>
        val tmp = item.aliasTypeFullName; tmp.isDefined && valueSet.contains(tmp.get)
      }
    }

  /** Traverse to nodes where aliasTypeFullName does not match the regular expression `value`.
    */
  def aliasTypeFullNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.aliasTypeFullName.isEmpty || node.aliasTypeFullName.get != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item =>
        val tmp = item.aliasTypeFullName; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where aliasTypeFullName does not match any of the regular expressions in `values`.
    */
  def aliasTypeFullNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filterNot { item =>
      val tmp = item.aliasTypeFullName; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

}
