package org.eclipse.cdt.internal.core.parser.scanner

import org.eclipse.cdt.core.dom.ast.{
  IASTFileLocation,
  IASTName,
  IASTPreprocessorElifStatement,
  IASTPreprocessorIfStatement,
  IASTPreprocessorMacroExpansion,
  IASTTranslationUnit
}
import org.eclipse.cdt.core.parser.util.CharArrayMap
import org.eclipse.cdt.internal.core.parser.scanner.Lexer.LexerOptions

import scala.annotation.nowarn
import scala.collection.mutable

/**
  * Utility class that obtains the arguments of the macro at `loc` of the translation
  * unit `tu`.
  *
  * This class must be in this package in order to have access to `PreprocessorMacro`.
  * */
class MacroArgumentExtractor(tu: IASTTranslationUnit, loc: IASTFileLocation) {

  def getArguments: List[String] = {
    val resolver = tu.getAdapter(classOf[ILocationResolver])
    val expansion = resolver.getMacroExpansions(loc).headOption
    expansion.toList.flatMap { exp =>
      val dictionary: CharArrayMap[PreprocessorMacro] = createDictionary(exp)
      val lexerOptions = tu.getAdapter(classOf[LexerOptions])
      val expander = new MacroExpander(ILexerLog.NULL, dictionary, null, lexerOptions)
      val tracker = new C2CpgMacroExpansionTracker(Integer.MAX_VALUE)
      val source: String = resolver.getUnpreprocessedSignature(loc).mkString("")
      val refLoc = exp.getFileLocation
      val input = source.substring(0, refLoc.getNodeLength)
      val enclosing = tu.getNodeSelector(null).findEnclosingNode(-1, 2)
      val isPPCondition = enclosing.isInstanceOf[IASTPreprocessorIfStatement] || enclosing
        .isInstanceOf[IASTPreprocessorElifStatement]
      expander.expand(input, tracker, tu.getFilePath, refLoc.getStartingLineNumber, isPPCondition)
      tracker.arguments.toList
    }
  }

  private def createDictionary(expansion: IASTPreprocessorMacroExpansion): CharArrayMap[PreprocessorMacro] = {
    val refs: Array[IASTName] = Array(expansion.getMacroReference)
    val map = new CharArrayMap[PreprocessorMacro](refs.length);
    refs.foreach(name => addMacroDefinition(map, name))
    map
  }

  private def addMacroDefinition(map: CharArrayMap[PreprocessorMacro], name: IASTName): Unit = {
    val binding = name.getBinding
    binding match {
      case preprocessorMacro: PreprocessorMacro =>
        map.put(name.getSimpleID, preprocessorMacro);
      case _ =>
    }
  }
}

class C2CpgMacroExpansionTracker(stepToTrack: Int) extends MacroExpansionTracker(stepToTrack) {

  val arguments: mutable.Buffer[String] = mutable.Buffer()

  @nowarn
  override def setExpandedMacroArgument(tokenList: TokenList): Unit = {
    var arg = ""
    var done = false
    while (!done) {
      val next = tokenList.removeFirst()
      if (next == null) {
        done = true
      } else {
        arg += next.toString + " "
      }
    }
    arguments.addOne(arg.trim)
  }

}
