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
      Resolver.bintrayRepo("mpollmeier", "maven"),
      "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/public"
    ),
    packageDoc / publishArtifact := true,
    packageSrc / publishArtifact := true,
    scmInfo := Some(
      ScmInfo(url("https://github.com/ShiftLeftSecurity/codepropertygraph"),
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

ThisBuild / publishTo := sonatypePublishToBundle.value
Global / useGpg := false

name := "codepropertygraph"
publish / skip := true

// parsed by project/Utils.scala
val fuzzyc2cpgVersion = "1.1.55-SNAPSHOT"

lazy val codepropertygraph = Projects.codepropertygraph
lazy val protoBindings = Projects.protoBindings
lazy val semanticcpg = Projects.semanticcpg
lazy val dataflowengineoss = Projects.dataflowengineoss
lazy val cpgvalidator = Projects.cpgvalidator
lazy val console = Projects.console
lazy val queries = Projects.queries

ThisBuild / scalacOptions ++= Seq("-deprecation",
                                  "-feature",
                                  "-Xfatal-warnings",
                                  "-language:implicitConversions",
                                  "-Ycache-macro-class-loader:last-modified",
                                  "-Ybackend-parallelism",
                                  "4")
ThisBuild / compile / javacOptions ++= Seq("-g") //debug symbols

Global / onChangedBuildSource := ReloadOnSourceChanges
onLoad in Global := {
  assert(GitLFSUtils.isGitLFSEnabled(), "You need to install git-lfs and run 'git lfs pull'")
  (onLoad in Global).value
}
