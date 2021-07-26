package io.shiftleft.fuzzyc2cpg.passes

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.NewNamespaceBlock
import io.shiftleft.fuzzyc2cpg.Global
import io.shiftleft.fuzzyc2cpg.passes.astcreation.{AntlrCModuleParserDriver, AstVisitor}
import io.shiftleft.passes.{CpgPassV2, DiffGraph, DiffGraphHandler, IntervalKeyPool}
import io.shiftleft.semanticcpg.language.types.structure.NamespaceTraversal
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import org.slf4j.LoggerFactory

/**
  * Given a list of filenames, this pass creates abstract syntax trees for
  * each file, including File and NamespaceBlock  Files are processed in parallel.
  * */
class AstCreationPass(filenames: List[String], keyPool: IntervalKeyPool)
    extends CpgPassV2[String](keyPools = Some(keyPool.split(filenames.size))) {

  private val logger = LoggerFactory.getLogger(getClass)
  val global: Global = Global()

  override def partIterator: Iterator[String] = filenames.iterator

  override def runOnPart(diffGraphHandler: DiffGraphHandler, filename: String): Unit = {

    val diffGraph = DiffGraph.newBuilder
    val absolutePath = new java.io.File(filename).toPath.toAbsolutePath.normalize().toString
    val namespaceBlock = nodes
      .NewNamespaceBlock()
      .name(NamespaceTraversal.globalNamespaceName)
      .fullName(MetaDataPass.getGlobalNamespaceBlockFullName(Some(absolutePath)))
      .filename(absolutePath)
      .order(1)

    diffGraph.addNode(namespaceBlock)
    val driver = createDriver(namespaceBlock)
    tryToParse(driver, filename, diffGraph).foreach(diffGraphHandler.addDiffGraph)
  }

  private def createDriver(namespaceBlock: NewNamespaceBlock): AntlrCModuleParserDriver = {
    val driver = new AntlrCModuleParserDriver()
    val astVisitor = new AstVisitor(driver, namespaceBlock, global)
    driver.addObserver(astVisitor)
    driver
  }

  private def tryToParse(driver: AntlrCModuleParserDriver,
                         filename: String,
                         diffGraph: DiffGraph.Builder): Iterator[DiffGraph] = {
    try {
      driver.parseAndWalkFile(filename, diffGraph)
      Iterator(diffGraph.build())
    } catch {
      case ex: RuntimeException => {
        logger.warn("Cannot parse module: " + filename + ", skipping")
        logger.warn("Complete exception: ", ex)
        ex.printStackTrace()
        Iterator()
      }
      case _: StackOverflowError => {
        logger.warn("Cannot parse module: " + filename + ", skipping, StackOverflow")
        Iterator()
      }
    }
  }

}
