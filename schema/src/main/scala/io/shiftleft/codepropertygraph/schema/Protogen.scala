package io.shiftleft.codepropertygraph.schema

import overflowdb.codegen.ProtoGen
import overflowdb.schema.{ProtoOptions, SchemaBuilder}

import java.io.File

object Protogen extends App {
  val outputDir =
    args.headOption.map(new File(_)).getOrElse(throw new AssertionError("please pass outputDir as first parameter"))

  val builder = new SchemaBuilder("io.shiftleft.codepropertygraph.generated")
  builder.protoOptions(
    ProtoOptions(
      pkg = "cpg",
      javaOuterClassname = "Cpg",
      javaPackage = "io.shiftleft.proto.cpg",
      goPackage = "github.com/ShiftLeftSecurity/proto/cpg",
      csharpNamespace = "io.shiftleft.proto.cpg",
      uncommonProtoEnumNameMappings = Map(
        "Languages" -> "LANGUAGES",
        "ControlStructureTypes" -> "CONTROL_STRUCTURE_TYPES",
        "Frameworks" -> "FRAMEWORKS",
      )
    ))
  val cpgSchema = new CpgSchema(builder)

  new ProtoGen(builder.build).run(outputDir)
}
