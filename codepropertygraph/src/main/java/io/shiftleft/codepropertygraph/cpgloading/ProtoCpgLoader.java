package io.shiftleft.codepropertygraph.cpgloading;

import io.shiftleft.codepropertygraph.Cpg;
import io.shiftleft.proto.cpg.Cpg.CpgOverlay;
import io.shiftleft.proto.cpg.Cpg.CpgStruct;
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Loader for CPGs stored in Google Protobuf Format
 */
public class ProtoCpgLoader {
  private static final Logger logger = LogManager.getLogger(ProtoCpgLoader.class);

  public static Cpg loadFromProtoZip(String filename) {
    return loadFromProtoZip(filename, Optional.empty());
  }

  /**
   * Load Code Property Graph from a zip-file containing
   * CPGs in proto format.
   */
  public static Cpg loadFromProtoZip(
    String filename,
    Optional<OnDiskOverflowConfig> onDiskOverflowConfig) {
    File tempDir = null;
    try {
      tempDir = Files.createTempDirectory("cpg2sp_proto").toFile();
      String tempDirPathName = tempDir.getAbsolutePath();
      extractIntoTemporaryDirectory(filename, tempDirPathName);
      long start;
      start = System.currentTimeMillis();
      Cpg cpg = ProtoCpgLoader.loadFromProtobufDirectory(tempDirPathName, onDiskOverflowConfig);
      logger.info("CPG construction finished in " +
          (System.currentTimeMillis() - start) + "ms.");

      return cpg;
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    } finally {
      removeTemporaryDirectory(tempDir);
    }
  }

  private static void extractIntoTemporaryDirectory(String filename, String tempDirPathName)
      throws IOException {
    long start = System.currentTimeMillis();
    new ZipArchive(filename).unzip(tempDirPathName);
    logger.info("Unzipping completed in " +
         (System.currentTimeMillis() - start) + "ms.");
  }

  private static void removeTemporaryDirectory(File tempDir) {
    try {
      if (tempDir != null) {
        FileUtils.deleteDirectory(tempDir);
      }
    } catch (IOException exception) {
      logger.warn("Unable to remove temporary directory: " + tempDir);
    }
  }

  public static List<CpgOverlay> loadOverlays(String filename) {
    File tempDir = null;
    try {
      tempDir = Files.createTempDirectory("cpg2sp_proto_overlay").toFile();
      String tempDirPathName = tempDir.getAbsolutePath();
      extractIntoTemporaryDirectory(filename, tempDirPathName);
      return ProtoCpgLoader.loadOverlaysFromProtobufDirectory(tempDirPathName);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      removeTemporaryDirectory(tempDir);
    }
    return new ArrayList<>();
  }

  private static List<CpgOverlay> loadOverlaysFromProtobufDirectory(String inputDirectory)
      throws IOException {
    List<CpgOverlay> cpgOverlays = new ArrayList<>();

    List<File> filesInDirectory = getFilesInDirectory(new File(inputDirectory));
    filesInDirectory.sort( (file1, file2) -> {
          String[] file1Split = file1.getName().split("_");
          String[] file2Split = file2.getName().split("_");
          if (file1Split.length < 2 || file2Split.length < 2) {
            return (file1.getName().compareTo(file2.getName()));
          }
          return (Integer.parseInt(file1Split[0]) - Integer.parseInt(file2Split[0]));
        }
    );

    for (File file : filesInDirectory) {
      FileInputStream inputStream = new FileInputStream(file);
      CpgOverlay cpgOverlay = CpgOverlay.parseFrom(inputStream);
      inputStream.close();
      cpgOverlays.add(cpgOverlay);
    }
    return cpgOverlays;
  }


  /**
   * Load Code Property Graph from a directory containing
   * CPGs in proto format.
   **/
  public static Cpg loadFromProtobufDirectory(
    String inputDirectory,
    Optional<OnDiskOverflowConfig> onDiskOverflowConfig)
      throws IOException {
    ProtoToCpg builder = new ProtoToCpg(onDiskOverflowConfig);
    for (File file : getFilesInDirectory(new File(inputDirectory))) {
      // TODO: use ".bin" extensions in proto output, and then only
      // load files with ".bin" extension here.
      FileInputStream inputStream = new FileInputStream(file);
      builder.addNodes(getNextProtoCpgFromStream(inputStream).getNodeList());
      inputStream.close();
    }

    /** second pass so we can stream for the edges
     * -> holding them all in memory is potentially too much
     * -> adding them as we go isn't an option because we may only have one of the adjacent vertices */
    for (File file : getFilesInDirectory(new File(inputDirectory))) {
      // TODO: use ".bin" extensions in proto output, and then only
      // load files with ".bin" extension here.
      FileInputStream inputStream = new FileInputStream(file);
      builder.addEdges(getNextProtoCpgFromStream(inputStream).getEdgeList());
      inputStream.close();
    }

    return builder.build();
  }

  public static Cpg loadFromInputStream(
    InputStream inputStream,
    Optional<OnDiskOverflowConfig> onDiskOverflowConfig) throws IOException {
    ProtoToCpg builder = new ProtoToCpg(onDiskOverflowConfig);
    try {
      consumeInputStream(builder, inputStream);
    } finally {
      closeProtoStream(inputStream);
    }
    return builder.build();
  }

  private static List<File> getFilesInDirectory(File directory) throws IOException {
    return Files.walk(directory.toPath())
            .filter(Files::isRegularFile)
            .map(Path::toFile)
            .collect(Collectors.toList());
  }

  private static List<Edge> consumeInputStreamNodes(ProtoToCpg builder,
                                                    InputStream inputStream)
          throws IOException {
    CpgStruct cpgStruct = getNextProtoCpgFromStream(inputStream);
    builder.addNodes(cpgStruct.getNodeList());
    return cpgStruct.getEdgeList();
  }

  private static void consumeInputStream(ProtoToCpg builder,
                                         InputStream inputStream)
      throws IOException {
    builder.addEdges(consumeInputStreamNodes(builder, inputStream));
  }

  /**
   * Load code property graph from a list of CPGs in proto format.
   **/
  public static Cpg loadFromListOfProtos(
    List<CpgStruct> cpgs,
    Optional<OnDiskOverflowConfig> onDiskOverflowConfig) {
    ProtoToCpg builder = new ProtoToCpg(onDiskOverflowConfig);

    for (CpgStruct cpgStruct : cpgs)
      builder.addNodes(cpgStruct.getNodeList());
    for (CpgStruct cpgStruct : cpgs)
      builder.addEdges(cpgStruct.getEdgeList());

    return builder.build();
  }

  private static CpgStruct getNextProtoCpgFromStream(InputStream inputStream)
      throws IOException {
    return CpgStruct.parseFrom(inputStream);
  }

  private static void closeProtoStream(InputStream inputStream) {
    try {
      inputStream.close();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

}
