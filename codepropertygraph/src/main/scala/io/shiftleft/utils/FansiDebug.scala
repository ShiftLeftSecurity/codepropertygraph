package io.shiftleft.utils

import pprint.{Renderer, Truncated}

object FansiDebug extends App {
  def fixup(s: String) = s.replaceAll("\\[m", "[39m")
  lazy val highlighted = sys.process.Process(Seq("source-highlight-esc.sh", "/home/mp/Projects/shiftleft/joern/code2.c", "-sC")).!!
//  println(highlighted)
//  fansi.Str(highlighted) //triggers the error
  // idea: detect encoding in pprint, don't reencode if already encoded.
  // problem: must return a fansi.Str, so i have to fix it or create the fansi.Str myself -> wrap in custom type and handle pprint in additionalhandler?
//   pprint.Result.fromString(highlighted)

//  val myPPrinter = new pprint.PPrinter(defaultHeight = 99999) {
//    override def tokenize(x: Any,
//                 width: Int = defaultWidth,
//                 height: Int = defaultHeight,
//                 indent: Int = defaultIndent,
//                 initialOffset: Int = 0): Iterator[fansi.Str] = x match {
//      case s: String =>
////        println("yyy: in myPPrinter")
////        println("yyy0 " + s)
//        val fixed = s.replaceAll("\\[m", "[39m")
////        println("yyy1 " + fixed)
////        println("yyy2")
//        Iterator.single(fixed)
//      case x => super.tokenize(x, width, height, indent, initialOffset)
//    }
//  }
//   println(pprinter.tokenize(highlighted))
//   println(myPPrinter.tokenize("\u001b[31mR\u001b[39m").toList)

//  highlighted.toCharArray
//  println(fansi.Str(highlighted.replaceAll("\\[m", "[39m")))

  // difference is the final mask to 0: [39m is shortened to [m by source-highlight
//  println(fansi.Str("\u001b[31mR\u001b[m")) //this fails parsing
//  println(fansi.Str("\u001b[31mR\u001b[39m"))

  /*



  java.lang.IllegalArgumentException: Unknown ansi-escape [m at index 9 inside string cannot be parsed into an fansi.Str
  fansi.ErrorMode$Throw$.handle(Fansi.scala:419)
  fansi.ErrorMode$Throw$.handle(Fansi.scala:407)
  fansi.Str$.apply(Fansi.scala:272)
  fansi.Str$.implicitApply(Fansi.scala:227)
  pprint.Renderer.$anonfun$rec$27(Renderer.scala:136)
  pprint.Result$.fromString(Result.scala:53)
  pprint.Renderer.rec(Renderer.scala:136)
  pprint.PPrinter.tokenize(PPrinter.scala:110)
  ammonite.repl.FullReplAPI$Internal.print(FullReplAPI.scala:106)
  ammonite.repl.FullReplAPI$Internal.print$(FullReplAPI.scala:61)
  ammonite.repl.FullReplAPI$$anon$1.print(FullReplAPI.scala:34)

   */
}
