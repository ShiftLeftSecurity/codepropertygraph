package io.shiftleft.c2cpg.passes

import java.util.concurrent.ConcurrentHashMap

case class Global(usedTypes: ConcurrentHashMap[String, Boolean] = new ConcurrentHashMap[String, Boolean]())
