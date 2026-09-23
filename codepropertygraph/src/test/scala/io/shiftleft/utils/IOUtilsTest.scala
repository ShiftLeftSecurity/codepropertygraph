package io.shiftleft.utils

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path}

class IOUtilsTest extends AnyWordSpec with Matchers {

  private val Utf8Bom = Array(0xef.toByte, 0xbb.toByte, 0xbf.toByte)

  private def withTempFile(content: Array[Byte])(f: Path => Unit): Unit = {
    val path = Files.createTempFile("io-utils-test", ".tmp")
    try {
      Files.write(path, content)
      f(path)
    } finally {
      Files.deleteIfExists(path)
    }
  }

  private def utf8(s: String): Array[Byte] = s.getBytes(StandardCharsets.UTF_8)

  "IOUtils.readLinesInFile" should {
    "read all lines" in {
      withTempFile(utf8("foo\nbar\r\nbaz")) { path =>
        IOUtils.readLinesInFile(path) shouldBe Seq("foo", "bar", "baz")
      }
    }

    "skip a UTF-8 BOM if present" in {
      withTempFile(Utf8Bom ++ utf8("foo\nbar")) { path =>
        IOUtils.readLinesInFile(path) shouldBe Seq("foo", "bar")
      }
    }

    "replace malformed UTF-8 input instead of throwing" in {
      // 0xC3 starts a two-byte sequence but 0x28 ('(') is not a valid continuation byte
      withTempFile(utf8("foo") ++ Array(0xc3.toByte, 0x28.toByte)) { path =>
        IOUtils.readLinesInFile(path) shouldBe Seq("foo\ufffd(")
      }
    }

    "not strip a leading character that merely encodes like a BOM" in {
      // EF AE BB is the genuine UTF-8 encoding of U+FBBB (a regular character, not a BOM)
      withTempFile(Array(0xef.toByte, 0xae.toByte, 0xbb.toByte)) { path =>
        IOUtils.readLinesInFile(path) shouldBe Seq("\ufbbb")
      }
    }

    "return an empty Seq for an empty file" in {
      withTempFile(Array.emptyByteArray) { path =>
        IOUtils.readLinesInFile(path) shouldBe empty
      }
    }
  }

  "IOUtils.readEntireFile" should {
    "read the entire content" in {
      withTempFile(utf8("foo\nbar\n")) { path =>
        IOUtils.readEntireFile(path) shouldBe "foo\nbar\n"
      }
    }

    "skip a UTF-8 BOM if present" in {
      withTempFile(Utf8Bom ++ utf8("foo")) { path =>
        IOUtils.readEntireFile(path) shouldBe "foo"
      }
    }

    "replace malformed UTF-8 input instead of throwing" in {
      withTempFile(Array(0xc3.toByte, 0x28.toByte)) { path =>
        IOUtils.readEntireFile(path) shouldBe "\ufffd("
      }
    }

    "not strip a leading character that merely encodes like a BOM" in {
      // EF AE BB is the genuine UTF-8 encoding of U+FBBB (a regular character, not a BOM)
      withTempFile(Array(0xef.toByte, 0xae.toByte, 0xbb.toByte)) { path =>
        IOUtils.readEntireFile(path) shouldBe "\ufbbb"
      }
    }

    "return an empty String for an empty file" in {
      withTempFile(Array.emptyByteArray) { path =>
        IOUtils.readEntireFile(path) shouldBe ""
      }
    }
  }

}
