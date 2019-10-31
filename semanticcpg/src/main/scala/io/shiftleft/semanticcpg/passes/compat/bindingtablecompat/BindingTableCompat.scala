package io.shiftleft.semanticcpg.passes.compat.bindingtablecompat

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.NewBinding
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import org.apache.logging.log4j.LogManager
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
        val nonConstructorMethods = getNonConstructorMethodsTransitive(typeDecl, Set.empty)
        nonConstructorMethods.foreach(createBinding(typeDecl, diffGraph))

        val constructorMethods = typeDecl.start.method.isConstructor.l
        constructorMethods.foreach(createBinding(typeDecl, diffGraph))
      }
    }

    Iterator(diffGraph)
  }

  private def createBinding(typeDecl: nodes.TypeDecl, diffGraph: DiffGraph)(method: nodes.Method): Unit = {
    // The csharp frontend creates method names which contain type parameters.
    // It is necessary to keep them because a class may define parametric and non-parametric
    // functions with the same name, i.e., `void f() {} and void f<T>() {}' is valid.
    // Also, different than Java, C# carries generic type information over to runtime.

    val newBinding = new NewBinding(method.name, method.signature)
    diffGraph.addNode(newBinding)
    diffGraph.addEdgeFromOriginal(typeDecl, newBinding, EdgeTypes.BINDS)
    diffGraph.addEdgeToOriginal(newBinding, method, EdgeTypes.REF)
  }

  private def getNonConstructorMethodsTransitive(typeDecl: nodes.TypeDecl,
                                                 alreadyVisitedTypeDecls: Set[nodes.TypeDecl]): List[nodes.Method] = {
    if (alreadyVisitedTypeDecls.contains(typeDecl)) {
      BindingTableCompat.logger.warn(s"Found invalid cyclic TYPE_DECL inheritance for TYPE_DECL ${typeDecl.fullName}")
      return Nil
    }

    val baseTypeDecls = typeDecl
      .vertices(Direction.OUT, EdgeTypes.INHERITS_FROM)
      .asScala
      .flatMap(typ => typ.vertices(Direction.OUT, EdgeTypes.REF).asScala)
      .toList

    val nonConstructorMethodsOfBases = baseTypeDecls.flatMap { baseTypeDecl =>
      getNonConstructorMethodsTransitive(baseTypeDecl.asInstanceOf[nodes.TypeDecl], alreadyVisitedTypeDecls + typeDecl)
    }

    val ownNonConstructorMethods = getNonConstructorMethods(typeDecl)

    val notShadowedInheritedMethods =
      nonConstructorMethodsOfBases.filter { method =>
        !ownNonConstructorMethods.exists { ownMethod =>
          ownMethod.name == method.name && ownMethod.signature == method.signature
        }
      }

    notShadowedInheritedMethods ++ ownNonConstructorMethods
  }

  private def getNonConstructorMethods(typeDecl: nodes.TypeDecl): List[nodes.Method] = {
    typeDecl
      .vertices(Direction.OUT, EdgeTypes.AST)
      .asScala
      .filter { method =>
        method.isInstanceOf[nodes.Method] &&
        method.asInstanceOf[nodes.Method].start.isConstructor.headOption().isEmpty
      }
      .map(_.asInstanceOf[nodes.Method])
      .toList
  }

}

object BindingTableCompat {
  private val logger = LogManager.getLogger(getClass)
}
