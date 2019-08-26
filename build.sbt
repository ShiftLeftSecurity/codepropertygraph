name := "codepropertygraph"
inThisBuild(
  List(
    organization := "io.shiftleft",
    scalaVersion := "2.12.8",
    resolvers ++= Seq(Resolver.mavenLocal, "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/public"),
    packageDoc / publishArtifact := true,
    packageSrc / publishArtifact := true,
    crossPaths := false, // do not append scala version to the generated artifacts
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
  ))

name := "codepropertygraph"
publish / skip := true

// parsed by project/Utils.scala
val fuzzyc2cpgVersion = "0.1.78"

lazy val codepropertygraph = Projects.codepropertygraph
lazy val protoBindings = Projects.protoBindings
lazy val semanticcpg = Projects.semanticcpg
lazy val dataflowengine = Projects.dataflowengine
lazy val cpgserver = Projects.cpgserver
lazy val cpgvalidator = Projects.cpgvalidator
lazy val cpg2overflowdb = Projects.cpg2overflowdb

ThisBuild/publishTo := sonatypePublishTo.value
ThisBuild/scalacOptions ++= Seq("-deprecation", "-feature", "-language:implicitConversions")
ThisBuild/compile/javacOptions ++= Seq("-g") //debug symbols

onLoad in Global := {
  assert(GitLFSUtils.isGitLFSEnabled(), "You need to install git-lfs and run 'git lfs pull'")
  (onLoad in Global).value
}
