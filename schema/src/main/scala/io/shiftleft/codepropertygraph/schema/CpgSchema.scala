package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Schema, SchemaBuilder}

class CpgSchema(builder: SchemaBuilder) {

  val base = Base(builder)
  val operators = Operators(builder)

  val metaData = MetaData(builder, base)
  val namespaces = Namespaces(builder, base)

  val typeDecl = TypeDecl(builder, base)
  val method = Method(builder, base, typeDecl)
  val fs = FileSystem(builder, base, namespaces, method, typeDecl)
  val ast = Ast(builder, base, namespaces, method, typeDecl, fs)

  val callGraph = CallGraph(builder, method, ast)
  val cfg = ControlFlowGraph(builder, base, method, ast)
  val dominators = Dominators(builder, method, ast)
  val pdg = Pdg(builder, method, ast)

  val shortcuts = Shortcuts(builder, base, method, ast, typeDecl, fs)

  val sourceSpecific = Comment(builder, base, ast, fs)
  val closure = Closure(builder, base, method, ast, callGraph)
  val tagsAndLocation = TagsAndLocation(builder, base, typeDecl, method, ast, fs)
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
