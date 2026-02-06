package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalPropertyImportedEntity[
  NodeType <: nodes.StoredNode & nodes.StaticType[nodes.HasImportedEntityEMT]
](val traversal: Iterator[NodeType])
    extends AnyVal {

  /** Traverse to importedEntity property */
  def importedEntity: Iterator[String] =
    traversal.flatMap(_.importedEntity)

  /** Traverse to nodes where the importedEntity matches the regular expression `value`
    */
  def importedEntity(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      importedEntityExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item =>
        val tmp = item.importedEntity; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where the importedEntity matches at least one of the regular expressions in `values`
    */
  def importedEntity(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item =>
      val tmp = item.importedEntity; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

  /** Traverse to nodes where importedEntity matches `value` exactly.
    */
  def importedEntityExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 25, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ =>
      traversal.filter { node =>
        val tmp = node.importedEntity; tmp.isDefined && tmp.get == value
      }
  }

  /** Traverse to nodes where importedEntity matches one of the elements in `values` exactly.
    */
  def importedEntityExact(values: String*): Iterator[NodeType] =
    if (values.length == 1) importedEntityExact(values.head)
    else {
      val valueSet = values.toSet
      traversal.filter { item =>
        val tmp = item.importedEntity; tmp.isDefined && valueSet.contains(tmp.get)
      }
    }

  /** Traverse to nodes where importedEntity does not match the regular expression `value`.
    */
  def importedEntityNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.importedEntity.isEmpty || node.importedEntity.get != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item =>
        val tmp = item.importedEntity; tmp.isDefined && matcher.reset(tmp.get).matches
      }
    }
  }

  /** Traverse to nodes where importedEntity does not match any of the regular expressions in `values`.
    */
  def importedEntityNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filterNot { item =>
      val tmp = item.importedEntity; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
    }
  }

}
