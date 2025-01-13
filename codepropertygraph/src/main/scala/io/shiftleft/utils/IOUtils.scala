package io.shiftleft.utils

import java.io.Reader
import java.nio.charset.{CharsetDecoder, CodingErrorAction}
import java.nio.file.Path
import java.util.regex.Pattern
import scala.io.{BufferedSource, Codec, Source}
import scala.jdk.CollectionConverters.IteratorHasAsScala
import scala.util.Using

object IOUtils {

  private val boms: Set[Char] = Set(
    '\uefbb', // UTF-8
    '\ufeff', // UTF-16 (BE)
    '\ufffe'  // UTF-16 (LE)
  )

  /** Creates a new UTF-8 decoder. Sadly, instances of CharsetDecoder are not thread-safe as the doc states: 'Instances
    * of this class are not safe for use by multiple concurrent threads.' (copied from:
    * [[java.nio.charset.CharsetDecoder]])
    *
    * As we are using it in a [[io.shiftleft.passes.ForkJoinParallelCpgPass]] it needs to be thread-safe. Hence, we make
    * sure to create a new instance everytime.
    */
  private def createDecoder(): CharsetDecoder =
    Codec.UTF8.decoder
      .onMalformedInput(CodingErrorAction.REPLACE)
      .onUnmappableCharacter(CodingErrorAction.REPLACE)

  private def skipBOMIfPresent(reader: Reader): Unit = {
    reader.mark(1)
    val possibleBOM = new Array[Char](1)
    reader.read(possibleBOM)
    if (!boms.contains(possibleBOM(0))) {
      reader.reset()
    }
  }

  private def contentFromBufferedSource(bufferedSource: BufferedSource): Seq[String] = {
    val reader = bufferedSource.bufferedReader()
    skipBOMIfPresent(reader)
    reader.lines().iterator().asScala.toSeq
  }

  private def contentStringFromBufferedSource(bufferedSource: BufferedSource): String = {
    val reader        = bufferedSource.bufferedReader()
    val stringBuilder = new StringBuilder
    val bufferSize    = 1024
    var productive    = true

    skipBOMIfPresent(reader)
    while (productive) {
      val buffer = new Array[Char](bufferSize)
      val read   = reader.read(buffer)
      productive = read > 0
      if (productive) {
        stringBuilder.appendAll(buffer, 0, read)
      }
    }

    stringBuilder.toString
  }

  /** Reads a file at the given path and:
    *   - skips BOM if present
    *   - removes unpaired surrogates
    *   - uses UTF-8 encoding (replacing malformed and unmappable characters)
    *
    * @param path
    *   the file path
    * @return
    *   a Seq with all lines in the given file as Strings
    */
  def readLinesInFile(path: Path): Seq[String] =
    Using.resource(Source.fromFile(path.toFile)(createDecoder()))(contentFromBufferedSource)

  /** Reads a file at the given path and:
    *   - skips BOM if present
    *   - removes unpaired surrogates
    *   - uses UTF-8 encoding (replacing malformed and unmappable characters)
    *
    * @param path
    *   the file path
    * @return
    *   a String with the given file's contents
    */
  def readEntireFile(path: Path): String =
    Using.resource(Source.fromFile(path.toFile)(createDecoder()))(contentStringFromBufferedSource)

}
