name := "codepropertygraph-schema"

libraryDependencies += "io.shiftleft" %% "overflowdb-codegen" % "436e7b7b49ba04083e1c63befea31d6bdd32c4f5"

val generateDomainClasses = taskKey[Seq[File]]("generate overflowdb domain classes for our schema")

generateDomainClasses := Def.taskDyn {
  val outputRoot = target.value / "odb-codegen"
  val currentMd5 = FileUtils.md5(sourceDirectory.value)

  if (!outputRoot.exists || CodeGenGlobalState.lastMd5 != currentMd5) {
    Def.task {
      FileUtils.deleteRecursively(outputRoot)
      val invoked = (Compile/runMain).toTask(s" io.shiftleft.codepropertygraph.schema.Codegen schema/target/odb-codegen").value
      CodeGenGlobalState.lastMd5 = currentMd5
      FileUtils.listFilesRecursively(outputRoot)
    }
  } else {
    Def.task {
      CodeGenGlobalState.lastMd5 = currentMd5
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
