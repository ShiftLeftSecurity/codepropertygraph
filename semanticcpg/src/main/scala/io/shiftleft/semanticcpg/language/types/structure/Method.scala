package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.{Doc, Doc2, StepsExt, StepsExtJ}
import io.shiftleft.semanticcpg.language.Help.{Entry, ForNode}
import io.shiftleft.semanticcpg.language._

/**
  * A method, function, or procedure
  * */
// using java annotation so i can use org.reflections. alternative might be ClassFinder
@StepsExtJ(nodeType = classOf[nodes.Method])
//@StepsExt
class Method(val wrapped: NodeSteps[nodes.Method]) extends AnyVal {
  private def raw: GremlinScala[nodes.Method] = wrapped.raw

  /**
    * Traverse to parameters of the method
    * */
  def parameter: NodeSteps[nodes.MethodParameterIn] =
    new NodeSteps(
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
        .cast[nodes.MethodParameterIn])

  /**
    * Traverse to formal return parameter
    * */
  def methodReturn: NodeSteps[nodes.MethodReturn] =
    new NodeSteps(raw.map(_.methodReturn))

  /**
    * Traverse to type decl which have this method bound to it.
    */
  def bindingTypeDecl: NodeSteps[nodes.TypeDecl] =
    referencingBinding.bindingTypeDecl

  /**
    * Traverse to bindings which reference to this method.
    */
  def referencingBinding: NodeSteps[nodes.Binding] =
    new NodeSteps(raw.in(EdgeTypes.REF).filter(_.hasLabel(NodeTypes.BINDING)).cast[nodes.Binding])

  /**
    * All control structures of this method
    * */
  def controlStructure: NodeSteps[nodes.ControlStructure] =
    wrapped.ast.isControlStructure

  /**
    * Shorthand to traverse to control structures where condition matches `regex`
    * */
  def controlStructure(regex: String): NodeSteps[nodes.ControlStructure] =
    wrapped.ast.isControlStructure.code(regex)

  /**
    * Outgoing call sites
    * */
  def callOut: NodeSteps[nodes.Call] =
    new NodeSteps(raw.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.CALL).cast[nodes.Call])

  /**
    * The type declaration associated with this method, e.g., the class it is defined in.
    * */
  def definingTypeDecl: NodeSteps[nodes.TypeDecl] =
    new NodeSteps(
      raw
        .cast[nodes.StoredNode]
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.TYPE_DECL))
        .cast[nodes.TypeDecl])

  /**
    * The method in which this method is defined
    * */
  def definingMethod: NodeSteps[nodes.Method] =
    new NodeSteps(
      raw
        .cast[nodes.StoredNode]
        .repeat(_.in(EdgeTypes.AST).cast[nodes.StoredNode])
        .until(_.hasLabel(NodeTypes.METHOD))
        .cast[nodes.Method])

  /**
    * Traverse only to methods that are stubs, e.g., their code is not available
    * */
  def isStub: NodeSteps[nodes.Method] =
    new NodeSteps(raw.filter(_.not(_.out(EdgeTypes.CFG))))

  /**
    * Traverse only to methods that are not stubs.
    * */
  def isNotStub: NodeSteps[nodes.Method] =
    new NodeSteps(raw.filter(_.out(EdgeTypes.CFG)))

  /**
    * Traverse to external methods, that is, methods not present
    * but only referenced in the CPG.
    * */
  def external: NodeSteps[nodes.Method] =
    new NodeSteps(raw.has(NodeKeys.IS_EXTERNAL -> true))

  /**
    * Traverse to internal methods, that is, methods for which
    * code is included in this CPG.
    * */
  def internal: NodeSteps[nodes.Method] =
    new NodeSteps(raw.has(NodeKeys.IS_EXTERNAL -> false))

  /**
    * Traverse to the methods local variables
    * */
  def local: NodeSteps[nodes.Local] =
    new NodeSteps(
      raw
        .out(EdgeTypes.CONTAINS)
        .hasLabel(NodeTypes.BLOCK)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.LOCAL)
        .cast[nodes.Local])

  /**
    * Traverse to literals of method
    * */
  def literal: NodeSteps[nodes.Literal] =
    new NodeSteps(raw.out(EdgeTypes.CONTAINS).hasLabel(NodeTypes.LITERAL).cast[nodes.Literal])

  def topLevelExpressions: NodeSteps[nodes.Expression] =
    new NodeSteps(
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.BLOCK)
        .out(EdgeTypes.AST)
        .not(_.hasLabel(NodeTypes.LOCAL))
        .cast[nodes.Expression])

  def cfgNode: NodeSteps[nodes.CfgNode] =
    new NodeSteps(raw.flatMap { method =>
      __(method.cfgNode.to(Seq): _*)
    })

  /**
    *  Traverse to first expression in CFG.
    */
  def cfgFirst: NodeSteps[nodes.Expression] =
    new NodeSteps(raw.out(EdgeTypes.CFG).cast[nodes.Expression])

  /**
    *  Traverse to last expression in CFG.
    */
  def cfgLast: NodeSteps[nodes.Expression] =
    methodReturn.cfgLast

  /**
    * Traverse to block
    * */
  def block: NodeSteps[nodes.Block] =
    new NodeSteps(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.BLOCK).cast[nodes.Block])

  /**
    * Traverse to method body (alias for `block`)
    * */
  def body: NodeSteps[nodes.Block] = block

  /**
    * Traverse to namespace
    * */
  @Doc(msg = "Traverse to namespace")
  @Doc2("Traverse to namespace")
  def namespace: NodeSteps[nodes.Namespace] =
    new NodeSteps(definingTypeDecl.namespace.raw)

  def numberOfLines: Steps[Int] = wrapped.map(_.numberOfLines)

}

object DocReflectionMagic extends App {
  import scala.reflect.runtime.{universe => ru}
  import scala.reflect.runtime.universe._
  import scala.tools.reflect.ToolBox
  import org.reflections._
  import org.reflections.util._
  import org.reflections.scanners._
  import java.lang.reflect.Field
  import java.util
  import scala.jdk.CollectionConverters._
  val reflections = new Reflections("io.shiftleft")
  val travExtHead = reflections.getTypesAnnotatedWith(classOf[StepsExtJ]).iterator.next
  val annotation = travExtHead.getAnnotation(classOf[StepsExtJ])
  val nodeType = annotation.nodeType
//  println(travExtHead)
//  println(annotation)
//  println(nodeType)

  // TODO use the nodeType class in `help` impl to compute the correct help
  // ideas: typetag?
  println(new Steps[nodes.Method](null).help2(nodeType))

  // get methods and their @Doc entries: easy
//  travExtHead.getMethods.toList.filter(_.getDeclaredAnnotations.nonEmpty).foreach { m =>
//    println(s"$m ${m.getDeclaredAnnotations.toList}")
//  }

  val mirror = runtimeMirror(this.getClass.getClassLoader)
  val tb = mirror.mkToolBox()
//  mirror.classLoader.
//  ru.

//  org.reflections.ReflectionUtils.getAllMethods()


  //  val r = new Reflections(new ConfigurationBuilder()
//    .setUrls(ClasspathHelper.forPackage("io.shiftleft"))
//    .setScanners(
//      new MethodAnnotationsScanner()
//      new SubTypesScanner(),
//      new TypeAnnotationsScanner()
//    )
//  )
//    println(r.getMethodsAnnotatedWith(classOf[Doc]))

  // TODO use reflection to find all steps and it's extensions?
  // alternative: central place to register all doc strings

//  def funcNameDocPairs(): List[(String, Doc)] = {
//    val tb = runtimeMirror(this.getClass.getClassLoader).mkToolBox()
//    //    tb.mirror.classLoader.
//    typeOf[io.shiftleft.semanticcpg.language.types.structure.Method].decls
//      .filter(_.isPublic)
//      .map { x =>
//        (x.name.toString,
//          x.annotations
//            .filter(a => a.tree.tpe =:= typeOf[Doc])
//            .map(a => tb.eval(tb.untypecheck(a.tree)).asInstanceOf[Doc])
//            .headOption
//            .orNull)}
//      .filter(_._2 != null)
//      .toList
//  }
//
//  println(funcNameDocPairs())
//  // TODO use reflection to find all steps and it's extensions? check what fabs did with Passes
//  // alternative: central place to register all doc strings
}

object Method {


  val Help = new ForNode[nodes.Method](
    "method node",
    List(
      Entry(".parameter", "Traverse to parameters of the method"),
      Entry(".methodReturn", "Traverse to formal return parameter"),
      Entry(".bindingTypeDecl", "Traverse to type decl which have this method bound to it."),
      Entry(".referencingBinding", "Traverse to bindings which reference to this method."),
      Entry(".controlStructure", "All control structures of this method"),
      Entry(".controlStructure", "Shorthand to traverse to control structures where condition matches `regex`"),
      Entry(".callOut", "Outgoing call sites"),
      Entry(".definingTypeDecl", "The type declaration associated with this method, e.g., the class it is defined in."),
      Entry(".definingMethod", "The method in which this method is defined"),
      Entry(".isStub", "Traverse only to methods that are stubs, e.g., their code is not available"),
      Entry(".isNotStub", "Traverse only to methods that are not stubs."),
      Entry(".external", "Traverse to external methods, that is, methods not present but only referenced in the CPG."),
      Entry(".internal", "Traverse to internal methods, that is, methods for which code is included in this CPG."),
      Entry(".local", "Traverse to the methods local variables"),
      Entry(".literal", "Traverse to literals of method"),
      Entry(".topLevelExpressions", ""),
      Entry(".cfgNode", ""),
      Entry(".cfgFirst", " Traverse to first expression in CFG."),
      Entry(".cfgLast", " Traverse to last expression in CFG."),
      Entry(".block", "Traverse to block"),
      Entry(".body", "Traverse to method body (alias for `block`)"),
      Entry(".namespace", "Traverse to namespace"),
      Entry(".numberOfLines", "Method's linecount")
    )
  )

}

