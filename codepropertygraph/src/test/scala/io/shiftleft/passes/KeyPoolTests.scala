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
