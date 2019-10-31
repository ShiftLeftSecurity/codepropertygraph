package io.shiftleft.utils

import org.scalatest.{Matchers, WordSpec}

class HighlightedSourceTest extends WordSpec with Matchers {

  "fixes ansi encoding ending" in {
    // `int` in green

    '\u001b'
    '['
    '3'
    '2'
    'm'
    'i'
    'n'
    't'
    '\u001b'
    '['
    'm'
    '\n'

    println(fansi.Color.Blue("if").overlay(fansi.Bold.On, 0, 2).render)
//    HighlightedSource("").fixedForFansi shouldBe ""
    //.replaceAll("""\u001b\[m""", """\u001b[39m""") //encoding ends differently for fansi
  }


}


