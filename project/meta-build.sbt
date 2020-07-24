libraryDependencies ++= Seq(
  "com.github.pathikrit" %% "better-files" % "3.8.0",
  "io.shiftleft" %% "overflowdb-codegen" % "f1024dc27b005f0d5a1702772ad95c1a6e2def3d-SNAPSHOT",
)

resolvers += Resolver.bintrayRepo("shiftleft", "maven")
 // "1.20-SNAPSHOT", "1.17"