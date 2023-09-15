name := "codepropertygraph-domain-classes"

val flatGraphVersion = "0.0.0+56-ef024eee"

libraryDependencies ++= Seq(
  "io.joern" %% "joern-generated" % flatGraphVersion,
  "io.shiftleft" %% "overflowdb-traversal" % Versions.overflowdb
)

lazy val generatedSrcDir = settingKey[File]("root for generated sources - we want to check those in")
generatedSrcDir := (Compile/sourceDirectory).value / "generated"
Compile/unmanagedSourceDirectories += generatedSrcDir.value
Compile/compile := (Compile/compile).dependsOn(Projects.schema/Compile/generateDomainClasses).value

/* generated sources occasionally have some warnings..
 * we're trying to minimise them on a best effort basis, but don't want
 * to fail the build because of them
 */
Compile / scalacOptions --= Seq("-Wconf:cat=deprecation:w,any:e", "-Wunused", "-Ywarn-unused")

// TODO uncomment, or rather find a better way to delete the generated files on `sbt clean`...
// this is just a temporarily commented out to fix the release build...
// minified command that fails if the below line isn't commented: `sbt clean doc`
// cleanFiles += baseDirectory.value / "src/main/generated"
