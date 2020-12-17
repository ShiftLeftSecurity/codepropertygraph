name := "dataflowengineoss-tests"

dependsOn(Projects.semanticcpg, Projects.semanticcpgtests % "test -> test",
  Projects.fuzzyc2cpgtests % "test ->test",
  Projects.dataflowengineoss,
)
dependsOn(Projects.fuzzyc2cpg % Test)

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

// execute tests in root project so that they work in sbt *and* intellij
Test / baseDirectory := (ThisBuild / Test / run / baseDirectory).value
