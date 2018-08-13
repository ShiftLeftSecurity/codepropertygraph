inThisBuild(List(
  organization := "io.shiftleft",
  scalaVersion := "2.12.6",
  coursierTtl := None, //always check for updated snapshots
  resolvers ++= Seq(Resolver.mavenLocal, "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/public"),
  packageDoc/publishArtifact := true,
  packageSrc/publishArtifact := true,
  crossPaths := false, // do not append scala version to the generated artifacts
  homepage := Some(url("https://github.com/ShiftLeftSecurity/codepropertygraph/")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(/* sonatype requires this to be non-empty */
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

name := "root"
publish/skip := true

lazy val codepropertygraph = project.in(file("codepropertygraph"))
lazy val protoBindings = project.in(file("proto-bindings"))


