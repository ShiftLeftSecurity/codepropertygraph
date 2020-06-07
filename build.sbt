name := "codepropertygraph"
inThisBuild(
  List(
    organization := "io.shiftleft",
    /* n.b. skip 2.13.1, it has a regression https://github.com/scala/bug/issues/11754,
     * which is fixed in https://github.com/scala/scala/pull/8447, i.e. we can upgrade
     * to 2.13.2 once that's released */
    scalaVersion := "2.13.0",
    resolvers ++= Seq(
      Resolver.mavenLocal,
      Resolver.bintrayRepo("shiftleft", "maven"),
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
val fuzzyc2cpgVersion = "5c917bb7709268af98c2eee351556fcf39f1a976"

lazy val codepropertygraph = Projects.codepropertygraph
lazy val protoBindings = Projects.protoBindings
lazy val semanticcpg = Projects.semanticcpg
lazy val dataflowengine = Projects.dataflowengine
lazy val cpgserver = Projects.cpgserver
lazy val cpgvalidator = Projects.cpgvalidator
lazy val console = Projects.console
lazy val queries = Projects.queries

ThisBuild/scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-Xfatal-warnings",
  "-language:implicitConversions",
  "-Ycache-macro-class-loader:last-modified",
  "-Ybackend-parallelism", "4")
ThisBuild/compile/javacOptions ++= Seq("-g") //debug symbols

Global/onChangedBuildSource := ReloadOnSourceChanges
onLoad in Global := {
  assert(GitLFSUtils.isGitLFSEnabled(), "You need to install git-lfs and run 'git lfs pull'")
  (onLoad in Global).value
}

