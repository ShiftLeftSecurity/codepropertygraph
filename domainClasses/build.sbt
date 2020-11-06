name := "codepropertygraph-domain-classes"

libraryDependencies += "io.shiftleft" %% "overflowdb-traversal" % Versions.overflowdb
dependsOn(Projects.schema)

lazy val mergeSchemaTask = taskKey[File]("Merge schemas")

Compile / sourceGenerators += Def.task {
  val schemaFile = (Projects.schema/mergeSchemaTask).value
  val currentMd5 = FileUtils.md5(List(new File("codepropertygraph/codegen/src/main"), schemaFile))
  val outputRoot = new File(sourceManaged.in(Compile).value.getAbsolutePath + "/io/shiftleft/codepropertygraph/generated")

  if (!outputRoot.exists || CodeGenGlobalState.lastMd5 != currentMd5) {
    println(s"generating domain classes from $schemaFile")
    val basePackage = "io.shiftleft.codepropertygraph.generated"
    val outputDir = (Compile / sourceManaged).value
    new overflowdb.codegen.CodeGen(schemaFile.getAbsolutePath, basePackage).run(outputDir)
  } else {
    println("no need to regenerate domain classes")
  }
  CodeGenGlobalState.lastMd5 = currentMd5

  FileUtils.listFilesRecursively(outputRoot)
}.taskValue
