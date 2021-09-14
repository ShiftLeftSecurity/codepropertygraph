name := "codepropertygraph-schema"

libraryDependencies += "io.shiftleft" %% "overflowdb-codegen" % "1.98+9-7bcb6404"

Compile / generateDomainClasses / classWithSchema := "io.shiftleft.codepropertygraph.schema.CpgSchema$"
Compile / generateDomainClasses / fieldName := "instance"

// val generateDomainClasses2 = taskKey[Seq[File]]("generate overflowdb domain classes for our schema")
// generateDomainClasses2 := Def.taskDyn {
//   val outputRoot = sourceManaged.value / "overflowdb-codegen"
//   val classWithSchema_ = (Compile/generateDomainClasses/classWithSchema).value
//   val fieldName_ = (Compile/generateDomainClasses/fieldName).value
//   val currentSchemaMd5 = FileUtils.md5(sourceDirectory.value, baseDirectory.value/"build.sbt")

//   if (outputRoot.exists && lastSchemaMd5 == Some(currentSchemaMd5)) {
//     Def.task {
//       FileUtils.listFilesRecursively(outputRoot)
//     }
//   } else {
//     Def.task {
//       FileUtils.deleteRecursively(outputRoot)
//       (Compile/runMain).toTask(s" overflowdb.codegen.Main --classWithSchema=$classWithSchema_ --field=$fieldName_ --out=$outputRoot").value
//       lastSchemaMd5(currentSchemaMd5)
//       FileUtils.listFilesRecursively(outputRoot)
//     }
//   }
// }.value

// TODO move to sbt plugin
// lazy val schemaMd5File = file("target/schema-src.md5")
// def lastSchemaMd5: Option[String] = scala.util.Try(IO.read(schemaMd5File)).toOption
// def lastSchemaMd5(value: String): Unit = IO.write(schemaMd5File, value)
