package io.shiftleft.semanticcpg.passes
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.passes.DiffGraph
import io.shiftleft.semanticcpg.language._
import org.scalatest.{Matchers, WordSpec}

class BindingMethodOverridesPassTests extends WordSpec with Matchers {
  "Binding propagation should not mark non-overwritten bindings" in {
    val cpg       = Cpg.emptyCpg
    val diffGraph = DiffGraph.newBuilder
    val typA      = nodes.NewType(name = "tA", fullName = "typA", typeDeclFullName = "typeDeclA")
    val typB      = nodes.NewType(name = "tB", fullName = "typB", typeDeclFullName = "typeDeclB")
    val typeDeclA = nodes.NewTypeDecl(name = "tdA",
      fullName = "typeDeclA",
      isExternal = false,
      inheritsFromTypeFullName = List())
    val typeDeclB = nodes.NewTypeDecl(name = "tdB",
      fullName = "typeDeclB",
      isExternal = false,
      inheritsFromTypeFullName = List("typA"))

    val bindingA = nodes.NewBinding(name = "name", signature = "signature")
    val bindingB = nodes.NewBinding(name = "name", signature = "signature")
    val methodA  = nodes.NewMethod(name = "name", fullName = "fullName", isExternal = false)
    val methodB  = nodes.NewMethod(name = "name", fullName = "fullNameB", isExternal = false)

    diffGraph.addNode(typA)
    diffGraph.addNode(typB)
    diffGraph.addNode(typeDeclA)
    diffGraph.addNode(typeDeclB)
    diffGraph.addNode(bindingA)
    diffGraph.addNode(bindingB)
    diffGraph.addNode(methodA)
    diffGraph.addNode(methodB)
    diffGraph.addEdge(typA, typeDeclA, EdgeTypes.REF)
    diffGraph.addEdge(typB, typeDeclB, EdgeTypes.REF)
    diffGraph.addEdge(typeDeclB, typA, EdgeTypes.INHERITS_FROM)
    diffGraph.addEdge(bindingA, methodA, EdgeTypes.REF)
    diffGraph.addEdge(typeDeclA, bindingA, EdgeTypes.BINDS)
    diffGraph.addEdge(bindingB, methodA, EdgeTypes.REF)
    diffGraph.addEdge(typeDeclB, bindingB, EdgeTypes.BINDS)

    DiffGraph.Applier.applyDiff(diffGraph.build(), cpg)
    DiffGraph.Applier.applyDiff((new BindingMethodOverridesPass(cpg)).run().next(), cpg)
    cpg.typeDecl("typeDeclA").methodBinding.head.isMethodNeverOverridden shouldBe Some(true)
    cpg.typeDecl("typeDeclB").methodBinding.head.isMethodNeverOverridden shouldBe Some(true)
  }

  "Binding propagation should mark overwritten bindings" in {
    val cpg       = Cpg.emptyCpg
    val diffGraph = DiffGraph.newBuilder
    val typA      = nodes.NewType(name = "tA", fullName = "typA", typeDeclFullName = "typeDeclA")
    val typB      = nodes.NewType(name = "tB", fullName = "typB", typeDeclFullName = "typeDeclB")
    val typeDeclA = nodes.NewTypeDecl(name = "tdA",
      fullName = "typeDeclA",
      isExternal = false,
      inheritsFromTypeFullName = List())
    val typeDeclB = nodes.NewTypeDecl(name = "tdB",
      fullName = "typeDeclB",
      isExternal = false,
      inheritsFromTypeFullName = List("typA"))

    val bindingA = nodes.NewBinding(name = "name", signature = "signature")
    val bindingB = nodes.NewBinding(name = "name", signature = "signature")
    val methodA  = nodes.NewMethod(name = "name", fullName = "fullName", isExternal = false)
    val methodB  = nodes.NewMethod(name = "name", fullName = "fullNameB", isExternal = false)

    diffGraph.addNode(typA)
    diffGraph.addNode(typB)
    diffGraph.addNode(typeDeclA)
    diffGraph.addNode(typeDeclB)
    diffGraph.addNode(bindingA)
    diffGraph.addNode(bindingB)
    diffGraph.addNode(methodA)
    diffGraph.addNode(methodB)
    diffGraph.addEdge(typA, typeDeclA, EdgeTypes.REF)
    diffGraph.addEdge(typB, typeDeclB, EdgeTypes.REF)
    diffGraph.addEdge(typeDeclB, typA, EdgeTypes.INHERITS_FROM)
    diffGraph.addEdge(bindingA, methodA, EdgeTypes.REF)
    diffGraph.addEdge(typeDeclA, bindingA, EdgeTypes.BINDS)
    diffGraph.addEdge(bindingB, methodB, EdgeTypes.REF)
    diffGraph.addEdge(typeDeclB, bindingB, EdgeTypes.BINDS)

    DiffGraph.Applier.applyDiff(diffGraph.build(), cpg)
    DiffGraph.Applier.applyDiff((new BindingMethodOverridesPass(cpg)).run().next(), cpg)
    cpg.typeDecl("typeDeclA").methodBinding.head.isMethodNeverOverridden shouldBe Some(false)
    cpg.typeDecl("typeDeclB").methodBinding.head.isMethodNeverOverridden shouldBe Some(true)
  }

}
