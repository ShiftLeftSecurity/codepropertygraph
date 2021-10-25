package io.shiftleft.semanticcpg.layers

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.PropertyNames
import io.shiftleft.passes.CpgPassBase
import io.shiftleft.semanticcpg.passes.FileCreationPass
import io.shiftleft.semanticcpg.passes.containsedges.ContainsEdgePass
import io.shiftleft.semanticcpg.passes.languagespecific.fuzzyc.MethodStubCreator
import io.shiftleft.semanticcpg.passes.linking.linker.{AstLinkerPass, TypeUsagePass}
import io.shiftleft.semanticcpg.passes.methoddecorations.MethodDecoratorPass
import io.shiftleft.semanticcpg.passes.namespacecreator.NamespaceCreator
import io.shiftleft.semanticcpg.passes.typenodes.TypeDeclStubCreator

import scala.annotation.nowarn

object Base {
  val overlayName: String = "base"
  val description: String = "base layer (linked frontend CPG)"
  def defaultOpts = new LayerCreatorOptions()

  def passes(cpg: Cpg): Iterator[CpgPassBase] = Iterator(
    new FileCreationPass(cpg),
    new NamespaceCreator(cpg),
    new TypeDeclStubCreator(cpg),
    new MethodStubCreator(cpg),
    new MethodDecoratorPass(cpg),
    new AstLinkerPass(cpg),
    new ContainsEdgePass(cpg),
    new TypeUsagePass(cpg)
  )

}

@nowarn
class Base(optionsUnused: LayerCreatorOptions = null) extends LayerCreator {
  override val overlayName: String = Base.overlayName
  override val description: String = Base.description

  override def create(context: LayerCreatorContext, storeUndoInfo: Boolean): Unit = {
    val cpg = context.cpg
    cpg.graph.indexManager.createNodePropertyIndex(PropertyNames.FULL_NAME)
    Base.passes(cpg).zipWithIndex.foreach {
      case (pass, index) =>
        runPass(pass, context, storeUndoInfo, index)
    }
  }

}
