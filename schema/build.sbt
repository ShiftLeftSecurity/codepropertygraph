name := "codepropertygraph-schema"

import better.files.FileExtensions

lazy val mergeSchemaTask = taskKey[File]("Merge schemas")
mergeSchemaTask := {
  // merge schemas into one cpg.json
  val schemasDir = new File("schema/src/main/resources/schemas")
  val currentMd5 = FileUtils.md5(schemasDir)
  val outputRoot = new File(sourceManaged.in(Compile).value.getAbsolutePath)
  outputRoot.mkdirs
  val outputFile = outputRoot / "cpg.json"
  if (outputFile.exists && MergeSchemaTaskGlobalState.lastMd5 == currentMd5) {
    println("schemas unchanged, no need to merge them again")
  } else {
    val schemaFiles = schemasDir.listFiles.toSeq
    val mergedSchema = overflowdb.codegen.SchemaMerger.mergeCollections(schemaFiles)
    mergedSchema.toScala.copyTo(outputFile.toScala, overwrite = true)
    println(s"successfully merged schemas into $outputFile")
  }
  MergeSchemaTaskGlobalState.lastMd5 = currentMd5
  outputFile
}

Compile / resourceGenerators += Def.task {
  Seq(mergeSchemaTask.value)
}.taskValue
