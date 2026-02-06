package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyArgumentLabel[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasArgumentLabelEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to argumentLabel property */
  def argumentLabel: Iterator[String] =
    traversal.flatMap(_.argumentLabel)

  /** Traverse to nodes where the argumentLabel matches the regular expression `value`
    */
  def argumentLabel(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      argumentLabelExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item =>
        val tmp = item.argumentLabel; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where the argumentLabel matches at least one of the regular expressions in `values`
    */
  def argumentLabel(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item =>
      val tmp = item.argumentLabel; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to nodes where argumentLabel matches `value` exactly.
    */
  def argumentLabelExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 2, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ =>
      traversal.filter { node =>
        val tmp = node.argumentLabel; tmp.isDefined && tmp.get == value
      }
  }

  /** Traverse to nodes where argumentLabel matches one of the elements in `values` exactly.
    */
  def argumentLabelExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) argumentLabelExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item =>
        val tmp = item.argumentLabel; tmp.isDefined && valueSet.contains(tmp.get)
      }
    }

  /** Traverse to nodes where argumentLabel does not match the regular expression `value`.
    */
  def argumentLabelNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.argumentLabel.isEmpty || node.argumentLabel.get != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item =>
        val tmp = item.argumentLabel; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where argumentLabel does not match any of the regular expressions in `values`.
    */
  def argumentLabelNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filterNot { item =>
      val tmp = item.argumentLabel; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

}
