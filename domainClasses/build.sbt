name := "codepropertygraph-domain-classes"

dependsOn(Projects.schema)

libraryDependencies += "io.shiftleft" %% "overflowdb-traversal" % Versions.overflowdb

// val generateDomainClasses = taskKey[Seq[File]]("generate overflowdb domain classes for our schema")

// Compile / sourceGenerators += Projects.schema/generateDomainClasses

/* generated sources occasionally have some warnings.. 
 * we're trying to minimise them on a best effort basis, but don't want
 * to fail the build because of them
 */
Compile / scalacOptions -= "-Xfatal-warnings" 
