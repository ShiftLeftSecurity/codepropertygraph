name := "dataflowengine"

dependsOn(Projects.semanticcpg % "compile -> compile; test -> test")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "io.shiftleft" %% "fuzzyc2cpg" % Versions.fuzzyc2cpg % Test
)
