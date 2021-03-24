name := "codepropertygraph-schema"

libraryDependencies += "io.shiftleft" %% "overflowdb-codegen" % "ef8fa7b8cc3d0d9305025f97726e525caa37d1b2"

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
