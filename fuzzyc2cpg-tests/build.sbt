name := "fuzzyc2cpg-tests"

dependsOn(Projects.semanticcpg,
          Projects.fuzzyc2cpg,
          Projects.dataflowengineoss % Test,
          Projects.semanticcpgtests % "compile->compile; test->test"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % Versions.scalatest % Test,
)

scalacOptions in (Compile, doc) ++= Seq(
  "-doc-title",
  "semanticcpg apidocs",
  "-doc-version",
  version.value
)

compile / javacOptions ++= Seq("-g") //debug symbols
publishArtifact in (Test, packageBin) := true
