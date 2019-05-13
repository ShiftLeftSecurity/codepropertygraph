package io.shiftleft.cpgvalidator

object ConstraintClasses {

  val N:Int = Int.MaxValue

  implicit class ConstraintStartWrapper(val nodeType: String) {
    def has(degreeStart: Int): ConstraintTier1 = {
      ConstraintTier1(nodeType, degreeStart to degreeStart)
    }

  }

  case class ConstraintTier1(nodeType: String, degreeRange: Range.Inclusive) {
    def to(degreeEnd: Int): ConstraintTier2 = {
      ConstraintTier2(nodeType, degreeRange.start to degreeEnd)
    }

    def outgoing(edgeType: String): OutConstraintTier1 = {
      OutConstraintTier1(nodeType, degreeRange, edgeType)
    }

    def incoming(edgeType: String): InConstraintTier1 = {
      InConstraintTier1(nodeType, degreeRange, edgeType)
    }
  }

  case class ConstraintTier2(nodeType: String, degreeRange: Range.Inclusive) {
    def outgoing(edgeType: String): OutConstraintTier1 = {
      OutConstraintTier1(nodeType, degreeRange, edgeType)
    }

    def incoming(edgeType: String): InConstraintTier1 = {
      InConstraintTier1(nodeType, degreeRange, edgeType)
    }
  }

  case class OutConstraintTier1(srcNodeType: String,
                                outDegreeRange: Range.Inclusive,
                                edgeType: String) {
    def to(dstNodeType: String): OutConstraint = {
      OutConstraint(srcNodeType, outDegreeRange, edgeType, dstNodeType :: Nil)
    }
  }

  case class OutConstraint(srcNodeType: String,
                           outDegreeRange: Range.Inclusive,
                           edgeType: String,
                           dstNodeTypes: List[String]) {
    def and(dstNodeType: String): OutConstraint = {
      OutConstraint(srcNodeType, outDegreeRange, edgeType, dstNodeType :: dstNodeTypes)
    }
  }

  case class InConstraintTier1(dstNodeType: String,
                               inDegreeRange: Range.Inclusive,
                               edgeType: String) {
    def from(srcNodeType: String): InConstraint = {
      InConstraint(dstNodeType, inDegreeRange, edgeType, srcNodeType :: Nil)
    }
  }

  case class InConstraint(dstNodeType: String,
                          inDegreeRange: Range.Inclusive,
                          edgeType: String,
                          srcNodeTypes: List[String]) {
    def and(srcNodeType: String): InConstraint = {
      InConstraint(dstNodeType, inDegreeRange, edgeType, srcNodeType :: srcNodeTypes)
    }
  }


}
