package io.shiftleft.c2cpg.datastructures

import io.shiftleft.c2cpg.parser.FileDefaults
import io.shiftleft.passes.DiffGraph
import io.shiftleft.x2cpg.Ast

import scala.collection.concurrent.TrieMap
import scala.collection.mutable

object Global {
  val usedTypes: TrieMap[String, Boolean] = TrieMap.empty

  // We cache our already created CPG Sub-ASTs for included header files
  // by filename -> (linenumber, columnnumber)
  val headerAstCache: mutable.HashMap[String, mutable.HashSet[(Integer, Integer)]] =
    mutable.HashMap.empty

  val headerToFilenameCache: mutable.HashMap[String, mutable.HashSet[String]] = mutable.HashMap.empty

  def getAstsFromAstCache(diffGraph: DiffGraph.Builder,
                          filename: String,
                          fromFilename: String,
                          linenumber: Option[Integer],
                          columnnumber: Option[Integer],
                          astCreatorFunction: => Seq[Ast]): Seq[Ast] = this.synchronized {
    if (FileDefaults
          .isHeaderFile(filename) && filename != fromFilename && linenumber.isDefined && columnnumber.isDefined) {
      if (!headerAstCache.contains(filename)) {
        val value = mutable.HashSet((linenumber.get, columnnumber.get))
        headerAstCache.put(filename, value)
        headerToFilenameCache.put(filename, mutable.HashSet(fromFilename))
        astCreatorFunction.foreach(Ast.storeInDiffGraph(_, diffGraph))
      } else {
        if (!headerAstCache(filename).contains((linenumber.get, columnnumber.get))) {
          headerAstCache(filename).add((linenumber.get, columnnumber.get))
          astCreatorFunction.foreach(Ast.storeInDiffGraph(_, diffGraph))
        } else {
          headerToFilenameCache(filename).add(fromFilename)
        }
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
      if (!headerAstCache.contains(filename)) {
        val value = mutable.HashSet((linenumber.get, columnnumber.get))
        headerAstCache.put(filename, value)
        headerToFilenameCache.put(filename, mutable.HashSet(fromFilename))
        Ast.storeInDiffGraph(astCreatorFunction, diffGraph)
      } else {
        if (!headerAstCache(filename).contains((linenumber.get, columnnumber.get))) {
          headerAstCache(filename).add((linenumber.get, columnnumber.get))
          Ast.storeInDiffGraph(astCreatorFunction, diffGraph)
        } else {
          headerToFilenameCache(filename).add(fromFilename)
        }
      }
      Ast()
    } else { astCreatorFunction }
  }
}
