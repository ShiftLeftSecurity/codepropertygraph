/* note: this is an entirely separate build, to avoid incidental complexity with sbt's 
 * crossScalaVersion, which leads to double-publishing of this artifact: 
 * https://travis-ci.org/github/ShiftLeftSecurity/codepropertygraph/jobs/688572983 
 * tl;dr; be warned: this setup is unusual, but there's a reason */

name := "schema-extender"
organization := "io.shiftleft"
resolvers += Resolver.bintrayRepo("shiftleft", "maven")
bintrayVcsUrl := Some("https://github.com/ShiftLeftSecurity/codepropertygraph")
licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

// the overflowdb-codegen is only available for scala 2.12 (driven by sbt being on 2.12), so we'll just do the same here
scalaVersion := "2.12.11"

libraryDependencies ++= Seq(
  "io.shiftleft" %% "overflowdb-codegen" % "54217e3fe052362e48309cca443f282b7370326f",
  "org.zeroturnaround" % "zt-zip" % "1.14",
  "com.github.pathikrit" %% "better-files" % "3.8.0",
  "com.github.scopt" %% "scopt" % "3.7.1",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
)

enablePlugins(JavaAppPackaging)

Global/onChangedBuildSource := ReloadOnSourceChanges
