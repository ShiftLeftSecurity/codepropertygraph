name := "codepropertygraph"

// parsed by project/Versions.scala, updated by updateDependencies.sh
val overflowdbVersion = "1.30"

inThisBuild(
  List(
    organization := "io.shiftleft",
    scalaVersion := "2.13.4",
    resolvers ++= Seq(
      Resolver.mavenLocal,
      Resolver.jcenterRepo,
      "jitpack" at "https://jitpack.io",
      "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/public"),
    packageDoc / publishArtifact := true,
    packageSrc / publishArtifact := true,
    scmInfo := Some(ScmInfo(url("https://github.com/ShiftLeftSecurity/codepropertygraph"),
                            "scm:git@github.com:ShiftLeftSecurity/codepropertygraph.git")),
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

ThisBuild/publishTo := sonatypePublishToBundle.value
ThisBuild / Test / fork := true
ThisBuild / Test / javaOptions += s"-Dlog4j2.configurationFile=file:${baseDirectory.in(ThisBuild).value}/resources/log4j2-test.xml"
// If we fork we immediately stumble upon https://github.com/sbt/sbt/issues/3892 and https://github.com/sbt/sbt/issues/3892
ThisBuild / Test / javaOptions += s"-Duser.dir=${baseDirectory.in(ThisBuild).value}"

ThisBuild/libraryDependencies ++= Seq(
  "org.apache.logging.log4j" %  "log4j-slf4j-impl" % "2.11.2" % Test
)

name := "codepropertygraph"
publish / skip := true

lazy val codepropertygraph = Projects.codepropertygraph
lazy val schema = Projects.schema
lazy val domainClasses = Projects.domainClasses
lazy val protoBindings = Projects.protoBindings
lazy val semanticcpg = Projects.semanticcpg
lazy val semanticcpgtests = Projects.semanticcpgtests
lazy val dataflowengineoss = Projects.dataflowengineoss
lazy val dataflowengineosstests = Projects.dataflowengineosstests
lazy val cpgvalidator = Projects.cpgvalidator
lazy val console = Projects.console
lazy val fuzzyc2cpg = Projects.fuzzyc2cpg
lazy val fuzzyc2cpgtests = Projects.fuzzyc2cpgtests
lazy val macros = Projects.macros

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

