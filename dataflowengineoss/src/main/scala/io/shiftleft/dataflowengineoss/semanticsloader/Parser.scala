package io.shiftleft.dataflowengineoss.semanticsloader

import io.shiftleft.dataflowengineoss.{SemanticsBaseListener, SemanticsLexer, SemanticsParser}
import org.antlr.v4.runtime.{CharStream, CharStreams, CommonTokenStream}
import org.antlr.v4.runtime.tree.ParseTreeWalker

import scala.collection.mutable
import scala.jdk.CollectionConverters._

object Semantics {

  def fromList(elements: List[FlowSemantic]): Semantics = {
    new Semantics(
      mutable.Map.newBuilder
        .addAll(
          elements.map { semantic =>
            semantic.methodFullName -> semantic
          }
        )
        .result()
    )
  }

  def fromFile(fileName: String): Semantics = {
    val elements = new Parser().parseFile(fileName)
    fromList(elements)
  }

  def empty: Semantics = new Semantics(mutable.HashMap())

}

class Semantics private (nameToSemantic: mutable.Map[String, FlowSemantic]) {

  def elements: List[FlowSemantic] = nameToSemantic.values.toList

  def forMethod(methodFullName: String): Option[FlowSemantic] = nameToSemantic.get(methodFullName)

}
case class FlowSemantic(methodFullName: String, mappings: List[(Int, Int)])

class Parser() {

  def parse(input: String): List[FlowSemantic] = {
    val charStream = CharStreams.fromString(input)
    parseCharStream(charStream)
  }

  def parseFile(fileName: String): List[FlowSemantic] = {
    val charStream = CharStreams.fromFileName(fileName)
    parseCharStream(charStream)
  }

  private def parseCharStream(charStream: CharStream): List[FlowSemantic] = {
    val lexer = new SemanticsLexer(charStream)
    val tokenStream = new CommonTokenStream(lexer)
    val parser = new SemanticsParser(tokenStream)
    val treeWalker = new ParseTreeWalker();

    val tree = parser.taintSemantics()
    val listener = new Listener()
    treeWalker.walk(listener, tree)
    listener.result.toList
  }

  private class Listener extends SemanticsBaseListener {

    val result: mutable.ListBuffer[FlowSemantic] = mutable.ListBuffer[FlowSemantic]()

    override def enterTaintSemantics(ctx: SemanticsParser.TaintSemanticsContext): Unit = {
      ctx.singleSemantic().asScala.foreach { semantic =>
        val methodName = semantic.methodName().name().getText
        val mappings = semantic.mapping().asScala.toList.map { mapping =>
          val src = mapping.src().NUMBER().getText.toInt
          val dst = mapping.dst().NUMBER().getText.toInt
          (src, dst)
        }
        result.addOne(FlowSemantic(methodName, mappings))
      }
    }

  }

}
