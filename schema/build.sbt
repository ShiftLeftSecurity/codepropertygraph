name := "codepropertygraph-schema"

libraryDependencies += "io.shiftleft" %% "overflowdb-codegen" % "1.63"

scalacOptions += "-Xfatal-warnings"

val generateDomainClasses = taskKey[Seq[File]]("generate overflowdb domain classes for our schema")
generateDomainClasses := Def.taskDyn {
  val outputRoot = target.value / "odb-codegen"
  val currentSchemaMd5 = FileUtils.md5(sourceDirectory.value)

  if (outputRoot.exists && lastSchemaMd5 == Some(currentSchemaMd5)) {
    Def.task {
      lastSchemaMd5(currentSchemaMd5)
      FileUtils.listFilesRecursively(outputRoot)
    }
  } else {
    Def.task {
      FileUtils.deleteRecursively(outputRoot)
      (Compile/runMain).toTask(s" io.shiftleft.codepropertygraph.schema.Codegen schema/target/odb-codegen").value
      lastSchemaMd5(currentSchemaMd5)
      FileUtils.listFilesRecursively(outputRoot)
    }
  }
}.value

val generateProtobuf = taskKey[File]("generate protobuf definitions: cpg.proto")
generateProtobuf := Def.taskDyn {
  val outputRoot = target.value / "protos"
  val outputFile = outputRoot / "cpg.proto"
  val currentMd5 = FileUtils.md5(sourceDirectory.value)

  if (!outputRoot.exists || GenerateProtobufTaskGlobalState.lastMd5 != currentMd5) {
    Def.task {
      FileUtils.deleteRecursively(outputRoot)
      val invoked = (Compile/runMain).toTask(s" io.shiftleft.codepropertygraph.schema.Protogen schema/target/protos").value
      GenerateProtobufTaskGlobalState.lastMd5 = currentMd5
      outputFile
    }
  } else {
    Def.task {
      GenerateProtobufTaskGlobalState.lastMd5 = currentMd5
      outputFile
    }
  }
}.value

lazy val schemaMd5File = file("target/schema-src.md5")
def lastSchemaMd5: Option[String] = scala.util.Try(IO.read(schemaMd5File)).toOption
def lastSchemaMd5(value: String): Unit = IO.write(schemaMd5File, value)
