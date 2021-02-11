libraryDependencies ++= Seq(
  "com.github.pathikrit" %% "better-files" % "3.8.0",
  "io.shiftleft" %% "overflowdb-codegen" % "b34563115b4403ac0b0df457aa05404af73b0d34-SNAPSHOT",
)

resolvers += Resolver.mavenLocal
resolvers += Resolver.bintrayRepo("shiftleft", "maven")
