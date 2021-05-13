package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Schema, SchemaBuilder}

/**
  * Schema for the base code property graph
  * Language modules are required to produce graphs adhering to this schema
  */
class CpgSchema(builder: SchemaBuilder) {
  // the foundation
  val base = Base(builder)
  val typeDecl = TypeDecl(builder, base)
  val method = Method(builder, base, typeDecl)
  val methodBody = MethodBody(builder, base, method, typeDecl)

  val metaData = MetaData(builder, base)

  val enhancements = Enhancements(builder, base, method, methodBody, typeDecl)

  // everything else
  val protoSerialize = ProtoSerialize(builder, methodBody)
  val closure = Closure(builder, base, method, methodBody, enhancements)
  val finding = Finding(builder, enhancements)
  val operators = Operators(builder)
  val sourceSpecific = Comment(builder, base, enhancements)
  val tagsAndLocation = TagsAndLocation(builder, base, typeDecl, method, methodBody, enhancements)
}

object CpgSchema {
  val instance: Schema = {
    val builder = new SchemaBuilder("io.shiftleft.codepropertygraph.generated")
    new CpgSchema(builder)
    builder.build
  }
}
