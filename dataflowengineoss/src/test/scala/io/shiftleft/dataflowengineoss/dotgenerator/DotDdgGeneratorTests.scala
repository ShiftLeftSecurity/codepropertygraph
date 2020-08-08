package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.dataflowengineoss.language.DataFlowCodeToCpgSuite
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.semanticcpg.language.dotextension.ImageViewer

import scala.sys.process.Process
import scala.util.Try

class DotDdgGeneratorTests extends DataFlowCodeToCpgSuite {

  override val code =
    """
      |int foo(int param1, char *param2) {
      |   int i = 0;
      |   while(i < 10) {
      |     char *boo = moo("%d\n", i + bar(i));
      |     printf(boo, param1);
      |     i++;
      |   }
      |   return 0;
      |}
      |""".stripMargin

  "A PdgDotGenerator" should {
    "create a dot graph with 11 edges" in {

      implicit val viewer = new ImageViewer {
        override def view(pathStr: String): Try[String] = {
          Try {
            Process(Seq("xdg-open", pathStr)).!!
          }
        }
      }

      cpg.method.name("foo").plotDotDdg

      val lines = cpg.method.name("foo").dotDdg.l.head.split("\n")
      lines.head.startsWith("digraph foo") shouldBe true
      lines.count(x => x.contains("->")) shouldBe 11
      lines.last.startsWith("}") shouldBe true
    }
  }

}
