package org.example

import io.shiftleft.cpgloading.tinkergraph.CpgLoader

object Main extends App {
  val cpg = CpgLoader.loadFromFile("path/to/cpg.bin.zip", runEnhancements = true)

  // Print all methods starting with "<operator>"
  cpg.method.name("<operator>.*").p

  // Store all methods in local list for further processing.
  val someMethods = cpg.method.l

}
