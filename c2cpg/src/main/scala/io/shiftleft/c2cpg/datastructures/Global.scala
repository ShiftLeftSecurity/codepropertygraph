package io.shiftleft.c2cpg.datastructures

import io.shiftleft.c2cpg.parser.FileDefaults
import io.shiftleft.passes.DiffGraph
import io.shiftleft.x2cpg.Ast

import scala.collection.concurrent.TrieMap

object Global {
  val usedTypes: TrieMap[String, Boolean] = TrieMap.empty

  // TODO: We could also the set isExternal to TypeDecls, Methods, and such
  //       if they are from a system header include
  case class HeaderIncludeResult(includedInFilenames: Set[String])

  // We cache our already created CPG Sub-ASTs for included header files
  // by (filename, linenumber, columnnumber)
  val headerAstCache: TrieMap[(String, Int, Int), HeaderIncludeResult] = TrieMap.empty

  def getAstsFromAstCache(diffGraph: DiffGraph.Builder,
                          filename: String,
                          fromFilename: String,
                          linenumber: Option[Integer],
                          columnnumber: Option[Integer],
                          astCreatorFunction: => Seq[Ast]): Seq[Ast] = this.synchronized {
    if (FileDefaults
          .isHeaderFile(filename) && filename != fromFilename && linenumber.isDefined && columnnumber.isDefined) {
      if (!headerAstCache.contains((filename, linenumber.get, columnnumber.get))) {
        headerAstCache((filename, linenumber.get, columnnumber.get)) = HeaderIncludeResult(Set(fromFilename))
        astCreatorFunction.foreach(Ast.storeInDiffGraph(_, diffGraph))
      } else {
        val prev = headerAstCache((filename, linenumber.get, columnnumber.get))
        headerAstCache((filename, linenumber.get, columnnumber.get)) =
          prev.copy(includedInFilenames = prev.includedInFilenames + fromFilename)
      }
      Seq.empty
    } else { astCreatorFunction }
  }

  def getAstFromAstCache(diffGraph: DiffGraph.Builder,
                         filename: String,
                         fromFilename: String,
                         linenumber: Option[Integer],
                         columnnumber: Option[Integer],
                         astCreatorFunction: => Ast): Ast = this.synchronized {
    if (FileDefaults
          .isHeaderFile(filename) && filename != fromFilename && linenumber.isDefined && columnnumber.isDefined) {
      if (!headerAstCache.contains((filename, linenumber.get, columnnumber.get))) {
        headerAstCache((filename, linenumber.get, columnnumber.get)) = HeaderIncludeResult(Set(fromFilename))
        Ast.storeInDiffGraph(astCreatorFunction, diffGraph)
      } else {
        val prev = headerAstCache((filename, linenumber.get, columnnumber.get))
        headerAstCache((filename, linenumber.get, columnnumber.get)) =
          prev.copy(includedInFilenames = prev.includedInFilenames + fromFilename)
      }
      Ast()
    } else { astCreatorFunction }
  }
}
