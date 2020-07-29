libraryDependencies ++= Seq(
  "com.github.pathikrit" %% "better-files" % "3.8.0",
  "io.shiftleft" %% "overflowdb-codegen" % "1ed4389493f4428a96050aaafa5722951a02cda7-SNAPSHOT",
)

resolvers += Resolver.bintrayRepo("shiftleft", "maven")
 // "1.20-SNAPSHOT", "1.17"