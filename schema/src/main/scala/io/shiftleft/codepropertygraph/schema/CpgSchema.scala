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

  val fs = FileSystem(builder, base, common, method, typeDecl)
  val ast = Ast(builder, base, method, common, typeDecl, fs)
  val callGraph = CallGraph(builder, method, ast)
  val cfg = ControlFlowGraph(builder, base, method, ast, common)
  val dominators = Dominators(builder, method, ast)
  val pdg = Pdg(builder, method, ast)

  val enhancements = Enhancements(builder, method, ast, typeDecl, fs)

  // everything else
  val protoSerialize = ProtoSerialize(builder, ast)
  val closure = Closure(builder, method, ast, callGraph)
  val finding = Finding(builder, common)
  val operators = Operators(builder)
  val sourceSpecific = Comment(builder, common, ast, fs)
  val tagsAndLocation = TagsAndLocation(builder, base, typeDecl, method, ast, fs, common)
}

object CpgSchema {
  val instance: Schema = {
    val builder = new SchemaBuilder("io.shiftleft.codepropertygraph.generated")
    new CpgSchema(builder)
    builder.build
  }
}
