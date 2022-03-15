name := "codepropertygraph-domain-classes"

libraryDependencies += "io.shiftleft" %% "overflowdb-traversal" % Versions.overflowdb

Compile / sourceGenerators += Projects.schema / Compile / generateDomainClasses

/* generated sources occasionally have some warnings..
 * we're trying to minimise them on a best effort basis, but don't want
 * to fail the build because of them
 */
Compile / scalacOptions --= Seq("-Wconf:cat=deprecation:w,any:e", "-Wunused", "-Ywarn-unused")

Compile/packageSrc/mappings ++= {
  val base  = (Projects.schema/Compile/sourceManaged).value / "overflowdb-codegen"
  val files = (Compile/managedSources).value
  files.map(f => (f, f.relativeTo(base).get.getPath))
}
