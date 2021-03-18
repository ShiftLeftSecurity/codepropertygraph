package io.shiftleft.codepropertygraph.schema

object Deprecated {
  def apply(base: Base.Schema) = new Schema(base)

  class Schema(base: Base.Schema) {
    import base._

    // node types
    callNode
      .addProperties(methodInstFullName, typeFullName)

  }

}
