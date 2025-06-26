package io.shiftleft.utils

import java.nio.file.Path
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class TempFileCopy(sourcePath: Path, prefix: String = "resource", suffix: String = ".tmp") extends AutoCloseable {
  val path: Path = {
    val temp = Files.createTempFile(prefix, suffix)
    Files.copy(sourcePath, temp, StandardCopyOption.REPLACE_EXISTING)
    temp
  }

  override def close() = Files.deleteIfExists(path)
}
