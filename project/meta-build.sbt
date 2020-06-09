libraryDependencies ++= Seq(
  "com.github.pathikrit" %% "better-files" % "3.8.0",
  "io.shiftleft" %% "overflowdb-codegen" % "0.0.0-MP",
)

resolvers += Resolver.bintrayRepo("shiftleft", "maven")
resolvers += Resolver.bintrayRepo("mpollmeier", "maven") //official build randomly failed last time, take out with next build
