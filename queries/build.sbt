name := "queries"

dependsOn(Projects.semanticcpg % "compile -> compile; test -> test",
          Projects.dataflowengineoss % "compile -> compile; test -> test")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest"  % Versions.scalatest % Test,
)
