package io.shiftleft.codepropertygraph.generated.nodes

object CallRepr {
  object PropertyNames {
    val Code             = "CODE"
    val ColumnNumber     = "COLUMN_NUMBER"
    val LineNumber       = "LINE_NUMBER"
    val Name             = "NAME"
    val Order            = "ORDER"
    val Signature        = "SIGNATURE"
    val all: Set[String] = Set(Code, ColumnNumber, LineNumber, Name, Order, Signature)
  }

  object Properties {
    val Code         = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber = new overflowdb.PropertyKey[scala.Int]("COLUMN_NUMBER")
    val LineNumber   = new overflowdb.PropertyKey[scala.Int]("LINE_NUMBER")
    val Name         = new overflowdb.PropertyKey[String]("NAME")
    val Order        = new overflowdb.PropertyKey[scala.Int]("ORDER")
    val Signature    = new overflowdb.PropertyKey[String]("SIGNATURE")
  }

  object PropertyDefaults {
    val Code      = "<empty>"
    val Name      = "<empty>"
    val Order     = -1: Int
    val Signature = ""
  }

  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String]  = Array()
  }

}

trait CallReprBase extends AbstractNode with CfgNodeBase {
  def code: String
  def columnNumber: Option[scala.Int]
  def lineNumber: Option[scala.Int]
  def name: String
  def order: scala.Int
  def signature: String
}

trait CallReprNew extends NewNode with CfgNodeNew {
  def code_=(value: String): Unit
  def columnNumber_=(value: Option[scala.Int]): Unit
  def lineNumber_=(value: Option[scala.Int]): Unit
  def name_=(value: String): Unit
  def order_=(value: scala.Int): Unit
  def signature_=(value: String): Unit
  def code: String
  def columnNumber: Option[scala.Int]
  def lineNumber: Option[scala.Int]
  def name: String
  def order: scala.Int
  def signature: String
}

trait CallRepr extends StoredNode with CallReprBase with CfgNode {
  import overflowdb.traversal._

}
