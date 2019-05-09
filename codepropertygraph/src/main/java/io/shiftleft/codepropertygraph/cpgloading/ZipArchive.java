package io.shiftleft.codepropertygraph.cpgloading;

import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.notExists;
import static java.nio.file.Files.walkFileTree;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
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
          Path destFile = Paths.get(outputDirectory.toString(), file.toString());
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
