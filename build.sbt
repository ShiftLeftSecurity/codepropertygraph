name := "codepropertygraph"

// parsed by project/Versions.scala, updated by updateDependencies.sh
val overflowdbVersion = "1.84"

inThisBuild(
  List(
    organization := "io.shiftleft",
    scalaVersion := "2.13.7",
    crossScalaVersions := Seq("2.13.7", "3.1.0"),
    resolvers ++= Seq(
      Resolver.mavenLocal,
      "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/public"
    ),
    packageDoc / publishArtifact := true,
    packageSrc / publishArtifact := true,
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/ShiftLeftSecurity/codepropertygraph"),
        "scm:git@github.com:ShiftLeftSecurity/codepropertygraph.git"
      )
    ),
    homepage := Some(url("https://github.com/ShiftLeftSecurity/codepropertygraph/")),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      /* sonatype requires this to be non-empty */
      Developer(
        "fabsx00",
        "Fabian Yamaguchi",
        "fabs@shiftleft.io",
        url("https://github.com/fabsx00")
      ),
      Developer(
        "ml86",
        "Markus Lottmann",
        "markus@shiftleft.io",
        url("https://github.com/ml86")
      ),
      Developer(
        "mpollmeier",
        "Michael Pollmeier",
        "michael@michaelpollmeier.com",
        url("http://www.michaelpollmeier.com/")
      )
    )
  )
)

ThisBuild / publishTo := sonatypePublishToBundle.value
ThisBuild / sonatypeTimeoutMillis := 7200000 // double the default close timeout
ThisBuild / Test / fork := true
ThisBuild / Test / javaOptions += s"-Dlog4j2.configurationFile=file:${(ThisBuild/baseDirectory).value}/resources/log4j2-test.xml"
// If we fork we immediately stumble upon https://github.com/sbt/sbt/issues/3892 and https://github.com/sbt/sbt/issues/3892
ThisBuild / Test / javaOptions += s"-Duser.dir=${(ThisBuild/baseDirectory).value}"

ThisBuild / libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.17.0" % Test
)

// Scalafix / imports check setup
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0-alpha.1"
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := "4.4.30" // alternative thay may work again in future: `scalafixSemanticdb.revision`

name := "codepropertygraph"
publish / skip := true

lazy val schema = Projects.schema
lazy val domainClasses = Projects.domainClasses
lazy val protoBindings = Projects.protoBindings
lazy val codepropertygraph = Projects.codepropertygraph
lazy val semanticcpg = Projects.semanticcpg
lazy val schema2json = Projects.schema2json

// Once sbt-scalafmt is at version > 2.x, use scalafmtAll
addCommandAlias("format", ";scalafixAll OrganizeImports;scalafmt;test:scalafmt")

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-Xfatal-warnings",
  "-language:implicitConversions",
) ++ (
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((3, _)) => Seq(
          "-Xtarget:8"
          )
    case _ => Seq(
          "-target:jvm-1.8",
          "-Ywarn-unused", // required by scalafix
          "-Ycache-macro-class-loader:last-modified",
          "-Ybackend-parallelism",
          "4",
      )   
  }
)

ThisBuild / compile / javacOptions ++= Seq(
  "-g", //debug symbols
  "-source", "1.8",
  "-target", "1.8",
  "-Xlint")

Global / onChangedBuildSource := ReloadOnSourceChanges
Global / onLoad := {
  assert(GitLFSUtils.isGitLFSEnabled(), "You need to install git-lfs and run 'git lfs pull'")
  (Global / onLoad).value
}
