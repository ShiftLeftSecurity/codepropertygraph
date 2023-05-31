package io.shiftleft.codepropertygraph.generated.nodes

object Declaration {
  object PropertyNames {
    val Name             = "NAME"
    val all: Set[String] = Set(Name)
  }

  object Properties {
    val Name = new overflowdb.PropertyKey[String]("NAME")
  }

  object PropertyDefaults {
    val Name = "<empty>"
  }

  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String]  = Array()
  }

}

trait DeclarationBase extends AbstractNode {
  def name: String
}

trait DeclarationNew extends NewNode {
  def name_=(value: String): Unit
  def name: String
}

trait Declaration extends StoredNode with DeclarationBase {
  import overflowdb.traversal._

}
