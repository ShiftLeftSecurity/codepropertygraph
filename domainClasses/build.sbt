name := "codepropertygraph-domain-classes"

libraryDependencies += "io.shiftleft" %% "overflowdb-traversal" % Versions.overflowdb

val generateDomainClasses = taskKey[Seq[File]]("generate overflowdb domain classes for our schema")

Compile / sourceGenerators += Projects.schema / generateDomainClasses

scalacOptions -= "-Xfatal-warnings"
