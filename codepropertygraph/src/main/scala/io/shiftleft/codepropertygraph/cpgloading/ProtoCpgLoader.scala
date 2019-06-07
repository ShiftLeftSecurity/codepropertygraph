package io.shiftleft.codepropertygraph.cpgloading

import java.io.{File, FileInputStream, IOException, InputStream}
import java.nio.file.Files
import java.util.Optional

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.proto
import io.shiftleft.proto.cpg.Cpg.{CpgOverlay, CpgStruct}
import org.apache.commons.io.FileUtils
import org.apache.logging.log4j.LogManager

import scala.collection.JavaConverters._

class ProtoCpgLoader {}

/**
  * Warning - this is an internal class. Please use `CpgLoader` if you
  * require a stable API.
  * */
object ProtoCpgLoader {

  private val logger = LogManager.getLogger(classOf[ProtoCpgLoader])

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
    extractArchiveAndExecuteOnDir[Cpg](filename, "cpg2sp_proto", { tempDirPathName =>
      loadFromProtobufDirectory(tempDirPathName, config)
    })
  }

  /**
    * Load overlays from archive of overlays stored at `filename`
    * @param filename the filename of the archive
    * */
  def loadOverlays(filename: String): List[proto.cpg.Cpg.CpgOverlay] =
    extractArchiveAndExecuteOnDir(filename, "cpg2sp_proto_overlay", { tempDirPathName =>
      loadOverlaysFromProtobufDirectory(tempDirPathName)
    })

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

  private def extractArchiveAndExecuteOnDir[T](filename: String, dstDirname: String, f: String => T): T = {

    def extractIntoTemporaryDirectory(filename: String, tempDirPathName: String): Unit = {
      val start = System.currentTimeMillis
      new ZipArchive(filename).unzip(tempDirPathName)
      logger.info("Unzipping completed in " + (System.currentTimeMillis - start) + "ms.")
    }

    def removeTemporaryDirectory(tempDir: File): Unit = {
      try if (tempDir != null) FileUtils.deleteDirectory(tempDir)
      catch {
        case _: IOException =>
          logger.warn("Unable to remove temporary directory: " + tempDir)
      }
    }

    var tempDir: File = null
    try {
      tempDir = Files.createTempDirectory(dstDirname).toFile
      val tempDirPathName = tempDir.getAbsolutePath
      extractIntoTemporaryDirectory(filename, tempDirPathName)
      var start = 0L
      start = System.currentTimeMillis
      f(tempDirPathName)
    } catch {
      case exception: IOException => throw new RuntimeException(exception)
    } finally {
      removeTemporaryDirectory(tempDir)
    }

  }

  /**
    * Load code property graph from directory containing protobuf bin files.
    * This is the directory obtained by extracting a CPG zip archive.
    * */
  def loadFromProtobufDirectory(inputDirectory: String, config: CpgLoaderConfig): Cpg = {
    val onDiskOverflowConfig = config.onDiskOverflowConfig
      .map { c =>
        Optional.ofNullable(c)
      }
      .getOrElse(Optional.empty())
    val builder = new ProtoToCpg(onDiskOverflowConfig)

    def matchesPattern(file: String): Boolean = {
      config.patterns.isEmpty || config.patterns.exists { p: String =>
        file.matches(p)
      }
    }

    getFileNamesInDirectory(new File(inputDirectory)).iterator.asScala
      .filter(matchesPattern)
      .foreach { file =>
        // TODO: use ".bin" extensions in proto output, and then only
        // load files with ".bin" extension here.
        val inputStream = new FileInputStream(file)
        builder.addNodes(getNextProtoCpgFromStream(inputStream).getNodeList)
        inputStream.close()
      }

    getFileNamesInDirectory(new File(inputDirectory)).iterator.asScala
      .filter(matchesPattern)
      .foreach { file =>
        // TODO: use ".bin" extensions in proto output, and then only
        // load files with ".bin" extension here.
        val inputStream = new FileInputStream(file)
        builder.addEdges(getNextProtoCpgFromStream(inputStream).getEdgeList)
        inputStream.close()
      }

    def getNextProtoCpgFromStream(inputStream: FileInputStream): CpgStruct = CpgStruct.parseFrom(inputStream)

    builder.build()
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
    val builder = new ProtoToCpg(onDiskOverflowConfig);
    try {
      builder.addEdges(consumeInputStreamNodes(builder, inputStream).asJava)
    } finally {
      closeProtoStream(inputStream);
    }
    return builder.build();
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
