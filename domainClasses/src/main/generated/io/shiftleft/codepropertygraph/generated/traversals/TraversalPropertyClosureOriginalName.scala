package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyClosureOriginalName[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasClosureOriginalNameEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to closureOriginalName property */
  def closureOriginalName: Iterator[String] =
    traversal.flatMap(_.closureOriginalName)

  /** Traverse to nodes where the closureOriginalName matches the regular expression `value`
    */
  def closureOriginalName(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      closureOriginalNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item =>
        val tmp = item.closureOriginalName; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where the closureOriginalName matches at least one of the regular expressions in `values`
    */
  def closureOriginalName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item =>
      val tmp = item.closureOriginalName; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to nodes where closureOriginalName matches `value` exactly.
    */
  def closureOriginalNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 9, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ =>
      traversal.filter { node =>
        val tmp = node.closureOriginalName; tmp.isDefined && tmp.get == value
      }
  }

  /** Traverse to nodes where closureOriginalName matches one of the elements in `values` exactly.
    */
  def closureOriginalNameExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) closureOriginalNameExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item =>
        val tmp = item.closureOriginalName; tmp.isDefined && valueSet.contains(tmp.get)
      }
    }

  /** Traverse to nodes where closureOriginalName does not match the regular expression `value`.
    */
  def closureOriginalNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.closureOriginalName.isEmpty || node.closureOriginalName.get != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item =>
        val tmp = item.closureOriginalName; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where closureOriginalName does not match any of the regular expressions in `values`.
    */
  def closureOriginalNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filterNot { item =>
      val tmp = item.closureOriginalName; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

}
