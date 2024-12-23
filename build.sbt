name := "codepropertygraph"

// parsed by project/Versions.scala, updated by updateDependencies.sh
val flatgraphVersion = "0.1.2"

inThisBuild(
  List(
    organization := "io.shiftleft",
    scalaVersion := "3.5.2",
    resolvers ++= Seq(
      "Github Package Registry" at "https://maven.pkg.github.com/Privado-Inc/flatgraph",
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
      Developer("fabsx00", "Fabian Yamaguchi", "fabs@shiftleft.io", url("https://github.com/fabsx00")),
      Developer("ml86", "Markus Lottmann", "markus@shiftleft.io", url("https://github.com/ml86")),
      Developer(
        "mpollmeier",
        "Michael Pollmeier",
        "michael@michaelpollmeier.com",
        url("http://www.michaelpollmeier.com/")
      )
    )
  )
)

ThisBuild / publishTo             := sonatypePublishToBundle.value
ThisBuild / sonatypeTimeoutMillis := 7200000 // double the default close timeout
ThisBuild / Test / fork           := true
ThisBuild / Test / javaOptions += s"-Dlog4j2.configurationFile=file:${(ThisBuild / baseDirectory).value}/resources/log4j2-test.xml"
// If we fork we immediately stumble upon https://github.com/sbt/sbt/issues/3892 and https://github.com/sbt/sbt/issues/3892
ThisBuild / Test / javaOptions += s"-Duser.dir=${(ThisBuild / baseDirectory).value}"

ThisBuild / libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-slf4j2-impl" % "2.19.0" % Optional,
  "org.apache.logging.log4j" % "log4j-core"        % "2.19.0" % Optional
  // `Optional` means "not transitive", but still included in "stage/lib"
)

name           := "codepropertygraph"
publish / skip := true

lazy val schema            = Projects.schema
lazy val domainClasses     = Projects.domainClasses
lazy val protoBindings     = Projects.protoBindings
lazy val codepropertygraph = Projects.codepropertygraph
lazy val schema2json       = Projects.schema2json

ThisBuild / scalacOptions ++= Seq(
  "-release",
  "8",
  "-deprecation",
  "-feature",
  // "-explain",
  // "-Xfatal-warnings",
  // "-Wconf:cat=deprecation:w,any:e",
  "-language:implicitConversions"
)

ThisBuild / javacOptions ++= Seq(
  "-g", // debug symbols
  "--release",
  "8"
)

Global / onChangedBuildSource := ReloadOnSourceChanges
Global / onLoad := {
  assert(GitLFSUtils.isGitLFSEnabled(), "You need to install git-lfs and run 'git lfs pull'")
  (Global / onLoad).value
}

githubOwner      := "Privado-Inc"
githubRepository := "codepropertygraph"
credentials +=
  Credentials(
    "GitHub Package Registry",
    "maven.pkg.github.com",
    "Privado-Inc",
    sys.env.getOrElse("GITHUB_TOKEN", "N/A")
  )
