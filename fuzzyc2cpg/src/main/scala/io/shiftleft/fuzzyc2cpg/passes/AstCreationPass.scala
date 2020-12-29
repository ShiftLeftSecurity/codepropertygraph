package io.shiftleft.fuzzyc2cpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.fuzzyc2cpg.passes.astcreation.{AntlrCModuleParserDriver, AstVisitor}
import io.shiftleft.fuzzyc2cpg.{Defines, Global}
import io.shiftleft.passes.{DiffGraph, IntervalKeyPool, ParallelCpgPass}
import org.slf4j.LoggerFactory

/**
  * Given a list of filenames, this pass creates abstract syntax trees for
  * each file, including File and NamespaceBlock nodes. Files are processed in parallel.
  * */
class AstCreationPass(filenames: List[String], cpg: Cpg, keyPool: IntervalKeyPool)
    extends ParallelCpgPass[String](cpg, keyPools = Some(keyPool.split(filenames.size))) {

  private val logger = LoggerFactory.getLogger(getClass)
  val global: Global = Global()

  override def partIterator: Iterator[String] = filenames.iterator

  override def runOnPart(filename: String): Iterator[DiffGraph] = {

    val diffGraph = DiffGraph.newBuilder
    val absolutePath = new java.io.File(filename).toPath.toAbsolutePath.normalize().toString
    val fileNode = nodes.NewFile(name = absolutePath, order = 0)
    diffGraph.addNode(fileNode)
    val namespaceBlock = nodes.NewNamespaceBlock(
      name = Defines.globalNamespaceName,
      fullName = CMetaDataPass.getGlobalNamespaceBlockFullName(Some(fileNode.name))
    )
    diffGraph.addNode(fileNode)
    diffGraph.addNode(namespaceBlock)
    diffGraph.addEdge(fileNode, namespaceBlock, EdgeTypes.AST)

    val driver = createDriver(fileNode, namespaceBlock)
    tryToParse(driver, filename, diffGraph)
  }

  private def createDriver(fileNode: nodes.NewFile,
                           namespaceBlock: nodes.NewNamespaceBlock): AntlrCModuleParserDriver = {
    val driver = new AntlrCModuleParserDriver()
    val astVisitor = new AstVisitor(driver, namespaceBlock, global)
    driver.addObserver(astVisitor)
    driver.setFileNode(fileNode)
    driver
  }

  private def tryToParse(driver: AntlrCModuleParserDriver,
                         filename: String,
                         diffGraph: DiffGraph.Builder): Iterator[DiffGraph] = {
    try {
      driver.parseAndWalkFile(filename, diffGraph)
      Iterator(diffGraph.build)
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
