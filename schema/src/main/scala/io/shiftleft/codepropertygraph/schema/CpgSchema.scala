package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Schema, SchemaBuilder}

class CpgSchema(builder: SchemaBuilder) {

  val base = Base(builder)
  val operators = Operators(builder)

  val metaData = MetaData(builder, base)
  val namespaces = Namespace(builder, base)

  val typeDecl = Type(builder, base)
  val method = Method(builder, base, typeDecl)
  val fs = FileSystem(builder, base, namespaces, method, typeDecl)
  val ast = Ast(builder, base, namespaces, method, typeDecl, fs)

  val callGraph = CallGraph(builder, method, ast)
  val cfg = Cfg(builder, base, method, ast)
  val dominators = Dominators(builder, method, ast)
  val pdg = Pdg(builder, method, ast)

  val shortcuts = Shortcuts(builder, base, method, ast, typeDecl, fs)

  val sourceSpecific = Comment(builder, base, ast, fs)
  val closure = Closure(builder, base, method, ast, callGraph)
  val tagsAndLocation = TagsAndLocation(builder, base, typeDecl, method, ast, fs, cfg)
  val finding = Finding(builder, base)
  val protoSerialize = ProtoSerialize(builder, ast)
}

object CpgSchema {
  val instance: Schema = {
    val builder = new SchemaBuilder("io.shiftleft.codepropertygraph.generated")
    new CpgSchema(builder)
    builder.build
  }
}
