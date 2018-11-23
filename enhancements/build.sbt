name := "enhancements"

dependsOn(Projects.queryPrimitives)

libraryDependencies ++= Seq(
  "com.github.pathikrit" %% "better-files" % "3.1.0",
  "org.scalaz" %% "scalaz-concurrent" % "7.2.18",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)
