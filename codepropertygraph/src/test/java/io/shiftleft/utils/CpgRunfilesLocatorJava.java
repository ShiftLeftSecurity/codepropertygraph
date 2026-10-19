package io.shiftleft.utils;

import java.io.IOException;
import java.util.Optional;

// This class is only used in the sbt build where the runfiles location is not a
// build concept and thus handled on a case by case basis.
// The sbt implementation just returns empty to indicate that the runfiles
// cannot be retrieved.
class CpgRunfilesLocatorJava {
  static Optional<String> resolve(String path) throws IOException {
    return Optional.empty();
  }
}
