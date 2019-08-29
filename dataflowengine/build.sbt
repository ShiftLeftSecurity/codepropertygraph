name := "dataflowengine"

dependsOn(Projects.semanticcpg % "compile -> compile; test -> test")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.3" % Test,
  "io.shiftleft" %% "fuzzyc2cpg" % Versions.fuzzyc2cpg % Test exclude("io.shiftleft", "codepropertygraph") exclude("io.shiftleft", "codepropertygraph-protos")
)
