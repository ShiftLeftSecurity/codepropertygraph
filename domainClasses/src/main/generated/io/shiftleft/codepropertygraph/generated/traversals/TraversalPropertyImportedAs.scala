package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyImportedAs[NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasImportedAsEMT]](
  val traversal: Iterator[NodeType]
) extends AnyVal {

  /** Traverse to importedAs property */
  def importedAs: Iterator[String] =
    traversal.flatMap(_.importedAs)

  /** Traverse to nodes where the importedAs matches the regular expression `value`
    */
  def importedAs(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      importedAsExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item =>
        val tmp = item.importedAs; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where the importedAs matches at least one of the regular expressions in `values`
    */
  def importedAs(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item =>
      val tmp = item.importedAs; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to nodes where importedAs matches `value` exactly.
    */
  def importedAsExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 24, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ =>
      traversal.filter { node =>
        val tmp = node.importedAs; tmp.isDefined && tmp.get == value
      }
  }

  /** Traverse to nodes where importedAs matches one of the elements in `values` exactly.
    */
  def importedAsExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) importedAsExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item =>
        val tmp = item.importedAs; tmp.isDefined && valueSet.contains(tmp.get)
      }
    }

  /** Traverse to nodes where importedAs does not match the regular expression `value`.
    */
  def importedAsNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.importedAs.isEmpty || node.importedAs.get != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item =>
        val tmp = item.importedAs; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where importedAs does not match any of the regular expressions in `values`.
    */
  def importedAsNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filterNot { item =>
      val tmp = item.importedAs; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

}
