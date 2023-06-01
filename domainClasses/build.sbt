name := "codepropertygraph-domain-classes"

libraryDependencies += "io.shiftleft" %% "overflowdb-traversal" % Versions.overflowdb

lazy val generatedSrcDir = settingKey[File]("root for generated sources - we want to check those in")
generatedSrcDir := (Compile/sourceDirectory).value / "generated"
Compile/unmanagedSourceDirectories += generatedSrcDir.value
Compile/compile := (Compile/compile).dependsOn(Projects.schema/Compile/generateDomainClasses).value

/* generated sources occasionally have some warnings..
 * we're trying to minimise them on a best effort basis, but don't want
 * to fail the build because of them
 */
Compile / scalacOptions --= Seq("-Wconf:cat=deprecation:w,any:e", "-Wunused", "-Ywarn-unused")

cleanFiles += baseDirectory.value / "src/main/generated"
