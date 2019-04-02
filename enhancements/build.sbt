name := "enhancements"

dependsOn(Projects.queryPrimitives)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)
