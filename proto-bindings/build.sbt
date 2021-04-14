name := "codepropertygraph-protos"

lazy val generateProtobuf = taskKey[File]("generate protobuf definitions: cpg.proto")

enablePlugins(ProtobufPlugin)
val protocLocalDir = "protoc"
val protocBinaryPath = s"$protocLocalDir/bin/protoc"
ProtobufConfig / protobufProtoc := protocBinaryPath
ProtobufConfig / version := "3.10.0"
sourceDirectories in ProtobufConfig += (protobufExternalIncludePath in ProtobufConfig).value
ProtobufConfig / protobufGenerate := (ProtobufConfig/protobufGenerate).dependsOn(copyLatestCpgProto).dependsOn(installProtoc).value

lazy val copyLatestCpgProto = taskKey[Unit]("copy latest cpg.proto to externalIncludePath")
copyLatestCpgProto := {
  val protoFile = (Projects.schema/generateProtobuf).value
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

lazy val installProtoc = taskKey[File]("downloads configured protoc version for your platform, unless already available")
installProtoc := {
  import sys.process._
  // import better.files.FileExtensions
  val protocVersion = (ProtobufConfig/version).value
  val protocBinary = new File(protocBinaryPath)
  val isAlreadyInstalled = protocBinary.exists && s"$protocBinaryPath --version".!!.contains(protocVersion)

  if (!isAlreadyInstalled) {
    val platform= (System.getProperty("os.name"), System.getProperty("os.arch")) match {
      case ("Linux", "amd64") => "linux-x86_64"
      case (name, "amd64") if name.startsWith("Windows") => "win64"
      case (name, arch) if name.toLowerCase.contains("mac") && arch.contains("64") => "osx-x86_64"
      case (name, arch) => throw new AssertionError(s"unknown platform name/arch ($name/$arch), please add in `proto-bindings/build.sbt` match")
    }
    val url = new URL(s"https://github.com/protocolbuffers/protobuf/releases/download/v$protocVersion/protoc-$protocVersion-$platform.zip")

    println(s"downloading $url and extracting into $protocLocalDir")
    val outdir = new File(protocLocalDir)
    FileUtils.deleteRecursively(outdir)
    IO.unzipURL(url, outdir)
    protocBinary.addPermission(java.nio.file.attribute.PosixFilePermission.OWNER_EXECUTE)
  }
  protocBinary
}
