package io.shiftleft.semanticcpg.passes.bindingtablecompat

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.NewBinding
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import org.apache.tinkerpop.gremlin.structure.Direction

import scala.collection.JavaConverters._

/**
  * Compatibility pass which calculates missing binding tables.
  * Prerequisite: Linking, but not yet call linking, must be done.
  * TODO remove when not needed anymore.
  */
class BindingTableCompat(cpg: Cpg) extends CpgPass(cpg) {

  override def run(): Iterator[DiffGraph] = {
    val diffGraph = new DiffGraph

    if (cpg.graph.traversal.V().hasLabel(NodeTypes.BINDING).asScala.isEmpty) {
      cpg.typeDecl.toIterator().foreach { typeDecl =>
        val methodsInVTable = typeDecl.start.vtableMethod.l
        methodsInVTable.foreach(createBinding(typeDecl, diffGraph))

        val nonVirtNonConstructorMethods = getNonVirtualNonConstructorMethodsTransitive(typeDecl)
        nonVirtNonConstructorMethods.foreach(createBinding(typeDecl, diffGraph))

        val constructorMethods = typeDecl.start.method.isConstructor.l
        constructorMethods.foreach(createBinding(typeDecl, diffGraph))
      }
    }


    Iterator(diffGraph)
  }

  private def createBinding(typeDecl: nodes.TypeDecl, diffGraph: DiffGraph)(method: nodes.Method): Unit = {
    val newBinding = new NewBinding(method.name, method.signature)
    diffGraph.addNode(newBinding)
    diffGraph.addEdgeFromOriginal(typeDecl, newBinding, EdgeTypes.BINDS)
    diffGraph.addEdgeToOriginal(newBinding, method, EdgeTypes.REF)
  }

  private def getNonVirtualNonConstructorMethodsTransitive(typeDecl: nodes.TypeDecl): List[nodes.Method] = {
    val baseTypeDecls = typeDecl.vertices(Direction.OUT, EdgeTypes.INHERITS_FROM).asScala
      .flatMap(typ => typ.vertices(Direction.OUT, EdgeTypes.REF).asScala).toList

    val nonVirtNonConstructorMethodsOfBases = baseTypeDecls.flatMap { baseTypeDecl =>
      getNonVirtualNonConstructorMethodsTransitive(baseTypeDecl.asInstanceOf[nodes.TypeDecl])}

    val ownNonVirtNonConstructorMethods = getNonVirtualNonConstructorMethods(typeDecl)

    val notShadowedInheritedMethods =
      nonVirtNonConstructorMethodsOfBases.filter { method =>
        !ownNonVirtNonConstructorMethods.exists { ownMethod =>
          ownMethod.name == method.name && ownMethod.signature == method.signature
        }
      }

    notShadowedInheritedMethods ++ ownNonVirtNonConstructorMethods
  }

  private def getNonVirtualNonConstructorMethods(typeDecl: nodes.TypeDecl): List[nodes.Method] = {
    typeDecl.vertices(Direction.OUT, EdgeTypes.AST).asScala
      .filter { method =>
        method.isInstanceOf[nodes.Method] &&
          method.asInstanceOf[nodes.Method].start.isVirtual.headOption().isEmpty &&
          method.asInstanceOf[nodes.Method].start.isConstructor.headOption().isEmpty
      }.map (_.asInstanceOf[nodes.Method]).toList
  }

}
