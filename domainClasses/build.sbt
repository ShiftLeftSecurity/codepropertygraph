name := "codepropertygraph-domain-classes"

libraryDependencies += "io.shiftleft" %% "overflowdb-traversal" % Versions.overflowdb

val generateDomainClasses = taskKey[Seq[File]]("generate overflowdb domain classes for our schema")

Compile / sourceGenerators += Projects.schema / generateDomainClasses

// Compile / sourceGenerators += Def.task {
//   val currentMd5 = FileUtils.md5(List(new File("codepropertygraph/codegen/src/main"), schemaFile))
//   val outputRoot = new File(sourceManaged.in(Compile).value.getAbsolutePath + "/io/shiftleft/codepropertygraph/generated")

//   if (!outputRoot.exists || CodeGenGlobalState.lastMd5 != currentMd5) {
//     println(s"generating domain classes from $schemaFile")
//     val basePackage = "io.shiftleft.codepropertygraph.generated"
//     val outputDir = (Compile / sourceManaged).value
//     val schema = new CpgSchema
//     new overflowdb.codegen.CodeGen(schema).run(outputDir)
//   } else {
//     println("no need to regenerate domain classes")
//   }
//   CodeGenGlobalState.lastMd5 = currentMd5
//   FileUtils.listFilesRecursively(outputRoot)
// }.taskValue
