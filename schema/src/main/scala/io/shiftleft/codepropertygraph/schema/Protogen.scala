package io.shiftleft.codepropertygraph.schema

import flatgraph.codegen.ProtoGen
import flatgraph.schema.{ProtoOptions, SchemaBuilder}

import java.nio.file.{Path, Paths}

object Protogen {
  def main(args: Array[String]): Unit = {
    val outputDir =
      args.headOption.map(Paths.get(_)).getOrElse(throw new AssertionError("please pass outputDir as first parameter"))

    val builder = new SchemaBuilder(domainShortName = "Cpg", basePackage = "io.shiftleft.codepropertygraph.generated")

    builder.protoOptions(
      ProtoOptions(
        pkg = "cpg",
        javaOuterClassname = "Cpg",
        javaPackage = "io.shiftleft.proto.cpg",
        goPackage = "github.com/ShiftLeftSecurity/proto/cpg",
        csharpNamespace = "io.shiftleft.proto.cpg",
        uncommonProtoEnumNameMappings = Map(
          "Languages"             -> "LANGUAGES",
          "ControlStructureTypes" -> "CONTROL_STRUCTURE_TYPES",
          "Frameworks"            -> "FRAMEWORKS"
        )
      )
    )
    new CpgSchema(builder)

    new ProtoGen(builder.build).run(outputDir)
  }
}
