package io.shiftleft.utils

import java.io.Reader
import java.nio.charset.{CharsetDecoder, CodingErrorAction, StandardCharsets}
import java.nio.file.{Files, Path}
import scala.io.{BufferedSource, Codec, Source}
import scala.util.Using

object IOUtils {

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

  /** Skips a leading byte order mark (BOM) if present. Once decoded as UTF-8, a BOM shows up as the single character
    * U+FEFF (e.g. the UTF-8 BOM bytes EF BB BF decode to U+FEFF).
    */
  private def skipBOMIfPresent(reader: Reader): Unit = {
    reader.mark(1)
    if (reader.read() != '\ufeff') {
      reader.reset()
    }
  }

  private def contentFromBufferedSource(bufferedSource: BufferedSource): Seq[String] = {
    val reader = bufferedSource.bufferedReader()
    skipBOMIfPresent(reader)
    val lines = List.newBuilder[String]
    var line  = reader.readLine()
    while (line != null) {
      lines += line
      line = reader.readLine()
    }
    lines.result()
  }

  /** Reads a file at the given path and:
    *   - skips BOM if present
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
    *   - uses UTF-8 encoding (replacing malformed and unmappable characters)
    *
    * @param path
    *   the file path
    * @return
    *   a String with the given file's contents
    */
  def readEntireFile(path: Path): String = {
    val bytes = Files.readAllBytes(path)
    // Strip the UTF-8 BOM (EF BB BF) if present. The String constructor replaces malformed and unmappable input with
    // the default replacement character, i.e. decoding is lenient just like in readLinesInFile above.
    val offset = if (startsWithUtf8Bom(bytes)) Utf8BomLength else 0
    new String(bytes, offset, bytes.length - offset, StandardCharsets.UTF_8)
  }

  private val Utf8BomLength = 3

  private def startsWithUtf8Bom(bytes: Array[Byte]): Boolean =
    bytes.length >= Utf8BomLength &&
      bytes(0) == 0xef.toByte && bytes(1) == 0xbb.toByte && bytes(2) == 0xbf.toByte

}
