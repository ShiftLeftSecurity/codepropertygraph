libraryDependencies ++= Seq(
  "com.github.pathikrit" %% "better-files" % "3.8.0",
  "io.shiftleft" %% "overflowdb-codegen" % "1.48",
)

resolvers += Resolver.mavenLocal
resolvers += Resolver.bintrayRepo("shiftleft", "maven")
