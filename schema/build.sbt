name := "codepropertygraph-schema"

libraryDependencies += "io.shiftleft" %% "overflowdb-codegen" % "cfb6ef4560cbf65394e8f30c3bd0db5e0e789599"

val generateDomainClasses = taskKey[Seq[File]]("generate overflowdb domain classes for our schema")

generateDomainClasses := {
  val invoked = (Compile/runMain).toTask(s" io.shiftleft.codepropertygraph.schema.CpgSchema schema/target/odb-codegen").value
  FileUtils.listFilesRecursively(new File("schema/target/odb-codegen"))
}
