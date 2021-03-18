libraryDependencies ++= Seq(
  "com.github.pathikrit" %% "better-files" % "3.8.0",
  "io.shiftleft" %% "overflowdb-codegen" % "f6d14741799b671cbea1e43bfdcdff89f91248f4",
)

resolvers += Resolver.mavenLocal
resolvers += Resolver.bintrayRepo("shiftleft", "maven")
