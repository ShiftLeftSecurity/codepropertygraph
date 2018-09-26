name := "query-primitives"

dependsOn(Projects.codepropertygraph, Projects.protoBindings)

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-native" % "3.5.2",
  "commons-io" % "commons-io" % "2.5",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)

scalacOptions in (Compile, doc) ++= Seq(
  "-doc-title",
  "query-primitives apidocs",
  "-doc-version",
  version.value
)
