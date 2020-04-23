package io.shiftleft.semanticcpg

import scala.annotation.StaticAnnotation
import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox

/**
  * Annotation used for documentation.
  *
  * @param short a one line description for the overview table
  * @param long in-depth documentation
  * @example a short example for the overview table
  * */
case class Doc(short: String, long: String = "", example: String = "") extends StaticAnnotation

object Doc {
  private lazy val mirror = runtimeMirror(this.getClass.getClassLoader)
  private lazy val mirrorToolbox = mirror.mkToolBox()

  def docByMethodName(tpe: Type): Map[String, Doc] = {
    def toDoc(annotation: Annotation): Doc =
      mirrorToolbox.eval(mirrorToolbox.untypecheck(annotation.tree)).asInstanceOf[Doc]

    tpe.members
      .filter(_.isPublic)
      .map { member =>
        val docAnnotationMaybe = member.annotations.filter(_.tree.tpe =:= typeOf[Doc]).map(toDoc).headOption
        (member.name.toString, docAnnotationMaybe)
      }.collect { case (methodName, Some(doc)) => (methodName, doc) }
      .toMap
  }

}
