package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Schema, SchemaBuilder}

/**
  * Schema for the base code property graph
  * Language modules are required to produce graphs adhering to this schema
  */
class CpgSchema(builder: SchemaBuilder) {
  // the foundation
  val base = Base(builder)
  val metaData = MetaData(builder, base)
  val enhancements = Enhancements(builder, base)

  // everything else
  val protoSerialize = ProtoSerialize(builder, base)
  val closure = Closure(builder, base, enhancements)
  val finding = Finding(builder, enhancements)
  val operators = Operators(builder)
  val sourceSpecific = SourceSpecific(builder, base, enhancements)
  val tagsAndLocation = TagsAndLocation(builder, base, enhancements)
}

object CpgSchema {
  val instance: Schema = {
    val builder = new SchemaBuilder("io.shiftleft.codepropertygraph.generated")
    new CpgSchema(builder)
    builder.build
  }
}
