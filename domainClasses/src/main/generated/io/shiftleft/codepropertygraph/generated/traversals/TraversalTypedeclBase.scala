package io.shiftleft.codepropertygraph.generated.traversals

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*

final class TraversalTypedeclBase[NodeType <: nodes.TypeDeclBase](val traversal: Iterator[NodeType]) extends AnyVal {

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

  /** Traverse to astParentFullName property */
  def astParentFullName: Iterator[String] =
    traversal.map(_.astParentFullName)

  /** Traverse to nodes where the astParentFullName matches the regular expression `value`
    */
  def astParentFullName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      astParentFullNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.astParentFullName).matches }
    }

  /** Traverse to nodes where the astParentFullName matches at least one of the regular expressions in `values`
    */
  def astParentFullName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.astParentFullName).matches } }
  }

  /** Traverse to nodes where astParentFullName matches `value` exactly.
    */
  def astParentFullNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 3, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.astParentFullName == value }
  }

  /** Traverse to nodes where astParentFullName matches one of the elements in `values` exactly.
    */
  def astParentFullNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return astParentFullNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 3, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.astParentFullName) }
    }
  }

  /** Traverse to nodes where astParentFullName does not match the regular expression `value`.
    */
  def astParentFullNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.astParentFullName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.astParentFullName).matches }
    }
  }

  /** Traverse to nodes where astParentFullName does not match any of the regular expressions in `values`.
    */
  def astParentFullNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.astParentFullName).matches }.isEmpty }
  }

  /** Traverse to astParentType property */
  def astParentType: Iterator[String] =
    traversal.map(_.astParentType)

  /** Traverse to nodes where the astParentType matches the regular expression `value`
    */
  def astParentType(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      astParentTypeExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.astParentType).matches }
    }

  /** Traverse to nodes where the astParentType matches at least one of the regular expressions in `values`
    */
  def astParentType(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.astParentType).matches } }
  }

  /** Traverse to nodes where astParentType matches `value` exactly.
    */
  def astParentTypeExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 4, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.astParentType == value }
  }

  /** Traverse to nodes where astParentType matches one of the elements in `values` exactly.
    */
  def astParentTypeExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return astParentTypeExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 4, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.astParentType) }
    }
  }

  /** Traverse to nodes where astParentType does not match the regular expression `value`.
    */
  def astParentTypeNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.astParentType != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.astParentType).matches }
    }
  }

  /** Traverse to nodes where astParentType does not match any of the regular expressions in `values`.
    */
  def astParentTypeNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.astParentType).matches }.isEmpty }
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
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 19, value)
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
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 19, value)
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

  /** Traverse to fullName property */
  def fullName: Iterator[String] =
    traversal.map(_.fullName)

  /** Traverse to nodes where the fullName matches the regular expression `value`
    */
  def fullName(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      fullNameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.fullName).matches }
    }

  /** Traverse to nodes where the fullName matches at least one of the regular expressions in `values`
    */
  def fullName(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.fullName).matches } }
  }

  /** Traverse to nodes where fullName matches `value` exactly.
    */
  def fullNameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 20, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.fullName == value }
  }

  /** Traverse to nodes where fullName matches one of the elements in `values` exactly.
    */
  def fullNameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return fullNameExact(values.head)
    traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        values.iterator.flatMap { value =>
          flatgraph.Accessors
            .getWithInverseIndex(someNode.graph, someNode.nodeKind, 20, value)
            .asInstanceOf[Iterator[NodeType]]
        }
      case _ =>
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.fullName) }
    }
  }

  /** Traverse to nodes where fullName does not match the regular expression `value`.
    */
  def fullNameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.fullName != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.fullName).matches }
    }
  }

  /** Traverse to nodes where fullName does not match any of the regular expressions in `values`.
    */
  def fullNameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.fullName).matches }.isEmpty }
  }

  /** Traverse to genericSignature property */
  def genericSignature: Iterator[String] =
    traversal.map(_.genericSignature)

  /** Traverse to nodes where the genericSignature matches the regular expression `value`
    */
  def genericSignature(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      genericSignatureExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.genericSignature).matches }
    }

  /** Traverse to nodes where the genericSignature matches at least one of the regular expressions in `values`
    */
  def genericSignature(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.genericSignature).matches } }
  }

  /** Traverse to nodes where genericSignature matches `value` exactly.
    */
  def genericSignatureExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 21, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.genericSignature == value }
  }

  /** Traverse to nodes where genericSignature matches one of the elements in `values` exactly.
    */
  def genericSignatureExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return genericSignatureExact(values.head)
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
        traversal.filter { item => valueSet.contains(item.genericSignature) }
    }
  }

  /** Traverse to nodes where genericSignature does not match the regular expression `value`.
    */
  def genericSignatureNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.genericSignature != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.genericSignature).matches }
    }
  }

  /** Traverse to nodes where genericSignature does not match any of the regular expressions in `values`.
    */
  def genericSignatureNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.genericSignature).matches }.isEmpty }
  }

  /** Traverse to inheritsFromTypeFullName property */
  def inheritsFromTypeFullName: Iterator[String] =
    traversal.flatMap(_.inheritsFromTypeFullName)

  /** Traverse to isExternal property */
  def isExternal: Iterator[Boolean] =
    traversal.map(_.isExternal)

  /** Traverse to nodes where the isExternal equals the given `value`
    */
  def isExternal(value: Boolean): Iterator[NodeType] =
    traversal.filter { _.isExternal == value }

  /** Traverse to name property */
  def name: Iterator[String] =
    traversal.map(_.name)

  /** Traverse to nodes where the name matches the regular expression `value`
    */
  def name(pattern: String): Iterator[NodeType] =
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      nameExact(pattern)
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filter { item => matcher.reset(item.name).matches }
    }

  /** Traverse to nodes where the name matches at least one of the regular expressions in `values`
    */
  def name(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.exists { _.reset(item.name).matches } }
  }

  /** Traverse to nodes where name matches `value` exactly.
    */
  def nameExact(value: String): Iterator[NodeType] = traversal match {
    case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
      val someNode = init.next
      flatgraph.Accessors
        .getWithInverseIndex(someNode.graph, someNode.nodeKind, 37, value)
        .asInstanceOf[Iterator[NodeType]]
    case _ => traversal.filter { _.name == value }
  }

  /** Traverse to nodes where name matches one of the elements in `values` exactly.
    */
  def nameExact(values: String*): Iterator[NodeType] = {
    if (values.length == 1) return nameExact(values.head)
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
        traversal.filter { item => valueSet.contains(item.name) }
    }
  }

  /** Traverse to nodes where name does not match the regular expression `value`.
    */
  def nameNot(pattern: String): Iterator[NodeType] = {
    if (!flatgraph.misc.Regex.isRegex(pattern)) {
      traversal.filter { node => node.name != pattern }
    } else {
      val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
      traversal.filterNot { item => matcher.reset(item.name).matches }
    }
  }

  /** Traverse to nodes where name does not match any of the regular expressions in `values`.
    */
  def nameNot(patterns: String*): Iterator[NodeType] = {
    val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
    traversal.filter { item => matchers.find { _.reset(item.name).matches }.isEmpty }
  }

}
