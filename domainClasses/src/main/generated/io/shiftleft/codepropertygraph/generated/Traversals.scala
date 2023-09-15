package io.shiftleft.codepropertygraph.generated.traversals
import io.shiftleft.codepropertygraph.generated.nodes

object Lang extends ConcreteStoredConversions

object Accessors {
  import io.shiftleft.codepropertygraph.generated.accessors.Lang.*

  /* accessors for concrete stored nodes start */
  final class Traversal_Property_ALIAS_TYPE_FULL_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasAliasTypeFullNameEMT]
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
  final class Traversal_Property_ARGUMENT_INDEX[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasArgumentIndexEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to argumentIndex property */
    def argumentIndex: Iterator[Int] =
      traversal.map(_.argumentIndex)

    /** Traverse to nodes where the argumentIndex equals the given `value`
      */
    def argumentIndex(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex == value }

    /** Traverse to nodes where the argumentIndex equals at least one of the given `values`
      */
    def argumentIndex(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => vset.contains(node.argumentIndex) }
    }

    /** Traverse to nodes where the argumentIndex is not equal to the given `value`
      */
    def argumentIndexNot(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex != value }

    /** Traverse to nodes where the argumentIndex is not equal to any of the given `values`
      */
    def argumentIndexNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => !vset.contains(node.argumentIndex) }
    }

    /** Traverse to nodes where the argumentIndex is greater than the given `value`
      */
    def argumentIndexGt(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex > value }

    /** Traverse to nodes where the argumentIndex is greater than or equal the given `value`
      */
    def argumentIndexGte(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex >= value }

    /** Traverse to nodes where the argumentIndex is less than the given `value`
      */
    def argumentIndexLt(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex < value }

    /** Traverse to nodes where the argumentIndex is less than or equal the given `value`
      */
    def argumentIndexLte(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex <= value }

  }
  final class Traversal_Property_ARGUMENT_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasArgumentNameEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to argumentName property */
    def argumentName: Iterator[String] =
      traversal.flatMap(_.argumentName)

    /** Traverse to nodes where the argumentName matches the regular expression `value`
      */
    def argumentName(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        argumentNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item =>
          val tmp = item.argumentName; tmp.isDefined && matcher.reset(tmp.get).matches
        }
      }
    }

    /** Traverse to nodes where the argumentName matches at least one of the regular expressions in `values`
      */
    def argumentName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item =>
        val tmp = item.argumentName; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
      }
    }

    /** Traverse to nodes where argumentName matches `value` exactly.
      */
    def argumentNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 2, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ =>
        traversal.filter { node =>
          val tmp = node.argumentName; tmp.isDefined && tmp.get == value
        }
    }

    /** Traverse to nodes where argumentName matches one of the elements in `values` exactly.
      */
    def argumentNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) argumentNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item =>
          val tmp = item.argumentName; tmp.isDefined && valueSet.contains(tmp.get)
        }
      }

    /** Traverse to nodes where argumentName does not match the regular expression `value`.
      */
    def argumentNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.argumentName.isEmpty || node.argumentName.get != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item =>
          val tmp = item.argumentName; tmp.isDefined && matcher.reset(tmp.get).matches
        }
      }
    }

    /** Traverse to nodes where argumentName does not match any of the regular expressions in `values`.
      */
    def argumentNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filterNot { item =>
        val tmp = item.argumentName; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
      }
    }

  }
  final class Traversal_Property_AST_PARENT_FULL_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasAstParentFullNameEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

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
    def astParentFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) astParentFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.astParentFullName) }
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

  }
  final class Traversal_Property_AST_PARENT_TYPE[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasAstParentTypeEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

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
    def astParentTypeExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) astParentTypeExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.astParentType) }
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

  }
  final class Traversal_Property_CANONICAL_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasCanonicalNameEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to canonicalName property */
    def canonicalName: Iterator[String] =
      traversal.map(_.canonicalName)

    /** Traverse to nodes where the canonicalName matches the regular expression `value`
      */
    def canonicalName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        canonicalNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.canonicalName).matches }
      }

    /** Traverse to nodes where the canonicalName matches at least one of the regular expressions in `values`
      */
    def canonicalName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.canonicalName).matches } }
    }

    /** Traverse to nodes where canonicalName matches `value` exactly.
      */
    def canonicalNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 5, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.canonicalName == value }
    }

    /** Traverse to nodes where canonicalName matches one of the elements in `values` exactly.
      */
    def canonicalNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) canonicalNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.canonicalName) }
      }

    /** Traverse to nodes where canonicalName does not match the regular expression `value`.
      */
    def canonicalNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.canonicalName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.canonicalName).matches }
      }
    }

    /** Traverse to nodes where canonicalName does not match any of the regular expressions in `values`.
      */
    def canonicalNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.canonicalName).matches }.isEmpty }
    }

  }
  final class Traversal_Property_CLASS_NAME[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasClassNameEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

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
    def classNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) classNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.className) }
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

  }
  final class Traversal_Property_CLASS_SHORT_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasClassShortNameEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

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
    def classShortNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) classShortNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.classShortName) }
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

  }
  final class Traversal_Property_CLOSURE_BINDING_ID[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasClosureBindingIdEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to closureBindingId property */
    def closureBindingId: Iterator[String] =
      traversal.flatMap(_.closureBindingId)

    /** Traverse to nodes where the closureBindingId matches the regular expression `value`
      */
    def closureBindingId(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        closureBindingIdExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item =>
          val tmp = item.closureBindingId; tmp.isDefined && matcher.reset(tmp.get).matches
        }
      }
    }

    /** Traverse to nodes where the closureBindingId matches at least one of the regular expressions in `values`
      */
    def closureBindingId(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item =>
        val tmp = item.closureBindingId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
      }
    }

    /** Traverse to nodes where closureBindingId matches `value` exactly.
      */
    def closureBindingIdExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 8, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ =>
        traversal.filter { node =>
          val tmp = node.closureBindingId; tmp.isDefined && tmp.get == value
        }
    }

    /** Traverse to nodes where closureBindingId matches one of the elements in `values` exactly.
      */
    def closureBindingIdExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) closureBindingIdExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item =>
          val tmp = item.closureBindingId; tmp.isDefined && valueSet.contains(tmp.get)
        }
      }

    /** Traverse to nodes where closureBindingId does not match the regular expression `value`.
      */
    def closureBindingIdNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.closureBindingId.isEmpty || node.closureBindingId.get != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item =>
          val tmp = item.closureBindingId; tmp.isDefined && matcher.reset(tmp.get).matches
        }
      }
    }

    /** Traverse to nodes where closureBindingId does not match any of the regular expressions in `values`.
      */
    def closureBindingIdNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filterNot { item =>
        val tmp = item.closureBindingId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
      }
    }

  }
  final class Traversal_Property_CLOSURE_ORIGINAL_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasClosureOriginalNameEMT]
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
  final class Traversal_Property_CODE[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasCodeEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to code property */
    def code: Iterator[String] =
      traversal.map(_.code)

    /** Traverse to nodes where the code matches the regular expression `value`
      */
    def code(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        codeExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.code).matches }
      }

    /** Traverse to nodes where the code matches at least one of the regular expressions in `values`
      */
    def code(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.code).matches } }
    }

    /** Traverse to nodes where code matches `value` exactly.
      */
    def codeExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 10, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.code == value }
    }

    /** Traverse to nodes where code matches one of the elements in `values` exactly.
      */
    def codeExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) codeExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.code) }
      }

    /** Traverse to nodes where code does not match the regular expression `value`.
      */
    def codeNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.code != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.code).matches }
      }
    }

    /** Traverse to nodes where code does not match any of the regular expressions in `values`.
      */
    def codeNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.code).matches }.isEmpty }
    }

  }
  final class Traversal_Property_COLUMN_NUMBER[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasColumnNumberEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to columnNumber property */
    def columnNumber: Iterator[Int] =
      traversal.flatMap(_.columnNumber)

    /** Traverse to nodes where the columnNumber equals the given `value`
      */
    def columnNumber(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isDefined && tmp.get == value
      }

    /** Traverse to nodes where the columnNumber equals at least one of the given `values`
      */
    def columnNumber(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isDefined && vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the columnNumber is not equal to the given `value`
      */
    def columnNumberNot(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isEmpty || tmp.get != value
      }

    /** Traverse to nodes where the columnNumber does not equal any one of the given `values`
      */
    def columnNumberNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isEmpty || !vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the columnNumber is greater than the given `value`
      */
    def columnNumberGt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isDefined && tmp.get > value
      }

    /** Traverse to nodes where the columnNumber is greater than or equal the given `value`
      */
    def columnNumberGte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isDefined && tmp.get >= value
      }

    /** Traverse to nodes where the columnNumber is less than the given `value`
      */
    def columnNumberLt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isDefined && tmp.get < value
      }

    /** Traverse to nodes where the columnNumber is less than or equal the given `value`
      */
    def columnNumberLte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isDefined && tmp.get <= value
      }

  }
  final class Traversal_Property_COLUMN_NUMBER_END[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasColumnNumberEndEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to columnNumberEnd property */
    def columnNumberEnd: Iterator[Int] =
      traversal.flatMap(_.columnNumberEnd)

    /** Traverse to nodes where the columnNumberEnd equals the given `value`
      */
    def columnNumberEnd(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get == value
      }

    /** Traverse to nodes where the columnNumberEnd equals at least one of the given `values`
      */
    def columnNumberEnd(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isDefined && vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the columnNumberEnd is not equal to the given `value`
      */
    def columnNumberEndNot(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isEmpty || tmp.get != value
      }

    /** Traverse to nodes where the columnNumberEnd does not equal any one of the given `values`
      */
    def columnNumberEndNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isEmpty || !vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the columnNumberEnd is greater than the given `value`
      */
    def columnNumberEndGt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get > value
      }

    /** Traverse to nodes where the columnNumberEnd is greater than or equal the given `value`
      */
    def columnNumberEndGte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get >= value
      }

    /** Traverse to nodes where the columnNumberEnd is less than the given `value`
      */
    def columnNumberEndLt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get < value
      }

    /** Traverse to nodes where the columnNumberEnd is less than or equal the given `value`
      */
    def columnNumberEndLte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get <= value
      }

  }
  final class Traversal_Property_CONTAINED_REF[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasContainedRefEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to containedRef property */
    def containedRef: Iterator[String] =
      traversal.map(_.containedRef)

    /** Traverse to nodes where the containedRef matches the regular expression `value`
      */
    def containedRef(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        containedRefExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.containedRef).matches }
      }

    /** Traverse to nodes where the containedRef matches at least one of the regular expressions in `values`
      */
    def containedRef(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.containedRef).matches } }
    }

    /** Traverse to nodes where containedRef matches `value` exactly.
      */
    def containedRefExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 13, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.containedRef == value }
    }

    /** Traverse to nodes where containedRef matches one of the elements in `values` exactly.
      */
    def containedRefExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) containedRefExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.containedRef) }
      }

    /** Traverse to nodes where containedRef does not match the regular expression `value`.
      */
    def containedRefNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.containedRef != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.containedRef).matches }
      }
    }

    /** Traverse to nodes where containedRef does not match any of the regular expressions in `values`.
      */
    def containedRefNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.containedRef).matches }.isEmpty }
    }

  }
  final class Traversal_Property_CONTENT[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasContentEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to content property */
    def content: Iterator[String] =
      traversal.map(_.content)

    /** Traverse to nodes where the content matches the regular expression `value`
      */
    def content(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        contentExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.content).matches }
      }

    /** Traverse to nodes where the content matches at least one of the regular expressions in `values`
      */
    def content(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.content).matches } }
    }

    /** Traverse to nodes where content matches `value` exactly.
      */
    def contentExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 14, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.content == value }
    }

    /** Traverse to nodes where content matches one of the elements in `values` exactly.
      */
    def contentExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) contentExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.content) }
      }

    /** Traverse to nodes where content does not match the regular expression `value`.
      */
    def contentNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.content != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.content).matches }
      }
    }

    /** Traverse to nodes where content does not match any of the regular expressions in `values`.
      */
    def contentNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.content).matches }.isEmpty }
    }

  }
  final class Traversal_Property_CONTROL_STRUCTURE_TYPE[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasControlStructureTypeEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to controlStructureType property */
    def controlStructureType: Iterator[String] =
      traversal.map(_.controlStructureType)

    /** Traverse to nodes where the controlStructureType matches the regular expression `value`
      */
    def controlStructureType(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        controlStructureTypeExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.controlStructureType).matches }
      }

    /** Traverse to nodes where the controlStructureType matches at least one of the regular expressions in `values`
      */
    def controlStructureType(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.controlStructureType).matches } }
    }

    /** Traverse to nodes where controlStructureType matches `value` exactly.
      */
    def controlStructureTypeExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 15, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.controlStructureType == value }
    }

    /** Traverse to nodes where controlStructureType matches one of the elements in `values` exactly.
      */
    def controlStructureTypeExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) controlStructureTypeExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.controlStructureType) }
      }

    /** Traverse to nodes where controlStructureType does not match the regular expression `value`.
      */
    def controlStructureTypeNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.controlStructureType != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.controlStructureType).matches }
      }
    }

    /** Traverse to nodes where controlStructureType does not match any of the regular expressions in `values`.
      */
    def controlStructureTypeNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.controlStructureType).matches }.isEmpty }
    }

  }
  final class Traversal_Property_DEPENDENCY_GROUP_ID[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasDependencyGroupIdEMT]
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
  final class Traversal_Property_DISPATCH_TYPE[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasDispatchTypeEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to dispatchType property */
    def dispatchType: Iterator[String] =
      traversal.map(_.dispatchType)

    /** Traverse to nodes where the dispatchType matches the regular expression `value`
      */
    def dispatchType(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        dispatchTypeExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.dispatchType).matches }
      }

    /** Traverse to nodes where the dispatchType matches at least one of the regular expressions in `values`
      */
    def dispatchType(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.dispatchType).matches } }
    }

    /** Traverse to nodes where dispatchType matches `value` exactly.
      */
    def dispatchTypeExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 17, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.dispatchType == value }
    }

    /** Traverse to nodes where dispatchType matches one of the elements in `values` exactly.
      */
    def dispatchTypeExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) dispatchTypeExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.dispatchType) }
      }

    /** Traverse to nodes where dispatchType does not match the regular expression `value`.
      */
    def dispatchTypeNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.dispatchType != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.dispatchType).matches }
      }
    }

    /** Traverse to nodes where dispatchType does not match any of the regular expressions in `values`.
      */
    def dispatchTypeNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.dispatchType).matches }.isEmpty }
    }

  }
  final class Traversal_Property_DYNAMIC_TYPE_HINT_FULL_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasDynamicTypeHintFullNameEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to dynamicTypeHintFullName property */
    def dynamicTypeHintFullName: Iterator[String] =
      traversal.flatMap(_.dynamicTypeHintFullName)

  }
  final class Traversal_Property_EVALUATION_STRATEGY[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasEvaluationStrategyEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to evaluationStrategy property */
    def evaluationStrategy: Iterator[String] =
      traversal.map(_.evaluationStrategy)

    /** Traverse to nodes where the evaluationStrategy matches the regular expression `value`
      */
    def evaluationStrategy(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        evaluationStrategyExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.evaluationStrategy).matches }
      }

    /** Traverse to nodes where the evaluationStrategy matches at least one of the regular expressions in `values`
      */
    def evaluationStrategy(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.evaluationStrategy).matches } }
    }

    /** Traverse to nodes where evaluationStrategy matches `value` exactly.
      */
    def evaluationStrategyExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 19, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.evaluationStrategy == value }
    }

    /** Traverse to nodes where evaluationStrategy matches one of the elements in `values` exactly.
      */
    def evaluationStrategyExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) evaluationStrategyExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.evaluationStrategy) }
      }

    /** Traverse to nodes where evaluationStrategy does not match the regular expression `value`.
      */
    def evaluationStrategyNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.evaluationStrategy != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.evaluationStrategy).matches }
      }
    }

    /** Traverse to nodes where evaluationStrategy does not match any of the regular expressions in `values`.
      */
    def evaluationStrategyNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.evaluationStrategy).matches }.isEmpty }
    }

  }
  final class Traversal_Property_EXPLICIT_AS[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasExplicitAsEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to explicitAs property */
    def explicitAs: Iterator[Boolean] =
      traversal.flatMap(_.explicitAs)

    /** Traverse to nodes where the explicitAs equals the given `value`
      */
    def explicitAs(value: Boolean): Iterator[NodeType] =
      traversal.filter { node => node.explicitAs.isDefined && node.explicitAs.get == value }

  }
  final class Traversal_Property_FILENAME[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasFilenameEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

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
    def filenameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) filenameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.filename) }
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

  }
  final class Traversal_Property_FULL_NAME[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasFullNameEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 22, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.fullName == value }
    }

    /** Traverse to nodes where fullName matches one of the elements in `values` exactly.
      */
    def fullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) fullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.fullName) }
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

  }
  final class Traversal_Property_HASH[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasHashEMT]](
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
  final class Traversal_Property_IMPORTED_AS[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasImportedAsEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

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
  final class Traversal_Property_IMPORTED_ENTITY[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasImportedEntityEMT]
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
  final class Traversal_Property_INDEX[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasIndexEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to index property */
    def index: Iterator[Int] =
      traversal.map(_.index)

    /** Traverse to nodes where the index equals the given `value`
      */
    def index(value: Int): Iterator[NodeType] =
      traversal.filter { _.index == value }

    /** Traverse to nodes where the index equals at least one of the given `values`
      */
    def index(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => vset.contains(node.index) }
    }

    /** Traverse to nodes where the index is not equal to the given `value`
      */
    def indexNot(value: Int): Iterator[NodeType] =
      traversal.filter { _.index != value }

    /** Traverse to nodes where the index is not equal to any of the given `values`
      */
    def indexNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => !vset.contains(node.index) }
    }

    /** Traverse to nodes where the index is greater than the given `value`
      */
    def indexGt(value: Int): Iterator[NodeType] =
      traversal.filter { _.index > value }

    /** Traverse to nodes where the index is greater than or equal the given `value`
      */
    def indexGte(value: Int): Iterator[NodeType] =
      traversal.filter { _.index >= value }

    /** Traverse to nodes where the index is less than the given `value`
      */
    def indexLt(value: Int): Iterator[NodeType] =
      traversal.filter { _.index < value }

    /** Traverse to nodes where the index is less than or equal the given `value`
      */
    def indexLte(value: Int): Iterator[NodeType] =
      traversal.filter { _.index <= value }

  }
  final class Traversal_Property_INHERITS_FROM_TYPE_FULL_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasInheritsFromTypeFullNameEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to inheritsFromTypeFullName property */
    def inheritsFromTypeFullName: Iterator[String] =
      traversal.flatMap(_.inheritsFromTypeFullName)

  }
  final class Traversal_Property_IS_EXPLICIT[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasIsExplicitEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to isExplicit property */
    def isExplicit: Iterator[Boolean] =
      traversal.flatMap(_.isExplicit)

    /** Traverse to nodes where the isExplicit equals the given `value`
      */
    def isExplicit(value: Boolean): Iterator[NodeType] =
      traversal.filter { node => node.isExplicit.isDefined && node.isExplicit.get == value }

  }
  final class Traversal_Property_IS_EXTERNAL[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasIsExternalEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to isExternal property */
    def isExternal: Iterator[Boolean] =
      traversal.map(_.isExternal)

    /** Traverse to nodes where the isExternal equals the given `value`
      */
    def isExternal(value: Boolean): Iterator[NodeType] =
      traversal.filter { _.isExternal == value }

  }
  final class Traversal_Property_IS_VARIADIC[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasIsVariadicEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to isVariadic property */
    def isVariadic: Iterator[Boolean] =
      traversal.map(_.isVariadic)

    /** Traverse to nodes where the isVariadic equals the given `value`
      */
    def isVariadic(value: Boolean): Iterator[NodeType] =
      traversal.filter { _.isVariadic == value }

  }
  final class Traversal_Property_IS_WILDCARD[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasIsWildcardEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to isWildcard property */
    def isWildcard: Iterator[Boolean] =
      traversal.flatMap(_.isWildcard)

    /** Traverse to nodes where the isWildcard equals the given `value`
      */
    def isWildcard(value: Boolean): Iterator[NodeType] =
      traversal.filter { node => node.isWildcard.isDefined && node.isWildcard.get == value }

  }
  final class Traversal_Property_KEY[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasKeyEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to key property */
    def key: Iterator[String] =
      traversal.map(_.key)

    /** Traverse to nodes where the key matches the regular expression `value`
      */
    def key(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        keyExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.key).matches }
      }

    /** Traverse to nodes where the key matches at least one of the regular expressions in `values`
      */
    def key(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.key).matches } }
    }

    /** Traverse to nodes where key matches `value` exactly.
      */
    def keyExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 32, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.key == value }
    }

    /** Traverse to nodes where key matches one of the elements in `values` exactly.
      */
    def keyExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) keyExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.key) }
      }

    /** Traverse to nodes where key does not match the regular expression `value`.
      */
    def keyNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.key != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.key).matches }
      }
    }

    /** Traverse to nodes where key does not match any of the regular expressions in `values`.
      */
    def keyNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.key).matches }.isEmpty }
    }

  }
  final class Traversal_Property_LANGUAGE[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasLanguageEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to language property */
    def language: Iterator[String] =
      traversal.map(_.language)

    /** Traverse to nodes where the language matches the regular expression `value`
      */
    def language(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        languageExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.language).matches }
      }

    /** Traverse to nodes where the language matches at least one of the regular expressions in `values`
      */
    def language(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.language).matches } }
    }

    /** Traverse to nodes where language matches `value` exactly.
      */
    def languageExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 33, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.language == value }
    }

    /** Traverse to nodes where language matches one of the elements in `values` exactly.
      */
    def languageExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) languageExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.language) }
      }

    /** Traverse to nodes where language does not match the regular expression `value`.
      */
    def languageNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.language != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.language).matches }
      }
    }

    /** Traverse to nodes where language does not match any of the regular expressions in `values`.
      */
    def languageNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.language).matches }.isEmpty }
    }

  }
  final class Traversal_Property_LINE_NUMBER[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasLineNumberEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

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

  }
  final class Traversal_Property_LINE_NUMBER_END[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasLineNumberEndEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to lineNumberEnd property */
    def lineNumberEnd: Iterator[Int] =
      traversal.flatMap(_.lineNumberEnd)

    /** Traverse to nodes where the lineNumberEnd equals the given `value`
      */
    def lineNumberEnd(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get == value
      }

    /** Traverse to nodes where the lineNumberEnd equals at least one of the given `values`
      */
    def lineNumberEnd(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isDefined && vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the lineNumberEnd is not equal to the given `value`
      */
    def lineNumberEndNot(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isEmpty || tmp.get != value
      }

    /** Traverse to nodes where the lineNumberEnd does not equal any one of the given `values`
      */
    def lineNumberEndNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isEmpty || !vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the lineNumberEnd is greater than the given `value`
      */
    def lineNumberEndGt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get > value
      }

    /** Traverse to nodes where the lineNumberEnd is greater than or equal the given `value`
      */
    def lineNumberEndGte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get >= value
      }

    /** Traverse to nodes where the lineNumberEnd is less than the given `value`
      */
    def lineNumberEndLt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get < value
      }

    /** Traverse to nodes where the lineNumberEnd is less than or equal the given `value`
      */
    def lineNumberEndLte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get <= value
      }

  }
  final class Traversal_Property_METHOD_FULL_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasMethodFullNameEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 36, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.methodFullName == value }
    }

    /** Traverse to nodes where methodFullName matches one of the elements in `values` exactly.
      */
    def methodFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) methodFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.methodFullName) }
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

  }
  final class Traversal_Property_METHOD_SHORT_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasMethodShortNameEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 37, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.methodShortName == value }
    }

    /** Traverse to nodes where methodShortName matches one of the elements in `values` exactly.
      */
    def methodShortNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) methodShortNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.methodShortName) }
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

  }
  final class Traversal_Property_MODIFIER_TYPE[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasModifierTypeEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to modifierType property */
    def modifierType: Iterator[String] =
      traversal.map(_.modifierType)

    /** Traverse to nodes where the modifierType matches the regular expression `value`
      */
    def modifierType(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        modifierTypeExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.modifierType).matches }
      }

    /** Traverse to nodes where the modifierType matches at least one of the regular expressions in `values`
      */
    def modifierType(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.modifierType).matches } }
    }

    /** Traverse to nodes where modifierType matches `value` exactly.
      */
    def modifierTypeExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 38, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.modifierType == value }
    }

    /** Traverse to nodes where modifierType matches one of the elements in `values` exactly.
      */
    def modifierTypeExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) modifierTypeExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.modifierType) }
      }

    /** Traverse to nodes where modifierType does not match the regular expression `value`.
      */
    def modifierTypeNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.modifierType != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.modifierType).matches }
      }
    }

    /** Traverse to nodes where modifierType does not match any of the regular expressions in `values`.
      */
    def modifierTypeNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.modifierType).matches }.isEmpty }
    }

  }
  final class Traversal_Property_NAME[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasNameEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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
  final class Traversal_Property_NODE_LABEL[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasNodeLabelEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 40, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.nodeLabel == value }
    }

    /** Traverse to nodes where nodeLabel matches one of the elements in `values` exactly.
      */
    def nodeLabelExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nodeLabelExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.nodeLabel) }
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

  }
  final class Traversal_Property_OFFSET[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasOffsetEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to offset property */
    def offset: Iterator[Int] =
      traversal.flatMap(_.offset)

    /** Traverse to nodes where the offset equals the given `value`
      */
    def offset(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get == value
      }

    /** Traverse to nodes where the offset equals at least one of the given `values`
      */
    def offset(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the offset is not equal to the given `value`
      */
    def offsetNot(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isEmpty || tmp.get != value
      }

    /** Traverse to nodes where the offset does not equal any one of the given `values`
      */
    def offsetNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.offset; tmp.isEmpty || !vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the offset is greater than the given `value`
      */
    def offsetGt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get > value
      }

    /** Traverse to nodes where the offset is greater than or equal the given `value`
      */
    def offsetGte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get >= value
      }

    /** Traverse to nodes where the offset is less than the given `value`
      */
    def offsetLt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get < value
      }

    /** Traverse to nodes where the offset is less than or equal the given `value`
      */
    def offsetLte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get <= value
      }

  }
  final class Traversal_Property_OFFSET_END[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasOffsetEndEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to offsetEnd property */
    def offsetEnd: Iterator[Int] =
      traversal.flatMap(_.offsetEnd)

    /** Traverse to nodes where the offsetEnd equals the given `value`
      */
    def offsetEnd(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get == value
      }

    /** Traverse to nodes where the offsetEnd equals at least one of the given `values`
      */
    def offsetEnd(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the offsetEnd is not equal to the given `value`
      */
    def offsetEndNot(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isEmpty || tmp.get != value
      }

    /** Traverse to nodes where the offsetEnd does not equal any one of the given `values`
      */
    def offsetEndNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isEmpty || !vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the offsetEnd is greater than the given `value`
      */
    def offsetEndGt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get > value
      }

    /** Traverse to nodes where the offsetEnd is greater than or equal the given `value`
      */
    def offsetEndGte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get >= value
      }

    /** Traverse to nodes where the offsetEnd is less than the given `value`
      */
    def offsetEndLt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get < value
      }

    /** Traverse to nodes where the offsetEnd is less than or equal the given `value`
      */
    def offsetEndLte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get <= value
      }

  }
  final class Traversal_Property_ORDER[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasOrderEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to order property */
    def order: Iterator[Int] =
      traversal.map(_.order)

    /** Traverse to nodes where the order equals the given `value`
      */
    def order(value: Int): Iterator[NodeType] =
      traversal.filter { _.order == value }

    /** Traverse to nodes where the order equals at least one of the given `values`
      */
    def order(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => vset.contains(node.order) }
    }

    /** Traverse to nodes where the order is not equal to the given `value`
      */
    def orderNot(value: Int): Iterator[NodeType] =
      traversal.filter { _.order != value }

    /** Traverse to nodes where the order is not equal to any of the given `values`
      */
    def orderNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => !vset.contains(node.order) }
    }

    /** Traverse to nodes where the order is greater than the given `value`
      */
    def orderGt(value: Int): Iterator[NodeType] =
      traversal.filter { _.order > value }

    /** Traverse to nodes where the order is greater than or equal the given `value`
      */
    def orderGte(value: Int): Iterator[NodeType] =
      traversal.filter { _.order >= value }

    /** Traverse to nodes where the order is less than the given `value`
      */
    def orderLt(value: Int): Iterator[NodeType] =
      traversal.filter { _.order < value }

    /** Traverse to nodes where the order is less than or equal the given `value`
      */
    def orderLte(value: Int): Iterator[NodeType] =
      traversal.filter { _.order <= value }

  }
  final class Traversal_Property_OVERLAYS[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasOverlaysEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to overlays property */
    def overlays: Iterator[String] =
      traversal.flatMap(_.overlays)

  }
  final class Traversal_Property_PACKAGE_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasPackageNameEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 45, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.packageName == value }
    }

    /** Traverse to nodes where packageName matches one of the elements in `values` exactly.
      */
    def packageNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) packageNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.packageName) }
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

  }
  final class Traversal_Property_PARSER_TYPE_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasParserTypeNameEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to parserTypeName property */
    def parserTypeName: Iterator[String] =
      traversal.map(_.parserTypeName)

    /** Traverse to nodes where the parserTypeName matches the regular expression `value`
      */
    def parserTypeName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        parserTypeNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.parserTypeName).matches }
      }

    /** Traverse to nodes where the parserTypeName matches at least one of the regular expressions in `values`
      */
    def parserTypeName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.parserTypeName).matches } }
    }

    /** Traverse to nodes where parserTypeName matches `value` exactly.
      */
    def parserTypeNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 46, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.parserTypeName == value }
    }

    /** Traverse to nodes where parserTypeName matches one of the elements in `values` exactly.
      */
    def parserTypeNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) parserTypeNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.parserTypeName) }
      }

    /** Traverse to nodes where parserTypeName does not match the regular expression `value`.
      */
    def parserTypeNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.parserTypeName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.parserTypeName).matches }
      }
    }

    /** Traverse to nodes where parserTypeName does not match any of the regular expressions in `values`.
      */
    def parserTypeNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.parserTypeName).matches }.isEmpty }
    }

  }
  final class Traversal_Property_POSSIBLE_TYPES[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasPossibleTypesEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to possibleTypes property */
    def possibleTypes: Iterator[String] =
      traversal.flatMap(_.possibleTypes)

  }
  final class Traversal_Property_ROOT[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasRootEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to root property */
    def root: Iterator[String] =
      traversal.map(_.root)

    /** Traverse to nodes where the root matches the regular expression `value`
      */
    def root(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        rootExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.root).matches }
      }

    /** Traverse to nodes where the root matches at least one of the regular expressions in `values`
      */
    def root(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.root).matches } }
    }

    /** Traverse to nodes where root matches `value` exactly.
      */
    def rootExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 48, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.root == value }
    }

    /** Traverse to nodes where root matches one of the elements in `values` exactly.
      */
    def rootExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) rootExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.root) }
      }

    /** Traverse to nodes where root does not match the regular expression `value`.
      */
    def rootNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.root != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.root).matches }
      }
    }

    /** Traverse to nodes where root does not match any of the regular expressions in `values`.
      */
    def rootNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.root).matches }.isEmpty }
    }

  }
  final class Traversal_Property_SIGNATURE[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasSignatureEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to signature property */
    def signature: Iterator[String] =
      traversal.map(_.signature)

    /** Traverse to nodes where the signature matches the regular expression `value`
      */
    def signature(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        signatureExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.signature).matches }
      }

    /** Traverse to nodes where the signature matches at least one of the regular expressions in `values`
      */
    def signature(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.signature).matches } }
    }

    /** Traverse to nodes where signature matches `value` exactly.
      */
    def signatureExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 49, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.signature == value }
    }

    /** Traverse to nodes where signature matches one of the elements in `values` exactly.
      */
    def signatureExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) signatureExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.signature) }
      }

    /** Traverse to nodes where signature does not match the regular expression `value`.
      */
    def signatureNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.signature != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.signature).matches }
      }
    }

    /** Traverse to nodes where signature does not match any of the regular expressions in `values`.
      */
    def signatureNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.signature).matches }.isEmpty }
    }

  }
  final class Traversal_Property_SYMBOL[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasSymbolEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 50, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.symbol == value }
    }

    /** Traverse to nodes where symbol matches one of the elements in `values` exactly.
      */
    def symbolExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) symbolExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.symbol) }
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
  final class Traversal_Property_TYPE_DECL_FULL_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasTypeDeclFullNameEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to typeDeclFullName property */
    def typeDeclFullName: Iterator[String] =
      traversal.map(_.typeDeclFullName)

    /** Traverse to nodes where the typeDeclFullName matches the regular expression `value`
      */
    def typeDeclFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeDeclFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeDeclFullName).matches }
      }

    /** Traverse to nodes where the typeDeclFullName matches at least one of the regular expressions in `values`
      */
    def typeDeclFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeDeclFullName).matches } }
    }

    /** Traverse to nodes where typeDeclFullName matches `value` exactly.
      */
    def typeDeclFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 51, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeDeclFullName == value }
    }

    /** Traverse to nodes where typeDeclFullName matches one of the elements in `values` exactly.
      */
    def typeDeclFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeDeclFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeDeclFullName) }
      }

    /** Traverse to nodes where typeDeclFullName does not match the regular expression `value`.
      */
    def typeDeclFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeDeclFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeDeclFullName).matches }
      }
    }

    /** Traverse to nodes where typeDeclFullName does not match any of the regular expressions in `values`.
      */
    def typeDeclFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeDeclFullName).matches }.isEmpty }
    }

  }
  final class Traversal_Property_TYPE_FULL_NAME[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasTypeFullNameEMT]
  ](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_Property_VALUE[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasValueEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to value property */
    def value: Iterator[String] =
      traversal.map(_.value)

    /** Traverse to nodes where the value matches the regular expression `value`
      */
    def value(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        valueExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.value).matches }
      }

    /** Traverse to nodes where the value matches at least one of the regular expressions in `values`
      */
    def value(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.value).matches } }
    }

    /** Traverse to nodes where value matches `value` exactly.
      */
    def valueExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 53, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.value == value }
    }

    /** Traverse to nodes where value matches one of the elements in `values` exactly.
      */
    def valueExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) valueExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.value) }
      }

    /** Traverse to nodes where value does not match the regular expression `value`.
      */
    def valueNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.value != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.value).matches }
      }
    }

    /** Traverse to nodes where value does not match any of the regular expressions in `values`.
      */
    def valueNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.value).matches }.isEmpty }
    }

  }
  final class Traversal_Property_VERSION[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasVersionEMT]](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to version property */
    def version: Iterator[String] =
      traversal.map(_.version)

    /** Traverse to nodes where the version matches the regular expression `value`
      */
    def version(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        versionExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.version).matches }
      }

    /** Traverse to nodes where the version matches at least one of the regular expressions in `values`
      */
    def version(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.version).matches } }
    }

    /** Traverse to nodes where version matches `value` exactly.
      */
    def versionExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 54, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.version == value }
    }

    /** Traverse to nodes where version matches one of the elements in `values` exactly.
      */
    def versionExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) versionExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.version) }
      }

    /** Traverse to nodes where version does not match the regular expression `value`.
      */
    def versionNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.version != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.version).matches }
      }
    }

    /** Traverse to nodes where version does not match any of the regular expressions in `values`.
      */
    def versionNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.version).matches }.isEmpty }
    }

  }
  /* accessors for concrete stored nodes end */

  /* accessors for base nodes start */
  final class Traversal_AnnotationBase[NodeType <: nodes.AnnotationBase](val traversal: Iterator[NodeType])
      extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 22, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.fullName == value }
    }

    /** Traverse to nodes where fullName matches one of the elements in `values` exactly.
      */
    def fullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) fullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.fullName) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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
  final class Traversal_AnnotationLiteralBase[NodeType <: nodes.AnnotationLiteralBase](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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
  final class Traversal_AnnotationParameterBase[NodeType <: nodes.AnnotationParameterBase](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {}
  final class Traversal_AnnotationParameterAssignBase[NodeType <: nodes.AnnotationParameterAssignBase](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {}
  final class Traversal_ArrayInitializerBase[NodeType <: nodes.ArrayInitializerBase](val traversal: Iterator[NodeType])
      extends AnyVal {}
  final class Traversal_BindingBase[NodeType <: nodes.BindingBase](val traversal: Iterator[NodeType]) extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 36, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.methodFullName == value }
    }

    /** Traverse to nodes where methodFullName matches one of the elements in `values` exactly.
      */
    def methodFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) methodFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.methodFullName) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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

    /** Traverse to signature property */
    def signature: Iterator[String] =
      traversal.map(_.signature)

    /** Traverse to nodes where the signature matches the regular expression `value`
      */
    def signature(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        signatureExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.signature).matches }
      }

    /** Traverse to nodes where the signature matches at least one of the regular expressions in `values`
      */
    def signature(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.signature).matches } }
    }

    /** Traverse to nodes where signature matches `value` exactly.
      */
    def signatureExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 49, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.signature == value }
    }

    /** Traverse to nodes where signature matches one of the elements in `values` exactly.
      */
    def signatureExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) signatureExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.signature) }
      }

    /** Traverse to nodes where signature does not match the regular expression `value`.
      */
    def signatureNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.signature != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.signature).matches }
      }
    }

    /** Traverse to nodes where signature does not match any of the regular expressions in `values`.
      */
    def signatureNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.signature).matches }.isEmpty }
    }

  }
  final class Traversal_BlockBase[NodeType <: nodes.BlockBase](val traversal: Iterator[NodeType]) extends AnyVal {

    /** Traverse to dynamicTypeHintFullName property */
    def dynamicTypeHintFullName: Iterator[String] =
      traversal.flatMap(_.dynamicTypeHintFullName)

    /** Traverse to possibleTypes property */
    def possibleTypes: Iterator[String] =
      traversal.flatMap(_.possibleTypes)

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_CallBase[NodeType <: nodes.CallBase](val traversal: Iterator[NodeType]) extends AnyVal {

    /** Traverse to dispatchType property */
    def dispatchType: Iterator[String] =
      traversal.map(_.dispatchType)

    /** Traverse to nodes where the dispatchType matches the regular expression `value`
      */
    def dispatchType(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        dispatchTypeExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.dispatchType).matches }
      }

    /** Traverse to nodes where the dispatchType matches at least one of the regular expressions in `values`
      */
    def dispatchType(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.dispatchType).matches } }
    }

    /** Traverse to nodes where dispatchType matches `value` exactly.
      */
    def dispatchTypeExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 17, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.dispatchType == value }
    }

    /** Traverse to nodes where dispatchType matches one of the elements in `values` exactly.
      */
    def dispatchTypeExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) dispatchTypeExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.dispatchType) }
      }

    /** Traverse to nodes where dispatchType does not match the regular expression `value`.
      */
    def dispatchTypeNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.dispatchType != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.dispatchType).matches }
      }
    }

    /** Traverse to nodes where dispatchType does not match any of the regular expressions in `values`.
      */
    def dispatchTypeNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.dispatchType).matches }.isEmpty }
    }

    /** Traverse to dynamicTypeHintFullName property */
    def dynamicTypeHintFullName: Iterator[String] =
      traversal.flatMap(_.dynamicTypeHintFullName)

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 36, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.methodFullName == value }
    }

    /** Traverse to nodes where methodFullName matches one of the elements in `values` exactly.
      */
    def methodFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) methodFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.methodFullName) }
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

    /** Traverse to possibleTypes property */
    def possibleTypes: Iterator[String] =
      traversal.flatMap(_.possibleTypes)

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_ClosureBindingBase[NodeType <: nodes.ClosureBindingBase](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to closureBindingId property */
    def closureBindingId: Iterator[String] =
      traversal.flatMap(_.closureBindingId)

    /** Traverse to nodes where the closureBindingId matches the regular expression `value`
      */
    def closureBindingId(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        closureBindingIdExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item =>
          val tmp = item.closureBindingId; tmp.isDefined && matcher.reset(tmp.get).matches
        }
      }
    }

    /** Traverse to nodes where the closureBindingId matches at least one of the regular expressions in `values`
      */
    def closureBindingId(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item =>
        val tmp = item.closureBindingId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
      }
    }

    /** Traverse to nodes where closureBindingId matches `value` exactly.
      */
    def closureBindingIdExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 8, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ =>
        traversal.filter { node =>
          val tmp = node.closureBindingId; tmp.isDefined && tmp.get == value
        }
    }

    /** Traverse to nodes where closureBindingId matches one of the elements in `values` exactly.
      */
    def closureBindingIdExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) closureBindingIdExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item =>
          val tmp = item.closureBindingId; tmp.isDefined && valueSet.contains(tmp.get)
        }
      }

    /** Traverse to nodes where closureBindingId does not match the regular expression `value`.
      */
    def closureBindingIdNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.closureBindingId.isEmpty || node.closureBindingId.get != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item =>
          val tmp = item.closureBindingId; tmp.isDefined && matcher.reset(tmp.get).matches
        }
      }
    }

    /** Traverse to nodes where closureBindingId does not match any of the regular expressions in `values`.
      */
    def closureBindingIdNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filterNot { item =>
        val tmp = item.closureBindingId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
      }
    }

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

    /** Traverse to evaluationStrategy property */
    def evaluationStrategy: Iterator[String] =
      traversal.map(_.evaluationStrategy)

    /** Traverse to nodes where the evaluationStrategy matches the regular expression `value`
      */
    def evaluationStrategy(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        evaluationStrategyExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.evaluationStrategy).matches }
      }

    /** Traverse to nodes where the evaluationStrategy matches at least one of the regular expressions in `values`
      */
    def evaluationStrategy(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.evaluationStrategy).matches } }
    }

    /** Traverse to nodes where evaluationStrategy matches `value` exactly.
      */
    def evaluationStrategyExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 19, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.evaluationStrategy == value }
    }

    /** Traverse to nodes where evaluationStrategy matches one of the elements in `values` exactly.
      */
    def evaluationStrategyExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) evaluationStrategyExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.evaluationStrategy) }
      }

    /** Traverse to nodes where evaluationStrategy does not match the regular expression `value`.
      */
    def evaluationStrategyNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.evaluationStrategy != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.evaluationStrategy).matches }
      }
    }

    /** Traverse to nodes where evaluationStrategy does not match any of the regular expressions in `values`.
      */
    def evaluationStrategyNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.evaluationStrategy).matches }.isEmpty }
    }

  }
  final class Traversal_CommentBase[NodeType <: nodes.CommentBase](val traversal: Iterator[NodeType]) extends AnyVal {

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
    def filenameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) filenameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.filename) }
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

  }
  final class Traversal_ConfigFileBase[NodeType <: nodes.ConfigFileBase](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to content property */
    def content: Iterator[String] =
      traversal.map(_.content)

    /** Traverse to nodes where the content matches the regular expression `value`
      */
    def content(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        contentExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.content).matches }
      }

    /** Traverse to nodes where the content matches at least one of the regular expressions in `values`
      */
    def content(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.content).matches } }
    }

    /** Traverse to nodes where content matches `value` exactly.
      */
    def contentExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 14, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.content == value }
    }

    /** Traverse to nodes where content matches one of the elements in `values` exactly.
      */
    def contentExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) contentExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.content) }
      }

    /** Traverse to nodes where content does not match the regular expression `value`.
      */
    def contentNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.content != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.content).matches }
      }
    }

    /** Traverse to nodes where content does not match any of the regular expressions in `values`.
      */
    def contentNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.content).matches }.isEmpty }
    }

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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
  final class Traversal_ControlStructureBase[NodeType <: nodes.ControlStructureBase](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to controlStructureType property */
    def controlStructureType: Iterator[String] =
      traversal.map(_.controlStructureType)

    /** Traverse to nodes where the controlStructureType matches the regular expression `value`
      */
    def controlStructureType(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        controlStructureTypeExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.controlStructureType).matches }
      }

    /** Traverse to nodes where the controlStructureType matches at least one of the regular expressions in `values`
      */
    def controlStructureType(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.controlStructureType).matches } }
    }

    /** Traverse to nodes where controlStructureType matches `value` exactly.
      */
    def controlStructureTypeExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 15, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.controlStructureType == value }
    }

    /** Traverse to nodes where controlStructureType matches one of the elements in `values` exactly.
      */
    def controlStructureTypeExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) controlStructureTypeExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.controlStructureType) }
      }

    /** Traverse to nodes where controlStructureType does not match the regular expression `value`.
      */
    def controlStructureTypeNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.controlStructureType != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.controlStructureType).matches }
      }
    }

    /** Traverse to nodes where controlStructureType does not match any of the regular expressions in `values`.
      */
    def controlStructureTypeNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.controlStructureType).matches }.isEmpty }
    }

    /** Traverse to parserTypeName property */
    def parserTypeName: Iterator[String] =
      traversal.map(_.parserTypeName)

    /** Traverse to nodes where the parserTypeName matches the regular expression `value`
      */
    def parserTypeName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        parserTypeNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.parserTypeName).matches }
      }

    /** Traverse to nodes where the parserTypeName matches at least one of the regular expressions in `values`
      */
    def parserTypeName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.parserTypeName).matches } }
    }

    /** Traverse to nodes where parserTypeName matches `value` exactly.
      */
    def parserTypeNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 46, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.parserTypeName == value }
    }

    /** Traverse to nodes where parserTypeName matches one of the elements in `values` exactly.
      */
    def parserTypeNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) parserTypeNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.parserTypeName) }
      }

    /** Traverse to nodes where parserTypeName does not match the regular expression `value`.
      */
    def parserTypeNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.parserTypeName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.parserTypeName).matches }
      }
    }

    /** Traverse to nodes where parserTypeName does not match any of the regular expressions in `values`.
      */
    def parserTypeNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.parserTypeName).matches }.isEmpty }
    }

  }
  final class Traversal_DependencyBase[NodeType <: nodes.DependencyBase](val traversal: Iterator[NodeType])
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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

    /** Traverse to version property */
    def version: Iterator[String] =
      traversal.map(_.version)

    /** Traverse to nodes where the version matches the regular expression `value`
      */
    def version(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        versionExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.version).matches }
      }

    /** Traverse to nodes where the version matches at least one of the regular expressions in `values`
      */
    def version(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.version).matches } }
    }

    /** Traverse to nodes where version matches `value` exactly.
      */
    def versionExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 54, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.version == value }
    }

    /** Traverse to nodes where version matches one of the elements in `values` exactly.
      */
    def versionExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) versionExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.version) }
      }

    /** Traverse to nodes where version does not match the regular expression `value`.
      */
    def versionNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.version != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.version).matches }
      }
    }

    /** Traverse to nodes where version does not match any of the regular expressions in `values`.
      */
    def versionNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.version).matches }.isEmpty }
    }

  }
  final class Traversal_FieldIdentifierBase[NodeType <: nodes.FieldIdentifierBase](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to canonicalName property */
    def canonicalName: Iterator[String] =
      traversal.map(_.canonicalName)

    /** Traverse to nodes where the canonicalName matches the regular expression `value`
      */
    def canonicalName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        canonicalNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.canonicalName).matches }
      }

    /** Traverse to nodes where the canonicalName matches at least one of the regular expressions in `values`
      */
    def canonicalName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.canonicalName).matches } }
    }

    /** Traverse to nodes where canonicalName matches `value` exactly.
      */
    def canonicalNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 5, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.canonicalName == value }
    }

    /** Traverse to nodes where canonicalName matches one of the elements in `values` exactly.
      */
    def canonicalNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) canonicalNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.canonicalName) }
      }

    /** Traverse to nodes where canonicalName does not match the regular expression `value`.
      */
    def canonicalNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.canonicalName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.canonicalName).matches }
      }
    }

    /** Traverse to nodes where canonicalName does not match any of the regular expressions in `values`.
      */
    def canonicalNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.canonicalName).matches }.isEmpty }
    }

  }
  final class Traversal_FileBase[NodeType <: nodes.FileBase](val traversal: Iterator[NodeType]) extends AnyVal {

    /** Traverse to content property */
    def content: Iterator[String] =
      traversal.map(_.content)

    /** Traverse to nodes where the content matches the regular expression `value`
      */
    def content(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        contentExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.content).matches }
      }

    /** Traverse to nodes where the content matches at least one of the regular expressions in `values`
      */
    def content(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.content).matches } }
    }

    /** Traverse to nodes where content matches `value` exactly.
      */
    def contentExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 14, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.content == value }
    }

    /** Traverse to nodes where content matches one of the elements in `values` exactly.
      */
    def contentExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) contentExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.content) }
      }

    /** Traverse to nodes where content does not match the regular expression `value`.
      */
    def contentNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.content != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.content).matches }
      }
    }

    /** Traverse to nodes where content does not match any of the regular expressions in `values`.
      */
    def contentNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.content).matches }.isEmpty }
    }

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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
  final class Traversal_FindingBase[NodeType <: nodes.FindingBase](val traversal: Iterator[NodeType]) extends AnyVal {}
  final class Traversal_IdentifierBase[NodeType <: nodes.IdentifierBase](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to dynamicTypeHintFullName property */
    def dynamicTypeHintFullName: Iterator[String] =
      traversal.flatMap(_.dynamicTypeHintFullName)

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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

    /** Traverse to possibleTypes property */
    def possibleTypes: Iterator[String] =
      traversal.flatMap(_.possibleTypes)

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_ImportBase[NodeType <: nodes.ImportBase](val traversal: Iterator[NodeType]) extends AnyVal {

    /** Traverse to explicitAs property */
    def explicitAs: Iterator[Boolean] =
      traversal.flatMap(_.explicitAs)

    /** Traverse to nodes where the explicitAs equals the given `value`
      */
    def explicitAs(value: Boolean): Iterator[NodeType] =
      traversal.filter { node => node.explicitAs.isDefined && node.explicitAs.get == value }

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

    /** Traverse to isExplicit property */
    def isExplicit: Iterator[Boolean] =
      traversal.flatMap(_.isExplicit)

    /** Traverse to nodes where the isExplicit equals the given `value`
      */
    def isExplicit(value: Boolean): Iterator[NodeType] =
      traversal.filter { node => node.isExplicit.isDefined && node.isExplicit.get == value }

    /** Traverse to isWildcard property */
    def isWildcard: Iterator[Boolean] =
      traversal.flatMap(_.isWildcard)

    /** Traverse to nodes where the isWildcard equals the given `value`
      */
    def isWildcard(value: Boolean): Iterator[NodeType] =
      traversal.filter { node => node.isWildcard.isDefined && node.isWildcard.get == value }

  }
  final class Traversal_JumpLabelBase[NodeType <: nodes.JumpLabelBase](val traversal: Iterator[NodeType])
      extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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

    /** Traverse to parserTypeName property */
    def parserTypeName: Iterator[String] =
      traversal.map(_.parserTypeName)

    /** Traverse to nodes where the parserTypeName matches the regular expression `value`
      */
    def parserTypeName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        parserTypeNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.parserTypeName).matches }
      }

    /** Traverse to nodes where the parserTypeName matches at least one of the regular expressions in `values`
      */
    def parserTypeName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.parserTypeName).matches } }
    }

    /** Traverse to nodes where parserTypeName matches `value` exactly.
      */
    def parserTypeNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 46, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.parserTypeName == value }
    }

    /** Traverse to nodes where parserTypeName matches one of the elements in `values` exactly.
      */
    def parserTypeNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) parserTypeNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.parserTypeName) }
      }

    /** Traverse to nodes where parserTypeName does not match the regular expression `value`.
      */
    def parserTypeNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.parserTypeName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.parserTypeName).matches }
      }
    }

    /** Traverse to nodes where parserTypeName does not match any of the regular expressions in `values`.
      */
    def parserTypeNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.parserTypeName).matches }.isEmpty }
    }

  }
  final class Traversal_JumpTargetBase[NodeType <: nodes.JumpTargetBase](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to argumentIndex property */
    def argumentIndex: Iterator[Int] =
      traversal.map(_.argumentIndex)

    /** Traverse to nodes where the argumentIndex equals the given `value`
      */
    def argumentIndex(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex == value }

    /** Traverse to nodes where the argumentIndex equals at least one of the given `values`
      */
    def argumentIndex(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => vset.contains(node.argumentIndex) }
    }

    /** Traverse to nodes where the argumentIndex is not equal to the given `value`
      */
    def argumentIndexNot(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex != value }

    /** Traverse to nodes where the argumentIndex is not equal to any of the given `values`
      */
    def argumentIndexNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => !vset.contains(node.argumentIndex) }
    }

    /** Traverse to nodes where the argumentIndex is greater than the given `value`
      */
    def argumentIndexGt(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex > value }

    /** Traverse to nodes where the argumentIndex is greater than or equal the given `value`
      */
    def argumentIndexGte(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex >= value }

    /** Traverse to nodes where the argumentIndex is less than the given `value`
      */
    def argumentIndexLt(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex < value }

    /** Traverse to nodes where the argumentIndex is less than or equal the given `value`
      */
    def argumentIndexLte(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex <= value }

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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

    /** Traverse to parserTypeName property */
    def parserTypeName: Iterator[String] =
      traversal.map(_.parserTypeName)

    /** Traverse to nodes where the parserTypeName matches the regular expression `value`
      */
    def parserTypeName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        parserTypeNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.parserTypeName).matches }
      }

    /** Traverse to nodes where the parserTypeName matches at least one of the regular expressions in `values`
      */
    def parserTypeName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.parserTypeName).matches } }
    }

    /** Traverse to nodes where parserTypeName matches `value` exactly.
      */
    def parserTypeNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 46, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.parserTypeName == value }
    }

    /** Traverse to nodes where parserTypeName matches one of the elements in `values` exactly.
      */
    def parserTypeNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) parserTypeNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.parserTypeName) }
      }

    /** Traverse to nodes where parserTypeName does not match the regular expression `value`.
      */
    def parserTypeNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.parserTypeName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.parserTypeName).matches }
      }
    }

    /** Traverse to nodes where parserTypeName does not match any of the regular expressions in `values`.
      */
    def parserTypeNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.parserTypeName).matches }.isEmpty }
    }

  }
  final class Traversal_KeyValuePairBase[NodeType <: nodes.KeyValuePairBase](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to key property */
    def key: Iterator[String] =
      traversal.map(_.key)

    /** Traverse to nodes where the key matches the regular expression `value`
      */
    def key(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        keyExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.key).matches }
      }

    /** Traverse to nodes where the key matches at least one of the regular expressions in `values`
      */
    def key(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.key).matches } }
    }

    /** Traverse to nodes where key matches `value` exactly.
      */
    def keyExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 32, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.key == value }
    }

    /** Traverse to nodes where key matches one of the elements in `values` exactly.
      */
    def keyExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) keyExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.key) }
      }

    /** Traverse to nodes where key does not match the regular expression `value`.
      */
    def keyNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.key != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.key).matches }
      }
    }

    /** Traverse to nodes where key does not match any of the regular expressions in `values`.
      */
    def keyNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.key).matches }.isEmpty }
    }

    /** Traverse to value property */
    def value: Iterator[String] =
      traversal.map(_.value)

    /** Traverse to nodes where the value matches the regular expression `value`
      */
    def value(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        valueExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.value).matches }
      }

    /** Traverse to nodes where the value matches at least one of the regular expressions in `values`
      */
    def value(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.value).matches } }
    }

    /** Traverse to nodes where value matches `value` exactly.
      */
    def valueExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 53, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.value == value }
    }

    /** Traverse to nodes where value matches one of the elements in `values` exactly.
      */
    def valueExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) valueExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.value) }
      }

    /** Traverse to nodes where value does not match the regular expression `value`.
      */
    def valueNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.value != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.value).matches }
      }
    }

    /** Traverse to nodes where value does not match any of the regular expressions in `values`.
      */
    def valueNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.value).matches }.isEmpty }
    }

  }
  final class Traversal_LiteralBase[NodeType <: nodes.LiteralBase](val traversal: Iterator[NodeType]) extends AnyVal {

    /** Traverse to dynamicTypeHintFullName property */
    def dynamicTypeHintFullName: Iterator[String] =
      traversal.flatMap(_.dynamicTypeHintFullName)

    /** Traverse to possibleTypes property */
    def possibleTypes: Iterator[String] =
      traversal.flatMap(_.possibleTypes)

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_LocalBase[NodeType <: nodes.LocalBase](val traversal: Iterator[NodeType]) extends AnyVal {

    /** Traverse to closureBindingId property */
    def closureBindingId: Iterator[String] =
      traversal.flatMap(_.closureBindingId)

    /** Traverse to nodes where the closureBindingId matches the regular expression `value`
      */
    def closureBindingId(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        closureBindingIdExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item =>
          val tmp = item.closureBindingId; tmp.isDefined && matcher.reset(tmp.get).matches
        }
      }
    }

    /** Traverse to nodes where the closureBindingId matches at least one of the regular expressions in `values`
      */
    def closureBindingId(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item =>
        val tmp = item.closureBindingId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
      }
    }

    /** Traverse to nodes where closureBindingId matches `value` exactly.
      */
    def closureBindingIdExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 8, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ =>
        traversal.filter { node =>
          val tmp = node.closureBindingId; tmp.isDefined && tmp.get == value
        }
    }

    /** Traverse to nodes where closureBindingId matches one of the elements in `values` exactly.
      */
    def closureBindingIdExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) closureBindingIdExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item =>
          val tmp = item.closureBindingId; tmp.isDefined && valueSet.contains(tmp.get)
        }
      }

    /** Traverse to nodes where closureBindingId does not match the regular expression `value`.
      */
    def closureBindingIdNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.closureBindingId.isEmpty || node.closureBindingId.get != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item =>
          val tmp = item.closureBindingId; tmp.isDefined && matcher.reset(tmp.get).matches
        }
      }
    }

    /** Traverse to nodes where closureBindingId does not match any of the regular expressions in `values`.
      */
    def closureBindingIdNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filterNot { item =>
        val tmp = item.closureBindingId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
      }
    }

    /** Traverse to dynamicTypeHintFullName property */
    def dynamicTypeHintFullName: Iterator[String] =
      traversal.flatMap(_.dynamicTypeHintFullName)

    /** Traverse to possibleTypes property */
    def possibleTypes: Iterator[String] =
      traversal.flatMap(_.possibleTypes)

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_LocationBase[NodeType <: nodes.LocationBase](val traversal: Iterator[NodeType]) extends AnyVal {

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
    def classNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) classNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.className) }
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
    def classShortNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) classShortNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.classShortName) }
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
    def filenameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) filenameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.filename) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 36, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.methodFullName == value }
    }

    /** Traverse to nodes where methodFullName matches one of the elements in `values` exactly.
      */
    def methodFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) methodFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.methodFullName) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 37, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.methodShortName == value }
    }

    /** Traverse to nodes where methodShortName matches one of the elements in `values` exactly.
      */
    def methodShortNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) methodShortNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.methodShortName) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 40, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.nodeLabel == value }
    }

    /** Traverse to nodes where nodeLabel matches one of the elements in `values` exactly.
      */
    def nodeLabelExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nodeLabelExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.nodeLabel) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 45, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.packageName == value }
    }

    /** Traverse to nodes where packageName matches one of the elements in `values` exactly.
      */
    def packageNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) packageNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.packageName) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 50, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.symbol == value }
    }

    /** Traverse to nodes where symbol matches one of the elements in `values` exactly.
      */
    def symbolExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) symbolExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.symbol) }
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
  final class Traversal_MemberBase[NodeType <: nodes.MemberBase](val traversal: Iterator[NodeType]) extends AnyVal {

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
    def astParentFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) astParentFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.astParentFullName) }
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
    def astParentTypeExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) astParentTypeExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.astParentType) }
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

    /** Traverse to dynamicTypeHintFullName property */
    def dynamicTypeHintFullName: Iterator[String] =
      traversal.flatMap(_.dynamicTypeHintFullName)

    /** Traverse to possibleTypes property */
    def possibleTypes: Iterator[String] =
      traversal.flatMap(_.possibleTypes)

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_MetaDataBase[NodeType <: nodes.MetaDataBase](val traversal: Iterator[NodeType]) extends AnyVal {

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

    /** Traverse to language property */
    def language: Iterator[String] =
      traversal.map(_.language)

    /** Traverse to nodes where the language matches the regular expression `value`
      */
    def language(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        languageExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.language).matches }
      }

    /** Traverse to nodes where the language matches at least one of the regular expressions in `values`
      */
    def language(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.language).matches } }
    }

    /** Traverse to nodes where language matches `value` exactly.
      */
    def languageExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 33, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.language == value }
    }

    /** Traverse to nodes where language matches one of the elements in `values` exactly.
      */
    def languageExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) languageExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.language) }
      }

    /** Traverse to nodes where language does not match the regular expression `value`.
      */
    def languageNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.language != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.language).matches }
      }
    }

    /** Traverse to nodes where language does not match any of the regular expressions in `values`.
      */
    def languageNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.language).matches }.isEmpty }
    }

    /** Traverse to overlays property */
    def overlays: Iterator[String] =
      traversal.flatMap(_.overlays)

    /** Traverse to root property */
    def root: Iterator[String] =
      traversal.map(_.root)

    /** Traverse to nodes where the root matches the regular expression `value`
      */
    def root(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        rootExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.root).matches }
      }

    /** Traverse to nodes where the root matches at least one of the regular expressions in `values`
      */
    def root(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.root).matches } }
    }

    /** Traverse to nodes where root matches `value` exactly.
      */
    def rootExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 48, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.root == value }
    }

    /** Traverse to nodes where root matches one of the elements in `values` exactly.
      */
    def rootExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) rootExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.root) }
      }

    /** Traverse to nodes where root does not match the regular expression `value`.
      */
    def rootNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.root != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.root).matches }
      }
    }

    /** Traverse to nodes where root does not match any of the regular expressions in `values`.
      */
    def rootNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.root).matches }.isEmpty }
    }

    /** Traverse to version property */
    def version: Iterator[String] =
      traversal.map(_.version)

    /** Traverse to nodes where the version matches the regular expression `value`
      */
    def version(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        versionExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.version).matches }
      }

    /** Traverse to nodes where the version matches at least one of the regular expressions in `values`
      */
    def version(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.version).matches } }
    }

    /** Traverse to nodes where version matches `value` exactly.
      */
    def versionExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 54, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.version == value }
    }

    /** Traverse to nodes where version matches one of the elements in `values` exactly.
      */
    def versionExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) versionExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.version) }
      }

    /** Traverse to nodes where version does not match the regular expression `value`.
      */
    def versionNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.version != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.version).matches }
      }
    }

    /** Traverse to nodes where version does not match any of the regular expressions in `values`.
      */
    def versionNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.version).matches }.isEmpty }
    }

  }
  final class Traversal_MethodBase[NodeType <: nodes.MethodBase](val traversal: Iterator[NodeType]) extends AnyVal {

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
    def astParentFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) astParentFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.astParentFullName) }
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
    def astParentTypeExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) astParentTypeExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.astParentType) }
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

    /** Traverse to columnNumberEnd property */
    def columnNumberEnd: Iterator[Int] =
      traversal.flatMap(_.columnNumberEnd)

    /** Traverse to nodes where the columnNumberEnd equals the given `value`
      */
    def columnNumberEnd(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get == value
      }

    /** Traverse to nodes where the columnNumberEnd equals at least one of the given `values`
      */
    def columnNumberEnd(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isDefined && vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the columnNumberEnd is not equal to the given `value`
      */
    def columnNumberEndNot(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isEmpty || tmp.get != value
      }

    /** Traverse to nodes where the columnNumberEnd does not equal any one of the given `values`
      */
    def columnNumberEndNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isEmpty || !vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the columnNumberEnd is greater than the given `value`
      */
    def columnNumberEndGt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get > value
      }

    /** Traverse to nodes where the columnNumberEnd is greater than or equal the given `value`
      */
    def columnNumberEndGte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get >= value
      }

    /** Traverse to nodes where the columnNumberEnd is less than the given `value`
      */
    def columnNumberEndLt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get < value
      }

    /** Traverse to nodes where the columnNumberEnd is less than or equal the given `value`
      */
    def columnNumberEndLte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumberEnd; tmp.isDefined && tmp.get <= value
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
    def filenameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) filenameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.filename) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 22, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.fullName == value }
    }

    /** Traverse to nodes where fullName matches one of the elements in `values` exactly.
      */
    def fullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) fullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.fullName) }
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

    /** Traverse to isExternal property */
    def isExternal: Iterator[Boolean] =
      traversal.map(_.isExternal)

    /** Traverse to nodes where the isExternal equals the given `value`
      */
    def isExternal(value: Boolean): Iterator[NodeType] =
      traversal.filter { _.isExternal == value }

    /** Traverse to lineNumberEnd property */
    def lineNumberEnd: Iterator[Int] =
      traversal.flatMap(_.lineNumberEnd)

    /** Traverse to nodes where the lineNumberEnd equals the given `value`
      */
    def lineNumberEnd(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get == value
      }

    /** Traverse to nodes where the lineNumberEnd equals at least one of the given `values`
      */
    def lineNumberEnd(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isDefined && vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the lineNumberEnd is not equal to the given `value`
      */
    def lineNumberEndNot(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isEmpty || tmp.get != value
      }

    /** Traverse to nodes where the lineNumberEnd does not equal any one of the given `values`
      */
    def lineNumberEndNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isEmpty || !vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the lineNumberEnd is greater than the given `value`
      */
    def lineNumberEndGt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get > value
      }

    /** Traverse to nodes where the lineNumberEnd is greater than or equal the given `value`
      */
    def lineNumberEndGte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get >= value
      }

    /** Traverse to nodes where the lineNumberEnd is less than the given `value`
      */
    def lineNumberEndLt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get < value
      }

    /** Traverse to nodes where the lineNumberEnd is less than or equal the given `value`
      */
    def lineNumberEndLte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.lineNumberEnd; tmp.isDefined && tmp.get <= value
      }

    /** Traverse to offset property */
    def offset: Iterator[Int] =
      traversal.flatMap(_.offset)

    /** Traverse to nodes where the offset equals the given `value`
      */
    def offset(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get == value
      }

    /** Traverse to nodes where the offset equals at least one of the given `values`
      */
    def offset(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the offset is not equal to the given `value`
      */
    def offsetNot(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isEmpty || tmp.get != value
      }

    /** Traverse to nodes where the offset does not equal any one of the given `values`
      */
    def offsetNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.offset; tmp.isEmpty || !vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the offset is greater than the given `value`
      */
    def offsetGt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get > value
      }

    /** Traverse to nodes where the offset is greater than or equal the given `value`
      */
    def offsetGte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get >= value
      }

    /** Traverse to nodes where the offset is less than the given `value`
      */
    def offsetLt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get < value
      }

    /** Traverse to nodes where the offset is less than or equal the given `value`
      */
    def offsetLte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get <= value
      }

    /** Traverse to offsetEnd property */
    def offsetEnd: Iterator[Int] =
      traversal.flatMap(_.offsetEnd)

    /** Traverse to nodes where the offsetEnd equals the given `value`
      */
    def offsetEnd(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get == value
      }

    /** Traverse to nodes where the offsetEnd equals at least one of the given `values`
      */
    def offsetEnd(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the offsetEnd is not equal to the given `value`
      */
    def offsetEndNot(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isEmpty || tmp.get != value
      }

    /** Traverse to nodes where the offsetEnd does not equal any one of the given `values`
      */
    def offsetEndNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isEmpty || !vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the offsetEnd is greater than the given `value`
      */
    def offsetEndGt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get > value
      }

    /** Traverse to nodes where the offsetEnd is greater than or equal the given `value`
      */
    def offsetEndGte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get >= value
      }

    /** Traverse to nodes where the offsetEnd is less than the given `value`
      */
    def offsetEndLt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get < value
      }

    /** Traverse to nodes where the offsetEnd is less than or equal the given `value`
      */
    def offsetEndLte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get <= value
      }

    /** Traverse to signature property */
    def signature: Iterator[String] =
      traversal.map(_.signature)

    /** Traverse to nodes where the signature matches the regular expression `value`
      */
    def signature(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        signatureExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.signature).matches }
      }

    /** Traverse to nodes where the signature matches at least one of the regular expressions in `values`
      */
    def signature(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.signature).matches } }
    }

    /** Traverse to nodes where signature matches `value` exactly.
      */
    def signatureExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 49, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.signature == value }
    }

    /** Traverse to nodes where signature matches one of the elements in `values` exactly.
      */
    def signatureExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) signatureExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.signature) }
      }

    /** Traverse to nodes where signature does not match the regular expression `value`.
      */
    def signatureNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.signature != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.signature).matches }
      }
    }

    /** Traverse to nodes where signature does not match any of the regular expressions in `values`.
      */
    def signatureNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.signature).matches }.isEmpty }
    }

  }
  final class Traversal_MethodParameterInBase[NodeType <: nodes.MethodParameterInBase](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to closureBindingId property */
    def closureBindingId: Iterator[String] =
      traversal.flatMap(_.closureBindingId)

    /** Traverse to nodes where the closureBindingId matches the regular expression `value`
      */
    def closureBindingId(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        closureBindingIdExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item =>
          val tmp = item.closureBindingId; tmp.isDefined && matcher.reset(tmp.get).matches
        }
      }
    }

    /** Traverse to nodes where the closureBindingId matches at least one of the regular expressions in `values`
      */
    def closureBindingId(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item =>
        val tmp = item.closureBindingId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
      }
    }

    /** Traverse to nodes where closureBindingId matches `value` exactly.
      */
    def closureBindingIdExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 8, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ =>
        traversal.filter { node =>
          val tmp = node.closureBindingId; tmp.isDefined && tmp.get == value
        }
    }

    /** Traverse to nodes where closureBindingId matches one of the elements in `values` exactly.
      */
    def closureBindingIdExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) closureBindingIdExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item =>
          val tmp = item.closureBindingId; tmp.isDefined && valueSet.contains(tmp.get)
        }
      }

    /** Traverse to nodes where closureBindingId does not match the regular expression `value`.
      */
    def closureBindingIdNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.closureBindingId.isEmpty || node.closureBindingId.get != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item =>
          val tmp = item.closureBindingId; tmp.isDefined && matcher.reset(tmp.get).matches
        }
      }
    }

    /** Traverse to nodes where closureBindingId does not match any of the regular expressions in `values`.
      */
    def closureBindingIdNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filterNot { item =>
        val tmp = item.closureBindingId; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
      }
    }

    /** Traverse to dynamicTypeHintFullName property */
    def dynamicTypeHintFullName: Iterator[String] =
      traversal.flatMap(_.dynamicTypeHintFullName)

    /** Traverse to evaluationStrategy property */
    def evaluationStrategy: Iterator[String] =
      traversal.map(_.evaluationStrategy)

    /** Traverse to nodes where the evaluationStrategy matches the regular expression `value`
      */
    def evaluationStrategy(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        evaluationStrategyExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.evaluationStrategy).matches }
      }

    /** Traverse to nodes where the evaluationStrategy matches at least one of the regular expressions in `values`
      */
    def evaluationStrategy(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.evaluationStrategy).matches } }
    }

    /** Traverse to nodes where evaluationStrategy matches `value` exactly.
      */
    def evaluationStrategyExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 19, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.evaluationStrategy == value }
    }

    /** Traverse to nodes where evaluationStrategy matches one of the elements in `values` exactly.
      */
    def evaluationStrategyExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) evaluationStrategyExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.evaluationStrategy) }
      }

    /** Traverse to nodes where evaluationStrategy does not match the regular expression `value`.
      */
    def evaluationStrategyNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.evaluationStrategy != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.evaluationStrategy).matches }
      }
    }

    /** Traverse to nodes where evaluationStrategy does not match any of the regular expressions in `values`.
      */
    def evaluationStrategyNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.evaluationStrategy).matches }.isEmpty }
    }

    /** Traverse to index property */
    def index: Iterator[Int] =
      traversal.map(_.index)

    /** Traverse to nodes where the index equals the given `value`
      */
    def index(value: Int): Iterator[NodeType] =
      traversal.filter { _.index == value }

    /** Traverse to nodes where the index equals at least one of the given `values`
      */
    def index(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => vset.contains(node.index) }
    }

    /** Traverse to nodes where the index is not equal to the given `value`
      */
    def indexNot(value: Int): Iterator[NodeType] =
      traversal.filter { _.index != value }

    /** Traverse to nodes where the index is not equal to any of the given `values`
      */
    def indexNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => !vset.contains(node.index) }
    }

    /** Traverse to nodes where the index is greater than the given `value`
      */
    def indexGt(value: Int): Iterator[NodeType] =
      traversal.filter { _.index > value }

    /** Traverse to nodes where the index is greater than or equal the given `value`
      */
    def indexGte(value: Int): Iterator[NodeType] =
      traversal.filter { _.index >= value }

    /** Traverse to nodes where the index is less than the given `value`
      */
    def indexLt(value: Int): Iterator[NodeType] =
      traversal.filter { _.index < value }

    /** Traverse to nodes where the index is less than or equal the given `value`
      */
    def indexLte(value: Int): Iterator[NodeType] =
      traversal.filter { _.index <= value }

    /** Traverse to isVariadic property */
    def isVariadic: Iterator[Boolean] =
      traversal.map(_.isVariadic)

    /** Traverse to nodes where the isVariadic equals the given `value`
      */
    def isVariadic(value: Boolean): Iterator[NodeType] =
      traversal.filter { _.isVariadic == value }

    /** Traverse to possibleTypes property */
    def possibleTypes: Iterator[String] =
      traversal.flatMap(_.possibleTypes)

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_MethodParameterOutBase[NodeType <: nodes.MethodParameterOutBase](
    val traversal: Iterator[NodeType]
  ) extends AnyVal {

    /** Traverse to evaluationStrategy property */
    def evaluationStrategy: Iterator[String] =
      traversal.map(_.evaluationStrategy)

    /** Traverse to nodes where the evaluationStrategy matches the regular expression `value`
      */
    def evaluationStrategy(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        evaluationStrategyExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.evaluationStrategy).matches }
      }

    /** Traverse to nodes where the evaluationStrategy matches at least one of the regular expressions in `values`
      */
    def evaluationStrategy(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.evaluationStrategy).matches } }
    }

    /** Traverse to nodes where evaluationStrategy matches `value` exactly.
      */
    def evaluationStrategyExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 19, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.evaluationStrategy == value }
    }

    /** Traverse to nodes where evaluationStrategy matches one of the elements in `values` exactly.
      */
    def evaluationStrategyExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) evaluationStrategyExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.evaluationStrategy) }
      }

    /** Traverse to nodes where evaluationStrategy does not match the regular expression `value`.
      */
    def evaluationStrategyNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.evaluationStrategy != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.evaluationStrategy).matches }
      }
    }

    /** Traverse to nodes where evaluationStrategy does not match any of the regular expressions in `values`.
      */
    def evaluationStrategyNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.evaluationStrategy).matches }.isEmpty }
    }

    /** Traverse to index property */
    def index: Iterator[Int] =
      traversal.map(_.index)

    /** Traverse to nodes where the index equals the given `value`
      */
    def index(value: Int): Iterator[NodeType] =
      traversal.filter { _.index == value }

    /** Traverse to nodes where the index equals at least one of the given `values`
      */
    def index(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => vset.contains(node.index) }
    }

    /** Traverse to nodes where the index is not equal to the given `value`
      */
    def indexNot(value: Int): Iterator[NodeType] =
      traversal.filter { _.index != value }

    /** Traverse to nodes where the index is not equal to any of the given `values`
      */
    def indexNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => !vset.contains(node.index) }
    }

    /** Traverse to nodes where the index is greater than the given `value`
      */
    def indexGt(value: Int): Iterator[NodeType] =
      traversal.filter { _.index > value }

    /** Traverse to nodes where the index is greater than or equal the given `value`
      */
    def indexGte(value: Int): Iterator[NodeType] =
      traversal.filter { _.index >= value }

    /** Traverse to nodes where the index is less than the given `value`
      */
    def indexLt(value: Int): Iterator[NodeType] =
      traversal.filter { _.index < value }

    /** Traverse to nodes where the index is less than or equal the given `value`
      */
    def indexLte(value: Int): Iterator[NodeType] =
      traversal.filter { _.index <= value }

    /** Traverse to isVariadic property */
    def isVariadic: Iterator[Boolean] =
      traversal.map(_.isVariadic)

    /** Traverse to nodes where the isVariadic equals the given `value`
      */
    def isVariadic(value: Boolean): Iterator[NodeType] =
      traversal.filter { _.isVariadic == value }

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_MethodRefBase[NodeType <: nodes.MethodRefBase](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to dynamicTypeHintFullName property */
    def dynamicTypeHintFullName: Iterator[String] =
      traversal.flatMap(_.dynamicTypeHintFullName)

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 36, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.methodFullName == value }
    }

    /** Traverse to nodes where methodFullName matches one of the elements in `values` exactly.
      */
    def methodFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) methodFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.methodFullName) }
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

    /** Traverse to possibleTypes property */
    def possibleTypes: Iterator[String] =
      traversal.flatMap(_.possibleTypes)

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_MethodReturnBase[NodeType <: nodes.MethodReturnBase](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to dynamicTypeHintFullName property */
    def dynamicTypeHintFullName: Iterator[String] =
      traversal.flatMap(_.dynamicTypeHintFullName)

    /** Traverse to evaluationStrategy property */
    def evaluationStrategy: Iterator[String] =
      traversal.map(_.evaluationStrategy)

    /** Traverse to nodes where the evaluationStrategy matches the regular expression `value`
      */
    def evaluationStrategy(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        evaluationStrategyExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.evaluationStrategy).matches }
      }

    /** Traverse to nodes where the evaluationStrategy matches at least one of the regular expressions in `values`
      */
    def evaluationStrategy(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.evaluationStrategy).matches } }
    }

    /** Traverse to nodes where evaluationStrategy matches `value` exactly.
      */
    def evaluationStrategyExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 19, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.evaluationStrategy == value }
    }

    /** Traverse to nodes where evaluationStrategy matches one of the elements in `values` exactly.
      */
    def evaluationStrategyExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) evaluationStrategyExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.evaluationStrategy) }
      }

    /** Traverse to nodes where evaluationStrategy does not match the regular expression `value`.
      */
    def evaluationStrategyNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.evaluationStrategy != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.evaluationStrategy).matches }
      }
    }

    /** Traverse to nodes where evaluationStrategy does not match any of the regular expressions in `values`.
      */
    def evaluationStrategyNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.evaluationStrategy).matches }.isEmpty }
    }

    /** Traverse to possibleTypes property */
    def possibleTypes: Iterator[String] =
      traversal.flatMap(_.possibleTypes)

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_ModifierBase[NodeType <: nodes.ModifierBase](val traversal: Iterator[NodeType]) extends AnyVal {

    /** Traverse to modifierType property */
    def modifierType: Iterator[String] =
      traversal.map(_.modifierType)

    /** Traverse to nodes where the modifierType matches the regular expression `value`
      */
    def modifierType(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        modifierTypeExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.modifierType).matches }
      }

    /** Traverse to nodes where the modifierType matches at least one of the regular expressions in `values`
      */
    def modifierType(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.modifierType).matches } }
    }

    /** Traverse to nodes where modifierType matches `value` exactly.
      */
    def modifierTypeExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 38, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.modifierType == value }
    }

    /** Traverse to nodes where modifierType matches one of the elements in `values` exactly.
      */
    def modifierTypeExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) modifierTypeExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.modifierType) }
      }

    /** Traverse to nodes where modifierType does not match the regular expression `value`.
      */
    def modifierTypeNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.modifierType != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.modifierType).matches }
      }
    }

    /** Traverse to nodes where modifierType does not match any of the regular expressions in `values`.
      */
    def modifierTypeNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.modifierType).matches }.isEmpty }
    }

  }
  final class Traversal_NamespaceBase[NodeType <: nodes.NamespaceBase](val traversal: Iterator[NodeType])
      extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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
  final class Traversal_NamespaceBlockBase[NodeType <: nodes.NamespaceBlockBase](val traversal: Iterator[NodeType])
      extends AnyVal {

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
    def filenameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) filenameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.filename) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 22, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.fullName == value }
    }

    /** Traverse to nodes where fullName matches one of the elements in `values` exactly.
      */
    def fullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) fullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.fullName) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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
  final class Traversal_ReturnBase[NodeType <: nodes.ReturnBase](val traversal: Iterator[NodeType]) extends AnyVal {}
  final class Traversal_TagBase[NodeType <: nodes.TagBase](val traversal: Iterator[NodeType])       extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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

    /** Traverse to value property */
    def value: Iterator[String] =
      traversal.map(_.value)

    /** Traverse to nodes where the value matches the regular expression `value`
      */
    def value(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        valueExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.value).matches }
      }

    /** Traverse to nodes where the value matches at least one of the regular expressions in `values`
      */
    def value(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.value).matches } }
    }

    /** Traverse to nodes where value matches `value` exactly.
      */
    def valueExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 53, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.value == value }
    }

    /** Traverse to nodes where value matches one of the elements in `values` exactly.
      */
    def valueExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) valueExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.value) }
      }

    /** Traverse to nodes where value does not match the regular expression `value`.
      */
    def valueNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.value != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.value).matches }
      }
    }

    /** Traverse to nodes where value does not match any of the regular expressions in `values`.
      */
    def valueNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.value).matches }.isEmpty }
    }

  }
  final class Traversal_TagNodePairBase[NodeType <: nodes.TagNodePairBase](val traversal: Iterator[NodeType])
      extends AnyVal {}
  final class Traversal_TemplateDomBase[NodeType <: nodes.TemplateDomBase](val traversal: Iterator[NodeType])
      extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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
  final class Traversal_TypeBase[NodeType <: nodes.TypeBase](val traversal: Iterator[NodeType]) extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 22, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.fullName == value }
    }

    /** Traverse to nodes where fullName matches one of the elements in `values` exactly.
      */
    def fullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) fullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.fullName) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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

    /** Traverse to typeDeclFullName property */
    def typeDeclFullName: Iterator[String] =
      traversal.map(_.typeDeclFullName)

    /** Traverse to nodes where the typeDeclFullName matches the regular expression `value`
      */
    def typeDeclFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeDeclFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeDeclFullName).matches }
      }

    /** Traverse to nodes where the typeDeclFullName matches at least one of the regular expressions in `values`
      */
    def typeDeclFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeDeclFullName).matches } }
    }

    /** Traverse to nodes where typeDeclFullName matches `value` exactly.
      */
    def typeDeclFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 51, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeDeclFullName == value }
    }

    /** Traverse to nodes where typeDeclFullName matches one of the elements in `values` exactly.
      */
    def typeDeclFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeDeclFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeDeclFullName) }
      }

    /** Traverse to nodes where typeDeclFullName does not match the regular expression `value`.
      */
    def typeDeclFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeDeclFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeDeclFullName).matches }
      }
    }

    /** Traverse to nodes where typeDeclFullName does not match any of the regular expressions in `values`.
      */
    def typeDeclFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeDeclFullName).matches }.isEmpty }
    }

  }
  final class Traversal_TypeArgumentBase[NodeType <: nodes.TypeArgumentBase](val traversal: Iterator[NodeType])
      extends AnyVal {}
  final class Traversal_TypeDeclBase[NodeType <: nodes.TypeDeclBase](val traversal: Iterator[NodeType]) extends AnyVal {

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
    def astParentFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) astParentFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.astParentFullName) }
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
    def astParentTypeExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) astParentTypeExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.astParentType) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 21, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.filename == value }
    }

    /** Traverse to nodes where filename matches one of the elements in `values` exactly.
      */
    def filenameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) filenameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.filename) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 22, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.fullName == value }
    }

    /** Traverse to nodes where fullName matches one of the elements in `values` exactly.
      */
    def fullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) fullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.fullName) }
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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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

    /** Traverse to offset property */
    def offset: Iterator[Int] =
      traversal.flatMap(_.offset)

    /** Traverse to nodes where the offset equals the given `value`
      */
    def offset(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get == value
      }

    /** Traverse to nodes where the offset equals at least one of the given `values`
      */
    def offset(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the offset is not equal to the given `value`
      */
    def offsetNot(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isEmpty || tmp.get != value
      }

    /** Traverse to nodes where the offset does not equal any one of the given `values`
      */
    def offsetNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.offset; tmp.isEmpty || !vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the offset is greater than the given `value`
      */
    def offsetGt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get > value
      }

    /** Traverse to nodes where the offset is greater than or equal the given `value`
      */
    def offsetGte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get >= value
      }

    /** Traverse to nodes where the offset is less than the given `value`
      */
    def offsetLt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get < value
      }

    /** Traverse to nodes where the offset is less than or equal the given `value`
      */
    def offsetLte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offset; tmp.isDefined && tmp.get <= value
      }

    /** Traverse to offsetEnd property */
    def offsetEnd: Iterator[Int] =
      traversal.flatMap(_.offsetEnd)

    /** Traverse to nodes where the offsetEnd equals the given `value`
      */
    def offsetEnd(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get == value
      }

    /** Traverse to nodes where the offsetEnd equals at least one of the given `values`
      */
    def offsetEnd(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the offsetEnd is not equal to the given `value`
      */
    def offsetEndNot(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isEmpty || tmp.get != value
      }

    /** Traverse to nodes where the offsetEnd does not equal any one of the given `values`
      */
    def offsetEndNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isEmpty || !vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the offsetEnd is greater than the given `value`
      */
    def offsetEndGt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get > value
      }

    /** Traverse to nodes where the offsetEnd is greater than or equal the given `value`
      */
    def offsetEndGte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get >= value
      }

    /** Traverse to nodes where the offsetEnd is less than the given `value`
      */
    def offsetEndLt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get < value
      }

    /** Traverse to nodes where the offsetEnd is less than or equal the given `value`
      */
    def offsetEndLte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.offsetEnd; tmp.isDefined && tmp.get <= value
      }

  }
  final class Traversal_TypeParameterBase[NodeType <: nodes.TypeParameterBase](val traversal: Iterator[NodeType])
      extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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
  final class Traversal_TypeRefBase[NodeType <: nodes.TypeRefBase](val traversal: Iterator[NodeType]) extends AnyVal {

    /** Traverse to dynamicTypeHintFullName property */
    def dynamicTypeHintFullName: Iterator[String] =
      traversal.flatMap(_.dynamicTypeHintFullName)

    /** Traverse to possibleTypes property */
    def possibleTypes: Iterator[String] =
      traversal.flatMap(_.possibleTypes)

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_UnknownBase[NodeType <: nodes.UnknownBase](val traversal: Iterator[NodeType]) extends AnyVal {

    /** Traverse to containedRef property */
    def containedRef: Iterator[String] =
      traversal.map(_.containedRef)

    /** Traverse to nodes where the containedRef matches the regular expression `value`
      */
    def containedRef(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        containedRefExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.containedRef).matches }
      }

    /** Traverse to nodes where the containedRef matches at least one of the regular expressions in `values`
      */
    def containedRef(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.containedRef).matches } }
    }

    /** Traverse to nodes where containedRef matches `value` exactly.
      */
    def containedRefExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 13, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.containedRef == value }
    }

    /** Traverse to nodes where containedRef matches one of the elements in `values` exactly.
      */
    def containedRefExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) containedRefExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.containedRef) }
      }

    /** Traverse to nodes where containedRef does not match the regular expression `value`.
      */
    def containedRefNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.containedRef != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.containedRef).matches }
      }
    }

    /** Traverse to nodes where containedRef does not match any of the regular expressions in `values`.
      */
    def containedRefNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.containedRef).matches }.isEmpty }
    }

    /** Traverse to dynamicTypeHintFullName property */
    def dynamicTypeHintFullName: Iterator[String] =
      traversal.flatMap(_.dynamicTypeHintFullName)

    /** Traverse to parserTypeName property */
    def parserTypeName: Iterator[String] =
      traversal.map(_.parserTypeName)

    /** Traverse to nodes where the parserTypeName matches the regular expression `value`
      */
    def parserTypeName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        parserTypeNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.parserTypeName).matches }
      }

    /** Traverse to nodes where the parserTypeName matches at least one of the regular expressions in `values`
      */
    def parserTypeName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.parserTypeName).matches } }
    }

    /** Traverse to nodes where parserTypeName matches `value` exactly.
      */
    def parserTypeNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 46, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.parserTypeName == value }
    }

    /** Traverse to nodes where parserTypeName matches one of the elements in `values` exactly.
      */
    def parserTypeNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) parserTypeNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.parserTypeName) }
      }

    /** Traverse to nodes where parserTypeName does not match the regular expression `value`.
      */
    def parserTypeNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.parserTypeName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.parserTypeName).matches }
      }
    }

    /** Traverse to nodes where parserTypeName does not match any of the regular expressions in `values`.
      */
    def parserTypeNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.parserTypeName).matches }.isEmpty }
    }

    /** Traverse to possibleTypes property */
    def possibleTypes: Iterator[String] =
      traversal.flatMap(_.possibleTypes)

    /** Traverse to typeFullName property */
    def typeFullName: Iterator[String] =
      traversal.map(_.typeFullName)

    /** Traverse to nodes where the typeFullName matches the regular expression `value`
      */
    def typeFullName(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        typeFullNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.typeFullName).matches }
      }

    /** Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
      */
    def typeFullName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.typeFullName).matches } }
    }

    /** Traverse to nodes where typeFullName matches `value` exactly.
      */
    def typeFullNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 52, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.typeFullName == value }
    }

    /** Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
      */
    def typeFullNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) typeFullNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.typeFullName) }
      }

    /** Traverse to nodes where typeFullName does not match the regular expression `value`.
      */
    def typeFullNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.typeFullName != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.typeFullName).matches }
      }
    }

    /** Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
      */
    def typeFullNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.typeFullName).matches }.isEmpty }
    }

  }
  final class Traversal_AstNodeBase[NodeType <: nodes.AstNodeBase](val traversal: Iterator[NodeType]) extends AnyVal {

    /** Traverse to code property */
    def code: Iterator[String] =
      traversal.map(_.code)

    /** Traverse to nodes where the code matches the regular expression `value`
      */
    def code(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        codeExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.code).matches }
      }

    /** Traverse to nodes where the code matches at least one of the regular expressions in `values`
      */
    def code(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.code).matches } }
    }

    /** Traverse to nodes where code matches `value` exactly.
      */
    def codeExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 10, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.code == value }
    }

    /** Traverse to nodes where code matches one of the elements in `values` exactly.
      */
    def codeExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) codeExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.code) }
      }

    /** Traverse to nodes where code does not match the regular expression `value`.
      */
    def codeNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.code != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.code).matches }
      }
    }

    /** Traverse to nodes where code does not match any of the regular expressions in `values`.
      */
    def codeNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.code).matches }.isEmpty }
    }

    /** Traverse to columnNumber property */
    def columnNumber: Iterator[Int] =
      traversal.flatMap(_.columnNumber)

    /** Traverse to nodes where the columnNumber equals the given `value`
      */
    def columnNumber(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isDefined && tmp.get == value
      }

    /** Traverse to nodes where the columnNumber equals at least one of the given `values`
      */
    def columnNumber(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isDefined && vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the columnNumber is not equal to the given `value`
      */
    def columnNumberNot(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isEmpty || tmp.get != value
      }

    /** Traverse to nodes where the columnNumber does not equal any one of the given `values`
      */
    def columnNumberNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isEmpty || !vset.contains(tmp.get)
      }
    }

    /** Traverse to nodes where the columnNumber is greater than the given `value`
      */
    def columnNumberGt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isDefined && tmp.get > value
      }

    /** Traverse to nodes where the columnNumber is greater than or equal the given `value`
      */
    def columnNumberGte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isDefined && tmp.get >= value
      }

    /** Traverse to nodes where the columnNumber is less than the given `value`
      */
    def columnNumberLt(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isDefined && tmp.get < value
      }

    /** Traverse to nodes where the columnNumber is less than or equal the given `value`
      */
    def columnNumberLte(value: Int): Iterator[NodeType] =
      traversal.filter { node =>
        val tmp = node.columnNumber; tmp.isDefined && tmp.get <= value
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

    /** Traverse to order property */
    def order: Iterator[Int] =
      traversal.map(_.order)

    /** Traverse to nodes where the order equals the given `value`
      */
    def order(value: Int): Iterator[NodeType] =
      traversal.filter { _.order == value }

    /** Traverse to nodes where the order equals at least one of the given `values`
      */
    def order(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => vset.contains(node.order) }
    }

    /** Traverse to nodes where the order is not equal to the given `value`
      */
    def orderNot(value: Int): Iterator[NodeType] =
      traversal.filter { _.order != value }

    /** Traverse to nodes where the order is not equal to any of the given `values`
      */
    def orderNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => !vset.contains(node.order) }
    }

    /** Traverse to nodes where the order is greater than the given `value`
      */
    def orderGt(value: Int): Iterator[NodeType] =
      traversal.filter { _.order > value }

    /** Traverse to nodes where the order is greater than or equal the given `value`
      */
    def orderGte(value: Int): Iterator[NodeType] =
      traversal.filter { _.order >= value }

    /** Traverse to nodes where the order is less than the given `value`
      */
    def orderLt(value: Int): Iterator[NodeType] =
      traversal.filter { _.order < value }

    /** Traverse to nodes where the order is less than or equal the given `value`
      */
    def orderLte(value: Int): Iterator[NodeType] =
      traversal.filter { _.order <= value }

  }
  final class Traversal_CallReprBase[NodeType <: nodes.CallReprBase](val traversal: Iterator[NodeType]) extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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

    /** Traverse to signature property */
    def signature: Iterator[String] =
      traversal.map(_.signature)

    /** Traverse to nodes where the signature matches the regular expression `value`
      */
    def signature(pattern: String): Iterator[NodeType] =
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        signatureExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item => matcher.reset(item.signature).matches }
      }

    /** Traverse to nodes where the signature matches at least one of the regular expressions in `values`
      */
    def signature(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.exists { _.reset(item.signature).matches } }
    }

    /** Traverse to nodes where signature matches `value` exactly.
      */
    def signatureExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 49, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.signature == value }
    }

    /** Traverse to nodes where signature matches one of the elements in `values` exactly.
      */
    def signatureExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) signatureExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.signature) }
      }

    /** Traverse to nodes where signature does not match the regular expression `value`.
      */
    def signatureNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.signature != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item => matcher.reset(item.signature).matches }
      }
    }

    /** Traverse to nodes where signature does not match any of the regular expressions in `values`.
      */
    def signatureNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item => matchers.find { _.reset(item.signature).matches }.isEmpty }
    }

  }
  final class Traversal_CfgNodeBase[NodeType <: nodes.CfgNodeBase](val traversal: Iterator[NodeType]) extends AnyVal {}
  final class Traversal_ExpressionBase[NodeType <: nodes.ExpressionBase](val traversal: Iterator[NodeType])
      extends AnyVal {

    /** Traverse to argumentIndex property */
    def argumentIndex: Iterator[Int] =
      traversal.map(_.argumentIndex)

    /** Traverse to nodes where the argumentIndex equals the given `value`
      */
    def argumentIndex(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex == value }

    /** Traverse to nodes where the argumentIndex equals at least one of the given `values`
      */
    def argumentIndex(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => vset.contains(node.argumentIndex) }
    }

    /** Traverse to nodes where the argumentIndex is not equal to the given `value`
      */
    def argumentIndexNot(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex != value }

    /** Traverse to nodes where the argumentIndex is not equal to any of the given `values`
      */
    def argumentIndexNot(values: Int*): Iterator[NodeType] = {
      val vset = values.toSet
      traversal.filter { node => !vset.contains(node.argumentIndex) }
    }

    /** Traverse to nodes where the argumentIndex is greater than the given `value`
      */
    def argumentIndexGt(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex > value }

    /** Traverse to nodes where the argumentIndex is greater than or equal the given `value`
      */
    def argumentIndexGte(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex >= value }

    /** Traverse to nodes where the argumentIndex is less than the given `value`
      */
    def argumentIndexLt(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex < value }

    /** Traverse to nodes where the argumentIndex is less than or equal the given `value`
      */
    def argumentIndexLte(value: Int): Iterator[NodeType] =
      traversal.filter { _.argumentIndex <= value }

    /** Traverse to argumentName property */
    def argumentName: Iterator[String] =
      traversal.flatMap(_.argumentName)

    /** Traverse to nodes where the argumentName matches the regular expression `value`
      */
    def argumentName(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        argumentNameExact(pattern)
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filter { item =>
          val tmp = item.argumentName; tmp.isDefined && matcher.reset(tmp.get).matches
        }
      }
    }

    /** Traverse to nodes where the argumentName matches at least one of the regular expressions in `values`
      */
    def argumentName(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filter { item =>
        val tmp = item.argumentName; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
      }
    }

    /** Traverse to nodes where argumentName matches `value` exactly.
      */
    def argumentNameExact(value: String): Iterator[NodeType] = traversal match {
      case init: flatgraph.misc.InitNodeIterator[flatgraph.GNode @unchecked] if init.isVirgin && init.hasNext =>
        val someNode = init.next
        flatgraph.Accessors
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 2, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ =>
        traversal.filter { node =>
          val tmp = node.argumentName; tmp.isDefined && tmp.get == value
        }
    }

    /** Traverse to nodes where argumentName matches one of the elements in `values` exactly.
      */
    def argumentNameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) argumentNameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item =>
          val tmp = item.argumentName; tmp.isDefined && valueSet.contains(tmp.get)
        }
      }

    /** Traverse to nodes where argumentName does not match the regular expression `value`.
      */
    def argumentNameNot(pattern: String): Iterator[NodeType] = {
      if (!flatgraph.misc.Regex.isRegex(pattern)) {
        traversal.filter { node => node.argumentName.isEmpty || node.argumentName.get != pattern }
      } else {
        val matcher = flatgraph.misc.Regex.multilineMatcher(pattern)
        traversal.filterNot { item =>
          val tmp = item.argumentName; tmp.isDefined && matcher.reset(tmp.get).matches
        }
      }
    }

    /** Traverse to nodes where argumentName does not match any of the regular expressions in `values`.
      */
    def argumentNameNot(patterns: String*): Iterator[NodeType] = {
      val matchers = patterns.map(flatgraph.misc.Regex.multilineMatcher)
      traversal.filterNot { item =>
        val tmp = item.argumentName; tmp.isDefined && matchers.exists { _.reset(tmp.get).matches }
      }
    }

  }
  final class Traversal_DeclarationBase[NodeType <: nodes.DeclarationBase](val traversal: Iterator[NodeType])
      extends AnyVal {

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
          .getWithInverseIndex(someNode.graph, someNode.nodeKind, 39, value)
          .asInstanceOf[Iterator[NodeType]]
      case _ => traversal.filter { _.name == value }
    }

    /** Traverse to nodes where name matches one of the elements in `values` exactly.
      */
    def nameExact(values: String*): Iterator[NodeType] =
      if (values.length == 1) nameExact(values.head)
      else {
        val valueSet = values.toSet
        traversal.filter { item => valueSet.contains(item.name) }
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
  /* accessors for base nodes end */
}
trait ConcreteStoredConversions extends ConcreteBaseConversions {
  import Accessors.*
  implicit def accessPropertyAliasTypeFullNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasAliasTypeFullNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_ALIAS_TYPE_FULL_NAME[NodeType] =
    new Traversal_Property_ALIAS_TYPE_FULL_NAME(traversal.iterator)
  implicit def accessPropertyArgumentIndexTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasArgumentIndexEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_ARGUMENT_INDEX[NodeType] =
    new Traversal_Property_ARGUMENT_INDEX(traversal.iterator)
  implicit def accessPropertyArgumentNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasArgumentNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_ARGUMENT_NAME[NodeType] =
    new Traversal_Property_ARGUMENT_NAME(traversal.iterator)
  implicit def accessPropertyAstParentFullNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasAstParentFullNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_AST_PARENT_FULL_NAME[NodeType] =
    new Traversal_Property_AST_PARENT_FULL_NAME(traversal.iterator)
  implicit def accessPropertyAstParentTypeTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasAstParentTypeEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_AST_PARENT_TYPE[NodeType] =
    new Traversal_Property_AST_PARENT_TYPE(traversal.iterator)
  implicit def accessPropertyCanonicalNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasCanonicalNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_CANONICAL_NAME[NodeType] =
    new Traversal_Property_CANONICAL_NAME(traversal.iterator)
  implicit def accessPropertyClassNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasClassNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_CLASS_NAME[NodeType] = new Traversal_Property_CLASS_NAME(
    traversal.iterator
  )
  implicit def accessPropertyClassShortNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasClassShortNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_CLASS_SHORT_NAME[NodeType] =
    new Traversal_Property_CLASS_SHORT_NAME(traversal.iterator)
  implicit def accessPropertyClosureBindingIdTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasClosureBindingIdEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_CLOSURE_BINDING_ID[NodeType] =
    new Traversal_Property_CLOSURE_BINDING_ID(traversal.iterator)
  implicit def accessPropertyClosureOriginalNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasClosureOriginalNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_CLOSURE_ORIGINAL_NAME[NodeType] =
    new Traversal_Property_CLOSURE_ORIGINAL_NAME(traversal.iterator)
  implicit def accessPropertyCodeTraversal[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasCodeEMT]](
    traversal: IterableOnce[NodeType]
  ): Traversal_Property_CODE[NodeType] = new Traversal_Property_CODE(traversal.iterator)
  implicit def accessPropertyColumnNumberTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasColumnNumberEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_COLUMN_NUMBER[NodeType] =
    new Traversal_Property_COLUMN_NUMBER(traversal.iterator)
  implicit def accessPropertyColumnNumberEndTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasColumnNumberEndEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_COLUMN_NUMBER_END[NodeType] =
    new Traversal_Property_COLUMN_NUMBER_END(traversal.iterator)
  implicit def accessPropertyContainedRefTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasContainedRefEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_CONTAINED_REF[NodeType] =
    new Traversal_Property_CONTAINED_REF(traversal.iterator)
  implicit def accessPropertyContentTraversal[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasContentEMT]](
    traversal: IterableOnce[NodeType]
  ): Traversal_Property_CONTENT[NodeType] = new Traversal_Property_CONTENT(traversal.iterator)
  implicit def accessPropertyControlStructureTypeTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasControlStructureTypeEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_CONTROL_STRUCTURE_TYPE[NodeType] =
    new Traversal_Property_CONTROL_STRUCTURE_TYPE(traversal.iterator)
  implicit def accessPropertyDependencyGroupIdTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasDependencyGroupIdEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_DEPENDENCY_GROUP_ID[NodeType] =
    new Traversal_Property_DEPENDENCY_GROUP_ID(traversal.iterator)
  implicit def accessPropertyDispatchTypeTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasDispatchTypeEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_DISPATCH_TYPE[NodeType] =
    new Traversal_Property_DISPATCH_TYPE(traversal.iterator)
  implicit def accessPropertyDynamicTypeHintFullNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasDynamicTypeHintFullNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_DYNAMIC_TYPE_HINT_FULL_NAME[NodeType] =
    new Traversal_Property_DYNAMIC_TYPE_HINT_FULL_NAME(traversal.iterator)
  implicit def accessPropertyEvaluationStrategyTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasEvaluationStrategyEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_EVALUATION_STRATEGY[NodeType] =
    new Traversal_Property_EVALUATION_STRATEGY(traversal.iterator)
  implicit def accessPropertyExplicitAsTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasExplicitAsEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_EXPLICIT_AS[NodeType] = new Traversal_Property_EXPLICIT_AS(
    traversal.iterator
  )
  implicit def accessPropertyFilenameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasFilenameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_FILENAME[NodeType] = new Traversal_Property_FILENAME(
    traversal.iterator
  )
  implicit def accessPropertyFullNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasFullNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_FULL_NAME[NodeType] = new Traversal_Property_FULL_NAME(
    traversal.iterator
  )
  implicit def accessPropertyHashTraversal[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasHashEMT]](
    traversal: IterableOnce[NodeType]
  ): Traversal_Property_HASH[NodeType] = new Traversal_Property_HASH(traversal.iterator)
  implicit def accessPropertyImportedAsTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasImportedAsEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_IMPORTED_AS[NodeType] = new Traversal_Property_IMPORTED_AS(
    traversal.iterator
  )
  implicit def accessPropertyImportedEntityTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasImportedEntityEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_IMPORTED_ENTITY[NodeType] =
    new Traversal_Property_IMPORTED_ENTITY(traversal.iterator)
  implicit def accessPropertyIndexTraversal[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasIndexEMT]](
    traversal: IterableOnce[NodeType]
  ): Traversal_Property_INDEX[NodeType] = new Traversal_Property_INDEX(traversal.iterator)
  implicit def accessPropertyInheritsFromTypeFullNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasInheritsFromTypeFullNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_INHERITS_FROM_TYPE_FULL_NAME[NodeType] =
    new Traversal_Property_INHERITS_FROM_TYPE_FULL_NAME(traversal.iterator)
  implicit def accessPropertyIsExplicitTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasIsExplicitEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_IS_EXPLICIT[NodeType] = new Traversal_Property_IS_EXPLICIT(
    traversal.iterator
  )
  implicit def accessPropertyIsExternalTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasIsExternalEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_IS_EXTERNAL[NodeType] = new Traversal_Property_IS_EXTERNAL(
    traversal.iterator
  )
  implicit def accessPropertyIsVariadicTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasIsVariadicEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_IS_VARIADIC[NodeType] = new Traversal_Property_IS_VARIADIC(
    traversal.iterator
  )
  implicit def accessPropertyIsWildcardTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasIsWildcardEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_IS_WILDCARD[NodeType] = new Traversal_Property_IS_WILDCARD(
    traversal.iterator
  )
  implicit def accessPropertyKeyTraversal[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasKeyEMT]](
    traversal: IterableOnce[NodeType]
  ): Traversal_Property_KEY[NodeType] = new Traversal_Property_KEY(traversal.iterator)
  implicit def accessPropertyLanguageTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasLanguageEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_LANGUAGE[NodeType] = new Traversal_Property_LANGUAGE(
    traversal.iterator
  )
  implicit def accessPropertyLineNumberTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasLineNumberEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_LINE_NUMBER[NodeType] = new Traversal_Property_LINE_NUMBER(
    traversal.iterator
  )
  implicit def accessPropertyLineNumberEndTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasLineNumberEndEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_LINE_NUMBER_END[NodeType] =
    new Traversal_Property_LINE_NUMBER_END(traversal.iterator)
  implicit def accessPropertyMethodFullNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasMethodFullNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_METHOD_FULL_NAME[NodeType] =
    new Traversal_Property_METHOD_FULL_NAME(traversal.iterator)
  implicit def accessPropertyMethodShortNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasMethodShortNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_METHOD_SHORT_NAME[NodeType] =
    new Traversal_Property_METHOD_SHORT_NAME(traversal.iterator)
  implicit def accessPropertyModifierTypeTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasModifierTypeEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_MODIFIER_TYPE[NodeType] =
    new Traversal_Property_MODIFIER_TYPE(traversal.iterator)
  implicit def accessPropertyNameTraversal[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasNameEMT]](
    traversal: IterableOnce[NodeType]
  ): Traversal_Property_NAME[NodeType] = new Traversal_Property_NAME(traversal.iterator)
  implicit def accessPropertyNodeLabelTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasNodeLabelEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_NODE_LABEL[NodeType] = new Traversal_Property_NODE_LABEL(
    traversal.iterator
  )
  implicit def accessPropertyOffsetTraversal[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasOffsetEMT]](
    traversal: IterableOnce[NodeType]
  ): Traversal_Property_OFFSET[NodeType] = new Traversal_Property_OFFSET(traversal.iterator)
  implicit def accessPropertyOffsetEndTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasOffsetEndEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_OFFSET_END[NodeType] = new Traversal_Property_OFFSET_END(
    traversal.iterator
  )
  implicit def accessPropertyOrderTraversal[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasOrderEMT]](
    traversal: IterableOnce[NodeType]
  ): Traversal_Property_ORDER[NodeType] = new Traversal_Property_ORDER(traversal.iterator)
  implicit def accessPropertyOverlaysTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasOverlaysEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_OVERLAYS[NodeType] = new Traversal_Property_OVERLAYS(
    traversal.iterator
  )
  implicit def accessPropertyPackageNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasPackageNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_PACKAGE_NAME[NodeType] = new Traversal_Property_PACKAGE_NAME(
    traversal.iterator
  )
  implicit def accessPropertyParserTypeNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasParserTypeNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_PARSER_TYPE_NAME[NodeType] =
    new Traversal_Property_PARSER_TYPE_NAME(traversal.iterator)
  implicit def accessPropertyPossibleTypesTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasPossibleTypesEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_POSSIBLE_TYPES[NodeType] =
    new Traversal_Property_POSSIBLE_TYPES(traversal.iterator)
  implicit def accessPropertyRootTraversal[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasRootEMT]](
    traversal: IterableOnce[NodeType]
  ): Traversal_Property_ROOT[NodeType] = new Traversal_Property_ROOT(traversal.iterator)
  implicit def accessPropertySignatureTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasSignatureEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_SIGNATURE[NodeType] = new Traversal_Property_SIGNATURE(
    traversal.iterator
  )
  implicit def accessPropertySymbolTraversal[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasSymbolEMT]](
    traversal: IterableOnce[NodeType]
  ): Traversal_Property_SYMBOL[NodeType] = new Traversal_Property_SYMBOL(traversal.iterator)
  implicit def accessPropertyTypeDeclFullNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasTypeDeclFullNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_TYPE_DECL_FULL_NAME[NodeType] =
    new Traversal_Property_TYPE_DECL_FULL_NAME(traversal.iterator)
  implicit def accessPropertyTypeFullNameTraversal[
    NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasTypeFullNameEMT]
  ](traversal: IterableOnce[NodeType]): Traversal_Property_TYPE_FULL_NAME[NodeType] =
    new Traversal_Property_TYPE_FULL_NAME(traversal.iterator)
  implicit def accessPropertyValueTraversal[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasValueEMT]](
    traversal: IterableOnce[NodeType]
  ): Traversal_Property_VALUE[NodeType] = new Traversal_Property_VALUE(traversal.iterator)
  implicit def accessPropertyVersionTraversal[NodeType <: nodes.StoredNode with nodes.StaticType[nodes.HasVersionEMT]](
    traversal: IterableOnce[NodeType]
  ): Traversal_Property_VERSION[NodeType] = new Traversal_Property_VERSION(traversal.iterator)
}

trait ConcreteBaseConversions extends AbstractBaseConversions0 {
  import Accessors.*
  implicit def traversal_AnnotationBase[NodeType <: nodes.AnnotationBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_AnnotationBase[NodeType] = new Traversal_AnnotationBase(traversal.iterator)
  implicit def traversal_AnnotationLiteralBase[NodeType <: nodes.AnnotationLiteralBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_AnnotationLiteralBase[NodeType] = new Traversal_AnnotationLiteralBase(traversal.iterator)
  implicit def traversal_AnnotationParameterBase[NodeType <: nodes.AnnotationParameterBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_AnnotationParameterBase[NodeType] = new Traversal_AnnotationParameterBase(traversal.iterator)
  implicit def traversal_AnnotationParameterAssignBase[NodeType <: nodes.AnnotationParameterAssignBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_AnnotationParameterAssignBase[NodeType] = new Traversal_AnnotationParameterAssignBase(traversal.iterator)
  implicit def traversal_ArrayInitializerBase[NodeType <: nodes.ArrayInitializerBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_ArrayInitializerBase[NodeType] = new Traversal_ArrayInitializerBase(traversal.iterator)
  implicit def traversal_BindingBase[NodeType <: nodes.BindingBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_BindingBase[NodeType] = new Traversal_BindingBase(traversal.iterator)
  implicit def traversal_BlockBase[NodeType <: nodes.BlockBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_BlockBase[NodeType] = new Traversal_BlockBase(traversal.iterator)
  implicit def traversal_CallBase[NodeType <: nodes.CallBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_CallBase[NodeType] = new Traversal_CallBase(traversal.iterator)
  implicit def traversal_ClosureBindingBase[NodeType <: nodes.ClosureBindingBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_ClosureBindingBase[NodeType] = new Traversal_ClosureBindingBase(traversal.iterator)
  implicit def traversal_CommentBase[NodeType <: nodes.CommentBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_CommentBase[NodeType] = new Traversal_CommentBase(traversal.iterator)
  implicit def traversal_ConfigFileBase[NodeType <: nodes.ConfigFileBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_ConfigFileBase[NodeType] = new Traversal_ConfigFileBase(traversal.iterator)
  implicit def traversal_ControlStructureBase[NodeType <: nodes.ControlStructureBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_ControlStructureBase[NodeType] = new Traversal_ControlStructureBase(traversal.iterator)
  implicit def traversal_DependencyBase[NodeType <: nodes.DependencyBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_DependencyBase[NodeType] = new Traversal_DependencyBase(traversal.iterator)
  implicit def traversal_FieldIdentifierBase[NodeType <: nodes.FieldIdentifierBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_FieldIdentifierBase[NodeType] = new Traversal_FieldIdentifierBase(traversal.iterator)
  implicit def traversal_FileBase[NodeType <: nodes.FileBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_FileBase[NodeType] = new Traversal_FileBase(traversal.iterator)
  implicit def traversal_FindingBase[NodeType <: nodes.FindingBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_FindingBase[NodeType] = new Traversal_FindingBase(traversal.iterator)
  implicit def traversal_IdentifierBase[NodeType <: nodes.IdentifierBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_IdentifierBase[NodeType] = new Traversal_IdentifierBase(traversal.iterator)
  implicit def traversal_ImportBase[NodeType <: nodes.ImportBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_ImportBase[NodeType] = new Traversal_ImportBase(traversal.iterator)
  implicit def traversal_JumpLabelBase[NodeType <: nodes.JumpLabelBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_JumpLabelBase[NodeType] = new Traversal_JumpLabelBase(traversal.iterator)
  implicit def traversal_JumpTargetBase[NodeType <: nodes.JumpTargetBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_JumpTargetBase[NodeType] = new Traversal_JumpTargetBase(traversal.iterator)
  implicit def traversal_KeyValuePairBase[NodeType <: nodes.KeyValuePairBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_KeyValuePairBase[NodeType] = new Traversal_KeyValuePairBase(traversal.iterator)
  implicit def traversal_LiteralBase[NodeType <: nodes.LiteralBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_LiteralBase[NodeType] = new Traversal_LiteralBase(traversal.iterator)
  implicit def traversal_LocalBase[NodeType <: nodes.LocalBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_LocalBase[NodeType] = new Traversal_LocalBase(traversal.iterator)
  implicit def traversal_LocationBase[NodeType <: nodes.LocationBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_LocationBase[NodeType] = new Traversal_LocationBase(traversal.iterator)
  implicit def traversal_MemberBase[NodeType <: nodes.MemberBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_MemberBase[NodeType] = new Traversal_MemberBase(traversal.iterator)
  implicit def traversal_MetaDataBase[NodeType <: nodes.MetaDataBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_MetaDataBase[NodeType] = new Traversal_MetaDataBase(traversal.iterator)
  implicit def traversal_MethodBase[NodeType <: nodes.MethodBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_MethodBase[NodeType] = new Traversal_MethodBase(traversal.iterator)
  implicit def traversal_MethodParameterInBase[NodeType <: nodes.MethodParameterInBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_MethodParameterInBase[NodeType] = new Traversal_MethodParameterInBase(traversal.iterator)
  implicit def traversal_MethodParameterOutBase[NodeType <: nodes.MethodParameterOutBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_MethodParameterOutBase[NodeType] = new Traversal_MethodParameterOutBase(traversal.iterator)
  implicit def traversal_MethodRefBase[NodeType <: nodes.MethodRefBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_MethodRefBase[NodeType] = new Traversal_MethodRefBase(traversal.iterator)
  implicit def traversal_MethodReturnBase[NodeType <: nodes.MethodReturnBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_MethodReturnBase[NodeType] = new Traversal_MethodReturnBase(traversal.iterator)
  implicit def traversal_ModifierBase[NodeType <: nodes.ModifierBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_ModifierBase[NodeType] = new Traversal_ModifierBase(traversal.iterator)
  implicit def traversal_NamespaceBase[NodeType <: nodes.NamespaceBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_NamespaceBase[NodeType] = new Traversal_NamespaceBase(traversal.iterator)
  implicit def traversal_NamespaceBlockBase[NodeType <: nodes.NamespaceBlockBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_NamespaceBlockBase[NodeType] = new Traversal_NamespaceBlockBase(traversal.iterator)
  implicit def traversal_ReturnBase[NodeType <: nodes.ReturnBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_ReturnBase[NodeType] = new Traversal_ReturnBase(traversal.iterator)
  implicit def traversal_TagBase[NodeType <: nodes.TagBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_TagBase[NodeType] = new Traversal_TagBase(traversal.iterator)
  implicit def traversal_TagNodePairBase[NodeType <: nodes.TagNodePairBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_TagNodePairBase[NodeType] = new Traversal_TagNodePairBase(traversal.iterator)
  implicit def traversal_TemplateDomBase[NodeType <: nodes.TemplateDomBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_TemplateDomBase[NodeType] = new Traversal_TemplateDomBase(traversal.iterator)
  implicit def traversal_TypeBase[NodeType <: nodes.TypeBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_TypeBase[NodeType] = new Traversal_TypeBase(traversal.iterator)
  implicit def traversal_TypeArgumentBase[NodeType <: nodes.TypeArgumentBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_TypeArgumentBase[NodeType] = new Traversal_TypeArgumentBase(traversal.iterator)
  implicit def traversal_TypeDeclBase[NodeType <: nodes.TypeDeclBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_TypeDeclBase[NodeType] = new Traversal_TypeDeclBase(traversal.iterator)
  implicit def traversal_TypeParameterBase[NodeType <: nodes.TypeParameterBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_TypeParameterBase[NodeType] = new Traversal_TypeParameterBase(traversal.iterator)
  implicit def traversal_TypeRefBase[NodeType <: nodes.TypeRefBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_TypeRefBase[NodeType] = new Traversal_TypeRefBase(traversal.iterator)
  implicit def traversal_UnknownBase[NodeType <: nodes.UnknownBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_UnknownBase[NodeType] = new Traversal_UnknownBase(traversal.iterator)
}

trait AbstractBaseConversions0 extends AbstractBaseConversions1 {
  import Accessors.*
  implicit def traversal_AstNodeBase[NodeType <: nodes.AstNodeBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_AstNodeBase[NodeType] = new Traversal_AstNodeBase(traversal.iterator)
  implicit def traversal_CallReprBase[NodeType <: nodes.CallReprBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_CallReprBase[NodeType] = new Traversal_CallReprBase(traversal.iterator)
  implicit def traversal_CfgNodeBase[NodeType <: nodes.CfgNodeBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_CfgNodeBase[NodeType] = new Traversal_CfgNodeBase(traversal.iterator)
  implicit def traversal_ExpressionBase[NodeType <: nodes.ExpressionBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_ExpressionBase[NodeType] = new Traversal_ExpressionBase(traversal.iterator)
}

trait AbstractBaseConversions1 {
  import Accessors.*
  implicit def traversal_DeclarationBase[NodeType <: nodes.DeclarationBase](
    traversal: IterableOnce[NodeType]
  ): Traversal_DeclarationBase[NodeType] = new Traversal_DeclarationBase(traversal.iterator)
}
