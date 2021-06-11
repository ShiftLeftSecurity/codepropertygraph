package io.shiftleft.semanticcpg.passes.compat.bindingtablecompat

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import org.slf4j.{Logger, LoggerFactory}
import overflowdb.traversal._

/**
  * Compatibility pass which calculates missing binding tables.
  * Prerequisite: Linking, but not yet call linking, must be done.
  * TODO remove when not needed anymore.
  */
class BindingTableCompat(cpg: Cpg) extends CpgPass(cpg) {

  override def run(): Iterator[DiffGraph] = {
    val diffGraph = DiffGraph.newBuilder

    if (cpg.graph.nodes(NodeTypes.BINDING).isEmpty) {
      cpg.typeDecl.foreach { typeDecl =>
        val nonConstructorMethods = getNonConstructorMethodsTransitive(typeDecl, Set.empty)
        nonConstructorMethods.foreach(createBinding(typeDecl, diffGraph))

        val constructorMethods = typeDecl.method.isConstructor
        constructorMethods.foreach(createBinding(typeDecl, diffGraph))
      }
    }

    Iterator(diffGraph.build())
  }

  private def createBinding(typeDecl: TypeDecl, diffGraph: DiffGraph.Builder)(method: Method): Unit = {
    // The csharp frontend creates method names which contain type parameters.
    // It is necessary to keep them because a class may define parametric and non-parametric
    // functions with the same name, i.e., `void f() {} and void f<T>() {}' is valid.
    // Also, different than Java, C# carries generic type information over to runtime.

    val newBinding = NewBinding().name(method.name).signature(method.signature)
    diffGraph.addNode(newBinding)
    diffGraph.addEdgeFromOriginal(typeDecl, newBinding, EdgeTypes.BINDS)
    diffGraph.addEdgeToOriginal(newBinding, method, EdgeTypes.REF)
  }

  private def getNonConstructorMethodsTransitive(typeDecl: TypeDecl,
                                                 alreadyVisitedTypeDecls: Set[TypeDecl]): List[Method] = {
    if (alreadyVisitedTypeDecls.contains(typeDecl)) {
      BindingTableCompat.logger.warn(s"Found invalid cyclic TYPE_DECL inheritance for TYPE_DECL ${typeDecl.fullName}")
      return Nil
    }

    val baseTypeDecls = typeDecl._typeViaInheritsFromOut.flatMap(_._typeDeclViaRefOut).toList

    val nonConstructorMethodsOfBases = baseTypeDecls.flatMap { baseTypeDecl =>
      getNonConstructorMethodsTransitive(baseTypeDecl, alreadyVisitedTypeDecls + typeDecl)
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

  private def getNonConstructorMethods(typeDecl: TypeDecl): List[Method] =
    typeDecl._methodViaAstOut
      .filter(method => method.isConstructor.isEmpty)
      .toList

}

object BindingTableCompat {
  private val logger: Logger = LoggerFactory.getLogger(classOf[BindingTableCompat])
}
