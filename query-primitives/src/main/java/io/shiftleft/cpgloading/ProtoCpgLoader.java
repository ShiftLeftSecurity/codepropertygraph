package io.shiftleft.cpgloading;

import io.shiftleft.proto.cpg.Cpg.CpgStruct;
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge;
import io.shiftleft.queryprimitives.steps.starters.Cpg;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Loader for CPGs stored in Google Protobuf Format
 * */
public class ProtoCpgLoader {

  protected final Logger logger = LogManager.getLogger(getClass());
  protected final ProtoToCpg builder;

  public ProtoCpgLoader(ProtoToCpg builder) {
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

      long start = System.currentTimeMillis();
      new ZipArchive(filename).unzip(tempDirPathName);
      logger.info("Unzipping completed in " +
           (System.currentTimeMillis() - start) + "ms.");

      start = System.currentTimeMillis();
      Cpg cpg = loadFromProtobufDirectory(tempDirPathName);
      logger.info("CPG construction finished in " +
          (System.currentTimeMillis() - start) + "ms.");

      return cpg;
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    } finally {
      try {
        if (tempDir != null) {
          FileUtils.deleteDirectory(tempDir);
        }
      } catch (IOException exception) {
        logger.warn("Unable to remove temporary directory: " + tempDir);
      }
    }

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
      consumeInputStreamNodes(builder, inputStream);
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
      CpgStruct cpgStruct = consumeInputStreamNodes(builder, inputStream);
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

  private CpgStruct consumeInputStreamNodes(ProtoToCpg builder,
                                                    InputStream inputStream)
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
