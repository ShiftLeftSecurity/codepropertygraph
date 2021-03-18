name := "codepropertygraph-schema"

libraryDependencies += "io.shiftleft" %% "overflowdb-codegen" % "cfb6ef4560cbf65394e8f30c3bd0db5e0e789599"

val generateDomainClasses = taskKey[Seq[File]]("generate overflowdb domain classes for our schema")

generateDomainClasses := Def.taskDyn {
  val schemaChecksum = FileUtils.md5(new File("schema/src/main"))
  val outputRoot = target.value / "odb-codegen"

  if (!outputRoot.exists || CodeGenGlobalState.lastMd5 != schemaChecksum) {
    val generated = (Compile/runMain).toTask(s" io.shiftleft.codepropertygraph.schema.CpgSchema schema/target/odb-codegen").value
  } else {
    println("no need to regenerate domain classes")
  }
  CodeGenGlobalState.lastMd5 = schemaChecksum
  Def.task(FileUtils.listFilesRecursively(outputRoot))
}.value
