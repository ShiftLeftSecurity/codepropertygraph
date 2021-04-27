name := "semanticcpg-tests"

dependsOn(Projects.semanticcpg)

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
