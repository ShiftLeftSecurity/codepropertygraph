package io.shiftleft.fuzzyc2cpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.NewNamespaceBlock
import io.shiftleft.fuzzyc2cpg.Global
import io.shiftleft.fuzzyc2cpg.passes.astcreation.{AntlrCModuleParserDriver, AstVisitor}
import io.shiftleft.passes.{DiffGraph, IntervalKeyPool, LargeChunkPass, ParallelCpgPass}
import io.shiftleft.semanticcpg.language.types.structure.NamespaceTraversal
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import org.slf4j.LoggerFactory

/**
  * Given a list of filenames, this pass creates abstract syntax trees for
  * each file, including File and NamespaceBlock  Files are processed in parallel.
  * */
class AstCreationPass(filenames: List[String], cpg: Cpg, keyPool: IntervalKeyPool)
    extends LargeChunkPass[String](cpg, keyPool = Some(keyPool)) {

  private val logger = LoggerFactory.getLogger(getClass)
  val global: Global = Global()

  override def generateParts(): Array[String] = filenames.toArray

  override def runOnPart(diffGraph: DiffGraph.Builder, filename: String): Unit = {

    val absolutePath = new java.io.File(filename).toPath.toAbsolutePath.normalize().toString
    val namespaceBlock = nodes
      .NewNamespaceBlock()
      .name(NamespaceTraversal.globalNamespaceName)
      .fullName(MetaDataPass.getGlobalNamespaceBlockFullName(Some(absolutePath)))
      .filename(absolutePath)
      .order(1)

    diffGraph.addNode(namespaceBlock)
    val localDiff = DiffGraph.newBuilder
    val driver = createDriver(namespaceBlock)
    //only commit changes from within the file if the entire file succeeds
    if (tryToParse(driver, filename, localDiff)) diffGraph.addChanges(localDiff)
  }

  private def createDriver(namespaceBlock: NewNamespaceBlock): AntlrCModuleParserDriver = {
    val driver = new AntlrCModuleParserDriver()
    val astVisitor = new AstVisitor(driver, namespaceBlock, global)
    driver.addObserver(astVisitor)
    driver
  }

  private def tryToParse(driver: AntlrCModuleParserDriver, filename: String, diffGraph: DiffGraph.Builder): Boolean = {
    try {
      driver.parseAndWalkFile(filename, diffGraph)
      true
    } catch {
      case ex: RuntimeException => {
        logger.warn("Cannot parse module: " + filename + ", skipping")
        logger.warn("Complete exception: ", ex)
        ex.printStackTrace()
        false
      }
      case _: StackOverflowError => {
        logger.warn("Cannot parse module: " + filename + ", skipping, StackOverflow")
        false
      }
    }
  }

}
