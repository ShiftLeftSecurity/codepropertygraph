package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalLocationBase[NodeType <: nodes.LocationBase](val traversal: Iterator[NodeType]) extends AnyVal {

  /** Traverse to className property */
  def className: Iterator[String] =
    traversal.map(_.className)

  /** Traverse to nodes where the className matches the regular expression `value`
    */
  def className(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      classNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.className).matches }
    }

  /** Traverse to nodes where the className matches at least one of the regular expressions in `values`
    */
  def className(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.className).matches } }
  }

  /** Traverse to nodes where className matches `value` exactly.
    */
  def classNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 6, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.className == value }
  }

  /** Traverse to nodes where className matches one of the elements in `values` exactly.
    */
  def classNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return classNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 6, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.className) }
    }
  }

  /** Traverse to nodes where className does not match the regular expression `value`.
    */
  def classNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.className != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.className).matches }
    }
  }

  /** Traverse to nodes where className does not match any of the regular expressions in `values`.
    */
  def classNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.className).matches }.isEmpty }
  }

  /** Traverse to classShortName property */
  def classShortName: Iterator[String] =
    traversal.map(_.classShortName)

  /** Traverse to nodes where the classShortName matches the regular expression `value`
    */
  def classShortName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      classShortNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.classShortName).matches }
    }

  /** Traverse to nodes where the classShortName matches at least one of the regular expressions in `values`
    */
  def classShortName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.classShortName).matches } }
  }

  /** Traverse to nodes where classShortName matches `value` exactly.
    */
  def classShortNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 7, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.classShortName == value }
  }

  /** Traverse to nodes where classShortName matches one of the elements in `values` exactly.
    */
  def classShortNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return classShortNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 7, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.classShortName) }
    }
  }

  /** Traverse to nodes where classShortName does not match the regular expression `value`.
    */
  def classShortNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.classShortName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.classShortName).matches }
    }
  }

  /** Traverse to nodes where classShortName does not match any of the regular expressions in `values`.
    */
  def classShortNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.classShortName).matches }.isEmpty }
  }

  /** Traverse to filename property */
  def filename: Iterator[String] =
    traversal.map(_.filename)

  /** Traverse to nodes where the filename matches the regular expression `value`
    */
  def filename(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      filenameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.filename).matches }
    }

  /** Traverse to nodes where the filename matches at least one of the regular expressions in `values`
    */
  def filename(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.filename).matches } }
  }

  /** Traverse to nodes where filename matches `value` exactly.
    */
  def filenameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 21, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.filename == value }
  }

  /** Traverse to nodes where filename matches one of the elements in `values` exactly.
    */
  def filenameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return filenameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 21, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.filename) }
    }
  }

  /** Traverse to nodes where filename does not match the regular expression `value`.
    */
  def filenameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.filename != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.filename).matches }
    }
  }

  /** Traverse to nodes where filename does not match any of the regular expressions in `values`.
    */
  def filenameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.filename).matches }.isEmpty }
  }

  /** Traverse to lineNumber property */
  def lineNumber: Iterator[Int] =
    traversal.flatMap(_.lineNumber)

  /** Traverse to nodes where the lineNumber equals the given `value`
    */
  def lineNumber(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isDefined && tmp.get == value
    }

  /** Traverse to nodes where the lineNumber equals at least one of the given `values`
    */
  def lineNumber(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isDefined && vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the lineNumber is not equal to the given `value`
    */
  def lineNumberNot(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isEmpty || tmp.get != value
    }

  /** Traverse to nodes where the lineNumber does not equal any one of the given `values`
    */
  def lineNumberNot(values: Int*): Iterator[NodeType] = {
    val vset = values.toSet
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isEmpty || !vset.contains(tmp.get)
    }
  }

  /** Traverse to nodes where the lineNumber is greater than the given `value`
    */
  def lineNumberGt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isDefined && tmp.get > value
    }

  /** Traverse to nodes where the lineNumber is greater than or equal the given `value`
    */
  def lineNumberGte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isDefined && tmp.get >= value
    }

  /** Traverse to nodes where the lineNumber is less than the given `value`
    */
  def lineNumberLt(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isDefined && tmp.get < value
    }

  /** Traverse to nodes where the lineNumber is less than or equal the given `value`
    */
  def lineNumberLte(value: Int): Iterator[NodeType] =
    traversal.filter { node =>
      val tmp = node.lineNumber; tmp.isDefined && tmp.get <= value
    }

  /** Traverse to methodFullName property */
  def methodFullName: Iterator[String] =
    traversal.map(_.methodFullName)

  /** Traverse to nodes where the methodFullName matches the regular expression `value`
    */
  def methodFullName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      methodFullNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.methodFullName).matches }
    }

  /** Traverse to nodes where the methodFullName matches at least one of the regular expressions in `values`
    */
  def methodFullName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.methodFullName).matches } }
  }

  /** Traverse to nodes where methodFullName matches `value` exactly.
    */
  def methodFullNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 37, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.methodFullName == value }
  }

  /** Traverse to nodes where methodFullName matches one of the elements in `values` exactly.
    */
  def methodFullNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return methodFullNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 37, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.methodFullName) }
    }
  }

  /** Traverse to nodes where methodFullName does not match the regular expression `value`.
    */
  def methodFullNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.methodFullName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.methodFullName).matches }
    }
  }

  /** Traverse to nodes where methodFullName does not match any of the regular expressions in `values`.
    */
  def methodFullNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.methodFullName).matches }.isEmpty }
  }

  /** Traverse to methodShortName property */
  def methodShortName: Iterator[String] =
    traversal.map(_.methodShortName)

  /** Traverse to nodes where the methodShortName matches the regular expression `value`
    */
  def methodShortName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      methodShortNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.methodShortName).matches }
    }

  /** Traverse to nodes where the methodShortName matches at least one of the regular expressions in `values`
    */
  def methodShortName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.methodShortName).matches } }
  }

  /** Traverse to nodes where methodShortName matches `value` exactly.
    */
  def methodShortNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 38, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.methodShortName == value }
  }

  /** Traverse to nodes where methodShortName matches one of the elements in `values` exactly.
    */
  def methodShortNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return methodShortNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 38, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.methodShortName) }
    }
  }

  /** Traverse to nodes where methodShortName does not match the regular expression `value`.
    */
  def methodShortNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.methodShortName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.methodShortName).matches }
    }
  }

  /** Traverse to nodes where methodShortName does not match any of the regular expressions in `values`.
    */
  def methodShortNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.methodShortName).matches }.isEmpty }
  }

  /** Traverse to nodeLabel property */
  def nodeLabel: Iterator[String] =
    traversal.map(_.nodeLabel)

  /** Traverse to nodes where the nodeLabel matches the regular expression `value`
    */
  def nodeLabel(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      nodeLabelExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.nodeLabel).matches }
    }

  /** Traverse to nodes where the nodeLabel matches at least one of the regular expressions in `values`
    */
  def nodeLabel(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.nodeLabel).matches } }
  }

  /** Traverse to nodes where nodeLabel matches `value` exactly.
    */
  def nodeLabelExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 41, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.nodeLabel == value }
  }

  /** Traverse to nodes where nodeLabel matches one of the elements in `values` exactly.
    */
  def nodeLabelExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return nodeLabelExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 41, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.nodeLabel) }
    }
  }

  /** Traverse to nodes where nodeLabel does not match the regular expression `value`.
    */
  def nodeLabelNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.nodeLabel != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.nodeLabel).matches }
    }
  }

  /** Traverse to nodes where nodeLabel does not match any of the regular expressions in `values`.
    */
  def nodeLabelNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.nodeLabel).matches }.isEmpty }
  }

  /** Traverse to packageName property */
  def packageName: Iterator[String] =
    traversal.map(_.packageName)

  /** Traverse to nodes where the packageName matches the regular expression `value`
    */
  def packageName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      packageNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.packageName).matches }
    }

  /** Traverse to nodes where the packageName matches at least one of the regular expressions in `values`
    */
  def packageName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.packageName).matches } }
  }

  /** Traverse to nodes where packageName matches `value` exactly.
    */
  def packageNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 46, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.packageName == value }
  }

  /** Traverse to nodes where packageName matches one of the elements in `values` exactly.
    */
  def packageNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return packageNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 46, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.packageName) }
    }
  }

  /** Traverse to nodes where packageName does not match the regular expression `value`.
    */
  def packageNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.packageName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.packageName).matches }
    }
  }

  /** Traverse to nodes where packageName does not match any of the regular expressions in `values`.
    */
  def packageNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.packageName).matches }.isEmpty }
  }

  /** Traverse to symbol property */
  def symbol: Iterator[String] =
    traversal.map(_.symbol)

  /** Traverse to nodes where the symbol matches the regular expression `value`
    */
  def symbol(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      symbolExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.symbol).matches }
    }

  /** Traverse to nodes where the symbol matches at least one of the regular expressions in `values`
    */
  def symbol(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.symbol).matches } }
  }

  /** Traverse to nodes where symbol matches `value` exactly.
    */
  def symbolExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 51, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.symbol == value }
  }

  /** Traverse to nodes where symbol matches one of the elements in `values` exactly.
    */
  def symbolExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return symbolExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 51, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.symbol) }
    }
  }

  /** Traverse to nodes where symbol does not match the regular expression `value`.
    */
  def symbolNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.symbol != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.symbol).matches }
    }
  }

  /** Traverse to nodes where symbol does not match any of the regular expressions in `values`.
    */
  def symbolNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.symbol).matches }.isEmpty }
  }

}
