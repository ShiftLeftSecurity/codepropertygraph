package io.shiftleft.c2cpg.datastructures

import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters._

object Cache {
  val DEFAULT_INITIAL_CAPACITY = 100
  val DEFAULT_CONCURRENCY_LEVEL = 16
  val DEFAULT_LOAD_FACTOR = 0.75f
}

class Cache[K, V](initialCapacity: Int = Cache.DEFAULT_INITIAL_CAPACITY,
                  loadFactor: Float = Cache.DEFAULT_LOAD_FACTOR,
                  concurrencyLevel: Int = Cache.DEFAULT_CONCURRENCY_LEVEL) {

  private val cache = new ConcurrentHashMap[K, V](initialCapacity, loadFactor, concurrencyLevel)

  def putIfAbsent(key: K, value: V): Unit = {
    cache.putIfAbsent(key, value)
  }

  def computeIfAbsent(key: K, value: => V): V = {
    cache.computeIfAbsent(key, _ => value)
  }

  def keys(): Seq[K] = cache.keys.asScala.toSeq

  def isEmpty: Boolean = cache.isEmpty

  def nonEmpty: Boolean = !isEmpty

  def clear(): Unit = cache.clear()

}
