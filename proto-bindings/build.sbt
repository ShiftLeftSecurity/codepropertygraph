name := "codepropertygraph-protos"

lazy val generateProtobuf = taskKey[File]("generate cpg.proto")

enablePlugins(ProtobufPlugin)
ProtobufConfig / version := "3.7.0"

sourceDirectories in ProtobufConfig += (protobufExternalIncludePath in ProtobufConfig).value
ProtobufConfig / protobufGenerate := (ProtobufConfig / protobufGenerate).dependsOn(copyLatestCpgProto).value

lazy val copyLatestCpgProto = taskKey[Unit]("copy latest cpg.proto to externalIncludePath")
copyLatestCpgProto := {
  val protoFile = (Projects.codepropertygraph/generateProtobuf).value
  val targetDir: java.io.File = (ProtobufConfig/protobufExternalIncludePath).value
  val targetFile = targetDir / (protoFile.getName)
  val currentMd5 = FileUtils.md5(protoFile)
  if (!targetFile.exists || CopyLatestCpgProtoTaskGlobalState.lastMd5 != currentMd5) {
    targetDir.mkdirs
    targetFile.delete
    println(s"copying $protoFile to $targetFile")
    java.nio.file.Files.copy(protoFile.toPath, targetFile.toPath)
  } else {
    println("no need to copy cpg.proto to externalIncludePath")
  }
  CopyLatestCpgProtoTaskGlobalState.lastMd5 = currentMd5
}

/** generate csharp bindings for proto file as a nuget package.
  * usage examples:
  * `sbt generateCsharpBindings -Ddotnet-version=1.0.0` -> just build it locally
  * `sbt  -Ddotnet-version=1.0.0 -Dpublish-to-repo=https://shiftleft.jfrog.io/shiftleft/api/nuget/nuget-release-local -Dpublish-key=michael:xxxxxxxxxx generateCsharpBindings` -> publish to the specified repo
  * note: dotnet is VERY restrictive with version names
  */
lazy val generateCsharpBindings = taskKey[File]("generate csharp proto bindings")
generateCsharpBindings := {
  (Projects.codepropertygraph/generateProtobuf).value //ensures this is being run beforehand
  val dotnetVersion = System.getProperty("dotnet-version")
  assert(dotnetVersion != null, "you must define the dotnet version via a jvm system property, e.g. via `-Ddotnet-version=1.0.0`")
  println(s"building and publishing csharp proto version $dotnetVersion")
  val publishToRepo = Option(System.getProperty("publish-to-repo")).map(repo => s"--publish-to-repo $repo")
  val publishKey = Option(System.getProperty("publish-key")).map(key => s"--publish-key $key")
  runCmd(s"""./build-dotnet-bindings.sh --cpg-version $dotnetVersion ${publishToRepo.getOrElse("")} ${publishKey.getOrElse("")}""", "./build-dotnet-bindings.sh")
  new File(s"cpg-proto-bindings.${dotnetVersion}.nupkg")
}

lazy val generateGoBindings = taskKey[File]("generate go proto bindings (doesn't publish them anywhere)")
generateGoBindings := {
  (Projects.codepropertygraph/generateProtobuf).value //ensures this is being run beforehand
  // protoc requires a relative path...
  val protoFile = "codepropertygraph/target/cpg.proto"
  val outDir = new File("codepropertygraph/target/protoc-go")
  outDir.mkdirs
  println(s"writing go proto bindings to $outDir")
  val cmd = s"""protoc --go_out=$outDir $protoFile"""
  runCmd(cmd, cmd)
  outDir
}

lazy val generatePythonBindings = taskKey[File]("generate Python proto bindings")
generatePythonBindings := {
  (Projects.codepropertygraph/generateProtobuf).value //ensures this is being run beforehand
  // protoc requires a relative path...
  val protoFile = "codepropertygraph/target/cpg.proto"
  val outDir = new File("codepropertygraph/target/protoc-py")
  outDir.mkdirs
  println(s"writing Python proto bindings to $outDir")
  val cmd = s"""protoc --python_out=$outDir $protoFile"""
  runCmd(cmd, cmd)
  outDir
}

def runCmd(cmd: String, contextOnError: => String): Unit = {
  import sys.process._
  val result = cmd.!
  assert(result == 0, s"exitCode=$result when running external command. context: `$contextOnError`")
}
