package io.shiftleft.dataflowengineoss.semanticsloader

import io.shiftleft.dataflowengineoss.{SemanticsBaseListener, SemanticsLexer, SemanticsParser}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.antlr.v4.runtime.tree.ParseTreeWalker

import scala.collection.mutable
import scala.jdk.CollectionConverters._

case class FlowSemantic(methodFullName: String, mappings: List[(Int, Int)])

class Parser() {

  def parse(input: String): List[FlowSemantic] = {

    val stream = CharStreams.fromString(input)
    val lexer = new SemanticsLexer(stream)
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
