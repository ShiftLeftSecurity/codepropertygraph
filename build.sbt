name := "codepropertygraph"

// parsed by project/Versions.scala, updated by updateDependencies.sh
val overflowdbVersion = "1.62"

inThisBuild(
  List(
    organization := "io.shiftleft",
    scalaVersion := "2.13.5",
    // don't upgrade to 2.13.6 until https://github.com/com-lihaoyi/Ammonite/issues/1182 is resolved
    resolvers ++= Seq(
      Resolver.mavenLocal,
      "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/public"
    ),
  )
)

name := "codepropertygraph"
publish / skip := true

// lazy val codepropertygraph = Projects.codepropertygraph
lazy val schema = Projects.schema
// lazy val domainClasses = Projects.domainClasses
// lazy val protoBindings = Projects.protoBindings
// lazy val schema2json = Projects.schema2json

Global / onChangedBuildSource := ReloadOnSourceChanges
