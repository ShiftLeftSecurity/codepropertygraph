package io.shiftleft.utils;

import com.google.devtools.build.runfiles.AutoBazelRepository;
import com.google.devtools.build.runfiles.Runfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

// This class is only part of the Bazel build.
@AutoBazelRepository
class CpgRunfilesLocatorJava {
  static Optional<String> resolve(String path) throws IOException {
    // The AutoBazelRepository_CpgRunfilesLocatorJava class is auto generated based on the @AutoBazelRepository
    // annotation.
    Runfiles runfiles = Runfiles.preload().withSourceRepository(AutoBazelRepository_CpgRunfilesLocatorJava.NAME);
    String resolvedPath = runfiles.rlocation(path);
    if (resolvedPath != null && Files.exists(Path.of(resolvedPath))) {
      return Optional.of(resolvedPath);
    } else {
      return Optional.empty();
    }
  }
}
