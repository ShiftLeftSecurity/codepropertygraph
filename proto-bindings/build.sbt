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

/** generate csharp bindings for proto file as a nuget package. 
  * usage examples:
  * `sbt generateCsharpBindings` -> just build it locally
  * `sbt -Dpublish-to-repo=https://shiftleft.jfrog.io/shiftleft/api/nuget/nuget-release-local -Dpublish-key=michael:xxxxxxxxxx generateCsharpBindings` -> publish to the specified repo
  */
lazy val generateCsharpBindings = taskKey[File]("generate csharp proto bindings")
generateCsharpBindings := {
  (codepropertygraph/generateProtobuf).value //ensures this is being run beforehand
  import sys.process._
  val millis = System.currentTimeMillis
  val dotnetVersion = s"0.0.1-${millis}-${version.value}" //dotnet is VERY restrictive with version names
  val publishToRepo = Option(System.getProperty("publish-to-repo")).map(repo => s"--publish-to-repo $repo")
  val publishKey = Option(System.getProperty("publish-key")).map(key => s"--publish-key $key")
  s"""./build-dotnet-bindings.sh --cpg-version $dotnetVersion ${publishToRepo.getOrElse("")} ${publishKey.getOrElse("")}""".!
  new File(s"cpg-proto-bindings.${dotnetVersion}.nupkg")
}
