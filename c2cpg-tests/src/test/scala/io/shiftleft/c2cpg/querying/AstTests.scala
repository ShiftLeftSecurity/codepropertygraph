package io.shiftleft.c2cpg.querying

import io.shiftleft.c2cpg.testfixtures.CCodeToCpgSuite
import io.shiftleft.codepropertygraph.generated.nodes.{Call, Identifier, Literal}
import io.shiftleft.codepropertygraph.generated.{DispatchTypes, Operators}
import io.shiftleft.semanticcpg.language._

class CAstTests extends CCodeToCpgSuite {

  override val code: String =
    """
      | int foo(int y) {
      |   int x = 10;
      |   if (x > 10) {
      |     moo(boo(1+2));
      |     return bar(x + 10);
      |   } else {
      |     if (y > x) {
      |       printf("reached");
      |     }
      |   }
      | }
    """.stripMargin

  "should identify four blocks" in {
    cpg.method.name("foo").ast.isBlock.l.size shouldBe 4
  }

  "should allow finding addition in argument to bar" in {
    implicit val resolver: ICallResolver = NoResolve
    cpg.method
      .name("bar")
      .callIn
      .argument(1)
      .containsCallTo("<operator>.(addition|multiplication)")
      .code
      .l shouldBe List("x + 10")
  }

  "should allow finding that addition is not a direct argument of moo" in {
    implicit val resolver: ICallResolver = NoResolve

    cpg.method
      .name("moo")
      .callIn
      .argument(1)
      .containsCallTo("<operator>.(addition|multiplication)")
      .code
      .l shouldBe List("boo(1+2)")

    cpg.method
      .name("moo")
      .callIn
      .argument(1)
      .filter(
        arg =>
          arg.ast
            .isCallTo("<operator>.(addition|multiplication)")
            .not(_.inAstMinusLeaf(arg).isCall)
            .l
            .nonEmpty)
      .code
      .l shouldBe List()
  }

  "should identify three control structures" in {
    cpg.method
      .name("foo")
      .ast
      .isControlStructure
      .isIf
      .l
      .size shouldBe 2

    cpg.method
      .name("foo")
      .ast
      .isControlStructure
      .isElse
      .l
      .size shouldBe 1
  }

  "should allow basic calling basic 'is' methods on AST node" in {
    cpg.method.name("foo").ast.isFile.l.size shouldBe 0
    cpg.method.name("foo").ast.isMember.l.size shouldBe 0
    cpg.method.name("foo").ast.isModifier.l.size shouldBe 0
    cpg.method.name("foo").ast.isNamespaceBlock.l.size shouldBe 0
    cpg.method.name("foo").ast.isParameter.l.size shouldBe 1
    cpg.method.name("foo").ast.isTypeDecl.l.size shouldBe 0
  }

  "should identify conditions" in {
    cpg.method.name("foo").controlStructure.condition.code.l shouldBe List("x > 10", "y > x")
  }

  "should allow parserTypeName filtering and then ast" in {
    val query1Size = cpg.method.name("foo").ast.isControlStructure.ast.l.size
    query1Size should be > 0

    val query2Size = cpg.method
      .name("foo")
      .ast
      .isControlStructure
      .parserTypeName(".*")
      .ast
      .l
      .size
    query1Size shouldBe query2Size
  }

  "should allow filtering on control structures" in {
    cpg.method
      .name("foo")
      .controlStructure(".*x > 10.*")
      .l
      .size shouldBe 1

    cpg.method
      .name("foo")
      .controlStructure(".*x > 10.*")
      .whenTrue
      .ast
      .isReturn
      .code
      .l shouldBe List("return bar(x + 10);")

    cpg.method
      .name("foo")
      .controlStructure(".*x > 10.*")
      .whenFalse
      .ast
      .isCall
      .code(".*printf.*")
      .code
      .l shouldBe List("printf(\"reached\")")
  }
}

class CAstTests2 extends CCodeToCpgSuite {

  override val code: String =
    """
      |void foo(int bar) {
      | char buf[10];
      | int i;
      | for (int i = 0;i < bar;i++) {
      |   buf[i] = 42;
      | }
      |}
    """.stripMargin

  "should find index `i` used for buf" in {

    cpg.call
      .name("<operator>.indirectIndexAccess")
      .argument
      .argumentIndex(2)
      .code
      .l shouldBe List("i")
  }

  "should find that i is assigned as part of loop header" in {

    cpg.call
      .name("<operator>.indirectIndexAccess")
      .argument
      .argumentIndex(2)
      .inAstMinusLeaf
      .isControlStructure
      .code
      .l shouldBe List("for (int i = 0;i < bar;i++)")

  }

  "should correctly identify condition of for loop" in {
    cpg.call
      .name("<operator>.indirectIndexAccess")
      .argument
      .argumentIndex(2)
      .inAstMinusLeaf
      .isControlStructure
      .condition
      .code
      .l shouldBe List("i < bar")
  }

}

class MacroHandlingTests1 extends CCodeToCpgSuite {
  override val code: String =
    """
       #define A_MACRO(x) (x = 10)
       int foo() {
        int y;
        A_MACRO(y);
        return 10 * y;
       }
    """.stripMargin

  "should correctly expand macro" in {
    val List(x: Call) = cpg.method("foo").call.nameExact(Operators.assignment).l
    x.code shouldBe "y = 10"
    x.dispatchType shouldBe DispatchTypes.STATIC_DISPATCH
    val List(l: Identifier, r: Literal) = x.astMinusRoot.l.sortBy(_.order)
    l.name shouldBe "y"
    r.code shouldBe "10"
  }
}

class MacroHandlingTests2 extends CCodeToCpgSuite {
  override val code: String =
    """
       #define A_MACRO(x) (x = A_SECOND_MACRO(x))
       #define A_SECOND_MACRO(x) (x+1)
       int foo() {
        int y;
        A_MACRO(y);
        return 10 * y;
       }
    """.stripMargin

  "should correctly expand macro inside macro" in {
    val List(x: Call) = cpg.method("foo").call.nameExact(Operators.assignment).l
    x.code shouldBe "y = (y + 1)"
    val List(identifier: Identifier, call2: Call) = x.astChildren.l.sortBy(_.order)
    identifier.code shouldBe "y"
    call2.code shouldBe "y + 1"
    val List(arg1: Identifier, arg2: Literal) = call2.argument.l.sortBy(_.argumentIndex)
    arg1.name shouldBe "y"
    arg2.code shouldBe "1"
  }
}

class MacroHandlingTests3 extends CCodeToCpgSuite {
  override val code: String =
    """
       #define A_MACRO(x) (printf(x))
       int foo() {
        int y;
        A_MACRO(y);
        return 10 * y;
       }
    """.stripMargin

  "should correctly expand macro inside macro" in {
    val List(x: Call) = cpg.method("foo").call.nameExact("printf").l
    x.code shouldBe "printf(y)"
    val List(arg: Identifier) = x.argument.l.sortBy(_.argumentIndex)
    arg.name shouldBe "y"
  }
}

class MacroHandlingTests4 extends CCodeToCpgSuite {
  override val code: String =
    """
       #define A_MACRO(dst, code, size)\
     do \
    { \
        if( (i_read) >= (size) ) \
        { \
            dst = (code); \
            p_peek += (size); \
            i_read -= (size); \
        } \
        else \
        { \
            dst = 0; \
            i_read = 0; \
        } \
    } while(0)

    #define A_MACRO_2() (dst++)

    int foo() {
        char * dst, ptr;
        A_MACRO(dst, ptr, 1);
        dst++;
        A_MACRO_2();
        return 10 * y;
       }
    """.stripMargin

  "should correctly include calls to macros" in {
    val List(call1: Call) = cpg.method("foo").call.nameExact("A_MACRO").l
    call1.code shouldBe "A_MACRO(dst, ptr, 1)"
    call1.name shouldBe "A_MACRO"
    call1.methodFullName shouldBe "A_MACRO"
    call1.lineNumber shouldBe Some(22)
    call1.columnNumber shouldBe Some(8)
    call1.typeFullName shouldBe "ANY"
    call1.dispatchType shouldBe DispatchTypes.INLINED
    val List(arg1, arg2, arg3) = call1.argument.l.sortBy(_.order)
    arg1.code shouldBe "dst"
    arg2.code shouldBe "ptr"
    arg3.code shouldBe "1"

    val List(call2: Call) = cpg.method("foo").call.nameExact("A_MACRO_2").l
    call2.code shouldBe "A_MACRO_2()"
    call2.name shouldBe "A_MACRO_2"
    call2.methodFullName shouldBe "A_MACRO_2"
    call2.lineNumber shouldBe Some(24)
    call2.columnNumber shouldBe Some(8)
    call2.typeFullName shouldBe "ANY"
    call2.argument.l.sortBy(_.order).size shouldBe 0
    call2.dispatchType shouldBe DispatchTypes.INLINED
  }
}

class MacroHandlingTests5 extends CCodeToCpgSuite {
  override val code: String =
    """
       #define A_MACRO (1)
       int foo() {
        return A_MACRO;
       }
    """.stripMargin

  "should correctly include call to macro that is only a constant in a return" in {
    val List(call: Call) = cpg.method("foo").call.l
    call.name shouldBe "A_MACRO"
    call.code shouldBe "A_MACRO"
    call.argument.size shouldBe 0
  }
}
