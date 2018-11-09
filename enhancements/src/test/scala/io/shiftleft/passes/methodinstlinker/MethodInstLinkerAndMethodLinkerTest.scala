package io.shiftleft.passes.methodinstlinker

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.passes.linking.linker.Linker
import io.shiftleft.queryprimitives.steps.Implicits._
import org.apache.tinkerpop.gremlin.structure.Direction
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import org.scalatest.{Matchers, WordSpec}

class MethodInstLinkerAndMethodLinkerTest extends WordSpec with Matchers {
  "CallDecoratorTest" in {
    implicit val graph: ScalaGraph = TinkerGraph.open.asScala

    val callsite  = graph + (NodeTypes.CALL,
      NodeKeys.METHOD_INST_FULL_NAME -> "methodInst1FullName",
      NodeKeys.TYPE_FULL_NAME -> "callsiteTypeFullName")
    val argument1 = graph + (NodeTypes.IDENTIFIER,
      NodeKeys.CODE -> "a",
      NodeKeys.ARGUMENT_INDEX -> 1,
      NodeKeys.TYPE_FULL_NAME -> "argument1TypeFullName")
    val argument2 = graph + (NodeTypes.IDENTIFIER,
      NodeKeys.CODE -> "b",
      NodeKeys.ARGUMENT_INDEX -> 2,
      NodeKeys.TYPE_FULL_NAME -> "argument2TypeFullName")
    callsite --- EdgeTypes.AST --> argument1
    callsite --- EdgeTypes.AST --> argument2
    val method = graph + (NodeTypes.METHOD,
      NodeKeys.AST_PARENT_TYPE -> NodeTypes.METHOD,
      NodeKeys.AST_PARENT_FULL_NAME -> "method1AstParentFullName",
      NodeKeys.CODE -> "method1",
      NodeKeys.NAME -> "method1Name",
      NodeKeys.FULL_NAME -> "method1FullName",
      NodeKeys.SIGNATURE -> "method1Signature")
    val methodInstance = graph + (NodeTypes.METHOD_INST,
      NodeKeys.CODE -> "method1",
      NodeKeys.FULL_NAME -> "methodInst1FullName",
      NodeKeys.NAME -> "methodInst1Name",
      NodeKeys.METHOD_FULL_NAME -> "method1FullName",
      NodeKeys.SIGNATURE -> "methodInst1Signature")
    val methodReturn    = graph + (NodeTypes.METHOD_RETURN,
      NodeKeys.TYPE_FULL_NAME -> "methodReturnTypeFullName")
    val methodParam2in  = graph + (NodeTypes.METHOD_PARAMETER_IN,
      NodeKeys.ORDER -> 2,
      NodeKeys.TYPE_FULL_NAME -> "methodParam2inTypeFullName")
    val methodParam2out = graph + (NodeTypes.METHOD_PARAMETER_OUT,
      NodeKeys.ORDER -> 2,
      NodeKeys.TYPE_FULL_NAME -> "methodParam2outTypeFullName")
    val methodParam1in  = graph + (NodeTypes.METHOD_PARAMETER_IN,
      NodeKeys.ORDER -> 1,
      NodeKeys.TYPE_FULL_NAME -> "methodParam1inTypeFullName")
    val methodParam1out = graph + (NodeTypes.METHOD_PARAMETER_OUT,
      NodeKeys.ORDER -> 1,
      NodeKeys.TYPE_FULL_NAME -> "methodParam1outTypeFullName")
    method --- EdgeTypes.AST --> methodReturn
    method --- EdgeTypes.AST --> methodParam2in
    method --- EdgeTypes.AST --> methodParam2out
    method --- EdgeTypes.AST --> methodParam1in
    method --- EdgeTypes.AST --> methodParam1out

    val linker = new Linker(graph)
    linker.executeAndApply()

    val callLinker = new MethodInstLinker(graph)
    callLinker.executeAndApply()

    callsite.vertices(Direction.IN, EdgeTypes.CALL_RET).nextChecked shouldBe methodReturn
    argument1.vertices(Direction.OUT, EdgeTypes.CALL_ARG).nextChecked shouldBe methodParam1in
    argument2.vertices(Direction.OUT, EdgeTypes.CALL_ARG).nextChecked shouldBe methodParam2in
    argument1.vertices(Direction.IN, EdgeTypes.CALL_ARG_OUT).nextChecked shouldBe methodParam1out
    argument2.vertices(Direction.IN, EdgeTypes.CALL_ARG_OUT).nextChecked shouldBe methodParam2out
    callsite.vertices(Direction.OUT, EdgeTypes.CALL).nextChecked shouldBe methodInstance
  }
}
