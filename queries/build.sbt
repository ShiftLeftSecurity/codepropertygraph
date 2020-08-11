name := "queries"

dependsOn(Projects.semanticcpg % "compile -> compile; test -> test",
          Projects.dataflowengineoss % "compile -> compile; test -> test")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.1.1" % Test,
  "io.shiftleft" %% "fuzzyc2cpg" % Versions.fuzzyc2cpg % Test
)
