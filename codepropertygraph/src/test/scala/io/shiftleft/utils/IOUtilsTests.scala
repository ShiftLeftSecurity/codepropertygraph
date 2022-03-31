package io.shiftleft.utils

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.nio.charset.{StandardCharsets}
import java.nio.file.Files

class IOUtilsTests extends AnyWordSpec with Matchers {
  class Fixture {
    def test(inputData: String, compareData: Option[String] = None): Unit = {
      val tmpFile = Files.createTempFile("IOUtilsTests", "")
      try {
        Files.write(tmpFile, inputData.getBytes(StandardCharsets.UTF_8))

        val readData = IOUtils.readFile(tmpFile)
        readData shouldBe compareData.getOrElse(inputData)
      } finally {
        Files.delete(tmpFile)
      }
    }
  }

  "readFile tests" in new Fixture() {
    test("")
    test("abc")
    test("\uefbbabc", Some("abc"))
    test("\ufeffabc", Some("abc"))
    test("\ufffeabc", Some("abc"))

    val strBuilder = new StringBuilder()
    for (_ <- 0 until(100000)) strBuilder.append("abc")

    test(strBuilder.toString())
  }

  "surrogate replacement tests" in new Fixture() {
    test(new String(Character.toChars(0x10000)), Some("??"))
    test(new String(Character.toChars(0x10FFFF)), Some("??"))
  }

}
