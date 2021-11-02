name := "c2cpg-tests"

dependsOn(Projects.semanticcpg,
          Projects.c2cpg,
          Projects.dataflowengineoss % Test,
          Projects.semanticcpg % "test->test"
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
