package io.shiftleft.passes

import org.scalatest.{Matchers, WordSpec}

class KeyPoolTests extends WordSpec with Matchers {

  "IntervalKeyPool" should {
    "return [first, ..., last] and then raise" in {
      val keyPool = new IntervalKeyPool(10, 19)
      List.range(0, 10).map(_ => keyPool.next) shouldBe List.range(10, 20)
      assertThrows[RuntimeException] { keyPool.next }
      assertThrows[RuntimeException] { keyPool.next }
    }

    "allow splitting into multiple pools" in {
      val keyPool = new IntervalKeyPool(1, 1000)
      val pools = keyPool.split(11).toList
      assertThrows[IllegalStateException] { keyPool.next }
      pools.size shouldBe 11
      // Pools should all have the same size
      pools
        .map { x =>
          (x.last - x.first)
        }
        .distinct
        .size shouldBe 1
      // Pools should be pairwise disjoint
      val keySets = pools.map { x =>
        (x.first to x.last).toSet
      }
      keySets.combinations(2).foreach {
        case List(x: Set[Long], y: Set[Long]) =>
          x.intersect(y).isEmpty shouldBe true
      }
    }

  }

  "SequenceKeyPool" should {
    "return elements of sequence one by one and then raise" in {
      val seq = List[Long](1, 2, 3)
      val keyPool = new SequenceKeyPool(seq)
      List.range(0, 3).map(_ => keyPool.next) shouldBe seq
      assertThrows[RuntimeException] { keyPool.next }
      assertThrows[RuntimeException] { keyPool.next }
    }
  }

}
