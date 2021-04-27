name := "semanticcpg"

dependsOn(Projects.codepropertygraph)

libraryDependencies ++= Seq(
  "org.json4s"             %% "json4s-native"            % "3.6.7",
  "org.scala-lang.modules" %% "scala-collection-contrib" % "0.2.1",
)

Compile/doc/scalacOptions ++= Seq(
  "-doc-title",
  "semanticcpg apidocs",
  "-doc-version",
  version.value
)

compile / javacOptions ++= Seq("-g") //debug symbols
Test/packageBin/publishArtifact := true
