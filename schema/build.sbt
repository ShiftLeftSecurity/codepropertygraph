name := "codepropertygraph-schema"

libraryDependencies += "io.shiftleft" %% "overflowdb-codegen" % "cfb6ef4560cbf65394e8f30c3bd0db5e0e789599"

val generateDomainClasses = taskKey[Seq[File]]("generate overflowdb domain classes for our schema")

generateDomainClasses := {
  val outputRoot = target.value / "odb-codegen"
  FileUtils.deleteRecursively(outputRoot)
  val invoked = (Compile/runMain).toTask(s" io.shiftleft.codepropertygraph.schema.Codegen schema/target/odb-codegen").value
  FileUtils.listFilesRecursively(outputRoot)
}
