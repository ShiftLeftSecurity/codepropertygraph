name := "fuzzyc2cpg-tests"

dependsOn(Projects.semanticcpg,
          Projects.fuzzyc2cpg,
          Projects.dataflowengineoss % Test,
          Projects.semanticcpgtests % "compile->compile; test->test"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % Versions.scalatest % Test,
)

Compile / doc / scalacOptions ++= Seq(
  "-doc-title",
  "semanticcpg apidocs",
  "-doc-version",
  version.value
)

compile / javacOptions ++= Seq("-g") //debug symbols
Test / packageBin / publishArtifact := true
