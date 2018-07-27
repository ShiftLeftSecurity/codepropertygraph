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

/** generate csharp bindings for proto file as a nuget package
  * 
  * if `publish-to-repo` and `publish-key` are specified, it will also publish the package, e.g. like this:
  * `sbt -Dpublish-to-repo=https://shiftleft.jfrog.io/shiftleft/api/nuget/nuget-release-local -Dpublish-key=michael:xxxxxxxxxx generateCsharpBindings`
  */
lazy val generateCsharpBindings = taskKey[File]("generate csharp proto bindings")
generateCsharpBindings := {
  (codepropertygraph/generateProtobuf).value //ensures this is being run beforehand
  import sys.process._
  val dotnetVersion = version.value.replaceAll("\\+", "-") //dotnet is more restrictive with version names
  val publishToRepo = Option(System.getProperty("publish-to-repo")).map(repo => s"--publish-to-repo $repo")
  val publishKey = Option(System.getProperty("publish-key")).map(key => s"--publish-key $key")
  s"""./build-dotnet-bindings.sh --cpg-version $dotnetVersion ${publishToRepo.getOrElse("")} ${publishKey.getOrElse("")}""".!
  new File(s"cpg-proto-bindings.${dotnetVersion}.nupkg")
}
