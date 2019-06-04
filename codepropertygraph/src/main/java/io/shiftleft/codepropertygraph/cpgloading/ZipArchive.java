package io.shiftleft.codepropertygraph.cpgloading;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.notExists;
import static java.nio.file.Files.walkFileTree;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ZipArchive {

  private String inputFile;

  ZipArchive(String inputFile) {
    this.inputFile = inputFile;
  }

  public void unzip(String outputDirectory) throws IOException {
    if (notExists(Paths.get(outputDirectory))) {
      createDirectories(Paths.get(outputDirectory));
    }

    try (FileSystem zipFileSystem = FileSystems.newFileSystem(Paths.get(this.inputFile), null)) {
      final Path root = zipFileSystem.getRootDirectories().iterator().next();

      walkFileTree(root, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          String filename = file.toString();
          if (filename.contains("..")) {
            System.err.println("Skipping entry " + filename + " because it contains '..'");
            return FileVisitResult.CONTINUE;
          }
          Path destFile = Paths.get(outputDirectory, file.toString());
          if (destFile.getParent() != null && !Files.exists(destFile.getParent())) {
            Files.createDirectories(destFile.getParent());
          }

          try {
            copy(file, destFile, StandardCopyOption.REPLACE_EXISTING);
          } catch (DirectoryNotEmptyException ignore) {
          }
          return FileVisitResult.CONTINUE;
        }
      });
    }
  }
}
