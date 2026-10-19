package io.shiftleft.utils

import scala.jdk.OptionConverters.RichOptional

/** Resolves files via the Bazel runfiles lookup. Only meant for tests inside of this repository. The sbt build always
  * returns None, so callers need to fall back to [[ProjectRoot.relativise]].
  */
private[shiftleft] object CpgRunfilesLocator {
  def resolve(path: String): Option[String] =
    CpgRunfilesLocatorJava.resolve(path).toScala
}
