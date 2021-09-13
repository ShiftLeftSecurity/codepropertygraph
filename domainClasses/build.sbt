name := "codepropertygraph-domain-classes"

dependsOn(Projects.schema)

// libraryDependencies += "io.shiftleft" %% "overflowdb-traversal" % Versions.overflowdb

Compile / generateDomainClasses / classWithSchema := "io.shiftleft.codepropertygraph.schema.CpgSchema$"
Compile / generateDomainClasses / fieldName := "instance"

Compile / sourceGenerators += Compile / generateDomainClasses

/* generated sources occasionally have some warnings.. 
 * we're trying to minimise them on a best effort basis, but don't want
 * to fail the build because of them
 */
Compile / scalacOptions -= "-Xfatal-warnings" 
