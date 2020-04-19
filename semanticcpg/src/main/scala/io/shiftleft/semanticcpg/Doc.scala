package io.shiftleft.semanticcpg

import scala.annotation.StaticAnnotation

class StepsExt extends StaticAnnotation
case class Doc2(short: String, long: String = "", example: String = "") extends StaticAnnotation

