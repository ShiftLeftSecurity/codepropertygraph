name := "codepropertygraph-domain-classes"

libraryDependencies += "io.shiftleft" %% "overflowdb-traversal" % Versions.overflowdb

Compile / sourceGenerators += Projects.schema / Compile / generateDomainClasses

/* generated sources occasionally have some warnings.. 
 * we're trying to minimise them on a best effort basis, but don't want
 * to fail the build because of them
 */
Compile / scalacOptions -= "-Xfatal-warnings" 
