package io.shiftleft.utils

import java.io.{InputStreamReader, Reader}
import java.nio.charset.{CharsetDecoder, CodingErrorAction}
import java.nio.file.{Files, Path}
import scala.io.{BufferedSource, Codec, Source}
import scala.jdk.CollectionConverters.IteratorHasAsScala
import scala.util.Using

object IOUtils {

  /** Creates a new UTF-8 decoder. Sadly, instances of CharsetDecoder are not thread-safe as the doc states: 'Instances
    * of this class are not safe for use by multiple concurrent threads.' (copied from:
    * [[java.nio.charset.CharsetDecoder]])
    *
    * As we are using it in a [[io.shiftleft.passes.ParallelCpgPass]] it needs to be thread-safe. Hence, we make sure to
    * create a new instance everytime.
    */
  private def createDecoder(): CharsetDecoder = {
    Codec.UTF8.decoder
      .onMalformedInput(CodingErrorAction.REPLACE)
      .onUnmappableCharacter(CodingErrorAction.REPLACE)
  }

  private val boms = Set(
    '\uefbb', // UTF-8
    '\ufeff', // UTF-16 (BE)
    '\ufffe'  // UTF-16 (LE)
  )

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
  //private def replaceUnpairedSurrogates(input: String): String =
    //input.replaceAll("[^\u0000-\uffff]", "???")

  private def replaceSurrogates(buffer: Array[Char]): Unit = {
    var i = 0
    val length = buffer.length
    while (i < length) {
      val char = buffer(i)
      if ((char >= '\uD800' && char <= '\uDBFF') || (char >= '\uDC00' && char <= '\uDFFF')) {
        buffer(i) = '?'
      }
      i += 1
    }
  }

  private def contentFromBufferedSource(bufferedSource: BufferedSource): Seq[String] = {
    val reader = bufferedSource.bufferedReader()
    skipBOMIfPresent(reader)
    reader.lines().iterator().asScala.toSeq
  }

  private def bufferedSourceFromFile(path: Path): BufferedSource = {
    Source.fromFile(path.toFile)(createDecoder())
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
  @deprecated("Please use readFile instead.")
  def readLinesInFile(path: Path): Seq[String] =
    Using.resource(bufferedSourceFromFile(path)) { bufferedSource =>
      contentFromBufferedSource(bufferedSource)
    }

  def readFile(path: Path): String = {
    val readLen = 2 << 14
    val fileLen = Files.size(path)
    if (fileLen > Int.MaxValue) {
      throw new RuntimeException(s"File $path is to big. Size: $fileLen")
    }
    var readPos = 0
    var readBuffer = new Array[Char](Math.max(readLen, fileLen.toInt) + readLen)
    Using.resource(new InputStreamReader(Files.newInputStream(path), createDecoder())) { bufferedReader =>
      var bytes = 0
      while (bytes != -1) {
        if (readBuffer.length - readPos < readLen) {
          val newReadBuffer = new Array[Char](Math.min(readBuffer.length * 2L, Int.MaxValue).toInt)
          Array.copy(readBuffer, 0, newReadBuffer, 0, readPos)
          readBuffer = newReadBuffer
        }
        bytes = bufferedReader.read(readBuffer, readPos, readLen)
        if (bytes != -1) {
          readPos += bytes
        }
      }

      replaceSurrogates(readBuffer)

      val content =
        if (readPos > 0 && boms.contains(readBuffer(0))) {
          new String(readBuffer, 1, readPos - 1)
        } else {
          new String(readBuffer, 0, readPos)
        }

      content
    }

  }

  /** Reads a file at the given path and:
    *   - skips BOM if present
    *   - removes unpaired surrogates
    *   - uses UTF-8 encoding (replacing malformed and unmappable characters)
    *
    * @param path
    *   the file path
    * @return
    *   the content of file as String
    */
  def readFile(path: Path): String = {
    val readLen = 2 << 14
    val fileLen = Files.size(path)
    if (fileLen > Int.MaxValue) {
      throw new RuntimeException(s"File $path is to big. Size: $fileLen")
    }
    var readPos    = 0
    var readBuffer = new Array[Char](Math.max(readLen, fileLen.toInt) + readLen)
    Using.resource(new InputStreamReader(Files.newInputStream(path), createDecoder())) { reader =>
      var bytes = 0
      while (bytes != -1) {
        if (readBuffer.length - readPos < readLen) {
          val newReadBuffer = new Array[Char](Math.min(readBuffer.length * 2L, Int.MaxValue).toInt)
          Array.copy(readBuffer, 0, newReadBuffer, 0, readPos)
          readBuffer = newReadBuffer
        }
        bytes = reader.read(readBuffer, readPos, readLen)
        if (bytes != -1) {
          readPos += bytes
        }
      }
      val withUnpaired =
        if (readPos > 0 && boms.contains(readBuffer(0))) {
          new String(readBuffer, 1, readPos - 1)
        } else {
          new String(readBuffer, 0, readPos)
        }

      replaceUnpairedSurrogates(withUnpaired)
    }

  }

}
