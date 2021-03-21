name := "codepropertygraph-schema"

libraryDependencies += "io.shiftleft" %% "overflowdb-codegen" % "4af837793cf85d20783e2b750f77404feed631bf"

val generateDomainClasses = taskKey[Seq[File]]("generate overflowdb domain classes for our schema")

generateDomainClasses := {
  val outputRoot = target.value / "odb-codegen"
  FileUtils.deleteRecursively(outputRoot)
  val invoked = (Compile/runMain).toTask(s" io.shiftleft.codepropertygraph.schema.Codegen schema/target/odb-codegen").value
  FileUtils.listFilesRecursively(outputRoot)
}
