package io.shiftleft.semanticcpg.accesspath

object AccessPath {

  val empty = new AccessPath(Elements(), List[Elements]())

  def apply(elements: Elements, exclusions: Seq[Elements]): AccessPath = {
    new AccessPath(elements, exclusions.toList)
  }

  def isExtensionExcluded(exclusions: Seq[Elements], extension: Elements): Boolean = {
    exclusions.exists(e => extension.elements.startsWith(e.elements))
  }
}

case class AccessPath(elements: Elements, exclusions: Seq[Elements]) {

  def isExtensionExcluded(extension: Elements): Boolean = {
    AccessPath.isExtensionExcluded(this.exclusions, extension)
  }

  def isEmpty: Boolean = this == AccessPath.empty

  // for handling of invertible elements, cf AccessPathAlgebra.md
  def ++(other: Elements): Option[AccessPath] = {
    if (isExtensionExcluded(other)) None
    //fixme: may need to process invertible tail of other better
    else Some(AccessPath(this.elements ++ other, this.truncateExclusions(other).exclusions))
  }

  def ++(other: AccessPath): Option[AccessPath] = {
    if (isExtensionExcluded(other.elements)) None
    //fixme: may need to process invertible tail of other better
    else {
      var accessPath = AccessPath(this.elements ++ other.elements, this.truncateExclusions(other.elements).exclusions)
      for (exclusion <- other.exclusions) {
        accessPath = accessPath.addExclusion(exclusion)
      }
      Some(accessPath)
    }
  }

  def matchFull(other: AccessPath): FullMatchResult = {
    val res = this.matchFull(other.elements)
    if (res.extensionDiff.isEmpty && res.stepIntoPath.isDefined && other.isExtensionExcluded(
          res.stepIntoPath.get.elements)) {
      FullMatchResult(Some(this), None, Elements.empty)
    } else res
  }

  def matchFull(other: Elements): FullMatchResult = {
    val (matchRes, matchDiff) = this.matchAndDiff(other)
    matchRes match {
      case MatchResult.NO_MATCH =>
        FullMatchResult(Some(this), None, Elements.empty)
      case MatchResult.PREFIX_MATCH | MatchResult.EXACT_MATCH =>
        FullMatchResult(None, Some(AccessPath(matchDiff, this.exclusions).intern), Elements.empty)
      case MatchResult.VARIABLE_PREFIX_MATCH | MatchResult.VARIABLE_EXACT_MATCH =>
        FullMatchResult(Some(this), Some(AccessPath(matchDiff, this.exclusions).intern), Elements.empty)
      case MatchResult.EXTENDED_MATCH =>
        FullMatchResult(Some(this.addExclusion(matchDiff)),
                        Some(AccessPath(Elements.empty, exclusions).truncateExclusions(matchDiff)),
                        matchDiff)
      case MatchResult.VARIABLE_EXTENDED_MATCH =>
        FullMatchResult(Some(this),
                        Some(AccessPath(Elements.empty, exclusions).truncateExclusions(matchDiff)),
                        matchDiff)
    }
  }

  def matchAndDiff(other: Elements): (MatchResult.MatchResult, Elements) = {
    val thisTail = elements.invertibleTailLength
    val otherTail = other.invertibleTailLength
    val thisHead = elements.elements.length - thisTail
    val otherHead = other.elements.length - otherTail

    val cmpUntil = scala.math.min(thisHead, otherHead)
    var idx = 0
    var overTainted = false
    while (idx < cmpUntil) {
      (elements.elements(idx), other.elements(idx)) match {
        case (VariableAccess, VariableAccess) | (_: ConstantAccess, VariableAccess) |
            (VariableAccess, _: ConstantAccess) | (VariablePointerShift, VariablePointerShift) |
            (_: PointerShift, VariablePointerShift) | (VariablePointerShift, _: PointerShift) =>
          overTainted = true
        case (thisElem, otherElem) =>
          if (thisElem != otherElem)
            return (MatchResult.NO_MATCH, Elements.empty)
      }
      idx += 1
    }
    var done = false

    /**
      *  We now try to greedily match more elements. We know that one of the two pathes will only contain invertible
      *  elements. The issue is the following:
      *   prefix <1> & x
      *   prefix <?> &
      *  With greedy matching, we end up with a diff: x.
      *  If we just did the invert-append algorithm, we would end up with a less precise
      *   diff: * <?> <1> &  x == * <?> & x.
      */
    val minlen = scala.math.min(elements.elements.length, other.elements.length)
    while (!done && idx < minlen) {
      (elements.elements(idx), other.elements(idx)) match {
        case (_: PointerShift, VariablePointerShift) | (VariablePointerShift, _: PointerShift) |
            (VariablePointerShift, VariablePointerShift) =>
          overTainted = true
          idx += 1
        case (thisElem, otherElem) =>
          if (thisElem == otherElem) {
            idx += 1
          } else {
            done = true
          }
      }
    }
    if (thisHead >= otherHead) {
      // prefix or exact
      val diff = Elements.inverted(other.elements.drop(idx)) ++ Elements.unnormalized(elements.elements.drop(idx))

      /**
        * we don't need to overtaint if thisTail has variable PointerShift: They can still get excluded
        * e.g. suppose we track "a" "b" <?> and encounter "a" <4>.
        */
      overTainted |= !other.noOvertaint(otherHead)
      if (!overTainted & thisHead == otherHead) (MatchResult.EXACT_MATCH, diff)
      else if (overTainted && thisHead == otherHead) (MatchResult.VARIABLE_EXACT_MATCH, diff)
      else if (!overTainted && thisHead != otherHead) (MatchResult.PREFIX_MATCH, diff)
      else if (overTainted && thisHead != otherHead) (MatchResult.VARIABLE_PREFIX_MATCH, diff)
      else throw new RuntimeException()
    } else {
      //extended
      val diff = Elements.inverted(elements.elements.drop(idx)) ++ Elements.unnormalized(other.elements.drop(idx))

      /**
        * we need to overtaint if any either otherTail or thisTail has variable PointerShift:
        * e.g. suppose we track "a" <4> and encounter "a" "b" <?> "c", or suppose that we track "a" <?> and encounter "a" "b"
        */
      overTainted |= !elements.noOvertaint(thisHead) | !other.noOvertaint(otherHead)

      if (overTainted) (MatchResult.VARIABLE_EXTENDED_MATCH, diff)
      else if (isExtensionExcluded(diff)) (MatchResult.NO_MATCH, Elements.empty)
      else (MatchResult.EXTENDED_MATCH, diff)
    }
  }

  private def truncateExclusions(compareExclusion: Elements): AccessPath = {
    val size = compareExclusion.elements.length
    val newExclusions =
      exclusions
        .filter(_.elements.startsWith(compareExclusion.elements))
        .map(exclusion => Elements.normalized(exclusion.elements.drop(size)))
    new AccessPath(elements, newExclusions)
  }

  private def addExclusion(newExclusion: Elements): AccessPath = {
    if (newExclusion.noOvertaint()) {
      val ex =
        Elements.unnormalized(newExclusion.elements.dropRight(newExclusion.invertibleTailLength))
      val unshadowed = exclusions.filter(!_.elements.startsWith(ex.elements))
      AccessPath(elements, unshadowed :+ ex)
    } else this
  }

  private def intern(): AccessPath = {
    if (this == AccessPath.empty) AccessPath.empty
    else this
  }

}

sealed trait MatchResult
object MatchResult extends Enumeration {
  type MatchResult = Value
  val NO_MATCH, EXACT_MATCH, VARIABLE_EXACT_MATCH, PREFIX_MATCH, VARIABLE_PREFIX_MATCH, EXTENDED_MATCH,
  VARIABLE_EXTENDED_MATCH = Value
}

/**
  * Result of `matchFull` comparison
  *
  * @param stepOverPath              the unaffected part of the accesspath. Some(this) for
  *                                  no match, None for perfect match; may have additional
  *                                  exclusions to this.
  * @param stepIntoPath              The affected part of the accesspath, mapped to be relative to
  *                                  this stepIntoPath.isDefined if and only if there is a match in
  *                                  paths, i.e. if the call can affect the tracked variable at all.
  *                                  Outside of overtainting, if stepIntoPath.isDefined &&
  *                                  stepIntoPath.elements.nonEmpty then: path.elements ==
  *                                  other.elements ++ path.matchFull(other).stepIntoPath.get.elements
  *                                  extensionDiff.isEmpty
  *
  * @param extensionDiff            extensionDiff is non empty if and only if a proper subset is
  *                                 affected. Outside of overtainting, if extensionDiff is non empty then:
  *                                 path.elements ++ path.matchFull(other).extensionDiff == other.elements
  *                                 path.matchFull(other).stepIntoPath.get.elements.isEmpty
  *
  * Invariants:
  *   - Exclusions have no invertible tail
  *   - Only paths without overTaint can have exclusions
  *   TODO: Figure out sensible assertions to defend these invariants
  */
case class FullMatchResult(stepOverPath: Option[AccessPath],
                           stepIntoPath: Option[AccessPath],
                           extensionDiff: Elements) {
  def hasMatch: Boolean = stepIntoPath.nonEmpty
}
