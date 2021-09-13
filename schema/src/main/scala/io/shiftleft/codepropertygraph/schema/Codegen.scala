package io.shiftleft.codepropertygraph.schema

import overflowdb.codegen.CodeGen
import overflowdb.schema.Schema
import java.io.File

object Codegen extends App {
  val outputDir =
    args.headOption.map(new File(_)).getOrElse(throw new AssertionError("please pass outputDir as first parameter"))

  new CodeGen(CpgSchema.instance).run(outputDir)
}

object Codegen2 extends App {
  val schemaStaticClass = "io.shiftleft.codepropertygraph.schema.CpgSchema$"
  val fieldName = "instance"
  val clazz = getClass.getClassLoader.loadClass(schemaStaticClass)
  val field = clazz.getDeclaredField(fieldName)
  assert(field.getType == classOf[Schema], s"field $fieldName in class `$schemaStaticClass` must be of type `overflowdb.schema.Schema`, but actually is of type `${field.getType}`")
  field.setAccessible(true)
  val schema = field.get().asInstanceOf[Schema]
  println(schema)
}