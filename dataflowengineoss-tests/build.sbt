name := "dataflowengineoss-tests"

dependsOn(Projects.semanticcpg, Projects.semanticcpgtests % "test -> test",
  Projects.dataflowengineoss,
)
dependsOn(Projects.fuzzyc2cpg % Test)

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
