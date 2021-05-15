package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Schema, SchemaBuilder}

/**
  * Schema for the base code property graph
  * Language modules are required to produce graphs adhering to this schema
  */
class CpgSchema(builder: SchemaBuilder) {
  // the foundation
  val common = CommonProperties(builder)
  val base = Base(builder, common)
  val metaData = MetaData(builder, common)
  val typeDecl = TypeDecl(builder, base, common)

  val method = Method(builder, base, common, typeDecl)
  val methodBody = MethodBody(builder, base, common, method, typeDecl)

  val callGraph = CallGraph(builder, method, methodBody)
  val cfg = ControlFlowGraph(builder, base, method, methodBody, common)
  val dominators = Dominators(builder, method, methodBody)
  val pdg = Pdg(builder, method, methodBody)

  val enhancements = Enhancements(builder, base, method, methodBody, typeDecl, common)

  // everything else
  val protoSerialize = ProtoSerialize(builder, methodBody)
  val closure = Closure(builder, base, method, methodBody, callGraph)
  val finding = Finding(builder, common)
  val operators = Operators(builder)
  val sourceSpecific = Comment(builder, base, enhancements, common)
  val tagsAndLocation = TagsAndLocation(builder, base, typeDecl, method, methodBody, enhancements, common)
}

object CpgSchema {
  val instance: Schema = {
    val builder = new SchemaBuilder("io.shiftleft.codepropertygraph.generated")
    new CpgSchema(builder)
    builder.build
  }
}
