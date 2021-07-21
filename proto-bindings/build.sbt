name := "codepropertygraph-protos"

lazy val generateProtobuf = taskKey[File]("generate protobuf definitions: cpg.proto")

enablePlugins(ProtobufPlugin)
val protocLocalDir = "protoc"
val protocBinaryPath = s"$protocLocalDir/bin/protoc"
ProtobufConfig / protobufProtoc := protocBinaryPath
ProtobufConfig / version := "3.10.0"
ProtobufConfig/sourceDirectories += (ProtobufConfig/protobufExternalIncludePath).value
ProtobufConfig / protobufGenerate := (ProtobufConfig/protobufGenerate).dependsOn(copyLatestCpgProto).dependsOn(installProtoc).value

lazy val copyLatestCpgProto = taskKey[Unit]("copy latest cpg.proto to externalIncludePath")
copyLatestCpgProto := {
  val protoFile = (Projects.schema/generateProtobuf).value
  val targetDir = (ProtobufConfig/protobufExternalIncludePath).value
  val targetFile = targetDir / (protoFile.getName)
  targetDir.mkdirs
  IO.copyFile(protoFile, targetFile, CopyOptions().withOverwrite(true).withPreserveLastModified(true))
}

lazy val installProtoc = taskKey[File]("downloads configured protoc version for your platform, unless already available")
installProtoc := {
  import sys.process._
  import java.nio.file.Files
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
    val permissionSet = new java.util.HashSet[java.nio.file.attribute.PosixFilePermission]()
    permissionSet.add(java.nio.file.attribute.PosixFilePermission.OWNER_EXECUTE)
    Files.setPosixFilePermissions(protocBinary.toPath(), permissionSet)
  }
  protocBinary
}
