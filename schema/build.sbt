name := "codepropertygraph-schema"

libraryDependencies += "io.shiftleft" %% "overflowdb-traversal" % "1.19"

import better.files.FileExtensions

lazy val mergeSchemaTask = taskKey[Unit]("Merge schemas")
mergeSchemaTask := {
  val schemasDir = new File("codepropertygraph/src/main/resources/schemas")
  val currentMd5 = FileUtils.md5(schemasDir)
  if (MergeSchemaTaskGlobalState.lastMd5 == currentMd5) {
    println("schemas unchanged, no need to merge them again")
  } else {
    val schemaFiles = schemasDir.listFiles.toSeq
    val mergedSchema = overflowdb.codegen.SchemaMerger.mergeCollections(schemaFiles)
    val outputFile = new File("codepropertygraph/src/main/resources/cpg.json")
    mergedSchema.toScala.copyTo(outputFile.toScala, overwrite = true)
    println(s"successfully merged schemas into $outputFile")
  }
  MergeSchemaTaskGlobalState.lastMd5 = currentMd5
}

Compile / sourceGenerators += Def.task {
  val currentMd5 = FileUtils.md5(List(
    new File("codepropertygraph/codegen/src/main"),
    new File("codepropertygraph/src/main/resources/schemas")))
  val outputRoot = new File(sourceManaged.in(Compile).value.getAbsolutePath + "/io/shiftleft/codepropertygraph/generated")

  if (!outputRoot.exists || CodeGenGlobalState.lastMd5 != currentMd5) {
    val schemaFile = "codepropertygraph/src/main/resources/cpg.json"
    println(s"generating domain classes from $schemaFile")
    val basePackage = "io.shiftleft.codepropertygraph.generated"
    val outputDir = (Compile / sourceManaged).value
    new overflowdb.codegen.CodeGen(schemaFile, basePackage).run(outputDir)
  } else {
    println("no need to regenerate domain classes")
  }
  CodeGenGlobalState.lastMd5 = currentMd5

  FileUtils.listFilesRecursively(outputRoot)
}.taskValue

(Compile / sourceGenerators) := (Compile / sourceGenerators).value.map(x => x.dependsOn(mergeSchemaTask.taskValue))
