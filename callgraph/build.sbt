name := "callgraph"

dependsOn(Projects.semanticcpg % "compile -> compile; test -> test", Projects.codepropertygraph)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.3" % Test,
  "io.shiftleft" %% "fuzzyc2cpg" % Versions.fuzzyc2cpg % Test
)
