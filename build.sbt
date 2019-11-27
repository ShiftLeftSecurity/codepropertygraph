name := "codepropertygraph"
inThisBuild(
  List(
    organization := "io.shiftleft",
    scalaVersion := "2.13.1",
    resolvers ++= Seq(
      Resolver.mavenLocal,
      Resolver.bintrayRepo("shiftleft", "maven"),
      Resolver.bintrayRepo("mpollmeier", "maven"),
      "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/public"),
    packageDoc / publishArtifact := true,
    packageSrc / publishArtifact := true,
    bintrayVcsUrl := Some("https://github.com/ShiftLeftSecurity/codepropertygraph"),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
  )
)

name := "codepropertygraph"
publish / skip := true

// parsed by project/Utils.scala
val fuzzyc2cpgVersion = "3deb1efc9747a6e31b66b7f71d959e97c1045f5f"

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
  // "-Xfatal-warnings", // reenable once https://github.com/scala/bug/issues/11457 is fixed (False positive unreachable code warning)
  "-language:implicitConversions")
ThisBuild/compile/javacOptions ++= Seq("-g") //debug symbols

Global/onChangedBuildSource := ReloadOnSourceChanges
onLoad in Global := {
  assert(GitLFSUtils.isGitLFSEnabled(), "You need to install git-lfs and run 'git lfs pull'")
  (onLoad in Global).value
}
