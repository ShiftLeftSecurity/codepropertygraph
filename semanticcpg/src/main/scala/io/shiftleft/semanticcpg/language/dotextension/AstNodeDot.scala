package io.shiftleft.semanticcpg.language.dotextension

import better.files.File
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.DotAstGenerator
import io.shiftleft.semanticcpg.language._

import scala.sys.process.Process
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

trait ImageViewer {
  def view(pathStr: String): Try[String]
}

class AstNodeDot[NodeType <: nodes.AstNode](val wrapped: NodeSteps[NodeType]) extends AnyVal {

  def dotAst: Steps[String] = DotAstGenerator.toDotAst(wrapped)

  def plotDotAst(implicit viewer: ImageViewer): Unit = {
    wrapped.dotAst.foreach { dotString =>
      File.usingTemporaryFile("semanticcpg") { dotFile =>
        File.usingTemporaryFile("semanticcpg") { svgFile =>
          dotFile.write(dotString)
          createSvgFile(dotFile, svgFile)
          viewer.view(svgFile.path.toAbsolutePath.toString)
        }
      }
    }
  }

  private def createSvgFile(in: File, out: File): Try[String] = {
    Try {
      Process(Seq("dot", "-Tsvg", in.path.toAbsolutePath.toString, "-o", out.path.toAbsolutePath.toString)).!!
    } match {
      case Success(v) => Success(v)
      case Failure(exc) =>
        System.err.println("Executing `dot` failed: is `graphviz` installed?")
        System.err.println(exc)
        Failure(exc)
    }
  }

}
