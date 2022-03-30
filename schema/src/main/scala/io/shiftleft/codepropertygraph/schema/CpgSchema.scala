package io.shiftleft.codepropertygraph.schema

import overflowdb.schema.{Schema, SchemaBuilder}

class CpgSchema(builder: SchemaBuilder) {

  val base       = Base(builder)
  val fs         = FileSystem(builder, base)
  val namespaces = Namespace(builder, base, fs)

  val operators     = Operators(builder)
  val configuration = Configuration(builder, base)
  val metaData      = MetaData(builder, base)

  val typeSchema = Type(builder, base, fs)
  val method     = Method(builder, base, typeSchema, fs)
  val ast        = Ast(builder, base, namespaces, method, typeSchema, fs)
  val callGraph  = CallGraph(builder, method, ast)

  val cfg        = Cfg(builder, method, ast)
  val dominators = Dominators(builder, method, ast)
  val pdg        = Pdg(builder, method, ast)

  val shortcuts = Shortcuts(builder, base, method, ast, typeSchema, fs)

  val sourceSpecific  = Comment(builder, ast, fs)
  val tagsAndLocation = TagsAndLocation(builder, base, typeSchema, method, ast, fs, callGraph)
  val binding         = Binding(builder, base, typeSchema, method, callGraph)
  val annotation      = Annotation(builder, base, method, typeSchema, ast, shortcuts)
  val finding         = Finding(builder, base)
  val hidden          = Hidden(builder, base, method, typeSchema, ast, cfg, fs, callGraph)
  val protoSerialize  = ProtoSerialize(builder, ast)
}

object CpgSchema {
  val instance: Schema = {
    val builder = new SchemaBuilder(
      domainShortName = "Cpg",
      basePackage = "io.shiftleft.codepropertygraph.generated",
      additionalTraversalsPackages = Seq("io.shiftleft")
    )
    new CpgSchema(builder)
    builder.build
  }

  object PropertyDefaults {
    val String           = "<empty>"
    val Boolean: Boolean = false
    val Byte: Byte       = -1
    val Short: Short     = -1
    val Int: Int         = -1
    val Long: Long       = -1
    val Float            = 0f
    val Double           = 0d
    val Char: Char       = '?'
  }
}
