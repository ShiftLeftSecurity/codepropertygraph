package io.shiftleft.dataflowengineoss.semanticsloader

case class Semantics(elements: List[FlowSemantic])

object SemanticsLoader {

  def emptySemantics: Semantics = {
    Semantics(Nil)
  }
}

class SemanticsLoader(filename: String) {

  def load(): Semantics = {
    val elements = new Parser().parseFile(filename)
    Semantics(elements)
  }

}
