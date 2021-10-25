package io.shiftleft.c2cpg.datastructures

import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters._

object Cache {
  val DEFAULT_INITIAL_CAPACITY = 1000
  val DEFAULT_CONCURRENCY_LEVEL = 8
  val DEFAULT_LOAD_FACTOR = 0.75f
}

class Cache[K, V](initialCapacity: Int = Cache.DEFAULT_INITIAL_CAPACITY,
                  loadFactor: Float = Cache.DEFAULT_LOAD_FACTOR,
                  concurrencyLevel: Int = Cache.DEFAULT_CONCURRENCY_LEVEL) {

  private val cache = new ConcurrentHashMap[K, LazyWrapper[V]](initialCapacity, loadFactor, concurrencyLevel).asScala

  def putIfAbsent(key: K, value: V): Option[V] = {
    cache.putIfAbsent(key, wrap(value)).map(unwrap)
  }

  def getOrElseUpdate(key: K, value: => V): V = {
    val newWrapper = wrap(value)
    cache.putIfAbsent(key, newWrapper).getOrElse(newWrapper).value
  }

  def keys(): Seq[K] = cache.keys.toSeq

  def isEmpty: Boolean = cache.isEmpty

  def nonEmpty: Boolean = cache.nonEmpty

  def clear(): Unit = {
    cache.clear()
  }

  private def wrap[T](value: => T): LazyWrapper[T] = new LazyWrapper[T](value)

  private def unwrap[T](lazyWrapper: LazyWrapper[T]): T = lazyWrapper.value
}

class LazyWrapper[T](wrapped: => T) {
  lazy val value: T = wrapped
}
