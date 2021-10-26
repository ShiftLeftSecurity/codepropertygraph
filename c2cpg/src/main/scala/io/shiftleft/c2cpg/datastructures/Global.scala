package io.shiftleft.c2cpg.datastructures

class Global {

  val usedTypes: Cache[String, Boolean] = new Cache()

  val headerAstCache: Cache[String, Cache[(Integer, Integer), Boolean]] = new Cache(initialCapacity = 100000)

}
