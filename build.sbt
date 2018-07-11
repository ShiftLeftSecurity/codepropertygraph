name := "root"
publish/skip := true

lazy val codepropertygraph = project.in(file("codepropertygraph"))
lazy val protoBindings = project.in(file("proto-bindings"))

ThisBuild/organization := "io.shiftleft"
ThisBuild/scalaVersion := "2.12.6"

ThisBuild/coursierTtl := None //always check for updated snapshots
ThisBuild/resolvers ++= Seq(
  Resolver.mavenLocal,
  "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/public")

// Disable scaladoc generation / publishing
ThisBuild/Compile/doc/sources := Seq.empty
ThisBuild/Compile/packageDoc/publishArtifact := false

ThisBuild/publishMavenStyle := true
ThisBuild/crossPaths := false // do not append scala version to the generated artifacts

