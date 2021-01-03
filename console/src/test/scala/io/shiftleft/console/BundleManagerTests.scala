package io.shiftleft.console

import better.files.Dsl._
import better.files._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BundleManagerTests extends AnyWordSpec with Matchers {

  "add" should {
    "not crash if file does not exist" in Fixture() { manager =>
      val testZipFileName = "console/src/test/resources/doesnotexist.zip"
      manager.add(testZipFileName)
    }

    "not crash if file isn't a valid zip" in Fixture() { manager =>
      val testZipFileName = "console/src/test/resources/nonzip.zip"
      manager.add(testZipFileName)
    }

    "copy jar files in zip to plugin dir" in Fixture() { manager =>
      val testZipFileName = "console/src/test/resources/test.zip"
      manager.add(testZipFileName)
      manager.libDir match {
        case Some(dir) =>
          dir.toFile.list().toList shouldBe List("joernext-test-foo.jar")
        case None => fail
      }
    }
  }

  "rm" should {

    "not crash if name of to-be-removed plugin is incorrect" in Fixture() { manager =>
      manager.rm("somename")
    }

    "remove existing plugin" in Fixture() { manager =>
      val testZipFileName = "console/src/test/resources/test.zip"
      manager.add(testZipFileName)
      manager.rm("test") shouldBe List("joernext-test-foo.jar")
      manager.list() shouldBe List()
      manager.add(testZipFileName)
      manager.rm("test") shouldBe List("joernext-test-foo.jar")
      manager.list() shouldBe List()
    }

  }

  "list" should {

    "display empty plugin list if no plugins exist" in Fixture() { manager =>
      manager.list() shouldBe List()
    }

    "display plugin after adding it" in Fixture() { manager =>
      val testZipFileName = "console/src/test/resources/test.zip"
      manager.add(testZipFileName)
      manager.list() shouldBe List("test")
    }
  }

}

object Fixture {

  def apply[T]()(f: BundleManager => T): T = {
    val dir = File.newTemporaryDirectory("pluginmantests")
    mkdir(dir / "lib")
    val result = f(new BundleManager(dir))
    dir.delete()
    result
  }
}
