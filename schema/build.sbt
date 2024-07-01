name := "codepropertygraph-schema"

// TODO change org back to io.joern, only changed temporarily so I can debug something on jenkins
libraryDependencies += "com.michaelpollmeier" %% "flatgraph-domain-classes-generator" % Versions.flatgraph

lazy val generatedSrcDir = settingKey[File]("root for generated sources - we want to check those in")
enablePlugins(FlatgraphCodegenSbtPlugin)
generateDomainClasses/classWithSchema := "io.shiftleft.codepropertygraph.schema.CpgSchema$"
generateDomainClasses/fieldName       := "instance"
generateDomainClasses/outputDir       := (Projects.domainClasses / generatedSrcDir).value

val generateProtobuf = taskKey[File]("generate protobuf definitions: cpg.proto")
generateProtobuf := Def.taskDyn {
  val outputRoot       = target.value / "protos"
  val outputFile       = outputRoot / "cpg.proto"
  val currentSchemaMd5 = FileUtils.md5(sourceDirectory.value, file("schema/build.sbt"))

  if (outputRoot.exists && lastSchemaMd5 == Some(currentSchemaMd5)) {
    Def.task {
      // inputs did not change, don't regenerate
      outputFile
    }
  } else {
    Def.task {
      FileUtils.deleteRecursively(outputRoot)
      val invoked =
        (Compile / runMain).toTask(s" io.shiftleft.codepropertygraph.schema.Protogen schema/target/protos").value
      lastSchemaMd5(currentSchemaMd5)
      outputFile
    }
  }
}.value

lazy val schemaMd5File                 = file("target/schema-src.md5")
def lastSchemaMd5: Option[String]      = scala.util.Try(IO.read(schemaMd5File)).toOption
def lastSchemaMd5(value: String): Unit = IO.write(schemaMd5File, value)
