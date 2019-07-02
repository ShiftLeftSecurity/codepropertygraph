package io.shiftleft.codepropertygraph.cpgloading;

import static java.nio.file.Files.walkFileTree;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;

public class ZipArchive implements Closeable {

  private final FileSystem zipFileSystem;

  ZipArchive(String inputFile) throws IOException {
    this.zipFileSystem = FileSystems.newFileSystem(Paths.get(inputFile), null);
  }

  public Collection<Path> getFileEntries() throws IOException {
    final Path root = zipFileSystem.getRootDirectories().iterator().next();
    ArrayList<Path> entries = new ArrayList<>();

    walkFileTree(root, new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (attrs.isRegularFile()) {
          entries.add(file);
        }
        return FileVisitResult.CONTINUE;
      }
    });

    return entries;
  }

  @Override
  public void close() throws IOException {
    zipFileSystem.close();
  }
}
