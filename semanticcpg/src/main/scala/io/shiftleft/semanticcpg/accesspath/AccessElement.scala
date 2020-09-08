package io.shiftleft.semanticcpg.accesspath

sealed abstract class AccessElement(name: String) {
  override def toString: String = name
}

case class ConstantAccess(constant: String) extends AccessElement(constant)

case object VariableAccess extends AccessElement("?")

case object VariablePointerShift extends AccessElement("<?>")

// this will eventually get an optional extent (how many bytes wide is the memory load/store)
case object IndirectionAccess extends AccessElement("*")

case object AddressOf extends AccessElement("&")

// this will eventually obtain an optional byteOffset
case class PointerShift(logicalOffset: Int) extends AccessElement(s"<${logicalOffset}>")
