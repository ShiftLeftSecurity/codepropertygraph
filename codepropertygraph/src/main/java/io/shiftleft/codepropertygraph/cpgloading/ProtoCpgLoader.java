package io.shiftleft.codepropertygraph.cpgloading;

import io.shiftleft.codepropertygraph.Cpg;
import io.shiftleft.proto.cpg.Cpg.CpgOverlay;
import io.shiftleft.proto.cpg.Cpg.CpgStruct;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Loader for CPGs stored in Google Protobuf Format
 */
public class ProtoCpgLoader {
  private static final Logger logger = LogManager.getLogger(ProtoCpgLoader.class);

  public static Cpg loadFromProtoZip(String filename) {
    return loadFromProtoZip(filename, OverflowDbConfig.withDefaults());
  }

  /**
   * Load Code Property Graph from a zip-file containing
   * CPGs in proto format.
   */
  public static Cpg loadFromProtoZip(
          String filename,
          OverflowDbConfig overflowDbConfig) {
    try {
      ProtoToCpg builder = new ProtoToCpg(overflowDbConfig);

      long start;
      start = System.currentTimeMillis();

      try (ZipArchive zip = new ZipArchive(filename)) {
        for (Path entry: zip.getFileEntries()) {
          InputStream inputStream = Files.newInputStream(entry);
          builder.addNodes(getNextProtoCpgFromStream(inputStream).getNodeList());
          inputStream.close();
        }
      }

      /** second pass so we can stream for the edges
       * -> holding them all in memory is potentially too much
       * -> adding them as we go isn't an option because we may only have one of the adjacent vertices */
      try (ZipArchive zip = new ZipArchive(filename)) {
        for (Path entry: zip.getFileEntries()) {
          InputStream inputStream = Files.newInputStream(entry);
          builder.addEdges(getNextProtoCpgFromStream(inputStream).getEdgeList());
          inputStream.close();
        }
      }

      Cpg cpg = builder.build();
      logger.info("CPG construction finished in " +
          (System.currentTimeMillis() - start) + "ms.");

      return cpg;
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  public static List<CpgOverlay> loadOverlays(String filename) {
    try (ZipArchive zip = new ZipArchive(filename)) {
      List<CpgOverlay> cpgOverlays = new ArrayList<>();

      List<Path> filesInDirectory = new ArrayList<>(zip.getFileEntries());
      filesInDirectory.sort( (path1, path2) -> {
            String[] file1Split = path1.toString().replace("/", "").split("_");
            String[] file2Split = path2.toString().replace("/", "").split("_");
            if (file1Split.length < 2 || file2Split.length < 2) {
              return (path1.toString().compareTo(path2.toString()));
            }
            return (Integer.parseInt(file1Split[0]) - Integer.parseInt(file2Split[0]));
          }
      );

      for (Path path : filesInDirectory) {
        InputStream inputStream = Files.newInputStream(path);
        CpgOverlay cpgOverlay = CpgOverlay.parseFrom(inputStream);
        inputStream.close();
        cpgOverlays.add(cpgOverlay);
      }
      return cpgOverlays;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  /**
   * Load code property graph from a list of CPGs in proto format.
   **/
  public static Cpg loadFromListOfProtos(
          List<CpgStruct> cpgs,
          OverflowDbConfig overflowDbConfig) {
    ProtoToCpg builder = new ProtoToCpg(overflowDbConfig);

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

}
