package io.shiftleft.codepropertygraph.schema

import overflowdb.codegen.CodeGen2

import java.io.File

object Codegen extends App {
  val outputDir =
    args.headOption.map(new File(_)).getOrElse(throw new AssertionError("please pass outputDir as first parameter"))

  new CodeGen2(CpgSchema.instance).run(outputDir)
}
