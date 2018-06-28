resolvers ++= Seq(
  "Artifactory snapshot local" at "https://shiftleft.jfrog.io/shiftleft/libs-snapshot-local",
  "Artifactory release local" at "https://shiftleft.jfrog.io/shiftleft/libs-release-local"
)


addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.1.0-M4")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.6")
addSbtPlugin("com.github.gseitz" % "sbt-protobuf" % "0.6.3")
addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "1.3.0")
addSbtPlugin("com.github.sbt" % "sbt-findbugs" % "2.0.0")
addSbtPlugin("io.shiftleft" %% "sbt-jardigests" % "0.2")
