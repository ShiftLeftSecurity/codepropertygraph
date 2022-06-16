package io.shiftleft.utils

import java.io.Reader
import java.nio.charset.{CharsetDecoder, CodingErrorAction}
import java.nio.file.Path
import java.util.regex.Pattern
import scala.io.{BufferedSource, Codec, Source}
import scala.jdk.CollectionConverters.IteratorHasAsScala
import scala.util.Using

object IOUtils {

  private val surrogatePattern: Pattern = Pattern.compile("[^\u0000-\uffff]")

  private val boms: Set[Char] = Set(
    '\uefbb', // UTF-8
    '\ufeff', // UTF-16 (BE)
    '\ufffe'  // UTF-16 (LE)
  )

  /** Creates a new UTF-8 decoder. Sadly, instances of CharsetDecoder are not thread-safe as the doc states: 'Instances
    * of this class are not safe for use by multiple concurrent threads.' (copied from:
    * [[java.nio.charset.CharsetDecoder]])
    *
    * As we are using it in a [[io.shiftleft.passes.ForkJoinParallelCpgPass]] or
    * [[io.shiftleft.passes.ConcurrentWriterCpgPass]] a it needs to be thread-safe. Hence, we make sure to create a new
    * instance everytime.
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

  /** Java strings are stored as sequences of 16-bit chars, but what they represent is sequences of unicode characters.
    * In unicode terminology, they are stored as code units, but model code points. Thus, it's somewhat meaningless to
    * talk about removing surrogates, which don't exist in the character / code point representation (unless you have
    * rogue single surrogates, in which case you have other problems). Rather, what you want to do is to remove any
    * characters which will require surrogates when encoded. That means any character which lies beyond the basic
    * multilingual plane. You can do that with a simple regular expression.
    */
  private def replaceUnpairedSurrogates(input: String): String =
    surrogatePattern.matcher(input).replaceAll("???")

  private def contentFromBufferedSource(bufferedSource: BufferedSource): Seq[String] = {
    val reader = bufferedSource.bufferedReader()
    skipBOMIfPresent(reader)
    reader.lines().iterator().asScala.map(replaceUnpairedSurrogates).toSeq
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

}
