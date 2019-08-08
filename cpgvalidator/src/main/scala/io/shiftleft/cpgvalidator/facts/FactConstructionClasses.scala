package io.shiftleft.cpgvalidator.facts

object FactConstructionClasses {

  val N: Int = Int.MaxValue

  abstract class Fact

  implicit class FactStartWrapper(val nodeType: String) {
    def has(degreeStart: Int): FactTier1 = {
      FactTier1(nodeType, degreeStart to degreeStart)
    }

  }

  implicit class KeysFactStartWrapper(val nodeType: String) {
    def withKey(nodeKeyType: String): KeysFactTier1 = {
      KeysFactTier1(nodeType, nodeKeyType)
    }

  }

  case class KeysFactTier1(nodeType: String, nodeKeyType: String) {
    def hasCardinality(cardinality: Cardinality): KeysFact =
      KeysFact(nodeType, nodeKeyType, cardinality)
  }

  sealed abstract class Cardinality(val name: String)
  object Cardinality {
    case object ZeroOrOne extends Cardinality("zeroOrOne")
    case object One extends Cardinality("one")
    case object List extends Cardinality("list")

    def apply(name: String): Cardinality =
      Seq(ZeroOrOne, One, List)
        .find(_.name == name)
        .getOrElse(
          throw new AssertionError(
            s"Cardinality must be one of `zeroOrOne`, `one`, `list`, but was '$name'!"
          )
        )
  }

  case class KeysFact(nodeType: String,
                      nodeKeyType: String,
                      cardinality: Cardinality)
      extends Fact

  case class FactTier1(nodeType: String, degreeRange: Range.Inclusive) {
    def to(degreeEnd: Int): FactTier2 = {
      FactTier2(nodeType, degreeRange.start to degreeEnd)
    }

    def outgoing(edgeType: String): OutFactTier1 = {
      OutFactTier1(nodeType, degreeRange, edgeType)
    }

    def incoming(edgeType: String): InFactTier1 = {
      InFactTier1(nodeType, degreeRange, edgeType)
    }
  }

  case class FactTier2(nodeType: String, degreeRange: Range.Inclusive) {
    def outgoing(edgeType: String): OutFactTier1 = {
      OutFactTier1(nodeType, degreeRange, edgeType)
    }

    def incoming(edgeType: String): InFactTier1 = {
      InFactTier1(nodeType, degreeRange, edgeType)
    }
  }

  case class OutFactTier1(srcNodeType: String,
                          outDegreeRange: Range.Inclusive,
                          edgeType: String) {
    def to(dstNodeType: String): OutFact = {
      OutFact(srcNodeType, outDegreeRange, edgeType, dstNodeType :: Nil)
    }

    def to(dstNodeTypes: List[String]): OutFact = {
      OutFact(srcNodeType, outDegreeRange, edgeType, dstNodeTypes)
    }
  }

  case class OutFact(srcNodeType: String,
                     outDegreeRange: Range.Inclusive,
                     edgeType: String,
                     dstNodeTypes: List[String])
      extends Fact {
    def or(dstNodeType: String): OutFact = {
      OutFact(
        srcNodeType,
        outDegreeRange,
        edgeType,
        dstNodeType :: dstNodeTypes
      )
    }

    def or(additionalDstNodeTypes: List[String]): OutFact = {
      OutFact(
        srcNodeType,
        outDegreeRange,
        edgeType,
        additionalDstNodeTypes ::: dstNodeTypes
      )
    }
  }

  case class InFactTier1(dstNodeType: String,
                         inDegreeRange: Range.Inclusive,
                         edgeType: String) {
    def from(srcNodeType: String): InFact = {
      InFact(dstNodeType, inDegreeRange, edgeType, srcNodeType :: Nil)
    }

    def from(srcNodeTypes: List[String]): InFact = {
      InFact(dstNodeType, inDegreeRange, edgeType, srcNodeTypes)
    }
  }

  case class InFact(dstNodeType: String,
                    inDegreeRange: Range.Inclusive,
                    edgeType: String,
                    srcNodeTypes: List[String])
      extends Fact {
    def or(srcNodeType: String): InFact = {
      InFact(dstNodeType, inDegreeRange, edgeType, srcNodeType :: srcNodeTypes)
    }

    def or(additionalSrcNodeType: List[String]): InFact = {
      InFact(
        dstNodeType,
        inDegreeRange,
        edgeType,
        additionalSrcNodeType ::: srcNodeTypes
      )
    }
  }

}
