name := "codepropertygraph"
inThisBuild(
  List(
    organization := "io.shiftleft",
    scalaVersion := "2.12.8", // do not upgrade until https://github.com/lihaoyi/Ammonite/issues/1009 is resolved
    resolvers ++= Seq(Resolver.mavenLocal, Resolver.bintrayRepo("shiftleft", "maven"), "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/public"),
    packageDoc / publishArtifact := true,
    packageSrc / publishArtifact := true,
    bintrayVcsUrl := Some("https://github.com/ShiftLeftSecurity/codepropertygraph"),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
  )
)

name := "codepropertygraph"
publish / skip := true

// parsed by project/Utils.scala

val fuzzyc2cpgVersion = "1.1.8"

lazy val codepropertygraph = Projects.codepropertygraph
lazy val protoBindings = Projects.protoBindings
lazy val semanticcpg = Projects.semanticcpg
lazy val dataflowengine = Projects.dataflowengine
lazy val cpgserver = Projects.cpgserver
lazy val cpgvalidator = Projects.cpgvalidator
lazy val cpg2overflowdb = Projects.cpg2overflowdb
lazy val console = Projects.console
lazy val queries = Projects.queries

ThisBuild/scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-Xfatal-warnings",
  "-language:implicitConversions")
ThisBuild/compile/javacOptions ++= Seq("-g") //debug symbols

Global/onChangedBuildSource := ReloadOnSourceChanges
onLoad in Global := {
  assert(GitLFSUtils.isGitLFSEnabled(), "You need to install git-lfs and run 'git lfs pull'")
  (onLoad in Global).value
}
