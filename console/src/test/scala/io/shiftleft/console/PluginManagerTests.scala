package io.shiftleft.console

import better.files.Dsl._
import better.files._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PluginManagerTests extends AnyWordSpec with Matchers {

  "Plugin manager" should {
    "foo" in Fixture() { manager =>
      println(manager)
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
