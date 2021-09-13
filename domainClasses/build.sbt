name := "codepropertygraph-domain-classes"

libraryDependencies += "io.shiftleft" %% "overflowdb-traversal" % Versions.overflowdb
dependsOn(Projects.schema)

// TODO move to sbt plugin
val classWithSchema = settingKey[String]("")
val fieldName = settingKey[String]("")
val generateDomainClasses2 = taskKey[Seq[File]]("generate overflowdb domain classes for our schema")

classWithSchema := "io.shiftleft.codepropertygraph.schema.CpgSchema$"
fieldName := "instance"

// TODO move to sbt plugin
generateDomainClasses2 := Def.taskDyn {
  // val outputRoot = sourceManaged.value / "odb-codegen"
  val outputRoot = target.value / "overflowdb-codegen"
  val classWithSchema_ = classWithSchema.value
  val fieldName_ = fieldName.value
  // TODO add caching back in
  Def.task {
    FileUtils.deleteRecursively(outputRoot)
    (Compile/runMain).toTask(s" overflowdb.codegen.Main --classWithSchema=$classWithSchema_ --field=$fieldName_ --out=$outputRoot").value
    FileUtils.listFilesRecursively(outputRoot)
  }
}.value

Compile / sourceGenerators += generateDomainClasses2

/* generated sources occasionally have some warnings.. 
 * we're trying to minimise them on a best effort basis, but don't want
 * to fail the build because of them
 */
Compile / scalacOptions -= "-Xfatal-warnings" 
