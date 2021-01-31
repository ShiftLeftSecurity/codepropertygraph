package io.shiftleft.fuzzyc2cpg.standard

import io.shiftleft.fuzzyc2cpg.testfixtures.FuzzyCCodeToCpgSuite
import io.shiftleft.semanticcpg.language._

class TypeDeclTests extends FuzzyCCodeToCpgSuite {

  override val code = """
                   | class foo {
                   |   char x;
                   |   int y;
                   |   int method () {}
                   | };
                   |
    """.stripMargin

  "should find types `foo`, `char`, `int`, and `void`" in {
    cpg.typeDecl.name.toSet shouldBe Set("foo", "char", "int", "void")
  }

  "should find only one internal type (`foo`)" in {
    cpg.typeDecl.internal.name.toSet shouldBe Set("foo")
  }

  "should find three external types (`char`, `int`, `void`)" in {
    cpg.typeDecl.external.name.toSet shouldBe Set("char", "int", "void")
  }

  "should find two members for `foo`: `x` and `y`" in {
    cpg.typeDecl.name("foo").member.name.toSet shouldBe Set("x", "y")
  }

  "should find one method in type `foo`" in {
    cpg.typeDecl.name("foo").method.name.toSet shouldBe Set("method")
  }

  "should allow traversing from type to enclosing file" in {
    cpg.typeDecl.file.filter(_.name.endsWith(".c")).l should not be empty
  }

}