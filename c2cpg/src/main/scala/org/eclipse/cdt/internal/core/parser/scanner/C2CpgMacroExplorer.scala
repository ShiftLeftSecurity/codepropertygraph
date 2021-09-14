package org.eclipse.cdt.internal.core.parser.scanner

import org.eclipse.cdt.core.dom.ast.{IASTFileLocation, IASTName, IASTPreprocessorElifStatement, IASTPreprocessorIfStatement, IASTTranslationUnit}
import org.eclipse.cdt.core.parser.util.CharArrayMap
import org.eclipse.cdt.internal.core.parser.scanner.Lexer.LexerOptions

class C2CpgMacroExplorer(tu : IASTTranslationUnit,  loc : IASTFileLocation) {

  private val resolver = tu.getAdapter(classOf[ILocationResolver])
  private val expansion = resolver.getMacroExpansions(loc).headOption
  private val dictionary : CharArrayMap[PreprocessorMacro] = createDictionary()
  private val lexerOptions = tu.getAdapter(classOf[LexerOptions])
  private val expander = new MacroExpander(ILexerLog.NULL, dictionary, null, lexerOptions)
  private val tracker : MacroExpansionTracker  = new MacroExpansionTracker(Integer.MAX_VALUE)

  val source : String = resolver.getUnpreprocessedSignature(loc).mkString("")
  expansion.foreach{ exp =>
    val refLoc = exp.getFileLocation
    val from = refLoc.getNodeOffset
    val to = from + refLoc.getNodeLength
    val input = source.substring(from, to - from)
    val enclosing = tu.getNodeSelector(null).findEnclosingNode(from -1, 2)
    val isPPCondition = enclosing.isInstanceOf[IASTPreprocessorIfStatement] || enclosing.isInstanceOf[IASTPreprocessorElifStatement]
    expander.expand(input, tracker, tu.getFilePath, refLoc.getStartingLineNumber, isPPCondition);
  }

  def createDictionary() : CharArrayMap[PreprocessorMacro] = {
    val refs : Array[IASTName] = expansion.map(_.getMacroReference).toList.toArray
    val map = new CharArrayMap[PreprocessorMacro](refs.length);
    refs.foreach(name => addMacroDefinition(map, name))
    map
  }

  private def addMacroDefinition(map : CharArrayMap[PreprocessorMacro], name : IASTName) : Unit = {
    val binding = name.getBinding
    binding match {
      case preprocessorMacro: PreprocessorMacro =>
        map.put(name.getSimpleID, preprocessorMacro);
      case _ =>
    }
  }


}
