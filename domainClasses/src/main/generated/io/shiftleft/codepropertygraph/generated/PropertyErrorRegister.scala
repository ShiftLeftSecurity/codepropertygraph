package io.shiftleft.codepropertygraph.generated

object PropertyErrorRegister {
  private var errorMap = Set.empty[(Class[?], String)]
  private val logger   = org.slf4j.LoggerFactory.getLogger(getClass)

  def logPropertyErrorIfFirst(clazz: Class[?], propertyName: String): Unit = {
    if (!errorMap.contains((clazz, propertyName))) {
      logger.warn("Property " + propertyName + " is deprecated for " + clazz.getName + ".")
      errorMap += ((clazz, propertyName))
    }
  }
}
