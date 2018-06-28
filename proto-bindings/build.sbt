name := "codepropertygraph-protos"
 
lazy val codepropertygraph = project.in(file("../codepropertygraph"))
lazy val generateProtobuf = taskKey[Seq[File]]("generate cpg.proto")

enablePlugins(ProtobufPlugin)
ProtobufConfig/version := "3.5.1"

sourceDirectories in ProtobufConfig += (protobufExternalIncludePath in ProtobufConfig).value
ProtobufConfig/protobufGenerate := (ProtobufConfig/protobufGenerate).dependsOn(copyLatestCpgProto).value

lazy val copyLatestCpgProto = taskKey[Unit]("copy latest cpg.proto to externalIncludePath")
copyLatestCpgProto := {
  val protoFile = (codepropertygraph/generateProtobuf).value.head
  val targetDir: java.io.File = (ProtobufConfig/protobufExternalIncludePath).value
  val targetFile = targetDir / (protoFile.getName)
  targetDir.mkdirs
  targetFile.delete
  println(s"copying $protoFile to $targetFile")
  java.nio.file.Files.copy(protoFile.toPath, targetFile.toPath)
}

