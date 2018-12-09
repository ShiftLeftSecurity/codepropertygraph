name := "codepropertygraph"

libraryDependencies ++= Seq(
  "io.shiftleft" % "tinkergraph-gremlin" % "3.3.4.1",
  "com.michaelpollmeier" %% "gremlin-scala" % "3.3.4.13",
  "com.google.guava" % "guava" % "21.0",
  "org.apache.commons" % "commons-lang3" % "3.5",
  "org.apache.logging.log4j" % "log4j-api" % "2.11.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.11.0",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)

lazy val mergeSchemaTask = taskKey[Unit]("Merge schemas")
mergeSchemaTask := {
  import scala.sys.process._
  val mergeCmd = "codepropertygraph/codegen/src/main/python/mergeSchemas.py"
  val mergeResult = Seq(mergeCmd).!
  if (mergeResult == 0)
    println("successfully merged schemas to generate cpg.json")
  else
    throw new Exception(s"problem when calling $mergeCmd. exitCode was $mergeResult")
}

Compile / sourceGenerators += Def.task {
  val javaDefs = { // TODO: port python to jpython, scala or java to avoid system call and pass values in/out
    import scala.sys.process._

    val cmd = "codepropertygraph/codegen/src/main/python/generateJava.py"
    val result = Seq(cmd).!
    if (result == 0)
      println(s"successfully called $cmd")
    else
      throw new Exception(s"problem when calling $cmd. exitCode was $result")
    new java.io.File(sourceManaged.in(Compile).value.getAbsolutePath +
      "/io/shiftleft/codepropertygraph/generated").getAbsoluteFile.listFiles
  }
  val domainClassFiles = DomainClassCreator.run((Compile / sourceManaged).value)

  domainClassFiles ++ javaDefs
}.taskValue

(Compile / sourceGenerators) := (Compile / sourceGenerators).value.map(x => x.dependsOn(mergeSchemaTask.taskValue))

lazy val generateProtobuf = taskKey[Seq[File]]("generate cpg.proto")
generateProtobuf := {
  // TODO: port python to jpython, scala or java to avoid system call and pass values in/out
  import scala.sys.process._
  val cmd = "codepropertygraph/codegen/src/main/python/generateProtobuf.py"
  val result = Seq(cmd).!
  val file = (resourceManaged in Compile).value / "cpg.proto"
  if (result == 0)
    println(s"successfully called $cmd")
  else
    throw new Exception(s"problem when calling $cmd. exitCode was $result")
  Seq(file)
}
generateProtobuf := generateProtobuf.dependsOn(mergeSchemaTask).value

// note: this is only invoked on `package`, `publish` etc. since it's not needed for `compile`
Compile / resourceGenerators += generateProtobuf.taskValue

(Compile / resourceGenerators) := (Compile / resourceGenerators).value.map(x => x.dependsOn(mergeSchemaTask.taskValue))

import Path._
mappings in (Compile, packageSrc) ++= { // publish generated sources
  val srcs = (managedSources in Compile).value
  val sdirs = (managedSourceDirectories in Compile).value
  val base = baseDirectory.value
  (((srcs --- sdirs --- base) pair (relativeTo(sdirs) | relativeTo(base) | flat)) toSeq)
}
