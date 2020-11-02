name := "codepropertygraph"

dependsOn(Projects.protoBindings, Projects.domainClasses)

libraryDependencies ++= Seq(
  "com.github.pathikrit"     %% "better-files"         % "3.8.0",
  "org.scala-lang.modules"   %% "scala-java8-compat"   % "0.9.0",
  "org.slf4j"                %  "slf4j-api"            % "1.7.30",
  "org.scalatest"            %% "scalatest"            % Versions.scalatest % Test
)

lazy val mergeSchemaTask = taskKey[File]("Merge schemas")

lazy val generateProtobuf = taskKey[File]("generate cpg.proto")
generateProtobuf := {
  import scala.sys.process._
  val output = better.files.File((resourceManaged.in(Compile).value / "cpg.proto").toPath)
  val schemaFile = (Projects.schema/mergeSchemaTask).value
  val currentMd5 = FileUtils.md5(List(new File("codepropertygraph/codegen/src/main"), schemaFile))
  if (!output.exists || GenerateProtobufTaskGlobalState.lastMd5 != currentMd5) {
    // TODO: port python to jpython, scala or java to avoid system call and pass values in/out
    val cmd = "codepropertygraph/codegen/src/main/python/generateProtobuf.py"
    val result = Seq(cmd).!
    val scriptOutputFile = better.files.File("codepropertygraph/target/cpg.proto")
    if (result == 0 && scriptOutputFile.exists) {
      output.createIfNotExists(createParents = true)
      scriptOutputFile.copyTo(output)(better.files.File.CopyOptions(overwrite = true))
      println(s"successfully wrote protobuf to $scriptOutputFile")
    } else throw new Exception(s"problem when calling $cmd. exitCode was $result")
  } else {
    println("no need to regenerate protobuf")
  }
  GenerateProtobufTaskGlobalState.lastMd5 = currentMd5
  output.toJava
}

// note: this is only invoked on `package`, `publish` etc. since it's not needed for `compile`
Compile / resourceGenerators += generateProtobuf.taskValue.map(Seq(_))

import Path._
mappings in (Compile, packageSrc) ++= { // publish generated sources
  val srcs = (managedSources in Compile).value
  val sdirs = (managedSourceDirectories in Compile).value
  val base = baseDirectory.value
  (srcs --- sdirs --- base) pair (relativeTo(sdirs) | relativeTo(base) | flat)
}
