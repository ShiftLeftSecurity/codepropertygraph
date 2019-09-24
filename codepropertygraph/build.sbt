name := "codepropertygraph"

dependsOn(Projects.protoBindings)

libraryDependencies ++= Seq(
  "io.shiftleft" % "overflowdb-tinkerpop3" % "0.18",
  "com.michaelpollmeier" %% "gremlin-scala" % "3.3.4.17",
  "com.google.guava" % "guava" % "21.0",
  "org.apache.commons" % "commons-lang3" % "3.5",
  "commons-io" % "commons-io" % "2.5",
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
  "com.jsuereth" %% "scala-arm" % "2.0",
  "com.github.scopt" %% "scopt" % "3.7.0",
  "org.apache.logging.log4j" % "log4j-api" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.11.2" % Runtime, //redirect tinkerpop's slf4j logging to log4j
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)

import java.io.File
import scala.sys.process._

lazy val mergeSchemaTask = taskKey[Unit]("Merge schemas")
mergeSchemaTask := {
  val currentMd5 = FileUtils.md5(new File("codepropertygraph/src/main/resources/schemas"))
  if (MergeSchemaTaskGlobalState.lastMd5 == currentMd5) {
    println("schemas unchanged, no need to merge them again")
  } else {
    val mergeCmd = "codepropertygraph/codegen/src/main/python/mergeSchemas.py"
    val mergeResult = Seq(mergeCmd).!
    if (mergeResult == 0)
      println("successfully merged schemas to generate cpg.json")
    else
      throw new Exception(s"problem when calling $mergeCmd. exitCode was $mergeResult")
  }
  MergeSchemaTaskGlobalState.lastMd5 = currentMd5
}

Compile / sourceGenerators += Def.task {
  val currentMd5 = FileUtils.md5(List(
    new File("codepropertygraph/codegen/src/main"),
    new File("project/DomainClassCreator.scala"),
    new File("codepropertygraph/src/main/resources/schemas")))
  val outputRoot = new File(sourceManaged.in(Compile).value.getAbsolutePath + "/io/shiftleft/codepropertygraph/generated")

  if (!outputRoot.exists || CodeGenGlobalState.lastMd5 != currentMd5) {
    val schemaFile = "codepropertygraph/src/main/resources/cpg.json"
    println(s"generating domain classes from $schemaFile")
    val basePackage = "io.shiftleft.codepropertygraph.generated"
    val outputDir = (Compile / sourceManaged).value
    new DomainClassCreator(schemaFile, basePackage).run(outputDir)

    // TODO: port python to jpython, scala or java to avoid system call and pass values in/out
    val cmd = "codepropertygraph/codegen/src/main/python/generateJava.py"
    val result = Seq(cmd).!
    if (result == 0) {
      val generateJavaTmpOutput = better.files.File("codepropertygraph/target/generateJava")
      generateJavaTmpOutput.children.foreach(
        _.copyToDirectory(better.files.File(outputRoot.toPath))(copyOptions = better.files.File.CopyOptions(overwrite = true)))
      println(s"successfully generated java files in $outputRoot")
    } else
      throw new Exception(s"problem when calling $cmd. exitCode was $result")
  } else {
    println("no need to regenerate domain classes")
  }
  CodeGenGlobalState.lastMd5 = currentMd5

  FileUtils.listFilesRecursively(outputRoot)
}.taskValue

(Compile / sourceGenerators) := (Compile / sourceGenerators).value.map(x => x.dependsOn(mergeSchemaTask.taskValue))

lazy val generateProtobuf = taskKey[File]("generate cpg.proto")
generateProtobuf := {
  val output = better.files.File((resourceManaged.in(Compile).value / "cpg.proto").toPath)

  val currentMd5 = FileUtils.md5(List(
    new File("codepropertygraph/codegen/src/main"),
    new File("codepropertygraph/src/main/resources/schemas")))
  if (!output.exists || GenerateProtobufTaskGlobalState.lastMd5 != currentMd5) {
    // TODO: port python to jpython, scala or java to avoid system call and pass values in/out
    val cmd = "codepropertygraph/codegen/src/main/python/generateProtobuf.py"
    val result = Seq(cmd).!
    val scriptOutputFile = better.files.File("codepropertygraph/target/cpg.proto")
    if (result == 0 && scriptOutputFile.exists) {
      output.createIfNotExists(createParents = true)
      scriptOutputFile.copyTo(output)(better.files.File.CopyOptions(overwrite = true))
      println(s"successfully wrote protobuf to $result")
    } else throw new Exception(s"problem when calling $cmd. exitCode was $result")
  } else {
    println("no need to regenerate protobuf")
  }
  GenerateProtobufTaskGlobalState.lastMd5 = currentMd5
  output.toJava
}
generateProtobuf := generateProtobuf.dependsOn(mergeSchemaTask).value

// note: this is only invoked on `package`, `publish` etc. since it's not needed for `compile`
Compile / resourceGenerators += generateProtobuf.taskValue.map(Seq(_))

(Compile / resourceGenerators) := (Compile / resourceGenerators).value.map(x => x.dependsOn(mergeSchemaTask.taskValue))

import Path._
mappings in (Compile, packageSrc) ++= { // publish generated sources
  val srcs = (managedSources in Compile).value
  val sdirs = (managedSourceDirectories in Compile).value
  val base = baseDirectory.value
  (((srcs --- sdirs --- base) pair (relativeTo(sdirs) | relativeTo(base) | flat)) toSeq)
}
