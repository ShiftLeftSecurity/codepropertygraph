name := "semanticcpg"

dependsOn(Projects.codepropertygraph)
dependsOn(Projects.fuzzyc2cpg % Test)

libraryDependencies ++= Seq(
  "org.json4s"             %% "json4s-native"            % "3.6.7",
  "org.scala-lang.modules" %% "scala-collection-contrib" % "0.2.1",
  "org.scalatest"          %% "scalatest"                % Versions.scalatest,
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
