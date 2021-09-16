name := "codepropertygraph-schema"

libraryDependencies += "io.shiftleft" %% "overflowdb-codegen" % "1.98+11-9d08dab6+20210916-1632"

Compile / generateDomainClasses / classWithSchema := "io.shiftleft.codepropertygraph.schema.CpgSchema$"
Compile / generateDomainClasses / fieldName := "instance"
