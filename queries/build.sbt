name := "queries"

dependsOn(Projects.semanticcpg % "compile -> compile; test -> test",
          Projects.dataflowengineoss % "compile -> compile; test -> test",
          Projects.dataflowengineosstests % "compile -> compile; test -> test"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest"  % Versions.scalatest % Test,
)

// execute tests in root project so that they work in sbt *and* intellij
Test / baseDirectory := (ThisBuild / Test / run / baseDirectory).value
