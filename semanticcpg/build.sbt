name := "semanticcpg"

dependsOn(Projects.codepropertygraph)

libraryDependencies ++= Seq(
  "org.json4s"    %% "json4s-native" % Versions.json4s,
  "org.scalatest" %% "scalatest"     % Versions.scalatest % Test
)

Compile / doc / scalacOptions ++= Seq("-doc-title", "semanticcpg apidocs", "-doc-version", version.value)

compile / javacOptions ++= Seq("-g") //debug symbols
Test / packageBin / publishArtifact := true
