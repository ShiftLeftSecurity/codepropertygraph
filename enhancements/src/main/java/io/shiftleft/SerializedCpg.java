package io.shiftleft;

import io.shiftleft.proto.cpg.Cpg.CpgOverlay;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SerializedCpg {

  private FileSystem zipFileSystem;
  private int counter = 0;

  /**
   * Create Serialized CPG from existing file. If the file does not exist,
   * an empty Serialized CPG is created.
   * */
  public SerializedCpg(String filename) throws URISyntaxException, IOException {
    initZipFilesystem(filename);
  }

  /**
   * We allow creating a dummy serialized CPG that does not do anything.
   * */

  public SerializedCpg() { }

  private void initZipFilesystem(String filename) throws URISyntaxException, IOException {
    Map<String, String> env = new HashMap<>();
    // This ensures that the file is created if it does not exist
    env.put("create", "true");
    URI outputUri = new URI("jar:file", null, new File(filename).getAbsolutePath(), null);
    zipFileSystem = FileSystems.newFileSystem(outputUri, env);
  }

  /**
   * Add overlay graph named `name` to the zip file
   * */
  public void addOverlay(CpgOverlay overlay, String name) throws IOException {

    if (zipFileSystem == null) {
      return;
    }

    Path pathInZip = zipFileSystem.getPath(counter + "_" + name);
    counter++;
    OutputStream outputStream = Files.newOutputStream(pathInZip);
    overlay.writeTo(outputStream);
    outputStream.close();
  }

  public void close() throws IOException {
    if (zipFileSystem == null) {
      return;
    }
    zipFileSystem.close();
  }

}
