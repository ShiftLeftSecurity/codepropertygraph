package io.shiftleft.utils

import gnu.trove.map.hash.THashMap

/**
  * Interface for deduplicating strings used by CPGLoader.
  * JVM with G1 GC does this automatically but it is not guaranteed to happen
  * and when performing CPU heavy calculations doing this manually ensures
  * that we won't run out of memory.
  */
trait StringInterner {
  def intern(s: String): String
}

object StringInterner {
  val DefaultMaxStringLength: Int = 1024
  val DefaultInitialSize: Int = 64 * 1024

  val noop: StringInterner = new StringInterner {
    override def intern(s: String): String = s
  }

  /** Creates a string interner that will hold strong references to the interned objects
    * and they wont be GC'ed until reference to the interner is released.
    * @param maxStringLength Maximum string length that will be considered for interning
    * @param initialSize Initial string table size
    * @return Instance of the StringInterner which uses strong references
    */
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
