package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.semanticcpg.testfixtures.CodeToCpgFixture
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._

class InheritanceTests extends WordSpec with Matchers {

  val code = """
               | class Foo { int i; };
               |
               | class Bar : public Foo {
               |   char x;
               |   int y;
               |   int method () {}
               | };
               |
               | Foo forceFooTohaveTypeNode();
               |
    """.stripMargin

  "should find that Bar is derived from Foo" in
    CodeToCpgFixture().buildCpg(code) {
      { cpg =>
        cpg.typeDecl.name("Bar").baseType.name.l shouldBe List("Foo")
      }
    }

}
