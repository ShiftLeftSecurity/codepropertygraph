package io.shiftleft.utils

import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.util.Calendar

class TimeMetricTest extends AnyWordSpec with Matchers with BeforeAndAfterAll {

  "Millisecond String format " should {
    "Millisecond" in {
      val firstTime = TimeMetric.cal.getTime
      TimeMetric.cal.add(Calendar.MILLISECOND, 10)
      val newTime = TimeMetric.cal.getTime
      val str     = TimeMetric.getDiffFormatted(newTime.getTime - firstTime.getTime)
      str shouldBe "10 ms - 00h:00m:00s:10ms"

    }
    "Hours, Min, Second and Milliseconds" in {
      val firstTime = TimeMetric.cal.getTime
      TimeMetric.cal.add(Calendar.MILLISECOND, 10)
      TimeMetric.cal.add(Calendar.SECOND, 11)
      TimeMetric.cal.add(Calendar.MINUTE, 12)
      TimeMetric.cal.add(Calendar.HOUR, 13)
      val newTime = TimeMetric.cal.getTime
      val str     = TimeMetric.getDiffFormatted(newTime.getTime - firstTime.getTime)
      str shouldBe "47531010 ms - 13h:12m:11s:10ms"
    }
  }
}
