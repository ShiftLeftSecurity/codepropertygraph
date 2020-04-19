package io.shiftleft.console

import scala.annotation.StaticAnnotation

/**
  * Annotation used for documentation.
  *
  * @param short a one line description for the overview table
  * @param long in-depth documentation
  * @example a short example for the overview table
  * */
case class Doc(short: String, long: String = "", example: String = "") extends StaticAnnotation {}