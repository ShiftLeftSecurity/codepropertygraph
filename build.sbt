name := "root"
publish/skip := true

lazy val codepropertygraph = project.in(file("codepropertygraph"))
lazy val protoBindings = project.in(file("proto-bindings"))

ThisBuild/organization := "io.shiftleft"
ThisBuild/scalaVersion := "2.12.6"

ThisBuild/coursierTtl := None //always check for updated snapshots
ThisBuild/resolvers ++= Seq(
  Resolver.mavenLocal,
  "Artifactory snapshot local" at "https://shiftleft.jfrog.io/shiftleft/libs-snapshot-local",
  "Artifactory release local" at "https://shiftleft.jfrog.io/shiftleft/libs-release-local",
  "Sonatype OSS" at "https://oss.sonatype.org/content/repositories/public")

// Disable scaladoc generation / publishing
ThisBuild/Compile/doc/sources := Seq.empty
ThisBuild/Compile/packageDoc/publishArtifact := false

ThisBuild/publishLocal := publishM2.value // always publish to local maven cache to avoid having to look in too many places when debugging
ThisBuild/publishTo := {
  val jfrog = "https://shiftleft.jfrog.io/shiftleft/"
  val buildTimestampSuffix = ";build.timestamp=" + new java.util.Date().getTime
  if (isSnapshot.value) {
    Some("snapshots" at jfrog + "libs-snapshot-local" + buildTimestampSuffix)
  } else {
    Some("releases" at jfrog + "libs-release-local")
  }
}
ThisBuild/releaseNextVersion := { _ => "0.0.0-SNAPSHOT" }
ThisBuild/publishMavenStyle := true
ThisBuild/crossPaths := false // do not append scala version to the generated artifacts

