package org.eclipse.cdt.internal.core.parser.scanner

import org.eclipse.cdt.core.dom.ast.{
  IASTFileLocation,
  IASTName,
  IASTPreprocessorElifStatement,
  IASTPreprocessorIfStatement,
  IASTTranslationUnit
}
import org.eclipse.cdt.core.parser.util.CharArrayMap
import org.eclipse.cdt.internal.core.parser.scanner.Lexer.LexerOptions

import scala.jdk.CollectionConverters.CollectionHasAsScala

class C2CpgMacroExplorer(tu: IASTTranslationUnit, loc: IASTFileLocation) {

  private val resolver = tu.getAdapter(classOf[ILocationResolver])
  private val expansion = resolver.getMacroExpansions(loc).headOption
  private val dictionary: CharArrayMap[PreprocessorMacro] = createDictionary()
  private val lexerOptions = tu.getAdapter(classOf[LexerOptions])
  private val expander = new MacroExpander(ILexerLog.NULL, dictionary, null, lexerOptions)
  private val tracker: MacroExpansionTracker = new C2CpgMacroExpansionTracker(Integer.MAX_VALUE)

  val source: String = resolver.getUnpreprocessedSignature(loc).mkString("")
  expansion.foreach { exp =>
    val refLoc = exp.getFileLocation
    val from = 0
    val to = from + refLoc.getNodeLength
    val input = source.substring(from, to - from)
    val enclosing = tu.getNodeSelector(null).findEnclosingNode(from - 1, 2)
    val isPPCondition = enclosing.isInstanceOf[IASTPreprocessorIfStatement] || enclosing
      .isInstanceOf[IASTPreprocessorElifStatement]
    expander.expand(input, tracker, tu.getFilePath, refLoc.getStartingLineNumber, isPPCondition)
    println(tracker)
  }

  def createDictionary(): CharArrayMap[PreprocessorMacro] = {
    val refs: Array[IASTName] = expansion.map(_.getMacroReference).toList.toArray
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

  override def endFunctionStyleMacro(): Unit = {
    val field = classOf[MacroExpansionTracker].getDeclaredField("fMacroStack")
    field.setAccessible(true)
    val list = field.get(this).asInstanceOf[java.util.List[MacroInfo]].asScala
    list.headOption.foreach { macroInfo =>
      val field = classOf[MacroInfo].getDeclaredField("fArguments")
      field.setAccessible(true)
      val arguments = field.get(macroInfo).asInstanceOf[java.util.ArrayList[TokenList]].asScala.toList
      arguments.foreach { argTokenList =>
        var arg = ""
        var done = false
        while (!done) {
          val next = argTokenList.removeFirst()
          if (next == null) {
            done = true
          } else {
            arg += next.toString + " "
          }
        }
        arg = arg.trim
        println(arg)
      }
    }
    println("HOOKED")
  }
}
