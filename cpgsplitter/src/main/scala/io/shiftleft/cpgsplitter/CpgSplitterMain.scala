package io.shiftleft.cpgsplitter

object CpgSplitterMain extends App {

  val splitter = new CpgSplitter()
  splitter.split("cpg.bin.zip", "out")

}
