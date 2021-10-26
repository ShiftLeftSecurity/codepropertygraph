package io.shiftleft.c2cpg.datastructures

import java.util.concurrent.ConcurrentHashMap

class Global {

  val usedTypes: ConcurrentHashMap[String, Boolean] =
    new ConcurrentHashMap()

  val headerAstCache: ConcurrentHashMap[String, ConcurrentHashMap[(Integer, Integer), Boolean]] =
    new ConcurrentHashMap()

}
