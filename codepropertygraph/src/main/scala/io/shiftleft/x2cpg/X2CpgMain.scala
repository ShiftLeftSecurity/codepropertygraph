package io.shiftleft.x2cpg

import scopt.OParser

trait X2CpgConfig[R] {
  def withAdditionalInputPath(inputPath: String): R
  def withOutputPath(x: String): R
}

object X2CpgMain {

  def parser[R <: X2CpgConfig[R]]: OParser[_, R] = {
    val builder = OParser.builder[R]
    import builder._
    OParser.sequence(
      arg[String]("input-dirs")
        .unbounded()
        .text("list of source files and/or source directories")
        .action((x, c) => c.withAdditionalInputPath(x)),
      opt[String]("output")
        .abbr("o")
        .text("output filename")
        .action { (x, c) =>
          c.withOutputPath(x)
        },
      help("help").text("display this help message")
    )
  }

}

trait X2CpgMain[T <: X2CpgConfig[_]] {

  def run(config: T): Unit

  def main(args: Array[String], parser: OParser[_, T], init: T): Unit = {
    OParser.parse(parser, args, init) match {
      case Some(config) =>
        run(config)
      case _ =>
        OParser.usage(parser)
        System.exit(1)
    }
  }

}
