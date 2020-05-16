name := "schema-extender"
publish/skip := true

// the overflowdb-codegen is only available for scala 2.12 (driven by sbt being on 2.12), so we'll just do the same here
scalaVersion := "2.12.11"

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "io.shiftleft" %% "overflowdb-codegen" % "1.8",
  "org.zeroturnaround" % "zt-zip" % "1.14",
  "com.github.pathikrit" %% "better-files" % "3.8.0",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
)
