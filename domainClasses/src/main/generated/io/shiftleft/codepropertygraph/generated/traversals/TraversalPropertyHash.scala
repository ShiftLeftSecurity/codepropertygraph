package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyHash[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasHashEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to hash property */
  def hash: Iterator[String] =
    traversal.flatMap(_.hash)

  /** Traverse to nodes where the hash matches the regular expression `value`
    */
  def hash(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      hashExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item =>
        val tmp = item.hash; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where the hash matches at least one of the regular expressions in `values`
    */
  def hash(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item =>
      val tmp = item.hash; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to nodes where hash matches `value` exactly.
    */
  def hashExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 23, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ =>
      traversal.filter { node =>
        val tmp = node.hash; tmp.isDefined && tmp.get == value
      }
  }

  /** Traverse to nodes where hash matches one of the elements in `values` exactly.
    */
  def hashExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) hashExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item =>
        val tmp = item.hash; tmp.isDefined && valueSet.contains(tmp.get)
      }
    }

  /** Traverse to nodes where hash does not match the regular expression `value`.
    */
  def hashNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.hash.isEmpty || node.hash.get != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item =>
        val tmp = item.hash; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where hash does not match any of the regular expressions in `values`.
    */
  def hashNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filterNot { item =>
      val tmp = item.hash; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

}
