package io.shiftleft.console

import better.files.Dsl._
import better.files._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PluginManagerTests extends AnyWordSpec with Matchers {

  "PluginManager::add" should {
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
      manager.pluginDir match {
        case Some(dir) =>
          dir.toFile.list().toList shouldBe List("test-foo.jar")
        case None => fail
      }
    }
  }

}

object Fixture {

  def apply[T]()(f: PluginManager => T): T = {
    val dir = File.newTemporaryDirectory("pluginmantests")
    mkdir(dir / "lib")
    val result = f(new PluginManager(dir))
    dir.delete()
    result
  }
}
