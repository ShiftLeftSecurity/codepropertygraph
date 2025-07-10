package io.shiftleft

import io.shiftleft.Implicits.*
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable.ArrayBuffer

class ImplicitsTests extends AnyWordSpec with Matchers {

  "loneElement returns the one and only element from an Iterable, and throws an exception otherwise" in {
    Seq(1).loneElement shouldBe 1
    Seq(1).loneElement("some context") shouldBe 1
    Seq(null).loneElement shouldBe null

    intercept[NoSuchElementException] {
      Seq.empty.loneElement
    }.getMessage should include("it is empty")

    intercept[NoSuchElementException] {
      Seq.empty.loneElement("some context")
    }.getMessage should include("it is empty. Hint: some context")

    intercept[AssertionError] {
      Seq(1, 2).loneElement
    }.getMessage should include("it has more than one")

    intercept[AssertionError] {
      ArrayBuffer(1, 2).loneElement
    }.getMessage should include(
      "it has 2"
    ) // ArrayBuffer can 'cheaply' compute their size, so we can have it in the exception message

    intercept[AssertionError] {
      Seq(1, 2).loneElement("some context")
    }.getMessage should include("it has more than one. Hint: some context")
  }

  "loneElementOption returns an Option of the one and only element from an Iterable, or else None" in {
    Seq(1).loneElementOption shouldBe Some(1)
    Seq(1).loneElementOption("some context") shouldBe Some(1)
    Seq(null).loneElementOption shouldBe Some(null)
    Seq(null).loneElementOption("some context") shouldBe Some(null)

    Seq.empty.loneElementOption shouldBe None
    Seq.empty.loneElementOption("some context") shouldBe None

    Seq(1, 2).loneElementOption shouldBe None
    Seq(1, 2).loneElementOption("some context") shouldBe None
  }

}
