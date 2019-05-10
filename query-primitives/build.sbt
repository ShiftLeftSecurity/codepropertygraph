name := "query-primitives"

dependsOn(Projects.codepropertygraph)

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-native" % "3.5.2",
  "com.massisframework" % "j-text-utils" % "0.3.4",
  "org.scalatest" %% "scalatest" % "3.0.3" % Test
)

scalacOptions in (Compile, doc) ++= Seq(
  "-doc-title",
  "query-primitives apidocs",
  "-doc-version",
  version.value
)
