package io.shiftleft.console
import better.files.File

/**
  * Plugin management component
  * @param installDir the Joern/Ocular installation dir
  * */
class PluginManager(installDir: File) {

  println(installDir)

  def listPlugins(): Unit = {
    ???
  }

  def add(filename: String): Unit = {
    ???
  }

  def rm(name: String): Unit = {
    ???
  }

}
