name := "codepropertygraph-schema"

libraryDependencies += "io.shiftleft" %% "overflowdb-codegen" % "2.20"

Compile / generateDomainClasses / classWithSchema := "io.shiftleft.codepropertygraph.schema.CpgSchema$"
Compile / generateDomainClasses / fieldName := "instance"

val generateProtobuf = taskKey[File]("generate protobuf definitions: cpg.proto")
generateProtobuf := Def.taskDyn {
  val outputRoot = target.value / "protos"
  val outputFile = outputRoot / "cpg.proto"
  val currentSchemaMd5 = FileUtils.md5(sourceDirectory.value, file("schema/build.sbt"))

  if (outputRoot.exists && lastSchemaMd5 == Some(currentSchemaMd5)) {
    Def.task {
      outputFile
    }
  } else {
    Def.task {
      FileUtils.deleteRecursively(outputRoot)
      val invoked = (Compile/runMain).toTask(s" io.shiftleft.codepropertygraph.schema.Protogen schema/target/protos").value
      lastSchemaMd5(currentSchemaMd5)
      outputFile
    }
  }
}.value

lazy val schemaMd5File = file("target/schema-src.md5")
def lastSchemaMd5: Option[String] = scala.util.Try(IO.read(schemaMd5File)).toOption
def lastSchemaMd5(value: String): Unit = IO.write(schemaMd5File, value)
