package io.shiftleft.codepropertygraph.cpgloading

import java.io.{File, FileInputStream, IOException, InputStream}
import java.nio.file.Files
import java.util.Optional

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.proto
import io.shiftleft.proto.cpg.Cpg.{CpgOverlay, CpgStruct}

import scala.collection.JavaConverters._

private class ProtoCpgLoader {}

/**
  * Warning - this is an internal class. Please use `CpgLoader` if you
  * require a stable API.
  * */
object ProtoCpgLoader {

  /**
    * Load code property graph from zip archive. The zip archive
    * contains proto files. The default loader configuration is used.
    * @param filename filename of the CPG archive
    * */
  def loadFromProtoZip(filename: String): Cpg =
    loadFromProtoZip(filename, CpgLoaderConfig.default)

  /**
    * Load code property graph from zip archive. The zip archive
    * contains proto files.
    * @param filename filename of the CPG archive
    * @param config loader configuration
    * */
  def loadFromProtoZip(filename: String, config: CpgLoaderConfig): Cpg = {
    val archiveLoader = new ProtoCpgArchiveLoader()
    val tmpDirPath = archiveLoader
      .extract(filename, "cpg2sp_proto")
      .getOrElse(throw new RuntimeException("Error extracting archive"))
    val cpg = loadFromProtobufDirectory(tmpDirPath, config)
    archiveLoader.destroy
    cpg
  }

  /**
    * Load code property graph from directory containing protobuf bin files.
    * This is the directory obtained by extracting a CPG zip archive.
    * */
  def loadFromProtobufDirectory(inputDirectory: String, config: CpgLoaderConfig): Cpg = {
    val filenamesInDirectory = filenamesForConfig(inputDirectory, config)
    val builder = builderForConfig(config)
    filenamesInDirectory.foreach(addNodes(_, builder))
    filenamesInDirectory.foreach(addEdges(_, builder))
    builder.build()
  }

  /**
    * Load overlays from archive of overlays stored at `filename`
    * @param filename the filename of the archive
    * */
  def loadOverlays(filename: String): List[proto.cpg.Cpg.CpgOverlay] = {
    val archiveLoader = new ProtoCpgArchiveLoader()
    val tmpDirPath = archiveLoader
      .extract(filename, "cpg2sp_proto_overlay")
      .getOrElse(throw new RuntimeException("Error extracting archive"))
    val cpg = loadOverlaysFromProtobufDirectory(tmpDirPath)
    archiveLoader.destroy
    cpg
  }

  /**
    * Load overlays from directory containing overlays in proto format.
    * @inputDirectory directory containing overlays
    * */
  def loadOverlaysFromProtobufDirectory(inputDirectory: String): List[proto.cpg.Cpg.CpgOverlay] = {

    def comparator(file1: String, file2: String): Int = {
      val file1Split = file1.split("_")
      val file2Split = file2.split("_")
      if (file1Split.length < 2 || file2Split.length < 2) {
        file1.compareTo(file2)
      } else {
        Integer.parseInt(file1Split(0)) - Integer.parseInt(file2Split(0))
      }
    }

    val filesInDirectory = getFileNamesInDirectory(new File(inputDirectory))
    // Note: sorting is necessary here because we need to load overlays
    // in the right order. Sorting requires us to eagerly accumulate
    // all filenames here. For the directory of overlays, this is not
    // problematic though, since the number of overlay files in rather small.
    // If it does ever grow, we may need to rethink this

    filesInDirectory
      .sorted(comparator)
      .iterator
      .asScala
      .map { file =>
        val inputStream = new FileInputStream(file)
        val cpgOverlay = CpgOverlay.parseFrom(inputStream)
        inputStream.close()
        cpgOverlay
      }
      .toList
  }

  private def getFileNamesInDirectory(directory: File): java.util.stream.Stream[String] =
    Files
      .walk(directory.toPath)
      .filter(_.toFile.isFile)
      .map[String](_.toFile.toString)

  private def builderForConfig(config: CpgLoaderConfig): ProtoToCpg = {
    val onDiskOverflowConfig: Optional[OnDiskOverflowConfig] = config.onDiskOverflowConfig
      .map(x => Optional.ofNullable(x))
      .getOrElse(Optional.empty())
    new ProtoToCpg(onDiskOverflowConfig)
  }

  private def filenamesForConfig(inputDirectory: String, config: CpgLoaderConfig) = {
    getFileNamesInDirectory(new File(inputDirectory)).iterator.asScala.filter(matchesPattern(_, config)).toList
  }

  private def matchesPattern(file: String, config: CpgLoaderConfig): Boolean =
    config.patterns.isEmpty || config.patterns.exists(file.matches)

  private def addNodes(file: String, builder: ProtoToCpg) = {
    // TODO: use ".bin" extensions in proto output, and then only
    // load files with ".bin" extension here.
    val inputStream = new FileInputStream(file)
    builder.addNodes(CpgStruct.parseFrom(inputStream).getNodeList)
    inputStream.close()
  }

  private def addEdges(file: String, builder: ProtoToCpg) = {
    // TODO: use ".bin" extensions in proto output, and then only
    // load files with ".bin" extension here.
    val inputStream = new FileInputStream(file)
    builder.addEdges(CpgStruct.parseFrom(inputStream).getEdgeList)
    inputStream.close()
  }

  /**
    * Load code property graph from input stream pointing to proto file
    * */
  def loadFromInputStream(inputStream: InputStream, config: CpgLoaderConfig): Cpg = {
    val onDiskOverflowConfig = config.onDiskOverflowConfig
      .map { c =>
        Optional.ofNullable(c)
      }
      .getOrElse(Optional.empty())
    val builder = new ProtoToCpg(onDiskOverflowConfig)
    try {
      builder.addEdges(consumeInputStreamNodes(builder, inputStream).asJava)
    } finally {
      closeProtoStream(inputStream)
    }
    return builder.build()
  }

  private def consumeInputStreamNodes(builder: ProtoToCpg, inputStream: InputStream): List[CpgStruct.Edge] = {
    val cpgStruct: CpgStruct = CpgStruct.parseFrom(inputStream)
    builder.addNodes(cpgStruct.getNodeList)
    cpgStruct.getEdgeList.asScala.toList
  }

  private def closeProtoStream(inputStream: InputStream): Unit = {
    try inputStream.close()
    catch {
      case exception: IOException =>
        throw new RuntimeException(exception)
    }
  }

  /**
    * Load code property graph from a list of CPGs in proto format.
    **/
  def loadFromListOfProtos(cpgs: List[proto.cpg.Cpg.CpgStruct], config: CpgLoaderConfig): Cpg = {
    val onDiskOverflowConfig = config.onDiskOverflowConfig
      .map { c =>
        Optional.ofNullable(c)
      }
      .getOrElse(Optional.empty())
    val builder = new ProtoToCpg(onDiskOverflowConfig)

    for (cpgStruct <- cpgs) {
      builder.addNodes(cpgStruct.getNodeList)
    }

    for (cpgStruct <- cpgs) {
      builder.addEdges(cpgStruct.getEdgeList)
    }

    return builder.build
  }

}
