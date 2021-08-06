package io.shiftleft.c2cpg.passes

import better.files.File
import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{ControlStructureTypes, Operators}
import io.shiftleft.passes.IntervalKeyPool
import io.shiftleft.semanticcpg.language._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import overflowdb.traversal._

class AstCreationPassTests extends AnyWordSpec with Matchers {

  private object CpgFixture {
    def apply(code: String, fileName: String = "file.c"): Cpg = {
      val cpg = Cpg.emptyCpg
      File.usingTemporaryDirectory("c2cpgtest") { dir =>
        val file = dir / fileName
        file.write(code)

        val keyPool = new IntervalKeyPool(1001, 2000)
        val filenames = List(file.path.toAbsolutePath.toString)
        new AstCreationPass(filenames, cpg, keyPool, Config()).createAndApply()
      }
      cpg
    }
  }

  private object Fixture {
    def apply(code: String, fileName: String = "file.c")(f: Cpg => Unit): Unit = {
      File.usingTemporaryDirectory("c2cpgtest") { dir =>
        val file = dir / fileName
        file.write(code)

        val cpg = Cpg.emptyCpg
        val keyPool = new IntervalKeyPool(1001, 2000)
        val filenames = List(file.path.toAbsolutePath.toString)
        new AstCreationPass(filenames, cpg, keyPool, Config()).createAndApply()

        f(cpg)
      }
    }
  }

  "AstCreationPass" should {
    val cpg = Cpg.emptyCpg
    File.usingTemporaryDirectory("astCreationTests") { dir =>
      val filenames = List("foo.c", "woo.c")
      val expectedFilenames = filenames.map { filename =>
        val file = dir / filename
        file.write("//foo")
        file.path.toAbsolutePath.toString
      }
      new AstCreationPass(expectedFilenames, cpg, new IntervalKeyPool(1, 1000), Config()).createAndApply()

      "create one NamespaceBlock per file" in {
        val expectedNamespaceFullNames = expectedFilenames.map(f => s"$f:<global>").toSet
        cpg.namespaceBlock.fullName.toSet shouldBe expectedNamespaceFullNames
      }

    }
  }

  "Method AST layout" should {

    "be correct for empty method" in Fixture("void method(int x) { }") { cpg =>
      cpg.method.name("method").astChildren.l match {
        case List(param: MethodParameterIn, _: Block, ret: MethodReturn) =>
          ret.typeFullName shouldBe "void"
          param.typeFullName shouldBe "int"
          param.name shouldBe "x"
        case _ => fail()
      }
    }

    "be correct for decl assignment" in Fixture("""
        |void method() {
        |  int local = 1;
        |}
        |""".stripMargin) { cpg =>
      cpg.method.name("method").block.astChildren.l match {
        case List(local: Local, call: Call) =>
          local.name shouldBe "local"
          local.typeFullName shouldBe "int"
          call.name shouldBe Operators.assignment
          call.astChildren.l match {
            case List(identifier: Identifier, literal: Literal) =>
              identifier.name shouldBe "local"
              identifier.typeFullName shouldBe "int"
              identifier.order shouldBe 1
              identifier.argumentIndex shouldBe 1
              literal.code shouldBe "1"
              literal.typeFullName shouldBe "int"
              literal.order shouldBe 2
              literal.argumentIndex shouldBe 2
            case _ => fail()
          }
        case _ => fail()
      }
    }

    "be correct for decl assignment with identifier on the right" in
      Fixture("""
          |void method(int x) {
          |  int local = x;
          |}""".stripMargin) { cpg =>
        cpg.method.block.astChildren.assignments.source.l match {
          case List(identifier: Identifier) =>
            identifier.code shouldBe "x"
            identifier.typeFullName shouldBe "int"
            identifier.order shouldBe 2
            identifier.argumentIndex shouldBe 2
          case _ => fail()
        }
      }

    "be correct for decl assignment of multiple locals" in
      Fixture("""
          |void method(int x, int y) {
          |  int local = x, local2 = y;
          |}""".stripMargin) { cpg =>
        // Note that `cpg.method.local` does not work
        // because it depends on CONTAINS edges which
        // are created by a backend pass in semanticcpg
        // construction.

        cpg.local.l.sortBy(_.order) match {
          case List(local1, local2) =>
            local1.name shouldBe "local"
            local1.typeFullName shouldBe "int"
            local2.name shouldBe "local2"
            local2.typeFullName shouldBe "int"
          case _ => fail()
        }

        cpg.assignment.l.sortBy(_.order) match {
          case List(a1, a2) =>
            List(a1.target.code, a1.source.code) shouldBe List("local", "x")
            List(a2.target.code, a2.source.code) shouldBe List("local2", "y")
          case _ => fail()
        }
      }

    "be correct for nested expression" in Fixture("""
        |void method() {
        |  int x;
        |  int y;
        |  int z;
        |
        |  x = y + z;
        |}
      """.stripMargin) { cpg =>
      cpg.local.l.sortBy(_.order).map(_.name) shouldBe List("x", "y", "z")

      cpg.method.assignments.l match {
        case List(assignment) =>
          assignment.target.code shouldBe "x"
          assignment.source.start.isCall.name.l shouldBe List(Operators.addition)
          assignment.source.astChildren.l match {
            case List(id1: Identifier, id2: Identifier) =>
              id1.order shouldBe 1
              id1.code shouldBe "y"
              id2.order shouldBe 2
              id2.code shouldBe "z"
            case _ => fail()
          }
        case _ => fail()
      }
    }

    "be correct for nested block" in Fixture("""
        |void method() {
        |  int x;
        |  {
        |    int y;
        |  }
        |}
      """.stripMargin) { cpg =>
      cpg.method.name("method").block.astChildren.l match {
        case List(local: Local, innerBlock: Block) =>
          local.name shouldBe "x"
          innerBlock.astChildren.l match {
            case List(localInBlock: Local) =>
              localInBlock.name shouldBe "y"
            case _ => fail()
          }
        case _ => fail()
      }
    }

    "be correct for while-loop" in Fixture("""
        |void method(int x) {
        |  while (x < 1) {
        |    x += 1;
        |  }
        |}
      """.stripMargin) { cpg =>
      cpg.method.name("method").block.astChildren.isControlStructure.l match {
        case List(controlStruct: ControlStructure) =>
          controlStruct.code shouldBe "while (x < 1)"
          controlStruct.controlStructureType shouldBe ControlStructureTypes.WHILE
          controlStruct.condition.l match {
            case List(cndNode) =>
              cndNode.code shouldBe "x < 1"
            case _ => fail()
          }
          controlStruct.whenTrue.assignments.code.l shouldBe List("x += 1")
        case _ => fail()
      }
    }

    "be correct for if" in Fixture("""
        |void method(int x) {
        |  int y;
        |  if (x > 0) {
        |    y = 0;
        |  }
        |}
      """.stripMargin) { cpg =>
      cpg.method.name("method").controlStructure.l match {
        case List(controlStruct: ControlStructure) =>
          controlStruct.code shouldBe "if (x > 0)"
          controlStruct.controlStructureType shouldBe ControlStructureTypes.IF
          controlStruct.condition.l match {
            case List(cndNode) =>
              cndNode.code shouldBe "x > 0"
            case _ => fail()

          }
          controlStruct.whenTrue.assignments.code.l shouldBe List("y = 0")
        case _ => fail()
      }
    }

    "be correct for if-else" in Fixture("""
        |void method(int x) {
        |  int y;
        |  if (x > 0) {
        |    y = 0;
        |  } else {
        |    y = 1;
        |  }
        |}
      """.stripMargin) { cpg =>
      cpg.method.name("method").controlStructure.l match {
        case List(ifStmt, elseStmt) =>
          ifStmt.controlStructureType shouldBe ControlStructureTypes.IF
          ifStmt.code shouldBe "if (x > 0)"
          elseStmt.controlStructureType shouldBe ControlStructureTypes.ELSE
          elseStmt.code shouldBe "else"

          ifStmt.condition.l match {
            case List(cndNode) =>
              cndNode.code shouldBe "x > 0"
            case _ => fail()
          }

          ifStmt.whenTrue.assignments
            .map(x => (x.target.code, x.source.code))
            .headOption shouldBe Some(("y", "0"))
          ifStmt.whenFalse.assignments
            .map(x => (x.target.code, x.source.code))
            .headOption shouldBe Some(("y", "1"))
        case _ => fail()
      }
    }

    "be correct for conditional expression in call" in Fixture(
      """
         | void method() {
         |   int x = (true ? vlc_dccp_CreateFD : vlc_datagram_CreateFD)(fd);
         | }
      """.stripMargin) { cpg =>
      cpg.method.name("method").ast.isCall.name(Operators.conditional).l match {
        case List(call) =>
          call.code shouldBe "true ? vlc_dccp_CreateFD : vlc_datagram_CreateFD"
        case _ => fail()
      }
    }

    "be correct for conditional expression" in Fixture("""
        | void method() {
        |   int x = (foo == 1) ? bar : 0;
        | }
      """.stripMargin) { cpg =>
      // Just like we cannot use `cpg.method.local`,
      // `cpg.method.call` will not work at this stage
      // either because there are no CONTAINS edges

      cpg.method.name("method").ast.isCall.name(Operators.conditional).l match {
        case List(call) =>
          call.code shouldBe "(foo == 1) ? bar : 0"
          // TODO call.argument => call.argument
          call.start.argument.l match {
            case List(condition, trueBranch, falseBranch) =>
              condition.argumentIndex shouldBe 1
              condition.code shouldBe "foo == 1"
              trueBranch.argumentIndex shouldBe 2
              trueBranch.code shouldBe "bar"
              falseBranch.argumentIndex shouldBe 3
              falseBranch.code shouldBe "0"
            case _ => fail()
          }
        case _ => fail()
      }
    }

    "be correct for ranged for-loop" in Fixture("""
       |void method() {
       |  for (int x : list) {
       |    int z = x;
       |  }
       |}""".stripMargin,
                                                "file.cpp") { cpg =>
      cpg.method.name("method").controlStructure.l match {
        case List(forStmt) =>
          forStmt.controlStructureType shouldBe ControlStructureTypes.FOR
          forStmt.astChildren.order(1).l match {
            case List(ident) =>
              ident.code shouldBe "list"
            case _ => fail()
          }
          forStmt.astChildren.order(2).l match {
            case List(ident) =>
              ident.code shouldBe "x"
            case _ => fail()
          }
          forStmt.astChildren.order(3).l match {
            case List(block) =>
              block.astChildren.isCall.code.l shouldBe List("z = x")
            case _ => fail()
          }
        case _ => fail()
      }
    }

    "be correct for for-loop with multiple initializations" in Fixture("""
        |void method(int x, int y) {
        |  for ( x = 0, y = 0; x < 1; x += 1) {
        |    int z = 0;
        |  }
        |}
      """.stripMargin) { cpg =>
      cpg.method.name("method").controlStructure.l match {
        case List(forStmt) =>
          forStmt.controlStructureType shouldBe ControlStructureTypes.FOR
          childContainsAssignments(forStmt, 1, List("x = 0", "y = 0"))

          forStmt.astChildren.order(2).l match {
            case List(condition: Expression) =>
              condition.code shouldBe "x < 1"
            case _ => fail()
          }

          forStmt.condition.l shouldBe forStmt.astChildren.order(2).l
          childContainsAssignments(forStmt, 3, List("x += 1"))
          childContainsAssignments(forStmt, 4, List("z = 0"))
        case _ => fail()
      }
    }

    def childContainsAssignments(node: AstNode, i: Int, list: List[String]) = {
      node.astChildren.order(i).l match {
        case List(child) =>
          child.assignments.code.l shouldBe list
        case _ => fail()
      }
    }

    "be correct for unary expression '++'" in Fixture("""
        |void method(int x) {
        |  ++x;
        |}
      """.stripMargin) { cpg =>
      cpg.method
        .name("method")
        .ast
        .isCall
        .name(Operators.preIncrement)
        .argument(1)
        .code
        .l shouldBe List("x")
    }

    "be correct for call expression" in Fixture("""
        |void method(int x) {
        |  foo(x);
        |}
      """.stripMargin) { cpg =>
      cpg.method
        .name("method")
        .ast
        .isCall
        .name("foo")
        .argument(1)
        .code
        .l shouldBe List("x")
    }

    "be correct for call expression returning pointer" in Fixture("""
        |int * foo(int arg);
        |int * method(int x) {
        |  foo(x);
        |}
      """.stripMargin) { cpg =>
      cpg.method.name("method").ast.isCall.l match {
        case List(call: Call) =>
          call.name shouldBe "foo"
          // TODO: fix call receiver
          // call.dispatchType shouldBe DispatchTypes.DYNAMIC_DISPATCH
          // val rec = call.receiver.l
          // rec.length shouldBe 1
          // rec.head.code shouldBe "foo"
          // call.argument(0).code shouldBe "foo"
          call.argument(1).code shouldBe "x"
        case _ => fail()
      }
    }

    "be correct for field access" in Fixture("""
        |void method(struct someUndefinedStruct x) {
        |  x.a;
        |}
      """.stripMargin) { cpg =>
      cpg.method.name("method").ast.isCall.name(Operators.fieldAccess).l match {
        case List(call) =>
          val arg1 = call.argument(1)
          val arg2 = call.argument(2)
          arg1.isIdentifier shouldBe true
          arg1.argumentIndex shouldBe 1
          arg1.asInstanceOf[Identifier].name shouldBe "x"
          arg2.isFieldIdentifier shouldBe true
          arg2.argumentIndex shouldBe 2
          arg2.asInstanceOf[FieldIdentifier].code shouldBe "a"
          arg2.asInstanceOf[FieldIdentifier].canonicalName shouldBe "a"
        case _ => fail()
      }
    }

    "be correct for indirect field access" in Fixture("""
        |void method(struct someUndefinedStruct *x) {
        |  x->a;
        |}
      """.stripMargin) { cpg =>
      cpg.method.name("method").ast.isCall.name(Operators.indirectFieldAccess).l match {
        case List(call) =>
          val arg1 = call.argument(1)
          val arg2 = call.argument(2)
          arg1.isIdentifier shouldBe true
          arg1.argumentIndex shouldBe 1
          arg1.asInstanceOf[Identifier].name shouldBe "x"
          arg2.isFieldIdentifier shouldBe true
          arg2.argumentIndex shouldBe 2
          arg2.asInstanceOf[FieldIdentifier].code shouldBe "a"
          arg2.asInstanceOf[FieldIdentifier].canonicalName shouldBe "a"
        case _ => fail()
      }
    }

    "be correct for indirect field access in call" in Fixture("""
          |void method(struct someUndefinedStruct *x) {
          |  return (x->a)(1, 2);
          |}
      """.stripMargin) { cpg =>
      cpg.method.name("method").ast.isCall.name(Operators.indirectFieldAccess).l match {
        case List(call) =>
          val arg1 = call.argument(1)
          val arg2 = call.argument(2)
          arg1.isIdentifier shouldBe true
          arg1.argumentIndex shouldBe 1
          arg1.asInstanceOf[Identifier].name shouldBe "x"
          arg2.isFieldIdentifier shouldBe true
          arg2.argumentIndex shouldBe 2
          arg2.asInstanceOf[FieldIdentifier].code shouldBe "a"
          arg2.asInstanceOf[FieldIdentifier].canonicalName shouldBe "a"
        case _ => fail()
      }
    }

    "be correct for sizeof operator on identifier with brackets" in Fixture(
      """
        |void method() {
        |  int a;
        |  sizeof(a);
        |}
      """.stripMargin
    ) { cpg =>
      cpg.method
        .name("method")
        .ast
        .isCall
        .name(Operators.sizeOf)
        .argument(1)
        .isIdentifier
        .name("a")
        .argumentIndex(1)
        .size shouldBe 1
    }

    "be correct for sizeof operator on identifier without brackets" in Fixture(
      """
        |void method() {
        |  int a;
        |  sizeof a ;
        |}
      """.stripMargin
    ) { cpg =>
      cpg.method
        .name("method")
        .ast
        .isCall
        .name(Operators.sizeOf)
        .argument(1)
        .isIdentifier
        .name("a")
        .argumentIndex(1)
        .size shouldBe 1
    }

    "be correct for sizeof operator on type" in Fixture("""
        |void method() {
        |  sizeof(int);
        |}""".stripMargin,
                                                        "file.cpp") { cpg =>
      cpg.method
        .name("method")
        .ast
        .isCall
        .name(Operators.sizeOf)
        .argument(1)
        .isIdentifier
        .name("int")
        .argumentIndex(1)
        .size shouldBe 1
    }
  }

  "Structural AST layout" should {

    "be correct for empty method" in Fixture("""
       | void method() {
       | };
      """.stripMargin) { cpg =>
      cpg.method.name("method").size shouldBe 1
    }

    "be correct for empty named struct" in Fixture("""
       | struct foo {
       | };
      """.stripMargin) { cpg =>
      cpg.typeDecl.name("foo").size shouldBe 1
    }

    "be correct for struct decl" in Fixture("""
       | struct foo;
      """.stripMargin) { cpg =>
      cpg.typeDecl.name("foo").size shouldBe 1
    }

    "be correct for named struct with single field" in Fixture("""
       | struct foo {
       |   int x;
       | };
      """.stripMargin) { cpg =>
      cpg.typeDecl
        .name("foo")
        .member
        .code("x")
        .name("x")
        .typeFullName("int")
        .size shouldBe 1
    }

    "be correct for named struct with multiple fields" in Fixture("""
        | struct foo {
        |   int x;
        |   int y;
        |   int z;
        | };
      """.stripMargin) { cpg =>
      cpg.typeDecl.name("foo").member.code.toSet shouldBe Set("x", "y", "z")
    }

    "be correct for named struct with nested struct" in Fixture("""
        | struct foo {
        |   int x;
        |   struct bar {
        |     int y;
        |     struct foo2 {
        |       int z;
        |     };
        |   };
        | };
      """.stripMargin) { cpg =>
      cpg.typeDecl.name("foo").l match {
        case List(fooStruct: TypeDecl) =>
          fooStruct.member.name("x").size shouldBe 1
          fooStruct.astChildren.isTypeDecl.l match {
            case List(barStruct: TypeDecl) =>
              barStruct.member.name("y").size shouldBe 1
              barStruct.astChildren.isTypeDecl.l match {
                case List(foo2Struct: TypeDecl) =>
                  foo2Struct.member.name("z").size shouldBe 1
                case _ => fail()
              }
            case _ => fail()
          }
        case _ => fail()
      }
    }

    "be correct for typedef struct" in Fixture(
      """
        |typedef struct foo {
        |} abc;
      """.stripMargin
    ) { cpg =>
      cpg.typeDecl.name("abc").aliasTypeFullName("foo").size shouldBe 1
    }

    "be correct for typedef enum" in Fixture(
      """
        |typedef enum foo {
        |} abc;
      """.stripMargin
    ) { cpg =>
      cpg.typeDecl.name("abc").aliasTypeFullName("foo").size shouldBe 1
    }

    "be correct for single inheritance" in Fixture(
      """
        |class Base {public: int i;};
        |class Derived : public Base{
        |public:
        | char x;
        | int method(){return i;};
        |};
      """.stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.typeDecl
        .name("Derived")
        .count(_.inheritsFromTypeFullName == List("Base")) shouldBe 1
    }

    "be correct for field access" in Fixture(
      """
        |class Foo {
        |public:
        | char x;
        | int method(){return i;};
        |};
        |
        |Foo f;
        |int x = f.method();
      """.stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.typeDecl
        .name("Foo")
        .l
        .size shouldBe 1
      cpg.call.code("f.method()").l match {
        case List(call: Call) =>
          call.methodFullName shouldBe Operators.fieldAccess
          call.argument(1).code shouldBe "f"
          call.argument(2).code shouldBe "method"
        case _ => fail()
      }
    }

    "be correct for type initializer expression" in Fixture(
      """
        |int x = (int){ 1 };
      """.stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.call.name(Operators.cast).l match {
        case List(call: Call) =>
          call.argument(1).code shouldBe "{ 1 }"
          call.argument(2).code shouldBe "int"
        case _ => fail()
      }
    }

    "be correct for static assert" in Fixture(
      """
        |void foo(){
        | int a = 0;
        | static_assert ( a == 0 , "not 0!");
        |}
      """.stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.call.codeExact("static_assert ( a == 0 , \"not 0!\");").l match {
        case List(call: Call) =>
          call.name shouldBe "static_assert"
          call.argument(1).code shouldBe "a == 0"
          call.argument(2).code shouldBe "\"not 0!\""
        case _ => fail()
      }
    }

    "be correct for try catch" in Fixture(
      """
        |void bar();
        |int foo(){
        | try { bar(); } 
        | catch(x) { return 0; };
        |}
      """.stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.controlStructure.l match {
        case List(t) =>
          t.ast.isCall.order(1).code.l shouldBe List("bar()")
          t.ast.isReturn.code.l shouldBe List("return 0;")
        case _ => fail()
      }
    }

    "be correct for constructor initializer" in Fixture(
      """
        |class Foo {
        |public:
        | Foo(int i){};
        |};
        |Foo f1(0);
      """.stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.typeDecl
        .name("Foo")
        .l
        .size shouldBe 1
      cpg.call.codeExact("f1(0)").l match {
        case List(call: Call) =>
          call.name shouldBe "f1"
          call.argument(1).code shouldBe "0"
        case _ => fail()
      }
    }

    "be correct for template class" in Fixture(
      """
        | template<class T>
        | class Y
        | {
        |   void mf() { }
        | };
        | template class Y<char*>;
        | template void Y<double>::mf();
      """.stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.typeDecl
        .name("Y")
        .l
        .size shouldBe 1
    }

    "be correct for template function" in Fixture(
      """
        | template<typename T>
        | void f(T s)
        | { }
        |
        | template void f<double>(double); // instantiates f<double>(double)
        | template void f<>(char); // instantiates f<char>(char), template argument deduced
        | template void f(int); // instantiates f<int>(int), template argument deduced
      """.stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.method
        .name("f")
        .l
        .size shouldBe 1
    }

    "be correct for constructor expression" in Fixture(
      """
        |class Foo {
        |public:
        | Foo(int i) {  };
        |};
        |Foo x = Foo{0};
      """.stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.typeDecl
        .name("Foo")
        .l
        .size shouldBe 1
      cpg.call.codeExact("Foo{0}").l match {
        case List(call: Call) =>
          call.name shouldBe "Foo"
          call.argument(1).code shouldBe "{0}"
        case _ => fail()
      }
    }

    "be correct for method calls" in Fixture(
      """
        |void foo(int x) {
        |  bar(x);
        |}
        |""".stripMargin
    ) { cpg =>
      cpg.method
        .name("foo")
        .ast
        .isCall
        .name("bar")
        .argument
        .code("x")
        .size shouldBe 1
    }

    "be correct for linkage specs" in Fixture(
      """
        |extern "C" {
        | #include <vlc/libvlc.h>
        | #include <vlc/libvlc_renderer_discoverer.h>
        | #include <vlc/libvlc_picture.h>
        | #include <vlc/libvlc_media.h>
        | int x = 0;
        |}
        |""".stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.call.code("x = 0").l.size shouldBe 1
    }

    "be correct for method returns" in Fixture(
      """
        |int d(int x) {
        |  return x * 2;
        |}
        |""".stripMargin
    ) { cpg =>
      // TODO no step class defined for `Return` nodes
      cpg.method.name("d").ast.isReturn.astChildren.order(1).isCall.code.l shouldBe List("x * 2")
    }

    "be correct for binary method calls" in Fixture(
      """
        |int d(int x) {
        |  return x * 2;
        |}
        |""".stripMargin
    ) { cpg =>
      cpg.call.name(Operators.multiplication).code.l shouldBe List("x * 2")
    }

    "be correct for unary method calls" in Fixture(
      """
        |bool invert(bool b) {
        |  return !b;
        |}
        |""".stripMargin
    ) { cpg =>
      cpg.call.name(Operators.logicalNot).argument(1).code.l shouldBe List("b")
    }

    "be correct for unary expr" in Fixture(
      """
        |int strnlen (const char *str, int max)
        |    {
        |      const char *end = memchr(str, 0, max);
        |      return end ? (int)(end - str) : max;
        |    }
        |""".stripMargin
    ) { cpg =>
      cpg.call.name(Operators.cast).astChildren.l match {
        case List(call: Call, tpe: Unknown) =>
          call.code shouldBe "end - str"
          call.argumentIndex shouldBe 1
          tpe.code shouldBe "int"
          tpe.argumentIndex shouldBe 2
        case _ => fail()
      }
    }

    "be correct for post increment method calls" in Fixture(
      """
        |int foo(int x) {
        |  int sub = x--;
        |  int pos = x++;
        |  return pos;
        |}
        |""".stripMargin
    ) { cpg =>
      cpg.call.name(Operators.postIncrement).argument(1).code("x").size shouldBe 1
      cpg.call.name(Operators.postDecrement).argument(1).code("x").size shouldBe 1
    }

    "be correct for conditional expressions containing calls" in Fixture(
      """
        |int abs(int x) {
        |  return x > 0 ? x : -x;
        |}
        |""".stripMargin
    ) { cpg =>
      cpg.call.name(Operators.conditional).argument.code.l shouldBe List("x > 0", "x", "-x")
    }

    "be correct for sizeof expressions" in Fixture(
      """
        |size_t int_size() {
        |  return sizeof(int);
        |}
        |""".stripMargin
    ) { cpg =>
      cpg.call.name(Operators.sizeOf).argument(1).code.l shouldBe List("int")
    }

    "be correct for label" in Fixture("void foo() { label:; }") { cpg =>
      cpg.jumpTarget.code("label:;").size shouldBe 1
    }

    "be correct for array indexing" in Fixture(
      """
        |int head(int x[]) {
        |  return x[0];
        |}
        |""".stripMargin
    ) { cpg =>
      cpg.call.name(Operators.indirectIndexAccess).argument.code.l shouldBe List("x", "0")
    }

    "be correct for type casts" in Fixture(
      """
        |int trunc(long x) {
        |  return (int) x;
        |}
        |""".stripMargin
    ) { cpg =>
      cpg.call.name(Operators.cast).argument.code.l shouldBe List("int", "x")
    }

    "be correct for 'new' array" in Fixture(
      """
        |int * alloc(int n) {
        |   int * arr = new int[n];
        |   return arr;
        |}
        |""".stripMargin,
      "file.cpp"
    ) { cpg =>
      // TODO: "<operator>.new" is not part of Operators
      cpg.call.name("<operator>.new").code("new int\\[n\\]").argument.code("int").size shouldBe 1
    }

    "be correct for 'new' object" in Fixture(
      """
        |Foo* alloc(int n) {
        |   Foo* foo = new Foo(n, 42);
        |   return foo;
        |}
        |""".stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.call.name("<operator>.new").codeExact("new Foo(n, 42)").argument.code("Foo").size shouldBe 1
    }

    "be correct for simple 'delete'" in Fixture(
      """
        |int delete_number(int* n) {
        |  delete n;
        |}
        |""".stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.call.name(Operators.delete).code("delete n").argument.code("n").size shouldBe 1
    }

    "be correct for array 'delete'" in Fixture(
      """
        |void delete_number(int n[]) {
        |  delete[] n;
        |}
        |""".stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.call.name(Operators.delete).codeExact("delete[] n").argument.code("n").size shouldBe 1
    }

    "be correct for const_cast" in Fixture(
      """
        |void foo() {
        |  int y = const_cast<int>(n);
        |  return;
        |}
        |""".stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.call.name(Operators.cast).codeExact("const_cast<int>(n)").argument.code.l shouldBe List("int", "n")
    }

    "be correct for static_cast" in Fixture(
      """
        |void foo() {
        |  int y = static_cast<int>(n);
        |  return;
        |}
        |""".stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.call.name(Operators.cast).codeExact("static_cast<int>(n)").argument.code.l shouldBe List("int", "n")
    }

    "be correct for dynamic_cast" in Fixture(
      """
        |void foo() {
        |  int y = dynamic_cast<int>(n);
        |  return;
        |}
        |""".stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.call.name(Operators.cast).codeExact("dynamic_cast<int>(n)").argument.code.l shouldBe List("int", "n")
    }

    "be correct for reinterpret_cast" in Fixture(
      """
        |void foo() {
        |  int y = reinterpret_cast<int>(n);
        |  return;
        |}
        |""".stripMargin,
      "file.cpp"
    ) { cpg =>
      cpg.call.name(Operators.cast).codeExact("reinterpret_cast<int>(n)").argument.code.l shouldBe List("int", "n")
    }
  }

  "AST" should {
    "have correct line number for method content" in Fixture("""
       |
       |
       |
       |
       | void method(int x) {
       |
       |   x = 1;
       | }
      """.stripMargin) { cpg =>
      cpg.method.name("method").lineNumber.l shouldBe List(6)
      cpg.method.name("method").block.assignments.lineNumber.l shouldBe List(8)
    }

    // for https://github.com/ShiftLeftSecurity/codepropertygraph/issues/1321
    "have correct line numbers example 1" in Fixture("""
       |int main() {
       |int a = 0;
       |statementthatdoesnothing();
       |int b = 0;
       |int c = 0;
       |}
      """.stripMargin) { cpg =>
      cpg.identifier.l match {
        case List(a, b, c) =>
          a.lineNumber shouldBe Some(3)
          a.columnNumber shouldBe Some(4)
          b.lineNumber shouldBe Some(5)
          b.columnNumber shouldBe Some(4)
          c.lineNumber shouldBe Some(6)
          c.columnNumber shouldBe Some(4)
        case _ => fail()
      }
    }

    // for https://github.com/ShiftLeftSecurity/codepropertygraph/issues/1321
    "have correct line/column numbers on all platforms" in {
      val windowsNewline = "\r\n"
      val windowsFixture: Cpg = CpgFixture(
        s"void offset() {${windowsNewline}char * data = NULL;${windowsNewline}memset(data, 'A', 100-1); /* fill with 'A's */${windowsNewline}data = dataBuffer;$windowsNewline}")
      val macNewline = "\r"
      val macFixture: Cpg = CpgFixture(
        s"void offset() {${macNewline}char * data = NULL;${macNewline}memset(data, 'A', 100-1); /* fill with 'A's */${macNewline}data = dataBuffer;$macNewline}")
      val linuxNewline = "\n"
      val linuxFixture: Cpg = CpgFixture(
        s"void offset() {${linuxNewline}char * data = NULL;${linuxNewline}memset(data, 'A', 100-1); /* fill with 'A's */${linuxNewline}data = dataBuffer;$linuxNewline}")

      val windowsLineNumbers = windowsFixture.identifier.lineNumber.l
      val macLineNumbers = macFixture.identifier.lineNumber.l
      val linuxLineNumbers = linuxFixture.identifier.lineNumber.l

      windowsLineNumbers should not be empty
      macLineNumbers should not be empty
      linuxLineNumbers should not be empty

      windowsLineNumbers shouldBe macLineNumbers
      windowsLineNumbers shouldBe linuxLineNumbers
      macLineNumbers shouldBe linuxLineNumbers

      val windowsColumnNumbers = windowsFixture.identifier.columnNumber.l
      val macColumnNumbers = macFixture.identifier.columnNumber.l
      val linuxColumnNumbers = linuxFixture.identifier.columnNumber.l

      windowsColumnNumbers should not be empty
      macColumnNumbers should not be empty
      linuxColumnNumbers should not be empty

      windowsColumnNumbers shouldBe macColumnNumbers
      windowsColumnNumbers shouldBe linuxColumnNumbers
      macColumnNumbers shouldBe linuxColumnNumbers
    }
  }

}
