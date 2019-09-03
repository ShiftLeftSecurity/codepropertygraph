package io.shiftleft.utils

import gnu.trove.map.hash.THashMap

trait StringInterner {
  def intern(s: String): String
}

object StringInterner {
  val DefaultMaxStringLength: Int = 1024
  val DefaultInitialSize: Int = 64 * 1024

  val noop: StringInterner = new StringInterner {
    override def intern(s: String): String = s
  }
  def makeStrongInterner(maxStringLength: Int = DefaultMaxStringLength,
                         initialSize: Int = DefaultInitialSize): StringInterner = new StringInterner {
    private val stringCache = new THashMap[String, String](initialSize)

    def intern(s: String): String = {
      if (s.length < maxStringLength) {
        val cached = stringCache.get(s)
        if (cached == null) {
          stringCache.put(s, s)
          s
        } else {
          cached
        }
      } else {
        s
      }
    }
  }
}
