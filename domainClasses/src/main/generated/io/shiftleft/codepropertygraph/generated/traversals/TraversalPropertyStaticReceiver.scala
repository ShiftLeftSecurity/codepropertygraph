package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyStaticReceiver[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasStaticReceiverEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to staticReceiver property */
  def staticReceiver: Iterator[String] =
    traversal.flatMap(_.staticReceiver)

  /** Traverse to nodes where the staticReceiver matches the regular expression `value`
    */
  def staticReceiver(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      staticReceiverExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item =>
        val tmp = item.staticReceiver; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where the staticReceiver matches at least one of the regular expressions in `values`
    */
  def staticReceiver(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item =>
      val tmp = item.staticReceiver; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to nodes where staticReceiver matches `value` exactly.
    */
  def staticReceiverExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 47, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ =>
      traversal.filter { node =>
        val tmp = node.staticReceiver; tmp.isDefined && tmp.get == value
      }
  }

  /** Traverse to nodes where staticReceiver matches one of the elements in `values` exactly.
    */
  def staticReceiverExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) staticReceiverExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item =>
        val tmp = item.staticReceiver; tmp.isDefined && valueSet.contains(tmp.get)
      }
    }

  /** Traverse to nodes where staticReceiver does not match the regular expression `value`.
    */
  def staticReceiverNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.staticReceiver.isEmpty || node.staticReceiver.get != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item =>
        val tmp = item.staticReceiver; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where staticReceiver does not match any of the regular expressions in `values`.
    */
  def staticReceiverNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filterNot { item =>
      val tmp = item.staticReceiver; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

}
