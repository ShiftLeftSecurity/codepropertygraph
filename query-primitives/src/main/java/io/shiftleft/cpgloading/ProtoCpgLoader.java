package io.shiftleft.cpgloading;

import io.shiftleft.proto.cpg.Cpg.CpgStruct;
import io.shiftleft.proto.cpg.Cpg.CpgOverlay;
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge;
import io.shiftleft.queryprimitives.steps.starters.Cpg;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * Loader for CPGs stored in Google Protobuf Format
 * */
public class ProtoCpgLoader {

  protected final Logger logger = LogManager.getLogger(getClass());
  protected final ProtoToCpgBase builder;

  public ProtoCpgLoader(ProtoToCpgBase builder) {
    this.builder = builder;
  }

  /**
   * Load Code Property Graph from a zip-file containing
   * CPGs in proto format.
   **/
  public Cpg loadFromProtoZip(String filename) {
    File tempDir = null;
    try {
      tempDir = Files.createTempDirectory("cpg2sp_proto").toFile();
      String tempDirPathName = tempDir.getAbsolutePath();
      extractIntoTemporaryDirectory(filename, tempDirPathName);
      long start;
      start = System.currentTimeMillis();
      Cpg cpg = loadFromProtobufDirectory(tempDirPathName);
      logger.info("CPG construction finished in " +
          (System.currentTimeMillis() - start) + "ms.");

      return cpg;
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    } finally {
      removeTemporaryDirectory(tempDir);
    }

  }

  protected void extractIntoTemporaryDirectory(String filename, String tempDirPathName)
      throws IOException {
    long start = System.currentTimeMillis();
    new ZipArchive(filename).unzip(tempDirPathName);
    logger.info("Unzipping completed in " +
         (System.currentTimeMillis() - start) + "ms.");
  }

  protected void removeTemporaryDirectory(File tempDir) {
    try {
      if (tempDir != null) {
        FileUtils.deleteDirectory(tempDir);
      }
    } catch (IOException exception) {
      logger.warn("Unable to remove temporary directory: " + tempDir);
    }
  }

  public List<CpgOverlay> loadOverlays(String filename) {
    File tempDir = null;
    try {
      tempDir = Files.createTempDirectory("cpg2sp_proto_overlay").toFile();
      String tempDirPathName = tempDir.getAbsolutePath();
      extractIntoTemporaryDirectory(filename, tempDirPathName);
      return loadOverlaysFromProtobufDirectory(tempDirPathName);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      removeTemporaryDirectory(tempDir);
    }
    return new LinkedList<>();
  }

  protected List<CpgOverlay> loadOverlaysFromProtobufDirectory(String inputDirectory)
      throws IOException {
    List<CpgOverlay> cpgOverlays = new LinkedList<>();

    List<File> filesInDirectory = getFilesInDirectory(inputDirectory);
    filesInDirectory.sort( (file1, file2) -> {
          String[] file1Split = file1.getName().split("_");
          String[] file2Split = file2.getName().split("_");
          if (file1Split.length < 2 || file2Split.length < 2) {
            return (file1.getName().compareTo(file2.getName()));
          }
          return (Integer.parseInt(file1Split[0]) - Integer.parseInt(file2Split[0]));
        }
    );

    for (File file : getFilesInDirectory(inputDirectory)) {
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
  public Cpg loadFromProtobufDirectory(String inputDirectory)
      throws IOException {
    assert(new File(inputDirectory).isDirectory());

    for (File file : getFilesInDirectory(inputDirectory)) {
      // TODO: use ".bin" extensions in proto output, and then only
      // load files with ".bin" extension here.
      FileInputStream inputStream = new FileInputStream(file);
      consumeInputStreamNodes(inputStream);
      inputStream.close();
    }

    // add edges in a second pass, to avoid hogging them in memory (and make the import of large graphs impossible)
    for (File file : getFilesInDirectory(inputDirectory)) {
      FileInputStream inputStream = new FileInputStream(file);
      CpgStruct cpgStruct = getNextProtoCpgFromStream(inputStream);
      builder.addEdges(cpgStruct.getEdgeList());
      inputStream.close();
    }
    return builder.build();
  }

  public Cpg loadFromInputStream(InputStream inputStream) throws IOException {
    try {
      CpgStruct cpgStruct = consumeInputStreamNodes(inputStream);
      builder.addEdges(cpgStruct.getEdgeList());
    } finally {
      closeProtoStream(inputStream);
    }
    return builder.build();
  }

  private List<File> getFilesInDirectory(String inputDirectory) {
    File directory = new File(inputDirectory);
    File[] files = directory.listFiles();
    return Arrays.stream(files).filter(file -> file.isFile()).collect(Collectors.toList());
  }

  private CpgStruct consumeInputStreamNodes(InputStream inputStream)
          throws IOException {
    CpgStruct cpgStruct;
    cpgStruct = getNextProtoCpgFromStream(inputStream);
    builder.addNodes(cpgStruct);
    return cpgStruct;
  }

  /**
   * Load code property graph from a list of CPGs in proto format.
   **/
  public Cpg loadFromListOfProtos(List<CpgStruct> cpgs) {
    for (CpgStruct cpgStruct : cpgs)
      builder.addNodes(cpgStruct);
    for (CpgStruct cpgStruct : cpgs)
      builder.addEdges(cpgStruct.getEdgeList());

    return builder.build();
  }

  private CpgStruct getNextProtoCpgFromStream(InputStream inputStream)
      throws IOException {
    return CpgStruct.parseFrom(inputStream);
  }

  private void closeProtoStream(InputStream inputStream) {
    try {
      inputStream.close();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

}
