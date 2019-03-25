package io.shiftleft.cpgloading

import io.shiftleft.cpgloading.Defaults.DEFAULT_CACHE_HEAP_PERCENTAGE

/** configure graphdb to use ondisk overflow. by default, system tmp directory is used (e.g. `/tmp`)  */
case class OnDiskOverflowConfig(cacheHeapPercentage: Float = DEFAULT_CACHE_HEAP_PERCENTAGE,
                                alternativeParentDirectory: Option[String] = None)
