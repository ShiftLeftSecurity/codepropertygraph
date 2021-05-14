package io.shiftleft.codepropertygraph.schema

trait SchemaBase {
  def index: Int
  def description: String
  def providedByFrontend: Boolean = false
}
