package io.shiftleft.codepropertygraph.schema

import overflowdb.codegen.CodeGen
import overflowdb.schema.SchemaBuilder

import java.io.File

/**
  * Schema for the base code property graph
  * Language modules are required to produce graphs adhering to this schema
  */
object CpgSchema extends App {
  val outputDir = args.headOption.map(new File(_)).getOrElse(
    throw new AssertionError("please pass outputDir as first parameter"))
  val builder = new SchemaBuilder("io.shiftleft.codepropertygraph.generated")

  // the foundation
  val base = Base(builder)
  val enhancements = Enhancements(builder, base)
  val javaSpecific = JavaSpecific(builder, base, enhancements)

  // everything else
  val closure = Closure(builder, base, enhancements)
  val dependency = Dependency(builder, base)
  val deprecated = Deprecated(base)
  val dom = Dom(builder, base, enhancements, javaSpecific)
  val enhancementsInternal = EnhancementsInternal(builder, base, enhancements, javaSpecific)
  val finding = Finding(builder, enhancements)
  val operators = Operators(builder)
  val sourceSpecific = SourceSpecific(builder, base)
  val splitting = Splitting(builder, enhancements)
  val tagsAndLocation = TagsAndLocation(builder, base, enhancements)

  new CodeGen(builder.build).run(outputDir)
}
