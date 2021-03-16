package io.shiftleft.codepropertygraph.generated.edges

import java.util.{Set => JSet}
import overflowdb._
import scala.jdk.CollectionConverters._

object PropertyErrorRegister {
  private var errorMap = Set[(Class[_], String)]()
  private val logger = org.slf4j.LoggerFactory.getLogger(getClass)

  def logPropertyErrorIfFirst(clazz: Class[_], propertyName: String): Unit = {
    if (!errorMap.contains((clazz, propertyName))) {
      logger.warn("Property " + propertyName + " is deprecated for " + clazz.getName + ".")
      errorMap += ((clazz, propertyName))
    }
  }
}

object Factories {
  lazy val all: List[EdgeFactory[_]] = List(Ast.factory, Cfg.factory, ContainsNode.factory, CapturedBy.factory, BindsTo.factory, Ref.factory, Vtable.factory, Receiver.factory, Condition.factory, Argument.factory, SourceFile.factory, ParameterLink.factory, Call.factory, TaggedBy.factory, EvalType.factory, InheritsFrom.factory, Contains.factory, Propagate.factory, ReachingDef.factory, AliasOf.factory, TypeDeclAlias.factory, Binds.factory, Capture.factory, TaintRemove.factory, DynamicType.factory, Dominate.factory, PostDominate.factory, Cdg.factory, AttachedData.factory)
  lazy val allAsJava: java.util.List[EdgeFactory[_]] = all.asJava
}

